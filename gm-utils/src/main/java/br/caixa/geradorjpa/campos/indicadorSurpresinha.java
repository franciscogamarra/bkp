package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class indicadorSurpresinha extends Campo {

	@Override
	public String getColumn() {
		return "ic_surpresinha";
	}

	@Override
	public boolean isNotNull() {
		return true;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.silce.bos.surpresinha.indicadorSurpresinha.IndicadorSurpresinha");
	}
	
	@Override
	public boolean autoConverter() {
		return true;
	}
	
}
