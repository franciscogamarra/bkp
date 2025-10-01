package src.commom.utils.idioma;

import gm.languages.ts.javaToTs.annotacoes.Any;
import js.annotations.NaoConverter;
import js.annotations.Support;

@Support @NaoConverter @Any
public class TextoSingularOuPluralObj {
	
	public String singular;
	public String plural;
	
	public TextoSingularOuPluralObj singular(String s) {
		this.singular = s;
		return this;
	}

	public TextoSingularOuPluralObj plural(String s) {
		this.plural = s;
		return this;
	}

}