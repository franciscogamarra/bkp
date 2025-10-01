package gm.languages.react;

import gm.languages.PalavrasLoad;
import gm.languages.java.expressoes.GenericsAbre;
import gm.languages.java.expressoes.IgualComparacao;
import gm.languages.java.expressoes.TipoJava;
import gm.languages.java.expressoes.words.Import;
import gm.languages.java.expressoes.words.Private;
import gm.languages.palavras.Comentario;
import gm.languages.palavras.ComentarioBlocoClose;
import gm.languages.palavras.ComentarioBlocoOpen;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.comuns.Diferente;
import gm.languages.palavras.comuns.Else;
import gm.languages.palavras.comuns.Extends;
import gm.languages.palavras.comuns.For;
import gm.languages.palavras.comuns.If;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.Null;
import gm.languages.palavras.comuns.Return;
import gm.languages.palavras.comuns.While;
import gm.languages.palavras.comuns.conjuntos.arrow.Arrow;
import gm.languages.palavras.comuns.conjuntos.bloco.AbreBloco;
import gm.languages.palavras.comuns.conjuntos.bloco.FechaBloco;
import gm.languages.palavras.comuns.conjuntos.parametro.ParentesesCondicaoClose;
import gm.languages.palavras.comuns.conjuntos.parametro.ParentesesCondicaoOpen;
import gm.languages.palavras.comuns.literal.Literal;
import gm.languages.palavras.comuns.simples.AbreChaves;
import gm.languages.palavras.comuns.simples.AbreColchetes;
import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.Barra;
import gm.languages.palavras.comuns.simples.DoisPontos;
import gm.languages.palavras.comuns.simples.Exclamacao;
import gm.languages.palavras.comuns.simples.FechaChaves;
import gm.languages.palavras.comuns.simples.FechaColchetes;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.languages.palavras.comuns.simples.Igual;
import gm.languages.palavras.comuns.simples.Instanceof;
import gm.languages.palavras.comuns.simples.Interrogacao;
import gm.languages.palavras.comuns.simples.Maior;
import gm.languages.palavras.comuns.simples.Menor;
import gm.languages.palavras.comuns.simples.Ponto;
import gm.languages.palavras.comuns.simples.PontoEVirgula;
import gm.languages.palavras.comuns.simples.Virgula;
import gm.languages.react.termos.Assinatura;
import gm.languages.react.termos.CallClose;
import gm.languages.react.termos.CallOpen;
import gm.languages.react.termos.DeclaracaoDeMetodo;
import gm.languages.react.termos.JsonParamClose;
import gm.languages.react.termos.JsonParamOpen;
import gm.languages.react.termos.NewArray;
import gm.languages.react.termos.NewJson;
import gm.languages.react.termos.PropDeclare;
import gm.languages.react.termos.ReactTagClose;
import gm.languages.react.termos.ReactTagOk;
import gm.languages.react.termos.ReactTagOpen;
import gm.languages.react.termos.ReactTagParamClose;
import gm.languages.react.termos.ReactTagParamOpen;
import gm.languages.react.termos.StateConhecido;
import gm.languages.react.termos.StateConhecidoClose;
import gm.languages.react.termos.StateGet;
import gm.languages.react.termos.StateIsEmpty;
import gm.languages.react.termos.StateSetClose;
import gm.languages.react.termos.StateSetOpen;
import gm.utils.comum.Lst;
import gm.utils.exception.NaoImplementadoException;
import gm.utils.string.ListString;
import src.commom.utils.string.StringPrimeiraMaiuscula;

public class ReactTsToJava extends PalavrasLoad {
	
	private static ListString exemplo = new ListString();
	static {
//		exemplo.add("const [carregando, setCarregando] = useState<boolean>(false);");
//		exemplo.add("const [cpf, setCpf] = useState<String>(\"\");");
		
		exemplo.add("      <Accordion expanded={expanded === 'panel1'} onChange={handleChange('panel1')}>");
		exemplo.add("        <AccordionSummary");
		exemplo.add("          expandIcon={<ExpandMoreIcon />}");
		exemplo.add("          aria__controls=\"panel1bh-content\"");
		exemplo.add("          id=\"panel1bh-header\"");
		exemplo.add("        >");
		exemplo.add("          <Typography sx={{ width: '33%', flexShrink: 0 }}>");
		exemplo.add("            General settings");
		exemplo.add("          </Typography>");
		exemplo.add("          <Typography sx={{ color: 'text.secondary' }}>I am an accordion</Typography>");
		exemplo.add("        </AccordionSummary>");
		exemplo.add("        <AccordionDetails>");
		exemplo.add("          <Typography>");
		exemplo.add("            Nulla facilisi. Phasellus sollicitudin nulla et quam mattis feugiat.");
		exemplo.add("            Aliquam eget maximus est, id dignissim quam.");
		exemplo.add("          </Typography>");
		exemplo.add("        </AccordionDetails>");
		exemplo.add("      </Accordion>");
		
		
	}
	
