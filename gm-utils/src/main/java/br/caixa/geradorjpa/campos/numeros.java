package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class numeros extends Campo {

	@Override
	public String getColumn() {
		return "de_prognostico";
	}

	@Override
	public boolean isNotNull() {
		return true;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.silce.bos.aposta.support.Prognostico");
	}
	
	@Override
	public boolean autoConverter() {
		return true;
	}
	
}
