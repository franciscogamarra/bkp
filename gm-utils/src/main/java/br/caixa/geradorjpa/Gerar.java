package br.caixa.geradorjpa;

import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import br.caixa.geradorjpa.campos.apostaEspelho;
import br.caixa.geradorjpa.campos.apostador;
import br.caixa.geradorjpa.campos.carrinhoFavorito;
import br.caixa.geradorjpa.campos.compra;
import br.caixa.geradorjpa.campos.equipeEsportiva;
import br.caixa.geradorjpa.campos.gerarEspelho;
import br.caixa.geradorjpa.campos.inclusao;
import br.caixa.geradorjpa.campos.indicadorSurpresinha;
import br.caixa.geradorjpa.campos.mesDeSorte;
import br.caixa.geradorjpa.campos.modalidade;
import br.caixa.geradorjpa.campos.nome;
import br.caixa.geradorjpa.campos.numeros;
import br.caixa.geradorjpa.campos.numerosTrevo;
import br.caixa.geradorjpa.campos.sequencialCombo;
import br.caixa.geradorjpa.campos.teimosinhas;
import br.caixa.geradorjpa.campos.tipoCombo;
import br.caixa.geradorjpa.campos.tipoConcurso;
import gm.utils.classes.UClass;
import gm.utils.comum.Lst;
import gm.utils.javaCreate.JcAnotacao;
import gm.utils.javaCreate.JcAtributo;
import gm.utils.javaCreate.JcClasse;
import gm.utils.javaCreate.JcTipo;
import src.commom.utils.string.StringToConstantName;

public class Gerar {

	private static final JcTipo notNull = new JcTipo("javax.validation.constraints.NotNull");
	
	public JcClasse jc;
	public final Lst<Class<? extends Campo>> campos = new Lst<>();
	public boolean identity = true;
	
	public void exec() {
		
		jc.addAnnotation(Entity.class);
		jc.lombokGetter().lombokSetter();
		jc.newAnnotation(Table.class).addParametro("name", "\"" + StringToConstantName.exec(jc.getSimpleName()).toLowerCase() + "\"");
		
		jc.setFormal(true);
		jc.setPularLinhaAntesDeCadaMetodo(true);
		
		{
			JcAtributo a = jc.atributo("id", Integer.class);
			a.newAnotacao(Id.class);
			if (identity) {
				a.newAnotacao(GeneratedValue.class).addParametro("strategy", "GenerationType.IDENTITY");
				jc.addImport(GenerationType.class);
			}
		}

		campos.add(inclusao.class);

		for (Class<? extends Campo> classe : campos) {
			
			Campo campo = UClass.newInstance(classe);
			
			JcAtributo a = jc.atributo(classe.getSimpleName(), campo.getType());
			
			if (campo.reference()) {
				JcAnotacao manyToOne = a.newAnotacao(ManyToOne.class);
				manyToOne.addParametro("fetch", "FetchType.LAZY");
				manyToOne.addParametro("optional", true);
				jc.addImport(FetchType.class);
			}
			
			JcAnotacao column = a.newAnotacao(campo.reference() ? JoinColumn.class : Column.class);
			column.addParametro("name", "\"" + campo.getColumn() + "\"");
			
			campo.onColumn(column);
			
			if (campo.isNotNull()) {
				a.addAnotacao(notNull);
				column.addParametro("nullable", false);
			}
			
			JcTipo converter = campo.getConverter();
			
			if (converter != null) {
				jc.addImport(converter);
				a.newAnotacao(Convert.class).addParametro("converter", converter.getSimpleName() + ".class");
			}
			
		}
		
		jc.print();
		
	}
	
	public static void main(String[] args) {
		apostaEmProcessamento();
	}

	public static void carrinhoFavorito() {
		Gerar gerar = new Gerar();
		gerar.jc = JcClasse.build("", "br.caixa.loterias.silce.bos.apostacarrinhofavorito", "CarrinhoFavorito");
		gerar.campos.add(apostador.class);
		gerar.campos.add(nome.class);
		gerar.exec();		
	}
	
	public static void aposta() {
		Gerar gerar = new Gerar();
		gerar.jc = JcClasse.build("", "br.caixa.loterias.silce.bos.aposta", "Aposta");
		apostaComuns(gerar);
		gerar.exec();
	}

	public static void apostaEmProcessamento() {
		Gerar gerar = new Gerar();
		gerar.jc = JcClasse.build("", "br.caixa.loterias.silce.bos.apostasEmProcessamento", "ApostaEmProcessamento");
		apostaComuns(gerar);
		gerar.campos.add(compra.class);
		gerar.exec();
	}
	
	public static void apostaComuns(Gerar gerar) {
		gerar.campos.add(apostador.class);
		cincoComuns(gerar);
		quatroComuns(gerar);
		gerar.campos.add(tipoConcurso.class);
		gerar.campos.add(tipoCombo.class);
		gerar.campos.add(sequencialCombo.class);
	}
	
	public static void apostaFavorita() {
		Gerar gerar = new Gerar();
		gerar.jc = JcClasse.build("", "br.caixa.loterias.silce.bos.apostaFavorita", "ApostaFavorita");
		gerar.campos.add(apostador.class);//ok
		cincoComuns(gerar);
		gerar.campos.add(nome.class);//ok
		gerar.exec();
	}
	
	public static void apostaCarrinhoFavorito() {
		Gerar gerar = new Gerar();
		gerar.jc = JcClasse.build("", "br.caixa.loterias.silce.bos.apostacarrinhofavorito", "ApostaCarrinhoFavorito");
		gerar.campos.add(carrinhoFavorito.class);//ok
		cincoComuns(gerar);
		quatroComuns(gerar);
		gerar.exec();
	}
	
	public static void cincoComuns(Gerar gerar) {
		gerar.campos.add(modalidade.class);//ok
		gerar.campos.add(numeros.class);//ok
		gerar.campos.add(numerosTrevo.class);//ok
		gerar.campos.add(mesDeSorte.class);//ok
		gerar.campos.add(equipeEsportiva.class);//ok
	}
	
	public static void quatroComuns(Gerar gerar) {
		gerar.campos.add(indicadorSurpresinha.class);//ok
		gerar.campos.add(teimosinhas.class);//ok
		gerar.campos.add(gerarEspelho.class);//ok
		gerar.campos.add(apostaEspelho.class);//ok
	}
	
}