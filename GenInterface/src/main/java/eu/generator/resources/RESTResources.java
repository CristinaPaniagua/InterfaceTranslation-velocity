// Auto generated
package eu.generator.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.underscore.lodash.U;
import eu.generator.consumer.RequestDTO_C0;
import eu.generator.consumer.ResponseDTO_C0;
import eu.generator.provider.RequestDTO_P0;
import eu.generator.provider.ResponseDTO_P0; 
import java.io.File;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import java.net.URI;
import java.net.URISyntaxException;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.exception.ConnectorException;

public class RESTResources extends CoapResource {
  public RESTResources() {
     super("publish");
         getAttributes().setTitle("Publish Resource");
  }

  public void handleGET(CoapExchange exchange) {
    		String response_out=" ";
	 
    	  System.out.println(exchange.getSourceAddress().toString());
          System.out.println(exchange.getQueryParameter(response_out));
          System.out.println(exchange.advanced().getEndpoint().getUri().getPath());
          System.out.println(exchange.advanced().getEndpoint().getUri().toString());
	ObjectMapper objMapper_consumer=new XmlMapper();
		
    


    ResponseDTO_P0 response=new ResponseDTO_P0();
    
        try {
            response=ProviderInterpreter.consumeService("coap://localhost:5683/");
	

          ResponseDTO_C0 response_C=new ResponseDTO_C0();
	  PayloadTranslator pt= new PayloadTranslator();
	  response_C= pt.responseAdaptor(response);
	  
	  
	  if(response==null) {
			System.out.println("ERROR: Response is null");
		}
		else {
	
			response_out= objMapper_consumer.writeValueAsString(response_C);
                exchange.respond(CoAP.ResponseCode.CONTENT,response_out,41); //41- application/xml

        		
		}
        }
        catch (Exception e) {
            e.printStackTrace();
	}
 
  }


}