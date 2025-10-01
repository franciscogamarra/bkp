package gm.utils.comum;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import com.google.gson.Gson;

import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.lambda.F1;
import gm.utils.lambda.F2;
import gm.utils.lambda.P0;
import gm.utils.lambda.P1;
import gm.utils.lambda.P2;
import gm.utils.map.MapSoFromObject;
import gm.utils.number.Numeric2;
import gm.utils.outros.UThread;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.comum.Print;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.longo.LongCompare;
import src.commom.utils.object.Equals;
import src.commom.utils.object.Null;
import src.commom.utils.shortt.ShortCompare;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringBox;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringEmptyPlus;
import src.commom.utils.string.StringLength;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringTrim;

public class Lst<T> extends ArrayList<T> {
	
	private static final long serialVersionUID = 1L;

	@Getter @Setter
	private boolean bloqueada;

	@Setter
	private Class<T> genericClass;
	
	private transient String charSet = "UTF-8";
	private transient boolean ignoreRepeateds = false;
	private transient boolean ignoreNulls = false;
	private transient boolean throwNulls = false;
	private transient boolean throwRepeateds = false;
	private transient boolean checarExistenciaAoSalvar = true;
	@Setter private transient boolean rtrimOnSave = true;
	public transient F2<T, T, Boolean> equalsComparator;
	private static final F2<String, String, Boolean> equalsStringComparator = (a, b) -> StringCompare.eq(a, b);	
	
	public Lst() {}

	@SafeVarargs
	public Lst(T... itens) {
		Collections.addAll(this, itens);
	}

	public Lst(Class<T> genericClass) {
		this.genericClass = genericClass;
	}

	public Lst(Collection<T> list) {
		addAll(list);
	}

	@SuppressWarnings("unchecked")
	public Class<T> getGenericClass() {

		if (genericClass == null && !isEmpty()) {
			genericClass = (Class<T>) get(0).getClass();
		}

		return genericClass;

	}

	/*
	 - simplifica a forma de filtrar uma lista
	 - torna igual ao javaScript
	 */
	public Lst<T> filter(Predicate<T> predicate) {
		return new Lst<>(stream().filter(predicate).collect(Collectors.toList()));
	}
	
	public Lst<T> extract(Predicate<T> predicate) {
		Lst<T> itens = filter(predicate);
		removeAll(itens);
		return itens;
	}
	
	@SuppressWarnings("unchecked")
	public <TT extends T> Lst<TT> filter(Class<TT> classe) {
		return filter(i -> UClass.a_herda_b(i.getClass(), classe)).map(i -> (TT) i);
	}

	public int indexOf(Predicate<T> predicate) {
		
		T o = getFirst();
		
		if (o == null) {
			return -1;
		}
		
		return indexOf(o);
		
	}
	
	public T getFirst(Predicate<T> predicate) {
		return filter(predicate).getFirst();
	}

	public Lst<T> addResult(P1<T> func) {

		if (getGenericClass() == null) {
			throw new RuntimeException("Para utilizar este metodo eh preciso que o GenericType seja informado");
		}

		T o = UClass.newInstance(genericClass);
		func.call(o);
		add(o);
		return this;
	}

	public boolean isNotEmpty() {
		return !isEmpty();
	}

	private void checaBloqueio() {
		if (bloqueada) {
			throw new RuntimeException("lista bloqueada");
		}
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
		
		beforeAdd(e);
		
		return true;
		
	}

	@Override
	public boolean add(T e) {
		if (continueAdd(e)) {
			boolean add = super.add(e);
			afterChange();
			return add;
		} else {
			return false;
		}
	}
	
	@Override
	public void add(int index, T e) {
		if (continueAdd(e)) {
			super.add(index, e);
		}
	}

//	@SuppressWarnings("unchecked")
	public Lst<T> ad(T o) {
//		for (T t : itens) {
		add(o);
//		}
		return this;
	}

