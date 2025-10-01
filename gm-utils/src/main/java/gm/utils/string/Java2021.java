package gm.utils.string;

import java.io.File;

import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.files.UFile;
import gm.utils.lambda.Resolve;
import lombok.Getter;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringBeforeLast;
import src.commom.utils.string.StringEmpty;
import src.commom.utils.string.StringRight;
import src.commom.utils.string.StringTrim;

@Getter
public class Java2021 {
	
	private ListString list = new ListString();
	private ListString load;
	private Lst<ListString> comentarios = new Lst<>();
	public Lst<String> strings = new Lst<>();
	private File file;
	private Boolean abstrata;
	private Boolean finala;

	public Java2021(String fileName) {
		this(new File(fileName));
	}
	
	public Java2021(File file) {
		
		this.file = file;
		
		load = new ListString().load(file);
		load.rtrim();
		load.removeDoubleWhites();
		load.removeLastEmptys();
		
		while (!load.isEmpty()) {
			
			String s = load.remove(0);
			
			int identacao = 0;
			
			while (s.startsWith("\t") || s.startsWith(" ")) {
				
				if (s.startsWith(" ")) {
					s = s.substring(1);
				} else {
					s = s.substring(1);
					identacao++;
				}
				
			}
			
			list.getMargem().set(identacao);
			load.getMargem().set(identacao);
			
			if (s.startsWith("//")) {
				fechaComentariosDeLinha(s);
				continue;
			}

			if (s.startsWith("/*")) {
				fechaComentarioDeBloco(s);
				continue;
			}
			
			int aspas = ix(s,"\"");
			int barra = ix(s,"//");
			int bloco = ix(s,"/*");

			if (aspas < barra) {
				
				if (aspas < bloco) {
					//significa a abertura de ums string
					
					String before = StringBeforeFirst.get(s, "\"");
					s = StringAfterFirst.get(s, "\"");
					
					String ss = "";
					
					while (true) {
						
						String x = s.substring(0, 1);
						s = s.substring(1);
						
						if (x.contentEquals("\\")) {
							x += s.substring(0, 1);
							s = s.substring(1);
							ss += x;
							continue;
						}
						
						if (x.contentEquals("\"")) {
							break;
						}
						
						ss += x;
						
					}
					
					strings.add(ss);

					if (s == null) {
						s = "";
					}

					s = before + "#String("+(strings.size()-1)+")" + s;
					
					load.add(0, s);
					
				} else {
					//significa a abertura de um bloco
					fechaComentarioDeBloco(s);
				}
				
			} else if (barra < bloco) {
				//significa um comentario de fim de linha
				String before = StringBeforeFirst.get(s, "//");

				s = StringAfterFirst.get(s, "//");
				comentarios.add(new ListString().addd(s));

				s = "#Comentario("+(comentarios.size()-1)+")";
				
				if (before != null) {
					s = before + s;
				}
				
				list.add(s);
				
			} else if (bloco < barra) {
				//significa um comentario de bloco
				fechaComentarioDeBloco(s);
			} else {
				list.add(s);
			}

		}
		
		for (int i = 0; i < comentarios.size(); i++) {
			String s = "#Comentario("+i+")";
			list.replaceTexto(s, "/*Comentario"+i+"Comentario*/");
		}

		for (int i = 0; i < strings.size(); i++) {
			String s = "#String("+i+")";
			list.replaceTexto(s, "\"String"+i+"String\"");
		}

	}

	private void fechaComentariosDeLinha(String s) {
		
		ListString cs = new ListString();

		s = StringAfterFirst.get(s, "//");
		cs.add(s);
		
		while (!load.isEmpty() && load.get(0).trim().startsWith("//")) {
			s = load.remove(0);
			s = StringAfterFirst.get(s, "//");
			cs.add(s);
		}
		
		comentarios.add(cs);
		
		s = "#Comentario("+(comentarios.size()-1)+")";
		list.add(s);
		
	}

	private void fechaComentarioDeBloco(String s) {

		String before = StringBeforeFirst.get(s, "/*");

		if (before == null) {
			before = "";
		}
		
		s = StringAfterFirst.get(s, "/*");
		
		ListString cs = new ListString();
		
		while (!s.contains("*/")) {
			cs.add(s);
			s = load.remove(0);
		}
		
		String ss = StringBeforeFirst.get(s, "*/");
		cs.add(ss);
		
		comentarios.add(cs);
		
		s = StringAfterFirst.get(s, "*/");

		ss = before + "#Comentario("+(comentarios.size()-1)+")";

		if (StringEmpty.is(s)) {
			list.add(ss);
		} else {
			
			s = ss + s;
			
			if (s.contains("/*")) {
				load.add(0, s);
			} else {
				list.add(s);
			}
		
		}
		
	}
	
	@Override
	public String toString() {
		return list.toString(" ");
	}

	private int ix(String s, String sub) {
		int x = s.indexOf(sub);
		if (x == -1) {
			return 999_999;
		}
		return x;
	}

	public void print() {
		list.print();
	}

