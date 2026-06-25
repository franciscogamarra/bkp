package sicoob.rural.empreendimento;

public class Passo07Zarc extends CopiarPasta {

	@Override
	protected String getOrigem() {
		return "app/funcionalidades/empreendimento/components/cadastrar-proposta/zarc";
	}

	@Override
	protected String replace(String s) {
		
		s = s.replace("zarc", "<tag>");
		s = s.replace("Zarc", "<Sn>");
		s = s.replace("zarc", "<sn>");
		
		s = s.replace("<sn>", "zarc");
		s = s.replace("<Sn>", "CadastroEmpreendimentoAbaZarc");
		s = s.replace("<tag>", "cadastro-empreendimento-aba-zarc");
		
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
		new Passo07Zarc();
	}

}
