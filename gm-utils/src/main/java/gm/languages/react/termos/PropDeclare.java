package gm.languages.react.termos;

import gm.languages.palavras.Palavra;
import gm.utils.comum.Lst;
import lombok.Setter;

@Setter
public class PropDeclare extends Palavra {

	public final String nome;
	private final Lst<Palavra> tipo = new Lst<>();
	private Palavra value;
	private boolean req = true;

	public PropDeclare(String nome) {
		super("");
		this.nome = nome;
	}
	
	public void addTipo(Palavra o) {
		tipo.add(o);
		o.clearIdentacao();
	}
	
	@Override
	public String toString() {
		String tp = tipo.joinString(i -> i.getS(), "");
		String s;
		
		if (req) {
			s = "private Req<"+tp+"> " + nome + " = req(";
		} else if (tp.equalsIgnoreCase("Boolean")) {
			s = "private PropBoolean " + nome + " = propBoolean(";	
		} else {
			s = "private Prop<"+tp+"> " + nome + " = prop(";
		}
		 
		if (value != null) {
			s += value.getS();
		}
		return s + ");";
	}

}
