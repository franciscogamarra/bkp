package gm.utils.test.strings;

import org.junit.Test;

import js.support.console;
import src.commom.utils.string.StringCompare;
import src.commom.utils.string.StringReplace;

public class StringReplaceTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test(" ", " ", "-", "-");
		test("  ", " ", "-", "--");
		test(" x ", " ", "-", "-x-");
		test("gmail.", "@", "", "gmail.");
		test(" 26239 Universidade Federal Do Para ", " Para ", " para ", " 26239 Universidade Federal Do para ");
		test("<IconCheck size={SizeProp.lg}/>","{SizeProp.lg", "{\"lg\"", "<IconCheck size={\"lg\"}/>");
		test("00 francisco de a gamarra", " f", " F", "00 Francisco de a gamarra");
		test("ab", "a", "x", "xb");
		test("UmNome-", "U.N", "UN", "UmNome-");
	}

	private static void test(String s, String a, String b, String esperado) {
		String x = StringReplace.exec(s, a, b);
		if (!StringCompare.eq(x, esperado)) {
			console.log(x);
			console.log(esperado);
			throw new RuntimeException();
		}
	}

}
