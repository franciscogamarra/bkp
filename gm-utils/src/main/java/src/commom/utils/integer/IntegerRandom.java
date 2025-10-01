package src.commom.utils.integer;

import js.Js;

public class IntegerRandom {

	public static int get(int max) {
		return Js.Math.floor(Js.Math.random() * max);
	}

}