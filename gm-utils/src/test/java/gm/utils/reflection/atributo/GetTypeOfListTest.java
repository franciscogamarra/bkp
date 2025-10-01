package gm.utils.reflection.atributo;

import java.util.List;

import org.junit.Test;

import gm.utils.comum.UAssert;
import gm.utils.reflection.Atributo;
import gm.utils.reflection.AtributosBuild;

public class GetTypeOfListTest {

	List<Integer> list;

	@Test
	public void exec() {
		Atributo a = AtributosBuild.get(GetTypeOfListTest.class).get(0);
		UAssert.eq(a.getTypeOfList(), Integer.class, "");
	}

	public static void main(String[] args) {
		new GetTypeOfListTest().exec();
	}

}
