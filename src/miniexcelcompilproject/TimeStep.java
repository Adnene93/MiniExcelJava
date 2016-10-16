/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package miniexcelcompilproject;

import evaluator.Cell;
import java.util.HashMap;

/**
 *
 * @author Hatem
 */
public class TimeStep {
    private HashMap<String, Cell> cellChanged;

    public TimeStep() {
        cellChanged=new HashMap<>();
    }
    
    void add(Cell cell){
        cellChanged.put(cell.getName(), cell);
    }

    public HashMap<String, Cell> getCellChanged() {
        return cellChanged;
    }
    
    
    
    
}
