package gm.utils.string;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import gm.utils.comum.UConstantes;
import gm.utils.number.Numeric;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringCamelCase;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringEquivalente;
import src.commom.utils.string.StringEscopo;
import src.commom.utils.string.StringExtraiNumeros;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.string.StringRepete;
import src.commom.utils.string.StringReplace;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringToCamelCaseSepare;
import src.commom.utils.string.StringTrim;
import src.commom.utils.string.ValidadorNomeProprio;

public class UString {

	@Deprecated
	public static boolean notEmpty(String s) {
		return StringEmpty.notIs(s);
	}

	@Deprecated
	public static boolean isEmpty(String s) {
		return StringEmpty.is(s);
	}

	public static String toString(Object o, String def) {
		String s = toString(o);
		return s == null ? def : s;
	}

	public static String toString(Object o) {
		if (o == null) {
			return null;
		}
		if (o instanceof BigDecimal) {
			BigDecimal b = (BigDecimal) o;
			return Numeric.toNumeric(b, b.precision()).toStringPonto();
		}
		String s = o.toString();
		if (StringEmpty.is(s)) {
			return null;
		}
		return s;
	}

	@Deprecated
	public static String afterLast(String s, String substring) {
		return StringAfterLast.get(s, substring);
	}

	@Deprecated
	public static String beforeLast(String s, String substring) {
		return StringBeforeLast.get(s, substring);
	}

	public static String beforeLastMaiuscula(String s) {
		s = UString.reverse(s);
		while (!s.isEmpty()) {
			String x = s.substring(0, 1);
			s = s.substring(1);
			if (StringEmpty.is(s)) {
				return null;
			}
			if (UConstantes.letrasMaiusculas.contains(x)) {
				return UString.reverse(s);
			}
		}
		return null;
	}

	@Deprecated
	public static String beforeFirst(String s, String substring) {
		return StringBeforeFirst.get(s, substring);
	}

	@Deprecated
	public static String afterFirstObrig(String s, String substring) {
		return StringAfterFirst.get(s, substring);
	}

	@Deprecated
	public static String afterFirst(String s, String substring) {
		return StringAfterFirst.get(s, substring);
	}

	@Deprecated
	public static boolean equals(String a, String b) {
		return StringCompare.eq(a, b);
	}

	@Deprecated
	public static boolean ne(String a, String b) {
		return !StringCompare.eq(a, b);
	}

	@Deprecated
	public static boolean equivalente(String a, String b) {
		return StringEquivalente.is(a, b);
	}

	@Deprecated
	public static String removerAcentos(String s) {
		return StringNormalizer.get(s);
	}

	@Deprecated
	public static String tratarParaSave(String s) {
		return StringTrim.trim(s);
	}

	@Deprecated
	public static String trimPlus(String s) {
		return StringTrim.plus(s);
	}

	@Deprecated
	public static String replaceWhile(String s, String a, String b) {
		return StringReplace.exec(s, a, b);
	}

	@Deprecated
	public static void clipboard(String s) {
		StringClipboard.set(s);
	}

	@Deprecated
	public static String clipboard() {
		return StringClipboard.get();
	}

	@Deprecated
	public static int compare(String a, String b) {
		return StringCompare.compare(a, b);
	}

	public static boolean containsIgnoreCase(String s, String substring) {
		if (StringEmpty.is(s) || StringEmpty.is(substring)) {
			return false;
		}
		return s.toLowerCase().contains(substring.toLowerCase());
	}

	@Deprecated
	public static String mantemSomenteNumeros(String s) {
		return StringExtraiNumeros.exec(s);
	}

	public static String mantemSomenteOsSeguintesCaracteres(String s, String... list) {
		return UString.mantemSomenteOsSeguintesCaracteres(s, ListString.newFromArray(list));
	}

	public static String mantemSomenteOsSeguintesCaracteres(String s, List<String> list) {
		return UStringFiltrarCaracteres.exec(s, list);
	}

