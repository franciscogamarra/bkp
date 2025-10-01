package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import js.annotations.NaoConverter;

@NaoConverter @ImportStatic
@From("@app/components/adiciona-itens-investimento/adiciona-itens-investimento.component")
public class AdicionaItensInvestimentoComponent extends Formulario {
	
	public static final AdicionaItensInvestimentoComponent instance = new AdicionaItensInvestimentoComponent();
	public final FormGroup form = new FormGroup(null);
	
	private AdicionaItensInvestimentoComponent() {
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
		form.addControl("bolItemNaoFinanciavel", new FormControl());
	}

	@Override
	protected void setTotais() {
		form.get("valorUnitarioItem").setValue(form.get("valorTotalItem").value);
		form.get("valorUnitarioFinanciadoItem").setValue(form.get("valorTotalFinanciadoItem").value);
	}
	
}
