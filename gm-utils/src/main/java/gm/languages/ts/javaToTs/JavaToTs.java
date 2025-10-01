package gm.languages.ts.javaToTs;

import java.lang.reflect.Type;

import gm.languages.comum.DeclaracaoDeVariavelEscopo;
import gm.languages.java.JavaLoad;
import gm.languages.java.expressoes.Cast;
import gm.languages.java.expressoes.DeclaracaoDeMetodo;
import gm.languages.java.expressoes.DeclaracaoDeVariavel;
import gm.languages.java.expressoes.DeclaracaoDeVariavelRef;
import gm.languages.java.expressoes.InvocacaoDeUmAtributo;
import gm.languages.java.expressoes.InvocacaoDeUmMetodo;
import gm.languages.java.expressoes.MaisIgual;
import gm.languages.java.expressoes.MaisMais;
import gm.languages.java.expressoes.MenosIgual;
import gm.languages.java.expressoes.MenosMenos;
import gm.languages.java.expressoes.ModificadorDeAcessoDefault;
import gm.languages.java.expressoes.Reticencias;
import gm.languages.java.expressoes.TipoJava;
import gm.languages.java.expressoes.words.Class_;
import gm.languages.java.expressoes.words.Enum_;
import gm.languages.java.expressoes.words.Implements;
import gm.languages.java.expressoes.words.Import;
import gm.languages.java.expressoes.words.New;
import gm.languages.java.expressoes.words.Public;
import gm.languages.java.expressoes.words.Static;
import gm.languages.java.expressoes.words.Super;
import gm.languages.java.expressoes.words.This;
import gm.languages.palavras.Is0;
import gm.languages.palavras.Is1;
import gm.languages.palavras.Is2;
import gm.languages.palavras.Linguagem;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.Else;
import gm.languages.palavras.comuns.Extends;
import gm.languages.palavras.comuns.If;
import gm.languages.palavras.comuns.IfWhile;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.Negacao;
import gm.languages.palavras.comuns.Null;
import gm.languages.palavras.comuns.Or;
import gm.languages.palavras.comuns.Return;
import gm.languages.palavras.comuns.conjuntos.array.AbreArray;
import gm.languages.palavras.comuns.conjuntos.array.FechaArray;
import gm.languages.palavras.comuns.conjuntos.arrow.AbreArrow;
import gm.languages.palavras.comuns.conjuntos.arrow.Arrow;
import gm.languages.palavras.comuns.conjuntos.arrow.FechaArrow;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.languages.palavras.comuns.conjuntos.bloco.FechaBloco;
import gm.languages.palavras.comuns.conjuntos.parametro.FechaParametro;
import gm.languages.palavras.comuns.literal.Literal;
import gm.languages.palavras.comuns.literal.LongLiteral;
import gm.languages.palavras.comuns.literal.StringLiteral;
import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.AspasDuplas;
import gm.languages.palavras.comuns.simples.Exclamacao;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.languages.palavras.comuns.simples.Igual;
import gm.languages.palavras.comuns.simples.Ponto;
import gm.languages.palavras.comuns.simples.PontoEVirgula;
import gm.languages.palavras.comuns.simples.Virgula;
import gm.languages.sql.palavras.As;
import gm.languages.ts.angular.core.Injectable;
import gm.languages.ts.conjuntos.JoinOpen;
import gm.languages.ts.expressoes.AbreParentesesMetodo;
import gm.languages.ts.expressoes.CastTs;
import gm.languages.ts.expressoes.ClassTs;
import gm.languages.ts.expressoes.DeclaracaoDeDestructs;
import gm.languages.ts.expressoes.DeclaracaoDeMetodoTs;
import gm.languages.ts.expressoes.DeclaracaoDeUmTipoGenericoTs;
import gm.languages.ts.expressoes.DeclaracaoDeVariavelTs;
import gm.languages.ts.expressoes.DeclaracaoDeVariavelTsRef;
import gm.languages.ts.expressoes.DefaultPropsDeclaration;
import gm.languages.ts.expressoes.ExportClass;
import gm.languages.ts.expressoes.FechaParentesesMetodo;
import gm.languages.ts.expressoes.ImportFrom;
import gm.languages.ts.expressoes.ObjetoVazio;
import gm.languages.ts.expressoes.ReferenciaAUmaVariavelTs;
import gm.languages.ts.expressoes.TipoTs;
import gm.languages.ts.javaToTs.annotacoes.Async;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.FromClass;
import gm.languages.ts.javaToTs.annotacoes.FromMethod;
import gm.languages.ts.javaToTs.annotacoes.ImportCss;
import gm.languages.ts.javaToTs.annotacoes.ImportFunction;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.languages.ts.javaToTs.annotacoes.Interface;
import gm.languages.ts.javaToTs.annotacoes.StringValue;
import gm.languages.ts.javaToTs.annotacoes.StringValues;
import gm.languages.ts.javaToTs.exemplo.xx.Any;
import gm.languages.ts.javaToTs.exemplos.src.Bla;
import gm.languages.ts.words.Console;
import gm.languages.ts.words.Constructor;
import gm.languages.ts.words.conjuntos.array.AbreColcheteField;
import gm.languages.ts.words.conjuntos.json.AbreJson;
import gm.languages.ts.words.conjuntos.json.FechaJson;
import gm.languages.ts.words.conjuntos.json.JSonKey;
import gm.utils.anotacoes.Ignorar;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.javaCreate.JcTipo;
import gm.utils.lambda.F1;
import gm.utils.lambda.P0;
import gm.utils.lambda.Resolve;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.string.ListString;
import js.Js;
import js.annotations.Method;
import js.annotations.NoAuto;
import js.annotations.Support;
import js.annotations.TypeJs;
import js.array.Array;
import js.array.Destruct;
import js.outros.JsMath;
import js.support.JSON;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.comum.BooleanWrapper;
import src.commom.utils.comum.JsMap;
import src.commom.utils.comum.OurTypes;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringRepete;
import src.commom.utils.string.StringRight;

public class JavaToTs extends JavaLoad {

	protected final Lst<JcTipo> anotacoesJcTipo = new Lst<>();
	public final Lst<TipoTs> tiposCriados = new Lst<>();

	public static boolean temFuncoes = false;
	private ListString path;
	protected boolean podeFecharBlocos = true;

	public static class config {
		public static boolean typeScript = true;
		public static boolean aceitaStaticsBlocks = true;
		public static boolean aceitaClassNull = true;
		public static boolean aceitaStringEmpty = true;
		public static F1<String, String> getCustomFroms;
		public static boolean aceitaClasses = true;
		public static String tabReplace;
		public static boolean stringAspasSimples;
		public static boolean colocarExclamacaoEmAtributosQueAceitamNulos = true;
		public static boolean ourTypes = true;
	}

	public static JavaToTs getInstance() {
		return (JavaToTs) JavaLoad.instance;
	}

	public static enum TipoDeClasse {
		CLASS, ENUM
	}

	public TipoDeClasse tipoDeClasse;

	public JavaToTs(Class<?> classe) {

		super(classe);

		Linguagem.selected = Linguagem.js;
		ClassTs.typesUtilizados.clear();
		exec();
		palavras.get(0).clearIdentacao();

//		if (Palavra.emAnalise) {
//		String s = classe.getName().replace("gm.languages.ts.javaToTs.exemplo.", "").replace(".", "/");
//			palavras.save("/opt/desen/gm/cs2019/gm-utils/tmp/"+s+".ts");
//		}

	}
	
	@Override
	protected ListString loadListString() {
		ListString list = super.loadListString();
		list.replaceTexto("_TSREPLACE", "");
		return list;
	}

	public static void main(String[] args) {
		Palavra.emAnalise = true;
		config.aceitaStaticsBlocks = false;
		JavaToTs.execJavaToTs(Bla.class).print();
	}

	public static ListString getSpacialJs(Class<?> classe) {

		if (classe == OurTypes.class) {
			return new ListString().load("C:\\opt\\desen\\gm\\cs2019\\gm-utils\\src\\main\\resources\\OurTypes.ts");
		}

		return null;

	}

	public static ListString execJavaToTs(Class<?> classe) {

		ListString list = getSpacialJs(classe);

		if (list != null) {
			return list;
		}

		return new JavaToTs(classe).getResult();
	}

	private final Lst<ImportFrom> importsFrom = new Lst<>();

	public ImportFrom getImportFrom(final String fromm) {
		
//		if (fromm.contains("Dom")) {
//			Print.ln();
//		}
		
		String from = fromm.replace("__", "-");

		ImportFrom o = importsFrom.unique(i -> from.contentEquals(i.getFrom()));

		if (o == null) {
			o = ImportFrom.build();
			o.setFrom(from);
			importsFrom.add(o);

			ImportFrom last = filter(ImportFrom.class).getLast();

			if (last == null) {
				Palavra a = palavras.get(0);
				a.setQuebras(2);
				addBefore(a, o);
			} else {
				o.setQuebras(1);
				addAfter(last, o);
			}

		}

		return o;
	}

//	public ImportFrom getImportFrom(From from) {
//		return getImportFrom(from.value());
//	}

