package gm.utils.reflection.classGet;

import java.util.HashMap;
import java.util.Map;

import gm.utils.classes.UClass;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.javaCreate.JcTipo;
import gm.utils.string.ListString;

public abstract class ClassGet {

	private String nome;
	private JcTipo tipo;
	private Class<?> classe;

	protected abstract String getNomePrivate();
	protected abstract Class<?> getClassePrivate();
	protected abstract JcTipo getTipoPrivate();
	protected abstract boolean isPrivate();
	protected abstract boolean isProtected();
	protected abstract boolean isPublic();

	public final String getNome() {
		if (nome == null) {
			nome = getNomePrivate();
		}
		return nome;
	}

	public final JcTipo getTipo() {
		if (tipo == null) {
			tipo = getTipoPrivate();
		}
		return tipo;
	}

	protected final Class<?> getClasse() {
		if (classe == null) {
			classe = getClassePrivate();
		}
		return classe;
	}

	private final Map<Class<?>, ListString> javas = new HashMap<>();

	protected ListString getJava() {

		ListString java = javas.get(getClasse());

		if (java == null) {
			java = new ListString().load(UClass.getJavaFileName(getClasse()));
			javas.put(getClasse(), java);
		}

		return java.copy();

	}

	public boolean isList() {
		return getTipo().isList();
	}

	public boolean isPrimitivo() {
		return getTipo().isPrimitivo();
	}

	public boolean isMap() {
		throw new NaoImplementadoException();
//		return getTipo().isMap();
	}

	public boolean isObject() {
		return !isList() && !isPrimitivo();
	}

	@Override
	public String toString() {
		return getTipo() + " " + getNome();
	}

}
