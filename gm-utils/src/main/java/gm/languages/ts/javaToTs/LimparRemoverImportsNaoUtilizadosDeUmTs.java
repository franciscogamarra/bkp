package gm.languages.ts.javaToTs;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.comum.Box;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringTrim;

public class LimparRemoverImportsNaoUtilizadosDeUmTs {
	
	public static void main(String[] args) {
		
		GFile path = GFile.get("C:\\opt\\desen\\gm\\elfa-care-front\\src\\");
		Lst<GFile> files = path.getAllFiles().filter(i -> i.isExtensao("tsx"));
		
		for (GFile file : files) {
			ListString list = file.load();
			if (exec(list)) {
				list.save();
			}
		}
		
	}

	public static boolean exec(ListString list) {
	
		boolean result = false;
		
		ListString lst = list.clone();
		lst.trimPlus();
		ListString imports = lst.removeAndGet(s -> s.startsWith("import "));
		lst.removeIfStartsWith("//");
		String text = lst.toString("");
		
		for (String imp : imports) {
			
			String s = StringAfterFirst.get(imp, " ");
			s = StringBeforeFirst.get(s, " from ").trim();
			
			if (s.isEmpty() || s.contentEquals("React")) {
				continue;
			}
			
			ListString itens = ListString.separaPalavras(s);
			itens.trimPlus();
			
			if (itens.size() > 1) {
				
				if (itens.size() == 3 && itens.get(0).contentEquals("{") && itens.get(2).contentEquals("}")) {
					itens.remove(0);
					itens.removeLast();
				} else {
//					TODO implementar
					continue;
				}
				
			}
			
//			if (itens.isEmpty()) {
//				SystemPrint.ln(imp);
//			}
			
			s = itens.get(0);
			
			if (!text.contains(s)) {
				list.remove(imp);
				result = true;
			}
			
		}
		
		Box<Boolean> box = new Box<>();
		box.set(false);
		
		list.replaceEach(s -> {
			if (!s.trim().isEmpty()) {
				String x = StringTrim.right(s);
				x = x.replace(" if(", " if (");
				x = x.replace("\tif(", "\tif (");
				x = x.replace(" return(", " return (");
				x = x.replace(" for(", " for (");
				x = x.replace("\tfor(", "\tfor (");
				x = x.replace(")  ", ") ");
				
				if (!x.contentEquals(s)) {
					box.set(true);
					return x;
				}
			}
			return s;
		});
		
		return list.removeDoubleWhites() > 0 || result || box.get();
		
	}
	
}
