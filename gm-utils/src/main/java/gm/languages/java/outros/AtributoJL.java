package gm.languages.java.outros;

import java.lang.annotation.Annotation;

import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;

import gm.languages.java.expressoes.AnnotationJava;
import gm.languages.java.expressoes.DeclaracaoDeVariavel;

public class AtributoJL {

	private DeclaracaoDeVariavel dec;

	public AtributoJL(DeclaracaoDeVariavel dec) {
		
		this.dec = dec;
		if (!dec.isAtributo()) {
			throw new RuntimeException();
		}
		
	}
	
	public String getNome() {
		return dec.getNome().getS();
	}
	
	
	private String getName(Class<? extends Annotation> classe) {
		AnnotationJava a = dec.getAnnotation(classe);
		return a == null ? null : a.getValue("name");
	}
	
	public String getNomeBanco() {
		
		String s = getName(Column.class);
		
		if (s == null) {
			s = getName(JoinColumn.class);
			if (s == null) {
				s = getNome();
			}
		}
		
		return s;
		
	}
	
}