package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import js.annotations.NaoConverter;

@NaoConverter @ImportStatic
@From("@app/components/adiciona-itens-investimento-com-quantidade/adiciona-itens-investimento-com-quantidade.component")
public class AdicionaItensInvestimentoComQuantidadeComponent extends Formulario {
	
	public static final AdicionaItensInvestimentoComQuantidadeComponent instance = new AdicionaItensInvestimentoComQuantidadeComponent();
	public final FormGroup form = new FormGroup(null);
	
	private AdicionaItensInvestimentoComQuantidadeComponent() {
		form.addControl("id", new FormControl());
		form.addControl("propostaBNDES", new FormControl());
		form.addControl("qtdItemInvestimento", new FormControl());
		form.addControl("valorUnitarioItem", new FormControl());
		form.addControl("valorTotalItem", new FormControl());
		form.addControl("valorUnitarioFinanciadoItem", new FormControl());
		form.addControl("valorTotalFinanciadoItem", new FormControl());
		form.addControl("numSeqItemInvestimento", new FormControl());
		form.addControl("bolItemEventoProducao", new FormControl());
		form.addControl("bolItemUsado", new FormControl());
		form.addControl("bolItemImportado", new FormControl());
	}

	@Override
	protected void setTotais() {
		Num valorUnitarioItem = Num.fromNumberObj(2, form.get("valorUnitarioItem").value);
		Num qtdItemInvestimento = Num.fromNumberObj(2, form.get("qtdItemInvestimento").value);
		Num valorTotaiItem = valorUnitarioItem.vezes(qtdItemInvestimento).arredondarParaBaixo(2);
		form.get("valorTotalItem").setValue(valorTotaiItem.toDouble());
	}
	
}
