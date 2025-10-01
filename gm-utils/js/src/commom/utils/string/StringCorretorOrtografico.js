import StringBox from './StringBox';
import StringConstants from './StringConstants';
import StringEmpty from './StringEmpty';
import StringReplacePalavra from './StringReplacePalavra';

export default class StringCorretorOrtografico {

	static exec(s) {

		if (StringEmpty.is(s)) {
			return s;
		}

		s = StringReplacePalavra.exec(s, "eh", StringConstants.e_agudo);
		s = StringReplacePalavra.exec(s, "Eh", StringConstants.E_agudo);

		let box = new StringBox(s);
		box.replace("nvalid", "nv"+StringConstants.a_agudo+"lid");

		StringConstants.SIMBOLOS.forEach(b => {
			box.replace("cao"+b, StringCorretorOrtografico.cao + b);
			box.replace("coes"+b, StringCorretorOrtografico.coes + b);
			box.replace("orio"+b, StringCorretorOrtografico.orio + b);
			box.replace("ario"+b, StringCorretorOrtografico.ario + b);
			box.replace("itona"+b, StringCorretorOrtografico.itona + b);
		});

		return box.get();

	}

	static main(args) {
		console.log(StringCorretorOrtografico.exec("isso eh demais"));
		console.log(StringCorretorOrtografico.exec("Eh isso eh demais"));
	}

}
StringCorretorOrtografico.cao = StringConstants.cedilha + StringConstants.a_til + "o";
StringCorretorOrtografico.coes = StringConstants.cedilha + StringConstants.o_til + "es";
StringCorretorOrtografico.orio = StringConstants.o_agudo + "rio";
StringCorretorOrtografico.ario = StringConstants.a_agudo + "rio";
StringCorretorOrtografico.itona = StringConstants.i_agudo + "tona";
