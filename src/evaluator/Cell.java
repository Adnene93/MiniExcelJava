/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import evaluator.CombinedReturnObject.Type;
import java.io.Serializable;
import java.text.ChoiceFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyValue;
import miniexcelcompilproject.FXMLDocumentController;

/**
 *
 * @author Hatem
 */
public class Cell implements Serializable {

    
    
    private String name;
    private String expression;
    private CombinedReturnObject value;
    private Evaluator evaluator;
    private boolean empty;
    private boolean containRecursiveReference;
    private TreeMap<String, Cell> myListners;
    private TreeMap<String, Cell> informers;
    private String printedValue = "";
    private boolean isExpression;
    private boolean isExpanded;
    private int expandedRowsNumber = 0;
    private int expandedColNumber = 0;
    private EvaluatorException error;
    private EvaluatorException errorFromReferencement;
    private boolean isSpecialValueCell; //actually for si for letting her display his value even if it's not a 
    private boolean isPrintableValue;
    private boolean updated;
    private boolean containRandomFunction;
    private String color;

    //private boolean earlyChange;
    public Cell(Cell cell) {
        this.name = cell.name;
        this.expression = cell.expression;
        this.value = cell.value;
        this.evaluator = cell.evaluator;
        this.empty = cell.empty;
        this.containRecursiveReference = cell.containRecursiveReference;
        this.myListners = (TreeMap<String, Cell>) cell.myListners.clone();
        this.printedValue = cell.printedValue;
        this.isExpression = cell.isExpression;
        this.isExpanded = cell.isExpanded;
        this.expandedColNumber = cell.expandedColNumber;
        this.expandedRowsNumber = cell.expandedRowsNumber;
        this.error = cell.error;
        this.errorFromReferencement = cell.errorFromReferencement;
        this.isSpecialValueCell = cell.isSpecialValueCell;
        this.informers = (TreeMap<String, Cell>) cell.informers.clone();
        this.updated = cell.updated;
        this.isPrintableValue = cell.isPrintableValue;
        this.containRandomFunction = cell.containRandomFunction;
        this.color=cell.color;

    }

    public void set(Cell cell) {
        this.name = cell.name;
        this.expression = cell.expression;
        this.value = cell.value;
        this.evaluator = cell.evaluator;
        this.empty = cell.empty;
        this.containRecursiveReference = cell.containRecursiveReference;
        this.myListners = (TreeMap<String, Cell>) cell.myListners.clone();
        this.printedValue = cell.printedValue;
        this.isExpression = cell.isExpression;
        this.isExpanded = cell.isExpanded;
        this.expandedColNumber = cell.expandedColNumber;
        this.expandedRowsNumber = cell.expandedRowsNumber;
        this.error = cell.error;
        this.errorFromReferencement = cell.errorFromReferencement;
        this.isSpecialValueCell = cell.isSpecialValueCell;
        this.informers = cell.informers;
        this.isPrintableValue = cell.isPrintableValue;
        this.updated = cell.updated;
        this.containRandomFunction = cell.containRandomFunction;
        this.color=cell.color;
    }

    public Cell(String name, String expression, Evaluator evaluator) {
        isExpanded = false;
        this.name = name;
        this.expression = expression;
        this.evaluator = evaluator;
        this.value = this.evaluator.evaluate(expression);
        empty = false;
        myListners = new TreeMap<String, Cell>();
        informers = new TreeMap<String, Cell>();
        containRecursiveReference = false;
        error = null;
        errorFromReferencement = null;
        isExpression = false;
        isSpecialValueCell = false;
        isPrintableValue = true;
        this.updated = true;
        this.containRandomFunction = false;
        this.color="";

    }

    public Cell(String name, Evaluator evaluator) {
        isExpanded = false;
        this.name = name;
        this.expression = "";
        this.evaluator = evaluator;
        this.value = new CombinedReturnObject(this.expression);
        empty = true;
        myListners = new TreeMap<String, Cell>();
        informers = new TreeMap<String, Cell>();
        containRecursiveReference = false;
        error = null;
        this.errorFromReferencement = null;
        isExpression = false;
        isSpecialValueCell = false;
        this.updated = true;
        isPrintableValue = true;
        this.containRandomFunction = false;
        this.color="";
    }

