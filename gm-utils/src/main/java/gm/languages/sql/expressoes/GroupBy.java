package gm.languages.sql.expressoes;

import gm.languages.palavras.Palavra;
import gm.languages.sql.palavras.By;
import gm.languages.sql.palavras.Group;

public class GroupBy extends Palavra {
	public GroupBy(Group group, By by) {
		super(group.getS() + " " + by.getS());
	}
}
