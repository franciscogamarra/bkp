package gm.utils.jpa;

import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.string.ListString;

public class CreateView {

	public static ListString exec(Class<?> classe) {
		return exec(classe, TableSchema.get(classe));
	}

	public static ListString exec(Class<?> classe, String ts) {

		Atributos as = AtributosBuild.getPersists(classe);

		ListString list = new ListString();

		list.add("create view map." + classe.getSimpleName() + " as select");
		list.add("  id = " + as.getId().getColumnName());
		for (Atributo a : as) {
			list.add(", " + a.nome() + " = " + a.getColumnName());
		}
		list.add("from " + ts);
		return list;

	}

}
