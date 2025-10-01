package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class tipoCombo extends Campo {

	@Override
	public String getColumn() {
		return "id_tipo_combo";
	}

	@Override
	public boolean isNotNull() {
		return false;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.silce.bos.combo.tipoCombo.TipoCombo");
	}
	
	@Override
	public boolean autoConverter() {
		return true;
	}
	
}
