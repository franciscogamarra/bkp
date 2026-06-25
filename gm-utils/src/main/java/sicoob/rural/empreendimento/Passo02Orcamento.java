package sicoob.rural.empreendimento;

public class Passo02Orcamento extends CopiarPasta {

	@Override
	protected String getOrigem() {
		return "app/funcionalidades/pesquisar_empreendimento/components/cadastro-empreendimento-aba-imovel";
	}

	@Override
	protected String replace(String s) {
		s = s.replace("imovel", "orcamento");
		s = s.replace("Imovel", "Orcamento");
		s = s.replace("imóvel", "orçamento");
		s = s.replace("Imóvel", "Orçamento");
		return s;
	}
	
//	src\app\funcionalidades\pesquisar_empreendimento\components\cadastro-empreendimento-aba-imovel
//	src\app\funcionalidades\pesquisar_empreendimento\components\cadastro-empreendimento-aba-sistema-producao
//	src\app\funcionalidades\pesquisar_empreendimento\components\cadastro-empreendimento-aba-informacoes-ambientais
//	src\app\funcionalidades\pesquisar_empreendimento\components\cadastro-empreendimento-aba-beneficiarios
//	src\app\funcionalidades\pesquisar_empreendimento\components\cadastro-empreendimento-aba-zarc

	@Override
	protected boolean excluirOrigem() {
		return false;
	}
	
	public static void main(String[] args) {
		new Passo02Orcamento();
	}

}