	public ImportFrom getImportFrom(TipoJava tipo) {

//		if (tipo.getS().contentEquals("string")) {
//			return null;
//		}
//
//		if (tipo.getS().contentEquals("String")) {
//			return null;
//		}

		if (tipo.getS().contentEquals("int")) {
			ImportFrom ourTypes = getImportFrom(OurTypes.class);
			ourTypes.add("int", true);
			return null;
		}

		if (tipo.getS().contentEquals("Integer")) {
			ImportFrom ourTypes = getImportFrom(OurTypes.class);
			ourTypes.add("Integer", true);
			return null;
		}

		if (tipo.getS().contentEquals("Boolean")) {
			ImportFrom ourTypes = getImportFrom(OurTypes.class);
			ourTypes.add("Boolean", true);
			return null;
		}
		
//		if (tipo.getS().contains("Dom")) {
//			Print.ln();
//		}

		boolean func = tipo.getPackage() == P0.class.getPackage();

		if (func) {
			temFuncoes = true;
		}

		From from = tipo.getAnnotation(From.class);

		BooleanWrapper identificadoComoStatic = new BooleanWrapper().set(false);

		String f;

		TipoJava tipoFinal;

		if (tipo.getType() != null) {
			tipoFinal = new TipoJava(tipo.getType());
		} else if (tipo.getTypeClass() != null) {
			tipoFinal = new TipoJava(JcTipo.build(tipo.getTypeClass()));
		} else {
			throw new NaoImplementadoException();
		}

		if (from == null) {

			boolean fromMethod = FromMethod.class.isAssignableFrom(tipo.getClasse());

			if (fromMethod) {
				FromMethod o = (FromMethod) UClass.newInstance(tipo.getClasse());
				f = o.getFrom();
				identificadoComoStatic.set(o.importStatic());
			} else {
				
				String custom = getCustom(tipo);
				
				if (custom != null) {
					f = custom;
				} else {

					boolean fromClass = FromClass.class.isAssignableFrom(tipo.getClasse());

					ListString path0 = path.copy();

					String pathTipo = Resolve.ft(() -> {

						if (func) {
							return OurTypes.class.getName();
						}

						if (fromClass) {
							FromClass o = (FromClass) UClass.newInstance(tipo.getClasse());
							identificadoComoStatic.set(true);// TODO verificar se eh sempre assim
							return o.getFromClass().getName();
						}

						if (StringValue.class.isAssignableFrom(tipo.getClasse())) {
							identificadoComoStatic.set(true);
							return OurTypes.class.getName();
						}

						return tipo.getName();

					});

					ListString path1 = ListString.byDelimiter(pathTipo, ".");

					if (path1.contains("srcx")) {
						path1.replace("srcx", "src");
						String s = path1.removeLast();
						s = StringRight.ignore1(s);
						path1.add(s);
						tipoFinal = new TipoJava(path1.toString("."));
					}

					if (path1.contains("src")) {
						while (!path1.get(0).contentEquals("src")) {
							path1.remove(0);
						}
					}

					while ((!path0.isEmpty()) && (!path1.isEmpty()) && path0.get(0).contentEquals(path1.get(0))) {
						path0.remove(0);
						path1.remove(0);
					}

					if (path1.size() > 1 && path1.getLast().contentEquals(path1.get(-2))) {
//						se o ultimo for igual ao penultimo sigfinic aque é um import-index
						path1.removeLast();
					}

					f = (path0.isEmpty() ? "./" : StringRepete.exec("../", path0.size())) + path1.toString("/");
					
				}

			}

		} else {
			f = from.value();
		}
		
		ImportFrom o = getImportFrom(f);
		boolean staticc = tipo.isAnnotationPresent(ImportStatic.class) || func || identificadoComoStatic.isTrue();
		o.add(TipoTs.build(tipoFinal), staticc);
		return o;

	}
	
	private String getCustom(TipoJava tipo) {
		if (config.getCustomFroms == null) {
			return null;
		} else {
			return config.getCustomFroms.call(tipo.getName());
		}
	}

	public ImportFrom getImportFrom(Class<?> classe) {
		return getImportFrom(new TipoJava(classe));
	}

	@Override
	protected void exec1() {

		super.exec1();

		path = ListString.byDelimiter(getClasse().getName(), ".");
		path.removeLast();

		while (!path.get(0).contentEquals("src")) {
			path.remove(0);

			if (path.isEmpty()) {
				throw new RuntimeException("Uma classe transpilada deve estar dentro de um pacote src");
			}

		}

		removeInJava();
		removePackage();
		removeSelfs();

		Public public1 = palavras.filter(Public.class).get(0);

		{
			Palavra tipoClasse = palavras.removeAfter(public1);

			if (tipoClasse instanceof Class_) {
				this.tipoDeClasse = TipoDeClasse.CLASS;
			} else if (tipoClasse instanceof Enum_) {
				this.tipoDeClasse = TipoDeClasse.ENUM;
			} else {
				throw new NaoImplementadoException(tipoClasse.getClass().getName());
			}
		}

		ExportClass exportClass = new ExportClass(public1.isAbstract());
		palavras.replace(public1, exportClass);

		if (getAnnotations().remove(ImportStatic.class)) {
			exportClass.setDefault(false);
		}

		filter(Extends.class).each(i -> {
			TipoJava tipo = after(i);
			if (tipo.eq("JS")) {
				remove(tipo);
				remove(i);
			}
		});

		filter(TipoJava.class).filter(i -> i.getS().contentEquals("UNative")).each(i -> {

			Ponto ponto = after(i);
			remove(ponto);

			InvocacaoDeUmMetodo inv = after(i);
			remove(inv);

			AbreParenteses abre = after(i);
			remove(abre);

			if (inv.getS().contentEquals("asArray1")) {

				FechaParenteses fecha = abre.getFechamento();

				Class_ cs = before(fecha);
				remove(cs);

				ponto = before(fecha);
				remove(ponto);

				TipoJava tipo = before(fecha);
				remove(tipo);

				Virgula virgula = before(fecha);
				remove(virgula);

				remove(fecha);

			} else if (inv.getS().contentEquals("asArray2")) {
				FechaParenteses fecha = abre.getFechamento();
				remove(fecha);
			} else {
				throw new NaoImplementadoException(inv.getS());
			}

			after(i).absorverIdentacao(i);
			remove(i);

		});

		filter(DeclaracaoDeVariavel.class).filter(i -> i.getTipo().eq(Stringg.class)).each(i -> {

			Palavra o = after(i);

			if (o.is(Igual.class)) {
				removeAfter(o).assertt("new Stringg");
				AbreParenteses abre = removeAfter(o);
				remove(abre.getFechamento());
				after(o).setEspacos(1);
			}

			i.setTipo(new TipoJava(String.class));

			i.getRefs().each(ref -> {
				if (after(ref).is(Ponto.class)) {
//					TODO retirei por causa da classe Avatar2, ver pq tinha colocado
//					removeAfter(ref);
//					removeAfter(ref);
				}
			});

		});

		filter(Import.class).each(i -> {

			TipoJava t = i.getTipo();

			if (t.hasAnnotation(From.class)) {
				return;
			}

			String name = "." + t.getName();

			if (name.contains(".src.")) {
				return;
			}

			if (name.contains(".srcx.")) {
				return;
			}

			if (t.getTypeClass() != null && t.getTypeClass().getTypeName().startsWith("gm.utils.lambda.")) {
				return;
			}

			if (FromClass.class.isAssignableFrom(t.getClasse())) {
				return;
			}

			if (FromMethod.class.isAssignableFrom(t.getClasse())) {
				return;
			}

			if (StringValue.class.isAssignableFrom(t.getClasse())) {
				return;
			}

			remove(i);

		});

		// TODO por enquanto vou remover os cast pois acho que pode ser uma ma ideia pois acho que cria uma nova referencia
		filter(Cast.class).each(cast -> remove(cast));

		/*
		 * filter(Cast.class).each(cast -> {
		 * remove(cast);
		 * palavras.addAfter(cast.getFechamento(), cast);
		 * });
		 * /
		 **/
		
		enums_com_parametros_devem_ser_convertidos_em_classe();

//		new Array<>() to []
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.getNewTipo() != null).each(i -> {

			Palavra o = i.getNewTipo();

			if (!o.is(TipoJava.class)) {
				return;
			}

			TipoJava tipo = (TipoJava) o;

			if (tipo.getTypeClass() != Array.class) {
				return;
			}

			AbreParenteses abre = after(i);

			remove(abre);

			AbreArray array = new AbreArray();

			replace(i, array);

			array.setFechamento(new FechaArray());

			replace(abre.getFechamento(), array.getFechamento());

		});

//		var.array(x) to var[x]
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("array")).each(i -> {
			removeBefore(i).assertt(Ponto.class);
			AbreParenteses abre = removeAfter(i);
			AbreArray abreArray = new AbreArray();
			abreArray.setFechamento(new FechaArray());
			replace(i, abreArray);
			replace(abre.getFechamento(), abreArray.getFechamento());
		});

//		var.arraySet(x, o) to var[x] = o
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("arraySet")).each(i -> {
			removeBefore(i).assertt(Ponto.class);
			AbreParenteses abre = removeAfter(i);
			remove(abre.getFechamento());
			AbreArray abreArray = new AbreArray();
			abreArray.setFechamento(new FechaArray());
			replace(i, abreArray);

			Palavra o = after(abreArray);

			while (o.isnt(Virgula.class)) {

				if (o.is(AbreParenteses.class)) {
					AbreParenteses a = (AbreParenteses) o;
					o = after(a.getFechamento());
					continue;
				}

				if (o.is(AbreArray.class)) {
					AbreArray a = (AbreArray) o;
					o = after(a.getFechamento());
					continue;
				}

				o = after(o);

			}

			replace(o, abreArray.getFechamento());

			addAfter(abreArray.getFechamento(), new Igual().incEspaco());

		});
		
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("js_attr")).each(o -> {

			Palavra inicio;

			if (before(o).is(Ponto.class)) {
				removeBefore(o);
				inicio = removeBefore(o);
			} else {
				inicio = o;
			}
			
			AbreParenteses abre = after(o);
			remove(o);
			final Palavra var = after(abre);
			
			var.absorverIdentacao(inicio);
			Palavra p = after(var);
			while (!p.is(Virgula.class)) {
				if (p.is(AbreParenteses.class)) {
					AbreParenteses abreP = (AbreParenteses) p;
					p = after(abreP.getFechamento());
					continue;
				}
				p = after(p);
			}

			Palavra obj = after(p);

			if (obj instanceof StringLiteral) {

				StringLiteral sl = (StringLiteral) obj;

				if (ehUmNomeDeAtributoValido(sl.getTexto())) {

					replace(p, new Ponto());
					InvocacaoDeUmMetodo inv = new InvocacaoDeUmMetodo(sl.getTexto());
					replace(sl, inv);
					inv.clearIdentacao();

					if (var.is(DeclaracaoDeVariavelTsRef.class)) {
						DeclaracaoDeVariavelTsRef ref = (DeclaracaoDeVariavelTsRef) var;
						DeclaracaoDeVariavelTs dec = ref.getDec();

						if (dec.getTipo().isString()) {
							dec.getTipo().setClasse(ClassTs.anyTs);
						}

					}

					remove(abre);
					remove(abre.getFechamento());

					return;

				}

			}

			p.assertt(Virgula.class);

			AbreColcheteField abreColcheteField = new AbreColcheteField();

			replace(p, abreColcheteField);
			remove(abre);
			replace(abre.getFechamento(), abreColcheteField.getFechamento());
			after(abreColcheteField).clearIdentacao();
			
		});
		
//		get_atributo(x, o) to x[o]
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("get_atributo")).each(i -> {
		
//			get_atributo(x, o)
			AbreParenteses abreParenteses = removeAfter(i);//get_atributox, o)
			Palavra variavel = removeAfter(i);//get_atributo, o)
			replace(i, variavel);//x, o)
			
			Palavra v = after(variavel);
			
			while (!v.is(Virgula.class)) {
				v = after(v);
				if (v.is(AbreParenteses.class)) {
					AbreParenteses a = (AbreParenteses) v;
					v = a.getFechamento();
				} else if (v.is(AbreArray.class)) {
					AbreArray a = (AbreArray) v;
					v = a.getFechamento();
				}
			}
			
			after(v).setEspacos(0);
			AbreArray abreArray = new AbreArray();
			abreArray.setFechamento(new FechaArray());
			replace(v, abreArray);
			replace(abreParenteses.getFechamento(), abreArray.getFechamento());

		});		

