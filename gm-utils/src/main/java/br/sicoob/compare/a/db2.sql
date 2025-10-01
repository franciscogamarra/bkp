--LISTAR_PROPOSTA_LEGADO_FINANCIAMENTO_FINAME

select distinct

  '02038232000164' as CNPJAgenteFinanceiro

, InfoRefin.NumContratoRefin as NumContratoCredito

, CondicaoOperacional.codCondicaoOperacionalBndes as CondicaoOperacional
, PropostaCredito.qtdArquivoAnexo as QuantidadeAnexos
, ProgramaBndes.codProdutoBndesOnline as Produto
, ProgramaBndes.siglaPrograma as ProgramaFinanciamento
, case
    when PropostaCredito.codSistematicaOperacional in (1,4) then 'S'
    when PropostaCredito.codSistematicaOperacional in (3,5,6) then 'C'
  end as Sistematica
, DATE(YEAR(PropostaCredito.dataPropostaBndes) || '-01-01') as AnoProposta
, PropostaCredito.numPropostaCredito as NumeroProposta
, 3 as TipoEvento

, case when Pessoa.codTipoPessoa = 1 then Pessoa.numCpfCnpj end as CNPJ
, case when Pessoa.codTipoPessoa = 0 then Pessoa.numCpfCnpj end as CPF

, EnquadramentoCliente.codCaracterizacaoCapitalSocial as CaracterizacaoCapitalSocial
, EnquadramentoCliente.bolEmpresarioIndividual as EmpresarioIndividual
, PropostaCredito.valorTotalOrcamento as ValorParticipacao

, case when Pessoa.codTipoPessoa = 1 then EnquadramentoCliente.valorReceitaOperacionalBruta end as ValorAnualPJ
, case when Pessoa.codTipoPessoa = 0 then EnquadramentoCliente.valorReceitaOperacionalBruta end as ValorAnualPF

, EnquadramentoCliente.bolControleFundoAtivoPrivado as FundoPrivateEquity

, case when Pessoa.codTipoPessoa = 1 then substring(IndicadorReceita.descIndicadorReceitaOperacionalBruta,1,1) end as TipoRendaPJ
, case when Pessoa.codTipoPessoa = 0 then substring(IndicadorReceita.descIndicadorReceitaOperacionalBruta,1,1) end as TipoRendaPF

, case when Pessoa.codTipoPessoa = 1 then EnquadramentoCliente.dataReferenciaReceitaOperacionalBruta end as DataReferenciaPJ
, case when Pessoa.codTipoPessoa = 0 then EnquadramentoCliente.dataReferenciaReceitaOperacionalBruta end as DataReferenciaPF

, GrupoEconomicoBndes.numCnpjLiderGrupo as CNPJLiderGrupo
, GrupoEconomicoBndes.valorReceitaOperacionalBrutaGrupo as ValorAnualGrupo
, GrupoEconomicoBndes.dataReferenciaReceitaOperacionalBrutaGrupo as DataReferenciaGrupo
, substring(IndicadorReceitaGrupo.descIndicadorReceitaOperacionalBruta,1,1) as TipoRendaGrupo

, case when Pessoa.codTipoPessoa = 1 then isNull(GrupoEconomicoBndes.codPorteEmpresa, EnquadramentoCliente.codPorteEmpresa) end as PorteTipoPJ
, case when Pessoa.codTipoPessoa = 0 then isNull(GrupoEconomicoBndes.codPorteEmpresa, EnquadramentoCliente.codPorteEmpresa) end as PorteTipoPF

, case when Pessoa.codTipoPessoa = 1 then isNull(GrupoEconomicoBndes.dataReferenciaReceitaOperacionalBrutaGrupo, EnquadramentoCliente.dataReferenciaReceitaOperacionalBruta) end as AnoPortePJ
, case when Pessoa.codTipoPessoa = 0 then isNull(GrupoEconomicoBndes.dataReferenciaReceitaOperacionalBrutaGrupo, EnquadramentoCliente.dataReferenciaReceitaOperacionalBruta) end as AnoPortePF

