package gm.utils.files;

import java.io.File;
import java.util.ConcurrentModificationException;
import java.util.List;

import gm.utils.comum.Lst;
import gm.utils.comum.USystem;
import gm.utils.string.ListString;
import js.support.console;

public class ComparePathsFileSize {

	private static Lst<Thread> threadsNaoIniciadas = new Lst<>();
	private static Lst<Thread> threadsEmAndamento = new Lst<>();
	private static ListString diferentes = new ListString();

	public static void main(String[] args) {
		String a = "/opt/desen/gm/cs2019/gm-frame-constructor/fc-main";
		String b = "/opt/desen/gm/cs20190/gm-frame-constructor/fc-main";
		exec(a,b);
	}

	private static void exec(String path1, String path2) {

		execute(path1, path2);

		while (isAlive()) {
			console.log("Aguardando " + diferentes.size());
			USystem.sleepSegundos(5);
		}

		for (String s : diferentes) {
			console.log( "diff " + s + " " + s.replace(path1, path2) );
		}

	}

	private static boolean isAlive() {

		threadsEmAndamento.removeIf(o -> !o.isAlive());

		while (threadsEmAndamento.size() < 10 && !threadsNaoIniciadas.isEmpty()) {

			try {
				Thread thread = threadsNaoIniciadas.remove(0);
				thread.start();
				threadsEmAndamento.add(thread);
			} catch (ConcurrentModificationException e) {
				console.log("Rolou um ConcurrentModificationException mas tá de boa");
				USystem.sleepSegundos(1);
			}

		}

		return !threadsEmAndamento.isEmpty();

	}

	private static void execute(String path1, String path2) {

		if (!UFile.exists(path1)) {
			return;
		}

		Thread thread = new Thread() {
			@Override
			public void run() {

				List<File> files = UFile.getJavas(path1);
				for (File file : files) {

					String p1 = file.toString();

					if (diferentes.contains(p1)) {
						continue;
					}

					String p2 = p1.replace(path1, path2);

					if (diferentes.contains(p2)) {
						continue;
					}

					if (!UFile.exists(p2)) {
						addDiferente(p2);
					} else {

						File file2 = new File(path2);

						if (file.length() != file2.length()) {
							addDiferente(p1);
						}

					}
				}

				List<File> directories = UFile.getDirectories(path1);
				for (File file : directories) {
					String p1 = file.toString();
					String p2 = p1.replace(path1, path2);
					execute(p1, p2);
					execute(p2, p1);
				}

			}

		};

		add(thread);

	}

	private static void addDiferente(String s) {

		try {
			diferentes.add(s);
			diferentes.saveTemp();
		} catch (ConcurrentModificationException e) {
			console.log("Rolou um ConcurrentModificationException mas tá de boa");
			addDiferente(s);
		}

	}

	private static void add(Thread thread) {
		try {
			threadsNaoIniciadas.add(thread);
		} catch (ConcurrentModificationException e) {
			console.log("Rolou um ConcurrentModificationException mas tá de boa");
			add(thread);
		}
	}

}
