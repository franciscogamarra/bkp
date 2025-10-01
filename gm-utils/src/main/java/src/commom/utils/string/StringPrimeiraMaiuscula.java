package src.commom.utils.string;

public class StringPrimeiraMaiuscula {

	private StringPrimeiraMaiuscula() {}

	public static String exec(String s) {
		s = StringTrim.plus(s);
		if (StringEmpty.is(s)) {
			return s;
		}
		return s.substring(0, 1).toUpperCase() + s.substring(1);
	}
	
	public static boolean is(String s) {

		s = StringTrim.plus(s);
		if (StringEmpty.is(s)) {
			return false;
		}
		
		s = s.substring(0, 1);
		
		return StringCompare.eq(s, s.toUpperCase());
		
	}

}
