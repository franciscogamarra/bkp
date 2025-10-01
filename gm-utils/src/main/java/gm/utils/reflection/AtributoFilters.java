package gm.utils.reflection;

import java.util.function.Predicate;

import jakarta.persistence.Transient;

public class AtributoFilters {

	public static final Predicate<Atributo> filterTransients = new Predicate<Atributo>() {
		@Override
		public boolean test(Atributo t) {
			return t.getField().getModifiers() == 130 || t.hasAnnotation(Transient.class);
		}
	};

}
