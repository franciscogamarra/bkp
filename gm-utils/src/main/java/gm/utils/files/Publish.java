package gm.utils.files;

import java.io.File;
import java.util.List;

import gm.utils.string.ListString;

public class Publish {

	private final String origem;
	private final String destino;

	public Publish(String origem, String destino) {
		this.origem = origem;
		this.destino = destino;
		excluir();
		UFile.criaDiretorio(this.destino);
		copiar();
	}

	private void copiar() {

		List<File> list = UFile.getAllFiles(new File(origem));

		list.removeIf(o -> {

			String full = o.toString();
			if (full.contains(".vscode") || full.contains("/target/") || full.contains("/src/test/java/") || full.contains("/node_modules/")) {
				return true;
			}
			
//			if (full.contains(".gitignore")) return true;
			if (full.contains(".git") || full.contains(".settings") || full.contains("arquivoControle.js"))
			 {
				return true;
//			if (full.contains("application.properties")) return true;
			}

			String name = o.getName();
			//			if (name.contains("README.md")) return true;
			if (name.contains(".vscode") || name.endsWith(".log")) {
				return true;
			}

			return false;
		});

		for (File file : list) {
			if (file.getName().contentEquals("pom.xml")) {
				ListString a = new ListString().load(file);
				a.removeIfContains("[remove]");
				a.save(file.toString().replace(origem, destino));
			} else {
				UFile.copy(file, new File(file.toString().replace(origem, destino)));
			}
		}
	}

	private void excluir() {

		if (!UFile.exists(destino)) {
			return;
		}

		List<File> list = UFile.getFilesAndDirectories(new File(destino));
		list.removeIf(o -> {
			String full = o.toString();
			if (full.contains(".git")) {
				return true;
			}

//			String name = o.getName();
//			if (name.contains("README.md")) return true;
			return false;
		});

		for (File file : list) {
			UFile.delete(file);
		}
	}

	public static void main(String[] args) {

		new Publish("/opt/desen/gm/cs2019/notec/taxas/web/"                   , "/opt/desen/git/notec/cooper-gecof-taxas/cooper-gecof-taxas-front/");
		new Publish("/opt/desen/gm/cs2019/notec/taxas/cooper-gestao-de-taxas/", "/opt/desen/git/notec/cooper-gecof-taxas/cooper-gecof-taxas-back/");
		new Publish("/opt/desen/gm/cs2019/gm-frame-constructor/fc-core-back/" , "/opt/desen/git/notec/tools/fc-core-back/");

		ListString properties = new ListString().load("/opt/desen/gm/cs2019/notec/taxas/cooper-gestao-de-taxas/src/main/resources/application.properties");
		properties.removeIfStartsWith("#");
		properties.removeDoubleWhites();
		properties.save("/opt/desen/git/notec/cooper-gecof-taxas/cooper-gecof-taxas-back/src/main/resources/application.properties");

	}

}
