package br.gerar;

import br.com.sicoob.concessao.bndes.utils.tools.P1;
import br.support.comum.Lst;
import br.support.exceptions.DevException;

public class Aba {
	
	final String nome;
	final Lst<Campo> campos = new Lst<>();
	static final Lst<Aba> abas = new Lst<>();
	
	public Aba(String nome) {
		this.nome = nome;
		abas.add(this);
	}
	
	public Campo newCampo(String nome, CampoTipo tipo) {
		
		if (campos.anyMatch(campo -> campo.nome.equals(nome))) {
			throw DevException.build("nome ja consta: " + nome);
		}
		
		Campo o = new Campo(nome, tipo);
		campos.add(o);
		if (onNewCampo != null) {
			onNewCampo.call(o);
		}
		return o;
	}
	
	public Campo newSelect(String nome) {
		return newCampo(nome, CampoTipo.select);
	}

	public Campo newBoolean(String nome) {
		Campo o = newSelect(nome);
		o.options = "Campo.booleanOptions";
		return o;
	}

	public Campo newMoney(String nome) {
		return newCampo(nome, CampoTipo.number);
	}

	public Campo newNumber(String nome) {
		return newCampo(nome, CampoTipo.number);
	}
	
	public Campo newCpfCnpj(String nome) {
		return newCampo(nome, CampoTipo.cpfCnpj);
	}

	public Campo newCnpj(String nome) {
		return newCampo(nome, CampoTipo.cnpj);
	}
	
	public void newAcao(String nome) {
		
	}
	
	public void gerar() {
		new MontarObjects(this).exec();
		MontarHtml.exec(this);
	}
	
	public Campo newString(String nome, int length) {
		return newCampo(nome, CampoTipo.text);		
	}
	
	public Campo newData(String nome) {
		return newCampo(nome, CampoTipo.data);
	}

	public Campo newCheckList(String nome) {
		return newCampo(nome, CampoTipo.checkList);
	}

	public Campo newPercentual(String nome) {
		return newCampo(nome, CampoTipo.percentual);		
	}
	
	public P1<Campo> onNewCampo;
	
}
