package br.sicoob.src.app.shared.forms;

import br.sicoob.src.app.funcionalidades.proposta_credito_bndes.models.unidade_federativa.model.ts.UnidadeFederativa;
import br.sicoob.src.app.shared.utils.UFs;
import gm.languages.ts.javaToTs.exemplo.xx.Any;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InputUF extends FormInput<UnidadeFederativa> {
	
	public InputUF(String name, boolean required) {
		super(name, required);
	}
	
	public InputUF set(UnidadeFederativa o) {
		setPrivate(o);
		return this;
	}

	@Override
	protected UnidadeFederativa parseT(String s) {
		
		if (isNull_(s)) {
			return null;
		}
		
		Any o = jsonParse(s, Any.class);
		return as(o, UnidadeFederativa.class);
		
	}
	
	public String getSigla() {
		
		if (isEmpty()) {
			return "";
		} else {
			return get().siglaUF;
		}
		
	}

	public void setSigla(String sigla) {
		set(UFs.getBySigla(sigla));
	}
	
}