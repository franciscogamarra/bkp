package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringIs;

public class StringIsTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		atrue("");
		afalse(null);
		afalse(1);
	}

	private static void atrue(Object o) {
		Assert.assertTrue(StringIs.is(o));
	}

	private static void afalse(Object o) {
		Assert.assertFalse(StringIs.is(o));
	}

}
