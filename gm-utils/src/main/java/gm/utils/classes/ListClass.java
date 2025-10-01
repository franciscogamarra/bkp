package gm.utils.classes;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.comum.Lst;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcTipo;
import gm.utils.string.ListString;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;

public class ListClass extends Lst<Class<?>> {

	private static final long serialVersionUID = 1L;
	private boolean locked = false;

	public ListClass(){
	}

	public ListClass(Class<?>... classes){
		this();
		for (Class<?> classe : classes) {
			this.add(classe);
		}
	}

	@Override
	public Class<?> remove(int index) {
		if (locked) {
			throw new RuntimeException("???");
		}
		return super.remove(index);
	}

	@Override
	public boolean remove(Object o) {
		if (locked) {
			throw new RuntimeException("???");
		}
		return super.remove(o);
	}

	@Override
	public boolean add(Class<?> e) {
		if (locked) {
			throw new RuntimeException("???");
		}
		return super.add(e);
	}

	public ListClass getByAnnotation(Class<? extends Annotation> a) {
		return getByAnnotation(a, false);
	}

	public ListClass getByAnnotation(Class<? extends Annotation> a, boolean findSuper) {

		ListClass list = new ListClass();

		if (findSuper) {
			for (Class<?> c : this) {
				if (isAnnotationPresent(c,a)) {
					list.add(c);
				}
			}
		} else {
			for (Class<?> c : this) {
				if (c.isAnnotationPresent(a)) {
					list.add(c);
				}
			}
		}

		return list;

	}

	private boolean isAnnotationPresent(Class<?> c, Class<? extends Annotation> a) {
		if (c.isAnnotationPresent(a)) {
			return true;
		}
		c = c.getSuperclass();
		if (c == null) {
			return false;
		}
		return isAnnotationPresent(c, a);
	}

	@Override
	public void print() {
		for (Class<?> c : this) {
			ULog.debug(c.getName());
		}
		ULog.debug("Total: " + size() + " classes");
	}

	public ListClass whereExtends(Class<?> classe) {
		ListClass list = new ListClass();
		for (Class<?> c : this) {
			if (UClass.instanceOf(c, classe)) {
				list.add(c);
			}
		}
		return list;
	}
	public ListClass whereNotExtends(Class<?> classe) {
		ListClass list = new ListClass();
		for (Class<?> c : this) {
			if (!UClass.instanceOf(c, classe)) {
				list.add(c);
			}
		}
		return list;
	}

	@Override
	public ListClass copy() {
		ListClass list = new ListClass();
		list.addAll(this);
		return list;
	}

	public void sortAsImport() {
		Comparator<Class<?>> importComparator = (o1, o2) -> {
			String a = StringBeforeLast.get(o1.getName(),".");
			String b = StringBeforeLast.get(o2.getName(),".");

			if (a.equals(b)) {
				return o1.getSimpleName().compareTo(o2.getSimpleName());
			}

			if (a.startsWith("java.")) {
				if (b.startsWith("java.")) {
					return a.compareTo(b);
				}
				return -1;
			}

			if (b.startsWith("java.")) {
				return 1;
			}

			return a.compareTo(b);
		};
		this.sort(importComparator);
		this.sort(importComparator);
		this.sort(importComparator);
	}

	public boolean removeIfPackage(String s) {
		String x = s + ".";
		Predicate<Class<?>> filter = t -> t.getName().startsWith(x);
		return removeIf(filter);
	}
	public boolean removeIfNotPackage(String s) {
		String x = s + ".";
		Predicate<Class<?>> filter = t -> !t.getName().startsWith(x);
		return removeIf(filter);
	}

	public boolean removeIfSimpleNameStartsWith(String s) {
		Predicate<Class<?>> filter = t -> t.getSimpleName().startsWith(s);
		return removeIf(filter);
	}

	public void sort() {
		this.sort(ClassSortComparator.instance);
	}
	@Override
	public void addIfNotContains(Class<?> classe) {
		if (!this.contains(classe)) {
			this.add(classe);
		}
	}
	public void addIfNotContains(List<?> list) {
		for (Object classe : list) {
			if (!this.contains(classe)) {
				this.add((Class<?>) classe);
			}
		}
	}
	public Class<?> get(GetClassName getter) {
		return this.get(getter.getClassName());
	}
	public <T> Class<T> getObrig(GetClassName getter) {
		return this.getObrig(getter.getClassName());
	}
	public <T> Class<T> getObrig(String name) {
		Class<T> classe = this.get(name);
		if (classe == null) {
			throw UException.runtime("Classe não encontrada: " + name);
		}
		return classe;
	}
	@SuppressWarnings("unchecked")
	public <T> Class<T> get(String name) {
		if (StringEmpty.is(name)) {
			throw UException.runtime("O nome da classe está em branco");
		}
		for (Class<?> classe : this) {
			if (classe.getSimpleName().contentEquals(name) || classe.getName().contentEquals(name)) {
				return (Class<T>) classe;
			}
		}
		return null;
	}

	@Override
	public Class<?> removeLast() {
		return this.remove(size() - 1);
	}

	@Override
	public ListClass filter(Predicate<Class<?>> predicate) {
		List<Class<?>> collect = stream().filter(predicate).collect(Collectors.toList());
		ListClass list = new ListClass();
		list.addAll(collect);
		return list;
	}

	@Override
	public ListClass extract(Predicate<Class<?>> predicate) {
		ListClass list = filter(predicate);
		removeAll(list);
		return list;
	}

	public ListClass join(ListClass list) {
		ListClass copy = copy();
		for (Class<?> classe : list) {
			copy.addIfNotContains(classe);
		}
		return copy;
	}

	public ListString getNames() {
		ListString list = new ListString();
		for (Class<?> classe : this) {
			list.addIfNotContains(classe.getSimpleName());
		}
		return list;
	}

	public ListString getNamesFull() {
		ListString list = new ListString();
		for (Class<?> classe : this) {
			list.addIfNotContains(classe.getName());
		}
		return list;
	}

	public static ListClass safe(ListClass list) {
		return list == null ? new ListClass() : list;
	}

	public boolean contains(String s) {
		if (StringContains.is(s, ".")) {
			return getNamesFull().contains(s);
		}
		return getNames().contains(s);
	}
	
	public boolean contains(JcTipo type) {
		return contains(type.getName());
	}

	public void lock() {
		locked = true;
	}

	public void load(GFile file) {
		ListString list = new ListString().load(file);
		for (String s : list) {
			addIfNotContains(UClass.getClassObrig(s));
		}
	}
	
	public void load(File file) {
		load(GFile.get(file));
	}

	public void save(GFile file) {
		ListString list = new ListString();
		for (Class<?> classe : this) {
			list.add(classe.getName());
		}
		list.save(file);
	}
	
	public void save(File file) {
		save(GFile.get(file));
	}

	public ListClass extractEnums() {
		return extract(o -> o.isEnum());
	}

	public ListClass extractAbstracts() {
		return extract(o -> UClass.isAbstract(o));
	}

	public void inverte() {
		ListClass list = copy();
		clear();
		while (!list.isEmpty()) {
			add(0, list.remove(0));
		}
	}
	
	public void removeIfHasAnnotation(Class<? extends Annotation> ann) {
		removeIf(i -> i.isAnnotationPresent(ann));
	}

}
