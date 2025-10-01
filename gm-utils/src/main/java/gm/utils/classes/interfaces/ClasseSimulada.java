package gm.utils.classes.interfaces;

import java.lang.annotation.Annotation;

import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcTipo;
import lombok.Getter;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeLast;

@Getter
public class ClasseSimulada extends IClass {
	
	private final String name;
	private final String simpleName;
	private final String packageName;
	private final Lst<IField> fieldsCarregados;
	
	public ClasseSimulada(String name, Lst<IField> fieldsCarregados) {
		this.name = name;
		this.fieldsCarregados = fieldsCarregados;
		simpleName = StringAfterLast.get(name, ".");
		packageName = StringBeforeLast.get(name, ".");
		
	}

	@Override
	public JcTipo toJcTipo() {
		return new JcTipo(name);
	}

	@Override
	protected Lst<IField> loadFields() {
		return fieldsCarregados;
	}

	@Override
	public IField getId() {
		return fieldsCarregados.unique(i -> i.isId());
	}

	@Override
	public IAnnotation getAnnotation(Class<? extends Annotation> annotationClass) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public IClass getMetodoRetorno(String nomeMetodo) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isEnum() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public String getTableName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSchemaName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected IClass getSuperclass() {
		throw new NaoImplementadoException();
	}

	@Override
	protected boolean instanceOf(IClass classe) {
		throw new NaoImplementadoException();
	}

	@Override
	protected boolean isAbstract() {
		throw new NaoImplementadoException();
	}

	@Override
	public GFile getJavaFile() {
		throw new NaoImplementadoException();
	}

}
