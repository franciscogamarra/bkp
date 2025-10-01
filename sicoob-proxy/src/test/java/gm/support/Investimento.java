package gm.support;

import java.util.Map;

import lombok.Getter;

@Getter
public class Investimento extends TesteBase {

	private final Map<String, Object> dados;
	
	public Investimento(Map<String, Object> dados) {
		this.dados = dados;
	}
	
	public void setId(Integer value) {
		dados.put("id", value);
	}
	
	public Integer getId() {
		return (Integer) dados.get("id");
	}

	public void setPropostaBNDES(PropostaCredito proposta) {
		if (proposta == null) {
			dados.put("propostaBNDES", null);
		} else {
			dados.put("propostaBNDES", proposta.getDados());		
		}
	}

	public void setDataSolicitacaoInstituicao(String value) {
		dados.put("dataSolicitacaoInstituicao", value);		
	}
	
}
