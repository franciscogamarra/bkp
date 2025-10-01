package gm.support;

import java.util.Map;

import lombok.Getter;

@Getter
public class ItemInvestimento extends TesteBase {

	private final Map<String, Object> dados;
	
	public ItemInvestimento(Map<String, Object> dados) {
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
	
}
