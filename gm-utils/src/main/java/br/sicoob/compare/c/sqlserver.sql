--LISTAR_PROPOSTA_LEGADO_CONTRATACAO
SELECT
CASE
    WHEN pcb.NumeroContratoBNDES IS NOT NULL THEN pcb.NumeroContratoBNDES
    WHEN pcb.NumeroDaFro IS NOT NULL THEN pcb.NumeroDaFro
    ELSE pcb.NumeroDaPac
END AS 'NroContratoBNDES'
,pcb.DataAssinatura AS 'DataContratacao'
,CASE WHEN p.NumContratoRecor > 0 THEN p.NumContratoRecor ELSE NULL
END AS 'NroReferenciaSICOR'
    ,t.NumContratoCredito AS 'NroContratoAgFinanceiro'
    ,( -- ## Subquery para montar a data de amortização. ## --
      select min(tpg.DataVencParcela)
      from dbo.PropostaCredito p2 WITH (NOLOCK) inner join dbo.TmpOpCredito t2 WITH (NOLOCK) on p2.IDTmpOpCredito = t2.IDTmpOpCredito and p2.NumCooperativa = t2.NumCooperativa and p2.NumPac = t2.NumPac
                                  inner join dbo.TmpPlanoPagto tpg WITH (NOLOCK) on tpg.IDTmpOpCredito = t2.IDTmpOpCredito
                                  inner join dbo.ModalidadeOpCredito moc2 WITH (NOLOCK) ON moc2.IDModalidadeProduto = t2.IDModalidadeProduto AND moc2.IDProduto = t2.IDProduto
      group by p2.DescNumPropostaCred, p2.NumDiaUtilVenc, tpg.TipoParcela, moc2.CodCarteira
      having p2.DescNumPropostaCred = p.DescNumPropostaCred and tpg.TipoParcela = 2 and moc2.CodCarteira = 12
      -- ##                  -- ## ## --                ## --
      ) AS 'DataPrimeiraAmortizacao'
  ,p.NumDiaUtilVenc AS 'NroDiaUtilVenc'
  ,pcb.BolEncExigiveisCarencia as 'HaPagJuroCarencia'
-- BENEFICIÁRIA
  ,CASE WHEN ps.CodPF_PJ = 1 THEN ps.NumCGC_CPF END as 'CNPJ'
  ,CASE WHEN ps.CodPF_PJ = 0 THEN ps.NumCGC_CPF END as 'CPF'
        ,epj.BolEmpresarioIndividual as 'EmpresarioIndividual'
        --ROB
  ,epj.ValorFaturamento as 'ValorAnualPJ'
,epf.ValorFaturamento as 'ValorAnualPF'
,epj.BolAtivoPrivado as 'FundoPrivateEquity'
,CASE
  WHEN epj.CodIndicadorFaturamento = 1 THEN 'E'
  WHEN epj.CodIndicadorFaturamento = 2 THEN 'P'
    END as 'TipoRendaPJ'
  ,CASE
  WHEN epf.CodIndicadorFaturamento = 1 THEN 'E'
  WHEN epf.CodIndicadorFaturamento = 2 THEN 'P'
    END as 'TipoRendaPF'

, cast(epj.DataRefFaturamento as date) as DataReferenciaPJ
, cast(epf.DataRefFaturamento as date) as DataReferenciaPF

,epj.NumCNPJLiderGrupo as 'CNPJLiderGrupo'
  ,epj.ValorFaturGrupo as 'ValorAnualGrupo'
  ,epj.DataRefFaturGrupo as 'DataReferenciaGrupo'
  ,CASE
      WHEN epj.CodIndicadorFaturGrupo = 1 THEN 'E'
      WHEN epj.CodIndicadorFaturGrupo = 2 THEN 'P'
      END as 'TipoRendaGrupo'
    -- CONDIÇÃO OPERAÇÃO
      ,pcb.PrazoCarencia as 'PrazoCarenciaMeses'
      ,(pcb.PrazoOperacoesMeses - pcb.PrazoCarencia) as 'PrazoAmortizacaoMeses'
      ,CASE
          WHEN pcb.TipoEncargoCarencia = 15 THEN '1001'
          WHEN pcb.TipoEncargoCarencia = 13 THEN '1003'
          WHEN pcb.TipoEncargoCarencia = 12 THEN '1005'
          WHEN pcb.TipoEncargoCarencia = 11 THEN '1006'
          ELSE '1008'
        END as 'PeriodicidadeCarencia'
        ,CASE
          WHEN pcb.TipoEncargoAmortizacao = 15 THEN '1001'
          WHEN pcb.TipoEncargoAmortizacao = 13 THEN '1003'
          WHEN pcb.TipoEncargoAmortizacao = 12 THEN '1005'
          WHEN pcb.TipoEncargoAmortizacao = 11 THEN '1006'
          ELSE '1008'
        END as 'PeriodicidadeAmortizacao'
        -- GARANTIA
      ,CASE
          WHEN tgpo.CodTipoGarantiaPrincipal = 10  THEN '15'
          WHEN tgpo.CodTipoGarantiaPrincipal = 8  THEN '20'
          WHEN tgpo.CodTipoGarantiaPrincipal = 4  THEN '12'
          WHEN tgpo.CodTipoGarantiaPrincipal = 9  THEN '13'
          WHEN tgpo.CodTipoGarantiaPrincipal = 13 THEN '8'
          WHEN tgpo.CodTipoGarantiaPrincipal = 5  THEN '3'
          WHEN tgpo.CodTipoGarantiaPrincipal = 3  THEN '15'
          WHEN tgpo.CodTipoGarantiaPrincipal = 14 THEN '16'
          WHEN tgpo.CodTipoGarantiaPrincipal in(6,16)  THEN '25'
          WHEN tgpo.CodTipoGarantiaPrincipal = 0  THEN '99'
      END as 'GarantiaPrincipal'
      ,CASE
          WHEN tgpo.CodTipoGarantiaPrincipal = 0 THEN 0
          WHEN tgpo.CodTipoGarantiaPrincipal = 8 THEN 100
          WHEN tgpo.CodTipoGarantiaPrincipal = 10 THEN 100
          ELSE CONVERT(FLOAT, SUBSTRING(dbo.fn_BuscaGarantiaPrincipal(t.IDTmpOpCredito, t.NumCooperativa, t.NumPac),3,5)) / 100
      END as 'PercentualCobertoGarantia'
      ,p.IDNivelRisco as 'ClassificacaoRiscoOperacao'
      ,pcb.BolGarantiaFGI as 'PossuiGarantiaFGI'
      ,pcb.PercTaxaRiscoFGI as 'PercTaxaRiscoFGI'
      ,( -- ## Subquery buscar TipoFGI. ## --
  select
  CASE
  WHEN tgoc.CodTipoGarFinameBNDES = 16  THEN '1'
  WHEN tgoc.CodTipoGarFinameBNDES = 17  THEN '2'
  WHEN tgoc.CodTipoGarFinameBNDES = 18  THEN '3'
  WHEN tgoc.CodTipoGarFinameBNDES = 19  THEN '4'
END
      from dbo.TmpGarantiaOpCred tgoc
      where  tgoc.IDTmpOpCredito = p.IDTmpOpCredito
  and  tgoc.CodTipoGarFinameBNDES IN (16, 17, 18, 19)
-- ##                  -- ## ## --                ## --
) as 'TipoFGI'
  --INFORMAÇÕES DA DAP
  ,tp.NumeroDAP as 'CodigoDAP'
