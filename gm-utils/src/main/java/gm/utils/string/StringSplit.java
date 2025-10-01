package gm.utils.string;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.Lst;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;

public class StringSplit {
	
	public static Lst<String> exec(String s, String sub) {
		
		Lst<String> itens = new Lst<>();
		
		if (Null.is(s)) {
			return itens;
		}
		
		if (Null.is(sub)) {
			sub = "";
		}
		
		if (StringCompare.eq(sub, "")) {
			while (!s.isEmpty()) {
				itens.add(s.substring(0, 1));
				s = s.substring(1);
			}
			return itens;
		}
		
		while (s.startsWith(sub)) {
			itens.add("");
			s = s.substring(sub.length());
		}
		
		while (s.contains(sub)) {
			itens.add(StringBeforeFirst.get(s, sub));
			s = StringAfterFirst.get(s, sub);
		}
		
		itens.add(s);
		
		return itens;
		
	}
	
	@IgnorarDaquiPraBaixo
	
	private StringSplit() {}
	
}
