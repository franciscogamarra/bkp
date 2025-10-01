package gm.utils;

import java.util.ArrayList;

public class Teste {
	
	private static int count = 0;
	private int id = count++;
	
	public Teste() {}

	public static void main(String[] args) {

		ArrayList<Teste> list = new ArrayList<>();
		list.add(new Teste());
		list.add(new Teste());
		System.out.println(list);
		
		
	}
	
	@Override
	public String toString() {
		return ""+id;
	}
	
}