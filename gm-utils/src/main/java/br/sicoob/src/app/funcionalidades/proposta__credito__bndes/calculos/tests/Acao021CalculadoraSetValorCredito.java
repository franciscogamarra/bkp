package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.CalculadoraOperacoesComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao021CalculadoraSetValorCredito extends PcbAcao {

	private final double value;

	public Acao021CalculadoraSetValorCredito(double value) {
		super("Acao021CalculadoraSetValorCredito");
		this.value = value;
	}
	
	@Override
	public void acao() {
		CalculadoraOperacoesComponent.instance.formCalculadora.get("valorCredito").setValue(value);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao021CalculadoraSetValorCredito.class);
	}
	
}