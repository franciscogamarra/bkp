package gm.languages.ts.javaToTs.exemplo.services;

import gm.languages.ts.javaToTs.GerarJson;
import gm.utils.anotacoes.Ignorar;
import gm.utils.anotacoes.IgnorarDaquiPraBaixo;
import gm.utils.comum.JSon;
import js.annotations.TypeJs;

@TypeJs
public class Mensagem {

	public String message;
	public Integer duration;
	public String position;
	public String color;

	@IgnorarDaquiPraBaixo
	private Mensagem() {}

	public static Mensagem json() {
		return new Mensagem();
	}

	public Mensagem message(String message) {
		this.message = message;
		return this;
	}

	public Mensagem duration(Integer duration) {
		this.duration = duration;
		return this;
	}

	public Mensagem position(String position) {
		this.position = position;
		return this;
	}

	public Mensagem color(String color) {
		this.color = color;
		return this;
	}

	@Ignorar
	private void buildExemplo() {
		Mensagem.json()
		.message("")
		.duration(0)
		.position("")
		.color("")
		;
	}

	public static void main(String... args) {
		//Transpilar.exec(Mensagem.class, false);
		GerarJson.exec();
	}

	@Override
	public String toString() {
		return JSon.toJson(this);
	}
}
