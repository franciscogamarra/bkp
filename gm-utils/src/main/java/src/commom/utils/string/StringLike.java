package src.commom.utils.string;

import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.SystemPrint;
import src.commom.utils.array.Itens;

public class StringLike {

	public static boolean is(String a, String b) {
		
		if (StringEmpty.is(b)) {
			return true;
		}
		if (StringEmpty.is(a)) {
			return false;
		}
		
		if (StringCompare.eq(a, b)) {
			return true;
		}
		
		if (!StringContains.is(b, "%")) {
			return false;
		}
		
		while (StringContains.is(b, "%%")) {
			b = StringReplace.exec(b, "%%", "%");
		}

		if (StringCompare.eq(b, "%") || StringContains.is(a, b)) {
			return true;
		}
		
		Itens<String> itens = StringSplit.exec(b, "%");

		if (b.startsWith("%")) {
			itens.remove(0);
		} else if (!a.startsWith(itens.get(0))) {
			return false;
		}

		if (b.endsWith("%")) {
			itens.removeLast();
		} else if (!a.endsWith(itens.getLast())) {
			return false;
		}
		
		while (!itens.isEmpty()) {
			
			String s = itens.remove(0);
			
			if (!StringContains.is(a, s)) {
				return false;
			}
			
			a = StringAfterFirst.get(a, s);
			
		}
		
		return true;
		
	}
	
	@IgnorarDaquiPraBaixo

	private StringLike() {}

	public static void main(String[] args) {
		String a = "o rato roeu a roupa do rei";
		String b = "%rato%roupa%";
		SystemPrint.ln(is(a, b));
	}

}
