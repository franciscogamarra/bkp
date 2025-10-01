package src.commom.utils.string;

import src.commom.utils.array.Itens;
import src.commom.utils.comum.Randomico;

public class NomePessoaFisica {

	private static final Itens<String> NOMES_MASCULINOS = Itens.buildString(
		"Adriano","Alessandro","Alex","André","Antônio","Anderson","Alison","Alexandre",
		"Bruno","Bartolomeu","Benjamin",
		"César","Caio","Celso",
		"Diego","Daniel","David","Diogo","Denis","Dilson","Denilson",
		"Expedito","Emanuel","Ernesto","Eduardo","Edilson","Edmar","Edmilson","Everaldo",
		"Ferdinand","Fernando","Francisco","Frank","Fabrício","Frederico","Flávio","Felipe",
		"Gustavo","Gabriel","Getúlio","Gerson",
		"Hélio","Henrique","Heitor","Herbert",
		"Isaias","Iuri","Irineu","Ítalo",
		"João","José","Joaquim","Jonas","Jonatam","Juliano",
		"Kaio","Kauã","Kevin","Kaique","Kaleb","Kelvin",
		"Leonardo","Leandro","Lucas",
		"Maicon","Marcos","Mario","Maurício","Maxuel","Maycon","Michael","Mike","Moisés","Miguel","Mateus",
		"Natanael","Nóe","Nildo","Nelson","Norberto",
		"Osvaldo","Orlando","Otávio",
		"Pedro","Pietro","Paulo",
		"Quintino",
		"Ricardo","Ruan","Raphael","Renan","Romário","Roberto","Rafael","Rômulo","Raul",
		"Saulo","Sandro","Sérgio","Samoel","Serafin",
		"Tiago","Tomé","Tomaz","Thiago",
		"Ulisses","Udson",
		"Vicente","Valter","Vanderley","Vitor",
		"Washington","Waldemar","Walisson","Wagner","Willian","Wesley",
		"Xavier",
		"Yuri","Yago",
		"Zaqueu","Zacarias"
	);
	
	private static final Itens<String> NOMES_FEMININOS = Itens.buildString(
		"Adriana","Alessandra","Aline","Ana","Amanda","Alexandra",
		"Beatriz","Bárbara","Bianca","Bruna","Bete",
		"Carina","Carolina","Cristiane","Carla","Carmen",
		"Daniela","Débora","Diana",
		"Emanuela","Evelin","Édna","Elaine","Ester","Elana",
		"Fátima","Fernanda","Francisca",
		"Gabriela","Graziela","Grace","Glória",	
		"Hélem","Helena","Heloísa","Heloise",
		"Ivete","Isabela","Isadora","Isabella","Isabel","Íris","Isabelly","Inês",
		"Joana","Janaína","Jéssica","Juliana","Júlia","Josefina",
		"Karem","Kelly","Kiara","Karina","Ketlyn","Karine","Kaylane",
		"Lídia","Luciana","Laura","Luana",
		"Marcela","Maria","Marta","Miriã","Mirian","Maura","Maria Dalva","Mercedes",
		"Nádia","Neuma","Noemi",
		"Otaviana","Olga","Olívia",
		"Patrícia","Priscila","Paloma",
		"Querina","Queren","Quezia","Quésia","Quitéria",		
		"Rosane","Rosângela","Ruth","Rosa",
		"Suélen","Sabrina","Sara","Sabrina",
		"Tatiana","Telma","Taís","Tamirez",
		"Valéria","Valentina","Vanessa","Vívian","Virgínia",
		"Wendy","Wilma","Wanda","Walquíria","Walesca",
		"Xaiane","Xayane","Ximena",		
		"Yara","Yolanda","Yasmine","Yeda",
		"Zélia","Zaíra","Zuleica","Zenilda"		
	);
	
	private static final Itens<String> SOBRENOMES = Itens.buildString(
		"Alves","Azevedo","Andrade","Adonai","Aurélio","Almeida","Abreu",
		"Borges","Beltrão","Berdinatzy","Bitencourt","Bragança","Benigno","Barbosa","Branco",
		"Cabral","Caetano","Camargo","Carvalho","Correia","Cruz","Campos","Couto",
		"Dias","Durval","Dalas","Dantas","Diniz",
		"Euclides",
		"Ferreira","Fernandes","Fortunato","Franco","França","Flores","Farias",
		"Gamarra","Godoi","Gomes","Gonçalves",
		"Hernesto",
		"Inácio",
		"Lima","Lisboa","Lopes",
		"Marcondes","Marlone","Metzenga","Machado","Meireles","Morais","Marques","Macedo","Moreira","Melo",
		"Novaes","Neves","Nunes","Nascimento",
		"Oliveira",
		"Pereira","Pinho","Paiva","Peixoto","Pontes",
		"Queiroz","Quintino",
		"Ramos","Rocha","Rezende","Rabelo","Rosa","Reis","Rios",
		"Santos","Saraiva","Silva","Souza","dos Santos","da Silva","Santana",
		"Tavarez","Tevez","Torres","Teixeira",
		"Vasconcelos","Vascos","Vieira","Viana","Vargas",
		"Xavier"
	);
	
	public static String getAleatorio() {
		return getAleatorioSexo(Randomico.getBoolean());
	}
	
	public static String getAleatorioSexo(boolean masculino) {
		
		Itens<String> nomes = masculino ? NOMES_MASCULINOS.copy() : NOMES_FEMININOS.copy();
		
		String s = Randomico.removeItem(nomes);
		
		if (Randomico.getInt(0, 3) == 2) {
			s += " " + Randomico.removeItem(nomes);
		}
		
		Itens<String> sobrenomes = SOBRENOMES.copy();
		
		for (int i = 0; i < 5; i++) {
			
			if (Randomico.getBoolean()) {
				s += " " + Randomico.removeItem(sobrenomes);
			}
			
		}
		
		return s;
		
	}	
	
}
