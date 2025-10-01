package gm.languages.java.expressoes;

import src.commom.utils.string.StringAfterFirst;

public class ModificadorDeAcessoDefault extends ModificadorDeAcesso {

	@Override
	public String getS() {
		return StringAfterFirst.get(super.getS(), " ");
	}
	
}
