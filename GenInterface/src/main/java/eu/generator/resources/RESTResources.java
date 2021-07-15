// Auto generated
package eu.generator.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
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

   @Override
    public void handlePOST(CoapExchange exchange){
        exchange.getRequestText();
        System.out.println(exchange.getRequestText());
        String receivedPayload=exchange.getRequestText();

        	
		String response_out=" ";
                JsonFactory jsonFactory_objMapper_consumer = new JsonFactory();
		ObjectMapper objMapper_consumer=new ObjectMapper(jsonFactory_objMapper_consumer);
		
	  
      

           try {
           RequestDTO_C0 request=objMapper_consumer.readValue(receivedPayload,RequestDTO_C0.class);
	  
	
	  PayloadTranslator pt= new PayloadTranslator();
	  RequestDTO_P0 request_P= pt.requestAdaptor(request);
	  
	  
	 		
		JsonFactory jsonFactory_objMapper_provider = new JsonFactory();
		ObjectMapper objMapper_provider=new ObjectMapper(jsonFactory_objMapper_provider);
		String payload=objMapper_provider.writeValueAsString(request_P);
		
	  	 
	  ResponseDTO_P0 response=new ResponseDTO_P0();
	  
	 
			
			response=ProviderInterpreter.consumeService("coap://localhost:5555/publish",payload);
                         ResponseDTO_C0 response_C=new ResponseDTO_C0();
                         response_C= pt.responseAdaptor(response);
	  
	  
	 if(response==null) {
			System.out.println("ERROR: Response is null");
		}
		else {
	
	                response_out= objMapper_consumer.writeValueAsString(response_C);
                exchange.respond(CoAP.ResponseCode.CONTENT,response_out,50); //50- application/json
	                }
                    }
		catch (Exception e) {
			e.printStackTrace();
		}


    }


  
}