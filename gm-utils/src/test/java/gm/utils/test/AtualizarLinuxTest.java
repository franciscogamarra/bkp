package gm.utils.test;

import java.io.File;

import gm.utils.comum.USystem;
import gm.utils.date.Data;
import gm.utils.outros.TerminalLinux;
import gm.utils.string.ListString;
import js.support.console;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;

public class AtualizarLinuxTest {

	public static void exec(TerminalLinux terminal, String s) {
		terminal.sudo();
		terminal.exec(s);
	}

	public static void exec(String s) {
		exec(new TerminalLinux(), s);
	}

	private static void update() {

		print("update");

		TerminalLinux t = new TerminalLinux();

		try {
			exec(t, "apt update");
		} catch (Exception e) {
			String s = t.saida.toString("");
			if (StringContains.is(s,
					"As assinaturas a seguir não puderam ser verificadas devido à chave pública não estar disponível")) {
				resolverProblemasDeChavePublica(t);
			} else if (StringContains.is(s, "404  Not Found")) {
				resolverProblemas404(t);
			} else {
				e.printStackTrace();
			}
		}
	}

	private static void print(String s) {
		console.log("=======================================================");
		console.log("=================== " + s + " ========================");
		console.log("=======================================================");
	}

	private static void resolverProblemas404(TerminalLinux t) {

		ListString saida = t.saida.copy();

		exec("sudo sed -i -e 's/archive.ubuntu.com\\|security.ubuntu.com/old-releases.ubuntu.com/g' /etc/apt/sources.list");

		exec("chmod 777 /etc/apt/sources.list");
		ListString file = new ListString().load("/etc/apt/sources.list");
		file.removeIfStartsWith("#");

		for (String s : saida) {
			if (s.trim().startsWith("Não foi possível resolve")) {
				s = StringAfterFirst.obrig(s, "'");
				s = StringBeforeFirst.obrig(s, "'");
				file.removeIfContains(s);
			} else if (s.trim().startsWith("404  Not Found")) {
				s = saida.get(saida.indexOf(s) - 1).trim();
				s = StringAfterFirst.obrig(s, " ");
				s = StringBeforeLast.obrig(s, " ");
				file.removeIfContains(s);
			}
		}

		file.removeEmptys();

		if (file.save()) {
			update();
		}

	}

	private static void resolverProblemasDeChavePublica(TerminalLinux t) {
		String s = t.saida.toString(" ");
		s = StringAfterFirst.obrig(s,
				"As assinaturas a seguir não puderam ser verificadas devido à chave pública não estar disponível: NO_PUBKEY ");
		s = StringBeforeFirst.get(s, " ");
		exec("sudo apt-key adv --keyserver keyserver.ubuntu.com --recv-keys " + s);
		update();
	}

//	@Test
	public void exec() {

		if (!USystem.hostGamarraCooper() && !USystem.hostCasa()) {
			return;
		}

		File file = new File("outros/" + USystem.hostName() + "/ultima-atualizacao-linux.txt");

		if (file.exists() && new Data(file.lastModified()).isHoje()) {
			return;
		}

		run();

		ListString list = new ListString();
		list.add("linux atualizados: " + Data.now());
		list.save(file);

	}

	private void run() {

		try {
			update();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			autoremove();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			autoclean();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			updateVsCode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			updateVsCode2();
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			upgrade();
		} catch (Exception e) {
			e.printStackTrace();
		}
//

	}

	public static void main(String[] args) {
		new AtualizarLinuxTest().run();
//		updateVsCode();
//		updateVsCode2();
	}

	private static void autoclean() {
		print("autoclean");
		exec("apt -y autoclean");
	}

	private static void autoremove() {
		print("autoremove");
		exec("apt -y autoremove");
	}

	private static void upgrade() {
		print("upgrade");
		exec("apt -y dist-upgrade");
	}

	private static void updateVsCode() {
		print("updateVsCode");
//		exec("wget https://vscode-update.azurewebsites.net/latest/linux-deb-x64/stable -O /tmp/code_latest_amd64.deb --no-check-certificate");
		exec("dpkg -i /tmp/code_latest_amd64.deb");
	}

	private static void updateVsCode2() {
		print("updateVsCode2");
		exec("sudo apt-get update");
		exec("sudo apt-get install code");
	}

//	public static void main(String[] args) {
//		TerminalLinux terminal = new TerminalLinux();
//		terminal.cd("/opt/desen/gm/cs2019/extras/fuvio/fuvio-app/");
//		terminal.exec("expo publish --release-channel teste");
//	}

}
