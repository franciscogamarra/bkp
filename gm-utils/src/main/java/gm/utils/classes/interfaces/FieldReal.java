package gm.utils.classes.interfaces;

import java.lang.annotation.Annotation;

import gm.utils.date.DataComHora;
import gm.utils.reflection.Atributo;

public class FieldReal extends IField {

	private Atributo a;

	public FieldReal(Atributo a) {
		this.a = a;
	}
	
	@Override
	public String getName() {
		return a.getName();
	}

	@Override
	public String upperNome() {
		return a.upperNome();
	}

	@Override
	public boolean isString() {
		return a.isString();
	}

	@Override
	public boolean isBoolean() {
		return a.isBoolean();
	}

	@Override
	public boolean isShort() {
		return a.isShort();
	}
	
	@Override
	public boolean isInteger() {
		return a.isInteger();
	}

	@Override
	public boolean isLong() {
		return a.isLong();
	}
	
	@Override
	public boolean isDate() {
		return a.isDate();
	}
	
	@Override
	public boolean isDataComHora() {
		return a.is(DataComHora.class);
	}

	@Override
	public boolean isFk() {
		return a.isFk();
	}

	@Override
	public boolean isTransient() {
		return a.isTransient();
	}

	@Override
	public boolean isStatic() {
		return a.isStatic();
	}

	@Override
	public boolean isList() {
		return a.isList();
	}

	@Override
	public boolean isFinal() {
		return a.isFinal();
	}

	@Override
	public String getNomeValue() {
		return a.getNomeValue();
	}

	@Override
	public boolean isId() {
		return a.isId();
	}

	@Override
	public IClass getType() {
		return ClasseReal.get(a.getType());
	}

	@Override
	public boolean isNumeric1() {
		return a.isNumeric1();
	}

	@Override
	public boolean isNumeric2() {
		return a.isNumeric2();
	}

	@Override
	public boolean isNumeric3() {
		return a.isNumeric3();
	}
	
	@Override
	public boolean isNumeric4() {
		return a.isNumeric4();
	}
	
	@Override
	public boolean isNumeric5() {
		return a.isNumeric5();
	}
	
	@Override
	public boolean isNumeric8() {
		return a.isNumeric8();
	}
	
	@Override
	public boolean isNumeric15() {
		return a.isNumeric15();
	}
	
	@Override
	public boolean isNumeric18() {
		return a.isNumeric18();
	}
	
	@Override
	public int digitsFraction() {
		return a.digitsFraction();
	}

	@Override
	public int getMin() {
		
		if (a.isString()) {
			if (a.isNotNull()) {
				return 1;
			} else {
				return 0;
			}
		}
		
		if (a.isInteger()) {
			return a.getMinInt();
		}
		
		return 0;
		
	}
	
	@Override
	public int getMax() {
		
		if (a.isString()) {
			return a.getLength(isString() ? 255 : 0);
		}
		
		if (a.isInteger()) {
			return a.getMaxInt();
		}
		
		return 0;
		
	}

	@Override
	public boolean isNotNull() {
		return a.isNotNull();
	}

	@Override
	public String getColumnName() {
		return a.getColumnName();
	}
	
	@Override
	public IAnnotation getAnnotation(Class<? extends Annotation> annotationClass) {
		return IAnnotation.build(a.getAnnotation(annotationClass));
	}

	@Override
	public boolean isEnum() {
		return a.isEnum();
	}

}
