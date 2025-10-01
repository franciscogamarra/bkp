package gm.utils.outros;

import gm.utils.comum.SystemPrint;
import gm.utils.string.StringClipboard;
import src.commom.utils.string.StringToConstantName;

public class ToSqlConstant {

	public static void main(String[] args) {
		String s = StringClipboard.get();
		s = "private static final String SQL_" + StringToConstantName.exec(s);
		SystemPrint.ln(s);
		StringClipboard.set(s);
	}

}