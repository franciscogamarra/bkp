package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.sql.palavras.By;
import gm.languages.sql.palavras.Order;

public class OrderBy extends Palavra {
	public OrderBy(Order order, By by) {
		super(order.getS() + " " + by.getS());
	}
}
