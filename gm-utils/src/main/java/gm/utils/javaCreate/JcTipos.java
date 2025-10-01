package gm.utils.javaCreate;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

import gm.utils.comum.UList;
import lombok.Getter;

@Getter
public class JcTipos implements Iterable<JcTipo> {

	private final List<JcTipo> list = new ArrayList<>();

	public JcTipos add(Class<?> classe) {
		return this.add(new JcTipo(classe));
	}
	public JcTipos add(String classe) {
		return this.add(new JcTipo(classe));
	}
	public JcTipos add(JcClasse classe) {
		return this.add(new JcTipo(classe.getName()));
	}
	public JcTipos add(JcTipo tipo) {
		if (!this.has(tipo)) {
			list.add(tipo);
		}
		return this;
	}
	public JcTipos add(JcTipos tipos) {
		for (JcTipo tipo : tipos.list) {
			this.add(tipo);
		}
		return this;
	}
	public boolean has(JcTipo tipo) {
		return this.has(tipo.getName());
	}
	public boolean has(String tipo) {
		return UList.exists(list, o -> o.getName().contentEquals(tipo));
	}
	public void sort() {
		list.sort((a,b) -> a.getName().compareTo(b.getName()) );
	}
	public boolean remove(JcTipo tipo) {
		return list.removeIf(o -> o.getName().contentEquals(tipo.getName()));
	}
	public boolean remove(Class<?> classe) {
		return list.removeIf(o -> o.getName().contentEquals(classe.getName()));
	}
	public void replace(Class<?> a, Class<?> b) {
		if (remove(a)) {
			add(b);
		}
	}
	@Override
	public void forEach(Consumer<? super JcTipo> action) {
		list.forEach(action);
	}

	@Override
	public Iterator<JcTipo> iterator() {
		return list.iterator();
	}

	public boolean isEmpty() {
		return list.isEmpty();
	}

}
