package gm.languages.sql;

import java.io.File;
import java.util.function.Predicate;

import gm.languages.palavras.DefaultReplaces;
import gm.languages.palavras.Is0;
import gm.languages.palavras.Is1;
import gm.languages.palavras.Linguagem;
import gm.languages.palavras.OperadorComparacao;
import gm.languages.palavras.Palavra;
import gm.languages.palavras.Palavras;
import gm.languages.palavras.ReplacerLst;
import gm.languages.palavras.SinalMatematico;
import gm.languages.palavras.comuns.And;
import gm.languages.palavras.comuns.AndOr;
import gm.languages.palavras.comuns.Diferente;
import gm.languages.palavras.comuns.Else;
import gm.languages.palavras.comuns.If;
import gm.languages.palavras.comuns.IfWhile;
import gm.languages.palavras.comuns.NaoClassificada;
import gm.languages.palavras.comuns.Null;
import gm.languages.palavras.comuns.Or;
import gm.languages.palavras.comuns.literal.InteiroLiteral;
import gm.languages.palavras.comuns.literal.StringLiteral;
import gm.languages.palavras.comuns.simples.AbreColchetes;
import gm.languages.palavras.comuns.simples.AbreParenteses;
import gm.languages.palavras.comuns.simples.AspasSimples;
import gm.languages.palavras.comuns.simples.FechaColchetes;
import gm.languages.palavras.comuns.simples.FechaParenteses;
import gm.languages.palavras.comuns.simples.Igual;
import gm.languages.palavras.comuns.simples.Maior;
import gm.languages.palavras.comuns.simples.Mais;
import gm.languages.palavras.comuns.simples.Menor;
import gm.languages.palavras.comuns.simples.Menos;
import gm.languages.palavras.comuns.simples.Ponto;
import gm.languages.palavras.comuns.simples.PontoEVirgula;
import gm.languages.palavras.comuns.simples.Virgula;
import gm.languages.sql.expressoes.BeginTran;
import gm.languages.sql.expressoes.CommitTran;
import gm.languages.sql.expressoes.CreateOrAlter;
import gm.languages.sql.expressoes.EndCase;
import gm.languages.sql.expressoes.FetchNextFrom;
import gm.languages.sql.expressoes.FromAny;
import gm.languages.sql.expressoes.Funcao;
import gm.languages.sql.expressoes.FuncaoBooleana;
import gm.languages.sql.expressoes.GroupBy;
import gm.languages.sql.expressoes.InnerJoin;
import gm.languages.sql.expressoes.IsNotNull;
import gm.languages.sql.expressoes.IsNull;
import gm.languages.sql.expressoes.JoinAny;
import gm.languages.sql.expressoes.LeftJoin;
import gm.languages.sql.expressoes.NotExists;
import gm.languages.sql.expressoes.Operacao4;
import gm.languages.sql.expressoes.OrderBy;
import gm.languages.sql.expressoes.PrimaryKey;
import gm.languages.sql.expressoes.SelectEnd;
import gm.languages.sql.expressoes.SetNoCountOff;
import gm.languages.sql.expressoes.SetNoCountOn;
import gm.languages.sql.expressoes.TipoSql;
import gm.languages.sql.expressoes.WithNoLock;
import gm.languages.sql.expressoes.cte.Cte;
import gm.languages.sql.expressoes.cte.CteDeclaration;
import gm.languages.sql.expressoes.cte.CteReference;
import gm.languages.sql.expressoes.dataSet.Coluna;
import gm.languages.sql.expressoes.dataSet.DataSet;
import gm.languages.sql.expressoes.variaveis.VarDeclaration;
import gm.languages.sql.expressoes.variaveis.VarReference;
import gm.languages.sql.expressoes.variaveis.Variavel;
import gm.languages.sql.palavras.Alter;
import gm.languages.sql.palavras.As;
import gm.languages.sql.palavras.Begin;
import gm.languages.sql.palavras.By;
import gm.languages.sql.palavras.Case;
import gm.languages.sql.palavras.Char;
import gm.languages.sql.palavras.Commit;
import gm.languages.sql.palavras.Create;
import gm.languages.sql.palavras.Declare;
import gm.languages.sql.palavras.Delete;
import gm.languages.sql.palavras.End;
import gm.languages.sql.palavras.Exec;
import gm.languages.sql.palavras.Exists;
import gm.languages.sql.palavras.Fetch;
import gm.languages.sql.palavras.From;
import gm.languages.sql.palavras.Function;
import gm.languages.sql.palavras.Group;
import gm.languages.sql.palavras.Inner;
import gm.languages.sql.palavras.Insert;
import gm.languages.sql.palavras.Is;
import gm.languages.sql.palavras.Join;
import gm.languages.sql.palavras.Key;
import gm.languages.sql.palavras.Left;
import gm.languages.sql.palavras.Max;
import gm.languages.sql.palavras.Next;
import gm.languages.sql.palavras.NoCount;
import gm.languages.sql.palavras.NoLock;
import gm.languages.sql.palavras.Not;
import gm.languages.sql.palavras.Numeric;
import gm.languages.sql.palavras.Off;
import gm.languages.sql.palavras.On;
import gm.languages.sql.palavras.Order;
import gm.languages.sql.palavras.Primary;
import gm.languages.sql.palavras.Procedure;
import gm.languages.sql.palavras.Select;
import gm.languages.sql.palavras.Set;
import gm.languages.sql.palavras.Tran;
import gm.languages.sql.palavras.Update;
import gm.languages.sql.palavras.Varchar;
import gm.languages.sql.palavras.Where;
import gm.languages.sql.palavras.With;
import gm.languages.sql.proposicoesIs.CandidatoATabela;
import gm.utils.comum.Lst;
import gm.utils.comum.SystemPrint;
import gm.utils.string.ListString;
import src.commom.utils.string.StringTrim;