    public String getExpression() {
        return expression;
    }

    public String getName() {
        return name;
    }

    public void removeThisAsListner() {
        for (Map.Entry<String, Cell> entry : evaluator.getTableOfCells().entrySet()) {
            Cell cell = entry.getValue();
            cell.removeListner(this);
        }
    }

    public Cell reevaluate(){
       this.evaluate(expression);
       return this;
    }
    
    public Cell evaluate(String expression) {
        this.containRandomFunction = false;
        this.evaluator.setActualEvaluatingCell(this);
        isSpecialValueCell = false;
        isPrintableValue = true;
        this.expression = expression;
        for (Map.Entry<String, Cell> entry : evaluator.getTableOfCells().entrySet()) {
            Cell cell = entry.getValue();
            cell.removeListner(this);
        }

        this.informers = new TreeMap<String, Cell>();

        this.setContainRecursiveReference(false);
        this.value = evaluator.evaluate(expression);
        //System.out.println(this.name+" : "+this.isErrorInExpression());
        if (expression.replaceAll(LexicalAnalyser.SEPARATOR, "").length() != 0) {
            empty = false;
        } else {
            empty = true;
        }
        this.evaluator.setActualEvaluatingCell(null);

        if (containRecursiveReference()) {
            /*this.error = new RecursiveReferencesException();*/
            this.errorFromReferencement = new RecursiveReferencesException();
            this.value.setErrorInExpression(true);
        } else if (!allInformersAreNotEmpty()) {
            {

                this.errorFromReferencement = new EmptyReferencedCellException();
                this.value.setErrorInExpression(true);
            }
        } else {
            this.errorFromReferencement = null;
        }
        informMyListners();
        if (isPrintableValue()) {
            if (!allInformersAreValue()) {
                value.setIsValue(false);
            }

        }
        majPrintableValue();
        majShownValue();
        if (isExpanded && !isErrorInExpression() && isPrintableValue()) {
            if (isMatrix()) {
                compressMatrix();
                expandMatrix();
            } else {
                compressMatrix();
            }

        } else if (isExpanded) {
            //if (isMatrix()) {
                compressMatrix();
            //}
        }

        return this;
    }

    public CombinedReturnObject getValue() {
        return value;
    }

    @Override
    public String toString() {
        return printedValue.toString();
    }

    public boolean isEmpty() {
        return empty;
    }

    public void addListner(Cell mListner) {
        this.myListners.put(mListner.name, mListner);
    }

    public void addInformer(Cell mListner) {
        this.informers.put(mListner.name, mListner);
    }

    public void removeListner(Cell mListner) {
        this.myListners.remove(mListner.name);
    }

    public void removeInformer(Cell mListner) {
        this.informers.put(mListner.name, mListner);
    }

    Cell getNonUpdatedListner() {
        //System.out.println("HALAO");
        TreeMap<String, Cell> tl = myListners;
        boolean found = false;
        Cell cellreturned = null;
        for (Map.Entry<String, Cell> entry : tl.entrySet()) {
            String string = entry.getKey();
            Cell cell = entry.getValue();
            if (!cell.isUpdated() && cell.allInformersAreUpdated()) {
                found = true;
                cellreturned = cell;
            }
        }
        if (!found) {

            for (Map.Entry<String, Cell> entry : tl.entrySet()) {
                String string = entry.getKey();
                Cell cell = entry.getValue();
                if (!cell.isUpdated()) {
                    cellreturned = cell;

                }
            }
        }
        return cellreturned;

    }

    public void informMyListners() /*throws EvaluatorException*/ {
        TreeMap<String, Cell> ftl = fullTreeOfListners();
        //added
        this.updated = true;
        for (Map.Entry<String, Cell> entry : ftl.entrySet()) {
            String string = entry.getKey();
            Cell cell = entry.getValue();
            cell.updated = false;

        }
        //added
        if (ftl.containsKey(this.name)) {
            setContainRecursiveReference(true);
            if (containRecursiveReference()) {
                /*this.error = new RecursiveReferencesException();*/
                this.errorFromReferencement = new RecursiveReferencesException();
                this.value.setErrorInExpression(true);
            }
            //throw new RecursiveReferencesException();
        } else {
            setContainRecursiveReference(false);
        }
        TreeMap<String, Cell> arg = new TreeMap<>();
        informMyListners(arg);

    }

