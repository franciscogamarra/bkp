package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class modalidade extends Campo {

	@Override
	public String getColumn() {
		return "id_modalidade";
	}

	@Override
	public boolean isNotNull() {
		return true;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.utils.enums.modalidade.Modalidade");
	}
	
	@Override
	public boolean autoConverter() {
		return true;
	}
	
}
