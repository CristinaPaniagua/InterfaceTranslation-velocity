/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.codgen;

/**
 *
 * @author cripan
 */




import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.cbor.CBORFactory;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.github.underscore.lodash.U;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import eu.arrowhead.common.CodgenUtil;
import static generator.codgen.EncodingParser.createObjectMapper;
import eu.generator.consumer.RequestDTO_C0;
import eu.generator.provider.RequestDTO_P0;
import eu.generator.consumer.ResponseDTO_C0;
import java.io.IOException;
import java.nio.file.Paths;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.lang.model.element.Modifier;
import javax.ws.rs.GET;;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import org.apache.http.HttpEntity;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import eu.generator.provider.ResponseDTO_P0;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import org.eclipse.californium.core.CoapClient;
import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapResponse;
import org.eclipse.californium.core.Utils;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.coap.MediaTypeRegistry;
import org.eclipse.californium.core.network.config.NetworkConfig;
import org.eclipse.californium.core.network.config.NetworkConfigDefaultHandler;
import org.eclipse.californium.core.server.resources.CoapExchange;
import org.eclipse.californium.elements.exception.ConnectorException;
public class ResourceLWGen {
    
    
    
      public static MethodSpec testEcho (){
          
     AnnotationSpec produces= AnnotationSpec
                 .builder(Produces.class)
                 .addMember("value", "$T.TEXT_PLAIN", MediaType.class)
                 .build();
     
     /*
     AnnotationSpec consumes= AnnotationSpec
                 .builder(Consumes.class)
                 .addMember("value", "MediaType.APPLICATION_JSON")
                 .build();
     
     */
       AnnotationSpec path= AnnotationSpec
                 .builder(Path.class)
                 .addMember("value", "$S", "/test")
                 .build(); 
       
         
     MethodSpec.Builder methodgen = MethodSpec.methodBuilder("echo")
     .addModifiers(Modifier.PUBLIC)
     .returns(String.class)
     .addAnnotation(GET.class)
     .addAnnotation(path)
     .addAnnotation(produces)
     .addStatement("return \"Ready to go\"");
   
          return methodgen.build();
     }
    
    public static MethodSpec methodgen (InterfaceMetadata MD_C,InterfaceMetadata MD_P ){
 
    
    String pathP=MD_P.getPathResource();
    //String addressP=MD_P.ge
    
    MethodSpec.Builder methodgen;
    
  
    
    
    
    if(MD_C.Protocol.equalsIgnoreCase("HTTP")){
    
    
     AnnotationSpec produces;
     
     
        if(MD_C.getMediatype_response().equalsIgnoreCase("JSON")){
       
         produces= AnnotationSpec
                 .builder(Produces.class)
                 .addMember("value", "$T.APPLICATION_JSON", MediaType.class)
                 .build();
        
    }else if(MD_C.getMediatype_response().equalsIgnoreCase("XML")){
        produces= AnnotationSpec
                 .builder(Produces.class)
                 .addMember("value", "$T.APPLICATION_XML", MediaType.class)
                 .build();
    }else if(MD_C.getMediatype_response().equalsIgnoreCase("CBOR")){
        produces= AnnotationSpec
                 .builder(Produces.class)
                 .addMember("value", "\"application/cbor\"")
                 .build();
    }else{
          produces= AnnotationSpec
                 .builder(Produces.class)
                 .addMember("value", "\"*/*\"", MediaType.class)
                 .build();
    } 
     
     
     
    
          String Mediatype="";
    if(MD_C.getMediatype_request().equalsIgnoreCase("JSON")){
        Mediatype="APPLICATION_JSON";
        
    }else if(MD_C.getMediatype_request().equalsIgnoreCase("XML")){
        Mediatype="APPLICATION_XML";
    }
    
     
     
     AnnotationSpec consumes;
     if(MD_C.getMediatype_request().equalsIgnoreCase("CBOR")){
             consumes= AnnotationSpec
                 .builder(Consumes.class)
                 .addMember("value", "\"application/cbor\"")
                 .build(); 
     }else{
         consumes= AnnotationSpec
                 .builder(Consumes.class)
                 .addMember("value", "$T.$L", MediaType.class,Mediatype)
                 .build(); 
     }
         
     String pathResource=MD_C.getPathResource();
       AnnotationSpec path= AnnotationSpec
                 .builder(Path.class)
                 .addMember("value", "$S", pathResource)
                 .build();
       
       
       
       //'''''''''''''''''''''''START REAL METHOD''''''''''''''''''''''//
         
    methodgen = MethodSpec.methodBuilder(MD_C.getID())
     .addModifiers(Modifier.PUBLIC);
    //if(MD_C.getResponse()){
        methodgen.returns(ResponseDTO_C0.class);
   // }else{
        if(MD_C.getMediatype_request().equalsIgnoreCase("CBOR")){
          methodgen.returns(byte[].class);
      }else {
          methodgen.returns(String.class);
      } 
    //}
    
    methodgen.addException(IOException.class);
    
     
     //TODO: ADD THE REST OF THE METHODS (PUT AND DELETE)
     if(MD_C.getMethod().equalsIgnoreCase("POST")){
         methodgen.addAnnotation(POST.class);
     }else if(MD_C.getMethod().equalsIgnoreCase("GET"))  methodgen.addAnnotation(GET.class);
     
     
     
      methodgen.addAnnotation(path)
     .addAnnotation(produces);
      
       if(!MD_C.getMethod().equalsIgnoreCase("GET")){
       methodgen.addAnnotation(consumes);
             if(MD_C.getMediatype_request().equalsIgnoreCase("CBOR")){
          methodgen.addParameter(byte[].class,"receivedPayload");
            }else{
           methodgen.addParameter(String.class,"receivedPayload");
            }
       }
      
    } else{ //COAP
        
        if(MD_C.getMethod().equalsIgnoreCase("POST")){
             methodgen = MethodSpec.methodBuilder("handlePOST")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(CoapExchange.class,"exchange")
            .addStatement("exchange.getRequestText()");
          
        }else 
            {//if(MD_C.getMethod().equalsIgnoreCase("GET"))  
             methodgen = MethodSpec.methodBuilder("handleGET")
            .addModifiers(Modifier.PUBLIC)
            .addParameter(CoapExchange.class,"exchange");
       
            }
        
        
        methodgen.addStatement("System.out.println(exchange.getRequestText())")
               .addStatement("String receivedPayload=exchange.getRequestText()");
               
        
         }
    
    
    
   
    
       
       if (MD_C.getRequest() && MD_P.getRequest()){
            CodeBlock mapperCode_request=createObjectMapper(MD_C.getMediatype_request(),"objMapper_Request");
             methodgen.addStatement("RequestDTO_C0 payload=new RequestDTO_C0()")
                      .addCode(mapperCode_request);
         } 
       
       
       
       
       if(MD_C.getMediatype_response().equalsIgnoreCase("CBOR")){
           methodgen.addStatement(" byte[] response_out=null");
      }else {
          methodgen.addStatement(" String response_out=\" \"");
      }
               
         if(MD_P.getResponse() && MD_C.getResponse()){
               CodeBlock mapperCode_response=createObjectMapper(MD_P.getMediatype_response(),"objMapper_Response");
               methodgen.addStatement("ResponseDTO_P0 response=new ResponseDTO_P0()")
                       .addCode(mapperCode_response);
           }  
            
         
     
     methodgen.beginControlFlow("try");
       if(!MD_C.getMethod().equalsIgnoreCase("GET")){
           methodgen.addStatement("payload=objMapper_Response.readValue(receivedPayload, RequestDTO_C0.class)")
             .addStatement("System.out.println(payload.toString())");
       }else{
             methodgen.addStatement("String payload=null");
       }
     
      methodgen.addStatement("response=consumeService(\"http://127.0.0.1:8889/demo$L\",payload)",pathP);
     
      if (MD_P.getResponse() && MD_C.getResponse() ){
  //HTTP
        if(MD_C.Protocol.equalsIgnoreCase("HTTP")){
            
            methodgen.endControlFlow()
             .beginControlFlow("catch ($T e)",Exception.class)      
              .addStatement(" e.printStackTrace()")
              .endControlFlow()
              .addStatement("ResponseDTO_C0 response_C=new ResponseDTO_C0()")
              .addStatement(" response_C= responseAdaptor(response)"); 
            
           methodgen.beginControlFlow("if(response==null)")
                   .addStatement("  return response_out")
            .endControlFlow() 
            .beginControlFlow("else")
             .addStatement("response_out= objMapper_Response.writeValueAsString(response_C)")
            .addStatement("return response_out")
                       .endControlFlow();
           
       }else{
            
//COAP
         methodgen.addStatement("ResponseDTO_C0 response_C=new ResponseDTO_C0()")
                 .addStatement(" response_C= responseAdaptor(response)")
                 .beginControlFlow("if(response==null)")
                 .addStatement("exchange.respond( \"ERROR: EMPTY RESPONSE\")")
            .endControlFlow() 
            .beginControlFlow("else")
            .addStatement("response_out= objMapper_Response.writeValueAsString(response_C)");
        if(MD_C.getMediatype_request().equalsIgnoreCase("JSON")){
            methodgen.addStatement(" exchange.respond($T.ResponseCode.CONTENT,response_out,50)",CoAP.class);
        }else if(MD_C.getMediatype_request().equalsIgnoreCase("XML")){
          methodgen.addStatement(" exchange.respond($T.ResponseCode.CONTENT,response_out,41)",CoAP.class);
        }else{
           methodgen.addStatement(" exchange.respond(response_C) "); 
        }   
        
           methodgen.endControlFlow()
             .endControlFlow()
             .beginControlFlow("catch ($T e)",Exception.class)      
              .addStatement(" e.printStackTrace()")
              .endControlFlow(); 
    }
      

      }else{
     
       if(MD_C.Protocol.equalsIgnoreCase("HTTP")){
           methodgen.beginControlFlow("if(response==null)")
                   .addStatement("  return response")
            .endControlFlow() 
            .beginControlFlow("else")
            .addStatement("return response");
       }else{ //COAP
         methodgen.beginControlFlow("if(response==null)")
                 .addStatement("exchange.respond( \"ERROR: EMPTY RESPONSE\")")
            .endControlFlow() 
            .beginControlFlow("else");
        if(MD_C.getMediatype_request().equalsIgnoreCase("JSON")){
            methodgen.addStatement(" exchange.respond($T.ResponseCode.CONTENT,response,50)",CoAP.class);
        }else if(MD_C.getMediatype_request().equalsIgnoreCase("XML")){
          methodgen.addStatement(" exchange.respond($T.ResponseCode.CONTENT,response,41)",CoAP.class);
        }else
             methodgen.addStatement(" exchange.respond(response) ");
    }
       
        methodgen.endControlFlow();
      }
     
   
    
    
  
           // exchange.respond(CoAP.ResponseCode.CONTENT, "{\"Response\":\"POST_REQUEST_SUCCESS\"}", 50);
    
    
    
          return methodgen.build();
     }
    
