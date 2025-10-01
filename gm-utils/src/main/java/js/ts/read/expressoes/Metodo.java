package js.ts.read.expressoes;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Metodo extends Palavra {
	
	private TipoJava tipo = new TipoJava(void.class);
	private boolean privado;

	public Metodo(Object s) {
		super(s);
	}
	
	@Override
	public String getS() {
		return (privado ? "private " : "public ") + tipo.getS() + " " + super.getS();
	}

}
