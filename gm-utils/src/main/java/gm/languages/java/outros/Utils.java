package gm.languages.java.outros;

import gm.utils.javaCreate.JcTipo;

public class Utils {

	public static String getSimpleName(JcTipo type) {
		
		if (type == null) {
			throw new NullPointerException();
		}
		
		return type.getSimpleName();
	}
	
}