package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.CalculadoraOperacoesComponent;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class Acao022CalculadoraSetValorRecursosProprios extends PcbAcao {

	private final double value;

	public Acao022CalculadoraSetValorRecursosProprios(double value) {
		super("Acao022CalculadoraSetValorRecursosProprios");
		this.value = value;
	}
	
	@Override
	public void acao() {
		CalculadoraOperacoesComponent.instance.formCalculadora.get("valorRecursosProprios").setValue(value);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Acao022CalculadoraSetValorRecursosProprios.class);
	}
	
}