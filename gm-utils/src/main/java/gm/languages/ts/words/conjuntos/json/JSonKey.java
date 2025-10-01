package gm.languages.ts.words.conjuntos.json;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.literal.StringLiteral;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class JSonKey extends Palavra {
	
	private String texto;

	public JSonKey(StringLiteral st) {
		
		super("");
		
		if (isValidKey(st)) {
			texto = st.getTexto();
		} else {
			boolean b = emAnalise;
			emAnalise = false;
			texto = st.toString();
			emAnalise = b;
		}
		
	}
	
	public JSonKey(String s) {
		super("");
		texto = s;
	}
	
	@Override
	public String getS() {
		
		String s = texto;
		
		if (s.contentEquals("reticencias")) {
			return "...";
		}
		
		if (s.contains("__")) {
			s = "\"" + s.replace("__", "-") + "\"";
		}
		
		return s + ":";
	}
	
	private static boolean isValidKey(StringLiteral st) {
		
		String s = st.getTexto();
		
		if (s.contains(" ")) {
			return false;
		}

		if (s.contains(".")) {
			return false;
		}

		if (s.contains("-")) {
			return false;
		}
		
		return true;
		
	} 	

}
