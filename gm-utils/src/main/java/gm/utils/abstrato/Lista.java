package gm.utils.abstrato;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.classes.UClass;
import gm.utils.comum.UList;
import gm.utils.exception.UException;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringParse;

@Getter @Setter
public class Lista<T> extends ArrayList<T> {

	private static final String SEPARADOR_SEGURO = "|=(-)=|";

	private boolean bloqueada = false;
	
	public Lista() {}

	static final long serialVersionUID = 1;
	boolean aceitaRepetidos = true;

	protected final Lista<T> remove_(int index, int quantidade) {

		Lista<T> list = newInstance();

		while (quantidade > 0) {
			quantidade--;
			list.add(remove(index));
		}

		return list;

	}
	@SuppressWarnings("unchecked")
	public <X extends Lista<T>> X copy(){
		Lista<T> list = newInstance();
		list.addAll(this);
		return (X) list;
	}
	@SuppressWarnings("unchecked")
	Lista<T> newInstance(){
		return UClass.newInstance( this.getClass() );
	}

	protected final Lista<T> removeLast_(int quantidade) {

		Lista<T> list = newInstance();

		while (quantidade > 0) {
			quantidade--;
			list.add(0, removeLast());
		}

		return list;

	}

	public T getLast() {
		return get(size() - 1);
	}

	public T getPenultimo() {
		return get(size() - 2);
	}

	public T removeLast() {
		return remove(size() - 1);
	}

	@Override
	public boolean addAll(Collection<? extends T> list) {
		if (UList.isEmpty(list)) {
			return false;
		}
		if (!aceitaRepetidos) {
			addIfNotContains(list);
			return true;
		}
		for (T t : list) {
			add(t);
		}
		return true;
	}

	public void addIfNotContains(Collection<? extends T> list) {
		for (T o : list) {
			addIfNotContains(o);
		}
	}

	public Lista<T> addIfNotContains(T s) {
		if (!contains(s)) {
			add(s);
		}
		return this;
	}

	public int codePoint(int index){
		return get(index).toString().codePointAt(0);
	}

	@Override
	public T get(int index) {
		
		int x = size();
		
		while (index < 0) {
			index += x;
		}

		while (index > x) {
			index -= x;
		}
		
		return super.get(index);

	}

	public Lista<T> get(int index, int count) {
		Lista<T> list = newInstance();
		for (int i = index; i < index + count; i++) {
			list.add( get(i) );
		}
		return list;
	}

	public final String concat() {
		String s = "";
		for (T t : this) {
			s += t;
		}
		return s;
	}

	public void add(Collection<T> keySet) {
		for (T x : keySet) {
			add(x);
		}
	}

	public final String toString(String separador) {
		if (isEmpty()) {
			return "";
		}
		String s = stream().map(String::valueOf).collect(Collectors.joining(SEPARADOR_SEGURO));
		s = s.replace("\n", SEPARADOR_SEGURO);
		return s.replace(SEPARADOR_SEGURO, separador);
	}

	@Override
	public boolean equals(Object o) {
		return eq(o);
	}

	@Override
	public int hashCode() {
		int prime = 31;
		int result = super.hashCode();
		return prime * result + (aceitaRepetidos ? 1231 : 1237);
	}

	public boolean eq(Object o) {
		if (o == null) {
			return false;
		}
		if ( super.equals(o) ) {
			return true;
		}
		return StringCompare.eq(StringParse.get(o), StringParse.get(this));
	}

	public boolean equals(Lista<T> o) {
		if (eq(o)) {
			return true;
		}
		if (o == null) {
			return false;
		}
		return o.toString("").equals(toString(""));
	}

	private void checaBloqueio() {
		if (bloqueada) {
			throw UException.runtime("A lista está bloqueada!");
		}
	}

	@Override
	public T remove(int index) {
		checaBloqueio();
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		checaBloqueio();
		return super.remove(o);
	}

	@Override
	public boolean add(T e) {
		checaBloqueio();
		return super.add(e);
	}

	@Override
	public void add(int index, T element) {
		checaBloqueio();
		super.add(index, element);
	}

	@Override
	public boolean addAll(int index, Collection<? extends T> c) {
		checaBloqueio();
		return super.addAll(index, c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		checaBloqueio();
		return super.removeAll(c);
	}

	@Override
	public boolean removeIf(Predicate<? super T> filter) {
		checaBloqueio();
		return super.removeIf(filter);
	}

	@Override
	protected void removeRange(int fromIndex, int toIndex) {
		checaBloqueio();
		super.removeRange(fromIndex, toIndex);
	}

	public boolean contains(Predicate<T> predicate) {
		return !filter(predicate).isEmpty();
	}

	public List<T> filter(Predicate<T> predicate) {
		return stream().filter(predicate).collect(Collectors.toList());
	}
	
	public List<T> removeAndGet(Predicate<T> predicate) {
		List<T> list = filter(predicate);
		removeAll(list);
		return list;
	}

	public T unique(Predicate<T> predicate) {
		List<T> filter = filter(predicate);
		if (filter.isEmpty()) {
			return null;
		}
		if (filter.size() > 1) {
			throw UException.runtime("O filtro retornou + de 1 resultado");
		}
		return filter.get(0);
	}

	public T uniqueObrig(Predicate<T> predicate) {
		T o = unique(predicate);
		if (o == null) {
			throw new RuntimeException();
		}
		return o;
	}

	public boolean some(Predicate<T> predicate) {
		return !filter(predicate).isEmpty();
	}

}
