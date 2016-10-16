/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;

/**
 *
 * @author Hatem
 */
public enum SymboleTypes implements Serializable {

    OPPLUS, OPMINUS, OPMULT, OPDIVIDE, OPPOW, OPENEDPARENTHESIS, CLOSEDPARENTHESIS,
    OPLESSER, OPGREATER, OPEQUAL, OPDIFFERENT, OPLESSEROREQUAL, OPGREATEROREQUAL,
    TRUE, FALSE, COMMA, SHARP,
    ARITHMETICFUNCTION,
    LOGICFUNCTION,
    FUNCTION,
    DOUBLENUMBER,
    VARIABLE,
    STARTOFEXP,
    DOUBLEPOINT,
    DOUBLEPOINTSUM,
    DOUBLEPOINTMUL,
    DOUBLEPOINTSUMSQUARE,
    DOUBLEPOINTLENGTH,
    UKNOWN;
//^\\^

    public String regExp() {
        if (this.equals(OPPLUS)) {
            return "\\+";
        } else if (this.equals(OPMINUS)) {
            return "-";
        } else if (this.equals(OPMULT)) {
            return "\\*";
        } else if (this.equals(OPDIVIDE)) {
            return "\\/";
        } else if (this.equals(OPPOW)) {
            return "\\^";
        } else if (this.equals(OPENEDPARENTHESIS)) {
            return "\\(";
        } else if (this.equals(CLOSEDPARENTHESIS)) {
            return "\\)";
        } else if (this.equals(OPLESSER)) {
            return "<";
        } else if (this.equals(OPGREATER)) {
            return ">";
        } else if (this.equals(OPLESSEROREQUAL)) {
            return "<=";
        } else if (this.equals(OPGREATEROREQUAL)) {
            return ">=";
        } else if (this.equals(OPEQUAL)) {
            return "==";
        } else if (this.equals(OPDIFFERENT)) {
            return "!=";
        } else if (this.equals(TRUE)) {
            return "true";
        } else if (this.equals(FALSE)) {
            return "false";
        } else if (this.equals(COMMA)) {
            return ",";
        } else if (this.equals(SHARP)) {
            return "#";
        } else if (this.equals(STARTOFEXP)) {
            return "=";
        } else if (this.equals(DOUBLEPOINT)) {
            return ":";
        } else if (this.equals(DOUBLEPOINTSUM)) {
            return ":\\+";
        } else if (this.equals(DOUBLEPOINTSUMSQUARE)) {
            return ":\\^";
        } else if (this.equals(DOUBLEPOINTMUL)) {
            return ":\\*";
        } else if (this.equals(DOUBLEPOINTLENGTH)) {
            return ":_";
        } else if (this.equals(ARITHMETICFUNCTION)) {
            return ArithmeticFunctionEvaluator.REGEXP_FUNCTIONSMATCH;
        } else if (this.equals(LOGICFUNCTION)) {
            return ArithmeticFunctionEvaluator.REGEXP_FUNCTIONSMATCH;
        } else if (this.equals(VARIABLE)) {
            return LexicalAnalyser.REGEXP_VARIABLESMATCH;
        } else if (this.equals(DOUBLENUMBER)) {
            return ArithmeticExpressionEvaluator.REGULAREXP_DOUBLEMATCH;
        } else {
            return ".?";
        }
    }

