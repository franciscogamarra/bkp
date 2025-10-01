package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class tipoConcurso extends Campo {

	@Override
	public String getColumn() {
		return "id_tipo_concurso";
	}

	@Override
	public boolean isNotNull() {
		return true;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.utils.enums.tipoConcurso.TipoConcurso");
	}
	
	@Override
	public boolean autoConverter() {
		return true;
	}
	
}
