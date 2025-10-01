package br.mapa;

import java.util.ArrayList;

import gm.utils.comum.UType;
import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringPrimeiraMaiuscula;
import src.commom.utils.string.StringRight;

public class GerarPrint {

	private static boolean isPrimitiva(String s) {

		if ("Date".contentEquals(s) || "BigDecimal".contentEquals(s)) {
			return true;
		}

		return UType.PRIMITIVAS_JAVA.contains(s);
	}

	private static GFile mainPath = GFile.get("C:\\dev\\projects\\funcafe-src\\funcafe-ejb\\src\\main\\java\\br\\gov\\mapa\\funcafe");
	private static GFile printsPath = mainPath.join("saves").join("prints");
//	private static GFile modelsPath = mainPath.join("saves").join("models");

	public static void main(String[] args) {

		GFile entity = mainPath.join("integracao").join("entity");

		exec(entity.join("SContratoFuncafe.java"));

		exec(entity.join("SCronogramaContratadoPK.java"));
		exec(entity.join("SCronogramaContratado.java"));

		exec(entity.join("SOperacaoCredito.java"));
		exec(entity.join("SRegraCredito.java"));

		exec(entity.join("SLiquidacaoPK.java"));
		exec(entity.join("SLiquidacao.java"));

		exec(entity.join("SLiberacaoEfetivadaPK.java"));
		exec(entity.join("SLiberacaoEfetivada.java"));

		exec(entity.join("SRequisicaoReembolso.java"));

		exec(entity.join("SDistRecContratoFuncafePK.java"));
		exec(entity.join("SDistRecContratoFuncafe.java"));





//		GFile dto = mainPath.join("dto");
//		exec(dto.join("MovimentacaoFinanceiraDTO.java"));

//		exec(modelsPath.join("MovimentacaoFinanceiraDtoParams.java"));

//		exec(modelsPath.join("MovimentacaoFinanceiraDTO_montarMovimentacaoFinanceiraDTO_Params.java"));

	}

	private static boolean ignorar(String tipo) {
		return !isPrimitiva(tipo) && !isEnum(tipo) && !printsPath.join(tipo+"Print.java").exists();
	}

