/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpProvider;

/**
 *
 * @author cripan
 */


import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;


public class JerseyApplication {
    public static void main(String[] args) throws Exception {
    InetAddress address = InetAddress.getByAddress(new byte[] {(byte)127,(byte)0,(byte)0,(byte)1});
    System.out.println("IP address: " + address.getHostAddress());
              System.out.println("Computer Name:"+address.getHostName());;
    InetSocketAddress socketAddress = new InetSocketAddress(address,8899);
     Server server = new Server(socketAddress);
     ServletContextHandler  servletContextHandler = new ServletContextHandler(NO_SESSIONS);
    servletContextHandler.setContextPath("/");
     server.setHandler(servletContextHandler);
    ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/*");
    servletHolder.setInitOrder(0);
    servletHolder.setInitParameter ("jersey.config.server.provider.classnames",
                      Resources.class.getCanonicalName());
    try {
      server.start();
      server.join();
    } catch(Exception e) {
      e.printStackTrace();
      server.stop();
      server.destroy();
    }
  }
}
