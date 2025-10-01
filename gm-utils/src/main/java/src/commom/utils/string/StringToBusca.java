package src.commom.utils.string;

import src.commom.utils.array.Itens;

public class StringToBusca {
	
	private static final Itens<String> CHARS = StringConstants.MINUSCULAS_SEM_ACENTOS.concat(StringConstants.NUMEROS);

	public static String exec(String s) {
		
		s = StringTrim.plusNull(s);
		
		if (s == null) {
			return "";
		}
		
		s = StringLength.max(StringRemoveAcentos.exec(s.toLowerCase()), 255);
		
		s = " " + s + " ";
		
		s = StringReplace.whilee(s, "ch", "x");
		s = StringReplace.whilee(s, "sh", "x");
		s = StringReplace.whilee(s, "pha", "fa");
		s = StringReplace.whilee(s, ".", " ");
		s = StringReplace.whilee(s, ",", " ");
		s = StringReplace.whilee(s, "/", " ");
		s = StringReplace.whilee(s, "\\", " ");
		s = StringReplace.whilee(s, "?", " ");
		s = StringReplace.whilee(s, "-", " ");
		s = StringReplace.whilee(s, "_", " ");
		s = StringReplace.whilee(s, " de ", " ");
		s = StringReplace.whilee(s, " da ", " ");
		s = StringReplace.whilee(s, " dos ", " ");
		s = StringReplace.whilee(s, " das ", " ");
		
		s = StringTrim.plusNull(s);

		Itens<String> itens = StringSplit.exec(s, "");
		Itens<String> itensValidos = new Itens<>();
		
		itens.forEach(i -> {
			if (CHARS.contains(i)) {
				if (StringCompare.ne(itensValidos.getLast(), i)) {
					itensValidos.add(i);
				}
			} else {
				if (StringCompare.ne(itensValidos.getLast(), " ")) {
					itensValidos.add(" ");
				}
			}
		});
		
		s = itensValidos.joinString("");
		s = StringTrim.plusNull(s);
		return s;
		
	}

	public static void main(String[] args) {
		System.out.println( exec("1 - Short ( 32.767 )") );
	}
	
	
}
