package sicoob.rural.empreendimento;

public class Passo00Empreendimento extends CopiarPasta {

	@Override
	protected String getOrigem() {
		return "app/funcionalidades/investimento";
	}

	@Override
	protected String replace(String s) {
		s = s.replace("investimento", "empreendimento");
		s = s.replace("Investimento", "Empreendimento");
		s = s.replace("redutores-taxa", "orcamento");
		s = s.replace("RedutoresTaxa", "Orcamento");
		s = s.replace("redutoresTaxa", "orcamento");
		s = s.replace("Redutores de Taxa", "Orçamento");
		s = s.replace("conformidadeAmbiental", "informacoesAmbientais");
		s = s.replace("Conformidade Ambiental", "Informações Ambientais");
		s = s.replace("conformidade-ambiental", "informacoes-ambientais");
		s = s.replace("ConformidadeAmbiental", "InformacoesAmbientais");
		s = s.replace("Conformidades", "Informacoes");
		s = s.replace("Conformidade", "Informacao");
		s = s.replace("conformidade", "informacao");
		s = s.replace("/funcionalidades/empreendimento", "/funcionalidades/pesquisar_empreendimento");
		return s;
	}

	public static void main(String[] args) {
		new Passo00Empreendimento();
	}

	@Override
	protected boolean excluirOrigem() {
		return false;
	}


}
