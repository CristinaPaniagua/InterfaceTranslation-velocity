/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpConsumer;

import ConsumerDTO.RequestDTO_C0;
import ConsumerDTO.Value;
import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

/**
 *
 * @author cripan
 */
public class HttpMain {

   public static String Method="GET";
   public static Boolean isRequest=false;
   public static Boolean isResponse=true;
   public static String Encoding="XML";
   public static Value value= new Value(23,"cel");
   public static RequestDTO_C0 request =new RequestDTO_C0("temp","Sweden",value);
   
   
    public static void main(String[] args) {
     
       //USE THE GENERATED INTERFACE URL! 
      ConsumerMethodsHttp consumer = new ConsumerMethodsHttp("http://127.0.0.1:8888/interface/test");
     
        if(Method.equalsIgnoreCase("GET")){
            if(!isRequest&&isResponse){
               if(Encoding.equalsIgnoreCase("JSON")){
                
                   consumer.GetJSONR();
               } 
                if(Encoding.equalsIgnoreCase("XML")){
                   
                   consumer.GetXMLR();
               }
                 if(Encoding.equalsIgnoreCase("CBOR")){
                  
                   consumer.GetCBORR();
               }
            }
        }
        
           if(Method.equalsIgnoreCase("POST")){
            if(isRequest&&isResponse){
                if(Encoding.equalsIgnoreCase("JSON")){
                 JsonFactory jsonFactory_objMapperP = new JsonFactory();
		ObjectMapper objMapper=new ObjectMapper(jsonFactory_objMapperP);
                 String payload="";
                try{
                payload=objMapper.writeValueAsString(request);
                }
		catch (Exception e) {
			e.printStackTrace();
		}
                   consumer.PostJSONRR(payload);
               } 
                if(Encoding.equalsIgnoreCase("XML")){
                ObjectMapper objMapper=new XmlMapper();
                String payload="";
                try{
                 payload=objMapper.writeValueAsString(request);
                }
		catch (Exception e) {
			e.printStackTrace();
		}
                   consumer.PostXMLRR(payload);
               }
                 if(Encoding.equalsIgnoreCase("CBOR")){
                      CBORFactory cborFactory = new CBORFactory();
		ObjectMapper objMapper=new ObjectMapper(cborFactory);
                    byte[] payload=null;
           
                try{
                    payload=objMapper.writeValueAsBytes(request);
                }
		catch (Exception e) {
			e.printStackTrace();
		}
                   consumer.PostCBORRR(payload);
               }
            }
        }
        
        
        
        
        
        
        
    }
    
}
