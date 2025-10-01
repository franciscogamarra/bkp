package gm.utils.jpa.criterions;

import java.util.List;

import org.hibernate.criterion.Order;

import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.date.Cronometro;
import gm.utils.exception.UException;
import gm.utils.string.ListString;
import jakarta.persistence.LockModeType;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.From;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Getter;

@Getter
public class CriterioQuery<T> {

	private final Criterio<?> co;
	private final Root<?> from;
	private final CriteriaBuilder cb;
	private final CriteriaQuery<T> cq;
	private From<?, ?> path;
//	private Class<T> classe;

	public CriterioQuery(Criterio<?> criterio, Class<T> classe) {
		co = criterio;
//		this.classe = classe;
		cb = criterio.em.getCriteriaBuilder();
		cq = cb.createQuery(classe);
		from = cq.from(criterio.getClasse());
		addWheres();
	}

	private void addWheres() {
		co.beforeList();
		Predicate[] predicates = new Predicate[co.adds.size()];
		int i = 0;
		for (QueryOperator o : co.adds) {
			predicates[i++] = o.getPredicate(this);
		}
		cq.where(predicates);
	}

	@SuppressWarnings("unchecked")
	public <X> Lst<X> distinct(String campo) {
		campo = trataCampo(campo);
		cq.select(path.get(campo));
		cq.distinct(true);
		TypedQuery<T> query = co.em.createQuery(cq);
		List<X> resultList = (List<X>) query.getResultList();
		return new Lst<>(resultList);
	}

	public String trataCampo(String campo) {
		this.path = from;
		if (campo.contains(".")) {
			ListString campos = ListString.byDelimiter(campo, ".");
			campo = campos.removeLast();
			for (String s : campos) {
				path = path.join(s, JoinType.LEFT);
			}
		}
		return campo;
	}

	@SuppressWarnings("unchecked")
	public int count() {
		Expression<T> count = (Expression<T>) cb.count(from);
		cq.select(count);
		Long o = (Long) co.em.createQuery(cq).getSingleResult();
		return o.intValue();
	}

	public CriterioResult<T> result() {

		if (!UList.isEmpty(co.getOrderBy())) {

			Lst<jakarta.persistence.criteria.Order> orders = new Lst<>();

			for (Order order : co.getOrderBy()) {

				String campo = order.getPropertyName();
				From<?, ?> path = from;

				if (campo.contains(".")) {
					ListString campos = ListString.byDelimiter(campo, ".");
					campo = campos.removeLast();
					for (String s : campos) {
						path = path.join(s, JoinType.LEFT);
					}
				}

				if (order.isAscending()) {
					orders.add(cb.asc(path.get(campo)));
				} else {
					orders.add(cb.desc(path.get(campo)));
				}

			}

			cq.orderBy(orders);

		}

		TypedQuery<T> query = co.em.createQuery(cq);
		
		if (co.isNoLock()) {
			query.setLockMode(LockModeType.PESSIMISTIC_WRITE);
		}

		if (co.getLimit() > 0) {

			query.setMaxResults(co.getLimit());

			if (co.getPage() == 0) {
				co.setPage(1);
			}

			int firstResult = co.getSkip() + (co.getPage() - 1) * co.getLimit();

			if (firstResult < 0) {
				throw UException.runtime("firstResult < 0");
			}
			if (firstResult > 0) {
				query.setFirstResult(firstResult);
			}

		} else if (co.getLimit() != -1) {
			query.setFirstResult(co.getSkip());
			query.setMaxResults(500);
		}

		Cronometro cronometro = new Cronometro();
		
		List<T> list = query.getResultList();
		if (cronometro.tempo() > 5000) {
			UException.runtime("Consulta demorou: " + cronometro.tempo() + " >> " + cq.toString());
		}

		CriterioResult<T> result = new CriterioResult<>();
		result.s = cq.toString();
		result.list = new Lst<>(list);
		return result;
	}

}
