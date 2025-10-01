package gm.languages.palavras.comuns.conjuntos.array;

import gm.languages.palavras.Palavra;
import gm.utils.exception.NaoImplementadoException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaArray extends Palavra {

	private AbreArray abertura;
	
	public FechaArray() {
		super("");
	}
	
	public void setAbertura(AbreArray abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
	@Override
	public String getS() {
		
		if (isJava()) {
			return "}";
		}
		
		if (isJs()) {
			return "]";
		}

		if (isAs()) {
			return "]";
		}

		throw new NaoImplementadoException(getLinguagem().getNome());
		
	}

}
