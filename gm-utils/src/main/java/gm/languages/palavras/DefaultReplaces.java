package gm.languages.palavras;

import gm.languages.java.expressoes.MenosMenos;
import gm.languages.palavras.comuns.Espaco;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.Quebra;
import gm.languages.palavras.comuns.Tab;
import gm.languages.palavras.comuns.literal.DoubleLiteral;
import gm.languages.palavras.comuns.literal.InteiroLiteral;
import gm.languages.palavras.comuns.literal.LongLiteral;
import gm.languages.palavras.comuns.literal.StringLiteral;
import gm.languages.palavras.comuns.simples.AbreChaves;
import gm.languages.palavras.comuns.simples.AbreColchetes;
import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.AspasDuplas;
import gm.languages.palavras.comuns.simples.AspasSimples;
import gm.languages.palavras.comuns.simples.Asterisco;
import gm.languages.palavras.comuns.simples.Barra;
import gm.languages.palavras.comuns.simples.BarraInvertida;
import gm.languages.palavras.comuns.simples.Crase;
import gm.languages.palavras.comuns.simples.FechaChaves;
import gm.languages.palavras.comuns.simples.FechaColchetes;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.languages.palavras.comuns.simples.Igual;
import gm.languages.palavras.comuns.simples.Maior;
import gm.languages.palavras.comuns.simples.MaiorOuIgual;
import gm.languages.palavras.comuns.simples.Menor;
import gm.languages.palavras.comuns.simples.MenorOuIgual;
import gm.languages.palavras.comuns.simples.Menos;
import gm.languages.palavras.comuns.simples.Ponto;
import gm.languages.palavras.comuns.simples.Virgula;
import gm.languages.palavras.java.BarraBarra;
import gm.languages.sql.palavras.Select;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.lambda.F1;
import gm.utils.number.ListInteger;

public class DefaultReplaces {

	public static void exec(Palavras palavras) {
		new DefaultReplaces(palavras);
	}
	
	private Palavras palavras;

	private DefaultReplaces(Palavras palavras) {
		this.palavras = palavras;
		basicos();
		stringsEComentarios();
		removeEspacosETabs();
		basicos();
		valores();
		comentarios();
		aberturasEFechamentos();
	}
	
	private void comentarios() {

		Lst<Comentario> comentarios = palavras.filter(Comentario.class);
		
		while (comentarios.isNotEmpty()) {
			Comentario c = comentarios.removeLast();
			Palavra o = palavras.after(c);
			if (o == null) {
				//quando isso acontece significa que o comentario esta no final do arquivo e nao ha nada depois
				o = new NaoClassificada("");
				palavras.add(o);
			}
			
			o.getComentarios().add(0, c);
			palavras.remove(c);
		}
		
	}
	
	private void valores() {

		palavras.replace(InteiroLiteral.class, Ponto.class, InteiroLiteral.class).por(lista -> {
			InteiroLiteral a = lista.remove(0);
			InteiroLiteral b = lista.removeLast();
			DoubleLiteral c = new DoubleLiteral(a, b);
			lista.clear();
			lista.add(c);
			return lista;
		});

		palavras.replace(InteiroLiteral.class, Ponto.class, LongLiteral.class).por(lista -> {
			LongLiteral a = lista.remove(0);
			LongLiteral b = lista.removeLast();
			DoubleLiteral c = new DoubleLiteral(a, b);
			lista.clear();
			lista.add(c);
			return lista;
		});
		
		Is0.func = i -> i.is(
			Virgula.class, AbreParenteses.class, Asterisco.class, Select.class,
			OperadorComparacao.class
		);

		palavras.replace(Is0.class, Menos.class, InteiroLiteral.class).por(lista -> {
			Palavra menos = lista.remove(1);
			InteiroLiteral x = (InteiroLiteral) lista.get(1);
			x.negativar();
			x.absorverIdentacao(menos);
			return lista;
		});

		palavras.replace(Is0.class, Menos.class, LongLiteral.class).por(lista -> {
			Palavra menos = lista.remove(1);
			LongLiteral x = (LongLiteral) lista.get(1);
			x.negativar();
			x.absorverIdentacao(menos);
			return lista;
		});
		
	}

	private void basicos() {
		palavras.replace(Menos.class, Menos.class).por(MenosMenos.class);
		palavras.replace(Barra.class, Asterisco.class).por(ComentarioBlocoOpen.class);
		palavras.replace(Asterisco.class, Barra.class).por(ComentarioBlocoClose.class);
		palavras.replace(Barra.class, Barra.class).por(BarraBarra.class);
		palavras.replace(Menor.class, Igual.class).por(MenorOuIgual.class);
		palavras.replace(Maior.class, Igual.class).por(MaiorOuIgual.class);
	}
	
