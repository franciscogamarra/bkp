package gm.languages.palavras;

import java.util.function.Predicate;

public abstract class Is0 extends Palavra {

	public Is0() {
		super("");
	}
	
	public abstract boolean exec(Palavra o);
	
	public static Predicate<Palavra> func;
	
}
