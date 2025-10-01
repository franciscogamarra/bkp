package gm.utils.outros;

import gm.utils.lambda.P0;

public class UThread {
	public static Thread exec(P0 func) {
		Thread o = new Thread() {
			@Override
			public void run() {
				func.call();
			}
		};
		o.start();
		return o;
	}
}
