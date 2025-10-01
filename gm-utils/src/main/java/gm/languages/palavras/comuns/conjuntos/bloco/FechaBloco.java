package gm.languages.palavras.comuns.conjuntos.bloco;

import gm.languages.palavras.Palavra;
import gm.utils.exception.NaoImplementadoException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaBloco extends Palavra {

	private AbreBloco abertura;
	
	public FechaBloco() {
		super("}");
	}
	
	public void setAbertura(AbreBloco abertura) {
		this.abertura = abertura;
		if (abertura.getFechamento() != this) {
			abertura.setFechamento(this);
		}
	}
	
	@Override
	public String getS() {
		
		if (monitorando || getAbertura().monitorando) {
			return "<"+getAbertura().id+"/"+id+">}";
		}
		
		if (getAbertura().isInvisivel()) {
			return "";
		}
		
		if (isJava() || isJs() || isAs()) {
			return "}";
		}
		
		if (isSql()) {
			return "end";
		}
		
		throw new NaoImplementadoException(getLinguagem().getNome());
		
	}
	
}