	public static String mantemSomenteOsSeguintesCaracteres(String s, Object... list) {
		ListString l = new ListString();
		for (Object o : list) {
			if (o instanceof List<?>) {
				List<?> x = (List<?>) o;
				l.add(x);
				continue;
			}
			l.add(o.toString());
		}
		return UString.mantemSomenteOsSeguintesCaracteres(s, l);
	}

	@Deprecated
	public static String primeiraMinuscula(String s) {
		return StringPrimeiraMinuscula.exec(s);
	}

	public static String primeiraMinuscula(Class<?> classe) {
		return StringPrimeiraMinuscula.exec(classe.getSimpleName());
	}

	public static String toCamelCaseSepare(Class<?> classe) {
		return StringToCamelCaseSepare.exec(classe.getSimpleName());
	}

	public static String toCamelCaseSepare(String s) {

		String x = s.substring(0, 1).toUpperCase();
		s = s.substring(1);
		s = s.replace("aa", "aA");
		String last = "";

		while (!StringEmpty.is(s)) {
			String n = s.substring(0, 1);
			s = s.substring(1);
			if (UConstantes.letrasMaiusculas.contains(n)) {
				x += " ";
			}
			if (IntegerIs.is(n) && !IntegerIs.is(last)) {
				x += " ";
			}
			last = n;
			if ("_".equals(n)) {
				x += " ";
				continue;
			}
			x += n;
		}
		
		x = ValidadorNomeProprio.instance.formatParcial(x);
		x = " " + x + " ";

		x = x.replace("ario ", "ário ");
		x = x.replace("Hodometro ", "Hod"+UConstantes.o_circunflexo+"metro ");
		x = x.replace("Codigo ", "Código ");
		x = x.replace(" Debito ", " Débito ");
		x = x.replace("Matricula ", "Matrícula ");
		x = x.replace("Pratica ", "Prática ");
		x = x.replace("Numero ", "N"+UConstantes.u_agudo+"mero ");
		x = x.replace("Pais ", "País ");
		x = x.replace("Veiculo ", "Veículo ");
		x = x.replace("Uf ", "UF ");
		x = x.replace(" U F ", " UF ");
		x = x.replace(" A ", " a ");
		x = x.replace(" O ", " o ");
		x = x.replace(" As ", " as ");
		x = x.replace(" Aos ", " aos ");
		x = x.replace(" Os ", " os ");
		x = x.replace(" Da ", " da ");
		x = x.replace(" Do ", " do ");
		x = x.replace(" Das ", " das ");
		x = x.replace(" Dos ", " dos ");
		x = x.replace(" Que ", " que ");
		x = x.replace("cao ", "ção ");
		x = x.replace("ao ", "ão ");
		x = x.replace("oes ", ""+UConstantes.o_til+"es ");
		x = x.replace("iao ", "ião ");
		x = x.replace("sao ", "são ");
		x = x.replace("aria ", "ária ");
		x = x.replace("aveis ", "áveis ");
		x = x.replace("ario ", "ário ");
		x = x.replace("icio ", "ício ");
		x = x.replace("ivel ", "ível ");
		x = x.replace("avel ", "ável ");
		x = x.replace("ipio ", "ípio ");
		x = x.replace("encia ", ""+UConstantes.e_circunflexo+"ncia ");
		x = x.replace("ogico ", UConstantes.o_agudo+"gico ");
		x = x.replace(" Orgao ", " "+UConstantes.O_til+"rgão ");
		x = x.replace("imetro", UConstantes.i_agudo+"metro");
		x = x.replace(" Em ", " em ");
		x = x.replace(" Ro ", " RO ");
		x = x.replace(" Area ", " área ");
		x = x.replace(" Agua ", " água ");
		x = x.replace(" Ultimo", " "+UConstantes.U_agudo+"ltimo");
		x = x.replace(" Unico", " "+UConstantes.U_agudo+"nico");
		x = x.replace(" Uteis ", " "+UConstantes.U_agudo+"teis ");
		x = x.replace(" Util ", " "+UConstantes.U_agudo+"til ");
		x = x.replace("Credito ", "Crédito ");
		x = x.replace(" Mae ", " Mãe ");
		x = x.replace(" Ja ", " já ");
		x = x.replace(" Ficara ", " Ficará ");
		x = x.replace(" Valido ", " Válido ");
		x = x.replace(" Seguranca ", " Segurança ");
		x = x.replace(" Eh ", " É ");
		x = x.replace(" Pre ", " Pré-");
		x = x.replace(" Nao ", " Não ");
		x = x.replace(" Media ", " Média ");
		x = x.replace(" Acrescimo", " Acréscimo");
		x = x.replace(" Endereco ", " Endereço ");
		x = x.replace(" Sabado", " Sábado");
		x = x.replace("emico ", ""+UConstantes.e_circunflexo+"mico ");
		x = x.replace(" Maxim", " Máxim");
		x = x.replace(" Minim", " Mínim");
		x = x.replace(" Apos ", " Após ");
		x = x.replace(" Serie ", " Série ");
		x = x.replace("onteudo ", "onte"+UConstantes.u_agudo+"do ");
		x = x.replace("orio ", "ório ");
		x = x.replace(" Email ", " E-mail ");
		x = x.replace(" Cnpj ", " CNPJ ");
		x = x.replace(" Cpj ", " CPF ");
		x = x.replace(" Cdi ", " CDI ");
		x = x.replace(" Tr ", " TR ");
		x = x.replace(" Selic ", " SELIC ");
		x = x.replace(" mes ", " m"+UConstantes.e_circunflexo+"s ");
		x = x.replace(" Mes ", " M"+UConstantes.e_circunflexo+"s ");
		x = x.replace(" Primeiro ", " 1"+UConstantes.o_primeiro+" ");
		x = x.replace(" Segundo ", " 2"+UConstantes.o_primeiro+" ");
		x = x.replace(" Terceiro ", " 3"+UConstantes.o_primeiro+" ");
		x = x.replace(" Primeira ", " 1"+UConstantes.a_primeira+" ");
		x = x.replace(" Segunda ", " 2"+UConstantes.a_primeira+" ");
		x = x.replace(" Terceira ", " 3"+UConstantes.a_primeira+" ");

		for (String numero : UConstantes.numeros) {
			ListString letras = UConstantes.letrasMaiusculas.union(UConstantes.letrasMinusculas);
			for (String letra : letras) {
				x = x.replace(numero + letra, numero + " " + letra);
			}
		}

		x = StringTrim.plus(x);

		if (x.endsWith("-")) {
			x = ignoreRigth(x).trim();
		}

		return StringPrimeiraMaiuscula.exec(x);

	}

