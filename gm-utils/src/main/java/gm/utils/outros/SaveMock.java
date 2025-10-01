package gm.utils.outros;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import gm.utils.classes.UClass;
import gm.utils.date.Data;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.Atributos;
import gm.utils.reflection.AtributosBuild;
import src.commom.utils.object.Null;

public class SaveMock {

	private static int i;

	public static void run(JcClasse jc, List<?> list) {

		Class<?> classe = UClass.getClass(list.get(0));
		JcMetodo m = jc.metodo("get").static_().public_().type(List.class, classe);
		m.add("List<"+classe.getSimpleName()+"> list = new ArrayList<>();");

		i = 0;
		for (Object o : list) {
			m.add("list.add(o"+i+"());");
			exec(jc, o);
			i++;
		}

		m.return_("list");

		jc.addImport(ArrayList.class);
		jc.save();

	}

	public static void run(JcClasse jc, Object o) {
		i = 0;
		Class<?> classe = UClass.getClass(o);
		JcMetodo m = jc.metodo("get").static_().public_().type(classe);
		m.return_("o0()");
		exec(jc, o);
		jc.save();
	}

	private static void exec(JcClasse jc, Object o) {
		Class<?> classe = UClass.getClass(o);
		JcMetodo m = jc.metodo("o"+i).static_().type(classe.getName().replace("$", "."));
		String sn = classe.getSimpleName();
		m.add(sn + " o = new " + sn + "();");

		Atributos as = AtributosBuild.get(o);

		for (Atributo a : as.filter(a -> a.isPrimitivo() && !a.isEnum())) {

			String s = "o.set" + a.upperNome() + "(";

			Object value = a.get(o);

			if (Null.is(value)) {
				s += null;
			} else if (a.isString()) {
				s += "\"" + value.toString().replace("\"", "\\\"") + "\"";
			} else if (a.is(BigDecimal.class)) {
				jc.addImport(BigDecimal.class);
				s += "new BigDecimal(" + value + ")";
			} else if (a.is(Data.class)) {
				Data d = (Data) value;
				jc.addImport(Data.class);
				s += "new Data(" + d.format("[yyyy], [m], [d], [h], [n], [s]") + ")";
			} else {
				s += value;
			}

			m.add(s + ");");

		}

		for (Atributo a : as.filter(a -> a.isList())) {

			jc.addImport(ArrayList.class);

			Object value = a.get(o);

			if (value == null) {
				m.add("o.set" + a.upperNome() + "(null);");
			} else {
				m.add("o.set" + a.upperNome() + "(new ArrayList<>());");
				List<?> list = (List<?>) value;
				for (Object obj : list) {
					i++;
					m.add("o.get" + a.upperNome() + "().add(o"+i+"());");
					exec(jc, obj);
				}
			}

		}

		for (Atributo a : as.filter(a -> a.isEnum())) {

			Object value = a.get(o);

			if (value == null) {
				m.add("o.set" + a.upperNome() + "(null);");
			} else {
				jc.addImport(a.getType());
				m.add("o.set" + a.upperNome() + "("+a.getType().getSimpleName() + "." + value + ");");
			}

		}

		for (Atributo a : as.filter(a -> a.isFk())) {

			Object value = a.get(o);

			if (value == null) {
				m.add("o.set" + a.upperNome() + "(null);");
			} else {
				i++;
				m.add("o.set" + a.upperNome() + "(o"+i+"());");
				exec(jc, value);
			}

		}

		m.return_("o");
	}

}
