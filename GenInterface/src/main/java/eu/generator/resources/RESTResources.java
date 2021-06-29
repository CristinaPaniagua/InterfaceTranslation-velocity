package eu.generator.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.generator.consumer.RequestDTO_C0;
import eu.generator.consumer.ResponseDTO_C0;
import eu.generator.provider.RequestDTO_P0;
import eu.generator.provider.ResponseDTO_P0;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;

@Path("/interface")
public class RESTResources {


@POST
  @Path("/indoortemperature")
  @Produces(MediaType.APPLICATION_XML)
  @Consumes(MediaType.APPLICATION_XML)
 
  
  	public String indoortemperature(String receivedPayload) throws IOException {
		String response_out=" ";
		RequestDTO_C0 request=new RequestDTO_C0();
		
			  
		ObjectMapper objMapper_consumer=new XmlMapper();
		
	    	  RequestDTO_C0 requestrequest=objMapper_consumer.readValue(receivedPayload,RequestDTO_C0.class);
	  
	
	  PayloadTranslator pt= new PayloadTranslator();
	  RequestDTO_P0 request_P= pt.requestAdaptor(request);
	  
	  
	 		
		JsonFactory jsonFactory_objMapper_provider = new JsonFactory();
		ObjectMapper objMapper_provider=new ObjectMapper(jsonFactory_objMapper_provider);
		String payload=objMapper_provider.writeValueAsString(request_P);
		
	  	 
	  ResponseDTO_P0 response=new ResponseDTO_P0();
	  
	  try {
			
			response=ProviderInterpreter.consumeService("http://127.0.0.1:8899/test/get_json_r",payload);
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	  
	  
	  
	  ResponseDTO_C0 response_C=new ResponseDTO_C0();
	  response_C= pt.responseAdaptor(response);
	  
	  
	  if(response==null) {
			return response_out;
		}
		else {
	
			response_out= objMapper_consumer.writeValueAsString(response_C);
			return response_out;
		}
	  
		
	  
 }
	


  }