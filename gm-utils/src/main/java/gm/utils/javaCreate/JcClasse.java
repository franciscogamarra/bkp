package gm.utils.javaCreate;

import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import gm.utils.classes.ClassBox;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.config.UConfig;
import gm.utils.files.GFile;
import gm.utils.files.UFile;
import gm.utils.lambda.F0;
import gm.utils.lambda.P1;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.Classe;
import gm.utils.string.ListString;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import src.commom.utils.array.Itens;
import src.commom.utils.comum.Print;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringPrimeiraMinuscula;

@Getter
public class JcClasse {

	public String getSimpleName() {
		return tipo.getSimpleName();
	}

	private final JcAnotacoes anotations = new JcAnotacoes();
	private final JcTipos interfaces = new JcTipos();
	private final ListString statesSets = new ListString();
	private final JcAtributos atributos = new JcAtributos(this);
	private final JcTipos imports = new JcTipos();
	private final Lst<JcMetodo> metodos = new Lst<>();
	private final Lst<JcClasse> inners = new Lst<>();
	private final String path;
	private Class<?> exceptionClass = RuntimeException.class;

	private final ListString comentarios = new ListString();
	private final ListString adds = new ListString();
	private final ListString primeirasLinhasDaClasse = new ListString();
	private boolean sortMetodos = false;
	private boolean abstract_ = false;
	private boolean singleton = false;

	@Setter
	private boolean final_ = false;
	private Map<String, String> replacesTexto = new HashMap<>();
	private final JcTipo tipo;
	private JcTipo extends_;

	public boolean jsClass = false;
	private boolean isInterface = false;
	private boolean isEnum = false;
	private boolean formal = false;
	@Setter private boolean anyThrow = false;

	@Setter private boolean pularLinhaAntesDeCadaMetodo = true;
	private boolean js;

	@Setter
	private int java = 18;
	
	private static Lst<JcClasse> instancias = new Lst<>();
	private static void addInstancia(JcClasse o) {
		if (o.getName().endsWith("AnoMesCamposAbstract")) {
			Print.ln(o);
		}
		if (getInstancia(o.getName()) != null) {
			throw new RuntimeException("Instancia já criada: " + o.getName());
		}
		instancias.add(o);
	}

	public static synchronized JcClasse getInstancia(String nome) {
		return instancias.unique(o -> StringCompare.eq(o.getName(), nome));
	}

	public static synchronized void clearInstancias() {
		instancias.clear();
	}

	public static JcClasse getInstancia(JcTipo tipo) {
		return getInstancia(tipo.getName());
	}

	protected JcClasse(JcTipo tipo, String path) {
		this.tipo = tipo;
		this.path = path;
		addInstancia(this);
	}

	public static JcClasse build(JcTipo tipo, String path) {
		return new JcClasse(tipo, path);
	}

	public static JcClasse build(String path, Class<?> classe) {
		JcTipo tipo = new JcTipo(classe);
		return build(tipo, path);
	}

	public static JcClasse build(Class<?> classe) {
		return build(getPath(classe), classe);
	}

	public static String getPath(Class<?> classe) {
		return StringBeforeFirst.get(UClass.getJavaFileName(classe), "/src/main/java/") + "/src/main/java/";
	}
	
	public static JcClasse build(Class<?> classe, String sufixo) {
		return getOrBuild(getPath(classe), new JcTipo(classe.getName() + sufixo));
	}
	
	public static JcClasse build(GFile file) {
		return build(GFile.get(StringBeforeLast.get(file.toString(), "/src/main/java/") + "/src/main/java/"), file.toClassName());
	}
	
	public static JcClasse build(GFile path, String nome) {
		return getOrBuild(path.toString(), new JcTipo(nome));
	}

	public static JcClasse buildReal(Class<?> classe) {
//		TODO completar
		JcClasse o = build(classe);
		Atributos as = AtributosBuild.get(o);
		for (Atributo a : as) {
			o.atributo(a);
		}
		return o;
	}

