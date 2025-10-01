package gm.factoryes;

import java.math.BigDecimal;

import br.com.sicoob.concessao.bndes.dtos.PropostaCreditoBndesDTO;

public class NewProposta {
	
	private final PropostaCreditoBndesDTO dto = new PropostaCreditoBndesDTO();
	
	public NewProposta() {
		dto.setIdInstituicao(1);
		dto.setIdUnidadeInstituicao(2);
		dto.setPercTaxaJurosRisco(new BigDecimal(3.5000));
		dto.setPercTaxaFixaBndes(new BigDecimal(12.119788));
		dto.setPercTaxaJuros(new BigDecimal(0.000000));
		dto.setPercTaxaJurosBasica(new BigDecimal(0.950000));
		valorOrcamento(6000).percFinanciada(5);
		dto.setHabilitaEnvioBndes(false);
		dto.setBolSisbr30(true);
		dto.setBolInformacaoAdicional(true);
		dto.setBolContratacaoAprovadaBndes(true);
		dto.setBolOperacaoColetivo(false);
		dto.setBolEncargosExigiveisCarencia(false);
		dto.setRejeitadaPorDuplicacao(false);
		dto.setBolPermiteAditamento(true);
		dto.setBolLiberacaoParcelada(true);
		dto.setBolPermiteCancelamento(true);
		dto.setPrograma(Programas.Item2473);
	}
	
	public NewProposta instituicao(int value) {
		dto.setIdInstituicao(value);
		return this;
	}
	
	public NewProposta unidadeInstituicao(int value) {
		dto.setIdUnidadeInstituicao(value);
		return this;
	}
	
	public NewProposta valorOrcamento(int value) {
		dto.setValorTotalOrcamento(new BigDecimal(value));
		calcula();
		return this;
	}
	
	public NewProposta percFinanciada(int value) {
		dto.setPercFinanciada(new BigDecimal(value));
		calcula();
		return this;
	}

	private void calcula() {
		
		if (dto.getValorTotalOrcamento() == null) {
			return;
		}
		
		if (dto.getPercFinanciada() == null) {
			return;
		}
		
		dto.setPercRecursoProprio(new BigDecimal(100 - dto.getPercFinanciada().intValue()));
		dto.setValorFinanciado(dto.getValorTotalOrcamento().divide(new BigDecimal(100)).multiply(dto.getPercRecursoProprio()));
		dto.setValorRecursosProprios(dto.getValorTotalOrcamento().subtract(dto.getValorFinanciado()));
		
	}
	
}