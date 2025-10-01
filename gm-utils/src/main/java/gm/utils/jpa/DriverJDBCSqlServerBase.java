package gm.utils.jpa;

import gm.utils.comum.SystemPrint;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringReplacePalavra;

public abstract class DriverJDBCSqlServerBase extends DriverJDBC {

	protected DriverJDBCSqlServerBase() {}

	@Override
	public String getDefaultSchema() {
		return "dbo";
	}

	@Override
	public String getDialect() {
		return "org.hibernate.dialect.SQLServerDialect";
	}

	@Override
	public String getDriverName() {
		return "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	}

	@Override
	public String getNomeBanco() {
		return "sqlserver";
	}

	@Override
	protected void startPropertyes() {
		System.setProperty("java.net.preferIPv6Addresses", "true");
	}

	@Override
	public String getScriptRestoreDate() {

		return ""
				+ "  DECLARE @DB sysname = 'SICOP',"
				+ "  @MaxRestoreDate DATETIME;"
				+ "  SELECT @MaxRestoreDate = MAX(restore_date)"
				+ "  FROM msdb.dbo.restorehistory"
				+ "  WHERE destination_database_name = @DB"
				+ "  AND restore_type = 'D';"
				+ "  SELECT a.restore_date,"
				+ "  a.destination_database_name,"
				+ "  a.user_name,"
				+ "  a.restore_type,"
				+ "  b.name,"
				+ "  b.backup_start_date,"
				+ "  b.backup_finish_date"
				+ "  FROM msdb.dbo.restorehistory AS a"
				+ "  INNER JOIN msdb.dbo.backupset AS b"
				+ "  ON b.backup_set_id = a.backup_set_id"
				+ "  WHERE destination_database_name = @DB"
				+ "  AND a.restore_type = 'D'"
				+ "  AND a.restore_date >= @MaxRestoreDate;"
				;

	}

	@Override
	public int getCurrentSequence(ConexaoJdbc con, String schema, String table) {
		return con.selectInt("SELECT id = IDENT_CURRENT ('"+schema+"."+table+"')");
	}

	@Override
	public boolean existsView(ConexaoJdbc con, String schema, String name) {
		String s = "select count(*) from sys.views v inner join sys.schemas s on s.schema_id = v.schema_id where s.name = '"+schema+"' and v.name = '"+name+"'";
		return con.selectInt(s) > 0;
	}

	@Override
	public boolean existsSchema(ConexaoJdbc con, String nome) {
		String s = "select count(*) from sys.schemas where lower(name) = '"+nome+"'";
		return con.selectInt(s) > 0;
	}

	private static final String SQL_GET_COLUMN_NAME = ""
			+ "  SELECT top 1 COLUMN_NAME"
			+ "  FROM INFORMATION_SCHEMA.KEY_COLUMN_USAGE"
			+ "  WHERE OBJECTPROPERTY(OBJECT_ID(CONSTRAINT_SCHEMA + '.' + QUOTENAME(CONSTRAINT_NAME)), 'IsPrimaryKey') = 1"
			+ "  AND TABLE_NAME = '%s' AND TABLE_SCHEMA = '%s'"
			;

	@Override
	public String getNomeColunaId(ConexaoJdbc con, String ts) {
		String schema = StringBeforeFirst.get(ts, ".");
		String table = StringAfterFirst.get(ts, ".");
		String sql = String.format(SQL_GET_COLUMN_NAME, table, schema);
		return con.selectString(sql);
	}

	@Override
	protected void createViewTabelas(ConexaoJdbc con) {

		String sql = ""
				+ "  create view "+SCHEMA_VIEWS+".tabelas as"
				+ "  select esquema = s.name, tabela = t.name"
				+ "  from sys.tables t"
				+ "  inner join sys.schemas s on t.schema_id = s.schema_id"
				;

		con.exec(sql);

	}

	@Override
	protected void createViewColunas(ConexaoJdbc con) {

		String sql = ""
				+ "  create view "+SCHEMA_VIEWS+".colunas as"
				+ "  select"
				+ "  esquema = s.name, tabela = t.name, coluna = c.name,"
				+ "  tipo = case"
				+ "  when c.user_type_id in (99,165,231,239,241,35)"
				+ "  then 'String(8000)'"
				+ "  when c.user_type_id = 34"
				+ "  then 'image'"
				+ "  when c.user_type_id = 36"
				+ "  then 'String(36)'"
				+ "  when c.user_type_id in (167,175)"
				+ "  then 'String(' + cast(c.max_length as varchar(10)) + ')'"
				+ "  when c.user_type_id = 127"
				+ "  then 'long'"
				+ "  when c.user_type_id in (56,48,52)"
				+ "  then 'int'"
				+ "  when c.user_type_id = 40"
				+ "  then 'Date'"
				+ "  when c.user_type_id in (61,58,42)"
				+ "  then 'DateTime'"
				+ "  when c.user_type_id in (189)"
				+ "  then 'timestamp'"
				+ "  when c.user_type_id = 104"
				+ "  then 'boolean'"
				+ "  when c.user_type_id in (60)"
				+ "  then 'Numeric(9,2)'"
				+ "  when c.user_type_id in (106,108,122,59,62)"
				+ "  then 'Numeric(' + cast(c.precision as varchar(10)) + ',' + cast(c.scale as varchar(10)) + ')'"
				+ "  else '? (' + cast(c.user_type_id as varchar(10)) + ')'"
				+ "  end,"
				+ "  fk = sfk.name + '.' + tfk.name + '(' + cfk.name + ')',"
				+ "  pk = cast(case when cnt.constraint_type is null then 0 else 1 end as bit),"
				+ "  case when c.is_nullable = 1 then 0 else 1 end as isNotNull,"
				+ "  c.user_type_id"
				+ "  from sys.columns c"
				+ "  inner join sys.tables t on c.object_id = t.object_id"
				+ "  inner join sys.schemas s on t.schema_id = s.schema_id"
				+ "  left join sys.foreign_key_columns fks"
				+ "  on fks.parent_object_id = t.object_id"
				+ "  and fks.parent_column_id = c.column_id"
				+ "  left join sys.columns cfk"
				+ "  on cfk.object_id = fks.referenced_object_id"
				+ "  and cfk.column_id = fks.referenced_column_id"
				+ "  left join sys.tables tfk"
				+ "  on tfk.object_id = fks.referenced_object_id"
				+ "  left join sys.schemas sfk"
				+ "  on sfk.schema_id = tfk.schema_id"
				+ "  left join INFORMATION_SCHEMA.KEY_COLUMN_USAGE col"
				+ "  on col.table_name = t.name"
				+ "  and col.table_schema = s.name"
				+ "  and col.column_name = c.name"
				+ "  left join INFORMATION_SCHEMA.TABLE_CONSTRAINTS cnt"
				+ "  on col.constraint_catalog = cnt.constraint_catalog"
				+ "  and col.constraint_schema = cnt.constraint_schema"
				+ "  and col.constraint_name = cnt.constraint_name"
				+ "  and cnt.constraint_type = 'PRIMARY KEY'"
				//			+ "  /* SELECT TYPE_NAME(36)*/"
				//			+ "  /* 61,58,189,42*/"
				;

		con.exec(sql);

	}

	@Override
	protected void createViewSchemas(ConexaoJdbc con) {
		String sql = "create view "+SCHEMA_VIEWS+".esquemas as select esquema = name from sys.schemas";
		con.exec(sql);
	}

	@Override
	public String beforeExec(String sql) {
		sql = sql.replace("||", "+");
		sql = sql.replace(" serial ", " int identity(1,1) ");
		sql = sql.replace(" boolean ", " bit ");
		sql = sql.replace(" boolean,", " bit,");
		sql = sql.replace("<<false>>", "0");
		sql = sql.replace("<<true>>", "1");
		sql = StringReplacePalavra.exec(sql, "true", "1");
		return StringReplacePalavra.exec(sql, "false", "0");
	}

	@Override
	protected int getVersao() {
		return 20220205;
	}
	
	@Override
	public void dropColumn(ConexaoJdbc con, String ts, String nome) {
		
		try {
			super.dropColumn(con, ts, nome);
		} catch (Exception e) {
			
			String s = e.getMessage();
			
			if (s.startsWith("com.microsoft.sqlserver.jdbc.SQLServerException: The object 'FK")) {
				s = StringAfterFirst.get(s, "'");
				s = StringBeforeFirst.get(s, "'");
				con.exec("alter table "+ts+" drop constraint " + s);
				dropColumn(con, ts, nome);
				return;
			}
			
			if (s.startsWith("com.microsoft.sqlserver.jdbc.SQLServerException: The index '")) {
				s = StringAfterFirst.get(s, "'");
				s = StringBeforeFirst.get(s, "'");
				s = "drop index " + ts + "." + s;
				con.exec(s);
				dropColumn(con, ts, nome);
				return;
			}
			
			SystemPrint.ln(s);
			
//			com.microsoft.sqlserver.jdbc.SQLServerException: The object 'FK__Tb_Chamad__Id_Me__681373AD' is dependent on column 'Id_Metodo'.
			
//			alter table tb_log_erro_mobile drop constraint FK__TB_LOG_ER__id_me__3493CFA7
			
			e.printStackTrace();
		}
		
	}

}
