package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.PropostaCreditoBndesForm;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Produto;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao001SelecionarProduto extends PcbAcao {

	private Produto produto;

	public Acao001SelecionarProduto(Produto produto) {
		super("Acao001SelecionarProduto " + produto.id);
		this.produto = produto;
	}

	@Override
	public void acao() {
		PropostaCreditoBndesForm.setProduto(produto);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao001SelecionarProduto.class);
	}

}
