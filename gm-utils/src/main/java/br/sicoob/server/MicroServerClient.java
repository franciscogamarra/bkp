package br.sicoob.server;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class MicroServerClient {

    private static final String SERVER_URL = "http://localhost:8000/bla";

    @SuppressWarnings("deprecation")
	public static void main(String[] args) {
        try {
            URL url = new URL(SERVER_URL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            // Configuração para um método GET
//            connection.setRequestMethod("GET");
//            connection.setRequestMethod("POST");
            connection.setRequestMethod("PUT");

            // Obtenção da resposta
            BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String inputLine;
            StringBuffer response = new StringBuffer();

            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();

            // Impressão da resposta
            System.out.println(response.toString());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
