package br.sicoob.compare;

import gm.utils.files.GFile;

public class ChecarEntidades {

	public static void main(String[] args) {
		
		String s = "C:\\dev\\projects\\cre-concessao-bndes\\Comum\\cre-concessao-bndes-entidades\\src\\main\\java\\br\\com\\sicoob\\sisbr\\bndes\\comum\\entidades\\";
//		
		GFile.get(s).getFiles().map(i -> i.getSimpleNameWithoutExtension())
		.map(i -> "<class>br.com.sicoob.sisbr.bndes.comum.entidades."+i+"</class>")
		.sortToString()
		.print();
		
//		ListString list = new ListString();
//		list.load("c:/tmp/x.txt");
//		list.sort();
//		list.print();
		
//		C:\dev\projects\cre-concessao-bndes\Integracao\cre-concessao-bndes-integracao-ejb\src\main\resources\META-INF\persistence.xm
		
	}
	
}