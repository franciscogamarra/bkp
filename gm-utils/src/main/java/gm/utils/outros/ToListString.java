package gm.utils.outros;

import gm.utils.string.ListString;
import gm.utils.string.StringClipboard;

public class ToListString {
	public static void main(String[] args) {
		ListString list = new ListString();
		list.add(StringClipboard.get().split("\n"));
		list.replaceTexto("\"", "\\\"");
		list.addLeft("list.add(\"");
		list.addRight("\");");
		list.print();
	}
}

/*

[submodule "cooperforte-corporativo"]
	path = cooperforte-corporativo
	url = http://apgit.cooperforte.coop/cooperforte/cooperforte-corporativo.git
[submodule "cooperforte-backend-boletobancario"]
	path = cooperforte-backend-boletobancario
	url = http://apgit.cooperforte.coop/cooperforte/cooperforte-backend-boletobancario.git
[submodule "cooperforte-facade-recebimento"]
	path = cooperforte-facade-recebimento
	url = http://apgit.cooperforte.coop/cooperforte/cooperforte-facade-recebimento.git
[submodule "cooperforte-app-sicop"]
	path = cooperforte-app-sicop
	url = http://apgit.cooperforte.coop/cooperforte/cooperforte-app-sicop.git

*/

/*

<!-- SO CONSEGUI ADICIONAR O cooperforte-40view-webbanking NO EAR REMOVENDO A LINHA ABAIXO -->
<!--
<listener>
	<listener-class>org.jboss.resteasy.plugins.server.servlet.ResteasyBootstrap</listener-class>
</listener>
 -->



*/