	private ListString list;
	
	public static void main(String[] args) {
//		ListString list = StringClipboard.getList();
//		list.replaceEach(s -> "exemplo.add(\"" + s + "\");");
//		list.print();
		ListString list = exemplo;
		list.trimPlus();
//		ListString list = GFile.get("C:\\opt\\desen\\gm\\cs2019\\mapa\\sipeagro\\sipeagro-web\\src\\dominios\\solicitacaoRegistroEstabelecimento\\Etapa01_InformarCpfCnpj.tsx").load();
//		ListString list = GFile.get("C:\\opt\\desen\\gm\\cs2019\\mapa\\sipeagro\\sipeagro-web\\src\\dominios\\solicitacaoRegistroEstabelecimento\\SolicitarRegistroEstabelecimentoModal.tsx").load();
		ReactTsToJava o = new ReactTsToJava(list);
		o.exec();
		o.print();
	}
	
	protected Palavra build(String s) {
		
		if (s.contentEquals("if")) {
			return new If();
		}

		if (s.contentEquals("else")) {
			return new Else();
		}

		if (s.contentEquals("while")) {
			return new While();
		}

		if (s.contentEquals("for")) {
			return new For();
		}

		if (s.contentEquals("null")) {
			return new Null();
		}

		if (s.contentEquals("return")) {
			return new Return();
		}

		if (s.contentEquals("extends")) {
			return new Extends();
		}
		
		if (s.contentEquals("instanceof")) {
			return new Instanceof();
		}

		if (s.contentEquals("import")) {
			return new Import();
		}
		
		return null;
		
	}
	
	public ReactTsToJava(ListString list) {
		this.list = list;
		palavras.funcBuildPalavra = this::build;
	}
	
	@Override
	protected void exec1() {
		vincular_aberturas_de_parenteses_ao_fechamento();
		vincular_aberturas_de_chaves_ao_fechamento();
		vincular_aberturas_de_colchetes_ao_fechamento();
		criar_tipos_conhecidos();
		detectar_parenteses_de_condicao();
		tratar_imports();
		converter_arrows();
		detectar_blocos_de_codigo();
		converter_operador_diferente();
		converter_operador_igual();
		tratar_generics();
		descobrir_tipos_por_fechamento_de_tag_com_body();
		tratar_generics();
		converter_metodos_arrow_tipados_que_possuem_blocos();
		converter_metodos_nao_arrow_tipados_que_possuem_blocos_e_parametros_com_parenteses();
		remover_literais_de_chaves();
		criar_r();
		detectar_fechamento_de_barra_tag();
		converter_fechamento_de_tags_com_body();
		converter_oks_de_tags_com_body_com_um_parametro_chaveado();
		colocar_pontoevirgula_em_finais_de_blocos_onde_terminam_com_fecha_parenteses();
		converter_props();
		converter_declaracoes_de_variaveis_tipadas();		
		converter_rparams();
		colocar_virgula_depois_de_fechamento_de_tags();
		colocar_pontoevirgula_depois_de_fechamento_de_tags();
		converter_states_conhecidos();
		vincular_closetags_a_opentags();
		converte_array_vazio();
//		assinatura_da_classe();
		colocar_get_em_states_e_props();
		remover_metodos_node_de_chaves();
//		criar_metodo_render();
		tratar_json_styles();
	}
	
	private void tratar_generics() {

		filter(Menor.class).filter(i -> {
			Palavra o = after(i);
			if (!o.is(TipoJava.class)) {
				return false;
			}
			o = after(o);
			return o.is(Maior.class) || o.is(Virgula.class);
		}).forEachInverted(i -> {
			
			GenericsAbre abre = new GenericsAbre();
			replace(i, abre);
			
			Palavra o = after(abre);
			while (!o.is(Maior.class)) {
				o = after(o);
			}
			
			replace(o, abre.getFechamento());
			
		});
		
	}

