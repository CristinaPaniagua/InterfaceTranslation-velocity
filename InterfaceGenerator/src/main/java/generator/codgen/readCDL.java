/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.codgen;
/**
 *
 * @author Cristina Paniagua
 */

import eu.arrowhead.common.CodgenUtil;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;
import org.w3c.dom.Element;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import static java.lang.System.out;

public class readCDL {
     
     
    public   ArrayList<ElementsPayload> elements_request = new ArrayList<ElementsPayload>(); 
    public   ArrayList<ElementsPayload> elements_response = new ArrayList<ElementsPayload>();
    public   ArrayList<String[]> payload_request = new ArrayList<String[]>(); 
    public   ArrayList<String[]> payload_response = new ArrayList<String[]>();
    public   ArrayList<String[]>  metadata_request = new ArrayList<String[]>();
    public   ArrayList<String[]> metadata_response = new ArrayList<String[]>();
    public  boolean request;
    public  boolean response;
    public  boolean param;
    public   ArrayList<Param> parameters = new ArrayList<>();
    public  ArrayList<String> subpaths=new ArrayList<>();

    public readCDL() {
    }
    
public InterfaceMetadata read(String service,String System) throws GenerationException {
    String sec=null;
    String protocol=null;
    String path=null;
    String method=null;
    String mediatype_request=null;
    String mediatype_response=null;
    String ID=null;
     String complexType_response=null;
    String complexType_request=null;
    
    Reset();
    
    Map<String,String> CDLstorage= new HashMap<>();
    CDLstorage.put("provider","cdl_PROVIDER_WS.xml");
    CDLstorage.put("consumer","cdl_CONSUMER_WS.xml");
    
    
    String CDLName=CDLstorage.get(System);
   
   

    
      try  {
          
    	out.println(CDLName);
	 File File1 = new File(CDLName);
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc= dBuilder.parse(File1);
        
        boolean serviceFound=false;  
         
        int interfaceNumber=0;
    
        int  methodNumber=0;
        
        
     //PROVIDER   
        NodeList interfaces = doc.getElementsByTagName("interface");
    
       for(int j= 0; j<interfaces.getLength(); j++){

        Node interf = interfaces.item(j);
        Element ele1 = (Element) interf;
        NodeList methods = ele1.getElementsByTagName("method");
         for (int h= 0; h<methods.getLength(); h++){
              Node nMethod = methods.item(h);
              Element eMethod= (Element) nMethod;
              if(eMethod.getAttribute("id").equalsIgnoreCase(service)){  //check if the interface contains the service that we want to consume
             interfaceNumber=j;
             methodNumber=h;
            serviceFound=true;
              j=interfaces.getLength();
              h=methods.getLength();
                }
         }
       }
       
     
       
       
       if(serviceFound==true) {
 //The interface anaylize is the one that have the service. TODO what happend if we have two interfaces for the same service, the service name is different?       
       
        Node inter1 = interfaces.item(interfaceNumber);
              if (inter1.getNodeType() == Node.ELEMENT_NODE) {
                  Element ele1 = (Element) inter1;
                NodeList methods = ele1.getElementsByTagName("method");
        
       
//Protocol
             protocol=ele1.getAttribute("protocol");
       
//Method 
            
   
        Node nMethod1 =  methods.item(methodNumber);
        if(nMethod1.getNodeType() == Node.ELEMENT_NODE){
                Element eMethod= (Element) nMethod1;
                        ID=eMethod.getAttribute("id");
                        method=eMethod.getAttribute("name");
                        path=eMethod.getAttribute("path");
                        
                        
          //PARAM
           NodeList  paramR=eMethod.getElementsByTagName("param");
           if (paramR.getLength()==0){
               param=false;
                        
          //REQUEST                         
                         
                NodeList  lR=eMethod.getElementsByTagName("request");
                            
                         if(lR.getLength()==0){
                           request=false;
                         }else{
                           request=true;
                         
                         
                      
                     Node nR = lR.item(0);
                     if(nR.getNodeType() == Node.ELEMENT_NODE){
                            Element eR= (Element) nR;
                                
                              

//Format comparision
//Encoding
                            Node nEncode1 =eR.getElementsByTagName("encode").item(0);
                            Element eEncode1= (Element) nEncode1;
                            
                            mediatype_request=eEncode1.getAttribute("name");

                            // Payload
                             
                             
                             
                             NodeList  lpayload=eR.getElementsByTagName("payload");
                            
                         if(lpayload.getLength()==0){
                              out.println("No payload Request defined");
                               
                           }else{  
                             
                               Node npayload1 = lpayload.item(0);
                               
                             
                             Element epayload1=(Element) npayload1;
                             NodeList complex1=epayload1.getElementsByTagName("complextype");
                             //if(!complex1.equals(null)){
                                  Node ncomplex1=complex1.item(0); 
                                  Element ecomplex= (Element)ncomplex1;
                              complexType_request=ecomplex.getAttribute("type");   
                            // } 

                             Node childNode=epayload1.getFirstChild();
                             
                             while(childNode.getNextSibling()!=null){          
                                    childNode = childNode.getNextSibling(); 
                                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {    
                                   Element e =(Element) childNode;
                                   //String[] ele;
                                   String[] ele=new String[2];
                                   String tagname= e.getTagName();
                                   String[] metadata;
                                    if("complexelement".equals(tagname)){
    
                                        complexelFunction ("REQUEST",e,"Newclass");
                                        
         
                                 
                                   }else{
                                    ele[0]=e.getAttribute("name");
                                   ele[1]=e.getAttribute("type");
                                   //out.println(ele[0]+" - "+ele[1]);
                                   
                                    metadata=new String[4];
                                    metadata[2]= e.getAttribute("variation");
                                    metadata[3]= e.getAttribute("unit");
                                    metadata[0]=ele[0];
                                    metadata[1]=ele[1];
                                    //out.println(metadata[0]+" - "+metadata[1]+" - "+ metadata[2]);
                                   if(!ele[1].equals("null")){
                                       payload_request.add(ele);
                                       metadata_request.add(metadata); 
                                   }
                                      
                                   }
                                    
                                    
                               }
                              
                             }
                             
                            
                                 ElementsPayload payloadRequest=new ElementsPayload(payload_request, metadata_request);
                                 elements_request.add(payloadRequest);
                      
                                   
                         }//payload no null    
                     }
                               
                         
        } //close else    
                        // CodgenUtil.readList(metadata_request); 
                         
           //RESPONSE    
           
                NodeList  lRR=eMethod.getElementsByTagName("response");
                            
                         if(lRR.getLength()==0){
                           response=false;
                         } else{
                             response=true;
                      
                     Node nRR = lRR.item(0);
                     if(nRR.getNodeType() == Node.ELEMENT_NODE){
                            Element eRR= (Element) nRR;
                                
                              

//Format comparision
//Encoding
                            Node nEncode1 =eRR.getElementsByTagName("encode").item(0);
                            Element eEncode1= (Element) nEncode1;
                            
                            mediatype_response=eEncode1.getAttribute("name");
  
                            // Payload
                        
                             
                             
                         NodeList  lRpayload=eRR.getElementsByTagName("payload");
                            
                         if(lRpayload.getLength()==0){
                              out.println("No payload Response defined");
                               
                           }else{  
                             
                               Node npayload1 = lRpayload.item(0);
                               
                             Element epayload1=(Element) npayload1;
                             
                             NodeList complex2=epayload1.getElementsByTagName("complextype");
                             
                                  Node ncomplex2=complex2.item(0); 
                                  Element ecomplex= (Element)ncomplex2;
                              complexType_response=ecomplex.getAttribute("type");   
                            
                             
                   
                             Node childNode=epayload1.getFirstChild();

                             while(childNode.getNextSibling()!=null){          
                                    childNode = childNode.getNextSibling(); 
                                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {    
                                   Element e =(Element) childNode;
                                   String[] ele;
                                 
                                   String tagname= e.getTagName();

                                    if("complexelement".equals(tagname)){
    
                                        complexelFunction ("RESPONSE",e,"Newclass");
                                        
         
                                 
                                   }else{
                                   ele=new String[2];
                                   ele[0]=e.getAttribute("name");
                                   ele[1]=e.getAttribute("type");
                                     String[] metadata;
                                    metadata=new String[4];
                                    metadata[2]= e.getAttribute("variation");
                                    metadata[3]= e.getAttribute("unit");
                                    metadata[0]=ele[0];
                                    metadata[1]=ele[1];
                                   if(!ele[1].equals("null")){
                                       payload_response.add(ele);
                                       metadata_response.add(metadata); 
                                   }
                                      
                                   }
                               }
                              
                             }
                             ElementsPayload payloadResponse=new ElementsPayload(payload_response,metadata_response);
                              elements_response.add(payloadResponse);

                                   
                           }//Payload not null
                     }
                     }//close else RESPONSE=TRUE
                         
        }//END:NO parameters
           
           
 //********************************************************************************************************************************************************
          
           else{ //BEGINGING PARAMETERS
               param=true;
              
               for(int p=0; p<paramR.getLength();p++){
                    Node nparam = paramR.item(p);
                    if(nparam.getNodeType() == Node.ELEMENT_NODE){
                        Element eparam=(Element) nparam;
                        String name= eparam.getAttribute("name");
                        String type=eparam.getAttribute("type");
                        String style=eparam.getAttribute("style");        
                        String required=eparam.getAttribute("required");
                        
                        Param parameter=new Param(name,type,style,required);
                        parameters.add(parameter);
                        
                    }
                    
               }
                    
                    //REQUEST                         
                         
                NodeList  lR=eMethod.getElementsByTagName("request");
                            
                         if(lR.getLength()==0){
                           request=false;
                         }else{
                           request=true;
                         
                         
                      
                     Node nR = lR.item(0);
                     if(nR.getNodeType() == Node.ELEMENT_NODE){
                            Element eR= (Element) nR;
                                
                              

//Format comparision
//Encoding
                            Node nEncode1 =eR.getElementsByTagName("encode").item(0);
                            Element eEncode1= (Element) nEncode1;
                            
                            mediatype_request=eEncode1.getAttribute("name");

                            // Payload
                            
                             
                             NodeList  lpayload=eR.getElementsByTagName("payload");
                            
                         if(lpayload.getLength()==0){
                              out.println("No payload Request defined");
                               
                           }else{  
                             
                               Node npayload1 = lpayload.item(0);
                               
                             
                             
                             Element epayload1=(Element) npayload1;
                             
                        //Payload with options
                             NodeList options1=epayload1.getElementsByTagName("option");
                             
                            // out.println("options found, number:"+options1.getLength());
                             
                             
                             for(int k=0; k<options1.getLength();k++){
                                
                                 ArrayList<String[]> arrayPayload = new ArrayList<String[]>(); 
                                // out.println("option: "+k);
                                 //payload_request.clear();
                               
                                 
                                 Node noption1=options1.item(k);
                                 Element eoption1= (Element)noption1;
                                 String subpath=eoption1.getAttribute("value");
                                 subpaths.add(subpath);
                                 
                                  NodeList complex1=eoption1.getElementsByTagName("complextype");
                             //if(!complex1.equals(null)){
                                  Node ncomplex1=complex1.item(0); 
                                  Element ecomplex= (Element)ncomplex1;
                              complexType_request=ecomplex.getAttribute("type");   
                            // } 
                                
                             Node childNode=eoption1.getFirstChild();
                                 
                             
                               
                             while(childNode.getNextSibling()!=null){     
                                 
                                    childNode = childNode.getNextSibling(); 
                                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {  
                                      
                                   Element e =(Element) childNode;
                                   String[] ele;
                                   String[] metadata;
                                   String tagname= e.getTagName();
                                  // out.println(tagname);

                                    if("complexelement".equals(tagname)){
    
                                        arrayPayload=complexelFunction("REQUEST",e,"Newclass");
                                        
         
                                 
                                   }else{
                                   ele=new String[2];
                                   ele[0]=e.getAttribute("name");
                                   ele[1]=e.getAttribute("type");
                                   
                                   
                                   
                                    metadata=new String[4];
                                    metadata[2]= e.getAttribute("variation");
                                    metadata[3]= e.getAttribute("unit");
                                    metadata[0]=ele[0];
                                    metadata[1]=ele[1];
                                   if(!ele[1].equals("null")){
                                       arrayPayload.add(ele);
                                       metadata_request.add(metadata); 
                                   }
                                   
                                   
                                   }
                                   
                               }
                              
                             } //END WHILE NODES In the payload
                             
                             ElementsPayload payloadRequest =new ElementsPayload(arrayPayload,metadata_request);
                             
                             elements_request.add(payloadRequest);
                             
                             
                             
                             } //END "FOR" LOOP: OPTIONS
                             
                            // out.println("number of objects in the array:"+elements_request.size());
                            // out.println(" 1  -----"+elements_request.get(0));
                            // out.println(elements_request.get(0).elements.get(0)[0]);
                            
                             //out.println(" 2 -----"+elements_request.get(1));
                             //out.println(elements_request.get(1).elements.get(0)[0]);
                             //printElements(elements_request.get(1).getElements());
                             
                             
                          }//no null payload END ELSE      
                     }//end request nodes
                           
                         
        } //close else  REQUEST=TRUE 
                         
              //RESPONSE    
                NodeList  lRR=eMethod.getElementsByTagName("response");
                            
                         if(lRR.getLength()==0){
                           response=false;
                         } else{
                             response=true;
                      
                     Node nRR = lRR.item(0);
                     if(nRR.getNodeType() == Node.ELEMENT_NODE){
                            Element eRR= (Element) nRR;
                                
                              

//Format comparision
//Encoding
                            Node nEncode1 =eRR.getElementsByTagName("encode").item(0);
                            Element eEncode1= (Element) nEncode1;
                            
                            mediatype_response=eEncode1.getAttribute("name");
  
                            // Payload
                           
                             
                         NodeList  lRpayload=eRR.getElementsByTagName("payload");
                            
                         if(lRpayload.getLength()==0){
                              out.println("No payload Request defined");
                               
                           }else{  
                             
                               Node npayload2 = lRpayload.item(0);
                             
                             Element epayload2=(Element) npayload2;
                             
                       //Payload with options
                             NodeList options2=epayload2.getElementsByTagName("option");
                             
                             //out.println("options found, number:"+options2.getLength());
                             
                             for(int l=0; l<options2.getLength();l++){
                                 boolean complex=false;
                                // out.println("option:"+l);
                                ArrayList<String[]> arrayPayloadR = new ArrayList<>(); 
                                 
                                 Node noption2=options2.item(l);
                                 Element eoption2= (Element)noption2;
                                 String subpath=eoption2.getAttribute("value");
                                 
                                 if(!subpaths.contains(subpath)){
                                 subpaths.add(subpath);
                                 }
                                 
                                NodeList complex2=eoption2.getElementsByTagName("complextype");
                             //if(!complex1.equals(null)){
                                  Node ncomplex2=complex2.item(0); 
                                  Element ecomplex2= (Element)ncomplex2;
                              complexType_response=ecomplex2.getAttribute("type");   
                            // } 
                                
                             Node childNode=eoption2.getFirstChild();
                                 
                            
                               
                             while(childNode.getNextSibling()!=null){     
                                  
                                    childNode = childNode.getNextSibling(); 
                                    if (childNode.getNodeType() == Node.ELEMENT_NODE) {  
                                       
                                   Element e =(Element) childNode;
                                   String[] ele;
                                   String [] metadata;
                                   String tagname= e.getTagName();
                                   //out.println(tagname);
                                    if("complexelement".equals(tagname)){
    
                                        arrayPayloadR=complexelFunction("RESPONSE",e,"Newclass");
                                       
         
                                 
                                   }else{
                                   ele=new String[2];
                                   ele[0]=e.getAttribute("name");
                                   ele[1]=e.getAttribute("type");
                                     
                                     metadata=new String[4];
                                    metadata[2]= e.getAttribute("variation");
                                    metadata[3]= e.getAttribute("unit");
                                    metadata[0]=ele[0];
                                    metadata[1]=ele[1];
                                   if(!ele[1].equals("null")){
                                       arrayPayloadR.add(ele);
                                       metadata_response.add(metadata); 
                                   }
                                   
                                   }
                                   
                               }
                              
                             } //END WHILE NODES In the payload
                             
                             
                                ElementsPayload  payloadResponse =new ElementsPayload(arrayPayloadR,metadata_response);
                             
                             elements_response.add(payloadResponse);
                             
                             
                             } //END "FOR" LOOP: OPTIONS
                             
                             //out.println("number of objects in the array:"+elements_response.size());
                             //out.println(" 1  -----"+elements_response.get(0));
                             //out.println(elements_response.get(0).elements.get(0)[0]);
                            
                             //out.println(" 2 -----"+elements_response.get(1));
                             //out.println(elements_response.get(1).elements.get(0)[0]);
                              
                             
                             
                           }// Payload no null END ELSE
                        }//close response elements
                     
                         
                     }//close else RESPONSE=TRUE
          
                     
                           
               
           }//There are paramters
                     
        }//METHOD      
                            
                            
                  }
       
           
                   
       }else{ out.println( "ERROR: Service interface not found");
       throw new GenerationException(" SERVICE INTERFACE NOT FOUND ");
       }
      
       
       
    } catch (Exception e) {
	e.printStackTrace();
    }
                   
    if(!response && request){
        mediatype_response=mediatype_request;
    }
    if(response && !request){
        mediatype_request=mediatype_response;
    }
    
 //out.println("elemtents: ");
 //printElements(elements_request.get(0).getElements());
  //CodgenUtil.readList(metadata_request); 
 InterfaceMetadata MD = new InterfaceMetadata(protocol,path,method,mediatype_request, mediatype_response,ID,complexType_request,complexType_response,elements_request, elements_response, request,response,param,parameters,subpaths);  
    return MD;
    
}

public ArrayList<String[]> complexelFunction ( String r, Element e, String level){
    ArrayList<String[]> complexPayload =new ArrayList<>();
 String [] c=new String[3];
  c[0]=level;
 String classname=e.getAttribute("name");
  c[1]=classname;
  c[2]=e.getAttribute("type");
     if (r.equalsIgnoreCase("REQUEST")){
         payload_request.add(c);
         metadata_request.add(c);
     }
     else{
         payload_response.add(c);
         metadata_response.add(c);
     }
     complexPayload.add(c);
     Node elechild=e.getFirstChild();

while(elechild.getNextSibling()!=null){ 
         elechild=elechild.getNextSibling();
         if (elechild.getNodeType() == Node.ELEMENT_NODE) { 
              Element e2 =(Element) elechild;
               String tagname= e2.getTagName();
               String ename=e2.getAttribute("name");
                 if("complexelement".equals(tagname)){
                      complexelFunction (r,e2,"child:Newclass");
                        elechild=elechild.getNextSibling();
                                              //if(elechild!=null)
                                             //while(elechild.getNodeType() != Node.ELEMENT_NODE) { 
                                               //elechild=elechild.getNextSibling();
                                             //}
                      }else{
                          String [] f=new String[3];
                           f[0]="child";
                           f[1]=e2.getAttribute("name");
                           f[2]=e2.getAttribute("type");
                          // out.println(f[0]+f[1]+f[2]);
                            String [] metadata=new String[4];
                                    metadata[2]= e2.getAttribute("variation");
                                    metadata[3]= e2.getAttribute("unit");
                                    metadata[0]=f[1];
                                    metadata[1]=f[2];         
                           if (r.equalsIgnoreCase("REQUEST")){
                               payload_request.add(f);
                               metadata_request.add(metadata);
                           }else {
                               payload_response.add(f);
                                metadata_response.add(metadata);
                           }
                             
                           complexPayload.add(f);
 
                                          }
                                                                                    
                                         }  
                                     
                                     }      
                                         //elements_response.add(c);  
                                       
                                     String [] StopClass=new String[2];
                                     StopClass[0]="StopClass"; 
                                     
                                      if (r.equalsIgnoreCase("REQUEST")){
                                        payload_request.add(StopClass);
                                     metadata_request.add(StopClass);   
                                      }else{
                                          payload_response.add(StopClass);
                                          metadata_response.add(StopClass);
                                      }
                                     complexPayload.add(StopClass);
                                     
           //if(r.equalsIgnoreCase("REQUEST")) return payload_request;
          // else return payload_response;
          return complexPayload;
}



    public static void printElements(ArrayList<String[]> elements){
        for(int i=0; i>elements.size();i++){
            String[] list=elements.get(i);
            for(int j=0; j>list.length;j++){
                out.println("   :"+list[j]);
            }
            
        }
    }

public void Reset(){
   elements_request.clear(); 
   elements_response.clear();
   payload_request.clear();
   payload_response.clear();
   parameters.clear();
   subpaths.clear();
 }



    }



