package src.commom.utils.comum;

import src.commom.utils.array.Itens;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringExtraiCaracteresBaseadoEmString;
import src.commom.utils.string.StringSplit;

public class SeparaMilhares {
	
	private static final JsMap<String, String> cache = new JsMap<>();

	public static String exec(String key) {
		
		String x = cache.get(key);
		
		if (!Null.is(x)) {
			return x;
		}
		
		String s = StringExtraiCaracteresBaseadoEmString.exec(key, "0123456789.");
		
		if (StringEmpty.is(s)) {
			cache.set(key, s);
			return "";
		}
		
		String fracao;
		
		if (StringContains.is(s, ".")) {
			fracao = "," + StringAfterFirst.get(s, ".");
			s = StringBeforeFirst.get(s, ".");
		} else {
			fracao = "";
		}
		
		Itens<String> split = StringSplit.exec(s, "");
		s = "";
		while (!split.isEmpty()) {
			s = split.removeLast() + s;
			if (!split.isEmpty()) {
				s = split.removeLast() + s;
				if (!split.isEmpty()) {
					s = split.removeLast() + s;
					if (!split.isEmpty()) {
						s = "." + s;
					}
				}
			}
		}
		
		s += fracao;
		cache.set(key, s);
		
		return s;
		
	}

}
