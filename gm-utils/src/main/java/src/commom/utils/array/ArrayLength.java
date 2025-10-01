package src.commom.utils.array;

import js.annotations.Method;
import js.array.Array;
import src.commom.utils.object.Null;

@Method
public class ArrayLength {

	public static int get(Itens<?> a) {
		if (Null.is(a)) {
			return 0;
		}
		return a.size();
	}

	public static int get0(Array<?> a) {
		if (Null.is(a)) {
			return 0;
		}
		return a.lengthArray();
	}

}