	public void save(String file) {
		list.save(file);
	}

	public void trata(ListString lst) {

		for (int i = 0; i < strings.size(); i++) {
			lst.replaceTexto("\"String"+i+"String\"", "\"" + strings.get(i) + "\"");
		}

		for (int i = 0; i < comentarios.size(); i++) {
			
			ListString list = comentarios.get(i);
			
			String tx = "/*Comentario"+i+"Comentario*/";
			
			if (list.isEmpty()) {
				lst.replaceTexto(tx, "");
			} else if (list.size(1)) {
				
				String s = StringTrim.right(list.get(0));
				String ss = s.trim();
				
				String margem = "";
				
				while (!s.contentEquals(ss)) {
					margem += s.substring(0, 1);
					s = s.substring(1);
				}
				
				margem = margem.replace("    ", "\t");
				lst.replaceTexto(tx, margem + "/* " + ss + " */");
				
			} else {
				
				String s = lst.unique(item -> item.contains(tx));
				
				if (s == null) {
					continue;
				}
				
				int index = lst.indexOf(s);

				String margem = "";
				
				{

					String sss = StringTrim.right(list.get(0));
					String ss = sss.trim();
					
					while (!sss.contentEquals(ss)) {
						margem += sss.substring(0, 1);
						sss = sss.substring(1);
					}
					
					margem = margem.replace("    ", "\t");
					
					lst.replaceTexto(tx, margem + "/* " + ss + " */");
					
				}
				
				String before = StringBeforeFirst.get(s, tx);
				s = before + "/* " + list.remove(0);
				
				lst.remove(index);
				lst.add(index, s);
				
				s = "";
				
				while (!list.isEmpty()) {
					s = list.remove(0);
					index++;
					if (list.isEmpty()) {
						s += " */";
					}
					lst.add(index, s);
				}
				
			}
			
		}
		
	}

	public String getDeclaracao() {
		
		try {
			
			ListString lst = list.copy().trimPlus();
			lst.replaceTexto("/t", " ");
			lst.getMargem().set(0);
			
			apagaComentarios(lst);
			apagaStrings(lst);

			lst.trimPlus();
			
			lst.removeIfEquals("+");
			lst.removeIfEquals(")");
			lst.removeIfEquals("(");
			lst.removeIfEquals(")}");
			lst.removeIfEquals("})");
			lst.removeIfEquals("{(");
			
			while (lst.contains(s -> s.startsWith("package "))) {
				lst.remove(0);
			}

			while (lst.contains(s -> s.startsWith("import "))) {
				lst.remove(0);
			}
			
			while (lst.get(0).startsWith("@")) {
				lst.remove(0);
			}
			
			String s = lst.toString(" ");
			s = s.replace("/t", " ");
			s = StringTrim.plus(s);
			s = StringBeforeFirst.get(s, "{");
			

			s = " " + s.trim();
			
			if (s.contains(" public ") || s.contains(" protected ")) {
				s = StringAfterFirst.get(s, " ");
			}
			
			return s.trim();
			
		} catch (Exception e) {
			throw e;
		}
		
	}

	private JavaTipo tipo;
	
	public JavaTipo getTipo() {

		if (tipo == null) {
			
			tipo = Resolve.ft(() -> {

				String s = getDeclaracao();
				
				if (s.startsWith("public ") || s.startsWith("protected ") || s.startsWith("private ")) {
					s = StringAfterFirst.get(s, " ");
				}

				if (s.startsWith("abstract ")) {
					s = StringAfterFirst.get(s, " ");
				}

				if (s.startsWith("final ")) {
					s = StringAfterFirst.get(s, " ");
				}

				if (s.startsWith("class ")) {
					return JavaTipo.CLASS;
				}

				if (s.startsWith("interface ")) {
					return JavaTipo.INTERFACE;
				}

				if (s.startsWith("@interface ")) {
					return JavaTipo.ANNOTATION;
				}

				if (s.startsWith("enum ")) {
					return JavaTipo.ENUM;
				}
				
				throw new NaoImplementadoException(s);

			});
			
		}
		
		return tipo;

	}

	private String getNomeSimplesSuperClass() {

		String s = getDeclaracao();
		
		if (!s.contains(" extends ")) {
			return null;
		}
		
		if (s.contains(" implements ")) {
			
			s = StringBeforeFirst.get(s, " implements ");

			if (!s.contains(" extends ")) {
				return null;
			}

		}
		
		ListString list = ListString.separaPalavras(s);
		list.trimPlus();

		String penultimo = list.get(-1);
		
		if (penultimo.contentEquals("extends")) {
			return list.getLast();
		}
		
		s = list.remove(0);
		
		if (s.contentEquals("public") || s.contentEquals("private") || s.contentEquals("protected")) {
			s = list.remove(0);
		}

		if (s.contentEquals("abstract")) {
			s = list.remove(0);
			abstrata = true;
		} else {
			abstrata = false;
		}

		if (s.contentEquals("final")) {
			s = list.remove(0);
			finala = true;
		} else {
			finala = false;
		}

		if (!s.contentEquals("class") && !s.contentEquals("interface") && !s.contentEquals("@interface") && !s.contentEquals("@enum")) {
			SystemPrint.ln(file);
			throw new NaoImplementadoException(s);
		}
		s = list.remove(0);
		
		String nome = StringBeforeLast.get(file.getName(), ".");
		
		if (!s.contentEquals(nome)) {
			throw new NaoImplementadoException(s);
		}
		s = list.remove(0);
		
		if (s.contentEquals("<")) {
			
			int opens = 1;
			
			while (opens > 0) {
				s = list.remove(0);
				if (s.contentEquals("<")) {
					opens++;
				} else if (s.contentEquals(">")) {
					opens--;
				}
			}
			
		}
		
		if (list.isEmpty()) {
			return null;
		}

		s = list.remove(0);

		if (s.contentEquals("extends")) {
			return list.remove(0);
		}
		return null;

	}
	
