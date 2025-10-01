package gm.utils.reflection.classGet;

import gm.utils.comum.UType;
import gm.utils.javaCreate.JcTipo;
import gm.utils.reflection.Classe;
import gm.utils.reflection.Metodo;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringPrimeiraMinuscula;
import src.commom.utils.string.StringRight;

public class ClassGetMetodo extends ClassGet {

	private Metodo m;

	public ClassGetMetodo(Metodo m) {
		this.m = m;
	}

	@Override
	protected String getNomePrivate() {
		return StringPrimeiraMinuscula.exec(m.getName().substring(3));
	}

	@Override
	protected Class<?> getClassePrivate() {
		return m.getClasse().getClasse();
	}

	@Override
	protected JcTipo getTipoPrivate() {

		Classe c = m.retorno();

		if (c == null) {
			//significa que é um generics nao informado
			return JcTipo.GENERICS("T");
		}

		if (UType.isPrimitiva(c)) {
			return new JcTipo(c);
		}

		ListString java = getJava();
		java.trimPlus();
		ListString imports = java.removeAndGet(s -> s.startsWith("import "));
		java.removeIfNotContains(" " + getNome());

		if (isPrivate()) {
			java.removeIfNotStartsWith("private ");
		} else if (isProtected()) {
			java.removeIfNotStartsWith("protected ");
		} else if (isPublic()) {
			java.removeIfNotStartsWith("public ");
		}

		java.removeIf(s -> !s.contains(" " + getNome()+"("));
		java.removeIf(s -> s.contains(" = "));

		java.replaceEach(s -> {

			if (isPrivate()) {
				s = StringAfterFirst.get(s, "private ");
			} else if (isProtected()) {
				s = StringAfterFirst.get(s, "protected ");
			} else if (isPublic()) {
				s = StringAfterFirst.get(s, "public ");
			}

			return s;

		});

		if (!java.size(1)) {
			throw new RuntimeException();
		}

		imports.replaceEach(s -> {
			s = StringAfterFirst.get(s, "import ");
			return StringRight.ignore1(s);
		});

		imports.sort();

		imports.sort((a,b) -> IntegerCompare.compare(a.length(), b.length()) * -1);

		String s = java.get(0);
		s = StringBeforeLast.get(s, " " + getNome());

		s = "<"+s+">";

		for (String i : imports) {
			String sn = StringAfterLast.get(i, ".");
			s = s.replace("<"+sn+"<", "<"+i+"<");
			s = s.replace("<"+sn+">", "<"+i+">");
			s = s.replace("<"+sn+",", "<"+i+",");
			s = s.replace(","+sn+",", ","+i+",");
			s = s.replace(","+sn+">", ","+i+">");
		}

		s = StringRight.ignore1(s).substring(1);

		return JcTipo.descobre(s);

	}

	@Override
	protected boolean isPrivate() {
		return m.isPrivate();
	}

	@Override
	protected boolean isProtected() {
		return m.isProtected();
	}

	@Override
	protected boolean isPublic() {
		return m.isPublic();
	}
}
