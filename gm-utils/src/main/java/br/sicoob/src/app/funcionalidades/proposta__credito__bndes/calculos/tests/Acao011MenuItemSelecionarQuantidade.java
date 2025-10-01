package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;

public class Acao011MenuItemSelecionarQuantidade extends PcbAcao {

	private int qtd;

	public Acao011MenuItemSelecionarQuantidade(int qtd) {
		super("Acao011MenuItemSelecionarQuantidade: " + qtd);
		this.qtd = qtd;
	}

	@Override
	public void acao() {
		AdicionaItensForm.setQuantidade(qtd);
	}
	
}
