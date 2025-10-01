package src.commom.utils.integer;

import src.commom.utils.object.Null;

public class IntegerFormat {

	public static String zerosEsquerda(Integer value, int casas) {
		if (Null.is(value)) {
			return "";
		}
		String s = ""+value;
		while (s.length() < casas) {
			s = "0" + s;
		}
		return s;
	}

	public static String xx(Integer value) {
		return zerosEsquerda(value, 2);
	}

	public static String xxx(Integer value) {
		return zerosEsquerda(value, 3);
	}
	
	public static String xxxx(Integer value) {
		return zerosEsquerda(value, 4);
	}

}
