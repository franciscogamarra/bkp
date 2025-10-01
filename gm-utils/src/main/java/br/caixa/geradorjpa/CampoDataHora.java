package br.caixa.geradorjpa;

import gm.utils.javaCreate.JcTipo;

public abstract class CampoDataHora extends Campo {

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.utils.tempo.DataHora");
	}
	
	@Override
	public JcTipo getConverter() {
		return new JcTipo("br.caixa.loterias.utils.tipos.converters.JpaConverterDataHora");
	}
	
	
}
