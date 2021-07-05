/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package eu.generator.interfaceGen;

import generator.codgen.InterfaceMetadata;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

/**
 *
 * @author cripan
 */
public class ProviderInterpreterGen {
    
    public void GenerateProvInterpreter(InterfaceMetadata MD) {
            final ClassLoader oldContextClassLoader = Thread.currentThread().getContextClassLoader();
            Thread.currentThread().setContextClassLoader(ServerGen.class.getClassLoader());
		 VelocityEngine velocityEngine = new VelocityEngine();
		   velocityEngine.init();
		try {
                    if(MD.getProtocol().equalsIgnoreCase("COAP")){
                        Template t=velocityEngine.getTemplate("templates/consumeServiceCoap.vm");
		   VelocityContext context = new VelocityContext();
		   context.put("method",MD.getMethod());
                   context.put("encoding",MD.getMediatype_response());
		   Writer writer = new FileWriter (new File("C:\\Users\\cripan\\Desktop\\Code_generation\\InterfaceTranslatorSystem\\GenInterface\\src\\main\\java\\eu\\generator\\resources\\ProviderInterpreter.java"));
		   t.merge(context,writer);
		   writer.flush();
		   writer.close();
                    }else{ //HTTP
                   Template t=velocityEngine.getTemplate("templates/consumeServiceHttp.vm");
		   VelocityContext context = new VelocityContext();
                    context.put("method",MD.getMethod());
                   context.put("encoding",MD.getMediatype_response());
		   Writer writer = new FileWriter (new File("C:\\Users\\cripan\\Desktop\\Code_generation\\InterfaceTranslatorSystem\\GenInterface\\src\\main\\java\\eu\\generator\\resources\\ProviderInterpreter.java"));
		   t.merge(context,writer);
		   writer.flush();
		   writer.close();
                        
                    }
		   
		      
        } catch (IOException e) {
     	   e.printStackTrace();}
	
                
          // set back default class loader
	         Thread.currentThread().setContextClassLoader(oldContextClassLoader);      
	}
        
    
}
