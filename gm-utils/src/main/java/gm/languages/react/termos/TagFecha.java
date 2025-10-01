package gm.languages.react.termos;

import gm.languages.palavras.Palavra;

public class TagFecha extends Palavra {

	public final Tag tag;
	public TagAbre abre;

	public TagFecha(Tag tag) {
		super("");
		this.tag = tag;
	}
	
	public TagFecha(TagAbre tagAbre) {
		this(tagAbre.tag);
		this.abre = tagAbre;
		this.abre.fecha = this;
	}
	
	@Override
	public String getS() {
		return "</"+tag+">";
	}

}
