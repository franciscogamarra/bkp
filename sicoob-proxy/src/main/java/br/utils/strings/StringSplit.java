package br.utils.strings;

import br.utils.Lst;

public class StringSplit {
	
	public static Lst<String> exec(String s, String sub) {
		
		Lst<String> itens = new Lst<>();
		
		if (s == null) {
			return itens;
		}
		
		if (sub == null) {
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
	
	private StringSplit() {
	}
	
}
