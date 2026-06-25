package br.gerar;

import java.util.HashMap;
import java.util.Map;

import br.support.comum.LstString;
import br.support.dev.GFile;
import br.support.strings.StringTrim;

public class Main {
	
	public static final String components = "C:/dev/rural/cre-concessao-rural-web/src/app/funcionalidades/pesquisar_empreendimento/components";

	public static void main(String[] args) {
		AbaSeguro.exec();
		AbaSistemaProducao.exec();
		AbaDescricao.exec();
		AbaZarc.exec();
		AbaOrcamento.exec();
		ImovelEdit.exec();
		CadastroEmpreendimentoController.exec();
		CadastroEmpreendimentoService.exec();
	}

	private static final Map<Integer, GFile> loads = new HashMap<>();
	public static LstString load(String fileName) {
		GFile file = GFile.get(Main.components + fileName);
		LstString list = file.load();
		list.removeDoubleWhites();
		list.replaceEach(s -> StringTrim.right(s));
		loads.put(list.getInstancia(), file);
		return list;
	}
	
	public static void save(LstString list) {
		list.removeDoubleWhites();
		list.replaceEach(s -> StringTrim.right(s));
		list.save(loads.get(list.getInstancia()));
	}
	
}
