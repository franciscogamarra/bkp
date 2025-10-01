package gm.languages.ts.javaToTs;

import gm.utils.javaCreate.JcClasse;
import gm.utils.reflection.Atributos;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringRight;

public class SortAs {

	private static final ListString addrOrders = ListString.array("addrZip","addrCountry","addrState","addrCity","addrLine","addrNumber","addrComplement");
	
	public static void exec(Atributos as, JcClasse jc) {
	
		ListString list = new ListString().load(jc.getFileName());
		list.trimPlus();
		list.removeIf(s -> !s.startsWith("public "));
		list.removeIf(s -> !s.endsWith(";"));
		list.replaceEach(s -> StringAfterLast.get(s, " "));
		list.replaceEach(s -> StringRight.ignore1(s));
		
		list.sort((a,b) -> {
			
			if (!a.startsWith("addr")) {
				return 0;
			}

			if (!b.startsWith("addr")) {
				return 0;
			}
			
			int ia = addrOrders.indexOf(a);
			int ib = addrOrders.indexOf(b);
			
			return IntegerCompare.compare(ia, ib);
			
		});
		
		as.sort((a, b) -> {
			int ia = list.indexOf(a.nome());
			int ib = list.indexOf(b.nome());
			return IntegerCompare.compare(ia, ib);
		});
		
		as.moveToStart("name");
		as.moveToStart("id");
		as.moveToEnd("createdAt");
		as.moveToEnd("updatedAt");
		
	}
	
}