	@Override
	public boolean remove(Object o) {
		checaBloqueio();
		boolean remove = super.remove(o);
		afterChange();
		return remove;
	}

	public Lst<T> remove(Collection<?> c) {
		removeAll(c);
		return this;
	}

	public Lst<T> remove(int index, int count) {
		
		Lst<T> itens = new Lst<>();
		
		while (count > 0) {
			itens.add(get(index));
			remove(index);
			count--;
		}
		
		return itens;
		
	}
	
	@Override
	public T remove(int index) {
		
		if (index < 0) {
			T o = get(index);
			remove(o);
			return o;
		}

		T o = super.remove(index);
		afterChange();
		return o;
		
	}

	@SuppressWarnings("unchecked")
	public Lst<T> remover(T... itens) {
		checaBloqueio();
		for (T t : itens) {
			super.remove(t);
		}
		return this;
	}

	@Override
	public T get(int index) {
		if (index < 0) {
			index += size();
		}
		return super.get(index);
	}

	/*
		evita o erro de IndexOutOfBounds
	 */
	public T getSafe(int index) {

		if (index < 0) {
			index += size();
		}

		if (index >= size()) {
			return null;
		}
		return super.get(index);

	}

	public <TT> Lst<TT> map(F1<T,TT> func) {
		return new Lst<>(stream().map(i -> func.call(i)).collect(Collectors.toList()));
	}

	public <TT> void forEach(F1<T,TT> getItem, P2<TT, Lst<T>> run) {
		Lst<TT> distincts = distinct(getItem, UCompare::equals);
		for (TT tt : distincts) {
			Lst<T> itens = filter(o -> UCompare.equals(getItem.call(o), tt));
			run.call(tt, itens);
		}
	}
	
	public Lst<T> then(Consumer<T> action) {

		if (isEmpty()) {
			return this;
		}
		
		if (size() > 1) {
			throw new RuntimeException("+ de 1 elemento na lista");
		}
		
		action.accept(getFirst());
		
		return this;
		
	}

	public void forEachP(Consumer<T> action) {
		stream().parallel().forEach(action);
	}

	/*
		realiza um groupBy em um map
	 */
	public <TT> Map<TT, Integer> count(F1<T,TT> func) {

		Map<TT, Integer> map = new HashMap<>();

		for (T t : this) {
			TT o = func.call(t);
			Integer value = map.get(o);
			if (value == null) {
				value = 1;
			} else {
				value++;
			}
			map.put(o, value);
		}

		return map;

	}
	
	public void setDistincts() {
		Lst<T> list = distincts();
		clear();
		addAll(list);
	}

	public Lst<T> distincts() {
		return distinct(o -> o);
	}

	public <TT> Lst<TT> distincts(F1<T,TT> func) {
		return distinct(func, UCompare::equals);
	}

	/*
		retorna uma relacao de propriedades distintas
		observar os metodos distintInts e distinctStrings para compreender melhor
	 */
	public <TT> Lst<TT> distinct(F1<T,TT> func, F2<TT,TT,Boolean> compare) {

		Lst<TT> map = map(func);

		Lst<TT> rs = new Lst<>();
		for (TT i : map) {
			if (!rs.anyMatch(o -> compare.call(o, i))) {
				rs.add(i);
			}
		}

		return rs;

	}

	public <TT> Lst<TT> distinct(F1<T,TT> func) {

		return distinct(func, (a,b) -> {

			if (a == b) {
				return true;
			}

			if (a == null || b == null) {
				return false;
			}

			return a.equals(b);

		});

	}

	public Lst<Integer> distinctInts(F1<T,Integer> func) {
		return distinct(func, IntegerCompare::eq);
	}

	public Lst<String> distinctStrings(F1<T,String> func) {
		return distinct(func, StringCompare::eq);
	}

