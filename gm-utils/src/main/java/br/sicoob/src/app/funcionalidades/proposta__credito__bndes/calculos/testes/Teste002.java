package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.testes;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Programas;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;

//23025760s
public class Teste002 extends BaseTest {

	public static void main(String[] args) {
		
		addTipo1();
		addItem(3, 1817810.00);
		addItem(2, 1343155.00);
		addItem(2,  332385.00);
		addItem(1,  460040.00);
		addItem(1,  142000.00);
		addItem(1,  167000.00);
		addItem(1,  482000.00);
		addItem(1, 1200000.00);
		addItem(1, 3235450.00);
		
		addTipo9();
		addItem(1,  189925.40);
		
		addTipo32();
		addItem(1,  237408.00);	
		
		addTipo34();
		addItem(1,  190875.00);
		
		Programa programa = Programas.p2424;
		calculo.programa.set(programa);
		
//		PropostaCreditoBndesCalculo.instance.setValorTotalInvestimento(5_000_000.00);
//		PcbCalculo.instance.setValorTotalInvestimento(15_109_208.40);
		
//		exec(9.99);
		exec(10);
		
//		PcbCalculo.print();
		
	}
	
}
