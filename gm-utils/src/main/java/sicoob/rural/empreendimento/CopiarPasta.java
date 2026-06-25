package sicoob.rural.empreendimento;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;

public abstract class CopiarPasta {

	protected abstract String getOrigem();
	protected abstract String replace(String s);
	protected abstract boolean excluirOrigem();
	
	public CopiarPasta() {
		GFile raiz = GFile.get("c:/dev/rural/cre-concessao-rural-web/src/" + getOrigem());
		Lst<GFile> files = raiz.getAllFiles();
		for (GFile inv : files) {
			GFile emp = GFile.get(replace(inv.toString().replace("\\", "/")));
			ListString list = inv.load();
			list.replaceEach(s -> replace(s));
			list.save(emp);
		}
		if (excluirOrigem()) {
			raiz.delete();
		}
	}

	public static void main(String[] args) {
		Passo00Empreendimento.main(args);
//		Passo02Orcamento.main(args);
//		Passo03Imovel.main(args);
//		Passo04SistemaProducao.main(args);
//		Passo05InformacoesAmbientais.main(args);
//		Passo06Beneficiarios.main(args);
//		Passo07Zarc.main(args);
//		Passo08Seguro.main(args);
//		Passo09Descricao.main(args);
	}
	
}
