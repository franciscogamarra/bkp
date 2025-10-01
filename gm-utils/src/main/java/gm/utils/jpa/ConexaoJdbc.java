package gm.utils.jpa;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import gm.utils.abstrato.ObjetoComId;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.UAssert;
import gm.utils.comum.UBoolean;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.comum.USystem;
import gm.utils.comum.UType;
import gm.utils.date.Cronometro;
import gm.utils.date.Data;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.jpa.nativeQuery.QRow;
import gm.utils.map.MapSO;
import gm.utils.number.ListInteger;
import gm.utils.number.ULong;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import js.support.console;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringSplit;

public class ConexaoJdbc {

	public static Map<String, ConexaoJdbc> conexoes = new HashMap<>();
	public static int limitDefault = 200;
	private static int idArquivo = 0;
	private static String nomeArquivo = ConexaoJdbc.getNomeArquivo();
	@Getter private final String banco;
	
	@Getter @Setter
	private String database;

	public ListString fileLog;
	public boolean keep_alive = false;
	public boolean debugOnExec = false;
	private ListString alteracoes;
	private String user = "postgres";
	private String pass = "postgres";

	private Connection con;
	private final Map<Class<?>,UTable> tables = new HashMap<>();

	public boolean conseguiuConectarAPrimeiraVez;
	private final boolean reconectarAutomaticamente = true;

	public boolean viewsParaFuncionamentoCriadas;
	public void criarViewsParaFuncionamento() {

		if (viewsParaFuncionamentoCriadas) {
			return;
		}

		driver.criarViews(this);

		viewsParaFuncionamentoCriadas = true;

	}

	@Getter
	private final DriverJDBC driver;

	public void testaConexao() {
		try {
			this.selectInt(driver.getSqlTest());
			ULog.info("ok " + banco);
		} catch (Exception e) {
			ULog.error("erro " + banco);
			UException.printTrace(e);
		}
	}

	public static ConexaoJdbc get(String url){
		return get(url, null, null);
	}

	public static ConexaoJdbc get(String url, String user, String pass){

		/*
		 jdbc:postgresql://localhost:5432/cooper-gestao-de-taxas
		*/

		if (!url.startsWith("jdbc:")) {
			throw new RuntimeException("???");
		}

		String s = StringAfterFirst.get(url, ":");

		String nomeDriver = StringBeforeFirst.get(s, ":");

		s = StringAfterFirst.get(s, ":").substring(2);

		DriverJDBC driver;

		if (nomeDriver.contentEquals("postgresql")) {
			driver = DriverJDBC.PostgreSQL;
		} else if (nomeDriver.contentEquals("sqlserver")) {
			driver = DriverJDBC.MSSQLServer;
		} else {
			throw new RuntimeException("???");
		}

		return ConexaoJdbc.get(s, driver, user, pass);

	}

	public static ConexaoJdbc get(String banco, DriverJDBC driver, String user, String pass){
		ConexaoJdbc o = ConexaoJdbc.conexoes.get(banco);
		if (o == null) {
			o = ConexaoJdbc.getNew(banco, driver, user, pass);
		}
		return o;
	}

	public static ConexaoJdbc getNew(String banco, DriverJDBC driver, String user, String pass){
		ConexaoJdbc o = new ConexaoJdbc(banco, driver, user, pass);
		ConexaoJdbc.conexoes.put(banco, o);
		return o;
	}

	private static DriverJDBC getDriver(Connection con) {
		if (con == null) {
			throw UException.runtime("con == null");
		}
		if (con.getClass().getName().contains("PgConnection")) {
			return DriverJDBC.PostgreSQL;
		}
		if (con.getClass().getName().contains("SQLServerConnection")) {
			return DriverJDBC.MSSQLServer;
		}
		throw UException.runtime("nao implementado " + con.getClass());
	}
	
	public ConexaoJdbc(Connection con) {
		if (con == null) {
			throw new RuntimeException("con == null");
		}
		this.con = con;
		banco = null;
		driver = ConexaoJdbc.getDriver(con);
	}

	protected ConexaoJdbc(String banco, DriverJDBC driver, String user, String pass){

		this.banco = banco;
		this.driver = driver;

		if (user == null) {
			this.user = driver.getDefaultUser();
		} else {
			this.user = user;
		}

		if (this.pass == null) {
			this.user = driver.getDefaultPass();
		} else {
			this.pass = pass;
		}

	}

