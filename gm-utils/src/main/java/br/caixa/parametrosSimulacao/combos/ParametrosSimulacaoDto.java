package br.caixa.parametrosSimulacao.combos;

import br.caixa.parametrosSimulacao.dtos.Dto;
import gm.utils.comum.Lst;

public class ParametrosSimulacaoDto extends Dto {

	public TipoComboDto tipoCombo;
	public Lst<ModalidadesComboDto> modalidadesCombo;
	public Double valor;
}
