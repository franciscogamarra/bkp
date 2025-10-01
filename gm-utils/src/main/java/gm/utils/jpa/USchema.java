package gm.utils.jpa;

import java.util.HashMap;
import java.util.Map;

import gm.utils.exception.UException;
import jakarta.persistence.Table;
import src.commom.utils.string.StringEmpty;

public class USchema {

	private final ConexaoJdbc con;
	private final DriverJDBC driver;
	private static Map<Class<?>, String> map = new HashMap<>();
	
	public static String schemaDefault = null;

	public USchema(ConexaoJdbc con) {
		this.con = con;
		driver = con.getDriver();
	}

	public void create(String nome) {
		if (!exists(nome)) {
			con.exec("create schema " + nome);
//			con.criarViewsParaFuncionamento();
		}
	}

	public boolean exists(String nome) {
		return getId(nome) != null;
	}

	public void create(Table table) {
		this.create(USchema.get(table));
	}

	public static String get(Class<?> classe) {
		String s = map.get(classe);
		if (s != null) {
			return s;
		}

		Table table = classe.getAnnotation(Table.class);
		if (table == null) {
			throw UException.runtime("A classe "+ classe.getSimpleName() + " está sem a anotacao @Table");
		}
		s = get(table);
		map.put(classe, s);
		return s;
	}

	public static String getSchemaDefault() {
		if (schemaDefault == null) {
			schemaDefault = System.getProperty("spring.jpa.properties.hibernate.default_schema");
		}
		return schemaDefault;
	}
	
	public static void setSchemaDefault(String s) {
		schemaDefault = s;
		System.setProperty("spring.jpa.properties.hibernate.default_schema", s);
	}

	public static String get(Table table) {
		return get(getSchemaDefault(), table);
	}

	public static String get(String defaultSchema, Table table) {
		String s = table.schema();
		if (StringEmpty.is(s)) {
			if (defaultSchema == null) {
				throw UException.runtime("table schema is empty");
			}
			return defaultSchema;
		}
		return s;
	}

	public Integer getId(String schema) {
		if (driver == DriverJDBC.PostgreSQL) {
			return con.selectInt("select 1 from pg_catalog.pg_namespace where lower(nspname) = '" + schema.toLowerCase() + "'");
		}
		if (driver == DriverJDBC.MSSQLServer) {
			return con.selectInt("select schema_id from sys.schemas where lower(name) = '" + schema.toLowerCase() + "'");
		}
		throw UException.runtime("driver nao configurado: " + driver);
	}

}