	private void tratar_json_styles() {
		filter(ReactTagParamOpen.class).filter(i -> i.name.contentEquals("style") || i.name.contentEquals("sx")).each(i -> {
			Palavra o = after(i);
			if (o instanceof AbreChaves) {
				NewJson json = new NewJson("Sto");
				replace(o, json);
				AbreChaves abre = (AbreChaves) o;
				o = after(json);
				while (o != abre.getFechamento()) {
					JsonParamOpen open = new JsonParamOpen(o.getS());
					open.setFechamento(new JsonParamClose());
					replace(o, open);
					removeAfter(open).assertt(DoisPontos.class);
					o = after(open);
					o.setEspacos(0);
					while (!o.is(Virgula.class) && o != abre.getFechamento()) {
						if (o instanceof AbreParenteses) {
							AbreParenteses ap = (AbreParenteses) o;
							o = after(ap.getFechamento());
						} else {
							o = after(o);
						}
					}
					
					o.setEspacos(0);
					boolean acabou = o == abre.getFechamento();
					replace(o, open.getFechamento());
					
					if (acabou) {
						break;
					}
					
					o = after(open.getFechamento());
					
				}
			}
		});
	}

	private void criar_metodo_render() {

		NaoClassificada override = new NaoClassificada("@Override");
		override.setQuebras(2);
		override.setTabs(1);
		
		DeclaracaoDeMetodo ultimoMetodo = filter(DeclaracaoDeMetodo.class).getLast();
		
		if (ultimoMetodo == null) {
			
			StateConhecido ultimoState = filter(StateConhecido.class).getLast();
			PropDeclare propDeclare = filter(PropDeclare.class).getLast();
			
			if (ultimoState == null && propDeclare == null) {
				addAfter(filter(FechaBloco.class).getLast().getAbertura(), override);
			} else if (ultimoState == null) {
				addAfter(propDeclare, override);
			} else if (propDeclare == null) {
				addAfter(ultimoState, override);
			} else {
				int propIndex = palavras.indexOf(propDeclare);
				int stateIndex = palavras.indexOf(ultimoState);
				if (propIndex > stateIndex) {
					addAfter(propDeclare, override);
				} else {
					addAfter(ultimoState, override);
				}
			}
			
		} else {
			AbreParenteses abreParenteses = after(ultimoMetodo);
			AbreBloco abreBloco = after(abreParenteses.getFechamento());
			addAfter(abreBloco.getFechamento(), override);
		}
		
		NaoClassificada dec = new NaoClassificada("protected ReactNode render()");
		dec.setQuebras(1);
		dec.setTabs(1);
		addAfter(override, dec);
		
		AbreBloco abreBloco = new AbreBloco();
		abreBloco.setEspacos(1);
		addAfter(dec, abreBloco);
		abreBloco.setFechamento(new FechaBloco());
		abreBloco.getFechamento().setQuebras(1);
		abreBloco.getFechamento().setTabs(1);
		
		FechaBloco ultimoBloco = filter(FechaBloco.class).getLast();
		addBefore(ultimoBloco, abreBloco.getFechamento());
		
		Palavra o = after(abreBloco);
		while (o != abreBloco.getFechamento()) {			
			if (o.hasQuebra()) {
				o.incTab();
			}
			o = after(o);
		}
		
		mover_useEffect_para_dentro_do_render(abreBloco);
		
	}

	private void mover_useEffect_para_dentro_do_render(Palavra posicao) {

		Lst<Palavra> useEffects = filter(i -> i.eq("useEffect"));
		
		for (Palavra useEffect : useEffects) {
			
			useEffect.setTabs(2);
			
			Palavra antes = before(useEffect);
			AbreParenteses abre = after(useEffect);
			PontoEVirgula pv = after(abre.getFechamento());
			
			Palavra o;
			
			do {
				o = after(antes);
				remove(o);
				addAfter(posicao, o);
				posicao = o;
			} while (o != pv);
			
		}
		
	}

	private void remover_metodos_node_de_chaves() {
		
		filter(DeclaracaoDeMetodo.class).each(m -> {
			filter(NaoClassificada.class).filter(i -> i.eq(m.nome)).each(i -> {
				if (before(i) instanceof AbreChaves) {
					AbreChaves abreChaves = before(i);
					i.absorverIdentacao(abreChaves);
					remove(abreChaves);
					remove(abreChaves.getFechamento());
					AbreParenteses abre = after(i);
					Palavra o = after(abre.getFechamento());
					if (o instanceof ReactTagOpen) {
						addAfter(abre.getFechamento(), new Virgula());
					}
					o = before(i);
					if (o instanceof ReactTagClose) {
						addAfter(o, new Virgula());
					}
				}
			});
		});

		filter(StateGet.class).each(i -> {
			if (before(i) instanceof AbreChaves) {
				AbreChaves abreChaves = before(i);
				i.absorverIdentacao(abreChaves);
				remove(abreChaves);
				remove(abreChaves.getFechamento());
				Palavra o = after(i);
				if (o instanceof ReactTagOpen) {
					addAfter(i, new Virgula());
				}
				o = before(i);
				if (o instanceof ReactTagClose) {
					addAfter(o, new Virgula());
				}
			}
		});
		
		
	}

