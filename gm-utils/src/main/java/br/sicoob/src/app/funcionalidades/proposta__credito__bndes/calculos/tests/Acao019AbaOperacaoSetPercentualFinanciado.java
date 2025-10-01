package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.PcbCalculo;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.PropostaCreditoBndesComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao019AbaOperacaoSetPercentualFinanciado extends PcbAcao {

	private double valor;

	public Acao019AbaOperacaoSetPercentualFinanciado(double valor) {
		super("Acao019AbaOperacaoSetPercentualFinanciado: " + valor);
		this.valor = valor;
	}
	
	@Override
	public void acao() {
		PropostaCreditoBndesComponent.instance.formCadastroProposta.get("percFinanciada").setValue(Num.fromNumber(2, valor).toDouble());
		PcbCalculo.get().atualizaPercentuaisBaseadoNoFinanciado();
	}
	
	@Override
	public int tempoDeExecucao() {
		return 1500;
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao019AbaOperacaoSetPercentualFinanciado.class);
	}
	
}