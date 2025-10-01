package gm.utils.classes.interfaces;

import java.lang.annotation.Annotation;

public abstract class IField {

	public abstract String getName();
	public abstract String upperNome();
	public abstract String getNomeValue();
	public abstract String getColumnName();
	
	public abstract boolean isString();
	public abstract boolean isBoolean();
	public abstract boolean isShort();
	public abstract boolean isInteger();
	public abstract boolean isLong();
	public abstract boolean isDate();
	public abstract boolean isDataComHora();
	public abstract boolean isFk();
	public abstract boolean isEnum();
	
	public abstract boolean isTransient();
	public abstract boolean isStatic();
	public abstract boolean isList();
	public abstract boolean isFinal();
	public abstract boolean isId();
	public abstract boolean isNumeric1();
	public abstract boolean isNumeric2();
	public abstract boolean isNumeric3();
	public abstract boolean isNumeric4();
	public abstract boolean isNumeric5();
	public abstract boolean isNumeric8();
	public abstract boolean isNumeric15();
	public abstract boolean isNumeric18();
	
	public abstract boolean isNotNull();
	
	public abstract int digitsFraction();
	public abstract int getMin();
	public abstract int getMax();
	
	public abstract IClass getType();
	
	public abstract IAnnotation getAnnotation(Class<? extends Annotation> annotationClass);
	
	public boolean isPersistent() {
		return !isTransient() && !isStatic() && !isList();
	}

	public final boolean is(Class<?> type) {
		IClass type2 = getType();
		return type2.eq(type);
	}

	public final boolean is(IClass type) {
		return getType().eq(type);
	}
	public final boolean eq(String name) {
		return getName().contentEquals(name);
	}
	
	public final String get() {
		return "get" + upperNome() + "()";
	}
	
	public final String set(String s) {
		return "set" + upperNome() + "("+s+");";
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return getAnnotation(annotationClass) != null;
	}

	@Override
	public String toString() {
		return getName();
	}
	
}