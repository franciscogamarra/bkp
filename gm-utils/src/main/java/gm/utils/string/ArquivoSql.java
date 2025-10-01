package gm.utils.string;

import gm.utils.comum.Lst;
import lombok.Getter;
import src.commom.utils.string.StringAfterFirst;
import src.commom.utils.string.StringBeforeFirst;
import src.commom.utils.string.StringEmpty;

@Getter
public class ArquivoSql {
	
	public static void main(String[] args) {
		ArquivoSql a = new ArquivoSql("/opt/desen/gm/cs2019/cooper/procedure/files/sch_emprestimo.stp_emp_get_limite_tela_inicial.sql");
		a.print();
//		a.comentarios.print();
	}

	private ListString list = new ListString();
	private ListString load;
	private Lst<ListString> comentarios = new Lst<>();
	private Lst<String> strings = new Lst<>();

	public ArquivoSql(String fileName) {
		
		load = new ListString().load(fileName);
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
			
			if (s.startsWith("--")) {
				fechaComentariosDeLinha(s);
				continue;
			}

			if (s.startsWith("/*")) {
				fechaComentarioDeBloco(s);
				continue;
			}
			
			int aspas = ix(s,"'");
			int traco = ix(s,"--");
			int bloco = ix(s,"/*");

			if (aspas < traco) {
				
				if (aspas < bloco) {
					//significa a abertura de ums string
					
					String before = StringBeforeFirst.get(s, "'");
					s = StringAfterFirst.get(s, "'");
					
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
						
						if (x.contentEquals("'")) {
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
				
			} else if (traco < bloco) {
				//significa um comentario de fim de linha
				String before = StringBeforeFirst.get(s, "--");

				s = StringAfterFirst.get(s, "--");
				comentarios.add(new ListString().addd(s));

				s = "#Comentario("+(comentarios.size()-1)+")";
				
				if (before != null) {
					s = before + s;
				}
				
				list.add(s);
				
			} else if (bloco < traco) {
				//significa um comentario de bloco
				fechaComentarioDeBloco(s);
			} else {
				list.add(s);
			}

		}
		
		for (int i = 0; i < comentarios.size(); i++) {
			String s = "#Comentario("+i+")";
			list.replaceTexto(s, "/*"+i+"*/");
		}

		for (int i = 0; i < strings.size(); i++) {
			String s = "#String("+i+")";
			list.replaceTexto(s, "\""+i+"\"");
		}

	}

	private void fechaComentariosDeLinha(String s) {
		
		ListString cs = new ListString();

		s = StringAfterFirst.get(s, "--").trim();
		cs.add(s);
		
		while (!load.isEmpty() && load.get(0).trim().startsWith("--")) {
			s = load.remove(0);
			s = StringAfterFirst.get(s, "--").trim();
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
			lst.replaceTexto("\""+i+"\"", "\"" + strings.get(i) + "\"");
		}

		for (int i = 0; i < comentarios.size(); i++) {
			
			ListString list = comentarios.get(i);
			
			if (list.isEmpty()) {
				lst.replaceTexto("/*"+i+"*/", "");
			} else if (list.size(1)) {
				lst.replaceTexto("/*"+i+"*/", "/*" + list.get(0) + "*/");
			} else {
				
				String texto = "/*"+i+"*/";
				String s = lst.unique(item -> item.contains(texto));
				
				if (s == null) {
					continue;
				}
				
				int index = lst.indexOf(s);
				
				String before = StringBeforeFirst.get(s, texto);
				s = before + "/*" + list.remove(0);
				
				lst.remove(index);
				lst.add(index, s);
				
				s = "";
				
				while (!list.isEmpty()) {
					s = list.remove(0);
					index++;
					if (list.isEmpty()) {
						s += "*/";
					}
					lst.add(index, s);
				}
				
			}
			
		}
		
	}

}
