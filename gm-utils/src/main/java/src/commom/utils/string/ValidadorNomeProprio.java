package src.commom.utils.string;

import src.commom.utils.array.Itens;

public class ValidadorNomeProprio {
	
	public static final ValidadorNomeProprio instance = new ValidadorNomeProprio(StringConstants.MAIUSCULAS
			.concat(StringConstants.MINUSCULAS)
			.concat(StringConstants.NUMEROS)
			.add(StringConstants.aspa_simples)
			.add(" ").add(".").add(",").add("(").add(")"), 2, 100, 3);
	
	private Itens<String> caracteresValidos;
	private int minLength;
	private int maxLength;
	private int sequenciaDeCaracteresIguaisAceita;
	
	public ValidadorNomeProprio(Itens<String> caracteresValidos, int minLength, int maxLength, int sequenciaDeCaracteresIguaisAceita) {
		this.caracteresValidos = caracteresValidos;
		this.minLength = minLength;
		this.maxLength = maxLength;
		this.sequenciaDeCaracteresIguaisAceita = sequenciaDeCaracteresIguaisAceita;
		
	}
	
	public String formatParcial(String s) {

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

		s = StringExtraiCaracteres.exec(s, caracteresValidos);

		if (StringEmpty.is(s)) {
			return "";
		}

		StringBox box = new StringBox(s);

		caracteresValidos.forEach(o -> {
			String sequenciaValida = StringRepete.exec(o, sequenciaDeCaracteresIguaisAceita);
			String sequenciaUtrapassando = StringRepete.exec(o, sequenciaDeCaracteresIguaisAceita + 1);
			box.replaceWhile(sequenciaUtrapassando, sequenciaValida);
		});

		box.replace("''", "'");
		box.set(" " + box + " ");
		box.replaceWhile("( ", "(");
		box.replaceWhile(" )", ")");

		StringConstants.MINUSCULAS.forEach(o -> {
			box.set(StringReplace.exec(box.get(), " " + o, " " + o.toUpperCase()));
			box.set(StringReplace.exec(box.get(), "(" + o, "(" + o.toUpperCase()));
		});

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

		return StringLength.max(s, maxLength);

	}

	public String getInvalidMessage(String s) {
		
		if (StringEmpty.is(s)) {
			return null;
		}
		
		if (!StringCompare.eq(StringTrim.plus(s), s)) {
			return "Nome possui espaços sobrando!";
		}
		
		if (s.length() < minLength) {
			return "Deve conter no mínimo "+minLength+" caracteres!";
		}

		if (s.length() > maxLength) {
			return "Deve conter no máximo "+maxLength+" caracteres!";
		}

		if (!StringCompare.eqIgnoreCase(formatParcial(s), s)) {
			return "Possui caractéres inválidos ou 3 letras sequenciais!";
		}
		
		return null;
		
	}
	
	public boolean isValid(String s) {
		return getInvalidMessage(s) == null;
	}

}