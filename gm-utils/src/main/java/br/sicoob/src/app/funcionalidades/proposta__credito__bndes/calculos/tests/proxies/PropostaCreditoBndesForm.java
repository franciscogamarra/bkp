package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.PropostaCreditoBndesComponent;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.LinhaBndes;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Produto;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;
import gm.languages.ts.angular.forms.FormGroup;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;

public class PropostaCreditoBndesForm {

	private static FormGroup getForm() {
		return PropostaCreditoBndesComponent.instance.formCadastroProposta;
	}
	
	public static void setProduto(Produto produto) {
		getForm().get("produto").setValue(produto.id);
	}
	
	public static void setLinhaBndes(LinhaBndes linhaBndes) {
		getForm().get("linhaBndes").setValue(linhaBndes.id);		
	}

	public static void setPrograma(Programa programa) {
		getForm().get("programa").setValue(programa);		
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PropostaCreditoBndesForm.class);
	}

}