	private void colocar_get_em_states_e_props() {
		filter(StateConhecido.class).each(i ->
			filter(NaoClassificada.class).filter(n -> n.eq(i.nome)).each(n -> replace(n, new StateGet(i.nome)))
		);
		filter(PropDeclare.class).each(i ->
			filter(NaoClassificada.class).filter(n -> n.eq(i.nome)).each(n -> replace(n, new StateGet(i.nome)))
		);
		filter(StateGet.class).each(i -> {
			if (before(i) instanceof Exclamacao) {
				removeBefore(i);
				replace(i, new StateIsEmpty(i.nome));
			}
		});
		
		filter(StateGet.class).each(i -> 
			filter(NaoClassificada.class).filter(n -> n.eq("set"+StringPrimeiraMaiuscula.exec(i.nome))).each(n -> {
				AbreParenteses abre = after(n);
				remove(abre);
				StateSetOpen stateSetOpen = new StateSetOpen(i.nome);
				stateSetOpen.setFechamento(new StateSetClose());
				replace(n, stateSetOpen);
				replace(abre.getFechamento(), stateSetOpen.getFechamento());
			})
		);
		
		filter(StateGet.class).each(i -> {
			if (after(i) instanceof AbreParenteses) {
				AbreParenteses ap = after(i);
				CallOpen callOpen = new CallOpen();
				callOpen.setFechamento(new CallClose());
				replace(ap, callOpen);
				replace(ap.getFechamento(), callOpen.getFechamento());
			}
		});
		
		
	}

	private void assinatura_da_classe() {
		remove(palavras.get(0));
		remove(palavras.get(0));
		Palavra o = palavras.get(0);
		Assinatura assinatura = new Assinatura(o.getS().trim());
		replace(o, assinatura);
		assinatura.clearIdentacao();
		AbreBloco abreBloco = filter(AbreBloco.class).get(0);
		while (after(assinatura) != abreBloco) {
			removeAfter(assinatura);
		}
		FechaBloco fechaBloco = abreBloco.getFechamento();
		fechaBloco.clearIdentacao();
		fechaBloco.setQuebras(2);
		
		while (after(fechaBloco) != null) {
			removeAfter(fechaBloco);
		}
		
	}

	private void converte_array_vazio() {
		filter(AbreColchetes.class).each(i -> {
			if (after(i) instanceof FechaColchetes) {
				removeAfter(i);
				replace(i, new NewArray());
			}
		});
	}

	private void converter_props() {
		
		Lst<Palavra> itens = filter(i -> i.eq("Props"));
		
		if (itens.isEmpty()) {
			return;
		}
		
		Palavra propsType = itens.remove(0);
		
		removeBefore(propsType).assertt("type");
		removeAfter(propsType).assertt(Igual.class);
		AbreChaves abreChaves = after(propsType);
		remove(propsType);
		
		Palavra dec = itens.remove(0);
		removeBefore(dec).assertt(DoisPontos.class);
		FechaChaves fechaChavesParametro = before(dec);
		AbreChaves abreChavesParametro = fechaChavesParametro.getAbertura();
		
		AbreBloco abreBloco = after(after(dec));
		removeBefore(dec);
		remove(dec);
		
		Palavra apos = abreBloco;
		
		while (true) {
			
			PropDeclare prop = new PropDeclare(removeAfter(abreChaves).getS());
			
			Palavra o = removeAfter(abreChaves);
			
			if (o instanceof Interrogacao) {
				prop.setReq(false);
				o = removeAfter(abreChaves);
			}
			
			o.assertt(DoisPontos.class);
			
			while (!after(abreChaves).is(PontoEVirgula.class)) {
				prop.addTipo(removeAfter(abreChaves));
			}
			
			removeAfter(abreChaves);//pontoEVirgula
			
			prop.setQuebras(apos == abreBloco ? 2 : 1);
			prop.setTabs(1);
			
			addAfter(apos, prop);
			apos = prop;
			
			o = after(abreChavesParametro);
			while (o != fechaChavesParametro) {
				if (o.eq(prop.nome)) {
					if (after(o) instanceof Igual) {
						removeAfter(o);
						prop.setValue(removeAfter(o));
					}
					Palavra after = after(o);
					remove(o);
					if (after instanceof Virgula) {
						o = after(after);
						remove(after);
					} else {
						o = after;
					}
					break;
				}
			}
			
			if (after(abreChaves) == abreChaves.getFechamento()) {
				remove(abreChaves);
				remove(abreChaves.getFechamento());
				remove(abreChavesParametro);
				remove(fechaChavesParametro);
				break;
			}
		}
		
		
	}

