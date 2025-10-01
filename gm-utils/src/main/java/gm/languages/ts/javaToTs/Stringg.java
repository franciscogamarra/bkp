package gm.languages.ts.javaToTs;

import js.array.Array;
import src.commom.utils.string.StringSplit;

public class Stringg {
	
	public Stringg(String s) {
		this.s = s;
	}
	
	public String s;

	public Array<String> split(String sub) {
		return StringSplit.exec(s, sub).getArray();
	}

	public boolean includes(String string) {
		// TODO Auto-generated method stub
		return false;
	}
}