	public Lst<Long> distinctLongs(F1<T,Long> func) {
		return distinct(func, LongCompare::eq);
	}

	public <TT> Lst<TT> map(F2<T,Integer,TT> func) {
		return UList.map(this, func);
	}

	@Override
	public T getFirst() {
		if (isEmpty()) {
			return null;
		}
		return get(0);
	}

	@Override
	public T getLast() {
		if (isEmpty()) {
			return null;
		}
		return get(size()-1);
	}

	public T findFirst(Predicate<T> predicate) {

		for (T o : this) {
			if (predicate.test(o)) {
				return o;
			}
		}

		return null;

	}

	@SuppressWarnings("unchecked")
	public <X extends T> X uniqueObrig(Predicate<T> predicate) {
		return (X) UList.filterUniqueObrig(this, predicate);
	}

	@SuppressWarnings("unchecked")
	public <X extends T> X unique(Predicate<T> predicate) {
		return (X) UList.filterUnique(this, predicate);
	}

	@SuppressWarnings("unchecked")
	public <X extends T> X unique(Predicate<T> predicate, String message) {
		return (X) UList.filterUnique(this, predicate, message);
	}

	@Deprecated // utilizar anyMatch
	public boolean exists(Predicate<T> predicate) {
		return anyMatch(predicate);
	}

	public boolean anyMatch(Predicate<T> predicate) {
		return stream().anyMatch(predicate);
	}
	
	public boolean anyMatchStringIgnoreCase(String s, F1<T,String> get) {
		return anyMatch(i -> StringCompare.eqIgnoreCase(get.call(i), s));
	}

	public <RESULT> RESULT reduce(F2<RESULT,T,RESULT> func, RESULT startValue) {
		return UList.reduce(this, func, startValue);
	}

	public String toString(String separator) {
		return toString(i -> i.toString(), separator);
	}
	
	public String toString(F1<T,String> get, String separator) {

		if (isEmpty()) {
			return "";
		}

		StringBuilder s = new StringBuilder();
		for (T o : this) {
			s.append(separator).append(get.call(o));
		}
		return s.toString().substring(separator.length());
	}

	@Override
	public T removeLast() {
		if (isEmpty()) {
			return null;
		}
		return this.remove(size()-1);
	}

	public Lst<T> removeLasts(int quantidade) {

		Lst<T> list = new Lst<>();

		while (quantidade > 0) {
			quantidade--;
			list.add(0, removeLast());
		}

		return list;

	}

	protected Lst<T> newO() {
		return new Lst<>();
	}

	public Lst<T> copy() {
		Lst<T> list = new Lst<>();
		forEach(i -> list.add(i));
		list.equalsComparator = equalsComparator;
		list.castFromString = castFromString;
		list.checarExistenciaAoSalvar = checarExistenciaAoSalvar;
		list.ignoreNulls = ignoreNulls;
		list.ignoreRepeateds = ignoreRepeateds;
		list.throwNulls = throwNulls;
		list.throwRepeateds = throwRepeateds;
		list.rtrimOnSave = rtrimOnSave;
		return list;
	}
	
	@Override @Deprecated
	public Lst<T> clone() {
		return copy();
	}	

	public Lst<T> concat(Collection<T> lst) {
		Lst<T> copy = copy();
		copy.addAll(lst);
		return copy;
	}

	public BigDecimal soma(F1<T,BigDecimal> func) {
		BigDecimal rs = BigDecimal.ZERO;
		for (T t : this) {
			BigDecimal o = func.call(t);
			if (!Null.is(o)) {
				rs = rs.add(o);
			}
		}
		return rs;
	}

	public int somaInt(F1<T,Integer> func) {
		int rs = 0;
		for (T t : this) {
			Integer o = func.call(t);
			if (!Null.is(o)) {
				rs += o;
			}
		}
		return rs;
	}
	
