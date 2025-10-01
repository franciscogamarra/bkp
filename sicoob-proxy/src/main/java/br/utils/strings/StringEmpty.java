package br.utils.strings;

public class StringEmpty {
	
	public static boolean is(String s) {
		
		if (s == null) {
			return true;
		}
		
		s = s.trim();
		
		if (s.isEmpty()) {
			return true;
		}
		
		/* pode vir com valor undefined do js */
		if (s.contentEquals("undefined")) {
			return true;
		}
		
		/* pode vir com valor null de uma chamada entre serviços ou do js */
		if (s.contentEquals("null")) {
			return true;
		}
		
		/* como este é o valor padrao que vem no swagger então eu ignoro */
		if (s.contentEquals("string")) {
			return true;
		}
		
		return false;
		
	}
	
}