	@Deprecated
	public static String right(String s, int count) {
		return StringRight.get(s, count);
	}

	@Deprecated
	public static String ignoreRigth(String s) {
		return StringRight.ignore1(s);
	}

	@Deprecated
	public static String ignoreRight(String s, int count) {
		return StringRight.ignore(s, count);
	}

	@Deprecated
	public static String ltrim(String s) {
		return StringTrim.left(s);
	}

	@Deprecated
	public static String rtrim(String s) {
		return StringTrim.right(s);
	}

	public static boolean contains(String s, String... list) {
		for (String string : list) {
			if (string != null && !"".equals(string) && StringContains.is(s, string)) {
				return true;
			}
		}
		return false;
	}

	public static String toCampoBusca(String s) {

		if (StringEmpty.is(s)) {
			return null;
		}

		s = s.toLowerCase();
		s = StringNormalizer.get(s);
		s = UString.mantemSomenteOsSeguintesCaracteres(s, " ", UConstantes.letrasMinusculas, UConstantes.numeros);
		s = StringTrim.plus(s);

		s = " " + s + " ";
		s = s.replace("y", "i");
		s = s.replace("ce", "se");
		s = s.replace("ci", "si");
		s = s.replace("ch", "x");
		s = s.replace("sh", "x");
		s = s.replace("c", "k");
		s = s.replace("qui", "ki");
		s = s.replace("que", "ke");
		s = s.replace("s", "z");

		for (String x : UConstantes.consoantes) {
			s = s.replace("ul" + x, "u");
		}

		s = s.replace("ul ", "u ");
		s = StringTrim.plus(s);

		return s.trim();

	}

