package gm.languages.java.expressoes;

import java.lang.annotation.Annotation;
import java.util.function.Consumer;
import java.util.function.Predicate;

import gm.utils.comum.Lst;
import lombok.Getter;

@Getter
public class AnnotationsJava {

	protected final Lst<AnnotationJava> lst = new Lst<>();
	
	public boolean has(Class<? extends Annotation> classe) {
//		JcTipo.b
		return lst.anyMatch(i -> i.getType().eq(classe));
	}

	public boolean remove(Class<? extends Annotation> classe) {
		AnnotationJava o = lst.unique(i -> i.getType().eq(classe));
		if (o == null) {
			return false;
		}
		lst.remove(o);
		return true;
	}
	
	public void add(AnnotationJava a) {
		lst.add(a);
	}
	
	public void add0(AnnotationJava a) {
		lst.add(0, a);
	}
	
	public void add(Class<?> classe) {
		add(new AnnotationJava(classe));
	}
	
	public void each(Consumer<AnnotationJava> action) {
		lst.each(action);
	}

	public boolean isNotEmpty() {
		return lst.isNotEmpty();
	}
	
	public boolean removeIf(Predicate<? super AnnotationJava> predicate) {
		return lst.removeIf(predicate);
	}
	
}
