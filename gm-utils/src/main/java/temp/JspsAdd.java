package temp;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;

public class JspsAdd {
	
	public static void main(String[] args) {

		ListString list = new ListString();
		list.load("C:\\DEV\\cs2019\\gm-utils\\src\\main\\java\\temp\\jsps.txt");
		for (String s : list) {
			exec(GFile.get(s));
		}
		
//		Lst<GFile> itens = GFile.get("C:\\DEV\\projects\\sipeagro-src\\sipe-web\\WebContent").getAllFiles().filter(i -> i.isExtensao("jsp"));
		
//		for (GFile file : itens) {
//			list.add(file.toString());
////			exec(file);
//		}
		
//		list.save("C:\\DEV\\cs2019\\gm-utils\\src\\main\\java\\temp\\jsps.txt");
		
//		C:\DEV\projects\sipeagro-src\sipe-web\WebContent\paginas\restringe.jsp
		
	}

	private static void exec(GFile file) {
		ListString list = file.load();
		list.add(0, "<!-- " + StringAfterFirst.get(file.toString(), "WebContent") + " begin -->");
		list.add("<!-- " + StringAfterFirst.get(file.toString(), "WebContent") + " end -->");
		list.save();
	}
	
}