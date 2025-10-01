package gm.utils.reflection;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;

import gm.utils.comum.Lst;
import gm.utils.comum.UType;
import gm.utils.exception.UException;
import js.support.console;
import lombok.Getter;

public class Metodo extends IMetodo {
	@Getter private Method method;
//	private Class<?> classe;
//	private Class<?> genericClass;
	public Metodo(Metodos metodos, Method method) {
		this.method = method;
//		classe = metodos.getClasse();
//		if (!UClass.isAbstract(classe)) {
//			genericClass = UGenerics.getGenericClass(classe);
//		}
	}
	public boolean isTransientModifier(){
		return java.lang.reflect.Modifier.isTransient(method.getModifiers());
	}
	public boolean isStatic(){
		return java.lang.reflect.Modifier.isStatic(method.getModifiers());
	}
	public boolean isFinal(){
		return java.lang.reflect.Modifier.isFinal(method.getModifiers());
	}
	public boolean isVolatile(){
		return java.lang.reflect.Modifier.isVolatile(method.getModifiers());
	}
	public boolean isOverride(){
		return hasAnnotation(Override.class);
	}
	public <T extends Annotation> boolean hasAnnotation(Class<T> annotation) {
		return getAnnotation(annotation) != null;
	}
	public <T extends Annotation> T getAnnotation(Class<T> annotation) {
		return method.getAnnotation(annotation);
	}
	
	public Lst<Annotation> getAnnotations() {
		if (method == null) {
			return null;
		}
		Annotation[] annotations = method.getAnnotations();
		return new Lst<>(Arrays.asList(annotations));
	}
	public Class<?> getClasseReal(){
		return method.getDeclaringClass();
	}
	public Classe retorno(){
		return Classe.get(method.getGenericReturnType());
//		Type type = method.getGenericReturnType();
//		if ( "T".equals(type.getTypeName()) ) {
//			return genericClass;
//		}
//		return method.getReturnType();
	}
	public boolean returnVoid() {
		Classe o = retorno();
		return o != null && (o.eq(Void.class) || o.eq(void.class));
	}
	public boolean isAbstract() {
		return java.lang.reflect.Modifier.isAbstract(method.getModifiers());
	}
	public String getAcesso() {
		return isPublic() ? "public" : isPrivate() ? "private" : isProtected() ? "protected" : "default";
	}

	public void print() {
		try {
			String o = getClasseReal().getSimpleName() + " - " + getAssinaturaSemOsNomesDosParametros();
			console.log(o);
		} catch (Throwable e) {
			System.err.println(method.getName());
			throw UException.runtime(e);
		}
	}

	@Override
	public String toString() {
		return method.toString();
	}
	
	public String getName() {
		return method.getName();
	}
	
	@Deprecated//usar getName
	public String nome() {
		return getName();
	}

	@SuppressWarnings("unchecked")
	public <T> T invoke(Object o, Object... args) {
		try {//o.getClass()//method.getDeclaringClass()
			return (T) method.invoke(o, args);
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}
	public <T> T invokeAutoParameters(Object o) {
		return invoke(o, UType.asAutoParameters(method.getParameterTypes()));
	}
	@Override
	public String getAssinaturaSemParametrosImpl() {
		return getModificadorDeAcesso() + " " + (isFinal() ? "final " : "") + retorno().getSimpleName() + " " + getName();
	}
	@Override
	protected Class<?> getClasseImpl() {
		return getClasseReal();
	}
	@Override
	protected int getModifiers() {
		return method.getModifiers();
	}
	@Override
	protected Parameter[] getParameters() {
		return method.getParameters();
	}
	@Override
	protected Class<?> getDeclaringClasseImpl() {
		return method.getDeclaringClass();
	}
}
