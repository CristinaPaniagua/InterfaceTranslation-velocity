// Auto generated
package eu.generator.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.underscore.lodash.U;
import eu.generator.consumer.ResponseDTO_C0;
import eu.generator.provider.ResponseDTO_P0;
import java.io.File;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.net.URI;
import java.net.URISyntaxException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;
import org.eclipse.californium.elements.exception.ConnectorException;

public class ProviderInterpreter{

 public static ResponseDTO_P0 consumeService(String url) throws IOException {
   
     File CONFIG_FILE = new File("Californium.properties");
        	 String CONFIG_HEADER = "Californium CoAP Properties file for Fileclient";
        	 int DEFAULT_MAX_RESOURCE_SIZE = 2 * 1024 * 1024; // 2 MB
        	 int DEFAULT_BLOCK_SIZE = 512;
     NetworkConfigDefaultHandler DEFAULTS = new NetworkConfigDefaultHandler() {

        		@Override
        		public void applyDefaults(NetworkConfig config) {
        			config.setInt(NetworkConfig.Keys.MAX_RESOURCE_BODY_SIZE, DEFAULT_MAX_RESOURCE_SIZE);
        			config.setInt(NetworkConfig.Keys.MAX_MESSAGE_SIZE, DEFAULT_BLOCK_SIZE);
        			config.setInt(NetworkConfig.Keys.PREFERRED_BLOCK_SIZE, DEFAULT_BLOCK_SIZE);
        		};};
				
    NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
    NetworkConfig.setStandard(config);
    URI uri=null;
    
	
    try {
      uri = new URI(url);
    } catch(URISyntaxException e) {
      System.err.println("Invalid URI: " + e.getMessage());
      System.exit(-1);
    }
    CoapClient client= new CoapClient(uri);
    CoapResponse response = null;

           String responseText= " ";
      
    try {
	        response = client.get();
		
    } catch(ConnectorException|IOException e) {
      System.err.println("Got an error: " + e);
    }
    if(response!=null) {
            responseText= response.getResponseText();
          } else {
      System.out.println("No response received.");
    }
    client.shutdown();
   
   			
		JsonFactory jsonFactory_objMapperP = new JsonFactory();
		ObjectMapper objMapperP=new ObjectMapper(jsonFactory_objMapperP);
			
					
   
   
    ResponseDTO_P0 responseObj=objMapperP.readValue(responseText,ResponseDTO_P0.class);
  
    return responseObj;
  }

}