public class ValidarQuery {
	
	private static final String OUT = "/opt/desen/gm/cs2019/gm-utils/src/main/java/gm/utils/jpa/nativeQuery/validator/files/out/";
	private static final String FILE = "/opt/desen/gm/cs2019/cooper/infra/procedure/files/SCH_EMPRESTIMO/STP_EMP_GET_LIMITE_TELA_INICIAL_LGC.sql";
	private static final boolean corrigir = true;

	public static void main(String[] args) {
		
//		exec("select * from dbo.teste x with(nolock) inner join t0 nolock join @t1 join xx");
//		exec("select id from openxml (@idoc, 'tb_saldos_liquidacao/row', 1) with (id int)");
		
		
		Palavra.emAnalise = true;
		
//		UFile.delete(OUT);
		
		/*
//		Lst<File> files = UFile.getAllFiles("/opt/desen/gm/cs2019/cooper/infra/procedure/files/SCH_EMPRESTIMO");
		Lst<File> files = UFile.getAllFiles("/opt/desen/gm/cs2019/cooper/infra/procedure/files");
		for (File file : files) {
			SystemPrint.ln("==================================");
			SystemPrint.ln(file);
			SystemPrint.ln("==================================");
			execFile(file);
		}
//		*/
//		saveFile();
//		execFile(Palavras.SAIDA_PADRAO);
		execFile("/opt/desen/gm/cs2019/cooper/infra/procedure/files/dbo/BAIXA_DO_DCO.sql");
//		execFile("/opt/desen/gm/cs2019/gm-utils/src/main/java/gm/utils/jpa/nativeQuery/validator/files/exemplo.sql");
		
	}

	public static void saveFile() {
		ListString lst = new ListString().load(FILE);
		lst.replacePalavra("WITH (NOLOCK)", "with(nolock)");
		lst.replacePalavra("WITH(NOLOCK)", "with(nolock)");
		lst.save(Palavras.SAIDA_PADRAO);
	}
	
	public static void execFile() {
		execFile(FILE);
	}
	
	public static void execFile(String fileName) {
		execFile(new File(fileName));
	}

	public static void execFile(File file) {
		
		ListString list = new ListString().load(file).rtrim();
		list.removeDoubleWhites();
		list.removeFisrtEmptys();
		list.removeLastEmptys();
		
		while (list.contains("<stop>")) {
			list.removeLast();
		}
		
		String ss = OUT + file.getName();
		String s = list.replaceTexto(" ", " _espaco_ ").replaceTexto("\t", " _tab_ ").toString(" _quebra_ ");
		exec(s, ss);
		
	}

	public static void exec(String sql) {
		exec(sql, Palavras.SAIDA_PADRAO);
	}

	public static void exec(String sql, String fileName) {
		Linguagem.selected = Linguagem.sql;
		new ValidarQuery(sql, fileName);
	}

	private final Palavras palavras = new Palavras();
	private final Lst<Cte> ctes = new Lst<>();
	private final Lst<Variavel> vars = new Lst<>();
	private final String fileName;

	private ValidarQuery(String sql, String fileName) {
//		String s = StringTrim.plus(sql).toLowerCase();
		String s = StringTrim.plus(sql);
		PalavrasBuildSql.start(palavras, vars);
		ListString.separaPalavras(s).trimPlus().each(i -> palavras.add(i));
		this.fileName = fileName;
		exec();
	}

	private void exec() {
		DefaultReplaces.exec(palavras);
		
		Lst<AspasSimples> aspas = palavras.filter(AspasSimples.class);
		
		if (aspas.isNotEmpty()) {
			throw new RuntimeException();
		}
		
		junta();
		corrige();
		validaWithNolock();
	}
	
	private Palavra getPrimeiroDaLinha(Palavra o) {

		while (o.getQuebras() == 0) {
			o = palavras.before(o);
		}
		
		return o;
		
	}

