package br.sicoob.was;

import java.io.File;

import gm.utils.comum.Lst;
import gm.utils.comum.USystem;
import gm.utils.files.GFile;
import gm.utils.files.UFile;
import src.commom.utils.tempo.DataHora;

public class WasClear {
	
	private static Lst<GFile> destinos = new Lst<>();

	public static void main(String[] args) {
//		limparLogs();
		publicar();
	}
	
	private static void publicar() {
		UFile.desprezar_target = false;
		exec("c:/dev/projects/cre-concessao-bndes-api/ear/target", "api");
		exec("c:/dev/projects/cre-concessao-bndes-api/processamento-ear/target", "processamento");
		exec("c:/dev/projects/cre-concessao-bndes/ear/cre-concessao-bndes-integracao-ear/target", "integracao");
		exec("C:/dev/projects/cre-concessao-bndes/EAR/cre-concessao-bndes-backoffice-ear/target", "backoffice");
		System.out.println(DataHora.now());
		
		USystem.sleepMinutos(5);
		
		destinos.forEach(i -> i.delete());
	}

	private static void exec(String target, String nome) {
		
		Lst<GFile> ears = GFile.get(target).filter(i -> i.isExtensao("ear"));
		if (ears.isEmpty()) {
			throw new RuntimeException("Nao encontrado nenhum ear: " + target);
		}
		ears.sortData(i -> i.lastModifiedData());
		GFile ear = ears.getLast();
		GFile destino = GFile.get("c:/program files/ibm/ears/"+nome+".ear");
//		GFile destino = GFile.get("c:/program files/IBM/configApp/installedApps/servers/server1/"+nome+".ear");
		
		ear.copy(destino);
		
		System.out.println("de   " + ear.toString().replace("/", "\\").toLowerCase());
		System.out.println("para " + destino);
		System.out.println("=========================================================");
		
		destinos.add(destino);
		
	}

	public static void limparLogs() {
		UFile.deleteFilesOfPath("c:/program files/ibm/configapp/installedapps/servers/server1");
		UFile.deleteFilesOfPath("c:/program files/ibm/websphere/appserver/profiles/appsrv01/logs");
		UFile.deleteFilesOfPath("c:/program files/ibm/websphere/appserver/profiles/appsrv01/temp");
		UFile.deleteFilesOfPath("c:/program files/ibm/websphere/appserver/profiles/appsrv01/wstemp");
		UFile.deleteFilesOfPath("c:/program files/ibm/websphere/appserver/profiles/appsrv01/workspace");
		UFile.deleteFilesOfPath("c:/program files/ibm/websphere/appserver/profiles/appsrv01/config/temp");
		UFile.deleteFilesOfPath("c:/program files/ibm/websphere/appserver/profiles/appsrv01/installedApps/cell01");
		UFile.getDirectories("c:/program files/ibm/websphere/appserver/profiles/appsrv01/config/cells/cell01/blas").filter(i -> i.toString().endsWith("-ear")).each(i -> UFile.delete(i));
		UFile.getDirectories("c:/program files/ibm/websphere/appserver/profiles/appsrv01/config/cells/cell01/cus").filter(i -> i.toString().endsWith("-ear")).each(i -> UFile.delete(i));
		UFile.getFilesAndDirectories(new File("c:/program files/ibm/websphere/appserver/profiles/appsrv01/config/cells/cell01/applications")).filter(i -> i.getName().startsWith("cre-")).each(i -> UFile.delete(i));
	}
	
}