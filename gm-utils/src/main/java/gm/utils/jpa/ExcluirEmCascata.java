package gm.utils.jpa;

import gm.utils.comum.ULog;
import gm.utils.config.UConfig;
import gm.utils.exception.UException;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;

public class ExcluirEmCascata {

	private final ConexaoJdbc con;

	public ExcluirEmCascata() {
		this( UConfig.con() );
	}

	public ExcluirEmCascata(ConexaoJdbc con) {
		this.con = con;
	}

	public void exec(Class<?> classe, String where) {
		exec(TableSchema.get(classe), where);

	}
	public void exec(Class<?> classe, Integer id) {
		Atributo atributoId = AtributosBuild.getId(classe);
		exec(classe, atributoId.nome() + " = " + id);
	}
//	public void exec(String ts, String where) {
//		String schema;
//		String table;
//		if (ts.contains(".")) {
//			schema = StringBeforeFirst.get(ts, ".");
//			table = StringAfterFirst.get(ts, ".");
//		} else {
//			schema = schemaPadrao;
//			table = ts;
//		}
//		exec(schema, table, where);
//	}

	public boolean exec(String ts, String where) {

		String sql = "select count(*) from " + ts + " where " + where;

		if ( con.selectInt(sql) == 0 ) {
			ULog.debug(sql + " -- (0)");
			return false;
		}

		sql = "delete from " + ts + " where " + where;

		try {
			ULog.debug(sql);
			con.exec(sql);
		} catch (Exception e) {

			try {

				String message = e.getMessage();
				ULog.debug(message);

				if ( !message.contains("constraint") ) {
					ULog.debug( ">>>" + sql );
					ULog.debug( ">>>" + message);
					ULog.debug( ">>>" + con.getBanco());
					throw UException.runtime("!message.contains constraint");
				}

				String s = StringAfterFirst.get(message, "constraint \"");

				String constraint = StringBeforeFirst.get(s, "\"");

				if (con.getDriver().equals(DriverJDBC.PostgreSQL)) {
					s = StringAfterFirst.get(s, "on table \"");
				} else if (con.getDriver().equals(DriverJDBC.MSSQLServer)) {
					s = StringAfterFirst.get(s, ", table \"");
				} else {
					throw UException.runtime("Driver não tratado: " + con.getDriver());
				}

				s = StringBeforeFirst.get(s, "\"");
				String tableRef = s;

				if (!tableRef.contains(".")) {
					String refSchema = con.schemaConstraint(constraint, tableRef);
					tableRef = refSchema + "." + tableRef;
				}

				String nomeColunaId = con.getNomeColunaId(ts);

				if (con.getDriver().equals(DriverJDBC.PostgreSQL)) {
					ListString columnsReference = con.columnReference(tableRef, ts, constraint);
					for (String column : columnsReference) {
						String newWhere = column + " in (select "+nomeColunaId+" from " + ts + " where " + where + ")";
						exec(tableRef, newWhere);
					}
				} else {
					s = StringAfterLast.get(message, "column '");
					s = StringBeforeLast.get(s, "'");
					String newWhere = s + " in (select "+nomeColunaId+" from " + ts + " where " + where + ")";
					exec(tableRef, newWhere);
				}
				exec(ts, where);

			} catch (Exception e2) {
				throw e2;
			}

		}
		return true;
	}
}

//
