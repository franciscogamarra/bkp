package gm.cenarios;

import br.com.sicoob.concessao.bndes.dtos.EnvioPropostaDTO;
import br.support.comum.Lst;
import gm.bos.SolicitacoesAPAResourceClient;
import gm.support.Programa;
import gm.support.PropostaCredito;

public class Cenario0002 {

	public static void main(String[] args) {
		
		Lst<PropostaCredito> propostas = new Lst<>();
		
		for (int i = 0; i < 10; i++) {
			PropostaCredito o = new PropostaCredito();
			o.load("proposta-credito-financiamento");
			o.setId(null);
			o.setNumeroProposta(null);
			o.setPrograma(Programa.O2473);
			o.save();
			propostas.add(o);
		}

		PropostaCredito o = new PropostaCredito();
		o.load("proposta-credito-financiamento");
		o.setId(null);
		o.setNumeroProposta(null);
		o.setPrograma(Programa.O2473);
		o.save();
		propostas.add(o);
		
		Lst<EnvioPropostaDTO> itens = propostas.map(i -> SolicitacoesAPAResourceClient.convert(i));
		EnvioPropostaDTO dto = SolicitacoesAPAResourceClient.convert(o);
		dto.setBolProjetoColetivo(true);
		itens.add(dto);
		
		String res = SolicitacoesAPAResourceClient.enviaPropostasBndes(itens);
		System.out.println(res);
		
	}
	
}