	public static void exec(final GFile file) {

		ListString list = file.load();

		list.trimPlus();

		ListString importsOriginais = list.filter(s -> s.startsWith("import "));
		ListString imports = new ListString();

		String modificador = file.getSimpleName().endsWith("_Params.java") ? "public " : "private ";
		list.removeIf(s -> !s.startsWith(modificador));
		list.removeIf(s -> !s.endsWith(";"));
		list.removeIf(s -> s.startsWith(modificador + "static "));
		list.replaceTexto(modificador, "");
		list.replaceTexto(";", "");
		list.trimPlus();
		list.replaceEach(s -> {
			if (s.contains("=")) {
				return StringBeforeFirst.get(s, "=").trim();
			} else {
				return s;
			}
		});

		/* jogas as listas para o final */
		list.sort((a,b) -> {

			if (a.startsWith("List<")) {
				if (b.startsWith("List<")) {
					System.out.println();
				} else {
					return 1;
				}
			} else if (b.startsWith("List<")) {
				return 0;
			}

			return 0;
		});


		ListString lst = new ListString();
		lst.add("package br.gov.mapa.funcafe.saves.prints;");
		lst.add();

		final String classeFull = file.toClassName();
		String sn = StringAfterLast.get(classeFull, ".");
		imports.add("import " + classeFull + ";");

//		lst.add("import br.gov.mapa.utils.Lst;");
		lst.add();

		lst.add("public class "+sn+"Print extends Print {");
		lst.margemInc();
		lst.add();
		lst.add("public static void exec(PrintObj po, String key, "+sn+" o) {");
		lst.margemInc();
		lst.add();
		lst.add("if (o == null) {");
		lst.add("\treturn;");
		lst.add("}");
		lst.add();

		for (String s : list) {

			String nome = StringPrimeiraMaiuscula.exec(StringAfterLast.get(s, " "));
			String tipo = StringBeforeLast.get(s, " ");

			boolean lista = tipo.startsWith("List<");

			String key = "key";

			if (lista) {

				tipo = StringAfterFirst.get(tipo, "<");
				tipo = StringBeforeLast.get(tipo, ">");

				if (ignorar(tipo)) {
					continue;
				}

				imports.addIfNotContains("import " + ArrayList.class.getName() + ";");

				lst.add("if (o.get"+nome+"() != null && !ignorar(\""+sn+".get"+nome+"()\")) {");
				lst.margemInc();

//				lst.add("po.list.add(\"objs.add(new "+tipo+"());\");");
//				lst.add("exec(po, key, \"set"+nome+"\", \"~new ArrayList<"+tipo+">()\");");

				lst.add("exec(po, key, \"set"+nome+"\", \"~new ArrayList<"+tipo+">()\");");
				lst.add("po.addImport(ArrayList.class);");

				if (isPrimitiva(tipo)) {
					lst.add("for ("+tipo+" i : o.get"+nome+"()) {");
					lst.margemInc();
					lst.add("po.list.add(key + \".get"+nome+"().add(\"+get(i)+\")\");");
					lst.margemDec();
					lst.add("}");
					lst.margemDec();
					lst.add("}");
					continue;
				}

				lst.add("po.addImport("+tipo+".class);");

				lst.add("int index = 0;");
				lst.add("for ("+tipo+" i : o.get"+nome+"()) {");
				lst.margemInc();
				lst.add("exec(po, key + \".get"+nome+"()\", \"add\", \"~new "+tipo+"()\");");

				key = "key + \".get" + nome + "().get(\"+index+\")\"";

				String imp = getImp(file, importsOriginais, tipo, classeFull);
				if (imp != null) {
					imports.addIfNotContains("import " + imp + ";");
				}

			} else if (ignorar(tipo)) {
				continue;
			}

			if (isPrimitiva(tipo)) {

				if (tipo.contentEquals("boolean")) {

					if (nome.startsWith("Is")) {
						nome = nome.substring(2);
					}

					lst.add("exec(po, "+key+", \"set"+nome+"\", o.is"+nome+"());");
				} else {
					lst.add("exec(po, "+key+", \"set"+nome+"\", o.get"+nome+"());");
				}

			} else if (isEnum(tipo)) {
				String imp = getImp(file, importsOriginais, tipo, classeFull);
				if (imp != null) {
					lst.add("po.addImport("+imp+".class);");
				}
				lst.add("if (o.get"+nome+"() != null) {");
				lst.add("	exec(po, "+key+", \"set"+nome+"\", \"~"+tipo+".\" + o.get"+nome+"().toString());");
				lst.add("}");
			} else if (lista) {
				lst.add(tipo + "Print.exec(po, "+key+", i);");
			} else {
				lst.add("if (o.get"+nome+"() != null && !ignorar(\""+sn+".get"+nome+"()\")) {");
				lst.margemInc();

				lst.add("boolean contains = po.objs.contains(o.get"+nome+"());");
				lst.add("if (!contains) {");
				lst.margemInc();
				lst.add("po.list.add(\"objs.add(new "+tipo+"());\");");
				lst.add("po.objs.add(o.get"+nome+"());");

				String imp = getImp(file, importsOriginais, tipo, classeFull);
				if (imp != null) {
					lst.add("po.addImport("+imp+".class);");
				}
				lst.margemDec();
				lst.add("}");
				lst.add("exec(po, key, \"set"+nome+"\", \"~("+tipo+") objs.get(\"+po.objs.indexOf(o.get"+nome+"())+\")\");");
				lst.add("if (!contains) {");
				lst.margemInc();
				lst.add(tipo + "Print.exec(po, "+key+" + \".get"+nome+"()\", o.get"+nome+"());");
				lst.margemDec();
				lst.add("}");

				lst.margemDec();
				lst.add("}");
			}

			if (lista) {
				lst.add("index++;");
				lst.margemDec();
				lst.add("}");
				lst.margemDec();
				lst.add("}");
			}

		}

		lst.margemDec();

		lst.add("}");
		lst.add();

		lst.add("public static void save("+sn+" o) {");
		lst.margemInc();
		lst.add("start(\""+sn+"\");");
		lst.add("PrintObj po = new PrintObj(o);");
		lst.add("exec(po, \"o\", o);");
		lst.add("save("+sn+".class, o.getStringId(), po);");
		lst.margemDec();
		lst.add("}");//method
		lst.add();

//		imports.addIfNotContains("import java.util.List;");

		lst.margemDec();

		for (String imp : imports) {
			lst.add(2, imp);
		}

		lst.add("}");

		lst.save(printsPath.join(sn + "Print.java"));

	}

	private static String getImp(GFile file, ListString importsOriginais, String tipo, String classeFull) {

		String x = importsOriginais.unique(ss -> ss.endsWith("." + tipo + ";"));
		if (x != null) {
			if (x.startsWith("import ")) {
				x = StringRight.ignore1(StringAfterFirst.get(x, " "));
			}
			return x;
		}
		if (file.getPath().join(tipo + ".java").exists()) {
			return StringBeforeLast.get(classeFull, ".") + "." + tipo;
		} else {
			return null;
		}


	}

	private static boolean isEnum(String s) {
		return s.endsWith("Enum");
	}

}