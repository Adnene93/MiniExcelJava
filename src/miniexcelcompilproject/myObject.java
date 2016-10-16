/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package miniexcelcompilproject;

import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.property.StringPropertyBase;

/**
 *
 * @author Hatem
 */
public class myObject  {
    StringProperty message;
    

    public myObject(String message) {
        
        this.message=new SimpleStringProperty(message);
        //this.message.set(message);
    }

    
    
    public StringProperty getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message.set(message);
    }

    @Override
    public String toString() {
        return this.message.getValue(); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
      
}
