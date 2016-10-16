/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import javafx.beans.property.SimpleIntegerProperty;

/**
 *
 * @author Hatem
 */
public class CombinedReturnObject  implements Serializable {

    private long intValue;
    private double doubleValue;
    private boolean booleanValue;
    private String stringValue;
    //private int as;
    private Type as;
    private boolean isValue;
    private boolean errorInExpression;
    private Matrix matrix;
    

    boolean isDouble() {
        try {
            double d = Double.parseDouble(stringValue);
            return true;
        }
        catch (NumberFormatException e){
            return false;
        }
    }
    

    enum Type {

        INT, DOUBLE, BOOLEAN, STRING;
        
        @Override
        public String toString() {
            if (this.equals(Type.INT)) {
                return "int";
            }
            if (this.equals(Type.DOUBLE)) {
                return "double";
            }
            if (this.equals(Type.BOOLEAN)) {
                return "boolean";
            } else {
                return "string";
            }
        }

        public int value() {
            if (this.equals(Type.INT)) {
                return 1;
            }
            if (this.equals(Type.DOUBLE)) {
                return 2;
            }
            if (this.equals(Type.BOOLEAN)) {
                return 3;
            } else {
                return 4;
            }
        }

        static public Type myType(int value) {
            if (value == 1) {
                return INT;
            }
            if (value == 2) {
                return DOUBLE;
            }
            if (value == 3) {
                return BOOLEAN;
            } else {
                return STRING;
            }
        }

    }

    public CombinedReturnObject(CombinedReturnObject arg) {
        this.intValue = arg.intValue;
        this.doubleValue = arg.doubleValue;
        this.booleanValue = arg.booleanValue;
        this.stringValue = arg.stringValue;
        this.as = arg.as;
        this.isValue = arg.isValue;
        //this.matrix = (ArrayList<ArrayList<Double>>) arg.matrix.clone();
        this.matrix = new Matrix(arg.matrix);
        this.errorInExpression = arg.errorInExpression;
        

    }

    public CombinedReturnObject(long intValue) {
        this.intValue = intValue;
        this.doubleValue = (double) intValue;
        this.stringValue = String.valueOf(intValue);
        if (intValue == 0) {
            this.booleanValue = false;
        } else {
            this.booleanValue = true;
        }
        this.as = Type.INT;
        this.isValue = true;
        /*createMatrix(1, 1);
         this.setElem(0, 0, doubleValue);*/
        this.matrix = new Matrix(doubleValue);
        this.errorInExpression = false;
    }

    public CombinedReturnObject(double doubleValue) {
        this.doubleValue = doubleValue;
        this.intValue = (long) doubleValue;
        this.stringValue = String.valueOf(doubleValue);
        if (intValue == 0) {
            this.booleanValue = false;
        } else {
            this.booleanValue = true;
        }
        this.as = Type.DOUBLE;
        this.isValue = true;
        this.matrix = new Matrix(doubleValue);
        this.errorInExpression = false;
    }

    public CombinedReturnObject(boolean booleanValue) {
        this.booleanValue = booleanValue;
        this.stringValue = String.valueOf(booleanValue);
        if (booleanValue == true) {
            this.doubleValue = 1.0;
            this.intValue = 1;
        } else {
            this.doubleValue = 0.0;
            this.intValue = 0;
        }
        this.as = Type.BOOLEAN;

        this.isValue = true;
        this.matrix = new Matrix(doubleValue);
        this.errorInExpression = false;
    }

    public CombinedReturnObject(String stringValue) {
        if (stringValue.length() > 0) {
            this.isValue = false; //empty
        } else {
            this.isValue = true;
        }
        this.stringValue = stringValue;
        if (stringValue.toLowerCase().matches("true")) {
            this.booleanValue = true;
            this.isValue = true;
        } else {
            this.booleanValue = false;
            if (stringValue.toLowerCase().matches("false")) {
                this.isValue = true;
            }
        }
        long integer = 0;
        double doublerec = 0.;
        try {
            integer = Long.valueOf(stringValue);
            doublerec = (double) integer;
            this.isValue = true;
        } catch (NumberFormatException e1) {
            integer = 0;
            try {
                doublerec = Double.valueOf(stringValue);
                this.isValue = true;
            } catch (NumberFormatException e2) {
                doublerec = 0.;
            }
        }

        this.intValue = integer;
        this.doubleValue = doublerec;
        this.as = Type.STRING;
        this.matrix = new Matrix(doubleValue);
        this.errorInExpression = false;
    }

    long intValue() {
        return this.intValue;
    }

    double doubleValue() {
        return this.doubleValue;
    }

    String stringValue() {
        return this.stringValue;
    }

    boolean booleanValue() {
        return this.booleanValue;
    }

    Type seeAs() {
        return this.as;
    }

    public void setIntValue(long intValue) {
        this.intValue = intValue;
        this.doubleValue = (double) intValue;
        this.stringValue = String.valueOf(intValue);
        if (intValue == 0) {
            this.booleanValue = false;
        } else {
            this.booleanValue = true;
        }
        this.as = Type.INT;
        //this.matrix.get(0).set(0,doubleValue);
        if (!isMatrix()) {
            //this.matrix.get(0).set(0, doubleValue);
            this.matrix.setFirstValue(doubleValue);
        }
        this.isValue = true;
        this.errorInExpression = false;

    }

