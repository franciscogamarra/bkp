package js.ts.read.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.simples.AbreChaves;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PublicClass extends Palavra {

	private AbreChaves open;
	
	public PublicClass(String s) {
		super("public class " + s);
	}

}
