package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class mesDeSorte extends Campo {

	@Override
	public String getColumn() {
		return "mm_sorte";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.utils.enums.mes.Mes");
	}
	
	@Override
	public boolean autoConverter() {
		return true;
	}
	
}
