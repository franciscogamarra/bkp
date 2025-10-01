package gm.utils.classes;

import java.io.File;
import java.lang.reflect.Constructor;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.collect.ImmutableSet;
import com.google.common.reflect.ClassPath.ClassInfo;

import gm.utils.anotacoes.Titulo;
import gm.utils.comum.Lst;
import gm.utils.comum.UAssert;
import gm.utils.comum.UConstantes;
import gm.utils.comum.UList;
import gm.utils.comum.ULog;
import gm.utils.comum.UType;
import gm.utils.config.UConfig;
import gm.utils.date.Data;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.files.UFile;
import gm.utils.map.MapSO;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import gm.utils.string.StringToCamelCaseSepareClass;
import js.support.console;
import src.commom.utils.object.Obrig;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringTrim;

public class UClass {

	private static Map<Class<?>, Class<?>> classesJaEncontradas = new HashMap<>();
	private static Map<String, Class<?>> nomesJaEncontrados = new HashMap<>();
	
	private static final String SS = "$$";

	public static void addClassReplace(Class<?> de, Class<?> para) {
		classesJaEncontradas.put(de, para);
	}

	public static <T> Class<T> getClass(GFile file) {
		
		if (!"java".contentEquals(file.getExtensao())) {
			throw new RuntimeException();
		}
		
		Lst<String> list = file.getList();
		
		while (true) {
			
			if (!list.get(0).contentEquals("src")) {
				list.remove(0);
			}

			list.remove(0);
			
			if (!list.get(0).contentEquals("main")) {
				continue;
			}
			
			list.remove(0);

			if (!list.get(0).contentEquals("java")) {
				continue;
			}
			
			list.remove(0);
			
			break;
			
		}
		
		list.removeLast();
		
		String s = list.joinString(".") + "." + file.getSimpleNameWithoutExtension();
		
		return getClass(s);
		
	}
	
	public static <T> Class<T> getClass(File file) {
		return getClass(GFile.get(file));
	}

	public static <T> Class<T> getClass(Object o) {
		return getClass(o.getClass());
	}

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(Class<?> classe) {
		Class<?> classe2 = classesJaEncontradas.get(classe);
		if (classe2 != null) {
			if (classe2 == classe) {
				return (Class<T>) classe;
			}
			return getClass(classe2);
		}
		String s = classe.getName();
		classe2 = getClass(s);
		if ((classe2 == null) || (classe != classe2 && s.contentEquals(classe2.getName()))) {
			classe2 = classe;
		}
		classesJaEncontradas.put(classe, classe2);
		return (Class<T>) classe2;
	}

	public static <T> Class<T> getClassObrig(String s) {
		Class<T> classe = getClass(s);
		if (classe == null) {
			throw UException.runtime("Classe não encontrada: " + s);
		}
		return classe;
	}

	public static ListClass tiposJava = new ListClass(String.class, Object.class, Integer.class, int.class,
			Double.class, double.class, Data.class, Calendar.class, Date.class, Float.class, float.class, Long.class,
			long.class, Boolean.class, boolean.class, BigDecimal.class);

	@SuppressWarnings("unchecked")
	public static <T> Class<T> getClass(String s) {

		s = StringTrim.plusNull(s);
		
		if (StringEmpty.is(s)) {
			throw new NullPointerException("s == null");
		}

		if (s.contentEquals("T")) {
			return (Class<T>) Object.class;
		}
		
		if (s.startsWith("import ")) {
			s = StringAfterFirst.get(s, " ");
		}

		if (s.endsWith(";")) {
			s = StringRight.ignore1(s).trim();
		}
		
		Class<?> classe = nomesJaEncontrados.get(s);

		if (classe != null) {
			Class<?> classe2 = classesJaEncontradas.get(classe);
			if (classe2 != null && classe2 != classe) {
				return getClass(classe2);
			}
			return (Class<T>) classe;
		}

		// verificar se eh um proxy
		if (StringContains.is(s, "_" + SS + "_")) {
			s = StringBeforeFirst.get(s, "_" + SS + "_");
		}
		if (StringContains.is(s, SS)) {
			s = StringBeforeFirst.get(s, SS);
		}
		if (StringContains.is(s, UConstantes.cifrao + "HibernateProxy" + UConstantes.cifrao)) {
			s = StringBeforeFirst.get(s, UConstantes.cifrao + "HibernateProxy" + UConstantes.cifrao);
		}
		if (s.startsWith("class ")) {
			s = StringAfterFirst.get(s, " ");
		}

		for (Class<?> tipo : tiposJava) {
			if (s.equalsIgnoreCase(tipo.getSimpleName())) {
				nomesJaEncontrados.put(s, tipo);
				return (Class<T>) tipo;
			}
		}
		
		if (!s.contains(".")) {
			try {
				classe = Class.forName("java.lang." + s);
				nomesJaEncontrados.put(s, classe);
				return (Class<T>) classe;
			} catch (Exception e) {}
		}

		try {
			classe = Class.forName(s);
			nomesJaEncontrados.put(s, classe);
			return (Class<T>) classe;
		} catch (ExceptionInInitializerError e) {
			console.log(s);
			throw e;
		} catch (ClassNotFoundException e) {

			String before = StringBeforeLast.get(s, ".");
			String after = StringAfterLast.get(s, ".");
			String s2 = before + "$" + after;

			try {
				classe = Class.forName(s2);
				nomesJaEncontrados.put(s, classe);
				nomesJaEncontrados.put(s2, classe);
				return (Class<T>) classe;
			} catch (ClassNotFoundException e2) {
				return null;
			}

		}
	}

