package js.ts.read;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.ts.words.Export;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;

public class PalavrasBuildTs {

	private static final Lst<Palavra> ITENS = UClass.getClassesDaPackage(Export.class.getPackage(), false)
			.map(i -> (Palavra) UClass.newInstance(i));

	public static void start(Palavras palavras) {

		palavras.funcBuildPalavra = s -> {

			Palavra item = ITENS.unique(i -> i.getS().equalsIgnoreCase(s));

			if (item != null) {
				Palavra o = UClass.newInstance(item.getClass());
				o.setS(s);
				return o;
			}
			
			return null;

		};

	}

}