    public void setDoubleValue(double doubleValue) {
        this.doubleValue = doubleValue;
        this.intValue = (long) doubleValue;
        this.stringValue = String.valueOf(doubleValue);
        if (intValue == 0) {
            this.booleanValue = false;
        } else {
            this.booleanValue = true;
        }
        this.as = Type.DOUBLE;
        //this.matrix.get(0).set(0, doubleValue);
        if (!isMatrix()) {
            this.matrix.setFirstValue(doubleValue);
        }
        this.isValue = true;
        this.errorInExpression = false;

    }

    public void setStringValue(String stringValue) {
        this.isValue = false;
        this.stringValue = stringValue;
        if (stringValue.matches("true")) {
            this.booleanValue = true;
            this.isValue = true;
        } else {
            this.booleanValue = false;
            if (stringValue.matches("false")) {
                this.isValue = true;
            }
        }
        long integer = 0;
        double doublerec = 0.;
        try {
            integer = Long.valueOf(stringValue);
            doublerec = (double) integer;
            this.isValue = true;
        } catch (NumberFormatException e1) {
            integer = 0;
            try {
                doublerec = Double.valueOf(stringValue);
                this.isValue = true;
            } catch (NumberFormatException e2) {
                doublerec = 0.;
            }
        }

        this.intValue = integer;
        this.doubleValue = doublerec;
        //this.matrix.get(0).set(0, doubleValue);
        if (!isMatrix()) {
            this.matrix.setFirstValue(doubleValue);
        }
        this.as = Type.STRING;
        this.errorInExpression = false;

    }

    public void setBooleanValue(boolean booleanValue) {
        this.booleanValue = booleanValue;
        this.stringValue = String.valueOf(booleanValue);
        if (booleanValue == true) {
            this.doubleValue = 1.0;
            this.intValue = 1;
        } else {
            this.doubleValue = 0.0;
            this.intValue = 0;
        }
        this.as = Type.BOOLEAN;
        //this.matrix.get(0).set(0, doubleValue);
        if (!isMatrix()) {
            this.matrix.setFirstValue(doubleValue);
        }
        this.isValue = true;
        this.errorInExpression = false;

    }

    @Override
    public String toString() {
        return this.stringValue();
    }

    public boolean isValue() {
        return isValue;
    }

    public void setIsValue(boolean newValue) {
        this.isValue = newValue;
    }

    public CombinedReturnObject negate() throws EvaluatorException {

        boolean isValueTemp = this.isValue;
        boolean isErrorTemp = this.errorInExpression;
        if (isMatrix()) {
            this.matrix = this.matrix.negateMatrix();
            this.setDoubleValue(matrix.getFirstValue());

        } else {

            this.setDoubleValue(this.doubleValue * (-1));

        }
        //this.setDoubleValue(this.doubleValue + arg.doubleValue);

        this.isValue = isValueTemp;
        this.errorInExpression = isErrorTemp;

        return this;
    }

