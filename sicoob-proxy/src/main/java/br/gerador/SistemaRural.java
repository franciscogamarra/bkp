package br.gerador;

import br.definicoes.Sistema;
import br.gerador.imovel.EmpreendimentoRuralImovel;
import br.gerador.imovel.Imovel;
import br.gerador.support.BuscaPessoa;
import br.gerador.support.PessoaSimples;
import br.gerar.Validar;
import br.gerar.ts.TipoTs;
import br.support.SuperObject;

public class SistemaRural extends SuperObject {

	public static void main(String[] args) {
		
		Sistema sistema = new Sistema(SistemaRural.class);
		sistema.back.gerar = false;
		
		sistema.front.basePathOndeOsArquivosSaoGerados = "src/app/model/";
//		sistema.front.caminho = "c:/dev/rural/cre-concessao-rural-web/src/app/funcionalidades/cadastro-imovel/incluir-gleba/incluir-gleba.component.html";
		sistema.front.caminho = "c:/dev/rural/cre-concessao-rural-web";

		//no sicoob geralmente tem o mesmo nome
		sistema.setToNomeBanco(s -> s);
		
		TipoTs.OURTYPES = "@app/utils/OurTypes";
		TipoTs.PKG_SUPPORT = "@app/utils";
		
		PessoaSimples.exec(sistema);
		BuscaPessoa.exec(sistema);
		Imovel.exec(sistema);
		EmpreendimentoRuralImovel.exec(sistema);
		
		Validar.exec(sistema);
		GerarModels.exec(sistema);
		CadastroHtml.exec(sistema);
		
	}
	
}
