package br.gerador.imovel;

import br.definicoes.Model;
import br.definicoes.Sistema;
import br.definicoes.TipoCampoLayout;
import br.gerador.support.PessoaSimples;

// no back o dto se chama EmpRuralImovelDTO
public class EmpreendimentoRuralImovel {

	public static void exec(Sistema sistema) {
		
		Model model = sistema.newModelRapido(EmpreendimentoRuralImovel.class);
		model.getId().banco.nome("idEmpreendimentoRuralImovel");
		
		model.front.pkg = "imovel";
		
		model.setOnNewCampo(campo -> {
//			campo.notNull();
			campo.front.edit.cols(3);
		});
		
		model.newLong("idImovel");
		model.newString("descNomeImovel", 100).readOnly().titulo("Imóvel");
		model.newString("descUF", 2).readOnly();
		model.newString("descMunicipio", 100).readOnly();
		model.newString("descAreaImovel", 100).readOnly().titulo("Área imóvel");
		
		model.newString("codigoSNCR", 13).titulo("Descrição SNCR").descricao("Número do imóvel no Sistema Nacional de Cadastro Rural");
		model.newString("codigoNIRF", 9).titulo("Descrição NIRF/CIB").descricao("Número do Imóvel na Receita Federal - NIRF. É o número de identifição junto à Receita Federal do Brasil atribuído ao imóvel rural no ato da inscrição no CAFIR - Cadastro de imóveis rurais");
		model.newString("codigoCAR", 41).titulo("Descrição CAR").descricao("Número de registro do imóvel no Cadastro Ambiental Rural");
		model.newString("numOutorgaDagua", 30).titulo("Nº Outorga D'água").descricao("Numero de registro de outorga d'água'");
		model.newBigDecimal("percPreservacao", 3, 2).max(100).titulo("Percentual Preservação");
		
		model.newBigDecimal("areaExplorada", 16, 2).titulo("Qtd explorada").banco.nome("valorAreaExplorada");
		
		model.newBooleanFalse("bolIndConformidadeImovel").titulo("Indicador Conformidade Imóvel Rural");
		model.newBooleanFalse("bolPossuiGleba").titulo("Possui Gleba").front.edit.layout(TipoCampoLayout.select);
		
		model.newBigDecimal("vlrUnidadeAnimal", 5, 2).titulo("Valor unidade animal").banco.nome("valorUnidadeAnimal");
		
		model.newFilhos("proprietarios", PessoaSimples.class);
		
		model.newBooleanFalse("urbano").readOnly();
		model.newInt("idLocalidade").readOnly().notNullFalse();
//		
//		model.front.cadastroCampos.codigoEmbutidoDto.add("imovel?: ImovelOptionDto,");
//		model.front.cadastroCampos.codigoEmbutidoDto.add("listaGlebaDTO?: Record<string, unknown>[],");
//		model.front.cadastroCampos.codigoEmbutidoCloneDto.add("imovel : origem.imovel,");
//		model.front.cadastroCampos.codigoEmbutidoCloneDto.add("listaGlebaDTO : origem.listaGlebaDTO?.map(item => ({ ...item })) || []");
//		model.front.cadastroCampos.codigoEmbutidoDto.imports.add(TipoTsRural.ImovelOptionDto);
		
	}
	
}

/*


	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empreendimentoRuralImovel")
	private List<EmpreendimentoRuralImovelGleba> listaEmpRuralImovelGleba = new ArrayList<EmpreendimentoRuralImovelGleba>();
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empreendimentoRuralImovel")
	private List<EmpreendimentoRuralImovelGlebaGeo> listaEmpRuralImovelGlebaGeo = new ArrayList<EmpreendimentoRuralImovelGlebaGeo>();

	@OneToMany(fetch = FetchType.LAZY, mappedBy = "empreendimentoRuralImovel")
	private List<EmpreendimentoRuralImovelProprietario> listaEmpRuralImovelProprietario = new ArrayList<EmpreendimentoRuralImovelProprietario>();

	
	
	
	
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "idEmpreendimentoRural", nullable = false)
	private EmpreendimentoRural empreendimentoRural;

*/
