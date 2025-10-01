package gm.utils.reflection;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.comum.Lst;
import gm.utils.exception.UException;
import lombok.Getter;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringCompare;

@Getter
public class Metodos extends Lst<Metodo>{
	
	private static final long serialVersionUID = 1L;
	
	private Class<?> classe;

	public Metodos(){}

	protected Metodos(Class<?> classe){
		this.classe = classe;

		List<Method> methods = read(classe);

		Lst<String> adicionados = new Lst<>();

		while ( classe != null && (classe != Object.class || this.classe == Object.class) ) {

			for (Method method : methods) {
				if (method.isSynthetic() || (method.getDeclaringClass() != classe) || (method.getDeclaringClass().equals(Object.class) && this.classe != Object.class)) {
					continue;
				}
				String s = method.toString();
				s = StringBeforeFirst.get(s, "(");
				s = StringAfterLast.get(s, " ");
				s = StringBeforeFirst.get(method.toString(), " " + s + "(");
				s = StringAfterFirst.get(method.toString(), s + " ");
				s = StringAfterFirst.get(s, classe.getName() + ".");

				if (adicionados.contains(s)) {
					continue;
				}

//				resolver metodos genericos sobrescritos
//				nao serve para todos os casos, depois terei que aperfeiçoar
				if (s.endsWith("(java.lang.Object)")) {
					String nome = StringBeforeFirst.get(s, "(");
					if (adicionados.anyMatch(o -> !StringCompare.eq(o, nome + "()") && o.startsWith(nome + "(") && !o.contains(","))) {
						continue;
					}
				}

				add( new Metodo(this, method) );
				adicionados.add(s);

			}
			classe = classe.getSuperclass();
		}
	}

	private static List<Method> read(Class<?> c) {

		List<Method> result = new ArrayList<>();
		
		if (c == Object.class) {
			
			Method[] list = c.getDeclaredMethods();

			for (Method o : list) {
				String n = o.getName();
				if (StringCompare.eq(n, "toString")) {
					result.add(o);
				}
			}
			
			return result;
			
		}

		if (c.getName().contains("$ReflectUtils$")) {
			return result;
		}
		
		Class<?>[] interfaces = c.getInterfaces();
		
		while (c != null && c != Object.class) {

			Method[] list = c.getDeclaredMethods();

			for (Method o : list) {
				
				String n = o.getName();
				
				if ("finalize".equals(n) || "register".equals(n)) {
//				if ("finalize".equals(n) || "clone".equals(n) || "register".equals(n)) {
					continue;
				}

				try {
					o.setAccessible(true);
					result.add(o);
				} catch (Throwable e) {
					if (StringCompare.eq(e.getClass().getSimpleName(), "InaccessibleObjectException")) {
						continue;
					}
					throw e;
				}
				
			}

			c = c.getSuperclass();

		}
		for (Class<?> i : interfaces) {
			result.addAll( read(i) );
		}
		return result;
	}

	public void removeIfClass(Class<?> classe){
		Predicate<Metodo> filter = t -> t.getClasseReal().equals(classe);
		removeIf(filter);
	}

	public Metodos sobrescritos(){
		Metodos metodos = new Metodos();
		metodos.classe = classe;
		for (Metodo m : this) {
			if (m.isOverride()) {
				metodos.add(m);
			}
		}
		return metodos;
	}
	public void removeSobrescritos() {
		remove(sobrescritos());
	}
	public void remove(Metodos list){
		removeAll(list);
	}
	public Metodos remove(String nome){
		Metodos list = find(nome);
		remove(list);
		return list;
	}
	public Metodos find(String nome) {
		Metodos list = new Metodos();
		for (Metodo o : this) {
			if ( o.getName().equalsIgnoreCase(nome) ) {
				list.add(o);
			}
		}
		return list;
	}
	public Metodo getObrig(String nome) {
		Metodo metodo = get(nome);
		if (metodo == null) {
			throw UException.runtime("Método não encontrado na classe: " + getClasse().getSimpleName() + "." + nome);
		}
		return metodo;
	}
	public Metodo get(String nome, int parameters) {
		List<Metodo> list = find(nome);
		if (list.isEmpty()) {
			return null;
		}
		List<Metodo> list2 = new ArrayList<>();
		for (Metodo metodo : list) {
			if ( metodo.getParameterCount() == parameters ) {
				list2.add(metodo);
			}
		}
		if (list2.isEmpty()) {
			return null;
		}
		if (list2.size() > 1) {
			throw UException.runtime("Foi encontrado mais de um metodo com o nome " + nome);
		}
		return list2.get(0);

	}
	public Metodo get(String nome) {
		Metodos list = find(nome);
		if (list.isEmpty()) {
			return null;
		}
		if (list.size() > 1) {
			throw UException.runtime("mais de um metodo com o nome " + nome);
		}
		return list.get(0);
	}
	public Metodo get(String nome, Class<?>... parametros) {
		List<Metodo> list = find(nome);
		for (Metodo metodo : list) {
			if (metodo.isParameters(parametros)) {
				return metodo;
			}
		}
		return null;
	}

	public void removeSeContemParametros() {
//		removeIf( o -> !o.getParameters().isEmpty() );
		Predicate<Metodo> filter = t -> t.getParameterCount() > 0;
		removeIf(filter);
	}
	public void removeVoids() {
//		removeIf( o -> o.returnVoid() );
		Predicate<Metodo> filter = t -> t.returnVoid();
		removeIf(filter);
	}
	public void removeHerdados() {
//		removeIf( o -> o.getClasseReal().equals(classe) );

		Class<?> classe = this.classe;
		Predicate<Metodo> filter = t -> !t.getClasseReal().equals(classe);
		removeIf(filter);
	}
	@Override
	public void print() {
//		forEach( o -> o.print() );
		for (Metodo o : this) {
			o.print();
		}
	}
	public Metodos getWhereNomeEndsWith(String s) {
		Metodos list = new Metodos();
		for (Metodo a : this) {
			if (a.getName().endsWith(s)) {
				list.add(a);
			}
		}
		return list;
	}
	public void removeifRetorno(Class<?> classe) {
//		removeIf( o -> o.retorno().equals(classe) );
		Predicate<Metodo> filter = t -> t.retorno().eq(classe);
		removeIf(filter);
	}
	public void sort() {
//		sort( (a,b) -> a.nome().compareTo(b.nome()) );
		Comparator<Metodo> c = (a, b) -> a.getName().compareTo(b.getName());
		sort(c);
	}

	public void removeAbstracts() {
		removeIf(o -> o.isAbstract());
	}

	@Override
	public Metodos filter(Predicate<Metodo> predicate) {
		List<Metodo> list = stream().filter(predicate).collect(Collectors.toList());
		Metodos metodos = new Metodos();
		metodos.addAll(list);
		return metodos;
	}
	public boolean contains(Predicate<Metodo> predicate) {
		return !filter(predicate).isEmpty();
	}
	public boolean contains(String nome) {
		return get(nome) != null;
	}
	public Lst<String> getNames() {
		Lst<String> list = new Lst<>();
		for (Metodo o : this) {
			list.add(o.getName());
		}
		return list;
	}

	@Override
	public Metodos clone() {
		Metodos list = new Metodos();
		list.addAll(this);
		return list;
	}

	public Metodos gets() {
		return filter(o -> o.getName().startsWith("get") && !o.returnVoid());
	}
	
	public static Metodos get(Type type) {
		return ListMetodos.get(type);
	}

}
