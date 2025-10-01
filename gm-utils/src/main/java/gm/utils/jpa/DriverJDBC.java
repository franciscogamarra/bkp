package gm.utils.jpa;

import gm.utils.exception.NaoImplementadoException;
import gm.utils.lambda.Resolve;
import lombok.Getter;

@Getter
public abstract class DriverJDBC {

	public static final String SCHEMA_VIEWS = "jdbc";

	public String getDialect() {
		throw new NaoImplementadoException();
	}

	public String getDefaultSchema() {
		throw new NaoImplementadoException();
	}

	public String getDriverName() {
		throw new NaoImplementadoException();
	}

	public String getNomeBanco() {
		throw new NaoImplementadoException();
	}

	protected void startPropertyes() {
//		throw new NaoImplementadoException();
	}

	public String getScriptRestoreDate() {
		throw new NaoImplementadoException();
	}

	public String getDefaultUser() {
		throw new NaoImplementadoException();
	}

	public String getDefaultPass() {
		throw new NaoImplementadoException();
	}

	public boolean existsSchema(ConexaoJdbc con, String nome) {
		throw new NaoImplementadoException();
	}

	public boolean existsView(ConexaoJdbc con, String schema, String name) {
		throw new NaoImplementadoException();
	}

	public void createSchema(ConexaoJdbc con, String nome) {
		con.exec("create schema " + nome);
	}

	public String getNomeColunaId(ConexaoJdbc con, String ts) {
		throw new NaoImplementadoException();
	}

	public int getCurrentSequence(ConexaoJdbc con, String schema, String table) {
		throw new NaoImplementadoException();
	}

	protected void criarViews(ConexaoJdbc con) {

		boolean precisaCriar = Resolve.ft(() -> {

			if (!existsSchema(con, SCHEMA_VIEWS)) {
				createSchema(con, SCHEMA_VIEWS);
				return true;
			}

			if (!existsView(con, SCHEMA_VIEWS, "versao")) {
				return true;
			}
			int versao = con.selectInt("select versao from " + SCHEMA_VIEWS + ".versao");
			if ((versao != getVersao()) || !con.existsView(SCHEMA_VIEWS, "tabelas") || !con.existsView(SCHEMA_VIEWS, "colunas") || !con.existsView(SCHEMA_VIEWS, "esquemas")) {
				return true;
			}

			return false;

		});

		if (precisaCriar) {
			con.dropView(SCHEMA_VIEWS, "tabelas");
			con.dropView(SCHEMA_VIEWS, "colunas");
			con.dropView(SCHEMA_VIEWS, "versao");
			con.dropView(SCHEMA_VIEWS, "esquemas");
			createViewVersao(con);
			createViewSchemas(con);
			createViewTabelas(con);
			createViewColunas(con);
		}

	}

	protected abstract int getVersao();

	protected void createViewSchemas(ConexaoJdbc con) {
		throw new NaoImplementadoException();
	}

	protected void createViewColunas(ConexaoJdbc con) {
		throw new NaoImplementadoException();
	}

	protected void createViewTabelas(ConexaoJdbc con) {
		throw new NaoImplementadoException();
	}

	private void createViewVersao(ConexaoJdbc con) {
		con.exec("create view "+SCHEMA_VIEWS+".versao as select " + getVersao() + " as versao");
	}

	public static final DriverJDBC DB2 = DriverJDBCDB2.getInstance();
	public static final DriverJDBC MSSQLServer = DriverJDBCSqlServer.getInstance();
	public static final DriverJDBC PostgreSQL = DriverJDBCPostgreSQL.instance;
	public static final DriverJDBC MySQL = DriverJDBCMySql.instance;

	public String beforeExec(String sql) {
		return sql;
	}
	
	public void dropColumn(ConexaoJdbc con, String ts, String nome) {
		String s = "alter table " + ts + " drop column " + nome;
		con.exec(s);
	}

	public String getUrl(String banco) {
		return "jdbc:"+getNomeBanco()+"://" + banco;
	}

	protected String getSqlTest() {
		return "select 1 as x";
	}

	public Class<?> getTipoColuna(String s) {
		return null;
	}

}
