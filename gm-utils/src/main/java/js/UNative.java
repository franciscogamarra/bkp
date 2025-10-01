package js;

import java.util.List;
import java.util.Map;

import gm.utils.classes.UClass;
import gm.utils.comum.UType;
import gm.utils.lambda.F0;
import gm.utils.lambda.P1;
import gm.utils.map.MapSO;
import gm.utils.map.MapSoFromObject;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.string.ListString;
import js.array.Array;
import js.promise.Promise;
import src.commom.utils.array.Itens;
import src.commom.utils.string.StringBeforeFirst;

public class UNative {

	public static <T> T asyncPromise(Promise<?> promise, P1<T> then) {
		return null;
	}

	public static ListString getList() {
		ListString list = new ListString();
		list.add("const UNative = {};");
		list.add("UNative.asyncPromise = async (promise, then) => {");
		list.add("	const o = await promise;");
		list.add("	then(o);");
		list.add("}");
		list.add("UNative.getAtributo = (o, key) => o[key];");
		list.add("UNative.setAtributo = (o, key, value) => o[key] = value;");
		list.add("UNative.inJava = false;");
		list.add("UNative.asArray = o => o;");
		list.add("export default UNative;");
		return list;
	}

	public static void setAtributo(Object o, String key, Object value) {
		AtributosBuild.get(o).getObrig(key).set(o, value);
	}

	@SuppressWarnings("unchecked")
	public static <T> T getAtributo(Object o, String key) {

		if (UType.isPrimitiva(o)) {
			return null;
		}

		if (o instanceof Map) {
			Map<String, ?> map = (Map<String, ?>) o;
			return (T) map.get(key);
		}

		Atributo a = AtributosBuild.get(o).get(key);

		if (a != null) {
			return a.get(o);
		}

		Metodo metodo = ListMetodos.get(UClass.getClass(o)).get(key);
		if (metodo != null) {
			F0<?> func = () -> metodo.invoke(o);
			return (T) func;
		}

		return null;
	}

	public static <T> T getProp(Object o, String key) {
		return getAtributo(o, key);
	}

	@SuppressWarnings("unchecked")
	public static <T> T convert(Object o, Class<T> classe) {

		if (o == null) {
			return null;
		}

		if (UClass.isInstanceOf(o, classe)) {
			return (T) o;
		}

		if (o instanceof MapSO) {
			MapSO map = (MapSO) o;
			tratarMapSO(map);
			return map.as(classe);
		}

		UType.tryCast = (obj, clazz) -> UNative.tryCast(obj, clazz);

		MapSO mapSO;

		if (o instanceof Map) {
			mapSO = mapToMapSO(o);
		} else {
			mapSO = MapSoFromObject.get(o);
		}

		return mapSO.as(classe);

	}

	private static void tratarMapSO(MapSO map) {
		ListString keys = map.getKeys();
		for (String key : keys) {
			Object o = map.get(key);
			if (o == null) {
				continue;
			}
			if (o instanceof Map && !(o instanceof MapSO)) {
				MapSO mapSo = mapToMapSO(o);
				map.put(key, mapSo);
				continue;
			}
			if (o instanceof List) {

				List<?> list = (List<?>) o;

				Array<?> array = new Array<>();
				for (Object x : list) {
					array.java_addCast(x);
				}
				map.put(key, array);

			}
		}
	}

	private static MapSO mapToMapSO(Object o) {
		@SuppressWarnings("unchecked")
		Map<String, Object> mp = (Map<String, Object>) o;
		MapSO map = new MapSO(mp);
		tratarMapSO(map);
		return map;
	}

	public static Array<?> asArray2(Object... array) {
		return new Array<>(array);
	}

	public static <T> Array<T> asArray(Itens<T> array) {
		return array.getArray();
	}

	public static <T> Array<T> asArray1(Object body, Class<T> classe) {

		if (body instanceof Array) {
			Array<?> list = (Array<?>) body;
			Array<T> map = list.map(o -> convert(o, classe));
			map.classe = classe;
			return map;
		}

		if (body.toString().startsWith("[L")) {
			String s = body.toString().substring(2);
			String nomeClasse = StringBeforeFirst.get(s, ";");
			Class<?> cs = UClass.getClassObrig(nomeClasse);
			if (cs != classe) {
				throw new RuntimeException();
			}
			Array<T> array = newArray(classe);
			Object[] os = (Object[]) body;
			for (Object o : os) {
				array.java_addCast(o);
			}
			return array;
		}

		List<?> list;

		if (body instanceof List) {
			list = (List<?>) body;
		} if (!(body instanceof MapSO)) {
			throw new RuntimeException();
		}
		MapSO map = (MapSO) body;
		list = map.get("array");

		Array<T> array = new Array<>();
		array.classe = classe;

		if (list.isEmpty()) {
			return array;
		}

		for (Object o : list) {
			T t = convert(o, classe);
			array.push(t);
		}

		return array;

	}

	public static <T> Array<T> asArray(List<MapSO> list, Class<T> classe) {
		Array<T> array = new Array<>();
		for (MapSO item : list) {
			array.push(item.as(classe));
		}
		return array;
	}

	private static Object tryCast(Object o, Class<?> classe) {
		if (o instanceof List && classe == Array.class) {
			List<?> list = (List<?>) o;
			if (list.isEmpty()) {
				return new Array<>();
			}
			return asArray2(o, UClass.getClass(list.get(0)));
		}
		return null;
	}

	public static <T> Array<T> newArray(Class<T> classe) {
		Array<T> array = new Array<>();
		array.classe = classe;
		return array;
	}

}