	public static String textoEntreFirst(String s, String a, String b) {
		s = StringAfterFirst.get(s, a);
		if (StringEmpty.is(s)) {
			return null;
		}
		return StringBeforeFirst.get(s, b);
	}

	public static String textoEntreLast(String s, String a, String b) {
		s = StringAfterLast.get(s, a);
		if (StringEmpty.is(s)) {
			return null;
		}
		return StringBeforeFirst.get(s, b);
	}

	@Deprecated
	public static String toCamelCase(String s) {
		return StringCamelCase.exec(s);
	}

	private static String replacePalavraInterno(String de, String expressao, String resultado) {
		de = " " + de + " ";
		for (String a : UConstantes.SIMBOLOS) {
			for (String b : UConstantes.SIMBOLOS) {
				String c = a + expressao + b;
				String d = a + resultado + b;
				de = UString.replaceWhile(de, c, d);
			}
		}
		de = de.substring(1);
		return de.substring(0, de.length() - 1);
	}

	private static final String EXPRESSAO_TEMP = "+sb!.@#$,%*()xt-";

	public static String replacePalavra(String de, String expressao, String resultado) {
		if (resultado.contains(expressao)) {
			de = UString.replacePalavraInterno(de, expressao, UString.EXPRESSAO_TEMP);
			expressao = UString.EXPRESSAO_TEMP;
		}
		return UString.replacePalavraInterno(de, expressao, resultado);
	}

	public static String corrigeCaracteres(String s) {
		if (StringEmpty.is(s)) {
			return null;
		}
		return s.replace(UConstantes.A_circunflexo + UConstantes.o_primeiro, UConstantes.o_primeiro);
	}

	public static String reverse(String s) {
		StringBuffer sb = new StringBuffer(s);
		sb.reverse();
		return sb.toString();
	}

	public static String join(String separador, Object... lista) {
		return new ListString(lista).join(separador);
	}

	public static String join(String separador, List<?> lista) {
		return lista.stream().map(String::valueOf).collect(Collectors.joining(separador));
	}

	public static boolean endsWith(String s, String... list) {
		for (String string : list) {
			if (s.endsWith(string)) {
				return true;
			}
		}
		return false;
	}

	public static boolean startsWith(String s, String... list) {
		for (String string : list) {
			if (s.startsWith(string)) {
				return true;
			}
		}
		return false;
	}

	@Deprecated
	public static String repete(String s, int vezes) {
		return StringRepete.exec(s, vezes);
	}

	public static String removeTextoEntreWhile(String s, String inicio, String fim) {
		String x = s;
		do {
			s = x;
			x = removeTextoEntre(s, inicio, fim);
			x = x.replace(inicio+fim, "");
		} while (!StringCompare.eq(s, x));
		return x;
	}
	public static String removeTextoEntre(String s, String inicio, String fim) {
		String before = StringBeforeLast.get(s, inicio);
		if (before == null) {
			return s;
		}
		String after = StringAfterLast.get(s, inicio);
		if (after == null) {
			return s;
		}
		after = StringAfterFirst.get(after, fim);
		if (after == null) {
			return s;
		}
		return before + after;
	}

	public static String completaComEspacosADireita(String s, Integer casas) {
		while (s.length() < casas) {
			s += " ";
		}
		return s;
	}

