package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoInt;

public class sequencialCombo extends CampoInt {

	@Override
	public String getColumn() {
		return "nu_sequencial_combo";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

}
