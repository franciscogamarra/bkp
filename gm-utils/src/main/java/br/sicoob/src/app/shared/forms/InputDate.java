package br.sicoob.src.app.shared.forms;

import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import js.Js;
import js.outros.Date;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.date.BaseData;
import src.commom.utils.string.StringParse;

@Getter @Setter
public class InputDate extends FormInput<Date> {
	
	public InputDate(String name, boolean required) {
		super(name, required);
	}
	
	public InputDate set(Date o) {
		setPrivate(o);
		return this;
	}

	@Override @PodeSerNull
	protected Date parseT(String s) {
		@PodeSerNull BaseData o = BaseData.desvenda(s);
		return isNull_(o) ? null : o.toDate();
	}
	
	public InputDate setCast(Object o) {
		
		if (isNull_(o)) {
			clear();
			return this;
		}
		
		if (Js.typeof(o) == "string") {
			
			String s = StringParse.get(o);
			
			@PodeSerNull Date value = parseT(s);
			
			if (isNull_(value)) {
				clear();
				return this;
			} else {
				return set(value);
			}
			
		}
		
		return this;
		
	}
	
}