/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.codgen;
import com.squareup.javapoet.AnnotationSpec;
import com.squareup.javapoet.CodeBlock;
import com.squareup.javapoet.JavaFile;
import com.squareup.javapoet.MethodSpec;
import com.squareup.javapoet.TypeSpec;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import javax.lang.model.element.Modifier;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletContextHandler;
import org.eclipse.jetty.servlet.ServletHolder;
import org.glassfish.jersey.servlet.ServletContainer;
import eu.generator.resources.RESTResources;
import java.net.SocketException;

import org.eclipse.californium.core.CoapResource;
import org.eclipse.californium.core.CoapServer;
import org.eclipse.californium.core.coap.CoAP;
import org.eclipse.californium.core.server.resources.CoapExchange;


import static org.eclipse.jetty.servlet.ServletContextHandler.NO_SESSIONS;
/**
 *
 * @author cripan
 */
public class ServerGen {
    
    public static MethodSpec  httpServer(){
  
    
     MethodSpec.Builder mainHttp = MethodSpec.methodBuilder("main")
    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
    .returns(void.class)
    .addParameter(String[].class, "args")
    .addException(Exception.class) ;
             
    mainHttp
            .addStatement("$T address = InetAddress.getByAddress(new byte[] {(byte)192,(byte)168,(byte)1,(byte)36})",InetAddress.class )
           .addStatement("System.out.println(\"IP address: \" + address.getHostAddress());\n" +
"      System.out.println(\"Computer Name:\"+address.getHostName());")
            .addStatement("$T socketAddress = new InetSocketAddress(address, 8088)",InetSocketAddress.class )
            .addStatement(" $T server = new Server(socketAddress)", Server.class)
            .addStatement(" $T  servletContextHandler = new ServletContextHandler(NO_SESSIONS)", ServletContextHandler.class)
            .addStatement("servletContextHandler.setContextPath(\"/\")")
            .addStatement(" server.setHandler(servletContextHandler)")
            .addStatement("$T servletHolder = servletContextHandler.addServlet($T.class, \"/*\")",ServletHolder.class,ServletContainer.class)
            .addStatement("servletHolder.setInitOrder(0)")
             .addStatement("servletHolder.setInitParameter ("+
                 "\"jersey.config.server.provider.classnames\",\n" +
"              $T.class.getCanonicalName())",  RESTResources.class)
            .beginControlFlow("try")
            .addStatement("server.start()")
            .addStatement("server.join()")
            .nextControlFlow("catch(Exception e)")
            .addStatement("e.printStackTrace()")
            .addStatement("server.stop()")
            .addStatement("server.destroy()")
            .endControlFlow() ;
        
     MethodSpec main= mainHttp.build();
        return main;
        
  } 


 public static MethodSpec  coapServer(){
  
    
     MethodSpec.Builder mainCoap = MethodSpec.methodBuilder("main")
    .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
    .returns(void.class)
    .addParameter(String[].class, "args");
    
             
    mainCoap.beginControlFlow("try")
            .addStatement("ServerApplication server = new ServerApplication()")
            .addStatement("server.start()")
            .nextControlFlow("catch($T e)",SocketException.class)
            .addStatement("System.err.println(\"Failed to initialize server: \" + e.getMessage())")
            .endControlFlow() ;
        
     MethodSpec main= mainCoap.build();
        return main;
        
  } 
 
  public static MethodSpec  CoapConstructor(){
  
     MethodSpec.Builder constCoap = MethodSpec.constructorBuilder()        
    .addModifiers(Modifier.PUBLIC)
    .addException(SocketException.class) 
    .addStatement("add(new $T())",RESTResources.class );
           
     MethodSpec constructor= constCoap.build();
        return constructor;      
  } 
 
  
    public static MethodSpec  publishResourceConst(){
  
     MethodSpec.Builder constResource = MethodSpec.constructorBuilder()
    .addModifiers(Modifier.PUBLIC)
    .addStatement(" super(\"publish\");\n" +

                    " getAttributes().setTitle(\"Publish Resource\")" );
           
     MethodSpec constructor= constResource.build();
        return constructor;      
  } 
  
  //METHOD TO GENERATE THE MAIN AND CREATE THE MAINCLASS      
     public static void Server (InterfaceMetadata MD){
         
     //class    
      TypeSpec ServerApplication; 
      JavaFile javaFile2;
      
    //Selection of the protocol based on the consumer CDL   
    
         
    if(MD.Protocol.equalsIgnoreCase("HTTP")){
          MethodSpec mainMethod=httpServer();
          
           ServerApplication = TypeSpec.classBuilder("ServerApplication")  
                .addMethod(mainMethod)
                .build();
           
           
     //Generation of the files
         javaFile2 = JavaFile.builder("Server",ServerApplication)
                .addFileComment("Auto generated")
                .addStaticImport(ServletContextHandler.class,"NO_SESSIONS")
                .build();       
          
    }else{ //COAP
         MethodSpec mainMethod=coapServer();
          MethodSpec constructor= CoapConstructor();
         // MethodSpec publishResourceConst= publishResourceConst();
       
          // TypeSpec publishResource = TypeSpec.classBuilder("PublishResource")
          //      .superclass(CoapResource.class)
          //      .addMethod(publishResourceConst)
          //      .build();
          
          
           ServerApplication = TypeSpec.classBuilder("ServerApplication")
                .superclass(CoapServer.class)
                .addMethod(constructor)
                .addMethod(mainMethod)
               // .addType(publishResource)
                .build();
           
          
           
           
            //Generation of the files
         javaFile2 = JavaFile.builder("Server",ServerApplication)
                .addFileComment("Auto generated")
                .build();
           
    }
    
     
         
   
     
 
 
        try{
            javaFile2.writeTo(Paths.get("C:\\Users\\cripan\\Desktop\\Code_generation\\ConsumerCodeGeneration\\ConsumerGenerationModulesSpring\\InterfaceLightweight\\src\\main\\java"));
        }catch (IOException ex){
            System.out.print("Exception:" + ex.getMessage());
        }
        
     }
}
