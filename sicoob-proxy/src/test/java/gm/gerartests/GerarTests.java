package gm.gerartests;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.files.GFile;

public class GerarTests {

	public static void main(String[] args) {
		
		GFile.class.getName();
		
		GFile path = GFile.get("C:/dev/projects/cre-concessao-bndes-api/web/src/main/java/");
		
		Lst<GFile> list = path.getAllFiles().filter(i -> i.isJava());

		list.map(i -> UClass.getClass(i.getJavaClassName())).forEach(i -> exec(i));
		
//		BancoobExceptionMapper
//		GFile
//		C:\dev\projects\cre-concessao-bndes-api\web\src\main\java\br\com\sicoob\concessao\bndes\exceptionmapper\DataSolicitacaoInvestimentoInvalidaExceptionMapper.java
		
	}

	private static void exec(Class<?> classe) {
		
	}
	
}