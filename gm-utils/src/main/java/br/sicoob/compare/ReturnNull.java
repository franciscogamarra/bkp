package br.sicoob.compare;

import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringPrimeiraMaiuscula;

public class ReturnNull {

	public static void main(String[] args) {
		
		ListString list = new ListString();
		list.load("c:/tmp/a.txt");
		list.trimPlus();
		list.removeIf(i -> !i.startsWith("protected"));
		list.replaceEach(i -> StringAfterFirst.get(i, " "));
		list.replaceEach(i -> StringAfterFirst.get(i, " "));
		list.replaceEach(i -> StringBeforeFirst.get(i, ";"));
		list.replaceEach(i -> "if (o.get" + StringPrimeiraMaiuscula.exec(i) + "() != null) {return o;}");
		
		list.print();
	}
	
}
