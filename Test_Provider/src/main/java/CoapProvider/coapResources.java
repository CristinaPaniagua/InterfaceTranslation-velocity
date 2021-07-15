/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoapProvider;

import ProviderDTO.ResponseDTO_P0;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;

/**
 *
 * @author cripan
 */
public class coapResources extends CoapResource {
  public coapResources() {
     super("publish");
         getAttributes().setTitle("Publish Resource");
  }
     public void handleGET(CoapExchange exchange) {
         
        int contentFormat= exchange.getRequestOptions().getContentFormat();
        System.out.println(contentFormat);
        ResponseDTO_P0 responseObj= new ResponseDTO_P0("temp",23,"celsius");
        String response=""; 
        JsonFactory jsonFactory_objMapperP = new JsonFactory();
        ObjectMapper objMapperP=new ObjectMapper(jsonFactory_objMapperP);
          //ObjectMapper objMapperP=new XmlMapper();
         //byte[] response=null;
        //CBORFactory cborFactory = new CBORFactory();
	//ObjectMapper objMapperP=new ObjectMapper(cborFactory);
                try{
                    response=objMapperP.writeValueAsString(responseObj);
                    //response=objMapperP.writeValueAsBytes(responseObj);
                } catch (IOException e) {
		    e.printStackTrace();
                }
        exchange.respond(CoAP.ResponseCode.CONTENT,response,contentFormat); 
	
     }
    public void handlePOST(CoapExchange exchange) {
        System.out.println(exchange.getRequestText());
         int contentFormat= exchange.getRequestOptions().getContentFormat();
        System.out.println(contentFormat);
        ResponseDTO_P0 responseObj= new ResponseDTO_P0("temp",23,"celsius");
        String response=""; 
        JsonFactory jsonFactory_objMapperP = new JsonFactory();
        ObjectMapper objMapperP=new ObjectMapper(jsonFactory_objMapperP);
          //ObjectMapper objMapperP=new XmlMapper();
         //byte[] response=null;
        //CBORFactory cborFactory = new CBORFactory();
	//ObjectMapper objMapperP=new ObjectMapper(cborFactory);
                try{
                    response=objMapperP.writeValueAsString(responseObj);
                    //response=objMapperP.writeValueAsBytes(responseObj);
                } catch (IOException e) {
		    e.printStackTrace();
                }
        exchange.respond(CoAP.ResponseCode.CONTENT,response,contentFormat); 
     }
    public void handlePUT(CoapExchange exchange) {
         System.out.println(exchange.getRequestText());
          exchange.respond(CoAP.ResponseCode.CONTENT);
         
     }
}
