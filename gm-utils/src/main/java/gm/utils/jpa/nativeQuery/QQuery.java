package gm.utils.jpa.nativeQuery;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import jakarta.persistence.Query;

import gm.utils.abstrato.GetIdLong;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UType;
import gm.utils.date.Data;
import gm.utils.number.Numeric;
import gm.utils.number.UBigDecimal;
import src.commom.utils.string.StringRight;

public class QQuery {

	private static final Map<Query, QQuery> map = new HashMap<>();

	private final Query q;
	private final String sql;

	private static class Param {
		Object o;
		Param(Object o) {
			this.o = o;
		}
	}

	private final Map<String, Param> params = new HashMap<>();

	public QQuery(Query q, String sql) {

		this.q = q;
		this.sql = sql;

		if (sql != null) {
			map.put(q, this);
		}

	}

	public QQuery(Query q) {
		this(q, null);
	}

	private QQuery setFinal(String key, Object value) {

		if (value instanceof GetIdLong) {
			GetIdLong o = (GetIdLong) value;
			value = o.getId();
		} else if (value instanceof Numeric) {
			Numeric<?> n = (Numeric<?>) value;
			return set(key, n.getValor());
		}

		Param param = params.get(key);

		if (param != null) {

			if (value == param.o) {
				//				Dev.throwss("Key ja foi adicionada na query: " + key);
				return this;
			}

			throw new RuntimeException("Key ja foi adicionada na query: " + key);
		}

		params.put(key, new Param(value));

		q.setParameter(key, trata(value));

		return this;
	}

	public QQuery setPrimary(String key, Object value) {

		if (value == null) {
			return setNull(key);
		}

		if (UType.isData(value)) {
			Data data = Data.to(value);
			return set(key, data);
		}

		if (value instanceof BigDecimal) {
			BigDecimal b = (BigDecimal) value;
			return set(key, b);
		}

		return set(key, value);

	}

	public QQuery set(String key, Object value) {
		return setFinal(key, value);
	}

	public QQuery setNull(String key) {
		return setFinal(key, null);
	}

	public QQuery setId(Integer id) {
		return set("id", id);
	}

	public QQuery setInscricao(int inscricao) {
		return set("inscricao", inscricao);
	}

	public QQuery setIdPessoa(int idPessoa) {
		return set("idPessoa", idPessoa);
	}

	public QQuery setData(Calendar data) {
		return setData(Data.to(data));
	}

	public QQuery setData(Data data) {
		return set("data", data);
	}

	public QQuery setOperador(long inscricao) {
		return set("operador", inscricao);
	}

	public QQuery setValor(BigDecimal valor) {
		return set("valor", valor);
	}

	public QQuery setProduto(int produto) {
		return set("produto", produto);
	}

	public QQuery setProduto(String produto) {
		return set("produto", produto);
	}

	public QQuery setParcelas(int parcelas) {
		return set("parcelas", parcelas);
	}

	public QQuery setTipo(Integer tipo) {
		return set("tipo", tipo);
	}

	public QQuery setTipo(String tipo) {
		return set("tipo", tipo);
	}

	public QQuery set(String key, Calendar value) {
		return set(key, Data.to(value));
	}

	public QQuery set(String key, Data value) {
		return setFinal(key, value == null ? null : value.getDate());
	}

	public QQuery set(String key, BigDecimal value) {

		if (value == null) {
			return setNull(key);
		}

		value = UBigDecimal.setScale8(value);

		return setFinal(key, value);

	}

	private boolean sqlContains(String key) {
		if (sql == null) {
			throw new RuntimeException("sql == null");
		}
		return sql.contains(":" + key);
	}

	public QQuery setIf(String key, Data value) {
		if (value != null && sqlContains(key)) {
			return set(key, value);
		}
		return this;
	}

	public QQuery setIf(String key, Object value) {
		if (value != null && sqlContains(key)) {
			return set(key, value);
		}
		return this;
	}

	public static void printSql(Query q) {
		QQuery o = map.get(q);
		if (o != null) {
			o.printSql();
		}
	}

	private void printSql() {

		String sql = this.sql;

		Set<String> keys = params.keySet();
		Lst<String> list = new Lst<>();
		list.addAll(keys);
		list.sort((a,b) -> b.length() - a.length());

		for (String key : list) {
			Param param = params.get(key);
			sql = sql.replace(":"+key, toString(param.o));
		}

		SystemPrint.ln("==================================================");
		SystemPrint.ln(sql);
		SystemPrint.ln("==================================================");

	}

	private static final DecimalFormat DECIMAL_FORMAT = new DecimalFormat("#.0000000000");

	private static String toString(Object o) {

		if (o == null) {
			return "null";
		}

		if (o instanceof String) {
			return "'"+o+"'";
		}

		if (o instanceof Calendar || o instanceof Date) {
			return Data.to(o).format_sql(true);
		}

		if (o instanceof BigDecimal) {
			String s = DECIMAL_FORMAT.format(o).replace(",", ".");
			while (s.endsWith("0")) {
				s = StringRight.ignore1(s);
			}
			if (s.endsWith(".")) {
				s = StringRight.ignore1(s);
			}
			return s;
		}

		return o.toString();

	}

	private Object trata(Object o) {

		if (o == null) {
			return null;
		}

		if (o instanceof String) {
			return o;
		}

		if (o instanceof BigDecimal) {
			return ((BigDecimal) o).doubleValue();
		}

		return o;

	}

	//RemoverDaquiPraBaixo
	public QQuery setId(Long id) {
		return set("id", id);
	}
	public QQuery setInscricao(long inscricao) {
		return set("inscricao", inscricao);
	}
	public QQuery setProduto(long produto) {
		return set("produto", produto);
	}

}