	public String getSuperClass() {
		
		try {
			getNomeSimplesSuperClass();
		} catch (Exception e) {
			getNomeSimplesSuperClass();
		}

		String nomeSimples = getNomeSimplesSuperClass();
		
		if (nomeSimples == null) {
			return null;
		}
		
		ListString loads = list.copy().trimPlus();
		
		String s = loads.filter(i -> i.startsWith("import ")).unique(i -> i.endsWith("." + nomeSimples + ";"));
		
		if (s != null) {
			s = StringAfterFirst.get(s, " ");
			return StringRight.ignore1(s);
		}
		String dir = StringBeforeLast.get(file.toString(), "/");
		
		if (UFile.getFiles(dir).anyMatch(i -> i.getName().contentEquals(nomeSimples + ".java"))) {
			
			s = loads.unique(i -> i.startsWith("package "));
			s = StringAfterFirst.get(s, " ");
			s = StringRight.ignore1(s);
			return s + "." + nomeSimples;
			
		}
		s = "java.lang." + nomeSimples;
		
		if (UClass.getClass(s) == null) {
			SystemPrint.ln(file);
			throw new NaoImplementadoException(nomeSimples);
		}
		return s;
		
	}

	public boolean isClass() {
		return getTipo() == JavaTipo.CLASS;
	}

	public boolean isEnum() {
		return getTipo() == JavaTipo.ENUM;
	}

	public boolean isInterface() {
		return getTipo() == JavaTipo.INTERFACE;
	}

	public boolean isAnnotation() {
		return getTipo() == JavaTipo.ANNOTATION;
	}
	
	public boolean isAbstract() {
		
		if (abstrata == null) {

			String s = getDeclaracao();

			ListString list = ListString.separaPalavras(s);
			list.trimPlus();

			s = list.remove(0);
			
			if (s.contentEquals("public") || s.contentEquals("private") || s.contentEquals("protected")) {
				s = list.remove(0);
			}

			abstrata = s.contentEquals("abstract");
		
		}
		
		return abstrata;
		
	}

	public boolean todoComentado() {
		
		if ((list.size() == 1) || (list.size() == 2 && list.get(1).contains("/*Comentario0Comentario*/"))) {
			return true;
		}

		return false;
	}

	private void apagaComentarios(ListString lst) {
		for (int i = 0; i < comentarios.size(); i++) {
			String tx = "/*Comentario"+i+"Comentario*/";
			lst.replaceTexto(tx, " ");
		}
	}
	
	private void apagaStrings(ListString lst) {
		for (int i = 0; i < strings.size(); i++) {
			String tx = "\"String"+i+"String\"";
			lst.replaceTexto(tx, " ");
		}
	}
	
	public void apagaComentarios() {
		apagaComentarios(list);
	}
	
	public void apagaStrings() {
		apagaStrings(list);
	}

	public ListString getImports() {
		ListString lst = list.copy();
		apagaComentarios(lst);
		apagaStrings(lst);
		lst.trimPlus();
		return lst.filter(i -> i.startsWith("import ")).mapString(i -> StringRight.ignore1(StringAfterFirst.get(i, " ")));
	}
	
	public ListString getImportsJoinImplicitos() {
		return getImports().union(getImportsImplicitos());
	}
	
	public ListString getImportsImplicitos() {
		ListString list = getImportsImplicitosFiles().toListString();
		list.eachRemoveBefore("/main/java/", true);
		list.eachRemoveAfter(".", true);
		list.replaceTexto("/", ".");
		return list;
	}
	
	public Lst<File> getImportsImplicitosFiles() {
	
		Lst<File> javas = UFile.getJavas(file.getParent());
		javas.remove(file);
		
		ListString lst = ListString.separaPalavras(list.copy().trimPlus().toString(" "));
		
		return javas.filter(i -> lst.contains(StringBeforeLast.get(i.getName(), ".") ));
		
	}
	
	public static void main(String[] args) {
		Java2021 o = new Java2021("/opt/desen/gm/cs2019/gm-utils/src/main/java/gm/utils/string/Java2021.java");
		o.getImportsImplicitosFiles().print();
	}

}