    @Override
    public String toString() {
        if (this.equals(OPPLUS)) {
            return "+";
        } else if (this.equals(OPMINUS)) {
            return "-";
        } else if (this.equals(OPMULT)) {
            return "*";
        } else if (this.equals(OPDIVIDE)) {
            return "\\/";
        } else if (this.equals(OPPOW)) {
            return "^";
        } else if (this.equals(OPENEDPARENTHESIS)) {
            return "(";
        } else if (this.equals(CLOSEDPARENTHESIS)) {
            return ")";
        } else if (this.equals(OPLESSER)) {
            return "<";
        } else if (this.equals(OPGREATER)) {
            return ">";
        } else if (this.equals(OPLESSEROREQUAL)) {
            return "<=";
        } else if (this.equals(OPGREATEROREQUAL)) {
            return ">=";
        } else if (this.equals(OPEQUAL)) {
            return "==";
        } else if (this.equals(OPDIFFERENT)) {
            return "!=";
        } else if (this.equals(TRUE)) {
            return "true";
        } else if (this.equals(FALSE)) {
            return "false";
        } else if (this.equals(COMMA)) {
            return ",";
        } else if (this.equals(SHARP)) {
            return "#";
        } else if (this.equals(STARTOFEXP)) {
            return "=";
        } else if (this.equals(DOUBLEPOINT)) {
            return ":";
        } else if (this.equals(DOUBLEPOINTSUM)) {
            return ":+";
        } else if (this.equals(DOUBLEPOINTMUL)) {
            return ":*";
        } else if (this.equals(DOUBLEPOINTSUMSQUARE)) {
            return ":^";
        } else if (this.equals(DOUBLEPOINTLENGTH)) {
            return ":_";
        } else if (this.equals(ARITHMETICFUNCTION)) {
            return ArithmeticFunctionEvaluator.REGEXP_FUNCTIONSMATCH;
        } else if (this.equals(LOGICFUNCTION)) {
            return ArithmeticFunctionEvaluator.REGEXP_FUNCTIONSMATCH;
        } else if (this.equals(VARIABLE)) {
            return LexicalAnalyser.REGEXP_VARIABLESMATCH;
        } else if (this.equals(DOUBLENUMBER)) {
            return ArithmeticExpressionEvaluator.REGULAREXP_DOUBLEMATCH;
        } else {
            return ".?";
        }
    }

    static SymboleTypes typeof(String value) {
        if (value.matches(OPPLUS.regExp())) {
            return OPPLUS;
        } else if (value.matches(OPMINUS.regExp())) {
            return OPMINUS;
        } else if (value.matches(OPMULT.regExp())) {
            return OPMULT;
        } else if (value.matches(OPDIVIDE.regExp())) {
            return OPDIVIDE;
        } else if (value.matches(OPPOW.regExp())) {
            return OPPOW;
        } else if (value.matches(OPENEDPARENTHESIS.regExp())) {
            return OPENEDPARENTHESIS;
        } else if (value.matches(CLOSEDPARENTHESIS.regExp())) {
            return CLOSEDPARENTHESIS;
        } else if (value.matches(OPLESSER.regExp())) {
            return OPLESSER;
        } else if (value.matches(OPGREATER.regExp())) {
            return OPGREATER;
        } else if (value.matches(OPLESSEROREQUAL.regExp())) {
            return OPLESSEROREQUAL;
        } else if (value.matches(OPGREATEROREQUAL.regExp())) {
            return OPGREATEROREQUAL;
        } else if (value.matches(OPEQUAL.regExp())) {
            return OPEQUAL;
        } else if (value.matches(OPDIFFERENT.regExp())) {
            return OPDIFFERENT;
        } else if (value.matches(TRUE.regExp())) {
            return TRUE;
        } else if (value.matches(FALSE.regExp())) {
            return FALSE;
        } else if (value.matches(COMMA.regExp())) {
            return COMMA;
        } else if (value.matches(SHARP.regExp())) {
            return SHARP;
        } else if (value.matches(ARITHMETICFUNCTION.regExp())) {
            return ARITHMETICFUNCTION;
        } else if (value.matches(LOGICFUNCTION.regExp())) {
            return LOGICFUNCTION;
        } else if (value.matches(DOUBLENUMBER.regExp())) {
            return DOUBLENUMBER;
        } else if (value.matches(VARIABLE.regExp())) {
            return VARIABLE;
        } else if (value.matches(STARTOFEXP.regExp())) {
            return STARTOFEXP;
        } else if (value.matches(DOUBLEPOINT.regExp())) {
            return DOUBLEPOINT;
        } else if (value.matches(DOUBLEPOINTSUM.regExp())) {
            return DOUBLEPOINTSUM;
        } else if (value.matches(DOUBLEPOINTMUL.regExp())) {
            return DOUBLEPOINTMUL;
        } else if (value.matches(DOUBLEPOINTSUMSQUARE.regExp())) {
            return DOUBLEPOINTSUMSQUARE;
        } else if (value.matches(DOUBLEPOINTLENGTH.regExp())) {
            return DOUBLEPOINTLENGTH;
        } else {
            return UKNOWN;
        }

    }

}
