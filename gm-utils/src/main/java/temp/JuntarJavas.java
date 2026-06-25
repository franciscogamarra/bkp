package temp;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;

public class JuntarJavas {

	public static void main(String[] args) {
		ListString lst = new ListString();
		Lst<GFile> filter = GFile.get("C:\\dev\\projs\\bkp\\sicoob-proxy\\src\\main\\java").getAllFiles().filter(i -> i.isJava());
		filter.forEach(i -> {
			String s = StringAfterFirst.get(i.toString(), "/java/");
			lst.add("=novo=" + s);
			lst.addAll(i.load());
			
		});
		lst.saveTemp();
	}
	
}
