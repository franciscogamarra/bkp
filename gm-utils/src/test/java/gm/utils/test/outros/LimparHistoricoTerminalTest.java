package gm.utils.test.outros;

import org.junit.Test;

import gm.utils.comum.SO;
import gm.utils.comum.USystem;
import gm.utils.files.UFile;
import gm.utils.string.ListString;

public class LimparHistoricoTerminalTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		
		if (!SO.linux()) {
			return;
		}

		if (!USystem.hostGamarraCooper() && !USystem.hostCasa()) {
			return;
		}

		ListString list = new ListString();
		list.add("mvn clean install");
		list.add("git ok");
		list.add("npm start");
		list.save(getFile());
	}

	private static String getFile() {
		if (UFile.exists("/home/cooperforte.coop@gamarra/.bash_history")) {
			return "/home/cooperforte.coop@gamarra/.bash_history";
		} else {
			return "/home/gamarra/.bash_history";
		}
	}

}