//		sf(o) to o?
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("sf")).inverteOrdem().each(i -> {
			AbreParenteses abre = removeAfter(i);
			Palavra oo = after(i);
			String x = oo.getComplemento();
			oo.absorverIdentacao(i);
			oo.setComplemento(x);
			remove(i);
			Palavra o = before(abre.getFechamento());
			remove(abre.getFechamento());
			o.setComplemento("?");

			SystemPrint.ln(o.getComplemento());

		});
		
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("toFixed")).inverteOrdem().each(o -> {
		
			AbreParenteses abre = after(o);
			abre.absorverIdentacao(o);
			remove(o);
			
			FechaParenteses fechamento = abre.getFechamento();
			Palavra x = before(fechamento);
			
			while (!x.is(Virgula.class)) {
				if (x.is(FechaParenteses.class)) {
					FechaParenteses fecha = (FechaParenteses) x;
					x = fecha.getAbertura();
				}
				x = before(x);
			}
			
			Ponto ponto = new Ponto();
			replace(x, ponto);
			addAfter(ponto, o);
			
			//significa que é uma expressao
			if (after(abre) != before(ponto)) {
				AbreParenteses abre2 = new AbreParenteses();
				abre2.setFechamento(new FechaParenteses());
				replace(abre, abre2);
				addBefore(ponto, abre2.getFechamento());
			} else {
				after(abre).absorverIdentacao(abre);
				remove(abre);
			}
			
			addAfter(o, abre);
			
		});		
		
		Is0.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("obj");
		palavras.replace(Is0.class, AbreParenteses.class, FechaParenteses.class).porNew(() -> new ObjetoVazio());

		palavras.replace(DeclaracaoDeVariavel.class, Arrow.class).por(lst -> {
			AbreParenteses abre = new AbreParenteses();
			abre.setFechamento(new FechaParenteses());
			lst.add(0, abre);
			lst.add(2, abre.getFechamento());
			return lst;
		});

		palavras.replace(DeclaracaoDeVariavel.class).porUm(lst -> {
			DeclaracaoDeVariavel o = lst.rm();
			TipoTs tipo = TipoTs.build(o.getTipo());
			DeclaracaoDeVariavelTs d = new DeclaracaoDeVariavelTs(o.getModificadorDeAcesso(), tipo, o.getNome());
			d.setAnnotationsJava(o.getAnnotations());
			d.setEscopo(o.getEscopo());
			d.setPodeRetornarNull(o.podeRetornarNull() || o.isString());
			o.getRefs().each(ref -> {
				DeclaracaoDeVariavelTsRef refTs = new DeclaracaoDeVariavelTsRef(d);
				replace(ref, refTs);
				if (d.isStatic()) {
					refTs.setClasse(getTipoThisTs());
				}
			});
			return d;
		});

		palavras.replace(DeclaracaoDeMetodo.class).porUm(lst -> {

			DeclaracaoDeMetodo o = lst.rm();

			if (o.annotations.has(Ignorar.class)) {
				FechaBloco fecha = o.getBloco().getFechamento();
				while (palavras.removeAfter(o) != fecha) {

				}
				lst.clear();
				return null;
			}

			DeclaracaoDeMetodoTs m;

			if (o.isConstrutor()) {
				m = new DeclaracaoDeMetodoTs(o.getModificadorDeAcesso(), null, "constructor", null);
			} else {

				TipoTs tipo = TipoTs.build(o.getTipo());

				m = new DeclaracaoDeMetodoTs(o.getModificadorDeAcesso(), tipo, o.getNome(), DeclaracaoDeUmTipoGenericoTs.build(o.getGenericParam()));
				m.getModificadorDeAcesso().setFinal(false);
				m.getModificadorDeAcesso().setSynchronized(false);
				m.setOverride(o.isOverrideClass());
				m.async = o.annotations.has(Async.class);

				if (o.getTipo().getClasse() != null && UClass.a_herda_b(o.getTipo().getClasse(), StringValue.class)) {
					m.setPodeRetornarNull(false);
				} else {
					m.setPodeRetornarNull(o.podeRetornarNull());
				}

			}

			AbreParenteses abreOld = after(o);
			AbreParentesesMetodo abre = new AbreParentesesMetodo(m);
			palavras.replace(abreOld, abre);

			FechaParentesesMetodo fecha = new FechaParentesesMetodo(m);
			palavras.replace(abreOld.getFechamento(), fecha);

			m.setBloco(o.getBloco());

			return m;

		});

		if (filter(Extends.class).isNotEmpty()) {

			palavras.filter(DeclaracaoDeMetodoTs.class).filter(i -> i.isConstructor()).each(i -> {

				Palavra o = after(i.getBloco());

				if (!o.is(Super.class)) {
					Super s = new Super();
					s.setQuebras(1);
					s.setTabs(o.getTabs());
					palavras.addBefore(o, s);
					AbreParenteses abre = new AbreParenteses();
					abre.setFechamento(new FechaParenteses());
					palavras.addAfter(s, abre);
					palavras.addAfter(abre, abre.getFechamento());
					palavras.addAfter(abre.getFechamento(), new PontoEVirgula());
				}

			});

		}

		Is0.func = o -> o.is(TipoJava.class) && ((TipoJava) o).eq(System.class);
		Is1.func = o -> o.is(InvocacaoDeUmAtributo.class) && o.getS().contentEquals("out");
		Is2.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.getS().contentEquals("println");

		palavras.replace(Is0.class, Ponto.class, Is1.class, Ponto.class, Is2.class).por(lst -> {
			lst.clear();
			lst.add(new Console());
			lst.add(new Ponto());
			lst.add(new InvocacaoDeUmMetodo("log"));
			return lst;
		});

		colocarThis();

		filter(Import.class).each(i -> {

			TipoJava tipo = i.getTipo();

			if (tipo.isAnnotation()) {
				palavras.remove(i);
				return;
			}

			if (tipo.eq(Any.class, Js.class, Array.class, JS.class, Number.class, JsMath.class, JsMap.class)) {
				palavras.remove(i);
				return;
			}

			if (tipo.eq(FromClass.class)) {

				palavras.remove(i);

				filter(TipoJava.class).filter(item -> item.eq(tipo)).each(item -> {

					if (before(item).is(Implements.class)) {
						removeBefore(item);
						remove(item);
					} else if (before(item).is(Virgula.class)) {
						if (after(item).is(Virgula.class) || after(after(item)).is(AbreBloco.class)) {
							removeBefore(item);
							remove(item);
						}
					}

				});

				return;
			}

			getImportFrom("react").add("userEffect", true);

			getImportFrom(tipo);
			remove(i);

		});

		Is0.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("as");
		palavras.replace(Ponto.class, Is0.class, AbreParenteses.class, FechaParenteses.class).remove();

		Is0.func = o -> o.is(TipoJava.class) && o.eq("Js");
		palavras.replace(Is0.class, Ponto.class, Palavra.class).por(lst -> {

			lst.rm();
			lst.rm();

			Palavra o = lst.get(0);
			if (o.eq("undefined") && after(o).is(AbreParenteses.class)) {
				removeAfter(o);
				removeAfter(o);
			}

			return lst;
		});

		metodosEspeciais();
		injecoes();
		newArray();

		Is0.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("call");

		getters();
		setters();

		annotations.removeIf(i -> i.getType().eq(From.class));
		annotations.removeIf(i -> i.getType().eq(NoAuto.class));
		annotations.removeIf(i -> i.getType().eq(Method.class));
		annotations.each(o -> addAnnotation(o.getType()));

		getImports().filter(i -> i.isAnnotationPresent(ImportFunction.class)).each(classe -> {

			filter(TipoJava.class).filter(i -> i.getS().contentEquals(classe.getSimpleName())).each(o -> {

				Palavra ponto = after(o);

				if (!(ponto instanceof Ponto)) {
					return;
				}

				InvocacaoDeUmMetodo invoke = after(ponto);

				invoke.setS(classe.getSimpleName());

				remove(o);
				remove(ponto);
				invoke.absorverIdentacao(o);

			});

		});

		palavras.replace(Ponto.class, Is0.class).por(lst -> lst.clear());
		palavras.replace(TipoJava.class).porUm(lst -> TipoTs.build(lst.rm()));
		removeImport(js.outros.Date.class);
		removeImport(JSON.class);

		filter(InvocacaoDeUmMetodo.class).filter(i -> i.getS().startsWith("{}")).each(i -> {
			AbreParenteses abre = after(i);
			remove(abre);
			remove(abre.getFechamento());
		});

		palavras.replace(Cast.class).porUm(lst -> {
			Cast cast = lst.get(0);
			TipoTs tipo = TipoTs.build(cast.getTipo());
			return new CastTs(tipo);
		});

		filter(CastTs.class).each(i -> i.clearIdentacao());

		destructors();
		initStaticBlocks();
		finalizar();

		filter(DeclaracaoDeVariavelTs.class).filter(i -> !i.isPossuiInicializacao()).each(i -> {

			Palavra o = after(i);

			if (o.is(Igual.class)) {

				i.setPossuiInicializacao(true);

				o = after(o);

				if (o.is(Literal.class) && !o.is(Null.class) && i.isFinal()) {
					i.setPodeRetornarNull(false);
				}

			}

//			TODO verificar nos construtores de classe

		});
		
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("as") && before(i).is(Ponto.class) && before(before(i)).eq("JS")).forEach(as -> {
			removeBefore(as);
			Palavra js = removeBefore(as).assertt("JS");
			AbreParenteses abre = removeAfter(as);
			FechaParenteses fecha = abre.getFechamento();
			removeBefore(fecha).assertt("class");
			removeBefore(fecha).assertt(Ponto.class);
			Virgula virgula = before(before(fecha));
			virgula.setEspacos(1);
			replace(virgula, new As());
			after(as).absorverIdentacao(js);
			remove(as);
			remove(fecha);
		});
		
