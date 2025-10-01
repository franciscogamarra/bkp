package gm.languages.ts.javaToTs.annotacoes;

import gm.utils.comum.SystemPrint;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.string.StringRight;

public abstract class StringValues {
	
	private final Atributos as;
	private final String snp;
	
	public StringValues() {
		as = AtributosBuild.get(getClass()).getStatics().filter(a -> a.is(StringValue.class));
		as.sort((a,b) -> -IntegerCompare.compare(a.nome().length(), b.nome().length()));
		snp = getClass().getSimpleName() + ".";
	}
	
	public boolean andNull() {
		return true;
	}
	
	public boolean andUndefined() {
		return true;
	}
	
	public StringValue get(Atributo a) {
		return a.get(getClass());
	}
	
	public StringValue get(String key) {
		return get(as.getObrig(key));
	}
	
	public void print() {
		
		Class<? extends StringValues> c = getClass();
		
		ListString list = new ListString();
		
		as.forEach(a -> list.add(get(a.nome()).s));
		
		list.sort();
		
		list.replaceEach(s -> "\"" + s + "\"");
		
		if (andNull()) {
			list.add("null");
		}
		
		if (andUndefined()) {
			list.add("undefined");
		}
		
		String s = list.toString("|");
		
		SystemPrint.ln("export type "+ StringRight.ignore1(c.getSimpleName()) +" = " + s + ";");
		
	}
	
	public void replaceInList(ListString list) {
		as.forEach(a -> list.replaceTexto(snp + a.nome(), "\"" + get(a) + "\""));
	}
	
}