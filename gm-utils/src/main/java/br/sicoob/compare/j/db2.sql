-- LISTAR_TMP_INFO_RESULTADO_ESPERADO

select
  case when Instituicao.idInstituicao = 1 then 1 else Instituicao.numCooperativa end as NumCooperativa
, PropostaCredito.NumContratoBNDES as NumPac
, InfoResultadoEsperado.QtdUltimoExercicio as QtdUltimoExercicio
, InfoResultadoEsperado.ValorUltimoExercicio as ValorUltimoExercicio
, InfoResultadoEsperado.QtdAposProjeto as QtdAposProjeto
, InfoResultadoEsperado.ValorAposProjeto as ValorAposProjeto
, InfoResultadoEsperado.valorInvestFinancFixos as ValorInvestFinancFixo
, InfoResultadoEsperado.valorInvestFinancEquipNacionais as ValorInvestFinancEquipNacional
, InfoResultadoEsperado.valorInvestFinancSoftware as ValorInvestFinancSoftware
, InfoResultadoEsperado.valorCapitalGiroAssociado as ValorCapitalGiroAssociado
, InfoResultadoEsperado.CodTipoResultadoEsperado as CodTipoResultadoEsperado
, InfoResultadoEsperado.codUnidadeMedida as CodTipoUnidadeMedida

from bnd.PrePropostaBndes              as PropostaCredito
join sci.viw_instituicao               as Instituicao              on Instituicao.idInstituicao = PropostaCredito.idInstituicao and Instituicao.numPac = 0
join bnd.infoResultadoEsperado as InfoResultadoEsperado on InfoResultadoEsperado.idInfoComplementarProjetoInvestimento = PropostaCredito.idInfoComplementarProjetoInvestimento
where PropostaCredito.numPropostaCredito = 