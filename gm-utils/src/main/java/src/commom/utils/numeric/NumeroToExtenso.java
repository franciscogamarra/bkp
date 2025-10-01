package src.commom.utils.numeric;

import gm.utils.comum.SystemPrint;
import gm.utils.string.ListString;
import js.annotations.NaoConverter;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringRight;

@NaoConverter /* TODO remover ListString */
public class NumeroToExtenso {

	public static void main(String[] args) {
//		SystemPrint.ln(get(10));
//		SystemPrint.ln(get(11));
//		SystemPrint.ln(get(111));
//		SystemPrint.ln(get(100));
		SystemPrint.ln(get(1018));
//		SystemPrint.ln(get(10000));
//		SystemPrint.ln(get(100000));
		SystemPrint.ln(get(123456789));
		SystemPrint.ln(get(10000000));
		SystemPrint.ln(get(100000000));
		SystemPrint.ln(get(1000000000));
		SystemPrint.ln(get(1010000000));
	}

	public static String get(int value) {
		return new NumeroToExtenso(value).result;
	}

	private String tripa;
	private int grupo = -1;
	private String result;

	private NumeroToExtenso(Integer valor) {

		if (valor == null) {
			result = "";
		} else if (valor.intValue() == 0) {
			result = "zero";
		} else {

			String s = valor.toString();

			ListString itens = new ListString();

			while (!s.isEmpty()) {
				tripa = StringRight.get(s, 3);
				s = StringRight.ignore(s, 3);
				itens.add(0, next());
			}

			itens.trimPlus();

			s = itens.toString(" ").trim() + " ";

			s = s.replace(" dez e um ", " onze ");
			s = s.replace(" dez e dois ", " doze ");
			s = s.replace(" dez e três ", " treze ");
			s = s.replace(" dez e quatro ", " quatorze ");
			s = s.replace(" dez e cinco ", " quinze ");
			s = s.replace(" dez e oito ", " dezoito ");
			s = s.replace(" dez e ", " deze");

			s = s.replace(" um milhões ", " um milhão ");
			s = s.replace(" um bilhões ", " um bilhão ");
			s = s.replace(" um trilhões ", " um trilhão ");
			s = s.replace(" um quadrilhões ", " um quadrilhão ");

			s = s.substring(2);

			if (s.startsWith("um mil ")) {
				s = s.substring(3);
			}

			result = s.trim();

		}

	}

	private static final ListString GRUPOS = ListString.array(
		""," mil"," milhões"," bilhões"," trilhões"," quadrilhões"
	);

	private static final ListString CENTENAS = ListString.array(
		"cem","duzentos","trezentos","quatrocentos","quinhentos","seiscentos","setecentos","oitocentos","novecentos"
	);

	private static final ListString DEZENAS = ListString.array(
		"dez","vinte","trinta","quarenta","cinquenta","sessenta","setenta","oitenta","novanta"
	);

	private static final ListString UNIDADES = ListString.array(
		"um","dois","três","quatro","cinco","seis","sete","oito","nove"
	);

	private String get(ListString list) {

		int i = IntegerParse.toInt(tripa.substring(0, 1));
		tripa = tripa.substring(1);

		if (i == 0) {
			return "";
		}

		return " e " + list.get(i-1);

	}

	private String next() {

		grupo++;

		StringBuilder builder = new StringBuilder();

		if (tripa.length() == 3) {
			builder.append(get(CENTENAS));
		}

		if (tripa.length() == 2) {
			builder.append(get(DEZENAS));
		}

		builder.append(get(UNIDADES));

		String s = builder.toString().trim();

		if (s.isEmpty()) {
			return "";
		}

		s += GRUPOS.get(grupo);

		return s;

	}


}