package js.ts.read.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaParentesesCraseParam extends Palavra {

	private AbreParentesesCraseParam abertura;
	
	public FechaParentesesCraseParam() {
		super("+ \"");
	}

}