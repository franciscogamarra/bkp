package src.commom.utils.array;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.lambda.F1;
import gm.utils.lambda.F2;
import gm.utils.lambda.P1;
import js.array.Array;
import js.support.console;
import react.support.ReactNode;
import src.commom.utils.comum.Box;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;
import src.commom.utils.object.Obj;
import src.commom.utils.object.ObjJs;
import src.commom.utils.string.StringBox;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringIs;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringReplace;

public class Itens<T> extends ObjJs {
	
	private final Array<T> valueArray = new Array<>();
	public F2<T, T, Boolean> equalsComparator;
	
	public Itens<T> add(T o) {
		valueArray.push(o);
		return this;
	}
	
	public boolean replace(T a, T b) {
		
		if (a == b) {
			return false;
		}
		
		int i = indexOf(a);
		
		if (i == -1) {
			return false;
		}
		
		addi(b, i+1);
		remove(i);
		
		return true;
		
	}
	
	public Itens<T> addi(T o, int index) {
		if (isEmpty() || index >= size()) {
			return add(o);
		}
		valueArray.splice(index, 0, o);
		return this;
	}
	
	public boolean addIfNotContains(T o) {
		if (Null.is(o) || contains(o)) {
			return false;
		} else {
			add(o);
			return true;
		}
	}
	
	public boolean addIfNotNull(T o) {
		if (Null.is(o)) {
			return false;
		} else {
			add(o);
			return true;
		}
	}
	
	public boolean check(T o) {
		return addIfNotContains(o) || !removeObj(o);
	}
	
	public Itens<T> removeIf(F1<T, Boolean> predicate) {
		removeAndGetIf(predicate);
		return this;
	}
	
	public Itens<T> removeAndGetIf(F1<T, Boolean> predicate) {

		Itens<T> filter = filter(predicate);
		
		if (filter.size() == size()) {
			clear();
			return filter;
		}
		
		Itens<T> itens = new Itens<>();
		
		while (!filter.isEmpty()) {
			T o = filter.removeFirst();
			removeObj(o);
			itens.add(o);
		}
		
		return itens;
		
	}
	
	public Itens<T> removeAll(Itens<T> list) {
		return removeIf(o -> list.contains(o));
	}

	public Itens<T> filter(F1<T, Boolean> predicate) {
		Itens<T> list = new Itens<>();
		list.addArray(valueArray.filter(predicate));
		return list;
	}
	
	public int somaInt(F1<T, Integer> func) {
		
		Box<Integer> box = new Box<>();
		box.set(0);
		
		forEach(i -> {
			Integer x = func.call(i);
			if (!Null.is(x)) {
				box.set(box.get() + x);
			}
		});
		
		return box.get();
		
		
	}
	
	public int count(F1<T, Boolean> predicate) {
		return filter(predicate).size();
	}
	
	public Itens<T> addArray(Array<T> list) {
		if (!Null.is(list)) {
			if (Array.isArray(list)) {
				list.forEach(i -> add(i));
			} else {
				throw new Error("Não é um array " + list);
			}
		}
		return this;
	}
	
	public Itens<T> addAll(Itens<T> itens) {
		return addArray(itens.valueArray);
	}
	
	public boolean contains(T o) {
		
		if (indexOf(o) > -1) {
			return true;
		}
		
		if (!Null.is(equalsComparator)) {
			return anyMatch(i -> equalsComparator.call(o, i));
		}
		
		return false;
		
	}
	
	public boolean anyMatch(F1<T, Boolean> predicate) {
		return !filter(predicate).isEmpty();
	}
	
	public boolean isEmpty() {
		return size() == 0;
	}

	public T removeFirstNotNull() {
		@PodeSerNull T o = removeFirst();
		if (o == null) {
			throw new Error("o == null");
		} else {
			return o;
		}
	}

	public boolean removeObj(T o) {
		int index = indexOf(o);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}
	
	@PodeSerNull
	public T removeFirst() {
		return orNull(valueArray.shift());
	}
	
	@PodeSerNull
	public T removeLast() {
		return orNull(valueArray.pop());
	}
	
