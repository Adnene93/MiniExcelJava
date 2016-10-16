/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;
import java.util.TreeMap;



/**
 *
 * @author Hatem
 */
public class LogicExpressionEvaluator  implements Serializable {

    static String REGEXP_LOGICOP = "^>=?|^<=?|^==|^!=";
    static String REGEXP_TRUEORFALSE = "^true|^false";
    private Evaluator evaluator;
    private LogicFunctionEvaluator eLFE;

    //static String REGEXP_OTHERSYMBOLS = "^\\(|^\\)|^#";
    //static String ENUM_STARTOF_LOGICOPERATION = "><=!";
    //static String TRUE = "true";
    //static String FALSE = "false";
    /*static boolean isStartOfLogicOperation(char x) {
    return ENUM_STARTOF_LOGICOPERATION.contains(String.valueOf(x));
    }*/
    public LogicExpressionEvaluator(Evaluator evaluator) {
        this.evaluator=evaluator;
        eLFE=new LogicFunctionEvaluator(this);
    }
    
    
    
    
    static boolean isLogicOperation(Symbol symbol) {
        return (symbol.type() == SymboleTypes.OPEQUAL) || (symbol.type() == SymboleTypes.OPDIFFERENT)
                || (symbol.type() == SymboleTypes.OPGREATER) || (symbol.type() == SymboleTypes.OPGREATEROREQUAL)
                || (symbol.type() == SymboleTypes.OPLESSER) || (symbol.type() == SymboleTypes.OPLESSEROREQUAL);
    }

    static boolean isTrueOrFalse(Symbol symbol) {
        return (symbol.type() == SymboleTypes.TRUE) || (symbol.type() == SymboleTypes.FALSE);
    }

    static boolean isStartOfLogicExpression(Symbol symbol) {
        return ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(symbol) || LogicFunctionEvaluator.isLogicFunction(symbol)
                || (symbol.type() == SymboleTypes.TRUE) || (symbol.type() == SymboleTypes.FALSE);
    }

    /*static boolean isExclusifStartOfLogicExpression(char x) {
        return LogicFunctionEvaluator.isStartOfLogFunction(x) || "tf".contains("" + x);
    }*/

