package gm.utils.jpa;

import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Table;

public class TableSchema {

	private static Map<Class<?>, String> map = new HashMap<>();

	public static String get(Class<?> classe) {
		return get(USchema.getSchemaDefault(), classe);
	}

	public static String get(String defaultSchema, Class<?> classe) {
		String s = map.get(classe);
		if (s != null) {
			return s;
		}
		Table table = classe.getAnnotation(Table.class);
		if (table == null) {

			if (defaultSchema == null) {
				throw new RuntimeException("table == null: " + classe);
			}
			s = defaultSchema + "." + classe.getSimpleName();

		} else {
			s = USchema.get(defaultSchema, table) + ".";
			if (table.name().isEmpty()) {
				s += classe.getSimpleName();
			} else {
				s += table.name();
			}
		}
		map.put(classe, s);
		return s;
	}

	public static String getSchema(Class<?> classe) {
		return USchema.get(classe);
	}

//	public static String tratarSql(String sql) {
//		Set<Class<?>> list = map.keySet();
//		for (Class<?> classe : list) {
//			String s = classe.getSimpleName() + " ";
//			String ts = map.get(classe) + " ";
//			if (sql.contains(" " + s)) {
//				sql = sql.replace("from "+s, "from "+ts);
//				sql = sql.replace("join "+s, "join "+ts);
//				sql = sql.replace("into " + s, "into "+ts);
//				sql = sql.replace("update " + s, "update "+ ts);
//				sql = sql.replace("alter table " + s, "alter table " + ts);
//			}
//		}
//		return sql;
//	}

}