	public static <T> T newInstance(String nomeClasse) {
		return newInstance(getClassObrig(nomeClasse));
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> classe) {

		UAssert.notEmpty(classe, "classe == null");

		if (classe.equals(java.sql.Date.class)) {
			return (T) new java.sql.Date(0L);
		}

		//		if (classe.equals(java.util.List.class)) {
		//			return (T) new ArrayList<>();
		//		}

		Object[] parameters = null;
		return newInstance(classe, parameters);

	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstance(Class<T> classe, Object... parameters) {

		UAssert.notEmpty(classe, "classe == null");
		
		classe = getClass(classe);

		if (classe.equals(Integer.class)) {
			Integer i = 0;
			return (T) i;
		}
		if (classe.equals(String.class)) {
			String s = "";
			return (T) s;
		}
		if (classe.equals(Double.class)) {
			Double d = 0.0;
			return (T) d;
		}
		if (classe.equals(java.util.Date.class)) {
			java.util.Date date = Data.now().getDate();
			return (T) date;
		}
		if (classe.equals(BigDecimal.class)) {
			return (T) BigDecimal.ZERO;
		}
		if (classe.equals(Boolean.class)) {
			return (T) Boolean.FALSE;
		}
		
		if (classe.isInterface()) {
			throw new RuntimeException("Não se pode construir uma interface " + classe.getName());
		}

		if (isAbstract(classe)) {
			throw new RuntimeException("Não se pode construir uma classe abstrata " + classe.getName());
		}
		
		try {

			if (parameters == null || parameters.length == 0) {
				Constructor<T> d = classe.getDeclaredConstructor();
				d.setAccessible(true);
				return d.newInstance();
				//				return classe.newInstance();
			}

			Constructor<?>[] constructors = classe.getConstructors();
			List<Constructor<?>> list = new ArrayList<>();
			for (Constructor<?> constructor : constructors) {
				if (parameters.length == constructor.getParameterCount()) {
					list.add(constructor);
				}
			}

			List<Constructor<?>> list2 = new ArrayList<>();
			for (Constructor<?> constructor : list) {
				Class<?>[] types = constructor.getParameterTypes();
				int i = 0;
				for (Class<?> type : types) {
					Object p = parameters[i];
					if (p == null || isInstanceOf(p, type)) {
						list2.add(constructor);
					}
				}
			}

			if (list2.isEmpty()) {
				String s = classe.getSimpleName() + "(" + parameters + ")";
				throw UException.runtime("Não foi encontrado um construtor adequado: " + s);
			}
			if (list2.size() > 1) {
				String s = classe.getSimpleName() + "(" + parameters + ")";
				throw UException.runtime("Existem mais de um construtor: " + s);
			}
			Constructor<?> constructor = list2.remove(0);

			if (parameters.length > 6) {

				throw UException.runtime("Na epoca da implementacao deste metodo "
						+ "nao havia como passar um array como argumento "
						+ "para um newInstance de um contrutor. Desta forma "
						+ "era necessario fazer um if para cada quantidade "
						+ "de argumentos. So foi implementado ate 6 argumentos. "
						+ " É necessario implementar mais situacoes nesta classe: " + parameters.length);

			}

			Object p0 = parameters[0];
			if (parameters.length == 1) {
				return (T) constructor.newInstance(p0);
			}

			Object p1 = parameters[1];
			if (parameters.length == 2) {
				return (T) constructor.newInstance(p0, p1);
			}

			Object p2 = parameters[2];
			if (parameters.length == 3) {
				return (T) constructor.newInstance(p0, p1, p2);
			}

			Object p3 = parameters[3];
			if (parameters.length == 4) {
				return (T) constructor.newInstance(p0, p1, p2, p3);
			}

			Object p4 = parameters[4];
			if (parameters.length == 5) {
				return (T) constructor.newInstance(p0, p1, p2, p3, p4);
			}

			Object p5 = parameters[5];
			if (parameters.length == 6) {
				return (T) constructor.newInstance(p0, p1, p2, p3, p4, p5);
			}

			Object p6 = parameters[6];
			if (parameters.length == 7) {
				return (T) constructor.newInstance(p0, p1, p2, p3, p4, p5, p6);
			}

			throw new NaoImplementadoException();

		} catch (RuntimeException e) {
			throw e;
		} catch (Exception e) {
			Throwable cause = e.getCause();
			if (cause instanceof RuntimeException) {
				RuntimeException me = (RuntimeException) cause;
				throw me;
			}
			e.printStackTrace();
			throw UException.runtime("Erro ao tentar instanciar a classe: " + classe.getName());
		}
	}

	public static boolean isInstanceOf(Object a, Class<?> c) {
		Class<?> classe = a.getClass();
		return instanceOf(classe, c);
	}

	public static boolean instanceOf(Class<?> classe, Class<?>... list) {

		for (Class<?> c : list) {

			if (a_herda_b(classe, c)) {
				return true;
			}

			Class<?> x = classe;

			while (x != null) {
				if (c.equals(x)) {
					return true;
				}
				x = x.getSuperclass();
			}

		}

		// TODO preciso completar com interfaces
		return false;
	}

	private static boolean a_herda_b_private(Class<?> a, Class<?> b) {

		if (a == null) {
			return false;
		}

		if (a == b) {
			return true;
		}

		if ((a == Object.class) || UType.isPrimitiva(a)) {
			return false;
		}

		if (b.isAssignableFrom(a)) {
			return true;
		}

		return a_herda_b_private(a.getSuperclass(), b);

	}

	public static boolean a_herda_b(Class<?> a, Class<?> b) {
		return a_herda_b_private(Obrig.check(a), Obrig.check(b));
	}

	public static boolean isAbstract(Class<?> classe) {
		int modifiers = classe.getModifiers();
		return modifiers >= 1024;
	}

	private static ListString sources = ListString.array("main", "test");

	public static String getJavaFileName(Class<?> classe) {
		return getJavaFileName(classe, true);
	}
	
	public static GFile getJavaFile(Class<?> classe) {
		return GFile.get(getJavaFileName(classe));
	}

	private static Lst<GFile> pathRaizProjetos;

	private static Lst<GFile> getPathRaizProjetos() {
		
		if (pathRaizProjetos == null) {
			
			GFile s = GFile.get(System.getProperty("user.dir")).join("src").join("main").join("java");
			if (UConfig.get() == null) {
				pathRaizProjetos = new Lst<>();
				pathRaizProjetos.add(s);
			} else {
				pathRaizProjetos = UConfig.get().getPathRaizProjetos();
				pathRaizProjetos.setBloqueada(false);
				pathRaizProjetos.addIfNotContains(s);
			}
			
			GFile reacts = GFile.get("/opt/desen/gm/cs2019/reacts/");
			
			if (s.startsWith(reacts)) {
				pathRaizProjetos.addIfNotContains(reacts.join("react").join("src").join("main").join("java"));
			}
			
			pathRaizProjetos.addIfNotContains(GFile.get("/opt/desen/gm/cs2019/gm-utils/src/main/java/"));
			pathRaizProjetos.setBloqueada(true);
		}
		
		return pathRaizProjetos;
	}

	public static void addPaths(Lst<File> list) {
		for (File file : list) {
			addPath(file);
		}
	}

	public static void addPath(GFile file) {
		getPathRaizProjetos();
		pathRaizProjetos.setBloqueada(false);
		pathRaizProjetos.addIfNotContains(file);
		pathRaizProjetos.setBloqueada(true);
	}
	
	public static void addPath(File file) {
		addPath(GFile.get(file));
	}

	public static void addPath(String s) {
		addPath(GFile.get(s));
	}

	public static String getJavaFileName(Class<?> classe, boolean obrig) {
		return getJavaFileName(classe.getName(), obrig);
	}

	public static String getJavaFileName(String nomeCompleto) {
		return getJavaFileName(nomeCompleto, true);
	}

	public static String getJavaFileName(String nomeCompleto, boolean obrig) {
		GFile file = getJavaFile(nomeCompleto, obrig);
		return file == null ? null : file.toString();
	}
	
	public static GFile getJavaFile(String nomeCompleto, boolean obrig) {

		//		/opt/desen/gm/cs2019/reacts/recat-mobile-cooper/src/main/java/src/app/misc/services/Service.java
		//		/opt/desen/gm/cs2019/reacts/react-mobile-cooper
		//		console.log(System.getProperty("user.dir"));

		Lst<GFile> locaisProcurados = new Lst<>();

		String nm = nomeCompleto.replace(".", "/") + ".java";
		
//		String caminho = "/src/%s/java/" + nomeCompleto.replace(".", "/") + ".java";

		for (GFile pathRaiz : getPathRaizProjetos()) {
			if (pathRaiz.contains("src")) {
				pathRaiz = pathRaiz.before("src");
			}
			for (String source : sources) {
				GFile file = pathRaiz.join("src").join(source).join("java").join(nm);
				if (file.exists()) {
					return file;
				}
				locaisProcurados.add(file);
			}
		}

		if (obrig) {
			locaisProcurados.print();
			throw new RuntimeException("Nao encontrato: " + nomeCompleto);
			/*nao pode usar pois o trata dá StackOverflowError */
//			throw UException.runtime("Nao encontrato: " + nomeCompleto);
		}
		return null;

	}

	public static ListClass getClassesDaMesmaPath(Class<?>... classes) {
		ListClass list = new ListClass();
		for (Class<?> classe : classes) {
			list.addAll(getClassesDaMesmaPath(StringBeforeLast.get(getJavaFileName(classe), "/")));
		}
		return list;
	}

	public static ListClass getClassesDaMesmaPath(String path) {
		ListClass list = new ListClass();
		List<File> files = UFile.getFiles(path);
		for (File file : files) {
			String s = file.toString();
			if (s.endsWith(".java")) {
				Class<?> classe = getClass(file);
				if (classe != null) {
					list.add(classe);
				}
			}
		}
		return list;
	}

	public static ListClass classesDaMesmaPackage(Class<?> classe) {
		return getClassesDaPackage(classe.getPackage());
	}

	public static ListClass classesDaMesmaPackageESubPackages(Class<?> classe) {
		return getClassesDaPackage(classe.getPackage(), true);
	}

	public static ListClass classesDaMesmaPackage(Class<?> classe, String subpackage) {

		StringBuilder pack = new StringBuilder().append(classe.getPackage().getName());

		//		String pack = classe.getPackageName();

		if (!StringEmpty.is(subpackage)) {
			pack.append(".").append(subpackage);
		}

		ListClass list = getClassesDaPackage(pack.toString());
		list.sort();
		return list;

	}

	public static ListClass getClassesDaPackage(Package pack) {
		return getClassesDaPackage(pack.getName(), false);
	}

	public static ListClass getClassesDaPackage(Package pack, boolean subPackages) {
		return getClassesDaPackage(pack.getName(), subPackages);
	}

	public static ListClass getClassesDaPackage(String pack) {
		return getClassesDaPackage(pack, false);
	}

	public static ListClass getClassesDaPackage(String pack, boolean subPackages) {

		try {

			ListClass list = new ListClass();

			ClassLoader loader = Thread.currentThread().getContextClassLoader();

			ImmutableSet<ClassInfo> topLevelClasses = com.google.common.reflect.ClassPath.from(loader).getTopLevelClasses();

			pack += ".";

			for (ClassInfo info : topLevelClasses) {
				if (info.getName().startsWith(pack)) {
					Class<?> classe = info.load();
					if (!subPackages) {
						String s = classe.getPackage().getName() + ".";
						if (!s.contentEquals(pack)) {
							continue;
						}
					}
					list.add(classe);
				}
			}

			list.sort();

			return list;

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	private static String getPathProjeto(String projeto) {

		MapSO map = new MapSO();
		map.loadIfExists("/opt/desen/gm/cs2019/gm-utils/paths.txt");
		String path = map.getString(projeto);
		if (!StringEmpty.is(path) && UFile.exists(path)) {
			return path;
		}
		List<File> dirs = UFile.getAllDirectories("/opt/desen/gm/cs2019/");
		dirs = UList.filter(dirs, file -> file.toString().endsWith("/" + projeto));
		dirs = UList.filter(dirs, file -> !file.toString().contains("/cleanInstall/"));
		if (dirs.isEmpty()) {
			throw new RuntimeException("??? " + projeto);
		}

		dirs = UList.filter(dirs, file -> UFile.exists(file.toString() + "/pom.xml"));
		if (dirs.isEmpty() || (dirs.size() > 1)) {
			throw new RuntimeException("??? " + projeto);
		}
		path = StringBeforeLast.get(dirs.get(0).toString(), "/") + "/" + projeto;
		map.add(projeto, path);
		map.save("/opt/desen/gm/cs2019/gm-utils/paths.txt");
		return path;

	}
	
	public static ListClass fromPath(GFile path) {
		return fromPath(path.toString());
	}

	public static ListClass fromPath(String path) {

		if (path.contains(".jar!")) {
			//			/home/gamarra/.m2/repository/gm/fc-core-back/0/fc-core-back-0.jar!/br/auto/model/
			String s = StringBeforeFirst.get(path, ".jar!");
			//			/home/gamarra/.m2/repository/gm/fc-core-back/0/fc-core-back-0
			s = StringBeforeLast.get(s, "/");
			//			/home/gamarra/.m2/repository/gm/fc-core-back/0
			s = StringBeforeLast.get(s, "/");
			//			/home/gamarra/.m2/repository/gm/fc-core-back
			s = StringAfterLast.get(s, "/");
			//			fc-core-back
			path = getPathProjeto(s) + "/src/main/java" + StringAfterFirst.get(path, ".jar!");
		}

		while (path.contains("/opt/desen/java/m2/reacts/")) {
			String s = StringAfterFirst.get(path, "/opt/desen/java/m2/reacts/");
			String nome = StringBeforeFirst.get(s, "/");
			s = StringAfterFirst.get(s, "/");
			String versao = StringBeforeFirst.get(s, "/");
			s = "/opt/desen/java/m2/reacts/" + nome + "/" + versao + "/" + nome + "-" + versao + ".jar!/";
			String x = "/opt/desen/gm/cs2019/reacts/" + nome + "/src/main/java/";
			path = path.replace(s, x);
			//			path = path.replace("/opt/desen/java/m2/reacts/front-constructor/0/front-constructor-0.jar!/", "/opt/desen/gm/cs2019/reacts/front-constructor/src/main/java/");
		}

		if (path.contains("-0.jar!")) {

			String before = StringBeforeFirst.get(path, "-0.jar!");
			//			/opt/desen/java/m2/react/react/0/react

			String after = StringAfterFirst.get(path, "-0.jar!");
			//			/src/infra/consts/enums/

			String projeto = StringAfterLast.get(before, "/");
			//			react

			GFile caminho = UConfig.get().getPathRaizProjetos().unique(s -> s.endsWith("/" + projeto + "/src/main/"));
			//			opt/desen/gm/cs2019/react/src/main/

			if (caminho == null) {
				throw UException.runtime("Nao foi possivel resolver " + path);
			}
			path = caminho.join("java").join(after).toString();

		}

		if (!path.startsWith("/")) {
			path = "/" + path;
		}

		String pack;

		if (path.contains("/src/main/java/")) {
			pack = StringAfterFirst.get(path, "/src/");
			pack = StringAfterFirst.get(pack, "/java/");
		} else {
			pack = StringAfterFirst.get(path, "/target/");
			pack = StringAfterFirst.get(pack, "classes/");
		}

		pack = pack.replace("/", ".");

		if (!path.endsWith("/")) {
			pack += ".";
		}

		ListClass list = new ListClass();

		List<File> files = UFile.getFiles(path, "class", "java");

		for (File file : files) {
			String s = file.toString();
			s = StringAfterLast.get(s, "/");
			s = StringBeforeLast.get(s, ".");
			s = pack + s;
			try {
				list.add(getClassObrig(s));
			} catch (ExceptionInInitializerError | Exception e) {
				ULog.debug("Erro ao tentar carregar " + s);
				UException.printTrace(e);
			}
		}
		return list;

	}

	@Deprecated//Chame direto
	public static boolean isList(Class<?> type) {
		return UType.isList(type);
	}

	@SuppressWarnings("unchecked")
	public static <T> T newInstanceAutoParameters(Class<T> classe) {

		try {

			if (isAbstract(classe)) {
				classe = AtributosBuild.get(classe, false).getObrig("DEFAULT_IMPLEMENTATION").get(classe);
			}

			Constructor<?> constructor = classe.getConstructors()[0];
			Class<?>[] parameterTypes = constructor.getParameterTypes();

			if (parameterTypes.length == 0) {
				return newInstance(classe);
			}

			Object[] args = UType.asAutoParameters(parameterTypes);

			return (T) constructor.newInstance(args);

		} catch (Exception e) {
			throw UException.runtime(e);
		}

	}

	public static String getTitulo(Class<?> classe) {
		Titulo annotation = classe.getAnnotation(Titulo.class);
		if (annotation != null) {
			return annotation.value();
		}
		return StringToCamelCaseSepareClass.exec(classe);
	}

}
