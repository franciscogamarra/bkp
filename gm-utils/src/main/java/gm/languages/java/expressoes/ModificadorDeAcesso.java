package gm.languages.java.expressoes;

import gm.languages.java.expressoes.words.Public;
import gm.languages.palavras.PalavraReservada;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class ModificadorDeAcesso extends PalavraReservada {
	
	private boolean isFinal;
	private boolean isStatic;
	private boolean isSynchronized;
	private boolean isAbstract;
	
	@Override
	public String getS() {
		
		String s = super.getS();
		
		if (isStatic) {
			s += " static";
		}
		
		if (isJava()) {

			if (isSynchronized) {
				s += " synchronized";
			}

			if (isFinal) {
				s += " final";
			}
			
		}
		
		if (isAbstract) {
			s += " abstract";
		}
		
		return s;
	}
	
	public boolean isPublic() {
		return getClass() == Public.class;
	}
	
}