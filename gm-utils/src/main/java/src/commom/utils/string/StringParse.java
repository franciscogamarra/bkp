package src.commom.utils.string;

import gm.languages.ts.javaToTs.JS;
import js.Js;
import js.annotations.Method;
import js.annotations.NoAuto;
import js.annotations.Support;
import js.array.Array;
import js.outros.Date;
import js.support.JSON;
import js.support.console;
import src.commom.utils.array.ArrayClear;
import src.commom.utils.classe.ClassSimpleName;
import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;

@Method @NoAuto
public class StringParse extends JS {

	private StringParse() {}

	public static String get(Object o) {

		long start = Date.now();

		try {

			if (Null.is(o)) {
				return "";
			}

			if (o == "") {
				return "";
			}

			if (ClassSimpleName.isString(o)) {
				return (String) o;
			}

			if (ClassSimpleName.isNumber(o)) {
				return "" + o;
			}

			if (ClassSimpleName.is(o, "Map")) {
				return get(fromEntries(o));
			}
			
			if (Js.inJava) {
				if (!o.getClass().getName().startsWith("src.") && !o.getClass().isAnnotationPresent(Support.class)) {
					return o.toString();
				}
			}

			Array<Object> cache = new Array<>();
			String s = JSON.stringify(o, (key, value) -> {
				if (Equals.is(value, null)) {
					return null;
				}
				if (ClassSimpleName.isObject(value)) {
					//nao usar contains ou qualquer outro
					if (cache.indexOf(value) > -1) {
						return null;
					} else {
						cache.push(value);
					}
				}
				return value;
			});
			
			ArrayClear.exec(cache);

			s = StringReplace.exec(s, "\"{", "{");
			s = StringReplace.exec(s, "}\"", "}");
			s = StringReplace.exec(s, "\"[", "[");
			s = StringReplace.exec(s, "]\"", "]");
			
			return StringReplace.exec(s, "\\\"", "\"");

		} finally {
			long end = Date.now();
			long dif = end - start;
			if (dif > 100) {
				console.log("ToString " + dif, o);
			}
		}
	}
}