	private void formatarParenteses() {

		Lst<AbreParenteses> opens = palavras.filter(AbreParenteses.class).filter(i -> after(i).getQuebras() > 0);
		
		for (AbreParenteses abre : opens) {
			Palavra o = getPrimeiroDaLinha(abre);
			FechaParenteses fecha = abre.getFechamento();
			fecha.setQuebras(1);
			fecha.setTabs(o.getTabs());
			fecha.setEspacos(o.getEspacos());
		}
		
	}

	private void corrige() {
		
		if (!corrigir) {
			return;
		}
		
		Is0.func = o -> !o.is(PontoEVirgula.class, Begin.class, VarDeclaration.class, End.class);
		palavras.replace(Is0.class, Declare.class).por(lst -> {
			lst.add(1, new PontoEVirgula());
			return lst;
		});

		palavras.replace(VarDeclaration.class, Operacao4.class).por(lst -> {
			lst.add(1, new PontoEVirgula());
			return lst;
		});
		
		palavras.replace(VarDeclaration.class, Set.class).por(lst -> {
			lst.add(1, new PontoEVirgula());
			return lst;
		});

		palavras.replace(VarDeclaration.class, Igual.class, Palavra.class, Exec.class).por(lst -> {
			lst.add(3, new PontoEVirgula());
			return lst;
		});

		Is0.func = o -> !o.is(PontoEVirgula.class, End.class);
		palavras.replace(Is0.class, End.class).por(lst -> {
			lst.add(1, new PontoEVirgula());
			return lst;
		});
		
		formatarParenteses();
		removerEspacosDeItensLiteraisDentroDeUmIn();
		removerEspacosParenteses();
		colocarUmPontoEVirgulaAntesDeCadaDeclare();
		removerQuebraDesnecessariaEmDeclaracaoSimples();
		colocarCondicoesIfEntreParenteses();
		marcarParentesesDeIfs();
		
//		colocarBeginEndAntesDeUmSetElse();
		colocarBeginEndDepoisDeUmSetElse();
		colocarUmBeginEndEmUmIfSetIf();
		identar();
	}

	
	
	private void marcarParentesesDeIfs() {

		palavras.each(o -> {
			
			if (o.getId() == 2883) {
				SystemPrint.ln(o);
			}
			
			if (o.is(AbreParenteses.class)) {
				AbreParenteses abre = (AbreParenteses) o;
				if (abre.getProprietario() == null) {
					Palavra candidato = before(abre);
					if (candidato.is(IfWhile.class)) {
						FechaParenteses fecha = abre.getFechamento();
						if (fecha != null) {
							Palavra after = after(fecha);
							if (after.is(Set.class, Begin.class, Delete.class, Insert.class, Update.class)) {
								abre.setProprietario(candidato);
							}
						}
					}
				}
			}
		});
		
		palavras.saveAnalise();
		
	}

	private void colocarUmBeginEndEmUmIfSetIf() {

		/*
		de
			if ()
			set @x = 1
			if
		para
			if ()
			begin
				set @x = 1;
			end
			
			if
		*/
		
		Is0.func = o -> o.isParentesesDeFechamentoDeUmIfWhile();
		Is1.func = o -> o.is(Else.class, If.class, Exec.class, Declare.class);
		
		palavras.replace(Is0.class, Set.class, VarReference.class, Igual.class, Palavra.class, Is1.class).por(lst -> {
			
			Begin begin = new Begin();
			
			Palavra set = lst.get(1);
			begin.absorverIdentacao(set);
			
			set.setQuebras(1);
			set.setTabs(begin.getTabs()+1);

			End end = new End();
			begin.setFechamento(end);
			end.setTabs(begin.getTabs());
			end.setEspacos(begin.getEspacos());
			end.setQuebras(1);
			
			Palavra fim = lst.getLast();
			
			lst.addBefore(set, begin);
			lst.addBefore(fim, end);
			lst.addBefore(end, new PontoEVirgula());
			
			return lst;
			
		});
		
	}

	protected void colocarBeginEndAntesDeUmSetElse() {
	
		/*
		de
			set @x = 1
			else
		para
			begin
				set @x = 1;
			end
			else
		*/
		
		palavras.replace(Set.class, VarReference.class, Igual.class, Palavra.class, Else.class).por(lst -> {
			
			Begin begin = new Begin();
			
			Palavra set = lst.get(0);
			begin.absorverIdentacao(set);
			
			set.setQuebras(1);
			set.setTabs(begin.getTabs()+1);

			End end = new End();
			begin.setFechamento(end);
			end.setTabs(begin.getTabs());
			end.setEspacos(begin.getEspacos());
			end.setQuebras(1);
			
			Palavra fim = lst.getLast();
			
			lst.addBefore(set, begin);
			lst.addBefore(fim, end);
			lst.addBefore(end, new PontoEVirgula());
			
			return lst;
		});
		
	}
	/**/
	
