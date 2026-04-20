package fix.was;

import br.support.dev.GFile;

public class WasClear {
	
	public static void main(String[] args) {
		deleteFiles("c:/dev/ibm/configapp/installedapps/servers/server1");
		deleteFiles("c:/dev/ibm/websphere/appserver/profiles/appsrv01/logs");
		deleteFiles("c:/dev/ibm/websphere/appserver/profiles/appsrv01/temp");
		deleteFiles("c:/dev/ibm/websphere/appserver/profiles/appsrv01/wstemp");
		deleteFiles("c:/dev/ibm/websphere/appserver/profiles/appsrv01/workspace");
		deleteFiles("c:/dev/ibm/websphere/appserver/profiles/appsrv01/config/temp");
		deleteFiles("c:/dev/ibm/websphere/appserver/profiles/appsrv01/installedApps/cell01");
		deleteFiles("c:/dev/ibm/websphere/appserver/profiles/appsrv01/bin/client_ffdc");
		
		deleteEars("c:/dev/ibm/websphere/appserver/profiles/appsrv01/config/cells/cell01/blas");
		deleteEars("c:/dev/ibm/websphere/appserver/profiles/appsrv01/config/cells/cell01/cus");
		
		
		GFile.get("c:/dev/ibm/websphere/appserver/profiles/appsrv01/config/cells/cell01/applications")
		.getItens().filter(i -> i.getSimpleName().startsWith("cre-")).forEach(i -> i.delete());
		
		deleteLogs(GFile.get("c:/dev/ibm"));
		
	}
	
	private static void deleteLogs(GFile dir) {
		dir.getFiles().forEach(i -> deleteLog(i));
		dir.getDirs().forEach(d -> deleteLogs(d));
	}

	private static void deleteLog(GFile file) {
		if (file.isExtensao("log")) {
			file.delete();
		}
	}

	private static void deleteEars(String s) {
		GFile.get(s).getDirs().filter(i -> i.toString().endsWith("-ear")).forEach(i -> i.delete());
	}

	private static void deleteFiles(String s) {
		GFile.get(s).deleteItens();		
	}
	

}