package fix.was;

import java.util.Hashtable;
import java.util.Properties;
import java.util.Set;

import javax.management.Notification;
import javax.management.NotificationListener;
import javax.management.ObjectName;

import com.ibm.websphere.management.AdminClient;
import com.ibm.websphere.management.AdminClientFactory;
import com.ibm.websphere.management.application.AppManagement;
import com.ibm.websphere.management.application.AppManagementProxy;

import br.support.lambda.P0E;
import br.utils.DevException;

public class DeployEarWAS {
	
    String earPath = "C:/dev/deploy/meuapp.ear";
    String appName = "MeuAppEAR";

    String cell   = "cell01";
    String node   = "nd1";
    String server = "server1";
    
    // ===== 1. Conectar ao WAS via SOAP =====
    private static final Properties props = new Properties();
    static {
        props.setProperty(AdminClient.CONNECTOR_HOST, "localhost");
        props.setProperty(AdminClient.CONNECTOR_PORT, "8880");
        props.setProperty("com.ibm.SOAP.connect.host", "localhost");
        props.setProperty("com.ibm.SOAP.connect.port", "8880");
        props.setProperty(AdminClient.CONNECTOR_TYPE, AdminClient.CONNECTOR_TYPE_SOAP);
        

        props.setProperty("com.ibm.SOAP.securityEnabled", "true");
        props.setProperty("com.ibm.SOAP.loginUserid", "wasadmin");
        props.setProperty("com.ibm.SOAP.loginPassword", "wasadmin");
        props.setProperty("com.ibm.SOAP.loginSource", "none");
        
    }
    
    private AdminClient adminClient;
    private ObjectName appMBean;
    private AppManagement appMgr;
    
	@SuppressWarnings("unchecked")
    private void init() {
		run("init", () -> {
            adminClient = AdminClientFactory.createAdminClient(props);
            Set<ObjectName> mbeans = adminClient.queryNames(new ObjectName("WebSphere:type=AppManagement,*"), null);
            appMBean = mbeans.iterator().next();
            appMgr = AppManagementProxy.getJMXProxyForClient(adminClient);
		});
    }

	private void exec(Evento evento, P0E<?> func) {
		run(evento.toString(), () -> {
			AppNotificationListener listener = new AppNotificationListener(evento);
			adminClient.addNotificationListener(appMBean, listener, null, null);
			func.call();
			listener.waitFor();
			adminClient.removeNotificationListener(appMBean, listener);
		});
	}
	
	private void stop() {
		exec(Evento.stop, () -> appMgr.stopApplication(appName, null, null));
	}
	
	private void uninstall() {
        Hashtable<String, Object> params = new Hashtable<>();
        params.put("cell", cell);
        params.put("node", node);
        params.put("server", server);
        exec(Evento.uninstall, () -> appMgr.uninstallApplication(appName, params, null));
	}

	private void install() {
        Hashtable<String, Object> params = new Hashtable<>();
        params.put("appName", appName);
        params.put("enable", "true");
        params.put("cell", cell);
        params.put("node", node);
        params.put("server", server);
        exec(Evento.install, () -> appMgr.installApplication(appName, params, null));
	}
	
	private void start() {
        exec(Evento.start, () -> appMgr.startApplication(appName, null, null));
	}
	
    private enum Evento {
    	install, uninstall, start, stop
    }
    
    private static class AppNotificationListener implements NotificationListener {
    	
    	private final Evento evento;
    	private boolean concluido;

		public AppNotificationListener(Evento evento) {
			this.evento = evento;
    	}
		
		private boolean conclusaoDoEventoEsperado(Notification notif) {

            String s = notif.getType().toLowerCase();
            String e = evento.toString();
            
            if (s.endsWith("." + e + ".completed")) {
            	return true;
            }

            if (s.endsWith("." + e + ".end")) {
            	return true;
            }
            
            if (s.endsWith("." + e + ".complete")) {
            	return true;
            }
            
            if (s.endsWith("." + e + ".success")) {
            	return true;
            }

            if (s.endsWith("." + e)) {
            	return true;
            }
            
            if (evento == Evento.uninstall && s.endsWith(".delete")) {
            	return true;
            }
            
            //logar evento para ver se chega algo que pode ser incluido na verificacao
            log(s);
            
            return false;
			
		}

        @Override
        public synchronized void handleNotification(Notification notif, Object handback) {
        	
        	if (conclusaoDoEventoEsperado(notif)) {
            	concluido = true;
            	notifyAll();
        	}
        	
        }
        
        public synchronized void waitFor() {

            long timeout = System.currentTimeMillis() + (5 * 60 * 1000); // 5 minutos

            try {
            	
                while (!concluido) {

                    long restante = timeout - System.currentTimeMillis();
                    if (restante <= 0) {
                        throw DevException.build("Timeout: evento '" + evento + "' não ocorreu.");
                    }

                    wait(restante);
                    
                }
                
            } catch (Exception e) {
                throw DevException.build(e);
            }
        }        

    }
    
    private static void log(String s) {
    	System.out.println(s);
    }

	private DeployEarWAS() {
		init();
		stop();
		uninstall();
		install();
		start();
    }
    
	public static void main(String[] args) {
    	new DeployEarWAS();
    }
	
	private static void run(String log, P0E<?> func) {
		
    	try {
    		log(log + " >>> start");
    		func.call();
    		log(log + " >>> success");
		} catch (Exception e) {
			log(log + " >>> fails");
			throw DevException.build(e);
		} finally {
			log(log + " >>> finally");
		}
		
	}
	
    
}
