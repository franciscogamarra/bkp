package src.commom.utils.array;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.utils.lambda.F1;
import gm.utils.lambda.F2;
import gm.utils.lambda.P1;
import gm.utils.lambda.P2;
import js.UNative;
import js.array.Array;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.object.Null;
import src.commom.utils.object.ObjJs;
import src.commom.utils.string.StringBox;
import src.commom.utils.string.StringIs;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringReplace;

public class ArrayLst<T> extends ObjJs {

	private final Array<T> valueArray = new Array<>();

	public ArrayLst<T> add(T o) {
		valueArray.push(o);
		return this;
	}
	
	public ArrayLst<T> addi(T o, Integer index) {
		valueArray.splice(index, 0, o);
		return this;
	}

	public ArrayLst<T> addAll(ArrayLst<T> list) {
		if (!Null.is(list)) {
			list.forEach(o -> this.push(o));
		}
		return this;
	}
	
	public ArrayLst<T> addArray(Array<T> list) {
		list.forEach(i -> add(i));
		return this;
	}

	public ArrayLst<T> filter(F1<T, Boolean> predicate) {
		ArrayLst<T> list = new ArrayLst<>();
		list.addArray(valueArray.filter(predicate));
		return list;
	}

	public <RESULT> RESULT reduce(F2<RESULT, T, RESULT> func, RESULT startValue) {
		return valueArray.reduce(func, startValue);
	}

	public boolean anyMatch(F1<T, Boolean> predicate) {
		return !filter(predicate).isEmpty();
	}

	public Integer somaInt(F1<T, Integer> func) {
		return reduce((o,t) -> {
			Integer x = func.call(t);
			if (!Null.is(x)) {
				o += x;
			}
			return o;
		}, 0);
	}

	public boolean some(F1<T, Boolean> predicate) {
		return anyMatch(predicate);
	}

	public boolean isEmpty() {
		return IntegerCompare.isZero(valueArray.lengthArray());
	}

	public ArrayLst<T> clear() {
		while (!isEmpty()) {
			removeLast();
		}
		return this;
	}
	
	@PodeSerNull
	public T removeLast() {
		return orNull(valueArray.pop());
	}

	@PodeSerNull
	public T removeFirst() {
		return orNull(valueArray.shift());
	}

	public T removeLastNotNull() {
		@PodeSerNull T o = removeLast();
		if (o == null) {
			throw new Error("o == null");
		} else {
			return o;
		}
	}

	public T removeFirstNotNull() {
		@PodeSerNull T o = removeFirst();
		if (o == null) {
			throw new Error("o == null");
		} else {
			return o;
		}
	}
	
	public ArrayLst<T> removeIf(F1<T, Boolean> predicate) {
		ArrayLst<T> filter = filter(predicate);
		while (!filter.isEmpty()) {
			T o = filter.removeFirstNotNull();
			removeObj(o);
		}
		return this;
	}

	public ArrayLst<T> removeAll(ArrayLst<T> list) {
		return removeIf(o -> list.contains(o));
	}

	public boolean removeObj(T o) {
		int index = indexOf(o);
		if (index == -1) {
			return false;
		}
		remove(index);
		return true;
	}

	public T remove(int index) {
		T o = get(index);
		valueArray.splice(index, 1);
		return o;
	}

	public T get(int index) {
		return valueArray.array(index);
	}

	public int indexOf(T o) {
		return valueArray.indexOf(o);
	}

	public ArrayLst<T> concat(ArrayLst<T> other) {
		int va = size();
		int vb = other.size();
		ArrayLst<T> a = new ArrayLst<>();
		a.addArray(valueArray.concat(other.valueArray));
		int vc = a.size();
		if (IntegerCompare.ne(va+vb, vc)) {
			throw new Error("O concat nao funcionou");
		}
		return a;
	}

	public ArrayLst<T> forEach(P1<T> action) {
		valueArray.forEach(action);
		return this;
	}

	public ArrayLst<T> forEachI(P2<T, Integer> action) {
		valueArray.forEach(action);
		return this;
	}

	public ArrayLst<T> copy() {
		ArrayLst<T> list = new ArrayLst<>();
		forEach(o -> list.add(o));
		return list;
	}

	public void inverteOrdem() {
		ArrayLst<T> copy = copy();
		clear();
		while (!copy.isEmpty()) {
			add(copy.removeLastNotNull());
		}
	}

	public boolean contains(T o) {
		return indexOf(o) > -1;
	}

	public String join(String separator) {
		return valueArray.join(separator);
	}

	public int size() {
		return valueArray.lengthArray();
	}

