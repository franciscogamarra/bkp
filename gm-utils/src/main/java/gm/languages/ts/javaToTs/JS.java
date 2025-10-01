package gm.languages.ts.javaToTs;

import gm.utils.classes.UClass;
import gm.utils.string.ListString;
import js.Js;
import js.JsObject;
import js.array.Array;
import js.map.Map;
import js.support.JSON;

public class JS {
	
	public static boolean isNotNull_(Object o) {
		return !isNull_(o);
	}

	public static boolean isNull_(Object o) {
		return o == null || o == "";
	}
	
	public static <T> T js_attr(Object o, String key) {
		return null;
	}
	
	@SuppressWarnings("unchecked")
	public static <T> T as(Object o, Class<T> classe) {
		return (T) o;
	}
	
	public static <T> T ppp(T o) {
		return o;
	}
	
	@SafeVarargs
	protected static <T> T join$(T... os) {
		return os[0];
//		{...a, ...b}
	}

	public static <T> T newAs(Class<T> classe) {
		return UClass.newInstance(classe);
	}
	
	public static <T> T jsonParse(String s, Class<T> classe) {
		return JSON.parse(s, classe);
	}
	
	public static <T> T obj() {
		return null;
	}

	public static <T> T orNull(T o) {
		return o;
	}
	
	@SafeVarargs
	public static <T> T or(T a, T... opcoes) {
		
		if (a != null) {
			return a;
		}
		
		for (T o : opcoes) {
			if (o != null) {
				return o;
			}
		}
		
		return null;
		
	}	

	public static Js js(Object o) {
		return (Js) o;
	}
	
	public static Object fromEntries(Object o) {
		Map<?,?> map = (Map<?, ?>) o;
		return JsObject.fromEntries(map);
	}
	
//	para poder substituir de sf(o).bla para o?.bla
	protected static <T> T sf(T o) {
		return o;
	}
	
	public static Array<String> split(String s, String sub) {
		
		ListString list = ListString.byDelimiterSplit(s, sub);
		
		if (s.endsWith(sub) && !sub.contentEquals("")) {
			list.add();
		}
		
		Array<String> array = new Array<>();

		for (String string : list) {
			array.push(string);
		}
		
		return array;
		
	}

	
}