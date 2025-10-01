package gm.utils.jpa.select;

import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import gm.utils.date.DataComHora;
import gm.utils.exception.UException;

public class SelectDataComHora<TS extends SelectBase<?,?,?>> extends SelectTypedLogical<TS, DataComHora> {
	
	public SelectDataComHora(TS x, String campo) {
		super(x, campo);
	}
	
	private static DataComHora from(Calendar value) {
		return DataComHora.from(value);
	}
	private static DataComHora from(LocalDate value) {
		return DataComHora.from(value);
	}
	private static DataComHora from(Date value) {
		return DataComHora.from(value);
	}
	private static DataComHora from(Object value) {
		return DataComHora.from(value);
	}

	public TS eq(Calendar value) {
		return super.eq(DataComHora.from(value));
	}
	public TS ne(Calendar value) {
		return super.ne(DataComHora.from(value));
	}
	public TS maior(Calendar value) {
		return super.maior(from(value));
	}
	public TS menor(Calendar value) {
		return super.menor(from(value));
	}
	public TS maiorOuIgual(Calendar value) {
		return super.maiorOuIgual(from(value));
	}
	public TS menorOuIgual(Calendar value) {
		return super.menorOuIgual(from(value));
	}
	public TS entre(Calendar a, Calendar b) {
		return super.entre(from(a), from(b));
	}
	public TS naoEntre(Calendar a, Calendar b) {
		return super.naoEntre(from(a), from(b));
	}

	public TS eq(LocalDate value) {
		return super.eq(from(value));
	}
	public TS ne(LocalDate value) {
		return super.ne(from(value));
	}
	public TS maior(LocalDate value) {
		return super.maior(from(value));
	}
	public TS menor(LocalDate value) {
		return super.menor(from(value));
	}
	public TS maiorOuIgual(LocalDate value) {
		return super.maiorOuIgual(from(value));
	}
	public TS menorOuIgual(LocalDate value) {
		return super.menorOuIgual(from(value));
	}
	public TS entre(LocalDate a, LocalDate b) {
		return super.entre(from(a), from(b));
	}
	public TS naoEntre(LocalDate a, LocalDate b) {
		return super.naoEntre(from(a), from(b));
	}

	public TS eq(Date value) {
		return super.eq(from(value));
	}
	public TS ne(Date value) {
		return super.ne(from(value));
	}
	public TS maior(Date value) {
		return super.maior(from(value));
	}
	public TS menor(Date value) {
		return super.menor(from(value));
	}
	public TS maiorOuIgual(Date value) {
		return super.maiorOuIgual(from(value));
	}
	public TS menorOuIgual(Date value) {
		return super.menorOuIgual(from(value));
	}
	public TS entre(Date a, Date b) {
		return super.entre(from(a), from(b));
	}
	public TS naoEntre(Date a, Date b) {
		return super.naoEntre(from(a), from(b));
	}

	public TS isHoje() {
		c().isHoje(getCampo());
		return ts;
	}
	public TS isNotHoje() {
		c().isNotHoje(getCampo());
		return ts;
	}
	public TS menorQueAgora(){
		return menor(DataComHora.now());
	}
	public TS maiorQueAgora(){
		return maior(DataComHora.now());
	}

	public TS menorQueHoje(){
		return menor(DataComHora.hoje());
	}

	public TS maiorQueHoje(){
		return maiorOuIgual(DataComHora.amanha());
	}

	public DataComHora max() {
		Object o = c().max(getCampo());
		return from(o);
	}

	public DataComHora min() {
		Object o = c().min(getCampo());
		return from(o);
	}

	public TS in(List<DataComHora> list){
		c().in(getCampo(), list);
		return ts;
	}

	public TS op(Operador operador, DataComHora inicio, DataComHora fim) {
		if (operador == Operador.igual) {
			return eq(inicio);
		}
		if (operador == Operador.maiorOuIgual) {
			return maiorOuIgual(inicio);
		}
		if (operador == Operador.menorOuIgual) {
			return menorOuIgual(inicio);
		}
		if (operador == Operador.entre) {
			return entre(inicio,fim);
		}
		if (operador == Operador.diferente) {
			return ne(inicio);
		}
		if (operador == Operador.naoEntre) {
			return naoEntre(inicio,fim);
		}
		throw UException.runtime("Operador inválido: " + operador);
	}

}
