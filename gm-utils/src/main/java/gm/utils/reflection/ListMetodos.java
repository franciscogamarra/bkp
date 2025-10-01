package gm.utils.reflection;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.Map;

import gm.utils.exception.NaoImplementadoException;

public class ListMetodos {

	private static Map<Class<?>, Metodos> map = new HashMap<>();

	public static Metodos get(Type type) {
		
		if (type instanceof Class) {
			Class<?> classe = (Class<?>) type;
			return get(classe);
		}
		
		throw new NaoImplementadoException();
		
	}
	
	public static Metodos get(Class<?> classe) {
		Metodos metodos = map.get(classe);
		if (metodos == null) {
			metodos = getSynchronized(classe);
		}
		return metodos.clone();
	}
	
	private synchronized static Metodos getSynchronized(Class<?> classe) {
		Metodos metodos = map.get(classe);
		if (metodos == null) {
			metodos = new Metodos(classe);
			metodos.sort();
			map.put(classe, metodos);
		}
		return metodos;
	}
	
	public static void main(String[] args) {
		get(Object.class).print();
	}

}