    private void informMyListners(TreeMap<String, Cell> callers) /*throws EvaluatorException*/ {

        callers.put(name, this);
        /*
         for (Map.Entry<String, Cell> entry : myListners.entrySet()) {
         String name = entry.getKey();
         Cell cell = entry.getValue();
         if (!callers.containsKey(name)) {
         cell.execute(callers);
         }
         }*/
        /*TreeMap<String, Cell> tree = new TreeMap<>();
         for (Map.Entry<String, Cell> entry : myListners.entrySet()) {

         String name = entry.getKey();
         Cell cell = entry.getValue();
         if (!callers.containsKey(name)) {
         if (cell.allInformersAreUpdated()) {
         cell.execute(callers);
         } else {
         tree.put(name, cell);
         }
         }
         }

         for (Map.Entry<String, Cell> entry : tree.entrySet()) {
         String name = entry.getKey();
         Cell cell = entry.getValue();
         if (!callers.containsKey(name)) {
         cell.execute(callers);

         }
         }*/
        Cell cell = null;
        while ((cell = getNonUpdatedListner()) != null) {
            if (!callers.containsKey(cell.getName())) {
                cell.execute(callers);
            } else {
                cell.setUpdated(true);
            }
        }
    }

    public void execute(TreeMap<String, Cell> arg) /*throws EvaluatorException*/ {
        this.evaluator.setActualEvaluatingCell(this);
        this.value = evaluator.evaluate(expression);
        this.evaluator.setActualEvaluatingCell(null);
        /*if (this.isExpanded) {
         this.expandMatrix();
         }*/
        if (this.fullTreeOfListners().containsKey(this.name)) {

            setContainRecursiveReference(true);
            if (containRecursiveReference()) {

                this.errorFromReferencement = new RecursiveReferencesException();
                this.value.setErrorInExpression(true);
            } else {

            }
        } else {
            setContainRecursiveReference(false);

            if (this.errorFromReferencement instanceof RecursiveReferencesException) {

                /*this.error = null;*/
                this.errorFromReferencement = null;
                if (this.error == null) {
                    if (this.value.isErrorInExpression()) {

                        this.errorFromReferencement = new ReferencedCellException();
                    } else {

                        this.value.setErrorInExpression(false);
                    }
                }
            }
        }
        if (!containRecursiveReference()) {

            if (!allInformersAreNotEmpty()) {

                this.errorFromReferencement = new EmptyReferencedCellException();
                this.value.setErrorInExpression(true);

            } else {

                if (this.errorFromReferencement instanceof EmptyReferencedCellException) {
                    this.errorFromReferencement = null;
                    if (this.error == null) {
                        if (this.value.isErrorInExpression()) {
                            this.errorFromReferencement = new ReferencedCellException();
                        } else {
                            this.value.setErrorInExpression(false);
                        }
                    }
                }
            }
        }


        /*else {
         if (this.errorFromReferencement instanceof EmptyReferencedCellException) {
         System.out.println("Step 4");
         this.errorFromReferencement = null;
         if (this.error == null) {
         this.value.setErrorInExpression(false);
         }
         }
         }*/
        if (this.error != null) {
            this.value.setErrorInExpression(true);
        }
        if (isExpanded && !isErrorInExpression()) {
            if (isMatrix()) {
                compressMatrix();
                expandMatrix();
            } else {
                compressMatrix();
            }

        } else if (isExpanded) {
            /*if (isMatrix()) {*/
                compressMatrix();
            //}
        }

        this.updated = true;
        informMyListners(arg);
        majPrintableValue();
        majShownValue();

    }

    public TreeMap<String, Cell> myListners() {
        return this.myListners;
    }

    public TreeMap<String, Cell> fullTreeOfListners() {

        TreeMap<String, Cell> arg = new TreeMap<>();
        TreeMap<String, Cell> ret = fullTreeOfListners(arg); //arg contain all the callers
        return ret;
    }