	public Itens<T> inverteOrdem() {
		Itens<T> copy = copy();
		clear();
		while (!copy.isEmpty()) {
			add(copy.removeLastNotNull());
		}
		return this;
	}
	
	public T removeLastNotNull() {
		@PodeSerNull T o = removeLast();
		if (Null.is(o)) {
			throw new Error("o == null");
		} else {
			return o;
		}
	}
	
	public Itens<T> removeNulls() {
		return removeIf(i -> Null.is(i));
	}

	public int indexOf(T o) {
		return valueArray.indexOf(o);
	}

	public T remove(int index) {
		T o = get(index);
		valueArray.splice(index, 1);
		return o;
	}

	@PodeSerNull
	public T getFirst() {
		if (isEmpty()) {
			return null;
		} else {
			return get(0);
		}
	}
	
	public T get(int index) {
		while (index < 0) {
			index = index + size();
		}
		return valueArray.array(index);
	}

	public Itens<T> forEach(P1<T> action) {
		valueArray.forEach(action);
		return this;
	}

	public Itens<T> forEachInvertido(P1<T> action) {
		int i = size() - 1;
		while (i > -1) {
			action.call(get(i));
			i--;
		}
		return this;
	}
	
	public <TT> Itens<TT> map(F1<T,TT> func) {
		Array<TT> array = valueArray.map(func);
		Itens<TT> list = new Itens<>();
		list.addArray(array);
		return list;
	}
	
	public <TT> Itens<TT> mapi(F2<T,Integer,TT> func) {
		Array<TT> array = valueArray.map(func);
		Itens<TT> list = new Itens<>();
		list.addArray(array);
		return list;
	}
	
	public int size() {
		return valueArray.lengthArray();
	}

	public T getLast() {
		if (isEmpty()) {
			return null;
		}
		return get(size() - 1);
	}

	public T getBefore(T o) {
		
		if (!contains(o)) {
			throw new Error("Não localizado na lista: " + o);
		}
		
		if (get(0) == o) {
			return getLast();
		}
		
		return get(indexOf(o) - 1);
		
	}

	public T getAfter(T o) {
		
		if (!contains(o)) {
			throw new Error("Não localizado na lista: " + o);
		}
		
		if (getLast() == o) {
			return get(0);
		}
		
		return get(indexOf(o) + 1);
		
	}
	
	public String joinString(String separator) {
		
		if (isEmpty()) {
			return "";
		}
		
		if (Null.is(separator)) {
			separator = "";
		}
		
		return valueArray.join(separator);
	}
	
	public String concatStrings(F1<T,String> func, String separador) {
		StringBox box = new StringBox("");
		forEach(o -> box.add(separador + func.call(o)));
		return box.get().substring(separador.length());
	}
	
	public Itens<T> copy() {
		Itens<T> list = new Itens<>();
		forEach(i -> list.add(i));
		list.equalsComparator = equalsComparator;
		return list;
	}

	public Itens<T> concat(Itens<T> outro) {
		Itens<T> list = copy();
		outro.forEach(i -> list.add(i));
		return list;
	}
	
	public Array<T> getArray() {
		return valueArray.concat(new Array<>());
	}
	
	public Itens<T> clear() {
		while (!isEmpty()) {
			removeLast();
		}
		return this;
	}
	
//	public static <TT> Itens<TT> build(Array<TT> array) {
//		Itens<TT> itens = new Itens<>();
//		itens.addArray(array);
//		return itens;
//	}
	
	@PodeSerNull
	public T byId(String id, F1<T, String> getId) {
		return unique(o -> StringCompare.eq(getId.call(o), id));
	}

	public boolean existsId(String id, F1<T, String> getId) {
		return !Null.is(byId(id, getId));
	}
	
	@PodeSerNull
	public T unique(F1<T, Boolean> predicate) {
		Itens<T> itens = filter(predicate);
		if (itens.isEmpty()) {
			return null;
		}
		if (itens.size() > 1) {
			throw new Error("O filtro retornou + de 1 resultado");
		} else {
			return itens.get(0);
		}
	}
	