	public static JcClasse getOrBuild(String path, JcTipo tipo) {
		JcClasse o = getInstancia(tipo);
		if (o == null) {
			return build(tipo, path);
		}
		return o;
	}

	public static JcClasse getOrBuild(String path, String pkg, String nome) {
		return getOrBuild(path, new JcTipo(pkg + "." + nome));
	}

	public static JcClasse build(String path, Package pkg, String nome) {
		return build(path, pkg.getName(), nome);
	}

	public static JcClasse build(Package pkg, String nome) {
		return build(pkg.getName(), nome);
	}

	public static JcClasse build(String path, String pacote, String nome) {
		JcTipo tipo = new JcTipo(pacote + "." + nome);
		return build(tipo, path);
	}

	public static String getCurrentPath() {
		return UConfig.get().getPathRaizProjetoAtual() + "java/";
	}
	
	public static JcClasse build(String pacote, String nome) {
		return build(getCurrentPath(), pacote, nome);
	}
	
	public static JcClasse build(JcTipo tipo) {
		return build(tipo, getCurrentPath());
	}

	public JcClasse extends_(JcTipo classe) {

		if (classe == null) {
			throw new NullPointerException("classe == null");
		}

		extends_ = classe;
		return this;
	}

	public JcClasse extends_(JcTipo classe, String... generics) {
		extends_ = classe;
		for (String s : generics) {
			extends_.addGenerics(s);
		}
		return this;
	}

	public JcClasse extends_(JcTipo classe, JcTipo... generics) {
		extends_ = classe;
		for (JcTipo gen : generics) {
			extends_.addGenerics(gen);
		}
		return this;
	}
	
	public JcClasse extends_(JcTipo classe, JcTipo generics1, Class<?> generics2) {
		JcTipo g2 = new JcTipo(generics2);
		return extends_(classe, generics1, g2);
	}

	public JcClasse extends_(Class<?> classe, JcTipo generics1, Class<?> generics2) {
		JcTipo cs = new JcTipo(classe);
		JcTipo g2 = new JcTipo(generics2);
		return extends_(cs, generics1, g2);
	}
	
	public JcClasse extends_(JcTipo classe, ToJcTipo... generics) {
		extends_ = classe;
		for (ToJcTipo gen : generics) {
			extends_.addGenerics(gen);
		}
		return this;
	}
	
	public JcClasse extends_(JcTipo classe, Lst<JcTipo> generics) {
		extends_ = classe;
		for (JcTipo gen : generics) {
			extends_.addGenerics(gen);
		}
		return this;
	}
	
	public JcClasse extends_(JcTipo classe, Class<?>... generics) {
		extends_ = classe;
		for (Class<?> gen : generics) {
			extends_.addGenerics(gen);
		}
		return this;
	}

	public JcClasse extends_(Class<?> classe, ToJcTipo... generics) {
		return extends_(new JcTipo(classe), generics);
	}
	
	public JcClasse extends_(Class<?> classe, JcTipo... generics) {
		return extends_(new JcTipo(classe), generics);
	}

	public JcClasse extends_(Class<?> classe, JcClasse... generics) {
		
		Lst<JcTipo> tipos = new Lst<>();
		
		for (JcClasse o : generics) {
			tipos.add(o.getTipo());
		}
		
		return extends_(new JcTipo(classe), tipos);
	}
	
	public JcClasse extends_(Class<?> classe, Class<?>... generics) {
		return extends_(new JcTipo(classe), generics);
	}

	public JcClasse extends_(Class<?> classe) {
		return extends_(new JcTipo(classe), new ListString().toArray());
	}

	public JcClasse extends_(JcClasse classe, String... generics) {
		return extends_(classe.getTipo(), generics);
	}

	public JcClasse extends_(String classe) {
		return extends_(new JcTipo(classe), new ListString().toArray());
	}

	public JcClasse addImports(ListString tipos) {
		for (String s : tipos) {
			addImport(s);
		}
		return this;
	}

	public JcClasse addImports(JcTipos tipos) {
		tipos.forEach(this::addImport);
		return this;
	}

	public JcClasse addImport(ToJcTipo tipo) {
		return addImport(tipo.toJcTipo());
	}
	
