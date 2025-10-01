package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.src.app.funcionalidades.licenca_ambiental.models.TipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.Num;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.PcbCalculo;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.proxies.AdicionaItensForm;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.comps.AbaItensInvestimentoComponent;
import js.annotations.NaoConverter;
import js.html.Element;
import js.html.HtmlDocument;
import js.html.IHtmlDocument;
import js.html.NodeList;
import js.support.ThreadsList;

@NaoConverter
public class RodarTestesNoJava {
	
	private static abstract class El extends Element {

		@Override
		public final void focus() {
			// TODO Auto-generated method stub
			
		}

		@Override
		public final void scrollIntoView(boolean b) {
			// TODO Auto-generated method stub
			
		}

	}

	public static void main(String[] args) {
		
		HtmlDocument.implementacao = new IHtmlDocument() {
			
			Element abaItens = new El() {
				@Override
				public void click() {
					System.out.println("Aba Itens clicado");
				}
			};

			Element abaOperacoes = new El() {
				@Override
				public void click() {
					System.out.println("Aba Operacoes clicado");
				}
			};
			
			Element botaoAdicionarEquipamento = new El() {
				@Override
				public void click() {
					AdicionaItensForm.formulario.onSubmit();
				}
			};

			Element botaoAdicionarTipoDeItem = new El() {
				@Override
				public void click() {
					AbaItensInvestimentoComponent.instance.tipoInvestimentos.push(TipoInvestimento.json());
				}
			};

			Element selectTipoInvestimentos = new El() {
				@Override
				public void click() {
					System.out.println("Select tipo investimento clicado");
				}
			};
			
			@Override
			public Element getElementById(String id) {
				
				if (id.contentEquals("accordion-group-aba-itens-investimento")) {
					return abaItens;
				}

				if (id.contentEquals("accordion-group-aba-operacoes")) {
					return abaOperacoes;
				}
				
				if (id.contentEquals("adiciona-equipamentos-botao-adicionar-equipamento")) {
					return botaoAdicionarEquipamento;
				}
				
				if (id.contentEquals("adiciona-itens-investimento-adicionar-itens-investimento")) {
					return botaoAdicionarEquipamento;
				}

				if (id.contentEquals("adiciona-itens-inverstimento-com-quantidade-adicionar-item-investimento")) {
					return botaoAdicionarEquipamento;
				}
				
				if (id.contentEquals("button-adicionarTipoDeItem")) {
					return botaoAdicionarTipoDeItem;
				}
				
				return null;
				
			}

			@Override
			public NodeList getElementsByClassName(String string) {
				
				NodeList list = new NodeList();
				
				if (string.contentEquals("select-tipoInvestimentos")) {
					list.itens.add(selectTipoInvestimentos);
				}
				
				return list;
			}
			
//			getElementsByClassName
			
		};
		
//		PcbTest001.exec();
//		PcbTest002.exec();
		PcbTest003.exec();
		ThreadsList.run();
		
		PcbCalculo.get().setPercFinanciada(Num.CEM);
		PcbCalculo.get().setPercFinanciada(Num.fromNumber(2, 35.48));
		
		PcbCalculo.print();
		
	}
	
}