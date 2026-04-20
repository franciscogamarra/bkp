package gm.support;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;

import br.support.comum.Lst;
import br.support.comum.Print;
import br.utils.ApiClient;
import br.utils.Dados;
import br.utils.DateUtils;
import br.utils.JsonUtils;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PropostaCredito {
	
	private final Dados dados = new Dados();
	public Dados getDados() {
		dados.set("id", getId());
		dados.set("numeroProposta", getNumeroProposta());
		dados.set("programa", getPrograma().getDados());
		dados.set("enquadramentoCliente", getEnquadramentoCliente().getDados());
		if (getItensInvestimento() == null) {
			dados.set("itensInvestimento", null);
		} else {
			dados.set("itensInvestimento", getItensInvestimento().map(i -> i.getDados()));
		}
		dados.set("dataPropostaBndes", DateUtils.formatToUtcIso(getDataPropostaBndes()));
		dados.set("dataHoraAtualizacao", DateUtils.formatToUtcIso(getDataHoraAtualizacao()));
		dados.set("investimento", getInvestimento().getDados());
		return dados;
	}
	
	private Integer id;
	private Integer numeroProposta;
	private Programa programa;
	private EnquadramentoCliente enquadramentoCliente;
	private Lst<ItemInvestimento> itensInvestimento;
	private LocalDateTime dataPropostaBndes;
	private LocalDateTime dataHoraAtualizacao;
	private Investimento investimento;
	
	public void load(String resource) {
		dados.load(resource);
		setId(dados.get("id"));
		setNumeroProposta(dados.get("numeroProposta"));
		setPrograma(Programa.get(dados.get("programa.id")));
		setEnquadramentoCliente(EnquadramentoCliente.get(dados.get("enquadramentoCliente.id")));
		Collection<Map<String, Object>> itens = dados.get("itensInvestimento");
		setItensInvestimento(new Lst<>(itens).map(i -> new ItemInvestimento(i)));
		setDataPropostaBndes(DateUtils.parseFromUtcIso(dados.get("dataPropostaBndes")));
		setDataHoraAtualizacao(DateUtils.parseFromUtcIso(dados.get("dataHoraAtualizacao")));
		setInvestimento(new Investimento(dados.get("investimento")));
	}
	
	public void print() {
		Print.amarelo("===========================================================");
		Print.ln(JsonUtils.toJsonFormatado(getDados().getDados()));
		Print.amarelo("===========================================================");
	}
	
	public void save() {
		if (getNumeroProposta() == null) {
			String res = ApiClient.local.get("/numeros-proposta-credito-bndes").call();
			setNumeroProposta(Integer.parseInt(res));
			Print.blocoVerde("NumeroProposta: " + getNumeroProposta());
			itensInvestimento.forEach(i -> {
				i.setId(null);
				i.setPropostaBNDES(null);
			});
			setDataPropostaBndes(LocalDateTime.now());
			setDataHoraAtualizacao(null);
			getInvestimento().setId(null);
			getInvestimento().setDataSolicitacaoInstituicao("2024-10-20");
		}
		
		String res = ApiClient.local.post("/propostas-credito-bndes").body(getDados().getDados()).call();
		dados.set(res);
	}
	
	public void validar() {
//		org.springframework.web.client.HttpClientErrorException$NotAcceptable: 406 Not Acceptable on POST request for "http://127.0.0.1:9080/cre-concessao-bndes-api-web/api/propostas-credito-bndes": "{"mensagem":"A data da solicitação de investimento não pode ser maior que a data atual: 01/06/2025 - 10/04/2025","codigo":"Not Acceptable"}"
	}

	public void enviar() {
		ApiClient.local.post("/solicitacoes-apa/enviar-propostas").body(
			JsonUtils.fromJsonList(
			"""
			[
			    {
			        \"numeroPropostaCreditoBndes\": {numero},
			        \"usuario\": \"fabioo0001_00\"
			    }
			]	
			""".replace("{numero}", getNumeroProposta().toString())
			, Object.class)
		).call();
	}
	
}
