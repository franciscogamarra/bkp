package br;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import br.support.comum.Print;
import br.utils.Cache;
import br.utils.DevException;
import br.utils.JsonUtils;
import br.utils.SSLUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/**")
public class Api {

	private final RestTemplate restTemplate;

	/**
	 * Atenção: modificar a porta para 8080 no arquivo:
	 * C:\dev\projs\cre-concessao-bndes-web\proxy.conf.json
	 * derrubar o front e subir novamente
	 *
	 * 
	 * C:\Users\francisco.gamarra\.m2\repository\br\com\sicoob\concessao\bndes\cre-concessao-bndes-api-comum
	 * 
	 */
	
	@AllArgsConstructor
	private static class Destino {
		final String url;
		final String replace;
	}
	
	protected static Destino LOCALHOST = new Destino("http://127.0.0.1:9080", null);
	protected static Destino HMG = new Destino("https://api-sisbr.homologacao.com.br", "/concessao-credito-bndes/v2");
	
//	private final Destino destino = HMG;
	private final Destino destino = LOCALHOST;

//	cre-concessao-bndes-api-web/api

	public Api() {
		SSLUtil.disableSSL();
		this.restTemplate = new RestTemplate();
	}

//    http://localhost:4200/cre-concessao-bndes-api-web/api/parametro/indicador-ativo/9102

//    GET http://127.0.0.1:9080/cre-concessao-bndes-api-web/api/parametro/indicador-ativo/9102

	@GetMapping("/cre-concessao-bndes-api-web/api/parametro/indicador-ativo/9102")
	public boolean m() {
		return true;
	}
	
    @GetMapping("/cre-concessao-bndes-api-web/api/enquadramentos-clientes/00213678166808/instituicoes/1")
    public ResponseEntity<String> getEnquadramento() {
        return getJson("enquadramentos-clientes/00213678166808");
    }

    @GetMapping("/cre-concessao-bndes-api-web/api/linhas-bndes")
    public ResponseEntity<String> getLinhaBndes() {
        return getJson("linhas-bndes");
    }
    
    @GetMapping("/cre-concessao-bndes-api-web/api/documento-anexo/recuperar-tipos-documento")
    public ResponseEntity<String> getTiposDocumento() {
        return getJson("tipos-documento");
    }
    
    @GetMapping("/cre-concessao-bndes-api-web/api/produtos-bndes")
    public ResponseEntity<String> getProdutosBndes() {
    	return getJson("produtos-bndes");
    }
    
    @GetMapping("/cre-concessao-bndes-api-web/api/programas-bndes/2424/percentual-valor-maximo")
    public ResponseEntity<String> getPercentualValorMaximo2024() {
    	return getJson("percentual-valor-maximo-2024");
    }
    
//    @GetMapping("/cre-concessao-bndes-api-web/api/propostas-credito-bndes/9361")
//    public ResponseEntity<String> getProposta31724() {
//    	return getJson("hmg/propostas/31724");
//    }
    
//    @GetMapping("/cre-concessao-bndes-api-web/api/propostas-credito-bndes/9404")
//    public ResponseEntity<String> getProposta9404() {
//    	return getJson("hmg/propostas/9404");
//    }
    
    @GetMapping("/cre-concessao-bndes-api-web/api/dominios/configuracao-financiamento-bacen?id=10010&idLinhaCredito=null&dataCadastro=null")
    public ResponseEntity<String> getConfiguracao() {
    	return ResponseEntity.ok("{}");
    }
    
//  http://localhost:4200/

    
    @GetMapping("/cre-concessao-bndes-api-web/api/programas-bndes")
    public ResponseEntity<String> getProgramas(
    		HttpServletRequest request,
            @RequestParam("idProdutoBndes") int idProdutoBndes,
            @RequestParam("idLinhaBndes") int idLinhaBndes) {

        if (idProdutoBndes == 6) {
        	
        	if (idLinhaBndes == 301) {
        		return getJson("programas6e301");
        	}

        	if (idLinhaBndes == 500) {
        		return getJson("programas6e500");
        	}
        	
        } else if (idProdutoBndes == 7) {

            if (idLinhaBndes == 301) {
            	return getJson("programas7e301");
            }
        	
        }
        
        
        return proxy(HttpMethod.GET, request, null);
        
    }

    @GetMapping("/cre-concessao-bndes-api-web/api/plataforma-credito/linha-credito")
    public ResponseEntity<String> getProgramas(
    		HttpServletRequest request,
            @RequestParam("idInstituicao") int idInstituicao,
            @RequestParam("idProduto") int idProduto,
            @RequestParam("idPrograma") int idPrograma,
            @RequestParam("usuarioLogado") String usuarioLogado
            ) {

        if (idInstituicao == 1 && idProduto == 6) {
        	
        	if (idPrograma == 2424) {
        		return ResponseEntity.ok().body("[]");
        	}
        	
        	if (idPrograma == 2271) {
        		return ResponseEntity.ok().body("[]");
        	}
        	
        }
        
        return proxy(HttpMethod.GET, request, null);
        
    }
    
    private static final Map<String, ResponseEntity<String>> RESOURCES = new HashMap<>();
    
	private ResponseEntity<String> getJson(String resourceName) {
		ResponseEntity<String> cache = RESOURCES.get(resourceName);
		if (cache == null) {
			try {
				cache = ResponseEntity.ok().body(JsonUtils.loadResource(resourceName));
				RESOURCES.put(resourceName, cache);
			} catch (Exception e) {
				throw DevException.build(e);
			}
		}
		return cache;
	}
    
	@RequestMapping(method = { RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE,
			RequestMethod.PATCH, RequestMethod.OPTIONS })
	public ResponseEntity<String> proxy(HttpMethod method, HttpServletRequest request, @RequestBody(required = false) Object body) {

		String path = request.getRequestURI();
		String query = request.getQueryString();
		String url = path + (query != null ? "?" + query : "");
		
		boolean get = method == HttpMethod.GET;
		
		if (get) {
			ResponseEntity<String> cache = Cache.get(url);
			if (cache != null) {
				return cache;
			}
		}
		
		if (destino.replace != null) {
			url = url.replace("/cre-concessao-bndes-api-web/api", destino.replace);
		}
		
		String key = method + " " + url;
		
		String fullUrl = destino.url + url;
		
		if (key.contains("configuracao-financiamento-bacen")) {
			return ResponseEntity.ok("{}");
		}
		
		Print.blocoVerde("> " + key);
		Print.blocoVerde("> " + method + " " + fullUrl);

		try {
			
			HttpHeaders headers = new HttpHeaders();
			Collections.list(request.getHeaderNames()).forEach(headerName -> {
				if (!headerName.equalsIgnoreCase("host") && !headerName.equalsIgnoreCase("content-length")
						&& !headerName.equalsIgnoreCase("connection")
						&& !headerName.equalsIgnoreCase("transfer-encoding")) {
					headers.add(headerName, request.getHeader(headerName));
				}
			});
			
			HttpEntity<Object> requestEntity = new HttpEntity<>(body, headers);
			
			ResponseEntity<String> res = restTemplate.exchange(fullUrl, method, requestEntity, String.class);
			
			if (url.contains("propostas-credito-bndes?")) {
				System.out.println();
			}
			
			int statusCode = res.getStatusCode().value();
			
			if (get && statusCode == 200) {
				Cache.set(url, res);
			}
			
			return res;
			
		} finally {
			Print.blocoAmarelo("< " + key);
		}
		
	}
}
