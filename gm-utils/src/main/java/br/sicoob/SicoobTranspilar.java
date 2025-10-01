package br.sicoob;

import gm.languages.java.JavaLoad;
import gm.languages.palavras.Palavra;
import gm.languages.ts.javaToTs.JavaToTs;
import gm.utils.classes.UClass;
import gm.utils.comum.GetMainClass;
import gm.utils.comum.SystemPrint;
import gm.utils.files.GFile;
import gm.utils.string.ListString;
import js.annotations.NaoConverter;
import src.commom.utils.array.Itens;
import src.commom.utils.string.StringAfterFirst;

public class SicoobTranspilar {
	
//	public static GFile PATH = GFile.get("C:\\opt\\desen\\gm\\cs2019\\gm-utils\\outros\\sicoob\\");
	public static GFile PATH = SicoobDeploy.PATH;
	private static long last = 0;
	static {
		JavaToTs.config.aceitaStaticsBlocks = false;
		JavaToTs.config.colocarExclamacaoEmAtributosQueAceitamNulos = false;
		JavaToTs.config.ourTypes = false;
		JavaToTs.config.tabReplace = "  ";
	}
	
	public static void main(String[] args) {
		exec(Itens.class);
//		all();
	}
	
	public static void all() {
		
		last = getModificacao(JavaToTs.class);
		last = getModificacao(JavaLoad.class);
		
//		PATH.delete();
		Palavra.emAnalise = false;
//		GetClasses.sicoob().each(i -> exec(i));
		
		GetClasses.all().each(i -> exec(i, true));
//		exec(JsMap.class);
	}

	public static void exec() {
		exec(GetMainClass.get());
	}
	
	public static void exec(Class<?> classe) {
		exec(classe, true);
	}
	
	public static void exec(Class<?> classe, boolean checarTime) {
		
		SystemPrint.ln(classe);
		
		if (classe == Itens.class) {
			return;
		}
		
		if (classe.isAnnotationPresent(NaoConverter.class)) {
			return;
		}
		
		String s = StringAfterFirst.get(classe.getName(), "src.").replace(".", "/").replace("__", "-") + ".ts";
		
		GFile file = PATH.join("src").join(s);
		
		if (checarTime && file.exists() && file.lastModified() > getModificacao(classe)) {
			return;
		}
		
//		s = "c:/dev/projects/cre-concessao-bndes-web/src/" + s;
		SystemPrint.ln("==============================================");
		SystemPrint.ln("===========" + classe + "=============");
		SystemPrint.ln("==============================================");
		ListString list = JavaToTs.execJavaToTs(classe);
		
		file.delete();
		list.save(file);
		
	}
	
	private static long getModificacao(Class<?> classe) {
		long o = UClass.getJavaFile(classe).lastModified();
		if (o < last) {
			return last;
		} else {
			return o;
		}
	}
	
}
