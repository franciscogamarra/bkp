package gm.utils.map;

import java.util.List;

import gm.utils.comum.ToJson;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.string.ListString;
import js.support.console;
import src.commom.utils.string.StringBox;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringRight;

public class MapSoToJson {

	private static String getValue(Object v, List<Object> jaProcessados) {

		if (v == null) {
			return "null";
		}
		if (jaProcessados.contains(v)) {
			return null;
		}

		console.log("ddd");
		jaProcessados.add(v);

		if (v instanceof String) {
			String x = (String) v;
			if (StringEmpty.is(x)) {
				return "null";
			} else {
				return "\""+v+"\"";
			}
		}
		if (UType.isData(v)) {
			Data data = Data.to(v);
			return "\""+data.format("[yyyy]/[mm]/[dd]")+"\"";
		} else if (UType.isArray(v)) {
			Object[] list = (Object[]) v;
			if (list.length == 0) {
				return "[]";
			}
			StringBox s = new StringBox("");
			for (Object obj : list) {
				s.add(", " + MapSoToJson.getValue(obj, jaProcessados));
			}
			return "[" + s.get().substring(2) + "]";
		} else if (UType.isPrimitiva(v)) {
			return StringParse.get(v);
		} else if (UType.isList(v)) {
			List<?> list = (List<?>) v;
			if (list.isEmpty()) {
				return "[]";
			}
			StringBox s = new StringBox("");
			for (Object obj : list) {
				s.add(", " + MapSoToJson.getValue(obj, jaProcessados));
			}
			return "[" + s.get().substring(2) + "]";
		} else if (v instanceof MapSO) {
			MapSO x = (MapSO) v;
			return MapSoToJson.get(x, jaProcessados);
		} else {
			return ToJson.get(v).trimPlus().toString(" ");
		}

	}

	private static String get(MapSO map, List<Object> jaProcessados) {

		StringBox s = new StringBox("{");
		map.forEach((k,v) -> {
			s.add("\""+k+"\": " + MapSoToJson.getValue(v, jaProcessados) + ", ");
		});
		if (!map.isEmpty()) {
			s.set(StringRight.ignore(s.get(), 2));
		}
		return s.get() + "}";

	}

	public static String get(MapSO map) {
		ListString list = map.asJson();
		list.trimPlus();
		String s = list.toString(" ");
		if (StringEmpty.is(s)) {
			return null;
		}

		String original;

		do {
			original = s;
			s = s.replace(" }", "}");
			s = s.replace("{ ", "{");
			s = s.replace(",}", "}");

			s = s.replace(" ]", "]");
			s = s.replace("[ ", "[");
			s = s.replace(",]", "]");

			s = s.replace(": \"false\"", ": false");
			s = s.replace(": \"true\"", ": true");
			s = s.replace("{}", "null");
		} while (!original.contentEquals(s));

		if (s.endsWith(",")) {
			s = StringRight.ignore1(s);
		}
		return s;
	}

	public static void main(String[] args) {
		MapSO map = new MapSO();
		map.put("a", "a");
		MapSoToJson.get(map);
	}

}
