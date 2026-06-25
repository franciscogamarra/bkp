package br.gerar;

public class AbaSistemaProducao {

	public static void exec() {
		Aba aba = new Aba("sistemaProducao");
		aba.newSelect("produtoConsorciado").cols(12);
		aba.newSelect("tipoAgropecuaria");
		aba.newSelect("tipoIntegracao");
		aba.newSelect("graoSemente").title("Grão/Semente");
		aba.newSelect("tipoIrrigacao");
		aba.newSelect("tipoCultivo");
		aba.newSelect("cicloProducao");
		aba.newSelect("tipoClima");
		aba.newSelect("outrasPraticasDeManejo");
		aba.gerar();
		
	}
	
}
