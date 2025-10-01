package gm.languages.sql;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.sql.expressoes.variaveis.VarReference;
import gm.languages.sql.expressoes.variaveis.Variavel;
import gm.languages.sql.palavras.Begin;
import gm.languages.sql.palavras.Int;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;

public class PalavrasBuildSql {

	private static final Lst<Palavra> ITENS = UClass.getClassesDaPackage(Begin.class.getPackage(), false)
			.map(i -> (Palavra) UClass.newInstance(i));

	public static void start(Palavras palavras, Lst<Variavel> vars) {

		palavras.funcBuildPalavra = s -> {

			Palavra item = ITENS.unique(i -> i.getS().equalsIgnoreCase(s));

			if (item != null) {
				Palavra o = UClass.newInstance(item.getClass());
				o.setS(s);
				return o;
			}

			if (s.equalsIgnoreCase("integer")) {
				Int o = new Int();
				o.setS(s);
				return o;
			}
			
			if (s.startsWith("@") || s.startsWith("#")) {
				
				Variavel o = vars.unique(i -> i.getNome().equalsIgnoreCase(s));

				if (o == null) {
					o = new Variavel(s);
					vars.add(o);
				}

				return new VarReference(o);

			}

			return null;

		};

	}

}