

/* Script: LISTAR_EQUIPAMENTO_POR_PROPOSTA_FINAME_FINANCIAMENTO */

select distinct
  cast(ItemInvestimento.numSeqItemInvestimento as numeric) as IDSeqEquipamento

/*
	GiroServicoSeguroNaLiberacaoComumPopulate
*/
, isNull(InfoResultadoEsperado.valorCapitalGiroAssociado, 0.0) as ValorCapitalGiro

/*
	GiroServicoSeguroNaLiberacaoComumPopulate
*/
, isNull(ControleItemLiberacao.valorSolicitadoItem, 0.0) as ValorServicoAssociado

/*
	GiroServicoSeguroNaLiberacaoComumPopulate
*/
, isNull(ControleItemLiberacao.valorSolicitadoItem, 0.0) as ValorSeguro
, PropostaCredito.codSistematicaOperacional as CodSistOperacional
, cast(ItemInvestimento.qtdItemInvestimento as numeric(8,4)) as QtdEquipamento
, ItemInvestimento.valorUnitarioItem as ValorUnitario

/*
	InvestimentoComumPopulate
	CodigoCFI
*/
, isNull(ItemInvestimento.codProdutoBndes, 0) as CodProdBNDES
, ItemInvestimento.valorTotalFinanciadoItem as ValorFinanciadoTotal

, case
	when ControleItemLiberacao.bolEquipamentoEventoProducao = true
	then '1' else '0'
  end as CodEventoEquipamento

, case
	when ItemInvestimento.bolItemUsado = 0 then false
	when ItemInvestimento.bolItemUsado = 1 then true
  end as BolEquipamentoUsado
  
, case
	when ItemInvestimento.bolItemImportado = 0 then false
	when ItemInvestimento.bolItemImportado = 1 then true
  end as BolEquipamentoImportado
  
, ItemInvestimento.descInformacaoAdicional as DescCompEquipamento

     from bnd.PrePropostaBndes      as PropostaCredito
	 join bnd.itemInvestimento      as ItemInvestimento      on ItemInvestimento.idPrePropostaBndes = PropostaCredito.idPrePropostaBndes
left join bnd.infoResultadoEsperado as InfoResultadoEsperado on InfoResultadoEsperado.idInfoComplementarProjetoInvestimento = PropostaCredito.idInfoComplementarProjetoInvestimento
left join bnd.controleItemLiberacao as ControleItemLiberacao on ControleItemLiberacao.idItemInvestimento = ItemInvestimento.idItemInvestimento
where PropostaCredito.numPropostaCredito = :numeroPropostaCredito