    /*public TreeMap<String, Cell> fullTreeOfListners(Cell arg) {
     TreeMap<String, Cell> ret = new TreeMap<String, Cell>(myListners);
     for (Map.Entry<String, Cell> entry : ret.entrySet()) {
     String string = entry.getKey();
     Cell cell = entry.getValue();
     if (!ret.containsValue(arg)) ret.putAll(cell.fullTreeOfListners(arg));
     }
     return ret;
     }*/
    private TreeMap<String, Cell> fullTreeOfListners(TreeMap<String, Cell> callers) {
        TreeMap<String, Cell> ret = new TreeMap<String, Cell>(myListners);
        callers.put(name, this);
        for (Map.Entry<String, Cell> entry : myListners.entrySet()) {

            String string = entry.getKey();
            Cell cell = entry.getValue();
            if (!callers.containsKey(string)) {
                ret.putAll(cell.fullTreeOfListners(callers));
            } else {

            }

        }
        return ret;
    }

    public TreeMap<String, Cell> myInformers() {
        return this.informers;
    }

    public TreeMap<String, Cell> fullTreeOfInformers() {

        TreeMap<String, Cell> arg = new TreeMap<>();
        TreeMap<String, Cell> ret = fullTreeOfInformers(arg); //arg contain all the callers
        return ret;
    }

    private TreeMap<String, Cell> fullTreeOfInformers(TreeMap<String, Cell> callers) {
        TreeMap<String, Cell> ret = new TreeMap<String, Cell>(informers);
        callers.put(name, this);
        for (Map.Entry<String, Cell> entry : informers.entrySet()) {

            String string = entry.getKey();
            Cell cell = entry.getValue();
            if (!callers.containsKey(string)) {
                ret.putAll(cell.fullTreeOfInformers(callers));
            } else {

            }
        }
        return ret;
    }

    boolean allInformersAreNotEmpty() {
        boolean returned = true;
        TreeMap<String, Cell> ret = fullTreeOfInformers();
        for (Map.Entry<String, Cell> entry : ret.entrySet()) {
            String string = entry.getKey();
            Cell cell = entry.getValue();
            if (cell.isEmpty()) {
                return false;
            }

        }
        return returned;
    }

    boolean allInformersAreUpdated() {
        boolean returned = true;
        TreeMap<String, Cell> ret = fullTreeOfInformers();
        for (Map.Entry<String, Cell> entry : ret.entrySet()) {
            String string = entry.getKey();
            Cell cell = entry.getValue();
            if (!cell.isUpdated()) {
                return false;
            }

        }
        return returned;
    }

    boolean allInformersAreValue() {
        boolean returned = true;
        TreeMap<String, Cell> ret = fullTreeOfInformers();
        for (Map.Entry<String, Cell> entry : ret.entrySet()) {
            String string = entry.getKey();
            Cell cell = entry.getValue();
            if (!cell.isValue() && !cell.isSpecialValueCell()) {
                return false;
            }

        }
        return returned;
    }

    boolean allInformersAreSafeFromError() {
        boolean returned = true;
        TreeMap<String, Cell> ret = fullTreeOfInformers();
        for (Map.Entry<String, Cell> entry : ret.entrySet()) {
            String string = entry.getKey();
            Cell cell = entry.getValue();
            if (cell.isErrorInExpression()) {
                return false;
            }

        }
        return returned;
    }

    public boolean containRecursiveReference() {
        return containRecursiveReference;
    }

    public void setContainRecursiveReference(boolean containRecursiveReference) {
        this.containRecursiveReference = containRecursiveReference;

        /*if (this.containRecursiveReference){
         //this.value.setErrorInExpression(true);
         this.error = new RecursiveReferencesException();
         }*/
    }

    public boolean isValue() {
        return value.isValue();
    }

    public Matrix getMatrix() {
        return this.value.getMatrix();
    }

    public boolean isMatrix() {
        return this.value.isMatrix();
    }