	private void vincular_closetags_a_opentags() {
		
		filter(ReactTagClose.class).each(i -> {
			
			if (i.getOk() != null) {
				return;
			}
			
			Palavra o = before(i);
			
			while (o != null) {
				
				if (o instanceof ReactTagOk) {
					ReactTagOk ok = (ReactTagOk) o;
					if (ok.getFechamento() == null) {
						ok.setFechamento(i);
						return;
					}
				}
				
				o = before(o);
				
			}
			
		});
	}

	private void converter_states_conhecidos() {
//		converter_state_conhecido("String");
//		converter_state_conhecido("Boolean");
//		converter_state_conhecido("Int");
//		converter_state_conhecido("boolean");
//	}
//	
//	private void converter_state_conhecido(String tipo) {
		
//		const [cpf, setCpf] = useState<String>("");
		
		filter(i -> i.eq("useState")).each(i -> {
			
			if (!(after(i) instanceof GenericsAbre)) {
				return;
			}
			
			GenericsAbre genericsOpen = after(i);
			
			TipoJava tipo = after(genericsOpen);
			
//			if (!after(genericsOpen).eq(tipo)) {
//				return;
//			}
			
			removeAfter(i);// <
			removeAfter(i);// String
			removeAfter(i);// >

			AbreParenteses abreParenteses = removeAfter(i);
			
			removeBefore(i).assertt(Igual.class);
			removeBefore(i).assertt(FechaColchetes.class);
			removeBefore(i).assertt(NaoClassificada.class);
			removeBefore(i).assertt(Virgula.class);
			NaoClassificada nome = removeBefore(i);
			removeBefore(i).assertt(AbreColchetes.class);
			
			Palavra constt = before(i).assertt("const");
			remove(i);
			
			StateConhecido stateString = new StateConhecido(nome.getS().trim(), tipo.getSimpleName());
			stateString.setFechamento(new StateConhecidoClose());
			
			replace(abreParenteses.getFechamento(), stateString.getFechamento());
			replace(constt, stateString);
			
		});
		
	}

	private void colocar_virgula_depois_de_fechamento_de_tags() {
		filter(ReactTagClose.class).each(i -> {
			if (after(i) instanceof ReactTagOpen) {
				addAfter(i, new Virgula());
			}
		});
	}
	
	private void colocar_pontoevirgula_depois_de_fechamento_de_tags() {
		filter(ReactTagClose.class).each(i -> {
			if (after(i) instanceof FechaBloco) {
				addAfter(i, new PontoEVirgula());
			}
		});
	}
	
	private void converter_rparams() {
		
		filter(ReactTagOpen.class).each(i -> {
			
			Palavra o = after(i);

			if (o instanceof ReactTagOk) {
				ReactTagOk ok = (ReactTagOk) o;
				i.setOk(ok);
				return;
			}
			
			while (true) {
				
				NaoClassificada nome = (NaoClassificada) o;
				ReactTagParamOpen open = new ReactTagParamOpen(i, nome.getS());
				open.setFechamento(new ReactTagParamClose());
				
				replace(nome, open);
				
				if (after(open) instanceof Igual) {
					removeAfter(open);
					if (after(open) instanceof Literal) {
						addAfter(after(open), open.getFechamento());
					} else if (after(open) instanceof AbreChaves) {
						AbreChaves abreChaves = after(open);
						FechaChaves fechaChaves = abreChaves.getFechamento();
						addAfter(fechaChaves, open.getFechamento());
						after(abreChaves).absorverIdentacao(abreChaves);
						remove(abreChaves);
						after(fechaChaves).absorverIdentacao(fechaChaves);
						remove(fechaChaves);
					} else {
						throw new NaoImplementadoException();
					}
				} else {
					addAfter(open, open.getFechamento());
				}
				
				o = after(open.getFechamento());

				if (o instanceof ReactTagOk) {
					ReactTagOk ok = (ReactTagOk) o;
					i.setOk(ok);
					break;
				}
				
				if (o instanceof Maior) {
					i.setOk(new ReactTagOk());
					replace(o, i.getOk());
					break;
				}
				
			}
			
			
		});
	}

	private void converter_oks_de_tags_com_body_com_um_parametro_chaveado() {
		filter(ReactTagClose.class)
		.filter(i -> i.getOk() == null)
		.filter(i -> before(i) instanceof FechaChaves)
		.each(close -> {
			FechaChaves fecha = before(close);
			Palavra maior = before(fecha.getAbertura());
			if (maior instanceof Maior) {
				close.setOk(new ReactTagOk());
				replace(maior, close.getOk());
			}
		});
	}

