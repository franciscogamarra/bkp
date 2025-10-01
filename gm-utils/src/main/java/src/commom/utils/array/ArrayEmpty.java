package src.commom.utils.array;

import js.array.Array;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;

public class ArrayEmpty {

	public static boolean is0(Array<?> o) {
		return Null.is(o) || IntegerCompare.eq(o.lengthArray(), 0);
	}

	public static boolean is(Itens<?> o) {
		return Null.is(o) || IntegerCompare.eq(o.size(), 0);
	}
	
}
