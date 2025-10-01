package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.PropostaCreditoBndes;
import gm.languages.ts.javaToTs.exemplo.services.AlertService;
import gm.languages.ts.javaToTs.exemplo.services.Mensagem;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;
import src.commom.utils.object.Null;

public class PropostaBndesUtils {
	
	public static boolean valida(PropostaCreditoBndes proposta, AlertService alertService) {
		
		Array<String> msgs = getInvalidMessages(proposta);
		
		if (msgs.length) {
			msgs.forEach(s -> alertService.open(Mensagem.json().message(s).duration(5000).color("danger").position("is-top")));
			return false;
		} else {
			return true;
		}
		
	}
	
	public static Array<String> getInvalidMessages(PropostaCreditoBndes proposta) {
		
		Array<String> msgs = new Array<>();
		
		if (programaExigeItensInvestimento(proposta)) {
			if (Null.is(proposta.itensInvestimento) || !proposta.itensInvestimento.length ) {
				msgs.push("Os itens de investimento devem ser preenchidos pois o programa exige isso.");
			}
		} else {
			proposta.itensInvestimento = null;
		}	
		
		return msgs;
		
	}

	public static boolean programaExigeItensInvestimento(PropostaCreditoBndes proposta) {
		if (Null.is(proposta) || Null.is(proposta.programa)) {
			return false;
		} else {
			return proposta.programa.bolExigeItemInvestimento;
		}
	}
	
	@IgnorarDaquiPraBaixo
	
	public static void main(String[] args) {
		SicoobTranspilar.exec(PropostaBndesUtils.class);
	}
	
}