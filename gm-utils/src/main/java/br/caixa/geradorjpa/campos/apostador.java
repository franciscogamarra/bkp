package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class apostador extends Campo {

	@Override
	public String getColumn() {
		return "nu_cpf_apostador";
	}

	@Override
	public boolean isNotNull() {
		return true;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.silce.bos.apostador.Apostador");
	}
	
	@Override
	public boolean autoConverter() {
		return true;
	}

}