	private void detectar_fechamento_de_barra_tag() {
		filter(Barra.class).filter(i -> after(i) instanceof Maior).each(barra -> {
			Palavra o = before(barra);
			while (true) {
				if (o instanceof ReactTagOpen) {
					ReactTagOpen open = (ReactTagOpen) o;
					if (open.getOk() == null) {
						open.setOk(new ReactTagOk());
						removeAfter(barra);
						replace(barra, open.getOk());
						open.getOk().setFechamento(new ReactTagClose());
						addAfter(open.getOk(), open.getOk().getFechamento());
						return;
					}
				}
				o = before(o);
			}
		});
	}

	private void converter_declaracoes_de_variaveis_tipadas() {
		
		filter(DoisPontos.class).each(doisPontos -> {
			
			Palavra tipo = after(doisPontos);
			
			if (!(tipo instanceof TipoJava)) {
				return;
			}
			
			Palavra nome = before(doisPontos);
			
			Palavra constt = before(nome);
			
			remove(doisPontos);
			
			nome.setEspacos(1);
			remove(tipo);
			
			if (constt.eq("const") || constt.eq("let") || constt.eq("var")) {
				replace(constt, tipo);
			} else {
				tipo.clearIdentacao();
				
				Comentario comentario = new Comentario();
				comentario.add(new ComentarioBlocoOpen());
				comentario.add(tipo);
				comentario.add(new ComentarioBlocoClose());
				
				addBefore(nome, comentario);
			}
			
		});
		
	}

	private void converter_fechamento_de_tags_com_body() {
		filter(TipoJava.class).each(i -> {
			Palavra barra = before(i);
			if (barra instanceof Barra) {
				Palavra menor = before(barra);
				if (menor instanceof Menor) {
					Palavra maior = after(i);
					if (maior instanceof Maior) {
						remove(i);
						remove(barra);
						remove(maior);
						replace(menor, new ReactTagClose());
					}
				}
			}
		});
	}

	private void colocar_pontoevirgula_em_finais_de_blocos_onde_terminam_com_fecha_parenteses() {
		filter(FechaBloco.class).forEach(i -> {
			if (before(i) instanceof FechaParenteses) {
				addBefore(i, new PontoEVirgula());
			}
		});
	}

	private void remover_literais_de_chaves() {
		filter(i -> i instanceof Literal).forEach(i -> {
			if (before(i).is(AbreChaves.class) && after(i).is(FechaChaves.class)) {
				Palavra before = before(i);
				removeAfter(i);
				remove(i);
				replace(before, i);
			}
		});
	}

	private void converter_metodos_arrow_tipados_que_possuem_blocos() {
		
		/*
		de    const isInvalid = () : boolean => {
		para  private boolean isInvalid() {
		*/
		filter(Arrow.class).forEach(arrow -> {
			
			if (!after(arrow).is(AbreBloco.class)) {
				return;
			}
			
			AbreBloco abreBloco = after(arrow);
			
			if (!before(arrow).is(TipoJava.class)) {
				return;
			}
			
			TipoJava tipo = before(arrow);
			
			if (!before(tipo).is(DoisPontos.class)) {
				return;
			}
			
			DoisPontos doisPontos = before(tipo); 

			if (!before(doisPontos).is(FechaParenteses.class)) {
				return;
			}
			
			FechaParenteses fecha = before(doisPontos);

			if (!before(fecha.getAbertura()).is(Igual.class)) {
				return;
			}
			
			Igual igual = before(fecha.getAbertura());
			
			if (!before(igual).is(NaoClassificada.class)) {
				return;
			}
			
			NaoClassificada nome = before(igual);
			
			if (!before(nome).eq("const")) {
				return;
			}
			
			NaoClassificada constt = before(nome);
			
			/*
			de    const isInvalid = () : boolean => {
			para  private boolean isInvalid() {
			*/
			
			Private privatee = new Private();
			
			replace(constt, privatee);
			remove(tipo);
			addAfter(privatee, tipo);
			remove(igual);
			remove(doisPontos);
			remove(arrow);
			
			tipo.clearIdentacao();
			tipo.setEspacos(1);
			
			nome.clearIdentacao();
			nome.setEspacos(1);
			
			replace(nome, new DeclaracaoDeMetodo(nome.getS().trim()));
			
			fecha.getAbertura().clearIdentacao();
			fecha.clearIdentacao();
			
			abreBloco.clearIdentacao();
			abreBloco.setEspacos(1);
			
			abreBloco.getFechamento().clearIdentacao();
			
			if (after(abreBloco) == abreBloco.getFechamento()) {
				return;
			}
			
			abreBloco.getFechamento().setQuebras(1);
			abreBloco.getFechamento().setTabs(1);
			
		});
	}
	
