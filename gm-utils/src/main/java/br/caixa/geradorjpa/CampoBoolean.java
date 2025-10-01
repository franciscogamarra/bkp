package br.caixa.geradorjpa;

import gm.utils.javaCreate.JcTipo;

public abstract class CampoBoolean extends Campo {

	@Override
	public JcTipo getType() {
		return new JcTipo(Boolean.class);
	}
	
}
