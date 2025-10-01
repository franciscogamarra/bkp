package gm.languages.palavras;

import java.util.function.Consumer;
import java.util.function.Predicate;

import gm.languages.java.expressoes.AnnotationJava;
import gm.languages.java.expressoes.AnnotationsJava;
import gm.languages.java.expressoes.DeclaracaoDeMetodo;
import gm.languages.java.expressoes.DeclaracaoDeVariavel;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.jpa.dbs.CooperDBs;
import gm.utils.lambda.F1;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRepete;
import src.commom.utils.string.StringTrim;

public class Palavras extends Palavra {

	public static final String SAIDA_PADRAO = "/opt/desen/gm/cs2019/gm-utils/src/main/java/gm/utils/jpa/nativeQuery/validator/files/xx.sql";
	
	@Setter
	private String caminhoSaveAnalise = "/opt/desen/gm/cs2019/gm-utils/src/main/java/gm/utils/jpa/nativeQuery/validator/files/x.sql";
	
	@Getter
	private final Lst<Palavra> list = new Lst<>();
	
	public F1<String, Palavra> funcBuildPalavra;
	
	public Comentario comentarioInicial;

	public Palavras(String... itens) {
		super("");
		list.ignoreRepeateds();
		for (String s : itens) {
			add(s);
		}
	}

	/* add */

	public Palavras add(Palavra o) {
		if (contains(o)) {
			throw new RuntimeException();
		}
		list.add(o);
		o.removido = false;
		return this;
	}

	public Palavras add(String s) {
		return add(PalavraBuild.buildPalavra(this, s));
	}

	public Palavras add(Class<? extends Palavra> classe) {
		return add(UClass.newInstance(classe));
	}

	/* contains */

	public boolean contains(Palavra o) {
		return list.contains(o);
	}

	public boolean contains(String s) {
		return contains(PalavraBuild.buildPalavra(this, s));
	}

	public boolean contains(Class<? extends Palavra> classe) {
		return list.anyMatch(o -> o.is(classe));
	}

	/* replace */

	public Palavras replace(Palavras de, Class<? extends Palavra> para) {
		list.replace(de.list, achados -> new Palavras().add(para).list);
		return this;
	}

	public Palavras replacef(Lst<F1<Palavra, Boolean>> des, F1<Lst<Palavra>, Lst<Palavra>> func) {
		list.replacef(des, func);
		return this;
	}
	
	public Palavras replace(Palavras de, Palavra para) {
		list.replace(de.list, para);
		return this;
	}

	public Palavras replace(String de, Palavra para) {
		return replace(PalavraBuild.buildPalavra(this, de), para);
	}
	
	public Palavras replace(Palavra de, Palavra para) {
		list.replace(de, para);
		para.absorverIdentacao(de);
		return this;
	}

	public Palavras replace(Palavra de, Class<? extends Palavra> para) {
		return replace(de, UClass.newInstance(para));
	}

	public Palavras replace(String de, Class<? extends Palavra> para) {
		return replace(PalavraBuild.buildPalavra(this, de), para);
	}

	@SuppressWarnings("unchecked")
	public Replacer replace(Class<?>... itens) {
		Replacer o = new Replacer(this);
		for (Class<?> classe : itens) {
			o.add((Class<? extends Palavra>) classe);
		}
		return o;
	}

	@Override
	public String toString() {
		return list.joinString("\n");
	}

	public Palavra get(int i) {
		return list.get(i);
	}

	public <TT extends Palavra> Lst<TT> filter(Class<TT> classe) {
		return filter(i -> i.is(classe));
	}
	
	@SuppressWarnings("unchecked")
	public <TT extends Palavra> Lst<TT> filter(Predicate<Palavra> predicate) {
		return getList().filter(predicate).map(i -> (TT) i);
	}

