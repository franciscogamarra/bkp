package gm.utils.jpa.select;

import java.util.List;

import gm.utils.abstrato.GetIdLong;
import gm.utils.comum.Lst;
import gm.utils.comum.UList;
import gm.utils.exception.UException;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.SqlNative;
import gm.utils.jpa.TableSchema;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.criterions.MontarQueryNativa;
import gm.utils.lambda.F0;
import gm.utils.lambda.P1;
import gm.utils.number.ListInteger;
import lombok.Getter;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringEmpty;

@Getter
public abstract class SelectBase<ORIGEM, T extends GetIdLong, TS extends SelectBase<ORIGEM, T, TS>> {

	protected Criterio<?> c;
	private String prefixo;
	private Class<T> classe;
	private ORIGEM origem;
	private String campo;

	public void setC(Criterio<?> c) {
		this.c = c;
	}

	@SuppressWarnings("unchecked")
	public SelectBase(ORIGEM origem, Criterio<?> criterio, String prefixo, Class<T> classe) {
		if (origem == null) {
			this.origem = (ORIGEM) this;
		} else {
			this.origem = origem;
		}
		this.c = criterio;
		this.classe = classe;

		if (StringEmpty.is(prefixo)) {
			prefixo = "";
			campo = "id";
		} else {
			while (prefixo.startsWith(".")) {
				prefixo = prefixo.substring(1);
			}
			campo = prefixo + ".id";
		}
		this.prefixo = prefixo;
	}

//	public void delete() {
//		List<?> list = list();
//		if (list.isEmpty()) {
//			return;
//		}
//		Class<? extends IdObject> classe = UClass.getClass(list.get(0));
//		persisterMaster.get(classe).deleteCast(list);
//	}
//	public void save() {
//		List<?> list = list();
//		if (list.isEmpty()) {
//			return;
//		}
//		Class<?> classe = UClass.getClass(list.get(0));
//		persisterMaster.get(classe).saveCast(list);
//	}
	public int deleteNative() {
		MontarQueryNativa qn = getC().getQueryNativa();
		qn.setSelect(null);
		qn.addSelect("id");
		String sql = qn.getSql();
		sql = "delete from " + TableSchema.get(classe) + " where id in (" + sql + ")";
		return getC().getEm().createNativeQuery(sql).executeUpdate();
//		return SqlNative.execSQL(sql);
	}

	@Override
	public String toString() {
		return c.toString();
	}

//	@SuppressWarnings("unchecked")
//	public T byId(int id) {
//		if (con == null) {
//			if (getC().getEm() == null) {
//				return (T) persisterMaster.get(classe).get(id);
//			} else {
//				return getC().getEm().find(classe, id);
//			}
//		} else {
//			getC().id(id);
//			return unique(con);
//		}
//	}

	@SuppressWarnings("unchecked")
	private TS THIS() {
		return (TS) this;
	}

	public TS limit(int x) {
		getC().limit(x);
		return THIS();
	}

	public TS abreParenteses() {
		getC().abreParenteses();
		return THIS();
	}
	public TS fechaParenteses() {
		getC().fechaParenteses();
		return THIS();
	}

	public TS or() {
		getC().or();
		return THIS();
	}

	public TS page(int x) {
		getC().page(x);
		return THIS();
	}

	public TS skip(int x) {
		getC().skip(x);
		return THIS();
	}

	public static ConexaoJdbc con;

	@SuppressWarnings("unchecked")
	public <XX extends GetIdLong> XX unique() {
		if (con == null) {
			return (XX) getC().unique();
		}
		return unique(con);
	}

//	@SuppressWarnings("unchecked")
//	public <XX> XX uniqueJoinIf() {
//		List<XX> list = (List<XX>) getC().list();
//		if (list.isEmpty()) {
//			return null;
//		}
//		XX result = list.remove(0);
//		while (!list.isEmpty()) {
//			XX x = list.remove(0);
//			delete(x);
//		}
//		return result;
//	}

//	private <XXX> void delete(XXX o) {
//		Class<XXX> classe = UClass.getClass(o);
//		persisterMaster.get(classe).deleteCast(o);
//	}

	public <XX extends GetIdLong> XX uniqueObrig(F0<RuntimeException> exception) {
		XX o = unique();
		if (o == null) {
			throw exception.call();
		}
		return o;
	}

	@SuppressWarnings("unchecked")
	public <XX extends GetIdLong> XX uniqueObrig() {
		return (XX) getC().uniqueObrig();
	}

	public <XX extends GetIdLong> XX unique(Class<XX> classe) {
		return unique();
	}

	@SuppressWarnings("unchecked")
	public <XX> XX unique(ConexaoJdbc con) {
		return (XX) getC().unique(con);
	}

