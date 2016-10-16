/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;
import java.util.function.BiConsumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Hatem
 */
public class GeneralExpressionEvaluator implements Serializable {

    private Evaluator evaluator;
    //static ArithmeticExpressionEvaluator aee;
    //static FunctionEvaluator fe;
    //static LogicExpressionEvaluator le;
    //static LogicFunctionEvaluator lfe;
    //static GeneralExpressionEvaluator ee;
    //LexicalAnalyser lexicalAnalyser;
    //static int tc;
    //static String expression;
    //private Symbol actualsymbol;
    //private Symbol suivsymbol;
    /*public TreeMap<String, Cell> VARIABLES;
     static ArrayList<String> STRINGONVARIABLENAME;*/

    public GeneralExpressionEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
    }

    /*CombinedReturnObject evaluateBoolean(String expression){
     this.expression=expression+"#";
     tc=0;
     return axiomeBoolean();
     }*/
    /*CombinedReturnObject evaluate(String expression) {
     this.expression = expression + "#";
     this.expression = this.expression.toLowerCase();
     //System.out.println("exp = "+this.expression);
     tc = 0;
     nextSymbol();
     return generalaxiome();
     }*/
    CombinedReturnObject generalaxiome() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        nextSymbol();

        if (actualSymbol().type() == SymboleTypes.STARTOFEXP) {
            nextSymbol();

            if (isStartofGeneralExpression(actualSymbol())) {
                //added
                /*if (actualSymbol().stringValue().matches("si")) {
                    if (evaluator.getActualEvaluatingCell()!= null) {
                        evaluator.getActualEvaluatingCell().setIsSpecialValueCell(true);
                    }
                } //added
                //addedNew
                else if (actualSymbol().type() == SymboleTypes.VARIABLE && (actualSymbol().stringValue().length() + actualSymbol().getIndex()) == evaluator.getCurrentExpression().length() - 1) {
                    if (evaluator.getActualEvaluatingCell() != null) {
                        evaluator.getActualEvaluatingCell().setIsSpecialValueCell(true);
                    }
                }
                */
                //addednNew
                ret = new CombinedReturnObject(generalExpression());
                actualSymbol();
                if (actualSymbol().type() != SymboleTypes.SHARP) {
                    throw new UnexpectedSymbolException(actualSymbol());
                }
            } else {
                if (actualSymbol().type() != SymboleTypes.SHARP) {
                    throw new UnexpectedSymbolExceptionUnvalidStartOfExpression(actualSymbol());
                } else {
                    ret = new CombinedReturnObject("="); //cell could contain only equal
                }
            }
        } else {
            ret = new CombinedReturnObject(this.evaluator.getCurrentEvaluatingExpression());

            throw new IsNotExpressionException();
        }

        return ret;
    }

    CombinedReturnObject generalExpression() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        if (isStartofGeneralExpression(actualSymbol())/*ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(x)*/) {
            ret = myAttachedExpressionEvaluator().expression();
            if (actualSymbol().type() == SymboleTypes.SHARP || LogicExpressionEvaluator.isLogicOperation(actualSymbol()) || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS || actualSymbol().type() == SymboleTypes.COMMA) {
                ret = generalExpression2(ret);
            }
        } else {
            throw new UnexpectedSymbolExceptionUnvalidStartOfExpression(actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject generalExpression2(CombinedReturnObject arg) throws EvaluatorException {
        CombinedReturnObject ret = arg;
        if (LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
            Symbol op = actualSymbol();
            nextSymbol();
            if (ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(actualSymbol())) {
                CombinedReturnObject recExp2 = myAttachedExpressionEvaluator().expression();
                ret = new CombinedReturnObject(arg.doubleValue());
                if (op.type() == SymboleTypes.OPGREATER) {
                    ret.setBooleanValue(arg.doubleValue() > recExp2.doubleValue());
                } else if (op.type() == SymboleTypes.OPLESSER) {
                    ret.setBooleanValue(arg.doubleValue() < recExp2.doubleValue());
                } else if (op.type() == SymboleTypes.OPEQUAL) {
                    ret.setBooleanValue(arg.doubleValue() == recExp2.doubleValue());
                } else if (op.type() == SymboleTypes.OPGREATEROREQUAL) {
                    ret.setBooleanValue(arg.doubleValue() >= recExp2.doubleValue());
                } else if (op.type() == SymboleTypes.OPLESSEROREQUAL) {
                    ret.setBooleanValue(arg.doubleValue() <= recExp2.doubleValue());
                } else if (op.type() == SymboleTypes.OPDIFFERENT) {
                    ret.setBooleanValue(arg.doubleValue() != recExp2.doubleValue());
                    
                }
                evaluator.setActualEvaluationCellSpecialValue(false);
            } else {
                throw new UnexpectedSymbolExceptionExpectedAnotherExpression(actualSymbol());
            }
        } else if (actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS || actualSymbol().type() == SymboleTypes.COMMA) {
            ret = arg;
        } else {
            throw new UnexpectedSymbolException(actualSymbol());
        }
        return ret;
    }

    static boolean isStartofGeneralExpression(Symbol symbol) {
        return ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(symbol) || LogicExpressionEvaluator.isStartOfLogicExpression(symbol);
    }

    Symbol actualSymbol() {
        return this.evaluator.actualSymbol();
    }

    /*
     Symbol lookafterSymbol() {
     return this.evaluator.lookafterSymbol();
     }*/
    Symbol nextSymbol() throws EvaluatorException {
        return this.evaluator.nextSymbol();
    }

    /*Symbol nextOneSymbol() throws EvaluatorException {
     return this.evaluator.nextOneSymbol();
     }*/
    ArithmeticExpressionEvaluator myAttachedExpressionEvaluator() {
        return this.evaluator.myAttachedArithmeticExpressionEvaluator();
    }

}
