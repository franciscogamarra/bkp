package gm.languages.java.expressoes;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import gm.languages.java.outros.Utils;
import gm.languages.palavras.PalavraReservada;
import gm.languages.palavras.comuns.conjuntos.arrow.Arrow;
import gm.utils.comum.Lst;
import gm.utils.comum.UType;
import gm.utils.javaCreate.JcTipo;
import gm.utils.lambda.P1;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TipoJava extends PalavraReservada {
	
	private Type typeClass;
	private JcTipo type;
	private Lst<TipoJava> generics;
	private TipoJava extendss;
	private boolean diamond;
	private boolean notNull;
	
	private final Lst<P1<Lst<TipoJava>>> genericsObservers = new Lst<>();

	public TipoJava(Class<?> classe) {
		super(classe.getSimpleName());
		type = JcTipo.build(classe);
		typeClass = classe;
	}
	
	public TipoJava(JcTipo tipo) {
		type = tipo;
	}
	
	public TipoJava(String s) {
		super(s.trim());
		
		s = s.trim();
		
		if ("?".contentEquals(s)) {
			type = JcTipo.INTERROGACAO();
		} else {
			typeClass = UType.PRIMITIVAS_JAVA.get(s);
			if (typeClass != null) {
				type = JcTipo.build(typeClass);
			} else {
				type = JcTipo.GENERICS(s);
			}
		}
		
	}
	
	@Override
	public TipoJava clone() {
		TipoJava o = new TipoJava(super.getS());
		o.setType(type);
		if (hasGenerics()) {
			generics.forEach(i -> o.addGeneric(i.clone()));
		}
		return o;
	}
	
	@Override
	public String getS() {
		
		if (type != null && type.eq(Arrow.class)) {
			return "";
		}
		
		String s = super.getS();
		
		if (s.contentEquals("tipoJava")) {
			s = Utils.getSimpleName(type);
		}

		if (diamond) {
			s += "<>";
		} else if (hasGenerics()) {
			s += "<" + generics.joinString(", ") + ">";
		}
		
		if (type != null && type.getReticenciasDe() != null) {
			s += "@...";
		}
		
		if (s.contentEquals("Integer") && notNull) {
			return "int";
		}
		
		if (extendss != null) {
			s += " extends " + extendss.getS();
		}
		
		return s;
	}

	@Override
	public String toString() {
		
		if (emAnalise) {
			return "Tipo["+getS()+"]";
		}
		
		return getS();
		
	}
	
	public boolean eq(TipoJava... classes) {

		if (type != null) {
			for (TipoJava oo : classes) {
				if (oo.getClasse() != null && type.eq(oo.getClasse())) {
					return true;
				}
			}
		}

		if (typeClass != null) {
			for (TipoJava oo : classes) {
				if (oo.getClasse() != null && typeClass == oo.getTypeClass()) {
					return true;
				}
			}
		}
		
		return false;
		
	}
	
	public boolean eq(Class<?>... classes) {
		
		if (type != null) {
			for (Class<?> o : classes) {
				if (type.eq(o)) {
					return true;
				}
			}
		}

		if (typeClass != null) {
			for (Class<?> o : classes) {
				if (typeClass == o) {
					return true;
				}
			}
		}
		
		return false;
	}

	public boolean isAnnotation() {
		return getClasse().isAnnotation();
	}

	public Package getPackage() {
		return getClasse().getPackage();
	}
	
	public Class<?> getClasse() {
		
		try {
			return type.getClasse();
		} catch (Exception e) {
			return null;
		}
		
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> a) {
		return getClasse().isAnnotationPresent(a);
	}

	public <T extends Annotation> T getAnnotation(Class<T> a) {
		return getClasse().getAnnotation(a);
	}

	public String getName() {
		
		if (isGenerics()) {
			return getS();
		}
		
		return getType().getName();
		
	}

	public void setType(Type type) {
		setType(JcTipo.build(type));
	}
	
	public void setType(JcTipo type) {
		this.type = type;
		typeClass = null;
	}

	public Type getTypeClass() {
		
		if (typeClass == null) {
			
			if (getType() == null) {
				return null;
			}
			
			typeClass = getType().getClasse();
		}
		
		return typeClass;
		
	}
	
	public String getSimpleName() {
		
		if (isGenerics()) {
			return getS();
		}
		
		return getType().getSimpleName();
	}
	
	public boolean isGenerics() {
		return getType() == null && getTypeClass() == null;
	}
	
	public JcTipo getJcTipo() {
		
		if (!hasGenerics()) {
			return getType();
		}
		
		JcTipo o = new JcTipo(getType());
		generics.each(i -> o.addGenerics(i.getJcTipo()));
		return o;
		
	}
	
	public void addGenericsObserver(P1<Lst<TipoJava>> func) {
		
		if (hasGenerics()) {
			func.call(generics);
		} else {
			genericsObservers.add(func);
		}
		
	}
	
	private void addGenericPrivate(TipoJava tipo) {
		if (tipo == null) {
			throw new RuntimeException("tipo == null");
		}
		if (generics == null) {
			generics = new Lst<>();
		}
		generics.add(tipo);
	}
	
	public void addGeneric(TipoJava tipo) {
		addGenericPrivate(tipo);
		genericsObservers.each(i -> i.call(generics));
	}
	
	public void addGenerics(Lst<TipoJava> lst) {
		lst.forEach(i -> addGenericPrivate(i));
		genericsObservers.each(i -> i.call(generics));
	}

	public void setGenerics(Lst<TipoJava> lst) {
		if (hasGenerics()) {
			generics.clear();
		}
		addGenerics(lst);
	}

	public boolean eq(Class<?> o) {
		return getType() != null && getType().eq(o);
	}
	
	public boolean hasAnnotation(Class<? extends Annotation> o) {

		if (getTypeClass() == null) {
			return false;
		}
		
		
		Class<?> classe = (Class<?>) getTypeClass();
		return classe.isAnnotationPresent(o);
		
	}

	public boolean isString() {
		return getClasse() == String.class;
	}

	public boolean hasGenerics() {
		return generics != null && generics.isNotEmpty();
	}

	public TipoJava getGenerics(int i) {
		
		if (!hasGenerics()) {
			throw new NullPointerException("Generics está nulo");
		}
		
		return generics.get(i);
	}

}