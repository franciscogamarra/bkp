package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests;

import br.sicoob.SicoobTranspilar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import js.Js;
import js.array.Array;
import js.support.console;

public abstract class PcbAcao {
	
	public static Array<PcbAcao> acoes = new Array<>();
	
	private String nome;

	public PcbAcao(String nome) {
		this.nome = nome;
		acoes.push(this);
	}
	
	public void exec() {
		console.log(">>> " + nome);
		acao();
		console.log("<<< " + nome);
		if (acoes.length) {
			PcbAcao proxima = acoes.shift();
			Js.setTimeout(() -> proxima.exec(), tempoDeExecucao());
		}
//		button-adicionarTipoDeItem
	} 
	
	public abstract void acao();

	public static void run() {
		if (acoes.length) {
			acoes.shift().exec();
		} else {
			console.log("Nenhuma acao foi definida");
		}
	}
	
	public int tempoDeExecucao() {
		return 500;
	}
	
	
	@IgnorarDaquiPraBaixo

	public static void main(String[] args) {
		SicoobTranspilar.exec(PcbAcao.class);
	}
	
}