	private void colocarBeginEndDepoisDeUmSetElse() {

		/*
		de
			else
			set @x = 1;
		para
			else
			begin
				set @x = 1;
			end
		*/

		palavras.replace(Else.class, Set.class, VarReference.class, Igual.class, Palavra.class, PontoEVirgula.class).por(lst -> {
			
			Begin begin = new Begin();
			
			Palavra els = lst.get(0);
			
			begin.copiaIdentacao(els);
			
			Palavra set = lst.get(1);
			set.clearIdentacao();
			set.setQuebras(1);
			set.setTabs(begin.getTabs()+1);

			End end = new End();
			begin.setFechamento(end);
			end.setTabs(begin.getTabs());
			end.setEspacos(begin.getEspacos());
			end.setQuebras(1);
			
			lst.addBefore(set, begin);
			lst.add(end);
			
			return lst;
			
		});
		
		/**/
		
	}

	private int identacao;
	
	private <T extends Palavra> T after(Palavra o) {
		return palavras.after(o);
	}
	
	private <T extends Palavra> T before(Palavra o) {
		return palavras.before(o);
	}
	
	public <TT extends Palavra> Lst<TT> filter(Class<TT> classe) {
		return palavras.filter(classe);
	}
	
	public <TT extends Palavra> Lst<TT> filter(Predicate<Palavra> predicate) {
		return palavras.filter(predicate);
	}
	
	private Palavra getAbertura(Palavra o) {
		
		if (o.is(End.class)) {
			End end = (End) o;
			return end.getAbertura();
		}

		if (o.is(FechaParenteses.class)) {
			FechaParenteses end = (FechaParenteses) o;
			return end.getAbertura();
		}

		if (o.is(CommitTran.class)) {
			CommitTran end = (CommitTran) o;
			return end.getAbertura();
		}
		
		return null;
		
	}
	
	private void identar() {
		
		filter(i -> i.getEspacos() > 1).each(i -> i.setEspacos(1));
		filter(i -> i.getTabs() > 0).each(i -> i.setTabs(0).setEspacos(1));

		filter(IfWhile.class).each(i -> after(i).clearIdentacao().setEspacos(1));
		filter(AndOr.class).each(i -> {
			Palavra o = after(i);
			if (!o.hasQuebra()) {
				o.clearIdentacao().setEspacos(1);
			}
		});
		
		filter(Case.class)
		.filter(i -> !i.hasQuebra())
		.filter(i -> i.getFechamento() != null)
		.filter(i -> i.getFechamento().hasQuebra() || palavras.entre(i, i.getFechamento()).anyMatch(x -> x.hasQuebra()))
		.each(i -> {
			i.clearIdentacao();
			i.setQuebras(1);
			i.getFechamento().setQuebras(1);
			i.getWhens(palavras).each(w -> w.clearIdentacao().setQuebras(1));
		});

		filter(i -> i.is(FuncaoBooleana.class) && after(i).hasQuebra() && after(after(i)).hasQuebra())
		.each(i -> {
			Palavra o = after(i);
			o.clearIdentacao().setEspacos(1);
			if (o.getComentarios().isNotEmpty()) {
				Palavra x = after(o);
				x.getComentarios().addAll(0, o.getComentarios());
				o.getComentarios().clear();
			}
		});

		filter(FuncaoBooleana.class).filter(i -> !after(after(i)).hasQuebra()).each(i -> after(i).clearIdentacao());
		
		filter(i -> i.getQuebras() > 0)
		.each(i -> i.setEspacos(0).setTabs(0))
		.each(i -> {
			
			if (i.is(Begin.class, BeginTran.class)) {
				i.setTabs(identacao);
				identacao++;
			} else if (i.is(End.class, FechaParenteses.class, CommitTran.class)) {
				identacao--;
				i.setTabs(identacao);
				Palavra o = getAbertura(i);
				if (o != null) {
					i.setEspacos(getPrimeiroDaLinha(o).getEspacos());
				}
			} else if (i.is(EndCase.class)) {
				identacao--;
				i.setTabs(identacao);
				identacao--;
				identacao--;
			} else if (palavras.beforeIs(i, AbreParenteses.class)) {
				identacao++;
				i.setTabs(identacao);
			} else if (i.is(Case.class) && after(i).hasQuebra()) {
				identacao++;
				identacao++;
				i.setTabs(identacao);
				identacao++;
			} else if (i.is(And.class)) {
				i.setTabs(identacao);
				i.setEspacos(2);
			} else if (i.is(Or.class)) {
				i.setTabs(identacao);
				i.setEspacos(3);
			} else {
				i.setTabs(identacao);
			}
			
		});
		
	}

	private static boolean fimIf(Palavra o) {
		return o.is(Set.class, Begin.class, Exec.class);
	}
	
	private void addParenteses(Palavra depoisDe, Palavra antesDe) {
		AbreParenteses abre = new AbreParenteses();
		FechaParenteses fecha = new FechaParenteses();
		abre.setFechamento(fecha);
		abre.setEspacos(1);
		after(depoisDe).clearIdentacao();
		palavras.addAfter(depoisDe, abre);
		palavras.addBefore(antesDe, fecha);
		abre.setProprietario(depoisDe);
	}
	
