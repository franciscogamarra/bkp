package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoString;

public class nome extends CampoString {

	@Override
	public String getColumn() {
		return "de_nome";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

	@Override
	public int getLength() {
		return 30;
	}

}
