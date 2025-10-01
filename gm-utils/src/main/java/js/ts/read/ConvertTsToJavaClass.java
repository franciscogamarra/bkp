package js.ts.read;

import gm.languages.java.expressoes.TipoJava;
import gm.languages.java.expressoes.words.Class_;
import gm.languages.java.expressoes.words.Private;
import gm.languages.java.expressoes.words.This;
import gm.languages.palavras.DefaultReplaces;
import gm.languages.palavras.Is0;
import gm.languages.palavras.Linguagem;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.literal.StringLiteral;
import gm.languages.palavras.comuns.simples.AbreChaves;
import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.AspasDuplas;
import gm.languages.palavras.comuns.simples.AspasSimples;
import gm.languages.palavras.comuns.simples.BarraInvertida;
import gm.languages.palavras.comuns.simples.Cifrao;
import gm.languages.palavras.comuns.simples.Crase;
import gm.languages.palavras.comuns.simples.DoisPontos;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.languages.palavras.comuns.simples.Ponto;
import gm.languages.ts.words.Any;
import gm.languages.ts.words.Constructor;
import gm.languages.ts.words.Default;
import gm.languages.ts.words.Export;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.string.ListString;
import js.ts.read.expressoes.AbreParentesesCraseParam;
import js.ts.read.expressoes.Chamada;
import js.ts.read.expressoes.FechaParentesesCraseParam;
import js.ts.read.expressoes.Metodo;
import js.ts.read.expressoes.PublicClass;
import js.ts.read.expressoes.PublicConstructor;
import src.commom.utils.string.StringAfterLast;
import src.commom.utils.string.StringBeforeLast;

public class ConvertTsToJavaClass {

	public static void main(String[] args) {
		new ConvertTsToJavaClass("/opt/desen/gm/cs2019/gm-utils/src/main/java/js/ts/read/files/SuperComponent.ts");
	}
	
	private final Palavras palavras = new Palavras();
	private final String simpleName;
	
	private ConvertTsToJavaClass(String fileName) {
		
		simpleName = StringBeforeLast.get(StringAfterLast.get(fileName, "/"), ".");
		
		palavras.setCaminhoSaveAnalise("/opt/desen/gm/cs2019/gm-utils/src/main/java/js/ts/read/files/analise/" + simpleName + ".txt");

		ListString list = new ListString().load(fileName).rtrim();
		list.removeDoubleWhites();
		String s = list.replaceTexto(" ", " _espaco_ ").replaceTexto("\t", " _tab_ ").toString(" _quebra_ ");
		
		Linguagem.selected = Linguagem.js;
//		String s = StringTrim.plus(sql).toLowerCase();
		PalavrasBuildTs.start(palavras);
		ListString.separaPalavras(s).trimPlus().each(i -> palavras.add(i));
		DefaultReplaces.exec(palavras);
		exec();
		palavras.save("/opt/desen/gm/cs2019/gm-utils/src/main/java/js/ts/read/files/out/" + simpleName + ".java");
		
	}
	
	private void exec() {
		
		palavras.filter(StringLiteral.class).each(i -> toJavaString(i));
		
		palavras.replace(Any.class).porNew(() -> new TipoJava(Object.class));
		
		palavras.replace(Export.class, Default.class, Class_.class, NaoClassificada.class).porNew(() -> new PublicClass(simpleName));
		
		PublicClass publicClass = palavras.getList().uniqueObrig(o -> o.is(PublicClass.class));
		
		{
			Palavra o = palavras.after(publicClass);
			while (!o.is(AbreChaves.class)) {
				o = palavras.after(o);
			}
			
			AbreChaves abre = (AbreChaves) o;
			abre.setProprietario(publicClass);
			publicClass.setOpen(abre);
			
		}

		{
			
			PublicConstructor c = new PublicConstructor(simpleName);
			c.incTab();
			
			palavras.replace(Constructor.class, AbreParenteses.class).por(lst -> {
				
				AbreParenteses open = lst.remove(1);
				lst.clear();
				lst.add(c);
				lst.add(open);
				open.setProprietario(c);
				
				AbreChaves o = palavras.after(open.getFechamento());
				o.setProprietario(c);
				
				return lst;
			});
			
		}
		
		Is0.func = o -> o instanceof NaoClassificada && o.getS().contentEquals("name");

		palavras.replace(This.class, Ponto.class, Constructor.class, Ponto.class, Is0.class).porUm(lst -> new Chamada("getClass().getSimpleName()", new TipoJava(String.class)));

		Palavra o = palavras.after(publicClass.getOpen());
		
		while (o != null) {
			
			if (o instanceof AbreChaves) {
				
				AbreChaves abre = (AbreChaves) o;
				
				if (abre.getProprietario() == null) {
					
					FechaParenteses fechaParenteses = palavras.before(abre);
					AbreParenteses abreParenteses = fechaParenteses.getAbertura();
					NaoClassificada nomeMetodo = palavras.before(abreParenteses);
					
					Metodo metodo = new Metodo(nomeMetodo.getS());
					palavras.replace(nomeMetodo, metodo);
					abre.setProprietario(metodo);
					
					Palavra before = palavras.before(metodo);
					if (before.is(Private.class)) {
						metodo.setPrivado(true);
						palavras.remove(before);
						metodo.absorverIdentacao(before);
					}

				}
				
				o = abre.getFechamento();
			}
			
			o = palavras.after(o);
			
		}
		
		palavras.saveAnalise();
		
		palavras.replace(NaoClassificada.class, DoisPontos.class, TipoJava.class).por(lst -> {
			Palavra a = lst.rm();
			lst.rm();
			a.setEspacos(1);
			lst.add(a);
			return lst;
		});
		
		palavras.replace(AbreParenteses.class, Palavra.class).por(lst -> {
			Palavra a = lst.get(1);
			a.setEspacos(0);
			return lst;
		});
		
//		palavras.filter(AbreParenteses.class)
//			.filter(i -> i.getDono() instanceof Metodo && !(palavras.after(i) instanceof FechaParenteses));
		
		
		
		
		palavras.saveAnalise();
		
	}

	private void toJavaString(StringLiteral o) {
		
		Palavra abre = o.getAbre();
		
		if (abre instanceof AspasDuplas) {
			return;
		}
		
		if (abre instanceof AspasSimples || abre instanceof Crase ) {
			
			o.setAbre(new AspasDuplas().absorverIdentacao(abre));
			o.setFecha(new AspasDuplas().absorverIdentacao(o.getFecha()));
			
			Palavras ps = o.getPalavras();
			
			ps.replace(AspasDuplas.class).por(lst -> {
				lst.add(0, new BarraInvertida());
				return lst;
			});
			
			if (abre instanceof Crase) {
				
				ps.replace(Cifrao.class, AbreParenteses.class).por(lst -> {
					
					lst.rm();
					
					AbreParenteses a = lst.rm();
					
					Palavra i = palavras.after(a);
					int opens = 1;
					while (opens > 0) {
						
						if (i instanceof AbreParenteses) {
							opens++;
						} else if (i instanceof FechaParenteses) {
							opens--;
						}
						
					}
					
					FechaParenteses b = (FechaParenteses) i;
					
					AbreParentesesCraseParam aa = new AbreParentesesCraseParam();
					FechaParentesesCraseParam bb = new FechaParentesesCraseParam();
					
					aa.setFechamento(bb);
					bb.setAbertura(aa);
					
					lst.add(aa);
					palavras.replace(b, bb);
					
					return lst;
				});
				
			}
			
			return;
		}
		
		throw new NaoImplementadoException();
		
	}

	
}
