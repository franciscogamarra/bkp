package js.support;

import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.lambda.P0;

public class ThreadsList {

	private static Lst<ThreadsListItem> list = new Lst<>();

	public static void add(P0 func) {
		list.add(newO(func));
	}

	private static ThreadsListItem newO(P0 func) {
		ThreadsListItem o = new ThreadsListItem();
		o.func = func;
		o.origem = new RuntimeException();
		return o;
	}

	public static void add(P0 func, int index) {

		ThreadsListItem o = newO(func);

		if (list.size() >= index) {
			list.add(index, o);
		} else {
			list.add(o);
		}

	}
	public static void run() {

		while (!list.isEmpty()) {

			ThreadsListItem o = list.remove(0);

			try {
				o.func.call();
			} catch (Exception e) {

				SystemPrint.err("========================================================");
				SystemPrint.err("======= Ocorreu um erro dentro de um threadList ========");
				SystemPrint.err("========================================================");
				o.origem.printStackTrace();
				SystemPrint.err("========================================================");

				throw e;
			}

		}

	}
}
