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

import ConsumerDTO.Value;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;


public class ConsumerMethodsHttp {
 String URL="";
 
    public ConsumerMethodsHttp() {
    }
    
       public ConsumerMethodsHttp(String URL) {
           this.URL=URL;
    }
    
    
  // TEST GET JSON only Response
    public void GetJSONR (){
        
        try{
            String result = sendGET(URL);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } 
  
    
   // TEST GET XML only Response
    public void GetXMLR (){
        
            try{
            String result = sendGET(URL);
            System.out.println(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }   
    
    
     // TEST GET CBOR only Response
    public void GetCBORR (){
        
            try{
            byte[] result = sendGET_Bytes(URL);
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }     
    
      // TEST POST JSON Request-Response
    public void PostJSONRR (String payload){
        
        try{
          
             System.out.println("request: "+payload);
            String result = sendPOST_JSON(URL,payload);
            System.out.println("response: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    } 
  
    
   // TEST POST XML only Request-Response
    public void PostXMLRR (String payload){
        
            try{
           
             System.out.println("request:"+payload);
            String result = sendPOST_XML(URL,payload);
             System.out.println("response: "+result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }   
    
    
     // TEST POST CBOR Request-Response
    public void PostCBORRR ( byte[]  payload){
        
            try{
              
            byte[] result = sendPOST_Bytes(URL, payload);
            System.out.println(result.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }    
    
    
    
    
    
  // AUXILIAR METHODS
    private static String sendPOST_JSON(String url, String payload) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        
        try {

            HttpPost request = new HttpPost(url);

       // add request headers
            request.addHeader("content-type", "application/json");
             request.setEntity(new StringEntity(payload.toString()));
            CloseableHttpResponse response = httpClient.execute(request);
           
            try {

                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                     result = EntityUtils.toString(entity);
                  
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    
        return result;
    }
    
    
     private static String sendPOST_XML(String url, String payload) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        
        try {

            HttpPost request = new HttpPost(url);

       // add request headers
            request.addHeader("content-type", "application/xml");
             request.setEntity(new StringEntity(payload.toString()));
            CloseableHttpResponse response = httpClient.execute(request);
           
            try {

                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                     result = EntityUtils.toString(entity);
                  
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    
        return result;
    }
        
        
      private static String sendPOST_CSV(String url, String payload) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String result = "";
        
        try {

            HttpPost request = new HttpPost(url);

       // add request headers
            request.addHeader("content-type", "application/csv");
             
        // send a JSON data
        
        
            request.setEntity(new StringEntity(payload.toString()));
            CloseableHttpResponse response = httpClient.execute(request);
           
            try {

                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                     result = EntityUtils.toString(entity);
                    
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    
        return result;
    }
        
    
    
    
    private static byte[] sendPOST_Bytes(String url, byte[]  payload) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        byte[] result=null;
        
        try {

            HttpPost request = new HttpPost(url);

       // add request headers
            request.addHeader("content-type", "application/cbor");

        
          EntityBuilder eb = EntityBuilder.create();
            eb.setBinary(payload);
            request.setEntity(eb.build());
            CloseableHttpResponse response = httpClient.execute(request);
           
            try {

                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
    
                     result = EntityUtils.toByteArray(entity);
                    
                
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    
        return result;
    }
        
        
      
       

     
        
       
    
    
    private static String sendGET(String url) throws IOException {
        
     CloseableHttpClient httpClient = HttpClients.createDefault();
      String result="";
        try {

            HttpGet request = new HttpGet(url);

            // add request headers
            //request.addHeader("custom-key", "mkyong");
            //request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    // return it as a String
                     result = EntityUtils.toString(entity);
                    System.out.println(result);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    
        return result;
    }
    
    
     private static byte[] sendGET_Bytes(String url) throws IOException {
        
     CloseableHttpClient httpClient = HttpClients.createDefault();
      byte[] result=null;
        try {

            HttpGet request = new HttpGet(url);

            // add request headers
            //request.addHeader("custom-key", "mkyong");
            //request.addHeader(HttpHeaders.USER_AGENT, "Googlebot");

            CloseableHttpResponse response = httpClient.execute(request);

            try {

                // Get HttpResponse Status
                System.out.println(response.getProtocolVersion());              // HTTP/1.1
                System.out.println(response.getStatusLine().getStatusCode());   // 200
                System.out.println(response.getStatusLine().getReasonPhrase()); // OK
                System.out.println(response.getStatusLine().toString());        // HTTP/1.1 200 OK

                HttpEntity entity = response.getEntity();
                if (entity != null) {
                   result = EntityUtils.toByteArray(entity);
                }

            } finally {
                response.close();
            }
        } finally {
            httpClient.close();
        }
    
        return result;
    }
    
    private static String ObjectToJson (Object ob){
        
        // Creating Object of ObjectMapper define in Jakson Api 
        ObjectMapper Obj = new ObjectMapper(); 
        String jsonStr="";
        try { 
  
            // get Oraganisation object as a json string 
            jsonStr = Obj.writeValueAsString(ob); 
  
            // Displaying JSON String 
            //System.out.println(jsonStr); 
        } 
  
        catch (IOException e) { 
            e.printStackTrace(); 
        } 
    return jsonStr;
  
    }
}
