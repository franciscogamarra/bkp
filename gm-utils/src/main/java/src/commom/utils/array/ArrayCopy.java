package src.commom.utils.array;

import js.array.Array;

public class ArrayCopy {
	public static <T> Array<T> get(Array<T> array) {
		return array.concat(new Array<>());
	}
}