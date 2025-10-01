package br.sicoob.src.app.funcionalidades.licenca_ambiental.models;

import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.languages.ts.javaToTs.annotacoes.Interface;

@ImportStatic @Interface
@From("@funcionalidades/licenca-ambiental/models/tipo-licenca-ambiental.model")
public class TipoLicencaAmbiental {
	public Integer id;
	public String descricao;
	public boolean exibeNumeroLicenca;
	public boolean exibeDataEmissao;
	public boolean exibeDataValidade;
	public boolean exibeOrgaoConcedente;
	public boolean exibeUfOrgaoConcedente;
	public boolean exibeFinalidadeFundamentoLegal;
}