package br.caixa.geradorjpa;

import gm.utils.javaCreate.JcAnotacao;
import gm.utils.javaCreate.JcTipo;

public abstract class CampoString extends Campo {

	@Override
	public JcTipo getType() {
		return new JcTipo(String.class);
	}
	
	@Override
	public void onColumn(JcAnotacao a) {
		a.addParametro("length", getLength());
	}
	
	public abstract int getLength();
	
}
