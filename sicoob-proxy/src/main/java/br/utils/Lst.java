package br.utils;

import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import br.com.sicoob.concessao.bndes.utils.JSonUtils;
import br.utils.ints.IntegerCompare;
import br.utils.lambda.F1;
import br.utils.lambda.F2;
import br.utils.strings.StringCompare;

public class Lst<T> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1L;
	
	private transient boolean ignoreRepeateds = false;
	private transient boolean ignoreNulls = false;
	private transient boolean throwNulls = false;
	private transient boolean throwRepeateds = false;
	private transient boolean bloqueada;
	
	public Lst() {}
	
	public Lst(T[] list) {
		this();
		if (list != null) {
			for (T t : list) {
				add(t);
			}
		}
	}
	
	@SafeVarargs
	public Lst(T o, T... itens) {
		this();
		add(o);
		Collections.addAll(this, itens);
	}
	
	public Lst(Collection<T> list) {
		this();
		if (list != null) {
			addAll(list);
		}
	}
	
	/*
	 * - simplifica a forma de filtrar uma lista - torna igual ao javaScript
	 */
	public Lst<T> filter(Predicate<T> predicate) {
		return new Lst<>(stream().filter(predicate).collect(Collectors.toList()));
	}
	
	public int sumInt(F1<T, Integer> func) {
		int o = 0;
		for (T i : this) {
			Integer v = func.call(i);
			if (v != null) {
				o += v;
			}
		}
		return o;
	}
	
	public Integer maxInt(F1<T, Integer> func) {
		
		if (isEmpty()) {
			return null;
		}
		
		Lst<Integer> ints = map(i -> func.call(i));
		ints.removeNulls();
		
		if (ints.isEmpty()) {
			return null;
		}
		
		ints.sortInt(i -> i);
		return ints.getLast();
		
	}
	
	public BigDecimal sumBigDecimal(F1<T, BigDecimal> func) {
		BigDecimal o = BigDecimal.ZERO;
		for (T i : this) {
			BigDecimal v = func.call(i);
			if (v != null) {
				o = o.add(v);
			}
		}
		return o;
	}
	
	public int count(Predicate<T> predicate) {
		return filter(predicate).size();
	}
	
	public Lst<T> extract(Predicate<T> predicate) {
		Lst<T> itens = filter(predicate);
		removeAll(itens);
		return itens;
	}
	
	public T extractUnique(Predicate<T> predicate) {
		return extract(predicate).unique();
	}
	
	public boolean anyMatch(Predicate<T> predicate) {
		return stream().anyMatch(predicate);
	}
	
	public boolean anyMatch(Collection<T> itens) {
		return anyMatch(itens::contains);
	}
	
	public T findFirst(Predicate<T> predicate) {
		
		for (T o : this) {
			if (predicate.test(o)) {
				return o;
			}
		}
		
		return null;
		
	}
	
	@Override @Deprecated
	public Stream<T> stream() {
		return super.stream();
	}
	
	public T unique(Predicate<T> predicate) {
		return filter(predicate).unique();
	}
	
	public T unique() {

		if (isEmpty()) {
			return null;
		}
		
		if (size() == 1) {
			return get(0);
		}
		
		throw DevException.build("A lista retornou + de 1 resultado (2)");
		
	}
	
	public <TT> Lst<TT> map(F1<T, TT> func) {
		return new Lst<>(stream().map(i -> func.call(i)).collect(Collectors.toList()));
	}
	
	public Lst<T> mapSelf(F1<T, T> func) {
		Lst<T> lst = map(func);
		clear();
		addAll(lst);
		return this;
		
	}
	
	public final String joinString(String separador) {
		
		if (isEmpty()) {
			return "";
		}
		
		if (separador == null || separador.contentEquals("")) {

			String s = "";
			
			for (T item : this) {
				s += String.valueOf(item);
			}
			
			return s;
			
		}
		
		String s = "";
		
		for (T item : this) {
			s += separador + String.valueOf(item);
		}
		
		return s.substring(separador.length());
		
	}
	
	public final String joinStringE() {

		if (isEmpty()) {
			return "";
		}
		
		if (size() == 1) {
			return String.valueOf(get(0));
		}
		
		Lst<T> list = copy();
		
		T last = list.removeLast();
		
		return list.joinString(", ") + " e " + String.valueOf(last);
		
	}
	
	public Lst<T> newLst() {
		return new Lst<>();
	}
	
	public Lst<T> copy() {
		Lst<T> lst = newLst();
		lst.addAll(this);
		lst.ignoreNulls = ignoreNulls;
		lst.ignoreRepeateds = ignoreRepeateds;
		lst.throwNulls = throwNulls;
		lst.throwRepeateds = throwRepeateds;
		return lst;
	}
	
	private boolean continueAdd(T e) {
		
		checaBloqueio();
		
		if (e == null) {
			if (ignoreNulls) {
				return false;
			} else if (throwNulls) {
				throw new NullPointerException();
			}
		}
		
		if (contains(e)) {
			
			if (ignoreRepeateds) {
				return false;
			} else if (throwRepeateds) {
				throw new RuntimeException("O item já consta na lista: " + e);
			}
			
		}
		
		return true;
		
	}
	
	@Override
	public boolean add(T e) {
		if (continueAdd(e)) {
			boolean added = super.add(e);
			if (added) {
				afterAdded(e);
			}
			return added;
		} else {
			return false;
		}
	}
	
	protected void afterAdded(T e) {
		
	}
	
	@Override
	public void add(int index, T e) {
		if (continueAdd(e)) {
			super.add(index, e);
		}
	}
	
	private void checaBloqueio() {
		if (bloqueada) {
			throw new RuntimeException("lista bloqueada");
		}
	}
	
	public Lst<T> ignoreNulls() {
		ignoreNulls = true;
		removeIf(i -> i == null);
		return this;
	}
	
	public Lst<T> ignoreRepeateds() {
		ignoreRepeateds = true;
		return this;
	}
	
	public Lst<T> throwNulls() {
		throwNulls = true;
		return this;
	}
	
	public Lst<T> throwRepeateds() {
		throwRepeateds = true;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public <TT extends Comparable<TT>> Lst<T> sort(F1<T, Comparable<?>> func) {
		
		sort((a, b) -> {
			
			if (a == b) {
				return 0;
			}
			
			if (a == null) {
				return -1;
			}

			if (b == null) {
				return 1;
			}
			
			TT va = (TT) func.call(a);
			TT vb = (TT) func.call(b);
			
			if (va == vb) {
				return 0;
			}
			
			if (va == null) {
				return -1;
			}

			if (vb == null) {
				return 1;
			}
			
			return va.compareTo(vb);
			
		});
		
		return this;
		
	}
	
	public Lst<T> sortInt(F1<T, Integer> func) {
		sort((a, b) -> IntegerCompare.compare(func.call(a), func.call(b)));
		return this;
	}
	
	public Lst<T> sortString(F1<T, String> func) {
		sort((a, b) -> StringCompare.compare(func.call(a), func.call(b)));
		return this;
	}

	public Lst<T> sortTimestamp(F1<T, java.sql.Timestamp> func) {
		sort((a, b) -> Compare.compare(func.call(a), func.call(b)));
		return this;
	}
	
	public Lst<T> sortLocalDateTime(F1<T, LocalDateTime> func) {
		sort((a, b) -> Compare.compare(func.call(a), func.call(b)));
		return this;
	}
	
	@Override
	public boolean addAll(Collection<? extends T> c) {

		if (c == null || c.isEmpty()) {
			return false;
		}
		
		boolean result = false;
		
		for (T t : c) {
			result = add(t) || result;
		}
		
		return result;
		
	}
	
	public <TT> Lst<TT> distinct(F1<T, TT> func, F2<TT, TT, Boolean> compare) {

		Lst<TT> map = map(func);

		Lst<TT> rs = new Lst<>();
		for (TT i : map) {
			if (!rs.anyMatch(o -> compare.call(o, i))) {
				rs.add(i);
			}
		}

		return rs;

	}
	
	public <TT> Lst<TT> distinct(F1<T, TT> func) {

		return distinct(func, (a, b) -> {

			if (a == b) {
				return true;
			}

			if (a == null || b == null) {
				return false;
			}

			return a.equals(b);

		});

	}
	
	public Lst<T> distincts() {
		return distinct(o -> o);
	}

	public <TT> Lst<TT> distincts(F1<T, TT> func) {
		return distinct(func, Equals::is);
	}	

	public final T getFirst() {
		if (isEmpty()) {
			return null;
		}
		return get(0);
	}

	public final T getLast() {
		if (isEmpty()) {
			return null;
		}
		return get(size() - 1);
	}
	
	public void print() {
		for (T o : this) {
			Print.ln(o);
		}
	}

	public void printCount() {
		Print.ln("\t> " + size() + " rows");		
	}

	public T removeLast() {
		if (isEmpty()) {
			return null;
		}
		T o = getLast();
		remove(size() - 1);
		return o;
	}
	
	public void lock() {
		bloqueada = true;
	}

	public Lst<T> removeNulls() {
		removeIf(i -> i == null);
		return this;
	}

	public Lst<T> invertOrdem() {
		Lst<T> list = copy();
		clear();
		while (!list.isEmpty()) {
			add(list.removeLast());
		}
		return this;
	}

	public void save(String file) {
        try {
            Path path = Paths.get(file);
            Files.createDirectories(path.getParent());
			Files.write(path, map(i -> i.toString()));
            Print.ln("arquivo salvo " + file);
        } catch (Exception e) {
        	throw DevException.build(e);
        }
	}
	
	public Set<T> asSet() {
		return new HashSet<>(this);
	}

	public boolean addIfNotContains(T o) {
		if (contains(o)) {
			return false;
		} else {
			return add(o);
		}
	}

	public static <TT> Lst<TT> of(Collection<TT> list) {
		return new Lst<>(list);
	}

	public String toJson() {
		return JSonUtils.toJson(this);
	}

}
