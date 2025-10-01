package src.commom.utils.classe;

import gm.languages.ts.javaToTs.JS;
import js.Js;
import src.commom.utils.string.StringCompare;

public class ClassSimpleName extends JS {

	public static String exec(Object o) {

		String s = Js.typeof(o);

		if (StringCompare.eqIgnoreCase(s, "string")) {
			return "string";
		}
		if (StringCompare.eq(s, "number")) {
			return "number";
		}

		if (Js.inJava) {
			if (o.getClass().equals(Double.class) || o.getClass().equals(Integer.class)) {
				return "number";
			}
			return o.getClass().getSimpleName();
		}

		return js(o).__proto__.constructor.name;

	}

	public static boolean is(Object o, String s) {
		return StringCompare.eq(exec(o), s);
	}

	public static boolean isObject(Object o) {
		return StringCompare.eq(exec(o), "Object");
	}

	public static boolean isString(Object o) {
		return StringCompare.eq(exec(o), "string");
	}

	public static boolean isNumber(Object o) {
		return StringCompare.eq(exec(o), "number");
	}

}
