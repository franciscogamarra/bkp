package src.commom.utils.array;

import js.array.Array;

public class ArrayClear {
	public static void exec(Array<?> o) {
		while (!ArrayEmpty.is0(o)) {
			o.pop();
		}
	}
}