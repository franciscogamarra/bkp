package gm.languages;

import java.util.function.Consumer;
import java.util.function.Predicate;

import gm.languages.java.expressoes.DeclaracaoDeVariavel;
import gm.languages.palavras.DefaultReplaces;
import gm.languages.palavras.Is0;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.languages.palavras.comuns.conjuntos.bloco.FechaBloco;
import gm.languages.palavras.comuns.literal.StringLiteral;
import gm.languages.palavras.comuns.simples.AbreChaves;
import gm.languages.palavras.comuns.simples.PontoEVirgula;
import gm.utils.comum.Lst;
import gm.utils.string.ListString;
import src.commom.utils.string.StringTrim;

public abstract class PalavrasLoad {

	protected final Palavras palavras = new Palavras();
	
	protected final void exec() {
		
		ListString list = loadListString().rtrim();
		preTratarListString(list);
		String s = list.replaceTexto(StringLiteral.OCORRENCIA_IMPROVAVEL_2, StringLiteral.OCORRENCIA_IMPROVAVEL).replaceTexto(" ", " _espaco_ ").replaceTexto("\t", " _tab_ ").toString(" _quebra_ ");
		s = StringTrim.plus(s);
		ListString itens = ListString.separaPalavras(s).trimPlus();
		
		if (itens.contains("@IgnorarDaquiPraBaixo")) {
			
			while (itens.contains("@IgnorarDaquiPraBaixo")) {
				itens.removeLast();
			}

			while (itens.endsWith("_quebra_") || itens.endsWith("_tab_") || itens.endsWith("_espaco_")) {
				itens.removeLast();
			}
			
			itens.add("_quebra_");
			itens.add("_quebra_");
			itens.add("}");
			
		}
		
		itens.each(i -> palavras.add(i));
		
		DefaultReplaces.exec(palavras);
		
		exec1();
		
	}

	protected abstract void exec1();

	protected void preTratarListString(ListString list) {}
	protected abstract ListString loadListString();
	
	protected final <T extends Palavra> T after(Palavra o) {
		return palavras.after(o);
	}
	
	protected final <T extends Palavra> T before(Palavra o) {
		return palavras.before(o);
	}

	public final <TT extends Palavra> Lst<TT> filter(Class<TT> classe) {
		
		if (Is0.class == classe) {
			throw new RuntimeException("nao da certo. use o filter abaixo com predicate");
		}
		
		return palavras.filter(classe);
	}
	
	protected final boolean beforeIs(Palavra o, Class<?>... classes) {
		Palavra i = before(o);
		return i != null && i.is(classes);
	}

	protected final boolean afterIs(Palavra o, Class<?>... classes) {
		Palavra i = after(o);
		return i != null && i.is(classes);
	}
	
	protected final <T extends Palavra> T removeBefore(Palavra o) {
		return palavras.removeBefore(o);
	}

	protected final <T extends Palavra> T removeAfter(Palavra o) {
		return palavras.removeAfter(o);
	}
	
	protected final void remove(Palavra o) {
		palavras.remove(o);
	}

	protected final void removeObrig(Palavra o) {
		palavras.removeObrig(o);
	}

	protected final <TT extends Palavra> Lst<TT> filter(Predicate<Palavra> predicate) {
		return palavras.filter(predicate);
	}
	
	public final <T extends Palavra> T addAfter(Palavra o, T a) {
		return palavras.addAfter(o, a);
	}

	public final <T extends Palavra> T addBefore(Palavra o, T a) {
		return palavras.addBefore(o, a);
	}
	
	protected final void each(Consumer<Palavra> action) {
		palavras.each(action);
	}
	
	protected final <T extends Palavra> T replace(Palavra de, T para) {
		
		if (de == para) {
			throw new RuntimeException("de == para");
		}
		
		palavras.replace(de, para);
		return para;
	}

	protected void blocos() {

		palavras.replace(AbreChaves.class).porUm(lst -> {
			
			AbreChaves abreChaves = lst.rm();
			
			AbreBloco abreBloco = new AbreBloco();
			FechaBloco fechaBloco = new FechaBloco();
			
			abreBloco.setFechamento(fechaBloco);
			fechaBloco.setAbertura(abreBloco);
			
			palavras.replace(abreChaves.getFechamento(), fechaBloco);
			
			return abreBloco;
			
		});
		
	}
	
	protected final AbreBloco getAbreBloco(Palavra o) {
		
		if (o.is(DeclaracaoDeVariavel.class)) {
			
			DeclaracaoDeVariavel dec = (DeclaracaoDeVariavel) o;
			
			if (dec.isStatic()) {
				return filter(AbreBloco.class).get(0);
			}

			if (dec.isParametro() || dec.isParametroCatch()) {

				Palavra i = after(o);
				
				while (!i.is(AbreBloco.class) ) {
					
					i = after(i);
					
					if (i.is(PontoEVirgula.class)) {
						//significa que eh um metodo abstrato
						return null;
					}
					
				}
				
				return (AbreBloco) i;
				
			}
			
		}
		
		Palavra i = before(o);
		
		while (!i.is(AbreBloco.class) ) {
			
			if (i.is(FechaBloco.class)) {
				FechaBloco fecha = (FechaBloco) i;
				i = fecha.getAbertura();
			}
			
			i = before(i);
			
		}
		
		return (AbreBloco) i;
		
	}

	public ListString getResult() {
		return palavras.getListString();
	}

	public void print() {
		getResult().print();
	}
	
}
