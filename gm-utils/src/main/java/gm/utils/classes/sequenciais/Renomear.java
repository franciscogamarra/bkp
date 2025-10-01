package gm.utils.classes.sequenciais;

import gm.utils.classes.UClass;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerFormat;

public class Renomear {
	public static void main(String[] args) {
//		UConfigFwConstructor.config();
//		String prefixoDe = FwScript001.class.getName();
		String prefixoDe = "";
		prefixoDe = prefixoDe.replace("001", "");
		int i = 1;
		while (true) {
			String s = prefixoDe + IntegerFormat.zerosEsquerda(i, 3);
			Class<Object> classe = UClass.getClass(s);
			if (classe == null) {
				return;
			}
			ListString list = ListString.loadClass(classe);
			list.replaceTexto("public class FwScript", "public class Script");
			list.replaceTexto("fwScripts", "scripts");
			String fileName = UClass.getJavaFileName(classe);
			fileName = fileName.replace("FwScript", "Script");
			fileName = fileName.replace("fwScripts", "scripts");
//			SystemPrint.ln(fileName);
			list.save(fileName);
			i++;
		}
		//		*/
	}
}