 public static MethodSpec  publishResourceConst(){
  
     MethodSpec.Builder constResource = MethodSpec.constructorBuilder()
    .addModifiers(Modifier.PUBLIC)
    .addStatement(" super(\"publish\");\n" +

                    " getAttributes().setTitle(\"Publish Resource\")" );
           
     MethodSpec constructor= constResource.build();
        return constructor;      
  } 
          
    
     public static MethodSpec consumeService (InterfaceMetadata MD_C, InterfaceMetadata MD_P){
         
         
     //TRANSLATION BLOCK OF THE RESPONSE TO THE APPROPIATE ENCODING 
      CodeBlock mapperResponseProvider=createObjectMapper(MD_P.getMediatype_response(),"objMapperP");
      CodeBlock mapperResponseConsumer=createObjectMapper(MD_C.getMediatype_response(),"objMapperC");    
         
         
     
         
      MethodSpec.Builder consumer = MethodSpec.methodBuilder("consumeService")
     .addModifiers(Modifier.PRIVATE)
     .addModifiers(Modifier.STATIC);
       if(MD_P.getResponse()){
        consumer.returns(ResponseDTO_P0.class);
    }else{
        if(MD_P.getMediatype_request().equalsIgnoreCase("CBOR")){
          consumer.returns(byte[].class);
      }else {
          consumer.returns(String.class);
      } 
    }
     
     
     consumer.addParameter(String.class, "url");
     if(MD_C.getRequest()){
        consumer.addParameter(RequestDTO_C0.class, "payload");
     }else{
         consumer.addParameter(String.class, "payload");
     }
     
     
    
     consumer.addException(IOException.class);
     
     if(MD_P.getRequest() && MD_C.getRequest()){
         consumer.addStatement(" RequestDTO_P0 payloadP= requestAdaptor(payload)");
     }else{
           consumer.addStatement(" String payloadP=payload");
     }
     
     
         
           if(MD_P.getProtocol().equalsIgnoreCase("COAP")){ //CONSUMER SIZE is COAP
               
                consumer.addStatement("//Adding Import $T",NetworkConfig.class)
                        .addStatement(" $T CONFIG_FILE = new File(\"Californium.properties\");\n" +
"	 String CONFIG_HEADER = \"Californium CoAP Properties file for Fileclient\";\n" +
"	 int DEFAULT_MAX_RESOURCE_SIZE = 2 * 1024 * 1024; // 2 MB\n" +
"	 int DEFAULT_BLOCK_SIZE = 512",File.class)
         .addStatement(" $T DEFAULTS = new NetworkConfigDefaultHandler() {\n" +
"\n" +
"		@Override\n" +
"		public void applyDefaults($T config) {\n" +
"			config.setInt(NetworkConfig.Keys.MAX_RESOURCE_BODY_SIZE, DEFAULT_MAX_RESOURCE_SIZE);\n" +
"			config.setInt(NetworkConfig.Keys.MAX_MESSAGE_SIZE, DEFAULT_BLOCK_SIZE);\n" +
"			config.setInt(NetworkConfig.Keys.PREFERRED_BLOCK_SIZE, DEFAULT_BLOCK_SIZE);\n" +
"		};}",NetworkConfigDefaultHandler.class,NetworkConfig.class)
     .addStatement("$T config = $T.createWithFile(CONFIG_FILE, CONFIG_HEADER, DEFAULTS)", NetworkConfig.class,NetworkConfig.class)
     .addStatement("NetworkConfig.setStandard(config)")
     .addStatement("$T uri=null", URI.class)
     //.addStatement("String[] args={\"0\"}")
     .addStatement("String responseText= \" \"")
     .beginControlFlow("try")
     .addStatement("uri = new URI(\"coap://192.168.1.35:5555/weatherstation/indoortemperature\")")
     .nextControlFlow("catch($T e)",URISyntaxException.class)
     .addStatement("System.err.println(\"Invalid URI: \" + e.getMessage())")
     .addStatement("System.exit(-1)")
     .endControlFlow()
     .addStatement("$T client= new $T(uri)",CoapClient.class,CoapClient.class)
     .addStatement("$T response = null",CoapResponse.class)
     .beginControlFlow("try");
     
     if(MD_P.getMethod().equalsIgnoreCase("GET")){
     consumer.addStatement("response = client.get()");
     }else
     { 
         
         
         if(MD_P.getMediatype_request().equalsIgnoreCase("JSON"))
         { 
          consumer.addStatement("String payloadS=new $T().writeValueAsString(payloadP)",ObjectMapper.class)
          .addStatement("System.out.println(\"Payload Sent: \" + payloadS)");
          
            if(MD_P.getMethod().equalsIgnoreCase("POST"))
            {
             consumer.addStatement("response = client.post(payloadS,$T.APPLICATION_JSON)",MediaTypeRegistry.class);
              }
            if(MD_P.getMethod().equalsIgnoreCase("PUT"))
            {
            consumer.addStatement("response = client.put(payloadS,$T.APPLICATION_JSON)",MediaTypeRegistry.class);
              }
         }
         
             if(MD_P.getMediatype_request().equalsIgnoreCase("XML"))
         { 
          consumer
          .addStatement("$T xmlMapper = new XmlMapper()",XmlMapper.class)
          .addStatement("String payloadS=xmlMapper.writeValueAsString(payload)")
          .addStatement("System.out.println(\"Payload Sent: \" + payloadS)");
             
             
            if(MD_P.getMethod().equalsIgnoreCase("POST"))
            {
             consumer.addStatement("response = client.post(payloadS,$T.APPLICATION_XML)",MediaTypeRegistry.class);
              }
            if(MD_P.getMethod().equalsIgnoreCase("PUT"))
            {
            consumer.addStatement("response = client.put(payloadS,$T.APPLICATION_XML)",MediaTypeRegistry.class);
              }
         }if(MD_P.getMediatype_request().equalsIgnoreCase("CBOR"))
         { 
             
         }
            
            
     }
    
      
     
     consumer.nextControlFlow("catch($T|$T e)",ConnectorException.class,IOException.class)
     .addStatement("System.err.println(\"Got an error: \" + e)")
     .endControlFlow()
     .beginControlFlow("if(response!=null)")
     .addStatement("System.out.println(response.getCode())")
    // .beginControlFlow("if (args.length > 1)")
     // .beginControlFlow("try ($T out = new $T(args[1]))",FileOutputStream.class,FileOutputStream.class)
     // .addStatement("out.write(response.getPayload())")
     // .nextControlFlow("catch ($T e)",IOException.class)
     // .addStatement("e.printStackTrace()")
    //  .endControlFlow()   
    //  .nextControlFlow("else")      
     .addStatement("responseText= response.getResponseText()") 
     .addStatement("System.out.println($T.prettyPrint(response))",Utils.class) 
    //  .endControlFlow() 
     .nextControlFlow("else")      
     .addStatement("System.out.println(\"No response received.\")")
     .endControlFlow() 
     .addStatement("client.shutdown()")
     .addCode(mapperResponseProvider)
     .addStatement("$T responseObj=objMapperP.readValue(responseText,ResponseDTO_P0.class)",ResponseDTO_P0.class)
     .addCode(mapperResponseConsumer);
     
     
      Boolean Payload=true;
       if(Payload==true){
            if(MD_C.getMediatype_response().equalsIgnoreCase("XML") && MD_P.getMediatype_response().equalsIgnoreCase("JSON")){
                 consumer.addStatement(" String result_out = \"\"") 
                         .addStatement("result_out= $T.jsonToXml(responseText)",U.class);
             }else if(MD_C.getMediatype_response().equalsIgnoreCase("JSON") && MD_P.getMediatype_response().equalsIgnoreCase("XML")){
                  consumer.addStatement(" String result_out = \"\"")
                          .addStatement("result_out= $T.xmlToJson(responseText)",U.class);
             }else if(MD_C.getMediatype_response().equalsIgnoreCase("CBOR")){
                  consumer.addStatement(" byte[] result_out")
                          .addStatement("result_out=objMapperC.writeValueAsBytes(responseObj)");
             }else{
                  consumer.addStatement("result_out=responseText");
            }
     
     if(MD_P.getResponse()){
        consumer.addStatement("return responseObj");
     }else{
           consumer.addStatement("return result_out");
     }
      

  
       }        
               
           
       }else{ //HTTP is the protocol by default --> TODO: add the error if there is not a permited protocol
            consumer
     .addStatement("$T httpClient = $T.createDefault()",CloseableHttpClient.class,HttpClients.class)
     .addStatement(" String result_in = \"\"") 
     .addStatement(" String result_out = \"\"") 
     .addStatement("$T responseObj=null",ResponseDTO_P0.class)
     .beginControlFlow("try");
      //TODO: ADD THE REST OF THE METHODS (PUT AND DELETE)
     
      
      if(MD_P.getMethod().equalsIgnoreCase("GET")){
       consumer.addStatement(" $T request = new $T(url)",HttpGet.class,HttpGet.class);
       
       
         String Mediatype="";
      if(MD_P.getMediatype_request().equalsIgnoreCase("JSON")){
       consumer.addStatement(" request.addHeader(\"content-type\", \"application/json\")"); 
       
       
        
    }else if(MD_P.getMediatype_request().equalsIgnoreCase("XML")){
        consumer.addStatement(" request.addHeader(\"content-type\", \"application/xml\")") 
       
     ;
      
    
      }else if(MD_P.getMediatype_request().equalsIgnoreCase("CBOR")){
        consumer.addStatement(" request.addHeader(\"content-type\", \"pplication/cbor\")") 
     ;
      
    }
      
     }else if(MD_P.getMethod().equalsIgnoreCase("POST")){
         consumer.addStatement(" $T request = new $T(url)",HttpPost.class,HttpPost.class);
     
     String Mediatype="";
      if(MD_P.getMediatype_request().equalsIgnoreCase("JSON")){
       consumer.addStatement(" request.addHeader(\"content-type\", \"application/json\")") 
       .addStatement("String createdPayload=new $T().writeValueAsString(payloadP)",ObjectMapper.class);
       
        
    }else if(MD_P.getMediatype_request().equalsIgnoreCase("XML")){
        consumer.addStatement(" request.addHeader(\"content-type\", \"application/xml\")") 
       .addStatement("$T xmlMapper = new XmlMapper()",XmlMapper.class)
       .addStatement("String createdPayload=xmlMapper.writeValueAsString(payloadP)",ObjectMapper.class);
      
    
      }else if(MD_P.getMediatype_request().equalsIgnoreCase("CBOR")){
        consumer.addStatement(" request.addHeader(\"content-type\", \"pplication/cbor\")") 
       .addStatement("$T f = new CBORFactory()",CBORFactory.class)
       .addStatement("$T cborMapper = new ObjectMapper(f)",ObjectMapper.class)
       .addStatement("byte[] createdPayload = cborMapper.writeValueAsBytes(payloadP)");
      
    }
       
        consumer.addStatement(" System.out.println(\"Payload: \"+ createdPayload )");
                
       if(MD_P.getMediatype_request().equalsIgnoreCase("CBOR")){
            consumer.addStatement("$T eb = EntityBuilder.create()",EntityBuilder.class)
                    .addStatement("eb.setBinary(createdPayload)")
                    .addStatement("request.setEntity(eb.build())");
       }else{
                
       consumer.addStatement(" request.setEntity(new $T(createdPayload))",StringEntity.class);  
               
               } 
     }
            //CodeBlock mapperResponseProvider=createObjectMapper(MD_P.getMediatype_response(),"objMapperP");
            //CodeBlock mapperResponseConsumer=createObjectMapper(MD_C.getMediatype_response(),"objMapperC");
           
       consumer.addStatement(" $T response = httpClient.execute(request)",CloseableHttpResponse.class) 
       .beginControlFlow("try")  
            .addStatement(" $T entity = response.getEntity()", HttpEntity.class)
            .addCode(mapperResponseProvider)
            .addStatement("$T contentType = ContentType.getOrDefault(entity)",ContentType.class)
            .addStatement("String mimeType = contentType.getMimeType()") 
            .addStatement("System.out.println(\"Response MediaType: \"+mimeType)")
            .beginControlFlow("if (entity != null)")
            .addStatement(" result_in = $T.toString(entity)",EntityUtils.class)
            .addStatement("responseObj=objMapperP.readValue(result_in,ResponseDTO_P0.class)",ResponseDTO_P0.class)
            .addCode(mapperResponseConsumer)
            .addStatement("result_out=objMapperC.writeValueAsString(responseObj)");
            
       Boolean PayloadH=true;// TODO look if there is payload and change the flag according
       if(PayloadH==true){
            if(MD_C.getMediatype_response().equalsIgnoreCase("XML") && MD_P.getMediatype_response().equalsIgnoreCase("JSON")){
                 consumer.addStatement("result_out= $T.jsonToXml(result_in)",U.class);
             }else if(MD_C.getMediatype_response().equalsIgnoreCase("JSON") && MD_P.getMediatype_response().equalsIgnoreCase("XML")){
                  consumer.addStatement("result_out= $T.xmlToJson(result_in)",U.class);
            }else{
                  consumer.addStatement("result_out=result_in");
            }
       }
       
      
            
            consumer.addStatement("System.out.println(\"Response In: \"+ result_in)")
            .addStatement("System.out.println(\"Response Out: \"+ result_out)")
            .endControlFlow() //TODO: ADD ELSE 
       .endControlFlow() 
       .beginControlFlow("finally")
        .addStatement(" response.close()")
        .endControlFlow() 
      .endControlFlow() 
      .beginControlFlow("finally")
        .addStatement("httpClient.close()")
      .endControlFlow();
            
             if(MD_P.getResponse()){
        consumer.addStatement("return responseObj");
     }else{
           consumer.addStatement("return result_out");
     }
            
     
             
     
      
       }
         
    
      
                return consumer.build();
     }
     
     
     
