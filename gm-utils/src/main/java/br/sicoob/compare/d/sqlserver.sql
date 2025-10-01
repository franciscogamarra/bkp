--RECUPERAR_DADOS_CAR_EMPREENDIMENTO_LEGADO
select distinct teop.NumRegCAR
from dbo.PropostaCredito p WITH (NOLOCK)
inner join dbo.TmpOpCredito tmp WITH (NOLOCK) on tmp.IDTmpOpCredito = p.IDTmpOpCredito and tmp.BolOperacaoPassiva = 0
inner join dbo.PropostaCreditoBNDES pcb WITH (NOLOCK) on pcb.IDTmpOpCredito = tmp.IDTmpOpCredito
inner join dbo.TmpEmpreendOpCred teop WITH (NOLOCK) on teop.IDTmpOpCredito = tmp.IDTmpOpCredito
where p.DescNumPropostaCred = 
