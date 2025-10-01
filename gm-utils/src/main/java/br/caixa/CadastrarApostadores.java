package br.caixa;

import gm.utils.date.Data;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.number.ListInteger;
import src.commom.utils.cp.UCpf;
import src.commom.utils.integer.IntegerFormat;
import src.commom.utils.numeric.NumeroToExtenso;
import src.commom.utils.string.StringExtraiNumeros;

public class CadastrarApostadores {
	
	private static final ConexaoJdbc con = CaixaDBs.con;

	private static final String hoje = Data.hoje().format("[yyyy]-[mm]-[dd]");

	private static final String INSERT_APOSTADOR = ""
		+ " insert into lce.lcetb002_apostador("
		+ " nu_apostador, nu_situacao_apostador, nu_uf_ibge_l99, nu_municipio_ibge, nu_dv_ibge, nu_cd, no_apostador,"
		+ " nu_cpf, dt_nascimento, nu_cep, de_email, ts_cadastro, ic_sexo, de_nome_mae"
		+ " )"
		+ " values (:id, 1, 53, 10, 8, 274, 'apostador :nnn', ':cpf', '2000-01-01', '70804110', 'a:xxx@gmail.com', '"+hoje+"-00.00.00.000000', 'M', 'mae :xxx')"
	;

	private static final String INSERT_COMPRA = ""
		+ " insert into lce.lcetb013_compra(nu_compra, nu_mes, nu_particao, nu_situacao_compra, nu_apostador, ts_alteracao_situacao)"
		+ " values (:id, 0, 0, 1, :id, '"+hoje+"-00.00.00.000000')"
	;
	
	public static void main(String[] args) {
		for (int id = 1; id <= 200; id++) {
			exec(id);
		}
	}
	
	public static void exec(int id) {
		String cpf = StringExtraiNumeros.exec(UCpf.mock(id));
		String xxx = IntegerFormat.xxx(id);
		
		ListInteger compras = con.selectInts("select nu_compra from lce.lcetb013_compra where " + id + " in (nu_apostador, nu_compra)");
		
		for (Integer compra : compras) {
			con.tryExec("delete from lce.lcetb011_aposta where nu_compra = " + compra);
			con.tryExec("delete from lce.lcetb013_compra where nu_compra = " + compra);
		}
		
		
		String nome = NumeroToExtenso.get(id);
		CaixaDBs.con.tryExec("delete from lce.lcetb002_apostador where nu_apostador = " + id);
		CaixaDBs.con.tryExec(INSERT_APOSTADOR.replace(":id", ""+id).replace(":xxx", xxx).replace(":cpf", cpf).replace(":nnn", nome));
		CaixaDBs.con.tryExec(INSERT_COMPRA.replace(":id", ""+id));
		
	}
	
}
