package gm.utils.jpa.criterions;

import java.util.List;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import gm.utils.comum.IWrapper;
import gm.utils.comum.Lst;
import gm.utils.comum.UAssert;
import gm.utils.exception.UException;
import gm.utils.jpa.SqlNativeValue;
import gm.utils.string.ListString;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.Predicate;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringAfterFirst;

@Getter @Setter
public abstract class QueryOperator {

	protected boolean not;
	private String campo;
	private Object value;

	public QueryOperator(String campo) {
		this(campo, null, true);
	}

	public ListString getCampos() {
		ListString list = new ListString();
		list.add(campo);
		return list;
	}

	public QueryOperator(String campo, Object value, boolean valorPodeSerNulo) {
		UAssert.notEmpty(campo, "O valor não pode ser nulo");
		this.campo = campo;
		this.value = value;
		if (!valorPodeSerNulo && (getValue() == null)) {
			throw UException.runtime("O valor não pode ser nulo");
		}
	}

	public Object getRealValue() {
		return value;
	}
	
	@SuppressWarnings("unchecked")
	public Object getValue() {

		if (value == null) {
			return null;
		}
		
		if (value instanceof IWrapper) {
			IWrapper<?> wrapper = (IWrapper<?>) value;
			return wrapper.unwrapper();
		}
		
		if (value instanceof List) {
			
			List<Object> values = (List<Object>) value;
			
			if (values.isEmpty()) {
				return values;
			}
			
			Lst<Object> lst = new Lst<>();
			lst.addAll(values);
			
			values.clear();
			values.addAll(lst.map(i -> {
				if (i == null) {
					return null;
				} else if (i instanceof IWrapper) {
					IWrapper<?> wrapper = (IWrapper<?>) i;
					return wrapper.unwrapper();
				} else {
					return i;
				}
			}));
			
		}
		
		
		return value;

	}

	public final Criterion getCriterion() {
		Criterion c = criterion();
		if (not) {
			return Restrictions.not(c);
		}
		return c;
	}

	public QueryOperator not() {
		setNot(true);
		return this;
	}

	protected abstract Criterion criterion();

	public final String getNativo() {
		String s = nativo();
		if (not) {
			s = "not ( " + s + " )";
		}
		return s;
	}

	protected abstract String nativo();
//	protected String nativo() {
//		throw UException.runtime("Não implementado: " + getClass().getSimpleName());
//	}

	@Override
	public String toString() {
		String s = getClass().getSimpleName();
		s = StringAfterFirst.get(s, "_");
		s = getCampo() + " " + s + " " + getValue();
		if (not) {
			s = "not (" + s + ")";
		}
		return s;
	}
	public final String getNativeValue() {
		return SqlNativeValue.get( getValue() );
	}
	@Override
	public boolean equals(Object obj) {
		if (obj == null) {
			return false;
		}
		if (super.equals(obj)) {
			return true;
		}
		QueryOperator o = (QueryOperator) obj;
		if ((o.isNot() != isNot()) || (o.getCampo() != getCampo()) || (o.getValue() != getValue())) {
			return false;
		}
		return true;
	}
	@Override
	public int hashCode() {
		Object v = getValue();
		int prime = 31;
		int result = 1;
		result = prime * result + ((campo == null) ? 0 : campo.hashCode());
		result = prime * result + (not ? 1231 : 1237);
		return prime * result + ((v == null) ? 0 : v.hashCode());
	}

	private From<?, ?> root;

	public final <TT> Predicate getPredicate(CriterioQuery<?> cq) {

		String field = cq.trataCampo(getCampo());

		if (not) {
			return getPredicateFalse(cq, field);
		}
		return getPredicateTrue(cq, field);
	}

//	protected static String getField(From<?, ?> path, String campo) {
//		if (campo.contains(".")) {
//			ListString campos = ListString.byDelimiter(campo, ".");
//			campo = campos.removeLast();
//			for (String s : campos) {
//				path = path.join(s, JoinType.LEFT);
//			}
//		}
//		return campo;
//	}

	protected abstract Predicate getPredicateTrue(CriterioQuery<?> cq, String campo);

	protected Predicate getPredicateFalse(CriterioQuery<?> cq, String campo) {
		return cq.getCb().not(getPredicateTrue(cq, campo));
	}

	protected Expression<?> getPath(CriterioQuery<?> cq, String campo, Object value) {
		Expression<?> path = cq.getPath().get(campo);
		if (value != null) {
			path = path.as(value.getClass());
		}
		return path;
	}

}
