package gm.languages.java.expressoes;

import gm.languages.java.outros.Utils;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.PalavraReservada;
import gm.languages.palavras.comuns.literal.StringLiteral;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.languages.palavras.comuns.simples.Igual;
import gm.languages.palavras.comuns.simples.Virgula;
import gm.utils.comum.Lst;
import gm.utils.javaCreate.JcTipo;
import lombok.Getter;

@Getter
public class AnnotationJava extends PalavraReservada {
	
	private final Lst<Palavra> params = new Lst<>();
	private JcTipo type;

	public AnnotationJava(Class<?> classe) {
		this(JcTipo.build(classe));
	}
	
	public AnnotationJava(TipoJava tipo) {
		this(tipo.getType());
	}
	
	public AnnotationJava(JcTipo type) {
		super("@" + Utils.getSimpleName(type));
		this.type = type;
	}

	@Override
	public String toString() {
		
		String s = getS() + params.toString(i -> i.getS(), "");
		
		if (emAnalise) {
			return "Annotation["+s+"]";
		}
		
		return s;
		
	}
	
	public String getValue(String paramName) {
		
		Palavra o = params.unique(i -> i.getS().contains(paramName));
		
		if (o == null) {
			return null;
		}
		
		Igual igual = (Igual) params.after(o);
		
		o = params.after(igual);
		
		if (o instanceof StringLiteral) {
			StringLiteral s = (StringLiteral) o;
			return s.getTexto();
		}
		
		String s = "";
		
		while (!(o instanceof Virgula) && !(o instanceof FechaParenteses)) {
			s += o.getS();
			o = params.after(o);
		}
		
		return s;
		
	}

}
