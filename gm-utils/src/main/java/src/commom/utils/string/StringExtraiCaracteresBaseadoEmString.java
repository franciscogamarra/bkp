package src.commom.utils.string;

public class StringExtraiCaracteresBaseadoEmString {

	private StringExtraiCaracteresBaseadoEmString() {}
	
	public static String exec(String s, String chars) {
		
		String x = "";
		while (!StringEmpty.is(s)) {
			
			String ss = s.substring(0,1);
			
			if (StringContains.is(chars, ss)) {
				x += ss;
			}
			
			s = s.substring(1);
			
		}
		
		return x;
		
	}

	public static String nullIfEmpty(String s, String chars) {
		s = exec(s, chars);
		if (StringEmpty.is(s)) {
			return null;
		}
		return s;
	}
	
}