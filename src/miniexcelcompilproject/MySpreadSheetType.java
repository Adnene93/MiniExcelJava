/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniexcelcompilproject;

import evaluator.Cell;
import evaluator.Evaluator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetCellType;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

/**
 *
 * @author Hatem
 */
public class MySpreadSheetType extends SpreadsheetCellType<Object> {

    FXMLDocumentController controller;
    private Cell cell;
    boolean doChange;
    /*SpreadsheetCell createCell(){
     new SpreadsheetCellBase(row, column, rowSpan, columnSpan)
     } */

    public MySpreadSheetType(FXMLDocumentController controller) {
        super();
        this.controller = controller;
        cell = null;

    }

    @Override
    public SpreadsheetCellEditor createEditor(SpreadsheetView sv) {
        return new MyStringEditor(sv, this);
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String toString(Object t) {
        return t.toString();

    }

    @Override
    public boolean match(Object value) {

        /*if (value instanceof Double) {
         return true;
         } else {
         try {
         Double convertedValue = (Double) converter.fromString(value == null ? null : value.toString());
         if (convertedValue >= 0 && convertedValue <= 100) {
         return true;
         } else {
         return false;
         }
         } catch (Exception e) {
         return false;
         }
         }*/
        return true;
    }

    @Override
    public Object convertValue(Object o) {
        String ret;

        if (doChange) {

            FXMLDocumentController.undoredoController.clearActualIndex();
            FXMLDocumentController.undoredoController.add(new Cell(cell));
            if (cell.isExpanded()) {
                HashMap<String, Cell> area = cell.cellArea();
                for (Cell areaCell : area.values()) {
                    FXMLDocumentController.undoredoController.add(new Cell(areaCell));
                }
            }
            TreeMap<String, Cell> ftl = cell.fullTreeOfListners();
            
            
            for (Map.Entry<String, Cell> entry : ftl.entrySet()) {
                String string = entry.getKey();
                Cell listener = entry.getValue();
                FXMLDocumentController.undoredoController.add(new Cell(listener));
                if (listener.isExpanded()) {
                    HashMap<String, Cell> area = listener.cellArea();
                    for (Cell areaCell : area.values()) {
                        FXMLDocumentController.undoredoController.add(new Cell(areaCell));
                    }
                }

            }

            doChange = false;
            if (o != null) {
                ret = o.toString();

                if (cell != null) {
                    if (controller.evaluator.getSelectedCell() == cell) {
                        ret = cell.evaluate(ret).toString();

                    } else {
                        ret = cell.toString();
                    }
                } else {
                    ret = "";
                }

            } else {
                ret = "";
            }
            controller.updateSpreadSheetView();

            //System.out.println(ret);
        } else {
            ret = "";
            if (o != null) {
                ret = o.toString();
            }
        }

        return ret;
    }

    public Cell getCell() {
        return cell;
    }

    public void setCell(Cell cell) {
        this.cell = cell;
    }

    public boolean isDoChange() {
        return doChange;
    }

    public void setDoChange(boolean changeVal) {
        this.doChange = changeVal;
       
    }

}
