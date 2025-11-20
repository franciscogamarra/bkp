package gm.support;

import br.utils.ApiClient;

public class GetProposta {
	
	public static String get(int id) {
		return ApiClient.local.get("/propostas-credito-bndes/" + id).call();
	}

	public static void main(String[] args) {
		System.out.println(get(9361));
		
//		
//		http://localhost:4200/cre-concessao-bndes-api-web/api/propostas-credito-bndes/9361
	}
	
}