	private void colocarCondicoesIfEntreParenteses() {

		palavras.filter(IfWhile.class).each(ifWhile -> {

			Palavra a = after(ifWhile);
			
			if (a.is(AbreParenteses.class)) {
				AbreParenteses abre = (AbreParenteses) a;
				if (fimIf(after(abre.getFechamento()))) {
					return;
				}
			}
			
			Palavra o = after(ifWhile);
			
			while (true) {
				
				if (o.is(AbreParenteses.class)) {
					AbreParenteses abre = (AbreParenteses) o;
					o = abre.getFechamento();
					continue;
				}
				
				o = after(o);

				if (o.is(AndOr.class)) {
					
					if (after(o).is(AbreParenteses.class)) {
						AbreParenteses abre = after(o);
						o = abre.getFechamento();
					}
					
					continue;
					
				}

				if (o.is(OperadorComparacao.class)) {
					o = after(o);
					o = after(o);
					if (o.is(AbreParenteses.class)) {
						AbreParenteses abre = (AbreParenteses) o;
						o = abre.getFechamento();
						o = after(o);
					}
				} else if (o.is(FuncaoBooleana.class)) {
					AbreParenteses abre = after(o);
					o = after(abre.getFechamento());
				} else if (o.is(SinalMatematico.class)) {
					o = after(o);
					continue;
				} else if (o.is(IsNull.class, IsNotNull.class)) {
					o = after(o);
				} else if (o.is(AbreParenteses.class)) {
					AbreParenteses abre = (AbreParenteses) o;
					o = abre.getFechamento();
					continue;
				} else if (o.is(NaoClassificada.class) && after(o).is(AbreParenteses.class)) {
					AbreParenteses abre = after(o);
					o = abre.getFechamento();
					continue;
				}
				
				if (fimIf(o)) {
					addParenteses(ifWhile, o);
					return;
				}
				
				if (o.is(AndOr.class)) {
					o = after(o);
					continue;
				}

				o.print();
				for (int i = 0; i < 10; i++) {
					o.print();
					o = before(o);
				}
				
				break;
				
			}
			
		});
	}

	private void colocarUmPontoEVirgulaAntesDeCadaDeclare() {
		palavras.filter(Declare.class).each(i -> addPontoEVirgulaAntesDe(i));
	}

	private void removerQuebraDesnecessariaEmDeclaracaoSimples() {
		
		palavras.saveAnalise();
		
		palavras.filter(Declare.class).each(o -> {
			
			Palavra declaracao = after(o);
			Palavra fim = after(declaracao);
			
			if (fim.is(PontoEVirgula.class)) {
				declaracao.setTabs(0).setQuebras(0).setEspacos(1);
				fim.setTabs(0).setQuebras(0).setEspacos(0);
			}
			
		});
	}
	
	public boolean addPontoEVirgulaAntesDe(Palavra o) {

		o = palavras.before(o);
		
		if (o == null) {
			return false;
		}
		
		return addPontoEVirgulaDepoiDe(o);
		
	}
	
	public boolean addPontoEVirgulaDepoiDe(Palavra o) {

		if (o.is(PontoEVirgula.class, Begin.class)) {
			return false;
		}
		
		o = after(o);

		if (o.is(PontoEVirgula.class, End.class)) {
			return false;
		}
		
		palavras.addBefore(o, new PontoEVirgula());
		
		return true;
		
	}

	private void removerEspacosParenteses() {
		
		palavras
		.filter(AbreParenteses.class)
		.filter(i -> after(i).getQuebras() == 0)
		.each(i -> after(i).setEspacos(0).setTabs(0));
		
		palavras
		.filter(FechaParenteses.class)
		.filter(i -> i.getQuebras() == 0)
		.each(i -> i.setEspacos(0).setTabs(0));
		
	}

	private void removerEspacosDeItensLiteraisDentroDeUmIn() {

		/*
			de   in ( 'a', 'b', 'c' )
			para in ('a','b','c')
		*/
		
		Lst<AbreParenteses> opens = palavras.filter(AbreParenteses.class);
		
		for (AbreParenteses abre : opens) {
		
			FechaParenteses fecha = abre.getFechamento();
			Lst<Palavra> itens = palavras.entre(abre, fecha);
			
			if (itens.anyMatch(i -> !i.is(Virgula.class, InteiroLiteral.class, StringLiteral.class))) {
				continue;
			}
			
			itens.add(fecha);
			itens.filter(i -> i.getQuebras() == 0).each(i -> i.setEspacos(0).setTabs(0));
			
		}
		
	}

