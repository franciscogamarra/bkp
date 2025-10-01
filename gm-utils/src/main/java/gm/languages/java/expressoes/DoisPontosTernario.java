package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;

public class DoisPontosTernario extends Palavra {

	private InterrogacaoTernaria abertura;
	
	public DoisPontosTernario() {
		super(":");
	}

	public void setAbertura(InterrogacaoTernaria abertura) {

		if (this.abertura == abertura) {
			return;
		}
		
		this.abertura = abertura;
		
		if (abertura != null) {
			abertura.setFechamento(this);
		}
		
	}
	
//	@Override
//	public String getS() {
//		return "[:]";
//	}

}
