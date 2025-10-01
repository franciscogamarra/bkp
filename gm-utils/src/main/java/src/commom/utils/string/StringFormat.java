package src.commom.utils.string;

public class StringFormat {

	public static String leftPad(String s, String substring, int len) {
		
		while (StringLength.get(s) < len) {
			s = substring + s;
		}
		
		return s;
		
	}

	public static String rightPad(String s, String substring, int len) {
		
		while (StringLength.get(s) < len) {
			s += substring;
		}
		
		return s;
		
	}

}