	private Connection connection(){
		if ( con == null ) {
			createConnection();
			return con;
		}

		try {
			if ( con.isClosed() ) {
				createConnection();
				return con;
			}
		} catch (Exception e) {}

		return con;
	}

	@Setter
	private String defaultSchema;

	public String getDefaultSchema() {

		if (defaultSchema == null) {
			defaultSchema = USchema.getSchemaDefault();
			if (defaultSchema == null) {
				defaultSchema = driver.getDefaultSchema();
			}
		}

		return defaultSchema;
	}

	private void createConnectionTry() {

		UAssert.notEmpty(banco, "banco is null");

		try {
			
			driver.startPropertyes();
			Class<?> forName = Class.forName(driver.getDriverName());
			UClass.newInstance(forName);
			url = driver.getUrl(banco);
			ULog.debug(">> Conectando a "+url+" com user "+user+" <<");
			con = DriverManager.getConnection(url, user, pass);
			conseguiuConectarAPrimeiraVez = true;
		} catch (Exception e) {
			throw UException.runtime(e);
		}

	}

	private void createConnection() {

		if (!conseguiuConectarAPrimeiraVez) {
			createConnectionTry();
			return;
		}

		if (reconectarAutomaticamente) {

			Exception ex = null;

			for (int i = 0; i < 100; i++) {

				try {
					createConnectionTry();
					return;
				} catch (Exception e) {

					if (i == 0) {
						ex = e;
						UException.printTrace(e);
					}

					try {
						if (con != null) {
							con.close();
						}
					} catch (Exception e2) {}

					con = null;
					ULog.error(">> A conexao caiu - tentando recuperar em 10 minutos - tentativa " + i + " de 100");
					USystem.sleepMinutos(10);

				}

			}

			throw UException.runtime(ex);

		}

		throw UException.runtime("Conexao perdida!");

	}

	private UDataSource ds;

	public DataSource getDataSource() {

		if (ds == null) {

			ds = new UDataSource() {
				@Override
				public Connection getConnection() {
					try {
						return ConexaoJdbc.this.connection();
					} catch (Exception e) {
						throw UException.runtime("bla");
					}
				}
			};

		}

		return ds;
	}

	public int tryExec(String sql) {
		return this.tryExec(sql, true);
	}

	public int tryExec(String sql, boolean printStackTrace) {
		try {
			return this.exec(sql);
		} catch (Exception e) {
			if (printStackTrace) {
				UException.printTrace(e);
			}
			return -1;
		}
	}

	private Thread thread;
	private int threadResult;

	public void execThread(String sql) {
		threadResult = -1;
		thread = new Thread() {
			@Override
			public void run() {
				threadResult = ConexaoJdbc.this.exec(sql);
			}
		};
		thread.start();
	}

	public boolean isRodando() {
		return thread != null && thread.isAlive();
	}

	public int getThreadResult() {
		while (isRodando()) {
			
		}
		return threadResult;
	}

	public int exec(File file) {
		return exec(new ListString().load(file));
	}

	public int exec(GFile file) {
		return exec(file.toFile());
	}
	
	public int exec(Lst<String> lst) {
		ListString list = new ListString();
		list.addAll(lst);
		return exec(list);
	}
	
	public int exec(ListString list) {
		list.trimPlus();
		list.eachRemoveAfter("--", true);
		String s = list.toString(" ") + " ";
		s = s.replace(" GO ", " go ");
		s = s.replace(" GO;", " go ;");
		Lst<String> itens = StringSplit.exec(s, " go ").getArray().list;
		return itens.somaInt(i -> exec(i));
	}

