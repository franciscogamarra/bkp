package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import src.commom.utils.string.StringPrimeiraMaiuscula;

@Getter
public class StateConhecido extends Palavra {
	
	private StateConhecidoClose fechamento;
	public final String nome;
	private final String tipo;

	public StateConhecido(String nome, String tipo) {
		super("");
		this.nome = nome;
		this.tipo = tipo;
	}
	
	public void setFechamento(StateConhecidoClose fechamento) {
		this.fechamento = fechamento;
		if (fechamento.getAbertura() != this) {
			fechamento.setAbertura(this);
		}
	}
	
	@Override
	public String getS() {
		
		if (tipo.contentEquals("Boolean") || tipo.contentEquals("boolean")) {
			return "private StateBoolean " + nome + " = stateBoolean(";	
		} else if (tipo.contentEquals("Integer") || tipo.contentEquals("int")) {
			return "private StateInt" + nome + " = stateInt(";	
		} else if (tipo.contentEquals("String") || tipo.contentEquals("str")) {
			return "private StateString" + nome + " = stateString(";	
		}
		
		return "private StateProp<"+StringPrimeiraMaiuscula.exec(tipo)+"> " + nome + " = state(";
	}

}
