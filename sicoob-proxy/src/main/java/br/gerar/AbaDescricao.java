package br.gerar;

public class AbaDescricao {

	public static void exec() {
		Aba aba = new Aba("descricao");
		aba.newString("descricao", 1000).cols(12).rows(10);
		aba.gerar();
	}
	
}
