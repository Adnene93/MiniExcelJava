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
abstract public class Function implements Serializable {

    int minimalNumberofArguments = 1;
    int maximalNumberofArguments = 1;
    FunctionType typeOfFunction;
    boolean integerFunction;
    boolean realPositiveFunction;

    abstract CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg);

    public Function(int minimalNumberofArguments, int maximalNumberofArguments, FunctionType type) {
        this.minimalNumberofArguments = minimalNumberofArguments;
        this.maximalNumberofArguments = maximalNumberofArguments;
        typeOfFunction = type;
        integerFunction = false;
        realPositiveFunction = false;
    }

    public Function(int minimalNumberofArguments, int maximalNumberofArguments, FunctionType type, boolean integerFunction) {
        this.minimalNumberofArguments = minimalNumberofArguments;
        this.maximalNumberofArguments = maximalNumberofArguments;
        typeOfFunction = type;
        this.integerFunction = integerFunction;
        realPositiveFunction = false;
    }
    
    public Function(int minimalNumberofArguments, int maximalNumberofArguments, FunctionType type,boolean integerFunction, boolean realPositiveFunction) {
        this.minimalNumberofArguments = minimalNumberofArguments;
        this.maximalNumberofArguments = maximalNumberofArguments;
        typeOfFunction = type;
        this.integerFunction = integerFunction;
        this.realPositiveFunction = realPositiveFunction;
    }

    public FunctionType getTypeOfFunction() {
        return typeOfFunction;
    }

    public String getMaximalNumberofArguments() {
        if (maximalNumberofArguments != -1) {
            return String.valueOf(maximalNumberofArguments);
        } else {
            return "inf";
        }
    }

    public void setIntegerFunction(boolean integerFunction) {
        this.integerFunction = integerFunction;
    }

    public boolean isIntegerFunction() {
        return integerFunction;
    }

    public boolean isRealPositiveFunction() {
        return realPositiveFunction;
    }
    
    

    public String getMinimalNumberofArguments() {
        return String.valueOf(minimalNumberofArguments);
    }

    public enum FunctionType {

        ARITHMETIC, LOGIC, STATISTIC;

        int getIndex() {
            if (this == ARITHMETIC) {
                return 0;
            } else if (this == LOGIC) {
                return 1;
            } else {
                return 2;
            }
        }

        @Override
        public String toString() {
            return super.toString(); //To change body of generated methods, choose Tools | Templates.
        }

    }

}