	public Lst<Integer> mapIntSorted(F1<T,Integer> func) {
		if (isEmpty()) {
			return new Lst<>();
		}
		Lst<Integer> values = map(func).filter(i -> i != null);
		if (values.isEmpty()) {
			return new Lst<>();
		}
		return values.sortInt(i -> i);
	}

	public Integer minInt(F1<T,Integer> func) {
		return mapIntSorted(func).getFirst();
	}
	
	public Integer maxInt(F1<T,Integer> func) {
		return mapIntSorted(func).getLast();
	}

	public Short maxShort(F1<T,Short> func) {
		return mapShortSorted(func).getLast();
	}
	
	public Lst<Short> mapShortSorted(F1<T,Short> func) {
		if (isEmpty()) {
			return new Lst<>();
		}
		Lst<Short> values = map(func).filter(i -> i != null);
		if (values.isEmpty()) {
			return new Lst<>();
		}
		return values.sortShort(i -> i);
	}
	
	
	public double somaDouble(F1<T,Double> func) {
		double rs = 0;
		for (T t : this) {
			Double o = func.call(t);
			if (!Null.is(o)) {
				rs = rs += o;
			}
		}
		return rs;
	}

	public Numeric2 somaNumeric2(F1<T,Numeric2> func) {
		Numeric2 rs = new Numeric2();
		for (T t : this) {
			Numeric2 o = func.call(t);
			if (!Null.is(o)) {
				rs.add(o);
			}
		}
		return rs;
	}

	public void printError() {
		SystemPrint.err("================================================================");
		for (T o : this) {
			SystemPrint.err(o);
		}
		SystemPrint.err("================================================================");
	}
	
	public void print() {

		if (isEmpty()) {
			SystemPrint.ln("[]");
			return;
		}

		SystemPrint.ln("[");
		SystemPrint.margemInc();
		
		try {
			
			Class<?> classe = get(0).getClass();

			if (UType.isPrimitiva(classe)) {
				for (T t : this) {
					Print.ln(t);
				}
				return;
			}

			String s = get(0).toString();

			if (s.contains("@") && s.startsWith(classe.getName() + "@")) {
				for (T t : this) {
					MapSoFromObject.get(t).print();
				}
			} else {
				for (T t : this) {
					SystemPrint.ln(t);
				}
			}			
			
		} finally {
			SystemPrint.margemDec();
			SystemPrint.ln("]");
		}

	}

	public ListString toListString() {
		return toListString(Object::toString);
	}

	public ListString toListString(F1<T,String> func) {
		Lst<String> map = map(func);
		return new ListString(map);
	}

	public Lst<T> sorted(Comparator<T> c) {
		Lst<T> list = copy();
		list.sort(c);
		return list;
	}
	
	public T max(Comparator<T> c) {
		return sorted(c).getLast();
	}
	
	public Lst<T> inverteOrdem() {
		Lst<T> list = copy();
		clear();
		while (list.isNotEmpty()) {
			add(list.removeLast());
		}
		return this;
	}

	public Lst<T> sortShort(F1<T,Short> func) {
		sort((a,b) -> ShortCompare.compare(func.call(a), func.call(b)));
		return this;
	}
	
	public Lst<T> sortShort(F1<T,Short> func, boolean nullsFirst) {
		if (nullsFirst) {
			return sortShort(func);
		} else {
			return sortShort(i -> {
				Short o = func.call(i);
				return o == null ? Short.MAX_VALUE : o;
			});
		}
	}
	
	public Lst<T> sortInt(F1<T,Integer> func) {
		sort((a,b) -> IntegerCompare.compare(func.call(a), func.call(b)));
		return this;
	}

	public Lst<T> sortLong(F1<T,Long> func) {
		sort((a,b) -> LongCompare.compare(func.call(a), func.call(b)));
		return this;
	}

	public Lst<T> sortString(F1<T,String> func) {
		sort((a,b) -> StringCompare.compare(func.call(a), func.call(b)));
		return this;
	}

