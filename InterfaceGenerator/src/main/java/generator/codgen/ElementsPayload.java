/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package generator.codgen;

import java.util.ArrayList;

/**
 *
 * @author cripan
 */
public class ElementsPayload {
     ArrayList<String[]> elements;
     ArrayList<String[]> metadata;
    public ElementsPayload() {
    }

    public ElementsPayload(ArrayList<String[]> elements, ArrayList<String[]> metadata) {
        this.elements = elements;
        this.metadata = metadata;
    }

    public ArrayList<String[]> getMetadata() {
        return metadata;
    }

    public void setMetadata(ArrayList<String[]> metadata) {
        this.metadata = metadata;
    }

   public void printMetadata(){
        for(int i=0; i>metadata.size();i++){
            String[] list=metadata.get(i);
            for(int j=0; j>list.length;j++){
                System.out.println(list[j]);
            }
            
        }
    }
     
     
   

    public ArrayList<String[]> getElements() {
        return elements;
    }

    public void setElements(ArrayList<String[]> elements) {
        this.elements = elements;
    }
    
    
    
    
    public void printElements(){
        for(int i=0; i>elements.size();i++){
            String[] list=elements.get(i);
            for(int j=0; j>list.length;j++){
                System.out.println(list[j]);
            }
            
        }
    }
     
    
     
     
     
     
}
