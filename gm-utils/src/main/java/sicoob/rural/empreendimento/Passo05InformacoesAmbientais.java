package sicoob.rural.empreendimento;

public class Passo05InformacoesAmbientais extends CopiarPasta {

	@Override
	protected String getOrigem() {
		return "app/funcionalidades/empreendimento/components/cadastrar-proposta/informacoes-ambientais";
	}

	@Override
	protected String replace(String s) {
		
		s = s.replace("informacoes-ambientais", "<tag>");
		s = s.replace("InformacoesAmbientais", "<Sn>");
		s = s.replace("informacoesAmbientais", "<sn>");
		
		s = s.replace("<tag>", "cadastro-empreendimento-aba-informacoes-ambientais");
		s = s.replace("<Sn>", "AbaInformacoesAmbientais");
		s = s.replace("<sn>", "informacoesAmbientais");
		
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
		new Passo05InformacoesAmbientais();
	}

}
