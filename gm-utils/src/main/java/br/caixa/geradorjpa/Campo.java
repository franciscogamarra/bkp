package br.caixa.geradorjpa;

import gm.utils.javaCreate.JcAnotacao;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcTipo;

public abstract class Campo {

	public abstract String getColumn();
	public abstract boolean isNotNull();
	public abstract JcTipo getType();
	
	public boolean autoConverter() {
		return false;
	}

	public boolean reference() {
		return false;
	}

	public JcTipo getConverter() {
		if (autoConverter()) {
			return new JcTipo(getType().getName() + "Converter");
		}
		return null;
	}
	
	public void onColumn(JcAnotacao a) {}
	
	public void onExec(JcClasse jc) {}
	
}
