 package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps;

import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.PcbCalculo;
import gm.languages.ts.angular.forms.FormGroup;
import gm.languages.ts.javaToTs.annotacoes.From;
import gm.languages.ts.javaToTs.annotacoes.ImportStatic;
import js.Js;
import js.annotations.NaoConverter;
import js.array.Array;

@NaoConverter @ImportStatic
@From("@app/funcionalidades/proposta-credito-bndes/components/aba-itens-investimento/aba-itens-investimento.component")
public class AbaItensInvestimentoComponent {

	public static final AbaItensInvestimentoComponent instance = new AbaItensInvestimentoComponent();
	private AbaItensInvestimentoComponent() {}
	
	public Array<TipoInvestimento> tipoInvestimentos = new Array<>();
	public final FormGroup formParent = PropostaCreditoBndesComponent.instance.formCadastroProposta;

	public void adicionarTipoInvestimento() {
		tipoInvestimentos.push(TipoInvestimento.json());
	}

	public void incluirItensInvestimento(TipoInvestimento item1) {
		
	}
	
	public PcbCalculo calculo() {
		
	    PcbCalculo.aoCalcular = () -> {
	      Js.setTimeout(() -> {
//	        this.ajustaListaItensInvestimento();
//	        this.cd.detectChanges();
	      }, PcbCalculo.TEMPO_REFRESH);
	    };
	    
	    return PcbCalculo.get();
		
	}
	
}
