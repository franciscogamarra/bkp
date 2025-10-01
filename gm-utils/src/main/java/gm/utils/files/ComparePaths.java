package gm.utils.files;

import java.io.File;
import java.util.List;

import gm.utils.comum.Lst;
import gm.utils.comum.USystem;
import gm.utils.string.ListString;
import js.support.console;

public class ComparePaths {

	private static Lst<Thread> threads = new Lst<>();
	private static ListString diferentes = new ListString();

	public static void main(String[] args) {
		String a = "/opt/desen/gm/my-lombok/gmrr-lombok-master";
		String b = "/opt/desen/gm/my-lombok/lombok-master";
		exec(a,b);
	}

	private static void exec(String path1, String path2) {

		execute(path1, path2);

		while (threads.anyMatch(o -> o.isAlive())) {
			console.log("Aguardando");
			USystem.sleepSegundos(5);
		}

		for (String s : diferentes) {
			console.log( "diff " + s + " " + s.replace(path1, path2) );
		}

	}

	private static void execute(String path1, String path2) {

		if (!UFile.exists(path1)) {
			return;
		}

//		Thread thread = new Thread() {
//			@Override
//			public void run() {

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
						diferentes.addIfNotContains(p1);
					} else {
						ListString f1 = new ListString().load(p1);
						ListString f2 = new ListString().load(p2);
						if (!f1.eq(f2)) {
							diferentes.add(p1);
							diferentes.saveTemp();
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

//			}
//		};
//
//		threads.add(thread);
//		thread.start();

	}

}
