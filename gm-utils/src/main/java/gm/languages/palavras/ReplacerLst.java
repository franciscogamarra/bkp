package gm.languages.palavras;

import java.util.function.Consumer;

import gm.utils.comum.Lst;
import lombok.Getter;

@Getter
public class ReplacerLst {
	
	private final Palavras palavras;
	private final Lst<Palavra> lst;

	public ReplacerLst(Palavras palavras, Lst<Palavra> lst) {
		this.palavras = palavras;
		this.lst = lst;
	}
	
	public ReplacerLst(Palavras palavras) {
		this(palavras, new Lst<>());
	}

	public ReplacerLst add(Palavra... os) {
		for (Palavra palavra : os) {
			lst.add(palavra);
		}
		return this;
	}

	public void add(int index, Palavra o) {
		lst.add(index, o);
	}
	
	public ReplacerLst clear() {
		lst.clear();
		return this;
	}

	@SuppressWarnings("unchecked")
	private static <T extends Palavra> T cast(Palavra o) {
		return (T) o;
	}

	public <T extends Palavra> T get(int i) {
		return cast(lst.get(i));
	}

	public <T extends Palavra> T rm() {
		return rm(0);
	}
	
	public <T extends Palavra> T rm(int i) {
		return remove(i);
	}
	
	public <T extends Palavra> T remove(int i) {
		return cast(lst.remove(i));
	}
	
	public <T extends Palavra> T removeLast() {
		return cast(lst.removeLast());
	}
	
	public <T extends Palavra> T getLast() {
		return cast(lst.getLast());
	}

	public void addBefore(Palavra o, Palavra a) {
		lst.addBefore(o, a);
	}

	public void addAfter(Palavra o, Palavra a) {
		lst.addAfter(o, a);
	}
	
	public boolean isEmpty() {
		return lst.isEmpty();
	}

	public ReplacerLst each(Consumer<Palavra> action) {
		lst.each(action);
		return this;
	}
	
}
