package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.testes;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.PcbCalculo;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Programas;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;

public class Teste004 extends BaseTest {

	public static void main(String[] args) {
		
		addTipo1();
		addItem(1, 40548.09);
		
		Programa programa = Programas.p2424;
		calculo.programa.set(programa);
		
//		PropostaCreditoBndesCalculo.instance.setValorTotalInvestimento(5_000_000.00);
//		PcbCalculo.instance.setValorTotalInvestimento(15_109_208.40);
		
		exec(100);
		
		PcbCalculo.print();
		
	}
	
}
