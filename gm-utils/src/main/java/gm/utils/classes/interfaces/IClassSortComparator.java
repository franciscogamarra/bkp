package gm.utils.classes.interfaces;

import java.util.Comparator;

public class IClassSortComparator implements Comparator<IClass> {

	public static final IClassSortComparator instance = new IClassSortComparator();

	private IClassSortComparator() {}

	@Override
	public int compare(IClass a, IClass b) {
		return a.getName().compareTo(b.getName());
	}
}