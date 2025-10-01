package js.html;

import gm.utils.lambda.P1;
import src.commom.utils.array.Itens;

public class NodeList {

	public Itens<Element> itens = new Itens<>();

	public void forEach(P1<Element> action) {
		itens.forEach(action);
	}

	public int length() {
		return itens.size();
	}

}