	public int exec(String sql) {

		sql = preparaSql(sql);

		if (!sql.trim().endsWith(";")) {
			sql += ";";
		}

		if (ULog.debug && !debugOnExec) {
			ULog.debug(sql);
		}
		
		Cronometro cron = new Cronometro();
		
		try (Statement stmt = createStatementExec(sql)) {

			int result = stmt.executeUpdate(sql);

			if (sql.contains("alter") || sql.contains("drop") || sql.contains("create")) {
				result = 1;
			}

			if (debugOnExec && result > 0) {
				ULog.debug = true;
				ULog.debug(sql);
				ULog.debug = false;

				if (alteracoes == null) {
					alteracoes = new ListString();
				}

				ListString rows = ListString.split(sql, "\n");
				alteracoes.add(rows);
				alteracoes.save(ConexaoJdbc.nomeArquivo);

				if (alteracoes.size() > 5000) {
					alteracoes.clear();
					ConexaoJdbc.idArquivo++;
					ConexaoJdbc.nomeArquivo = ConexaoJdbc.getNomeArquivo();
				}

			}

			Cronometro.print(cron, sql);

			if (fileLog != null) {
				fileLog.add(sql + " = " + result);
			}

			return result;
		} catch (Exception e) {
			console.log("erro:>>" + sql);
			console.log(">>>>>>>" + banco);
			throw UException.runtime(e);
		}
	}

	public void addExecute(String sql, int result) {

		if (sql.contains("alter") || sql.contains("drop") || sql.contains("create") || sql.contains("select setval(")) {
			result = 1;
		}

		if (debugOnExec && result > 0) {
			ULog.debug = true;
			ULog.debug(sql);
			ULog.debug = false;

			if (alteracoes == null) {
				alteracoes = new ListString();
			}

			sql = sql.trim();
			if (!sql.endsWith(";")) {
				sql += ";";
			}

			ListString rows = ListString.split(sql, "\n");
			alteracoes.add(rows);
			alteracoes.save(ConexaoJdbc.nomeArquivo);

			if (alteracoes.size() > 1000) {
				alteracoes.clear();
				ConexaoJdbc.idArquivo++;
				ConexaoJdbc.nomeArquivo = ConexaoJdbc.getNomeArquivo();
			}

		}
	}

	private static String getNomeArquivo() {
		return "/opt/tmp/script/script"+ IntegerFormat.xx(ConexaoJdbc.idArquivo) +".txt";
	}