	public <TT extends Palavra> TT getFirst(Class<TT> classe) {
		return filter(classe).getFirst();
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Palavra> T after(Palavra o) {
		return (T) getList().after(o);
	}
	
	@SuppressWarnings("unchecked")
	public <T extends Palavra> T before(Palavra o) {
		return (T) getList().before(o);
	}
	
	public boolean beforeIs(Palavra o, Class<?>... classes) {
		o = before(o);
		return o != null && o.is(classes);
	}

	public String toString(String separator) {
		return list.joinString(separator);
	}
	
	public String toStringGetS(String separator) {
		return list.toString(i -> i.getS(), separator);
	}

	@SuppressWarnings("unchecked")
	public <T extends Palavra> T removeAfter(Palavra o) {
		return (T) getList().removeAfter(o);
	}

	@SuppressWarnings("unchecked")
	public <T extends Palavra> T removeBefore(Palavra o) {
		return (T) getList().removeBefore(o);
	}
	
	public void removeObrig(Palavra o) {
		if (!contains(o)) {
			throw new RuntimeException("o nao esta na lista: " + o);
		}
		remove(o);
	}
	
	public void remove(Palavra o) {
		getList().remove(o);
		o.removido = true;
	}

	public Lst<Palavra> remove(Class<?> classe) {
		Lst<Palavra> itens = list.filter(o -> o.is(classe));
		list.remove(itens);
		return itens;
	}

	@SuppressWarnings("unchecked")
	public <T extends Palavra> T removeLast() {
		return (T) getList().removeLast();
	}

	public int indexOf(Palavra o) {
		return getList().indexOf(o);
	}
	
	public int indexOf(Predicate<Palavra> func) {
		return getList().indexOf(func);
	}
	
	public Palavra getFirst(Predicate<Palavra> func) {
		return getList().getFirst(func);
	}

	private final ListString saves = new ListString();

	public void saveAnalise() {
		if (Palavra.emAnalise) {
			saves.clear();
			saves.add(list);
			saves.save(caminhoSaveAnalise );
			saves.add();
			saves.add("=============================================================");
			saves.add();
		}
	}

	@Override
	public void print() {
		list.print();
	}

	private static void add(Lst<Palavra> itens, Palavras as) {
		
		for (Palavra o : as.list) {
			
			if (o.getComentarios().isNotEmpty()) {
				o.getComentarios().each(i -> {
					if (i.hasQuebra()) {
						i.setTabs(o.getTabs()).setEspacos(o.getEspacos());
					}
					itens.add(i);
				});
			}
			
			
			itens.add(o);
			
			if (o.getItens() != null) {
				add(itens, o.getItens());
			}
			
		}
		
	}

	public void save() {
		save(SAIDA_PADRAO);
	}
	
	public void save(String fileName) {
		getListString().save(fileName);
	}
	
	public void exec() {
		CooperDBs.homol().exec(getListString().toString("\n"));
	}
	
	public ListString getListString() {

		Palavra.emAnalise = false;
		
		Lst<Palavra> itens = new Lst<>();
		
		add(itens, this);
		
		ListString lst = new ListString();
		
		if (comentarioInicial != null) {
			lst.add(comentarioInicial.getS());
		}
		
		String linha = "";
		
		while (itens.isNotEmpty()) {
			
			Palavra o = itens.remove(0);
					
			if (o.getQuebras() > 0) {
				lst.add(linha);
				linha = "";
				for (int i = 1; i < o.getQuebras(); i++) {
					lst.add();
				}
			}
			
			String identacao = StringRepete.exec("\t", o.getTabs()) + StringRepete.exec(" ", o.getEspacos());
			
			AnnotationsJava annotations = getAnnotations(o);
			
			if (annotations != null) {
				for (AnnotationJava a : annotations.getLst()) {
					lst.add(identacao + a);
				}
			}
			
			linha += identacao + o;
			
			if (o.getComplemento() != null) {
				linha += o.getComplemento();
			}
			
		}
		
		lst.add(linha);
		
		lst.removeFisrtEmptys();
		lst.removeLastEmptys();
		lst.removeEmptysDenessessariosDeBodys();
		lst.removeDoubleWhites();
		lst.replaceEach(s -> {
			if (!StringEmpty.is(s)) {
				s = StringTrim.right(s);
			}
			return s;
		});
		
		return lst;
		
	}
	
	private AnnotationsJava getAnnotations(Palavra o) {
		
		if (o instanceof DeclaracaoDeVariavel) {
			DeclaracaoDeVariavel d = (DeclaracaoDeVariavel) o;
			return d.getAnnotations();
		}
		
		if (o instanceof DeclaracaoDeMetodo) {
			DeclaracaoDeMetodo d = (DeclaracaoDeMetodo) o;
			return d.getAnnotations();
		}
		
		return null;
		
	}

	public Palavras each(Consumer<Palavra> action) {
		list.each(action);
		return this;
	}

	public <T extends Palavra> T addBefore(Palavra o, T a) {
		list.addBefore(o, a);
		return a;
	}
	
	public <T extends Palavra> T addAfter(Palavra o, T a) {
		list.addAfter(o, a);
		return a;
	}

	public Lst<Palavra> entre(Palavra a, Palavra b) {
		return list.entre(a, b);
	}
	
	public boolean isEmpty() {
		return getList().isEmpty();
	}

	public Palavra getLast() {
		return getList().getLast();
	}
	
}