package br.sicoob.compare;

import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.dbs.SicoobDBs;
import gm.utils.number.ListInteger;

public class BuscarNumeros2 {
	
	private static final ConexaoJdbc db2 = SicoobDBs.db2_dev();
	private static final ConexaoJdbc sql = SicoobDBs.sqlserver_dev();
	
	public static void main(String[] args) {
		ListInteger ids = db2.selectInts("select numPropostaCredito from bnd.prePropostaBndes order by 1 desc limit 100000");
		for (Integer id : ids) {
			if (sql.selectInt(
					"select top 1 1 " +
					"FROM dbo.PropostaCredito pc WITH (NOLOCK) " +
					"INNER JOIN dbo.TmpOpCredito t WITH (NOLOCK) ON pc.IDTmpOpCredito = t.IDTmpOpCredito AND pc.NumCooperativa = t.NumCooperativa AND pc.NumPac = t.NumPac " +
					"INNER JOIN dbo.PropostaCreditoBNDES pcb WITH (NOLOCK) ON pcb.IDTmpOpCredito = t.IDTmpOpCredito AND pcb.NumCooperativa = t.NumCooperativa AND pcb.NumPac = t.NumPac " +
					"INNER JOIN dbo.Pessoa ps WITH (NOLOCK) ON ps.NumPessoa = t.NumClienteLibBNDES " +
					"INNER JOIN dbo.Cooperativa c WITH (NOLOCK) ON pc.NumCooperativa = c.NumCooperativa AND pc.NumPac = c.NumPac " +
					"INNER JOIN dbo.ProgramasBNDES pb WITH (NOLOCK) ON pcb.CodPrograma = pb.CodPrograma " +
					"INNER JOIN dbo.CondOperacionalVigBNDES cov WITH (NOLOCK) ON cov.CodCondOperacional = pcb.CodCondOperacional " +
					"INNER JOIN dbo.ObjetivoProjetoBNDES opb WITH (NOLOCK) ON opb.IdObjetivoProjetoBNDES = pcb.IdObjetivoProjetoBNDES " +
					"INNER JOIN dbo.ModalidadeOpCredito moc WITH (NOLOCK) ON moc.IDModalidadeProduto = t.IDModalidadeProduto AND moc.IDProduto = t.IDProduto " +					
					"where pc.DescNumPropostaCred = " + id
			) != null) {
				System.out.println(id);
			}
		}
	}

}
