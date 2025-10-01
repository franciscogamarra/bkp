package src.commom.utils.object;

import js.outros.ObjJson;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;

public abstract class ObjJs extends ObjJson {

	@Override
	public String toString() {
		return toJSON();
	}

	public final String toJSON() {

		String s = toJsonImpl();

		if (StringEmpty.is(s)) {
			return "";
		}

		s = replace(s, " ,", ",");
		s = replace(s, ",,", ",");
		s = replace(s, " }", "}");
		s = replace(s, ",}", "}");
		s = replace(s, " ]", "]");
		return replace(s, ",]", "]");

	}

	//nao utilizar StringReplace pois dá referencia circular
	private static String replace(String s, String a, String b) {

		if (StringEmpty.is(s)) {
			return "";
		}
		
		while (StringContains.is(s, a)) {
			s = s.replace(a, b);
		}
		
		return s;
		
	}

	public static String itemString(String key, String valueP) {
		return UJson.itemString(key, valueP);
	}

	public static String itemInteger(String key, Integer valueP) {
		return UJson.itemInteger(key, valueP);
	}

	public static String itemBoolean(String key, Boolean valueP) {
		return UJson.itemBoolean(key, valueP);
	}

	public static String itemObj(String key, ObjJs valueP) {
		return UJson.itemObj(key, valueP);
	}
	
	protected abstract String toJsonImpl();

}
