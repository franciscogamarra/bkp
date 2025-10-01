package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.testes;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.PcbCalculo;

public class Teste003 extends BaseTest {

	public static void main(String[] args) {
		
		addTipo8();
		addItem(1, 1000);
		
//		PropostaCreditoBndesCalculo.instance.setValorTotalInvestimento(5_000_000.00);
//		PcbCalculo.instance.setValorTotalInvestimento(15_109_208.40);
		
		exec(50);
		
		PcbCalculo.print();
		
	}
	
}
