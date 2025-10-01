--LISTAR_TMP_FONTES_DE_RECURSO_ADICIONAIS
select
  FonteRecursoAdicional.descFonteRecursoAdicional as DescFonteRecursoAdicional
, FonteRecursoAdicional.valorFonteRecursoAdicional as ValorFonteRecursoAdicional
  from bnd.PrePropostaBndes                    as PropostaCredito
  join bnd.infoComplementarProjetoInvestimento as InfoComplementar      on InfoComplementar.idInfoComplementarProjetoInvestimento = PropostaCredito.idInfoComplementarProjetoInvestimento
  join bnd.FonteRecursoAdicional               as FonteRecursoAdicional on FonteRecursoAdicional.idInfoComplementarProjetoInvestimento = InfoComplementar.idInfoComplementarProjetoInvestimento 
where PropostaCredito.numPropostaCredito = ?