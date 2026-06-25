package br.gerar;

public class AbaSeguro {

	public static void exec() {
		
		Aba aba = new Aba("seguro");
		
		aba.newSelect("tipoDeSeguro")
		.container("tipoDeSeguro")
		.cols(12)
		.options = "SeguroController.tipoDeSeguroOptions";
		
		//proagro
		aba.onNewCampo = campo -> campo.cols(6).container("abaProagro");
		aba.newSelect("aliquotaSeguro").cols(4);
		aba.newPercentual("aliquotaTotal").cols(4);
		aba.newBoolean("descontoRiscoApurado").cols(4);
		aba.newMoney("valorFinanciado");
		aba.newMoney("valorRecursoProprioEnquadrado");
		aba.newMoney("valorRecursoProprio");
		aba.newMoney("valorSeguro");
		aba.newMoney("valorGarantiaRendaMinima");
		aba.newMoney("valorTotalEnquadrado");
		aba.newBoolean("financia").title("Financia ?");
		aba.newAcao("calcular");
		aba.newAcao("limpar");

		//seguro privado
		aba.onNewCampo = campo -> campo.container("abaSeguroPrivado");
		aba.newCpfCnpj("cpfCnpjSegurado").title("CPF/CNPJ do Segurado");
		aba.newCnpj("cnpjBeneficiario").title("CNPJ do Beneficiário");
		aba.newSelect("seguradora").title("Seguradora + CNPJ").cols(12);
		aba.newString("corretora", 250).cols(12);
		aba.newSelect("tipoSeguro").title("Tipo do Seguro").cols(12);
		aba.newSelect("tipoDocumento").title("Tipo de Documento").options = "SeguroController.tipoDocumentoOptions";
		aba.newString("numeroPropostaOuApolice", 50).title("Nº Proposta/Ápólice");
		aba.newData("inicioVigencia");
		aba.newData("fimVigencia");
		
		aba.newCheckList("eventosCobertos").cols(12);
		aba.newMoney("valorSegurado");
		aba.newBoolean("financiarSeguro");
		aba.newMoney("valorPremio").title("Valor do Prêmio").last();
		
		Campo desejaSubversao = aba.newBoolean("desejaSubversao").title("Deseja Subverção?");
		aba.newSelect("tipoSubvercao").title("Tipo de Subverção?").visivelSe(desejaSubversao);
		aba.newMoney("valorSubvercao").title("Valor da Subverção").visivelSe(desejaSubversao);
		aba.newBoolean("financiarSubversao").title("Financiar Subverção?").visivelSe(desejaSubversao);
		
		aba.gerar();
		
	}
	
}
