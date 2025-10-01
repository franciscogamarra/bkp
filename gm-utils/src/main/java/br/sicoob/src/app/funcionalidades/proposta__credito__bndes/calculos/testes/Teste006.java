package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.testes;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Programas;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;
import br.sicoob.src.app.shared.forms.StateFormControl;
import gm.utils.comum.SystemPrint;
import gm.utils.exception.UException;
import gm.utils.string.ListString;

public class Teste006 extends BaseTest {
	
	private static class esperado {
		static double total;
		static double perFin;
		static double perRec;
		static double vPerFin;
		static double vPerRec;
		static boolean percentualMinimoDeRecursosPropriosAceitavelInconsistente;
	}
	
	private static ListString erros = new ListString();
	
	private static void assertt(String s, boolean atual, boolean expected) {
		if (atual != expected) {
			erros.add(s + " = Esperado: " + expected + " / Atual: " + atual);
		}
	}
	
	private static void assertt(String s, StateFormControl comp, double expected) {
		Num atual = comp.getMoney();
		Num esperado = Num.fromNumber(2, expected);
		if (!atual.igual(esperado)) {
			erros.add(s + " = Esperado: " + esperado + " / Atual: " + atual);
		}
	} 
	
	private static void assertt() {
		assertt("valorTotalInvestimentoOut", calculo.valorTotalInvestimentoOut, esperado.total);
		assertt("percentualFinanciado", calculo.percentualFinanciado, esperado.perFin);
		assertt("percentualRecursoProprio", calculo.percentualRecursoProprio, esperado.perRec);
		assertt("valorFinanciadoOut", calculo.valorFinanciadoOut, esperado.vPerFin);
		assertt("valorRecursosPropriosOut", calculo.valorRecursosPropriosOut, esperado.vPerRec);
		assertt("percentualMinimoDeRecursosPropriosAceitavelInconsistente", calculo.percentualMinimoDeRecursosPropriosAceitavelInconsistente, esperado.percentualMinimoDeRecursosPropriosAceitavelInconsistente);
		
		if (!erros.isEmpty()) {
			erros.printErros();
			SystemPrint.ln();
			throw UException.runtime("falhou");
		}
		
	}

	public static void main(String[] args) {

		Programa programa = Programas.p2424;
		calculo.programa.set(programa);
			
		addTipo1();
		
		addItem(1, 3000);
		
		esperado.total = 3000;
		esperado.perFin = 100;
		esperado.perRec = 0;
		esperado.vPerFin = 3000;
		esperado.vPerRec = 0;
		esperado.percentualMinimoDeRecursosPropriosAceitavelInconsistente = false;
		assertt();

		addItem(1, 2000);
		esperado.total = 5000;
		esperado.perFin = 100;
		esperado.perRec = 0;
		esperado.vPerFin = 5000;
		esperado.vPerRec = 0;
		assertt();
		
		addTipo34();
		assertt();

		addItem(1, 1000);
		esperado.total = 6000;
		esperado.perFin = 100;
		esperado.perRec = 0;
		esperado.vPerFin = 6000;
		esperado.vPerRec = 0;
		esperado.percentualMinimoDeRecursosPropriosAceitavelInconsistente = true;
		assertt();
		
		calculo.setPercRecursoProprioForm(calculo.percentualMinimoDeRecursosPropriosAceitavel);

		esperado.perFin = 83.33;
		esperado.perRec = 16.67;
		esperado.vPerFin = 4_999.80;
		esperado.vPerRec = 1_000.20;
		esperado.percentualMinimoDeRecursosPropriosAceitavelInconsistente = false;
		calculo.erros.print();
		assertt();
		
		
		
	}
	
	
}
