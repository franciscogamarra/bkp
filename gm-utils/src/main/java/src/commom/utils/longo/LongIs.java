package src.commom.utils.longo;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRight;

public class LongIs {

	public static boolean is(String s) {
		
		if (StringEmpty.is(s)) {
			return false;
		}
		
		if (s.endsWith("L") || s.endsWith("l")) {
			s = StringRight.ignore1(s);
		}
		
		try {
			Long.parseLong(s);
			return true;
		} catch (Exception e) {
			return false;
		}
		
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		System.out.println(is("a"));
		System.out.println(is("1L"));
	}
	
}
