package gm.utils.string;

import java.util.function.Predicate;

import gm.utils.classes.UClass;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import lombok.Getter;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringRight;

@Getter
public class Java {

	/*
	um comentario
	*/
	
	private String file;
	private final ListString list = new ListString();
	private Class<?> classe;

	private ListString strings = new ListString();

	public Java(String file) {
		this.file = file;
		reLoad();
	}

	public void reLoad() {

		list.clear();

		if (UFile.exists(file)) {
			ListString lst = new ListString().load(file);
			lst.removeLastEmptys();
			lst.removeFisrtEmptys();

			int index = 0;
			ListString lst2 = new ListString();

			for (String s : lst) {
				s = s.replace("\\\"", "[ESCAPE_ASPA]");
				while (StringContains.is(s, "\"")) {
					String before = StringBeforeFirst.get(s, "\"");
					s = StringAfterFirst.get(s, "\"");
					String string = StringBeforeFirst.get(s, "\"");
					strings.add(string);
					s = StringAfterFirst.get(s, "\"");
					s = before + "[STRING"+index+"]" + s;
					index++;
				}
				lst2.add(s);
			}
			
			for (String s : lst2) {
				if (StringContains.is(s, "//")) {
					String s2 = StringAfterFirst.get(s, "//");
					s = StringBeforeFirst.get(s, "//");
					s += "/*"+s2+"*/";
				}
				list.add(s);
			}
		}
		
	}

	public void replaceStrings(ListString list) {
		for (int i = 0; i < strings.size(); i++) {
			list.replaceTexto("[STRING"+i+"]", "\"" + strings.get(i) + "\"");
		}
		list.replaceTexto("[ESCAPE_ASPA]", "\\\"");
	}

	public void save() {
		replaceStrings(list);
		ListString filter = list.filter(s -> s.startsWith("/*") && s.endsWith("*/"));
		for (String s : filter) {
			String x = "//" + StringRight.ignore(s.substring(2),2);
			list.replace(s, x);
		}
		list.save(file);
	}
	
	public void print() {
		replaceStrings(list);
		list.print();
	}

	public void add(String s) {
		list.add(s);
	}

	public Java copy() {
		return new Java(file);
	}

	public ListString getList() {
		reLoad();
		return list.copy();
	}

	public void save(ListString list) {
		this.list.clear();
		this.list.add(list);
		save();
	}

	public void removeIf(Predicate<String> filter) {
		list.removeIf(filter);
		list.removeLastEmptys();
	}

	public void removeLast() {
		list.removeLast();
		list.removeLastEmptys();
	}

	public void add() {
		list.add();
	}

	public Class<?> getClasse() {

		if (classe != null) {
			return classe;
		}

		for (String s : list) {
			if (s.startsWith("package ")) {
				s = StringAfterFirst.get(s, " ");
				s = StringBeforeLast.get(s, ";");
				s += "." + StringAfterLast.get(file, "/");
				s += "." + StringBeforeLast.get(s, ".java");
				classe = UClass.getClass(s);
				return classe;
			}
		}

		throw UException.runtime("Não foi possível determinar a classe");

	}

	public boolean contains(String s) {
		return getString().contains(s);
	}

	public String getString() {
		return list.toString(" ");
	}

	public void removeComentarios() {

		boolean repetir = true;

		while (repetir) {

			repetir = false;
			ListString lst = new ListString();

			while (!list.isEmpty()) {
				String s = list.remove(0);
				if (s.trim().startsWith("/*")) {
					while (!StringContains.is(s, "*/")) {
						s = list.remove(0);
					}
					s = StringAfterFirst.get(s, "*/");
					if (!s.isEmpty()) {
						lst.add(s);
					}
					repetir = true;
				} else {
					lst.add(s);
				}
			}

			list.add(lst);

		}

		ListString lst = list.copy();
		list.clear();
		lst.removeIfTrimStartsWith("//");
		for (String s : lst) {
			if (StringContains.is(s, "//")) {
				s = StringBeforeFirst.get(s, "//");
			}
			list.add(s);
		}

	}

}
