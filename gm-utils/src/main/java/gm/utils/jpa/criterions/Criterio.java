package gm.utils.jpa.criterions;

import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Criteria;
import org.hibernate.Session;
//import org.hibernate.NullPrecedence;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.InExpression;
import org.hibernate.criterion.LikeExpression;
import org.hibernate.criterion.NotExpression;
import org.hibernate.criterion.NotNullExpression;
import org.hibernate.criterion.NullExpression;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.ProjectionList;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.PropertySubqueryExpression;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.internal.CriteriaImpl.OrderEntry;

import gm.utils.abstrato.ExtraidId;
import gm.utils.abstrato.ObjetoComId;
import gm.utils.classes.UClass;
import gm.utils.comum.Aleatorio;
import gm.utils.comum.IWrapper;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.comum.UCompare;
import gm.utils.comum.UList;
import gm.utils.comum.ULog;
import gm.utils.comum.UObject;
import gm.utils.comum.UType;
import gm.utils.config.UConfig;
import gm.utils.date.Cronometro;
import gm.utils.date.Data;
import gm.utils.date.Periodo;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.UTable;
import gm.utils.jpa.converters.JpaConverter;
import gm.utils.lambda.P1;
import gm.utils.number.ListInteger;
import gm.utils.number.ListLong;
import gm.utils.number.Numeric2;
import gm.utils.number.ULong;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;
import gm.utils.string.UString;
import jakarta.persistence.Convert;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.object.Obrig;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringParse;
import src.commom.utils.string.StringTrim;

@Getter @Setter
public final class Criterio<T> {

	private enum TipoBusca {
		nativa, criteria, criteriaQuery
	}

	private TipoBusca tipoBusca = TipoBusca.criteriaQuery;
	private Class<T> classe;
	private Atributo id;
	protected EntityManager em;

	public P1<T> comoDeletar;
	public P1<T> comoSalvar;
	
	private boolean noLock;

	public Atributo getId() {
		if (this.as == null) {
			this.as();
		}
		return this.id;
	}

	private Atributos as;

	private Atributos as() {
		if (this.as == null) {
			this.as = AtributosBuild.get(this.classe);
			this.as.removeTransients();
			this.as.removeStatics();
			this.id = this.as.getId();
		}
		return this.as;
	}

	private boolean ignoreObserver = false;

	public Criterio(Class<T> classe) {
		this.classe = classe;
	}
	public Criterio(Class<T> classe, EntityManager em) {
		this(classe);
		this.em = em;
	}

	public Criterio<T> skip(int x) {
		this.skip = x;
		return this;
	}

	private boolean beforeListChecked = false;
	protected void beforeList() {

		if (beforeListChecked) {
			return;
		}

		while (this.fechaParenteses()) {
			;
		}
		if (!this.ignoreObserver && !this.observerExecutado) {
			if (this.em == null) {
				UConfig.jpa().beforeSelect(this, this.classe);
			}
			this.observerExecutado = true;
		}

		beforeListChecked = true;

	}

	private static Atributos atributosCriteria;

	@Deprecated//verificar como utilizar na nova versao do jpa
	private Criteria newCriteria() {

		this.beforeList();

		Criteria criteria;

		if (this.em == null) {
			criteria = UConfig.jpa().createCriteria(this.classe);
		} else {
			Session session = this.em.unwrap(Session.class);
			criteria = session.createCriteria(this.classe);
		}

		if (Criterio.atributosCriteria == null) {
			Criterio.atributosCriteria = AtributosBuild.get( UClass.getClass(criteria) );
		}

		for (QueryOperator criterion : this.adds) {
			criteria.add(criterion.getCriterion());
		}
		this.setAlias(criteria);
		if (this.limit > 0) {
			criteria.setMaxResults(this.limit);
			if (this.page == 0) {
				this.page = 1;
			}
			int firstResult = this.skip + (this.page-1) * this.limit;
			if (firstResult < 0) {
				throw UException.runtime("firstResult < 0");
			}
			criteria.setFirstResult(firstResult);
		} else if (this.limit != -1) {
			criteria.setFirstResult(this.skip);
			criteria.setMaxResults(500);
		}
		return criteria;
	}

	public int count() {
		if (tipoBusca == TipoBusca.criteriaQuery) {
			return this.countCriteriaQuery();
		}
		if (tipoBusca == TipoBusca.criteria) {
			return this.countCriteria();
		} else if (tipoBusca == TipoBusca.nativa) {
			return countNativa();
		} else {
			throw new RuntimeException("???");
		}
	}

	private int countNativa() {
		throw new RuntimeException("Não implementado");
	}

	private int countCriteriaQuery() {
		return new CriterioQuery<>(this, Long.class).count();
	}

	private int countCriteria() {

		List<Order> orderByOriginal = this.orderBy;
		this.orderBy = null;

		try {
			Criteria c = this.newCriteria();
			Criterio.atributosCriteria.get("maxResults").set(c, 1);
			List<?> orderEntries = Criterio.atributosCriteria.get("orderEntries").get(c);
			if (orderEntries.size() > 0) {
				orderEntries.clear();
			}
//			MonitorSqlBo.add(this, "count(*)", "x");
			Number number = (Number) c.setProjection(Projections.rowCount()).uniqueResult();
			if (number == null) {
				return 0;
			}
			return number.intValue();
		} catch (Exception e) {
			throw UException.runtime(e);
		} finally {
			this.observerExecutado = false;
			this.orderBy = orderByOriginal;
		}
	}
	private int limit = 0;
	private int page = 0;
	private int skip = 0;
	public Criterio<T> setLimit(int limit) {
		if (limit < -1) {
			this.limit = 0;
		} else {
			this.limit = limit;
		}
		return this;
	}
	public Criterio<T> limit(int limit) {
		return this.setLimit(limit);
	}
	public Criterio<T> order(Atributo atributo) {
		return this.order(atributo.getReal().nome());
	}
	public Criterio<T> order(String s) {
		return this.addOrderAsc(s);
	}
	public Criterio<T> desc(String s) {
		return this.addOrderDesc(s);
	}
	public Criterio<T> page(int page) {
		this.page = page;
		if ( this.limit == 0 ) {
			this.limit(20);
		}
		return this;
	}
	public Lst<T> list(EntityManager em) {
		this.em = em;
		return this.list();
	}
	public Lst<T> list() {
		return busca().list;
	}

