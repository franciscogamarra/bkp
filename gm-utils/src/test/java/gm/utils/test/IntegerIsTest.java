package gm.utils.test;

import org.junit.Test;

import src.commom.utils.integer.IntegerIs;

public class IntegerIsTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		afalse(" ");
		atrue("1");
	}

	private static void afalse(Object s) {
		if (IntegerIs.is(s)) {
			throw new RuntimeException();
		}
	}

	private static void atrue(Object s) {
		if (!IntegerIs.is(s)) {
			throw new RuntimeException();
		}
	}

}
