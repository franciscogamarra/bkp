package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import js.annotations.NaoConverter;

@NaoConverter @ImportStatic
@From("@app/components/adiciona-equipamentos/adiciona-equipamentos.component")
public class AdicionaEquipamentosComponent extends Formulario {
	
	public static final AdicionaEquipamentosComponent instance = new AdicionaEquipamentosComponent();
	
	private AdicionaEquipamentosComponent() {
		form.addControl("id", new FormControl());
		form.addControl("numCpfCnpjFabricante", new FormControl());
		form.addControl("codProdutoBNDES", new FormControl());
		form.addControl("bolItemEventoProducao", new FormControl());
		form.addControl("qtdItemInvestimento", new FormControl());
		form.addControl("valorTotalItem", new FormControl());
		form.addControl("valorUnitarioItem", new FormControl());
		form.addControl("valorTotalFinanciadoItem", new FormControl());
		form.addControl("valorUnitarioFinanciadoItem", new FormControl());
		form.addControl("descInformacaoAdicional", new FormControl());
		form.addControl("propostaBNDES", new FormControl());
		form.addControl("numSeqItemInvestimento", new FormControl());
		form.addControl("bolItemUsado", new FormControl());
		form.addControl("bolItemImportado", new FormControl());
	}

	@Override
	protected void setTotais() {

//	    const equipamento: ItemInvestimento = this.form.value;
//	    if(this.getEquipamentoCredenciado()) {
//	      this.preencheFabricanteEquipamento(equipamento);
//	    }
//	    this.actionBarRef.close(equipamento);
		
	}
	
}
