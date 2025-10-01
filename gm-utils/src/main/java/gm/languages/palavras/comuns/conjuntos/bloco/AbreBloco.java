package gm.languages.palavras.comuns.conjuntos.bloco;

import gm.languages.palavras.Palavra;
import gm.utils.exception.NaoImplementadoException;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreBloco extends Palavra {

	private AbreBloco pai;
	private FechaBloco fechamento;
	
	public AbreBloco() {
		super("{");
	}
	
	public void setFechamento(FechaBloco fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}
	
	@Override
	public String getS() {
		
		if (monitorando || getFechamento().monitorando) {
			return "{<"+id+"/"+getFechamento().id+">";
		}

		
		if (isInvisivel()) {
			return "";
		}
		
		if (isJava() || isJs() || isAs()) {
			return "{";
		}
		
		if (isSql()) {
			return "begin";
		}
		
		throw new NaoImplementadoException(getLinguagem().getNome());
		
	}
	
}