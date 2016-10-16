/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package miniexcelcompilproject;

import javafx.scene.control.TextField;
import org.controlsfx.control.spreadsheet.SpreadsheetCellEditor;
import org.controlsfx.control.spreadsheet.SpreadsheetView;

/**
 *
 * @author Hatem
 */
public class MyStringEditor extends SpreadsheetCellEditor.StringEditor{

    MySpreadSheetType mySpreadSheetType;
    
    public MyStringEditor(SpreadsheetView view,MySpreadSheetType mySpreadSheetType) {
        super(view);
        this.mySpreadSheetType=mySpreadSheetType;
        
        
        
    }

    @Override
    public TextField getEditor() {
       
       TextField tf = super.getEditor(); //To change body of generated methods, choose Tools | Templates.
       tf.setText(FXMLDocumentController.evaluator.getSelectedCell().getExpression());
       tf.textProperty().bindBidirectional(FXMLDocumentController.controller.EditTextFormula.textProperty());
       
       return tf;
    }

    @Override
    public String getControlValue() {
        mySpreadSheetType.setDoChange(true);
        return super.getControlValue(); //To change body of generated methods, choose Tools | Templates.
    }
    
    

    

    
    
    
    
    

    
    
    

    
    
    
    
    
    
}
