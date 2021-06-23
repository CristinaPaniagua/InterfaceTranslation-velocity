/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package HttpConsumer;

/**
 *
 * @author cripan
 */
public class HttpMain {

   public static String Method="GET";
   public static Boolean isRequest=false;
   public static Boolean isResponse=true;
   public static String Encoding="XML";
  
   
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
                 
                   consumer.PostJSONRR();
               } 
                if(Encoding.equalsIgnoreCase("XML")){
                   
                   consumer.PostXMLRR();
               }
                 if(Encoding.equalsIgnoreCase("CBOR")){
                    
                   consumer.PostCBORRR();
               }
            }
        }
        
        
        
        
        
        
        
    }
    
}
