package gm.cenarios;

import gm.support.Programa;
import gm.support.PropostaCredito;

public class Cenario0001 {

	public static void main(String[] args) {
		PropostaCredito o = new PropostaCredito();
		o.load("proposta-credito-nova");
		o.setId(null);
		o.setNumeroProposta(null);
		o.setPrograma(Programa.O2424);
		o.save();
		o.enviar();
	}
	
}
