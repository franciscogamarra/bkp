package br.sicoob.src.app.components.cadastro_licenca_ambiental;

import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.LicencaAmbiental;
import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoLicencaAmbiental;
import br.sicoob.src.app.shared.forms.Form;
import br.sicoob.src.app.shared.forms.InputDate;
import br.sicoob.src.app.shared.forms.InputInt;
import br.sicoob.src.app.shared.forms.InputString;
import br.sicoob.src.app.shared.forms.InputUF;
import gm.languages.ts.javaToTs.JS;

public class LicencaAmbientalForm extends JS {

	public Form form = new Form();
	public InputInt id = new InputInt("id", false);
	public InputInt idCliente = new InputInt("idCliente", false);
	public InputInt codigoTipoLicenca = new InputInt("codigoTipoLicenca", true);
	public InputString numeroLicenca = new InputString("numeroLicenca", false);
	public InputDate dataEmissao = new InputDate("dataEmissao", false);
	public InputDate dataValidade = new InputDate("dataValidade", false);
	public InputString orgaoConcedente = new InputString("orgaoConcedente", false);
	public InputUF ufOrgaoConcedente = new InputUF("ufOrgaoConcedente", false);
	public InputString finalidadeFundamentoLegal = new InputString("finalidadeFundamentoLegal", false);

	private LicencaAmbientalForm() {
		form.add(id);
		form.add(idCliente);
		form.add(codigoTipoLicenca);
		form.add(numeroLicenca);
		form.add(dataEmissao);
		form.add(dataValidade);
		form.add(orgaoConcedente);
		form.add(ufOrgaoConcedente);
		form.add(finalidadeFundamentoLegal);
	}

	public void clear() {
		form.clear();
	}

	public void clearValidators() {
		form.clearValidators();
	}
	
	public void restart() {
		clearValidators();
		form.clearErrors();
	}
	
	public void set(LicencaAmbiental o) {
		id.set(o.id);
		codigoTipoLicenca.set(o.tipoLicencaAmbiental.id);
		numeroLicenca.set(o.numeroLicenca);
		dataEmissao.setCast(o.dataEmissao);
		dataValidade.setCast(o.dataValidade);
		orgaoConcedente.set(o.orgaoConcedente);
		ufOrgaoConcedente.setSigla(o.ufOrgaoConcedente);
		finalidadeFundamentoLegal.set(o.finalidadeFundamentoLegal);
	}
	
	public LicencaAmbiental get() {
		LicencaAmbiental o = new LicencaAmbiental();
		o.id = id.get();
		o.tipoLicencaAmbiental = new TipoLicencaAmbiental();
		o.tipoLicencaAmbiental.id = codigoTipoLicenca.get();
		o.numeroLicenca = numeroLicenca.get();
		o.dataEmissao = dataEmissao.get();
		o.dataValidade = dataValidade.get();
		o.orgaoConcedente = orgaoConcedente.get();
		o.ufOrgaoConcedente = ufOrgaoConcedente.get().siglaUF;
		o.finalidadeFundamentoLegal = finalidadeFundamentoLegal.get();
		return o;
	}
	
	public boolean isValid() {
		return form.isValid();
	}
	
	private static LicencaAmbientalForm instance;

	public static LicencaAmbientalForm getInstance() {
		if (isNull_(instance)) {
			instance = new LicencaAmbientalForm();
		}
		return instance;
	}
	
	public Form getForm() {
		return form;
	}

}