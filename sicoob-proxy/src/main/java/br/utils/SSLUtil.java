package br.utils;

import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

public class SSLUtil {
	
	private SSLUtil() {}

    public static void disableSSL() {
        try {
            SSLContext sslc = SSLContext.getInstance("TLSv1.2");
            TrustManager[] trustManagerArray = { new NullX509TrustManager() };
            sslc.init(null, trustManagerArray, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sslc.getSocketFactory());
            HttpsURLConnection.setDefaultHostnameVerifier(new NullHostnameVerifier());
        } catch(Exception e) {
        	ExceptionsUtils.printTrace(e);
        }
    }

    private static class NullX509TrustManager implements X509TrustManager {
        
    	@Override
		public void checkClientTrusted(X509Certificate[] chain, String authType) {
        	/* apenas para nao quebrar */
        }
        
        @Override
		public void checkServerTrusted(X509Certificate[] chain, String authType) {
        	/* apenas para nao quebrar */
        }
        
        @Override
		public X509Certificate[] getAcceptedIssuers() {
            return getAcceptedIssuersPrivate();
        }
        
		private X509Certificate[] getAcceptedIssuersPrivate() {
			return new X509Certificate[0]; 
		}
		
    }

    private static class NullHostnameVerifier implements HostnameVerifier {
        @Override
		public boolean verify(String hostname, SSLSession session) {
            return getVerifyPrivate(hostname, session);
        }

		private boolean getVerifyPrivate(String hostname, SSLSession session) {
			Print.ln(hostname + " / " + session);
			return true;
		}
    }
}