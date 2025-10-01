package gm.utils.string;

import src.commom.utils.string.StringReplace;

public class Teste {

	public static void main(String[] args) {
		
		String s = null;
		
		s = StringReplace.exec(s, "\"{", "{");
		s = StringReplace.exec(s, "}\"", "}");
		
		s = StringReplace.exec(s, "\"[", "[");
		s = StringReplace.exec(s, "]\"", "]");
		
	}
	
}
