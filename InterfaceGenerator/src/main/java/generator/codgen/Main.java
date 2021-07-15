   /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.codgen;

import java.util.ArrayList;
import eu.generator.interfaceGen.ProviderInterpreterGen;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

import eu.generator.interfaceGen.ServerGen;





/**
 *
 * @author cripan
 */
public class Main {

private static InterfaceMetadata MD_Provider=null;
private static InterfaceMetadata MD_Consumer=null;
//private static TypeSafeProperties request = CodgenUtil.getProp("request_config");
private static ArrayList<ArrayList<String>> classesRequestP= new ArrayList<ArrayList<String>>();
private static ArrayList<String> classesResponseP= new ArrayList<String>();
private static ArrayList<ArrayList<String>> classesRequestC= new ArrayList<ArrayList<String>>();
private static ArrayList<String> classesResponseC= new ArrayList<String>();
    public static void main(String[] args) throws GenerationException 
    {
        
        String service = "indoortemperature";
        String system="providerTest";
        String systemURL="coap://localhost:5555";
        readCDL readConsumer = new readCDL();
        MD_Consumer= readConsumer.read("indoortemperature","consumer");
        Boolean MD_ConsumerValid=metedataValidation(MD_Consumer);
        readCDL readProvider = new readCDL();
        MD_Provider = readProvider.read("indoortemperature","provider");
        Boolean MD_ProviderValid=metedataValidation(MD_Provider);
        
        System.out.println(MD_ConsumerValid);
        System.out.println(MD_ProviderValid);
      
        
    if(MD_ConsumerValid && MD_ProviderValid){
        
    
        
        System.out.println(MD_Provider.toString());
        System.out.println(MD_Consumer.toString());
      
     
             
     if(MD_Provider.getRequest()){
           ClassGenSimple Request=new ClassGenSimple();
       
             for(int i=0; i<MD_Provider.elements_request.size();i++)
             {
                ArrayList<String[]> elements_requestP=MD_Provider.elements_request.get(i).getElements();
               classesRequestP.add(Request.classGen(elements_requestP,"RequestDTO_P"+i));
             }
           // for(int h=0;h<classesRequestP.size();h++)
             //   System.out.println("..........."+classesRequestP.get(h));
  
       }
        
       
        if(MD_Provider.getResponse()){
            ClassGenSimple Response=new ClassGenSimple();
      
                 for(int j=0; j<MD_Provider.elements_response.size();j++)
                 {
                    ArrayList<String[]> elements_responseP=MD_Provider.elements_response.get(j).getElements();
                    classesResponseP=Response.classGen(elements_responseP,"ResponseDTO_P"+j);
                 }
           
        }
        
        
         if(MD_Consumer.getRequest()){
           ClassGenSimple Request=new ClassGenSimple();
       
             for(int i=0; i<MD_Consumer.elements_request.size();i++)
             {
                ArrayList<String[]> elements_requestC=MD_Consumer.elements_request.get(i).getElements();
               classesRequestC.add(Request.classGen(elements_requestC,"RequestDTO_C"+i));
             }
           // for(int h=0;h<classesRequestC.size();h++)
                //System.out.println("..........."+classesRequestC.get(h));
  
       }
        
       
        if(MD_Consumer.getResponse()){
            ClassGenSimple Response=new ClassGenSimple();
      
                 for(int j=0; j<MD_Consumer.elements_response.size();j++)
                 {
                    ArrayList<String[]> elements_responseC=MD_Consumer.elements_response.get(j).getElements();
                    classesResponseC=Response.classGen(elements_responseC,"ResponseDTO_C"+j);
                 }
           
        }
        
        
        
        
        //if(MD_Provider.getProtocol().equalsIgnoreCase("COAP"))
        //oapGenerator.coap(MD_Provider);
    
         
        
            
   
        
        //Server generation.. Consumer Side
        ServerGen sg= new ServerGen();
         sg.GenerateServer(MD_Consumer.getProtocol());
         sg.GenerateResources(MD_Consumer,MD_Provider, systemURL);
        ProviderInterpreterGen pi= new ProviderInterpreterGen();
         pi.GenerateProvInterpreter(MD_Provider);
        //Payload Translator 
         ResourceLWGen.PayloadTranslator(MD_Consumer, MD_Provider);
      
        
        
       /* 
         try{
       execute();
        } catch (IOException e) {
		e.printStackTrace();
	} catch (InterruptedException e) {
		e.printStackTrace();
	}
        
       */ 
         } else {
          System.out.println(" ERROR: GENERATION ABORTED -- ONE OR MORE CDLs ARE MALFORMED ");
          // throw new GenerationException(" ONE OR MORE OF THE CDLs ARE MALFORMED ");
    }   
       
    }
    
    //EXECUTE BAT
   
   public static void execute() throws InterruptedException, IOException {
        
       
       ExecutorService executor = Executors.newSingleThreadExecutor();
        ProcessBuilder processBuilder = new ProcessBuilder();

        processBuilder.command(("C:\\Users\\cripan\\Desktop\\Code_generation\\InterfaceTranslatorSystem\\GenInterface\\config\\Init.bat"));

        try {

            Process process = processBuilder.start();

            executor.submit(new ProcessTask(process.getInputStream()));
           

           
        } finally {
            executor.shutdown();
        }
    

   }
   
   
   
   private static class ProcessTask implements Callable<List<String>> {

        private InputStream inputStream;

        public ProcessTask(InputStream inputStream) {
            this.inputStream = inputStream;
        }

        @Override
        public List<String> call() {
            return new BufferedReader(new InputStreamReader(inputStream))
                    .lines()
                    .collect(Collectors.toList());
        }
    }
   
   
public static Boolean metedataValidation (InterfaceMetadata MD){
    Boolean validation= true;
    if(MD.getProtocol().equals("")){
        validation= false;
    }else if(MD.getMethod().equals("")){
        validation= false;
    }else if(MD.getID()==null){
        validation=false;
        
    }else if(MD.getPathResource().equals("")){
         validation=false;
        
    } else if(MD.getRequest()){
        
        if(MD.getMediatype_request().equals("")){
            validation= false;
        }
        if(MD.getElements_request().isEmpty()){  //IT IS POSIBLE A EMPTY REQUEST? 
            validation= false;
        }
    }
    return validation ;
}
   
   
   
 }
    

       
    

