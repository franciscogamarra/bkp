package br.sicoob;

//import br.com.sicoob.sisbr.bndes.integracao.xml.modelo.Beneficiaria;
//import br.com.sicoob.sisbr.bndes.integracao.xml.populate.IPopulate;
import gm.utils.files.GFile;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcMetodo;
import gm.utils.javaCreate.JcTipo;
import gm.utils.string.ListString;
import src.commom.utils.string.StringPrimeiraMaiuscula;

public class GerarPopulate {

	public static void main(String[] args) {
		
		ListString list = new ListString().load("c:/tmp/x.txt");
//		list.print();
//		valor;Campo Não Necessário.
		
		JcClasse jc = JcClasse.build(GFile.get("C:\\dev\\projects\\cre-concessao-bndes\\Integracao\\cre-concessao-bndes-integracao-ejb\\src\\main\\java\\br\\com\\sicoob\\sisbr\\bndes\\integracao\\xml\\programas\\bndes\\produtoCreditoRuralLinhasInvestimentoCusteioMaquinas\\BndesProdutoCreditoRuralLinhasInvestimentoCusteioMaquinasPopulate.java"));
		
//		IPopulate<Beneficiaria>
		
		jc.add("");
		
		JcTipo IPopulate = new JcTipo("br.com.sicoob.sisbr.bndes.integracao.xml.populate.IPopulate");
		
		jc.extends_(new JcTipo("br.com.sicoob.sisbr.bndes.integracao.xml.populate.financiamento.FinanciamentoBndesAutomatico"));
		
		for (String s : list) {
			
			ListString itens = ListString.byDelimiter(s, ";");

			String id = itens.get(0);
			
			if (!id.endsWith("01")) {
				continue;
			}
			
			String tag = itens.get(1);
			String comentario = itens.get(2);
			
			String name = StringPrimeiraMaiuscula.exec(tag);
			
			String pack = "br.com.sicoob.sisbr.bndes.integracao.xml.modelo.";
			
			if (name.contentEquals("Identificacao")) {
				pack += "Financiamento.";
			}
			
			String stipo = pack + name;
			
			JcTipo tipo = new JcTipo(stipo);
			
			JcMetodo get = jc.metodo("get"+name+"Populate");
			get.addComentario(id + " - " + tag + " - " + comentario);
			get.override();
			get.public_();
			get.type(new JcTipo(IPopulate, tipo));
			
			if (comentario.contentEquals("Campo Não Deve Ser Informado")) {
				get.returnNull();
			} else {
				get.returnSuper();
			}
			
		}
		
		jc.save();
		
	}
	
}
/*

financiamento;Campo Obrigatório no Financiamento
identificacao;Campo Obrigatório no Financiamento
cnpjAgenteFinanceiro;Campo Obrigatório no Financiamento
condicaoOperacional;Campo Obrigatório no Financiamento
produto;Campo Obrigatório no Financiamento
programaLinhaFinanciamento;Campo Obrigatório no Financiamento
anoProposta;Campo Obrigatório no Financiamento
proposta;Campo Obrigatório no Financiamento
tipoEvento;Campo Obrigatório no Financiamento
beneficiaria;Campo Obrigatório no Financiamento
cnpjBeneficiaria;Campo Obrigatório no Financiamento
cpfBeneficiaria;Campo Obrigatório no Financiamento
caracterizacaoCapitalSocial;Campo Obrigatório no Financiamento
corEtnia;Campo Obrigatório no Financiamento quando aplicável, vide comentário
crntrc;Campo Não Deve Ser Informado
valorParticipacaoNoFinanciamento;Campo Obrigatório no Financiamento
receitaOperacionalBruta;Campo Obrigatório no Financiamento
valorAnual;Campo Obrigatório no Financiamento
tipoRenda;Campo Obrigatório no Financiamento
dataReferencia;Campo Obrigatório no Financiamento
receitaOperacionalBrutaGrupo;Campo Opcional
valorAnual;Campo Opcional
tipoRenda;Campo Opcional
dataReferencia;Campo Opcional
cnpjLiderGrupo;Campo Opcional
controleFundoPrivateEquity;Campo Obrigatório no Financiamento
porte;Campo Obrigatório no Financiamento
tipo;Campo Obrigatório no Financiamento
anoReferencia;Campo Não Necessário.
contato;Campo Opcional
email;Campo Opcional
telefone;Campo Opcional
ddd;Campo Opcional
numero;Campo Opcional
ramal;Campo Opcional
celular;Campo Opcional
ddd;Campo Opcional
numero;Campo Opcional
complementarSaepWeb;Campo Obrigatório no Financ ou Contrat
dapCodigo;Campo Não Deve Ser Informado
dapDataValidade;Campo Não Deve Ser Informado
pisPasepNisNit;Campo Não Deve Ser Informado
grauDeInstrucao;Campo Não Deve Ser Informado
valorTAC;Campo Obrigatório no Financ ou Contrat
codigoAgenciaBancaria;Campo Obrigatório no Financ ou Contrat
enquadramento;Campo Obrigatório no Financiamento
cnaeInvestimento;Campo Obrigatório no Financiamento
localInvestimento;Campo Obrigatório no Financiamento
cnpjDoInvestimento;Campo Obrigatório no Financiamento
municipio;Campo Não Permitido
enderecoCompleto;Campo Obrigatório no Financiamento
tipoLogradouro;Campo Obrigatório no Financiamento
logradouro;Campo Obrigatório no Financiamento
numero;Campo Obrigatório no Financiamento
complemento;Campo Obrigatório no Financiamento
bairro;Campo Obrigatório no Financiamento
cep;Campo Obrigatório no Financiamento
municipio;Campo Obrigatório no Financiamento
uf;Campo Obrigatório no Financiamento
enquadramentoComprador;Campo Não Deve Ser Informado
cnpjComprador;Campo Não Deve Ser Informado
cpfComprador;Campo Não Deve Ser Informado
porte;Campo Não Deve Ser Informado
tipo;Campo Não Deve Ser Informado
anoReferencia;Campo Não Deve Ser Informado
receitaOperacionalBruta;Campo Não Deve Ser Informado
valorAnual;Campo Não Deve Ser Informado
tipoRenda;Campo Não Deve Ser Informado
dataReferencia;Campo Não Deve Ser Informado
receitaOperacionalBrutaGrupo;Campo Não Deve Ser Informado
valorAnual;Campo Não Deve Ser Informado
tipoRenda;Campo Não Deve Ser Informado
dataReferencia;Campo Não Deve Ser Informado
cnpjLiderGrupo;Campo Não Deve Ser Informado
controleFundoPrivateEquity;Campo Não Deve Ser Informado
caracterizacaoCapitalSocial;Campo Não Deve Ser Informado
investimento;Campo Obrigatório no Financiamento
objetivo;Campo Obrigatório no Financiamento
dataSolicitacaoNoAgenteFinanceiro;Campo Obrigatório no Financ ou Contrat
numeroEmpregosAntes;Campo Não Necessário.
numeroEmpregosDepois;Campo Não Necessário.
destinacaoInvestimento;Campo Opcional
condicoesOperacao;Campo Obrigatório no Financiamento
prazoCarenciaMeses;Campo Obrigatório no Financ ou Contrat
prazoAmortizacaoMeses;Campo Obrigatório no Financ ou Contrat
periodicidadeCarencia;Campo Obrigatório no Financ ou Contrat
periodicidadeAmortizacao;Campo Obrigatório no Financ ou Contrat
pgtoJurosCarencia;Campo Obrigatório no Financ ou Contrat
remuneracaoInstituicaoFinanceira;Campo Obrigatório no Financiamento
primeiraParticipacaoCustoFinanceiro;Campo Obrigatório no Financiamento
segundaParticipacaoCustoFinanceiro;Não utilizado (Não Deve Ser Informado)
participacaoRegularValor;Não utilizado (Não Deve Ser Informado)
participacaoAmpliadaCustoFinanceiro;Não utilizado (Não Deve Ser Informado)
participacaoAmpliadaValor;Não utilizado (Não Deve Ser Informado)
participacaoAmpliadaSpreadAgente;Não utilizado (Não Deve Ser Informado)
nivelParticipacaoBNDES;Campo Obrigatório no Financiamento
valorTotalFinanciamento;Campo Obrigatório no Financiamento
patrimonio_referencia;Campo Não Deve Ser Informado
momentoFixacaoTaxa;Campo Obrigatório no Financiamento quando aplicável. Vide comentário.
prazoPLDias;Campo Obrigatório no Financiamento quando aplicável. Vide comentário.
garantia;Campo Opcional
temGarantiaFGI;Campo Opcional
tipoFGI;Campo Não Deve Ser Informado
classificacaoDeRiscoComFGI;Campo Não Deve Ser Informado
percentualDeRiscoFGI;Campo Não Deve Ser Informado
garantiaPrincipal;Campo Opcional
percentualCobertoGarantia;Campo Opcional
classificacaoDeRisco;Campo Opcional
itemInvestimento;Campo Obrigatório no Financiamento (Linha Máquinas e Equipamentos)
sequencial;Campo Obrigatório no Financiamento (Linha Máquinas e Equipamentos)
tipo;Campo Obrigatório no Financiamento (Linha Máquinas e Equipamentos)
descricao;Campo Não Necessário.
unidadeMedida;Campo Não Necessário.
quantidade;Campo Obrigatório no Financiamento quando aplicável (Linha Máquinas e Equipamentos) . Vide comentário.
valorUnitario;Campo Obrigatório no Financiamento quando aplicável (Linha Máquinas e Equipamentos) . Vide comentário.
produtoEquipamento;Campo Obrigatório no Financiamento (Linha Máquina e Equipamentos)
codigoCFI;Campo Obrigatório no Financiamento (Linha Máquina e Equipamentos)
valorFinanciado;Campo Obrigatório no Financiamento (Linha Máquina e Equipamentos)
temEventoDeProducao;Campo Obrigatório no Financiamento (Linha Máquina e Equipamentos)
informacoesGiroServicoSeguro;Campo Opcional (Linha Máquinas e Equipamentos)
valorFinanciado;Campo Opcional (Linha Máquinas e Equipamentos)
apuracaoValoresFinanciados;Campo Não Necessário.
fixo;Campo Não Necessário.
equipamentoCFI;Campo Não Necessário.
softwares;Campo Não Necessário.
giro;Campo Não Necessário.
seguro;Campo Não Necessário.
outrosInvestimentos;Campo Não Necessário.
informacoesComplementaresAgricola;Campo Obrigatório no Financiamento quando aplicável. 
quantidadeAnimais;Campo Não Deve Ser Informado
dataEstimadaPrimeiroCorte;Campo Não Deve Ser Informado
areaASerPlantadaHectares;Campo Não Deve Ser Informado
especieFlorestalASerCultivada;Campo Não Deve Ser Informado
cnpjCooperativaSingularCredito;Campo Obrigatório no Financiamento quando aplicável. Vide comentário.
temBonusAdimplencia;Campo Não Deve Ser Informado
dataTerminoColheita;Campo Obrigatório no Financiamento quando aplicável. Vide comentário.
informacoesComplementaresProjetoInvestimento;Campo obrigatório. Vide Comentários
imovelDePropriedadeBeneficiario;Campo Opcionals
vigenciaContratoImovelIncluiPrazoTotalFinanc;Campo Opcionals
dataInicioProjInvestimento;Campo Opcionals
dataFimProjInvestimento;Campo Opcionals
descricaoProjInvestimentoAgenteFinanceiro;Campo Opcionals
informacoesResultadosEsperados;Campo obrigatório. Vide Comentários
tipoResultadoEsperado;Campo obrigatório. Vide Comentários
unidadeMedida;Campo obrigatório. Vide Comentários
quantidadeUltimoExercicio;Campo obrigatório. Vide Comentários
valorUltimoExercicio;Campo Opcionals
quantidadeAposProjeto;Campo Opcionals
valorAposProjeto;Campo Opcionals
valorInvestimentosFinanciadosFixos;Campo obrigatório. Vide Comentários
valorInvestimentosFinanciadosEquipamentosNacionais;Campo obrigatório. Vide Comentários
valorInvestimentosFinanciadosSoftware;Campo obrigatório. Vide Comentários
valorCapitalGiroAssociado;Campo obrigatório. Vide Comentários
fontesRecursos;Campo obrigatório. Vide Comentários
valorFonteBNDES;Campo obrigatório. Vide Comentários
valorFonteRecursoProprio;Campo Opcionals
outrasFontes;Campo Opcionals
descricao;Campo Opcionals
valor;Campo Opcionals
cooperado;Campo Não Necessário.
cnpj;Campo Não Necessário.
cpf;Campo Não Necessário.
valorResponsabilidade;Campo Não Necessário.
municipioInvestimento;Campo Não Necessário.
porte;Campo Não Necessário.
porteAnoReferencia;Campo Não Necessário.
cnaeAplicacaoRecursos;Campo Não Necessário.
corEtnia;Campo Não Necessário.
informacoesComplementares;Campo Não Necessário.
areaASerPlantadaHectares;Campo Não Necessário.
especieFlorestalASerCultivada;Campo Não Necessário.
quantidadeAnimais;Campo Não Necessário.
informacaoAdicional;Campo Não Necessário.
chave;Campo Não Necessário.
valor;Campo Não Necessário.
quantidadeAnexos;Campo Obrigatório no Financiamento quando aplicável. Vide comentário.



0
A01
A02
A03
A04
A05
A06
A07
A08


B01
B02
B03
B04
B05
B06
B07
B08
B09
B10
B11
B12
B13
B14
B15
B16
B17
B18
B19
B20
B21
B22
B23
B24
B25
B26
B27
B28
B29
B30
B31
B32
B33
B34
B35
B36


C01
C02
C03
C04
C05
C06
C07
C08
C09
C10
C11
C12
C13
C14
C15
C16
C17
C18
C19
C20
C21
C22
C23
C24
C25
C26
C27
C28
C29
C30
C31


D01
D02
D03
D04
D05
D06


E01
E02
E03
E04
E05
E06
E07
E08
E09
E10
E11
E12
E13
E14
E15
E16
E17
E18


G01
G02
G03
G04
G05
G06
G07
G08


H01
H02
H03
H04
H05
H06
H07
H08
H09
H10
H11
H12
H13


J01
J02
J03
J04
J05
J06
J07


L01
L02
L03
L04
L05
L06
L07
L08


P01
P02
P03
P04
P05
P06
P07
P08
P09
P10
P11
P12
P13
P14
P15
P16
P17
P18
P19
P20
P21
P22
P23


M01
M02
M03
M04
M05
M06
M07
M08
M09
M10
M11
M12
M13


N01
N02
N03


O01


*/