--LISTAR_PROPOSTA_LEGADO_FINANCIAMENTO_FINAME
SELECT
  --pc.IDTmpOpCredito as IDTmpOpCredito
--, 
  c.NumCNPJ as CNPJAgenteFinanceiro
, t.NumContratoCredito as NumContratoCredito
, cov.CodCondOperVigPAC as CondicaoOperacional
, pcb.QtdArquivoAnexo as QuantidadeAnexos
, pb.CodProdutoBNDESOnline as Produto
, pb.SiglaPrograma as ProgramaFinanciamento
,
CASE
WHEN pcb.CodSistOperacional in(1,4) THEN 'S'
WHEN pcb.CodSistOperacional in(3,5,6) THEN 'C'
END as Sistematica
, CAST(CAST(YEAR(pc.DataProposta) AS VARCHAR) + '-01-01' AS DATE) as AnoProposta
, pc.DescNumPropostaCred as NumeroProposta
, '3' as TipoEvento
, CASE WHEN ps.CodPF_PJ = 1 THEN ps.NumCGC_CPF END as CNPJ
, CASE WHEN ps.CodPF_PJ = 0 THEN ps.NumCGC_CPF END as CPF
,
CASE
WHEN epj.CodCaracterizacaoCapSocial = 5 THEN '4'
WHEN epj.CodCaracterizacaoCapSocial = 6 THEN '5'
WHEN epj.CodCaracterizacaoCapSocial = 7 THEN '2'
END as CaracterizacaoCapitalSocial
, epj.BolEmpresarioIndividual as EmpresarioIndividual
, t.ValorOpCred as ValorParticipacao
, epj.ValorFaturamento as ValorAnualPJ
, epf.ValorFaturamento as ValorAnualPF
, epj.BolAtivoPrivado as FundoPrivateEquity
,
CASE
WHEN epj.CodIndicadorFaturamento = 1 THEN 'E'
WHEN epj.CodIndicadorFaturamento = 2 THEN 'P'
END as TipoRendaPJ
,
CASE
WHEN epf.CodIndicadorFaturamento = 1 THEN 'E'
WHEN epf.CodIndicadorFaturamento = 2 THEN 'P'
END as TipoRendaPF

, cast(epj.DataRefFaturamento as date) as DataReferenciaPJ
, cast(epf.DataRefFaturamento as date) as DataReferenciaPF
, epj.NumCNPJLiderGrupo as CNPJLiderGrupo
, epj.ValorFaturGrupo as ValorAnualGrupo
, epj.DataRefFaturGrupo as DataReferenciaGrupo
,
CASE WHEN epj.CodIndicadorFaturGrupo = 1 THEN 'E'
WHEN epj.CodIndicadorFaturGrupo = 2 THEN 'P'
END as TipoRendaGrupo
,
CASE
WHEN epj.CodPorteEmpresaBndes = 8 THEN '3'
WHEN epj.CodPorteEmpresaBndes = 9 THEN '4'
WHEN epj.CodPorteEmpresaBndes = 2 THEN '1'
WHEN epj.CodPorteEmpresaBndes = 10 THEN '6'
WHEN epj.CodPorteEmpresaBndes = 3 THEN '2'
END as PorteTipoPJ
,
CASE
WHEN epf.CodPorteEmpresaBndes = 8 THEN '3'
WHEN epf.CodPorteEmpresaBndes = 9 THEN '4'
WHEN epf.CodPorteEmpresaBndes = 2 THEN '1'
WHEN epf.CodPorteEmpresaBndes = 10 THEN '6'
WHEN epf.CodPorteEmpresaBndes = 3 THEN '2'
END as PorteTipoPF

