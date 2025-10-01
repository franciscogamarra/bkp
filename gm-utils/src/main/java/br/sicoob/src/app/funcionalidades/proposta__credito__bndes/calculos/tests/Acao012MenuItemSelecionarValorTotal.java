package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao012MenuItemSelecionarValorTotal extends PcbAcao {

	private double valor;

	public Acao012MenuItemSelecionarValorTotal(double valor) {
		super("Acao012bMenuItemSelecionarValorTotal: " + valor);
		this.valor = valor;
	}
	
	@Override
	public void acao() {
		AdicionaItensForm.setValorTotalItem(valor);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao012MenuItemSelecionarValorTotal.class);
	}
	
}