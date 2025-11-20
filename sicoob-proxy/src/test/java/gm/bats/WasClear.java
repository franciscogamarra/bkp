package gm.bats;

import br.utils.DevException;
import br.utils.GFile;
import br.utils.Lst;
import br.utils.Sleep;

public class WasClear {
	
	private static Lst<GFile> destinos = new Lst<>();

	public static void main(String[] args) {
		exec("c:/dev/projs/cre-concessao-bndes-api/ear/target", "api");
		exec("c:/dev/projs/cre-concessao-bndes-api/processamento-ear/target", "processamento");
		exec("c:/dev/projs/cre-concessao-bndes/ear/cre-concessao-bndes-integracao-ear/target", "integracao");
		exec("C:/dev/projs/cre-concessao-bndes/EAR/cre-concessao-bndes-backoffice-ear/target", "backoffice");
		
		Sleep.minutos(5);
		
		destinos.forEach(i -> i.delete());
		
	}

	private static void exec(String target, String nome) {
		
		Lst<GFile> ears = GFile.get(target).getFiles().filter(i -> i.isExtensao("ear"));
		if (ears.isEmpty()) {
			throw DevException.build("Nao encontrado nenhum ear: " + target);
		}
		
		ears.sort(i -> i.getModificacao());
		
		GFile ear = ears.getLast();
		GFile destino = GFile.get("c:/dev/ibm/ears/"+nome+".ear");
		ear.copy(destino);
		
		System.out.println("de   " + ear.toString().replace("/", "\\").toLowerCase());
		System.out.println("para " + destino);
		System.out.println("=========================================================");
		
		destinos.add(destino);
		
	}

}