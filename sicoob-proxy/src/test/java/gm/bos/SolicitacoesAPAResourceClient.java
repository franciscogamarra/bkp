package gm.bos;

import br.com.sicoob.concessao.bndes.dtos.EnvioPropostaDTO;
import br.com.sicoob.concessao.bndes.dtos.UsuarioDTO;
import br.support.comum.Lst;
import br.utils.ApiClient;
import gm.support.PropostaCredito;

public class SolicitacoesAPAResourceClient {
	
	private static final UsuarioDTO usuario = new UsuarioDTO("fabioo0001_00");

	public static String enviaPropostasComuns(Lst<PropostaCredito> propostas) {
		return enviaPropostasBndes(propostas.map(proposta -> convert(proposta)));
	}
	
	public static String enviaPropostasBndes(Lst<EnvioPropostaDTO> propostas) {
		return ApiClient.local.post("/solicitacoes-apa/enviar-propostas").body(propostas).call();
	}
	
	public static EnvioPropostaDTO convert(PropostaCredito proposta) {
		EnvioPropostaDTO o = new EnvioPropostaDTO();
		o.setNumeroPropostaCreditoBndes(proposta.getNumeroProposta().longValue());
		o.setUsuario(usuario);
		o.setBolProjetoColetivo(false);
		return o;
	} 
	
}