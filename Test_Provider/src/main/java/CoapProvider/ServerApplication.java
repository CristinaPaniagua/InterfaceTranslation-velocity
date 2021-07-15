// Auto generated
package CoapProvider;

import java.net.SocketException;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.network.CoapEndpoint;

class ServerApplication extends CoapServer {
  public ServerApplication() throws SocketException {
    add(new coapResources());
  }

  public static void main(String[] args) {
    try {
      ServerApplication server = new ServerApplication();
      
       CoapEndpoint.Builder tmp = new CoapEndpoint.Builder();
          tmp.setPort(5555);
         server.addEndpoint(tmp.build());
	
      server.start();
    } catch(SocketException e) {
      System.err.println("Failed to initialize server: " + e.getMessage());
    }
  }
}
