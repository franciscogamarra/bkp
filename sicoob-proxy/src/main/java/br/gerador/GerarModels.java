package br.gerador;

import br.definicoes.Campo;
import br.definicoes.Model;
import br.definicoes.Sistema;
import br.definicoes.TipoCampo;
import br.gerar.CampoTipo;
import br.gerar.angular.support.GerarTs;
import br.gerar.angular.support.TipoTsAngular;
import br.gerar.ts.TipoTs;
import br.support.comum.Lst;
import br.support.exceptions.NaoImplementadoException;
import br.support.strings.StringTrim;

public class GerarModels {

	private static void exec(Model model, GerarTs lst) {
		
//		GerarTs lst = new GerarTs(model, "cadastro", null);
		lst.addImport(TipoTsAngular.tFormGroup);
		lst.addImport(TipoTsRural.Campo);
		lst.addImport(TipoTs.tLst);
		
		String E = model.getNome();
		
		Lst<String> constructorParams = new Lst<>();
		constructorParams.add("private readonly form : FormGroup");
		
		lst.add("export class " + E + " {");
		lst.add();
		lst.add("<constructor>");
		
		boolean api = false;
		
		Lst<Campo> campos = model.getCampos();
		
		for (Campo campo : campos) {
			
			CampoTipo tipo = getTipo(campo);
			
			String nullValue = "null";
			
			if (tipo == CampoTipo.select) {
				nullValue = "SelectOption.NULL";
			}
			
			boolean required = !campo.isReadOnly() && campo.front.isNotNull();
			
			lst.add();
			lst.add("public readonly "+campo.getNome()+" : Campo = new Campo(");
			lst.add("	this.form,");
			lst.add("	"+toString(campo.getNome())+",");
			lst.add("	"+toString(campo.front.edit.getTitulo())+",");
			lst.add("	'"+tipo+"',");
			lst.add("	"+required+",");
			lst.add("	"+nullValue+",");
			lst.add(")");
			
			if (campo.front.edit.rows > 1) {
				lst.add(lst.removeLast() + ".setRows("+campo.front.edit.rows+")");
			}
			
			if (tipo == CampoTipo.select) {
				
				String last = lst.removeLast();
				
				lst.add(last + ".setGetOptions(() => {");
				
				boolean abortar = false;
				
				if (campo.getTipo() == TipoCampo.BOOLEAN) {
					lst.addImport(TipoTs.PromiseBuilder);
					lst.addImport(TipoTsRural.SelectOption);
					lst.add("return PromiseBuilder.ft(() => [SelectOption.NULL].concat(Campo.booleanOptions));");
				} else {

					Model ref = campo.getModelReferencia();
					
					if (ref.isEnumm()) {
						lst.add("return PromiseBuilder.ft(() => ["+nullValue+"].concat("+ref.getNome()+".OPTIONS));");
					} else if (campo.back.getEndPointGetOptions() == null) {
						abortar = true;
					} else {
						lst.addImport(TipoTsRural.SelectOption);
						lst.addImport(TipoTsRural.SimpleOptionDto);
						api = true;
						String url = campo.back.getEndPointGetOptions();
						lst.add("return this.api.get('"+url+"').then(dtos =>");
						lst.add("	["+nullValue+"].concat(dtos.map(dto => SelectOption.buildSimple(dto as SimpleOptionDto)))");
						lst.add(");");
					}
					
				}

				if (abortar) {
					lst.removeLast();
					lst.add(last);
				} else {
					lst.add("})");
				}
//				lst.add(lst.removeLast() + ".setPrepareSetValueCutom(value => {");
//				lst.add("if (value.id) {");
//				lst.add("	return Imovel.fromDto(value);");
//				lst.add("}");
//				lst.add("return value;");
				
//				if (value.id) {
//					const i : Imovel = Imovel.get(value.id);
//					i.readDto(value);
//					return i;
//				}
//				return null;
//			});				
				
			} else if (tipo == CampoTipo.bool) {
			}
			
//			Lst<Condicao> visivelSeList = campo.getVisivelSe();
//			for (Condicao visivelSe : visivelSeList) {
//				list.add("	.addVisibleCondition(() => "+visivelSe.call()+")");
//			}
			
			if (campo.isReadOnly()) {
				lst.add(lst.removeLast() + ".setReadOnly()");
			}
			
			if (campo.getTipo() == TipoCampo.BIGDECIMAL) {
				lst.add(lst.removeLast() + ".setCasasInteiras("+campo.bigdecimal.inteiros+")");
				lst.add(lst.removeLast() + ".setCasasDecimais("+campo.bigdecimal.decimais+")");
				if (campo.getMin() > 0) {
					String s = campo.getMin() + "." + "0".repeat(campo.bigdecimal.decimais);
					lst.add(lst.removeLast() + ".setMin("+s+")");
				}
			}

			if (campo.getMax() > 0) {
				String s = campo.getMax() + "";
				if (campo.getTipo() == TipoCampo.BIGDECIMAL) {
					s += "." + "0".repeat(campo.bigdecimal.decimais);
				}
				lst.add(lst.removeLast() + ".setMax("+s+")");
			}
			
			
//			if (!campo.showTextoErro) {
//				list.add(list.removeLast() + ".setShowTextoErro(false)");
//			}
//
//			if (campo.bold) {
//				list.add(list.removeLast() + ".setBold()");
//			}
			
			lst.add(lst.removeLast() + ";");
			lst.add("");

		}
		if (api) {
			lst.addImport(TipoTsRural.api);
			constructorParams.add(TipoTsRural.apiInject);
		}
		
		int index = lst.rows.indexOf("<constructor>");
		lst.rows.remove(index);
		
		lst.rows.add(index, "constructor(");
		index++;
		
		while (!constructorParams.isEmpty()) {
			lst.rows.add(index, constructorParams.remove(0) + ",");
			index++;
		}

		lst.rows.add(index, ") {}");
		index++;
		
		campos.removeIf(campo -> campo.front.edit.lupa);
		
		lst.add("public getCampos() : Lst<Campo> {");
		lst.add("	const list : Lst<Campo> = new Lst<Campo>();");
		for (Campo campo : campos) {
			lst.add("list.add(this."+campo.getNome()+");");
		}
		lst.add("	return list;");
		lst.add("}//getCampos");
		lst.add();
		lst.add("public readDto(dto : "+E+"Dto) : "+E+" {");
		for (Campo campo : campos) {
			lst.add("this."+campo.getNome()+".setValue(dto."+campo.getNome()+");");	
		}
		lst.add("	return this;");
		lst.add("}//readDto");
		lst.add();
		
//		lst.add("private static readonly cache : "+E+"[] = [];");
//		lst.add("public static fromDto(dto : "+E+"Dto) : "+E+" {");
//		lst.add("	let o : "+E+";");
//		lst.add("	if (dto && dto.id) {");
//		lst.add("		o = this.cache.find(item => item.id === dto.id);");
//		lst.add("		if (!o) {");
//		lst.add("			o = new "+E+"();");
//		lst.add("	}");
//		lst.add("}");
		lst.add();
		lst.add("public setDto(o : "+E+"Dto) : void {");
		for (Campo campo : campos) {
			if (campo.isReadOnly() && !campo.isId()) {
				continue;
			}
			lst.add("o."+campo.getNome()+" = this."+campo.getNome()+".getValueFinal();");
		}
		lst.add("}//setDto");
		lst.add();
		lst.add("	public asDto() : "+E+"Dto {");
		lst.add("		const o : "+E+"Dto = "+E+"Support.newDto();");
		lst.add("		this.setDto(o);");
		lst.add("		return o;");
		lst.add("	}//asDto");
		lst.add();
		lst.add("}//Campos");
		lst.add();
		lst.add("export interface " + E + "Dto {");
		lst.add();
		for (Campo campo : campos) {
			
			String t;
			
			TipoCampo tipo = campo.getTipo();
			
			if (tipo == TipoCampo.ForeignKey) {
				t = campo.getReferencia() + "Dto";
//				t = TipoTsRural.SimpleOptionDto.nome;
//				lst.addImport(TipoTsRural.SimpleOptionDto);
			} else if (tipo == TipoCampo.BIGDECIMAL) {
				t = "number";
			} else if (tipo == TipoCampo.INT) {
				lst.addImport(TipoTs.tint);
				t = "int";
			} else if (tipo == TipoCampo.STRING) {
				t = "string";
			} else if (tipo == TipoCampo.CPFouCNPJ) {
				t = "string";
			} else if (tipo == TipoCampo.BOOLEAN) {
				t = "boolean";
			} else if (tipo == TipoCampo.Filhos) {
				t = campo.getReferencia()+"Dto[]";
			} else {
				throw new NaoImplementadoException(tipo);
			}
			
			lst.add(campo.getNome()+"? : "+t+" | null | undefined,");
			
		}
		
		lst.add(model.front.cadastroCampos.codigoEmbutidoDto);
		lst.add();
		lst.add("}//Dto");
		lst.add();
		lst.add("export class " + E + "Support {");
		lst.add("public static newDto() : "+E+"Dto {");
		lst.add("	return {");
		for (Campo campo : campos) {
			
			TipoCampo tipo = campo.getTipo();
			
			String s = null;
			if (tipo == TipoCampo.BIGDECIMAL) {
				s = "0";
			} else if (tipo == TipoCampo.INT) {
				s = "0";
			} else if (tipo == TipoCampo.STRING) {
				s = "''";
			} else if (tipo == TipoCampo.CPFouCNPJ) {
				s = "''";
			} else if (tipo == TipoCampo.BOOLEAN) {
				s = "false";
			} else if (tipo == TipoCampo.Filhos) {
				s = "[]";
			} else if (tipo == TipoCampo.ForeignKey) {
				//
			} else {
				throw new NaoImplementadoException(tipo);
			}
			
			if (s != null) {
				lst.add(campo.getNome()+" : "+s+",");
			}
			
		}
		lst.add("}");
		lst.add("}//newDto");
		lst.add();
		lst.add("public static cloneDto(origem : "+E+"Dto) : "+E+"Dto {");
		lst.add("	return {");
		for (Campo campo : campos) {
			lst.add(campo.getNome()+" : origem."+campo.getNome()+",");
		}
		lst.add(model.front.cadastroCampos.codigoEmbutidoCloneDto);
		lst.add("	};");
		lst.add("}//cloneDto");
		
		lst.add("}");
		
//		export class ImovelRegistro {
//
//			  public imovel: ImovelOption = ImovelOption.NULL;
//			  public qtdExplorada: number | null = null;
//			  public qtdGleba: number = 0;
//			  public proprietario: string = '';
//			  public listaGlebaDTO: Record<string, unknown>[] = [];
//
//			  //sempre que adicionar um atribuito lembrar de adicionar no clone
//			  public clone(): ImovelRegistro {
//			    const o = new ImovelRegistro();
//			    o.imovel = this.imovel;
//			    o.qtdExplorada = this.qtdExplorada;
//			    o.qtdGleba = this.qtdGleba;
//			    o.proprietario = this.proprietario;
//			    o.listaGlebaDTO = this.listaGlebaDTO.map(item => ({ ...item }));
//			    return o;
//			  }
//
//			}//ImovelRegistro		
		
		
//		lst.save();
		
	}

