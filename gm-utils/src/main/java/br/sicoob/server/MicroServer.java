package br.sicoob.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.EquipamentosLegados;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.FabricantesLegados;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.LinhasBndes;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.ProdutosBndes;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.Programas;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.ProgramasTipoInvestimento;
import br.sicoob.src.app.funcionalidades.proposta__credito__bndes.calculos.tests.mocks.TiposInvestimento;
import src.commom.utils.string.StringAfterFirst;

public class MicroServer {
	
	public static void main(String[] args) throws Exception {
		// Cria um servidor que escuta na porta 8000
		HttpServer server = HttpServer.create(new InetSocketAddress(9080), 0);
		// Definindo o contexto para a URL base e o handler
		server.createContext("/", new MyHandler());
		server.setExecutor(null); // cria um executor default
		server.start();
		System.out.println("Servidor iniciado na porta 9080");
	}

	static class MyHandler implements HttpHandler {
		
	    @Override
	    public void handle(HttpExchange httpExchange) throws IOException {
	        // Capturando o método HTTP e o URI
	        String requestMethod = httpExchange.getRequestMethod();
	        URI requestURI = httpExchange.getRequestURI();

	        // Capturando o payload
	        InputStream requestBody = httpExchange.getRequestBody();
	        StringBuilder payload = new StringBuilder();
	        BufferedReader reader = new BufferedReader(new InputStreamReader(requestBody, "UTF-8"));
	        String line;
	        while ((line = reader.readLine()) != null) {
	            payload.append(line);
	        }

	        // Imprimindo os detalhes da requisição
	        System.out.println("Método HTTP: " + requestMethod);
	        String uri = requestURI.toString();
	        uri = StringAfterFirst.get(uri, "/cre-concessao-bndes-api-web/api/").trim();
			System.out.println("URI: " + uri);
	        System.out.println("Payload: " + payload.toString());

	        // Configurando os cabeçalhos para permitir o CORS
	        httpExchange.getResponseHeaders().add("Access-Control-Allow-Origin", "*");
	        httpExchange.getResponseHeaders().add("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
	        httpExchange.getResponseHeaders().add("Access-Control-Allow-Headers", "*");

	        // Se for uma requisição preflight (OPTIONS), apenas retorna os cabeçalhos
	        if ("OPTIONS".equalsIgnoreCase(httpExchange.getRequestMethod())) {
	            httpExchange.sendResponseHeaders(204, -1);  // 204 No Content, e -1 significa que não há corpo na resposta.
	            return;
	        }
	        
//	        http://localhost:9080/cre-concessao-bndes-api-web/api/fabricantes-legado?termo=maquina
	        
	        String response;
	        
	        ProgramasTipoInvestimento.start();
	        
	        if (uri.contentEquals("produtos-bndes")) {
	        	response = ProdutosBndes.itens.list.toJson();
			} else if (uri.contentEquals("linhas-bndes")) {
				response = LinhasBndes.itens.list.toJson();
			} else if (uri.startsWith("programas-bndes?")) {
				response = Programas.itens.list.toJson();
			} else if (uri.startsWith("tipo-investimento/242")) {
				response = TiposInvestimento.itens.list.toJson();
//			} else if (uri.contentEquals("tipo-investimento/2424")) {
//				response = TiposInvestimento.itens2424.list.toJson();
//			} else if (uri.contentEquals("tipo-investimento/2425")) {
//				response = TiposInvestimento.itens2425.list.toJson();
			} else if (uri.startsWith("fabricantes-legado?termo=")) {
				response = FabricantesLegados.itens.list.toJson();
			} else if (uri.contentEquals("equipamentos-legado?termo=null&codigoFabricante=76840537000121&equipamentoNovoUsado=false")) {
				response = EquipamentosLegados.itens.list.toJson();
			} else {
				response = "{}";
			}
	        
	        byte[] responseBytes = response.getBytes("UTF-8");
	        httpExchange.sendResponseHeaders(200, responseBytes.length);
	        OutputStream os = httpExchange.getResponseBody();
	        os.write(responseBytes);
	        os.close();
	    }
	    
	}

}