	private void junta() {

		palavras.replace(AbreColchetes.class, Palavra.class).porUm(l -> l.get(1));
		palavras.remove(FechaColchetes.class);
		palavras.replace(With.class, AbreParenteses.class, NoLock.class, FechaParenteses.class).por(WithNoLock.class);
		palavras.replace(AbreParenteses.class, NoLock.class, FechaParenteses.class).por(WithNoLock.class);
		palavras.replace(NoLock.class).por(WithNoLock.class);
		
		palavras.replace(Inner.class, Join.class).porUm(l -> new InnerJoin(l.rm(), l.rm()));
		palavras.replace(Left.class, Join.class).porUm(l -> new LeftJoin(l.rm(), l.rm()));
		palavras.replace(Join.class).porUm(l -> new InnerJoin(l.rm()));
		
		palavras.replace(Group.class, By.class).porUm(lst -> new GroupBy(lst.rm(), lst.rm()));
		palavras.replace(Order.class, By.class).porUm(lst -> new OrderBy(lst.rm(), lst.rm()));
		
		palavras.replace(Create.class, Or.class, Alter.class).porNew(() -> new CreateOrAlter());
		
		palavras.replace(Begin.class, Tran.class).porUm(l -> new BeginTran(l.rm(), l.rm()));
		palavras.replace(Commit.class, Tran.class).porUm(l -> new CommitTran(l.rm(), l.rm()));
		palavras.replace(Not.class, Exists.class).porUm(l -> new NotExists(l.rm(), l.rm()));
		
		Is0.func = o -> o.is(Procedure.class, Function.class);
		palavras.replace(Create.class, Is0.class).por(lst -> {
			lst.rm();
			lst.add(0, new CreateOrAlter());
			return lst;
		});
		
		palavras.replace(Create.class, Function.class).porNew(() -> new CreateOrAlter());
		
		palavras.replace(Primary.class, Key.class).por(PrimaryKey.class);
		
		if (corrigir) {
			palavras.replace(Is.class, Null.class).porUm(l -> new IsNull(l.rm(), l.rm()));
			palavras.replace(Is.class, Not.class, Null.class).porUm(l -> new IsNotNull(l.rm(), l.rm(), l.rm()));
		}
		
		palavras.replace(Set.class, NoCount.class, On.class).porUm(l -> new SetNoCountOn(l.rm(), l.rm(), l.rm()));
		
		palavras.replace(Set.class, NoCount.class, Off.class).por(SetNoCountOff.class);
		palavras.replace(Fetch.class, Next.class, From.class).por(FetchNextFrom.class);
		palavras.replace(Menor.class, Maior.class).por(Diferente.class);
		
		palavras.replace(With.class, NaoClassificada.class, As.class).porUm(lst -> {
			Cte cte = new Cte((NaoClassificada) lst.remove(1));
			ctes.add(cte);
			return new CteDeclaration(cte);
		});
		
		if (corrigir) {

			Is0.func = o -> o.is(Mais.class, Menos.class, OperadorComparacao.class);
			
			palavras.replace(Is0.class, Palavra.class).por(lst -> lst.each(i -> i.clearIdentacao().setEspacos(1)));
			
			palavras.replace(Virgula.class, Palavra.class).por(lst -> {
				
				lst.get(0).setEspacos(0);
				
				if (lst.get(1).getEspacos() == 0) {
					lst.get(1).incEspaco();
				}
				return lst;
			});
			
			palavras.each(o -> {
				
				if (o.is(Ponto.class, Virgula.class, AbreParenteses.class, FechaParenteses.class)) {
					return;
				}
				
				if (o.getEspacos() == 0 && o.getTabs() == 0 && o.getQuebras() == 0) {
					Palavra b = palavras.before(o);
					if (b != null && !b.is(Ponto.class, AbreParenteses.class)) {
						o.setEspacos(1);
					}
				}
			});
			
			/**/
			
		}
		
		palavras
		.replace(Numeric.class, AbreParenteses.class, InteiroLiteral.class, Virgula.class, InteiroLiteral.class, FechaParenteses.class)
		.por(lst -> {
			Numeric n = lst.get(0);
			InteiroLiteral left = (InteiroLiteral) lst.get(2);
			InteiroLiteral rigth = (InteiroLiteral) lst.get(4);
			n.set(left, rigth);
			lst.clear();
			lst.add(n);
			return lst;
		});
		
		Is0.func = o -> o.is(InteiroLiteral.class, Max.class);

		palavras
		.replace(Varchar.class, AbreParenteses.class, Is0.class, FechaParenteses.class)
		.porUm(lst -> {
			Varchar n = lst.rm();
			lst.rm();
			Palavra valor = lst.rm();
			if (valor instanceof InteiroLiteral) {
				InteiroLiteral x = (InteiroLiteral) valor;
				n.setSize(x);
			} else {
				n.setMax(true);
			}
			return n;
		});

		palavras
		.replace(Char.class, AbreParenteses.class, Is0.class, FechaParenteses.class)
		.porUm(lst -> {
			Char n = lst.rm();
			lst.rm();
			Palavra valor = lst.rm();
			if (valor instanceof InteiroLiteral) {
				InteiroLiteral x = (InteiroLiteral) valor;
				n.setSize(x);
			} else {
				n.setMax(true);
			}
			return n;
		});
		
		palavras.replace(VarReference.class, TipoSql.class).por(lista -> {
			VarReference a = (VarReference) lista.remove(0);
			TipoSql b = (TipoSql) lista.remove(0);
			VarDeclaration dec = new VarDeclaration(a.getVar(), b);
			lista.add(dec);
			return lista;
		});

		palavras.replace(FromAny.class, NaoClassificada.class, Ponto.class, NaoClassificada.class, Ponto.class, NaoClassificada.class).por(lst -> {
			Palavra from = lst.remove(0);
			NaoClassificada banco = (NaoClassificada) lst.remove(0);
			lst.remove(0);// ponto
			NaoClassificada schema = (NaoClassificada) lst.remove(0);
			lst.remove(0);// ponto
			NaoClassificada nome = (NaoClassificada) lst.remove(0);
			lst.add(from);
			DataSet ds = new DataSet(banco, schema, nome);
			ds.absorverIdentacao(banco);
			lst.add(ds);
			return lst;
		});
		
		palavras.replace(FromAny.class, NaoClassificada.class, Ponto.class, NaoClassificada.class).por(lst -> {
			FromAny fromAny = lst.remove(0);
			NaoClassificada schema = (NaoClassificada) lst.remove(0);
			lst.remove(0);// ponto
			NaoClassificada nome = (NaoClassificada) lst.remove(0);
			DataSet ds = new DataSet(schema, nome);
			ds.absorverIdentacao(schema);
			lst.add(fromAny);
			lst.add(ds);
			return lst;
		});

		palavras.saveAnalise();

		palavras.replace(FromAny.class, CandidatoATabela.class).por(itens -> {
			Palavra item = itens.remove(1);
			DataSet ds = new DataSet(item);
			ds.absorverIdentacao(item);
			itens.add(ds);
			return itens;
		});

		palavras.saveAnalise();

		palavras.replace(DataSet.class, NaoClassificada.class, WithNoLock.class).por(itens -> {
			DataSet dataset = (DataSet) itens.get(0);
			NaoClassificada item = (NaoClassificada) itens.remove(1);
			dataset.setAlias(item.getS());
			dataset.setNolock(true);
			itens.remove(1);
			return itens;
		});

		palavras.saveAnalise();

		palavras.replace(DataSet.class, As.class, NaoClassificada.class).porUm(itens -> {
			DataSet dataset = itens.rm();
			itens.rm();//as
			NaoClassificada item = itens.rm();
			dataset.setAlias(item.getS());
			return dataset;
		});

		palavras.saveAnalise();
		
		palavras.replace(DataSet.class, WithNoLock.class).porUm(itens -> {
			DataSet dataset = itens.rm();
			dataset.setNolock(true);
			return dataset;
		});

		palavras.saveAnalise();

		Is0.func = o -> o.is(Where.class, GroupBy.class, OrderBy.class);
		
		palavras.replace(DataSet.class, NaoClassificada.class, Is0.class).por(itens -> {
			DataSet dataset = (DataSet) itens.get(0);
			NaoClassificada item = (NaoClassificada) itens.remove(1);
			dataset.setAlias(item.getS());
			return itens;
		});

		palavras.saveAnalise();

		{

			Lst<DataSet> itens = palavras.filter(DataSet.class).filter(i -> i.faltaWithNoLock());

			for (DataSet dataSet : itens) {
				Palavra o = after(dataSet);
				if (o instanceof AbreParenteses) {
					Funcao funcao = dataSet.marcaComoFuncao();
					funcao.extraiParams((AbreParenteses) o, palavras);
				} else {
					Palavra nome = dataSet.getNome();
					if (nome instanceof NaoClassificada) {
						Cte cte = ctes.unique(i -> i.getNome().contentEquals(nome.getS()));
						if (cte != null) {
							CteReference cteReference = new CteReference(cte);
							dataSet.setNome(cteReference);
						}
					}
				}
			}

			palavras.saveAnalise();

		}

		Is0.func = o -> o.is(OrderBy.class, JoinAny.class, On.class);
		
		palavras.replace(DataSet.class, NaoClassificada.class, Is0.class).por(itens -> {
			DataSet dataset = (DataSet) itens.get(0);
			NaoClassificada item = (NaoClassificada) itens.remove(1);
			dataset.setAlias(item.getS());
			return itens;
		});
		
		Is0.func = o -> o.is(On.class, Where.class, AndOr.class);

		palavras
		.replace(Is0.class, NaoClassificada.class, Ponto.class, NaoClassificada.class, OperadorComparacao.class)
		.por(itens -> newColunaDataSetAcima(itens));

		Is0.func = o -> o.is(JoinAny.class, AndOr.class, Where.class);
		
		palavras
		.replace(OperadorComparacao.class, NaoClassificada.class, Ponto.class, NaoClassificada.class, Is0.class)
		.por(itens -> newColunaDataSetAcima(itens));

//		palavras
//		.replace(Virgula.class, NaoClassificada.class, Ponto.class, NaoClassificada.class, ParentesesClose.class)
//		.por(itens -> newColunaDataSetAbaixo(itens));
		
		identificarEndCases();
		identificarOFimDosSelects();
		
//		palavras
//		.replace(Coluna.class, NaoClassificada.class, Ponto.class, NaoClassificada.class, OperadorComparacao.class)
//		.por(itens -> newColuna(itens));
		

		palavras.saveAnalise();

	}
	
