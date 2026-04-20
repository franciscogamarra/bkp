package gm.bats;

import br.support.dev.GFile;
import br.utils.strings.Substring;

public class ListarArquivos {

	public static void main(String[] args) {
//		
		GFile.get("C:/dev/IBM/WebSphere/AppServer/profiles/AppSrv01/config/cells").getFiles().print();
		
//		GFile.get("C:\\dev\\IBM\\WebSphere\\AppServer\\runtimes").getFiles().print();
//		GFile.get("C:\\dev\\IBM\\WebSphere\\AppServer\\plugins").getFiles().print();
//		GFile.get("C:\\dev\\IBM\\WebSphere\\AppServer\\lib").getFiles().print();
		
//		copy("runtimes/com.ibm.ws.admin.client_9.0.jar");
//		
//		copy("C:/dev/IBM/WebSphere/AppServer/runtimes/com.ibm.ws.admin.client_9.0.jar");
//		copy("C:/dev/IBM/WebSphere/AppServer/runtimes/com.ibm.ws.admin.client.forJython21_9.0.jar");
//		copy("C:/dev/IBM/WebSphere/AppServer/runtimes/com.ibm.ws.orb_9.0.jar");
//		copy("C:/dev/IBM/WebSphere/AppServer/runtimes/com.ibm.ws.ejb.thinclient_9.0.jar");
//		copy("C:/dev/IBM/WebSphere/AppServer/plugins/com.ibm.ws.admin.core.jar");
//		copy("C:/dev/IBM/WebSphere/AppServer/plugins/com.ibm.ws.runtime.jar");
//		copy("C:/dev/IBM/WebSphere/AppServer/plugins/com.ibm.ws.admin.services.jar");
//		copy("C:/dev/IBM/WebSphere/AppServer/plugins/com.ibm.ws.security.crypto.jar");
		
		
	}

	private static void copy(String s) {
		s = s.toLowerCase();
		GFile.get(s).copy("c:/dev/projs/bkp/sicoob-proxy/lib-was/" + Substring.afterLast(s, "/"));
	}
	
}
