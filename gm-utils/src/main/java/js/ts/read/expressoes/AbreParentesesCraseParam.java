package js.ts.read.expressoes;

import gm.languages.palavras.Palavra;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AbreParentesesCraseParam extends Palavra {

	private FechaParentesesCraseParam fechamento;
	
	public AbreParentesesCraseParam() {
		super("\" + ");
	}

}
