package gm.utils.outros;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

import gm.utils.comum.CredenciaisDeRede;
import gm.utils.comum.USystem;
import gm.utils.exception.UException;
import gm.utils.lambda.F1;
import gm.utils.string.ListString;
import js.support.console;
import src.commom.utils.integer.IntegerParse;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;

public class TerminalLinux {

	private String path = "/";
	public ListString saida = new ListString();
	private boolean sudo;
	private String password;

	public void exec(String s) {

		try {

			if (sudo) {
				s = "echo '"+getPassword()+"' | sudo -S " + s;
			}

			ProcessBuilder processBuilder = new ProcessBuilder();

			if (path != null) {
				processBuilder.directory(new File(path));
//				processBuilder.command("bash", "-c", "ls /home/");
			}

			console.log(s);
			processBuilder.command("bash", "-c", s);

			Process process = processBuilder.start();

			BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

			console.log("readLines - start");

			String line;
			while ((line = reader.readLine()) != null) {
				console.log(line);
				saida.add(line);
			}

			console.log("readLines - end");

			console.log("process.waitFor() - start");
			int exitCode = process.waitFor();
			console.log("process.waitFor() - end");

			if (exitCode > 0) {
				throw new RuntimeException("Exited with error code : " + exitCode);
			}

		} catch (Exception e) {
			throw UException.runtime(e);
		}

	}

	private String getPassword() {
		if (password != null) {
			return password;
		}
		String s = System.getProperty("senha-linux");
		if (s != null) {
			return s;
		}
		if (estouNaCooper()) {
			return CredenciaisDeRede.getPass();
		} else if (USystem.hostIs("gamarra-Aspire-A515-51G")) {
			return "@Gamarra04";
		} else {
			throw new RuntimeException("Defina a senha sudo: " + USystem.hostName());
		}
	}

	public static boolean estouNaCooper() {
		return USystem.hostGamarraCooper();
	}

	public void cd(String path) {
		this.path = path;
	}

	public void kill(int id) {
		exec("kill -9 " + id);
	}

	public void kill(String s) {
		s = StringAfterFirst.get(s, " ").trim();
		s = StringBeforeFirst.get(s, " ").trim();
		int id = IntegerParse.toInt(s);
		kill(id);
	}

	public void killGrep(String word, F1<String,Boolean> func) {
		String x = "ps -ef | grep " + word;
		saida.clear();
		exec(x);
		saida.removeIf(s -> s.endsWith(x));
		saida.removeIf(s -> s.endsWith("grep " + word));
		for (String s : saida) {
			if (func.call(s)) {
				kill(s);
			}
		}

	}

	public static void main(String[] args) {

//		TerminalLinux terminal = new TerminalLinux();
//		terminal.cd("/opt/desen/gm/cs2019/extras/consolidate/consolidate-front");
//		terminal.cd("/opt/desen/gm/cs2019/extras/consolidate/consolidate-back");
//		terminal.exec("mvn clean install -DskipTests");

//		terminal.exec("git pull");
//		terminal.exec("npm install").print();
//		console.log("----");
//		itens.print();
//		terminal.killGrep("node", s -> s.contains("node_modules") || s.contains("/usr/share/code") || s.contains("vscode"));
//		terminal.killGrep("vscode", s -> true);

	}

	public void sudo() {
		sudo = true;
	}

	public void sudo(String password) {
		this.password = password;
		sudo = true;
	}

}
