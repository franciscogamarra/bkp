package br.sicoob;

import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringTrim;

public class TratarClasse {

	public static void main(String[] args) {
		exec("C:\\dev\\projects\\cre-concessao-bndes\\Backoffice\\cre-concessao-bndes-ejb\\src\\main\\java\\br\\com\\sicoob\\sisbr\\bndes\\negocio\\servicos\\ejb\\interfaces\\IPropostaCreditoServico.java");
		exec("C:\\dev\\projects\\cre-concessao-bndes\\Backoffice\\cre-concessao-bndes-ejb\\src\\main\\java\\br\\com\\sicoob\\sisbr\\bndes\\negocio\\servicos\\ejb\\SolicitacoesAPAServicoEJB.java");
		exec("C:\\dev\\projects\\cre-concessao-bndes\\Backoffice\\cre-concessao-bndes-ejb\\src\\main\\java\\br\\com\\sicoob\\sisbr\\bndes\\negocio\\servicos\\ejb\\PropostaCreditoServicoEJB.java");
		exec("C:\\dev\\projects\\cre-concessao-bndes\\Backoffice\\cre-concessao-bndes-ejb\\src\\main\\java\\br\\com\\sicoob\\sisbr\\bndes\\dto\\PropostaCreditoDTO.java");
	}
	
	public static void exec(String fileName) {

		ListString list = GFile.get(fileName).load();
		list.replaceTexto("    ", "\t");
		list.removeIfTrimEquals("/** */");
		
		list.replaceEach(s -> {
			if (StringEmpty.is(s)) {
				return s;
			} else {
				return StringTrim.right(s);
			}
		});
		
		for (int i = 0; i < list.size(); i++) {
			
			if (list.get(i).trim().contentEquals("/**")) {
				if (list.get(i+1).trim().contentEquals("*")) {
					if (list.get(i+2).trim().contentEquals("*/")) {
						list.remove(i);
						list.remove(i);
						list.remove(i);
					}
				}
			}
			
		}
		
		list.removeDoubleWhites();
		list.save();
		
	}
	
}
