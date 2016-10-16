/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Hatem
 */
public class FunctionAsAttribute  implements Serializable{

    Symbol functionSymbol;
    Function functionIdentifier;
    ArrayList<CombinedReturnObject> listOfCombined;
    

    public FunctionAsAttribute(Symbol functionSymbol, Function functionIdentifier) {
        this.functionSymbol = functionSymbol;
        this.functionIdentifier = functionIdentifier;
        listOfCombined = new ArrayList<CombinedReturnObject>();
       
    }
    
    

    public boolean numberOfArgsStillRespected() {
        return (((this.functionIdentifier.maximalNumberofArguments == -1)
                || (listOfCombined.size() <= this.functionIdentifier.maximalNumberofArguments)));
    }

    public boolean numberOfArgsRespected() {
        return (((this.functionIdentifier.maximalNumberofArguments == -1)
                || (listOfCombined.size() <= this.functionIdentifier.maximalNumberofArguments))) && (listOfCombined.size() >= this.functionIdentifier.minimalNumberofArguments);
    }

    public Symbol getFunctionSymbol() {
        return functionSymbol;
    }
    
    public boolean allArgumentsAreInteger(){
        boolean ret = true;
        for (int i=0;i<listOfCombined.size();i++){
            if (!listOfCombined.get(i).isInteger()) return false;
        }
        return true;
    }
    
    public boolean allArgumentsAreRealPostif(){
        boolean ret = true;
        for (int i=0;i<listOfCombined.size();i++){
            if (!listOfCombined.get(i).isRealPositif()) return false;
        }
        return true;
    }
    
    

   
    
    
    
    
}
