package sicoob.rural.empreendimento;

public class Passo01Imovel extends CopiarPasta {

	@Override
	protected String getOrigem() {
		return "app/funcionalidades/empreendimento/components/cadastrar-proposta/imovel";
	}

	@Override
	protected String replace(String s) {
		s = s.replace("imovel", "<sn>");
		s = s.replace("Imovel", "<Sn>");
		s = s.replace("<sn>", "cadastro-empreendimento-aba-imovel");
		s = s.replace("<Sn>", "CadastroEmpreendimentoAbaImovel");
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
		new Passo01Imovel();
	}

}