	private void converter_metodos_nao_arrow_tipados_que_possuem_blocos_e_parametros_com_parenteses() {
		
		/*
		de    const isInvalid = () : boolean => {
		para  private boolean isInvalid() {
		*/
		filter(Arrow.class).forEach(arrow -> {
			
			if (!after(arrow).is(AbreBloco.class)) {
				return;
			}
			
			AbreBloco abreBloco = after(arrow);

			if (!before(arrow).is(FechaParenteses.class)) {
				return;
			}
			
			FechaParenteses fecha = before(arrow);

			if (!before(fecha.getAbertura()).is(Igual.class)) {
				return;
			}
			
			Igual igual = before(fecha.getAbertura());
			
			if (!before(igual).is(NaoClassificada.class)) {
				return;
			}
			
			NaoClassificada nome = before(igual);
			
			if (!before(nome).eq("const")) {
				return;
			}
			
			NaoClassificada constt = before(nome);
			
			/*
			de    const isInvalid = () : boolean => {
			para  private boolean isInvalid() {
			*/
			
			Private privatee = new Private();
			
			TipoJava tipo = new TipoJava(void.class);
			
			replace(constt, privatee);
			addAfter(privatee, tipo);
			remove(igual);
			remove(arrow);
			
			tipo.setEspacos(1);
			
			nome.clearIdentacao();
			nome.setEspacos(1);
			
			replace(nome, new DeclaracaoDeMetodo(nome.getS().trim()));
			
			fecha.getAbertura().clearIdentacao();
			fecha.clearIdentacao();
			
			abreBloco.clearIdentacao();
			abreBloco.setEspacos(1);
			
			abreBloco.getFechamento().clearIdentacao();
			
			if (after(abreBloco) == abreBloco.getFechamento()) {
				return;
			}
			
			abreBloco.getFechamento().setQuebras(1);
			abreBloco.getFechamento().setTabs(1);
			
		});
	}	

	private void criar_tipos_conhecidos() {
		criar_tipo("boolean", boolean.class);
		criar_tipo("Boolean", Boolean.class);
		criar_tipo("str", String.class);
		criar_tipo("string", String.class);
		criar_tipo("String", String.class);
		criar_tipo("int", int.class);
		criar_tipo("Integer", Integer.class);
		criar_tipo("void", void.class);
		criar_tipo("any", Object.class);
	}

	private void criar_tipo(String name, Class<?> classe) {
		filter(i -> i.eq(name)).forEach(i -> replace(i, new TipoJava(classe)));
	}
	
	private void detectar_parenteses_de_condicao() {
		detectar_parenteses_de_condicao(If.class);
		detectar_parenteses_de_condicao(While.class);
		detectar_parenteses_de_condicao(For.class);
	}

	private void detectar_parenteses_de_condicao(Class<? extends Palavra> classe) {
		filter(classe).each(i -> {
			AbreParenteses abre = after(i);
			ParentesesCondicaoOpen open = new ParentesesCondicaoOpen();
			open.setFechamento(new ParentesesCondicaoClose());
			replace(abre, open);
			replace(abre.getFechamento(), open.getFechamento());
		});
	}

	private void detectar_blocos_de_codigo() {
		detectar_blocos_de_codigo(ParentesesCondicaoClose.class);
		detectar_blocos_de_codigo(Arrow.class);
		detectar_blocos_de_codigo(Else.class);
	}
	
	private void detectar_blocos_de_codigo(Class<? extends Palavra> classe) {
		filter(classe).each(i -> {
			Palavra o = after(i);
			if (o instanceof AbreChaves) {
				AbreChaves abre = (AbreChaves) o;
				AbreBloco open = new AbreBloco();
				open.setFechamento(new FechaBloco());
				replace(abre, open);
				replace(abre.getFechamento(), open.getFechamento());
			}
		});
	}

	private void vincular_aberturas_de_chaves_ao_fechamento() {
		filter(AbreChaves.class).forEachInverted(abre -> {
			Palavra o = after(abre);
			while (abre.getFechamento() == null) {
				if (o.is(FechaChaves.class)) {
					FechaChaves fecha = (FechaChaves) o;
					if (fecha.getAbertura() == null) {
						abre.setFechamento(fecha);
						break;
					}
				}
				o = after(o);
			}
		});
	}

