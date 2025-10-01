package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import js.annotations.NaoConverter;

@NaoConverter @ImportStatic
@From("@app/funcionalidades/proposta-credito-bndes/components/proposta-credito-bndes/proposta-credito-bndes.component")
public class PropostaCreditoBndesComponent {

	public static final PropostaCreditoBndesComponent instance = new PropostaCreditoBndesComponent();
	public FormGroup formCadastroProposta = new FormGroup(null);
	
	private PropostaCreditoBndesComponent() {
		formCadastroProposta.addControl("produto", new FormControl());
		formCadastroProposta.addControl("programa", new FormControl());
		formCadastroProposta.addControl("linhaBndes", new FormControl());
		formCadastroProposta.addControl("percFinanciada", new FormControl());
		formCadastroProposta.addControl("percRecursoProprio", new FormControl());
		formCadastroProposta.addControl("percTotalInvestimento", new FormControl());
		formCadastroProposta.addControl("valorTotalInvestimento", new FormControl());
		formCadastroProposta.addControl("valorFinanciado", new FormControl());
		formCadastroProposta.addControl("valorRecursosProprios", new FormControl());
		formCadastroProposta.addControl("itensInvestimento", new FormControl());
	}

	public void refresh() {
		// TODO Auto-generated method stub
		
	}
	
	
}
