package js;

import gm.utils.exception.NaoImplementadoException;
import gm.utils.reflection.AtributosBuild;
import js.array.Array;
import js.map.Map;

public class JsObject {
	
	public static Object fromEntries(Map<?, ?> map) {
		return map;
	}

	public static Array<String> keys(Object o) {
		Array<String> list = new Array<>();
		AtributosBuild.get(o).forEach(i -> list.push(i.nome()));
		return list;
	}
	
	public static void assign(Object a, Object b) {
		throw new NaoImplementadoException();
	}
	
}
