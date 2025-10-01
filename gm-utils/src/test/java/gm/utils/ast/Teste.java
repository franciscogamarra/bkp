package gm.utils.ast;

import gm.utils.string.ListString;

public class Teste {

	public static void main(String[] args) {

		ListString list = new ListString();
		list.load("/opt/desen/gm/cs2019/gm-utils/src/test/java/gm/utils/ast/UmaClasse.java");
		list.print();

	}

}
