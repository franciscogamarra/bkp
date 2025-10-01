package src.commom.utils.string;

import gm.utils.comum.SystemPrint;
import src.commom.utils.array.Itens;

public class StringToCamelCaseSepare {

	private StringToCamelCaseSepare() {}

	private static Itens<String> ALL = StringConstants.MAIUSCULAS.concat(StringConstants.MINUSCULAS).concat(StringConstants.NUMEROS);

	public static String exec(String s) {

		if (StringEmpty.is(s)) {
			return s;
		}

		s = StringTrim.plus(s);

		if (s.toUpperCase().contentEquals(s)) {
			s = s.toLowerCase();
		}

		if ("fk".equalsIgnoreCase(s)) {
			return "Fk";
		}

		if (s.toLowerCase().startsWith("fk")) {
			s = s.substring(2);
		}

		if (StringCompare.eqIgnoreCase(s, "de")) {
			return "De";
		}

		s = s.replace("aa", "aA");

		s = s.replace(" S.A.", " SA ");
		s = s.replace(".", " ");

		StringBox box = new StringBox(s);

		StringConstants.MAIUSCULAS.forEach(a -> StringConstants.MAIUSCULAS.forEach(b -> box.replace(a + "." + b, a + b)));

		StringConstants.MAIUSCULAS.forEach(maiuscula -> {
			StringConstants.MINUSCULAS.forEach(minuscula -> {
				box.replace(minuscula + maiuscula, minuscula + " " + maiuscula);

				StringConstants.MINUSCULAS.forEach(minuscula2 -> {
					box.replace(minuscula + "E" + maiuscula + minuscula2, minuscula + "E " + maiuscula + minuscula2);
				});
			});
			StringConstants.NUMEROS.forEach(numero -> {
				box.replace(numero + maiuscula, numero + " " + maiuscula);
				box.replace(maiuscula + numero, maiuscula + " " + numero);
			});
		});

		StringConstants.MINUSCULAS.forEach(minuscula -> {
			StringConstants.NUMEROS.forEach(numero -> {
				box.replace(numero + minuscula, numero + " " + minuscula);
				box.replace(minuscula + numero, minuscula + " " + numero);
			});
		});

		s = "";

		while (!box.isEmpty()) {
			String x = box.removeLeft(1);
			if (StringToCamelCaseSepare.ALL.contains(x)) {
				s += x;
			} else {
				s = s.trim() + " ";
			}
		}
		
		s = StringSepararPalavras.exec(s);
		
		s = ValidadorNomeProprio.instance.formatParcial(s.trim());
		s = StringColocarAcentos.exec(s);
		s = StringPrimeiraMaiuscula.exec(s);

		s = " " + s + " ";
		s = StringReplace.exec(s, " No ", " no ");
		s = StringReplace.exec(s, " Na ", " na ");
		s = StringReplace.exec(s, " Nos ", " nos ");
		s = StringReplace.exec(s, " Nas ", " nas ");

		s = StringReplace.exec(s, " Omesmo ", " o Mesmo ");
		s = StringReplace.exec(s, " Amesma ", " a Mesma ");

		s = s.replace(" Dd ", " DDD ");

		s = s.replace(" Dois ", " 2 ");
		s = s.replace(" Tres ", " 3 ");
		s = s.replace(" Quatro ", " 4 ");
		s = s.replace(" Cinco ", " 5 ");
		s = s.replace(" Seis ", " 6 ");

		for (int i = 0; i < 9; i++) {
			s = s.replace(i + " Porcento ", i + "%");
		}

		return s.trim();

	}

	public static void main(String[] args) {
		SystemPrint.ln(exec("Quantidade Máxima de Apostas no Carrinho de Apostas."));
	}

}
