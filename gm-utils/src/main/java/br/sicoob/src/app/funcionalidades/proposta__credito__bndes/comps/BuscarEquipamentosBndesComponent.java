package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.EquipamentoLegado;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.FabricanteLegado;
import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import js.annotations.NaoConverter;

@NaoConverter @ImportStatic
@From("@app/components/buscar-equipamentos-bndes/buscar-equipamentos-bndes.component")
public class BuscarEquipamentosBndesComponent {
	
	public static final BuscarEquipamentosBndesComponent instance = new BuscarEquipamentosBndesComponent();
	
	public FormGroup form = new FormGroup(null);
	
	private BuscarEquipamentosBndesComponent() {
		form.addControl("codigoFabricante", new FormControl());
		form.addControl("codigoEquipamento", new FormControl());
	}

	public void onChangeCodigoFabricante(String codigo) {}

	public void aplicaEquipamentosAlteracao() {
		
	}

	public void onSelectFabricante(FabricanteLegado fabricante) {
		
	}

	public void onSelectEquipamento(EquipamentoLegado item1) {
		
	}
	
}
