package gm.languages.ts.expressoes;

import gm.languages.comum.DeclaracaoDeVariavelEscopo;
import gm.languages.java.expressoes.AnnotationsJava;
import gm.languages.java.expressoes.ModificadorDeAcesso;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.languages.ts.javaToTs.JavaToTs;
import gm.languages.ts.javaToTs.exemplo.xx.Any;
import gm.utils.comum.Lst;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeclaracaoDeVariavelTs extends Palavra {
	
	public void setConstant(boolean constant) {
		this.constant = constant;
	}
	
	private TipoTs tipo;
	private AbreBloco bloco;
	private boolean podeRetornarNull;
	private String nome;
	private boolean interrogacaoNoNome;
	private boolean ignorarExclamacao;
	private boolean possuiInicializacao;
	private DeclaracaoDeVariavelEscopo escopo;
	private boolean constant;
	private ModificadorDeAcesso modificadorDeAcesso;
	public final Lst<DeclaracaoDeVariavelTsRef> refs = new Lst<>();
	public AnnotationsJava annotationsJava;

	public DeclaracaoDeVariavelTs(ModificadorDeAcesso modificadorDeAcesso, TipoTs tipo, String nome) {
		super("");
		setModificadorDeAcesso(modificadorDeAcesso);
		this.tipo = tipo;
		
//		if (id == 1124) {
//			Print.ln("");
//		}
		
		this.nome = nome;
		podeRetornarNull = !tipo.getClasse().isPrimitiva();
	}
	
	public DeclaracaoDeVariavelTs(ModificadorDeAcesso modificadorDeAcesso, TipoTs tipo, NaoClassificada nome) {
		this(modificadorDeAcesso, tipo, nome.getS());
	}
	
	public void setModificadorDeAcesso(ModificadorDeAcesso modificadorDeAcesso) {
		this.modificadorDeAcesso = modificadorDeAcesso;
		
		if (modificadorDeAcesso != null) {
			setEscopo(DeclaracaoDeVariavelEscopo.atributo);
		}
	}
	
	public DeclaracaoDeVariavelTs(TipoTs tipo, String nome) {
		this(null, tipo, nome);
	}
	
	public DeclaracaoDeVariavelTs(TipoTs tipo, NaoClassificada nome) {
		this(tipo, nome.getS());
	}
	
	@Override
	public String getS() {
		
		String s = nome;
		
		if (interrogacaoNoNome) {
			s += "?";
		} else if (JavaToTs.config.colocarExclamacaoEmAtributosQueAceitamNulos && !ignorarExclamacao && isAtributo() && !possuiInicializacao && !isStatic()) {
			s += "!";
		}
		
		if (JavaToTs.config.typeScript) {
			if (isAny()) {
				s += " : any";
			} else if (escopo != DeclaracaoDeVariavelEscopo.catchParam) {
				s += " : " + tipo.getS(isNotNull());
			}
		}
		
		if (tipo.isReticencias()) {
			s = "..." + s;
		}
		
		if (escopo == DeclaracaoDeVariavelEscopo.atributo) {
			if (modificadorDeAcesso != null) {
				s = modificadorDeAcesso.getS() + " " + s;
			}
		} else if (escopo == DeclaracaoDeVariavelEscopo.parametro) {
			//nada
		} else if (escopo == DeclaracaoDeVariavelEscopo.variavel) {
			
			if (constant || isFinal()) {
				s = "const " + s;
			} else {
				s = "let " + s;
			}
			
		} else if (escopo == DeclaracaoDeVariavelEscopo.catchParam) {
			//nada
		} else if (escopo == DeclaracaoDeVariavelEscopo.forr) {
			s = "let " + s;
		} else {
			s = "/*?*/ " + s;
		}
		
		return s;
	}

	public boolean isAny() {
		
		if (annotationsJava != null && annotationsJava.has(gm.languages.ts.javaToTs.annotacoes.Any.class)) {
			return true;
		}
		
		if (tipo.getClasse().getSimpleName().contentEquals(Any.class.getSimpleName())) {
			return true;
		}
		
		return false;
		
	}
	
	public boolean isNotNull() {
		
		if (annotationsJava != null && annotationsJava.has(NotNull.class)) {
			return true;
		} else {
			return !podeRetornarNull;
		}
		
	}
	
	public boolean isAtributo() {
		return getEscopo() == DeclaracaoDeVariavelEscopo.atributo;
	}

	public boolean isStatic() {
		return getModificadorDeAcesso() != null && getModificadorDeAcesso().isStatic();
	}

	public boolean isFinal() {
		return getModificadorDeAcesso() != null && getModificadorDeAcesso().isFinal();
	}
	
	public void setNome(String nome) {
		
//		if (id == 1109) {
//			Print.ln("");
//		}
		
		this.nome = nome;
	}

}
