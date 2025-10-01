package gm.languages.ts.expressoes;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import gm.languages.java.outros.Utils;
import gm.languages.palavras.comuns.conjuntos.arrow.Arrow;
import gm.languages.ts.javaToTs.JavaToTs;
import gm.languages.ts.javaToTs.exemplo.xx.Any;
import gm.utils.comum.Lst;
import gm.utils.javaCreate.JcTipo;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringRight;

@Getter @Setter
public class ClassTs {
	
	public static final String NULLUNDEFINED = "|null|undefined";
	
	public static final Lst<ClassTs> typesUtilizados = new Lst<>();

	private String path;
	private String simpleName;
	private boolean primitiva;
	private boolean jaContemplaNull;
	
	public static final ClassTs anyTs = new ClassTs(null, "any");

	public static final ClassTs str = new ClassTs(null, "str") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			
			if (notNull) {
				throw new RuntimeException("tipo deveria ser string e nao str");
			}
			
			return "str";
			
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
	};
	
	public static final ClassTs objectTs = new ClassTs(null, "Object");
	
	public static final ClassTs numberTs = new ClassTs(null, "number");

	public static final ClassTs string = new ClassTs(null, "string");
	
	public static final ClassTs intPrimitiva = new ClassTs(null, "int") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			return "int";
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
		@Override
		public boolean isPrimitiva() {
			return true;
		}
		
	};

	public static final ClassTs integerTs = new ClassTs(null, "Integer") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			return "Integer";
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
	};

	public static final ClassTs longPrimitiva = new ClassTs(null, "long") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			return "long";
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
		@Override
		public boolean isPrimitiva() {
			return true;
		}
		
	};

	public static final ClassTs longTs = new ClassTs(null, "Long") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			return "Long";
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
	};

	public static final ClassTs doubleTs = new ClassTs(null, "Double") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			return "Double";
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
	};

	public static final ClassTs doublePrimitivaTs = new ClassTs(null, "double") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			return "double";
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
	};
	
	public static final ClassTs booleanPrimitiva = new ClassTs(null, "boolean") {
		
		@Override
		public String getSimpleName(boolean notNull) {
			return "boolean";
		}
		
	};
	
	public static final ClassTs booleanTs = new ClassTs(null, "Boolean") {

		@Override
		public String getSimpleName(boolean notNull) {
			return "Boolean";
		}
		
		@Override
		protected boolean isType() {
			return true;
		}
		
	};
	
	public static final ClassTs voidTs = new ClassTs(null, "void");

	private ClassTs(String path, String simpleName) {
		this.path = path;
		this.simpleName = simpleName;
	}
	
	public String getSimpleName(boolean notNull) {
		
		if (this == anyTs) {
			return "any";
		}
		
		String s = getSimpleName();
		if (!notNull && !jaContemplaNull) {
			s += NULLUNDEFINED;
		}
		return s;
	}
	
	public String getName() {
		
		if (getPath() == null) {
			return getSimpleName();
		}
		
		return getPath() + "/" + getSimpleName();
		
	}
	
	private static Map<String, ClassTs> map = new HashMap<>();
	
	public static ClassTs onGet(ClassTs o) {
		if (o.isType()) {
			typesUtilizados.addIfNotContains(o);
		}
		return o;
	}
	
	protected boolean isType() {
		return false;
	}
	
	public static ClassTs get(String path, String simpleName) {
		return onGet(getPrivate(path, simpleName));
	}
	
	public static ClassTs get(JcTipo type) {
		return onGet(getPrivate(type));
	}
	
	private static ClassTs getPrivate(String path, String simpleName) {
		
		if ("?".contentEquals(simpleName) || "Object".contentEquals(simpleName)) {
			return anyTs;
		}

		if ("String".contentEquals(simpleName)) {
			return getString();
		}

		if ("int".contentEquals(simpleName)) {
			return getIntTs();
		}

		if ("Integer".contentEquals(simpleName)) {
			return getIntegerTs();
		}
		
		if ("long".contentEquals(simpleName)) {
			return longPrimitiva;
		}

		if ("Long".contentEquals(simpleName) ) {
			return longTs;
		}

		if ("boolean".contentEquals(simpleName)) {
			return booleanPrimitiva;
		}
		
		if ("Boolean".contentEquals(simpleName)) {
			return booleanTs;
		}

		if ("JsObject".contentEquals(simpleName)) {
			return objectTs;
		}

		if ("Double".contentEquals(simpleName)) {
			return getDoubleTs();
		}

		if ("double".contentEquals(simpleName)) {
			return getDoublePrimitivaTs();
		}
		
		if ("Number".contentEquals(simpleName)) {
			return numberTs;
		}

		if (path != null && path.contains("srcx")) {
			path = path.replace("srcx", "src");
			simpleName = StringRight.ignore1(simpleName);
		}
		
		String key = path + "." + simpleName;
		ClassTs o = map.get(key);
		if (o == null) {
			o = new ClassTs(path, simpleName);
			map.put(key, o);
		}
		return o;
	}

	private static ClassTs getIntegerTs() {
		return JavaToTs.config.ourTypes ? integerTs: numberTs;
	}

	private static ClassTs getIntTs() {
		return JavaToTs.config.ourTypes ? intPrimitiva : numberTs;
	}

	private static ClassTs getDoubleTs() {
		return JavaToTs.config.ourTypes ? doubleTs : numberTs;
	}

	private static ClassTs getDoublePrimitivaTs() {
		return JavaToTs.config.ourTypes ? doublePrimitivaTs : numberTs;
	}
	
	public static ClassTs getString() {
		return JavaToTs.config.ourTypes ? str : string;
	}
	
	private static Map<Type, ClassTs> map2 = new HashMap<>();
	
	public static ClassTs getGenerics(String nome) {
		if (nome.contentEquals("?")) {
			return anyTs;
		}
		return new ClassTs(null, nome);
	}
	
	public static ClassTs getReticencias(TipoTs tipo) {
		
		String key = "..." + tipo.getClasse().getName();
		ClassTs o = map.get(key);
		if (o == null) {
			o = new ClassTs(null, key);
			map.put(key, o);
		}
		return o;
	}
	
	private static ClassTs getPrivate(JcTipo type) {

		if (type == null) {
			throw new NullPointerException("classe == null");
		}
		
		if (type.getName().contains(".")) {
			Class<?> classe = type.getClasse();
			if (classe != null) {
				return get(classe);
			}
		}
		
		return get(type.getPkg(), Utils.getSimpleName(type));
		
	}
	
	public static ClassTs get(Type type) {
		
		if (type == null) {
			throw new NullPointerException("classe == null");
		}

		if (type == Object.class || type == Arrow.class || type == Any.class ) {
			return anyTs;
		}

		if (type == String.class) {
			return getString();
		}

		if (type == int.class) {
			return getIntTs();
		}

		if (type == Integer.class) {
			return getIntegerTs();
		}

		if (type == long.class) {
			return longPrimitiva;
		}

		if (type == Long.class) {
			return longTs;
		}

		if (type == boolean.class) {
			return booleanPrimitiva;
		}

		if (type == Boolean.class) {
			return booleanTs;
		}

		if (type == Double.class) {
			return getDoubleTs();
		}

		if (type == Number.class) {
			return numberTs;
		}
		
		if (type == void.class) {
			return voidTs;
		}
		
		if (type instanceof Class) {
			
			Class<?> c = (Class<?>) type;
			
			if (c.isAnnotationPresent(gm.languages.ts.javaToTs.annotacoes.Any.class)) {
				return anyTs;
			}
			
		}
		
		ClassTs o = map2.get(type);
		if (o == null) {
			
			if (type instanceof Class) {
				
				Class<?> c = (Class<?>) type;
				
				if (c.getPackage() == null) {
					throw new NullPointerException("classe.getPackage() == null : " + c.getSimpleName());
				}
				
				o = get(c.getPackage().getName(), c.getSimpleName());
				map2.put(type, o);
				
			} else {
				o = getGenerics(type.getTypeName());
			}
			
		}
		return o;
	}

	public boolean eq(Type type) {
		return this == get(type);
	}
	
	public Type getType() {
		Set<Type> keySet = map2.keySet();
		for (Type type : keySet) {
			if (eq(type)) {
				return type;
			}
		}
		return null;
	}
	
	@Override
	public String toString() {
		return getSimpleName();
	}

	public boolean isString() {
		return this == getString() || this == string;
	}

}