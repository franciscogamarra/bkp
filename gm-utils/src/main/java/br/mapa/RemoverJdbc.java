package br.mapa;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringCompare;

public class RemoverJdbc {
	
	private static final ListString sistemas = new ListString();
	static {

//		sistemas.add("segurancaadds");//usado pelo segaout

		sistemas.add("DCPOASigsifDS");
		sistemas.add("JDBC Data Source-0");
		sistemas.add("SiszarcDS");
		sistemas.add("SisserDS");
		sistemas.add("SismanDS");
		sistemas.add("AdidosPool");
		sistemas.add("SigmaPool");
		sistemas.add("RenasemDS");
		sistemas.add("SapcanaPool");
		sistemas.add("SisprocerPool");
		sistemas.add("SigvigPool");
		sistemas.add("SigPoaPool");
		sistemas.add("SilgaPool");
		sistemas.add("confcon-pool");
		sistemas.add("ConfconDS");
		sistemas.add("PgaSigsifDS");
		sistemas.add("ScvaDS"); 
		sistemas.add("SiadPool");
		sistemas.add("SigvigDS");
//		sistemas.add("SivibeDS");
		sistemas.add("SislabDS");
		sistemas.add("SacOldDS");
		sistemas.add("LogWSDS");
//		sistemas.add("SrhDS");
		sistemas.add("SGIPool");
		sistemas.add("sipe");
		sistemas.add("SipeDS");
//		sistemas.add("srh");
//		sistemas.add("seguranca");
//		sistemas.add("upload");
		sistemas.add("scvapool");
		sistemas.add("SiglaPool");
		sistemas.add("SiglaResPool");
	}

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
		
//		boolean houveMudancas = false;
		
		while (!list.isEmpty()) {
			
			String s = list.remove(0);
			
			if (s.trim().contentEquals("<jdbc-system-resource>")) {
				
				String ss = list.get(0).trim();
				
				if (sistemas.contains(i -> StringCompare.eq("<name>"+i+"</name>", ss) )) {
					
					while (!s.contentEquals("</jdbc-system-resource>")) {
						s = list.remove(0).trim();
					}
					
//					houveMudancas = true;
					
					continue;
					
				}
				
			}
			
			list1.add(s);
			
		}
		
		list1.rtrim();
		list1.removeDoubleWhites();
		list1.removeLastEmptys();
		list1.removeFisrtEmptys();
		
//		if (houveMudancas) {
			list1.save(file);
//		}
		
	}
	
}