	private void stringsEComentarios() {

		Linguagem lg = palavras.getLinguagem();
		
		AspasDuplas aspasDuplas = lg.isStringComAspasDuplas() ? palavras.getFirst(AspasDuplas.class) : null;
		AspasSimples aspasSimples = lg.isStringComAspasSimples() ? palavras.getFirst(AspasSimples.class) : null;
		Crase crase = lg.isStringComCrase() ? palavras.getFirst(Crase.class) : null;
		
		ComentarioBlocoOpen bloco = palavras.getFirst(ComentarioBlocoOpen.class);
		
		Palavra mm = palavras.getFirst(palavras.getLinguagem().getComentarioLinhaOpenClass());

		int iAspasSimples = aspasSimples == null ? Integer.MAX_VALUE : palavras.indexOf(aspasSimples);
		int iAspasDuplas = aspasDuplas == null ? Integer.MAX_VALUE : palavras.indexOf(aspasDuplas);
		int iCrase = crase == null ? Integer.MAX_VALUE : palavras.indexOf(crase);
		
		int im = mm == null ? Integer.MAX_VALUE : palavras.indexOf(mm);
		int ib = bloco == null ? Integer.MAX_VALUE : palavras.indexOf(bloco);
		
		ListInteger ints = new ListInteger(iAspasSimples, iAspasDuplas, iCrase, im, ib);
		ints.sort();
		int menor = ints.get(0);
		
		if (menor == Integer.MAX_VALUE) {
			return;
		}
		if (menor == iAspasSimples) {
			strings(aspasSimples, o -> false);
		} else if (menor == iAspasDuplas) {
			strings(aspasDuplas, o -> o.is(BarraInvertida.class));
		} else if (menor == iCrase) {
			strings(crase, o -> false);
		} else if (menor == im) {
			comentarioDeLinha(mm);
		} else if (menor == ib) {
			comentarioBloco(bloco);
		} else {
			throw new NaoImplementadoException();
		}
		
		stringsEComentarios();//precisa ter, pois deve repetir enquanto houver

	}

	private void strings(Palavra charr, F1<Palavra, Boolean> isEscape) {

		StringLiteral s = new StringLiteral();
		s.setAbre(charr);

		palavras.replace(charr, s);
		
		boolean escapando = false;

		while (true) {
			
			Palavra o = palavras.removeAfter(s);
			
			if (escapando) {
				escapando = false;
				s.add(o);
				continue;
			}
			
			if (isEscape.call(o)) {
				escapando = true;
				s.add(o);
				continue;
			}
			
			if (o.is(charr.getClass())) {
				s.setFecha(o);
				break;
			}
			
			s.add(o);
			
		}

	}

	private void comentarioDeLinha(Palavra open) {

		Comentario s = new Comentario();

		palavras.replace(open, s);

		s.add(new ComentarioLinhaOpen());

		while (true) {
			Palavra p = palavras.after(s);
			if (p == null || p instanceof Quebra) {
				break;
			}
			s.add(p);
			palavras.remove(p);
		}

	}

	private void comentarioBloco(ComentarioBlocoOpen open) {

		Comentario s = new Comentario();

		palavras.replace(open, s);
		s.add(open);

		while (true) {
			Palavra p = palavras.removeAfter(s);
			s.add(p);
			if (p instanceof ComentarioBlocoClose) {
				break;
			}
		}

	}
	
	private void removeEspacosETabs() {
		
		Is0.func = i -> !i.is(Espaco.class, Tab.class, Quebra.class);
		
		while (true) {

			int size = palavras.getList().size();
			
			palavras.replace(Espaco.class, Is0.class).por(itens -> {
				itens.remove(0);
				itens.get(0).incEspaco();
				return itens;
			});
			
			palavras.replace(Tab.class, Is0.class).por(itens -> {
				itens.remove(0);
				itens.get(0).incTab();
				return itens;
			});

			palavras.replace(Quebra.class, Is0.class).por(itens -> {
				itens.remove(0);
				itens.get(0).incQuebra();
				return itens;
			});
			
			if (palavras.getList().size() == size) {
				break;
			}
			
		}
		
		palavras.remove(Espaco.class);
		palavras.remove(Tab.class);
		palavras.remove(Quebra.class);
		
	}
	
	private void aberturasEFechamentos() {
		
		{

			Lst<AbreChaves> abres = palavras.filter(AbreChaves.class);
			
			for (AbreChaves abre : abres) {
				
				int opens = 1;
				
				Palavra o = abre;
				
				while (opens > 0) {
					
					o = palavras.after(o);
					
					if (o instanceof AbreChaves) {
						opens++;
					} else if (o instanceof FechaChaves) {
						opens--;
					}
					
				}
				
				FechaChaves fecha = (FechaChaves) o;
				
				abre.setFechamento(fecha);
				fecha.setAbertura(abre);
				
			}
			
		}

		{

			Lst<AbreParenteses> abres = palavras.filter(AbreParenteses.class);
			
			for (AbreParenteses abre : abres) {
				
				int opens = 1;
				
				Palavra o = abre;
				
				while (opens > 0) {
					
					o = palavras.after(o);
					
					if (o == null) {
						throw new NullPointerException("o == null");
					}
					
					if (o instanceof AbreParenteses) {
						opens++;
					} else if (o instanceof FechaParenteses) {
						opens--;
					}
					
				}
				
				FechaParenteses fecha = (FechaParenteses) o;
				
				abre.setFechamento(fecha);
				fecha.setAbertura(abre);
				
			}
			
		}
		
		{

			Lst<AbreColchetes> abres = palavras.filter(AbreColchetes.class);
			
			for (AbreColchetes abre : abres) {
				
				int opens = 1;
				
				Palavra o = abre;
				
				while (opens > 0) {
					
					o = palavras.after(o);
					
					if (o instanceof AbreColchetes) {
						opens++;
					} else if (o instanceof FechaColchetes) {
						opens--;
					}
					
				}
				
				FechaColchetes fecha = (FechaColchetes) o;
				
				abre.setFechamento(fecha);
				fecha.setAbertura(abre);
				
			}
			
		}
		
	}
	

}
