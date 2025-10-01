--LISTAR_CONTRATO_LEGADO_LIBERACAO_SUBSEQUENTE
SELECT
t.valoropcred,
tplb.NumeroContratoBNDES   AS 'NroContratoBNDES'
-- Esse número é aleatório gerar no java.
,
''                          AS 'NroPedidoLiberacao',
(SELECT SUM(telb.ValorBeneficiario) FROM dbo.TmpEfetivaLiberacaoBNDES telb
		WHERE telb.IDTmpOpCredito = tplb.IDTmpOpCredito AND telb.IdPL = tplb.IdPL) AS 'ValorALiberar',
CASE
	WHEN la.codtipolicenca = 1 AND la.codtipolicencaconc = 1 THEN 1
	WHEN la.codtipolicenca = 1 AND la.codtipolicencaconc = 2 THEN 2
	WHEN la.CodTipoLicenca = 2 AND la.codtipolicencaconc = 3 THEN 3
	WHEN la.CodTipoLicenca = 2 AND la.codtipolicencaconc = 4 THEN 4
	WHEN la.CodTipoLicenca = 3 THEN 5
END                         AS 'TipoDocumento',
la.descnumlicenca          AS 'NroDocumento',
la.dataemislicenca         AS 'DataEmissao',
la.datafimlicenca          AS 'DataValidade',
la.descorgaoconcedente     AS 'OrgaoConcedente',
la.siglauforgaoconc        AS 'UFOrgaoConcedente',
la.descfinalidadeconc      AS 'FinalidadeLegal',
tplb.QtdArquivoAnexo        AS 'QtdArquivoAnexoTmpPedidoLiberacaoBndes',
ccb.CodSistOperacional      AS 'CodSistOperacional',
tplb.CodFinalidadeLiberacao AS 'CodFinalidadeLiberacao'
FROM   dbo.TmpOpCredito t WITH (NOLOCK)
INNER JOIN dbo.ContratoCredito cc WITH (NOLOCK) ON cc.NumContratoCredito = t.NumContratoCredito AND cc.NumCliente = t.NumClienteLibBNDES AND cc.IDModalidadeProduto = t.IDModalidadeProduto AND cc.IDProduto = t.IDProduto AND cc.BolOperacaoPassiva = 0
INNER JOIN dbo.ContratoCreditoBNDES ccb WITH (NOLOCK) ON ccb.NumContratoCredito = t.NumContratoCredito AND ccb.NumCliente = t.NumClienteLibBNDES AND ccb.IDModalidadeProduto = t.IDModalidadeProduto AND ccb.IDProduto = t.IDProduto
INNER JOIN dbo.ModalidadeOpCredito moc WITH (NOLOCK) ON moc.IDModalidadeProduto = t.IDModalidadeProduto AND moc.IDProduto = t.IDProduto
INNER JOIN dbo.TmpPedidoLiberacaoBNDES tplb WITH (NOLOCK) ON t.IDTmpOpCredito = tplb.IDTmpOpCredito AND t.NumCooperativa = tplb.NumCooperativa AND t.NumPac = tplb.NumPac
LEFT JOIN dbo.LicencaAmb la WITH (NOLOCK) ON la.NumContratoCredito = cc.NumContratoCredito AND la.NumCliente = cc.NumCliente
WHERE moc.CodCarteira = 12
  AND t.BolOperacaoPassiva = 0
  --AND tplb.IdPL = 1 --<bancoob:valor valor="${idPL}" />
  AND t.NumContratoCredito =

  