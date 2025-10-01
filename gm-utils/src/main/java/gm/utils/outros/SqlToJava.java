package gm.utils.outros;

import gm.utils.string.ListString;
import gm.utils.string.StringClipboard;
import src.commom.utils.string.StringContains;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringTrim;

public class SqlToJava {

	public static void main(String[] args) {
		ListString list = new ListString();
		list.add(StringClipboard.get().split("\n"));
		list.eachRemoveBefore("\"", true);
		list.eachRemoveAfter("\"", true);
		list.trim();
		list = toStringSqlDeclaration(list);
		list.removeWhites();
		list.print();
//		StringClipboard.set(list.toString("\n"));
	}

	public static ListString toStringSqlDeclaration(String s) {
		ListString list = new ListString();
		list.add(s);
		return toStringSqlDeclaration(list);
	}

	public static ListString toStringSqlDeclaration(ListString original) {

		original.addLeft(" ");
		original.addRight(" ");

		ListString list = new ListString();
		list.add("private static final String SQL = \"\"");

		for (String s : original) {

			if (StringEmpty.is(s)) {
				list.add();
				continue;
			}

			s = StringTrim.right(s);
			if (StringContains.is(s, "--")) {
				s = s.replace("--", "/*");
				s += "*/";
			}

			s = s.replace("\t", "  ");

			s = "\t+ \" " + s.trim();
			s += "\"";

			list.add(s);

		}
		list.add(";");
		return list;
	}
}