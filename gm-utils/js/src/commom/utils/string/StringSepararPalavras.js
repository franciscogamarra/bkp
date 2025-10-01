import ArrayLst from '../array/ArrayLst';
import Null from '../object/Null';
import StringBox from './StringBox';
import StringRight from './StringRight';

export default class StringSepararPalavras {

	list = new ArrayLst();

	constructor() {

		this.addd("bndes","BNDES");
		this.addd("ddd","DDD");
		this.addd("cpf","CPF");

		this.add("fase");
		this.add("investimento");
		this.addd("cobranca","cobrança");
		this.add("recebimento");
		this.add("fixado");
		this.add("arquivo");
		this.addd("informacao","informação");
		this.addd("condicao","condição");
		this.addd("proposta","proposta");
		this.addd("situacao","situação");
		this.addd("datahora","data/hora");
		this.addd("operacao","operação");
		this.addd("credito","crédito");
		this.addd("liberacao", "liberação");
		this.add("programa");
		this.addd("amortizacao", "amortização");
		this.add("periodicidade");
		this.add("taxa");
		this.add("juros");
		this.addd("carencia","carência");
		this.addd("correcao","correção");
		this.addd("condicao","condição");
		this.addd("contratacao","contratação");
		this.add("aprovada");
		this.add("garantia");
		this.add("prazo");
		this.add("evento");
		this.add("tipo");
		this.add("enquadramento");
		this.add("cliente");
		this.add("recurso");
		this.addd("proprio", "próprio");
		this.add("sobre");
		this.addd("indice","índice");
		this.addd("instituicao","instituição");
		this.addd("ultima","última");
		this.add("primeira");
		this.add("valor");
		this.add("total");
		this.addd("orcamento","orçamento");
		this.add("sistema");
		this.addd("sistematica","sistemática");
		this.addd("sistematico","sistemático");
		this.add("operacional");
		this.add("limite");
		this.addd("solicitacao","solicitação");
		this.add("cadastro");
		this.add("envio");
		this.add("linha");
		this.add("custo");
		this.addd("intermediacao","intermediação");
		this.addd("financeira","financeira");
		this.add("data");
		this.addd("inicio","início");
		this.addd("contagem","contagem");
		this.add("encargo");
		this.add("risco");
		this.addd("fgi","FGI");
		this.addd("tfb","TFB");
		this.add("info");
		this.add("complementar");
		this.add("projeto");
		/* add("id"); */
		this.add("fim");
		this.add("primeiro");
		this.add("contrato");
		this.add("num");
		this.addd("homologacao","homologação");
		this.add("financiamento");
		this.add("protocolo");
		this.add("financiada");
		this.add("sisbr");
		this.addd("mes","mês");
		this.add("meses");
		this.addd("exigivel","exigível");

		this.list.sort((a,b) => b.crua.length - a.crua.length);
		for (let i = 0; i < this.list.size(); i++) {
			this.list.get(i).id = i;
		}

	}

	addd(crua, cozida) {
		let o = {};
		o.crua = crua;
		o.cozida = cozida;
		this.list.add(o);

		if (crua.endsWith("cao") && cozida.endsWith("ção")) {
			crua = StringRight.ignore(crua, 3) + "coes";
			cozida = StringRight.ignore(cozida, 3) + "ções";
			this.addd(crua, cozida);
		} else if (crua.endsWith("ivel") && cozida.endsWith("ível")) {
			crua = StringRight.ignore(crua, 4) + "iveis";
			cozida = StringRight.ignore(cozida, 4) + "íveis";
			this.addd(crua, cozida);
		}

	}

	add(s) {
		this.addd(s, s);
	}

	run(ss) {

		let s = new StringBox();
		s.set(ss);

		this.list.forEach(p => {
			s.replace(p.crua, "["+p.id+"]");
			s.replace(p.cozida, "["+p.id+"]");
		});

		this.list.forEach(p => {
			s.replace("["+p.id+"]", " " + p.cozida + " ");
		});

		s.set(" " + s.get() + " ");
		s.replace(" s ", "s ");

		return s.get().trim();

	}

	static instance;

	static exec(s) {

		if (Null.is(StringSepararPalavras.instance)) {
			StringSepararPalavras.instance = new StringSepararPalavras();
		}

		return StringSepararPalavras.instance.run(s);

	}

}