	public Integer selectInt(String sql, Integer def){
		return IntegerParse.toIntDef( this.selectInt(sql), def );
	}
	public Integer selectInt(String sql){
		return IntegerParse.toInt( selectField1(sql) );
	}
	public Boolean getBoolean(String sql){
		return UBoolean.toBoolean(selectField1(sql));
	}
	public boolean isTrue(String sql){
		return UBoolean.isTrue(getBoolean(sql));
	}
	public boolean isFalse(String sql){
		return UBoolean.isFalse(getBoolean(sql));
	}
	public Long selectLong(String sql){
		return ULong.toLong( selectField1(sql) );
	}
	public Data selectData(String sql) {
		return Data.to( selectField1(sql) );
	}
	public String selectString(String sql){
		return StringParse.get( selectField1(sql) );
	}
	public Object selectField1(String sql){
		Map<String, Object> map = selectMapUnique(sql);
		if (map == null) {
			return null;
		}
		return map.entrySet().iterator().next().getValue();
	}
	public MapSO selectMapUnique(String sql) {
		List<MapSO> select = selectMap(sql);
		if (select.isEmpty()) {
			return null;
		}
		if ( select.size() > 1 ) {
			throw UException.runtime("A consulta retornou + de 1 resultado - " + sql);
		}
		return select.get(0);
	}
	public ListString selectStrings(String sql) {
		ListString list = new ListString();
		for (Object[] os : this.rs(sql).dados) {
			Object o = os[0];
			if (UObject.isEmpty(o)) {
				continue;
			}
			list.add(StringParse.get(o));
		}
		return list;
	}
	public ListInteger selectInts(String sql) {
		ListInteger list = new ListInteger();
		for (Object[] os : this.rs(sql).dados) {
			Object o = os[0];
			Integer i = IntegerParse.toInt(o);
			list.add(i);
		}
		return list;
	}
	public UResultSet rs(String sql) {
		return this.rs(sql, true);
	}
	public UResultSet rs(String sql, boolean prepare) {
		try {
			Statement statement = createStatement(sql);
			return this.rs(sql, statement, prepare);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	public UResultSet rs(String sql, Statement stmt, boolean prepare) {

		if (prepare) {
			sql = preparaSql(sql);
		}

		UResultSet result = new UResultSet();

		try (ResultSet rs = stmt.executeQuery(sql)) {
			ResultSetMetaData rsmd = rs.getMetaData();
			int columns = rsmd.getColumnCount();

			int pular = -1;

			for (int i = 0; i < columns; i++) {
				String name = rsmd.getColumnLabel(i+1);
				result.titulos.add(name);
			}
			
			while (rs.next()) {
				List<Object> os = new ArrayList<>();
				for (int i = 0; i < columns; i++) {

					if (pular == i) {
						continue;
					}
					
					Object value = rs.getObject(i+1);
					os.add(value);
				}
				result.dados.add(os.toArray());
			}

		} catch (SQLException e) {
			ULog.debug(sql);
			UException.printTrace(e);
			
			if (stopOnError) {
				System.exit(0);
			}
			
			throw UException.runtime(e);
		}

		return result;

	}
	
	public static boolean stopOnError = false;
	
	public Lst<Object[]> selectArray(String sql) {
		return this.rs(sql).dados;
	}
	private String preparaSql(String sql) {
		return SqlNative.preparaSql(sql, this.getDriver());
	}

	public MapSO getMap(String sql, ListString colNames) {
		return getMaps(sql, colNames).getFirst();
	}
	
	public Lst<MapSO> getMaps(String sql, ListString colNames) {
	
		Lst<MapSO> list = new Lst<>();

		sql = preparaSql(sql);
		
		try (Statement stmt = createStatement(sql);

			ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next()) {
				MapSO o = new MapSO();
				for (String a : colNames) {
					o.put(a, rs.getObject(a));
				}
				list.add(o);
			}
			
		} catch (SQLException e) {
			throw UException.runtime(e);
		}

		return list;
		
	}
	
	protected Statement createStatement(String sql) {
		
		try {
			return connection().createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}
		
	}

	protected Statement createStatementExec(String sql) {

		try {
			return connection().createStatement();
		} catch (SQLException e) {
			throw new RuntimeException(e);
		}

	}
	
	public <T> List<T> get(Class<T> classe, String sql) {

		List<T> list = new ArrayList<>();
		
		sql = preparaSql(sql);

		try (Statement stmt = createStatement(sql);

			ResultSet rs = stmt.executeQuery(sql)) {

			Atributos atributos = AtributosBuild.getPersists(classe, false);
			Atributo fieldId = atributos.getId();

//			if (fieldId == null) {
//				throw Utils.exception("fieldId == null");
//			}

			while (rs.next()) {
				T o = UClass.newInstance(classe);

				if (fieldId != null) {
					Object x = rs.getObject( fieldId.getColumnName() );
					fieldId.set(o, x);
				}

				for (Atributo a : atributos) {

					Object value = null;

					try {
						value = rs.getObject( a.getColumnName() );
					} catch (Exception e) {
						continue;
//						throw Utils.exception("Campo nao encontrado na query: " + a.getColumnName());
					}

					value = UType.parse(value, a.getType());

					try {
						a.set(o, value);
					} catch (Exception e) {
						Object v = UClass.newInstance( a.getType() );
						Atributos as = AtributosBuild.get( a.getType() );
						Atributo id = as.getId();
						id.set(v, value);
						a.set(o, v);
					}
				}
				list.add(o);
			}
		} catch (SQLException e) {
			throw UException.runtime(e);
		}

		return list;

	}

	public void close() {
		try {
			con.close();
			ULog.debug("Close " + banco);
		} catch (Exception e2) {}
		con = null;
	}

	public void dropTriggers() {
		List<MapSO> list = selectMap( "select distinct trigger_name tg, event_object_schema||'.'||event_object_table tb from information_schema.triggers" );
		for (MapSO map : list) {
			String tg = map.getObrig("tg");
			String tb = map.getObrig("tb");
			String s = "drop trigger "+tg+" on "+tb;
			this.exec(s);
		}
	}

	public UTable table(String name){
		return this.table("public", name);
	}
	public UTable table(String schema, String name){
		return new UTable(this, schema, name);
	}

	public UTable table(Class<?> classe){
		UTable o = tables.get(classe);
		if (o == null) {
			o = new UTable(this, classe);
			tables.put(classe, o);
		}
		return o;
	}

	public ConexaoJdbc copy() {
		return new ConexaoJdbc(banco, driver, user, pass);
	}
	public ConexaoJdbc us(String user, String pass) {
		this.user = user;
		this.pass = pass;
		return this;
	}
	public String tamanho() {
		return selectString("SELECT pg_size_pretty(pg_database_size(current_database()))");
	}
	public String schemaConstraint(String constraint, String table){

		if (table.contains(".")) {
			return StringBeforeFirst.get(table, ".");
		}

		return "public";
		/*
		String sql =
			" SELECT pg_namespace.nspname as s"
			+ " FROM pg_namespace"
			+ " JOIN pg_class ON pg_namespace.oid=pg_class.relnamespace"
			+ " JOIN pg_constraint ON pg_class.oid=pg_constraint.conrelid"
			+ " WHERE conname = ':constraint'"
			+ "   and pg_class.relname = ':table'"
		;

		sql = sql.replace(":constraint", constraint).replace(":table", table);
		return selectString(sql);
		*/
	}

	private static final String sqlColumnReference = ""
			+ " SELECT pg_get_constraintdef(pg_constraint.oid)"
			+ " FROM pg_namespace"
			+ " JOIN pg_class ON pg_namespace.oid=pg_class.relnamespace"
			+ " JOIN pg_constraint ON pg_class.oid=pg_constraint.conrelid"
			+ " WHERE pg_class.relkind='r'"
			+ " and relname = ':tableOrigem'"
			+ " and ( pg_get_constraintdef(pg_constraint.oid) like 'FOREIGN KEY (%) REFERENCES :tableReferenciada(%'"
		;

	public ListString columnReference(String tableOrigem, String tableReferenciada, String constraint){

		tableReferenciada = tableReferenciada.toLowerCase();
		tableOrigem = tableOrigem.toLowerCase();

		String s = ConexaoJdbc.sqlColumnReference;
		s = s.replace(":tableOrigem", tableOrigem);
		s = s.replace(":tableReferenciada", tableReferenciada);
		if (tableReferenciada.contains(".")) {
			tableReferenciada = StringAfterFirst.get(tableReferenciada, ".");
			s += "  or pg_get_constraintdef(pg_constraint.oid) like 'FOREIGN KEY (%) REFERENCES " + tableReferenciada + "(%' ";
		}
		s += "  or pg_get_constraintdef(pg_constraint.oid) like 'FOREIGN KEY (%) REFERENCES %." + tableReferenciada + "(%' ";
		s += ")";
		s = s.replace(":tableReferenciada", tableReferenciada);

		if (!StringEmpty.is(constraint)) {
			s += " and conname = '" + constraint + "'";
		}

		ListString list = new ListString();

		ListString selectStrings = selectStrings(s);
		for (String string : selectStrings) {
			ULog.debug(string);
			s = StringAfterFirst.get(string, "FOREIGN KEY (");
			s = StringBeforeFirst.get(s, ")");
			list.add(s);
		}
		return list;
	}
	public Connection getCon() {
		return connection();
	}
	public void startTransaction() {
		try {
			con.setAutoCommit(false);
		} catch (SQLException e) {
			throw UException.runtime(e);
		}
	}
	public void commit() {
		try {
			con.commit();
			con.setAutoCommit(true);
		} catch (SQLException e) {
			throw UException.runtime(e);
		}
	}
	public void rollback() {
		try {
			con.rollback();
		} catch (SQLException e) {
			throw UException.runtime(e);
		}
	}
	public Lst<MapSO> selectMap(String sql) {
		if (sql == null) {
			throw new NullPointerException("sql == null");
		}
		return new NativeSelectMap(connection(), driver, sql).map();
	}
	
	public void dropColumn(Class<?> classe, String nome){
		dropColumn(TableSchema.get(classe), nome);
	}
	
	public void dropColumn(String ts, String nome){
		if (this.existsColumn(ts, nome)){
			getDriver().dropColumn(this, ts, nome);
		}
	}

	public boolean exists(Class<?> classe, Integer id){
		return exists(TableSchema.get(classe), "id = " + id);
	}

	public boolean exists(String ts, String where){
		String sql = "select count(*) from " + ts + " where " + where;
		return this.selectInt(sql) > 0;
	}

	public boolean existsColumn(Class<?> classe, String nome) {
		String ts = TableSchema.get(getDefaultSchema(), classe);
		return existsColumn(ts, nome);
	}
	
	public boolean existsColumn(String ts, String nome) {
		
		String schema = StringBeforeFirst.get(ts, ".");
		String table = StringAfterFirst.get(ts, ".");

		String s = "select count(*)";
		s += " from "+DriverJDBC.SCHEMA_VIEWS+".colunas ";
		s += " where lower(esquema) = '"+ schema.toLowerCase() +"' ";
		s += "   and lower(tabela) = '"+ table.toLowerCase() +"' ";
		s += "   and lower(coluna) = '"+ nome.toLowerCase() +"' ";
		int i = this.selectInt(s);
		return i > 0;
		
	}

	public boolean existsView(Class<?> classe, String nome) {
		String s = "select count(*)";
		s += " from "+DriverJDBC.SCHEMA_VIEWS+".views ";
		s += " where lower(nome) = '"+ classe.getSimpleName().toLowerCase() +"' ";
		int i = selectInt(s);
		return i > 0;
	}

	public boolean existsView(String schema, String name) {
		return driver.existsView(this, schema, name);
	}

	public boolean dropView(String schema, String name) {
		if (existsView(schema, name)) {
			exec("drop view " + schema + "." + name);
			return true;
		}
		return false;
	}

//	private QueryExecutor executor;
//
//	public QueryExecutor getExecutor() {
//		if (executor == null) {
//			executor = new JdbcQueryExecutor(this);
//		}
//		return executor;
//	}

	public <T extends ObjetoComId<?>> T save(T o) {
		Class<?> classe = UClass.getClass(o);
		return this.table(classe).save(o);
	}

	public String getNomeColunaId(String ts) {
		String s = driver.getNomeColunaId(this, ts);
		if (s == null) {
			throw UException.runtime("Nao encontrada a columnId " + ts);
		}
		return s;
	}

	@Setter private String owner;

	public String url;

	public String getOwner() {
		if (owner == null) {
			setOwner(user);
		}
		return owner;
	}

	public Data getDataRestore() {
		return selectData(driver.getScriptRestoreDate());
	}

	public int getCurrentSequence(String schema, String table) {
		return driver.getCurrentSequence(this, schema, table);
	}

	public static ConexaoJdbc load_application_properties() {
		return load_application_properties(System.getProperty("user.dir") + "/src/main/resources/application.properties");
	}

	public static ConexaoJdbc load_application_properties(String fileName) {

		MapSO map = new MapSO();
		map.loadObrig(fileName);

		String url = map.getStringObrig("spring.datasource.url");
		String username = map.getStringObrig("spring.datasource.username");
		String password = map.getStringObrig("spring.datasource.password");

		ConexaoJdbc con = ConexaoJdbc.get(url, username, password);
		con.setDefaultSchema(map.getString("spring.jpa.properties.hibernate.default_schema"));
		return con;

	}

	public void dropTable(String schema, String nome) {
		
		if (table(schema, nome).exists()) {
			exec("drop table " + nome);
		}
		
	}
	
	public void dropTables(String schema) {

		criarViewsParaFuncionamento();

		ListString tabelas = selectStrings("select tabela from "+DriverJDBC.SCHEMA_VIEWS+".tabelas where esquema = '"+schema+"'");

		while (!tabelas.isEmpty()) {

			String s = tabelas.remove(0);

			try {
				exec("drop table " + schema + "." + s);
			} catch (Exception e) {
				tabelas.add(s);
			}

		}

	}

	public boolean existsSchema(String s) {
		criarViewsParaFuncionamento();
		return selectInt("select count(*) from "+DriverJDBC.SCHEMA_VIEWS+".esquemas where esquema = '"+s+"'") > 0;
	}

	public void createSchema(String s) {
		if (!existsSchema(s)) {
			driver.createSchema(this, s);
		}
	}

	public Lst<QRow> getRows(String sql) {
		return selectArray(sql).map(i -> new QRow(i));
	}

	public QRow getRow(String sql) {
		return getRows(sql).getFirst();
	}
	
	public Lst<String> getStrings(String sql) {
		return getRows(sql).map(i -> i.getString());
	}

}
