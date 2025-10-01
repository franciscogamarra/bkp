package js.support;

import gm.utils.lambda.P0;

public class ThreadTimeout {

	private P0 func;
	private int milisegundos;
	private long start;

	public ThreadTimeout(P0 func, int milisegundos) {
		this.func = func;
		this.milisegundos = milisegundos;
		start = System.currentTimeMillis();
		ThreadsList.add(() -> call());
	}

	private void call() {
		if (System.currentTimeMillis() - start >= milisegundos) {
			func.call();
		} else {
			ThreadsList.add(() -> call(), 1);
		}
	}

}