	public JcClasse addImport(JcTipo tipo) {

		if (tipo.isPrimitivo()) {
			return this;
		}

		imports.add(tipo);
		JcTipos tipos = tipo.getTipos();
		for (JcTipo o : tipos) {
			if (canAddImport(o.getName())) {
				imports.add(o);
			}
		}
		return this;
	}

	public JcClasse addImport(JcClasse classe) {
		return addImport(classe.getTipo());
	}

	private boolean canAddImport(String s) {
		if (s.startsWith("java.lang.") || s.startsWith("*.")) {
			return false;
		}
		if (StringContains.is(s, "<")) {
			s = StringBeforeFirst.get(s, "<");
		}
		if (s.startsWith("$GENERICS$.")) {
			return false;
		}
		return true;
	}

	public JcClasse addImport(String s) {
		if (canAddImport(s)) {
			if (StringContains.is(s, "<")) {
				s = StringBeforeFirst.get(s, "<");
			}
			if (!s.startsWith("$GENERICS$.")) {
				imports.add(s);
			}
		}
		return this;
	}

	public JcClasse addImport(Class<?> tipo) {
		return this.addImport(tipo.getName());
	}

	public JcClasse addAtributo(JcAtributo atributo) {
		atributos.add(atributo);
		return this;
	}

	public JcClasse addMetodo(JcMetodo metodo) {
		metodos.add(metodo);
		metodo.setJc(this);
		return this;
	}

	public String getFileName() {
		String p = path.endsWith("/") ? path : path + "/";
		return p + getPkg().replace(".", "/") + "/" + getSimpleName() + ".java";
	}

	public boolean exists() {
		return UFile.exists(getFileName());
	}

	public boolean hasAnnotation(Class<?> tipo) {
		return anotations.has(tipo);
	}

	public JcClasse addAnnotation(Class<? extends Annotation> tipo) {
		newAnnotation(tipo);
		return this;
	}
	
	public JcAnotacao newAnnotation(Class<? extends Annotation> tipo) {
		return anotations.add(tipo);
	}

	public JcClasse addAnnotation(Class<?> tipo, String value) {
		return this.addAnnotation(tipo.getName(), value);
	}

	public JcClasse addAnnotation(String tipo) {
		anotations.add(tipo);
		return this;
	}

	public JcClasse addAnnotation(String tipo, Object value) {
		JcAnotacao jcAnotacao = new JcAnotacao(tipo, value);
		return this.addAnnotation(jcAnotacao);
	}

	public JcClasse addAnnotation(JcAnotacao anotacao) {
		anotations.add(anotacao);
		return this;
	}

	public JcClasse addAnnotations(JcAnotacoes anotacoes) {
		anotations.add(anotacoes);
		return this;
	}

	public JcClasse addImplements(JcClasse classe) {
		return addImplements(new JcTipo(classe));
	}

	public JcClasse addImplements(JcTipo tipo) {
		this.addImport(tipo);
		interfaces.add(tipo);
		return this;
	}

	public JcClasse addImplements(Class<?> tipo) {
		return this.addImplements(new JcTipo(tipo));
	}

	public JcClasse addImplements(String tipo) {
		return this.addImplements(new JcTipo(tipo));
	}

	public boolean save() {
		if (get().save(getFileName())) {
			deleteCache();
			return true;
		} else {
			return false;
		}
	}

	public void delete() {
		if (UFile.delete(getFileName())) {
			deleteCache();
		}
	}

	private void deleteCache() {
		ClassBox.getCacheImportsFile(getName()).delete();
	}

	public void save(P1<ListString> tratar) {
		ListString list = get();
		tratar.call(list);
		list.save(getFileName());
	}

	enum TipoComponent {
		stateless, api
	}

	private TipoComponent tipoComponent;

	private boolean anotarStateless() {

		if (abstract_) {
			return false;
		}

		if (tipoComponent == null) {
			return !cdis.isEmpty();
		}

		return tipoComponent == TipoComponent.stateless;

	}

