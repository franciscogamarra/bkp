package gm.languages.ts.expressoes;

import gm.languages.java.expressoes.AnnotationJava;
import gm.languages.palavras.Palavra;
import gm.languages.ts.javaToTs.JavaToTs;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.utils.comum.Lst;
import gm.utils.javaCreate.JcTipo;
import gm.utils.string.ListString;
import lombok.Getter;
import lombok.Setter;
import src.commom.utils.string.StringRight;

@Getter
public class ImportFrom extends Palavra {
	
	public void add(Class<?> classe) {
		
		From f = classe.getAnnotation(From.class);
		
		if (f != null && !f.value().contentEquals(from)) {
			throw new RuntimeException("O from da classe é diferente do from do import");
		}
		
		boolean staticc = classe.isAnnotationPresent(ImportStatic.class);
		ClassTs tipo = ClassTs.get(null, classe.getSimpleName());
		add(tipo, staticc);
		
	}
	
	public void add(JcTipo tipo, boolean staticc) {
		add(ClassTs.get(tipo), staticc);
	}
	
	public void add(AnnotationJava a, boolean staticc) {
		add(a.getType(), staticc);
	}

	public void add(String nome, boolean staticc) {
		TipoTs tipo = new TipoTs(ClassTs.getGenerics(nome));
		add(tipo, staticc);
	}
	
	private TipoTs tipoDef;
	
	
	@Setter
	private String from;
	
	private Lst<TipoTs> statics = new Lst<>();

	public Lst<TipoTs> getTipos() {
		Lst<TipoTs> lst = statics.copy();
		if (tipoDef != null) {
			lst.add(0, tipoDef);
		}
		return lst;
	}
	
	public void remove(TipoTs tipo) {
		
		if (tipoDef == tipo) {
			tipoDef = null;
		} else {
			statics.remove(tipo);
		}
		
	}
	
	public void remove(String nome) {
		
		if (tipoDef != null && tipoDef.eq(nome)) {
			tipoDef = null;
		}
		
		statics.removeIf(i -> i.eq(nome));
		
	}
	
	public void add(TipoTs tipo, boolean staticc) {
		
		if (tipo.getClasse().getSimpleName().contentEquals("OurTypes")) {
			return;
		}
		
		if (staticc) {
			if (!statics.anyMatch(i -> i.eq(tipo))) {
				statics.add(tipo);
			}
		} else if (tipoDef != tipo) {
			tipoDef = tipo;
		}
		
	}
	
	public void add(ClassTs tipo, boolean staticc) {
		add(new TipoTs(tipo), staticc);
	}

	private ImportFrom() {
		super("");
	}
	
	private static final ListString extensoes = ListString.array("png","svg","jpg","json");
	
	@Override
	public String getS() {
		
		String s = "import ";
		
		if (tipoDef != null) {
			s += tipoDef.getS();
		}
		
		if (statics.isNotEmpty()) {
			
			if (tipoDef != null) {
				s += ", ";
			}
			
			s += "{ "+statics.sortToString().toString(i -> i.getS(), ", ")+" }";
			
		}
		
		String f = from;
		
		for (String e : extensoes) {
			if (f.endsWith("_" + e)) {
				f = StringRight.ignore(f, e.length()+1) + "." + e;
			}
		}
		
		String aspas = JavaToTs.config.stringAspasSimples ? "'" : "\"";
		
		
		s += " from " + aspas + f + aspas + ";";
		
		return s;
		
	}
	
	public static ImportFrom build() {
		return new ImportFrom();
	}

	public boolean isEmpty() {
		return tipoDef == null && statics.isEmpty();
	}
	
}