, EnquadramentoCliente.numCrnTrc as NumRegistroTransportadorRodoviarioCarga

, Instituicao.descEmail as Email
, Instituicao.numDdd as DDDTelefone
, Instituicao.numTelefone as Telefone
, null as Ramal
, Instituicao.numDdd2 as DDDCelular
, Instituicao.numTelefone2 as Celular
, case when Instituicao.idInstituicao = 1 then 1 else Instituicao.numCooperativa end as CodigoAgencia
, Instituicao.numCnpj as CNPJCooperativa
, ObjetivoProjetoBndes.codObjetivoBndes as ObjetivoInvestimento
/*
, substring(InvestimentoBndes.numCnaeFiscal,2,100) as CodigoCNAE
, InvestimentoBndes.NUMCNPJINVESTIMENTO as CNPJInvestimento
, InvestimentoBndes.codMunicipio as CodMunicipioInvestimentoBNDES
, InvestimentoBndes.codTipoLogradouroEndereco as TipoLogradouro
, InvestimentoBndes.descLogradouroEndereco as Logradouro
, InvestimentoBndes.numEndereco as N
, InvestimentoBndes.descComplementoEndereco as Complemento
, InvestimentoBndes.descBairroEndereco as Bairro
, InvestimentoBndes.numCep as CEP
, InvestimentoBndes.codMunicipio as CodMunicipio
, InvestimentoBndes.siglaUf as UF
, InvestimentoBndes.dataSolicitacaoInstituicao as DataSolicitacaoAgFinanceiro
, InvestimentoBndes.qtdEmpregoAntes as NroEmpregadosAntes
, InvestimentoBndes.qtdEmpregoApos as NroEmpregadosApos
*/
, PropostaCredito.prazoCarenciaMeses as PrazoCarenciaMeses
, PropostaCredito.prazoOperacaoMeses - PropostaCredito.prazoCarenciaMeses as PrazoAmortizacaoMeses
, ifNull(PeriodicidadeCarencia.codPeriodicidadeBndes, 1008) as PeriodicidadeCarencia
, ifNull(PeriodicidadeAmortizacao.codPeriodicidadeBndes, 1008) as PeriodicidadeAmortizacao
  
, case
	when PropostaCredito.bolEncargosExigiveisCarencia = 1
	then true
	else false
  end
  as HaPagJuroCarencia

, PropostaCredito.PERCFINANCIADA as NivelParticipacaoBNDES
, PropostaCredito.VALORTOTALORCAMENTO as ValorTotalFinanciamento
, PropostaCredito.PERCTAXAJUROSRISCO as RemuneracaoInstituicaoFinanceira
, ProgramaBndes.IDCUSTOFINANCEIRO as PrimeiraParticipacaoCustoFinanceiro
, PropostaCredito.IDIndiceCorrecao as IDIndiceCorrecao

/*
	FinanciamentoFieldsConstants.GARANTIA_PRINCIPAL
		GarantiaComumPopulate
	não consegui encontrar o valor no db2	
*/
, null as GarantiaPrincipal

/*
	FinanciamentoFieldsConstants.PERCENTUAL_COBERTO_GARANTIA
		GarantiaComumPopulate
	não consegui encontrar o valor no db2	
*/
, null as PercentualCobertoGarantia

/*
	FinanciamentoFieldsConstants.CLASSIFICACAO_RISCO_OPERACAO
		GarantiaCreditoPequenasEmpresasPopulate
		GarantiaCreditoCaminhoneiroPopulate
		GarantiaBndesGiroPopulate
		InfoProCDDPopulate
		GarantiaComumPopulate
		InfoCreditoPequenasEmpresasPopulate
		InfoCreditoCaminhoneiroPopulate
	não consegui encontrar o valor no db2	
*/
, null as ClassificacaoRiscoOperacao

