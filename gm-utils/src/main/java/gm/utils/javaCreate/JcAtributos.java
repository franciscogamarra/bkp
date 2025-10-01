package gm.utils.javaCreate;

import java.util.function.Predicate;

import gm.utils.comum.Lst;
import lombok.Getter;
import src.commom.utils.object.Obrig;
import src.commom.utils.string.StringCompare;

@Getter
public class JcAtributos extends Lst<JcAtributo> {

	private static final long serialVersionUID = 1L;
	
	private JcAtributo id;
	private JcClasse classe;

	public JcAtributos() {}

	public JcAtributos(JcClasse classe) {
		this.classe = classe;
	}

	public JcAtributos getPersistAndVirtuais() {
		return filter(a -> !a.isTransient_());
	}

	@Override
	public boolean add(JcAtributo e) {
		e.setAtributos(this);
		if (e.isId()) {
			id = e;
		}
		return super.add(e);
	}

	@Override
	public JcAtributos filter(Predicate<JcAtributo> predicate) {
		JcAtributos as = new JcAtributos(classe);
		as.addAll(super.filter(predicate));
		return as;
	}

	public JcAtributos getWhereType(Class<?> classe) {
		return filter(o -> o.getTipo().eq(classe));
	}
	public JcAtributos getWhereType(JcTipo classe) {
		return filter(o -> o.getTipo().eq(classe));
	}

	public JcAtributo get(String nome) {
		return unique(o -> StringCompare.eq(nome, o.getNome()));
	}

	public boolean contains(String nome) {
		return get(nome) != null;
	}

	public JcAtributo getObrig(String nome) {
		return Obrig.checkWithMessage(get(nome), "Não foi encontrado um atributo com o nome " + nome);
	}

}
