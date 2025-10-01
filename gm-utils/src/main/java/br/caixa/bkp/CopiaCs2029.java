package br.caixa.bkp;

import gm.utils.files.GFile;

public class CopiaCs2029 {
	
	CopiaCs2029(boolean paraCs2019) {
		
		GFile pathCx = GFile.get("c:/desenvolvimento/dev/quarkus");
		GFile pathCs = GFile.get("c:/desenvolvimento/opt/desen/gm/cs2019/caixa");
		
		GFile origem = paraCs2019 ? pathCx : pathCs;
		GFile destino = paraCs2019 ? pathCs : pathCx;
		
		origem.getDirs().filter(i -> i.getSimpleName().startsWith("LOTERIAS-")).forEach(i -> {
			
			GFile path = destino.join(i.getSimpleName());
			
			GFile srcDestino = path.join("src");
			srcDestino.delete();
			
			GFile pomDestino = path.join("pom.xml");
			pomDestino.delete();
			
			GFile srcOrigem = i.join("src");
			GFile pomOrigem = i.join("pom.xml");
			
			srcOrigem.copy(srcDestino);
			pomOrigem.copy(pomDestino);
			
		});
		
		
	}
	
	public static void main(String[] args) {
//		GFile.get("C:/desenvolvimento/opt/desen/gm/cs2019/.git/index.lock").print();
		new CopiaCs2029(false);
	}

}
