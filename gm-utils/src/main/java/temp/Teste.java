package temp;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringTrim;

public class Teste {

	public static void main(String[] args) {
		
		etapa01();
//		etapa02();
		
	}

	private static void etapa02() {
		ListString list = GFile.get("C:\\temp\\x.txt").load();
		list.removeIfStartsWith("com.");
		list.removeIfStartsWith("sun.");
		list.removeIfStartsWith("weblogic.");
		list.removeIfStartsWith("oracle.");
		list.removeIfStartsWith("java.");
//		list.removeIfStartsWith("org.");
		list.saveTemp();
	}
	
	private static void etapa01() {
		ListString list = GFile.get("C:\\tmp\\x.txt").load();
		list.trimPlus();
		String s = list.toString(" ");
		s = StringTrim.plus(s);
		list = ListString.byDelimiter(s, " at ");
		list.saveTemp();
	}
	
}
