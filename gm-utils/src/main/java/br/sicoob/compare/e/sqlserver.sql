--LISTAR_PROPOSTA_LEGADO_LIBERACAO_FINAME
SELECT 

	   t.valoropcred,
	   pcb.numerodapac             AS 'NroContratoBNDES'
	   -- Esse número é aleatório gerar no java.
	   ,
	   ''                          AS 'NroPedidoLiberacao',
	   (SELECT SUM(telb.ValorBeneficiario) FROM dbo.TmpEfetivaLiberacaoBNDES telb
			WHERE telb.IDTmpOpCredito = tplb.IDTmpOpCredito AND telb.IdPL = tplb.IdPL) AS 'ValorALiberar',
	   CASE
		WHEN tla.codtipolicenca = 1 AND tla.codtipolicencaconc = 1 THEN 1
		WHEN tla.codtipolicenca = 1 AND tla.codtipolicencaconc = 2 THEN 2
		WHEN tla.CodTipoLicenca = 2 AND tla.codtipolicencaconc = 3 THEN 3
		WHEN tla.CodTipoLicenca = 2 AND tla.codtipolicencaconc = 4 THEN 4
		WHEN tla.CodTipoLicenca = 3 THEN 5
	   END                         AS 'TipoDocumento',
	   tla.descnumlicenca          AS 'NroDocumento',
	   tla.dataemislicenca         AS 'DataEmissao',
	   tla.datafimlicenca          AS 'DataValidade',
	   tla.descorgaoconcedente     AS 'OrgaoConcedente',
	   tla.siglauforgaoconc        AS 'UFOrgaoConcedente',
	   tla.descfinalidadeconc      AS 'FinalidadeLegal',
	   tplb.QtdArquivoAnexo        AS 'QtdArquivoAnexoTmpPedidoLiberacaoBndes',
	   pcb.CodSistOperacional      AS 'CodSistOperacional',
	   tplb.CodFinalidadeLiberacao AS 'CodFinalidadeLiberacao'
FROM   dbo.propostacredito p WITH (NOLOCK)
	   INNER JOIN dbo.tmpopcredito t WITH (NOLOCK) ON p.idtmpopcredito = t.idtmpopcredito AND p.numcooperativa = t.numcooperativa AND p.numpac = t.numpac
	   INNER JOIN dbo.propostacreditobndes pcb WITH (NOLOCK) ON pcb.idtmpopcredito = t.idtmpopcredito AND pcb.numcooperativa = t.numcooperativa AND pcb.numpac = t.numpac
	   INNER JOIN dbo.modalidadeopcredito moc WITH (NOLOCK) ON moc.idmodalidadeproduto = t.idmodalidadeproduto AND moc.idproduto = t.idproduto
	   INNER JOIN dbo.TmpPedidoLiberacaoBNDES tplb WITH (NOLOCK) ON p.IDTmpOpCredito = tplb.IDTmpOpCredito AND p.NumCooperativa = tplb.NumCooperativa AND p.NumPac = tplb.NumPac
	   LEFT JOIN dbo.tmplicencaamb tla WITH (NOLOCK) ON tla.idtmpopcredito = t.idtmpopcredito AND tla.numcooperativa = t.numcooperativa AND tla.numpac = t.numpac
WHERE
    pcb.bolPlataformaAPA = 1
AND moc.codcarteira = 12
AND t.boloperacaopassiva = 0
AND p.descnumpropostacred = 