	public Lst<T> sortStringDesc(F1<T,String> func) {
		sort((a,b) -> StringCompare.compare(func.call(b), func.call(a)));
		return this;
	}
	
	public Lst<T> sortToString() {
		return sortString(i -> i.toString());
	}
	
	public Lst<T> sortByLength(F1<T,String> func) {
		sort((a,b) -> IntegerCompare.compare(StringLength.get(func.call(a)), StringLength.get(func.call(b))));
		return this;
	}
	

	public Lst<T> sortData(F1<T,Data> func) {
		sort((a,b) -> Data.compare(func.call(a), func.call(b)));
		return this;
	}

	public void addIfNotContains(T o) {
		if (!contains(o)) {
			add(o);
		}
	}

	public void addIfNotContains(Lst<T> list) {
		for (T o : list) {
			addIfNotContains(o);
		}
	}

	public Lst<T> primeiros(int count) {

		if (count <= size()) {
			return copy();
		}
		Lst<T> lst = new Lst<>();
		for (int i = 0; i < count; i++) {
			lst.add(get(i));
		}
		return lst;

	}

	public Lst<T> ultimos(int count) {
		return apos(size()-count);
	}

	public Lst<T> apos(int index) {

		int last = size()-1;

		if (index >= last) {
			return new Lst<>();
		}

		Lst<T> lst = new Lst<>();
		for (int i = index+1; i <= last; i++) {
			lst.add(get(i));
		}
		return lst;

	}

	public T before(T o) {

		int index = indexOf(o);

		if (index < 1) {
			return null;
		}
		return getSafe(index-1);

	}

	public T after(T o) {

		int index = indexOf(o);
		
		if (index == -1) {
			throw new RuntimeException("Não localizado " + o);
		}

		if (index >= size()) {
			return null;
		}
		return getSafe(index+1);

	}

	public T removeBefore(T o) {
		if (!contains(o)) {
			throw new RuntimeException("o nao esta na lista: " + o);
		}
		o = before(o);
		remove(o);
		return o;
	}

	public T removeAfter(T o) {
		if (!contains(o)) {
			throw new RuntimeException("o nao esta na lista: " + o);
		}
		o = after(o);
		remove(o);
		return o;
	}
	
	public Lst<T> befores(T o) {

		int index = indexOf(o);

		if (index != -1) {
			return primeiros(index+1);
		}
		return new Lst<>();

	}

	public Lst<T> afters(T o) {

		int index = indexOf(o);

		if (index != -1) {
			return apos(index);
		}
		return new Lst<>();

	}

	public int replace(T de, T para) {

		int count = 0;
		
		while (contains(de)) {
			int index = indexOf(de);
			remove(index);
			add(index, para);
			count++;
		}
		
		return count;

	}

	public int replace(Lst<T> de, T para) {
		return replace(de, new Lst<>(para));
	}

	public int replace(T de, Lst<T> para) {
		return replace(new Lst<>(de), para);
	}
	
	public int replace(Lst<T> de, Lst<T> para) {
		return replace(de, achados -> para);
	}
	
	public F1<T, Boolean> degug;
	
	public int replacef(Lst<F1<T, Boolean>> des, F1<Lst<T>, Lst<T>> func) {
		
		if (des.isEmpty()) {
			return 0;
		}
		
		int indexMaximo = size() - des.size();
		
		if (indexMaximo < 0) {
			return 0;
		}
		
		Lst<Lst<T>> listas = new Lst<>();
		
		each(i -> {
			
			if (indexOf(i) > indexMaximo) {
				return;
			}
			
			Lst<T> itens = new Lst<>();
			
			F1<T, Boolean> f = des.get(0);
			
			while (f != null) {
				
				if (degug != null && degug.call(i)) {
					SystemPrint.ln();
				}
				
				if (!f.call(i)) {
					return;
				}
				
				itens.ad(i);
				
				f = des.after(f);
				i = after(i);
				
			}
			
			listas.ad(itens);
			
		});
		
		listas.each(lista -> {
			int index = indexOf(lista.get(0));
			
			//significa que já foi substituido em uma rodada anterior
			if (index == -1) {
				return;
			}
			
			int size = lista.size();
			Lst<T> itens = func.call(lista);
			remove(index, size);
			addAll(index, itens);
		});
		
		return listas.size();
		
	}
	
