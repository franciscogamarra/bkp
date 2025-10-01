package src.commom.utils.shortt;

import src.commom.utils.object.Null;

public class ShortFormat {

	public static String zerosEsquerda(Short value, int casas) {
		if (Null.is(value)) {
			return "";
		}
		String s = ""+value;
		while (s.length() < casas) {
			s = "0" + s;
		}
		return s;
	}

	public static String xx(Short value) {
		return zerosEsquerda(value, 2);
	}

	public static String xxx(Short value) {
		return zerosEsquerda(value, 3);
	}
	
	public static String xxxx(Short value) {
		return zerosEsquerda(value, 4);
	}

}
