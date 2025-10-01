package gm.utils.reflection;

import java.lang.reflect.Parameter;
import java.lang.reflect.Type;
import java.util.function.Consumer;

import gm.utils.exception.NaoImplementadoException;
import gm.utils.lambda.F0;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringExtraiNumeros;

@Getter @Setter
public class Parametro {

	private Parameter parameter;
	private String nome;

	public Parametro(Parameter parameter) {
		this.parameter = parameter;
		setNome(parameter.getName());
	}

	public Class<?> getType() {
		return parameter.getType();
	}
	
	public Type getParameterizedType() {
		return parameter.getParameterizedType();
	}

	public boolean is(Class<?> classe) {
		return getType().equals(classe);
	}

	@Override
	public String toString() {
		return parameter.toString();
	}

	public boolean isArrow() {
		
		Class<?> o = getType();
		
		if (o == Consumer.class) {
			return true;
		}
		
		if (o.getPackage() == F0.class.getPackage()) {
			return true;
		}
		
		return false;
		
	}
	
	public int getArrowParamsCount() {
		
		Class<?> o = getType();
		
		if (o == Consumer.class) {
			return 1;
		}
		
		String ss = StringExtraiNumeros.exec(o.toString());
		
		if (!ss.isEmpty()) {
			return IntegerParse.toInt(ss);
		}

		throw new NaoImplementadoException();
		
	}

}
