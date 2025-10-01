package src.commom.utils.object;

import gm.languages.ts.javaToTs.JS;

public class Obj {
	public static <T> T get(Object o, String key) {
		return JS.js_attr(o, key);
	}
}
