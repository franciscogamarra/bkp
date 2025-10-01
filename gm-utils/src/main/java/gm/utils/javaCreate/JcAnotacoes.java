package gm.utils.javaCreate;

import java.lang.annotation.Annotation;

import gm.utils.comum.Lst;
import gm.utils.string.ListString;

public class JcAnotacoes {

	private final Lst<JcAnotacao> list = new Lst<>();

	public JcAnotacao add(String tipo) {
		return this.add(new JcTipo(tipo));
	}
	public JcAnotacao add(Class<? extends Annotation> classe) {
		return this.add(new JcAnotacao(classe));
	}
	public JcAnotacao add(Class<? extends Annotation> classe, String parametro) {
		return this.add(new JcAnotacao(classe, parametro));
	}
	public JcAnotacao add(JcTipo tipo) {
		return this.add(new JcAnotacao(tipo));
	}
	public JcAnotacao add(JcAnotacao anotacao) {
		if (!this.has(anotacao.getTipo().getName())) {
			list.add(anotacao);
		}
		return anotacao;
	}
	public JcAnotacoes add(JcAnotacoes anotacoes) {
		for (JcAnotacao o : anotacoes.list) {
			this.add(o);
		}
		return this;
	}

	@Override
	public String toString() {

		if (list.isEmpty()) {
			return "";
		}

		list.sort((a,b) -> {

			String na = a.getTipo().getSimpleName();
			String nb = b.getTipo().getSimpleName();

			if (na.contentEquals("RestController")) {
				if (nb.contentEquals("RequestMapping")) {
					return -1;
				}
			} else if (nb.contentEquals("RestController") && na.contentEquals("RequestMapping")) {
				return 1;
			}

			return 0;

		});

		StringBuilder s = new StringBuilder();

		for (JcAnotacao o : list) {
			s.append(" ").append(o);
		}

		return s.toString().substring(1);

	}
	public ListString toListString() {
		ListString lst = new ListString();
		for (JcAnotacao o : list) {
			lst.add(o.toString());
		}
		return lst;
	}
	public boolean has() {
		return !list.isEmpty();
	}
	public Lst<JcAnotacao> getList() {
		return list;
	}
	public boolean has(Class<?> tipo) {
		return this.has(tipo.getName());
	}
	public boolean has(JcTipo tipo) {
		return this.has(tipo.getName());
	}
	public boolean has(String tipo) {
		return get(tipo) != null;
	}
	public boolean has(JcAnotacao tipo) {
		return get(tipo) != null;
	}
	public JcAnotacao get(Class<?> tipo) {
		return get(tipo.getName());
	}
	public JcAnotacao get(JcAnotacao tipo) {
		return get(tipo.getTipo());
	}
	public JcAnotacao get(JcTipo tipo) {
		return get(tipo.getName());
	}
	public JcAnotacao get(String tipo) {
		return list.unique(o -> o.getTipo().getName().contentEquals(tipo));
	}
}
