package gm.utils.jpa;

public class DriverJDBCPostgreSQL extends DriverJDBC {

	private DriverJDBCPostgreSQL() {}

	public static final DriverJDBC instance = new DriverJDBCPostgreSQL();

	@Override
	public String getDefaultSchema() {
		return "public";
	}

	@Override
	public String getDialect() {
		return "org.hibernate.dialect.PostgreSQLDialect";
	}

	@Override
	public String getDriverName() {
		return "org.postgresql.Driver";
	}

	@Override
	public String getNomeBanco() {
		return "postgresql";
	}

	@Override
	protected void startPropertyes() {}

	@Override
	public String getDefaultUser() {
		return "postgres";
	}

	@Override
	public String getDefaultPass() {
		return "postgres";
	}

	@Override
	public boolean existsView(ConexaoJdbc con, String schema, String name) {
		String s = "select count(*) from pg_catalog.pg_views where schemaname = '"+schema+"' and viewname = '"+name+"'";
		return con.selectInt(s) > 0;
	}

	@Override
	protected void createViewTabelas(ConexaoJdbc con) {

		String sql = ""
				+ "  create view "+SCHEMA_VIEWS+".tabelas as"
				+ "  select lower(schemaname) as esquema, tablename as tabela"
				+ "  from pg_catalog.pg_tables"
				+ "  where schemaname not in ('pg_catalog','information_schema')"
				;

		con.exec(sql);

	}

	@Override
	public boolean existsSchema(ConexaoJdbc con, String nome) {
		return con.exists("information_schema.schemata", "schema_name = '"+nome+"'");
	}

	@Override
	protected void createViewColunas(ConexaoJdbc con) {

		String sql = ""
				+ "  create view "+SCHEMA_VIEWS+".colunas as"
				+ "  select"
				+ "  lower(b.schemaname) as esquema,"
				+ "  a.table_name as tabela,"
				+ "  a.column_name as coluna,"
				+ "  case"
				+ "  when a.data_type = 'character varying' then 'String('||a.character_maximum_length||')'"
				+ "  when data_type = 'numeric' then 'Numeric('||numeric_precision||','||numeric_scale||')'"
				+ "  when data_type = 'timestamp without time zone' then 'DateTime'"
				+ "  when data_type = 'integer' then 'int'"
				+ "  else a.data_type"
				+ "  end as tipo"
				+ "  from information_schema.columns as a"
				+ "  inner join pg_catalog.pg_tables as b on a.table_name = b.tablename and a.table_schema = b.schemaname"
				+ "  where b.schemaname not in ('pg_catalog','information_schema')"
				;

		con.exec(sql);

	}

	@Override
	protected void createViewSchemas(ConexaoJdbc con) {
		String sql = "create view "+SCHEMA_VIEWS+".esquemas as select schema_name as esquema from information_schema.schemata where schema_name not like 'pg\\_%'";
		con.exec(sql);
	}

	@Override
	public String beforeExec(String sql) {
		sql = sql.replace(" int identity(1,1)", " serial");
		sql = sql.replace(" bit ", " boolean ");
		sql = sql.replace(" bit,", " boolean,");
		sql = sql.replace("<<false>>", "false");
		return sql.replace("<<true>>", "true");
	}

	@Override
	protected int getVersao() {
		return 20220205;
	}
	
	public static void main(String[] args) {
		
	}

}
