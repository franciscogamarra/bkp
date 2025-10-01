package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class TestRun {

	public static void exec() {
		PcbTest003.exec();
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(TestRun.class);
	}
	
}
