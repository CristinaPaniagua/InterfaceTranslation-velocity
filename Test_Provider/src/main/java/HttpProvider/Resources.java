/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpProvider;

import ProviderDTO.ResponseDTO_P0;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import java.io.IOException;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;



/**
 *
 * @author cripan
 */
@Path("/test")
public class Resources {
    ResponseDTO_P0 responseObj= new ResponseDTO_P0("temp",23,"cel");
    
    //echo
      @GET
	@Path("/echo")
	@Produces(MediaType.TEXT_PLAIN)
	public String echo() {

		//String test="{\"Response\":\"hello world\"}";
                String test="Ready to go";
		return test; 

	}
    
    
    // TEST GET JSON only Response
    @GET
    @Path("/get_json_r")
    @Produces(MediaType.APPLICATION_JSON)
	public String getJSONR() {
                String response="";
                JsonFactory jsonFactory_objMapperP = new JsonFactory();
                ObjectMapper objMapperP=new ObjectMapper(jsonFactory_objMapperP);
                try{
                    response=objMapperP.writeValueAsString(responseObj);
                } catch (IOException e) {
		    e.printStackTrace();
                }
		return response; 
	}
    
    // TEST GET XML only Response
    @GET
    @Path("/get_xml_r")
    @Produces(MediaType.APPLICATION_XML)
	public String getXMLR() {
             String response="";
             ObjectMapper objMapperP=new XmlMapper();
	 try{
                    response=objMapperP.writeValueAsString(responseObj);
                } catch (IOException e) {
		    e.printStackTrace();
                }
		return response; 
	}    
        
    // TEST GET CBOR only Response
    @GET
    @Path("/get_cbor_r")
    @Produces("application/cbor")
	public byte[] getCBORR() {
		byte[] response=null;
                CBORFactory cborFactory = new CBORFactory();
		ObjectMapper objMapperP=new ObjectMapper(cborFactory);
                try{
                    response=objMapperP.writeValueAsBytes(responseObj);
                } catch (IOException e) {
		    e.printStackTrace();
                }
		return response; 
	} 
        
    //GET SHOULD NOT CONSUME ANYTHING    
    /*    
      // TEST GET JSON Request-Response
    @GET
    @Path("/get_json_rr")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
	public String getJSONRR(String Payload) {
            System.out.println(Payload);
             
		String response="{\"Response\":\"Data recieved:"+Payload+"\"}";
		return response; 
	}
    
    // TEST GET XML Request-Response
    @GET
    @Path("/get_xml_rr")
    @Consumes(MediaType.APPLICATION_XML)
    @Produces(MediaType.APPLICATION_XML)
	public String getXMLRR(String Payload) {
            System.out.println(Payload);
             
		String response="<Response>Data recieved:"+Payload+"</Response>";
		return response; 
	}    
        
    // TEST GET CBOR Request-Response
    @GET
    @Path("/get_cbor_rr")
    @Consumes("application/cbor")
    @Produces("application/cbor")
	public byte[] getCBORRR(byte[] Payload) {
           
            System.out.println(Payload.toString());
             
		byte[] response=Payload;
		return response; 
	}      
       
    

   */
        
     //TEST POST XML Request-Response    
        @POST
        @Path("/post_xml_rr")
        @Produces(MediaType.APPLICATION_XML)
        @Consumes(MediaType.APPLICATION_XML)
        public String postXMLRR(String Payload) {

		System.out.println(Payload);
             
                String response="<response>Data recieved</response>";
		return response; 

	}
        
        
      //TEST POST JSON Request-Response     
        
        @POST
        @Path("/post_json_rr")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_JSON)
        public String postJSONRR(String Payload) {

		System.out.println(Payload);
                String response="{\"response\":\"Data recieved\"}";
               
		return response; 

	}
     
     //TEST POST CBOR Request-Response      
        @POST
        @Path("/post_cbor_rr")
        @Produces("application/cbor")
        @Consumes("application/cbor")
        public byte[] postCBORRR(byte[] Payload) {

	System.out.println(Payload.toString());
             
		byte[] response=Payload;
		return response; 

	}
        
        
        
        // RESPONSE AND REQUEST DIFFERENT
        @POST
        @Path("/testTranslationAB")
        @Produces(MediaType.APPLICATION_XML)
        @Consumes(MediaType.APPLICATION_JSON)
        public String testMix(String Payload) {

		System.out.println(Payload);
                String test="<response>Data recieved</response>";
               
		return test; 

	}
        
        
               @POST
        @Path("/testTranslationBA")
        @Produces(MediaType.APPLICATION_JSON)
        @Consumes(MediaType.APPLICATION_XML)
        public String testMix2(String Payload) {

		System.out.println(Payload);
                String test="{\"response\":\"Data recieved\"}";
               
		return test; 

	}
        

        
        
        
        
        
        
       
}
