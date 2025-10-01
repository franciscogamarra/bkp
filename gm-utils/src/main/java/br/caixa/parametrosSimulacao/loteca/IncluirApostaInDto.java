package br.caixa.parametrosSimulacao.loteca;

import br.caixa.parametrosSimulacao.dtos.MesDto;
import br.caixa.parametrosSimulacao.dtos.TipoConcursoDto;
import gm.utils.comum.Lst;

public class IncluirApostaInDto {
	
	public Integer id;
	public String modalidade;
	public String descricaoModalidade;
	public String classe;
	public String nomeJogo;
	public String tituloJogo;
	public String tituloHeaderJogo;
	public Boolean geraEspelho;
	
	public Boolean surpresinha;
	public Integer quantidadeNumeros;
	public Integer quantidadeNumerosMaisMilionariaTrevo;
	public Integer quantidadeSurpresinhas;
	public IndicadorSurpresinhaDto indicadorSurpresinha;
	
	public String cssTabCarrinhoJogo;
	public Lst<Object> numerosSelecionados;
	public Integer quantidadeTeimosinhas;
	public Lst<Integer> concursos;
	public Integer concursoAlvo;
	public TipoConcursoDto tipoConcurso;
	public String dataInclusao; //"2023-02-15T20:52:43.752Z"
	public EquipeEsportivaDto timeDoCoracao;
	public EquipeEsportivaDto equipe;
	public String valor;
	public MesDto mesDeSorte;
	public Lst<String> numerosSelecionadosMaisMilionariaTrevo;
	public Lst<PartidaLotecaDto> partidasLoteca;
	
}