	public T uniqueObrig(F1<T, Boolean> predicate) {
		@PodeSerNull T o = unique(predicate);
		if (o == null) {
			throw new Error("O filtro não retornou resultados");
		}
		return o;
	}

	public Itens<T> sort(F2<T,T,Integer> comparator) {
		valueArray.sort(comparator);
		return this;
	}

	public Itens<T> sortInt(F1<T,Integer> comparator) {
		return sort((a,b) -> IntegerCompare.compare(comparator.call(a), comparator.call(b)));
	}
	
	public static <X> Itens<X> novo(Array<X> array) {
		Itens<X> o = new Itens<>();
		o.addArray(array);
		return o ;
	}
	
	@Override
	protected String toJsonImpl() {
		
		/* um array nao possui o metodo toJSON em js */
		if (isEmpty()) {
			return "[]";
		}
		if (StringIs.is(get(0))) {
			Itens<String> lst = map(i -> StringParse.get(i));
			return "[" + lst.map(i -> "\"" + StringReplace.exec(i, "\"", "\\\"") + "\"").joinString(", ") + "]";
		} else {
			return "[" + map(i -> StringParse.get(i)).joinString(", ") + "]";
		}
		
	}
	
	public Array<ReactNode> nodes(F1<T,ReactNode> func) {
		return map(func).valueArray;
	}

	public Array<ReactNode> nodesi(F2<T,Integer,ReactNode> func) {
		return mapi(func).valueArray;
	}
	
	public Itens<T> getFirsts(int qtd) {
		
		Itens<T> list = new Itens<>();

		int index = 0;
		
		while (index <= size()-1 && list.size() < qtd) {
			list.add(get(index));
			index++;
		}
		
		return list;
		
	}
	
	public Itens<T> ignoreFirsts(int qtd) {
		
		if (qtd >= size()) {
			return new Itens<>();
		}

		Itens<T> list = copy();

		while (qtd > 0) {
			list.removeFirst();
			qtd--;
		}
		
		return list;
		
	}
	
	public Itens<T> removeAndGetFirsts(int qtd) {
		
		Itens<T> list = new Itens<>();
		
		while (!isEmpty() && qtd > 0) {
			list.add(removeFirst());
			qtd--;
		}
		
		return list;
		
	}
	
	public Itens<T> manterSomenteOsPrimeiros(int qtd) {
		Itens<T> itens = removeAndGetFirsts(qtd);
		clear();
		addAll(itens);
		return this;
	}
	
	@Override
	public void print() {
		console.log(valueArray);
	}

	@SafeVarargs
	public static <X> Itens<X> build(X... array) {
		
		if (!Array.isArray(array)) {
			throw new Error("nao eh um array");
		}
		
		Itens<X> lst = new Itens<>();
		for (int i = 0; i < array.length; i++) {
			lst.add(array[i]);
		}
		return lst;
	}
	
	private static final F2<String, String, Boolean> equalsStringComparator = (a, b) -> StringCompare.eq(a, b);
	
	public static Itens<String> buildString(String... array) {
		
		if (!Array.isArray(array)) {
			throw new Error("nao eh um array");
		}
		
		Itens<String> lst = new Itens<>();
		lst.equalsComparator = equalsStringComparator;
		for (int i = 0; i < array.length; i++) {
			lst.add(array[i]);
		}
		return lst;
		
	}

	public T find(String id) {
		return filter(item -> StringCompare.eq(Obj.get(item, "id"), id)).getFirst();
	}

	public Itens<T> removeSequenciasIguais(F2<T, T, Boolean> compare) {
		Itens<T> itens = new Itens<>();
		forEach(i -> {
			if (!compare.call(i, itens.getLast())) {
				itens.add(i);
			}
		});
		clear();
		addAll(itens);
		return this;
	}
	
	public Itens<T> distincts() {
		Itens<T> itens = new Itens<>();
		forEach(i -> {
			if (!itens.contains(i)) {
				itens.add(i);
			}
		});
		return itens;
	}
	
	@IgnorarDaquiPraBaixo
	
	public Itens<T> addArray(T[] list) {
		return addArray(new Array<>(list));
	}

}