	private boolean anotarApi() {
		if (abstract_) {
			return false;
		}
		return tipoComponent == TipoComponent.api;
	}

	public ListString get() {

		if (anotarStateless()) {
			addAnnotation(JcTipoProjeto.selected.getStateless());
		} else if (anotarApi()) {
			addAnnotation(JcTipoProjeto.selected.getApi());
		}

		for (JcAtributo o : atributos) {
			if (o.isGetter()) {

				JcMetodo m = metodo("get" + StringPrimeiraMaiuscula.exec(o.getNome()))
				.type(o.getTipo())
				.public_();

				if (o.isStatico()) {
					m.static_().return_(getSimpleName() +"."+o.getNome());
				} else {
					m.return_("this."+o.getNome());
				}

			}
			if (o.isSetter() || o.isSetterThis()) {
				JcMetodo m = metodo("set" + StringPrimeiraMaiuscula.exec(o.getNome())).paramAndSet(o.getNome(), o.getTipo()).public_();
				if (o.isSetterThis()) {
					m.returnThis();
				}
			}
			if (o.isGetterLombok()) {
				this.addImport(Getter.class);
			}
			if (o.isSetterLombok()) {
				this.addImport(Setter.class);
			}
			if (o.isBuilder()) {
				metodo(o.getNome()).public_().paramAndSet(o.getNome(), o.getTipo()).returnThis();
				if (o.getBuilderNome() != null) {
					metodo(o.getBuilderNome()).type(this).public_().param(o.getNome(), o.getTipo()).return_("this."+o.getNome()+"("+o.getNome()+");");
				}
			}
		}

		ListString list = new ListString();

		if (!comentarios.isEmpty()) {
			list.add("/*");
			list.margemInc();
			list.add(comentarios);
			list.margemDec();
			list.add("*/");
		}

		list.add("package "+getPkg()+";");
		list.add();

		anotations.getList().forEach(o -> addImports(o.getTipos()));
		metodos.forEach(o -> addImports(o.getTipos()));

		metodos.forEach(o -> {
			if (o.isHasThrow()) {
				addImport(exceptionClass);
				anyThrow = true;
			}
		});

		atributos.forEach(o -> {
			addImports(o.getTipos());
			o.getAnotacoes().getList().forEach(i -> {
				addImports(i.getTipos());
			});
		});

		if (singleton) {
			this.addImport(F0.class);
			this.addImport(Null.class);
		}

		ListString innerStrings = new ListString();

		for (JcClasse inner : inners) {

			ListString lst = inner.get();
			lst.remove(0);

			while (lst.contains(i -> i.startsWith("import "))) {
				lst.remove(0);
			}

			lst.replaceTexto("public class", "public static class");
			lst.replaceTexto("public interface", "private interface");
			lst.removeFisrtEmptys();
			innerStrings.add();
			innerStrings.add(lst);

			for (JcTipo tipo : inner.imports.getList()) {
				if (inner.getTipo().eq(tipo)) {
					continue;
				}
				addImport(tipo);
			}

		}

		addImports(getTipo().getTipos());

		if (extends_ != null) {
			addImport(extends_);
			addImports(extends_.getTipos());
		}

		imports.sort();

		if (getPkg() == null) {
			throw new RuntimeException();
		}

		for (JcTipo tipo : imports.getList()) {

			if (tipo.isGenerics() || tipo.isFullyQualifiedName()) {
				continue;
			}

			String s = tipo.getName();

			String pacote = StringBeforeLast.get(s, ".");

			if (pacote == null) {
				throw new RuntimeException();
			}

			if (!pacote.equals(getPkg()) && !s.startsWith(".")) {

				if (s.startsWith("java.lang.") && (ListString.byDelimiter(s, ".").size() == 3)) {
					//as classe s do pacote java.lang nao precisam ser importadas
					continue;
				}

				list.add("import "+s+";");
			}
		}

		list.add();

		if (anotations.has()) {
			list.add(anotations.toString());
		}

		boolean abstrato = abstract_ || UList.exists(metodos, JcMetodo::isAbstrato);

		String s;
		if (abstrato) {
			s = "public abstract class ";
		} else if (isInterface) {
			s = "public interface ";
		} else if (isEnum) {
			s = "public enum ";
		} else if (final_) {
			s = "public final class ";
		} else {
			s = "public class ";
		}

		s += tipo;
		if (extends_ != null) {
			extends_.setExtendsLigado(false);
			s += " extends " + extends_;
			extends_.setExtendsLigado(true);
		}

		if (!interfaces.isEmpty()) {
			StringBuilder x = new StringBuilder();
			for (JcTipo interf : interfaces) {
				x.append(", ").append(StringAfterLast.get("." + interf, "."));
			}
			s += " implements" + x.toString().substring(1);
		}

		list.add(s + " {");

		if (!innerStrings.isEmpty()) {
			list.margemInc();
			list.add();
			list.add(innerStrings);
			list.margemDec();
		}

		list.getMargem().inc();
		
		if (!primeirasLinhasDaClasse.isEmpty()) {
			list.add();
			for (String string : primeirasLinhasDaClasse) {
				list.add(string);
			}
		}
		
		if (singleton) {
			list.add();
			list.add("private static "+getSimpleName()+" instance;");
			if (abstrato) {
				list.add("public static F0<"+getSimpleName()+"> newInstance;");
			} else {
				list.add("public static F0<"+getSimpleName()+"> newInstance = () -> new "+getSimpleName()+"();");
			}
			list.add("public static "+getSimpleName()+" getInstance() {");
			list.add("	if (Null.is(instance)) {");
			list.add("		instance = newInstance.call();");
			list.add("	}");
			list.add("	return instance;");
			list.add("}");
		}
		
		if (!atributos.isEmpty()) {

			list.add();
//			nao deve ser ordenado
//			atributos.sort((a,b) -> a.toString().compareTo(b.toString()));

			if (formal) {

				atributos.filter(JcAtributo::isStatico).forEach(o -> {
					list.add(o.toListString());
					list.add();
				});

				JcAnotacao cdi = JcTipoProjeto.selected.getCdi();

				ListString lst = new ListString();

				String prefixo = "@"+cdi.getSimpleName()+" " + (abstract_ ? "protected " : "");

				atributos.filter(o -> o.hasAnnotation(cdi)).forEach(o -> {
					lst.add(prefixo + o.getTipo() + " " + o.getNome() + ";");
				});

				lst.sort();
				lst.sort((a,b) -> IntegerCompare.compare(a.length(), b.length()));

				list.addAll(lst);

				atributos.filter(o -> !o.isStatico() && !o.hasAnnotation(cdi)).forEach(o -> {
					list.add(o.toListString());
					list.add();
				});

			} else {
				atributos.filter(JcAtributo::isStatico).forEach(o -> list.add(o.toString()));
				atributos.filter(o -> !o.isStatico()).forEach(o -> list.add(o.toString()));
			}

		}

		if (!pularLinhaAntesDeCadaMetodo) {
			metodos.forEach(o -> o.setPularLinhaAntes(false));
		}

		if (sortMetodos) {
			metodos.sort((a, b) -> {
				int x = IntegerCompare.compare(a.getOrdem(), b.getOrdem());
				if (x == 0) {
					x = a.getAssinatura().compareTo(b.getAssinatura());
				}
				return x;
			});
		} else {
			metodos.sort((a, b) -> IntegerCompare.compare(a.getOrdem(), b.getOrdem()));
		}

		metodos.forEach(o -> list.add(o.get()));

		if (!statesSets.isEmpty()) {
			list.add();
			list.add(statesSets);
		}

		if (!adds.isEmpty()) {
			list.add();
			list.add(adds);
		}

		list.getMargem().dec();
		list.add();

		list.add("}");

		list.removeDoubleWhites();
		list.getMargem().set(0);
		list.rtrim();
		list.replace(ListString.array("\n", "}"), "}");
		list.juntarFimComComecos("{", "}", "");
		list.juntarFimComComecos("}", "else", " ");
		list.juntarFimComComecos("}", "catch", " ");
		list.rtrim();
		if (anyThrow) {
			list.replaceTexto("[exceptionClass]", exceptionClass.getSimpleName());
		}

		list.replaceTexto(" + \"\")", ")");
		list.replaceTexto("[TAB]", "\t");
		list.replaceTexto("<?, ?>", "<?,?>");

		replacesTexto.forEach((k,v) -> list.replaceTexto(k, v));

		if (list.get(-2).startsWith("\tprivate ")) {
			s = list.filter(i -> i.startsWith("public ")).getFirst();
			int index = list.indexOf(s) + 1;
			
			boolean temAlgumaLinhaEmBranco = false;
			
			for (int i = index+1; i < list.size()-2; i++) {
				if (StringEmpty.is(list.get(i))) {
					temAlgumaLinhaEmBranco = true;
					break;
				}
			}
			
			if (temAlgumaLinhaEmBranco) {
				list.add(-1, "");
			} else if (list.get(index).trim().isEmpty()) {
				list.remove(index);
			}
		}

		return list;

	}

