package gm.utils.test.promise;

import org.junit.Test;

import js.Error;
import js.promise.Promise;
import js.support.ThreadsList;
import js.support.console;

public class PromiseTest {

	@Test
	public void exec() {
		main(null);
	}

	public static Promise<String> soAceitaPares(int value) {

		return new Promise<String>((resolve, reject) -> {
			if (value % 2 == 0) {
				resolve.call("ok");
			} else {
				reject.call(new Error("Você passou um número ímpar!"));
			}
		});

	}

	public static void main(String[] args) {

		Promise<String> m = soAceitaPares(1);

		m
		.then(s -> {
			console.log(s);
			return s.length();
		})
		.then(i -> {
			console.log(i);
			return "sucesso";
		})
		.then(s -> {
			console.log(s);
			return null;
		});

		m.catch_(e -> {
			e.printStackTrace();
		})
		.then(e -> {

			console.log("Ocorreu um erro e continuou");

			return 1;
		})

		;

		ThreadsList.run();

	}

}
