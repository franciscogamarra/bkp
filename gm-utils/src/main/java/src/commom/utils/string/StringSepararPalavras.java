package src.commom.utils.string;

import src.commom.utils.array.Itens;
import src.commom.utils.object.Null;

public class StringSepararPalavras {

	private final Itens<StringSepararPalavrasSupport> list = new Itens<>();
	
	private StringSepararPalavras() {
		
		addd("bndes","BNDES");
		addd("ddd","DDD");
		addd("cpf","CPF");

		add("fase");
		add("investimento");
		addd("cobranca","cobrança");
		add("recebimento");
		add("fixado");
		add("arquivo");
		addd("informacao","informação");
		addd("condicao","condição");
		addd("proposta","proposta");
		addd("situacao","situação");
		addd("datahora","data/hora");
		addd("operacao","operação");
		addd("credito","crédito");
		addd("liberacao", "liberação");
		add("programa");
		addd("amortizacao", "amortização");
		add("periodicidade");
		add("taxa");
		add("juros");
		addd("carencia","carência");
		addd("correcao","correção");
		addd("condicao","condição");
		addd("contratacao","contratação");
		add("aprovada");
		add("garantia");
		add("prazo");
		add("evento");
		add("tipo");
		add("enquadramento");
		add("cliente");
		add("recurso");
		addd("proprio", "próprio");
		add("sobre");
		addd("indice","índice");
		addd("instituicao","instituição");
		addd("ultima","última");
		add("primeira");
		add("valor");
		add("total");
		addd("orcamento","orçamento");
		add("sistema");
		addd("sistematica","sistemática");
		addd("sistematico","sistemático");
		add("operacional");
		add("limite");
		addd("solicitacao","solicitação");
		add("cadastro");
		add("envio");
		add("linha");
		add("custo");
		addd("intermediacao","intermediação");
		addd("financeira","financeira");
		add("data");
		addd("inicio","início");
		addd("contagem","contagem");
		add("encargo");
		add("risco");
		addd("fgi","FGI");
		addd("tfb","TFB");
		add("info");
		add("complementar");
		add("projeto");
//		add("id");
		add("fim");
		add("primeiro");
		add("contrato");
		add("num");
		addd("homologacao","homologação");
		add("financiamento");
		add("protocolo");
		add("financiada");
		add("sisbr");
		addd("mes","mês");
		add("meses");
		addd("exigivel","exigível");
		
		list.sort((a,b) -> b.crua.length() - a.crua.length());
		for (int i = 0; i < list.size(); i++) {
			list.get(i).id = i;
		}
		
	}

	private void addd(String crua, String cozida) {
		StringSepararPalavrasSupport o = new StringSepararPalavrasSupport();
		o.crua = crua;
		o.cozida = cozida;
		list.add(o);
		
		if (crua.endsWith("cao") && cozida.endsWith("ção")) {
			crua = StringRight.ignore(crua, 3) + "coes";
			cozida = StringRight.ignore(cozida, 3) + "ções";
			addd(crua, cozida);
		} else if (crua.endsWith("ivel") && cozida.endsWith("ível")) {
			crua = StringRight.ignore(crua, 4) + "iveis";
			cozida = StringRight.ignore(cozida, 4) + "íveis";
			addd(crua, cozida);
		}
		
	}
	
	private void add(String s) {
		addd(s, s);
	}
	
	private String run(String ss) {
		
		StringBox s = new StringBox(ss);
		
		list.forEach(p -> {
			s.replace(p.crua, "["+p.id+"]");
			s.replace(p.cozida, "["+p.id+"]");
		});

		list.forEach(p -> {
			s.replace("["+p.id+"]", " " + p.cozida + " ");
		});
		
		s.set(" " + s.get() + " ");
		s.replace(" s ", "s ");
		
		return s.get().trim();
		
	}
	
	private static StringSepararPalavras instance;
	
	public static String exec(String s) {
		
		if (Null.is(instance)) {
			instance = new StringSepararPalavras();
		}
		
		return instance.run(s);
		
	}
	
}
