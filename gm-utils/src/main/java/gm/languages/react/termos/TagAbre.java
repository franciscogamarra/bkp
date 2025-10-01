package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import gm.utils.comum.Lst;

public class TagAbre extends Palavra {

	public final Tag tag;
	public TagFecha fecha;
	public Lst<TagParam> params; 

	public TagAbre(Tag tag) {
		super("");
		this.tag = tag;
	}
	
	@Override
	public String getS() {
		
		if (params == null) {
			return "<"+tag+">";
		} else {
			return "<"+tag+params.toString(" ")+">";
		}
		
	}

}
