package gm.utils.classes;

import java.util.Comparator;

public class ClassSortComparator implements Comparator<Class<?>> {

	public static final ClassSortComparator instance = new ClassSortComparator();

	private ClassSortComparator() {}

	@Override
	public int compare(Class<?> a, Class<?> b) {
		return a.getName().compareTo(b.getName());
	}
}