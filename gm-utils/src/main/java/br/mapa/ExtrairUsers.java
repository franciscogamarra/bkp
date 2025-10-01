package br.mapa;

import gm.utils.comum.SystemPrint;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;

public class ExtrairUsers {

	public static void main(String[] args) {
		
		ListString list = new ListString().load("C:\\opt\\desen\\gm\\cs2019\\chrome-plugin\\mapa-sisprocer-issue-594.js");
		list.trimPlus();
		list.removeIf(s -> !s.contains("let user ="));
		list.removeIf(s -> !s.contains("funcafe"));
		list.replaceEach(s -> StringAfterFirst.get(s, "=").trim());
		list.replaceEach(s -> s.replace("//", ""));
		list.replaceEach(s -> s.replace(";", " - "));
		list.replaceEach(s -> s.replace("'", ""));
		list.replaceEach(s -> s.replace("\"", ""));
		list.trimPlus();
		
		SystemPrint.ln("Bom dia a todos!");
		SystemPrint.ln("Solicito liberação dos seguintes usuários ao sistema FUNCAFE em ambiente mapadup:");
		SystemPrint.ln();
		
//		for (String s : list) {
//			StringSplit.exec(s, "-").trimPlus().removeIfEquals("funcafe").addd("").print();
//		}
		
	}
	
}
