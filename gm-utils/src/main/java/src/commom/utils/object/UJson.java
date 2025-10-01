package src.commom.utils.object;

import src.commom.utils.string.StringEmpty;

public class UJson {

	private static String getKey(String key) {
		return "\""+key+"\": ";
	}

	private static String getNull(String key) {
		return getKey(key) + "null,";
	}

	public static String itemString(String key, String value) {
		
		if (StringEmpty.is(value)) {
			return getNull(key);
		}
		
		return getKey(key) + "\""+value+"\",";
		
	}

	public static String itemInteger(String key, Integer value) {

		if (Null.is(value)) {
			return getNull(key);
		}
		
		return getKey(key) + value+",";

	}

	public static String itemLong(String key, Long value) {

		if (Null.is(value)) {
			return getNull(key);
		}
		
		return getKey(key) + value+",";

	}

	public static String itemBoolean(String key, Boolean value) {

		if (Null.is(value)) {
			return getNull(key);
		}
		
		return getKey(key) + value+",";

	}
	
	public static String itemObj(String key, ObjJs value) {

		if (Null.is(value)) {
			return getNull(key);
		}
		
		return getKey(key) + value.toJSON()+",";

	}

}
