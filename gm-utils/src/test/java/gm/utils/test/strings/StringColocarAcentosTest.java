package gm.utils.test.strings;

import org.junit.Test;

import junit.framework.Assert;
import src.commom.utils.string.StringColocarAcentos;
import src.commom.utils.string.StringCompare;

public class StringColocarAcentosTest {

	@Test
	public void exec() {
		main(null);
	}

	public static void main(String[] args) {
		test("solicitacao", "solicitação");
		test("orgao", "órgão");
		test("usuario", "usuário");
		test("", "");
	}

	private static void test(String s, String esperado) {
		String x = StringColocarAcentos.exec(s);
		Assert.assertTrue(StringCompare.eq(x, esperado));
	}

}
