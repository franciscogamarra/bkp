package gm.utils.reflection;

import java.lang.reflect.Parameter;
import java.util.List;

import gm.utils.classes.ClassBox;
import gm.utils.classes.ListClass;
import gm.utils.comum.Identar;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.string.StringSplit;
import gm.utils.string.UString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEscopo;
import src.commom.utils.string.StringTrim;

public abstract class IMetodo {

	private String assinaturaSemOsNomesDosParametros;
	public final String getAssinaturaSemOsNomesDosParametros() {
		if (assinaturaSemOsNomesDosParametros == null) {
			String p = "";
			List<Parametro> params = getParametros();
			if (!params.isEmpty()) {
				for (Parametro parametro : params) {
					p += ", " + parametro.getType().getSimpleName();
				}
				p = p.substring(2);
			}
			assinaturaSemOsNomesDosParametros = getAssinaturaSemParametros() + "("+p+")";
		}
		return assinaturaSemOsNomesDosParametros;
	}

	private String assinaturaSemParametros;

	public final String getAssinaturaSemParametros() {
		if (assinaturaSemParametros == null) {
			assinaturaSemParametros = getAssinaturaSemParametrosImpl();
		}
		return assinaturaSemParametros;
	}

	public abstract String getAssinaturaSemParametrosImpl();
	protected abstract Class<?> getClasseImpl();
	protected abstract Class<?> getDeclaringClasseImpl();

	private ClassBox classe;
	public final ClassBox getClasse() {
		if (classe == null) {
			classe = ClassBox.get(getClasseImpl());
		}
		return classe;
	}

	private ClassBox declaringClasse;
	public final ClassBox getDeclaringClasse() {
		if (declaringClasse == null) {
			declaringClasse = ClassBox.get(getDeclaringClasseImpl());
		}
		return declaringClasse;
	}

	private Lst<Parametro> parametros;

	public final Lst<Parametro> getParametros() {
		if (parametros == null) {
			Parameter[] parameters = getParameters();
			parametros = new Lst<>();
			for (Parameter parameter : parameters) {
				parametros.add(new Parametro(parameter));
			}
		}
		return parametros.copy();
	}

	protected abstract Parameter[] getParameters();

	private String assinaturaComOsNomesDosParametros;

	public Lst<String> getCodigoJava() {

		String java = javaSemGenerics();

		String assin = getAssinaturaComOsNomesDosParametros();

		assin = removeGenerics(assin);

		for (String string : generics) {
			assin = assin.replace(" " + string + " ", " Object ");
			assin = assin.replace("(" + string + " ", "(Object ");
		}

		while (assin.contains("<")) {
			String params = StringEscopo.exec(assin, "<", ">");
			assin = assin.replace("<"+params+">", "");
		}

		String s = StringAfterFirst.get(java, assin);

		if (s == null) {
			String modificador = StringBeforeFirst.get(assin, " ");
			assin = StringAfterFirst.get(assin, " ");
			boolean isFinal = assin.startsWith("final ");
			assin = StringAfterFirst.get(assin, " ");
			if (isFinal) {
				assin = StringAfterFirst.get(assin, " ");
			}
			assin = modificador + (isFinal ? " final " : " ") + "Object " + assin;
			s = StringAfterFirst.get(java, assin);
		}

//		gene
		if (StringEscopo.exec(s, "{", "}") == null) {
			SystemPrint.ln(java);
			SystemPrint.ln(assin);
			SystemPrint.ln(s);
		}
		s = StringEscopo.exec(s, "{", "}");
		s = s.replace("{", "{\n");
		s = s.replace("}", "\n}");

		String x = StringEscopo.exec(s, "/*", "*/");

		while (x != null) {
			s = s.replace(x, "");
			x = StringEscopo.exec(s, "/*", "*/");
		}
		
		Lst<String> list = StringSplit.exec(s, "\n");
		Identar.exec(list);
		return list;

	}

	public final String getAssinaturaComOsNomesDosParametros() {
		if (assinaturaComOsNomesDosParametros == null) {
			assinaturaComOsNomesDosParametros = getAssinaturaComOsNomesDosParametrosImpl();
//			String s = UString.textoEntreFirst(assinaturaComOsNomesDosParametros, "(", ")");
//			Lst<String> list = Lst<String>.byDelimiter(s, ",");
//			list.trimPlus();
//			List<Parametro> parametros = getParametros();
//			for (int i = 0; i < list.size(); i++) {
//				parametros.get(i).setNome(StringAfterFirst.get(list.get(i), " "));
//			}
		}
		return assinaturaComOsNomesDosParametros;
	}

	private Lst<String> generics = new Lst<String>();

