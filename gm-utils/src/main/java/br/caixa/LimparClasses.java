package br.caixa;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.number.ULong;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringTrim;

public class LimparClasses {

	private static final GFile quarkus = GFile.get("C:\\desenvolvimento\\dev\\quarkus");
	
	public static void main(String[] args) {
//		exec(GFile.get("C:\\desenvolvimento\\dev\\quarkus\\LOTERIAS-parametros\\loterias-parametros-service\\src\\main\\java\\br\\caixa\\loterias\\parametros\\parametrosSimulacao\\ParametroSimulacaoApi.java"));
		all();
	}
	
	public static void all() {
		
		long ultima;
		
		GFile paramsFile = quarkus.join("params.txt");
		if (paramsFile.exists()) {
			String s = paramsFile.load().unique(i -> i.startsWith("ultima-limpeza="));
			s = StringAfterFirst.get(s, "=");
			ultima = ULong.toLong(s);
		} else {
			ultima = 0;
		}
		
		Lst<GFile> javas = quarkus.getAllFiles().filter(i -> i.isJava()).filter(i -> i.lastModified() > ultima);
		
		for (GFile file : javas) {
			exec(file);
		}
		
	}

	private static void exec(GFile file) {
		
		ListString list = file.load();
		list.removeDoubleWhites();
//		list.removeIf(s -> s.startsWith("<<<<<<< HEAD:"));
//		list.removeIf(s -> s.contentEquals("======="));
//		list.removeIf(s -> s.startsWith(">>>>>>> "));
		list.replaceEach(s -> StringTrim.right(s));
		list.replaceEach(s -> s
			.replace("throw  ", "throw ")
			.replace(" if(", " if (")
			.replace(" return(", " return (")
			.replace(" for(", " for (")
			.replace("\tfor(", "\tfor (")
			.replace(")  ", ") ")
			.replace("){", ") {")
			.replace("package br.caixa.loterias.silce.v1.", "package br.caixa.loterias.silce.")
			.replace("import br.caixa.loterias.silce.v1.", "import br.caixa.loterias.silce.")
		);
		
//		x = x.replace(" if(", " if (");
//		x = x.replace("\tif(", "\tif (");
//		x = x.replace(" return(", " return (");
//		x = x.replace(" for(", " for (");
//		x = x.replace("\tfor(", "\tfor (");
//		x = x.replace(")  ", ") ");

		
		for (int i = 1; i < list.size(); i++) {
			
			String s = list.get(i);
			
			if (s.isEmpty()) {
				
				String ss = list.get(i-1);

				String tabs = "";
				while (ss.startsWith("\t")) {
					tabs += "\t";
					ss = ss.substring(1);
				}
				
				if (ss.endsWith("{")) {
					tabs += "\t";
				}
				
				s = tabs + s;
				
				list.remove(i);
				list.add(i, s);
				
			}
			
		}
		
		for (int i = 1; i < list.size(); i++) {
			String s = list.get(i);
			
			if (s.startsWith("\t@Override ")) {
				list.remove(i);
				list.add(i, "\t@Override");
				list.add(i+1, s.replace("@Override ", ""));
				i++;
			}
			
		}
		
		list.removeIf(s -> s.trim().contentEquals("ParametroClientAbstract.testParametroSimulacao = testParametroSimulacao;"));
		list.replaceTexto(", @HeaderParam(\"test-parametro-simulacao\") Integer testParametroSimulacao", "");
		
		list.juntarComAProximaSe(s -> s.contentEquals("\t@Inject"), " ");

		String s = list.toString("");
		
		String sn = file.getSimpleNameWithoutExtension();
		
		if (!sn.contentEquals("DataHora") && !sn.contentEquals("Hora")) {
			if (s.contains("Calendar.getInstance()")) {
				throw new RuntimeException("Somente a DataHora pode ter um Calendar.getInstance() : " + sn);
			}
		}

		if (s.contains("new Date()")) {
			throw new RuntimeException("Não utilize 'new Date()' : " + sn);
		}
		
//		if (s.contains(".contentEquals(") && !sn.contentEquals("StringCompare")) {
//			throw new RuntimeException("Para comparar Strings utilize StringCompare.eq() : " + sn);
//		}
		
		list.juntarFimComComecos("{", "}", "");
		
		list.save();
	}
	
}