    void majShownValue() {
        
        if (!isEmpty()) {
            if (isExpression()) {
                if (isErrorInExpression()) {

                    if (errorFromReferencement != null) {
                        printedValue = errorFromReferencement.getType().toString();
                    } else {
                        if (error != null) {
                            printedValue = error.getType().toString();
                            /*if (!(error instanceof IsNotExpressionException)) {
                             setErrorInExpression(true);
                             }*/
                        } else if (isErrorInExpression()) {
                            errorFromReferencement = new ReferencedCellException();
                            printedValue = errorFromReferencement.getType().toString();
                        }

                    }
                    /*if (error != null) {
                     printedValue = error.getType().toString();
                     } else {
                     error = new ReferencedCellException();
                     printedValue = error.getType().toString();
                     }*/

                } else {

                    error = null;
                    errorFromReferencement = null;
                    /*if (isValue()) {
                     printedValue = this.value.stringValue();

                     } else {
                     if (isSpecialValueCell()) {
                     printedValue = this.value.stringValue();
                     } else {
                     //printedValue = "#VALUE!";
                     this.value.setStringValue("#VALUE!");
                     printedValue = this.value.stringValue();
                     }
                     }*/
                    if (isPrintableValue()) {
                        if (value.isDouble()) {
                            printedValue = evaluator.formatter.format(this.value.doubleValue());
                        } else {
                            printedValue = this.value.stringValue();
                            isPrintableValue=false;
                        }

                    } else {
                        printedValue = "#VALUE!";
                    }
                }

            } else {
                if (value.isDouble()) {
                    printedValue = evaluator.formatter.format(this.value.doubleValue());
                } else {
                    
                    printedValue = this.value.stringValue();
                }
            }

        } else {
            printedValue = "";
        }

    }

    public boolean isExpression() {
        return isExpression;
    }

    public void setIsExpression(boolean isExpression) {
        this.isExpression = isExpression;
    }

    public boolean isErrorInExpression() {
        return value.isErrorInExpression();
    }

    public boolean isErroned() {
        return ((error != null && !(error instanceof IsNotExpressionException)) || errorFromReferencement != null);
    }

    public void setErrorInExpression(boolean errorInExpression) {
        this.value.setErrorInExpression(errorInExpression);
    }

    public String getErrorMessage() {

        if (isEmpty()) {
            return "Empty Cell";
        } else if (errorFromReferencement != null) {
            return errorFromReferencement.getMessage();
        } else if (error != null) {
            return error.getMessage();
        } else {
            return "No Error";
        }
    }

    public void setError(EvaluatorException error) {
        this.error = error;
    }

    public boolean isSpecialValueCell() {
        return isSpecialValueCell;
    }

    public void setIsSpecialValueCell(boolean isSpecialValueCell) {
        this.isSpecialValueCell = isSpecialValueCell;
    }

    public HashMap<String, Cell> expandMatrix() {
        HashMap<String, Cell> ret = new HashMap<String, Cell>();
        isExpanded = true;
        String startCellName = this.name;
        ArrayList<Integer> myIndex = this.evaluator.getVariableIndexFromName(name);
        int i1 = myIndex.get(0);
        int j1 = myIndex.get(1);
        int i2 = i1 + this.value.getMatrix().rowsNumber - 1;
        int j2 = j1 + this.value.getMatrix().columnsNumber - 1;
        expandedRowsNumber = this.value.getMatrix().rowsNumber;
        expandedColNumber = this.value.getMatrix().columnsNumber;
        String endCellName = evaluator.getCellName(i2, j2);
        String cellName;
        int i = 0, j = 0;
        ArrayList<String> familyName = evaluator.variableNameFamily(startCellName, endCellName);
        for (int index = 1; index < familyName.size(); index++) {
            cellName = familyName.get(index);

            i = index % this.getMatrix().rowsNumber;
            j = index / this.getMatrix().rowsNumber;
            Cell cell = evaluator.getVariable(cellName);
            ret.put(cell.name, new Cell(cell));
            //cell.evaluate(String.valueOf(this.getMatrix().get(i, j)));
            cell.evaluate(this.getMatrix().getString(i, j));
            cell.removeThisAsListner();
            cell.informers = new TreeMap<>();
        }
        return ret;
    }