//		Is0.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("as");

		filter(DeclaracaoDeVariavelTs.class).filter(i -> after(i).is(Igual.class)).each(i -> {
			if (!i.getRefs().anyMatch(ref -> after(ref).is(Igual.class, MaisIgual.class, MenosIgual.class, MaisMais.class, MenosMenos.class))) {
				i.setConstant(true);
			}
		});
		
		todo_metodo_que_retorna_uma_promise_deve_ser_async();
		todo_metodo_que_contem_um_await_deve_ser_async();
		toda_arrowfunction_que_contem_um_await_deve_ser_async();
		abertura_de_blocos_json_nao_devem_estar_em_novas_linhas();

		ojs();

		addOurTypes();

	}

	private void enums_com_parametros_devem_ser_convertidos_em_classe() {

		if (!isEnum()) {
			return;
		}
		
		AbreBloco blocoDeDeclaracao = filter(AbreBloco.class).getFirst();
		
		Palavra o = after(blocoDeDeclaracao);
		
		if (o instanceof InvocacaoDeUmMetodo) {
			
			tipoDeClasse = TipoDeClasse.CLASS;
			
			/* significa que possui um construtor com parametros */
			
			while (true) {
				
				InvocacaoDeUmMetodo inv = (InvocacaoDeUmMetodo) o;
				
				DeclaracaoDeVariavel dec = new DeclaracaoDeVariavel(
					new Public(),
					getTipoThis(),
					new NaoClassificada(inv.getS())
				);
				
				dec.getModificadorDeAcesso().setStatic(true);
				dec.getModificadorDeAcesso().setFinal(true);
				
				replace(inv, dec);
				
				Igual igual = new Igual();
				igual.setEspacos(1);
				addAfter(dec, igual);
				
				New neww = new New();
				neww.setEspacos(1);
				addAfter(igual, neww);
				
				inv = new InvocacaoDeUmMetodo(getTipoThis().getSimpleName());
				inv.setEspacos(1);
				addAfter(neww, inv);
				
				AbreParenteses abre = after(inv);
				FechaParenteses fecha = abre.getFechamento();

				o = after(fecha);
				
				if (o instanceof PontoEVirgula) {
					break;
				}
				
				Virgula v = (Virgula) o;
				PontoEVirgula pv = new PontoEVirgula();
				replace(v, pv);
				
				pv.setEspacos(0);
				
				o = after(pv);
				o.setQuebras(1);
				o.setEspacos(0);
				o.setTabs(1);				
				
			}
			
		}
		
	}

	private void abertura_de_blocos_json_nao_devem_estar_em_novas_linhas() {
		
		filter(AbreJson.class).forEach(i -> {
			
			if (i.getQuebras() > 0) {
				i.clearIdentacao();
			}
			
			if (before(i) instanceof AbreParenteses) {
				i.setEspacos(0);
			} else {
				i.setEspacos(1);
			}
			
		});
		
	}

	private void toda_arrowfunction_que_contem_um_await_deve_ser_async() {

		get_arrows_nao_async().each(dec -> {

			AbreArrow abre = dec.getAbertura();
			FechaArrow fecha = abre.getFechamentoArrow();

			Palavra o = after(dec);
			while (o != fecha) {
				
				if (o.eq("await")) {
					dec.setAsync(true);
					after(abre).setEspacos(1);
					if (!(before(abre) instanceof AbreParenteses)) {
						abre.setEspacos(1);
					}
					break;
				}

				if (o instanceof Arrow) {
					Arrow a = (Arrow) o;
					o = after(a.getFechamentoArrow());
					continue;
				}

				o = after(o);

			}

		});

	}

	private void todo_metodo_que_contem_um_await_deve_ser_async() {
		get_metodos_nao_async().each(dec -> {			
			AbreBloco abre = dec.getBloco();
			
			if (abre == null) {
				/* significa que eh um metodo abstrato */
				return;
			}
			
			FechaBloco fecha = abre.getFechamento();
			Palavra o = after(dec);
			while (o != fecha) {
				
				if (o.eq("await")) {
					throw new RuntimeException("O método " + getClasse().getSimpleName() + "." + dec.getNome() + " contém um await. Portanto seu retorno deve ser uma Promise");
				}

				if (o instanceof Arrow) {
					Arrow a = (Arrow) o;
					o = after(a.getFechamentoArrow());
					continue;
				}

				o = after(o);

			}
		});
	}

	private void todo_metodo_que_retorna_uma_promise_deve_ser_async() {
		get_metodos_nao_async().each(dec -> {
			
			if (dec.getTipo() == null) {

			} else if (dec.getTipo().getClasse() == null) {

			} else if (dec.getTipo().getClasse().getSimpleName().contentEquals("Promise")) {
				dec.setAsync(true);
			}
		});
	}

	private Lst<DeclaracaoDeMetodoTs> get_metodos_nao_async() {
		return filter(DeclaracaoDeMetodoTs.class).filter(i -> !i.isAsync());
	}

	private Lst<Arrow> get_arrows_nao_async() {
		return filter(Arrow.class).filter(i -> !i.isAsync());
	}

	private void addOurTypes() {

		Lst<String> lst = filter(DeclaracaoDeVariavelTs.class).map(i -> i.getS());

		ImportFrom ourTypes = getImportFrom(OurTypes.class);

		if (lst.anyMatch(s -> s.endsWith(": str"))) {
			ourTypes.add("str", true);
		}

		if (lst.anyMatch(s -> s.endsWith(": int"))) {
			ourTypes.add("int", true);
		}

		if (lst.anyMatch(s -> s.endsWith(": Integer"))) {
			ourTypes.add("Integer", true);
		}

	}

	private void removeSelfs() {

		filter(InvocacaoDeUmAtributo.class).filter(i -> i.eq("self")).each(i -> {
			removeBefore(i).assertt(Ponto.class);
			remove(i);
		});

	}

	private void ojs() {

		Lst<AbreJson> atuais = filter(AbreJson.class);

		filter(TipoTs.class).filter(i -> i.eq("ojs")).inverteOrdem().each(i -> {
			replace(i, new AbreJson());
		});

		filter(AbreJson.class).filter(i -> !atuais.contains(i)).each(i -> {

			boolean first = true;

			Palavra o = i;

			while (after(o).is(Ponto.class)) {

				Ponto ponto = removeAfter(o);
				InvocacaoDeUmMetodo v = removeAfter(o);
				AbreParenteses abre = removeAfter(o);

				StringLiteral stringKey = removeAfter(o);

				JSonKey key = new JSonKey(stringKey);
				key.setQuebras(ponto.getQuebras() + v.getQuebras() + stringKey.getQuebras());
				key.setTabs(ponto.getTabs() + v.getTabs() + stringKey.getTabs());

				addAfter(o, key);

				Virgula virgula = removeAfter(key);

				Palavra primeiro = after(key);

				primeiro.setEspacos(1);
				primeiro.setQuebras(virgula.getQuebras() + primeiro.getQuebras());
				primeiro.setTabs(virgula.getTabs() + primeiro.getTabs());

				o = before(abre.getFechamento());

				remove(abre.getFechamento());

				if (first) {
					first = false;
					key.setEspacos(0);
				} else {
					key.setEspacos(1);
					virgula.clearIdentacao();
					addBefore(key, virgula);
				}

			}

			addAfter(o, i.getFechamento());

		});

	}

	private void destructors() {

		filter(TipoTs.class).filter(i -> i.eq(Destruct.class)).each(i -> {

			Igual igual = before(i);
			DeclaracaoDeVariavelTs decx = before(igual);
			DeclaracaoDeDestructs dec = new DeclaracaoDeDestructs();
			replace(decx, dec);

			Ponto ponto = after(i);
			InvocacaoDeUmMetodo exec = after(ponto);
			AbreParenteses abre = after(exec);

			remove(abre);
			remove(abre.getFechamento());
			remove(ponto);
			remove(exec);
			remove(i);

			after(igual).absorverIdentacao(i);

			filter(DeclaracaoDeVariavelTsRef.class).filter(d -> d.getDec() == decx).each(d -> {

				removeAfter(d).assertt(Ponto.class);
				removeAfter(d).assertt(InvocacaoDeUmMetodo.class);
				removeAfter(d).assertt(AbreParenteses.class);
				removeAfter(d).assertt(FechaParenteses.class);
				removeAfter(d).assertt(PontoEVirgula.class);
				removeBefore(d).assertt(Igual.class);

				DeclaracaoDeVariavelTs o = before(d);
				remove(d);

				dec.add(o);
				remove(o);

			});

		});

	}

	protected void finalizar() {

		ImportFrom imp = getImportFrom(OurTypes.class);
		ClassTs.typesUtilizados.each(i -> imp.add(i.getSimpleName(), true));

		removerImportsNaoUtilizados();
		jsons();
		jsonizarSupports();
		adicionarImportsCss();

		ImportFrom lastImport = filter(ImportFrom.class).getLast();

		if (lastImport != null) {
			after(lastImport).setQuebras(2);
		}

		if (getClasse().isAnnotationPresent(Interface.class)) {
			filter(DeclaracaoDeVariavelTs.class).each(i -> {
				i.setModificadorDeAcesso(null);
				i.setIgnorarExclamacao(true);
			});
			filter(ExportClass.class).get(0).setInterface(true);
		}

		if (filter(InvocacaoDeUmMetodo.class).anyMatch(i -> i.eq("useRef"))) {
			getImportFrom("react").add("useRef", true);
		}

		filter(IfWhile.class).each(i -> {

			AbreParenteses abre = after(i);

			Palavra a = after(abre);
			Palavra b = after(a);
			Palavra c = after(b);
			Palavra d = after(c);

			if (a.is(Negacao.class, Exclamacao.class) && b.is(Negacao.class, Exclamacao.class) && d == abre.getFechamento()) {
				remove(a);
				remove(b);
			}

		});

		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("dataCast")).each(i -> {
			AbreParenteses abre = after(i);
			while (after(abre) != abre.getFechamento()) {
				removeAfter(abre);
			}
			removeAfter(abre);
			remove(abre);
			replace(i, new InvocacaoDeUmAtributo("data"));
		});

		filter(AbreParenteses.class).each(i -> {
			after(i).setEspacos(0);
			if (after(i).is(AbreArrow.class)) {
				after(after(i)).setEspacos(0);
			}
		});

		if (!config.typeScript) {
//			filter(DeclaracaoDeVariavelTs.class)
//			.filter(i -> i.getModificadorDeAcesso() != null && !i.getModificadorDeAcesso().is(ModificadorDeAcessoDefault.class)).each(i -> {
//				ModificadorDeAcessoDefault o = new ModificadorDeAcessoDefault();
//				o.setStatic(i.isStatic());
//				i.setModificadorDeAcesso(o);
//			});

			filter(DeclaracaoDeVariavelTs.class).each(i -> {

				if (i.isStatic()) {
					ModificadorDeAcessoDefault o = new ModificadorDeAcessoDefault();
					o.setStatic(i.isStatic());
					i.setModificadorDeAcesso(o);
				} else {
					i.setModificadorDeAcesso(null);
				}

			});

			filter(DeclaracaoDeMetodoTs.class).each(i -> {

				if (i.isStatic()) {
					ModificadorDeAcessoDefault o = new ModificadorDeAcessoDefault();
					o.setStatic(i.isStatic());
					i.setModificadorDeAcesso(o);
				} else {
					i.setModificadorDeAcesso(null);
				}

				i.setGenericParam(null);

			});

			filter(InvocacaoDeUmMetodo.class).each(i -> {
				if (i.getNewTipo() instanceof TipoTs) {
					TipoTs tipo = (TipoTs) i.getNewTipo();
					tipo.setGenerics(null);
				}
			});

		}

		filter(i -> i.getComentarios().isNotEmpty()).each(i -> i.getComentarios().removeIf(c -> c.getS().startsWith("/* ignore")));

		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("parse")).each(i -> {
			AbreParenteses abreParenteses = after(i);

			FechaParenteses fechamento = abreParenteses.getFechamento();

			Palavra before = before(fechamento);

			if (before.is(Class_.class)) {
				while (!removeBefore(fechamento).is(Virgula.class)) {

				}
			}
		});

		if (isEnum()) {

			ExportClass exp = (ExportClass) palavras.get(0);

			Enum_ enumm = new Enum_();
			replace(exp, enumm);

			enumm.setQuebras(0);
			enumm.setEspacos(0);

			palavras.remove(exp);

			filter(PontoEVirgula.class).each(i -> remove(i));

			exp.setEnum(true);
			exp.setQuebras(2);

			palavras.add(exp);
			palavras.add(getTipoThis());
			palavras.add(new PontoEVirgula());

		}

		filter(FechaJson.class).filter(fecha -> fecha.getQuebras() == 0).forEach(fecha -> {

			Palavra o = before(fecha);

			int quebras = 0;

			while (o != fecha.getAbertura()) {
				quebras += o.getQuebras();
				o = before(o);
			}

			if (quebras == 0) {
				return;
			}

			while (o.getQuebras() == 0) {
				o = before(o);
			}

			fecha.setQuebras(1);
			fecha.setTabs(o.getTabs());
			fecha.setEspacos(0);

		});

		filter(AbreJson.class).forEach(abre -> {

			if (abre.getQuebras() == 0) {
				return;
			}

			if (after(abre) == abre.getFechamento()) {
//				nao quero que junte em objetos vazios
				return;
			}

			Palavra o = before(abre);

			if (!o.is(Virgula.class)) {
				return;
			}

			o = before(o);

			if (!o.is(FechaJson.class)) {
				return;
			}

			if (o.getQuebras() > 0) {
				abre.clearIdentacao();
			}

		});

		filter(AbreArray.class).forEach(abre -> {

			if (abre.getQuebras() == 0) {
				return;
			}

			if (after(abre) == abre.getFechamento()) {
//				nao quero que junte em objetos vazios
				return;
			}

			Palavra o = before(abre);

			if (!o.is(Virgula.class)) {
				return;
			}

			o = before(o);

			if (!o.is(FechaArray.class)) {
				return;
			}

			if (o.getQuebras() > 0) {
				abre.clearIdentacao();
			}

		});

		filter(DeclaracaoDeVariavelTs.class).forEach(dec -> {
			if (dec.getQuebras() == 0 && beforeIs(dec, Virgula.class)) {
				dec.clearIdentacao();
				dec.setEspacos(1);
			}
		});

		filter(FechaArray.class).forEach(fecha -> {

			if (fecha.getQuebras() == 0) {
				return;
			}

			if (before(fecha).getTabs() == fecha.getTabs()) {
				fecha.clearIdentacao();
			}

		});
		
		fecharBlocos();
		
		filter(FechaBloco.class).forEach(fecha -> {
			if (fecha.getQuebras() > 0) {
				fecha.setEspacos(0);
			}
		});

		filter(FechaJson.class).forEach(fecha -> {
			if (fecha.getQuebras() > 0) {
				fecha.setEspacos(0);
			}
		});

		filter(Virgula.class).forEach(virgula -> {
			if (beforeIs(virgula, FechaJson.class)
			 && afterIs(virgula, AbreJson.class)
			 && before(virgula).hasQuebra()
			 && after(after(virgula)).hasQuebra()
			) {
				virgula.clearIdentacao();
				after(virgula).clearIdentacao();
			}
		});

		filter(AbreJson.class).inverteOrdem().forEach(abre -> {
			
			Palavra o = abre;
			
			while (!o.hasQuebra()) {
				o = before(o);
			}
			
			int tabs = o.getTabs() + 1;
			FechaJson fecha = abre.getFechamento();
			o = after(abre);
			
			while (o != fecha) {
				if (o.hasQuebra() && o.getTabs() < tabs) {
					o.setTabs(tabs);
				}
				o = after(o);
			}
			
			if (fecha.hasQuebra()) {
				fecha.setTabs(tabs-1);
				o = after(abre);
				if (!o.hasQuebra()) {
					o.clearIdentacao();
					o.setQuebras(1);
					o.setTabs(tabs);
				}
				
				o = after(fecha);
				
				if (o.is(FechaParametro.class)) {
					FechaParametro fechaParametro = (FechaParametro) o;
					if (before(abre) == fechaParametro.getAbertura()) {
						abre.clearIdentacao();
					}
				}
				
				if (before(abre).is(JSonKey.class)) {
					abre.clearIdentacao();
					abre.setEspacos(1);
				}
				
			}
			
		});
		
		filter(LongLiteral.class).forEach(i -> i.semLnofinal());
		
	}
	
	/* adiciona um ponto e virgula apos o fechamento de cada bloco */
	protected void fecharBlocos() {
		
		if (!podeFecharBlocos) {
			return;
		}
		
		if (config.typeScript) {
			return;
		}

		filter(FechaBloco.class).forEach(fecha -> {

			Palavra o = after(fecha);
			if (o == null || o.is(FechaBloco.class, PontoEVirgula.class, Else.class)) {
				return;
			}
			
			if (o.is(DeclaracaoDeMetodoTs.class, FechaParentesesMetodo.class, DefaultPropsDeclaration.class)) {
				addAfter(fecha, new PontoEVirgula());
				return;
			}
			
			Palavra before = before(fecha.getAbertura());

			if (before.is(DefaultPropsDeclaration.class)) {
				addAfter(fecha, new PontoEVirgula());
				return;
			}
			
			if (before.is(FechaParentesesMetodo.class)) {
				FechaParentesesMetodo f = (FechaParentesesMetodo) before;
				DeclaracaoDeMetodoTs metodo = f.getMetodo();
				if (metodo.isDeclararComoArrow() || metodo.isDeclararComoFuncao()) {
					addAfter(fecha, new PontoEVirgula());
				}
				return;
			}
			
		});

		filter(FechaBloco.class).forEach(fecha -> {
			if (before(fecha).is(PontoEVirgula.class)) {
				fecha.setQuebras(1);
			}
		});

	}

	private void jsons() {

		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("json", "jsonn")).inverteOrdem().each(i -> {
			removeBefore(i).assertt(Ponto.class);
			Palavra first = removeBefore(i).assertt(TipoTs.class);
			removeAfter(i).assertt(AbreParenteses.class);
			removeAfter(i).assertt(FechaParenteses.class);
			jsoniza(i).absorverIdentacao(first);
		});

	}

	private void jsonizarSupports() {

		filter(TipoTs.class).filter(i -> {

			if (!after(i).is(Ponto.class)) {
				return false;
			}

			Type type = i.getClasse().getType();

			if (type == null) {
				return false;
			}

			if (!(type instanceof Class)) {
				return false;
			}

			Class<?> classe = (Class<?>) type;

			if (!classe.isAnnotationPresent(Support.class)) {
				return false;
			}

			return true;

		}).inverteOrdem().each(i -> jsoniza(i));

		filter(InvocacaoDeUmMetodo.class).filter(i -> {

			if (i.getNewTipo() == null || !i.getNewTipo().is(TipoTs.class)) {
				return false;
			}

			TipoTs tipo = (TipoTs) i.getNewTipo();

			Type type = tipo.getClasse().getType();

			if (type == null) {
				return false;
			}

			if (!(type instanceof Class)) {
				return false;
			}

			Class<?> classe = (Class<?>) type;

			if (!classe.isAnnotationPresent(Support.class)) {
				return false;
			}

			return true;

		}).inverteOrdem().each(i -> {
			removeAfter(i).assertt(AbreParenteses.class);
			removeAfter(i).assertt(FechaParenteses.class);
			jsoniza(i);
		});

		filter(InvocacaoDeUmMetodo.class).filter(i -> i.getS().contentEquals("{}")).inverteOrdem().each(i -> jsoniza(i));

	}

	private AbreJson jsoniza(Palavra i) {

		AbreJson abreJson = new AbreJson();

		replace(i, abreJson);

		boolean first = true;

		Palavra o = after(abreJson);

		while (o.is(Ponto.class)) {

			Ponto ponto = (Ponto) o;

			if (first) {
				first = false;
				o = after(ponto);
				remove(ponto);
				o.absorverIdentacao(ponto);
			} else {
				o = after(ponto);
				o.absorverIdentacao(ponto);
				remove(ponto);
				addBefore(o, new Virgula());
				if (!o.hasQuebra()) {
					o.setEspacos(1);
				}
			}

			JSonKey key;

			if (o.getS().contentEquals("kv$")) {
				StringLiteral k = removeAfter(after(o));
				removeAfter(after(o)).assertt(Virgula.class);
				after(after(o)).clearIdentacao();
				key = new JSonKey(k);
			} else {
				key = new JSonKey(o.getS());
			}

			replace(o, key);

			AbreParenteses abre = after(key);

			boolean vazio = after(abre) == abre.getFechamento();

			remove(abre);

			o = after(abre.getFechamento());
			remove(abre.getFechamento());

			if (vazio) {
				String s = key.getTexto();
				if (!s.contains("$")) {
					throw new NaoImplementadoException();
				}
				key.setTexto(StringBeforeFirst.get(s, "$"));
				s = StringAfterFirst.get(s, "$");
				s = s.replace("__", "-");

				StringLiteral ss = new StringLiteral();
				ss.setAbre(new AspasDuplas());
				ss.setFecha(new AspasDuplas());
				ss.add(new NaoClassificada(s));
				addAfter(key, ss);

			}

			if (key.getS().contentEquals("...")) {
				after(key).setEspacos(0);
			} else {
				after(key).setEspacos(1);
			}

		}

		addBefore(o, abreJson.getFechamento());

		Palavra after = after(abreJson.getFechamento());

		if (jsonizaDeveFechar(after)) {
			abreJson.getFechamento().absorverIdentacao(after);
		}

		return abreJson;

	}

	protected boolean jsonizaDeveFechar(Palavra o) {
		return o.is(FechaParenteses.class);
	}

	private void adicionarImportsCss() {

		ImportCss importCss = getClasse().getAnnotation(ImportCss.class);

		if (importCss != null) {

			Palavra o = palavras.getList().getFirst();

			if (!o.is(ImportFrom.class)) {
				o.setQuebras(1);
			}

			Lst<String> lst = new Lst<>(importCss.value()).inverteOrdem();
			for (String s : lst) {
				NaoClassificada x = new NaoClassificada("import \"" + s + "\";");
				o.incQuebra();
				addBefore(o, x);
				o = x;
			}

		}

	}

	protected void addOurtype(String s) {
		ImportFrom ourTypes = getImportFrom(OurTypes.class);
		ourTypes.add(s, true);
	}

	private void removerImportsNaoUtilizados() {

		Lst<DeclaracaoDeVariavelTs> decVars = config.typeScript ? filter(DeclaracaoDeVariavelTs.class) : new Lst<>();

		if (decVars.anyMatch(i -> i.getTipo().containsType("str"))) {
			addOurtype("str");
		}

		if (decVars.anyMatch(i -> i.getTipo().containsType("int"))) {
			addOurtype("int");
		}

		filter(ImportFrom.class).each(imp -> {

			imp.getTipos().each(tipo -> {

				if (tipo.getClasse().getType() == OurTypes.class) {
					imp.remove(tipo);
					return;
				}

				String sn = tipo.getClasse().getSimpleName();

				if (sn.contentEquals("any")) {
					imp.remove(tipo);
					return;
				}

				if (sn.contentEquals(getClasse().getSimpleName())) {
					imp.remove(tipo);
					return;
				}

				if (!filter(p -> p != imp && ListString.separaPalavras(p.getS()).contains(sn)).isEmpty()) {
					return;
				}

				if (decVars.anyMatch(i -> {
					TipoTs t = i.getTipo();
					return t.possuiTipo(sn);
				})) {

					if (sn.contentEquals("str")) {

						if (decVars.filter(i -> i.getTipo().isString()).anyMatch(i -> {
							TipoTs t = i.getTipo();
							String s = t.getS(i.isNotNull());
							return s.contentEquals("str");
						})) {
							addOurtype("str");
							return;
						}

					} else if (sn.contentEquals("int")) {

						if (decVars.filter(i -> i.getTipo().isString()).anyMatch(i -> {
							TipoTs t = i.getTipo();
							String s = t.getS(i.isNotNull());
							return s.contentEquals("int");
						})) {
							addOurtype("int");
							return;
						}

					} else {
						return;
					}

				}

				if (!deveManter(tipo)) {
					imp.remove(tipo);
				}

			});

			if (imp.isEmpty()) {
				remove(imp);
			}

		});

	}

	protected boolean deveManter(TipoTs tipo) {
		return false;
	}

	private void initStaticBlocks() {

		if (config.aceitaStaticsBlocks) {
			return;
		}

		Lst<Static> itens = filter(Static.class).filter(i -> after(i).is(AbreBloco.class));

		for (Static staticc : itens) {

			ModificadorDeAcessoDefault acesso = new ModificadorDeAcessoDefault();
			acesso.setStatic(true);

			DeclaracaoDeMetodoTs m = new DeclaracaoDeMetodoTs(acesso, TipoTs.newVoid(), "initStatic" + itens.indexOf(staticc), null);
			m.setFechamento("() : void");
			replace(staticc, m);

			NaoClassificada o = new NaoClassificada(getSimpleName() + "." + m.getNome() + "();");

			if (itens.indexOf(staticc) == 0) {
				o.setQuebras(2);
			} else {
				o.setQuebras(1);
			}

			palavras.add(o);

		}

	}

	private void removeInJava() {

		filter(InvocacaoDeUmAtributo.class).filter(i -> i.getS().contentEquals("inJava")).each(i -> {

			Ponto ponto = before(i);
			remove(ponto);

			TipoJava tipo = before(i);
			remove(tipo);

			AbreParenteses abre = before(i);
			remove(abre);

			If iif = before(i);
			remove(iif);

			FechaParenteses fecha = after(i);
			remove(fecha);

			AbreBloco abreBloco = after(i);

			while (after(i) != abreBloco.getFechamento()) {
				Palavra o = removeAfter(i);
				if (o.is(DeclaracaoDeVariavelRef.class)) {
					DeclaracaoDeVariavelRef ref = (DeclaracaoDeVariavelRef) o;
					ref.getDec().refs.remove(ref);
				}
			}

			removeAfter(i);
			remove(i);

		});

	}

	private void removeImport(Type type) {
		importsFrom.filter(i -> i.getTipos().anyMatch(tipo -> tipo.eq(type))).each(i -> {
			TipoTs tp = i.getTipos().unique(tipo -> tipo.eq(type));
			i.remove(tp);
			if (i.isEmpty()) {
				palavras.remove(i);
			}
		});
	}

	protected Atributos getAs() {
		Atributos as = AtributosBuild.get(getClasse()).filter(i -> i.getField().getDeclaringClass() == getClasse());
		if (as.getId() != null) {
			as.add(0, as.getId());
		}
		return as;
	}

	private void getters() {

		if (isEnum()) {
			return;
		}

		Atributos as = getAs();
		as.removeStatics();
		as.removeIf(i -> i.getField().getDeclaringClass() != getClasse());

		if (!annotations.removeIf(i -> i.getType().eq(Getter.class))) {
			as.removeIf(a -> !a.hasAnnotation(Getter.class));
		}

		if (as.isEmpty()) {
			return;
		}

		Lst<DeclaracaoDeMetodoTs> jaDeclarados = palavras.filter(DeclaracaoDeMetodoTs.class);

		as.removeIf(a -> jaDeclarados.anyMatch(m -> m.getNome().contentEquals("get" + a.upperNome())));

		if (as.isEmpty()) {
			return;
		}

		Lst<DeclaracaoDeVariavelTs> itens = filter(DeclaracaoDeVariavelTs.class).filter(i -> i.isAtributo() && as.contains(i.getNome()));

		Palavra ultimo = before(filter(FechaBloco.class).getLast());

		for (Atributo a : as) {

			DeclaracaoDeVariavelTs dec = itens.unique(i -> i.getNome().contentEquals(a.nome()));

			if (dec == null) {
				System.out.println();
			}
			
			TipoTs tipo = dec.getTipo().clone();

			DeclaracaoDeMetodoTs m = new DeclaracaoDeMetodoTs(new Public(), tipo, "get" + a.upperNome(), null);
			m.getModificadorDeAcesso().incQuebra().incQuebra().incTab();

			Lst<Palavra> lst = new Lst<>();
			lst.add(m);
			m.incQuebra().incQuebra().incTab();

			AbreParentesesMetodo abre = new AbreParentesesMetodo(m);
			FechaParentesesMetodo fecha = new FechaParentesesMetodo(m);

			lst.add(abre);
			lst.add(fecha);

			AbreBloco abreBloco = new AbreBloco();
			abreBloco.setFechamento(new FechaBloco());
			abreBloco.incEspaco();

			lst.add(abreBloco);

			lst.add(new Return().incQuebra().incTab().incTab());
			lst.add(new ReferenciaAUmaVariavelTs(dec).incEspaco());
			lst.add(new PontoEVirgula());

			lst.add(abreBloco.getFechamento().incQuebra().incTab());

			for (Palavra o : lst) {
				palavras.addAfter(ultimo, o);
				ultimo = o;
			}

		}

	}

	protected boolean isEnum() {
		return tipoDeClasse == TipoDeClasse.ENUM;
	}

	private void setters() {

		if (isEnum()) {
			return;
		}

		Atributos as = getAs();
		as.removeStatics();

		if (!annotations.removeIf(i -> i.getType().eq(Setter.class))) {
			as.removeIf(a -> !a.hasAnnotation(Setter.class));
		}

		if (as.isEmpty()) {
			return;
		}

		Lst<DeclaracaoDeMetodoTs> jaDeclarados = palavras.filter(DeclaracaoDeMetodoTs.class);

		as.removeIf(a -> jaDeclarados.anyMatch(m -> m.getNome().contentEquals("set" + a.upperNome())));

		if (as.isEmpty()) {
			return;
		}

		Lst<DeclaracaoDeVariavelTs> itens = filter(DeclaracaoDeVariavelTs.class).filter(i -> i.isAtributo() && as.contains(i.getNome()));

		Palavra ultimo = before(filter(FechaBloco.class).getLast());

		for (Atributo a : as) {

			DeclaracaoDeVariavelTs dec = itens.unique(i -> i.getNome().contentEquals(a.nome()));

			DeclaracaoDeMetodoTs m = new DeclaracaoDeMetodoTs(new Public(), TipoTs.newVoid(), "set" + a.upperNome(), null);
			m.getModificadorDeAcesso().incQuebra().incQuebra().incTab();

			Lst<Palavra> lst = new Lst<>();
			lst.add(m);
			m.incQuebra().incQuebra().incTab();

			AbreParentesesMetodo abre = new AbreParentesesMetodo(m);
			FechaParentesesMetodo fecha = new FechaParentesesMetodo(m);

			TipoTs tipo = dec.getTipo().clone();

			DeclaracaoDeVariavelTs param = new DeclaracaoDeVariavelTs(tipo, "value");
			param.setEscopo(DeclaracaoDeVariavelEscopo.parametro);

			lst.add(abre);
			lst.add(param);
			lst.add(fecha);

			AbreBloco abreBloco = new AbreBloco();
			abreBloco.setFechamento(new FechaBloco());
			abreBloco.incEspaco();

			lst.add(abreBloco);

			lst.add(new ReferenciaAUmaVariavelTs(dec).incQuebra().incTab().incTab());
			lst.add(new Igual().incEspaco());
			lst.add(new ReferenciaAUmaVariavelTs(param).incEspaco());
			lst.add(new PontoEVirgula());

			lst.add(abreBloco.getFechamento().incQuebra().incTab());

			for (Palavra o : lst) {
				palavras.addAfter(ultimo, o);
				ultimo = o;
			}

		}

	}

	private void newArray() {

		Is0.func = o -> o.is(TipoJava.class) && o.getS().contentEquals("Array");

		palavras.replace(New.class, Is0.class).por(lst -> {

			Palavra p0 = lst.rm();
			TipoJava tipo = lst.rm();

			AbreParenteses abre = after(tipo);

			AbreArray abreArray = new AbreArray();
			abreArray.setFechamento(new FechaArray());

			replace(abre, abreArray);
			replace(abre.getFechamento(), abreArray.getFechamento());

			abreArray.absorverIdentacao(p0);

			return lst;

		});

	}

	private void injecoes() {

		if (isEnum()) {
			return;
		}

		Atributos as = AtributosBuild.get(getClasse()).filter(a -> a.hasAnnotation(Injectable.class));

		if (as.isEmpty()) {
			return;
		}

		Palavra o = filter(ExportClass.class).getFirst();

		while (!o.is(AbreBloco.class)) {
			o = after(o);
		}

		Constructor constructor = new Constructor();
		constructor.setQuebras(2);
		constructor.setTabs(1);

		o = palavras.addAfter(o, constructor);

		AbreParenteses abre = new AbreParenteses();
		o = palavras.addAfter(o, abre);

		FechaParenteses fecha = new FechaParenteses();
		abre.setFechamento(fecha);
		fecha.setQuebras(1);
		fecha.setTabs(1);

		o = palavras.addAfter(o, fecha);
		AbreBloco abreBloco = palavras.addAfter(o, new AbreBloco());
		FechaBloco fechaBloco = palavras.addAfter(abreBloco, new FechaBloco());
		abreBloco.setFechamento(fechaBloco);

		Lst<DeclaracaoDeVariavelTs> itens = filter(DeclaracaoDeVariavelTs.class).filter(i -> i.isAtributo() && as.contains(i.getNome()));

		for (DeclaracaoDeVariavelTs i : itens) {
			PontoEVirgula pv = after(i);
			remove(pv);
			remove(i);
			i.setQuebras(1);
			i.setTabs(2);
			palavras.addBefore(fecha, i);
			if (i != itens.getLast()) {
				palavras.addBefore(fecha, new Virgula());
			}
		}

	}

	private void metodosEspeciais() {
		metodosString();
		metodosArray();
		metodoError();
		then();
		typeValues();
	}

	private void typeValues() {
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("typeValue")).each(i -> {
			AbreParenteses abre = after(i);
			Palavra value = removeAfter(abre);
			remove(abre);
			remove(abre.getFechamento());
			removeBefore(i).assertt(Ponto.class);
			Palavra tipo = before(i);
			remove(i);
			replace(tipo, value);
		});
	}

	private void then() {
		Lst<InvocacaoDeUmMetodo> invs = filter(InvocacaoDeUmMetodo.class);
		invs.filter(i -> i.eq("finally_")).each(i -> i.setS("finally"));
		invs.filter(i -> i.eq("catch_")).each(i -> i.setS("catch"));
	}

	private void metodoError() {

		Is0.func = o -> o.is(DeclaracaoDeVariavelTsRef.class) && ((DeclaracaoDeVariavelTsRef) o).getDec().getTipo().eq("Error");
		Is1.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("getMessage");

		palavras.replace(Is0.class, Ponto.class, Is1.class).por(lst -> {
			InvocacaoDeUmMetodo m = lst.removeLast();
			lst.add(new InvocacaoDeUmAtributo("message"));
			palavras.removeAfter(m);
			palavras.removeAfter(m);
			return lst;
		});

	}

	private void metodosString() {

		Is0.func = o -> o.is(DeclaracaoDeVariavelTsRef.class) && ((DeclaracaoDeVariavelTsRef) o).getDec().getTipo().isString();
		Is1.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("length");

		palavras.replace(Is0.class, Ponto.class, Is1.class).por(lst -> {
			InvocacaoDeUmMetodo m = lst.removeLast();
			lst.add(new InvocacaoDeUmAtributo(m.getS()));
			palavras.removeAfter(m);
			palavras.removeAfter(m);
			return lst;
		});

//		Is1.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("compareTo");
//		palavras.replace(Is0.class, Ponto.class, Is1.class).por(lst -> {
//			InvocacaoDeUmMetodo m = lst.getLast();
//			m.setS("localeCompare");
//			return lst;
//		});
		
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("split")).each(i -> {
			
			Palavra o = before(i);

			if (o.is(Ponto.class)) {

				o = before(o);
				
				if (o.is(TipoJava.class)) {
					TipoJava tipo = (TipoJava) o;
					if (tipo.getClasse() == js.array.Array.class) {
						AbreParenteses abre = after(i);
						DeclaracaoDeVariavelTsRef ref = after(abre);
						removeAfter(ref).assertt(Virgula.class);
						after(ref).setEspacos(0);
						remove(ref);
						replace(tipo, ref);
					}
				}

			}
			
		});
		
		filter(InvocacaoDeUmMetodo.class).filter(i -> i.eq("length", "compareTo", "lengthArray")).each(i -> {

			Palavra o = before(i);

			if (o.is(Ponto.class)) {

				o = before(o);

				if (o.is(FechaParenteses.class)) {
					FechaParenteses fecha = (FechaParenteses) o;
					AbreParenteses abre = fecha.getAbertura();
					o = before(abre);
				}

				Class<?> returnType = null;

				if (i.eq("lengthArray")) {
					returnType = Array.class;
				} else if (o.is(InvocacaoDeUmMetodo.class)) {
					InvocacaoDeUmMetodo inv = (InvocacaoDeUmMetodo) o;
					if (inv.getMetodo() == null) {
						return;
					}
					returnType = inv.getMetodo().getMethod().getReturnType();
				} else if (o.is(InvocacaoDeUmAtributo.class)) {
					returnType = String.class;
//					throw new NaoImplementadoException();
				} else if (o.is(DeclaracaoDeVariavelTsRef.class)) {
					DeclaracaoDeVariavelTsRef ref = (DeclaracaoDeVariavelTsRef) o;
					if (ref.getDec().getTipo().eq("str")) {
						returnType = String.class;
					} else if (ref.getDec().getTipo().eq("int")) {
						returnType = int.class;
					} else if (ref.getDec().getTipo().getClasse().getSimpleName().contentEquals("Array")) {
						returnType = Array.class;
					} else {
//						SystemPrint.ln("1941 " + i);
					}
				} else if (o.is(This.class)) {
					return;
				} else {
					SystemPrint.ln("1946 " + i);
				}

				if (i.eq("length")) {

					if (returnType == null) {
						throw new NaoImplementadoException();
					}

					if (returnType.equals(String.class) || returnType.equals(Array.class)) {
						o = new InvocacaoDeUmAtributo(i.getS());
						replace(i, o);
						removeAfter(o);
						removeAfter(o);
					}

				} else if (i.eq("compareTo")) {
					i.setS("localeCompare");
				} else if (i.eq("lengthArray")) {
					o = new InvocacaoDeUmAtributo("length");
					replace(i, o);
					removeAfter(o);
					removeAfter(o);
				} else {
					throw new NaoImplementadoException();
				}

			}

		});

	}

	private void metodosArray() {

		Is0.func = o -> o.is(DeclaracaoDeVariavelTsRef.class) && ((DeclaracaoDeVariavelTsRef) o).getDec().getTipo().eq(Array.class);
		Is1.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("length");

		palavras.replace(Is0.class, Ponto.class, Is1.class).por(lst -> {
			InvocacaoDeUmMetodo m = lst.removeLast();
			lst.add(new InvocacaoDeUmAtributo(m.getS()));
			palavras.removeAfter(m);// abreParenteses
			palavras.removeAfter(m);// fechaParenteses
			return lst;
		});

		Is1.func = o -> o.is(InvocacaoDeUmMetodo.class) && o.eq("get");

		palavras.replace(Is0.class, Ponto.class, Is1.class).por(lst -> {
			Palavra get = lst.removeLast();// get
			lst.removeLast();// ponto
			AbreParenteses abre = palavras.after(get);
			AbreArray abreArray = new AbreArray();
			abreArray.setFechamento(new FechaArray());
			palavras.replace(abre, abreArray);
			palavras.replace(abre.getFechamento(), abreArray.getFechamento());
			return lst;
		});

	}

	private void addAnnotation(JcTipo tipo) {
		anotacoesJcTipo.ad(tipo);
	}

	private void colocarThis() {

		if (isEnum()) {
			return;
		}

		Atributos as = AtributosBuild.get(getClasse());

		for (Atributo a : as) {

			Lst<Palavra> itens = filter(o -> o.is(InvocacaoDeUmAtributo.class, NaoClassificada.class) && o.getS().contentEquals(a.nome()));

			for (Palavra item : itens) {

				Palavra o = before(item);

				if (o.is(Ponto.class, TipoTs.class)) {
					continue;
				}

				boolean deveColocar = true;

				while (true) {

					o = before(o);

					if (o == null) {
						break;
					}

					if (o instanceof DeclaracaoDeMetodoTs) {
						break;
					}

					if (o instanceof DeclaracaoDeVariavelTs) {
						DeclaracaoDeVariavelTs d = (DeclaracaoDeVariavelTs) o;
						if (d.getNome().contentEquals(a.nome())) {
							deveColocar = false;
							ReferenciaAUmaVariavelTs obj = new ReferenciaAUmaVariavelTs(d);
							replace(item, obj);
							break;
						}

					}

				}

				if (deveColocar) {
					addThis(item, a.isStatic());
				}

			}

		}

		Lst<InvocacaoDeUmMetodo> invocacoes = filter(InvocacaoDeUmMetodo.class);

		if (invocacoes.isNotEmpty()) {

//			if (!config.aceitaClassNull) {
//
//			}

			invocacoes.filter(i -> i.eq("is") && before(i).is(Ponto.class)).filter(i -> {
				Palavra b = before(before(i));

				if (b.eq("Null") && !config.aceitaClassNull) {
					return true;
				}

				if (b.eq("StringEmpty") && !config.aceitaStringEmpty) {
					return true;
				}

				return false;

			}).each(i -> {

				AbreParenteses abre = after(i);
				Palavra a = after(abre);
				remove(abre);
				Palavra b = before(abre.getFechamento());
				remove(abre.getFechamento());
				removeBefore(i);
				Palavra nullClass = before(i);
				replace(nullClass, new Negacao());
				remove(i);

				if (a == b && a.is(DeclaracaoDeVariavelTsRef.class)) {

					DeclaracaoDeVariavelTsRef ref = (DeclaracaoDeVariavelTsRef) a;

//					if (ref.getDec().getTipo().getClasse() == ClassTs.stringTs) {
//						 SystemPrint.row("");
//					} else {
					ref.getDec().setPodeRetornarNull(true);
//					}

				}

				invocacoes.remove(i);

			});

			Metodos metodos = ListMetodos.get(getClasse());

			for (Metodo m : metodos) {

				Lst<InvocacaoDeUmMetodo> candidatos = invocacoes.filter(o -> o.getS().contentEquals(m.getName()) && !(before(o) instanceof Ponto));
				candidatos.each(o -> {

					if (m.getName().contentEquals("isNull_")) {
						AbreParenteses abre = after(o);
						remove(abre);
						remove(abre.getFechamento());
						replace(o, new Negacao());
						return;
					}

					if (m.getName().contentEquals("newO")) {
						AbreParenteses abre = after(o);
						remove(abre);
						remove(abre.getFechamento());
						replace(o, new NaoClassificada("{}"));
						return;
					}

					if (m.getName().contentEquals("join$")) {

						AbreParenteses abre = after(o);
						JoinOpen joinOpen = new JoinOpen();
						remove(abre);
						replace(o, joinOpen);
						replace(abre.getFechamento(), joinOpen.getFechamento());
						Palavra i = after(joinOpen);

						Reticencias ret = new Reticencias();
						ret.absorverIdentacao(i);
						addBefore(i, ret);

						while (i != joinOpen.getFechamento()) {

							i = after(i);

							if (i.is(Virgula.class)) {
								ret = new Reticencias();
								addAfter(i, ret);
								i = after(ret);
								ret.absorverIdentacao(after(ret));
							}

							if (i.is(AbreParenteses.class)) {
								abre = (AbreParenteses) i;
								i = after(abre.getFechamento());
							}

						}

						return;

					}

					if (m.getName().contentEquals("isNotNull_")) {
						AbreParenteses abre = after(o);
						remove(o);
						remove(abre);
						remove(abre.getFechamento());
						return;
					}

					if (m.getName().contentEquals("jsonParse")) {
						o.setS("JSON.parse");
						AbreParenteses abre = after(o);
						Palavra s = after(abre);
						while (after(s) != abre.getFechamento()) {
							palavras.removeAfter(s);
						}
						return;
					}

					if (m.getName().contentEquals("split")) {
						AbreParenteses abre = after(o);
						Palavra s = removeAfter(abre);
						removeAfter(abre).assertt(Virgula.class);
						addBefore(o, s);
						addBefore(o, new Ponto());
						s.absorverIdentacao(o);
						return;
					}

					if (m.getName().contentEquals("or")) {

						AbreParenteses abre = after(o);
						Palavra first = after(abre);
						remove(abre);
						remove(o);
						first.absorverIdentacao(o);

						Palavra x = first;

						while (x != abre.getFechamento()) {

							if (x.is(AbreParenteses.class)) {
								AbreParenteses a = (AbreParenteses) x;
								x = a.getFechamento();
								continue;
							}

							if (x.is(Virgula.class)) {
								x.setEspacos(1);
								Or or = new Or();
								replace(x, or);
								x = after(or);
								continue;
							}

							x = after(x);

						}

						remove(abre.getFechamento());

//						last.setComplemento(" || null");
						return;
					}
					
					if (m.getName().contentEquals("orNull")) {
						AbreParenteses abre = after(o);
						Palavra first = after(abre);
						remove(abre);
						Palavra last = before(abre.getFechamento());
						remove(abre.getFechamento());
						remove(o);
						first.absorverIdentacao(o);
						last.setComplemento(" || null");
						return;
					}

					if (m.getName().contentEquals("fromEntries")) {
						o.setS("Object.fromEntries");
						return;
					}

					if (m.getName().contentEquals("as")) {
						AbreParenteses abre = after(o);
						remove(o);
						Palavra obj = after(abre);
						remove(abre);
						obj.absorverIdentacao(o);
						remove(before(abre.getFechamento()));
						remove(before(abre.getFechamento()));

						Palavra tipo = before(abre.getFechamento());
						Virgula v = before(tipo);
						NaoClassificada ass = new NaoClassificada("as");
						replace(v, ass);
						ass.setEspacos(1);

						remove(abre.getFechamento());

						return;
					}

					if (m.getName().contentEquals("ppp")) {

						AbreParenteses abre = removeAfter(o);
						remove(abre);

						AbreJson abreJson = new AbreJson();
						replace(o, abreJson);
						replace(abre.getFechamento(), abreJson.getFechamento());

						abreJson.setComplemento("...");

						return;

					}

					if (m.getName().contentEquals("js")) {
						AbreParenteses abre = after(o);
						remove(abre);
						remove(abre.getFechamento());
						Palavra x = after(o);
						remove(o);
						x.absorverIdentacao(o);
						return;
					}

					if (m.getName().contentEquals("undefinedIfNull")) {

						AbreParenteses abre = after(o);
						remove(o);
						addAfter(abre.getFechamento(), new NaoClassificada(" || undefined"));

//						TODO verificar quando pode ser expressao

//						if (after(abre) == before(abre.getFechamento())) {
						remove(abre);
						remove(abre.getFechamento());
//						}
						return;
					}

					if (m.getName().contentEquals("cast")) {
						AbreParenteses abre = after(o);
						remove(abre);
						after(o).absorverIdentacao(o);
						remove(o);
						remove(abre.getFechamento());
						return;
					}

					if (m.getName().contentEquals("js_attr")) {

						AbreParenteses abre = after(o);
						remove(o);
						final Palavra var = after(abre);
						var.absorverIdentacao(o);
						Palavra p = after(var);
						while (!p.is(Virgula.class)) {
							if (p.is(AbreParenteses.class)) {
								AbreParenteses abreP = (AbreParenteses) p;
								p = after(abreP.getFechamento());
								continue;
							}
							p = after(p);
						}

						Palavra obj = after(p);

						if (obj instanceof StringLiteral) {

							StringLiteral sl = (StringLiteral) obj;

							if (ehUmNomeDeAtributoValido(sl.getTexto())) {

								replace(p, new Ponto());
								InvocacaoDeUmMetodo inv = new InvocacaoDeUmMetodo(sl.getTexto());
								replace(sl, inv);
								inv.clearIdentacao();

								if (var.is(DeclaracaoDeVariavelTsRef.class)) {
									DeclaracaoDeVariavelTsRef ref = (DeclaracaoDeVariavelTsRef) var;
									DeclaracaoDeVariavelTs dec = ref.getDec();

									if (dec.getTipo().isString()) {
										dec.getTipo().setClasse(ClassTs.anyTs);
									}

								}

								remove(abre);
								remove(abre.getFechamento());

								return;

							}

						}

						p.assertt(Virgula.class);

						AbreColcheteField abreColcheteField = new AbreColcheteField();

						replace(p, abreColcheteField);
						remove(abre);
						replace(abre.getFechamento(), abreColcheteField.getFechamento());
						after(abreColcheteField).clearIdentacao();

						return;

					}

					if (tratarInvocacaoAoCololocarThis(o, m)) {
						return;
					}

					addThis(o, m.isStatic());

				});
			}

			invocacoes.filter(o -> o.getNewTipo() != null).each(o -> {
				TipoJava tipo = (TipoJava) o.getNewTipo();
				TipoTs tipoTs = TipoTs.build(tipo);
				o.setNewTipo(tipoTs);
			});

		}

	}

	private boolean ehUmNomeDeAtributoValido(String s) {

		if (s == null) {
			return false;
		}

		s = s.trim();

		if (s.isEmpty()) {
			return false;
		}

		if (s.contains(" ") || s.contains("-") || IntegerIs.is(s.substring(0, 1))) {
			return false;
		}

		return true;
	}

	protected boolean tratarInvocacaoAoCololocarThis(InvocacaoDeUmMetodo o, Metodo m) {
		return false;
	}

	private void addThis(Palavra o, boolean staticc) {
		Palavra ref = staticc ? getTipoThis().clone() : new This();
		palavras.addBefore(o, ref.absorverIdentacao(o));
		palavras.addBefore(o, new Ponto());
	}

	private void removePackage() {
		getPackageRow().each(i -> {
			remove(i);
			if (i.getComentarios().isNotEmpty()) {
				palavras.comentarioInicial = i.getComentarios().get(0);
			}
		});
	}

	public TipoTs getTipoThisTs() {
		return TipoTs.build(getTipoThis());
	}

	public void addImport(String name, String from, boolean staticc) {
		getImportFrom(from).add(name, staticc);
	}

	@Override
	protected void preTratarListString(ListString list) {

		super.preTratarListString(list);

		list.filter(i -> i.startsWith("import")).forEach(s -> {
			Class<?> cs = UClass.getClassObrig(s);
			if (UClass.a_herda_b(cs, StringValues.class)) {
				StringValues obj = (StringValues) UClass.newInstance(cs);
				obj.replaceInList(list);
			}
		});

		list.replaceTexto(".self()", "");
		list.replaceTexto(" extends JS {", " {");
		list.replaceTexto("new RuntimeException()", "new Error()");
		list.replaceTexto("Integer.parseInt(", "parseInt(");
		list.replaceTexto("fontFamily(\"Poppins\")", "fontFamily(\"Poppins, sans-serif\")");
		list.replaceTexto("Js.get(", "get_atributo(");
		list.replaceTexto("new NaoImplementadoException()", "new Error(\"NaoImplementadoException\")");
		list.replaceTexto("new NaoImplementadoException(\"", "new Error(\"NaoImplementadoException: ");
		list.replaceTexto("new NaoImplementadoException(", "new Error(");
		list.removeIf(s -> s.trim().startsWith("//"));

		tiposConhecidos.add(new TipoJava(Js.class));
		tiposConhecidos.add(new TipoJava(Number.class));
		
		if (!config.typeScript) {
			list.removeIf(s -> s.contains(" abstract ") && s.endsWith(";"));
		}

	}

	@Override
	public ListString getResult() {

		ListString list = super.getResult();
		list.replaceTexto(" extends any ", " ");
		list.replaceTexto("e.getMessage()", "e.message");
		list.replaceTexto("JsMath", "Math");
		list.replaceTexto("formhtml", "form");
		list.replaceTexto("number.isInteger", "Number.isInteger");

		if (config.typeScript) {
			list.replaceTexto("} catch (e) {", "} catch (e : any) {");
			list.replaceTexto("Throwable e", "e : any");
			list.replaceTexto("<Throwable>", "<any>");
			list.replaceTexto("/*AxiosError*/(e : Throwable)", "(e : AxiosError)");
		} else {
			list.replaceTexto(" abstract ", " ");
		}
		
		list.replaceTexto("{ }", "{}");
		list.replaceTexto("{  }", "{}");
		
		if (getClasse().isAnnotationPresent(TypeJs.class)) {
			String sn = getClasse().getSimpleName();

			list.replaceTexto("public ", "");
			list.replaceTexto("!", "?");
			String s = "type " + sn + " = {";
			list.replaceTexto("export default class " + sn + " {", s);

			list.removeEmptys();
			list.removeLast();
			list.add("} | null | undefined;");

			int ix = list.indexOf(s);

			if (ix > 0) {
				list.add(ix, "");
			}

			list.add();
			list.add("export default " + sn + ";");
		}
		
		list.replaceTexto("System.currentTimeMillis()", "new Date().getTime()");
		list.replaceTexto("!!", "");
		
		LimparRemoverImportsNaoUtilizadosDeUmTs.exec(list);
		
		if (config.tabReplace != null) {
			list.replaceEach(s -> {
				String tab = "";
				while (s.startsWith("\t")) {
					s = s.substring(1);
					tab += config.tabReplace;
				}
				return tab + s;
			});
		}
		
//		list.replaceEach(s -> {
//			if (s.trim().contentEquals("}") && s.replace("\t", "").contentEquals(" }") ) {
//				s = s.replace(" }", "}");
//			}
//			return s;
//		});

		return list;

	}

}