package gm.utils.reflection.classGet;

import gm.utils.comum.Lst;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import gm.utils.reflection.ListMetodos;
import gm.utils.reflection.Metodo;
import gm.utils.reflection.Metodos;
import src.commom.utils.string.StringPrimeiraMinuscula;

public class ClassGetsLst {

	public static Lst<ClassGet> get(Class<?> classe) {

		Lst<ClassGet> lst = new Lst<>();

		Atributos as = AtributosBuild.get(classe);
		as.removeStatics();

		if (as.getId() != null) {
			as.add(0, as.getId());
		}
		
		for (Atributo a : as) {
			lst.add(new ClassGetAtributo(a));
		}

		Metodos metodos = ListMetodos.get(classe);

		for (Metodo m : metodos) {

			if (m.returnVoid()) {
				continue;
			}

			String nome = m.getName();
			if (!nome.startsWith("get")) {
				continue;
			}

			nome = StringPrimeiraMinuscula.exec(nome.substring(3));

			if (as.contains(nome)) {
				continue;
			}

			lst.add(new ClassGetMetodo(m));

		}

		return lst;

	}

}
