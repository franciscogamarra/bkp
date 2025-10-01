package gm.languages.ts.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.ts.javaToTs.JavaToTs;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FechaParentesesMetodo extends Palavra {

	private DeclaracaoDeMetodoTs metodo;
	
	public FechaParentesesMetodo(DeclaracaoDeMetodoTs metodo) {
		super(")");
		this.metodo = metodo;
		metodo.setFechaParentesesMetodo(this);
	}
	
	public AbreParentesesMetodo getFechamento() {
		return getMetodo().getAbreParentesesMetodo();
	}
	
	@Override
	public String getS() {
		
		if (metodo.isConstructor() || metodo.isOverride()) {
			return ")";
		}
		
		String s = ")";
		
		if (JavaToTs.config.typeScript && metodo.getTipo() != null) {

			s += " : " + metodo.getTipo().getS();
			
			if (metodo.isPodeRetornarNull() && !metodo.getTipo().eq("void") && !metodo.getTipo().eq("any")) {
				s += " | null";
			}
			
		}
		
		if (metodo.isDeclararComoArrow()) {
			s += " =>";
		}
		
		return s;
	}
	
}
