package br.gerar;

public class ImovelEdit {

	public static void exec() {
		
		Aba aba = new Aba("imovel-edit");

		aba.newSelect("imovel").cols(4)
		.dto("ImovelOptionDto").nullValue("ImovelOption.NULL");
		
		aba.newNumber("produtividade").cols(4).title("Produtividade (Tonelada / Hectare)");
		aba.newPercentual("percentualFinanciado").cols(4);
		
		aba.newMoney("totalExploracao").cols(4).container(null)
		.readOnly().casasInteiras(15).title(null).showTextoErroFalse().bold();
		
		aba.gerar();
		
		
		
//	    <div class="cadastro-empreendimento-aba-imovel-info-header">
//	      <div class="info-item">
//	        <label>Imóvel</label>
//	        <p><b>{{ imovelInfo.imovel }}</b></p>
//	      </div>
//	      <div class="info-item">
//	        <label>UF</label>
//	        <p><b>{{ imovelInfo.uf }}</b></p>
//	      </div>
//	      <div class="info-item">
//	        <label>Município</label>
//	        <p><b>{{ imovelInfo.municipio }}</b></p>
//	      </div>
//	      <div class="info-item">
//	        <label>Área Imóvel</label>
//	        <p><b>{{ imovelInfo.areaImovel }}</b></p>
//	      </div>
//	    </div>
//
//	    <div class="ss-grid" style="margin-top: 24px;">
//	      <div class="col-4 field-with-required bold-label">
//	        <sc-form-field>
//	          <label><b>Descrição SNCR</b></label>
//	          <input scInput formControlName="descricaoSNCR" type="text" />
//	          <span class="field-required">requerido</span>
//	        </sc-form-field>
//	      </div>
//
//	      <div class="col-4 field-with-required bold-label">
//	        <sc-form-field>
//	          <label><b>Descrição NIRF/CIB</b></label>
//	          <input scInput formControlName="descricaoNIRFCIB" type="text" />
//	          <span class="field-required">requerido</span>
//	        </sc-form-field>
//	      </div>
//	    </div>
//
//	    <div class="ss-grid" style="margin-top: 18px;">
//	      <div class="col-4 field-with-required bold-label">
//	        <sc-form-field>
//	          <label><b>Descrição CAR</b></label>
//	          <input scInput formControlName="descricaoCAR" type="text" />
//	          <span class="field-required">requerido</span>
//	        </sc-form-field>
//	      </div>
//	      <div class="col-4 field-with-required bold-label">
//	        <sc-form-field>
//	          <label><b>Nº Outorga D'água</b></label>
//	          <input scInput formControlName="numeroOutorgaAgua" type="text" />
//	          <span class="field-required">requerido</span>
//	        </sc-form-field>
//	      </div>
//	    </div>
//
//	    <div class="ss-grid" style="margin-top: 18px; align-items: flex-end;">
//	      <div class="col-2 field-with-required bold-label">
//	        <sc-form-field>
//	          <label><b>Percentual Preservação</b></label>
//	          <input scInput formControlName="percentualPreservacao" type="text" />
//	                    <span class="field-icon">
//	            <img src="assets/icons/pesquisar_empreendimento/percentual.png" alt="%" />
//	          </span>
//	          <span class="field-required">requerido</span>
//	        </sc-form-field>
//	      </div>
//
//	      <div class="col-3 bold-label">
//	        <div class="checkbox-with-icon ">
//	          <sc-checkbox formControlName="indicadorInformacaoImoveRural"></sc-checkbox>
//	          <span class="checkbox-label-with-icon">
//	            <b>Indicador Informacao Imóvel Rural</b>
//	            <img src="assets/icons/pesquisar_empreendimento/info-circle.png" alt="Informação" title="Informações sobre informacao" />
//	          </span>
//	        </div>
//	      </div>
//
//	      <div class="col-2 field-with-required bold-label">
//	        <sc-form-field>
//	          <label><b>Possui Gleba?</b></label>
//	          <select scSelect formControlName="possuiGleba">
//	            <option value="">Selecione...</option>
//	            <option value="S">Sim</option>
//	            <option value="N">Não</option>
//	          </select>
//	          <span class="field-required">requerido</span>
//	        </sc-form-field>
//	      </div>
//	    </div>		
		
	}
	
}
