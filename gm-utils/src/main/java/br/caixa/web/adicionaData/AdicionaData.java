package br.caixa.web.adicionaData;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringRepete;

public class AdicionaData {
	
	private final GFile file;
	
	public AdicionaData(GFile file) {
		this.file = file;
		if (possuiNewDate()) {
			adicionaImport();
		}
	}

	private void adicionaImport() {

		ListString list = file.load();
		list.removeFisrtEmptys();
		list.add(0, "");
		list.add(0, getImport());
		list.replaceTexto("new Date()", "DataHora.now()");
		list.setRtrimOnSave(false);
		list.save();
		
	}

	private boolean possuiNewDate() {
		
		ListString list = file.load();
		list.removeIf(s -> s.trim().startsWith("//"));
		
		list.replaceEach(s -> {
			if (s.contains("//")) {
				s = StringBeforeFirst.get(s, "//");
			}
			return s;
		});
		
		list.trimPlus();
		String s = list.toString("");
		
		while (s.contains("/*")) {
			String before = StringBeforeFirst.get(s, "/*");
			s = StringAfterFirst.get(s, "/*");
			s = StringAfterFirst.get(s, "*/");
			s = before + s;
		}
		
		return s.contains("new Date()");
		
	}

	private String getImport() {
		
//		import Data from "../../../utils/Data.js";
		//C:/desenvolvimento/dev/quarkus/silce-web/resource/modules/utils/Data.js
		//C:/desenvolvimento/dev/quarkus/silce-web/resource/modules/app/silce-app/factories/AppFactory.js
		
		String s = file.toString();
		s = StringAfterFirst.get(s, "/resource/modules/");
		ListString list = ListString.byDelimiter(s, "/");
		
		s = StringRepete.exec("../", list.size() - 1);
		s += "utils/DataHora.js\";";
		s = "import DataHora from \"" + s;
		
		return s;
	}
	

	public static void main(String[] args) {
		
		GFile.get("C:/desenvolvimento/dev/quarkus/silce-web/resource/modules/app/").getAllFiles().filter(i -> i.isExtensao("js")).forEach(i -> new AdicionaData(i));
		
//		AdicionaData adicionaData = new AdicionaData(GFile.get("C:/desenvolvimento/dev/quarkus/silce-web/resource/modules/app/silce-app/factories/AppFactory.js"));
		
//		String imp = adicionaData.getImport();
//		
//		SystemPrint.ln(imp);
//		
		
	}
	
	
}