    public static MethodSpec requestTransform(InterfaceMetadata MD_C, InterfaceMetadata MD_P){
  
    MethodSpec.Builder payload = MethodSpec.methodBuilder("requestAdaptor")
    .addModifiers(Modifier.PUBLIC)
    .addModifiers(Modifier.STATIC)
    .returns(RequestDTO_P0.class)
    .addParameter(RequestDTO_C0.class, "payload_C")
    .addStatement(" $T payload_P = new RequestDTO_P0()",RequestDTO_P0.class);
    
     Boolean match =false;
      ArrayList<String[]> elements_requestC=MD_C.elements_request.get(0).getElements();
      ArrayList<String[]> elements_requestP=MD_P.elements_request.get(0).getElements();
      ArrayList<String[]> metadata_requestC=MD_C.elements_request.get(0).getMetadata();
      ArrayList<String[]> metadata_requestP=MD_P.elements_request.get(0).getMetadata();
      Boolean NestedP=false;
      Boolean NestedC=false;
      String NewClassP=null;
      String NewClassC=null;
      String nameP="";
      String typeP="";
      String nameC="";
      String typeC="";
     int childNumberP=0;
      int childNumberC=0;
      
      System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(metadata_requestP);
        System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(elements_requestP);
       System.out.println("LIST OF RESPONSE ELEMENTS C:");
       CodgenUtil.readList(metadata_requestC);
        System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(elements_requestC);
       
     for (int i = 0; i < elements_requestC.size()-1; i++){ 
       nameC=elements_requestC.get(i)[0];
       typeC=elements_requestC.get(i)[1];
       match=false;
        
     if(nameC.equals("Newclass")){
               NewClassC= typeC;
               NestedC=true;
               
       }else if(nameC.equals("StopClass")){
               NestedC=false;
               childNumberC=0;
       }else{       
           
           
         
           if(nameC.equals("child")){
               childNumberC++;
                     nameC=elements_requestC.get(i)[1];
                     typeC=elements_requestC.get(i)[2];
           }
        
        
      System.out.println("Consumer reponse: " +nameC +" - "+ typeC);
       // match=false;
        
            for (int j = 0; j < elements_requestP.size(); j++){ 
               nameP=elements_requestP.get(j)[0];
               typeP=elements_requestP.get(j)[1];
                 
                 
                if(nameP.equals("Newclass")){
                    NewClassP= typeP;
                   
                   // if(NestedC==false) payload.addStatement(" eu.generator.consumer.$L $L = new  eu.generator.consumer.$L ()", Capitalize(NewClassC), NewClassC, Capitalize(NewClassC));
                        NestedP=true;
                        
                 }else if(nameP.equals("StopClass")){
                        NestedP=false;
                    
                    }else{  
                    
                    
                    if(nameP.equals("child")){
                         childNumberP++;
                        nameP=elements_requestP.get(j)[1];
                        typeP=elements_requestP.get(j)[2];
                    }
                        
                 
                 
                System.out.println("Provider Response: " +nameP +" - "+ typeP);
                 
               System.out.println("NestedP: " +NestedP +", NestedC: "+ NestedC);
                 
                 if(nameC.equals(nameP)){
                     match=true;
                System.out.println("NAME MATCH '''''''''''''''''''''''");
                     j= elements_requestP.size()+1;
                    } else{
                          String variantC= metadata_requestC.get(i)[2]; 
                          
                         System.out.println(j); 
                            String variantP=metadata_requestP.get(j)[2];
                            nameP=metadata_requestP.get(j)[0];
                            System.out.println("Variant: " +variantP);
                            if(nameC.equalsIgnoreCase(variantP)&& !variantP.equals(" ")){
                                System.out.println("VARIANT-NAME MATCH '''''''''''''''''''''''");
                                //nameC=metadata_responseC.get(j)[0];
                                typeP=metadata_requestP.get(j)[1];
                                System.out.println("Provider Response_variant: "+variantP+" -" +nameP +" - "+ typeP);
                               match=true;
                               
                            }else if(nameP.equalsIgnoreCase(variantC)&& !variantC.equals(" ")){
                                System.out.println("NAME-VARIANT MATCH '''''''''''''''''''''''");
                               /// nameC=metadata_responseP.get(j)[0];
                                typeP=metadata_requestP.get(j)[1];
                                System.out.println("Consumer Response_variant: "+variantC+" -" +nameC +" - "+ typeC);
                               match=true;
                              
                            } else if(variantC.equalsIgnoreCase(variantP)&& !variantP.equals(" ")&& !variantC.equals(" ")){
                                System.out.println("VARIANT-VARIANT MATCH '''''''''''''''''''''''");
                                //nameC=metadata_responseC.get(j)[0];
                                typeP=metadata_requestP.get(j)[1];
                                 System.out.println("Consumer Response_variant: "+variantC+" -"  +nameC +" - "+ typeC);
                                 System.out.println("Provider reponse_variant: " +variantP+" -" +nameP +" - "+ typeP);
                               match=true;
                               
                            }
                      
                        }
                     
                 }
              
                
                
               
          if(match){
              System.out.println("MATCH --- Provider: " +nameP +" - Consumer: " +nameC);
         
              if(NestedC){
                         payload.addStatement("$T $L=payload_C.get$L().get$L() ",CodgenUtil.getType(typeC), nameC,NewClassC,nameC);
                     } else {
                         payload.addStatement("$T $L=payload_C.get$L() ",CodgenUtil.getType(typeC), nameC,nameC);
                     }
                     
                     if(!typeC.equalsIgnoreCase(typeP)){
                         
                         if(typeC.equalsIgnoreCase("String")){
                             if(typeP.equalsIgnoreCase("Boolean")){
                              
                                      payload.addStatement("$T $L_P",CodgenUtil.getType(typeP),nameC)
                                     .beginControlFlow("if($L)",nameC)
                                       .addStatement("$L_P= true",nameC)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_P= false",nameC)
                                     .endControlFlow();
                           
                             }else payload.addStatement("$T $L_P= $L.parse$L($L)",CodgenUtil.getType(typeP),nameC,Capitalize(typeP), Capitalize(CodgenUtil.getType(typeP).toString()), nameC);
                         
                         }else if(typeP.equalsIgnoreCase("String")){
                             if(typeC.equalsIgnoreCase("Boolean")){
                              
                                     payload.addStatement("$T $L_P",CodgenUtil.getType(typeP),nameC)
                                     .beginControlFlow("if($L.equalsIgnoreCase(\"true\"))",nameC)
                                       .addStatement("$L_P= \"true\"",nameC)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_P= \"false\"",nameC)
                                     .endControlFlow();
                             }else payload.addStatement("$T $L_P= $L +\"\"",CodgenUtil.getType(typeP),nameC, nameC);
                             
                             
                            
                         }else if(typeC.equalsIgnoreCase("Boolean")){
                          
                             payload.addStatement("$T $L_P",CodgenUtil.getType(typeP),nameC)
                                     .beginControlFlow("if($L)",nameC)
                                       .addStatement("$L_P= 1",nameC)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_P= 0",nameC)
                                     .endControlFlow();
                         }else{
                             
                             
                         
                             if((numberTypeDef(typeP)>numberTypeDef(typeC))){
                             
                                 payload.addStatement("$T $L_P= $L",CodgenUtil.getType(typeP),nameC, nameC);
                             
                              }else if((numberTypeDef(typeP)<numberTypeDef(typeC))){
                                 payload.addStatement("$T $L_P=($T)$L",CodgenUtil.getType(typeP),nameC,CodgenUtil.getType(typeP), nameC);
                                 }
                         }
                         
                          
                     }else {
                          payload.addStatement(" $T $L_P=$L", CodgenUtil.getType(typeP),nameC, nameC);
                     }
                     
                     
                     
                     
                       if(NestedP){
                           
                        if(childNumberP<2)  payload.addStatement(" eu.generator.consumer.$L $L = new  eu.generator.consumer.$L ()", Capitalize(NewClassP), NewClassP, Capitalize(NewClassP));
                         payload.addStatement(" $L.set$L($L_P)",NewClassP, nameP, nameC)
                                 .addStatement("payload_P.set$L($L)",NewClassP,NewClassP);
                     } else {
                         payload.addStatement("payload_P.set$L($L_P)",nameP,nameC);
                     } 
                       
                       
                        match=false;
                j=elements_requestP.size()+1;
                       
   } else System.out.println("NO MATCH--Provider: " +nameP +" - Consumer: " +nameC); 
                
                
                
            }
            
          
          
            
            
           
          }
           
           
          
    }
        
     
     
     
     payload.addStatement("return payload_P");
           
