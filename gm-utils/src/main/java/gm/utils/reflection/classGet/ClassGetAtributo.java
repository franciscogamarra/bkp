package gm.utils.reflection.classGet;

import gm.utils.classes.ClassBox;
import gm.utils.classes.ListClass;
import gm.utils.javaCreate.JcTipo;
import gm.utils.reflection.Atributo;
import gm.utils.string.ListString;
import src.commom.utils.integer.IntegerCompare;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringRight;

public class ClassGetAtributo extends ClassGet {

	private Atributo a;

	public ClassGetAtributo(Atributo a) {
		this.a = a;
	}

	@Override
	protected String getNomePrivate() {
		return a.nome();
	}

	@Override
	protected JcTipo getTipoPrivate() {

		if (a.isPrimitivo()) {
			return new JcTipo(a.getType());
		}

		ListClass imports = ClassBox.get(getClasse()).getImports();

		ListString java = getJava();
		java.trimPlus();
		java.removeIfNotContains(" " + getNome());

		if (a.isPrivate()) {
			java.removeIfNotStartsWith("private ");
		} else if (a.isProtected()) {
			java.removeIfNotStartsWith("protected ");
		} else if (a.isPublic()) {
			java.removeIfNotStartsWith("public ");
		}

		java.removeIf(s -> !s.contains(getNome()+";") && !s.contains(getNome()+" = "));

		java.replaceEach(s -> {

			if (s.contains(" = ")) {
				s = StringBeforeFirst.get(s, " = ");
			} else {
				s = StringRight.ignore1(s);
			}

			if (a.isPrivate()) {
				s = StringAfterFirst.get(s, "private ");
			} else if (a.isProtected()) {
				s = StringAfterFirst.get(s, "protected ");
			} else if (a.isPublic()) {
				s = StringAfterFirst.get(s, "public ");
			}

			if (s.startsWith("final ")) {
				s = StringAfterFirst.get(s, "final ");
			}

			return s;

		});

		java.removeIfNotEndsWith(" " + getNome());

		if (!java.size(1)) {
			throw new RuntimeException();
		}

		imports.sort();
		imports.sort((a,b) -> IntegerCompare.compare(a.getName().length(), b.getName().length()) * -1);

		String s = java.get(0);
		s = StringBeforeLast.get(s, " " + getNome());

		s = "<"+s+">";

		for (Class<?> item : imports) {
			String sn = item.getSimpleName();
			String nome = item.getName();
			s = s.replace("<"+sn+"<", "<"+nome+"<");
			s = s.replace("<"+sn+">", "<"+nome+">");
			s = s.replace("<"+sn+",", "<"+nome+",");
			s = s.replace(","+sn+",", ","+nome+",");
			s = s.replace(","+sn+">", ","+nome+">");
		}

		s = StringRight.ignore1(s).substring(1);

		return JcTipo.descobre(s);

	}

	@Override
	protected Class<?> getClassePrivate() {
		return a.getClasse();
	}

	@Override
	protected boolean isPrivate() {
		return a.isPrivate();
	}

	@Override
	protected boolean isProtected() {
		return a.isProtected();
	}

	@Override
	protected boolean isPublic() {
		return a.isPublic();
	}

}
