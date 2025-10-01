package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoInt;

public class compra extends CampoInt {

	@Override
	public String getColumn() {
		return "id_compra";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

}
