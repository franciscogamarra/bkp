package br.sicoob.src.app.shared.forms;

import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.JS;
import src.commom.utils.array.Itens;

public class Form extends JS {
	
	private final FormGroup form = new FormGroup(obj());
	
	private Itens<FormInput<?>> inputs = new Itens<>();

	public void add(FormInput<?> input) {
		inputs.add(input);
		form.addControl(input.getName(), input.getControl());
	}

	public void clear() {
		inputs.forEach(i -> i.clear());
	}
	
	public void clearValidators() {
		inputs.forEach(i -> i.clearValidators());
	}

	public void clearErrors() {
		inputs.forEach(i -> i.clearErrors());
	}

	public boolean isValid() {
		return form.valid;
	}
	
	public FormGroup getForm() {
		return form;
	}
	
}