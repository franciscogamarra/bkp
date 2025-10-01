package gm.utils.jpa.select;

import java.util.Arrays;
import java.util.Collection;

import gm.utils.comum.IWrapper;
import gm.utils.comum.Lst;
import gm.utils.comum.UAssert;
import gm.utils.jpa.criterions.Criterio;
import gm.utils.jpa.criterions.MontarQueryNativa;
import src.commom.utils.string.StringEmpty;

public class SelectTyped<TS extends SelectBase<?,?,?>, T> {
	private String campo;
	public String getCampo() {
		return campo;
	}
	protected TS ts;
	public SelectTyped(TS x, String campo) {
		this.ts = x;
		String prefixo = x.getPrefixo();
		if (StringEmpty.is(prefixo)) {
			this.campo = campo;
		} else {
			this.campo = prefixo+"."+campo;
		}
	}
	protected Criterio<?> c() {
		return ts.getC();
	}
	public TS eq(T value) {
		if (value == null) {
			isNull();
		} else {
			c().eq(campo, value);
		}
		return ts;
	}
	public TS eq(IWrapper<T> value) {
		c().eq(campo, value);
		return ts;
	}
	public TS eqProperty(String value) {
		c().eqProperty(campo, value);
		return ts;
	}
	public TS ne(T value) {
		if (value == null) {
			isNotNull();
		} else {
			c().ne(campo, value).or().isNull(campo);
		}
		return ts;
	}
	public TS ne(IWrapper<T> value) {
		c().ne(campo, value).or().isNull(campo);
		return ts;
	}
	public TS isNull() {
		c().isNull(campo);
		return ts;
	}
	public TS isNotNull() {
		c().isNotNull(campo);
		return ts;
	}
	public TS asc(){
		c().order(campo);
		return ts;
	}
	public TS desc(){
		c().addOrderDesc(campo);
		return ts;
	}
	public TS in(Collection<T> list){
		c().in(campo, list);
		return ts;
	}

	public TS inSubQuery(MontarQueryNativa nativeSubQuery){
		nativeSubQuery.addSelect("id", "id");
		return inSubQuery(nativeSubQuery.getResult().toString(" "));
	}
	public TS inSubQuery(String nativeSubQuery){
		c().in(campo, nativeSubQuery);
		return ts;
	}
	@SuppressWarnings("unchecked")
	public TS in(T... list){
		return in(Arrays.asList(list));
	}
	public TS notIn(Collection<T> list){
		c().not_in(campo, list);
		return ts;
	}
	@SuppressWarnings("unchecked")
	public TS notIn(T... list){
		return notIn(Arrays.asList(list));
	}
	public Lst<T> distinct(){
		return c().distinct(campo);
	}
	protected void checkNotNull(Object value) {
		UAssert.notEmpty(value, "value == null");
	}
}
