--LISTAR_PROPOSTA_LEGADO_LIBERACAO
SELECT
	CASE
		WHEN pcb.NumeroContratoBNDES IS NOT NULL THEN pcb.NumeroContratoBNDES
		WHEN pcb.NumeroDaFro IS NOT NULL THEN pcb.NumeroDaFro
		ELSE pcb.NumeroDaPac
	 END AS 'NroContratoBNDES'
	-- Esse número é aleatório gerar no java.
	,'' AS 'NroPedidoLiberacao'
	,(SELECT SUM(telb.ValorBeneficiario) FROM dbo.TmpEfetivaLiberacaoBNDES telb
		 WHERE telb.IDTmpOpCredito = tplb.IDTmpOpCredito AND telb.IdPL = tplb.IdPL) AS 'ValorALiberar'
	,CASE
		WHEN tla.codtipolicenca = 1 AND tla.codtipolicencaconc = 1 THEN 1
		WHEN tla.codtipolicenca = 1 AND tla.codtipolicencaconc = 2 THEN 2
		WHEN tla.CodTipoLicenca = 2 AND tla.codtipolicencaconc = 3 THEN 3
		WHEN tla.CodTipoLicenca = 2 AND tla.codtipolicencaconc = 4 THEN 4
		WHEN tla.CodTipoLicenca = 3 THEN 5
	 END AS 'TipoDocumento'
	,tla.DescNumLicenca AS 'NroDocumento'
	,tla.DataEmisLicenca  AS 'DataEmissao'
	,tla.DataFimLicenca AS 'DataValidade'
	,tla.DescOrgaoConcedente AS 'OrgaoConcedente'
	,tla.SiglaUFOrgaoConc AS 'UFOrgaoConcedente'
	,tla.DescFinalidadeConc AS 'FinalidadeLegal'
	,tplb.QtdArquivoAnexo AS 'QtdArquivoAnexoTmpPedidoLiberacaoBndes'
	,pcb.CodSistOperacional      AS 'CodSistOperacional'
	,tplb.CodFinalidadeLiberacao AS 'CodFinalidadeLiberacao'
FROM dbo.PropostaCredito p WITH (NOLOCK)
	INNER JOIN dbo.TmpOpCredito t WITH (NOLOCK) ON p.IDTmpOpCredito = t.IDTmpOpCredito AND p.NumCooperativa = t.NumCooperativa AND p.NumPac = t.NumPac
	INNER JOIN dbo.PropostaCreditoBNDES pcb WITH (NOLOCK) ON pcb.IDTmpOpCredito = t.IDTmpOpCredito AND pcb.NumCooperativa = t.NumCooperativa AND pcb.NumPac = t.NumPac
	INNER JOIN dbo.ModalidadeOpCredito moc WITH (NOLOCK) ON moc.IDModalidadeProduto = t.IDModalidadeProduto AND moc.IDProduto = t.IDProduto
	INNER JOIN dbo.TmpPedidoLiberacaoBNDES tplb WITH (NOLOCK) ON p.IDTmpOpCredito = tplb.IDTmpOpCredito AND p.NumCooperativa = tplb.NumCooperativa AND p.NumPac = tplb.NumPac
	LEFT JOIN dbo.TmpLicencaAmb tla WITH (NOLOCK) ON tla.IDTmpOpCredito = t.IDTmpOpCredito AND tla.NumCooperativa = t.NumCooperativa AND tla.NumPac = t.NumPac
WHERE pcb.bolPlataformaAPA = 1
  AND moc.CodCarteira = 12
  AND t.BolOperacaoPassiva = 0
  AND p.DescNumPropostaCred = 