	public int replace(Lst<T> de, F1<Lst<T>, Lst<T>> func) {

		if (de.isEmpty()) {
			return 0;
		}
		
		Lst<T> des = filter(i -> i == de.get(0));
		
		if (des.isEmpty()) {
			return 0;
		}
		
		int result = 0;
		
		for (T a : des) {
			
			Lst<T> achados = new Lst<>();
			
			achados.add(a);
			
			boolean sucesso = true;
			
			while (achados.size() < de.size()) {
				
				T item = achados.getLast();
				achados.add(after(item));
				
				if (de.after(item) != achados.getLast()) {
					sucesso = false;
					break;
				}
				
			}
			
			if (sucesso) {
				Lst<T> para = func.call(achados);
				int index = indexOf(a);
				while (achados.isNotEmpty()) {
					remove(achados.removeLast());
				}
				addAll(index, para);
				result++;
			}
			
		}
		
		return result;

	}

	public final String joinString(String separador) {
		if (isEmpty()) {
			return "";
		}
		String s = stream().map(String::valueOf).collect(Collectors.joining("|=()=|"));
		s = s.replace("\n", "|=()=|");
		return s.replace("|=()=|", separador);
	}
	
	public final String joinString(F1<T, String> func, String separador) {
		return map(func).joinString(separador);
	}
	public String join(String separador) {
		if (isEmpty()) {
			return "";
		}
		if (size() == 1) {
			return get(0).toString();
		}
		StringBox box = new StringBox("");
		forEach(o -> box.add(separador + o));
		String s = box.get();
		if (!StringEmpty.is(s) && separador.length() > 0) {
			s = s.substring(separador.length());
		}
		return s;
	}
	

	public <TA> Lst<TA> mapConcat(F1<T,Lst<TA>> fa) {

		Lst<TA> las = new Lst<>();

		for (T t : this) {
			Lst<TA> itens = fa.call(t);
			for (TA ta : itens) {
				las.addIfNotContains(ta);
			}
		}

		return las;

	}

	public Lst<T> each(Consumer<T> action) {
		
		try {
			forEach(action);
		} catch (BreakLoop e) {
			// de boa
		}
		
		return this;
	}
	
	public Lst<T> eachP(Consumer<T> action) {
		forEach(i -> UThread.exec(() -> action.accept(i)));
		return this;
	}
	
	public Lst<T> eachi(P2<T, Integer> action) {
		for (int i = 0; i < size(); i++) {
			try {
				action.call(get(i), i);
			} catch (BreakLoop e) {
				break;
			}
		}
		return this;
	}
	
	public Lst<T> eachWhile(F1<T, Boolean> action) {
		
		for (T o : this) {
			if (!UBoolean.isTrue(action.call(o))) {
				break;
			}
		}
		
		return this;
		
	}