	private String javaSemGenerics() {

		String s = getAssinaturaSemParametros();
		String java = getClasse().getJava().getString();

		String xx = StringAfterFirst.get(java, "class " + classe.getName());
		xx = StringBeforeFirst.get(xx, "{");
		if (xx.contains("<")) {

			boolean repetir = true;

			xx = UString.textoEntreFirst(xx, "<", ">");
			Lst<String> list = StringSplit.exec(s, "\n").map(ss -> StringTrim.plus(ss));
			
			for (String string : list) {
				if (string.contains(" extends ")) {
					string = StringBeforeFirst.get(string, " ");
				}
				generics.addIfNotContains(string);
			}

			while (repetir) {
				repetir = false;
				for (String string : generics) {

					if ("Object".equals(string)) {
						continue;
					}

					java = java.replace("<"+string+">", "");

					if (java.contains("<"+string+", ")) {
						java = java.replace("<"+string+", ", "<");
						repetir = true;
					}

					java = java.replace(" "+string+" ", " Object ");
					java = java.replace(" "+string+"(", " Object(");
					java = java.replace("("+string+" ", "(Object ");
					java = java.replace("	"+string+" ", "	Object ");
					s = s.replace("<"+string+">", "Object");
					s = s.replace(" "+string+" ", " Object ");
					s = s.replace(" "+string+" ", " Object ");
					s = s.replace("("+string+" ", "(Object ");
					s = s.replace(","+string+" ", ", Object ");
				}
			}

		}

//		public <BODY> Promessa post(String url, BODY body) {
		while (java.contains("public <")) {
			xx = StringAfterFirst.get(java, "public <");
			xx = StringEscopo.exec("<" + xx, "<", ">");
			generics.add(xx);
			java = java.replace("public <" + xx + ">", "public");
			java = java.replace(" " + xx + " ", " Object ");
			java = java.replace("(" + xx + " ", "(Object ");
			java = java.replace("	" + xx + " ", "	Object ");
		}

		return removeGenerics(java);

	}

	private String removeGenerics(String s) {

		ListClass imports = getClasse().getImports();

		for (Class<?> c : imports) {
			String n = c.getSimpleName();
			while (StringContains.is(s, n+"<")) {
				String ss = StringAfterFirst.get(s, n+"<");
				ss = StringEscopo.exec("<" + ss, "<", ">");
				s = s.replace(n+"<" + ss + ">", n);
			}
		}

		return s;
	}

	private String getAssinaturaComOsNomesDosParametrosImpl() {

//		String java = javaSemGenerics();

		String s = getAssinaturaSemParametros();
		for (String string : generics) {
			s = s.replace(" " + string + " ", " Object ");
		}

		String ss = "";

		List<Parametro> parametros = getParametros();
		if (!parametros.isEmpty()) {
			for (Parametro p : parametros) {
				String tipo = StringBeforeLast.get(p.getParameter().toString(), " ");
				if (tipo.contains("<")) {
					tipo = StringBeforeFirst.get(tipo, "<");
				}
				if (tipo.contains(".")) {
					tipo = StringAfterLast.get(tipo, ".");
				}
//				ss += ", " + p.getType().getSimpleName() + " " + p.getNome();
				ss += ", " + tipo + " " + p.getNome();
			}
			ss = ss.substring(2);
		}
		return s + "("+ss+")";

		/*

		int qtdParametros = parametros.size();

		while (true) {
			String ss = StringAfterFirst.get(java, s);
			if (ss == null) {
				getAssinaturaComOsNomesDosParametrosImpl();
				throw UException.runtime("Algo deu errado!");
			}
			java = StringAfterFirst.get(java, ss);
			ss = StringAfterFirst.get(ss, "(");
			ss = StringBeforeFirst.get(ss, ")");

 			Lst<String> l = Lst<String>.byDelimiter(ss, ",");
			l.trimPlus();

			Lst<String> list = new Lst<String>();

			while (!l.isEmpty()) {
				String remove = l.remove(0);
				if (!remove.contains(" ") && remove.contains("<")) {
					remove = StringBeforeFirst.get(remove, "<");
					String xx = l.remove(0);
					if (xx.contains(">")) {
						remove += " " + StringAfterFirst.get(xx, ">").trim();
					} else {
						throw UException.runtime("Não tratado");
					}
				}
				list.add(remove);
			}

			if (list.size() == qtdParametros) {
				boolean iguais = true;
				for (int i = 0; i < qtdParametros; i++) {
					String tipo = StringBeforeFirst.get(list.get(i), " ");
					if (tipo.contains("<")) {
						tipo = StringBeforeFirst.get(tipo, "<");
					}
					if (tipo.contains("...")) {
						tipo = tipo.replace("...", "[]");
					}
					String sn = parametros.get(i).getType().getSimpleName();
					if ( !sn.equals(tipo) && !generics.contains(sn) ) {
						iguais = false;
						break;
					}
				}
				if (iguais) {
					if (ss == null) {
						return s + "()";
					} else {
						return s + "("+ss+")";
					}
				}
			}
		}
		/**/
	}

	protected abstract int getModifiers();

	public final String getModificadorDeAcesso() {
		if (isPublic()) {
			return "public";
		}
		if (isPrivate()) {
			return "private";
		} else if (isProtected()) {
			return "protected";
		} else {
			return "default";
		}
	}

	public final boolean isPublic(){
		return java.lang.reflect.Modifier.isPublic(getModifiers());
	}
	public final boolean isPrivate(){
		return java.lang.reflect.Modifier.isPrivate(getModifiers());
	}
	public final boolean isProtected(){
		return java.lang.reflect.Modifier.isProtected(getModifiers());
	}
	public boolean isDefault(){
		return !isPublic() && !isPrivate() && !isProtected();
	}
	public int getParameterCount() {
		return getParametros().size();
	}
	public boolean isParameters(Class<?>... parametros) {
		if ( getParameterCount() != parametros.length ) {
			return false;
		}
		List<Parametro> params = getParametros();
		for (int i = 0; i < parametros.length; i++) {
			if ( !params.get(i).is(parametros[i]) ) {
				return false;
			}
		}
		return true;
	}
	public String getParametrosJs() {
		if (getParameterCount() == 0) {
			return "";
		}
		getAssinaturaComOsNomesDosParametros();
		String s = "";
		List<Parametro> parametros = getParametros();
		for (Parametro parametro : parametros) {
			s += ", " + parametro.getNome();
		}
		return s.substring(2);
	}

}
