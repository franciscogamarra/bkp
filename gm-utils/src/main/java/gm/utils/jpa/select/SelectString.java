package gm.utils.jpa.select;

import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringTrim;

public class SelectString<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, String> {
	public SelectString(TS x, String campo) {
		super(x, campo);
	}
	public TS like(StringBuilder s){
		return like(s.toString());
	}
	public TS like(String s){
		c().like(getCampo(), s);
		return ts;
	}
	public TS notLike(String s){
		c().notLike(getCampo(), s);
		return ts;
	}
	public TS likePlus(String s){
		s = StringTrim.plus(s);
		if (StringEmpty.is(s)) {
			return ts;
		} else {
			return like("%" + s.replace(" ", "%") + "%");
		}
	}
	public TS startsWith(String s){
		c().startsWith(getCampo(), s);
		return ts;
	}
	public TS notStartsWith(String nome) {
		c().notStartsWith(getCampo(), nome);
		return ts;
	}
	public TS endsWith(String s){
		c().endsWith(getCampo(), s);
		return ts;
	}
	public TS notEndsWith(String s){
		c().notEndsWith(getCampo(), s);
		return ts;
	}

//	public SelectStringLength<TS> len(){
//		new SelectStringLength<>(ts, campo, c)
//		return null;
//	}
}
