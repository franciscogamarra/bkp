package src.commom.utils.string;

public class ValidadorNomePessoaFisica extends ValidadorNomeProprio {
	
	public static final ValidadorNomePessoaFisica instance = new ValidadorNomePessoaFisica();

	private ValidadorNomePessoaFisica() {
		super(StringConstants.MAIUSCULAS.concat(StringConstants.MINUSCULAS).add(StringConstants.aspa_simples).add(" ").add("."), 3, 60, 3);
	}

	@Override
	public String getInvalidMessage(String s) {
		
		if (StringEmpty.is(s)) {
			return null;
		}
		
		String msg = super.getInvalidMessage(s);
		
		if (msg != null) {
			return msg;
		}

		if (!StringContains.is(s, " ")) {
			return "Deve conter pelo menos um sobrenome!";
		}
		
		return null;
		
	}

}