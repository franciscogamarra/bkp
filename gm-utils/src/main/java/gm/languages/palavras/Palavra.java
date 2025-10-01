package gm.languages.palavras;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.palavras.comuns.IfWhile;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.javaCreate.JcTipo;
import gm.utils.lambda.P1;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringRight;

@Getter @Setter
public abstract class Palavra {
	
	public static boolean removerEspacosQuandoHaQuebrasOuTabs = false;
	
	private static int count = 0;
	
	public final int id = count++;
	
	public boolean monitorando;
	
	public static boolean emAnalise = false;
	
	private Lst<Comentario> comentarios = new Lst<>();
	
	private Object s;
	private String complemento;
	
	private boolean invisivel;
	private boolean identacaoBloqueada;

	public String getS() {
		
		if (invisivel) {
			return "";
		}
		
		return s.toString();
	}
	
	public Palavra(Object s) {
		this.s = s;
		
		
//		if (id == 1124) {
//			Print.ln();
//		}
		
	}
	
	public boolean eq(String s) {
		return StringCompare.eq(getS(), s);
	}
	
	public boolean eq(String s, String... itens) {
		
		String ss = getS();
		if (ss.contentEquals(s)) {
			return true;
		}
		
		for (String sss : itens) {
			if (ss.contentEquals(sss)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	protected String toStringFinal(String ss) {
		if (emAnalise) {
			return getClass().getSimpleName() + "["+ss+"("+id+","+quebras+","+tabs+","+espacos+")"+"]";
		}
		return ss;
	}
	
	@Override
	public String toString() {
		String ss = getS();
//		if (getItens() != null) {
//			ss += getItens().toString();
//		}
		return toStringFinal(ss);
	}
	
	public static boolean debug = false;
	
	public boolean isnt(Class<?>... classes) {
		return !is(classes);
	}
	
	public boolean is(Class<?>... classes) {

		for (Class<?> classe : classes) {
			
			if ((classe == Palavra.class) || UClass.instanceOf(getClass(), classe)) {
				return true;
			}
			
			if (classe == Is0.class) {
				return Is0.func.test(this);
			}

			if (classe == Is1.class) {
				return Is1.func.call(this);
			}

			if (classe == Is2.class) {
				return Is2.func.call(this);
			}
			
			if (UClass.instanceOf(classe, Is0.class)) {
				Is0 proposicao = (Is0) UClass.newInstance(classe);
				return proposicao.exec(this);
			}
			
		}
		
		return false;
		
	}
	
	public boolean proprietarioIs(Class<?>... classes) {
		return proprietario != null && proprietario.is(classes);
	}
	
	public Palavras getItens() {
		return null;
	}
	
	private int espacos;
	public Palavra incEspaco() {
		espacos++;
		return this;
	}

	private int tabs;
	public Palavra incTab() {
		tabs++;
		return this;
	}
	public Palavra decTab() {
		tabs--;
		return this;
	}

	private int quebras;
	public Palavra incQuebra() {
		incQuebras(1);
		return this;
	}
	
	public Palavra incQuebras(int value) {
		setQuebras(getQuebras()+value);
		return this;
	}

	public Palavra incTabs(int value) {
		tabs += value;
		return this;
	}
	
	public boolean hasIdentacao() {
		return getEspacos() > 0 || getTabs() > 0 || getQuebras() > 0;
	}

	public Palavra copiaIdentacao(Palavra o) {
		if (o == null) {
			throw new NullPointerException("o == null");
		}
		setEspacos(o.getEspacos());
		setTabs(o.getTabs());
		setQuebras(o.getQuebras());
		return this;
	}
	
	public Palavra absorverIdentacao(Palavra o) {
		if (o == null) {
			throw new NullPointerException("o == null");
		}
		copiaIdentacao(o);
		comentarios.addAll(o.getComentarios());
		o.clearIdentacao();
		o.comentarios.clear();
		setComplemento(o.getComplemento());
		o.setComplemento(null);
		return this;
	}
	
	public boolean hasQuebra() {
		return getQuebras() > 0;
	}

	public boolean pulaLinha() {
		return getQuebras() > 1;
	}
	
	public Palavra setEspacos(int espacos) {
		
		if (identacaoBloqueada) {
			throw new RuntimeException("identacaoBloqueada");
		}
		
		this.espacos = espacos;
		return this;
	}
	
	public Palavra setQuebras(int quebras) {
		
		if (identacaoBloqueada) {
			throw new RuntimeException("identacaoBloqueada");
		}
		
		this.quebras = quebras;
		return this;
	}
	
	public Palavra setTabs(int tabs) {
		
		if (identacaoBloqueada) {
			throw new RuntimeException("identacaoBloqueada");
		}
		
		this.tabs = tabs;
		return this;
	}

	public Palavra clearIdentacao() {
		return setEspacos(0).setTabs(0).setQuebras(0);
	}

	public void print() {
		SystemPrint.ln(this);
	}
	
	public boolean isParentesesDeFechamentoDeUmIfWhile() {
		return is(FechaParenteses.class) && proprietarioIs(IfWhile.class);
	}
	
	private Palavra proprietario;
	private final Lst<Palavra> propriedades = new Lst<>();

	public boolean removido;
	
	public final void setProprietario(Palavra o) {
		proprietario = o;
		o.propriedades.add(this);
	}
	
	public void addComentario(String s) {
		Comentario o = new Comentario();
		o.add(new ComentarioBlocoOpen());
		o.add(new NaoClassificada(s));
		o.add(new ComentarioBlocoClose());
		o.incQuebra();
		comentarios.add(o);
	}
	
	public Palavra assertt(Class<?> classe) {
		if (!is(classe)) {
			throw new RuntimeException("Não é um instancia de " + classe.getSimpleName() + " / " + getClass().getSimpleName());
		}
		return this;
	}
	
	public Palavra assertt(String s) {
		if (!eq(s)) {
			throw new RuntimeException("Não é igual : " + getS() + " / " + s);
		}
		return this;
	}

	public Linguagem getLinguagem() {
		return Linguagem.selected;
	}
	
	public boolean isJava() {
		return getLinguagem() == Linguagem.java;
	}

	public boolean isJs() {
		return getLinguagem() == Linguagem.js;
	}
	
	public boolean isAs() {
		return getLinguagem() == Linguagem.as;
	}
	
	public boolean isSql() {
		return getLinguagem() == Linguagem.sql;
	}

	public String getPossivelTipoNoComentario() {

		if (getComentarios().size() != 1) {
			return null;
		}
		
		Comentario o = getComentarios().getFirst();
		
		if (o == null) {
			return null;
		}
		
		String s = o.getS();
		
		if (s.startsWith("/*")) {
			s = s.substring(2);
			s = StringRight.ignore(s, 2);
		}
		
		return s;
		
	}
	
	public TipoJava getTipoNoComentario() {
		
		String s = getPossivelTipoNoComentario();

		if (s == null) {
			return null;
		}
		
		if (s.contentEquals("String")) {
			return new TipoJava(String.class);
		}

		if (s.contentEquals("Integer")) {
			return new TipoJava(Integer.class);
		}
		
		if (s.contentEquals("P1<Object>") || s.contentEquals("P1<?>")) {
			TipoJava tipo = new TipoJava(new JcTipo(P1.class));
			TipoJava obj = new TipoJava(Object.class);
			Lst<TipoJava> gen = new Lst<>();
			gen.add(obj);
			tipo.setGenerics(gen);
			return tipo;
		}

		if (s.contentEquals("any")) {
			return new TipoJava(Object.class);
		}
		
		return null;
		
	}

	@SuppressWarnings("unchecked")
	public <T extends Palavra> T cast(Class<T> classe) {
		if (!is(classe)) {
			throw new RuntimeException("Esperado: " + classe.getSimpleName() + " / Recebido: " + getClass().getSimpleName());
		}
		return (T) this;
	}

	public void sDeveSer(String s) {
		if (!getS().contentEquals(s)) {
			throw new RuntimeException();
		}
	}
	
	public int getEspacos() {
		
		if (removerEspacosQuandoHaQuebrasOuTabs && (getQuebras() > 0 || getTabs() > 0)) {
			return 0;
		}
		
		return espacos;
	}
	
	public void setComplemento(String complemento) {
		
		if (this.complemento != null) {
			SystemPrint.ln();
		}
		
		this.complemento = complemento;
	}
	
	public void bloqueiaIdentacao() {
		identacaoBloqueada = true;
	}
	
}
