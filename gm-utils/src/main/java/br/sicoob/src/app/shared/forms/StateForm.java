package br.sicoob.src.app.shared.forms;

import br.sicoob.SicoobTranspilar;
import gm.languages.ts.angular.forms.FormGroup;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.lambda.F0;

public class StateForm {

	private F0<FormGroup> getForm;

	public StateForm(F0<FormGroup> getForm) {
		this.getForm = getForm;
	}
	
	public FormGroup get() {
		return getForm.call();
	}

	public StateFormControl build(String field) {
		return new StateFormControl(this, field);
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(StateForm.class);
	}

}
