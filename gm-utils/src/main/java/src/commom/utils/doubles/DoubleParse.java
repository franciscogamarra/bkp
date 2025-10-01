package src.commom.utils.doubles;

import js.Js;

public class DoubleParse {

	public static Double toDouble(Object o) {
		if (DoubleIs.is(o)) {
			return Js.parseFloat(o);
		}
		throw new Error("NaN");
	}

	public static Double toDoubleDef(Object o, double def) {
		if (DoubleIs.is(o)) {
			return Js.parseFloat(o);
		}
		return def;
	}

}
