package br.mapa;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;

public class MapearXmls {

	public static void main(String[] args) {
		
		ListString list = new ListString();
		
		GFile file = GFile.get("C:\\MAPA\\xmls.txt");
		
		Lst<GFile> ignorar = new Lst<>();
		ignorar.add(GFile.get("C:/MAPA/libs/"));
		ignorar.add(GFile.get("C:/MAPA/softwares/"));
		ignorar.add(GFile.get("C:/MAPA/WebLogic/modules/"));
		
		GFile.get("C:\\MAPA").eachFileP(i -> {
			
			String s = i.toString();
			
			try {
				if (i.isExtensao("xml")) {
					if (!list.contains(s)) {
						list.add(s);
						list.save(file);
					}
				}
			} catch (Exception e) {
				// TODO: handle exception
			}
			
		}, i -> {
			
			if (ignorar.contains(i)) {
				return false;
			}
			
			if (i.endsWith("tmp")) {
				return false;
			}
			
			String s = i.toString();
			
			if (s.startsWith("C:/MAPA/WebLogic/jrockit")) {
				return false;
			}

			if (s.contains("/base_domain") && !s.contains("/base_domain/")) {
				return false;
			}
			
			return true;
			
		});
	}
	
}

