// Auto generated
package eu.generator.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.databind.ObjectMapper;
import eu.generator.consumer.ResponseDTO_C0;
import eu.generator.provider.ResponseDTO_P0;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/interface")
public class RESTResources {

  @GET
  @Path("/test")
  @Produces(MediaType.APPLICATION_XML)
 
  
  	public String indoortemperature() throws IOException {
		String response_out=" ";
		ResponseDTO_P0 response=new ResponseDTO_P0();
	  		ObjectMapper objMapper_Response=new XmlMapper();
	  		try {
			String payload=null;
			response=ProviderInterpreter.consumeService("http://127.0.0.1:8899/test/get_json_r",payload);
			}
		catch (Exception e) {
			e.printStackTrace();
		}
	
			ResponseDTO_C0 response_C=new ResponseDTO_C0();
	
			PayloadTranslator pt= new PayloadTranslator();
			response_C= pt.responseAdaptor(response);
	 
		if(response==null) {
			return response_out;
		}
		else {
	
			response_out= objMapper_Response.writeValueAsString(response_C);
			return response_out;
		}
  }
  
        }