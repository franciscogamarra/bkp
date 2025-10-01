package br.mapa;

import gm.utils.files.GFile;
import gm.utils.string.ListString;

public class RemoverJdbcs {
	
	public static void main(String[] args) {
		GFile file = GFile.get("C:\\MAPA\\xmls.txt");
		ListString xmls = file.load();
		ListString xmls0 = new ListString();
		for (String xml : xmls) {
			GFile fl = GFile.get(xml);
			if (fl.exists()) {
				exec(fl);
				xmls0.add(xml);
			}
		}
		xmls0.save(file);
//		exec(GFile.get("C:\\MAPA\\WebLogic\\user_projects\\domains\\base_domain00\\config\\config.xml"));
	}


	private static void exec(GFile file) {
		
		ListString list = file.load();
		
		ListString list1 = new ListString();
		
		boolean houveMudancas = false;
		
		while (!list.isEmpty()) {
			
			String s = list.remove(0);
			
			if (s.trim().contentEquals("<jdbc-system-resource>")) {
				
				while (!s.contentEquals("</jdbc-system-resource>")) {
					s = list.remove(0).trim();
				}
				
				houveMudancas = true;
				
				continue;
				
			}
			
			list1.add(s);
			
		}
		
		if (houveMudancas) {
			list1.save(file);
		}
		
	}
	
}
