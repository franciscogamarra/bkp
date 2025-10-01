package src.commom.utils.object;

import js.Js;
import js.array.Array;
import js.support.JSON;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmptyPlus;

public class Empty {

	public static boolean is(Object o) {
		
		if (Null.is(o)) {
			return true;
		}
		
		if (Js.typeof(o) == "string") {
			String s = (String) o;
			return StringEmptyPlus.is(s);
		}
		
		if (Array.isArray(o)) {
			Array<?> array = (Array<?>) o;
			return !array.length;
		}
		
		if (Js.typeof(o) == "object") {
			return StringCompare.eq(JSON.stringify(o), "{}");
		}
		
		return false;
		
	}

}