     MethodSpec payloadTrans=payload.build();
        return payloadTrans;      
  } 
    
    
    //RESPONSE TRANSFOR CONSIDERING THAT THE CONSUMER IS THE REFENCE ( WE WANT A MATCH FOR ALL THE CONSUMER IS ASKING)
      public static MethodSpec responseTransform(InterfaceMetadata MD_C, InterfaceMetadata MD_P){
  
     MethodSpec.Builder payload = MethodSpec.methodBuilder("responseAdaptor")
    .addModifiers(Modifier.PUBLIC)
    .addModifiers(Modifier.STATIC)
    .returns(ResponseDTO_C0.class)
    .addParameter(ResponseDTO_P0.class, "payload_P")
    .addStatement(" $T payload_C= new ResponseDTO_C0()",ResponseDTO_C0.class);
  
    
     Boolean match =false;
      ArrayList<String[]> elements_responseC=MD_C.elements_response.get(0).getElements();
      ArrayList<String[]> elements_responseP=MD_P.elements_response.get(0).getElements();
      ArrayList<String[]> metadata_responseC=MD_C.elements_response.get(0).getMetadata();
      ArrayList<String[]> metadata_responseP=MD_P.elements_response.get(0).getMetadata();
      Boolean NestedP=false;
      Boolean NestedC=false;
      String NewClassP=null;
      String NewClassC=null;
      String nameP="";
      String typeP="";
      String nameC="";
      String typeC="";
      int childNumberP=0;
      int childNumberC=0;
      
      System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(metadata_responseP);
        System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(elements_responseP);
       System.out.println("LIST OF RESPONSE ELEMENTS C:");
       CodgenUtil.readList(metadata_responseC);
        System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(elements_responseC);
       
     for (int i = 0; i < elements_responseC.size(); i++){ 
       nameC=elements_responseC.get(i)[0];
       typeC=elements_responseC.get(i)[1];
       // System.out.println(i); 
        
       if(nameC.equals("Newclass")){
               NewClassC= typeC;
               NestedC=true;
               
       }else if(nameC.equals("StopClass")){
               NestedC=false;
               childNumberC=0;
       }else{       
           
           
         
           if(nameC.equals("child")){
               childNumberC++;
                     nameC=elements_responseC.get(i)[1];
                     typeC=elements_responseC.get(i)[2];
           }
        
        
      System.out.println("Consumer reponse: " +nameC +" - "+ typeC);
       // match=false;
        
            for (int j = 0; j < elements_responseP.size(); j++){ 
               nameP=elements_responseP.get(j)[0];
               typeP=elements_responseP.get(j)[1];
                 
                 
                if(nameP.equals("Newclass")){
                    NewClassP= typeP;
                    NestedP=true;
                        
                 }else if(nameP.equals("StopClass")){
                        NestedP=false;
                         childNumberP=0;
                    }else{  
                    
                    
                    if(nameP.equals("child")){
                        childNumberP++;
                        nameP=elements_responseP.get(j)[1];
                        typeP=elements_responseP.get(j)[2];
                    }
                        
                 
                 
                System.out.println("Provider Response: " +nameP +" - "+ typeP);
                 
               System.out.println("NestedP: " +NestedP +", NestedC: "+ NestedC);
                 
                 if(nameC.equals(nameP)){
                     match=true;
                System.out.println("NAME MATCH '''''''''''''''''''''''");
                     j= elements_responseP.size()+1;
                    } else{
                          String variantC= metadata_responseC.get(i)[2]; 
                          int m=j;
                         System.out.println(j); 
                            String variantP=metadata_responseP.get(m)[2];
                            nameP=metadata_responseP.get(m)[0];
                            System.out.println("Variant: " +variantP);
                            if(nameC.equalsIgnoreCase(variantP)&& !variantP.equals(" ")){
                                System.out.println("NAME-VARIANT MATCH '''''''''''''''''''''''");
                                //nameC=metadata_responseC.get(j)[0];
                                typeP=metadata_responseP.get(m)[1];
                                System.out.println("Provider Response_variant: "+variantP+" -" +nameP +" - "+ typeP);
                               match=true;
                               
                            }else if(nameP.equalsIgnoreCase(variantC)&& !variantC.equals(" ")){
                                System.out.println("VARIANT-NAME MATCH '''''''''''''''''''''''");
                               /// nameC=metadata_responseP.get(j)[0];
                                 typeP=metadata_responseP.get(m)[1];
                                System.out.println("Consumer Response_variant: "+variantC+" -" +nameC +" - "+ typeC);
                               match=true;
                              
                            } else if(variantP.equalsIgnoreCase(variantC)&& !variantC.equals(" ")&& !variantP.equals(" ")){
                                System.out.println("VARIANT-VARIANT MATCH '''''''''''''''''''''''");
                                //nameC=metadata_responseC.get(j)[0];
                                typeP=metadata_responseP.get(m)[1];
                                 System.out.println("Consumer Response_variant: "+variantC+" -"  +nameC +" - "+ typeC);
                                 System.out.println("Provider reponse_variant: " +variantP+" -" +nameP +" - "+ typeP);
                               match=true;
                               
                            }
                      
                        }
                     
                 }
              
                
                
               
          if(match){
              System.out.println("MATCH --- Provider: " +nameP +" - Consumer: " +nameC);
              
              if(NestedP){
                         payload.addStatement("$T $L=payload_P.get$L().get$L() ",CodgenUtil.getType(typeP), nameP,NewClassP,nameP);
                     } else {
                         payload.addStatement("$T $L=payload_P.get$L() ",CodgenUtil.getType(typeP), nameP,nameP);
                     }
                     
                     if(!typeP.equalsIgnoreCase(typeC)){
                         
                         if(typeP.equalsIgnoreCase("String")){
                             if(typeC.equalsIgnoreCase("Boolean")){
                              
                                      payload.addStatement("$T $L_C",CodgenUtil.getType(typeC),nameP)
                                     .beginControlFlow("if($L)",nameP)
                                       .addStatement("$L_C= true",nameP)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_C= false",nameP)
                                     .endControlFlow();
                           
                             }else payload.addStatement("$T $L_C= $L.parse$L($L)",CodgenUtil.getType(typeC),nameP,Capitalize(typeC), Capitalize(typeC), nameP);
                         
                         }else if(typeC.equalsIgnoreCase("String")){
                             if(typeP.equalsIgnoreCase("Boolean")){
                              
                                     payload.addStatement("$T $L_C",CodgenUtil.getType(typeC),nameP)
                                     .beginControlFlow("if($L.equalsIgnoreCase(\"true\"))",nameP)
                                       .addStatement("$L_C= \"true\"",nameP)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_C= \"false\"",nameP)
                                     .endControlFlow();
                             }else payload.addStatement("$T $L_C= $L +\"\"",CodgenUtil.getType(typeC),nameP, nameP);
                             
                             
                            
                         }else if(typeP.equalsIgnoreCase("Boolean")){
                          
                             payload.addStatement("$T $L_C",CodgenUtil.getType(typeC),nameP)
                                     .beginControlFlow("if($L)",nameP)
                                       .addStatement("$L_C= 1",nameP)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_C= 0",nameP)
                                     .endControlFlow();
                         }else{
                             
                             
                         
                             if((numberTypeDef(typeC)>numberTypeDef(typeP))){
                             
                                 payload.addStatement("$T $L_C= $L",CodgenUtil.getType(typeC),nameP, nameP);
                             
                              }else if((numberTypeDef(typeC)<numberTypeDef(typeP))){
                                 payload.addStatement("$T $L_C=($T)$L",CodgenUtil.getType(typeC),nameP,CodgenUtil.getType(typeC), nameP);
                                 }
                         }
                         
                          
                     }else {
                          payload.addStatement(" $T $L_C=$L", CodgenUtil.getType(typeC),nameP, nameP);
                     }
                     
                     
                     
                     
                       if(NestedC){
                           System.out.println("number of nested items: "+ childNumberC);
                       if(childNumberC<2)  payload.addStatement(" eu.generator.consumer.$L $L = new  eu.generator.consumer.$L ()", Capitalize(NewClassC), NewClassC, Capitalize(NewClassC));
                         payload.addStatement(" $L.set$L($L_C)",NewClassC, nameC, nameP)
                                 .addStatement("payload_C.set$L($L)",NewClassC,NewClassC);
                     } else {
                         payload.addStatement("payload_C.set$L($L_C)",nameC,nameP);
                     } 
                       
                match=false;
                j=elements_responseC.size()+1;
                       
                       
          } else System.out.println("NO MATCH--Provider: " +nameP +" - Consumer: " +nameC); 
                
                
                
            }
            
          
          
            
            
           
          }
           
           
          
    }
        
     
     
     payload.addStatement("return payload_C");
           
         
     MethodSpec payloadTrans=payload.build();
        return payloadTrans;      
  }
    
    
    /* //RESPONSE TRANSFOR CONSIDERING THAT THE PROVIDER IS THE REFENCE ( WE WANT A MATCH FOR ALL THE PROVIDER INFO)
      public static MethodSpec responseTransform(InterfaceMetadata MD_C, InterfaceMetadata MD_P){
  
     MethodSpec.Builder payload = MethodSpec.methodBuilder("responseAdaptor")
    .addModifiers(Modifier.PUBLIC)
    .addModifiers(Modifier.STATIC)
    .returns(ResponseDTO_C0.class)
    .addParameter(ResponseDTO_P0.class, "payload_P")
    .addStatement(" $T payload_C= new ResponseDTO_C0()",ResponseDTO_C0.class);
  
    
     Boolean match =false;
      ArrayList<String[]> elements_responseC=MD_C.elements_response.get(0).getElements();
      ArrayList<String[]> elements_responseP=MD_P.elements_response.get(0).getElements();
      ArrayList<String[]> metadata_responseC=MD_C.elements_response.get(0).getMetadata();
      ArrayList<String[]> metadata_responseP=MD_P.elements_response.get(0).getMetadata();
      Boolean NestedP=false;
      Boolean NestedC=false;
      String NewClassP=null;
      String NewClassC=null;
      String nameP="";
      String typeP="";
      String nameC="";
      String typeC="";
      int newClassesP=0;
      int newClassesC=0;
      
      System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(metadata_responseP);
        System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(elements_responseP);
       System.out.println("LIST OF RESPONSE ELEMENTS C:");
       CodgenUtil.readList(metadata_responseC);
        System.out.println("LIST OF RESPONSE ELEMENTS P:");
       CodgenUtil.readList(elements_responseC);
       
     for (int i = 0; i < elements_responseP.size(); i++){ 
       nameP=elements_responseP.get(i)[0];
       typeP=elements_responseP.get(i)[1];
       // System.out.println(i); 
        
       if(nameP.equals("Newclass")){
               NewClassP= typeP;
               NestedP=true;
               newClassesP++;
       }else if(nameP.equals("StopClass")){
               NestedP=false;
       }else{       
           
           
         
           if(nameP.equals("child")){
                     nameP=elements_responseP.get(i)[1];
                     typeP=elements_responseP.get(i)[2];
           }
        
        
      System.out.println("Provider reponse: " +nameP +" - "+ typeP);
       // match=false;
        
            for (int j = 0; j < elements_responseC.size(); j++){ 
               nameC=elements_responseC.get(j)[0];
               typeC=elements_responseC.get(j)[1];
                 
                 
                if(nameC.equals("Newclass")){
                    NewClassC= typeC;
                    newClassesC++;
                   // if(NestedC==false) payload.addStatement(" eu.generator.consumer.$L $L = new  eu.generator.consumer.$L ()", Capitalize(NewClassC), NewClassC, Capitalize(NewClassC));
                        NestedC=true;
                        
                 }else if(nameC.equals("StopClass")){
                        NestedC=false;
                    
                    }else{  
                    
                    
                    if(nameC.equals("child")){
                        nameC=elements_responseC.get(j)[1];
                        typeC=elements_responseC.get(j)[2];
                    }
                        
                 
                 
                System.out.println("Consumer Response: " +nameC +" - "+ typeC);
                 
               System.out.println("NestedP: " +NestedP +", NestedC: "+ NestedC);
                 
                 if(nameP.equals(nameC)){
                     match=true;
                System.out.println("NAME MATCH '''''''''''''''''''''''");
                     j= elements_responseC.size()+1;
                    } else{
                          String variantP= metadata_responseP.get(i)[2]; 
                          int m=j;
                         System.out.println(j); 
                            String variantC=metadata_responseC.get(m)[2];
                            nameC=metadata_responseC.get(m)[0];
                            System.out.println("Variant: " +variantC);
                            if(nameP.equalsIgnoreCase(variantC)&& !variantC.equals(" ")){
                                System.out.println("NAME-VARIANT MATCH '''''''''''''''''''''''");
                                //nameC=metadata_responseC.get(j)[0];
                                typeC=metadata_responseC.get(m)[1];
                                System.out.println("Consumer Response_variant: "+variantC+" -" +nameC +" - "+ typeC);
                               match=true;
                               
                            }else if(nameC.equalsIgnoreCase(variantP)&& !variantP.equals(" ")){
                                System.out.println("VARIANT-NAME MATCH '''''''''''''''''''''''");
                               /// nameC=metadata_responseP.get(j)[0];
                                typeC=metadata_responseC.get(m)[1];
                                System.out.println("Consumer Response_variant: "+variantP+" -" +nameP +" - "+ typeP);
                               match=true;
                              
                            } else if(variantP.equalsIgnoreCase(variantC)&& !variantC.equals(" ")&& !variantP.equals(" ")){
                                System.out.println("VARIANT-VARIANT MATCH '''''''''''''''''''''''");
                                //nameC=metadata_responseC.get(j)[0];
                                typeC=metadata_responseC.get(m)[1];
                                 System.out.println("Consumer Response_variant: "+variantC+" -"  +nameC +" - "+ typeC);
                                 System.out.println("Provider reponse_variant: " +variantP+" -" +nameP +" - "+ typeP);
                               match=true;
                               
                            }
                      
                        }
                     
                 }
              
                
                
               
          if(match){
              System.out.println("MATCH --- Provider: " +nameP +" - Consumer: " +nameC);
              
              if(NestedP){
                         payload.addStatement("$T $L=payload_P.get$L().get$L() ",CodgenUtil.getType(typeP), nameP,NewClassP,nameP);
                     } else {
                         payload.addStatement("$T $L=payload_P.get$L() ",CodgenUtil.getType(typeP), nameP,nameP);
                     }
                     
                     if(!typeP.equalsIgnoreCase(typeC)){
                         
                         if(typeP.equalsIgnoreCase("String")){
                             if(typeC.equalsIgnoreCase("Boolean")){
                              
                                      payload.addStatement("$T $L_C",CodgenUtil.getType(typeC),nameP)
                                     .beginControlFlow("if($L)",nameP)
                                       .addStatement("$L_C= true",nameP)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_C= false",nameP)
                                     .endControlFlow();
                           
                             }else payload.addStatement("$T $L_C= $L.parse$L($L)",CodgenUtil.getType(typeC),nameP,Capitalize(typeC), Capitalize(typeC), nameP);
                         
                         }else if(typeC.equalsIgnoreCase("String")){
                             if(typeP.equalsIgnoreCase("Boolean")){
                              
                                     payload.addStatement("$T $L_C",CodgenUtil.getType(typeC),nameP)
                                     .beginControlFlow("if($L.equalsIgnoreCase(\"true\"))",nameP)
                                       .addStatement("$L_C= \"true\"",nameP)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_C= \"false\"",nameP)
                                     .endControlFlow();
                             }else payload.addStatement("$T $L_C= $L +\"\"",CodgenUtil.getType(typeC),nameP, nameP);
                             
                             
                            
                         }else if(typeP.equalsIgnoreCase("Boolean")){
                          
                             payload.addStatement("$T $L_C",CodgenUtil.getType(typeC),nameP)
                                     .beginControlFlow("if($L)",nameP)
                                       .addStatement("$L_C= 1",nameP)
                                       .endControlFlow()
                                     .beginControlFlow("else")
                                        .addStatement("$L_C= 0",nameP)
                                     .endControlFlow();
                         }else{
                             
                             
                         
                             if((numberTypeDef(typeC)>numberTypeDef(typeP))){
                             
                                 payload.addStatement("$T $L_C= $L",CodgenUtil.getType(typeC),nameP, nameP);
                             
                              }else if((numberTypeDef(typeC)<numberTypeDef(typeP))){
                                 payload.addStatement("$T $L_C=($T)$L",CodgenUtil.getType(typeC),nameP,CodgenUtil.getType(typeC), nameP);
                                 }
                         }
                         
                          
                     }else {
                          payload.addStatement(" $T $L_C=$L", CodgenUtil.getType(typeC),nameP, nameP);
                     }
                     
                     
                     
                     
                       if(NestedC){
                       if(newClassesC<2)  payload.addStatement(" eu.generator.consumer.$L $L = new  eu.generator.consumer.$L ()", Capitalize(NewClassC), NewClassC, Capitalize(NewClassC));
                         payload.addStatement(" $L.set$L($L_C)",NewClassC, nameC, nameP)
                                 .addStatement("payload_C.set$L($L)",NewClassC,NewClassC);
                     } else {
                         payload.addStatement("payload_C.set$L($L_C)",nameC,nameP);
                     } 
                       
                match=false;
                j=elements_responseC.size()+1;
                       
                       
          } else System.out.println("NO MATCH--Provider: " +nameP +" - Consumer: " +nameC); 
                
                
                
            }
            
          
          
            
            
           
          }
           
           
          
    }
        
     
     
     payload.addStatement("return payload_C");
           
         
     MethodSpec payloadTrans=payload.build();
        return payloadTrans;      
  } */
     
      public static String Capitalize( String name){
          name =name.substring(0, 1).toUpperCase() + name.substring(1,name.length()); 
          return name;
      }
     
     
      public static int numberTypeDef( String type){
          int typeNumber;
          
          
          //if(type.equalsIgnoreCase("String")) typeNumber=-1;
         //else if (type.equalsIgnoreCase("Boolean")) typeNumber=-2;
         //else
         if (type.equalsIgnoreCase("Integer")||type.equalsIgnoreCase("int")) typeNumber=2;
         else if (type.equalsIgnoreCase("Byte")) typeNumber=0;
         else if (type.equalsIgnoreCase("Double")) typeNumber=5;
         else if (type.equalsIgnoreCase("Float")) typeNumber=4;
         else if (type.equalsIgnoreCase("Short")) typeNumber=1;
         else if (type.equalsIgnoreCase("Long"))typeNumber=3;
         else typeNumber=-100;
          
          return typeNumber;
      }
      
      
      
    
     public static void ResourcesLWGen (InterfaceMetadata MD_C, InterfaceMetadata MD_P ){
         
  
       MethodSpec testEcho=  testEcho();
       MethodSpec methodgen=methodgen(MD_C, MD_P);
       MethodSpec consumeService =consumeService(MD_C,MD_P); 
       MethodSpec  constructor =publishResourceConst();
       MethodSpec  requestAdaptor=null;
       MethodSpec  responseAdaptor=null;
       
          if(MD_P.getResponse() && MD_C.getResponse()){
               responseAdaptor = responseTransform(MD_C,MD_P);
           }  
            
         if (MD_C.getRequest() && MD_P.getRequest()){
            requestAdaptor = requestTransform(MD_C,MD_P);
             
         }
     
        
        
        
      AnnotationSpec path= AnnotationSpec
                 .builder(Path.class)
                 .addMember("value", "$S", "/weatherstation")
                 .build();
      
      
             
     TypeSpec.Builder classGen =TypeSpec.classBuilder("RESTResources")
              .addModifiers(Modifier.PUBLIC)
             //.addMethod(testEcho)
             .addMethod(methodgen);
     
     if(!MD_C.getResponse() && !MD_P.getResponse()){
          classGen.addMethod(requestAdaptor);
            
        }else if (!MD_C.getRequest() && !MD_P.getRequest()){
            classGen.addMethod(responseAdaptor);
            
         }else{
             classGen.addMethod(requestAdaptor)
             .addMethod(responseAdaptor);
           
        }
             
             classGen.addMethod(consumeService);
     
     if(MD_C.Protocol.equalsIgnoreCase("COAP")){
         classGen.superclass(CoapResource.class)
                 .addMethod(constructor);
     }
        
     
     if(MD_C.Protocol.equalsIgnoreCase("HTTP")){
         classGen.addAnnotation(path);
     }
        TypeSpec RclassGen  = classGen.build();

               
 
  
        JavaFile javaFile2 = JavaFile.builder("eu.generator.resources",RclassGen)
                .addFileComment("Auto generated")
                .build();
        try{
            javaFile2.writeTo(Paths.get("C:\\Users\\cripan\\Desktop\\Code_generation\\ConsumerCodeGeneration\\ConsumerGenerationModulesSpring\\InterfaceLightweight\\src\\main\\java"));
        }catch (IOException ex){
            System.out.print("Exception:" + ex.getMessage());
        }
        
     }
    
    
    
}