	public boolean save(String fileName) {

		if (SO.windows()) {
			fileName = fileName.replace("\\", "/");
		}

		if (fileName.contains(" ")) {
			throw new RuntimeException("Nao coloque espacos em nomes de arquivos: " + fileName);
		}

		String pasta = StringBeforeLast.get(fileName, "/");
		if (pasta != null && !UFile.exists(pasta)) {
			SystemPrint.ln("Criando diretório: " + pasta);
			new File(pasta).mkdirs();
		}

		boolean exists = UFile.exists(fileName);

		if (exists) {

			if (checarExistenciaAoSalvar && getCastFromString() != null) {
				Lst<T> lst = new Lst<>();
				lst.castFromString = castFromString;
				lst.load(fileName);
				//se for igual nao precisa salvar de novo
				if (lst.eq(this)) {
					return false;
				}
			}

			SystemPrint.ln("Gravando: " + fileName + " (replace)");
		} else {
			SystemPrint.ln("Gravando: " + fileName + " (new)");
		}

		if ( fileName.contains("null.war") ) {
			throw UException.runtime("Gravando no lugar errado");
		}

		try {
			try (FileOutputStream fos = new FileOutputStream(fileName)) {
				if (charSet == null) {
					try (OutputStreamWriter osw = new OutputStreamWriter(fos)) {
						save(osw);
					}
				} else {
					try (OutputStreamWriter osw = new OutputStreamWriter(fos, charSet)) {
						save(osw);
					}
				}
			}
		} catch (IOException e) {
			throw new RuntimeException(e);
		}

		File file = new File(fileName);
		file.setExecutable(true, false);
		file.setReadable(true, false);
		file.setWritable(true, false);

		return true;
	}
	
	public boolean eq(Lst<T> lst) {
		
		if (equals(lst)) {
			return true;
		}

		if (lst.size() != size()) {
			return false;
		}

		for (int i = 0; i < size(); i++) {
			if (!Equals.is(lst.get(i), get(i))) {
				return false;
			}
		}

		return true;

	}

	public Lst<T> load(String file) {

		if (file.startsWith("../") && SO.windows()) {
			file = file.substring(1);
		}

		if (!UFile.exists(file)) {
			throw new RuntimeException("Arquivo nao encontrado: " + file);
		}

		try {
			return load(new FileInputStream(file));
		} catch (Exception e) {
			throw UException.runtime(e);
		}


	}

