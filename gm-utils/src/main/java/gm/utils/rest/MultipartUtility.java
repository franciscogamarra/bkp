package gm.utils.rest;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

import javax.ws.rs.core.MediaType;

import gm.utils.comum.SystemPrint;

public class MultipartUtility {
	private static int count = 0;
    private final String boundary = "BLA-"+(count++)+"BLA-";
    private static final String LINE_FEED = "\r\n";
    private HttpURLConnection httpConn;
    private String charset = "UTF-8";
    private OutputStream outputStream;
    private PrintWriter writer;

    @SuppressWarnings("deprecation")
	public MultipartUtility(String requestURL) {

		try {

            // creates a unique boundary based on time stamp
            URL url = new URL(requestURL);
            httpConn = (HttpURLConnection) url.openConnection();
            httpConn.setUseCaches(false);
            httpConn.setDoOutput(true);    // indicates POST method
//            httpConn.setDoOutput(false);    // indicates POST method
            httpConn.setDoInput(true);
            httpConn.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + boundary);
            outputStream = httpConn.getOutputStream();
            writer = new PrintWriter(new OutputStreamWriter(outputStream, charset), true);

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

    }

    public void addFormField(String name, String value) {
        writer.append("--"+boundary).append(LINE_FEED);
        writer.append("Content-Disposition: form-data; name=\"" + name + "\"").append(LINE_FEED);
//        writer.append("Content-Type: text/plain; charset=" + charset).append(LINE_FEED);
        writer.append("Content-Type: "+MediaType.APPLICATION_JSON+"; charset=" + charset).append(LINE_FEED);
        writer.append(LINE_FEED);
        writer.append(value).append(LINE_FEED);
        writer.flush();
    }

    public void addFilePart(String fieldName, File uploadFile) {

    	try {

            String fileName = uploadFile.getName();
            writer.append("--" + boundary).append(LINE_FEED);
            writer.append("Content-Disposition: form-data; name=\"" + fieldName + "\"; filename=\"" + fileName + "\"").append(LINE_FEED);
            writer.append("Content-Type: " + URLConnection.guessContentTypeFromName(fileName)).append(LINE_FEED);
            writer.append("Content-Transfer-Encoding: binary").append(LINE_FEED);
            writer.append(LINE_FEED);
            writer.flush();

            FileInputStream inputStream = new FileInputStream(uploadFile);
            byte[] buffer = new byte[4096];
            int bytesRead = -1;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, bytesRead);
            }
            outputStream.flush();
            inputStream.close();
            writer.append(LINE_FEED);
            writer.flush();

		} catch (Exception e) {
			throw new RuntimeException(e);
		}

    }

    public void addHeaderField(String name, String value) {
        writer.append(name + ": " + value).append(LINE_FEED);
        writer.flush();
    }

    public List<String> finish() {

    	try {

            List<String> response = new ArrayList<>();
            writer.append(LINE_FEED).flush();
            writer.append("--"+boundary+"--").append(LINE_FEED);
            writer.close();

            int status = httpConn.getResponseCode();
            if (status != HttpURLConnection.HTTP_OK) {
                throw new IOException("Server returned non-OK status: " + status);
            }
			BufferedReader reader = new BufferedReader(new InputStreamReader(httpConn.getInputStream()));
			String line = null;
			while ((line = reader.readLine()) != null) {
			    response.add(line);
			}
			reader.close();
			httpConn.disconnect();
            return response;

		} catch (Exception e) {
			try {
				SystemPrint.err(httpConn.getResponseMessage());
			} catch (Exception e2) {
				// TODO: handle exception
			}
			throw new RuntimeException(e);
		}

    }

}
