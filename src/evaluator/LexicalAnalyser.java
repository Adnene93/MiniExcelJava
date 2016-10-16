/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import static evaluator.SymboleTypes.*;
import java.io.Serializable;

/**
 *
 * @author Hatem
 */
public class LexicalAnalyser implements Serializable {

    private String expression;
    private int tc;
    private int position;
    private Symbol actualSymbol;
    //private Symbol suivSymbol;
    private Evaluator evaluator;

    private static String REGEXP_OTHER = ".?";
    private static String REGEXP_TREATMENT = "^=";
    static String REGEXP_VARIABLES = "^[a-z][a-z]?[0123456789]+";
    static String REGEXP_VARIABLESMATCH = "[a-z][a-z]?[0123456789]+";
    static String REGEXP_SPECIAL = "^:_|^:\\+|^:\\*|^:\\^|^:";
    static String SEPARATOR = "[ \t\n]";

    public LexicalAnalyser(Evaluator evaluator) {
        this.evaluator = evaluator;
        tc = 0;
        position = 0;
    }

    public void startEvaluation(String expression) {
        this.expression = expression;
        tc = 0;
        position = 0;
        //actualSymbol=nextSymbol();
    }

    /*Symbol nextSymbol(){ //favorise le double du -
     Symbol ret;
     String recup="";
     String reste=ExpressionEvaluator.expression.substring(ExpressionEvaluator.tc);
     String generalSymbolRegularExp=ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE+"|"+
     ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS+"|"+
     LogicExpressionEvaluator.REGEXP_LOGICOP+"|"+
     LogicExpressionEvaluator.REGEXP_TRUEORFALSE;
     String functionsRegularExp=FunctionEvaluator.REGEXP_FUNCTIONS+"|"+
     LogicFunctionEvaluator.ENUM_STARTOF_LOGFUNCTIONS+"|"+
     LogicFunctionEvaluator.ENUM_STARTOF_SPECFUNCTIONS;
     Pattern pattern = Pattern.compile(generalSymbolRegularExp);
     Matcher matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();
     ExpressionEvaluator.tc+=matcher.end();
     }
     else {
     pattern = Pattern.compile(functionsRegularExp);
     matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();
     recup=recup.substring(0, recup.length()-1);
     if (ExpressionEvaluator.fe.FUNCTIONS.containsKey(recup) || ExpressionEvaluator.lfe.FUNCTIONS.containsKey(recup) || ExpressionEvaluator.lfe.SPECFUNCTIONS.containsKey(recup)) {
     ExpressionEvaluator.tc+=matcher.end()-1;
     }
     else {
                    
     }
            
     }
     else {
     pattern = Pattern.compile(REGEXP_OTHER);
     matcher = pattern.matcher(reste);
     matcher.find();
     recup=matcher.group();
                    
     }
     }
     ret=new Symbol(recup);
     return ret;
     }*/
    Symbol actualSymbol() {
        return actualSymbol;
    }

    /*Symbol lookafterSymbol(){
     return suivSymbol;
     }*/
    Symbol nextSymbol() throws EvaluatorException {
        Symbol ret = new Symbol("", tc, position);
        String recup = "";
        String reste = expression.substring(tc);
        String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
                + ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
                + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
                + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
                + ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS + "|"
                + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
        Pattern pattern = Pattern.compile(generalSymbolRegularExp);

        boolean stop = false;
        while (!stop) {
            reste = expression.substring(tc);
            Matcher matcher = pattern.matcher(reste);
            if (matcher.find()) {
                recup = matcher.group();

                if (recup.matches(ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS)) {
                    stop = true;
                    recup = recup.substring(0, recup.length() - 1);
                    if (evaluator.myAttachedArithmeticExpressionEvaluator().myAttachedArithmeticFunctionEvaluator().FUNCTIONS.containsKey(recup)) {
                        ret = new Symbol(recup, ARITHMETICFUNCTION, tc,position);
                        tc += matcher.end() - 1;
                    } else if (evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().FUNCTIONS.containsKey(recup) || evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().SPECFUNCTIONS.containsKey(recup)) {
                        ret = new Symbol(recup, LOGICFUNCTION, tc,position);
                        tc += matcher.end() - 1;
                    } else {
                        ret = new Symbol(recup, FUNCTION, tc,position);
                        //throw new UnrecognizedFunctionException(ret);
                    }
                } else {
                    ret = new Symbol(recup, tc, position);
                    if (ret.type() != SymboleTypes.UKNOWN) {
                        tc += matcher.end();
                        stop = true;

                    } else {
                        if (!ret.stringValue().matches(SEPARATOR)) {
                            stop = true;
                        } else {
                            tc += matcher.end();
                        }
                        //throw new UnkownSymbolFoundException(ret);
                    }
                }

            } else {
                ret = new Symbol("", tc, position);
            }

            actualSymbol = ret;
            //new removed
            //suivSymbol=nextSymboltemp();

        }
        position++;
        
        
        return ret;
    }
    
