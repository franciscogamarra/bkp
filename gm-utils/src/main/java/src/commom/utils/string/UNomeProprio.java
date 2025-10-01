package src.commom.utils.string;

import src.commom.utils.array.Itens;
import src.commom.utils.comum.Randomico;

public class UNomeProprio {

	private static final Itens<String> CARACTERES_VALIDOS =
		StringConstants.MAIUSCULAS
		.concat(StringConstants.MINUSCULAS)
		.concat(StringConstants.NUMEROS)
		.add(StringConstants.aspa_simples)
		.add(" ").add(".").add(",").add("(").add(")");
	
	public static String formatParcial(String s) {

		if (StringEmpty.is(s)) {
			return "";
		}

		s = StringReplace.exec(s, "\t", " ");

		boolean espaco = s.endsWith(" ");
		s = StringTrim.plus(s);

		while (!StringEmpty.is(s) && s.startsWith("'")) {
			s = s.substring(1);
			s = StringTrim.plus(s);
		}

		if (StringEmpty.is(s)) {
			return "";
		}

		s = s.toLowerCase();

		s = StringExtraiCaracteres.exec(s, CARACTERES_VALIDOS);

		if (StringEmpty.is(s)) {
			return "";
		}

		StringBox box = new StringBox(s);

		CARACTERES_VALIDOS.forEach(o -> box.replaceWhile(o+o+o, o+o));

		box.replace("''", "'");
		box.set(" " + box + " ");

		StringConstants.MINUSCULAS.forEach(o ->
			box.set(StringReplace.exec(box.get(), " " + o, " " + o.toUpperCase()))
		);

		StringConstants.PREPOSICOES_NOME_PROPRIO.forEach(x -> box.replace(" " + StringPrimeiraMaiuscula.exec(x) + " ", " " + x + " "));
		StringConstants.ARTIGOS.forEach(x -> box.replace(" " + StringPrimeiraMaiuscula.exec(x) + " ", " " + x + " "));
		box.replace(" E ", " e ");
		box.replace(" Ou ", " ou ");
		box.replace(" de a ", " de A ");
		box.replace(" de o ", " de O ");

		s = StringTrim.plus(box.get());

		if (StringLength.get(s) < 2) {
			return s;
		}

		s = s.substring(0,1).toUpperCase() + s.substring(1);

		if (espaco) {
			s += " ";
		}

		return StringLength.max(s, 60);

	}

	public static String getInvalidMessage(String s, int min, int max) {
		
		if (StringEmpty.is(s)) {
			return null;
		}
		
		s = StringTrim.plus(s);
		
		if (!StringCompare.eq(StringTrim.plus(s), s)) {
			return "Nome possui espaços sobrando!";
		}
		
		if (!StringContains.is(s, " ")) {
			return "Deve conter pelo menos um sobrenome!";
		}

		if (s.length() > max) {
			return "Deve conter no máximo "+max+" caracteres!";
		}

		if (s.length() > min) {
			return "Deve conter no máximo "+min+" caracteres!";
		}
		
		if (!StringCompare.eqIgnoreCase(formatParcial(s), s)) {
			return "Possui caractéres inválidos ou 3 letras sequenciais!";
		}
		
		return null;
		
	}
	
	public static boolean isValid(String s, int min, int max) {
		return getInvalidMessage(s, min, max) == null;
	}

	public static String getAleatorio() {
		return formatParcial(Randomico.LOREM_IPSUM.substring(0, Randomico.getInt(3, 100))).trim();
	}

}