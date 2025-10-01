package gm.utils.outros;

import gm.utils.comum.Lst;
import gm.utils.lambda.P0;

public class ThreadList {

	private Lst<Thread> list = new Lst<>();

	public void exec(P0 func) {
		list.add(UThread.exec(func));
	}

	public void esperar() {
		while (!list.isEmpty()) {
			list.removeIf(o -> !o.isAlive());
		}
	}

}
