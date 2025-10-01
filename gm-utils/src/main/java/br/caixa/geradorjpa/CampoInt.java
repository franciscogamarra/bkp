package br.caixa.geradorjpa;

import gm.utils.javaCreate.JcTipo;

public abstract class CampoInt extends Campo {

	@Override
	public JcTipo getType() {
		return new JcTipo(Integer.class);
	}
	
}
