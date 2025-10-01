package br.sicoob.src.app.shared.utils;

import gm.languages.ts.javaToTs.JS;
import gm.languages.ts.javaToTs.annotacoes.PodeSerNull;
import js.Js;

public class Str extends JS {

	private String value = "";

	public Str set(String value) {

		if (isNull_(value)) {
			this.value = "";
		} else {
			this.value = value;
		}
		
		return this;

	}
	
	public Str sett(Str value) {
		set(value.get());
		return this;
	}

	public String get() {
		return value;
	}
	
	public boolean isEmpty() {
		return length() == 0;
	}
	
	public int length() {
		return value.length();
	}

	public void setTrim() {
		set(value.trim());
	}
	
	public void clear() {
		set("");
	}

	public boolean eq(String s) {
		
		if (isNull_(s)) {
			return isEmpty();
		}
		
		return value == s;
		
	}
	
	@Override
	public Str clone() {
		return new Str().set(value);
	}
	
	public Str extraiLeft(int count) {
		
		if (isEmpty()) {
			return clone();
		}
		
		if (count >= length()) {
			Str s = clone();
			clear();
			return s;
		}

		Str s = new Str().set(value.substring(0, count));
		set(value.substring(count));
		return s;
		
	}

	public Str getSomenteNumeros() {
		
		Str res = new Str();
		
		if (isEmpty()) {
			return res;
		}
		
		Str s = clone();
		
		while (!s.isEmpty()) {
			Str x = s.extraiLeft(1);
			if (x.isInt()) {
				res.add(x);
			}
		}
		
		return res;
		
	}
	
	private void add(Str x) {
		set(value + x.value);
	}

	public boolean isInt() {
		return !Js.isNaN(Js.parseInt(value));
	}
	
	@PodeSerNull
	public Integer toInt() {
		
		if (isEmpty()) {
			return null;
		}
		
		if (isInt()) {
			return Js.parseInt(value);
		}
		
		throw new Error("O valor não é um inteiro: " + value);
		
	}
	
	public String substring(int start, int end) {
		return value.substring(start, end);
	}
	
	public Str substr(int start, int count) {
		return new Str().set(substring(start, start + count));
	}
	
	public Str charAt(int index) {
		
		if (index >= length() || index < 0) {
			return new Str();
		}
		
		return substr(index, 1);
		
	}

	public void truncate(int max) {
		if (value.length() > max) {
			set(value.substring(0, max));
		}
	}
	
}
