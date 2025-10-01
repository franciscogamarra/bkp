package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.testes;

import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.ItemInvestimento;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.PcbCalculo;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.TiposInvestimento;
import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.utils.comum.SystemPrint;
import gm.utils.number.Numeric2;
import js.array.Array;

public class BaseTest {

	private static final FormGroup formGroup = new FormGroup(null);
	static {
		formGroup.addControl("programa", new FormControl());
		formGroup.addControl("valorTotalInvestimento", new FormControl());
		formGroup.addControl("itensInvestimento", new FormControl());
		formGroup.addControl("valorFinanciado", new FormControl());
		formGroup.addControl("valorRecursosProprios", new FormControl());
		formGroup.addControl("percFinanciada", new FormControl());
		formGroup.addControl("percRecursoProprio", new FormControl());
		formGroup.addControl("percTotalInvestimento", new FormControl());
	}
	
	protected static TipoInvestimento tipo;
	protected static ItemInvestimento item = ItemInvestimento.json();
	public static final Array<TipoInvestimento> tiposInvestimento = new Array<>();
	public static final PcbCalculo calculo = PcbCalculo.get();

	private static void addTipo(int id) {
		tiposInvestimento.list.removeIf(i -> i.id == id);
		tipo = TiposInvestimento.findById(id);
		tiposInvestimento.push(tipo);
		calculo.atualizarTiposJava();
	}
	
	protected static void addTipo1() {
		addTipo(1);
	}
	
	protected static void addTipo8() {
		addTipo(8);
	}

	protected static void addTipo9() {
		addTipo(9);
	}
	
	protected static void addTipo32() {
		addTipo(32);
	}

	protected static void addTipo34() {
		addTipo(34);
	}
		
	protected static void addItem(int qtd, double valor) {
		
		if (tipo == null) {
			throw new NullPointerException();
		}
		
		item = ItemInvestimento.json().qtdItemInvestimento(qtd).valorUnitarioItem(valor);
		tipo.itensInvestimento.push(item);
		
		calculo.atualizarTiposJava();
		calculo.atualizaPercentuaisBaseadoNosTotais();
		
	}
	
	protected static void reset() {
		tiposInvestimento.list.clear();
		tipo = null;
	}
	
	protected static void print() {
		tiposInvestimento.forEach(tipo -> {
			SystemPrint.ln("addTipo"+tipo.id+"();");
			tipo.itensInvestimento.forEach((i, ix) -> {
				SystemPrint.ln("addItem("+i.qtdItemInvestimento+", "+new Numeric2(i.valorUnitarioItem).toStringPonto()+");//" + ix);
			});
		});
	}
	
	protected static void exec() {
		
		double per = 0;
		
		try {

			for (int i = 1; i <= 100; i++) {
				per = i / 100.;
				exec(per);
				if (!calculo.sucesso() && !calculo.erros.length) {
					SystemPrint.err(calculo.erros);
					throw new RuntimeException();
				}
			}
			
			reset();
			
		} catch (Exception e) {
			SystemPrint.ln("per: " + new Numeric2(per).toStringPonto());
			print();
			throw e;
		}
		
	}
	
	protected static void exec(double per) {
		Num num = Num.fromNumber(2, per);
		calculo.setPercFinanciada(num);
//		Print.ln(num);
//		Print.ln(calculo.erros);
		
		
	}
	
}
