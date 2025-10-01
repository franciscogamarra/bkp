package gm.languages.java.expressoes;

import java.lang.annotation.Annotation;

import gm.languages.comum.DeclaracaoDeVariavelEscopo;
import gm.languages.palavras.Comentario;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.conjuntos.arrow.Arrow;
import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.comum.Lst;
import gm.utils.comum.UType;
import gm.utils.lambda.P1;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeclaracaoDeVariavel extends Palavra {

	public void setEscopo(DeclaracaoDeVariavelEscopo escopo) {
		this.escopo = escopo;
	}
	
	private TipoJava tipo;
	private NaoClassificada nome;
	private ModificadorDeAcesso modificadorDeAcesso;
	public final AnnotationsJava annotations = new AnnotationsJava();
	public final Lst<DeclaracaoDeVariavelRef> refs = new Lst<>();
	private DeclaracaoDeVariavelEscopo escopo = DeclaracaoDeVariavelEscopo.variavel;
	private final Lst<P1<TipoJava>> tipoObservers = new Lst<>();

	public DeclaracaoDeVariavel(ModificadorDeAcesso modificadorDeAcesso, TipoJava tipo, NaoClassificada nome) {
		super("");
		setModificadorDeAcesso(modificadorDeAcesso);
		setTipo(tipo);
		this.nome = nome;
	}
	
	public void setTipo(TipoJava tipo) {
		
		if (this.tipo == tipo) {
			return;
		}
		
//		if (tipo != null && nome != null && tipo.eq("V") && nome.eq("k")) {
//			SystemPrint.ln();
//		}
		
		this.tipo = tipo;
		tipo = getTipoPrivate();
		
		if (tipo != null) {
//			if (tipo.eq("V") && nome != null && nome.eq("k")) {
//				SystemPrint.ln();
//			}
			tipoObservers.each(i -> i.call(getTipo()));
		}
		
	}
	
	public void addTipoObserver(P1<TipoJava> func) {
		
		if (getTipo() == null) {
			tipoObservers.add(func);
		} else {
			func.call(getTipo());
		}
		
	}
	
	public void setModificadorDeAcesso(ModificadorDeAcesso modificadorDeAcesso) {
		this.modificadorDeAcesso = modificadorDeAcesso;
		if (modificadorDeAcesso != null) {
			setEscopo(DeclaracaoDeVariavelEscopo.atributo);
		}
	}
	
	public DeclaracaoDeVariavel(TipoJava tipo, NaoClassificada nome) {
		this(null, tipo, nome);
	}
	
	@Override
	public String getS() {
		
		String s = nome.getS();
		
		if (getTipo() != null) {
			
			if (isAs()) {
				s += " : " + getTipo().getS();
			} else {
				s = getTipo().getS() + " " + s;
			}
			
		}

		if (modificadorDeAcesso != null) {
			s = modificadorDeAcesso.getS() + " " + s;
		}
		
		if (isAs() && !isParametro() && !isAtributo()) {
			s = "var " + s;
		}
		
		return s;
	}
	
	public boolean podeRetornarNull() {
		
		if (!annotations.has(PodeSerNull.class)) {
			return false;
		}
		
		if (getTipo().isGenerics()) {
			return true;
		}
		
		return !UType.PRIMITIVAS_JAVA_REAL.contains(getTipo().getType());
		
	}

	public boolean isParametro() {
		return getEscopo() == DeclaracaoDeVariavelEscopo.parametro;
	}

	public boolean isParametroCatch() {
		return getEscopo() == DeclaracaoDeVariavelEscopo.catchParam;
	}

	public boolean isAtributo() {
		return getEscopo() == DeclaracaoDeVariavelEscopo.atributo;
	}

	public boolean isStatic() {
		return getModificadorDeAcesso() != null && getModificadorDeAcesso().isStatic();
	}
	
	public AnnotationJava getAnnotation(String simpleName) {
		return getAnnotations().getLst().unique(i -> i.getType().getSimpleName().contentEquals(simpleName));
	}
	
	public AnnotationJava getAnnotation(Class<? extends Annotation> classe) {
		return getAnnotation(classe.getSimpleName());
	}
	
	private TipoJava getTipoPrivate() {

		if (tipo == null || tipo.eq(Arrow.class)) {
			Comentario o = getComentarios().getFirst();
			if ((o != null) && o.eq("/*String*/")) {
				getComentarios().clear();
				return new TipoJava(String.class);
			}
		}
		
		return tipo;
		
	}
	
	public TipoJava getTipo() {
		TipoJava tipo = getTipoPrivate();
		if (tipo != this.tipo) {
			setTipo(tipo);
		}
		return tipo;
	}

	public boolean isString() {
		return getTipo().isString();
	}
	
}
