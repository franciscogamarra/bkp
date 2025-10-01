package br.caixa.geradorjpa.campos;

import br.caixa.geradorjpa.Campo;
import gm.utils.javaCreate.JcTipo;

public class carrinhoFavorito extends Campo {

	@Override
	public String getColumn() {
		return "id_carrinho_favorito";
	}

	@Override
	public boolean isNotNull() {
		return true;
	}

	@Override
	public JcTipo getType() {
		return new JcTipo("br.caixa.loterias.silce.bos.apostacarrinhofavorito.CarrinhoFavorito");
	}
	
	@Override
	public boolean reference() {
		return true;
	}
	
}
