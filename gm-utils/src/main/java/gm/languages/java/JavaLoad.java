package gm.languages.java;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import gm.languages.PalavrasLoad;
import gm.languages.comum.DeclaracaoDeVariavelEscopo;
import gm.languages.java.expressoes.AnnotationJava;
import gm.languages.java.expressoes.AnnotationJavaSemTipo;
import gm.languages.java.expressoes.AnnotationsJava;
import gm.languages.java.expressoes.Cast;
import gm.languages.java.expressoes.DeclaracaoDeMetodo;
import gm.languages.java.expressoes.DeclaracaoDeUmTipoGenerico;
import gm.languages.java.expressoes.DeclaracaoDeVariavel;
import gm.languages.java.expressoes.DeclaracaoDeVariavelRef;
import gm.languages.java.expressoes.Diamond;
import gm.languages.java.expressoes.DoisPontosTernario;
import gm.languages.java.expressoes.GenericsAbre;
import gm.languages.java.expressoes.GenericsFecha;
import gm.languages.java.expressoes.IgualComparacao;
import gm.languages.java.expressoes.InterrogacaoGenerics;
import gm.languages.java.expressoes.InterrogacaoTernaria;
import gm.languages.java.expressoes.InvocacaoDeUmAtributo;
import gm.languages.java.expressoes.InvocacaoDeUmMetodo;
import gm.languages.java.expressoes.MaisIgual;
import gm.languages.java.expressoes.MaisMais;
import gm.languages.java.expressoes.MenosIgual;
import gm.languages.java.expressoes.MenosMenos;
import gm.languages.java.expressoes.ModificadorDeAcesso;
import gm.languages.java.expressoes.Reticencias;
import gm.languages.java.expressoes.TipoJava;
import gm.languages.java.expressoes.words.Abstract;
import gm.languages.java.expressoes.words.Catch;
import gm.languages.java.expressoes.words.Class_;
import gm.languages.java.expressoes.words.Final;
import gm.languages.java.expressoes.words.Import;
import gm.languages.java.expressoes.words.New;
import gm.languages.java.expressoes.words.Package_;
import gm.languages.java.expressoes.words.Static;
import gm.languages.java.expressoes.words.Super;
import gm.languages.java.expressoes.words.Synchronized;
import gm.languages.java.expressoes.words.This;
import gm.languages.java.expressoes.words.Throw;
import gm.languages.java.outros.AtributoJL;
import gm.languages.palavras.Is0;
import gm.languages.palavras.OperadorComparacao;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.SinalMatematico;
import gm.languages.palavras.comuns.And;
import gm.languages.palavras.comuns.AndOr;
import gm.languages.palavras.comuns.Diferente;
import gm.languages.palavras.comuns.Else;
import gm.languages.palavras.comuns.Extends;
import gm.languages.palavras.comuns.For;
import gm.languages.palavras.comuns.If;
import gm.languages.palavras.comuns.IfWhile;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.Null;
import gm.languages.palavras.comuns.Or;
import gm.languages.palavras.comuns.Return;
import gm.languages.palavras.comuns.While;
import gm.languages.palavras.comuns.conjuntos.arrow.AbreArrow;
import gm.languages.palavras.comuns.conjuntos.arrow.Arrow;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.languages.palavras.comuns.conjuntos.bloco.FechaBloco;
import gm.languages.palavras.comuns.literal.DoubleLiteral;
import gm.languages.palavras.comuns.literal.InteiroLiteral;
import gm.languages.palavras.comuns.literal.LongLiteral;
import gm.languages.palavras.comuns.literal.StringLiteral;
import gm.languages.palavras.comuns.simples.AbreChaves;
import gm.languages.palavras.comuns.simples.AbreColchetes;
import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.DoisPontos;
import gm.languages.palavras.comuns.simples.EComercial;
import gm.languages.palavras.comuns.simples.Exclamacao;
import gm.languages.palavras.comuns.simples.FechaColchetes;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.languages.palavras.comuns.simples.Igual;
import gm.languages.palavras.comuns.simples.Instanceof;
import gm.languages.palavras.comuns.simples.Interrogacao;
import gm.languages.palavras.comuns.simples.Maior;
import gm.languages.palavras.comuns.simples.Mais;
import gm.languages.palavras.comuns.simples.Menor;
import gm.languages.palavras.comuns.simples.Menos;
import gm.languages.palavras.comuns.simples.Pipe;
import gm.languages.palavras.comuns.simples.Ponto;
import gm.languages.palavras.comuns.simples.PontoEVirgula;
import gm.languages.palavras.comuns.simples.Virgula;
import gm.languages.ts.javaToTs.exemplos.src.Bla;
import gm.utils.classes.ClassBox;
import gm.utils.classes.ListClass;
import gm.utils.classes.UClass;
import gm.utils.comum.BreakLoop;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UType;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcTipo;
import gm.utils.lambda.F1;
import gm.utils.lambda.P1;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.reflection.Parametro;
import gm.utils.string.ListString;
import js.array.Array;
import lombok.Getter;
import src.commom.utils.array.Itens;
import src.commom.utils.comum.BooleanWrapper;
import src.commom.utils.comum.Box;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringRight;

@Getter
public class JavaLoad extends PalavrasLoad {
	
	static {
		Palavra.removerEspacosQuandoHaQuebrasOuTabs = true;
	}
	
	public static class config {
		public static P1<ListString> onLoad;
	}
	
	public static final ListClass classesConhecidas = new ListClass();

	protected final AnnotationsJava annotations = new AnnotationsJava();
	private final GFile file;
	private final ListClass imports = new ListClass();
	private final ListClass tiposJava = UType.PRIMITIVAS_JAVA.copy();
	protected final Lst<TipoJava> tiposConhecidos = new Lst<>();
	private boolean porClasse;
	private final Lst<TipoJava> generics = new Lst<>();
	
	public static void main(String[] args) {
		Palavra.emAnalise = true;
		exec(Bla.class);
//		exec(StringReplace.class);
	}
	
	public static void exec(Class<?> classe) {
		JavaLoad o = new JavaLoad(classe);
		o.exec();
		o.print();
	}

	public static JavaLoad exec(GFile file) {
		JavaLoad jl = new JavaLoad(file);
		jl.exec();
		return jl;
	}
	
	private static final Lst<Palavra> RESERVADAS = UClass.getClassesDaPackage(Class_.class.getPackage(), false).map(i -> (Palavra) UClass.newInstance(i));
	private Class<?> classe;
	
	protected Palavra build(String s) {
		
		if (s.contentEquals("if")) {
			return new If();
		}

		if (s.contentEquals("else")) {
			return new Else();
		}

		if (s.contentEquals("while")) {
			return new While();
		}

		if (s.contentEquals("for")) {
			return new For();
		}

		if (s.contentEquals("null")) {
			return new Null();
		}

		if (s.contentEquals("return")) {
			return new Return();
		}

		if (s.contentEquals("extends")) {
			return new Extends();
		}
		
		if (s.contentEquals("RuntimeException")) {
			return new TipoJava(RuntimeException.class);
		}

		if (s.contentEquals("Exception")) {
			return new TipoJava(Exception.class);
		}

		if (s.contentEquals("instanceof")) {
			return new Instanceof();
		}

		Palavra item = RESERVADAS.unique(i -> i.getS().equals(s));

		if (item != null) {
			Palavra o = UClass.newInstance(item.getClass());
			o.setS(s);
			return o;
		}
		
		{
			Class<?> classe = tiposJava.get(s);
			if (classe != null) {
				return new TipoJava(classe);
			}
		}
		
		if (s.startsWith("@") && !s.contentEquals("@")) {
			
			Class<?> classe = tiposJava.get(s.substring(1));
			if (classe != null) {
				return new AnnotationJava(classe);
			}

			classe = UClass.getClass(s.substring(1));
			if (classe != null) {
				tiposJava.add(classe);
				return new AnnotationJava(classe);
			}
			
			return new AnnotationJavaSemTipo(s.substring(1));
				
		}
		
		return null;
		
	}
	
