package br.sicoob.src.app.funcionalidades.proposta__credito__bndes.models;

import gm.utils.comum.Lst;

public class Logradouro {

	public int codigoBndes;
	public String descricaoLogradouro;
	
	public static final Lst<Logradouro> itens = new Lst<>();
	static {
		
		Logradouro o = new Logradouro();
		o.codigoBndes = 1;
		o.descricaoLogradouro = "Aeroporto";
		itens.add(o);
		
		o = new Logradouro();
		o.codigoBndes = 2;
		o.descricaoLogradouro = "Alameda";
		itens.add(o);

		o = new Logradouro();
		o.codigoBndes = 3;
		o.descricaoLogradouro = "Área";
		itens.add(o);
		
	}	
	
}
//[
//	{
//		"codigoBndes": 1,
//		"descricaoLogradouro": "Aeroporto"
//	},
//	{
//		"codigoBndes": 2,
//		"descricaoLogradouro": "Alameda"
//	},
//	{
//		"codigoBndes": 3,
//		"descricaoLogradouro": "Área"
//	},
//	{
//		"codigoBndes": 4,
//		"descricaoLogradouro": "Avenida"
//	},
//	{
//		"codigoBndes": 5,
//		"descricaoLogradouro": "Campo"
//	},
//	{
//		"codigoBndes": 6,
//		"descricaoLogradouro": "Chácara"
//	},
//	{
//		"codigoBndes": 7,
//		"descricaoLogradouro": "Colônia"
//	},
//	{
//		"codigoBndes": 8,
//		"descricaoLogradouro": "Condomínio"
//	},
//	{
//		"codigoBndes": 9,
//		"descricaoLogradouro": "Conjunto"
//	},
//	{
//		"codigoBndes": 10,
//		"descricaoLogradouro": "Distrito"
//	},
//	{
//		"codigoBndes": 11,
//		"descricaoLogradouro": "Esplanada"
//	},
//	{
//		"codigoBndes": 12,
//		"descricaoLogradouro": "Estação"
//	},
//	{
//		"codigoBndes": 13,
//		"descricaoLogradouro": "Estrada"
//	},
//	{
//		"codigoBndes": 14,
//		"descricaoLogradouro": "Favela"
//	},
//	{
//		"codigoBndes": 15,
//		"descricaoLogradouro": "Fazenda"
//	},
//	{
//		"codigoBndes": 16,
//		"descricaoLogradouro": "Feira"
//	},
//	{
//		"codigoBndes": 17,
//		"descricaoLogradouro": "Jardim"
//	},
//	{
//		"codigoBndes": 18,
//		"descricaoLogradouro": "Ladeira"
//	},
//	{
//		"codigoBndes": 19,
//		"descricaoLogradouro": "Lago"
//	},
//	{
//		"codigoBndes": 20,
//		"descricaoLogradouro": "Lagoa"
//	},
//	{
//		"codigoBndes": 21,
//		"descricaoLogradouro": "Largo"
//	},
//	{
//		"codigoBndes": 22,
//		"descricaoLogradouro": "Morro"
//	},
//	{
//		"codigoBndes": 23,
//		"descricaoLogradouro": "Núcleo"
//	},
//	{
//		"codigoBndes": 24,
//		"descricaoLogradouro": "Parque"
//	},
//	{
//		"codigoBndes": 25,
//		"descricaoLogradouro": "Passarela"
//	},
//	{
//		"codigoBndes": 26,
//		"descricaoLogradouro": "Pátio"
//	},
//	{
//		"codigoBndes": 27,
//		"descricaoLogradouro": "Praça"
//	},
//	{
//		"codigoBndes": 28,
//		"descricaoLogradouro": "Quadra"
//	},
//	{
//		"codigoBndes": 29,
//		"descricaoLogradouro": "Recanto"
//	},
//	{
//		"codigoBndes": 30,
//		"descricaoLogradouro": "Residencial"
//	},
//	{
//		"codigoBndes": 31,
//		"descricaoLogradouro": "Rodovia"
//	},
//	{
//		"codigoBndes": 32,
//		"descricaoLogradouro": "Rua"
//	},
//	{
//		"codigoBndes": 33,
//		"descricaoLogradouro": "Setor"
//	},
//	{
//		"codigoBndes": 34,
//		"descricaoLogradouro": "Sitio"
//	},
//	{
//		"codigoBndes": 35,
//		"descricaoLogradouro": "Travessa"
//	},
//	{
//		"codigoBndes": 36,
//		"descricaoLogradouro": "Trecho"
//	},
//	{
//		"codigoBndes": 37,
//		"descricaoLogradouro": "Trevo"
//	},
//	{
//		"codigoBndes": 38,
//		"descricaoLogradouro": "Vale"
//	},
//	{
//		"codigoBndes": 39,
//		"descricaoLogradouro": "Vereda"
//	},
//	{
//		"codigoBndes": 40,
//		"descricaoLogradouro": "Via"
//	},
//	{
//		"codigoBndes": 41,
//		"descricaoLogradouro": "Viaduto"
//	},
//	{
//		"codigoBndes": 42,
//		"descricaoLogradouro": "Viela"
//	},
//	{
//		"codigoBndes": 43,
//		"descricaoLogradouro": "Vila"
//	},
//	{
//		"codigoBndes": 44,
//		"descricaoLogradouro": "Outros"
//	},
//	{
//		"codigoBndes": 44,
//		"descricaoLogradouro": "Outro"
//	}
//]