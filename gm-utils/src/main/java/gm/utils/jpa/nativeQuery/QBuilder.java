package gm.utils.jpa.nativeQuery;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;

import gm.utils.abstrato.GetIdLong;
import gm.utils.date.Data;
import gm.utils.number.Numeric;
import gm.utils.number.UBigDecimal;

public class QBuilder {

	private final Map<String, Object> params = new HashMap<>();
	private final StringBuilder sql;

	public QBuilder(String sql) {
		 this.sql = new StringBuilder(sql);
	}

	private QBuilder setFinal(String key, Object value) {

		if (value instanceof GetIdLong) {
			GetIdLong o = (GetIdLong) value;
			value = o.getId();
		} else if (value instanceof Numeric) {
			Numeric<?> n = (Numeric<?>) value;
			return set(key, n.getValor());
		}

		params.put(key, value);
		return this;
	}

	public QBuilder add(String sql) {
		this.sql.append(" ").append(sql);
		return this;
	}

	public QBuilder eqIf(String campo, Object value) {
		if (value == null) {
			return this;
		}
		return eq(campo, value);
	}

	public QBuilder maiorOuIgualIf(String campo, Object value) {
		if (value == null) {
			return this;
		}
		return maiorOuIgual(campo, value);
	}

	public QBuilder maiorOuIgual(String campo, Object value) {
		sql.append(" and ").append(campo).append(" >= :").append(campo);
		return set(campo, value);
	}

	public QBuilder eq(String campo, Object value) {
		sql.append(" and ").append(campo).append(" = :").append(campo);
		return set(campo, value);
	}

	public QBuilder and(String sql) {
		this.sql.append(" and ").append(sql);
		return this;
	}

	public QBuilder set(String key, Object value) {
		return setFinal(key, value);
	}

	public QBuilder setNull(String key) {
		return setFinal(key, null);
	}

	public QBuilder setId(Integer id) {
		return set("id", id);
	}

	public QBuilder setInscricao(int inscricao) {
		return set("inscricao", inscricao);
	}

	public QBuilder setData(Calendar data) {
		return setData(Data.to(data));
	}

	public QBuilder setData(Data data) {
		return set("data", data);
	}

	public QBuilder set(String key, Calendar value) {
		return set(key, Data.to(value));
	}

	public QBuilder set(String key, Data value) {
		return setFinal(key, value == null ? null : value.getDate());
	}

	public QBuilder set(String key, BigDecimal value) {

		if (value == null) {
			return setNull(key);
		}

		value = UBigDecimal.setScale8(value);

		return setFinal(key, value);

	}

	public Query build(EntityManager em) {

		Query query = em.createNativeQuery(sql.toString());

		for (String key : params.keySet()) {
			query.setParameter(key, params.get(key));
		}

		return query;

	}

	//RemoverDaquiPraBaixo
	public QBuilder setId(Long id) {
		return set("id", id);
	}
	public QBuilder setInscricao(long inscricao) {
		return set("inscricao", inscricao);
	}

}
