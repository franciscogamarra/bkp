package br.sicoob.arrumarimports;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;

public class ArrumarImports {

	public static void main(String[] args) {
		
		Lst<GFile> files = GFile.get("C:\\dev\\projects\\cre-concessao-bndes\\Integracao\\cre-concessao-bndes-integracao-ejb\\src\\main\\java\\br\\com\\sicoob\\sisbr\\bndes\\integracao\\xml\\modelo").getFiles();
		
		for (GFile file : files) {
			exec(file);
		}
		
	}

	private static void exec(GFile file) {
		
		ListString lst = new ListString();
		
		ListString list = file.load();
		for (String s : list) {
			if (s.startsWith("import") && lst.getLast().trim().isEmpty() && lst.get(-2).startsWith("import")) {
				lst.removeLast();
			}
			lst.add(s);
		}
		lst.setRtrimOnSave(false);
		lst.save(file);
		
	}
	
}