	public static String completaComEspacosAEsquerda(String s, Integer casas) {
		if (s == null) {
			s = "";
		}
		while (s.length() < casas) {
			s = " " + s;
		}
		return s;
	}

	public static String completaComZerosAEsquerda(String s, Integer casas) {
		while (s.length() < casas) {
			s = "0" + s;
		}
		return s;
	}

	public static boolean contemSomenteNumeros(String s) {
		return UString.contemSomenteOsSeguintesCaracteres(s, UConstantes.numeros);
	}

	public static boolean contemSomenteOsSeguintesCaracteres(String s, ListString list) {
		if (StringEmpty.is(s)) {
			return false;
		}
		s = s.toLowerCase();
		s = UString.replace(s, list, "");
		return s.isEmpty();
	}

	public static String replace(String em, ListString itens, String por) {
		for (String string : itens) {
			em = em.replace(string, por);
		}
		return em;
	}

	public static boolean contemPalavra(String s, String... list) {
		s = s.toLowerCase();
		s = StringNormalizer.get(s);
		for (String x : UConstantes.SIMBOLOS) {
			s = s.replace(x, " ");
		}
		s = StringTrim.plus(s);
		s = " " + s + " ";
		for (String x : list) {
			if (StringContains.is(s, " " + x + " ") ) {
				return true;
			}
		}
		return false;
	}

	public static boolean checaSeSubstringEstaNaPosicao(String s, int inicio, String substring) {
		if ( s.length() < inicio ) {
			return false;
		}
		s = s.substring(inicio);
		return s.startsWith(substring);
	}

	@Deprecated
	public static final String textoScopo(String s, String abre, String fecha) {
		return StringEscopo.exec(s, abre, fecha);
	}

	public static int ocorrencias(String s, String substring) {
		int i = s.length();
		s = s.replace(substring, "");
		i -= s.length();
		return i / substring.length();
	}

	public static boolean feminino(String s) {
		if (StringEmpty.is(s)) {
			return false;
		}
		s = StringToCamelCaseSepare.exec(s).toLowerCase();
		if (s.endsWith("a") || s.endsWith("ção")) {
			return true;
		}
		return false;
	}
	public static boolean startsWithUpper(String s) {
		if (StringEmpty.is(s)) {
			return false;
		}
		s = s.substring(0, 1).trim();
		return UConstantes.letrasMaiusculas.contains(s);
	}
	public static boolean in(String s, String a, String... strings) {
		if (a.equals(s)) {
			return true;
		}
		for (String string : strings) {
			if (s.equals(string)) {
				return true;
			}
		}
		return false;
	}
	public static int getMenorIndex(String s, String a, String b) {

		int index1 = s.indexOf(a);
		int index2 = s.indexOf(b);

		if (index1 == -1) {
			return index2;
		}
		return index1;

	}

	@Deprecated
	public static int length(String s) {
		return StringLength.get(s);
	}

	@Deprecated
	public static boolean lengthIs(String s, int i) {
		return StringLength.get(s) == i;
	}

	@Deprecated
	public static String maxLength(String s, int max) {
		return StringLength.max(s, max);
	}

	public static String conteudoProximoParenteses(String s) {
		s = StringAfterFirst.get(s, "(");
		if (StringEmpty.is(s) || !StringContains.is(s, ")")) {
			return null;
		}

		String r = "";

		int opens = 1;
		while (opens > 0) {
			String c = s.substring(0, 1);
			r += c;
			s = s.substring(1);
			if (c.contentEquals("(")) {
				opens++;
			} else if (c.contentEquals(")")) {
				opens--;
			}
		}

		if (r.endsWith(")")) {
			return StringRight.ignore1(r);
		}
		return r;

	}

	public static String stuff(String s, int posicao, int count, String substr) {
		String x = s.substring(0, posicao-1);
		s = s.substring(posicao+count-1);
		return x + substr + s;
	}

}
