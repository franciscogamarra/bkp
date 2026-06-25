package br.gerar;

public class AbaZarc {

	public static void exec() {
		Aba aba = new Aba("zarc");
		aba.newSelect("tipoManejo").cols(4).title("Tipo de Manejo");
		aba.newSelect("tipoSolo").cols(4).title("Tipo do Solo");
		aba.newSelect("cicloCultivar").cols(4).title("Ciclo de Cultivar");
		aba.gerar();
		
	}
	
}
