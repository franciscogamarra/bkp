package gm.utils.outros;

import gm.utils.string.ListString;
import gm.utils.string.StringClipboard;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringTrim;

public class Teste {

	public static void main0(String[] args) {
		ListString list = new ListString();
		list.add(StringClipboard.get().split("\n"));
		list.saveTemp();
	}
	
	public static void main(String[] args) {
		ListString list = new ListString();
		list.loadTemp();
		list.replaceEach(s -> {
			s = StringTrim.plus(s);
			s = StringAfterFirst.get(s, "\"");
			String field = StringBeforeFirst.get(s, "\"");
			s = StringAfterFirst.get(s, "\"");
			s = StringAfterFirst.get(s, "\"");
			s = StringBeforeFirst.get(s, "\"");
			s = "public String "+field+";//" + s;
			return s;
		});
		list.saveTemp();
//		StringClipboard.set(list.toString("\n"));
	}
	
}