/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.generator.interfaceGen;

/**
 *
 * @author cripan
 */

import generator.codgen.InterfaceMetadata;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class ServerGen {

    public ServerGen() {
    }
     
    	public void GenerateServer(String protocol) {
            final ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(ServerGen.class.getClassLoader());
		 VelocityEngine velocityEngine = new VelocityEngine();
		   velocityEngine.init();
		try {
                    if(protocol.equalsIgnoreCase("COAP")){
                        Template t=velocityEngine.getTemplate("templates/serverCoap.vm");
		   VelocityContext context = new VelocityContext();
		   //context.put("port","8888");
		   Writer writer = new FileWriter (new File("C:\\Users\\cripan\\Desktop\\Code_generation\\InterfaceTranslatorSystem\\GenInterface\\src\\main\\java\\Server\\ServerApplication.java"));
		   t.merge(context,writer);
		   writer.flush();
		   writer.close();
                    }else{ //HTTP
                   Template t=velocityEngine.getTemplate("templates/serverHttp.vm");
		   VelocityContext context = new VelocityContext();
		   context.put("port","8888");
		   Writer writer = new FileWriter (new File("C:\\Users\\cripan\\Desktop\\Code_generation\\InterfaceTranslatorSystem\\GenInterface\\src\\main\\java\\Server\\ServerApplication.java"));
		   t.merge(context,writer);
		   writer.flush();
		   writer.close();
                        
                    }
		   
		      
        } catch (IOException e) {
     	   e.printStackTrace();}
	
                
          // set back default class loader
	         Thread.currentThread().setContextClassLoader(oldContextClassLoader);      
	}
        
        
        public void GenerateResources(InterfaceMetadata MD){
             final ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(ServerGen.class.getClassLoader());
		 VelocityEngine velocityEngine = new VelocityEngine();
		   velocityEngine.init();
                   try {
                   Template t=velocityEngine.getTemplate("templates/RESTResource.vm");
		   VelocityContext context = new VelocityContext();
		   //context.put("port","8888");
		   Writer writer = new FileWriter (new File("C:\\Users\\cripan\\Desktop\\Code_generation\\InterfaceTranslatorSystem\\GenInterface\\src\\main\\java\\eu\\generator\\resources\\RESTResources.java"));
		   t.merge(context,writer);
		   writer.flush();
		   writer.close();
                   
                    } catch (IOException e) {
                          e.printStackTrace();
                    }
	
                
          // set back default class loader
	         Thread.currentThread().setContextClassLoader(oldContextClassLoader);    
                   
                   
         }
    
}
