package gm.utils.date;

public class Range {

	private final int start;
	private final int end;
	private final int dif;
	private int value;

	public Range(int start, int end, int value) {
		if (start > end) {
			throw new RuntimeException("start deve ser menor do que end: " + start + " / " + end);
		}
		this.start = start;
		this.end = end;
		this.value = value;
		this.dif = end - start + 1;
		valida();
	}
	
	public Range(int start, int end) {
		this(start, end, start);
	}

	public void set(int value) {
		this.value = value;
		valida();
	}
	
	public int get() {
		return this.value;
	}
	
	private void valida() {
		if (value < start || value > end) {
			throw new RuntimeException("value deve ser >= start e <= end: " + this);
		}
	}
	
	public int add() {
		return add(1);
	}
	
	public int remove() {
		return add(-1);
	}
	
	public int remove(int qtd) {
		return add(-qtd);
	}
	
	/* retorna a quantidade de giros */
	public int add(int qtd) {
		
		if (qtd == 0) {
			return 0;
		}
		
		value += qtd;
		
		if (value < start) {
			qtd = 0;
			while (value < start) {
				value += dif;
				qtd--;
			}
			return qtd;
		}

		if (value > end) {
			qtd = 0;
			while (value > end) {
				value -= dif;
				qtd++;
			}
			return qtd;
		}
		
		return 0;
		
	}
	
	@Override
	public String toString() {
		return "" + value;
	}
	
}