	private void vincular_aberturas_de_colchetes_ao_fechamento() {
		filter(AbreColchetes.class).forEachInverted(abre -> {
			Palavra o = after(abre);
			while (abre.getFechamento() == null) {
				if (o.is(FechaColchetes.class)) {
					FechaColchetes fecha = (FechaColchetes) o;
					if (fecha.getAbertura() == null) {
						abre.setFechamento(fecha);
						break;
					}
				}
				o = after(o);
			}			
		});
	}
	
	private void vincular_aberturas_de_parenteses_ao_fechamento() {
		filter(AbreParenteses.class).forEachInverted(abre -> {
			Palavra o = after(abre);
			while (abre.getFechamento() == null) {
				if (o.is(FechaParenteses.class)) {
					FechaParenteses fecha = (FechaParenteses) o;
					if (fecha.getAbertura() == null) {
						abre.setFechamento(fecha);
						break;
					}
				}
				o = after(o);
			}
		});
	}

	private void converter_operador_igual() {
		filter(Igual.class).forEachInverted(i -> {
			Palavra a = after(i);
			Palavra b = after(a);
			if (a.is(Igual.class) && b.is(Igual.class)) {
				remove(a);
				remove(b);
				replace(i, new IgualComparacao());
			}
		});
		filter(Igual.class).forEachInverted(i -> {
			Palavra a = after(i);
			if (a.is(Igual.class)) {
				remove(a);
				replace(i, new IgualComparacao());
			}
		});
	}

	private void converter_operador_diferente() {
		filter(Exclamacao.class).each(i -> {
			Palavra a = after(i);
			Palavra b = after(a);
			if (a.is(Igual.class) && b.is(Igual.class)) {
				remove(a);
				remove(b);
				replace(i, new Diferente());
			}
		});
		filter(Exclamacao.class).forEachInverted(i -> {
			Palavra a = after(i);
			if (a.is(Igual.class)) {
				remove(a);
				replace(i, new Diferente());
			}
		});
	}

	private void converter_arrows() {
		filter(Igual.class).each(i -> {
			if (after(i).is(Maior.class)) {
				removeAfter(i);
				replace(i, new Arrow());
			}
		});
	}

	private void descobrir_tipos_por_fechamento_de_tag_com_body() {
		
		ListString tipos = new ListString();
		
		filter(Menor.class).each(i -> {
//			</Alert>
			Palavra o = after(i);
			if (!(o instanceof Barra)) {
				return;
			}
			
			o = after(o);
			if (!(o instanceof NaoClassificada)) {
				return;
			}
			
			if (!(after(o) instanceof Maior)) {
				return;
			}
			
			tipos.addIfNotContains(o.getS());
			
		});
		
		criar_tipos_java(tipos);
		
	}

	private void criar_r() {

		filter(TipoJava.class).forEachInverted(tipo -> {
			
			Palavra o = before(tipo);
			
			if (!(o instanceof Menor)) {
				return;
			}
			
			if (before(o) instanceof TipoJava) {
				/* significa que eh um generics */
				return;
			}
			
			if (after(tipo) instanceof Ponto) {
				return;
			}
			
			remove(tipo);
			ReactTagOpen tag = new ReactTagOpen(tipo);
			replace(o, tag);
			
			if (after(tag) instanceof Barra) {
				Barra barra = after(tag);
				if (after(barra) instanceof Maior) {
					removeAfter(barra);
					tag.setOk(new ReactTagOk());
					replace(barra, tag.getOk());
					tag.getOk().setFechamento(new ReactTagClose());
					addAfter(tag.getOk(), tag.getOk().getFechamento());
				}
			}
			
		});
		
	}

	private void tratar_imports() {

		ListString tipos = new ListString();
		
		filter(Import.class).each(i -> {
			
			Palavra a = after(i);
			
			if (a instanceof AbreChaves) {
				
				AbreChaves abre = (AbreChaves) a;
				Palavra o = after(a);
				while (o != abre.getFechamento()) {
					
					if (!(o instanceof Virgula)) {
						tipos.addIfNotContains(o.getS());
					}
					
					o = after(o);
				}
				
			} else {
				tipos.addIfNotContains(a.getS());
			}
			
		});

		filter(Import.class).each(i -> {
			while (!removeAfter(i).is(PontoEVirgula.class));
			remove(i);
		});
		
		criar_tipos_java(tipos);
		
	}

	private void criar_tipos_java(ListString tipos) {
		Lst<NaoClassificada> filter = filter(NaoClassificada.class).filter(i -> tipos.contains(i.getS().trim()));
		filter.each(i -> {
			if (!(before(i) instanceof Ponto)) {
				replace(i, new TipoJava(i.getS()));
			}
		});
	}

	@Override
	protected ListString loadListString() {
		return list;
	}
	
}
