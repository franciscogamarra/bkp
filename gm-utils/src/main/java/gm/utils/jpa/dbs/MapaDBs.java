package gm.utils.jpa.dbs;

import gm.utils.comum.SystemPrint;
import gm.utils.jpa.ConexaoJdbc;
import gm.utils.jpa.DriverJDBCOracle;

public class MapaDBs {

	public static void main(String[] args) {
//		dev().testaConexao();
//		dup().testaConexao();
		
		dup().selectMap("select * from SISPROCER_MAPA.S_PARAMETRO").each(map -> {
			
			String s = "buildMock(" + map.getString("id_parametro") + ", \"" + map.getString("cd_parametro") + "\", \"" + map.getString("nm_parametro") + "\", \"" + map.getString("ds_parametro") + "\");";
			SystemPrint.ln(s);
			
//			{id_parametro=2, cd_parametro=2078, nm_parametro=ID_PERFIL_AGENTE_PROAGRO, ds_parametro=ID do Perfil Agente do Proagro}
		});
		
//	    @Id
//	    @Column(name = "ID_PARAMETRO")
//	    @SequenceGenerator(name = "SQ_PARAMETRO", sequenceName = "SISPROCER_MAPA.SQ_PARAMETRO", allocationSize = 1)
//	    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "SQ_PARAMETRO")
//	    private Long idParametro;
//
//	    @Column(name = "CD_PARAMETRO")
//	    private String cdParametro;
//
//	    @Column(name = "NM_PARAMETRO")
//	    private String nmParametro;
//
//	    @Column(name = "DS_PARAMETRO")
//	    private String dsParametro;		
		
	}
	
	public static ConexaoJdbc dev() {
		return ConexaoJdbc.get("@10.255.1.28:1521:MAPADEV", DriverJDBCOracle.getInstance(), "NEW_AUTENTICACAO_APL", "NEW_AUTENTICACAO_APL");
	}

	public static ConexaoJdbc dup() {
		return ConexaoJdbc.get("@10.255.1.80:1521:MAPADUP", DriverJDBCOracle.getInstance(), "SISPROCER_MAPA_APL", "SISPROCER_MAPA_APL");
	}
	
}

/*

1	23,26,220,581,640,2507,3729,4093,4101,4419,4453,941,4695	IDS_ORGAOS_REPRESENTADOS_SISPROCER	Lista de Ids dos órgãos representados para o SISPROCER
2	2078	ID_PERFIL_AGENTE_PROAGRO	ID do Perfil Agente do Proagro
3	2072	ID_PERFIL_AGENTE_FINANCEIRO	ID do Perfil Agente Financeiro
4	2073	ID_PERFIL_ADMINISTRATIVO_CER	ID do Perfil Administrativo da CER
5	2074	ID_PERFIL_COORDENADOR_CER	ID do Perfil Coordenador da CER
6	2075	ID_PERFIL_ADMINISTRADOR	ID do Perfil Administrador
7	2076	ID_PERFIL_ADMINISTRADOR_CER	ID do Perfil Administrador da CER
8	2077	ID_PERFIL_ANALISTA_CER	ID do Perfil Analista da CER
9	2079	ID_PERFIL_FUNCIONARIO_ADMINISTRATIVO_CER	ID do Perfil Funcionário Administrativo CER
10	2080	ID_PERFIL_BACEN	ID do Perfil Bacen
11	2081	ID_PERFIL_MEMBRO_TURMA	ID do Perfil Membro de Turma
12	11144	ID_MENU_JULGAMENTO	ID do menu principal Julgamento
13	11145	ID_MENU_ABRIR_SESSAO	ID do menu Abrir Sessao
14	11146	ID_MENU_GERAR_RESOLUCAO	ID do menu Gerar Resolucao
15	11151	ID_MENU_MANTER_ATA	ID do menu Manter Ata
16	11147	ID_MENU_VOTACAO_COLETIVA	ID do menu Votacao Coletiva
17	11149	ID_MENU_APURAR_VOTOS	ID do menu Apurar Votos
18	2507	ID_PERFIL_DEFESA_PREVIA	ID do Perfil Defesa Previa

*/

