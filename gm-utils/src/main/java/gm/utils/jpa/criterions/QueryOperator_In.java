package gm.utils.jpa.criterions;

import java.util.List;

import jakarta.persistence.criteria.CriteriaBuilder.In;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.jpa.SqlNativeValue;

public class QueryOperator_In extends QueryOperator {

	private List<Object> list;

	public QueryOperator_In(String campo, List<Object> list) {
		super(campo, list, false);
		this.list = list;
	}

	@Override
	protected Criterion criterion() {
		return Restrictions.in(getCampo(), list);
	}

	@Override
	protected String nativo() {
		String s = "";
		for (Object o : list) {
			String x = SqlNativeValue.get(o);
			s += ", " + x;
		}
		s = s.substring(2);
		return getCampo() + " in (" + s + ")";
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		Expression<?> path = getPath(cq, campo, list.get(0));
		In<Object> in = cq.getCb().in(path);
		for (Object o : list) {
			in.value(o);
		}
		return in;
	}

}