, cast(epj.DataRefFaturamento as date) as AnoPortePJ
, cast(epf.DataRefFaturamento as date) as AnoPortePF
,
CASE
WHEN epf.NumRegistroTransportadorRodoviarioCarga IS NULL THEN epj.NumRegistroTransportadorRodoviarioCarga
ELSE epf.NumRegistroTransportadorRodoviarioCarga
END as NumRegistroTransportadorRodoviarioCarga
, ps.DescEndInternet as Email
, ep.NumDDD as DDDTelefone
, ep.NumTelefone as Telefone
, ep.NumRamal as Ramal
, ps.NumDDD as DDDCelular
, ps.NumCelFax as Celular
, pc.NumCooperativaCredDeb as CodigoAgencia
, SUBSTRING(tib.CodCNAE,2,7) as CodigoCNAE
, tib.NumCNPJ as CNPJInvestimento
, c2.NumCNPJ as CNPJCooperativa
, tib.IdMunicipioRecor as CodMunicipioInvestimentoBNDES
, tib.CodTipoLogradouroBNDES as TipoLogradouro
, tib.DescLogradouroEnd as Logradouro
, tib.NumEnd as N
, tib.DescCompEnd as Complemento
, tib.NomeBairroEnd as Bairro
, tib.NumCEP as CEP
, tib.IdMunicipioRecor as CodMunicipio
, tib.SiglaUF as UF
,
CASE
WHEN pcb.CodSistOperacional IN (1, 3) THEN opb.CodObjetivoProjBNDESPac
WHEN pcb.CodSistOperacional IN (4, 6) THEN opb.CodObjetivoProjBNDESInternet
WHEN pcb.CodSistOperacional = 5 THEN opb.CodObjetivoProjBNDESFro
END as ObjetivoInvestimento
, cast(pcb.DATAPROTOCOLOCLIENTECOOP as date) as DataSolicitacaoAgFinanceiro
, tib.QtdEmpregoAntes as NroEmpregadosAntes
, tib.QtdEmpregoApos as NroEmpregadosApos
, pcb.PrazoCarencia as PrazoCarenciaMeses
, (pcb.PrazoOperacoesMeses - pcb.PrazoCarencia) as PrazoAmortizacaoMeses
,
CASE
WHEN pcb.TipoEncargoCarencia = 15 THEN '1001'
WHEN pcb.TipoEncargoCarencia = 13 THEN '1003'
WHEN pcb.TipoEncargoCarencia = 12 THEN '1005'
WHEN pcb.TipoEncargoCarencia = 11 THEN '1006'
ELSE '1008'
END as PeriodicidadeCarencia
,
CASE
WHEN pcb.TipoEncargoAmortizacao = 15 THEN '1001'
WHEN pcb.TipoEncargoAmortizacao = 13 THEN '1003'
WHEN pcb.TipoEncargoAmortizacao = 12 THEN '1005'
WHEN pcb.TipoEncargoAmortizacao = 11 THEN '1006'
ELSE '1008'
END as PeriodicidadeAmortizacao
, pcb.BolEncExigiveisCarencia as HaPagJuroCarencia
, pcb.PERCPARTICIPACAOBNDES as NivelParticipacaoBNDES
, t.ValorOpCred as ValorTotalFinanciamento
, t.PercTaxaJurosRisco as RemuneracaoInstituicaoFinanceira
, pb.IdCustoFinanceiro as PrimeiraParticipacaoCustoFinanceiro
, t.IDIndiceCorrecao as IDIndiceCorrecao
,
CASE
WHEN tgpo.CodTipoGarantiaPrincipal = 10 THEN '15'
WHEN tgpo.CodTipoGarantiaPrincipal = 8 THEN '20'
WHEN tgpo.CodTipoGarantiaPrincipal = 4 THEN '12'
WHEN tgpo.CodTipoGarantiaPrincipal = 9 THEN '13'
WHEN tgpo.CodTipoGarantiaPrincipal = 13 THEN '8'
WHEN tgpo.CodTipoGarantiaPrincipal = 5 THEN '3'
WHEN tgpo.CodTipoGarantiaPrincipal = 3 THEN '15'
WHEN tgpo.CodTipoGarantiaPrincipal = 14 THEN '16'
WHEN tgpo.CodTipoGarantiaPrincipal in(6,16) THEN '25'
WHEN tgpo.CodTipoGarantiaPrincipal = 0 THEN ''
END as GarantiaPrincipal
,
CASE
WHEN tgpo.CodTipoGarantiaPrincipal = 0 THEN 0
WHEN tgpo.CodTipoGarantiaPrincipal = 8 THEN 100
WHEN tgpo.CodTipoGarantiaPrincipal = 10 THEN 100
ELSE CONVERT(FLOAT, SUBSTRING(dbo.fn_BuscaGarantiaPrincipal(t.IDTmpOpCredito, t.NumCooperativa, t.NumPac),3,5)) / 100
END as PercentualCobertoGarantia
, pc.IDNivelRisco as ClassificacaoRiscoOperacao
--, pcb.QtdAnimais as QtdAnimais
--, tpga.TamAreaIntegrada as AreaSerIntegrada
--, tpga.ValorAquisicaoBovinos as ValorAquisicaoBovinos
--, tpga.ValorILPF as ValorILPF
--, tpga.ValorILP as ValorILP
--, tpga.ValorOutraTecnologia as ValorOutrasTecnologias
--, tpga.TamAreaPlantioDireto as AreaPlantioDiretoHectares
--, tpga.ValorRecuperacao as ValorRecuperacao
, tpga.TamAreaRecuperada as AreaRecuperadaHectares
, tpga.TamAreaObraCivil as AreaObraCivilM2
, tpga.ValorObraCivil as ValorObraCivil
, tpga.TamAreaIrrigada as AreaIrrigadaSerImplementada
, tpga.TamCapacidadeArmazenagem as CapacidadeArmazenagem
, cast(pcb.DataPrimeiroCorte as date) as DataPrimeiroCorte
, pcb.QtdAreaPlantada as AreaSerPlantada
, pcb.CodEspecieFlorestal as EspecieFlorestalCultivada
, '' as CNPJCooperativaSingularCredito
--, pcb.BolBonusAdimpAdic as TemBonusAdimplencia
, cast(pcb.DataTerminoColheita as date) as DataTerminoColheita
, t.IDProduto as IDProdutoBNDES
, pls.PrazoLimiteSolicitacaoPLDia as PrazoLimiteSolicitacaoPL
, pcb.IdTipoEvento as MomentoFixacao
FROM dbo.PropostaCredito pc WITH (NOLOCK)
INNER JOIN dbo.TmpOpCredito t WITH (NOLOCK) ON pc.IDTmpOpCredito = t.IDTmpOpCredito AND pc.NumCooperativa = t.NumCooperativa AND pc.NumPac = t.NumPac
INNER JOIN dbo.PropostaCreditoBNDES pcb WITH (NOLOCK) ON pcb.IDTmpOpCredito = t.IDTmpOpCredito AND pcb.NumCooperativa = t.NumCooperativa AND pcb.NumPac = t.NumPac
INNER JOIN dbo.Pessoa ps WITH (NOLOCK) ON ps.NumPessoa = t.NumClienteLibBNDES
INNER JOIN dbo.Cooperativa c WITH (NOLOCK) ON pc.NumCooperativa = c.NumCooperativa AND pc.NumPac = c.NumPac
INNER JOIN dbo.ProgramasBNDES pb WITH (NOLOCK) ON pcb.CodPrograma = pb.CodPrograma
INNER JOIN dbo.CondOperacionalVigBNDES cov WITH (NOLOCK) ON cov.CodCondOperacional = pcb.CodCondOperacional
INNER JOIN dbo.ObjetivoProjetoBNDES opb WITH (NOLOCK) ON opb.IdObjetivoProjetoBNDES = pcb.IdObjetivoProjetoBNDES
INNER JOIN dbo.ModalidadeOpCredito moc WITH (NOLOCK) ON moc.IDModalidadeProduto = t.IDModalidadeProduto AND moc.IDProduto = t.IDProduto
LEFT JOIN dbo.TmpGarantiaPrincipalOpCred tgpo WITH (NOLOCK) ON tgpo.IDTmpOpCredito = t.IDTmpOpCredito
LEFT JOIN dbo.Cooperativa c2 WITH (NOLOCK) ON pc.NumCooperativaCredDeb = c2.NumCooperativa AND c2.NumPac = pc.NumPacCredDeb AND c2.NumPac = 0
LEFT JOIN dbo.EnquadramentoPJBndes epj WITH (NOLOCK) ON epj.NumPessoa = ps.NumPessoa
LEFT JOIN dbo.EnquadramentoPFBndes epf WITH (NOLOCK) ON epf.NumPessoa = ps.NumPessoa
LEFT JOIN dbo.EnderecoPessoa ep WITH (NOLOCK) ON ep.NumPessoa = ps.NumPessoa AND ep.BolEnvioCorrespondencia = 1
LEFT JOIN dbo.TmpInvestimentoBNDES tib WITH (NOLOCK) ON tib.IDTmpOpCredito = t.IDTmpOpCredito
LEFT JOIN dbo.TmpDadosPGA tpga WITH (NOLOCK) ON tpga.IDTmpOpCredito = t.IDTmpOpCredito
LEFT JOIN dbo.PrazoLimiteSolicitacaoPL pls WITH (NOLOCK) ON pcb.IdPrazoLimiteSolicitacaoPL = pls.IdPrazoLimiteSolicitacaoPL
WHERE pcb.bolPlataformaAPA = 1
AND moc.CodCarteira = 12
AND t.BolOperacaoPassiva = 0
and pc.DescNumPropostaCred = 