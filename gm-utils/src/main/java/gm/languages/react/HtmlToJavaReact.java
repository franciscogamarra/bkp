package gm.languages.react;

import gm.languages.PalavrasLoad;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.conjuntos.bloco.FechaBloco;
import gm.languages.palavras.comuns.simples.Barra;
import gm.languages.palavras.comuns.simples.Maior;
import gm.languages.palavras.comuns.simples.Menor;
import gm.languages.react.termos.Tag;
import gm.languages.react.termos.TagAbre;
import gm.languages.react.termos.TagAbreEnd;
import gm.languages.react.termos.TagFecha;
import gm.utils.comum.Lst;
import gm.utils.files.GFile;
import gm.utils.string.ListString;
import src.commom.utils.comum.BooleanWrapper;

public class HtmlToJavaReact extends PalavrasLoad {
	
	private static ListString exemplo = new ListString();
	static {
		exemplo.add("              <tr>");
		exemplo.add("                <OurInput form={form} field=\"addrZip\" cep disabled={disabled} required onValidate={s => handleCheckCep(s)} onInvalidate={() => putCep({})} td1/>");
		exemplo.add("                <OurInput form={form} field=\"addrCity\" label=\"Cidade\" readOnly sxBox={{width: \"100%\"}} td2 {...CEP_FIELDS} />");
		exemplo.add("              </tr>");
	}
	
	private ListString list;
	
	public static void main(String[] args) {
		
//		ListString list = new ListString();
//		list.add(StringClipboard.get().split("\n"));
//		ListString list = exemplo;
		
		ListString list = GFile.get("C:\\opt\\desen\\gm\\portal-do-cliente\\src\\cotacao\\BotaoCotacao.js").load();
		
		HtmlToJavaReact o = new HtmlToJavaReact(list);
		o.exec();
		o.palavras.print();
//		o.palavras.getListString().print();
	}
	
	public HtmlToJavaReact(ListString list) {
		this.list = list;
	}

	@Override
	protected void exec1() {
		
		blocos();
		
		execTags();
		
	}
	
	private void execTags() {
		
		BooleanWrapper houveMudancas = new BooleanWrapper();
		
		filter(Menor.class).filter(menor -> afterIs(menor, Maior.class)).forEach(menor -> {
			removeAfter(menor);
			TagAbre tagAbre = new TagAbre(Tag.frag());
			replace(menor, tagAbre);
			houveMudancas.set(true);
		});

		filter(Menor.class).filter(menor -> afterIs(menor, Barra.class) && afterIs(after(menor), Maior.class)).forEach(menor -> {
			removeAfter(menor);
			removeAfter(menor);
			TagFecha tagFecha = new TagFecha(Tag.frag());
			replace(menor, tagFecha);
			houveMudancas.set(true);
		});
		
		filter(Menor.class).filter(menor -> afterIs(menor, Barra.class)).forEach(menor -> {
			removeAfter(menor);
			Tag tag = Tag.get(removeAfter(menor).getS());
			TagFecha tagFecha = new TagFecha(tag);
			replace(menor, tagFecha);
			removeAfter(tagFecha).assertt(Maior.class);
			houveMudancas.set(true);
		});
		
		
		filter(Menor.class).filter(menor -> beforeIs(menor, TagFecha.class)).forEach(menor -> {
			Tag tag = Tag.get(removeAfter(menor).getS());
			TagAbre tagAbre = new TagAbre(tag);
			replace(menor, tagAbre);
			
			if (afterIs(tagAbre, Barra.class)) {
				// <Comp/>
				removeAfter(tagAbre);
				Maior maior = after(tagAbre);
				replace(maior, new TagFecha(tagAbre));
			} else if (afterIs(tagAbre, Maior.class)) {
				// <Comp>
				removeAfter(tagAbre);
			} else {
				tagAbre.params = new Lst<>();
			}
			
			houveMudancas.set(true);
			
		});

		filter(Menor.class).filter(menor -> afterIs(after(menor), Maior.class)).forEach(menor -> {
			Tag tag = Tag.get(removeAfter(menor).getS());
			TagAbre tagAbre = new TagAbre(tag);
			replace(menor, tagAbre);
			removeAfter(tagAbre);
			houveMudancas.set(true);
		});
		
		filter(Menor.class).forEach(menor -> {

			Palavra a = after(menor);
			
			if (!a.is(NaoClassificada.class)) {
				return;
			}
			
			if (!Tag.contains(a.getS())) {
				return;
			}
			
			Palavra aa = after(a);

			if (aa.getEspacos() == 0 && aa.getQuebras() == 0 && aa.getTabs() == 0) {
				return;
			}
			
			TagAbre tagAbre = new TagAbre(Tag.get(a.getS()));
			replace(menor, tagAbre);
			remove(a);
			tagAbre.params = new Lst<>();
			
			houveMudancas.set(true);
			
		});
		
		filter(Maior.class).forEach(o -> {

			Palavra a = after(o);
			
			if (!a.is(TagAbre.class)) {
				return;
			}
			
			Palavra b = before(o);
			
			if (!b.is(FechaBloco.class)) {
				return;
			}

			TagAbreEnd tagAbreEnd = new TagAbreEnd();
			
			replace(o, tagAbreEnd);
			
			houveMudancas.set(true);
			
		});
		
		if (houveMudancas.isTrue()) {
			execTags();
		}

	}

	@Override
	protected ListString loadListString() {
		return list;
	}
	
}