	public Lst<T> load(InputStream is) {

		try {

			InputStreamReader in;

			if ( charSet == null ) {
				in = new InputStreamReader(is);
			} else {
				in = new InputStreamReader(is, charSet);
			}

			BufferedReader buffer = new BufferedReader(in);

			String linha = buffer.readLine();

			while (linha != null) {
				T o = castFromString.call(linha);
				add(o);
				linha = buffer.readLine();
			}

			buffer.close();

			return this;

		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	private static final Gson gson = new Gson();
	
	private void save(OutputStreamWriter osw) {
		
		try (BufferedWriter out = new BufferedWriter(osw)) {
			for (T t : this) {
				String s;
				if (t == null) {
					s = "";
				} else {
					
					if (t instanceof String) {
						s = (String) t;
					} else {
						s = gson.toJson(t);
					}
					
					if (StringEmptyPlus.is(s)) {
						s = "";
					} else {
						s = StringTrim.right(s);
					}
				}
				out.write(s + "\n");
			}
		} catch (Exception e) {
			throw UException.runtime(e);
		}
	}

	private F1<T,String> castToStringDefault = StringParse::get;
	public F1<T,String> castToString = castToStringDefault;

	@Setter
	private F1<String,T> castFromString;

	@SuppressWarnings("unchecked")
	public F1<String,T> getCastFromString() {

		if (castFromString == null) {

			if (getGenericClass() == null) {
				return null;
			}

			if (genericClass == String.class) {
				castFromString = s -> (T) s;
			} else if (genericClass == Integer.class) {
				castFromString = s -> (T) IntegerParse.toInt(s);
			}

		}

		return castFromString;
	}

	public T getUnique() {
		if (isEmpty()) {
			return null;
		}
		if (size() > 1) {
			throw new RuntimeException("A lista possui + de 1 item");
		}
		return get(0);
	}

	public String toStringE(F1<T,String> get) {
		return toStringEOu(get, "e");
	}

	public String toStringOu(F1<T,String> get) {
		return toStringEOu(get, "ou");
	}

	private String toStringEOu(F1<T,String> get, String x) {
		if (isEmpty()) {
			return "";
		}
		Lst<T> lst = copy();
		T last = lst.removeLast();
		return lst.toString(get, ", ") + " " + x + " " + get.call(last);
	}
	
	public Lst<T> replaceIf(Predicate<T> predicate, F1<T,T> create) {
		filter(predicate).each(i -> replace(i, create.call(i))).size();
		return this;
	}

	public void addBefore(T o, T a) {
		add(indexOf(o), a);
	}

	public void addAfter(T o, T a) {
		int i = indexOf(o);
		add(i+1, a);
	}

	public Lst<T> entre(T a, T b) {
		
		Lst<T> lst = new Lst<>();
		
		int index = indexOf(a);
		
		while (true) {
			
			index++;
			
			if (index == size()) {
				return new Lst<>();
			}
			
			a = get(index);
			
			if (a == b) {
				return lst;
			}
			
			lst.add(a);
			
		}
		
	}

	public Data getMenorData(F1<T, Data> func) {
		Data o = null;
		for (T t : this) {
			Data x = func.call(t);
			if ((x != null) && ((o == null) || x.menor(o))) {
				o = x;
			}
		}
		return o;
	}

	public Data getMaiorData(F1<T, Data> func) {
		Data o = null;
		for (T t : this) {
			Data x = func.call(t);
			if ((x != null) && ((o == null) || x.maior(o))) {
				o = x;
			}
		}
		return o;
	}
	
	public P1<T> beforeAdd;
	
	private void beforeAdd(T e) {
		if (beforeAdd != null) {
			beforeAdd.call(e);
		}
	}
	
	public P0 afterChangeFunc;
	private String json;
	
	public void afterChange() {
		if (afterChangeFunc != null) {
			afterChangeFunc.call();
		}
		this.json = null;
	}
	
	//a funcao deve retornar o menor dos dois itens
	public void sortt(F2<T,T,T> func) {
		
		sort((a,b) -> {
			
			T c = func.call(a, b);
			
			//deve retornar nulo quando forem iguais
			if (c == null) {
				return 0;
			}
			
			if (c == a) {
				return -1;
			}
			
			if (c == b) {
				return 1;
			}
			
			throw new RuntimeException("A funcao deve retornar ou a ou b");
			
		});
		
	}

	public static Lst<String> buildString(String... array) {
		
		Lst<String> lst = new Lst<>();
		lst.equalsComparator = equalsStringComparator;
		for (int i = 0; i < array.length; i++) {
			lst.add(array[i]);
		}
		return lst;
		
	}

	public void replace(F1<T,T> func) {
		Lst<T> map = map(i -> func.call(i));
		clear();
		addAll(map);
	}

	public Lst<T> ignoreNulls() {
		this.ignoreNulls = true;
		return this;
	}
	
	public Lst<T> ignoreRepeateds() {
		this.ignoreRepeateds = true;
		return this;
	}
	
	public Lst<T> throwNulls() {
		this.throwNulls = true;
		return this;
	}
	
	public Lst<T> throwRepeateds() {
		this.throwRepeateds = true;
		return this;
	}
	
	public String toJson() {
		if (json == null) {
			json = JSon.toJson(this);
		}
		return json;
	}
	
	public void forEachInverted(Consumer<? super T> action) {
		copy().inverteOrdem().forEach(action);
	}

	@SuppressWarnings("unchecked")
	public void addCast(Object o) {
		add((T) o);
	}

	public T findById(Object id) {
		
		if (isEmpty()) {
			return null;
		}
		
		Atributo atributo = Atributos.get(getGenericClass()).getId();
		
		if (atributo == null) {
			throw new NaoImplementadoException("Nao foi localizado um atributo id para a classe " + getGenericClass().getSimpleName());
		}
		
		return unique(o -> Equals.is(atributo.get(o), id));
		
	}
	
}