    public CombinedReturnObject add(CombinedReturnObject arg) throws EvaluatorException {

        boolean isValueTemp = this.isValue;
        boolean isErrorTemp = this.errorInExpression;
        if (isMatrix()) {
            if (arg.isMatrix()) {
                this.matrix = this.matrix.addToMatrix(arg.matrix);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.matrix = this.matrix.addToFactor(arg.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            }
        } else {
            if (arg.isMatrix()) {
                this.matrix = arg.matrix.addToFactor(this.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.setDoubleValue(this.doubleValue + arg.doubleValue);
            }
        }
        //this.setDoubleValue(this.doubleValue + arg.doubleValue);

        this.isValue = isValueTemp && arg.isValue();
        this.errorInExpression = isErrorTemp || arg.isErrorInExpression();

        return this;
    }

    public CombinedReturnObject sub(CombinedReturnObject arg) throws EvaluatorException {
        boolean isValueTemp = this.isValue;
        boolean isErrorTemp = this.errorInExpression;
        if (isMatrix()) {
            if (arg.isMatrix()) {
                this.matrix = this.matrix.subByMatrix(arg.matrix);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.matrix = this.matrix.subByFactor(arg.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            }
        } else {
            if (arg.isMatrix()) {
                this.matrix = arg.matrix.subFromFactor(this.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.setDoubleValue(this.doubleValue - arg.doubleValue);
            }
        }
        //this.setDoubleValue(this.doubleValue + arg.doubleValue);
        this.errorInExpression = isErrorTemp || arg.isErrorInExpression();
        this.isValue = isValueTemp && arg.isValue();
        return this;
    }

    public CombinedReturnObject mul(CombinedReturnObject arg) throws EvaluatorException {
        boolean isValueTemp = this.isValue;
        boolean isErrorTemp = this.errorInExpression;
        if (isMatrix()) {
            if (arg.isMatrix()) {
                this.matrix = this.matrix.multByMatrix(arg.matrix);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.matrix = this.matrix.multByFactor(arg.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            }
        } else {
            if (arg.isMatrix()) {
                this.matrix = arg.matrix.multByFactor(this.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.setDoubleValue(this.doubleValue * arg.doubleValue);
            }
        }
        //this.setDoubleValue(this.doubleValue + arg.doubleValue);
        this.errorInExpression = isErrorTemp || arg.isErrorInExpression();
        this.isValue = isValueTemp && arg.isValue();
        return this;
    }

    public CombinedReturnObject div(CombinedReturnObject arg) throws EvaluatorException {
        boolean isValueTemp = this.isValue;
        boolean isErrorTemp = this.errorInExpression;
        if (isMatrix()) {
            if (arg.isMatrix()) {
                this.matrix = this.matrix.divideByMatrix(arg.matrix);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.matrix = this.matrix.divideByFactor(arg.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            }
        } else {
            if (arg.isMatrix()) {
                this.matrix = arg.matrix.divideFromFactor(this.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            } else {
                this.setDoubleValue(this.doubleValue / arg.doubleValue);
            }
        }

        this.errorInExpression = isErrorTemp || arg.isErrorInExpression();
        this.isValue = isValueTemp && arg.isValue();
        return this;
    }
    
    public CombinedReturnObject pow(CombinedReturnObject arg) throws EvaluatorException {
        boolean isValueTemp = this.isValue;
        boolean isErrorTemp = this.errorInExpression;
        if (isMatrix()) {
            if (arg.isMatrix()) {
                throw new NotAllowedOperationException("a matrix could not be an exponent !");
            } else {
                this.matrix = this.matrix.powByFactor(arg.doubleValue);
                this.setDoubleValue(matrix.getFirstValue());
            }
        } else {
            if (arg.isMatrix()) {
                throw new NotAllowedOperationException("a matrix could not be an exponent !");
            } else {
                this.setDoubleValue(Math.pow(this.doubleValue, arg.doubleValue));
            }
        }
        //this.setDoubleValue(this.doubleValue + arg.doubleValue);
        this.errorInExpression = isErrorTemp || arg.isErrorInExpression();
        this.isValue = isValueTemp && arg.isValue();
        return this;
    }

    public int getNumberOfVariables() {
        return (this.matrix.getRowsNumber()) * (this.matrix.getColumnsNumber());
    }

    public void createMatrixFromArrayList(int n, int m, ArrayList<CombinedReturnObject> list) { //ligne par ligne

        //createMatrix(n, m);
        this.matrix = new Matrix(n, m);
        int row = 0, col = 0;
        int accessIndex = 0;
        boolean isValue = true;
        for (row = 0; row < n; row++) {
            for (col = 0; col < m; col++) {
                //this.matrix.set(row, col, list.get(accessIndex).doubleValue());
                this.matrix.set(row, col, list.get(accessIndex).doubleValue(),list.get(accessIndex).stringValue());
                isValue = isValue && list.get(accessIndex).isValue();
                accessIndex++;
            }
        }
        this.setIsValue(isValue);

    }

    public ArrayList<CombinedReturnObject> asArrayList() {
        ArrayList<CombinedReturnObject> ret = new ArrayList<>();

        for (int i = 0; i < matrix.getRowsNumber(); i++) {
            for (int j = 0; j < matrix.getColumnsNumber(); j++) {
                if (i == 0 && j == 0) {
                    ret.add(new CombinedReturnObject(this));
                } else {
                    ret.add(new CombinedReturnObject(matrix.getString(i, j)));
                }
            }

        }
        /*for (int j = 0; j < matrix.get(0).size(); j++) {
         for (int i = 0; i < matrix.size(); i++) {
         if (i == 0 && j == 0) {
         ret.add(new CombinedReturnObject(this));
         } else {
         ret.add(new CombinedReturnObject(matrix.get(i).get(j)));
         }
         }
         }*/
        return ret;
    }

    public int getNumberOfRows() {
        return matrix.getRowsNumber();
    }

    public int getNumberOfColumns() {
        return matrix.getColumnsNumber();
    }

    boolean isMatrix() {
        //return (matrix.size() > 1 || matrix.get(0).size() > 1);
        return matrix.isMatrix();
    }

    public Matrix getMatrix() {
        return matrix;
    }

    public void setErrorInExpression(boolean errorInExpression) {
        this.errorInExpression = errorInExpression;
    }

    public boolean isErrorInExpression() {
        return errorInExpression;
    }
    
    public boolean isEmpty(){
        return (stringValue.length()==0);
    }
    
    public boolean isInteger(){
        /*try {
            System.out.println(stringValue);
            long i = 
            return true;
        } catch (NumberFormatException e){
            return false;
        }*/
        return (doubleValue()>=0 && doubleValue()==Math.round(doubleValue));
    }
    
    public boolean isRealPositif(){
        /*try {
            System.out.println(stringValue);
            long i = 
            return true;
        } catch (NumberFormatException e){
            return false;
        }*/
        return (doubleValue()>=0);
    }
    
    

}
