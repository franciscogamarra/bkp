package br.caixa;

import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.integer.IntegerIs;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringBeforeFirst;

public class RenomearTests {
	
	private static final Lst<Integer> utilizados = new Lst<>();
	
	public static int getProximoNumeroDisponivel() {
		int i = 1;
		while (utilizados.contains(i)) {
			i++;
		}
		utilizados.add(i);
		return i;
	}

	public static void main(String[] args) {
		
		Lst<GFile> files = GFile.get("C:\\desenvolvimento\\dev\\quarkus\\LOTERIAS-SILCE-carrinho\\src\\test\\java\\br\\caixa\\loterias\\silce").getAllFiles();
		files.removeIf(i -> !i.isJava());
		files.removeIf(i -> !i.getSimpleNameWithoutExtension().endsWith("Test"));
		files.removeIf(i -> i.getSimpleNameWithoutExtension().endsWith("BaseTest"));
		
		files.removeIf(i -> {
			
			String name = i.getSimpleNameWithoutExtension();
			
			if (name.startsWith("T") && name.contains("_")) {
				
				String s = StringBeforeFirst.get(name, "_").substring(1);
				
				if (IntegerIs.is(s)) {
					utilizados.add(IntegerParse.toInt(s));
					return true;
				}
				
			}
			
			return false;
			
		});
		
		for (GFile file : files) {
			
			String name = file.getSimpleNameWithoutExtension();
			
			String snumero = IntegerFormat.xxxx(getProximoNumeroDisponivel());
			
			GFile novoFile = file.getPath().join("T" + snumero + "_" + name + ".java");
			
			file.rename(novoFile);
			
			novoFile.load().replaceTexto("public class "+name+" ", "public class T"+snumero+"_"+name+" ").save();
			
		}
		
	}
	
}