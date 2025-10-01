package gm.languages.palavras.comuns.conjuntos.arrow;

import gm.languages.palavras.Palavra;
import gm.utils.exception.NaoImplementadoException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class Arrow extends Palavra {

	private final AbreArrow abertura = new AbreArrow(this);
	
	private Integer paramsCount;
	private boolean async;
	
	public Arrow() {
		super("->");
	}
	
	@Override
	public String getS() {
		
		if (isJava()) {
			return "->";
		}

		if (isJs()) {
			return "=>";
		}
		
		throw new NaoImplementadoException(getLinguagem().getNome());
		
	}
	
	public FechaArrow getFechamentoArrow() {
		return getAbertura().getFechamentoArrow();
	}
	
}
