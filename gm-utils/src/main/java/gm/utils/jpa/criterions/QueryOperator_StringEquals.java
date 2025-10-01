package gm.utils.jpa.criterions;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;

public class QueryOperator_StringEquals extends QueryOperator {

	public QueryOperator_StringEquals(String campo, String value) {
		super(campo, value, false);
	}

	@Override
	protected Criterion criterion() {
		
		String s = (String) getValue();
		
		if (QueryOperator_String.CASE_SENSITIVE) {
			s = s.toLowerCase();
			SimpleExpression o = Restrictions.eq(getCampo(), s);
			return o.ignoreCase();
		}
		return Restrictions.eq(getCampo(), s);
		
	}

	@Override
	protected String nativo() {
		
		String s = (String) getValue();
		
		if (QueryOperator_String.CASE_SENSITIVE) {
			return "lower(" + getCampo() + ") like '" + s.toLowerCase() + "'";
		}
		return getCampo() + " like '" + s.toLowerCase() + "'";
		
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {

		String s = (String) getValue();
		
		Expression<String> cp = cq.getPath().get(campo).as(String.class);
		
		if (QueryOperator_String.CASE_SENSITIVE) {
			return cq.getCb().equal(cq.getCb().lower(cp), s.toLowerCase());
		}
		return cq.getCb().equal(cp, s);
		
	}

}
