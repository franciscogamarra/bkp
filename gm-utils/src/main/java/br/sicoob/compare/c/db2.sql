--LISTAR_PROPOSTA_LEGADO_CONTRATACAO
select distinct
  PropostaCredito.NumContratoBNDES as NroContratoBNDES
, PropostaCredito.DATACONTRATACAO as DataContratacao
, InfoRefin.numSicorRefin as NroReferenciaSICOR
, InfoRefin.NumContratoRefin as NroContratoAgFinanceiro
, null as DataPrimeiraAmortizacao
, null as NroDiaUtilVenc

, case
	when PropostaCredito.bolEncargosExigiveisCarencia = 1
	then true
	else false
  end
  as HaPagJuroCarencia

, PJ.numCpfCnpj as CNPJ
, PF.numCpfCnpj as CPF
, null as EmpresarioIndividual

, case when PJ.idPessoa is null then null else EnquadramentoCliente.valorReceitaOperacionalBruta end as ValorAnualPJ
, case when PF.idPessoa is null then null else EnquadramentoCliente.valorReceitaOperacionalBruta end as ValorAnualPF

, null as FundoPrivateEquity

, case when PJ.idPessoa is null then null when EnquadramentoCliente.codIndicadorReceitaOperacionalBruta = 1 then 'E' else 'P' end as TipoRendaPJ
, case when PF.idPessoa is null then null when EnquadramentoCliente.codIndicadorReceitaOperacionalBruta = 1 then 'E' else 'P' end as TipoRendaPF
, case when PJ.idPessoa is null then null else EnquadramentoCliente.dataReferenciaReceitaOperacionalBruta end as DataReferenciaPJ
, case when PF.idPessoa is null then null else EnquadramentoCliente.dataReferenciaReceitaOperacionalBruta end as DataReferenciaPF

, null as CNPJLiderGrupo
, null as ValorAnualGrupo
, null as DataReferenciaGrupo
, null as TipoRendaGrupo
, PropostaCredito.prazoCarenciaMeses as PrazoCarenciaMeses
, null as PrazoAmortizacaoMeses
, PeriodicidadeCarencia.codBndes as PeriodicidadeCarencia
, PeriodicidadeAmortizacao.codBndes as PeriodicidadeAmortizacao
, null as GarantiaPrincipal
, null as PercentualCobertoGarantia
, null as ClassificacaoRiscoOperacao

, case
	when PropostaCredito.bolGarantiaFgi = 1
	then true
	else false
  end
  as PossuiGarantiaFGI

, null as PercTaxaRiscoFGI
, null as TipoFGI
, null as CodigoDAP
, null as DataDAP
, null as PisPasepNisNitDAP
, null as GrauInstrucaoDAP
, case when Instituicao.idInstituicao = 1 then 1 else Instituicao.numCooperativa end as CodigoAgencia
, null as ValorTACDAP

, InfoComplementarAgricola.dataEstimadaPrimeiroCorte as DataPrimeiroCorte
, InfoComplementarAgricola.areaASerPlantada as AreaSerPlantada
, InfoComplementarAgricola.codEspecieFlorestal as EspecieFlorestalCultivada
, InfoComplementarAgricola.dataTerminoColheita as DataTerminoColheita

, ProgramaBndes.codProdutoSisbr as IDProduto
, null as DESCSTN

     from bnd.PrePropostaBndes         as PropostaCredito
left join bnd.ProgramaBndes            as ProgramaBndes            on ProgramaBndes.CODPROGRAMABNDES = PropostaCredito.CODPROGRAMABNDES
left join bnd.EnquadramentoCliente     as EnquadramentoCliente     on EnquadramentoCliente.idEnquadramentoCliente = PropostaCredito.idEnquadramentoCliente
left join bnd.PessoaTemp               as PF                       on PF.idPessoa = EnquadramentoCliente.idPessoa and PF.codTipoPessoa = 0
left join bnd.PessoaTemp               as PJ                       on PJ.idPessoa = EnquadramentoCliente.idPessoa and PJ.codTipoPessoa = 1
left join bnd.InfoComplementarAgricola as InfoComplementarAgricola on InfoComplementarAgricola.idInfoComplementarAgricola = PropostaCredito.idInfoComplementarAgricola
left join sci.viw_instituicao          as Instituicao              on Instituicao.idInstituicao = PropostaCredito.idInstituicao and Instituicao.numPac = 0
left join bnd.tipoPeriodicidade        as PeriodicidadeCarencia    on (PeriodicidadeCarencia.codTipoPeriodicidade = PropostaCredito.codPeriodicidadeEncCarencia) or (PropostaCredito.codPeriodicidadeEncCarencia is null and PeriodicidadeCarencia.codTipoPeriodicidade = 99)
left join bnd.tipoPeriodicidade        as PeriodicidadeAmortizacao on (PeriodicidadeAmortizacao.codTipoPeriodicidade = PropostaCredito.codPeriodicidadeAmortizacao) or (PropostaCredito.codPeriodicidadeAmortizacao is null and PeriodicidadeAmortizacao.codTipoPeriodicidade = 99)
left join bnd.informacaoAdicionalRefin as InfoRefin                on InfoRefin.idPrePropostaBndes = PropostaCredito.idPrePropostaBndes

where PropostaCredito.numPropostaCredito = 