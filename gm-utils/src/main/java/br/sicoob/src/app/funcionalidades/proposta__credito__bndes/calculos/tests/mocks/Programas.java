package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks;

import br.sicoob.SicoobTranspilar;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models.Programa;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.array.Array;

public class Programas {
	
	public static final Array<Programa> itens = new Array<>();
	
	public static final Programa p2424 = 
		Programa.json()
		.id(2424)//
		.prazosLimitesSolicitacaoPL(new Array<>())//
//		.prazosTotaisOperacao(PrazosTotaisOperacao.itens)//
		.tipoEventos(TiposEvento.itens)//
//		.tiposEnquadramentoOperacao(null)
		.descricao("FINAME - BK AQUISIÇÃO E COMERCIALIZAÇÃO")//
		.sigla("DC1,4 - MPME - BK AQUISIÇÃO - FINANCIAMENTO À COMPRADORA (TFB)")//
		.taxaJurosBasica(1.1500)//
		.taxaJurosRisco(1.4000)//
		.produtoBndes(6)//
		.linhaBndes(301)//
		.bolFinancEquipNovoUsado(false)//
		.siglaPrograma("")
		.bolExigeItemInvestimento(true)//
		.bolEnviaQtdEmpregado(false)//
		.bolHabilitaCadastroPl(true)//
		.taxaJurosRiscoMax(1.4000)//
		.taxaJurosRiscoMin(1.4000)//
		.taxaJurosRiscoPadrao(1.4000)//
		.bolHabilitaEnvioPlataformaCredito(true)//
		.bolLicencaDispensaAmb(true)//
		.bolLicencaAmbItemDiverso(false)//
		.bolLicencaAmbExcecao(false)//
		.custoFinanceiro(CustosFinanceiros.o1026)//
		.bolExigeDataEmissaoCartaFianca(true)//
		.bolFinalidadeLiberacao(false)//
		.bolFinalidadeLiberacaoExcecao(false)//
		.indiceBndes(null)
		.codigoPrograma(null)
		.percCustoIntermediacaoFinanceira(null)
		.paramInfoComplementarAgricola(null)
		.paramInfoComplementarProjetoInvestimento(null)
		.paramInfoResultadoEsperado(null)

	;	
			
	static {
		itens.push(p2424);
		
//		itens.add(o);
		/*
		
		"paramInfoComplementarProjetoInvestimento = {
			"id = 3,
			"bolHabilitaImoveldePropriedadeBeneficiario = true,
			"bolHabilitaVigenciaContratoImovel = true,
			"bolHabilitaDataInicioProjetoInvestimento = true,
			"bolHabilitaDataFimProjetoInvestimento = true,
			"bolHabilitaDescProjetoInvestimento = true,
			"bolDataInicioProjetoInvestimentoObrigatorio = false,
			"bolDataFimProjetoInvestimentoObrigatorio = false,
			"bolDescProjetoInvestimentoObrigatorio = true
		},
		"paramInfoResultadoEsperado = {
			"id = 3,
			"bolHabilitaQtdUltimoExercicio = true,
			"bolHabilitaValorUltimoExercicio = true,
			"bolHabilitaQtdAposProjeto = true,
			"bolHabilitaValorAposProjeto = true,
			"bolHabilitaValorInvestFinanFixos = true,
			"bolHabilitaValorInvestFinancEquipNacionais = true,
			"bolHabilitaValorInvestFinancSoftware = true,
			"bolHabilitaValorCapitalGiroAssociado = true,
			"bolHabilitaTipoResultadoEsperado = true,
			"bolHabilitaUnidadeMedida = true,
			"bolQtdUltimoExercicioObrigatorio = false,
			"bolValorUltimoExercicioObrigatorio = false,
			"bolQtdAposProjetoObrigatorio = true,
			"bolValorAposProjetoObrigatorio = false,
			"bolTipoResultadoEsperadoObrigatorio = false,
			"bolUnidadeMedidaObrigatorio = false
		}
	},		
		
		*/		
		
	}

	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(Programas.class);
	}
	
}