	private void identificarEndCases() {

		Lst<Case> cases = palavras.filter(Case.class);
		
		while (cases.isNotEmpty()) {
			Case cs = cases.removeLast();
			Palavra o = after(cs);
			while (!(o instanceof End)) {
				o = after(o);
			}
			palavras.replace(o, new EndCase(cs, (End) o));
		}
		
	}

	private void identificarOFimDosSelects() {

		Lst<Select> selects = palavras.filter(Select.class);
		
		for (Select select : selects) {

			Palavra o = after(select);
			
			while (o != null) {
				
				if (o.is(Exec.class, Insert.class, Update.class, FechaParenteses.class, IfWhile.class, Declare.class, PontoEVirgula.class)) {
					break;
				}
				
				if (o.is(AbreParenteses.class)) {
					int opens = 1;
					while (opens > 0) {
						o = after(o);
						if (o.is(AbreParenteses.class)) {
							opens++;
						} else if (o.is(FechaParenteses.class)) {
							opens--;
						}
					}
					
				}
				
				o = after(o);
				
			}
			
			SelectEnd end = new SelectEnd(select);
			
			if (o == null) {
				palavras.add(end);
			} else {
				palavras.addBefore(o, end);
			}
			
		}
		
	}

//	private static ReplacerLst newColunaDataSetAbaixo(ReplacerLst itens) {
//		return null;
//	}
	
