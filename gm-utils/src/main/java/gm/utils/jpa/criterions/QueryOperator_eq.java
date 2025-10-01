package gm.utils.jpa.criterions;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.abstrato.ExtraidId;
import gm.utils.abstrato.ObjetoComId;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import src.commom.utils.integer.IntegerIs;

public class QueryOperator_eq extends QueryOperator {

	public QueryOperator_eq(String campo, Object value) {
		super(campo, value, false);
	}

	@Override
	protected Criterion criterion() {
		return Restrictions.eq(getCampo(), getValue());
	}

	@Override
	protected String nativo() {
		Object o = getValue();

		if (o instanceof ObjetoComId) {
			Object id = ExtraidId.exec(o);
			return getCampo() + " = " + id;
		}
		if (UType.isData(o)) {
			Data data = Data.to(o);
			return getCampo() + " = " + data.format_sql(true);
		}
		if (IntegerIs.is(o)) {
			return getCampo() + " = " + o;
		}
		return getCampo() + " = '" + o + "'";
	}

	@Override
	protected Predicate getPredicateTrue(CriterioQuery<?> cq, String campo) {
		Object value = getValue();
		Expression<?> path = getPath(cq, campo, value);
		return cq.getCb().equal(path, value);
	}

}