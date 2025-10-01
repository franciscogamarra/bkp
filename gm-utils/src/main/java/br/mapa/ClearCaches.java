package br.mapa;

import gm.utils.files.GFile;

public class ClearCaches {
		
	private static GFile mainPath = GFile.get("C:\\DEV\\tools\\weblogic\\middleware\\user_projects");
	private static GFile domains = mainPath.join("domains");
	private static GFile base_domain = domains.join("dev");
	private static GFile servers = base_domain.join("servers");
	private static GFile adminServer = servers.join("AdminServer");
	private static GFile consolePrefs = base_domain.join("servers/AdminServer/data/console/ConsolePreferences.xml");
	private static GFile consolePrefsModelo = domains.join("ConsolePreferences.xml");
//	private static GFile startWebLogic = base_domain.join("startWebLogic.cmd");
//	private static GFile startWebLogicModelo = domains.join("startWebLogic.cmd");
	
	public static void main(String[] args) {
//		base_domain.moveToTrash();
		adminServer.join("tmp").moveToTrash();
		adminServer.join("logs").moveToTrash();
		adminServer.join("data").moveToTrash();
		adminServer.join("cache").moveToTrash();
//		GFile.get("C:\\MAPA\\softwares\\iReport-5.1.0\\").filter(i -> i.isExtensao("log")).each(i -> i.delete());
		adminServer.getAllFiles().filter(i -> i.isExtensao("lok")).each(i -> i.delete());
		consolePrefsModelo.copy(consolePrefs);
//		startWebLogicModelo.copy(startWebLogic);
	} 

}


/*

Start server
Remove all applications deployments
Remove all jdbc configs
Stop server
Remove all tmp, caches, .lok’s, data, logs de cada um dos domains
Start server
Configure jdbc e garantir que está conectando
Stop server
Remove all tmp, caches, .lok’s, data, logs de cada um dos domains
Start server
Configurar a aplicação

*/




