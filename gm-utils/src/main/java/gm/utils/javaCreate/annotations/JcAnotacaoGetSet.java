package gm.utils.javaCreate.annotations;

import java.lang.annotation.Annotation;

import gm.utils.javaCreate.JcAnotacao;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import lombok.Getter;
import src.commom.utils.object.Obrig;

@Getter
public abstract class JcAnotacaoGetSet {

	protected Class<? extends Annotation> annotation;

	public JcAnotacaoGetSet(Class<? extends Annotation> annotation) {
		setTipo(annotation);
	}

	public void setTipo(Class<? extends Annotation> annotation) {
		this.annotation = Obrig.check(annotation);
	}

	public void set(JcAnotacao an) {
		for (Atributo a : as()) {
			a.set(this, an.getParam(a.nome()));
		}
	}

	private Atributos as() {
		Atributos as = AtributosBuild.get(getClass());
		as.remove("annotation");
		as.sort();
		return as;
	}

	public JcAnotacao get() {
		JcAnotacao o = new JcAnotacao(annotation);
		for (Atributo a : as()) {
			Object value = a.get(this);
			if (value != null) {
				if (a.isString()) {
					value = "\"" + value + "\"";
				}
				o.addParametro(a.nome(), value);
			}
		}
		return o;
	}

}
