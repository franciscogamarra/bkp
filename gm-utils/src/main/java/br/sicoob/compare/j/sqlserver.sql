-- LISTAR_TMP_INFO_RESULTADO_ESPERADO

select
  d.NumCooperativa
, d.NumPac
, d.QtdUltimoExercicio
, d.ValorUltimoExercicio
, d.QtdAposProjeto
, d.ValorAposProjeto
, d.ValorInvestFinancFixo
, d.ValorInvestFinancEquipNacional
, d.ValorInvestFinancSoftware
, d.ValorCapitalGiroAssociado
, d.CodTipoResultadoEsperado
, d.CodTipoUnidadeMedida
from dbo.PropostaCredito a with(nolock)
join dbo.TmpOpCredito b with(nolock)
  on a.IDTmpOpCredito = b.IDTmpOpCredito
 and a.NumCooperativa = b.NumCooperativa
 and a.NumPac = b.NumPac
join dbo.PropostaCreditoBNDES c with(nolock)
  on b.IDTmpOpCredito = c.IDTmpOpCredito
 and b.NumCooperativa = c.NumCooperativa
 and b.NumPac = c.NumPac
join dbo.TmpInfoResultadoEsperado d with(nolock)
  on c.IDTmpOpCredito = d.IDTmpOpCredito
 and c.NumCooperativa = d.NumCooperativa
 and c.NumPac = d.NumPac
where a.DescNumPropostaCred = 