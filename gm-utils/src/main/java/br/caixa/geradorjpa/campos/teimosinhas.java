package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoInt;
import gm.utils.javaCreate.JcClasse;

public class teimosinhas extends CampoInt {

	@Override
	public String getColumn() {
		return "qt_teimosinha";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}
	
	@Override
	public void onExec(JcClasse jc) {
		jc.metodo("contemTeimosinhas").type(boolean.class).public_().return_("teimosinhas != null && teimosinhas > 1");
	}

}
