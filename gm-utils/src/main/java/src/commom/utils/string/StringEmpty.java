package src.commom.utils.string;

import js.Error;
import js.Js;
import js.support.console;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;

public class StringEmpty {

	private StringEmpty() {}

	public static boolean is(String s) {
		
		if (Null.is(s)) {
			return true;
		}
		
		if (Js.typeof(s) != "string") {
			console.log(s);
			throw new Error("nao eh um string: " + s);
		}
		
		return IntegerCompare.eq(s.trim().length(), 0);
		
	}

	public static boolean notIs(String s) {
		return !is(s);
	}

}
