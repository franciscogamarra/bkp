package br.mapa;

import gm.languages.java.JavaLoad;
import gm.languages.java.expressoes.DeclaracaoDeMetodo;
import gm.languages.java.expressoes.DeclaracaoDeVariavel;
import gm.languages.java.expressoes.ModificadorDeAcesso;
import gm.languages.java.expressoes.TipoJava;
import gm.languages.java.expressoes.words.Private;
import gm.languages.java.expressoes.words.Protected;
import gm.languages.java.expressoes.words.Public;
import gm.languages.java.expressoes.words.Throws;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.PontoEVirgula;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.javaCreate.JcTipo;

public class AdicionarTempo extends JavaLoad {

	protected AdicionarTempo(GFile file) {
		super(file);
	}
	
	@Override
	protected void exec1() {
		
		super.exec1();
		
		JcTipo tipoThis = getTipoThis().getJcTipo();
		
		JcClasse jc = JcClasse.build(tipoThis, "");
		jc.extends_(new JcTipo(tipoThis.getName() + "Super"));
		
		jc.addSerialVersionId();
			
		filter(DeclaracaoDeMetodo.class).each(mt -> {
			
			ModificadorDeAcesso mod = mt.getModificadorDeAcesso();
			mod.setFinal(false);

			JcMetodo m = jc.metodo(mt.getNome());
			m.override();
			
			if (mod.is(Private.class)) {
				Public p = new Public();
				p.setAbstract(mod.isAbstract());
				p.setSynchronized(mod.isSynchronized());
				p.setStatic(mod.isStatic());
				mt.setModificadorDeAcesso(p);
				mod = p;
				mod.setTabs(1);
			}
			
			mod.setFinal(false);
			
			if (mod.is(Protected.class)) {
				m.protected_();
			} else {
				m.public_();
			}
			
			m.setTipoRetorno( mt.getTipo().getJcTipo() );
			
			m.add("ControleTempo.start();");
			m.add("try {");
			
			String paramNames = "";
			
			
			AbreParenteses abre = after(mt);
			
			Palavra o = after(abre);
			
			while (o != abre.getFechamento()) {
				
				if (o.is(DeclaracaoDeVariavel.class)) {
					DeclaracaoDeVariavel dec = (DeclaracaoDeVariavel) o;
					String nome = dec.getNome().getS();
					JcTipo tipo = dec.getTipo().getJcTipo();
					m.param(nome, tipo);
					paramNames += ", " + nome;
					
				}
				
				o = after(o);
				
			}
			
			String superr = "super." + m.getNome() + "(";

			if (!paramNames.isEmpty()) {
				superr += paramNames.substring(2);
			}
			
			superr += ");"; 
			
			if (!m.getTipoRetorno().eq(void.class)) {
				superr = "return " + superr;
			}
			
			m.add(superr);
			
			m.add("} finally {");
			m.add("ControleTempo.finish();");
			m.add("}");
			
			o = after(o);
			
			if (o.is(Throws.class)) {
				
				o = after(o);
				
				while (!o.is(AbreBloco.class, PontoEVirgula.class)) {
					
					if (o.is(TipoJava.class)) {
						TipoJava tipo = (TipoJava) o;
						m.addThrows(tipo.getJcTipo());
					}
					
					o = after(o);
					
				}
				
				
			}
			
		});
		
//		G:\Meu Drive\Documents\mapa\Saida.java
		
		GFile saida = GFile.get("C:\\desenvolvimento\\projetos\\sisprocer-src\\sisprocer-web\\src\\main\\java\\br\\gov\\mapa\\sisprocer\\apresentacao\\action\\ManterAnaliseActionA.java");
		
		jc.get().save(saida);
		
		saida.load().replaceTexto("...String", "String...").save();
		
	}

	public static void main(String[] args) {
		GFile file = GFile.get("C:\\desenvolvimento\\projetos\\sisprocer-src\\sisprocer-web\\src\\main\\java\\br\\gov\\mapa\\sisprocer\\apresentacao\\action\\ManterAnaliseAction.java");
		AdicionarTempo o = new AdicionarTempo(file);
		o.exec();
		o.getResult().save(file.getPath().join("Saida.java"));
	}
	
}