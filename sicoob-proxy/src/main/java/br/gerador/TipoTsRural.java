package br.gerador;

import br.gerar.ts.TipoTs;

public class TipoTsRural {

	public static final TipoTs Campo = new TipoTs("Campo", "@app/components/campo/Campo", false);
	public static final TipoTs SelectOption = new TipoTs("SelectOption", "@app/components/campo/SelectOption", false);
	public static final TipoTs SimpleOptionDto = new TipoTs("SimpleOptionDto", "@app/components/campo/SelectOption", true);
	public static final TipoTs ImovelOption = new TipoTs("ImovelOption", "@app/funcionalidades/pesquisar_empreendimento/components/interfaces/ImovelObjects", true);
	public static final TipoTs ImovelOptionDto = new TipoTs("ImovelOptionDto", "@app/funcionalidades/pesquisar_empreendimento/components/interfaces/ImovelObjects", true);
	public static final TipoTs api = new TipoTs("CadastroEmpreendimentoApiService", "@app/funcionalidades/pesquisar_empreendimento/services/cadastro-empreendimento-api.service", true);
	public static final String apiInject = "private api: CadastroEmpreendimentoApiService";
	
}