	public TipoJava descobreTipo(String s) {
		
		if (s.startsWith("Array<")) {
			String ss = StringAfterFirst.get(s, "<");
			ss = StringBeforeLast.get(ss, ">");
			TipoJava tipo = new TipoJava(Array.class);
			TipoJava param = descobreTipo(ss);
			tipo.addGeneric(param);
			return tipo;
		}
		
		if (s.contains("<")) {
			
			String before = StringBeforeFirst.get(s, "<");
			TipoJava tipo = descobreTipo(before);
			
			String ss = StringRight.ignore1(StringAfterFirst.get(s, "<"));
			
			if (ss.contains(",")) {
//				mais de um item
				throw new NaoImplementadoException();
			}
			
			TipoJava gen = descobreTipo(ss);
			
			if (gen == null) {
				descobreTipo(ss);
			}
			
			tipo.addGeneric(gen);
			return tipo;
			
		}
		
		Class<?> classe = tiposJava.get(s);
		if (classe != null) {
			return new TipoJava(classe);
		}
		
		classe = classesConhecidas.get(s);
		if (classe != null) {
			return new TipoJava(classe);
		}
		
		TipoJava tipo = getTiposConhecidos().unique(ii -> ii.eq(s));
		
		if (tipo != null) {
			return tipo.clone();
		}
		
		tipo = generics.filter(ii -> ii.eq(s)).getFirst();
		if (tipo != null) {
			return tipo.clone();
		}

		tipo = generics.filter(ii -> ii.getS().startsWith(s + " extends")).getFirst();
		if (tipo != null) {
			return tipo.clone();
		}
		
		classe = UClass.getClass(s);
		if (classe != null && classe != Object.class) {
			tiposJava.add(classe);
			return new TipoJava(classe);
		}
		
		String ss = StringPrimeiraMaiuscula.exec(s);
		
		if (!ss.contentEquals(s)) {
			return descobreTipo(ss);
		}
		
		return null;
		
	}
	
	protected static JavaLoad instance;
	
	protected JavaLoad(Class<?> classe) {
		this(UClass.getJavaFile(classe));
		ClassBox.get(classe).getImports().each(o -> imports.addIfNotContains(o));
		imports.each(o -> tiposJava.addIfNotContains(o));
		tiposJava.add(classe);
		porClasse = true;
		this.classe = classe;
	}
	
	protected JavaLoad(GFile file) {
		instance = this;
		this.file = file;
		tiposJava.add(Override.class);
		tiposJava.add(System.class);
		tiposJava.add(Object.class);
		tiposJava.add(Error.class);
		tiposJava.add(Class.class);
		palavras.funcBuildPalavra = this::build;
	}
	
	@Override
	protected ListString loadListString() {
		return new ListString().load(file);
	}
	
	@Override
	protected void exec1() {

		sinais();
		imports();
		blocos();
		lerTipoThis();
		importsImplicitos();
		acharTipos();
		casts();
		acharTipos2();
		casts();
		reticencias();
		interrogacoes();
		generics();
		reticencias();
		referenciarInvocacoes();
		declaracoes();
		referenciarInvocacoes();
		contarParametrosInvocacaoMetodo();
		detectarTiposArrow(true);
		referenciarInvocacoes();
		detectarTiposArrow(false);
		referenciarInvocacoes();
		removerEspacosDesnecessarios();
		casts();
		
		testes();
		
	}
	
	@Override
	protected void blocos() {
		
		super.blocos();
		
		Lst<AbreBloco> aberturas = filter(AbreBloco.class);
		
		while (aberturas.isNotEmpty()) {
			
			AbreBloco a = aberturas.removeLast();
			
			if (aberturas.isEmpty()) {
				break;
			}
			
			AbreBloco b = aberturas.getLast();
			
			int i = palavras.indexOf(a);
			
			while (palavras.indexOf(b.getFechamento()) < i) {
				b = aberturas.before(b);
			}
			
			a.setPai(b);
			
		}
		
	}

	private void testes() {
		filter(DeclaracaoDeVariavel.class).filter(i -> i.getTipo() == null).each(i -> {
			throw new RuntimeException("A seguinte variavel está sem tipo: " + i.getNome());
		});
	}

	@Override
	protected void preTratarListString(ListString list) {

		list.removeDoubleWhites();
		list.removeFisrtEmptys();
		list.removeLastEmptys();
		
		if (list.contains(s -> s.trim().contentEquals("public static void javaLoad_replaceTexto(ListString list) {"))) {
			ListMetodos.get(classe).get("javaLoad_replaceTexto").invoke(classe, list);
		}
		
		list.replaceTexto("<? extends MockRowRes>", "<MockRowRes>");
		
		if (config.onLoad != null) {
			config.onLoad.call(list);
		}
		
	}

	private void interrogacoes() {
		
		filter(Interrogacao.class).inverteOrdem().each(i -> {
			
			Palavra b = before(i);
			
			if (b.is(Menor.class, GenericsAbre.class)) {
				replace(i, new InterrogacaoGenerics());
				return;
			}
			
			Palavra a = after(i);
			
			while (!a.is(DoisPontos.class) ) {
				
				if (ehAlgumaAbertura(a)) {
					a = getFechamento(a);
				} else {
					a = after(a);
				}
				
			}
			
			InterrogacaoTernaria it = new InterrogacaoTernaria();
			it.setFechamento(new DoisPontosTernario());
			
			replace(i, it);
			replace(a, it.getFechamento());
			
		});		
		
	}

	private void casts() {
		
		palavras.replace(AbreParenteses.class, TipoJava.class, FechaParenteses.class).por(lst -> {
			lst.rm();
			Cast cast = new Cast(lst.rm());
			lst.rm();
			lst.add(cast);
			return lst;
		});
		
		filter(Cast.class).filter(i -> i.getFechamento() == null).each(cast -> {
			
			Palavra o = after(cast);
			
			while (true) {
				
				if (o.is(PontoEVirgula.class, OperadorComparacao.class, SinalMatematico.class, AndOr.class, FechaParenteses.class, Virgula.class)) {
					cast.setFechamento(before(o));
					return;
				}
				
				if (o.is(AbreParenteses.class)) {
					AbreParenteses abre = (AbreParenteses) o;
					o = after(abre.getFechamento());
					continue;
				}
				
				o = after(o);
				
			}
			
		});
		
	}

	private void reticencias() {
		palavras.replace(TipoJava.class, Reticencias.class).por(lst -> {
			lst.removeLast();
			TipoJava tipo = lst.get(0);
			JcTipo reticencias = JcTipo.RETICENCIAS(tipo.getType());
			tipo.setType(reticencias);
			return lst;
		});
	}

	private void acharTipos() {
		
		Lst<NaoClassificada> naoClassificadas = filter(NaoClassificada.class);
		tiposConhecidos.each(i ->
			naoClassificadas.filter(o -> o.eq(i.getSimpleName())).each(o ->
				replace(o, i.clone())
			)
		);
		
		filter(TipoJava.class).each(tipo -> {
			
			Palavra a = after(tipo);
			
			if (a.is(AbreColchetes.class)) {
				
				Palavra aa = after(a);
				
				if (aa.is(FechaColchetes.class)) {
					remove(a);
					remove(aa);
					TipoJava tp = new TipoJava(tipo.getType().getName() + "[]");
					replace(tipo, tp);
				} else {
					throw new NaoImplementadoException();
				}
				
			}
			
		});
		
	}
	
	private void acharTipos2() {
		
		int vez = 0;
		
		Lst<Palavra> pular = new Lst<>();
		
		while (true) {
			
			vez++;
			
			if (vez > 100) {
				SystemPrint.ln(vez);
			}
		
			Palavra o = filter(Extends.class).filter(i -> before(i).is(NaoClassificada.class)).getLast();
			
			if (o != null) {
				tiparGeneric(before(o));
				continue;
			}
			
			/*===================================*/
			
			o = filter(Extends.class).filter(i -> before(i).is(TipoJava.class) && !pular.contains(i)).getLast();
			
			if (o != null) {
				pular.add(o);
				o = before(o);
				tiparGenericComTipo(o, (TipoJava) o);
				continue;
			}
			
			/*===================================*/
			
			o = filter(Virgula.class).filter(i -> after(i).is(NaoClassificada.class) && before(i).is(TipoJava.class)).getLast();

			if (o != null) {
				tiparGeneric(before(o));
				continue;
			}
			
			/*===================================*/
			
			o = filter(Menor.class).filter(i -> after(i).is(NaoClassificada.class) && before(i).is(TipoJava.class)).getLast();

			if (o != null) {
				tiparGeneric(before(o));
				continue;
			}
			
			/*===================================*/
			
			o = filter(GenericsAbre.class).filter(i -> 
				before(i).is(TipoJava.class) &&
				after(i).is(TipoJava.class) &&
				after(after(i)).is(GenericsFecha.class)
			).getLast();

			if (o != null) {
				TipoJava tipo = before(o);
				tipo.addGeneric(removeAfter(o));
				removeAfter(o);
				remove(o);
				continue;
			}
			
			/*===================================*/
			
			o = filter(Extends.class).filter(i -> 
				before(i).is(TipoJava.class) &&
				after(i).is(TipoJava.class) &&
				after(after(i)).is(GenericsFecha.class)
			).getLast();
		
			if (o != null) {
				TipoJava tipo = before(o);
				tipo.setExtendss(removeAfter(o));
				remove(o);
				continue;
			}
			
			/*===================================*/
			
			o = filter(TipoJava.class).filter(i -> 
				after(i).is(GenericsAbre.class) &&
				after(after(i)).is(TipoJava.class) &&
				after(after(after(i))).is(Virgula.class)
			).getLast();

			if (o != null) {
				TipoJava tipo = (TipoJava) o;
				TipoJava tipoa = after(after(o));
				tipo.addGeneric(tipoa);
				removeAfter(tipoa);
				remove(tipoa);
				continue;
			}
			
			/*===================================*/
			
			o = filter(TipoJava.class).filter(i -> 
				after(i).is(GenericsAbre.class) &&
				after(after(i)).is(TipoJava.class) &&
				after(after(after(i))).is(GenericsFecha.class)
			).getLast();
		
			if (o != null) {
				TipoJava tipo = (TipoJava) o;
				removeAfter(tipo);
				tipo.addGeneric(removeAfter(tipo));
				removeAfter(tipo);
				continue;
			}

			/*===================================*/

			o = filter(Class_.class).filter(i ->
				!pular.contains(i) &&
				after(i).is(TipoJava.class) &&
				((TipoJava) after(i)).hasGenerics()
			).getLast();
		
			if (o != null) {
				pular.add(o);
				TipoJava tipo = after(o);
				tipo.getGenerics().forEach(gen -> {
					tiparGenericComTipo(tipo, gen);
					generics.add(gen);
				});
				continue;
			}
	
			/*===================================*/
			
			break;
			
		}
		
	}

	
	private TipoJava tipoThis;

