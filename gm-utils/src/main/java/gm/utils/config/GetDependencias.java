package gm.utils.config;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;

public class GetDependencias {

	private static final GFile pomAst = GFile.get("/opt/desen/gm/cs2019/gm.ast2/pom.xml");
	private static final GFile cleanInstall = GFile.get("/opt/desen/gm/cs2019/cleanInstall");
	
	private Lst<GFile> poms = new Lst<>();
	private ListString groups = ListString.array("react","reacts","gm");
	private Lst<GFile> projetos = new Lst<>();

	public static Lst<GFile> get() {
		return new GetDependencias().projetos;
	}

	private GetDependencias() {
		GFile pom = GFile.userDir().join("pom.xml");
		exec(pom);
		poms.remove(pomAst);
		projetos = poms.map(i -> i.getPath());
	}

	private void exec(GFile pom) {

		if (poms.contains(pom)) {
			return;
		}

		poms.add(pom);

		ListString list = new ListString().load(pom);

		list.trimPlus();

		while (!"<dependencies>".equalsIgnoreCase(list.get(0))) {
			list.remove(0);
		}

		while (!"</dependencies>".equalsIgnoreCase(list.getLast())) {
			list.removeLast();
		}

		list.remove(0);
		list.removeLast();

		list.removeIf(s -> !s.startsWith("<groupId>") && !s.startsWith("<artifactId>"));

		ListString itens = new ListString();

		while (!list.isEmpty()) {

			String groupId = get(list.remove(0));
			String artifactId = get(list.remove(0));

			if (groups.contains(groupId) && !itens.contains(artifactId)) {
				itens.add(artifactId);
			}
		}

		for (String s : itens) {
			GFile projeto = cleanInstall.join(s);
			GFile file = projeto.join("pom.xml");
			if (!file.exists()) {
				SystemPrint.err("file not exists: " + file);
				continue;
			}
			projetos.add(projeto);
			ListString pomClean = new ListString().load(file);
			pomClean.trimPlus();
			s = pomClean.filter(o -> o.startsWith("<module>")).get(0);
			s = get(s);
			s = StringAfterFirst.obrig(s, "../../");
			s = "/opt/desen/gm/cs2019/" + s + "/pom.xml";
			exec(GFile.get(s));
		}

	}

	private static String get(String s) {
		s = StringAfterFirst.get(s, ">");
		return StringBeforeFirst.get(s, "<");
	}

}
