package br.mapa;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.javaCreate.JcTipo;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringRight;

public class GerarParams {

	private static GFile savesPath = GFile.get("C:\\desenvolvimento\\projetos\\funcafe-src\\funcafe-ejb\\src\\main\\java\\br\\gov\\mapa\\funcafe\\saves");
	private static GFile modelPath = savesPath.join("models");
	
	public static void main(String[] args) {
		exec("MovimentacaoFinanceiraDTO_montarMovimentacaoFinanceiraDTO_Params");
	}

	private static void exec(String sn) {
		
		GFile file = modelPath.join(sn + ".java");
		
		ListString list = file.load();
		list.trimPlus();
		list.removeIf(s -> !s.startsWith("import ") && !s.startsWith("public "));
		
		Lst<JcTipo> imports = list.removeAndGet(s -> s.startsWith("import")).mapString(s -> StringRight.ignore1(StringAfterFirst.get(s, " "))).map(s -> new JcTipo(s));
		
		list.removeIf(s -> !s.endsWith(";"));
		
		SystemPrint.ln("==");
		
		SystemPrint.ln("============================");
		
		JcClasse jc = JcClasse.build(file);
		jc.lombokGetter().lombokSetter();

		imports.forEach(i -> jc.addImport(i));
		
		Lst<String> fields = new Lst<>();
		
		list.forEach(s -> {
			s = StringRight.ignore1(StringAfterFirst.get(s, " ")).trim();
			String nome = StringAfterFirst.get(s, " ");
			String nomeTipo = StringBeforeFirst.get(s, " ");
			
			JcTipo tipo = imports.unique(i -> i.getSimpleName().contentEquals(nomeTipo));
			
			if (tipo == null) {
				tipo = JcTipo.descobre(nomeTipo);
				if (tipo == null) {
					throw new NullPointerException("Não foi possivel determinar " + nomeTipo);
				}
			}
			
			jc.atributo(nome, tipo).public_();
			fields.add(nome);
			
		});
		
		String testClass = file.toClassName().replace(".models.", ".tests.") + "_Test";
		
		jc.atributo("ligado", boolean.class).static_().inicializacao("Dev.in");
		
		JcMetodo m = jc.metodo("save").public_();
		m.add();
		m.if_("!ligado", "return;");
		m.add();
		m.add("Lst<String> lst = new Lst<String>();");
		m.add();
		m.add("lst.add(\"package "+StringBeforeLast.get(testClass, ".")+";\");");
		m.add("lst.add(\"\");");
		imports.filter(i -> !i.getName().startsWith("lombok.") && !i.getName().contentEquals(testClass)).forEach(i -> m.add("lst.add(\"import "+i.getName()+";\");"));
		m.add("lst.add(\"import "+jc.getName()+";\");");
		m.add("lst.add(\"\");");
		m.add("lst.add(\"public class "+sn+"_Test {\");");
		m.add("lst.add(\"\");");
		m.add("lst.add(\"	public static "+sn+" get() {\");");
		m.add("lst.add(\"\");");
		m.add();
		m.add("Lst<String> list = "+sn+"Print.save(this, \"c:/tmp/"+sn+".txt\");");
		m.add();
		m.add("for (String s : list) {");
		m.add("lst.add(\"		\" + s);");
		m.add("}");
		m.add();
		m.add("lst.add(\"		return o;\");");
		m.add("lst.add(\"\");");
		m.add("lst.add(\"	}\");");
		m.add("lst.add(\"\");");
		m.add("lst.add(\"}\");");
		m.add();
		m.add("lst.save(\""+savesPath+"/tests/"+sn+"_Test.java\");");
		
		JcMetodo main = jc.main();
		main.add("ligado = false;");
		main.add(sn + " o = "+sn+"_Test.get();");
		
		main.add(StringBeforeLast.get(sn, "_").replace("_", ".") + "("+fields.toString(s -> "o." + s, ", ")+");");
		
////		params.save();
//		MovimentacaoFinanceiraDTO.addValorParcelaTratandoResiduoNovo(params.txJuros, params.dataAtual, params.listaRetorno, params.residuo, params.cronograma, params.listMovimentacaoFinanceira, params.saldoDevedor, params.dataBase, params.saldoDevedorAjustado);

		
		jc.save();
	}
	
}
