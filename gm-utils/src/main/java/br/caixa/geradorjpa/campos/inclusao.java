package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.CampoDataHora;

public class inclusao extends CampoDataHora {

	@Override
	public String getColumn() {
		return "ts_inclusao";
	}

	@Override
	public boolean isNotNull() {
		return true;
	}
	
}
