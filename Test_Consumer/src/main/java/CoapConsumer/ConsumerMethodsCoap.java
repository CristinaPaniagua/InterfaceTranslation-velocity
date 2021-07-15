/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package CoapConsumer;


import ConsumerDTO.ResponseDTO_C0;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.elements.exception.ConnectorException;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;
import org.eclipse.californium.core.network.config.NetworkConfig.Keys;
/**
 *
 * @author cripan
 */
public class ConsumerMethodsCoap {
 
    
    
    
    	private static final File CONFIG_FILE = new File("Californium.properties");
	private static final String CONFIG_HEADER = "Californium CoAP Properties file for Fileclient";
	private static final int DEFAULT_MAX_RESOURCE_SIZE = 2 * 1024 * 1024; // 2 MB
	private static final int DEFAULT_BLOCK_SIZE = 512;

	private static NetworkConfigDefaultHandler DEFAULTS = new NetworkConfigDefaultHandler() {

		@Override
		public void applyDefaults(NetworkConfig config) {
			config.setInt(Keys.MAX_RESOURCE_BODY_SIZE, DEFAULT_MAX_RESOURCE_SIZE);
			config.setInt(Keys.MAX_MESSAGE_SIZE, DEFAULT_BLOCK_SIZE);
			config.setInt(Keys.PREFERRED_BLOCK_SIZE, DEFAULT_BLOCK_SIZE);
		}
	};
    
        
       String URL="";
 
    public ConsumerMethodsCoap() {
    }
    
       public ConsumerMethodsCoap(String URL) {
           this.URL=URL;
    }
	
       
        // TEST GET Text only Response
    public void GetStringR (){
        
     System.out.println("1");  
    NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
    NetworkConfig.setStandard(config);
    URI uri=null;
    
	  System.out.println("2");  
    try {
      uri = new URI(URL);
        System.out.println("3");  
    } catch(URISyntaxException e) {
      System.err.println("Invalid URI: " + e.getMessage());
      System.exit(-1);
    }
    CoapClient client= new CoapClient(uri);
    CoapResponse response = null;
     String responseText= " ";
      try {
            System.out.println("4");  
    response = client.get();
     
     } catch(ConnectorException|IOException e) {
      System.err.println("Got an error: " + e);
    }
      
       if(response!=null) {
         responseText= response.getResponseText();
         System.out.println(responseText);
        } else {
         System.out.println("No response received.");
         }
    client.shutdown();
     System.out.println("5");
        
    } 
  
    
   
    
    
     // TEST GET CBOR only Response
    public void GetCBORR  (){
        
       NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
    NetworkConfig.setStandard(config);
    URI uri=null;
    
	
    try {
      uri = new URI(URL);
    } catch(URISyntaxException e) {
      System.err.println("Invalid URI: " + e.getMessage());
      System.exit(-1);
    }
    CoapClient client= new CoapClient(uri);
    CoapResponse response = null;
     byte[] responseByte= null;
      try {
    response = client.get();
     
     } catch(ConnectorException|IOException e) {
      System.err.println("Got an error: " + e);
    }
      
       if(response!=null) {
         responseByte= response.getPayload();
          CBORFactory cborFactory = new CBORFactory();
            ObjectMapper objMapperP=new ObjectMapper(cborFactory);
            ResponseDTO_C0 responseObj=new ResponseDTO_C0();
               try {
            responseObj= objMapperP.readValue(responseByte, ResponseDTO_C0.class);
             } catch(Exception e) {
                System.err.println("Got an error: " + e);
                }
         System.out.println(responseObj.toString());
        } else {
         System.out.println("No response received.");
         }
    client.shutdown();
        
    }     
    
      // TEST POST JSON Request-Response
    public void PostJSONRR (String payload){
        
     NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
    NetworkConfig.setStandard(config);
    URI uri=null;
    
	
    try {
      uri = new URI(URL);
    } catch(URISyntaxException e) {
      System.err.println("Invalid URI: " + e.getMessage());
      System.exit(-1);
    }
    CoapClient client= new CoapClient(uri);
    CoapResponse response = null;
     String responseText= " ";
      try {
     
     response = client.post(payload,MediaTypeRegistry.APPLICATION_JSON);
     
     } catch(Exception e) {
      System.err.println("Got an error: " + e);
    }
      
       if(response!=null) {
         responseText= response.getResponseText();
         System.out.println(responseText);
        } else {
         System.out.println("No response received.");
         }
    client.shutdown();
        
    } 
  
    
   // TEST POST XML only Request-Response
    public void PostXMLRR (String payload){
        
          
     NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
    NetworkConfig.setStandard(config);
    URI uri=null;
    
	
    try {
      uri = new URI(URL);
    } catch(URISyntaxException e) {
      System.err.println("Invalid URI: " + e.getMessage());
      System.exit(-1);
    }
    CoapClient client= new CoapClient(uri);
    CoapResponse response = null;
     String responseText= " ";
      try {
     
     response = client.post(payload,MediaTypeRegistry.APPLICATION_XML);
     
     } catch(Exception e) {
      System.err.println("Got an error: " + e);
    }
      
       if(response!=null) {
         responseText= response.getResponseText();
         System.out.println(responseText);
        } else {
         System.out.println("No response received.");
         }
    client.shutdown();
        
        
    }   
    
    
     // TEST POST CBOR Request-Response
    public void PostCBORRR (byte[] payload){
    
               
     NetworkConfig config = NetworkConfig.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS);
    NetworkConfig.setStandard(config);
    URI uri=null;
    
	
    try {
      uri = new URI(URL);
    } catch(URISyntaxException e) {
      System.err.println("Invalid URI: " + e.getMessage());
      System.exit(-1);
    }
    CoapClient client= new CoapClient(uri);
    CoapResponse response = null;
    byte[] responseByte= null;
      try {
     
     response = client.post(payload,MediaTypeRegistry.APPLICATION_CBOR);
     
     } catch(Exception e) {
      System.err.println("Got an error: " + e);
    }
      
       if(response!=null) {
         responseByte= response.getPayload();
         System.out.println(responseByte.toString());
        } else {
         System.out.println("No response received.");
         }
    client.shutdown();
        
    }    
        
}
