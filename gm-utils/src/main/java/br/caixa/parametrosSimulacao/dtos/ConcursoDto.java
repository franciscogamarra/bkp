package br.caixa.parametrosSimulacao.dtos;

public class ConcursoDto extends Dto {

	public String modalidade;
	public ModalidadeDetalhadaDto modalidadeDetalhada;
	public String tipoConcurso;
	public Integer numero;
	public String dataFechamento;
	public String dataAbertura;
	public String dataHoraSorteio;
	public String dataSorteio;
	public Double valorApostaMinima;
	public String situacao;
	public Boolean bloqueado;
	public Double estimativa;
	public Boolean aberto;
	public Boolean naoInicializado;
	public String dataFinalBloqueio;
}
