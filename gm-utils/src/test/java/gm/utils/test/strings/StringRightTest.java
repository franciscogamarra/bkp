package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringRight;

public class StringRightTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("abcdef", 1, "abcde");
		test("abcdef", 2, "abcd");
		test("abcdef", 3, "abc");
		test("abcdef", 4, "ab");
		test("abcdef", 5, "a");
		test("abcdef", 6, "");
		test("abcdef", 7, "");
		test("abcdef", 8, "");
		test(null, 8, "");
	}

	private static void test(String s, int length, String esperado) {
		String x = StringRight.ignore(s, length);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
