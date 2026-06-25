package sicoob.rural.empreendimento;

public class Passo06Beneficiarios extends CopiarPasta {

	@Override
	protected String getOrigem() {
		return "app/funcionalidades/empreendimento/components/cadastrar-proposta/beneficiarios";
	}

	@Override
	protected String replace(String s) {
		
		s = s.replace("beneficiarios", "<tag>");
		s = s.replace("Beneficiarios", "<Sn>");
		s = s.replace("beneficiarios", "<sn>");
		
		s = s.replace("<tag>", "cadastro-empreendimento-aba-beneficiarios");
		s = s.replace("<Sn>", "CadastroEmpreendimentoAbaBeneficiarios");
		s = s.replace("<sn>", "beneficiarios");
		
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
		new Passo06Beneficiarios();
	}

}