    public HashMap<String, Cell> compressMatrix() {
        HashMap<String, Cell> ret = new HashMap<String, Cell>();
        isExpanded = false;
        String startCellName = this.name;
        ArrayList<Integer> myIndex = this.evaluator.getVariableIndexFromName(name);
        int i1 = myIndex.get(0);
        int j1 = myIndex.get(1);
        int i2 = i1 + expandedRowsNumber - 1;
        int j2 = j1 + expandedColNumber - 1;
        String endCellName = evaluator.getCellName(i2, j2);
        String cellName;
        int i = 0, j = 0;
        ArrayList<String> familyName = evaluator.variableNameFamily(startCellName, endCellName);
        HashMap<String, Cell> history = FXMLDocumentController.expandedDataHistory.getExpandedDataHistory(this.name);
        for (int index = 1; index < familyName.size(); index++) {
            cellName = familyName.get(index);

            //i = index / this.getMatrix().columnsNumber;
            //j = index % this.getMatrix().columnsNumber;
            Cell cell = evaluator.getVariable(cellName);

            ret.put(cell.name, new Cell(cell));
            if (history.containsKey(cell.name)) {
                cell.set(history.get(cell.name));
            } else {
                cell.evaluate(String.valueOf(""));
                cell.removeThisAsListner();
            }

        }
        return ret;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public boolean isUpdated() {
        return updated;
    }

    public void setUpdated(boolean updated) {
        this.updated = updated;
    }

    public HashMap<String, Cell> cellArea() {
        HashMap<String, Cell> ret = new HashMap<String, Cell>();
        String startCellName = this.name;
        if (isExpanded) {
            ArrayList<Integer> myIndex = this.evaluator.getVariableIndexFromName(name);
            int i1 = myIndex.get(0);
            int j1 = myIndex.get(1);
            int i2 = i1 + expandedRowsNumber - 1;
            int j2 = j1 + expandedColNumber - 1;
            String endCellName = evaluator.getCellName(i2, j2);

            ArrayList<String> familyName = evaluator.variableNameFamily(startCellName, endCellName);
            for (int i = 0; i < familyName.size(); i++) {
                ret.put(familyName.get(i), new Cell(evaluator.getVariable(familyName.get(i))));
            }
        } else {
            ret.put(name, new Cell(this));
        }

        return ret;
    }

    public HashMap<String, Cell> cellAreaFromName(String nameCell, int expandedRowsNumber, int expandedColNumber) {
        HashMap<String, Cell> ret = new HashMap<String, Cell>();
        String startCellName = nameCell;
        if (isExpanded) {
            ArrayList<Integer> myIndex = this.evaluator.getVariableIndexFromName(name);
            int i1 = myIndex.get(0);
            int j1 = myIndex.get(1);
            int i2 = i1 + expandedRowsNumber - 1;
            int j2 = j1 + expandedColNumber - 1;
            String endCellName = evaluator.getCellName(i2, j2);

            ArrayList<String> familyName = evaluator.variableNameFamily(startCellName, endCellName);
            for (int i = 0; i < familyName.size(); i++) {
                ret.put(familyName.get(i), new Cell(evaluator.getVariable(familyName.get(i))));
            }
        } else {
            ret.put(nameCell, new Cell(evaluator.getVariable(nameCell)));
        }

        return ret;
    }

    public int getExpandedColNumber() {
        return expandedColNumber;
    }

    public int getExpandedRowsNumber() {
        return expandedRowsNumber;
    }

    void majPrintableValue() {
        isPrintableValue = !(!isValue() && !isSpecialValueCell);
        if (isPrintableValue && !isSpecialValueCell) {
            if (!allInformersAreValue()) {
                isPrintableValue = false;
            }
        }
    }

    public boolean isPrintableValue() {
        return isPrintableValue;
    }

    public boolean isContainRandomFunction() {
        return containRandomFunction;
    }

    public void setContainRandomFunction(boolean containRandomFunction) {
        this.containRandomFunction = containRandomFunction;
    }

    public String getErrorType() {
        if (isEmpty()) {
            return "Empty Cell";
        } else if (errorFromReferencement != null) {
            return errorFromReferencement.getType().toString();
        } else if (error != null) {
            return error.getType().toString();
        } else {
            return "No Error";
        }
        
    }
    
    public void setColor(String color){
        this.color=color;
    }

    public String getColor() {
        return color;
    }
    
    
    
    
}
