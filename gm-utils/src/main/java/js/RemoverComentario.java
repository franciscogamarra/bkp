package js;

import gm.utils.files.GFile;
import gm.utils.string.ListString;

public class RemoverComentario {

	public static void main(String[] args) {
		
		GFile js = GFile.get("C:\\opt\\desen\\gm\\cs2019\\gm-utils\\js");
		js.getAllFiles().each(file -> {

			ListString list = new ListString().load(file);
			
			if (!list.get(0).startsWith("/*")) {
				return;
			}
			
			if (!list.get(1).trim().startsWith("desenvolvido por")) {
				return;
			}

			if (!list.get(2).trim().startsWith("ultima")) {
				return;
			}
			
			if (!list.get(3).startsWith("*/")) {
				return;
			}
			
			list.remove(0);
			list.remove(0);
			list.remove(0);
			list.remove(0);
			
			list.save();
			
		});
		
		
	}
	
}
