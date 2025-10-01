package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoInt;

public class equipeEsportiva extends CampoInt {

	@Override
	public String getColumn() {
		return "id_equipe_esportiva";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

}