, 0.0 as AreaRecuperadaHectares
, 0.0 as AreaObraCivilM2
, 0.0 as ValorObraCivil
, 0.0 as AreaIrrigadaSerImplementada
, 0.0 as CapacidadeArmazenagem

, InfoComplementarAgricola.dataEstimadaPrimeiroCorte as DataPrimeiroCorte
, InfoComplementarAgricola.areaASerPlantada as AreaSerPlantada
, InfoComplementarAgricola.codEspecieFlorestal as EspecieFlorestalCultivada
, '' as CNPJCooperativaSingularCredito
, InfoComplementarAgricola.dataTerminoColheita as DataTerminoColheita

, ProgramaBndes.codProdutoSisbr as IDProdutoBNDES

, PrazoLimiteSolicitacaoPL.PrazoLimiteSolicitacaoPLDia as PrazoLimiteSolicitacaoPL
, PropostaCredito.idTipoEvento as MomentoFixacao

     from bnd.PrePropostaBndes                 as PropostaCredito
left join bnd.ProgramaBndes                    as ProgramaBndes            on ProgramaBndes.CODPROGRAMABNDES = PropostaCredito.CODPROGRAMABNDES
left join bnd.CondicaoOperacional              as CondicaoOperacional      on CondicaoOperacional.idCondicaoOperacional = ProgramaBndes.idCondicaoOperacional
left join bnd.EnquadramentoCliente             as EnquadramentoCliente     on EnquadramentoCliente.idEnquadramentoCliente = PropostaCredito.idEnquadramentoCliente
left join bnd.GrupoEconomicoBndes              as GrupoEconomicoBndes      on GrupoEconomicoBndes.idGrupoEconomicoBndes = EnquadramentoCliente.idGrupoEconomicoBndes
left join bnd.InvestimentoBndes                as InvestimentoBndes        on InvestimentoBndes.idInvestimentoBndes = PropostaCredito.idInvestimentoBndes
left join bnd.PessoaTemp                       as Pessoa                   on Pessoa.idPessoa = EnquadramentoCliente.idPessoa
left join bnd.InfoComplementarAgricola         as InfoComplementarAgricola on InfoComplementarAgricola.idInfoComplementarAgricola = PropostaCredito.idInfoComplementarAgricola
left join sci.viw_instituicao                  as Instituicao              on Instituicao.idInstituicao = PropostaCredito.idInstituicao and Instituicao.numPac = 0
left join bnd.PrazoLimiteSolicitacaoPL         as PrazoLimiteSolicitacaoPL on PrazoLimiteSolicitacaoPL.idPrazoLimiteSolicitacaoPL = PropostaCredito.idPrazoLimiteSolicitacaoPL
left join bnd.tipoPeriodicidade                as PeriodicidadeCarencia    on PeriodicidadeCarencia.codTipoPeriodicidade = PropostaCredito.codPeriodicidadeEncCarencia
left join bnd.tipoPeriodicidade                as PeriodicidadeAmortizacao on PeriodicidadeAmortizacao.codTipoPeriodicidade = PropostaCredito.codPeriodicidadeAmortizacao
left join bnd.objetivoProjetoBndes             as ObjetivoProjetoBndes     on ObjetivoProjetoBndes.codObjetivoProjetoBndes = InvestimentoBndes.codObjetivoProjetoBndes
left join bnd.indicadorReceitaOperacionalBruta as IndicadorReceita         on IndicadorReceita.codIndicadorReceitaOperacionalBruta = EnquadramentoCliente.codIndicadorReceitaOperacionalBruta
left join bnd.indicadorReceitaOperacionalBruta as IndicadorReceitaGrupo    on IndicadorReceitaGrupo.codIndicadorReceitaOperacionalBruta = GrupoEconomicoBndes.codIndicadorReceitaOperacionalBruta
left join bnd.informacaoAdicionalRefin         as InfoRefin                on InfoRefin.idPrePropostaBndes = PropostaCredito.idPrePropostaBndes

where PropostaCredito.numPropostaCredito = :numeroPropostaCredito