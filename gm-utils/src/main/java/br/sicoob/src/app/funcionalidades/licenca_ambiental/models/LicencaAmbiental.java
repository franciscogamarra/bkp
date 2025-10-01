package br.sicoob.src.app.funcionalidades.licenca_ambiental.models;

import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import gm.languages.ts.javaToTs.annotacoes.Interface;
import js.outros.Date;

@ImportStatic @Interface
@From("@app/funcionalidades/licenca-ambiental/models/licenca-ambiental.model")
public class LicencaAmbiental {
	public Integer id;
	public Integer idCliente;
	public TipoLicencaAmbiental tipoLicencaAmbiental;
	public String numeroLicenca;
	public Date dataEmissao;
	public Date dataValidade;
	public String orgaoConcedente;
	public String ufOrgaoConcedente;
	public String finalidadeFundamentoLegal;
	public String tipoDocumentoTratado;
	public Integer numeroLicencaSort;
}

//C:\dev\projects\cre-concessao-bndes-web\src\app\funcionalidades\pedido-liberacao-bndes\components
