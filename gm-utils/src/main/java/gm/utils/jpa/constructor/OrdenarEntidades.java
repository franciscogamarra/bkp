package gm.utils.jpa.constructor;

import gm.utils.classes.interfaces.IClass;
import gm.utils.classes.interfaces.IField;
import gm.utils.comum.Lst;
import gm.utils.comum.ULog;
import gm.utils.exception.UException;
import gm.utils.string.ListString;

public class OrdenarEntidades {

	private IClass classeBusca;
	private Lst<IClass> all;

	public OrdenarEntidades(Lst<IClass> list) {
		this(list, null);
	}
	public OrdenarEntidades(Lst<IClass> list, IClass classeBusca) {

		all = list;
		this.classeBusca = classeBusca;

		ListString print = new ListString();
		Lst<IClass> ordenadas = new Lst<>();

		//separar as que nao apontam para ninguem
		for (IClass classe : list) {
			Lst<IField> as = atributos(classe);
			as.removeIf(i -> i.is(classe));
			if (as.isEmpty()) {
				ordenadas.add(classe);
			}
		}

		list.removeAll(ordenadas);

		print.add();

		for (int i = 0; i < 20; i++) {
			Lst<IClass> ordenadas2 = new Lst<>();
			for (IClass classe : list) {
				Lst<IField> as = atributos(classe);
				as.removeIf(item -> item.is(classe));
				for (IClass type : ordenadas) {
					as.removeIf(item -> item.is(type));
				}
				if (as.isEmpty()) {
					ordenadas2.add(classe);
				}
			}

			print.add();
			ordenadas.addAll(ordenadas2);
			list.removeAll(ordenadas2);
		}

		if (list.isEmpty()) {
			if ((classeBusca != null) && ordenadas.remove(classeBusca)) {
				ordenadas.add(0, classeBusca);
			}
			list.addAll(ordenadas);
			return;
		}

		ULog.debug("Ordenadas:");
		ordenadas.print();
		ULog.debug();
		ULog.debug();
		ULog.debug("N\u00e3o Ordenadas:");
		ULog.debug(list.size());
		for (IClass classe : list) {
			print.add("------------------------------");
			print.add("*******" + classe);
			Lst<IField> as = atributos(classe);
			as.removeIf(item -> item.is(classe));
			for (IClass type : ordenadas) {
				as.removeIf(item -> item.is(type));
			}
			as.print();
		}

		throw UException.runtime("Existem mapeamentos que n\u00e3o foi poss\u00edvel ordena-los");

	}

	protected Lst<IField> atributos(IClass classe) {
		
		if (classe.isEnum()) {
			return new Lst<>();
		}
		
		Lst<IField> as = classe.getFieldsPersistentes().filter(i -> i.isFk());
		as.removeIf(a -> !all.contains(a.getType()));
		if (classeBusca != null) {
			as.removeIf(i -> i.is(classeBusca));
		}
//		as.removeIf(i -> i.is(IEntityHierarquica));

		return as;
	}

}
