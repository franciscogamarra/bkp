package gm.utils.string;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

public class StringClipboard {

	public static String get() {

		Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
		Transferable contents = clipboard.getContents(null);

		if ((contents == null) || !contents.isDataFlavorSupported(DataFlavor.stringFlavor)) {
			return null;
		}

		try {
			return (String) contents.getTransferData(DataFlavor.stringFlavor);
		} catch (UnsupportedFlavorException | IOException ex) {
			return null;
		}

	}
	
	public static ListString getList() {
		ListString list = new ListString();
		list.add(get().split("\n"));
		return list;
	}

	public static void set(String s) {
		try {
			StringSelection selection = new StringSelection(s);
			Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
			clipboard.setContents(selection, selection);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}

	public static void main(String[] args) {

	}

}