	private static CampoTipo getTipo(Campo campo) {
		
		if (campo.getTipo() == TipoCampo.ForeignKey) {
			return CampoTipo.select;
		}
		
		if (campo.getTipo() == TipoCampo.STRING) {
			return CampoTipo.text;
		}

		if (campo.getTipo() == TipoCampo.CPFouCNPJ) {
			return CampoTipo.pessoa;
		}

		if (campo.getTipo() == TipoCampo.CNPJ) {
			return CampoTipo.cnpj;
		}

		if (campo.getTipo() == TipoCampo.PERCENTUAL) {
			return CampoTipo.percentual;
		}

		if (campo.getTipo() == TipoCampo.BIGDECIMAL) {
			return CampoTipo.number;
		}

		if (campo.getTipo() == TipoCampo.DATA) {
			return CampoTipo.data;
		}
		
		if (campo.getTipo() == TipoCampo.INT) {
			return CampoTipo.number;
		}

		if (campo.getTipo() == TipoCampo.BOOLEAN) {
			return CampoTipo.select;
		}

		if (campo.getTipo() == TipoCampo.Filhos) {
			return CampoTipo.filhos;
		}
		
		throw new NaoImplementadoException(campo.toStringDebug());
		
	}

	public static void exec(Sistema sistema) {
		GerarTs lst = new GerarTs(sistema, "", "Models");
		sistema.getModels().forEach(model -> exec(model, lst));
		lst.save();
	}

	private static String toString(String s) {
		s = StringTrim.plusNull(s);
		return s == null ? "\"\"" : "\""+s+"\"";
	}
	
}