    Symbol lookAfterSymbol() throws EvaluatorException {
        int tctemp=tc;
        Symbol ret = new Symbol("", tc, position);
        String recup = "";
        String reste = expression.substring(tc);
        String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
                + ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
                + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
                + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
                + ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS + "|"
                + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
        Pattern pattern = Pattern.compile(generalSymbolRegularExp);

        boolean stop = false;
        while (!stop) {
            reste = expression.substring(tc);
            Matcher matcher = pattern.matcher(reste);
            if (matcher.find()) {
                recup = matcher.group();

                if (recup.matches(ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS)) {
                    stop = true;
                    recup = recup.substring(0, recup.length() - 1);
                    if (evaluator.myAttachedArithmeticExpressionEvaluator().myAttachedArithmeticFunctionEvaluator().FUNCTIONS.containsKey(recup)) {
                        ret = new Symbol(recup, ARITHMETICFUNCTION, tc,position);
                        tc += matcher.end() - 1;
                    } else if (evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().FUNCTIONS.containsKey(recup) || evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().SPECFUNCTIONS.containsKey(recup)) {
                        ret = new Symbol(recup, LOGICFUNCTION, tc,position);
                        tc += matcher.end() - 1;
                    } else {
                        ret = new Symbol(recup, FUNCTION, tc,position);
                        //throw new UnrecognizedFunctionException(ret);
                    }
                } else {
                    ret = new Symbol(recup, tc, position);
                    if (ret.type() != SymboleTypes.UKNOWN) {
                        tc += matcher.end();
                        stop = true;

                    } else {
                        if (!ret.stringValue().matches(SEPARATOR)) {
                            stop = true;
                        } else {
                            tc += matcher.end();
                        }
                        //throw new UnkownSymbolFoundException(ret);
                    }
                }

            } else {
                ret = new Symbol("", tc, position);
            }

            //actualSymbol = ret;
            //new removed
            //suivSymbol=nextSymboltemp();

        }
        //position++;
        System.out.println(ret.stringValue()+" : "+ret.getPosition());
        tc=tctemp;
        return ret;
    }

    /*Symbol nextSymbol() throws EvaluatorException {
     Symbol ret = new Symbol("", tc);;
     String recup = "";
     String reste = expression.substring(tc);
     String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
     + ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
     + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
     + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
     + ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS + "|"
     + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
     Pattern pattern = Pattern.compile(generalSymbolRegularExp);

     reste = expression.substring(tc);
     Matcher matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();

     if (recup.matches(ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS)) {

     recup = recup.substring(0, recup.length() - 1);
     if (evaluator.myAttachedArithmeticExpressionEvaluator().myAttachedArithmeticFunctionEvaluator().FUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, ARITHMETICFUNCTION, tc);
     tc += matcher.end() - 1;
     } else if (evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().FUNCTIONS.containsKey(recup) || evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().SPECFUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, LOGICFUNCTION, tc);
     tc += matcher.end() - 1;
     } else {
     ret = new Symbol(recup, FUNCTION, tc);
     //throw new UnrecognizedFunctionException(ret);
     }
     } else {
     ret = new Symbol(recup, tc);
     if (ret.type() != SymboleTypes.UKNOWN) {
     tc += matcher.end();

     } else {

     //throw new UnkownSymbolFoundException(ret);
     }
     }

     } else {
     ret = new Symbol("", tc);
     }

     actualSymbol = ret;
     //new removed
     //suivSymbol=nextSymboltemp();

     return ret;
     }*/

