package gm.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UType;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.jpa.TableSchema;
import gm.utils.string.ListString;
import lombok.Getter;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.string.StringRight;

public class Classe {
	
	private static final Map<Type, Classe> map = new HashMap<>();
	
	@Getter
	public final Type type;
	
	@Getter
	public final Class<?> classe;
	public final Lst<Classe> generics;
	public final String name;
	public final String sn;
	public final String snm;
	
	public static Classe get(Type type) {
		
		if (type == null) {
			throw new NullPointerException("type == null");
		}
		
		Classe o = map.get(type);
		if (o == null) {
			o = new Classe(type);
			map.put(type, o);
		}
		return o;
	}
	
	private Classe(Type type) {
		
		this.type = type;
		
		if (type instanceof ParameterizedType) {
			generics = new Lst<>();
			ParameterizedType ptype = (ParameterizedType) type;
			for (Type subtype : ptype.getActualTypeArguments()) {
				generics.add(new Classe(subtype));
			}
		} else {
			generics = null;
		}
		
		name = type.getTypeName();
		
		String s = name;
		if (s.contains("<")) {
			s = StringBeforeFirst.get(s, "<");
		}
		
		this.classe = UClass.getClass(s);
		
//		if (this.classe == null) {
//			Print.ln(s);
//		}
		
		if (s.contains(".")) {
			s = StringAfterLast.get(s, ".");
		}
		sn = s;
		snm = StringPrimeiraMinuscula.exec(sn);
		
//		if (!(type instanceof Class)) {
//			Print.ln(this);
//		}

	}
	
	@Override
	public String toString() {
		return getSimpleName() + (generics == null ? "" : "<"+generics.join(", ")+">");
	}
	
	public String getName() {
		return name;
	}
	
	public String getSimpleName() {
		return sn;
	}
	
	public boolean isLang() {
		return UType.isLang(classe);
	}
	
	public boolean eq(Type type) {
		return this.type == type;
	}
	
	public boolean isAnnotationPresent(Class<? extends Annotation> annotation) {
		if (classe == null) {
			SystemPrint.ln("");
		}
		return classe.isAnnotationPresent(annotation);
	}
	
	public boolean isAnyAnnotationPresent(Class<? extends Annotation> a, Class<? extends Annotation> b) {
		
		if (classe.isAnnotationPresent(a)) {
			return true;
		}
		
		if (classe.isAnnotationPresent(b)) {
			return true;
		}
		
		return false;
		
	}
	
	public boolean pkgIs(String s) {
		return s != null && getPkg().contentEquals(s);
	}
	
	public String getPkg() {
		return classe.getPackage().getName().toString();
	}
	
	public boolean isEntity() {
		return isAnnotationPresent(Entity.class);
	}
	
	public Atributo getAtributoId() {
		return Atributos.getPersists(classe).unique(i -> i.isAnnotationPresent(Id.class));
	}
	
	public Atributos getAtributos() {
		return Atributos.get(classe);
	}
	
	public boolean isData() {
		return UType.isData(classe);
	}
	
	public boolean instanceOf(Class<?> classe) {
		return classe.isAssignableFrom(this.classe);
	}
	
	public static final Lst<GFile> knowPaths = new Lst<>();
	static {
		knowPaths.add(GFile.get("../src/main/java"));
	}
	
	public ListString load() {
		
		String s = name.replace(".", "/") + ".java";
		
		for (GFile path : knowPaths) {
			GFile file = path.join(s);
			if (file.exists()) {
				return file.load();
			}
		}
		
		knowPaths.map(i -> i.join(s).toString()).printError();
		
		throw UException.runtime("Não foi encontrado nenhum arquivo");
		
	}
	
	public void sort(Lst<Atributo> as) {
		ListString list = load();
		list.trimPlus();
		list.removeIf(s -> !s.startsWith("private") && !s.endsWith(";"));
		list.replaceEach(i -> StringRight.ignore1(StringAfterLast.get(i, " ")));
		as.sortInt(i -> list.indexOf(i.getName()));
	}

	public String getTs() {
		return TableSchema.get(classe);
	}
	
	public String getSchema() {
		return TableSchema.getSchema(classe);
	}

	public boolean isEnum() {
		return classe.isEnum();
	}

	public boolean isAbstract() {
		return UClass.isAbstract(classe);
	}
	
	public <A extends Annotation> A getAnnotation(Class<A> annotationClass) {
		return classe.getAnnotation(annotationClass);
	}

	public Metodos getMetodos() {
		return Metodos.get(classe);
	}
	
}
