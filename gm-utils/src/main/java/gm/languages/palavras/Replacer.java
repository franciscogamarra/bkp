package gm.languages.palavras;

import gm.utils.comum.Lst;
import gm.utils.lambda.F0;
import gm.utils.lambda.F1;

public class Replacer {
	
	private Palavras palavras;
	private final Lst<F1<Palavra, Boolean>> des = new Lst<>();
	private final Lst<Class<? extends Palavra>> por = new Lst<>();
	
	private boolean inPor;

	public Replacer(Palavras palavras) {
		this.palavras = palavras;
	}
	
	public Replacer add(Class<? extends Palavra> classe) {
		
		if (inPor) {
			por.add(classe);
		} else {
			des.add(i -> i.is(classe));
		}
		
		return this;
	}
	
	public Replacer por() {
		inPor = true;
		return this;
	}
	
	@SuppressWarnings("unchecked")
	public Palavras por(Class<?>... classes) {
		Replacer o = por();
		for (Class<?> classe : classes) {
			o.add((Class<? extends Palavra>) classe);
		}
		return o.exec();
	}
	
	public Palavras porNew(F0<Palavra> func) {
		return porUm(lst -> func.call());
	}
	
	public Palavras porUm(F1<ReplacerLst, Palavra> func) {
		return por(lst -> {
			Palavra o = func.call(lst);
			lst.clear();
			if (o != null) {
				lst.add(o);
			}
			return lst;
		});
	}
	
	public Palavras por(F1<ReplacerLst, ReplacerLst> func) {
		
		palavras.replacef(des, lst -> {
			Palavra o = lst.get(0);
			ReplacerLst rl = new ReplacerLst(palavras, lst);
			ReplacerLst res = func.call(rl);
			
			if (!res.isEmpty()) {
				
				Palavra n = res.get(0);
				
				if (n != o) {
					if (o.hasIdentacao() && !n.hasIdentacao()) {
						n.absorverIdentacao(o);
					}
					n.getComentarios().addAll(o.getComentarios());
				}

			}
			
			return res.getLst();
			
		});
		
		palavras.each(i -> {
			if (i.getItens() != null) {
				Replacer replacer = new Replacer(i.getItens());
				replacer.des.addAll(des);
				replacer.por(func);
			}
		});
		
		return palavras;
	}
	
	public Palavras exec() {

		F1<ReplacerLst, ReplacerLst> func = lista -> {
			ReplacerLst para = new ReplacerLst(palavras);
			for (Class<? extends Palavra> classe : por) {
				para.add(PalavraBuild.build(classe));
			}
			return para;
		};
		
		return por(func);
		
	}

	public Palavras remove() {
		return porNew(() -> null);
	}

}
