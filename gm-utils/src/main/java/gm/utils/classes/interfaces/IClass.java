package gm.utils.classes.interfaces;

import java.lang.annotation.Annotation;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcTipo;
import gm.utils.javaCreate.ToJcTipo;
import src.commom.utils.string.StringPrimeiraMinuscula;

public abstract class IClass implements ToJcTipo {
	
	public abstract String getName();
	public abstract String getSimpleName();
	protected abstract Lst<IField> loadFields();
	public abstract String getPackageName();
	public abstract IField getId();
	public abstract IAnnotation getAnnotation(Class<? extends Annotation> annotationClass);
	public abstract IClass getMetodoRetorno(String nomeMetodo);
	public abstract boolean isEnum();
	
	private Lst<IField> fields;
	
	public final Lst<IField> getFields() {
		
		if (fields == null) {
			fields = loadFields();
		}
		
		return fields.copy();
		
	}
	
	/* be careful */
	public final Lst<IField> getFieldsReal() {
		
		if (fields == null) {
			fields = loadFields();
		}
		
		return fields;
		
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if (obj == null) {
			return false;
		}
		
		if (obj == this) {
			return true;
		}
		
		if (obj instanceof IClass) {
			IClass o = (IClass) obj;
			return o.getName().contentEquals(getName());
		}

		if (obj instanceof Class) {
			Class<?> o = (Class<?>) obj;
			return o.getName().contentEquals(getName());
		}
		
		return false;
		
	}
	
	public boolean eq(Class<?> classe) {
		if (classe == null) {
			return false;
		}
		return classe.getName().contentEquals(getName());
	}
	
	public boolean eq(IClass classe) {
		if (classe == null) {
			return false;
		}
		return classe.getName().contentEquals(getName());
	}
	
	public JcTipo sufixType(String s) {
		return new JcTipo(getName() + s);
	}

	public abstract String getTableName();
	public abstract String getSchemaName();
	
	public final String ts() {
		return getSchemaName() + "." + getTableName();
	}
	
	public final String sn() {
		return getSimpleName();
	}
	
	public final boolean isAnnotationPresent(Class<? extends Annotation> annotationClass) {
		return getAnnotation(annotationClass) != null;
	}
	public final boolean isInt() {
		return eq(Integer.class) || eq(int.class);
	}
	public final boolean isBoolean() {
		return eq(Boolean.class) || eq(boolean.class);
	}
	
	public final String getVarName() {
		return StringPrimeiraMinuscula.exec(getSimpleName());
	}
	
	public final Lst<IField> getFieldsPersistentes() {
		return getFields().filter(i -> i.isPersistent());
	}

	@Override
	public String toString() {
		return getSimpleName();
	}
	
	protected abstract IClass getSuperclass();
	protected abstract boolean instanceOf(IClass classe);
	protected abstract boolean isAbstract();
	
	public static IClass build(String s) {
		Class<?> classe = UClass.getClass(s);
		if (classe == null) {
			throw new NaoImplementadoException();
		} else {
			return ClasseReal.get(classe);
		}
	}
	
	public abstract GFile getJavaFile();
	
}
