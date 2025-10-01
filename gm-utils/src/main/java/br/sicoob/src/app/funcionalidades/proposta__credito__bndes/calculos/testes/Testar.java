package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.testes;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Programas;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;
import gm.utils.comum.Aleatorio;
import gm.utils.comum.SystemPrint;
import gm.utils.number.Numeric2;

public class Testar extends BaseTest {
	
	public static void main(String[] args) {
		
//	    addItem(10, 30159904.37);
//	    addItem(2, 12871428.93);
//	    exec();
		
		Programa programa = Programas.p2424;
		calculo.programa.set(programa);
		
		addTipo1();
	    addItem(1,  1000);
	    addItem(1,  1000);
	    addItem(2, 13000);
	    addItem(1,  3500);
	    exec(0.03);
	    exec();
	    
		addTipo1();
	    addItem(1, 237408);
	    exec();
		
		addTipo1();
	    addItem(1, 10000);
	    addItem(2,  8000);
	    addItem(1,  5500);
	    exec();

		addTipo1();
	    addItem(3, 1_817_100);
	    addItem(2, 1_343_155);
	    addItem(2,   332_385);
	    addItem(1,   460_040);
	    addItem(1,   142_000);
	    addItem(1,   167_000);
	    addItem(1,   482_000);
	    addItem(1, 1_200_000);
	    addItem(1, 3_235_450);
	    addItem(1,   189_925.40);
	    addItem(1,   237_408);
	    exec();
	    
	    for (int tentativa = 0; tentativa < 500; tentativa++) {
	    	
	    	SystemPrint.ln("tentativa: " + tentativa);

		    for (int i = 0; i < 40; i++) {
		    	Numeric2 valor = Aleatorio.getNumeric2(0.01, 24_999.99);
		    	addTipo1();
		    	addItem(Aleatorio.get(1, 40), valor.toDouble());
			}

		    exec();
	    	
	    }
	    
	}
	
}
