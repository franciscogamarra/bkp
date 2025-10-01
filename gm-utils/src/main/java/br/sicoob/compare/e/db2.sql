select
  PropostaCredito.valorOperacao as valorOpCred
, PropostaCredito.NumContratoBNDES as NroContratoBNDES
, '' as NroPedidoLiberacao -- Esse número é aleatório gerar no java.
, PedidoLiberacao.valorSolicitadoBndes as ValorALiberar
, LicencaAmbiental.codTipoLicencaAmbiental as TipoDocumento
, LicencaAmbiental.numLicenca as NroDocumento
, LicencaAmbiental.dataEmissaoLicenca as DataEmissao
, LicencaAmbiental.dataFimLicenca as DataValidade
, LicencaAmbiental.descOrgaoConcedente as OrgaoConcedente
, LicencaAmbiental.siglaUfOrgaoConc as UFOrgaoConcedente
, LicencaAmbiental.descFinalidadeLegal as FinalidadeLegal
, PedidoLiberacao.qtdArquivoAnexo as QtdArquivoAnexoTmpPedidoLiberacaoBndes
, PropostaCredito.codSistematicaOperacional as CodSistOperacional
, PedidoLiberacao.codFinalidadeLiberacao as CodFinalidadeLiberacao

     from bnd.PrePropostaBndes     as PropostaCredito
     join bnd.pedidoLiberacaoBndes as PedidoLiberacao  on PedidoLiberacao.idPrePropostaBndes = PropostaCredito.idPrePropostaBndes
left join bnd.LicencaAmbiental     as LicencaAmbiental on LicencaAmbiental.idLicencaAmbiental = PedidoLiberacao.idLicencaAmbiental

where PropostaCredito.numPropostaCredito = 