	public JcMetodo construtor() {
		JcMetodo o = metodo(getSimpleName());
		o.setConstrutor(true);
		return o;
	}
	public JcMetodo staticPart() {
		JcMetodo o = new JcStaticPart();
		addMetodo(o);
		return o;
	}
	public JcMetodo metodo(String nome) {
		JcMetodo o = new JcMetodo(nome);
		addMetodo(o);
		return o;
	}
	public JcAtributo constant(String nome, Class<?> tipo, Class<?> generic, String inicializacao) {
		return this.constant(nome, tipo, inicializacao).generics(generic);
	}
	public JcAtributo constantString(String nome, String value) {
		return this.constant(nome, String.class, "\""+value+"\"");
	}
	public static String toInicializacao(ListString list) {
		list.add(0, "");
		list.addLeft("\t\t");
		list.add("\t");
		return list.toString("\n");
	}
	public JcAtributo constant(String nome, Class<?> tipo, ListString inicializacao) {
		return this.constant(nome, tipo, JcClasse.toInicializacao(inicializacao));
	}
	public JcAtributo constant(String nome, Class<?> tipo, String inicializacao) {
		return this.constant(nome, new JcTipo(tipo), inicializacao);
	}
	public JcAtributo constant(String nome, Class<?> tipo, Class<?> generics, ListString inicializacao) {
		return this.constant(nome, new JcTipo(tipo, generics), JcClasse.toInicializacao(inicializacao));
	}
	public JcAtributo constant(String nome, JcTipo tipo, ListString inicializacao) {
		return this.constant(nome, tipo, JcClasse.toInicializacao(inicializacao));
	}
	public JcAtributo constant(String nome, JcTipo tipo, String inicializacao) {
		return atributo(nome, tipo).constant(inicializacao);
	}
	public JcAtributo atributo(Atributo a) {
		return atributo(a.nome(), a.getTypeJc());

//		if (a.isList()) {
//			return atributo(a.nome(), a.getType(), a.getTypeOfList());
//		}
//		if (a.isMap() && a.getType() != MapSO.class) {
//			return atributo(a.nome(), Map.class, a.getTypesOfMap().get(0), a.getTypesOfMap().get(1));
//		}
//		return atributo(a.nome(), a.getType());

	}
	public JcAtributo atributo(String nome, String tipo) {
		JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcAtributo atributo(String nome, JcClasse tipo) {
		JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcAtributo atributo(String nome, JcTipo tipo) {
		JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcAtributo atributo(String nome, ToJcTipo tipo) {
		return atributo(nome, tipo.toJcTipo());
	}
	public JcAtributo atributo(String nome, Class<?> tipo, JcClasse... generics) {
		return atributo(nome, new JcTipo(tipo, generics));
	}
	public JcAtributo atributo(String nome, Class<?> tipo, JcTipo... generics) {
		return atributo(nome, new JcTipo(tipo, generics));
	}
	public JcAtributo atributo(String nome, Class<?> tipo, Class<?>... generics) {
		return atributo(nome, new JcTipo(tipo, generics));
	}
	public JcAtributo atributo(String nome, Classe tipo, Class<?>... generics) {
		return atributo(nome, new JcTipo(tipo, generics));
	}
	public JcAtributo aString(String nome) {
		return atributo(nome, String.class);
	}
	public JcAtributo aInteger(String nome) {
		return atributo(nome, Integer.class);
	}
	public JcAtributo atributo(String nome, Class<?> tipo) {
		JcAtributo o = new JcAtributo(nome, tipo);
		addAtributo(o);
		return o;
	}
	public JcClasse add(String s) {
		adds.add(s);
		return this;
	}
	public JcClasse singleton() {
		singleton = true;
		return this;
	}
	public JcMetodo main() {
		return metodo("main").public_().static_().paramArray("args", String.class);
	}
	public JcClasse sortMetodos() {
		sortMetodos = true;
		return this;
	}
	public JcClasse lombokGetter() {
		return this.addAnnotation(Getter.class);
	}
	public JcClasse lombokSetter() {
		return this.addAnnotation(Setter.class);
	}
	public JcClasse lombokAllArgsConstructor() {
		return this.addAnnotation(AllArgsConstructor.class);
	}
	public JcClasse lombokNoArgsConstructor() {
		return this.addAnnotation(NoArgsConstructor.class);
	}
	public JcClasse lombokRequiredArgsConstructor() {
		return this.addAnnotation(RequiredArgsConstructor.class);
	}
	public JcClasse lombokBuilder() {
		return this.addAnnotation(lombok.Builder.class);
	}
	public JcClasse lombokToString() {
		return this.addAnnotation(lombok.ToString.class);
	}
	public JcClasse lombokEqualsHash(boolean callSuper) {
		JcAnotacao a = new JcAnotacao(lombok.EqualsAndHashCode.class);
		a.addParametro("callSuper", callSuper);
		addAnnotation(a);
		return this;
	}
	public JcClasse setAbstract() {
		abstract_ = true;
		return this;
	}
	public void setExceptionClass(Class<?> classe) {
//		addImport(classe);
		exceptionClass = classe;
	}
	public JcAtributo getAtributo(String s) {
		return UList.filterUnique(atributos, o -> StringCompare.eq(o.getNome(), s));
	}
	public String getName() {
		return getPkg() + "." + getSimpleName();
	}
	public void addInner(JcClasse inner) {
		inners.add(inner);
	}
	public void remove(JcMetodo o) {
		metodos.remove(o);
	}
	@Deprecated
	public String getNome() {
		return getSimpleName();
	}

	public String getPkg() {
		return tipo.getPkg();
	}

	@Deprecated
	public String getPacote() {
		return getPkg();
	}

	public JcClasse addGenerics(JcTipo o) {
		tipo.addGenerics(o);
		return this;
	}
	
	public JcClasse addGenerics(Class<?> classe) {
		return addGenerics(JcTipo.build(classe));
	}

	@Override
	public String toString() {
		return getName() + " / " + getPath();
	}

	public JcClasse js() {
		js = true;
		return this;
	}

	public JcAtributo mapSS(String nome) {
		return map(nome, String.class, String.class);
	}

	public JcAtributo mapSO(String nome) {
		return map(nome, String.class, Object.class);
	}

	public JcAtributo map(String nome, Class<?> a, Class<?> b) {
		if (js) {
			return atributo(nome, js.map.Map.class).generics(a).generics(b);
		}
		return atributo(nome, Map.class).generics(a).generics(b);
	}

	public JcAtributo array(String nome, Class<?> generics) {
		return atributo(nome, Itens.class).generics(generics);
	}
	public JcAtributo constantArrayString(String nome, ListString itens) {
		String s = "ArrayLst.build(\"" + itens.toString("\", \"") + "\")";
		return constant(nome, Itens.class, s).generics(String.class);
	}
	public JcAtributo constantTextBlock(String nome, ListString itens) {

		String s;

		if (java > 14) {
			s = "\n\"\"\"\n" + itens.toString("\n\t") + "\n\"\"\"";
		} else {
			s = "\"\"\n" + itens.mapString(i -> "+ \" " + i + "\"").toString("\n");
		}

		return constant(nome, String.class, s);
	}
	public JcAtributo constantArray(Class<?> classe, String nome, ListString itens) {
		return constantArray(new JcTipo(classe), nome, itens);
	}
	public JcAtributo constantArray(JcTipo tipo, String nome, ListString itens) {
		String s = "ArrayLst.build(\n\t" + itens.toString(",\n\t") + "\n)";
		JcAtributo o = constant(nome, Itens.class, s);
		o.setGenerics(tipo);
		return o;
	}

	public JcClasse setFormal(boolean formal) {
		this.formal = formal;
		return this;
	}

	public void setIsInterface(boolean value) {
		isInterface = value;
	}

	public void setIsEnum(boolean value) {
		isEnum = value;
	}

	private ListString cdis = new ListString();

	public String cdi(String nomeClasse) {
		return cdi(new JcTipo(nomeClasse));
	}

	public String cdi(JcTipo classe) {
		return cdi(classe, false);
	}

	public String cdi(JcTipo classe, boolean protected_) {

		String s = StringPrimeiraMinuscula.exec(classe.getSimpleName());

		if (cdis.contains(s)) {
			return s;
		}

		cdis.add(s);

		JcAtributo a = atributo(s, classe);

		a.addAnotacao(JcTipoProjeto.selected.getCdi());

		if (protected_) {
			a.protected_();
		}

		return s;
	}

	public String cdi(Class<?> classe) {
		return cdi(new JcTipo(classe));
	}

	private void setTipoComponent(TipoComponent value) {

		if (tipoComponent == null) {
			tipoComponent = value;
		} else if (tipoComponent != value) {
			throw new RuntimeException("this.tipoComponent != null");
		}

	}

	public JcClasse stateless() {
		setTipoComponent(TipoComponent.stateless);
		return this;
	}

	public JcClasse api() {
		setTipoComponent(TipoComponent.api);
		return this;
	}

	@Deprecated
	public JcClasse componentSpring() {
		return stateless();
	}

	@Deprecated
	public JcClasse componentReact() {
		return stateless();
	}

	@Deprecated
	public JcClasse restController() {
		return api();
	}

	private String requestMapping;

	public JcClasse requestMapping(String s) {

		if (requestMapping != null) {

			if (requestMapping.contentEquals(s)) {
				return this;
			}

			throw new RuntimeException("A classe ja foi anotada com a seguinte requestMapping: " + requestMapping);

		}

		requestMapping = s;

		if (!s.startsWith("/")) {
			s = "/" + s;
		}

		s = "\"" + s + "\"";

		addAnnotation("org.springframework.web.bind.annotation.RequestMapping", s);

		return restController();

	}

	@Deprecated
	public JcMetodo initSpring() {
		return postConstruct();
	}

	public JcMetodo postConstruct() {
		stateless();
		return metodo("postConstruct").postConstruct();
	}

	public void addComentario(String s) {
		comentarios.add(s);
	}

	public void serializable() {
		addImplements(Serializable.class);
	}

	public void addSerialVersionId() {
		constant("serialVersionUID", long.class, "1L");
	}

	public void addReplaceTexto(String a, String b) {
		replacesTexto.put(a, b);
	}

	public void print() {
		get().print();
	}

	public void constantInstance() {
		atributo("instance", this).inicializacaoNew().static_().public_().final_();
	}

	public String inject(Class<?> classe) {
		return inject(new JcTipo(classe));
	}
	
	public String inject(JcTipo tipo) {
		String name = StringPrimeiraMinuscula.exec(tipo.getSimpleName());
		if (!atributos.anyMatch(i -> i.getNome().contentEquals(name))) {
			atributo(name, tipo).protected_().addAnotacao(new JcTipo("jakarta.inject.Inject"));
		}
		return name;
	}

	public String inject(String name) {
		return inject(new JcTipo(name));
	}

	public JcAtributo constantItensLst() {
		return atributo("ITENS", Lst.class, this).static_().final_().inicializacao("new Lst<>()");
	}

}
