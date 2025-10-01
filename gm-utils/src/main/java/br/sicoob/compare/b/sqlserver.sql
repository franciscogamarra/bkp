--LISTAR_EQUIPAMENTO_POR_PROPOSTA_FINAME_FINANCIAMENTO
SELECT distinct
  eqp.IDSeqEquipamento
-- , pcb.IDTmpOpCredito
, pcb.ValorCapitalGiro
, pcb.ValorServicoAssociado
, pcb.ValorSeguro
, pcb.CodSistOperacional
, eqp.QtdEquipamento
, eqp.ValorUnitario
, CASE
    WHEN eqp.BolEquipamentoUsado = 0 THEN eqp.CodProdBNDES
    WHEN eqp.BolEquipamentoImportado = 0 THEN eqp.CodProdBNDES
  END AS 'CodProdBNDES'
, eqp.ValorFinanciadoTotal  AS 'ValorFinanciadoTotal'
, CASE
    WHEN eqp.CodEventoEquipamento = 1 THEN '0'
    WHEN eqp.CodEventoEquipamento = 2 THEN '1'
  END AS 'CodEventoEquipamento'
, eqp.BolEquipamentoUsado
, eqp.BolEquipamentoImportado
, eqp.DescCompEquipamento

FROM dbo.PropostaCredito pc WITH (NOLOCK)
INNER JOIN dbo.TmpOpCredito t WITH (NOLOCK) ON pc.IDTmpOpCredito = t.IDTmpOpCredito AND pc.NumCooperativa = t.NumCooperativa AND pc.NumPac = t.NumPac
INNER JOIN dbo.PropostaCreditoBNDES pcb WITH (NOLOCK) ON pcb.IDTmpOpCredito = t.IDTmpOpCredito AND pcb.NumCooperativa = t.NumCooperativa AND pcb.NumPac = t.NumPac
LEFT JOIN dbo.TmpEquipamento eqp ON eqp.IDTmpOpCredito = pcb.IDTmpOpCredito

where pc.DescNumPropostaCred = :numeroPropostaCredito
  and eqp.IDSeqEquipamento = 1