    /*Symbol nextSymbolOneCar() throws EvaluatorException { //favorise le - du double
     Symbol ret;
     String recup = "";
     String reste = expression.substring(tc);
     String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
     + ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
     + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
     + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
     + ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS + "|"
     + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
     Pattern pattern = Pattern.compile(generalSymbolRegularExp);
     Matcher matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();

     if (recup.matches(ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS)) {
     recup = recup.substring(0, recup.length() - 1);
     if (evaluator.myAttachedArithmeticExpressionEvaluator().myAttachedArithmeticFunctionEvaluator().FUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, ARITHMETICFUNCTION,tc);
     tc += matcher.end() - 1;
     } else if (evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().FUNCTIONS.containsKey(recup) || evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().SPECFUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, LOGICFUNCTION,tc);
     tc += matcher.end() - 1;
     } else {
     ret = new Symbol(recup,FUNCTION,tc);
     //throw new UnrecognizedFunctionException(ret);
     }
     } else {
     ret = new Symbol(recup,tc);
     if (ret.type() != SymboleTypes.UKNOWN) {
     tc += matcher.end();
     }
     else {
     //throw new UnkownSymbolFoundException(ret);
     }
     }

     } else {
     ret = new Symbol("",tc);
     }
     //ret=new Symbol(recup);
     actualSymbol=ret;
     //new removed
     //suivSymbol=nextSymboltemp();
        
     return ret;
     }
    
     Symbol nextSymboltemp() throws EvaluatorException { //favorise le double du - and without advancing tc
     Symbol ret;
     String recup = "";
     int savedtc = tc;
     String reste = expression.substring(tc);
     String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
     + ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
     + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
     + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
     + ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS + "|"
     + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
     Pattern pattern = Pattern.compile(generalSymbolRegularExp);
     Matcher matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();

     if (recup.matches(ArithmeticFunctionEvaluator.REGEXP_FUNCTIONS)) {
     recup = recup.substring(0, recup.length() - 1);
     if (evaluator.myAttachedArithmeticExpressionEvaluator().myAttachedArithmeticFunctionEvaluator().FUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, ARITHMETICFUNCTION,tc);
     tc += matcher.end() - 1;
     } else if (evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().FUNCTIONS.containsKey(recup) || evaluator.myAttachedLogicExpressionEvaluator().myAttachedLogicFunctionEvaluator().SPECFUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, LOGICFUNCTION,tc);
     tc += matcher.end() - 1;
     } else {
     ret = new Symbol(recup,FUNCTION,tc);
     //throw new UnrecognizedFunctionException(ret);
     }
     } else {
     ret = new Symbol(recup,tc);
     if (ret.type() != SymboleTypes.UKNOWN) {
     tc += matcher.end();
     }
     else {
     //throw new UnkownSymbolFoundException(ret);
     }
     }

     } else {
     ret = new Symbol("",tc);
            
     }
     //ret=new Symbol(recup);
     tc = savedtc;
     return ret;
     }
     */
    /*
    
     Symbol nextSymbol() { //favorise le double du -
     Symbol ret;
     String recup = "";
     String reste = GeneralExpressionEvaluator.expression.substring(GeneralExpressionEvaluator.tc);
     String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
     + ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
     + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
     + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
     + FunctionEvaluator.REGEXP_FUNCTIONS + "|"
     + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
     Pattern pattern = Pattern.compile(generalSymbolRegularExp);
     Matcher matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();

     if (recup.matches(FunctionEvaluator.REGEXP_FUNCTIONS)) {
     recup = recup.substring(0, recup.length() - 1);
     if (GeneralExpressionEvaluator.fe.FUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, ARITHMETICFUNCTION);
     GeneralExpressionEvaluator.tc += matcher.end() - 1;
     } else if (GeneralExpressionEvaluator.lfe.FUNCTIONS.containsKey(recup) || GeneralExpressionEvaluator.lfe.SPECFUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, LOGICFUNCTION);
     GeneralExpressionEvaluator.tc += matcher.end() - 1;
     } else {
     ret = new Symbol(recup);
     }
     } else {
     ret = new Symbol(recup);
     if (ret.type() != SymboleTypes.UKNOWN) {
     GeneralExpressionEvaluator.tc += matcher.end();
     }
     }

     } else {
     ret = new Symbol("");
     }
     //ret=new Symbol(recup);
     return ret;
     }

     Symbol nextSymbolOneCar() { //favorise le - du double
     Symbol ret;
     String recup = "";
     String reste = GeneralExpressionEvaluator.expression.substring(GeneralExpressionEvaluator.tc);
     String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
     + ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
     + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
     + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
     + FunctionEvaluator.REGEXP_FUNCTIONS + "|"
     + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
     Pattern pattern = Pattern.compile(generalSymbolRegularExp);
     Matcher matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();

     if (recup.matches(FunctionEvaluator.REGEXP_FUNCTIONS)) {
     recup = recup.substring(0, recup.length() - 1);
     if (GeneralExpressionEvaluator.fe.FUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, ARITHMETICFUNCTION);
     GeneralExpressionEvaluator.tc += matcher.end() - 1;
     } else if (GeneralExpressionEvaluator.lfe.FUNCTIONS.containsKey(recup) || GeneralExpressionEvaluator.lfe.SPECFUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, LOGICFUNCTION);
     GeneralExpressionEvaluator.tc += matcher.end() - 1;
     } else {
     ret = new Symbol(recup);
     }
     } else {
     ret = new Symbol(recup);
     if (ret.type() != SymboleTypes.UKNOWN) {
     GeneralExpressionEvaluator.tc += matcher.end();
     }
     }

     } else {
     ret = new Symbol("");
     }
     //ret=new Symbol(recup);
     return ret;
     }
    
     Symbol nextSymboltemp() { //favorise le double du - and without advancing tc
     Symbol ret;
     String recup = "";
     int savedtc = GeneralExpressionEvaluatortc;
     String reste = GeneralExpressionEvaluator.expression.substring(GeneralExpressionEvaluator.tc);
     String generalSymbolRegularExp = ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE + "|"
     + ArithmeticExpressionEvaluator.REGULAREXP_OTHERSYMBOLS + "|"
     + LogicExpressionEvaluator.REGEXP_LOGICOP + "|"
     + LogicExpressionEvaluator.REGEXP_TRUEORFALSE + "|"
     + FunctionEvaluator.REGEXP_FUNCTIONS + "|"
     + REGEXP_VARIABLES + "|" + REGEXP_TREATMENT + "|" + REGEXP_SPECIAL + "|" + REGEXP_OTHER;
     Pattern pattern = Pattern.compile(generalSymbolRegularExp);
     Matcher matcher = pattern.matcher(reste);
     if (matcher.find()) {
     recup = matcher.group();

     if (recup.matches(FunctionEvaluator.REGEXP_FUNCTIONS)) {
     recup = recup.substring(0, recup.length() - 1);
     if (GeneralExpressionEvaluator.fe.FUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, ARITHMETICFUNCTION);
     GeneralExpressionEvaluator.tc += matcher.end() - 1;
     } else if (GeneralExpressionEvaluator.lfe.FUNCTIONS.containsKey(recup) || GeneralExpressionEvaluator.lfe.SPECFUNCTIONS.containsKey(recup)) {
     ret = new Symbol(recup, LOGICFUNCTION);
     GeneralExpressionEvaluator.tc += matcher.end() - 1;
     } else {
     ret = new Symbol(recup);
     }
     } else {
     ret = new Symbol(recup);
     if (ret.type() != SymboleTypes.UKNOWN) {
     GeneralExpressionEvaluator.tc += matcher.end();
     }
     }

     } else {
     ret = new Symbol("");
     }
     //ret=new Symbol(recup);
     GeneralExpressionEvaluator.tc = savedtc;
     return ret;
     }
    
    
     */
}
