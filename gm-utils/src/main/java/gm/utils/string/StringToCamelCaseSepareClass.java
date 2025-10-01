package gm.utils.string;

import src.commom.utils.string.StringToCamelCaseSepare;

public class StringToCamelCaseSepareClass {

	public static String exec(Class<?> classe) {
		return StringToCamelCaseSepare.exec(classe.getSimpleName());
	}

}
