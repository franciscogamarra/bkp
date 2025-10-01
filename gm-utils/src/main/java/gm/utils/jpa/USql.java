package gm.utils.jpa;

import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringTrim;

public class USql {

	public static String format(String s) {
		s = StringTrim.plus(s);
		if (StringEmpty.is(s)) {
			return null;
		}
		s = s.replace("ubdate", "update");
		s = s.replace("{;}", ";");
		s = s.replace(";", "\n;\n");
		s = s.replace(" from", "\nfrom");
		s = s.replace(" set ", "\nset ");
		s = s.replace(" where ", "\nwhere ");
		s = s.replace(" update ", "\nupdate ");
		s = s.replace(" having ", "\nhaving ");
		s = s.replace(" inner ", "\ninner ");
		s = s.replace(" left ", "\nleft ");
		s = s.replace(" and ", "\nand ");
		s = s.replace(" select ", "\nselect ");
		s = s.replace("*/", "*/\n");
		return s.replace("\n\n", "\n");
	}

}
