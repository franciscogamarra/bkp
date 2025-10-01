package gm.languages.palavras.comuns.literal;

import gm.languages.java.expressoes.AnnotationJavaSemTipo;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.ts.javaToTs.JavaToTs;
import gm.utils.comum.Lst;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class StringLiteral extends Literal {
	
	public static final String OCORRENCIA_IMPROVAVEL = "#UMA-OCORRENCIA-IMPROVAVEL#";
	public static final String OCORRENCIA_IMPROVAVEL_2 = "#UMA-OCORRENCIA-IMPROVAVEL-2#";

	private final Palavras palavras = new Palavras();
	
	private Palavra abre;
	private Palavra fecha;

	public StringLiteral() {
		super("");
	}

	public void add(Palavra o) {
		palavras.add(o);
	}

	@Override
	public String toString() {
		
		String a = abre.getS();
		String f = fecha.getS();
		
		if (JavaToTs.config.stringAspasSimples) {
			a = a.replace("\"", "'");
			f = f.replace("\"", "'");
		}
		
		String s = a + getTexto() + f;
		return toStringFinal(s);
	}
	
	@Override
	public String getS() {
		return toString();
	}
	
	public String getTexto() {
		
		Lst<AnnotationJavaSemTipo> annotacoes = palavras.filter(AnnotationJavaSemTipo.class);
		for (AnnotationJavaSemTipo a : annotacoes) {
			palavras.replace(a, new NaoClassificada("@"+a.getS()));
		}
		
		String s = palavras.toStringGetS("");
		
		if (s.contentEquals(OCORRENCIA_IMPROVAVEL)) {
			return OCORRENCIA_IMPROVAVEL_2;
		}
		
		return s;
	}

}