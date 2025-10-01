package gm.utils.classes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import gm.utils.comum.SystemPrint;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.Construtor;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.string.Java;
import gm.utils.string.Java2;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEscopo;

public class ClassBox {

	@Getter
	private Class<?> classe;
	private Java java;
	private Java2 java2;
	private Metodos metodos;
	private Map<String, Class<?>> parametrosConstrutor;

	@Setter
	private ListClass imports;

	private List<ImportStaticJava> importsStatic;

	@Getter
	private String name;

	private ClassBox(Class<?> classe) {
		this.classe = classe;
		name = classe.getSimpleName();
	}

	public Map<String, Class<?>> getParametrosConstrutor() {
		if (parametrosConstrutor == null) {
			parametrosConstrutor = new HashMap<>();
			Constructor<?>[] constructors = classe.getConstructors();
			if (constructors.length > 1) {
				throw UException.runtime(name + " - constructors.length > 1");
			}

			Class<?>[] parameterTypes = constructors[0].getParameterTypes();
			if (parameterTypes.length == 0) {
				return parametrosConstrutor;
			}

			ListString list = getJava().getList();
			String s = list.trimPlus().toString(" ").replace(" (", "(");
			s = StringAfterFirst.get(s, " " + name + "(");
			s = StringBeforeFirst.get(s, ")");
			list = ListString.byDelimiter(s, ",").trimPlus();

			for (Class<?> type : parameterTypes) {
				s = list.remove(0);
				String nomeTipo = StringBeforeFirst.get(s, " ");
				if (nomeTipo.endsWith("...")) {
					nomeTipo = StringBeforeLast.get(nomeTipo, "...") + "[]";
				}
				if (nomeTipo.contains(".")) {
					nomeTipo = StringAfterLast.get(nomeTipo, ".");
				}
				if (!nomeTipo.equals(type.getSimpleName())) {
					for (int x = 0; x < parameterTypes.length; x++) {
//						SystemPrint.ln(parameterTypes[x].getSimpleName());
					}
					list.print();
					throw UException.runtime("Algo deu errado!");
				}
				s = StringAfterFirst.get(s, " ");
				parametrosConstrutor.put(s, type);
			}
		}

		return parametrosConstrutor;

	}

	public Java getJava() {
		if (java == null) {
			java = new Java(getFile().toString());
		}
		return java;
	}
	public Java2 getJava2() {
		if (java2 == null) {
			java2 = new Java2(getFile().toString());
		}
		return java2;
	}

	private static Map<Class<?>, ClassBox> map = new HashMap<>();

	public static ClassBox get(Class<?> classe) {
		classe = UClass.getClass(classe);
		ClassBox o = map.get(classe);
		if (o == null) {
			o = new ClassBox(classe);
			map.put(classe, o);
		}
		return o;
	}

//	public String getFileName() {
//		return getFile().toString();
//	}
	
	private GFile file;
	
	public GFile getFile() {
		if (file == null) {
			file = UClass.getJavaFile(classe);
		}
		return file;
	}
	
	public Atributos getAtributos() {return getAtributos(false);}
	public Atributos getAtributos(Predicate<Atributo> predicate) {return getAtributos(predicate, false);}
	public Atributos getAtributos(boolean withId) {
		return AtributosBuild.get(classe, withId);
	}
	public Atributos getAtributos(Predicate<Atributo> predicate, boolean withId) {
		return getAtributos(withId).filter(predicate);
	}
	public Metodos getMetodos(Predicate<Metodo> predicate) {
		return getMetodos().filter(predicate);
	}
	public Metodos getMetodos() {
		if (metodos == null) {
			metodos = ListMetodos.get(classe);
		}
		return metodos.clone();
	}

	public List<ImportStaticJava> getImportsStatic() {

		if (importsStatic != null) {
			return importsStatic;
		}

		importsStatic = new ArrayList<>();

		ListString javaList = getJava().getList();

		ListString list = javaList.filter(s -> s.startsWith("import static"));
		for (String s : list) {
			s = StringAfterFirst.get(s, "import static ");
			s = StringBeforeLast.get(s, ";");
			ImportStaticJava o = new ImportStaticJava();
			o.setNome(StringAfterLast.get(s, "."));
			s = StringBeforeLast.get(s, ".");
			o.setClasse(UClass.getClassObrig(s));
			importsStatic.add(o);
		}

		return importsStatic;

	}

