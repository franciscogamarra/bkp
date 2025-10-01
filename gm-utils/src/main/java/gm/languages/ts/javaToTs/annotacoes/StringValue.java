package gm.languages.ts.javaToTs.annotacoes;

public class StringValue {

	public final String s;

	public StringValue(String s) {
		this.s = s;
	}
	
	@Override
	public String toString() {
		return s;
	}
	
	public String self() {
		return s;
	}
	
}
