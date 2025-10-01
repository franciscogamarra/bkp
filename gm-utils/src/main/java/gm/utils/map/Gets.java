package gm.utils.map;

import java.util.HashMap;
import java.util.Map;

import gm.utils.anotacoes.IgnoreJson;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import gm.utils.string.ListString;

public class Gets {
	
	private static final ListString metodosIgnorar = ListString.array("hashCode","equals","toString");
	
	public final Atributos as = new Atributos();
	public final Metodos mts = new Metodos();

	private Class<?> classe;
	
	private Gets(Class<?> classe) {
		
		this.classe = classe;
		Atributos as = AtributosBuild.get(classe);
		as.removeStatics();
		if (as.getId() != null) {
			as.add(0, as.getId());
		}

		ListString nomesGets = new ListString();

		for (Atributo a : as) {

			nomesGets.add("get"+a.upperNome());
			nomesGets.add("is"+a.upperNome());

			if ((!a.isList() && a.getType().toString().startsWith("interface ")) || a.hasAnnotation(IgnoreJson.class)) {
				continue;
			}
			
			this.as.add(a);

		}

		Metodos metodos = ListMetodos.get(classe).filter(m -> !m.returnVoid() && !nomesGets.contains(m.getName()) && m.getParametros().isEmpty() && m.getName().startsWith("get"));
		metodos.removeIf(m -> metodosIgnorar.contains(m.getName()));

		for (Metodo m : metodos) {

			if (m.hasAnnotation(IgnoreJson.class)) {
				continue;
			}
			
			mts.add(m);

		}
		
	}
	
	@Override
	public String toString() {
		return classe.getSimpleName();
	}
	
	private static final Map<Class<?>, Gets> map = new HashMap<>();
	
	public static Gets get(Class<?> classe) {
		
		Gets o = map.get(classe);
		
		if (o == null) {
			o = new Gets(classe);
			map.put(classe, o);
		}
		
		return o;
		
	}
	
}