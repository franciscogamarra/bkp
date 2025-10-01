package gm.languages.palavras;

import gm.languages.palavras.comuns.Espaco;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.Quebra;
import gm.languages.palavras.comuns.Tab;
import gm.languages.palavras.comuns.literal.InteiroLiteral;
import gm.languages.palavras.comuns.literal.LongLiteral;
import gm.languages.palavras.comuns.simples.Barra;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.longo.LongIs;

public class PalavraBuild {
	
	private static final Lst<Palavra> itens = UClass.getClassesDaPackage(Barra.class.getPackage(), false).map(i -> (Palavra) UClass.newInstance(i));

	public static Palavra buildPalavra(Palavras palavras, String s) {
		
		if (palavras.funcBuildPalavra != null) {
			Palavra o = palavras.funcBuildPalavra.call(s);
			if (o != null) {
				return o;
			}
		}
		
		Palavra item = itens.unique(i -> i.getS().equalsIgnoreCase(s));
		
		if (item != null) {
			return UClass.newInstance(item.getClass());
		}

		if (IntegerIs.is(s)) {
			return new InteiroLiteral(s);
		}
		
		if (LongIs.is(s)) {
			return new LongLiteral(s);
		}
		
		if (s.equalsIgnoreCase("_espaco_")) {
			return new Espaco();
		}

		if (s.equalsIgnoreCase("_tab_")) {
			return new Tab();
		}

		if (s.equalsIgnoreCase("_quebra_")) {
			return new Quebra();
		}
		
		return new NaoClassificada(s);
		
	}

	public static Palavra build(Class<? extends Palavra> classe) {
		return UClass.newInstance(classe);
	}
	
}