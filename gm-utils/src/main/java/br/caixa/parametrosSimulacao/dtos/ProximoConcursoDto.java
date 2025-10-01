package br.caixa.parametrosSimulacao.dtos;

import gm.utils.comum.Lst;

public class ProximoConcursoDto extends Dto {

	public ConcursoDto concurso;
	public String situacaoConcursoCanal;
	public Integer probabilidadeDeGanhar;
	public Boolean aceitaEspelho;
	public Integer prognosticoMaximo;
	public Integer quantidadeMinima;
	public Integer quantidadeMaxima;
	public Lst<Integer> teimosinhas;
	public Integer quantidadeSurpresinhas;
	public Lst<ValoresApostaDto> valoresAposta;
	public TrevosDto trevos;
	public Boolean concursoCanalAberto;
	public Boolean concursoDisponivelSimulacao;
	public Lst<EquipeDto> equipes;
	public Lst<PartidaDto> partidas;
	public Lst<Object> legendas;
	public Double valorApostaMinima;
	public Lst<MesDto> meses;
}
