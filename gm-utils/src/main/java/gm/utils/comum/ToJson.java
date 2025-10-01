package gm.utils.comum;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import gm.utils.anotacoes.IgnoreJson;
import gm.utils.classes.UClass;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.rest.GetMapStringString;
import gm.utils.string.ListString;
import js.support.console;
import lombok.Setter;
import src.commom.utils.string.StringParse;

@Setter
public class ToJson {

	private Object o;
	private boolean nulos = false;
	private boolean first = true;
	private List<Object> os;

	public static ListString get(Object o) {
		return new ToJson(new ArrayList<>(), o).get();
	}

	private ToJson(List<Object> os, Object o) {
		this.os = os;
		this.o = o;
	}

	private static String getPrimitivaValue(Object o) {
		if (o instanceof String) {
			return "\"" + StringParse.get(o) + "\"";
		}
		return StringParse.get(o);
	}

//	private static int count = 0;

	public ListString get() {

		if (UType.isPrimitiva(o)) {
			String s = ToJson.getPrimitivaValue(o);
			ListString list = new ListString();
			list.add(s);
			return list;
		}

//		if (o instanceof Map) {
//			Map<?, ?> map = (Map<?, ?>) o;
//
//		}

		if (os.contains(o)) {
			ListString list = new ListString();
			list.add("[]");
			return list;
		}
		os.add(o);

		Class<?> classe = UClass.getClass(o);
		ListString list = new ListString();
//		list.setPrintOnAdd(true);
		list.add("{");
		first = true;
		Atributos as = AtributosBuild.get(classe).filter(a -> !a.hasAnnotation(IgnoreJson.class) && !a.isStatic());
		for (Atributo a : as) {
			
			if (a.isFunction()) {
				continue;
			}

			Object value = a.get(o);
			String v;
			if (value == null) {
				if (!nulos) {
					continue;
				}
				v = "null";
			} else if (a.isPrimitivo()) {
				v = ToJson.getPrimitivaValue(value);
			} else if (value.getClass().getName().contains("com.google.gson.internal.LinkedTreeMap") || value.getClass().getName().contains(".gson.")) {
				continue;
			} else if (UType.isMap(value) || o instanceof GetMapStringString) {
				Map<String, Object> map = asMap(value);

				for (String key : map.keySet()) {
					console.log("ToJson -> " + key);
				}
				continue;

			} else if (UType.isList(value)) {

				List<?> lst = (List<?>) value;
				if (!lst.isEmpty()) {
					String s = getVirgula();
					s += "\"" + a.nome() + "\" : [";

					Object object = lst.get(0);

					if (UType.isPrimitiva(object)) {
						String xx = "";
						for (Object x : lst) {
							xx += ", " + ToJson.getPrimitivaValue(x);
						}
						xx = xx.substring(2);
						s += xx + "]";
						list.add(s);
					} else {

						list.add(s);
						list.getMargem().inc();
						for (Object x : lst) {
							new ToJson(os, x).get().forEach(i -> list.add(i));
							if (!UList.getLast(lst).equals(x)) {
								s = list.removeLast().substring(1);
								s += ",";
								list.add(s);
							}
						}
						list.getMargem().dec();
						list.add("  ]");

					}

					continue;
				}
				v = "[]";
			} else {
				ListString lst = new ToJson(os, value).get();
				if (lst.size(1)) {
					v = "{}";
				} else {
					lst.remove(0);
					lst.removeLast();
					lst.trim();
					lst.addLeft("\t");
					lst.add(0, "{");
					lst.add("   }");
					v = lst.toString("\n");
				}
			}

			String s = getVirgula();

			s += "\"" + a.nome() + "\" : " + v;
			list.add(s);
		}

		if (list.size() == 1) {
			list.clear();
			list.add("{}");
		} else {
			list.add("}");
		}
		return list;
	}

	private Map<String, Object> asMap(Object o) {
		Map<String, Object> map = new HashMap<>();
		if (o instanceof GetMapStringString) {
			GetMapStringString get = (GetMapStringString) o;
			Map<String, String> mp = get.getMapStringString();
			mp.keySet().forEach(key -> map.put(key, mp.get(key)));
		} else {
			Map<?, ?> mp = (Map<?, ?>) o;
			mp.keySet().forEach(key -> map.put(StringParse.get(key), mp.get(key)));
		}
		return map;
	}

	private String getVirgula() {
		if (first) {
			first = false;
			return "  ";
		}
		return ", ";
	}

}
