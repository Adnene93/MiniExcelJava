/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniexcelcompilproject;

import evaluator.Cell;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Hatem
 */
public class UndoRedoController {

    final int UNDOREDOSIZE = 100;
    ArrayList<TimeStep> changeList;
    SimpleIntegerProperty index;

    public UndoRedoController() {
        changeList = new ArrayList<>();
        index = new SimpleIntegerProperty(0);
        changeList.add(new TimeStep());
    }

    void add(Cell cell) {
        changeList.get(index.get()).add(cell);
    }

    void set(HashMap<String, Cell> tree) {
        changeList.get(index.get()).getCellChanged().clear();
        for (Map.Entry<String, Cell> entry : tree.entrySet()) {
            String string = entry.getKey();
            Cell cell = entry.getValue();
            changeList.get(index.get()).add(cell);
        }
    }

    void nextStep() {
        boolean erased=false;
        index.set(index.get() + 1);
        /*for (int i = index.get(); i < changeList.size(); i++) {
            changeList.remove(i);
            erased=true;
        }*/
        int i=index.get();
        while (i!=changeList.size()) changeList.remove(i);
        //if (i!=changeList.size()) index.set(changeList.size());
        changeList.add(new TimeStep());
        resize();
    }

    void next() {
        index.set(index.get() + 1);
    }

    void oldStep() {
        index.set(index.get() - 1);
    }

    HashMap<String, Cell> undo() {
        int i = index.get() - 1;
        return changeList.get(i).getCellChanged();
    }

    HashMap<String, Cell> redo() {
        int i = index.get();
        return changeList.get(i).getCellChanged();
    }

    boolean indexRespectdBoundsForUndo() {
        boolean ret = true;
        if (((index.get() - 1) < 0)) {
            ret = false;
        }
        return ret;
    }

    boolean indexRespectdBoundsForRedo() {
        boolean ret = true;
        if (((index.get() + 1) >= changeList.size())) {
            ret = false;
        }
        return ret;
    }
    
    void clearActualIndex(){
        this.changeList.get(index.get()).getCellChanged().clear();
    }

    
    public void show() {
        for (int i=0;i<changeList.size();i++){
            System.out.println(changeList.get(i).getCellChanged());
        }
        System.out.println("Index = "+index.intValue());
    }
    
    void resize(){
        if (index.get()>UNDOREDOSIZE) {
            index.set(index.get()-1);
            changeList.remove(0);
        }
    }
}