	public ListClass getImports() {
		return getImports(false);
	}

	public static GFile getCacheImportsFile(String classFullName) {
		return GFile.get("/opt/desen/gm/cs2019/gm-utils/outros/cache-imports/" + classFullName);
	}

	public ListClass getImports(boolean safe) {

		if (imports == null) {

			imports = new ListClass();

			GFile cache = getCacheImportsFile(classe.getName());

			boolean src = classe.getPackage().getName().contentEquals("src");

			if (!src && cache.exists() && cache.lastModified() > getFile().lastModified()) {
				try {
					imports.load(cache);
					imports.sort();
					return imports.copy();
				} catch (Exception e) {
					SystemPrint.err(cache);
				}
			}

			ListString javaList = getJava().getList();

			ListString list = javaList.filter(s -> s.startsWith("import ") && !s.startsWith("import static "));
			list.setBloqueada(true);
			for (String x : list) {
				String s = StringAfterFirst.get(x, " ");
				s = StringBeforeLast.get(s, ";");

				if (s == null) {
					throw new NullPointerException("s == null: " + x);
				}

				if (safe) {
					Class<?> o = UClass.getClass(s);
					if (o != null) {
						imports.add(o);
					}
				} else {
					imports.add(UClass.getClassObrig(s));
				}
			}

			ListClass classes = UClass.classesDaMesmaPackage(getClasse());
			classes.remove(getClasse());

			if (!classes.isEmpty()) {
				String s = javaList.trimPlus().toString(" ");
				s = s.replace("(", "( ");
				s = s.replace(" (", "(");
				ListString palavras = ListString.separaPalavras(s);
				for (Class<?> c : classes) {
					if (palavras.contains(c.getSimpleName())) {
						imports.add(c);
					}
				}
			}

			imports.sort();

			if (!src) {
				cache.delete();
				imports.save(cache);
			}

		}

		return imports.copy();
	}

	private List<Construtor> construtores;

	public List<Construtor> getConstrutores() {
		if (construtores == null) {
			construtores = new ArrayList<>();
			Constructor<?>[] constructors = classe.getConstructors();
			for (Constructor<?> constructor : constructors) {
				construtores.add(new Construtor(constructor));
			}
		}
		return construtores;
	}

	public boolean instanceOf(Class<?> classe) {
		return UClass.a_herda_b(getClasse(), classe);
	}

	public boolean isAbstract() {
		return UClass.isAbstract(classe);
	}

	public boolean hasAnnotation(Class<? extends Annotation> annotationClass) {
		return classe.isAnnotationPresent(annotationClass);
	}

	private ListString genericNames;

	public ListString getGenericNames() {

		if (genericNames == null) {

			genericNames = new ListString();

			if (classe.isEnum()) {
				return genericNames;
			}

			String s = getJava().getList().toString(" ");

			if (classe.isInterface()) {
				s = StringAfterFirst.get(s, " interface " + getName());
			} else {
				s = StringAfterFirst.get(s, " class " + getName());
			}

			s = StringBeforeFirst.get(s, "{");

			s = s.replace("<>", "");
			if (StringContains.is(s, "<")) {
				s = StringEscopo.exec(s, "<", ">");
				while (StringContains.is(s, "<")) {
					String old = s;
					s = s.replace(StringEscopo.exec(s, "<", ">"), "");
					s = s.replace("<>", "");
					if (old.equals(s)) {
						throw UException.runtime("loop infinito");
					}
				}
				ListString list = ListString.byDelimiter(s, ",").trimPlus();
				for (String string : list) {
					if (string.contains(" ")) {
						string = StringBeforeFirst.get(string, " ");
					}
					genericNames.add(string);
				}
			}

		}

		return genericNames;
	}

	private String tripa;

	public String getTripa() {
		if (tripa == null) {
			tripa = getJava().getList().trimPlus().toString(" ");
		}
		return tripa;
	}

}
