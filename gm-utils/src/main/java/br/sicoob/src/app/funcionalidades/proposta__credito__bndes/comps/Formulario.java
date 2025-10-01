package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.ItemInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;
import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.annotacoes.Any;
import js.annotations.NaoConverter;
import src.commom.utils.object.Null;

@NaoConverter @Any
public abstract class Formulario {

	public final FormGroup form = new FormGroup(null);
	
	public final void onSubmit() {
		
		setTotais();
		
		ItemInvestimento item = ItemInvestimento.json()
		.id(getValue("id"))
		.qtdItemInvestimento(getValue("qtdItemInvestimento"))
		.descInformacaoAdicional(getValue("descInformacaoAdicional"))
		.descricaoEquipamento(getValue("descricaoEquipamento"))
		.descricaoFabricante(getValue("descricaoFabricante"))
		.valorUnitarioItem(getValue("valorUnitarioItem"))
		.valorUnitarioFinanciadoItem(getValue("valorUnitarioFinanciadoItem"))
		.valorTotalFinanciadoItem(getValue("valorTotalFinanciadoItem"))
		.valorTotalItem(getValue("valorTotalItem"))
		;
		
		AdicionaItensForm.tipo.value.itensInvestimento.push(item);
		
	}
	
	@SuppressWarnings("unchecked")
	private <T> T getValue(String key) {
		FormControl formControl = form.get(key);
		if (Null.is(formControl)) {
			return null;
		} else {
			return (T) formControl.value;
		}
	}
	
	protected abstract void setTotais();
	
}
