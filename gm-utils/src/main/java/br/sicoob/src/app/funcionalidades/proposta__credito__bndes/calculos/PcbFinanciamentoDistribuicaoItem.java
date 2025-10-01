package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Error;

public class PcbFinanciamentoDistribuicaoItem {

	public ItemInvestimentoWrapper item;

	public final Num valorUnitario = Num.novo(2);
	public final Num valorTotal = Num.novo(2);
	
	public final Num valorUnitarioFinanciadoNaoAjustado = Num.novo(2);
	public final Num valorTotalFinaciadoNaoAjustado = Num.novo(2);
	
	public final Num valorUnitarioFinanciadoAjustado = Num.novo(2);
	public final Num valorTotalFinaciadoAjustado = Num.novo(2);

	public boolean arrendondariaParaCimaNaUnidade;
	public boolean arrendondariaParaCimaNoTotal;

	public Num quantoArredondouNaUnidade;
	public Num quantoArredondouNoTotal;

	public PcbFinanciamentoDistribuicaoItem(ItemInvestimentoWrapper item) {
		this.item = item;
		setValorUnitario(item.getUnitario());
	}
	
	private void setValorUnitario(Num value) {
		value.assertCasas(2);
		this.valorUnitario.set(value);
	}
	
	public void setValorTotal(Num value) {
		value.assertCasas(2);
		this.valorTotal.set(value);
	}
	
	public void setValorUnitarioFinanciadoNaoAjustado(Num value) {
		value.assertCasas(2);
		this.valorUnitarioFinanciadoNaoAjustado.set(value);
	}
	
	public void setValorTotalFinaciadoNaoAjustado(Num value) {
		value.assertCasas(2);
		this.valorTotalFinaciadoNaoAjustado.set(value);
		if (valorTotalFinaciadoNaoAjustado.maior(valorTotal)) {
			throw new Error("valorTotalFinaciadoAjustado "+valorTotalFinaciadoNaoAjustado+" > valorTotal " + valorTotal);
		}
	}

	public void setValorUnitarioFinanciadoAjustado(Num value) {
		value.assertCasas(2);
		this.valorUnitarioFinanciadoAjustado.set(value);
	}

	public void setValorTotalFinaciadoAjustado(Num value) {
		value.assertCasas(2);
		this.valorTotalFinaciadoAjustado.set(value);
		if (valorTotalFinaciadoAjustado.maior(valorTotal)) {
			throw new Error("valorTotalFinaciadoAjustado "+valorTotalFinaciadoAjustado+" > valorTotal " + valorTotal);
		}
	}
	
	public void aplica() {
		this.item.setUnitarioFinanciado(this.valorUnitarioFinanciadoAjustado);
		this.item.setTotalFinanciado(this.valorTotalFinaciadoAjustado);
	}
	
	public void valida() {
		
		if (valorUnitarioFinanciadoAjustado.maior(valorUnitario)) {
			throw new Error("i.valorUnitarioFinanciadoAjustado "+valorUnitarioFinanciadoAjustado+" > i.valorUnitario " + valorUnitario);
		}

		if (valorTotalFinaciadoAjustado.maior(valorTotal)) {
			throw new Error("i.valorTotalFinaciadoAjustado "+valorTotalFinaciadoAjustado+" > i.valorTotal " + valorTotal + " / ");
		}
		
	}
	
	public Num getQuantidade() {
		return item.getQuantidade();
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbFinanciamentoDistribuicaoItem.class);
	}

}
