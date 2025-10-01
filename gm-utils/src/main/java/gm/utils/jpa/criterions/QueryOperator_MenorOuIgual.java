package gm.utils.jpa.criterions;

import java.util.Calendar;
import java.util.Date;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

public class QueryOperator_MenorOuIgual extends QueryOperator{
	public QueryOperator_MenorOuIgual(String campo, Object value) {
		super(campo, value, false);
	}
	@Override
	protected Criterion criterion() {
		return Restrictions.le(getCampo(), getValue());
	}
	@Override
	protected String nativo() {
		return getCampo() + " <= " + getNativeValue();
	}
	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {

		CriteriaBuilder cb = cq.getCb();
		Object value = getValue();

		if (value instanceof Number) {
			Number number = (Number) value;
			return cb.le(cq.getPath().get(campo).as(Number.class), number);
		}

		if (value instanceof Calendar) {
			Calendar c = (Calendar) value;
			return cb.lessThanOrEqualTo(cq.getPath().get(campo).as(Calendar.class), c);
		}

		if (value instanceof Date) {
			Date c = (Date) value;
			return cb.lessThanOrEqualTo(cq.getPath().get(campo).as(Date.class), c);
		}

		throw new RuntimeException("Tipo não tratado: " + value.getClass());

	}
}
