package js.array;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.anotacoes.Ignorar;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.lambda.F1;
import gm.utils.lambda.F2;
import gm.utils.lambda.P1;
import gm.utils.lambda.P2;
import gm.utils.map.MapSO;
import js.html.Element;
import js.html.NodeList;
import src.commom.utils.array.Itens;
import src.commom.utils.integer.IntegerBox;
import src.commom.utils.string.StringBox;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;

@Ignorar
public class Array<T> {

	public Class<T> classe;

	public Array() {
		list.afterChangeFunc = () -> {
			length = lengthArray() > 0;
		};
	}

	@SafeVarargs
	public Array(T... itens) {
		this();
		for (T iten : itens) {
			this.push(iten);
		}
	}

	public final Lst<T> list = new Lst<>();

	public void push(T o) {
		this.list.add(o);
	}
	public void forEach(P1<T> action) {
		this.list.forEach(i -> action.call(i));
	}
	public void forEach(P2<T, Integer> action) {
		for (int i = 0; i < list.size(); i++) {
			action.call(list.get(i), i);
		}
	}
	public void sort(F2<T,T,Integer> action) {
		this.list.sort((a, b) -> action.call(a, b));
	}
	public Array<T> filter(F1<T, Boolean> predicate) {
		Array<T> array = new Array<>();
		array.classe = this.classe;
		List<T> collect = this.list.stream().filter(i -> predicate.call(i)).collect(Collectors.toList());
		for (T o : collect) {
			array.push(o);
		}
		return array;
	}
	
	public T find(F1<T, Boolean> predicate) {
		Array<T> array = filter(predicate);
		if (array.lengthArray() == 0) {
			return null;
		} else {
			return array.array(0);
		}
	}
	
	/* utilizado no react para identificação */
	public static final Lst<IntegerBox> MAP_IN = new Lst<>();
	
	public <TT> Array<TT> map(F1<T,TT> func) {
		IntegerBox box = new IntegerBox(0);
		MAP_IN.add(box);
		Array<TT> array = new Array<>();
		this.list.forEach(o -> {
			array.push(func.call(o));
			box.inc1();
		});
		MAP_IN.removeLast();
		return array;
	}
	
	public <TT> Array<TT> map(F2<T,Integer,TT> func) {
		IntegerBox box = new IntegerBox(0);
		MAP_IN.add(box);
		Array<TT> array = new Array<>();
		this.list.forEach(o -> {
			array.push(func.call(o, box.get()));
			box.inc1();
		});
		MAP_IN.removeLast();
		return array;
	}
	
	@Deprecated
	public T removeFirst() {
		return shift();
	}

	@Deprecated
	public T removeLast() {
		return pop();
	}

	//removeFirst
	public T shift() {
		if (list.isEmpty()) {
			return null;
		}
		return list.remove(0);
	}

	public boolean some(Predicate<T> predicate) {
		return list.stream().anyMatch(predicate);
	}
	@SuppressWarnings("unchecked")
	public Array<T> concat(T... os) {
		Array<T> array = new Array<>();
		for (T o : list) {
			array.push(o);
		}
		for (T o : os) {
			array.push(o);
		}
		return array;
	}
	public Array<T> concat(Array<T> list) {
		Array<T> array = new Array<>();
		for (T t : this.list) {
			array.push(t);
		}
		for (T t : list.list) {
			array.push(t);
		}
		return array;
	}
	public int indexOf(T o) {
		return list.indexOf(o);
	}

	public T pop() {
		if (list.isEmpty()) {
			return null;
		}
		return list.removeLast();
	}

	
	public boolean length;

	@Deprecated /* utilize lengthArray pois eh melhor para conversao */
	public int length() {
		return lengthArray();
	}
	
	//para facilitar na conversao
	public int lengthArray() {
		return list.size();
	}
	
	public <RESULT> RESULT reduce(F2<RESULT, T, RESULT> func, RESULT startValue) {
		for (T t : list) {
			startValue = func.call(startValue, t);
		}
		return startValue;
	}
	public String join(String separador) {
		if (list.isEmpty()) {
			return "";
		}
		if (list.size() == 1) {
			return list.get(0).toString();
		}
		StringBox box = new StringBox("");
		forEach(o -> box.add(separador + StringParse.get(o)));
		String s = box.get();
		if (!StringEmpty.is(s) && separador.length() > 0) {
			s = s.substring(separador.length());
		}
		return s;
	}
	public static boolean isArray(Object o) {
		
		if (o instanceof Array) {
			return true;
		}
		
		return o.getClass().isArray();
		
	}
	public void splice(int index, int count) {
		for (int i = 0; i < count; i++) {
			this.list.remove(index);
		}
	}
	public void splice(int index, int count, T element) {
		splice(index, count);
		list.add(index, element);
	}

	public static Array<String> from(Set<String> keys) {
		Array<String> list = new Array<>();
		keys.forEach(s -> list.push(s));
		return list;
	}
	
	public static Array<Element> from(NodeList nodes) {
		return nodes.itens.getArray();
	}

	@SuppressWarnings("unchecked")
	public void java_addCast(Object o) {
		T t = (T) o;
		push(t);
	}

	@SuppressWarnings("unchecked")
	public void setClasse(Class<T> classe) {

		this.classe = classe;

		Lst<?> l = list;
		Lst<T> map = l.map(o -> {
			if (o instanceof MapSO) {
				return ((MapSO)o).as(classe);
			}
			return (T) o;
		});

		list.clear();
		list.addAll(map);

	}

	@Override
	public String toString() {
		return list.toString();
	}

	@SafeVarargs
	public static <X> Array<X> build(X... array) {
		return Itens.build(array).getArray();
	}

	/* este metodo existe para que o converssor possa converter facilmente para [] */
	public T array(int index) {
		if (this.list.isEmpty()) {
			return null;
		}
		return this.list.get(index);
	}

	/*
	@Deprecated
	utilize o metodo array ao invés desse
	public T get(int index) {
	*/
	
	/* este metodo existe para que o converssor possa converter facilmente para [] = x */
	public void arraySet(int i, T o) {
		list.remove(i);
		list.add(i, o);
	}

	public void print() {
		list.print();
	}

	public static Array<String> split(String s, String sub) {
		throw new NaoImplementadoException();
	}
	
}
