package temp;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;

public class JspsRemove {
	
	public static void main(String[] args) {

		ListString list = new ListString();
		list.load("C:\\DEV\\cs2019\\gm-utils\\src\\main\\java\\temp\\jsps.txt");
		for (String s : list) {
			exec(GFile.get(s));
		}
		
	}

	private static void exec(GFile file) {
		ListString list = file.load();
		String begin = "<!-- " + StringAfterFirst.get(file.toString(), "WebContent") + " begin -->";
		String end = "<!-- " + StringAfterFirst.get(file.toString(), "WebContent") + " end -->";
		list.removeIfEquals(begin);
		list.removeIfEquals(end);
		list.save();
	}
	
}