,tp.DataVenctoDAP as 'DataDAP'
,tb.NumPISPASEP as 'PisPasepNisNitDAP'
,g.CodGrauInstrucaoBNDES as 'GrauInstrucaoDAP'
,p.NumCooperativaCredDeb  as 'CodigoAgencia'
,'' as 'ValorTACDAP'
      --INFORMACOES COMPLEMENTARES
      --,pcb.QtdAnimais as 'QtdAnimais'
      ,pcb.DataPrimeiroCorte as 'DataPrimeiroCorte'
      ,pcb.QtdAreaPlantada as 'AreaSerPlantada'
      ,pcb.CodEspecieFlorestal as 'EspecieFlorestalCultivada'
      ,pcb.DataTerminoColheita as 'DataTerminoColheita'
      ,t.IDProduto as 'IDProduto'
--DADOS EMPREENDIMENTO
      ,emp.DESCSTN as 'DESCSTN'
FROM dbo.PropostaCredito p WITH (NOLOCK)
            INNER JOIN dbo.TmpOpCredito t WITH (NOLOCK) ON p.IDTmpOpCredito = t.IDTmpOpCredito AND p.NumCooperativa = t.NumCooperativa AND p.NumPac = t.NumPac
                        INNER JOIN dbo.PropostaCreditoBNDES pcb WITH (NOLOCK) ON pcb.IDTmpOpCredito = t.IDTmpOpCredito AND pcb.NumCooperativa = t.NumCooperativa AND pcb.NumPac = t.NumPac
            INNER JOIN dbo.ModalidadeOpCredito moc WITH (NOLOCK) ON moc.IDModalidadeProduto = t.IDModalidadeProduto AND moc.IDProduto = t.IDProduto

            --Adicionado para validar envio de CorEtnia na Contratação para Pessoa Jurídica Empresario Individual
                LEFT JOIN dbo.Pessoa ps WITH (NOLOCK) on ps.NumPessoa = t.NumClienteLibBNDES
                LEFT JOIN dbo.EnquadramentoPJBndes epj WITH (NOLOCK) on epj.NumPessoa = ps.NumPessoa

                --DAP
                LEFT  JOIN dbo.TmpBeneficiarios tb WITH (NOLOCK) ON tb.IDTmpOpCredito = p.IDTmpOpCredito
                LEFT  JOIN dbo.PessoaFisica pf WITH (NOLOCK) ON pf.NumPessoa = ps.NumPessoa
                        LEFT  JOIN dbo.GrauInstrucao g WITH (NOLOCK) ON g.IdGrauInstrucao = pf.CodNivelEducacional
                        LEFT  JOIN dbo.TmpDAPPronaf tp WITH (NOLOCK) ON tp.IDTmpOpCredito = p.IDTmpOpCredito

                --ROB
                LEFT JOIN dbo.EnquadramentoPFBndes epf WITH (NOLOCK) on epf.NumPessoa = ps.NumPessoa
                LEFT JOIN dbo.TmpGarantiaPrincipalOpCred tgpo WITH (NOLOCK) ON tgpo.IDTmpOpCredito = t.IDTmpOpCredito

            --Empreendimento
                              LEFT JOIN dbo.TmpEmpreendOpCred emp WITH (NOLOCK) on emp.IDTmpOpCredito = p.IDTmpOpCredito

WHERE pcb.bolPlataformaAPA = 1
AND moc.CodCarteira = 12
AND t.BolOperacaoPassiva = 0
and p.DescNumPropostaCred = 