	private P1<Lst<T>> afterBusca;

	private CriterioResult<T> busca() {
		CriterioResult<T> res = busca0();
		if (afterBusca != null) {
			afterBusca.call(res.list);
		}
		return res;
	}

	private CriterioResult<T> busca0() {
		if (tipoBusca == TipoBusca.criteriaQuery) {
			return this.buscaComCriteriaQuery();
		}
		if (tipoBusca == TipoBusca.criteria) {
			return this.buscaComCriteria();
		} else if (tipoBusca == TipoBusca.nativa) {
			return buscaComSql();
		} else {
			throw new RuntimeException("???");
		}
	}

	public List<T> list(ConexaoJdbc con) {
		MontarQueryNativa qn = this.getQueryNativa();
		qn.addSelect("id");
		UTable table = con.table( this.getClasse() );
		return table.selectAs("id in ("+qn.getSql()+")");
	}
	public Criterio<T> ignoreObserver() {
		this.ignoreObserver = true;
		return this;
	}
	private boolean observerExecutado = false;

	private CriterioResult<T> buscaComSql() {
		String sql = this.getSql();
		sql = sql.replace("\n", " ");
		sql = StringTrim.plus(sql);
		sql = sql.replace("a.*", "a");
		sql = sql.replace(" distinct ", " ");
		sql = sql.replace(" as ", " ");
		sql = sql.replace("prospect.", "");

		TypedQuery<T> createQuery;

		if (this.em == null) {
			createQuery = UConfig.jpa().createQuery(sql, this.classe);
		} else {
			createQuery = this.em.createQuery(sql, this.classe);
		}

		List<T> resultList = createQuery.getResultList();
		CriterioResult<T> result = new CriterioResult<>();
		result.s = sql;
		result.list = new Lst<>(resultList);
		return result;
	}

	@SuppressWarnings("unchecked")
	private CriterioResult<T> buscaComCriteria() {

		Criteria c;

		try {
			c = this.newCriteria();
		} catch (Exception e) {
			throw UException.runtime(e);
		}

		try {

			this.setOrderBy(c);

			Cronometro cronometro = new Cronometro();
			List<T> list = c.list();
			if (cronometro.tempo() > 5000) {
				UException.runtime("Consulta demorou: " + cronometro.tempo() + " >> " + c.toString());
			}

			CriterioResult<T> result = new CriterioResult<>();
			result.s = c.toString();
			result.list = new Lst<>(list);
//			MonitorSqlBo.add(this, "*", "*");
			return result;
		} catch (Exception e) {
			ULog.error( StringParse.get(c) );
			throw UException.runtime(e);
		}
	}

	private CriterioResult<T> buscaComCriteriaQuery() {
		return new CriterioQuery<>(this, classe).result();
	}

	private void setOrderBy(Criteria c) {
		if (!UList.isEmpty(this.orderBy)) {
			for (Order order : this.orderBy) {
				c.addOrder(order);
			}
		}
	}
	public T listUnique() {
		if (this.limit == 0) {
			this.limit(2);
		}
		return this.findUnique(this.busca());
	}
	public T unique(ConexaoJdbc con) {
		Integer id = this.uniqueId(con);
		if (id == null) {
			return null;
		}
		return con.table(this.getClasse()).byId(id);
	}
	public Integer uniqueId(ConexaoJdbc con) {
		MontarQueryNativa qn = this.getQueryNativa();
		qn.addSelect("id");
		String sql = qn.getSql();
		return con.selectInt(sql);
	}
	public T uniqueObrig() {
		return this.uniqueObrig("o == null");
	}
	public T uniqueObrig(String message) {
		T o = this.unique();
		if (o == null) {
			tryPrintSql();
			throw new RuntimeException(message);
		}
		return o;
	}

