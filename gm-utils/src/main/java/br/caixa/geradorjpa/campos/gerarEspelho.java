package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoBoolean;

public class gerarEspelho extends CampoBoolean {

	@Override
	public String getColumn() {
		return "ic_gerar_espelho";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

}