	public T uniqueObrig(F1<T, Boolean> predicate) {
		@PodeSerNull T o = unique(predicate);
		if (o == null) {
			throw new Error("O filtro não retornou resultados");
		}
		return o;
	}

	@PodeSerNull
	public T unique(F1<T, Boolean> predicate) {
		ArrayLst<T> itens = filter(predicate);
		if (itens.isEmpty()) {
			return null;
		}
		if (itens.size() > 1) {
			throw new Error("O filtro retornou + de 1 resultado");
		} else {
			return itens.get(0);
		}
	}

	public ArrayLst<T> sort(F2<T,T,Integer> comparator) {
		valueArray.sort(comparator);
		return this;
	}

	@PodeSerNull
	public T pop() {
		return removeLast();
	}

	@PodeSerNull
	public T shift() {
		return removeFirst();
	}

	@Override
	public String toJsonImpl() {
		
		/* um array nao possui o metodo toJSON em js */
		if (isEmpty()) {
			return "[]";
		}
		if (StringIs.is(get(0))) {
			ArrayLst<String> lst = map(i -> StringParse.get(i));
			return "[" + lst.map(i -> "\"" + StringReplace.exec(i, "\"", "\\\"") + "\"").join(", ") + "]";
		} else {
			return "[" + map(i -> StringParse.get(i)).join(", ") + "]";
		}
		
	}

	@PodeSerNull
	public T byId(Integer id, F1<T, Integer> getId) {
		return unique(o -> IntegerCompare.eq(getId.call(o), id));
	}

	public boolean existsId(Integer id, F1<T, Integer> getId) {
		return !Null.is(byId(id, getId));
	}

	public ArrayLst<T> push(T o) {
		return add(o);
	}

	public <TT> ArrayLst<TT> map(F1<T,TT> func) {
		Array<TT> array = valueArray.map(func);
		ArrayLst<TT> list = new ArrayLst<>();
		list.addArray(array);
		return list;
	}

	public <TT> ArrayLst<TT> mapi(F2<T,Integer,TT> func) {
		Array<TT> array = valueArray.map(func);
		ArrayLst<TT> list = new ArrayLst<>();
		list.addArray(array);
		return list;
	}

	public boolean addIfNotContains(T o) {
		if (Null.is(o) || contains(o)) {
			return false;
		}
		add(o);
		return true;
	}

	public Array<T> getArray() {
		return copy().valueArray;
	}

	@PodeSerNull
	public T getSafe(int index) {
		if (isEmpty()) {
			return null;
		}
		if (size() < index +1) {
			return null;
		} else {
			return get(index);
		}
	}

	public T getLast() {
		return get(size()-1);
	}

	public <RESULT> ArrayLst<RESULT> distinct(F1<T,RESULT> func) {
		ArrayLst<RESULT> list = new ArrayLst<>();
		valueArray.forEach(o -> list.addIfNotContains(func.call(o)));
		return list;
	}

	public String concatStrings(F1<T,String> func, String separador) {
		StringBox box = new StringBox("");
		forEach(o -> box.add(separador + func.call(o)));
		return box.get().substring(separador.length());
	}

	public ArrayLst<T> befores(T o) {

		int index = indexOf(o);

		int i = 0;

		ArrayLst<T> lst = new ArrayLst<>();

		while (i < index) {
			lst.add(get(i));
			i++;
		}

		return lst;

	}

	public ArrayLst<T> afters(T o) {

		int i = indexOf(o) + 1;

		ArrayLst<T> lst = new ArrayLst<>();

		while (i < size()) {
			lst.add(get(i));
			i++;
		}

		return lst;

	}

	@PodeSerNull
	public T after(T o) {

		int i = indexOfObrig(o) + 1;

		if (i == size()) {
			return null;
		}
		return get(i);

	}

	@PodeSerNull
	public T before(T o) {

		int i = indexOfObrig(o) - 1;

		if (i == -1) {
			return null;
		}
		return get(i);

	}

	private int indexOfObrig(T o) {

		int i = indexOf(o);

		if (i == -1) {
			throw new Error("Objeto nao consta na lista");
		}
		return i;

	}

	public void addBefore(T existente, T novo) {
		int i = indexOfObrig(existente);
		addi(novo, i);
	}

	public void addAfter(T existente, T novo) {
		int i = indexOfObrig(existente);
		addi(novo, i+1);
	}

	@SuppressWarnings("unchecked") @SafeVarargs
	public static <X> ArrayLst<X> build(X... array) {
		ArrayLst<X> lst = new ArrayLst<>();
		Array<?> ar = UNative.asArray2(array);
		ar.forEach(a -> lst.add((X) a));
		return lst;
	}
	
}