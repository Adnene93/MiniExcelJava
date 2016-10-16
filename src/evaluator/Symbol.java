/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import static evaluator.SymboleTypes.*;
import java.io.Serializable;

/**
 *
 * @author Hatem
 */
class Symbol  implements Serializable{

    private String value;
    private SymboleTypes typeSymbol;
    private int index;
    private int position;

    public Symbol(String value,int index,int position) {
        this.value = value;
        typeSymbol=SymboleTypes.typeof(value);
        this.index=index;
        this.position=position;
    }
    
    /*public Symbol(String value) {
        this.value = value;
        if (value.matches("\\+")) {
            typeSymbol = OPPLUS;
        } else if (value.matches("-")) {
            typeSymbol = OPMINUS;
        } else if (value.matches("\\*")) {
            typeSymbol = OPMULT;
        } else if (value.matches("/")) {
            typeSymbol = OPDIVIDE;
        } else if (value.matches("\\(")) {
            typeSymbol = OPENEDPARENTHESIS;
        } else if (value.matches("\\)")) {
            typeSymbol = CLOSEDPARENTHESIS;
        } else if (value.matches(">")) {
            typeSymbol = OPGREATER;
        } else if (value.matches(">=")) {
            typeSymbol = OPGREATEROREQUAL;
        } else if (value.matches("<")) {
            typeSymbol = OPLESSER;
        } else if (value.matches("<=")) {
            typeSymbol = OPLESSEROREQUAL;
        } else if (value.matches("==")) {
            typeSymbol = OPEQUAL;
        } else if (value.matches("!=+")) {
            typeSymbol = OPDIFFERENT;
        } else if (value.matches("true")) {
            typeSymbol = TRUE;
        } else if (value.matches("false")) {
            typeSymbol = FALSE;
        } else if (value.matches(",")) {
            typeSymbol = COMMA;
        } else if (value.matches("#")) {
            typeSymbol = SHARP;
        } else if (value.matches("=")) {
            typeSymbol = STARTOFEXP;
        } else if (value.matches(":")) {
            typeSymbol = DOUBLEPOINT;
        } else if (value.matches(ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE)) {
            typeSymbol = DOUBLENUMBER;
        } else if (value.matches(LexicalAnalyser.REGEXP_VARIABLES)) {
            typeSymbol = VARIABLE;
        } else {
            typeSymbol = UKNOWN;
        }
    }*/

    public Symbol(String value, SymboleTypes typeSymbol,int index,int position) {
        this.value = value;
        this.typeSymbol = typeSymbol;
        this.index=index;
        this.position=position;
    }

    String stringValue() {
        return this.value;
    }

    CombinedReturnObject value() {
        CombinedReturnObject ret;
        if (this.typeSymbol == DOUBLENUMBER) {
            ret = new CombinedReturnObject(Double.valueOf(value));
        } else if (this.typeSymbol == TRUE) {
            ret = new CombinedReturnObject(true);
        } else if (this.typeSymbol == FALSE) {
            ret = new CombinedReturnObject(false);
        } else {
            ret = new CombinedReturnObject(value);
        }
        return ret;
    }

    SymboleTypes type() {
        return this.typeSymbol;
    }
    
    

    @Override
    public String toString() {
        return stringValue();
    }

    public int getIndex() {
        return index;
    }

    public int getPosition() {
        return position;
    }
    
    
    
    

}
