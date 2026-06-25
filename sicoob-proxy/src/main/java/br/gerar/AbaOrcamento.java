package br.gerar;

public class AbaOrcamento {

	public static void exec() {
		Aba aba = new Aba("orcamento");
		aba.newSelect("tecnologia").cols(4);
		aba.newNumber("produtividade").cols(4).title("Produtividade (Tonelada / Hectare)");
		aba.newPercentual("percentualFinanciado").cols(4);
		
		aba.newMoney("totalExploracao").cols(4).container(null)
		.readOnly().casasInteiras(15).title(null).showTextoErroFalse().bold();
		
		aba.gerar();
	}
	
}
