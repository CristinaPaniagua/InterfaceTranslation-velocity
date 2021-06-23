package Server;

import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;

import eu.generator.resources.RESTResources;
import java.lang.Exception;
import java.lang.String;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;

class ServerApplication {
  public static void main(String[] args) throws Exception {
    InetAddress address = InetAddress.getByAddress(new byte[] {(byte)127,(byte)0,(byte)0,(byte)1});
    System.out.println("IP address: " + address.getHostAddress());
              System.out.println("Computer Name:"+address.getHostName());;
    InetSocketAddress socketAddress = new InetSocketAddress(address,8888);
     Server server = new Server(socketAddress);
     ServletContextHandler  servletContextHandler = new ServletContextHandler(NO_SESSIONS);
    servletContextHandler.setContextPath("/");
     server.setHandler(servletContextHandler);
    ServletHolder servletHolder = servletContextHandler.addServlet(ServletContainer.class, "/*");
    servletHolder.setInitOrder(0);
    servletHolder.setInitParameter ("jersey.config.server.provider.classnames",
                      RESTResources.class.getCanonicalName());
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