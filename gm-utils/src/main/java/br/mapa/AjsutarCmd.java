package br.mapa;

import gm.utils.string.ListString;

public class AjsutarCmd {

	public static void main(String[] args) {
		ListString list = new ListString();
		list.load("C:\\MAPA\\WebLogic\\user_projects\\domains\\base_domain\\startWebLogic.cmd");
		list.trimPlus();
		list.removeIfStartsWith("@REM ");
		String linhaDebug = "set JAVA_OPTIONS=%JAVA_OPTIONS% -Xdebug -Xnoagent -Xrunjdwp:transport=dt_socket,address=8453,server=y,suspend=n";
		if (!list.contains(linhaDebug)) {
			list.add(-2, linhaDebug);
		}
		list.save();
	}
	
}