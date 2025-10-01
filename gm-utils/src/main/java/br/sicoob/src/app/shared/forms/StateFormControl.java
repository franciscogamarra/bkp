package br.sicoob.src.app.shared.forms;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import br.sicoob.src.app.shared.utils.Utils;
import gm.languages.ts.angular.forms.FormControl;
import gm.languages.ts.angular.forms.ValidatorFn;
import gm.languages.ts.javaToTs.JS;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.lambda.P1;
import js.Js;
import js.array.Array;
import js.support.JSON;
import src.commom.utils.object.Null;

public class StateFormControl {

	private StateForm stateForm;
	private String field;
	private boolean bloqueado = false;

	public StateFormControl(StateForm stateForm, String field) {
	    this.stateForm = stateForm;
		this.field = field;
	}

	public void bloqueia() {
		bloqueado = true;
	}

	public void desbloqueia() {
		bloqueado = false;
	}

	public Object get() {
		return getControl().value;
	}

	public Num getMoney() {
		return Num.fromNumber(2, Js.Number(getControl().value));
	}
	
	public boolean setNum(Num value) {
		return setMoney(value.toDouble());
	}
	
	public boolean setMoney(Double value) {
		if (Null.is(value)) {
			return set(0);
		} else {
			return set(Num.fromNumber(2, Utils.toDouble(value)).toDouble());
		}
	}
	
	public boolean set(Object value) {
		
		if (bloqueado) {
			throw new Error("bloqueado");
		}
		
		Object old = get();
		
		if (old == value) {
			return false;
		}
		
		if (Null.is(old) || Null.is(value)) {
//			console.log("field", field);
//			console.log("de", old);
//			console.log("para", value);
			getControl().setValue(value);
			return true;
		}
		
		String a = JSON.stringify(old);
		String b = JSON.stringify(value);
		
		if (a == b) {
			return false;
		}
		
//		console.log("field", field);
//		console.log("de", a);
//		console.log("para", b);
		getControl().setValue(value);
		return true;
	}
	
	public void subscribe(P1<Object> func) {
		getControl().valueChanges.subscribe(func);
	}
	
	public void setValidators(Array<ValidatorFn> array) {
		getControl().setValidators(array);
	}
	
	public void setErrors(Object errors) {
		getControl().setErrors(errors);
	}
	
	private FormControl getControl() {
		
		if (Js.inJava) {
			if (stateForm.get().get(field) == null) {
				throw new RuntimeException(field);
			}
		}
		
		return JS.as(stateForm.get().get(field), FormControl.class);
//		return (FormControl) getForm.call().get(field);
	}
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(StateFormControl.class);
	}

}