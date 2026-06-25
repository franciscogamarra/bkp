package br.gerar;

import br.support.comum.Lst;
import br.support.exceptions.DevException;
import br.support.lambda.F0;
import br.support.strings.StringToTitulo;

public class Campo {

	final String nome;
	final CampoTipo tipo;
	
	private static final Lst<String> nomesUtilizados = new Lst<>();
	
	public Campo(String nome, CampoTipo tipo) {
		
		if (nomesUtilizados.contains(nome)) {
			throw DevException.build("nome ja utilizado: " + nome);
		}
		
		nomesUtilizados.add(nome);

		this.nome = nome;
		this.tipo = tipo;
		
		title = StringToTitulo.exec(nome);
		
		if (tipo == CampoTipo.select) {
			nullValue = "SelectOption.NULL";
		}
		
	}
	
	boolean required = true;
	String options;
	
	String nullValue;
	public Campo nullValue(String s) {this.nullValue = s; return this;}

	String dto = "SimpleOptionDto";
	public Campo dto(String s) {this.dto = s; return this;}
	
	String title;
	public Campo title(String s) {this.title = s; return this;}
	
	int cols = 3;
	public Campo cols(int v) {this.cols = v; return this;}
	
	int rows = 0;
	public Campo rows(int v) {this.rows = v; return this;}

	int casasInteiras = -1;
	public Campo casasInteiras(int v) {this.casasInteiras = v; return this;}
	
	int casasDecimais = -1;
	public Campo casasDecimais(int v) {this.casasDecimais = v; return this;}
	
	String container = "campos";
	public Campo container(String s) {this.container = s; return this;}

	boolean readOnly = false;
	public Campo readOnly() {this.readOnly = true; this.required = false; return this;}
	
	boolean showTextoErro = true;
	public Campo showTextoErroFalse() {this.showTextoErro = false; return this;}

	boolean bold = false;
	public Campo bold() {this.bold = true; return this;}
	
	boolean last;
	public Campo last() {last = true; return this;}


	final Lst<F0<String>> visivelSe = new Lst<>();

	public void visivelSe(Campo campo) {
		visivelSe.add(() -> {
			String s = "this." + campo.nome + ".isVisible()";
			if (campo.isBoolean()) {
				s += " && this." + campo.nome + ".getValueFinal() === true";
			} else {
				s += " && !this." + campo.nome + ".isEmpty()";
			}
			return s;
		});
	}

	private boolean isBoolean() {
		return "Campo.booleanOptions".equals(options);
	}

	@Override
	public String toString() {
		return nome;
	}
	
}