	private void lerTipoThis() {

		if (porClasse) {
			tipoThis = new TipoJava(classe);
			return;
		}
		
		Class_ c = palavras.filter(Class_.class).get(0);
		
		Palavra o = after(c);
		
		if (o instanceof Abstract) {
			o = after(o);
		}
		
		if (o instanceof NaoClassificada) {
			getPkg();
			JcTipo jcTipo = new JcTipo(pkg + "." + o.getS());
			TipoJava tipo = new TipoJava(jcTipo);
			replace(o, tipo);
			tiposConhecidos.add(tipo);
			tipoThis = tipo;
		}
		
	}
	
	private String pkg;
	
	public String getPkg() {
		
		if (pkg == null) {
			Lst<Palavra> row = getPackageRow();
			row.remove(0);
			row.removeLast();
			pkg = row.toString(i -> i.getS(), "");
		}
		
		return pkg;
		
	}
	
	public Lst<Palavra> getPackageRow() {
		
		Lst<Palavra> lst = new Lst<>();
		
		Palavra o = filter(Package_.class).get(0);
		
		lst.add(o);
		
		while (!(o instanceof PontoEVirgula)) {
			o = after(o);
			lst.add(o);
		}
		
		return lst;
		
	}

	private void contarParametrosInvocacaoMetodo() {
		
		filter(InvocacaoDeUmMetodo.class).each(i -> {
			
			i.setInicioParametros(new Lst<>());
			
			AbreParenteses abre = after(i);
			
			Palavra o = after(abre);
			
			if (o == abre.getFechamento()) {
				return;
			}
			
			if (i.getS().contentEquals("map")) {
				Palavra x = after(o);
				if (x.getS().trim().contentEquals("ii")) {
					debug();
				}
				
			}
			
			i.getInicioParametros().add(abre);
			
			while (o != abre.getFechamento()) {
				
				if (o.is(Virgula.class)) {
					
					if ((i.getParametrosCount() == 1) && i.getS().contentEquals("map")) {
						debug();
					}
					
					i.getInicioParametros().add(o);
				} else if (o.is(AbreArrow.class)) {
					AbreArrow a = (AbreArrow) o;
					o = a.getFechamentoArrow();
				} else if (o.is(AbreParenteses.class)) {
					AbreParenteses a = (AbreParenteses) o;
					o = a.getFechamento();
				}
				
				o = after(o);
				
			}
			
			
		});
		
	}
	
	private Type getClasseReferencia(Palavra o) {
		
		if (o.is(Ponto.class)) {
			
			after(o);
			
			o = before(o);
			
			if (o.is(This.class, Super.class)) {
				return classe;
			}
			if (o.is(InvocacaoDeUmAtributo.class)) {
				InvocacaoDeUmAtributo x = (InvocacaoDeUmAtributo) o;
				if (x.getAtributo() != null) {
					return x.getAtributo().getType();
				}
				return null;
			}
			if (o.is(DeclaracaoDeVariavelRef.class)) {
				DeclaracaoDeVariavelRef x = (DeclaracaoDeVariavelRef) o;
				return x.getDec().getTipo().getTypeClass();
			}
			if (o.is(TipoJava.class)) {
				TipoJava x = (TipoJava) o;
				return x.getTypeClass();
			}
			if (!o.is(FechaParenteses.class)) {
//				after(o) bef
				throw new NaoImplementadoException(o.getS() + " - " + o.getClass().getSimpleName());
			}
			FechaParenteses fecha = (FechaParenteses) o;
			Palavra before = before(fecha.getAbertura());
			
			if (!(before instanceof InvocacaoDeUmMetodo)) {
				return resolveTipoOperacao(fecha.getAbertura());
			}
			InvocacaoDeUmMetodo x = (InvocacaoDeUmMetodo) before;
			if (x.getMetodo() != null) {
				return x.getMetodo().retorno().getClasse();
			}
			
			return null;
			
		}
		
		if (terminaInstrucao(o)) {
			return classe;
		}
		
		throw new NaoImplementadoException(o.getS());
		
	}
	
	private boolean terminaInstrucao(Palavra o) {
		return o.is(AbreBloco.class, AbreParenteses.class, Igual.class, Return.class, Virgula.class, OperadorComparacao.class, PontoEVirgula.class, FechaBloco.class, Exclamacao.class, AndOr.class, New.class, SinalMatematico.class, Throw.class, InterrogacaoTernaria.class, DoisPontosTernario.class, MaisIgual.class);
	}

	private Type resolveTipoOperacao(final AbreParenteses abre) {
		
		Lst<Palavra> itens = new Lst<>();
		Palavra o = after(abre);
		while (o != abre.getFechamento()) {
			itens.add(o);
			o = after(o);
		}
		
		if (itens.get(0).is(StringLiteral.class)) {
			itens.remove(0);
			o = itens.remove(0);
			if (o.is(Mais.class)) {
				itens.remove(0);
				o = itens.remove(0);
				
				if (o.is(AbreParenteses.class)) {
					
					AbreParenteses a = (AbreParenteses) o;
					
					while (itens.get(0) != a.getFechamento()) {
						itens.remove(0);
					}
					
					itens.remove(0);
					
					if (itens.isEmpty()) {
						return String.class;
					}
					
				}
				
			}
			
		} else if (itens.get(0).is(Null.class)) {
			return Object.class;
		}
		
		//resolveTipoOperacao(abre);
		
		throw new NaoImplementadoException();
		
	}
	
//	private static int vez;

	private void referenciarInvocacoes() {
		
//		Print.ln("vez" + (vez++));
		
		BooleanWrapper executouAlgum = new BooleanWrapper();
		
		filter(InvocacaoDeUmAtributo.class).filter(i -> i.getAtributo() == null).each(i -> {
			
//			vez++;
//			Print.ln(vez);

			Palavra o = before(i);
			
			Type classe;
			
			try {
				classe = getClasseReferencia(o);
			} catch (Exception e) {
				
				try {
					getClasseReferencia(o);
				} catch (Exception e2) {
					// TODO: handle exception
				}
				
				classe = null;
			}
			
			if (classe == null || classe == Arrow.class || classe == java.lang.System.class || classe == Object.class ) {
				return;
			}

//			if (vez == 35) {
//				Print.ln(vez);
//			}
			
			Atributos as = AtributosBuild.get(classe);
			
			if (as.get(i.getS()) == null) {
				debug();
			}
			
			i.setAtributo(as.getObrig(i.getS()));
			executouAlgum.set(true);
			
		});
		
		if (executouAlgum.isTrue()) {
			referenciarInvocacoes();
			return;
		}
		
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.getMetodo() == null && i.getInicioParametros() != null && i.getNewTipo() == null).each(i -> {
			
			if (i.getS().contentEquals("super") || i.getS().contentEquals("getClass")) {
				return;
			}
			
			Palavra o = before(i);
			
			if (o.is(FechaParenteses.class)) {
				return;
			}
			
			Type classe = getClasseReferencia(o);
			
			if (classe == Arrow.class) {
				return;
			}
			
			if (classe == null) {
				getClasseReferencia(o);
				return;
			}
			
			Metodos metodoss = ListMetodos.get(classe).filter(m -> m.getName().contentEquals(i.getS()));
			
			if (metodoss.size() == 1) {
				i.setMetodo(metodoss.get(0));
				executouAlgum.set(true);
				throw new BreakLoop();
			}
			
//			metodoss.get(0).getParametros();
//			metodoss.get(0).getParameterCount();
			Metodos metodos = metodoss.filter(m -> m.getParameterCount() == i.getParametrosCount());
			
			if (metodos.isEmpty()) {
				
				if (i.getS().contentEquals("ok") && i.getParametrosCount() > 9) {
					metodos.add(metodoss.unique(m -> m.toString().endsWith("(react.support.ReactNode[])")));
				} else {
					return;
//					i.getParametrosCount();
//					throw new NaoImplementadoException(i.getS() + " / " + i.getParametrosCount());
				}
				
			}
			
			if (metodos.size() == 1) {
				i.setMetodo(metodos.get(0));
				executouAlgum.set(true);
				throw new BreakLoop();
			}
			
			i.getInicioParametros().eachi((ini, index) -> {

				Palavra oo = after(ini);
				
				if (oo.is(AbreArrow.class)) {
					
					AbreArrow aa = (AbreArrow) oo;
					
					metodos.removeIf(m -> !m.getParametros().get(index).isArrow());
					metodos.removeIf(m -> m.getParametros().get(index).getArrowParamsCount() != aa.getArrow().getParamsCount());
					
					if (metodos.size() == 1) {
						i.setMetodo(metodos.get(0));
						executouAlgum.set(true);
						throw new BreakLoop();
					}
					
				}
				
			});
			
		});
		
