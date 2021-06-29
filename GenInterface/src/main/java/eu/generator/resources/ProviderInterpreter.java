// Auto generated
package eu.generator.resources;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.underscore.lodash.U;
import eu.generator.consumer.ResponseDTO_C0;
import eu.generator.provider.ResponseDTO_P0;
import java.io.IOException;
import java.lang.Exception;
import java.lang.String;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class ProviderInterpreter{

 
	
		
	 public static ResponseDTO_P0 consumeService(String url, String payload) throws IOException {
		String payloadP=payload;
		CloseableHttpClient httpClient = HttpClients.createDefault();
		String result = "";
		
		ResponseDTO_P0 responseObj=null;
		try {
			HttpGet request = new HttpGet(url);
			request.addHeader("content-type", "application/json");
			CloseableHttpResponse response = httpClient.execute(request);	
	   
			try {
			HttpEntity entity = response.getEntity();
			
						
			JsonFactory jsonFactory_objMapperP = new JsonFactory();
			ObjectMapper objMapperP=new ObjectMapper(jsonFactory_objMapperP);
			
						
			

			if (entity != null) {
				result= EntityUtils.toString(entity);
				responseObj=objMapperP.readValue(result,ResponseDTO_P0.class);
				
				
        }
      }
      finally {
         response.close();
      }
    }
    finally {
      httpClient.close();
    }
    return responseObj;
  }


}