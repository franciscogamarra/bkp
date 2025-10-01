package gm.utils.jpa.nativeQuery;

import java.util.List;
import java.util.function.Predicate;

import jakarta.persistence.Query;

import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.lambda.F1;
import gm.utils.lambda.P1;

public class UQuery {

	private Query query;
	private Lst<QRow> list;

	public UQuery(Query query) {
		this.query = query;
	}
	public UQuery param(String key, Object value) {
		query.setParameter(key, value);
		return this;
	}
	public UQuery param(int position, Object value) {
		query.setParameter(position, value);
		return this;
	}

	public Lst<QRow> list() {
		if (list == null) {
			list = new Lst<>();

			List<?> lst = query.getResultList();

			if (lst.isEmpty()) {
				return list;
			}

			if (lst.get(0) instanceof Object[]) {
				lst.forEach(o -> list.add(new QRow((Object[])o)));
			} else {
				lst.forEach(o -> {
					Object[] x = new Object[1];
					x[0] = o;
					list.add(new QRow(x));
				});
			}

		}
		return list;
	}
	public void forEach(P1<QRow> action) {
		list().forEach(o -> action.call(o));
	}
	public <T> Lst<T> map(F1<QRow, T> action) {
		Lst<T> list = new Lst<>();
		forEach(o -> list.add(action.call(o)));
		return list;
	}
	public QRow unique() {
		return UList.getUnique(list());
	}
	public UQuery filter(Predicate<QRow> predicate) {
		UQuery o = new UQuery(null);
		o.list = UList.filter(list(), predicate);
		return o;
	}

}