	private void tryPrintSql() {

		try {
			SystemPrint.ln(getSql());
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	public T unique() {
		return this.listUnique();
	}
	private T findUnique(CriterioResult<T> result) {
		if (result.list.isEmpty()) {
			return null;
		}
		if (result.list.size() > 1) {
			String message = "A lista retornou + de 1 resultado: " + this.classe.getName() + " - " + result.s;
			throw UException.runtime(message);
		}
		return result.list.get(0);
	}
	
	public Criterio<T> in(String field, List<?> list) {
		this.add(this.op_in(field, list));
		return this;
	}
	
	public void not_in(Collection<?> list) {
		this.add(this.restriction_not_in(list));
	}
	
	public void in_subSelect(Class<?> classe, String campo) {
		this.add( new QueryOperator_InSubSelect(classe, campo) );
	}
	private Collection<?> trataIds(Collection<?> ids) {
		if ( this.getId().isLong()) {
			List<Long> longs = new ArrayList<>();
			for (Object o : ids) {
				longs.add(ULong.toLong(o));
			}
			ids = longs;
		}
		return ids;
	}
	public Criterio<T> idIn(Collection<?> ids) {
		return this.add(this.restriction_in(this.getId(), this.trataIds(ids)));
	}
	public Criterio<T> idNotIn(Collection<?> ids) {
		return this.add(this.restriction_not_in(this.getId(), this.trataIds(ids)));
	}
	public void in(Collection<?> list) {
		QueryOperator_In op = this.restriction_in(list);
		this.add(op);
	}
	public Criterio<T> notInIgnoreEmpty(String field, Object... values) {
		if (values == null || values.length == 0) {
			return this.backIfOr();
		}
		return this.not_in(field, values);
	}
	public Criterio<T> not_in(String field, Object... values) {
		return this.add(this.restriction_not_in(field, values));
	}
	private void removeNulls(Collection<?> list) {

		list = UList.removeEmptys(list);

		if (list.isEmpty()) {
			return;
		}
		Object[] array = list.toArray();
		List<Object> itensToRemove = new ArrayList<>();
		for (Object o : array) {
			if ( o instanceof ObjetoComId ) {
				Object id = ExtraidId.exec(o);
				if (id == null) {
					itensToRemove.add(o);
				}
			}
		}
		list.removeAll(itensToRemove);
	}
	public Criterio<T> not_in(String field, Collection<?> list) {
		this.removeNulls(list);
		if (!list.isEmpty()) {
			this.add(this.restriction_not_in(field, list));
		}
		return this;
	}
	public Criterio<T> in(String field, Object... values) {
		this.add(this.restriction_in(field, values));
		return this;
	}
	public Criterio<T> in(String field, Collection<?> list) {
		this.add(this.op_in(field, list));
		return this;
	}
	public Criterio<T> maiorOuIgual(String field, Data data) {
		return this.maiorOuIgual(field, data.getCalendar().getTime());
	}
	public Criterio<T> menorOuIgual(String field, Data data) {
		return this.menorOuIgual(field, data.getCalendar().getTime());
	}
	public Criterio<T> menorOuIgual(Atributo a, Data data) {
		return this.menorOuIgual(a.nome(), data);
	}
	public Criterio<T> menorOuIgual(Atributo a, Object value) {
		if (value instanceof Data) {
			Data data = (Data) value;
			return this.menorOuIgual(a, data);
		}
		return this.menorOuIgual(a.nome(), value);
	}
	public Criterio<T> maiorOuIgual(Atributo a, Object value) {
		if (value instanceof Data) {
			Data data = (Data) value;
			value = data.getCalendar();
		}
//		if (value instanceof Integer) {
//			Integer data = (Integer) value;
//			return maiorOuIgual(a, data);
//		}
		return this.maiorOuIgual(a.nome(), value);
	}
	public Criterio<T> menor(Atributo a, Data data) {
		return this.menor(a.nome(), data);
	}
	public Criterio<T> menor(String field, Data data) {
		return this.menor(field, data.getCalendar().getTime());
	}
	public Criterio<T> maior(String field, Data data) {
		return this.maior(field, data.getCalendar().getTime());
	}
	public Criterio<T> maior(Atributo a, Numeric2 value) {
		return this.maior(a, value.getValor());
	}
	public Criterio<T> maior(Atributo a, BigDecimal value) {
		return this.maior(a.nome(), value);
	}
	public Criterio<T> menor(Atributo a, Numeric2 value) {
		return this.menor(a, value.getValor());
	}
	public Criterio<T> menor(Atributo a, BigDecimal value) {
		return this.menor(a.nome(), value);
	}
	public Criterio<T> maior(Atributo a, Data data) {
		return this.maior(a.nome(), data);
	}
	public Criterio<T> maior(Atributo a, Integer value) {
		return this.maior(a.nome(), value);
	}
	public Criterio<T> menor(Atributo a, Integer value) {
		return this.menor(a.nome(), value);
	}
	public Criterio<T> maiorOuIgual(String field, Object value) {
		this.add(this.restriction_maiorOuIgual(field, value));
		return this;
	}
	public Criterio<T> menorOuIgual(String field, Object value) {
		this.add(this.restriction_menorOuIgual(field, value));
		return this;
	}
	public Criterio<T> entre(String field, Periodo periodo) {
		Atributo f = this.as().get(field);
		Object v1;
		Object v2;
		if (f.getType().equals(Date.class)) {
			v1 = periodo.getInicio().toDate();
			v2 = periodo.getFim().toDate();
		} else if (f.getType().equals(Calendar.class)) {
			v1 = periodo.getInicio().getCalendar();
			v2 = periodo.getFim().getCalendar();
		} else {
			throw UException.runtime("Tipo desconhecido de data: " + f.getType());
		}
		this.entre(field, v1, v2);
		return this;
	}
	public Criterio<T> entre(Atributo a, Object value1, Object value2) {
		return this.entre(a.nome(), value1, value2);
	}
	public Criterio<T> entre(String field, Object value1, Object value2) {
		if (value1 == null) {
			throw UException.runtime("value1 == null");
		}
		if (value2 == null) {
			throw UException.runtime("value2 == null");
		}
		if (value1.equals(value2)) {
			this.eq(field, value1);
		} else {
			this.maiorOuIgual(field, value1);
			this.menorOuIgual(field, value2);
		}
		return this;
	}
	public Criterio<T> naoEntre(Atributo a, Object value1, Object value2) {
		return this.naoEntre(a.nome(), value1, value2);
	}
	public Criterio<T> naoEntre(String field, Object value1, Object value2) {
		this.menor(field, value1);
		this.maior(field, value2);
		return this;
	}
	public Criterio<T> menor(String field, Object value) {
		this.add(this.restriction_menor(field, value));
		return this;
	}
	public Criterio<T> maior(String field, Object value) {
		this.add(this.restriction_maior(field, value));
		return this;
	}
	public Criterio<T> isNull(String field) {
		this.add(this.restriction_isNull(field));
		return this;
	}
	public Criterio<T> isNull(Atributo a) {
		return this.isNull(a.getNomeValue());
	}
	public Criterio<T> isNotNull(Atributo a) {
		return this.isNotNull(a.getNomeValue());
	}
	public Criterio<T> isNotNull(String field) {
		this.add(this.restriction_isNotNull(field));
		return this;
	}
	public Criterio<T> eqResolve(String field, Object value, boolean considerarNulo) {
		value = this.resolve(value.getClass(), value);
		if (value == null && !considerarNulo) {
			return this.backIfOr();
		}
		this.eq(field, value);
		return this;
	}
	public Object resolve(Class<?> classe, Object o){
		if (o == null) {
			return null;
		}
		Object id = o;
		if ( UClass.isInstanceOf(o, classe) ) {
			Atributo atributoId = AtributosBuild.getId(classe);
			id = atributoId.get(o);
			if (id == null) {
				return null;
			}
		}
		try {
			if (this.em == null) {
				return UConfig.jpa().findById(classe, id);
			}
			return this.em.find(classe, id);
		} catch (Exception e) {}

		throw UException.runtime("Não foi possível resolver: " + classe + " >> " + o);
	}
	public Criterio<T> eqNn(String field, Object value) {
		if (value == null) {
			return this.backIfOr();
		}
		if (!UType.isPrimitiva(value)) {
			Object id = ExtraidId.exec(value);
			if (id == null) {
				return this.backIfOr();
			}
		}
		return this.eq(field, value);

	}
	public Criterio<T> eqNn(Object value) {
		if (value == null) {
			return this.backIfOr();
		}
		if (!UType.isPrimitiva(value)) {
			Object id = ExtraidId.exec(value);
			if (id == null) {
				return this.backIfOr();
			}
		}
		return this.eq(value);
	}
	public Criterio<T> eq(Object... values) {
		for (Object value : values) {
			this.eq(value);
		}
		return this;
	}
	public Criterio<T> eq(Object value) {
		if (value == null) {
			throw UException.runtime("value == null");
		}
		Class<?> type = UClass.getClass(value);

		Atributos atributos = this.as().getWhereType(type);

		if (atributos.isEmpty()) {
			Atributos as2 = this.as();
			for (Atributo field : as2) {
				if (UClass.a_herda_b(type, field.getType())) {
					atributos.add(field);
				}
			}
			if (atributos.isEmpty()) {
				throw UException.runtime("Nenhum field com o type = " + type.getName());
			}
		}
		if (atributos.size() > 1) {
			throw UException.runtime("Varios fields com o type = " + type.getName());
		}

		return this.eq(atributos.get(0), value);
	}
	public Criterio<T> eq(Atributo a, Object value) {
		return this.eq(a.getNomeValue(), value);
	}
	public Criterio<T> eq(String field, Object value) {
		this.add(this.restriction_eq(field, value));
		return this;
	}
	private QueryOperator _eqProperty(String a, String b) {
		return new QueryOperator_eqProperty(a, b);
	}

	public Criterio<T> eqProperty(String a, String b) {
		this.add(this._eqProperty(a,b));
		return this;
	}
	public Criterio<T> neProperty(String a, String b) {
		this.add(this._eqProperty(a,b).not());
		return this;
	}
	public Criterio<T> ne(int id) {
		return this.idNotIn(new ListInteger(id));
	}
	public Criterio<T> ne(Long id) {
		if (id == null) {
			return this.backIfOr();
		}
		return this.idNotIn(new ListLong(id));
	}
	public Criterio<T> ne(Atributo a, Object value) {
		return this.ne(a.nome(), value);
	}
	public Criterio<T> ne(String field, Object value) {
		this.add(this.restriction_ne(field, value));
		return this;
	}
	private Criterio<T> in(Atributo a, String nativeSubquery, boolean not) {
		QueryOperator_InNativeSubQuery op = new QueryOperator_InNativeSubQuery(a.getColumnName(), nativeSubquery);
		op.setNot(not);
		this.add(op);
		return this;
	}
	public Criterio<T> in(Atributo a, String nativeSubquery) {
		return this.in(a, nativeSubquery, false);
	}
	public Criterio<T> notIn(Atributo a, String nativeSubquery) {
		return this.in(a, nativeSubquery, true);
	}
	public Criterio<T> in(String nativeSubquery) {
		return this.in(this.as().getId(), nativeSubquery);
	}
	public Criterio<T> notIn(String nativeSubquery) {
		return this.notIn(this.as().getId(), nativeSubquery);
	}
	public Criterio<T> in(String field, String nativeSubquery) {
		if (field.endsWith(".id")) {
			field = StringBeforeLast.get(field, ".");
		}
		return this.in(this.as().get(field), nativeSubquery);
	}
	public Criterio<T> notIn(String field, String nativeSubquery) {
		return this.notIn(this.as().get(field), nativeSubquery);
	}
	public Criterio<T> like(Atributo atributo, String value) {
		return this.like(atributo.nome(), value);
	}
	public Criterio<T> like(String field, String value) {
		if (StringEmpty.is(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringContains(field, value));
		return this;
	}
	public Criterio<T> notLike(String field, String value) {
		if (StringEmpty.is(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringContains(field, value).not());
		return this;
	}
	public Criterio<T> startsWith(String field, String value) {
		if (StringEmpty.is(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringStartsWith(field, value));
		return this;
	}
	public Criterio<T> notStartsWith(String field, String value) {
		if (StringEmpty.is(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringStartsWith(field, value).not());
		return this;
	}
	public Criterio<T> endsWith(String field, String value) {
		if (StringEmpty.is(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringEndsWith(field, value));
		return this;
	}
	public Criterio<T> notEndsWith(String field, String value) {
		if (StringEmpty.is(value)) {
			return this.backIfOr();
		}
		this.add(new QueryOperator_StringEndsWith(field, value).not());
		return this;
	}
	public Criterio<T> ativos() {
		return this.ativos(true);
	}
	public Criterio<T> inativos() {
		return this.ativos(false);
	}
	public Criterio<T> ativos(boolean val) {
		Atributo ativo = this.as().getAtivo();
		if (ativo != null) {
			this.eq(ativo, val);
		}
		return this;
	}
	public Integer maxId() {
		Object o = this.max(this.getId());
		return IntegerParse.toInt(o);
	}
	public <X> X max(Atributo a) {
		return this.max(a.getField());
	}
	public <X> X max(Field field) {
		return this.max(field.getName());
	}
	public <X> X max(String field) {
		return maxComCriteria(field);
	}
	@SuppressWarnings("unchecked")
	private <X> X maxComCriteria(String field) {
		Criteria c = this.newCriteria();
		return (X) c.setProjection(Projections.max(field)).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public <X> X min(String field) {
		Criteria c = this.newCriteria();
//		MonitorSqlBo.add(this, "min("+field+")", "x");
		return (X) c.setProjection(Projections.min(field)).uniqueResult();
	}
	@SuppressWarnings("unchecked")
	public <X> X sum(String field) {
		Criteria c = this.newCriteria();
		Object o = c.setProjection(Projections.sum(field)).uniqueResult();
//		MonitorSqlBo.add(this, "sum("+field+")", "x");
		return (X) o;
	}
	@SuppressWarnings("unchecked")
	public <K, V> Map<K, V> sum(String groupField, String sumField, boolean sumFieldOrder, boolean ascOrder) {
		ProjectionList p = Projections.projectionList();
		p.add(Projections.groupProperty(groupField));
		p.add(Projections.sum(sumField));
		Criteria c = this.newCriteria();
		c.setProjection(p);
		List<Object[]> l = c.list();
		int index = sumFieldOrder ? 1 : 0;
		Comparator<Object[]> comparator = (a, b) -> {
			Comparable<Object> va = (Comparable<Object>) a[index];
			Comparable<Object> vb = (Comparable<Object>) b[index];
			int i = va.compareTo(vb);
			if (!ascOrder) {
				i = i * -1;
			}
			return i;
		};
		l.sort(comparator);
		LinkedHashMap<K, V> map = new LinkedHashMap<>();
		for (Object[] o : l) {
			K key = (K) o[0];
			V value = (V) o[1];
			map.put(key, value);
		}
		return map;
	}
	public <X> Lst<X> distinct(String campo) {
		if (tipoBusca == TipoBusca.criteriaQuery) {
			return distinctCriteriaQuery(campo);
		}
		if (tipoBusca == TipoBusca.criteria) {
			return distinctCriteria(campo);
		} else if (tipoBusca == TipoBusca.nativa) {
			return distinctNativa(campo);
		} else {
			throw new RuntimeException("???");
		}
	}
	private <X> Lst<X> distinctCriteriaQuery(String campo) {
		return new CriterioQuery<>(this, Object.class).distinct(campo);
	}
	private <X> Lst<X> distinctNativa(String campo) {
		throw new RuntimeException("???");
	}
	@SuppressWarnings("unchecked")
	private <X> Lst<X> distinctCriteria(String campo) {
		Criteria c = this.newCriteria();
		campo = this.addAlias(campo, c);
		List<?> list = c.setProjection(Projections.distinct(Projections.property(campo))).list();
		List<?> x = UList.removeEmptys(list);
		Lst<X> list2 = new Lst<>();
		for (Object o : x) {
			list2.add((X) o);
		}
		list2.sort(UCompare.instance);
		return list2;
	}
	private String addAlias(String campo, Criteria c) {
		return CriterioSetAlias.addAlias(this.aliases, campo, c);
	}
	private QueryOperator_In restriction_in(Atributo atributo, Collection<?> list) {
		return this.op_in(atributo.nome(), list);
	}
	private QueryOperator_In op_in(String string, Collection<?> list) {

		list = UList.removeEmptys(list);

		if (UList.isEmpty(list)) {
			throw UException.runtime("A lista esta vazia");
		}
		List<Object> l = new ArrayList<>();
		for (Object o : list) {
			o = this.resolveValue(string, o);
			l.add(o);
		}

		return new QueryOperator_In(string, l);

	}
	private QueryOperator_In restriction_not_in(Collection<?> list) {
		QueryOperator_In op = this.restriction_in(list);
		op.not();
		return op;
	}
	private QueryOperator_In restriction_in(Collection<?> list) {
		Atributo atributoId = UList.getId(list);
		ListInteger ids = new ListInteger();
		for (Object o : list) {
			Integer id = atributoId.get(o);
			if (!ids.contains(id)) {
				ids.add(id);
			}
		}

		return this.op_in("id", ids);
	}
	private QueryOperator_In restriction_not_in(Atributo atributo, Collection<?> list) {
		return this.restriction_not_in(atributo.nome(), list);
	}
	private QueryOperator_In restriction_not_in(String field, Object... values) {
		QueryOperator_In op = this.restriction_in(field, values);
		op.not();
		return op;
	}
	private QueryOperator_In restriction_not_in(String field, Collection<?> list) {
		QueryOperator_In op = this.op_in(field, list);
		op.not();
		return op;
	}
	private QueryOperator_In restriction_in(String field, Object... values) {
		List<Object> list = Arrays.asList(values);
		return this.op_in(field, list);
	}
	private QueryOperator_MaiorOuIgual restriction_maiorOuIgual(String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_MaiorOuIgual(field, value);
	}

	private QueryOperator_MenorOuIgual restriction_menorOuIgual(String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_MenorOuIgual(field, value);
	}

	public static Map<Class<JpaConverter<?,?>>, JpaConverter<?,?>> converters = new HashMap<>();

	public static Object resolveValue(Atributo atributo, Object value) {

		Convert convert = atributo.getAnnotation(Convert.class);

		if (convert != null) {

			Class<JpaConverter<?,?>> classe = UClass.getClass(convert.converter());

			JpaConverter<?,?> o = converters.get(classe);

			if (o == null) {
				o = UClass.newInstance(classe);
				converters.put(classe, o);
			}

			return o.toBancoCast(value);

		}

		if ((value instanceof IWrapper) || atributo.isEnum()) {
			return value;
		}

		Class<?> type = atributo.getType();

//		if (type.equals(IEntity.class)) {
//			throw UException.runtime("type == IEntity - Impossível resolver o valor de um tipo abstrato >> " + field);
//		}
		if (UType.isPrimitiva(type)) {
			return UType.tryCast(value, type);
		}
		try {

			if (UType.isPrimitiva(value)) {
				Object o = UConfig.jpa().findById(type, value);
				if (o != null) {
					return o;
				}
			} else {

				Atributos a = AtributosBuild.get(type.getClass());

				if (a.getId() != null) {
					Object id = a.getId().get(value);

					if (id == null) {
						throw UException.runtime("id == null");
					}

					Object o = UConfig.jpa().findById(type, id);
					if (o != null) {
						return o;
					}
				}

			}

		} catch (Exception e) {
			if ("id == null".equals(e.getMessage())) {
				throw UException.runtime(e);
			}
		}

		try {
			if (UClass.instanceOf(value.getClass(), type)) {
				return value;
			}
		} catch (Exception e) {}

		try {
			return UType.cast(value, type);
		} catch (Exception e) {}

		try {
			Atributos atributos = AtributosBuild.get(type);
			Atributo idAtributo = atributos.getId();
			Object id = idAtributo.get(value);
			Object o = UConfig.jpa().findById(type, id);
			if (o != null) {
				return o;
			}
		} catch (Exception e) {}

		try {
			Object o = UConfig.jpa().findById(type, IntegerParse.toInt(value) );
			if (o != null) {
				return o;
			}
		} catch (Exception e) {}

		try {
			Object o = UConfig.jpa().findById(type, value);
			if (o != null) {
				return o;
			}
		} catch (Exception e) {}

		throw UException.runtime("Não foi possível resolver o valor: " + type.getSimpleName() + "." + atributo.nome() + " >> " + value);
	}

	private Object resolveValue(String fieldName, Object value) {
		if (!fieldName.contains(".")) {
			Atributo atributo = this.as().getObrig(fieldName);
			return Criterio.resolveValue(atributo, value);
		}
		ListString list = ListString.byDelimiter(fieldName, ".");
		list.removeEmptys();

		Atributos atributos = this.as();

		Atributo field = null;
		while (!list.isEmpty()) {
			field = atributos.get(list.remove(0));
			if (field == null) {
				throw UException.runtime("field == null >> " + fieldName + " >> " + value);
			}
			if (UType.isPrimitiva(field.getType())) {
				if (!list.isEmpty()) {
					throw new RuntimeException("???");
				}
			} else {
				atributos = AtributosBuild.get(field.getType());
			}
		}
		return Criterio.resolveValue(field, value);
	}

	private QueryOperator_Menor restriction_menor(String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_Menor(field, value);
	}
	private QueryOperator_Maior restriction_maior(String field, Object value) {
		value = this.resolveValue(field, value);
		return new QueryOperator_Maior(field, value);
	}
//	private QueryOperator restriction_isNull(Atributo atributo) {
//		return restriction_isNull(atributo.nome());
//	}
	private QueryOperator restriction_isNull(String field) {
		return new QueryOperator_isNull(field);
	}
	private QueryOperator restriction_isNotNull(String field) {
		return new QueryOperator_isNull(field).not();
	}
	private QueryOperator restriction_eq(String field, Object value) {
		if (value == null) {
			return this.restriction_isNull(field);
		}
		value = this.resolveValue(field, value);
		if (value instanceof String) {
			return new QueryOperator_StringEquals(field, (String) value);
		}
		return new QueryOperator_eq(field, value);
	}
//	private QueryOperator restriction_eq(Atributo atributo, Object source) {
//		try {
//			if (!UClass.isInstanceOf(source, atributo.getType())) {
//				source = atributo.get(source);
//			}
//			if (source == null) {
//				return restriction_isNull(atributo);
//			}
//			source = resolveValue(atributo, source);
//			return restriction_eq(atributo.nome(), source);
//		} catch (Exception e) {
//			throw UException.runtime(e);
//		}
//	}
	private QueryOperator restriction_ne(String field, Object value) {
		if (UObject.isEmpty(value)) {
			return this.restriction_isNotNull(field);
		}
		return this.restriction_eq(field, value).not();
	}
	protected static void add(List<Criterion> expressions, Object criterion) {
		if (criterion instanceof SimpleExpression) {
			expressions.add((SimpleExpression) criterion);
			return;
		}
		if (criterion instanceof Disjunction) {
			Disjunction disjunction = (Disjunction) criterion;
			Iterable<Criterion> conditions = disjunction.conditions();
			for (Criterion c2 : conditions) {
				Criterio.add(expressions, c2);
			}
			return;
		}
		if (criterion instanceof NullExpression) {
			expressions.add((NullExpression) criterion);
			return;
		}
		if (criterion instanceof NotNullExpression) {
			expressions.add((NotNullExpression) criterion);
			return;
		}
		if (criterion instanceof InExpression) {
			expressions.add((InExpression) criterion);
			return;
		}
		if (criterion instanceof LikeExpression) {
			expressions.add((LikeExpression) criterion);
			return;
		}
		if (criterion instanceof org.hibernate.criterion.PropertyExpression) {
			expressions.add((org.hibernate.criterion.PropertyExpression) criterion);
			return;
		}
		if (criterion instanceof Conjunction) {
			Conjunction conjunction = (Conjunction) criterion;
			Iterable<Criterion> conditions = conjunction.conditions();
			for (Criterion c2 : conditions) {
				Criterio.add(expressions, c2);
			}
			return;
		}
		if (criterion instanceof PropertySubqueryExpression) {
			throw UException.runtime("PropertySubqueryExpression Não implementado");
		}
		if (criterion instanceof NotExpression) {
			expressions.add((NotExpression) criterion);
			return;
//			throw UException.runtime("NotExpression Não implementado");
		}
		if (criterion instanceof org.hibernate.criterion.SQLCriterion) {
			return;
//			throw UException.runtime("SQLCriterion Não implementado");
		}
		String erro = "ERRO: Criterio.add(List<Criterion> expressions, Object criterion):";
		erro += " Nao sei tratar este tipo de expressao: " + criterion.getClass();
		ULog.debug(erro);
		throw UException.runtime(erro);
	}
	public List<Criterion> getExpressions() {
		return CriterioSetAlias.getExpressions(this.newCriteria());
	}

	private ListString aliases;

	private void setAlias(Criteria c) {
		this.aliases = new ListString();
		CriterioSetAlias.set(c, this.aliases, this.orderBy);
	}

	private List<Order> orderBy;

//	private void addOrder(Order order) {
//		if (orderBy == null) {
//			orderBy = new ArrayList<>();
//		}
//		orderBy.add(order);
//	}

	private Criterio<T> addOrder(String campo, boolean asc) {

//		campo = CriterioSetAlias.putAliasInEL(campo);

		/* evita que a mesma coluna seja posta no orderby + de 1 vez.
		 * quando isso ocorre dah um erro no sqlserver
		 *  */
		if (this.orderBy != null) {
			for (Order orders : this.orderBy) {
				if (orders.getPropertyName().equals(campo)) {
					return this;
				}
			}
		}

		Order order;

//		TODO FIXME ver como resolver o problema de NullPrecedence no sqlServer

		if (asc) {
			order = Order.asc(campo);
//			order.nulls(NullPrecedence.FIRST);
		} else {
			order = Order.desc(campo);
//			order.nulls(NullPrecedence.LAST);
		}

		if (this.orderBy == null) {
			this.orderBy = new ArrayList<>();
		}
		this.orderBy.add(order);

		return this;
	}
	public void addOrderAsc(ListString list) {
		for (String s : list) {
			this.addOrder(s, true);
		}
	}
	public Criterio<T> addOrderAsc(String... campos) {
		ListString list = ListString.newFromArray(campos);
		this.addOrderAsc(list);
		return this;
	}
	public Criterio<T> desc(String... campos) {
		return this.addOrderDesc(campos);
	}

	public Criterio<T> addOrderDesc(String... campos) {
		for (String campo : campos) {
			this.addOrder(campo, false);
		}
		return this;
	}

	public static List<Order> getOrders(Criteria c) {

		@SuppressWarnings("unchecked")
		List<OrderEntry> list = (List<OrderEntry>) Criterio.atributosCriteria.get("orderEntries").get(c);
		List<Order> expressions = new ArrayList<>();

		for (OrderEntry entry : list) {
			Order o = entry.getOrder();
			expressions.add(o);
		}

		return expressions;

	}

	public Criterio<T> id(Object o) {
		this.eq(this.getId(), o);
		return this;
	}

	public T porId(Object id) {
		if (id == null) {
			return null;
		}
		return this.id(id).listUnique();
	}

	Lst<Lst<QueryOperator>> addsBack = new Lst<>();

	Lst<QueryOperator> adds = new Lst<>();

	public Lst<QueryOperator> getAdds() {
		return this.adds;
	}

	private Criterio<T> backIfOr() {

		QueryOperator last = UList.getLast(this.adds);

		if (last instanceof QueryOperator_or) {
			QueryOperator_or o = (QueryOperator_or) last;
			if (o.getRigth() == null) {
				this.adds.remove(last);
				QueryOperator left = o.getLeft();
				this.add(left);
			}
		}

		return this;

	}

	public Criterio<T> or(){

		if (this.adds.isEmpty()) {
			throw UException.runtime("Nada para comparar");
		}

		QueryOperator last = UList.getLast(this.adds);

		if (last instanceof QueryOperator_or) {
			QueryOperator_or o = (QueryOperator_or) last;
			if (o.getRigth() == null) {
				throw UException.runtime("Or já aberto");
			}
		}

		QueryOperator_or or = new QueryOperator_or();
		or.setLeft(last);
		this.adds.remove(last);
		this.add(or);
		return this;
	}

	private Criterio<T> add(QueryOperator criterion) {
		if (criterion == null) {
			return this;
		}
		QueryOperator last = UList.getLast(this.adds);

		if (last instanceof QueryOperator_or) {
			QueryOperator_or or = (QueryOperator_or) last;
			if (or.getRigth() != null) {
				this.adds.add(criterion);
				return this;
			}
			or.setRigth(criterion);
		} else {
			this.adds.add(criterion);
		}

		return this;
	}

	public void abreParenteses() {
		this.addsBack.add(this.adds);
		this.adds = new Lst<>();
	}

	public boolean fechaParenteses() {
		if ( this.addsBack.isEmpty() ) {
			return false;
		}
		QueryOperator_and and = null;
		if (!this.adds.isEmpty()) {
			and = new QueryOperator_and();
			for (QueryOperator o : this.adds) {
				and.add(o);
			}
		}
		this.adds = UList.removeLast( this.addsBack );
		if (and != null) {
			this.add(and);
		}
		return true;
	}

	public boolean exists() {
		return this.count() > 0;
	}

	public T last() {
		return this.limit(1).desc(this.getId().nome()).unique();
	}

	public T first() {
		return this.limit(1).unique();
	}

	public T aleatorio() {
		// TODO implementar o count
		return this.page(Aleatorio.get(1, 100)).limit(1).unique();
	}

	public Criterio<T> eqAnoMes(String campo, Calendar data) {
		return this.eqAnoMes(campo, new Data(data));
	}

	public Criterio<T> eqAnoMes(String campo, Data data) {
		data = data.copy();
		data.setDia(1);
		data.zeraTime();
		this.maiorOuIgual(campo, data.getCalendar());
		data.addMes();
		this.menor(campo, data.getCalendar());
		return this;
	}

	public Criterio<T> eqAnoMesDia(String campo, Data data){
		data.zeraTime();
		data = data.copy();
		data.zeraTime();
		maiorOuIgual(campo, data.getCalendar());
		data = data.copy();
		data.add();
		menor(campo, data.getCalendar());
		return this;
	}

	public Criterio<T> notEqAnoMesDia(String campo, Data data){
		data.zeraTime();
		data = data.copy();
		data.zeraTime();
		menor(campo, data.getCalendar());
		data = data.copy();
		data.add();
		or();
		maiorOuIgual(campo, data.getCalendar());
		return this;
	}

	public Criterio<T> isHoje(String campo) {
		return eqAnoMesDia(campo, Data.now());
	}

	public Criterio<T> isNotHoje(String campo) {
		return notEqAnoMesDia(campo, Data.now());
	}

	public Criterio<T> between(Calendar d, String campo1, String campo2) {
		this.menorOuIgual(campo1, d);
		this.maiorOuIgual(campo2, d);
		return this;
	}

	public Lst<T> delete() {
		Obrig.check(comoDeletar);
		
		Lst<T> lst = new Lst<>();
		
		boolean loop = limit == 0;
		
		while (true) {
			
			Lst<T> list = list();
			
			if (list.isEmpty()) {
				break;
			}
			
			list.forEach(o -> comoDeletar.call(o));
			lst.addAll(list);
			
			if (!loop) {
				break;
			}
			
		}
		
		return lst;
		
	}

	public Lst<T> save() {
		Obrig.check(comoSalvar);
		Lst<T> list = list();
		list.forEach(o -> comoSalvar.call(o));
		return list;
	}

	public Criterio<T> noLimit() {
		return this.limit(-1);
	}

	public Criterio<T> busca(String text) {
		text = UString.toCampoBusca(text);
		return this.like("busca", text);
	}

//	private boolean considerarCompartilhamento = true;

//	public Criterio<T> considerarCompartilhamento() {
//		considerarCompartilhamento = true;
//		return this;
//	}
	@Override
	public String toString() {
		return this.newCriteria().toString();
	}

	public String getSql(){
		return this.getQueryNativa().getSql();
	}
	public MontarQueryNativa getQueryNativa() {
		MontarQueryNativa m = new MontarQueryNativa(this.getClasse());
		m.setLimit( this.getLimit() );
		m.setOffSet( this.offSet() );
		m.setOps( this.getAdds() );
		m.setOrders( this.getOrderBy() );
		return m;
	}

//	public String getSql2(){
//
//		Criteria c0 = newCriteria();
//		setOrderBy(c0);
//
//		CriteriaImpl criteriaImpl = (CriteriaImpl) c0;
//		SessionImplementor session = criteriaImpl.getSession();
//		SessionFactoryImplementor factory = session.getFactory();
//		CriteriaQueryTranslator translator=new CriteriaQueryTranslator(factory,criteriaImpl,criteriaImpl.getEntityOrClassName(),CriteriaQueryTranslator.ROOT_SQL_ALIAS);
//		String[] implementors = factory.getImplementors( criteriaImpl.getEntityOrClassName() );
//
//		CriteriaJoinWalker walker = new CriteriaJoinWalker((OuterJoinLoadable)factory.getEntityPersister(implementors[0]),
//		                        translator,
//		                        factory,
//		                        criteriaImpl,
//		                        criteriaImpl.getEntityOrClassName(),
//		                        session.getLoadQueryInfluencers()   );
//
//		String sql=walker.getSQLString();
//		return sql;
//
//	}

	public int offSet() {

		if (this.skip > 0) {
			return this.skip;
		}

		int p = this.getPage();
		if (p < 2) {
			return 0;
		}
		return (this.page-1)*30;
	}

	public void addNative(String sql) {
		QueryOperator_NativeSql nativeSql = new QueryOperator_NativeSql(sql);
		this.add(nativeSql);
	}

}
