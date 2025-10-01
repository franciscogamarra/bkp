/* 
 * TODO
 * Esta classe não está completa ainda, sendo construída da forma mais simples com o objetivo de atender a ResumeTab.
 * Ela não é eficiente pois não considera preposicoes, portanto pluraliza indevidamente adjuntos adnominais e termos complementares à verbos
 * Deve ser refatorada no futuro
 * */
package src.commom.utils.idioma;

import gm.languages.ts.javaToTs.Stringg;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.SystemPrint;
import js.array.Array;
import src.commom.utils.array.Itens;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringSplit;

public class TextoSingularOuPlural {

	private static Array<TextoSingularOuPluralObj> knows = new Array<>();
	
	private static Array<TextoSingularOuPluralObj> ends = new Array<>(
		new TextoSingularOuPluralObj().singular("ão").plural("ões"),
		new TextoSingularOuPluralObj().singular("cao").plural("coes"),
		new TextoSingularOuPluralObj().singular("l").plural("is"),
		new TextoSingularOuPluralObj().singular("a").plural("as"),
		new TextoSingularOuPluralObj().singular("o").plural("os"),
		new TextoSingularOuPluralObj().singular("e").plural("es"),
		new TextoSingularOuPluralObj().singular("r").plural("res"),
		new TextoSingularOuPluralObj().singular("m").plural("ns")	
	);

	private static Array<TextoSingularOuPluralObj> specials = new Array<>(
		new TextoSingularOuPluralObj().singular("país").plural("países"),
		new TextoSingularOuPluralObj().singular("mês").plural("meses"),
		new TextoSingularOuPluralObj().singular("mes").plural("meses")
	);
	
	private static Array<String> quebras = new Array<>(
		" ", ",", ".", ")", ";", "!", "?"
	);
	
	public static String getPlural(String s) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		Stringg ss = new Stringg(s.trim());
		
		Array<TextoSingularOuPluralObj> itens = knows.filter(i -> StringCompare.eq(i.singular, ss.s));
		
		if (itens.lengthArray() > 0) {
			return itens.array(0).plural;
		}
		
		if (ss.s.indexOf(" ") == -1) {
			ss.s += " ";
		}
		
		ends.forEach(/*TextoSingularOuPluralObj*/ end ->
			quebras.forEach(/*String*/ quebra ->
				ss.s = ss.s.replace(end.singular + quebra, end.plural + quebra)
		));
		
		specials.forEach(/*TextoSingularOuPluralObj*/ end ->
			quebras.forEach(/*String*/ before ->
				quebras.forEach(/*String*/ after ->
					ss.s = ss.s.replace(before + end.singular + after, before + end.plural + after)
		)));
		
		ss.s = ss.s.replace(" des ", " de ");
		
		String sx = ss.s.trim() + " ";
		
		Itens<String> palavrasSeparadas = StringSplit.exec(ss.s.trim(), " ");
		while (palavrasSeparadas.size() > 1) {
			String atual = palavrasSeparadas.removeFirst();
			if (StringCompare.eq(atual, "dos") || StringCompare.eq(atual, "das") || StringCompare.eq(atual, "nos") || StringCompare.eq(atual, "nas")) {
				String proxima = palavrasSeparadas.removeFirst();
				if (!proxima.endsWith("s")) {
					String x = " "+atual+" "+proxima;
					String y = " "+StringRight.ignore1(atual)+" "+proxima;
					sx = sx.replace(x+" ", y+" ");
					sx = sx.replace(x+",", y+",");
					sx = sx.replace(x+".", y+".");
					sx = sx.replace(x+"?", y+"?");
					sx = sx.replace(x+")", y+")");
				}
			}
		}
		
		sx = sx.trim();
		
		knows.push(new TextoSingularOuPluralObj().singular(s).plural(sx));
		
		return sx;
		
	}

	public static String getSingular(String s) {
		
		if (StringEmpty.is(s)) {
			return "";
		}
		
		Stringg ss = new Stringg(s.trim());
		
		Array<TextoSingularOuPluralObj> itens = knows.filter(i -> StringCompare.eq(i.plural, ss.s));
		
		if (itens.lengthArray() > 0) {
			return itens.array(0).singular;
		}
		
		if (ss.s.indexOf(" ") == -1) {
			ss.s += " ";
		}
		
		ss.s = " " + ss.s;
		
		specials.forEach(/*TextoSingularOuPluralObj*/ end ->
			quebras.forEach(/*String*/ before ->
				quebras.forEach(/*String*/ after ->
					ss.s = ss.s.replace(before + end.plural + after, before + end.singular + after)
		)));
				
		ends.forEach(/*TextoSingularOuPluralObj*/ end ->
			quebras.forEach(/*String*/ quebra ->
				ss.s = ss.s.replace(end.plural + quebra, end.singular + quebra)
		));

		ss.s = ss.s.trim();
		
		knows.push(new TextoSingularOuPluralObj().plural(s).singular(ss.s));
		
		return ss.s.trim();

	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SystemPrint.ln( getPlural("Entidade do Sistema") );
	}
	
	
}