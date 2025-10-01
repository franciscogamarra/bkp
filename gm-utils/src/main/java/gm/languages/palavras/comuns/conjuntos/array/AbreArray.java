package gm.languages.palavras.comuns.conjuntos.array;

import gm.languages.palavras.Palavra;
import gm.utils.exception.NaoImplementadoException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreArray extends Palavra {

	private AbreArray pai;
	private FechaArray fechamento;
	
	public AbreArray() {
		super("");
	}
	
	public void setFechamento(FechaArray fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}
	
	@Override
	public String getS() {

		if (isJava()) {
			return "{";
		}
		
		if (isJs()) {
			return "[";
		}

		if (isAs()) {
			return "[";
		}
		
		throw new NaoImplementadoException(getLinguagem().getNome());
		
	}
	
}