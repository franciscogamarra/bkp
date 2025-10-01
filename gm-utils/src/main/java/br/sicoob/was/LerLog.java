package br.sicoob.was;

import gm.utils.date.Data;
import gm.utils.string.ListString;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;

public class LerLog {

	public static void main(String[] args) {
		
		ListString list = new ListString();
		list.load("C:\\Program Files\\IBM\\WebSphere\\AppServer\\profiles\\AppSrv01\\logs\\server1\\SystemOut.log");
		list.removeIf(s -> s.trim().startsWith("at com."));
		list.removeIf(s -> s.trim().startsWith("at org."));
		list.removeIf(s -> s.trim().startsWith("at sun."));
		list.removeIf(s -> s.trim().startsWith("at java."));
		list.removeIf(s -> s.trim().startsWith("... "));
		
		Data data = new Data(2024,4,3,10,25);
		
		while (!list.isEmpty()) {
			
			String s = list.get(0).trim();

			if (!s.startsWith("[") || !s.contains(" BRT]")) {
				list.remove(0);
				continue;
			}
			
			s = s.substring(1);
			s = StringBeforeFirst.get(s, " BRT]");
			s = StringBeforeLast.get(s, ":");
			
			Data dt = Data.unformat("[dd]/[mm]/[yy] [hh]:[nn]:[ss]", s);
			
			if (dt.maiorOuIgual(data)) {
				break;
			} else {
				list.remove(0);
			}
			
		}
		
		list.print();
		System.out.println(list.size() + " linhas");
		
	}
	
}
