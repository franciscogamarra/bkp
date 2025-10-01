package gm.utils.classes.interfaces;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

import gm.utils.reflection.Metodos;
import lombok.Getter;

public class IAnnotation {
	
	private Map<String, Object> map = new HashMap<>();

	@Getter
	private String nome;
	
	public IAnnotation(String nome) {
		this.nome = nome;
	}
	
	@SuppressWarnings("unchecked")
	public <T> T get(String key) {
		return (T) map.get(key);
	}
	
	public void set(String key, Object value) {
		map.put(key, value);
	}
	
	public static IAnnotation build(Annotation a) {
		
		if (a == null) {
			return null;
		}
		
		IAnnotation o = new IAnnotation(a.getClass().getSimpleName());
		Metodos metodos = Metodos.get(a.getClass()).filter(i -> i.getParameterCount() == 0);
		metodos.forEach(i -> {
			Object value = i.invoke(a);
			if (value != null) {
				if (value.getClass() == Class.class) {
					value = ClasseReal.get((Class<?>) value);
				}
				o.set(i.getName(), value);
			}
		});
		return o;
		
	}
	
}
