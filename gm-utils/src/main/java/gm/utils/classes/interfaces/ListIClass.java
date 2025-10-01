package gm.utils.classes.interfaces;

import java.io.File;
import java.lang.annotation.Annotation;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import gm.utils.classes.GetClassName;
import gm.utils.comum.Lst;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcTipo;
import gm.utils.string.ListString;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;

public class ListIClass extends Lst<IClass> {

	private static final long serialVersionUID = 1L;
	private boolean locked = false;

	public ListIClass(){
	}

	public ListIClass(IClass... classes){
		this();
		for (IClass classe : classes) {
			this.add(classe);
		}
	}

	@Override
	public IClass remove(int index) {
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
	public boolean add(IClass e) {
		if (locked) {
			throw new RuntimeException("???");
		}
		return super.add(e);
	}

	public ListIClass getByAnnotation(Class<? extends Annotation> a) {
		return getByAnnotation(a, false);
	}

	public ListIClass getByAnnotation(Class<? extends Annotation> a, boolean findSuper) {

		ListIClass list = new ListIClass();

		if (findSuper) {
			for (IClass c : this) {
				if (isAnnotationPresent(c,a)) {
					list.add(c);
				}
			}
		} else {
			for (IClass c : this) {
				if (c.isAnnotationPresent(a)) {
					list.add(c);
				}
			}
		}

		return list;

	}

	private boolean isAnnotationPresent(IClass c, Class<? extends Annotation> a) {
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
		for (IClass c : this) {
			ULog.debug(c.getName());
		}
		ULog.debug("Total: " + size() + " classes");
	}

	public ListIClass whereExtends(IClass classe) {
		ListIClass list = new ListIClass();
		for (IClass c : this) {
			if (c.instanceOf(classe)) {
				list.add(c);
			}
		}
		return list;
	}
	public ListIClass whereNotExtends(IClass classe) {
		ListIClass list = new ListIClass();
		for (IClass c : this) {
			if (!c.instanceOf(classe)) {
				list.add(c);
			}
		}
		return list;
	}

	@Override
	public ListIClass copy() {
		ListIClass list = new ListIClass();
		list.addAll(this);
		return list;
	}

	public void sortAsImport() {
		Comparator<IClass> importComparator = (o1, o2) -> {
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
		Predicate<IClass> filter = t -> t.getName().startsWith(x);
		return removeIf(filter);
	}
	public boolean removeIfNotPackage(String s) {
		String x = s + ".";
		Predicate<IClass> filter = t -> !t.getName().startsWith(x);
		return removeIf(filter);
	}

	public boolean removeIfSimpleNameStartsWith(String s) {
		Predicate<IClass> filter = t -> t.getSimpleName().startsWith(s);
		return removeIf(filter);
	}

	public void sort() {
		this.sort(IClassSortComparator.instance);
	}
	@Override
	public void addIfNotContains(IClass classe) {
		if (!this.contains(classe)) {
			this.add(classe);
		}
	}
	public void addIfNotContains(List<?> list) {
		for (Object classe : list) {
			if (!this.contains(classe)) {
				this.add((IClass) classe);
			}
		}
	}
	public IClass get(GetClassName getter) {
		return this.get(getter.getClassName());
	}
	public IClass getObrig(GetClassName getter) {
		return this.getObrig(getter.getClassName());
	}
	public IClass getObrig(String name) {
		IClass classe = this.get(name);
		if (classe == null) {
			throw UException.runtime("Classe não encontrada: " + name);
		}
		return classe;
	}
	
	public IClass get(String name) {
		if (StringEmpty.is(name)) {
			throw UException.runtime("O nome da classe está em branco");
		}
		for (IClass classe : this) {
			if (classe.getSimpleName().contentEquals(name) || classe.getName().contentEquals(name)) {
				return classe;
			}
		}
		return null;
	}

	@Override
	public IClass removeLast() {
		return this.remove(size() - 1);
	}

	@Override
	public ListIClass filter(Predicate<IClass> predicate) {
		List<IClass> collect = stream().filter(predicate).collect(Collectors.toList());
		ListIClass list = new ListIClass();
		list.addAll(collect);
		return list;
	}

	@Override
	public ListIClass extract(Predicate<IClass> predicate) {
		ListIClass list = filter(predicate);
		removeAll(list);
		return list;
	}

	public ListIClass join(ListIClass list) {
		ListIClass copy = copy();
		for (IClass classe : list) {
			copy.addIfNotContains(classe);
		}
		return copy;
	}

	public ListString getNames() {
		ListString list = new ListString();
		for (IClass classe : this) {
			list.addIfNotContains(classe.getSimpleName());
		}
		return list;
	}

	public ListString getNamesFull() {
		ListString list = new ListString();
		for (IClass classe : this) {
			list.addIfNotContains(classe.getName());
		}
		return list;
	}

	public static ListIClass safe(ListIClass list) {
		return list == null ? new ListIClass() : list;
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
			addIfNotContains(IClass.build(s));
		}
	}
	
	public void load(File file) {
		load(GFile.get(file));
	}

	public void save(GFile file) {
		ListString list = new ListString();
		for (IClass classe : this) {
			list.add(classe.getName());
		}
		list.save(file);
	}
	
	public void save(File file) {
		save(GFile.get(file));
	}

	public ListIClass extractEnums() {
		return extract(o -> o.isEnum());
	}

	public ListIClass extractAbstracts() {
		return extract(o -> o.isAbstract());
	}

	public void inverte() {
		ListIClass list = copy();
		clear();
		while (!list.isEmpty()) {
			add(0, list.remove(0));
		}
	}
	
	public void removeIfHasAnnotation(Class<? extends Annotation> ann) {
		removeIf(i -> i.isAnnotationPresent(ann));
	}

}