		if (executouAlgum.isTrue()) {
			referenciarInvocacoes();
		}
		
	}

	private void removerEspacosDesnecessarios() {

		filter(AbreParenteses.class).each(p -> {
			Palavra o = after(p);
			if (o.getQuebras() == 0) {
				o.clearIdentacao();
			}
		});
		
	}
	
//	int vez = 0;
	
	private void detectarTiposArrow(boolean antesDeReferenciar) {
		
		filter(DeclaracaoDeVariavel.class).filter(i -> i.getTipo().eq(Arrow.class)).inverteOrdem().each(i -> {
			
//			vez++;
//			Print.ln(vez);
//			
//			if (vez == 24) {
//				Print.ln();
//			}
			
			{
				Palavra o = before(i);
				
				int pos = 0;
				
				while (o.is(Virgula.class)) {
					o = before(o);
					o = before(o);
					pos++;
				}
				
				if (o.is(AbreParenteses.class)) {
					o = before(o);
				}
				
				if (o.is(Igual.class)) {
					
					o = before(o);
					
					if (o.is(DeclaracaoDeVariavel.class)) {
						DeclaracaoDeVariavel d = (DeclaracaoDeVariavel) o;
						TipoJava tipo = d.getTipo().getGenerics().get(pos);
						i.setTipo(tipo);
						return;
					}
					
				}
				
				if (o.is(AbreArrow.class)) {
					
					o = before(o);
					
					if (o.is(AbreParenteses.class)) {
					
						o = before(o);
						
						if (o.is(InvocacaoDeUmMetodo.class)) {
							
							InvocacaoDeUmMetodo inv = (InvocacaoDeUmMetodo) o;
							
							if (inv.eq("unique")) {
								Palavra x = before(o);
								if (x.is(Ponto.class)) {
									x = before(x);
									if (x.is(FechaParenteses.class)) {
										FechaParenteses fecha = (FechaParenteses) x;
										x = before(fecha.getAbertura());
										if (x.eq("get")) {
											x = before(x);
											if (x.is(Ponto.class)) {
												x = before(x);
												
												if (x.is(DeclaracaoDeVariavelRef.class)) {
													DeclaracaoDeVariavelRef ref = (DeclaracaoDeVariavelRef) x;
													DeclaracaoDeVariavel dec = ref.getDec();
													
													TipoJava tipo = dec.getTipo();
													
													if (tipo.getSimpleName().contentEquals("Prop")) {
														tipo = tipo.getGenerics(0);
														if (tipo.eq(Itens.class)) {
															tipo = tipo.getGenerics(0);
															i.setTipo(tipo);
															return;
														}
													}
												}
											}
										}
									}
								}
							}
							
							if (inv.getMetodo() == null && inv.getNewTipo() == null) {
								
								if (!antesDeReferenciar) {
									
//									Isso está errado, não ligar. O tipo da var não é ReactNode, o metodo é que deve retornar um ReactNode
//									if (inv.getS().contentEquals("nodes")) {
//										TipoJava tipo = new TipoJava(ReactNode.class);
//										i.setTipo(tipo);
//										return;
//									}
									
									if (i.getNome().getS().contentEquals("s")) {
										TipoJava tipo = new TipoJava(String.class);
										i.setTipo(tipo);
										return;
									}
									
									i.getTipoNoComentario();
									i.getPossivelTipoNoComentario();
									throw new RuntimeException("Não foi possivel determinar o tipo arrow " + i.getNome().getS());
								}
								
							} else {
								
								if (inv.getMetodo() == null) {
									TipoJava tipo = inv.getTipoNewTipoJava();
									i.setTipo(tipo.clone());
									return;
								}

								Class<?> type = inv.getMetodo().getParametros().get(0).getType();

								if ((type.getPackage() != F1.class.getPackage()) && (type != java.util.function.Consumer.class) && (type != java.util.function.Predicate.class)) {
									throw new NaoImplementadoException();
								}
								o = before(o);
								
								if (o.is(Ponto.class)) {
									
									o = before(o);
									
									if (o.is(This.class)) {
										throw new NaoImplementadoException();//implements T
									}
									if (o.is(DeclaracaoDeVariavelRef.class)) {
										DeclaracaoDeVariavelRef dec = (DeclaracaoDeVariavelRef) o;
										TipoJava tipo = getGenerics(dec.getDec().getTipo());
										i.setTipo(tipo.clone());
										return;
									}
									
								} else if (terminaInstrucao(o)) {
									Parametro param = inv.getMetodo().getParametros().get(0);
									String s = param.getParameterizedType().getTypeName();
									s = StringAfterFirst.get(s, "<");
									s = StringBeforeLast.get(s, ">");
									
									ListString list = ListString.separaPalavras(s);
									list.trimPlus();
									
									ListString itens = new ListString();
									
									s = "";
									
									while (!list.isEmpty()) {
										
										String ss = list.remove(0);
										
										if (ss.contentEquals(",")) {
											itens.add(s);
											s = "";
											continue;
										}
										
										s += ss;
										
										if (ss.contentEquals("<")) {
											
											int opens = 1;
											while (opens > 0) {
												ss = list.remove(0);
												s += ss;
												if (ss.contains("<")) {
													opens++;
												} else if (ss.contains(">")) {
													opens--;
												}
											}
											
										}
										
									}
									
									itens.add(s);
									
									String sss = itens.get(pos);
									
									if (sss.contentEquals("K")) {
										debug();
									}
									
									TipoJava tipo = descobreTipo(sss);
									
									if (tipo != null) {
										i.setTipo(tipo);
										return;
									}
									
									debug();
									
//										if (!antesDeReferenciar) {
//											throw new RuntimeException();
//										}										
								}
								
							}
						}
					}
				}
			}

			for (DeclaracaoDeVariavelRef ref : i.getRefs()) {

				Palavra o = after(ref);
				
				if (o.is(Instanceof.class)) {
					return;
				} else if (o.is(Ponto.class)) {
					o = after(o);
					if (o.is(InvocacaoDeUmMetodo.class) && o.getS().contentEquals("length")) {
						i.getTipo().setType(String.class);
						return;
					}
				} else if (o.is(MaisIgual.class, MenosIgual.class)) {
					
					o = after(o);
					
					if (after(o).is(PontoEVirgula.class)) {
						
						if (o.is(StringLiteral.class)) {
							i.getTipo().setType(String.class);
							return;
						}

						if (o.is(InteiroLiteral.class)) {
							i.getTipo().setType(Integer.class);
							return;
						}

						if (o.is(LongLiteral.class)) {
							i.getTipo().setType(Long.class);
							return;
						}

						if (o.is(DoubleLiteral.class)) {
							i.getTipo().setType(Double.class);
							return;
						}
						
						if (o.is(DeclaracaoDeVariavelRef.class)) {
							DeclaracaoDeVariavelRef reff = (DeclaracaoDeVariavelRef) o;
							DeclaracaoDeVariavel dec = reff.getDec();
							dec.addTipoObserver(tipo -> i.getTipo().setType(tipo.getType()));
							return;
						}
						
						throw new NaoImplementadoException();
						
					}
					
				} else if (o.is(Virgula.class)) {
					
					while (!o.is(AbreParenteses.class)) {
						o = before(o);
						if (o.is(FechaParenteses.class)) {
							FechaParenteses fecha = (FechaParenteses) o;
							o = fecha.getAbertura();
							o = before(o);
						}
					}
					
					o = before(o);
					
					InvocacaoDeUmMetodo inv = (InvocacaoDeUmMetodo) o;
					detectarTiposArrowInvocacaoDeUmMetodo(i, antesDeReferenciar, inv);
					return;
					
				} else if (o.is(FechaParenteses.class)) {
					FechaParenteses fecha = (FechaParenteses) o;
					o = before(fecha.getAbertura());
					if (o.is(InvocacaoDeUmMetodo.class)) {
						InvocacaoDeUmMetodo inv = (InvocacaoDeUmMetodo) o;
						detectarTiposArrowInvocacaoDeUmMetodo(i, antesDeReferenciar, inv);
						return;
					}
				} else if (!antesDeReferenciar) {
					after(ref);
					before(ref);
					before(before(ref));
					throw new NaoImplementadoException();
				}
				
			}
			
		});
		
	}
	
	protected TipoJava getGenerics(TipoJava tipo) {
		return tipo.getGenerics(0);
	}

	private void detectarTiposArrowInvocacaoDeUmMetodo(DeclaracaoDeVariavel i, boolean antesDeReferenciar, InvocacaoDeUmMetodo inv) {
		
		if (inv.getNewTipo() != null) {
			Type type = inv.getTipoNewTipo().getClasse().getType();
			i.getTipo().setType(type);
			return;
		}
		
		if (inv.getMetodo() == null) {
			if (antesDeReferenciar) {
				return;
			}
			throw new NullPointerException("inv.getMetodo() == null");
		}
		
		if (inv.getMetodo().getName().contentEquals("call")) {
			Ponto ponto = before(inv);
			Palavra o = before(ponto);
			
			if (!o.is(DeclaracaoDeVariavelRef.class)) {
				throw new NaoImplementadoException();
			}
			DeclaracaoDeVariavelRef ref2 = (DeclaracaoDeVariavelRef) o;
			TipoJava tipoJava = ref2.getDec().getTipo();
			Lst<TipoJava> generics = tipoJava.getGenerics().copy();
			if (tipoJava.getSimpleName().startsWith("F")) {
				generics.removeLast();
			}
			TipoJava tipo = generics.getLast();
			i.setTipo(tipo.clone());
			return;
			
		}
		
		Type type = inv.getMetodo().getParametros().getLast().getParameterizedType();
		i.getTipo().setType(type);
		
	}
	
	private void tiparGenericComTipo(Palavra inicio, TipoJava tipo) {

		Palavra o = after(inicio);
		
		while (!o.is(AbreBloco.class)) {
			
			if (o.is(NaoClassificada.class) && o.eq(tipo.getS())) {
				TipoJava clone = tipo.clone();
				replace(o, clone);
				o = clone;
			}
			
			o = after(o);
			
		}
		
		AbreBloco abre = (AbreBloco) o;
		
		o = after(o);
		
		while (o != abre.getFechamento()) {

			if (o.is(NaoClassificada.class) && o.eq(tipo.getS())) {
				TipoJava clone = tipo.clone();
				replace(o, clone);
				o = clone;
			}
			
			o = after(o);
			
		}		
	}
	
	private void tiparGeneric(NaoClassificada origem) {
		TipoJava tipo = new TipoJava(origem.getS());
		replace(origem, tipo);
		tiparGenericComTipo(tipo, tipo);
	}

	private void generics() {
		
		//Menor, TipoJava, Extends, TipoJava, Maior
		filter(Menor.class).each(menor -> {
			Palavra tipoJava1 = after(menor);
			if (tipoJava1 instanceof TipoJava) {
				Palavra extendss = after(tipoJava1);
				if (extendss instanceof Extends) {
					Palavra tipoJava2 = after(extendss);
					if (tipoJava2 instanceof TipoJava) {
						Palavra maior = after(tipoJava2);
						if (maior instanceof Maior) {
							TipoJava t1 = (TipoJava) tipoJava1;
							TipoJava t2 = (TipoJava) tipoJava2;
							remove(extendss);
							remove(t2);
							t1.setExtendss(t2);
							GenericsFecha fecha = new GenericsFecha(new GenericsAbre());
							replace(maior, fecha);
							replace(menor, fecha.getAbertura());
						}
					}
				}
			}
		});
		
		int indexBloco = palavras.indexOf(filter(AbreBloco.class).getFirst());

		
		filter(Menor.class).filter(menor -> palavras.indexOf(menor) < indexBloco).inverteOrdem().each(menor -> {
			
			Palavra o = after(menor);
			TipoJava tipo;
			
			if (o instanceof TipoJava) {
				tipo = (TipoJava) o;
			} else {
				tipo = new TipoJava(o.getS());
				o = replace(o, tipo);
				
				if (after(o).is(Extends.class)) {
					removeAfter(o);
					tipo.setExtendss(removeAfter(o));
				}
				
			}
			
			generics.add(tipo);
			
			int opens = 1;
			while (opens > 0) {
				
				o = after(o);
				
				if (ehAlgumaAbertura(o)) {
					o = getFechamento(o);
					continue;
				}
				
				if (o.is(Maior.class)) {
					opens--;
				} else if (o.is(Menor.class)) {
					opens++;
				} else if (o.is(NaoClassificada.class)) {
					tipo = new TipoJava(o.getS());
					generics.add(tipo);
					o = replace(o, tipo);
				} else if (o.is(TipoJava.class)) {
					tipo = (TipoJava) o;
					generics.add(tipo);
				} else if (!o.is(Virgula.class)) {
					throw new NaoImplementadoException();
				}
			}
			
			Maior maior = (Maior) o;
			GenericsAbre genericsAbre = new GenericsAbre();
			
			replace(menor, genericsAbre);
			replace(maior, genericsAbre.getFechamento());
			
		});
		
		filter(GenericsAbre.class).filter(menor -> palavras.indexOf(menor) < indexBloco).inverteOrdem().each(menor -> {
			
			Palavra o = after(menor);
			TipoJava tipo;
			
			if (o instanceof TipoJava) {
				tipo = (TipoJava) o;
			} else {
				tipo = new TipoJava(o.getS());
				o = replace(o, tipo);
				
				if (after(o).is(Extends.class)) {
					removeAfter(o);
					tipo.setExtendss(removeAfter(o));
				}
				
			}
			
			generics.add(tipo);
			
			int opens = 1;
			while (opens > 0) {
				
				o = after(o);
				
				if (ehAlgumaAbertura(o)) {
					o = getFechamento(o);
					continue;
				}
				
				if (o.is(Virgula.class)) {
					continue;
				}
				
				if (o.is(GenericsFecha.class)) {
					opens--;
					continue;
				}
				
				if (o.is(GenericsAbre.class)) {
					opens++;
					continue;
				}

				if (o.is(NaoClassificada.class)) {
					tipo = new TipoJava(o.getS());
					generics.add(tipo);
					o = replace(o, tipo);
					continue;
				}
				
				if (o.is(TipoJava.class)) {
					tipo = (TipoJava) o;
					generics.add(tipo);
					continue;
				}
				
				if (o.is(Extends.class)) {
					tipo.setExtendss(removeAfter(o));
					remove(o);
					o = tipo;
					SystemPrint.ln(tipo.getS());
					continue;
				}
				
//				tipo.setExtendss(tipo)
				
				throw new NaoImplementadoException();
				
			}
			
		});		
		
		generics.each(o ->
			filter(NaoClassificada.class)
			.filter(i -> i.eq(o.getS()))
			.each(i -> replace(i, new TipoJava(o.getS())))
		);
		
		filter(o -> o.is(Menor.class, GenericsAbre.class)).each(menor -> {
			if (before(menor).is(TipoJava.class)) {
				Palavra o = after(menor);
				if (o.is(NaoClassificada.class, Interrogacao.class, InterrogacaoGenerics.class)) {
					TipoJava tipo = new TipoJava(o.getS());
					replace(o, tipo);
				}
			}
		});
		
		genericsMethods();
		
		filter(o -> o.is(Maior.class, GenericsFecha.class)).each(o -> {
			
			if (before(o).is(TipoJava.class)) {

				Lst<TipoJava> tipos = new Lst<>();
				
				while (!before(o).is(Menor.class, GenericsAbre.class)) {
					
					if (!before(o).is(TipoJava.class)) {
						debug();
					}
					
					if (before(o).is(AnnotationJava.class)) {

						AnnotationJava ann = removeBefore(o);
						
						if (ann.eq("@NotNull")) {
							
							if (tipos.get(0).eq(Integer.class)) {
								tipos.remove(0);
								tipos.add(0, new TipoJava(int.class));
								continue;
							}

							if (tipos.get(0).eq(String.class)) {
								tipos.get(0).setNotNull(true);
								continue;
							}
							
							throw new NaoImplementadoException();
						}
						
						throw new NaoImplementadoException();
						
					}
					
					tipos.add(0, removeBefore(o));
					if (before(o).is(Virgula.class)) {
						removeBefore(o);
					}
				}
				
				removeBefore(o);
				
				if (!before(o).is(TipoJava.class)) {
					debug();
				}
				
				TipoJava tipo = before(o);
				remove(o);
				tipo.addGenerics(tipos);
				
			}
			
		});
		
		filter(Menor.class).filter(menor -> after(menor).eq("?")).each(menor -> {
			Palavra before = before(menor);
			SystemPrint.ln(before);
			SystemPrint.ln(menor);
			SystemPrint.ln(menor);
			SystemPrint.ln(menor);
		});
		
		filter(GenericsAbre.class).filter(ga -> before(ga).is(TipoJava.class)).each(ga -> {
			SystemPrint.ln(ga);
		});
		
	}
	
	private void genericsMethods() {
		
		filter(Menor.class)
		.filter(o -> after(o).is(NaoClassificada.class))
		.filter(o -> before(o).is(ModificadorDeAcesso.class, Static.class, Final.class))
		.each(m -> {
		
			Palavra o = after(m);
			
			String s = o.getS();
			
			JcTipo jcTipo = JcTipo.GENERICS(s);
			
			TipoJava tipo = new TipoJava(jcTipo);
			
			genericasMethod(m, o, s, tipo);
			
		});
		
		filter(GenericsAbre.class)
		.filter(o -> after(o).is(TipoJava.class))
		.filter(o -> before(o).is(ModificadorDeAcesso.class, Static.class, Final.class))
		.each(m -> {
		
			TipoJava o = after(m);
			
			String s = o.getS();
			
			if (s.contains(" ") && StringAfterFirst.get(s, " ").startsWith("extends ")) {
				s = StringBeforeFirst.get(s, " ");
			}
			
			genericasMethod(m, o, s, o);
			
		});
		
	}

	private void genericasMethod(Palavra genericosOpen, Palavra o, String s, TipoJava tipo) {
		DeclaracaoDeUmTipoGenerico d = new DeclaracaoDeUmTipoGenerico(tipo);
		replace(genericosOpen, d);
		remove(o);
		
		if (!after(d).is(Maior.class, GenericsFecha.class)) {
			throw new NaoImplementadoException();
		}
		
		removeAfter(d);
		
		o = after(d);
		
		while (!o.is(PontoEVirgula.class, AbreBloco.class)) {
			
			if (o.getS().contentEquals(s)) {

				if (!o.is(NaoClassificada.class, TipoJava.class)) {
					debug();
					throw new NaoImplementadoException();
				}
				TipoJava t = tipo.clone();
				replace(o, t);
				o = t;
				
			}

			o = after(o);
			
		}
		
		if (o.is(AbreBloco.class)) {
			AbreBloco abre = (AbreBloco) o;
			o = after(o);
			while (o != abre.getFechamento()) {

				if (o.is(NaoClassificada.class) && o.getS().contentEquals(s)) {
					TipoJava t = tipo.clone();
					replace(o, t);
					o = t;
				}
				
				o = after(o);
				
			}
		}
	}
	
	private void ajustaMaiorParaOpenGenerics() {
		
		Menor menor = filter(Menor.class).filter(i -> before(i).is(TipoJava.class)).getFirst();
		
		if (menor == null) {
			return;
		}

		GenericsAbre genericsAbre = new GenericsAbre();
		replace(menor, genericsAbre);
		
		int opens = 1;
		
		Palavra o = genericsAbre;
		
		while (opens > 0) {
			
			o = after(o);
			
			if (o.is(Menor.class)) {
				opens++;
			} else if (o.is(Maior.class)) {
				opens--;
			}
			
		}
		
		replace(o, genericsAbre.getFechamento());
		
		Palavra item = genericsAbre;
		
		while (true) {
			
			item = after(item);
			
			if (item == genericsAbre.getFechamento()) {
				break;
			}
			
			if (item.is(Virgula.class)) {
				continue;
			}
			
			o = item;
			
			if (o.is(NaoClassificada.class)) {
				TipoJava tipo = new TipoJava(o.getS());
				replace(o, tipo);
				item = tipo;
				o = after(tipo);
				
				AbreChaves abre = null;
				
				while (abre == null || o != abre.getFechamento()) {
					
					if (abre == null && o.is(AbreChaves.class)) {
						abre = (AbreChaves) o;
						o = after(o);
					}
					
					if (o.is(NaoClassificada.class) && o.eq(tipo.getS())) {
						TipoJava clone = tipo.clone();
						replace(o, clone);
						o = clone;
					}
					
					o = after(o);
					
				}
				
			}			
			
		}
		
		ajustaMaiorParaOpenGenerics();
		
	}

	private void sinais() {
		palavras.replace(Igual.class, Igual.class).porNew(() -> new IgualComparacao());
		palavras.replace(Exclamacao.class, Igual.class).porNew(() -> new Diferente());
		palavras.replace(Menos.class, Maior.class).porNew(() -> new Arrow());
		palavras.replace(EComercial.class, EComercial.class).porNew(() -> new And());
		palavras.replace(Pipe.class, Pipe.class).porNew(() -> new Or());
		palavras.replace(Menor.class, Maior.class).porNew(() -> new Diamond());
		palavras.replace(Mais.class, Igual.class).porNew(() -> new MaisIgual());
		palavras.replace(Menos.class, Igual.class).porNew(() -> new MenosIgual());
		palavras.replace(Mais.class, Mais.class).porNew(() -> new MaisMais());
		palavras.replace(Menos.class, Menos.class).porNew(() -> new MenosMenos());
		palavras.replace(Ponto.class, Ponto.class, Ponto.class).porNew(() -> new Reticencias());
		ajustaMaiorParaOpenGenerics();
		interrogacoes();
	}

	private boolean ehAlgumaAbertura(Palavra o) {
		return o.is(AbreParenteses.class, AbreChaves.class, AbreColchetes.class, AbreBloco.class, GenericsAbre.class);
	}

	private Palavra getFechamento(Palavra o) {
		
		if (!ehAlgumaAbertura(o)) {
			throw new RuntimeException("Não é uma abertura: " + o);
		}
		
		if (o.is(AbreParenteses.class)) {
			AbreParenteses abre = (AbreParenteses) o;
			return abre.getFechamento();
		}
		
		if (o.is(AbreChaves.class)) {
			AbreChaves abre = (AbreChaves) o;
			return abre.getFechamento();
		}

		if (o.is(AbreColchetes.class)) {
			AbreColchetes abre = (AbreColchetes) o;
			return abre.getFechamento();
		}

		if (o.is(AbreBloco.class)) {
			AbreBloco abre = (AbreBloco) o;
			return abre.getFechamento();
		}

		if (o.is(GenericsAbre.class)) {
			GenericsAbre abre = (GenericsAbre) o;
			return abre.getFechamento();
		}
		
		throw new NaoImplementadoException();
		
	}
	
	private void importsImplicitos() {
		getPkg();
		Lst<GFile> files = file.getOutrosArquivosMesmaPath();
		for (GFile f : files) {
			String s = pkg + "." + f.getSimpleNameWithoutExtension();
			JcTipo jcTipo = new JcTipo(s);
			TipoJava tipoJava = new TipoJava(jcTipo);
			tiposConhecidos.add(tipoJava);
		}
	}
	
	private void imports() {
		
		ListClass imps = imports.copy();
		
		filter(Import.class).each(i -> {

			Palavra o = palavras.removeAfter(i);
			String s = "";
			
			while (!(o instanceof PontoEVirgula)) {
				s += o.getS();
				o = palavras.removeAfter(i);
				
				if (o == null) {
					throw new RuntimeException();
				}
				
			}
			
			Class<?> cs = tiposJava.get(s);
			
			if (cs == null) {
				cs = UClass.getClass(s);
				if (cs != null) {
					tiposJava.add(cs);
				}
			}
			
			if (cs == null) {
				JcTipo tipo = new JcTipo(s);
				i.setTipo(new TipoJava(tipo));
			} else {
				i.setTipo(new TipoJava(cs));
				imps.remove(cs);
			}
			
			tiposConhecidos.add(i.getTipo());
			
		});
		
		Palavra p;
		
		{
			Palavra o = filter(Import.class).getLast();
			if (o == null) {
				p = filter(PontoEVirgula.class).getFirst();
			} else {
				p = o;
			}
		}
		
		imps.each(cs -> {
			Import i = new Import();
			i.setTipo(new TipoJava(cs));
			i.incQuebra();
			palavras.addAfter(p, i);
			tiposConhecidos.add(i.getTipo());
		});
		
	}

	private void declaracoes() {
		
		Box<Boolean> deveContinuar = new Box<>();
		deveContinuar.set(true);
		
		while (deveContinuar.get()) {

			deveContinuar.set(false);

			palavras.replace(ModificadorDeAcesso.class, Static.class).por(lst -> {
				ModificadorDeAcesso m = lst.get(0);
				lst.remove(1);
				m.setStatic(true);
				deveContinuar.set(true);
				return lst;
			});
			
			palavras.replace(ModificadorDeAcesso.class, Synchronized.class).por(lst -> {
				ModificadorDeAcesso m = lst.get(0);
				lst.remove(1);
				m.setSynchronized(true);
				deveContinuar.set(true);
				return lst;
			});

			palavras.replace(ModificadorDeAcesso.class, Final.class).por(lst -> {
				ModificadorDeAcesso m = lst.get(0);
				lst.remove(1);
				m.setFinal(true);
				deveContinuar.set(true);
				return lst;
			});

			palavras.replace(ModificadorDeAcesso.class, Abstract.class).por(lst -> {
				ModificadorDeAcesso m = lst.get(0);
				lst.remove(1);
				m.setAbstract(true);
				deveContinuar.set(true);
				return lst;
			});
			
		}
		
		palavras.replace(ModificadorDeAcesso.class, Static.class).por(lst -> {
			ModificadorDeAcesso m = lst.get(0);
			lst.remove(1);
			m.setStatic(true);
			return lst;
		});
		
		Is0.func = o -> o.is(PontoEVirgula.class, Igual.class, Virgula.class, FechaParenteses.class, DoisPontos.class);
		
		palavras.replace(ModificadorDeAcesso.class, TipoJava.class, NaoClassificada.class, Is0.class).por(lst -> {
			ModificadorDeAcesso m = lst.rm();
			TipoJava t = lst.rm();
			NaoClassificada n = lst.rm();
			lst.add(0, new DeclaracaoDeVariavel(m, t, n));
			return lst;
		});

		palavras.replace(TipoJava.class, NaoClassificada.class, Is0.class).por(lst -> {
			TipoJava t = lst.rm();
			NaoClassificada n = lst.rm();
			lst.add(0, new DeclaracaoDeVariavel(t, n));
			return lst;
		});

		
		/*
			esse cenario pode acontecer quando o nome de uma variavel eh o mesmo nome de uma classe
			que foi declarada com letra minuscula
		*/
		palavras.replace(TipoJava.class, TipoJava.class, Is0.class).por(lst -> {
			
			String s = lst.get(1).getS().substring(0,1);
			
			if (s.toLowerCase().contentEquals(s)) {
				TipoJava t = lst.rm();
				TipoJava tt = lst.rm();
				NaoClassificada n = new NaoClassificada(tt.getS());
				lst.add(0, new DeclaracaoDeVariavel(t, n));
			}
			
			return lst;
		});
		
		filter(For.class).each(m -> {
			AbreParenteses abre = after(m);
			DeclaracaoDeVariavel dec = after(abre);
			dec.setEscopo(DeclaracaoDeVariavelEscopo.forr);
		});
		
		filter(Arrow.class).inverteOrdem().each(arrow -> {
			
			Palavra o = before(arrow);
			
			if (o.is(NaoClassificada.class)) {
				DeclaracaoDeVariavel dec = new DeclaracaoDeVariavel(newTipoArrow(o), (NaoClassificada) o);
				dec.setEscopo(DeclaracaoDeVariavelEscopo.parametro);
				replace(o, dec);
				palavras.addBefore(dec, arrow.getAbertura());
				arrow.setParamsCount(1);
			} else if (o.is(FechaParenteses.class)) {
				int count = 0;
				while (!o.is(AbreParenteses.class)) {
					o = before(o);
					if (!o.is(AbreParenteses.class)) {
						DeclaracaoDeVariavel dec = new DeclaracaoDeVariavel(newTipoArrow(o), (NaoClassificada) o);
						dec.setEscopo(DeclaracaoDeVariavelEscopo.parametro);
						replace(o, dec);
						o = before(dec);
						count++;
					}
				}
				palavras.addBefore(o, arrow.getAbertura());
				arrow.setParamsCount(count);
			} else {
				throw new NaoImplementadoException();
			}
			
			o = after(arrow);
			
			FechaBloco fechaBloco;
			
			if (o.is(AbreBloco.class)) {
				AbreBloco a = (AbreBloco) o;
				fechaBloco = a.getFechamento();
			} else {
				
				AbreBloco abre = new AbreBloco();
				abre.absorverIdentacao(o);
				abre.setFechamento(new FechaBloco());
				abre.setInvisivel(true);
				palavras.addBefore(o, abre);
				
				while (true) {
					
					o = after(o);
					
					if (o.is(AbreParenteses.class)) {
						AbreParenteses a = (AbreParenteses) o;
						o = after(a.getFechamento());
					}
					
					if (o.is(FechaParenteses.class, PontoEVirgula.class, Virgula.class)) {
						break;
					}
					
				}
				
				palavras.addBefore(o, abre.getFechamento());
				fechaBloco = abre.getFechamento();
				
			}
			
			palavras.addAfter(fechaBloco, arrow.getFechamentoArrow());
			
		});
		
		palavras.replace(ModificadorDeAcesso.class, TipoJava.class, AbreParenteses.class).por(lst -> {
			ModificadorDeAcesso m = lst.rm();
			TipoJava n = lst.rm();
			DeclaracaoDeMetodo dec = new DeclaracaoDeMetodo(m, null, n);
			lst.add(0, dec);
			return lst;
		});
		
		palavras.replace(ModificadorDeAcesso.class, TipoJava.class, NaoClassificada.class, AbreParenteses.class).por(lst -> {
			ModificadorDeAcesso m = lst.rm();
			TipoJava t = lst.rm();
			NaoClassificada n = lst.rm();
			lst.add(0, new DeclaracaoDeMetodo(m, t, n));
			return lst;
		});
		
		palavras.replace(TipoJava.class, NaoClassificada.class, AbreParenteses.class).por(lst -> {
			TipoJava t = lst.rm();
			NaoClassificada n = lst.rm();
			lst.add(0, new DeclaracaoDeMetodo(null, t, n));
			return lst;
		});

		palavras.replace(DeclaracaoDeUmTipoGenerico.class, DeclaracaoDeMetodo.class).por(lst -> {
			DeclaracaoDeUmTipoGenerico g = lst.rm();
			DeclaracaoDeMetodo dec = lst.get(0);
			dec.setGenericParam(g);
			return lst;
		});
		
		palavras.replace(ModificadorDeAcesso.class, DeclaracaoDeMetodo.class).por(lst -> {
			ModificadorDeAcesso m = lst.rm();
			DeclaracaoDeMetodo dec = lst.get(0);
			dec.setModificadorDeAcesso(m);
			return lst;
		});
		
		filter(AnnotationJavaSemTipo.class).each(a -> {
			Import imp = filter(Import.class).unique(i -> i.getTipo().getType().getSimpleName().contentEquals(a.getS()));
			if (imp != null) {
				AnnotationJava o = new AnnotationJava(imp.getTipo());
				replace(a, o);
			}
		});
		

		filter(AnnotationJava.class).each(a -> {
			
			if (after(a).is(AbreParenteses.class)) {
				AbreParenteses abre = after(a);
				
				Palavra o = abre;
				
				while (o != abre.getFechamento()) {
					a.getParams().add(o);
					remove(o);
					o = after(a);
				}

				a.getParams().add(o);
				remove(o);
				
			}
			
		});
		
		filter(Catch.class).each(m -> {
			
			AbreParenteses abre = after(m);
			FechaParenteses fecha = abre.getFechamento();
			
			Palavra o = after(abre);
			
			while (o != fecha) {
				
				if (o instanceof DeclaracaoDeVariavel) {
					DeclaracaoDeVariavel v = (DeclaracaoDeVariavel) o;
					v.setEscopo(DeclaracaoDeVariavelEscopo.catchParam);
				}
				
				o = after(o);
			}
			
		});
		
		filter(DeclaracaoDeMetodo.class).each(m -> {
			
			AbreParenteses abre = after(m);
			FechaParenteses fecha = abre.getFechamento();
			
			Palavra o = after(abre);
			
			while (o != fecha) {
				
				if (o instanceof DeclaracaoDeVariavel) {
					DeclaracaoDeVariavel v = (DeclaracaoDeVariavel) o;
					v.setEscopo(DeclaracaoDeVariavelEscopo.parametro);
				}
				
				o = after(o);
			}
			
			while (before(m) instanceof AnnotationJava) {
				m.getAnnotations().add(removeBefore(m));
			}
			
			m.setQuebras(2);
			
			
			o = after(fecha);
			
			if (o instanceof AbreBloco) {
				m.setBloco((AbreBloco) o);
			}
			
		});
		
		filter(DeclaracaoDeVariavel.class).each(o -> {
			while (before(o) instanceof AnnotationJava) {
				AnnotationJava a = removeBefore(o);
				o.getAnnotations().add0(a);
				o.absorverIdentacao(a);
			}
		});
		
		int indexBloco = palavras.indexOf(filter(AbreBloco.class).getFirst());
		
		Is0.func = o -> palavras.indexOf(o) > indexBloco && o.is(Ponto.class, FechaParenteses.class, Virgula.class, OperadorComparacao.class);
		
		palavras.replace(Ponto.class, NaoClassificada.class, Is0.class).por(lst -> {
			NaoClassificada n = lst.remove(1);
			lst.add(1, new InvocacaoDeUmAtributo(n.getS()));
			return lst;
		});
		
		
		filter(PontoEVirgula.class).filter(pv -> palavras.indexOf(pv) > indexBloco).each(pv -> {
			
			Palavra a = before(pv);
			
			if (a.is(NaoClassificada.class)) {
				Palavra o = before(a);
				if (o.is(Ponto.class)) {
					InvocacaoDeUmAtributo i = new InvocacaoDeUmAtributo(a.getS());
					replace(a, i);
				}
			}
			
		});
		
		deveContinuar.set(true);
		
		Is0.func = o -> o.is(TipoJava.class, InvocacaoDeUmAtributo.class, FechaParenteses.class, This.class, DeclaracaoDeVariavelRef.class);
		
		while (deveContinuar.get()) {

			deveContinuar.set(false);
			
			palavras.replace(Is0.class, Ponto.class, NaoClassificada.class).por(lst -> {
				
				deveContinuar.set(true);
				
				NaoClassificada n = lst.removeLast();
				if (palavras.after(n) instanceof AbreParenteses) {
					InvocacaoDeUmMetodo inv = new InvocacaoDeUmMetodo(n.getS());
					lst.add(inv);
					inv.absorverIdentacao(n);
				} else {
					InvocacaoDeUmAtributo inv = new InvocacaoDeUmAtributo(n.getS());
					lst.add(inv);
					inv.absorverIdentacao(n);
				}
				return lst;
				
			});
			
			palavras.replace(Is0.class, Ponto.class, TipoJava.class).por(lst -> {
				
				deveContinuar.set(true);
				
				TipoJava n = lst.removeLast();
				if (palavras.after(n) instanceof AbreParenteses) {
					InvocacaoDeUmMetodo inv = new InvocacaoDeUmMetodo(n.getS());
					lst.add(inv);
					inv.absorverIdentacao(n);
				} else {
					InvocacaoDeUmAtributo inv = new InvocacaoDeUmAtributo(n.getS());
					lst.add(inv);
					inv.absorverIdentacao(n);
				}
				return lst;
				
			});
			
		}
		
		palavras.replace(NaoClassificada.class, AbreParenteses.class).por(lst -> {
			lst.add(0, new InvocacaoDeUmMetodo(lst.rm().getS()));
			return lst;
		});

		palavras.replace(New.class, TipoJava.class, AbreParenteses.class).por(lst -> {
			lst.rm();
			TipoJava tipo = lst.rm();
			InvocacaoDeUmMetodo inv = new InvocacaoDeUmMetodo("");
			inv.setNewTipo(tipo);
			lst.add(0, inv);
			return lst;
		});

		variaveisRefs();
		
		referenciarInvocacoes();

		palavras.replace(New.class, TipoJava.class, Diamond.class, AbreParenteses.class).por(lst -> {
			New n = lst.rm();//new
			TipoJava tipo = lst.rm();//tipo
			tipo.setDiamond(true);
			lst.rm();//diamond
			InvocacaoDeUmMetodo inv = new InvocacaoDeUmMetodo("");
			inv.setNewTipo(tipo);
			lst.add(0, inv);
			
			Palavra o = before(n);
			
			if (o instanceof Igual) {
				o = before(o);
				if (o.is(DeclaracaoDeVariavel.class)) {
					DeclaracaoDeVariavel dec = (DeclaracaoDeVariavel) o;
					dec.getTipo().addGenericsObserver(itens -> tipo.setGenerics(itens));
				} else if (o.is(InvocacaoDeUmAtributo.class)) {
					InvocacaoDeUmAtributo invo = (InvocacaoDeUmAtributo) o;
					TipoJava tipoJava = new TipoJava(invo.getAtributo().getTypeJc());
					tipoJava.addGenericsObserver(itens -> tipo.setGenerics(itens));
				} else if (o.is(DeclaracaoDeVariavelRef.class)) {
					DeclaracaoDeVariavelRef ref = (DeclaracaoDeVariavelRef) o;
					DeclaracaoDeVariavel dec = ref.getDec();
					dec.getTipo().addGenericsObserver(itens -> tipo.setGenerics(itens));
				} else {
					throw new NaoImplementadoException();
				}
			} else if (o instanceof Return) {
				TipoJava tipoJava = descobreOQueEstahRetornando(o);
				if (tipoJava != null) {
					tipoJava.addGenericsObserver(itens -> tipo.setGenerics(itens));
				}
			}
			
			return lst;
		});
		
		filter(AnnotationJava.class).each(a -> {
			annotations.add(a);
			Palavra o = after(a);
			remove(a);
			o.absorverIdentacao(a);
		});
		
		variaveisRefs();
		
		
	}

	private void variaveisRefs() {

		filter(DeclaracaoDeVariavel.class).sorted((a,b) -> {
			
			if (a.getEscopo() == b.getEscopo()) {
				return 0;
			}
			
			if (a.getEscopo() == DeclaracaoDeVariavelEscopo.atributo) {
				return 1;
			}

			if (b.getEscopo() == DeclaracaoDeVariavelEscopo.atributo) {
				return -1;
			}
			
			return 0;
			
		}).each(dec -> {
			
			AbreBloco abreBloco = getAbreBloco(dec);
			
			if (abreBloco == null) {
				//significa que eh um metodo abstrato
				return;
			}
			
			FechaBloco fecha = abreBloco.getFechamento();
			
			Palavra o;
			
			if (palavras.indexOf(abreBloco) < palavras.indexOf(dec)) {
				o = after(abreBloco);
			} else {
				o = after(dec);
			}
			
			while (o != fecha) {
				
				if (o.is(NaoClassificada.class) && o.getS().contentEquals(dec.getNome().getS())) {
					
					if (o.eq("ooo")) {
						SystemPrint.ln(o);
					}
					
					Palavra before = before(o);
					
					if (before.is(Ponto.class)) {
						
						if (before(before).is(This.class) && dec.getEscopo() == DeclaracaoDeVariavelEscopo.atributo) {

							removeBefore(o);//remove o ponto
							This t = before(o);
							remove(o);
							
							DeclaracaoDeVariavelRef ref = new DeclaracaoDeVariavelRef(dec);
							ref.setComThis();
							replace(t, ref);
							
							o = ref;
							
						}
						
					} else {
						DeclaracaoDeVariavelRef ref = new DeclaracaoDeVariavelRef(dec);
						replace(o, ref);
						o = ref;
					}
					
				}

				o = after(o);
				
			}
			
		});
		
	}

	private TipoJava newTipoArrow(Palavra o) {
		
		TipoJava tipo = o.getTipoNoComentario();
		
		if (tipo == null) {
			
			String s = o.getPossivelTipoNoComentario();
			
			if (s == null) {
				s = o.getS();
			}
			
			tipo = descobreTipo(s);
			
			if (tipo == null) {
				tipo = new TipoJava(Arrow.class);	
			} else {
				o.getComentarios().clear();
			}
			
		} else {
			o.getComentarios().clear();
		}
		
		return tipo;
		
	}

	private TipoJava descobreOQueEstahRetornando(Palavra o) {
		o = getAbreBloco(o);
		o = before(o);
		
		if (o.is(FechaParenteses.class)) {
			FechaParenteses fecha = (FechaParenteses) o;
			o = before(fecha.getAbertura());
			if (o.is(IfWhile.class)) {
				return descobreOQueEstahRetornando(o);
			}
			
			if (o.is(DeclaracaoDeMetodo.class)) {
				DeclaracaoDeMetodo dec = (DeclaracaoDeMetodo) o;
				return dec.getTipo();
			}
			
			debug();
			
		}
		
		return null;
	}

	public Lst<AtributoJL> getAtributos() {
		return palavras.filter(DeclaracaoDeVariavel.class).filter(i -> i.isAtributo() && !i.isStatic()).map(i -> new AtributoJL(i));
	}

	public String getSimpleName() {
		return getFile().getSimpleNameWithoutExtension();
	}

	public AnnotationJava getAnnotation(String simpleName) {
		return getAnnotations().getLst().unique(i -> i.getType().getSimpleName().contentEquals(simpleName));
	}
	
	public AnnotationJava getAnnotation(Class<? extends Annotation> classe) {
		return getAnnotation(classe.getSimpleName());
	}
	
	private static void debug() {
		SystemPrint.row("");
	}
	
	@Override
	public ListString getResult() {
		ListString list = palavras.getListString();
		Metodo metodo = ListMetodos.get(getClasse()).get("finish_replaceTexto");
		if (metodo != null) {
			metodo.invoke(classe, list);
		}
		return list;
	}
	
	public boolean todosOsMembrosSaoEstaticos() {
		
		if (ListMetodos.get(getClasse()).anyMatch(i -> !i.isStatic())) {
			return false;
		}
		
		if (AtributosBuild.get(getClasse()).anyMatch(i -> !i.isStatic())) {
			return false;
		}
		
		return true;
		
	}
	
}
