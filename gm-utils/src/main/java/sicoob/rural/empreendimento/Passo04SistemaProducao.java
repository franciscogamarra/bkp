package sicoob.rural.empreendimento;

public class Passo04SistemaProducao extends CopiarPasta {

	@Override
	protected String getOrigem() {
		return "app/funcionalidades/empreendimento/components/cadastrar-proposta/sistema-producao";
	}

	@Override
	protected String replace(String s) {
		
		s = s.replace("sistema-producao", "<tag>");
		s = s.replace("SistemaProducao", "<Sn>");
		s = s.replace("sistemaProducao", "<sn>");
		
		s = s.replace("<tag>", "cadastro-empreendimento-aba-sistema-producao");
		s = s.replace("<Sn>", "CadastroEmpreendimentoAbaSistemaProducao");
		s = s.replace("<sn>", "sistemaProducao");
		
		s = s.replace("/empreendimento/", "/pesquisar_empreendimento/");
		s = s.replace("/cadastrar-proposta/", "/");
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
		new Passo04SistemaProducao();
	}

}
