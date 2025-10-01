package br.sicoob.src.app.shared.forms;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.shared.utils.Str;
import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.ValidatorFn;
import gm.languages.ts.angular.forms.Validators;
import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import gm.languages.ts.javaToTs.exemplo.services.Utils;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.lambda.P2;
import js.array.Array;
import src.commom.utils.array.Itens;
import src.commom.utils.object.Null;
import src.commom.utils.object.UJson;
import src.commom.utils.string.StringRight;

public abstract class FormInput<T> extends JS {
	
	private final FormControl control = new FormControl("");
	private String valorAntesDaMudanca;
	private final Itens<P2<T,T>> afterChangeObservers = new Itens<>();
	private boolean required;
	private final String name;
	
	public FormInput(String name, boolean required) {
		this.name = name;
		control.valueChanges.subscribe(value -> afterChange(value));
		setRequired(required);
	}
	
	public void setRequired(boolean required) {
		this.required = required;
		setValidators();
	}
	
	private void setValidators() {
		
		Array<ValidatorFn> array = new Array<>();
		
		if (required) {
			array.push(Validators.required);
		}
		
		control.setValidators(array);
		
	}
	
	private void afterChange(Object value) {
		
		String ss = asString(value);
		
		Str s = new Str().set(ss);
		s.sett(s.getSomenteNumeros());
		
		if (!s.eq(ss)) {
			control.setValue(s.get());
			return;
		}
		
		if (s.eq(valorAntesDaMudanca)) {
			return;
		}
		
		afterChangeObservers.forEach(func -> func.call(parseT(valorAntesDaMudanca), get()));
		
	}
	
	public void addObserver(P2<T,T> func) {
		afterChangeObservers.add(func);
	}
	
	private String asString(Object value) {
		return Null.is(value) ? "" : value + "";
	}
	
	private String getControlValue() {
		return asString(control.value);
	}
	
	@PodeSerNull
	public T get() {
		
		if (isNull_(control.value)) {
			return null;
		}
		
		return parseT(getControlValue());
		
	}
	
	protected void setPrivate(@PodeSerNull T o) {
		
		String s;
		
		if (isNull_(o)) {
			s = "";
		} else {
			s = "" + o;
		}
		
		String atual = getControlValue();

		if (atual == s) {
			return;
		}
		
		valorAntesDaMudanca = atual;
		control.setValue(s);
		
	}
	
	@PodeSerNull
	protected abstract T parseT(String s);

	public void clear() {
		setPrivate(null);
	}
	
	public void clearValidators() {
		setRequired(false);
	}
	
	public void clearErrors() {
		control.setErrors(null);
	}
	
	public boolean isInvalid() {
		return Utils.getInputInvalida(control);
	}
	
	public boolean isEmpty() {
		return isNull_(get());
	}
	
	public String getName() {
		return name;
	}
	
	public FormControl getControl() {
		return control;
	}
	
	public void setError(String key, boolean value) {
		String s = UJson.itemBoolean(key, value);
		s = "{" + StringRight.ignore1(s) + "}";
		Object o = jsonParse(s, Object.class);
		control.setErrors(o);
	}
	
	public void updateValueAndValidity() {
		control.updateValueAndValidity();
	}
	
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(FormInput.class);
	}
	
}
