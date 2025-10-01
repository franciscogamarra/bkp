package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoInt;

public class apostaEspelho extends CampoInt {

	@Override
	public String getColumn() {
		return "id_aposta_espelho";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

}
