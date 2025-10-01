package gm.utils.comum;

import gm.utils.string.ListString;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRight;

public class Identar {
	
	public static void main(String[] args) {
//		String s = StringClipboard.get();	
		String s = "function ehUmEndpointQuarkus(url) { if (url.endsWith(\"/\")) { url = url.substring(0, url.length-1); } const paths = url.split(\"/\"); for (let i = 0; i < urlsQuarkus.length; i++) { let s = urlsQuarkus[i]; const paths2 = url.split(\"/\"); if (paths2.length != paths.length) { continue; } for (let j = 0; j < paths.length; j++) { s = paths[j]; if (s.startsWith(\"{\")) { continue; } if (s !== paths2[j]) { return false; } } return true; } }";
		exec(s);
	}
	
	public static void exec(String s) {
		s = s.replace("{", "{\n");
		s = s.replace("}", "\n}\n");
		s = s.replace(";", ";\n");
		ListString list = ListString.byDelimiter(s, "\n");
		Lst<String> lst = new Lst<>(list);
		exec(lst);
		lst.print();
	}
	
	private final Lst<String> list = new Lst<>();
	private String margem = "";

	private Identar(Lst<String> list) {
		for (String s : list) {
			add(s);
		}
		list.clear();
		list.addAll(this.list);
	}
	
	public static void exec(Lst<String> list) {
		new Identar(list);
	}
	
	private void add(String s) {

		s = s.trim();
		
		while (s.endsWith(";;")) {
			s = StringRight.ignore1(s);
		}

		if (s.trim().startsWith("}") || s.startsWith(")") || s.startsWith("]")) {
			while (StringEmpty.is(getLast())) {
				removeLast();
			}
			String last = getLast();
			if (last.endsWith(",")) {
				removeLast();
				last = StringRight.ignore1(last).trim();
				list.add(last);
			}
			
			margem = margem.substring(1);
			
		}
		
		if (s.trim().startsWith("}") && list.getLast().endsWith("{")) {
			list.add(list.removeLast() + "}");
			return;
		}		
		
		list.add(margem + s);
		
		if (StringContains.is(s, "//")) {
			s = StringBeforeFirst.get(s, "//");
		}
		
		if (s.endsWith("{") || s.endsWith("(") || s.endsWith("[")) {
			margem += "\t";
		}
		
	}

	private void removeLast() {
		list.removeLast();
	}

	private String getLast() {
		return list.getLast();
	}

}
