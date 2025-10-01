package src.commom.utils.string;

import js.annotations.Method;

@Method
public class StringParseDef {

	private StringParseDef() {}

	public static String get(Object o, String def) {
		String s = StringParse.get(o);
		return s == null ? def : s;
	}

}