	private static ReplacerLst newColunaDataSetAcima(ReplacerLst itens) {

		Palavra inicio = itens.remove(0);
		Palavra origem = itens.remove(0);
		Palavra ponto = itens.remove(0);
		NaoClassificada nome = itens.remove(0);
		Palavra fim = itens.remove(0);
		
		Palavra o = itens.getPalavras().before(inicio);
		String s = origem.getS();
		
		while (true) {
			
			if (o instanceof DataSet) {
				DataSet ds = (DataSet) o;
				if (ds.ehReferencia(s)) {
					Coluna coluna = new Coluna(ds, nome);
					coluna.setComAliasDoDataSet(true);
					coluna.absorverIdentacao(origem);
					itens.add(inicio);
					itens.add(coluna);
					itens.add(fim);
					return itens;
				}
			}
			
			o = itens.getPalavras().before(o);
			
			if (o == null) {
				itens.add(inicio);
				itens.add(origem);
				itens.add(ponto);
				itens.add(nome);
				itens.add(fim);
				return itens;
			}
			
		}
		
	}

	private void validaWithNolock() {

		Lst<DataSet> itens = palavras.filter(DataSet.class).filter(i -> i.faltaWithNoLock());

		if (itens.isEmpty()) {
			return;
		}
		
//		try {
//			palavras.exec();
//		} catch (Exception e) {
			palavras.save(fileName);
//			throw e;
//		}
		
//		if (!itens.isEmpty()) {
//			ListString erro = new ListString();
//			erro.add();
//			erro.add("=========================================================");
//			erro.add("As seguintes tabelas devem ser anotadas com with(nolock):");
//			itens.each(i -> {
//
//				erro.add("\t- " + i.toString().trim());
//
//				String s = i.getNome().getS();
//
//				Palavra o = palavras.before(i);
//
//				while ((o != null) && !(o instanceof Select)) {
//					s = o.getS() + " " + s;
//					o = palavras.before(o);
//				}
//
//				erro.add("\t\t " + s);
//
//			});
//			erro.add("=========================================================");
//			throw new RuntimeException(erro.toString("\n"));
//		}

//		palavras.replace(, palavras)p

//
//		if (!s.startsWith("select") || !s.contains(" from ")) {
//			return;
//		}
//
//		while (palavras.get(0).contentEquals(""))
//
//		if (!s.contains(".func_") && !s.contains(" func_")) {
//
//			if (!s.contains("with(nolock)")) {
//				return "select sem nolock: " + s;
//			}
//
//			if (!s.contains("order by")) {
//
//				if (s.startsWith("select top 1") || s.startsWith("select count(") || s.startsWith("select max(") || s.startsWith("select min(")) {
//					return null;
//				}
//
//				return "select sem orderBy: " + s;
//
//			}
//
//		}
//
//		return null;

	}

}
