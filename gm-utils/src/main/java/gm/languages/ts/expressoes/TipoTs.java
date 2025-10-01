package gm.languages.ts.expressoes;

import java.lang.reflect.Type;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.palavras.PalavraReservada;
import gm.languages.ts.javaToTs.JavaToTs;
import gm.utils.comum.Lst;
import lombok.Getter;
import lombok.Setter;

@Getter
public class TipoTs extends PalavraReservada {

	@Setter
	private ClassTs classe;
	private Lst<TipoTs> generics;
	private TipoTs extendss;
	
//	@Setter
//	private boolean tipoPrincipalDeClasse;
	
	@Setter
	private String toStringManual;
	
	public TipoTs(ClassTs classe) {
		super("");
		this.classe = classe;
		if (classe == null) {
			throw new NullPointerException();
		}
		JavaToTs.getInstance().tiposCriados.add(this);
	}
	
	public static TipoTs build(TipoJava tipo) {
		
		ClassTs ts;
		
		if (tipo.getType() == null) {
			ts = ClassTs.getGenerics(tipo.getS());
		} else {
			
			if (tipo.getType().getReticenciasDe() != null) {
				TipoJava tipoJava = new TipoJava(tipo.getType().getReticenciasDe());
				TipoTs tp = build(tipoJava);
				ts = ClassTs.getReticencias(tp);
				return new TipoTs(ts);
			}
			
			if (tipo.getTypeClass() == String.class) {
				
				if (tipo.isNotNull()) {
					ts = ClassTs.string;
				} else {
					ts = ClassTs.getString();
				}
				
			} else {
				ts = ClassTs.get(tipo.getType());
			}
			
		}
		
		TipoTs o = new TipoTs(ts);
		if (tipo.getGenerics() != null) {
			o.generics = tipo.getGenerics().map(i -> build(i));
		}
		
		if (tipo.getExtendss() != null) {
			o.extendss = build(tipo.getExtendss());
		}
		
		return o;
		
	}
	
	public String getS(boolean notNull) {
		
		if (toStringManual != null) {
			return toStringManual;
		}
		
		if (isPrimitiva()) {
			return classe.getName();
		}
		
		if (isString()) {
			if (notNull || classe == ClassTs.string) {
				return "string";
			} else {
				return "str";
			}
		}
		
		String s = classe.getSimpleName(notNull);
		
		if (s.startsWith("JSXElement")) {
			return "JSX.Element";
		}
		
		if (s.contentEquals("JsMap")) {
			s = "Map";
		}
		
		if (!s.contentEquals("any")) {

			if (generics != null) {
				
				String x;
				
				if (generics.isEmpty()) {
					x = "<any>";
				} else {
					generics.filter(i -> i.isString() && i.classe != ClassTs.string).each(i -> i.setToStringManual("str"));
					x = "<" + generics.joinString(", ") + ">";
				}
				
				if (s.endsWith(ClassTs.NULLUNDEFINED)) {
					s = s.replace(ClassTs.NULLUNDEFINED, x + ClassTs.NULLUNDEFINED);
				} else {
					s += x;
				}
				
			}
			
			if (extendss != null) {
				s += " extends " + extendss.getS(notNull);
			}
			
		}
		
		if (isReticencias()) {
			s = s.substring(3) + "[]";
		}
		
//		if (tipoPrincipalDeClasse) {
//			
//			if (s.contains("<")) {
//				String before = StringBeforeFirst.get(s, "<");
//				String after = StringAfterFirst.get(s, "<");
//				s = before + " = <" + after;
//			} else {
//				s += " =";
//			}
//			
//		}
		
		return s;
		
	}
	
	public void setGenerics(Lst<TipoTs> generics) {
		this.generics = generics;
	}

	@Override
	public String getS() {
		return getS(true);
	}
	
	public boolean isReticencias() {
		return classe.getSimpleName().startsWith("...");
	}
	
	@Override
	public TipoTs clone() {
		TipoTs o = new TipoTs(classe);
		if (hasGenerics()) {
			o.generics = generics.map(i -> i.clone());
		}
		return o;
	}
	
	public static TipoTs newVoid() {
		return new TipoTs(ClassTs.voidTs);
	}
	
	@Override
	public String toString() {
		return getS();
	}
	
	public boolean eq(Type type) {

		if (classe == null) {
			return false;
		}
		
		return classe.eq(type);
		
	}

	public boolean eq(Type a, Type b, Type... outros) {

		if (classe == null) {
			return false;
		}
		
		if (eq(a) || eq(b)) {
			return true;
		}
		
		for (Type c : outros) {
			if (eq(c)) {
				return true;
			}
		}
		
		return false;
		
	}
	
	public boolean eq(TipoTs o) {
		
		if (this == o) {
			return true;
		}
		
		return getS().contentEquals(o.getS());
		
	}

	public boolean isPrimitiva() {
		return classe.isPrimitiva();
	}

	public boolean isString() {
		return classe.isString();
	}

	public boolean possuiTipo(String sn) {
		
		if ( getClasse().getSimpleName().contentEquals(sn) ) {
			return true;
		}
		
		if ( hasGenerics() && getGenerics().anyMatch(i -> i.possuiTipo(sn))) {
			return true;
		}
		
		return false;
		
	}

	public boolean hasGenerics() {
		return getGenerics() != null && getGenerics().isNotEmpty();
	}

	public boolean containsType(String s) {
		
		String x = getS();
		
		if (x.contentEquals(s) || x.startsWith(s + "<") || x.contains("<"+s+">")) {
			return true;
		}
		
		if (hasGenerics()) {
			for (TipoTs o : generics) {
				if (o.containsType(s)) {
					return true;
				}
			}
		}
		
		if (extendss != null && extendss.containsType(s)) {
			return true;
		}
		
		return false;
		
	}
	
	
}