    CombinedReturnObject evaluate(String exp) throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject("");
        return axiome();
    }

    CombinedReturnObject axiome() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject("");

        if (isStartOfLogicExpression(actualSymbol())) {
            //ret = logicalExp();
            ret = evaluator.myAttachedGeneralExpressionEvaluator().generalExpression();
            if (actualSymbol().type() != SymboleTypes.SHARP) {
                System.out.println("ERRORLOG1!-" + actualSymbol());
            }
        } else {
            System.out.println("ERRORLOG2!-" + actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject logicalExpression2() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject("");

        if (LogicFunctionEvaluator.isLogicFunction(actualSymbol())) {
            ret = eLFE.formula();
        } else if (LogicExpressionEvaluator.isTrueOrFalse(actualSymbol())) {
            // String reste = expression.substring(tc);

            CombinedReturnObject recoTrueorFalse = actualSymbol().value();
            nextSymbol();
            ret = recoTrueorFalse;
        } else {
            throw new UnexpectedSymbolException(actualSymbol());
        }
        return ret;
    }

    static boolean isStartOfLogicalExpression2(Symbol symbol) { //LogicalExpression3 deal with exclusive start of logical expression
        return LogicFunctionEvaluator.isLogicFunction(symbol)
                || (symbol.type() == SymboleTypes.TRUE) || (symbol.type() == SymboleTypes.FALSE);
    }

    Symbol actualSymbol() {
        return evaluator.actualSymbol();
    }

    Symbol nextSymbol() throws EvaluatorException {
        return evaluator.nextSymbol();
    }

    /*Symbol nextOneSymbol() throws EvaluatorException {
        return evaluator.nextOneSymbol();
    }*/
    
    /*
    Symbol lookafterSymbol() {
        return evaluator.lookafterSymbol();
    }*/
    
    /*public CombinedReturnObject logicalExp() {
     CombinedReturnObject ret= new CombinedReturnObject(false);
     Symbol op;
     if(ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(actualSymbol())) {
            
     CombinedReturnObject recExp1 = ExpressionEvaluator.aee.expression();
     if (isLogicOperation(actualSymbol())){
     op = actualSymbol();
     nextSymbol();
     if (ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(actualSymbol())){
     CombinedReturnObject recExp2 = ExpressionEvaluator.aee.expression();
     if(op.type()==SymboleTypes.OPGREATER) ret.setBooleanValue(recExp1.doubleValue()>recExp2.doubleValue());
     else if(op.type()==SymboleTypes.OPLESSER) ret.setBooleanValue(recExp1.doubleValue()<recExp2.doubleValue());
     else if(op.type()==SymboleTypes.OPEQUAL)ret.setBooleanValue(recExp1.doubleValue()==recExp2.doubleValue());
     else if(op.type()==SymboleTypes.OPGREATEROREQUAL) ret.setBooleanValue(recExp1.doubleValue()>=recExp2.doubleValue());
     else if(op.type()==SymboleTypes.OPLESSEROREQUAL) ret.setBooleanValue(recExp1.doubleValue()<=recExp2.doubleValue());
     else if(op.type()==SymboleTypes.OPDIFFERENT) ret.setBooleanValue(recExp1.doubleValue()!=recExp2.doubleValue());
     }
     else System.out.println("ErrorLog11 - "+tc);
     }
     else System.out.println("ErrorLog6 - "+tc);   
     }
     else if (LogicFunctionEvaluator.isLogicFunction(actualSymbol())){
     ret=ExpressionEvaluator.lfe.formula();
     }
     else if (isTrueOrFalse(actualSymbol())) {
     ret=actualSymbol().value();
     nextSymbol();
     }
     else System.out.println("ErrorLog8 - "+tc);
     return ret;
     }
     */
    
    /*private CombinedReturnObject logicalExp2(CombinedReturnObject param) {
     CombinedReturnObject ret= param;
     char x = expression.charAt(tc);
     String op="";
     if(isStartOfLogicOperation(x)) {
     String reste = expression.substring(tc);
     op = recognizeLogicOperation(reste);
     if (op.matches(">") || op.matches("<") || op.matches("==") || op.matches("<=") || op.matches(">=") || op.matches("!=")){
     x = expression.charAt(tc);
     if (ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(x)){
     double recExp2 = ExpressionEvaluator.aee.expression();
     if(op.matches(">")) ret.setBooleanValue(param.doubleValue()>recExp2);
     if(op.matches("<")) ret.setBooleanValue(param.doubleValue()<recExp2);
     if(op.matches("=="))ret.setBooleanValue(param.doubleValue()==recExp2);
     if(op.matches(">=")) ret.setBooleanValue(param.doubleValue()>=recExp2);
     if(op.matches("<=")) ret.setBooleanValue(param.doubleValue()<=recExp2);
     if(op.matches("!=")) ret.setBooleanValue(param.doubleValue()!=recExp2);
     }
     else System.out.println("ERRORLOG5 - "+tc);
     }
     else System.out.println("ErrorLog6 - "+tc);   
     }
     else if (LogicFunctionEvaluator.isStartOfLogFunction(x)){
     ret=ExpressionEvaluator.lfe.formula();
     }
     else if ("tf".contains(x+"")) {
     String reste = expression.substring(tc);
     String recoTrueorFalse=recognizeTrueOrFalse(reste);
     if (recoTrueorFalse.matches(TRUE)) ret=new CombinedReturnObject(true);
     else if (recoTrueorFalse.matches(FALSE)) ret=new CombinedReturnObject(false);
     else System.out.println("ErrorLog7 - "+tc);
     }
     else System.out.println("ErrorLog8 - "+tc);
     return ret;
     }*/
    /*String recognizeLogicOperation(String logicOperation){
     String ret;
     Pattern pattern = Pattern.compile(REGEXP_LOGICOP);
     Matcher matcher = pattern.matcher(logicOperation);
     if (matcher.find()) {
     ret = matcher.group();
     tc += matcher.end();
     }
     else {
     ret = "";
     }
     return ret;
     }
    
     String recognizeTrueOrFalse(String logicOperation){
     String ret;
     Pattern pattern = Pattern.compile(REGEXP_TRUEORFALSE);
     Matcher matcher = pattern.matcher(logicOperation);
     if (matcher.find()) {
     ret = matcher.group();
     tc += matcher.end();
     }
     else {
     ret = "";
     }
     return ret;
     }*/
    LogicFunctionEvaluator myAttachedLogicFunctionEvaluator(){
        return this.eLFE;
    }
    
    Evaluator myEvaluator(){
        return this.evaluator;
    }
    
    TreeMap<String,Function> getTableOfFunction(){
        return eLFE.FUNCTIONS;
    }
    TreeMap<String,Function> getTableOfSpecFunction(){
        return eLFE.SPECFUNCTIONS;
    }
    
    
}