	public <XX> XX uniqueObrig(ConexaoJdbc con) {
		XX o = unique(con);
		if (o == null) {
			throw UException.runtime("o == null");
		}
		return o;
	}

	public int count() {
		return getC().count();
	}

	public int paginas() {
		int count = count();
		int limit = getC().getLimit();
		int paginas = count / limit;
		if (paginas * limit < count) {
			paginas++;
		}
		return paginas;
	}

	@SuppressWarnings("unchecked")
	public <X> X first() {
		return (X) getC().first();
	}

	public boolean exists() {
		return getC().exists();
	}

	public TS ignoreObserver() {
		getC().ignoreObserver();
		return THIS();
	}

	public TS withNolock() {
		getC().setNoLock(true);
		return THIS();
	}
	
	public Long getId() {
		GetIdLong o = unique();
		return o == null ? null : o.getId();
	}

	public Lst<Long> ids() {
		List<T> list = list();
		Lst<Long> ids = new Lst<>();
		for (T o : list) {
			ids.add(o.getId());
		}
		ids.sortLong(i -> i);
		return ids;
	}

	public <X> void exec(Class<X> classe, P1<X> func) {
		Lst<X> list = list();
		for (X x : list) {
			func.call(x);
		}
	}
	
	@SuppressWarnings("unchecked")
	public <X> Lst<X> list() {
		if (con == null) {
			beforeSelect();
			return (Lst<X>) getC().list();
		}
		return list(con);
	}

	@SuppressWarnings("unchecked")
	public <X> Lst<X> list(ConexaoJdbc con) {
		beforeSelect();
		return (Lst<X>) getC().list(con);
	}

	protected void beforeSelect() {}

	public Lst<T> distinct() {
		String s = campo;
		if (s.endsWith(".id")) {
			s = StringBeforeLast.get(s, ".");
		}
		return c.distinct(s);
	}
	public ORIGEM eq(T value) {
		if (value == null || value.getId() == null) {
			c.isNull(campo);
		} else {
			c.eq(campo, value);
		}
		return origem;
	}

	public ORIGEM ne(T value) {
		if (value == null || value.getId() == null) {
			c.isNotNull(campo);
		} else {
			c.ne(campo, value);
		}
		return origem;
	}

	public ORIGEM ne(Integer id) {
		if (id == null) {
			c.isNotNull(campo);
		} else {
			notIn(id);
		}
		return origem;
	}

	public ORIGEM eqProperty(String s) {
		c.eqProperty(campo, s);
		return origem;
	}
	public ORIGEM neProperty(String s) {
		c.neProperty(campo, s + ".id");
		return origem;
	}
	
	
	/* ================================ */
	
	public ORIGEM inIf(List<T> itens) {
		if (itens.isEmpty()) {
			return origem;
		}
		return in(itens);
	}
	
	public ORIGEM notInIf(List<T> itens) {
		if (itens.isEmpty()) {
			return origem;
		}
		return notIn(itens);
	}

	/* ================================ */

	public ORIGEM in(List<T> itens) {
		c.in(campo, itens);
		return origem;
	}

	public ORIGEM notIn(List<T> itens) {
		c.not_in(campo, itens);
		return origem;
	}

	/* ================================ */

	@SuppressWarnings("unchecked")
	public ORIGEM in(T... itens) {
		List<T> list2 = UList.asList(itens);
		return in(list2);
	}

	@SuppressWarnings("unchecked")
	public ORIGEM notIn(T... itens) {
		List<T> list2 = UList.asList(itens);
		return notIn(list2);
	}

	/* ============================ */


	public ORIGEM notIn(TS select) {
		throw UException.runtime("implemnetar");
//		return origem;
	}
	
	public ORIGEM notIn(Integer... ids) {
		return notIn(ListInteger.array(ids));
	}

	public ORIGEM notIn(ListInteger ids) {

		String s = getPrefixo();

		if (StringEmpty.is(s)) {
			s = "id";
		} else {
			s += ".id";
		}

		getC().not_in(s, ids);

		return origem;

	}
	
	public TS notIn(String sql) {
		ListInteger ids = SqlNative.getInts(sql);
		c.idNotIn(ids);
		return THIS();
	}

	/* ================================= */
	
	public ORIGEM isNull() {
		c.isNull(campo);
		return origem;
	}
	public ORIGEM isNotNull() {
		c.isNotNull(campo);
		return origem;
	}
	public TS inSubQuery(String sql) {
		ListInteger ids = SqlNative.getInts(sql);
		c.in("id", ids);
		return THIS();
	}
	
	public String getSql() {
		return getC().getSql();
	}

	public Lst<?> save() {
		return c.save();
	}

	public Lst<?> delete() {
		return c.delete();
	}
	
	public abstract SelectLong<?> id();
	
}
