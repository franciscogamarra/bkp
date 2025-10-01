package gm.utils.string;

import java.util.List;
import java.util.function.Predicate;

import gm.utils.classes.UClass;
import gm.utils.exception.UException;
import gm.utils.files.UFile;
import gm.utils.number.IsPar;
import lombok.Getter;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRight;

@Getter
public class Java2 {

	private String file;
	private Class<?> classe;

	private ListString list;
	private ListString list2;
	private ListString imports = new ListString();
	private ListString comentariosDeLinha = new ListString();
	private ListString comentariosDeBlocoMesmaLinha = new ListString();
	private ListString comentariosDeBlocoStart = new ListString();
	private ListString comentariosDeBlocoMeio = new ListString();
	private ListString comentariosDeBlocoEnd = new ListString();
	private ListString strings = new ListString();
	private ListString stringsAspasSimples = new ListString();

	public Java2(String file) {
		if (UFile.exists(file)) {
			list = new ListString().load(file);
			list.rtrim();
			list.removeLastEmptys();
			while (StringEmpty.is(list.get(0))) {
				list.remove(0);
			}
			list.replaceTexto("http://", "$HTTP$");
			list.replaceTexto("\"\\\\\"", "$BARRABARRAASPAS$");
			list.replaceTexto("\\\"", "$BARRAASPAS$");
			readComentarios();
//			readImports();
			readStrings();
		}

		this.file = file;
	}

	public String getString(int index) {
		String s = strings.get(index);
		s = s.replace("$BARRAASPAS$", "\\\"");
		s = s.replace("$BARRABARRAASPAS$", "\"\\\\\"");
		return s.replace("$HTTP$", "http://");
	}
	public String getStringAspa(int index) {
		return stringsAspasSimples.get(index);
	}

	public void replaceStrings(ListString list) {
		for (int i = 0; i < strings.size(); i++) {
			list.replaceTexto("$STR" + IntegerFormat.zerosEsquerda(i, 3)+"$", "\"" + getString(i) + "\"");
		}
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

	public Java2 copy() {
		return new Java2(file);
	}

	public ListString getList() {
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

	/**/

//	private void readImports() {
//
//		boolean ja = false;
//		list2 = new ListString();
//		while (!list.isEmpty()) {
//			String s = list.remove(0);
//			if (StringEmpty.is(s)) {
//				list2.add();
//				continue;
//			}
//			if (!s.startsWith("import ")) {
//				list2.add(s);
//				continue;
//			}
//
//			while (!StringContains.is(s, ";")) {
//				s += " " + list.remove(0).trim();
//			}
//			imports.add(s);
//			if (!ja) {
//				list2.add("$IMPORTS$");
//				ja = true;
//			}
//
//		}
//		list = list2;
//	}

	private void readStrings() {
		list2 = new ListString();
		while (!list.isEmpty()) {
			String s = list.remove(0);
			if (StringEmpty.is(s)) {
				list2.add();
				continue;
			}
			if (addStringAspasDuplas(s) || addStringAspasSimples(s)) {
				continue;
			}
			list2.add(s);
		}
		list = list2;
	}

	private boolean addStringAspasDuplas(String s) {
		if (!StringContains.is(s, "\"")) {
			return false;
		}
		while (StringContains.is(s, "\"")) {
			String before = StringBeforeFirst.get(s, "\"");
			if (before == null) {
				before = "";
			} else if (before.contains("'")) {
				return false;
			}
			s = StringAfterFirst.get(s, "\"");
			String texto = StringBeforeFirst.get(s, "\"");
			String after = StringAfterFirst.get(s, "\"");

			s = before + "$STR"+f000(strings)+"$" + after;
			strings.add(texto);

		}

		list.add(0, s);

		return true;
	}

	private boolean addStringAspasSimples(String s) {

		if (!StringContains.is(s, "'")) {
			return false;
		}
		String before = StringBeforeFirst.get(s, "'");
		if (before == null) {
			before = "";
		} else if (before.contains("\"")) {
			throw UException.runtime("nao deveria ocorrer");
		}
		s = StringAfterFirst.get(s, "'");
		String texto = StringBeforeFirst.get(s, "'");
		String after = StringAfterFirst.get(s, "'");

		s = before + "$CHR"+f000(strings)+"$" + after;
		list.add(0, s);
		stringsAspasSimples.add(texto);

		return true;

	}

	private void readComentarios() {
		list2 = new ListString();
		while (!list.isEmpty()) {
			String s = list.remove(0);

			if (StringEmpty.is(s)) {
				list2.add();
				continue;
			}

			if (addComentarioDeLinha(s) || addComentarioDeBloco(s)) {
				continue;
			}

			list2.add(s);

		}
		list = list2;
	}

	private String f000(List<?> list) {
		return IntegerFormat.zerosEsquerda(list.size(), 3);
	}

//	private static int vez = 0;

	private boolean addComentarioDeBloco(String s) {

		if (!StringContains.is(s, "/*")) {
			return false;
		}

//		vez++;
//
//		if (vez == 10) {
//			SystemPrint.ln("vez " + vez);
//		}

		String before = StringBeforeFirst.get(s, "/*");

		if (before != null && before.contains("\"")) {
			int ocorrencias = UString.ocorrencias(before, "\"");
			if (!IsPar.is(ocorrencias)) {
				return false;
			}
		}

		s = StringAfterFirst.get(s, "/*");

		if (StringContains.is(s, "*/")) {
			before += "$CBL"+f000(comentariosDeBlocoMesmaLinha)+"$";
			String comentario = StringBeforeFirst.get(s, "*/");
			comentariosDeBlocoMesmaLinha.add(tratarReplaces(comentario));
			list.add(0, before);//para ser novamente avaliada
			return true;
		}

		list2.add(before +"$CBS"+f000(comentariosDeBlocoStart)+"$");
		comentariosDeBlocoStart.add(s);

		while (true) {
			s = list.remove(0);
//			SystemPrint.ln(s);
			if (StringContains.is(s, "*/")) {
				before = StringBeforeFirst.get(s, "*/");
				s = StringAfterFirst.get(s, "*/");
				list.add(0, "$CBE"+f000(comentariosDeBlocoEnd)+"$" + s);//para ser novamente avaliada
				s = tratarReplaces(before);
				comentariosDeBlocoEnd.add(s);
				return true;
			}
			list2.add("$CBM"+f000(comentariosDeBlocoMeio)+"$");
			s = tratarReplaces(s);
			comentariosDeBlocoMeio.add(s);
		}

	}
	private boolean addComentarioDeLinha(String s) {

		if (!StringContains.is(s, "//")) {
			return false;
		}

		String before = StringBeforeFirst.get(s, "//");

		if (before.contains("/*") || !IsPar.is(UString.ocorrencias(before, "\""))) {
			return false;
		}

		s = StringAfterFirst.get(s, "//");

		list2.add(before + "$CDL"+f000(comentariosDeLinha)+"$");
		s = tratarReplaces(s);
		comentariosDeLinha.add(s);

		return true;
	}

	private String tratarReplaces(String s) {
		s = s.replace("$BARRABARRAASPAS$", "\"\\\\\"");
		s = s.replace("$HTTP$", "http://");
		return s.replace("$BARRAASPAS$", "\\\"");
	}

//	list.replaceTexto
//	list.replaceTexto("\"\\\\\"", "$BARRABARRAASPAS$");
//	list.replaceTexto

}
