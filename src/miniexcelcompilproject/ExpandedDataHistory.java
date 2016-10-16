/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package miniexcelcompilproject;

import evaluator.Cell;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author Hatem
 */
public class ExpandedDataHistory {
    private HashMap<String,TimeStep> crushedData;

    public ExpandedDataHistory() {
        crushedData = new HashMap<String,TimeStep>();
    }
    
    public void put(String cellName,Cell cell){
        if (crushedData.get(cellName)==null) crushedData.put(cellName, new TimeStep());
        crushedData.get(cellName).add(cell);
    } 
    
    public HashMap<String,Cell> getExpandedDataHistory(String cellName){
        if (crushedData.get(cellName)!=null) return crushedData.get(cellName).getCellChanged();
        else return new HashMap<String,Cell>();
    }
    
    
    
    
    
    
    
    
    
}
