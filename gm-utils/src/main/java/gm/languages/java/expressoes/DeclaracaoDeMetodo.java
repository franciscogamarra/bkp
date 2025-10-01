package gm.languages.java.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UType;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodos;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class DeclaracaoDeMetodo extends Palavra {

	private TipoJava tipo;
	private AbreBloco bloco;
	private String nome;
	private DeclaracaoDeUmTipoGenerico genericParam;
	private ModificadorDeAcesso modificadorDeAcesso;
	public final AnnotationsJava annotations = new AnnotationsJava();

	public DeclaracaoDeMetodo(ModificadorDeAcesso modificadorDeAcesso, TipoJava tipo, String nome) {
		super("");
		this.modificadorDeAcesso = modificadorDeAcesso;
		this.tipo = tipo;
		this.nome = nome;
	}
	
	public DeclaracaoDeMetodo(ModificadorDeAcesso modificadorDeAcesso, TipoJava tipo, Palavra nome) {
		this(modificadorDeAcesso, tipo, nome.getS());
	}
	
	public DeclaracaoDeMetodo(TipoJava tipo, NaoClassificada nome) {
		this(null, tipo, nome);
	}
	
	@Override
	public String getS() {
		
		String s = nome;
		
		if (!isConstrutor()) {
			
			if (isAs()) {
				s = "function " + s;
			} else {
				s = tipo.getS() + " " + s;
			}
			
		}

		if (genericParam != null) {
			s = genericParam.getS() + " " + s;
		}
		
		if (modificadorDeAcesso != null) {
			s = modificadorDeAcesso.getS() + " " + s;
		}
		
		return s;
		
	}

	public boolean isOverride() {
		return annotations.has(Override.class);
	}

	public boolean isOverrideClass() {

		if (isConstrutor()) {
			return false;
		}
		
		if (!isOverride()) {
			return false;
		}
		
		if (tipo.isGenerics()) {
			return false;
		}
		
		if (tipo.getClasse() == null) {
			SystemPrint.ln(tipo);
		}
		
		Class<?> classe = tipo.getClasse().getSuperclass();
		
		while (classe != null && classe != Object.class) {
			
			Metodos metodos = ListMetodos.get(classe).filter(i -> i.getName().contentEquals(nome));
			
			if (metodos.isNotEmpty()) {
				return true;//TODO isto nao está completo, mas pode funcionar por causa do js exigir unicidade de nomes
			}
			
			classe = classe.getSuperclass();
		}
		
		return false;
		
	}

	public boolean podeRetornarNull() {
		
		if (!annotations.has(PodeSerNull.class)) {
			return false;
		}
		
		return getTipo().isGenerics() || !UType.PRIMITIVAS_JAVA_REAL.contains(getTipo().getType());
		
	}
	
	public boolean isConstrutor() {
		return tipo == null;
	}
	
	@Override
	public int getTabs() {
		
		if (getModificadorDeAcesso() == null) {
			return super.getTabs();
		}
		
		return getModificadorDeAcesso().getTabs();
		
	}
	
	@Override
	public int getEspacos() {
		return 0;
	}
	
}
