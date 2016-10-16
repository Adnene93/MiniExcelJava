/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Hatem
 */
public class ArithmeticExpressionEvaluator implements Serializable {

   
    static String REGULAREXP_DOUBLE = "^[0-9]+(\\.[0-9]+)?";
    static String REGULAREXP_DOUBLEMATCH = "^[0-9]+(\\.[0-9]+)?";
    static String REGULAREXP_INTEGER = "^-?[0-9]+";
    static String REGULAREXP_NATURAL = "^[0-9]+";
    static String REGULAREXP_OTHERSYMBOLS = "^\\+|^-|^\\*|^\\/|^\\^|^\\(|^\\)|^#|^,";
    Evaluator evaluator;
    ArithmeticFunctionEvaluator eAFE;

    public ArithmeticExpressionEvaluator(Evaluator evaluator) {
        this.evaluator = evaluator;
        eAFE = new ArithmeticFunctionEvaluator(this);
    }

   
    CombinedReturnObject evaluate(String exp) throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        /*expression = exp+"#";
         tc = 0*/;
        return axiome();
    }

    static boolean isStartOfArithmeticExpression(Symbol symbol) {
        return (symbol.type() == SymboleTypes.OPENEDPARENTHESIS) || symbol.type() == SymboleTypes.DOUBLENUMBER || ArithmeticFunctionEvaluator.isFunction(symbol) || LogicExpressionEvaluator.isStartOfLogicalExpression2(symbol) || symbol.type() == SymboleTypes.VARIABLE || symbol.type() == SymboleTypes.OPMINUS;
    }

    CombinedReturnObject axiome() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);

        if (isStartOfArithmeticExpression(actualSymbol())) {
            ret = expression();
           
            if (actualSymbol().type() != SymboleTypes.SHARP) {
                //System.out.println("ERROR1!-" + actualSymbol());
            }
        } else {
            //System.out.println("ERROR2!-" + actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject expression() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        CombinedReturnObject terme = new CombinedReturnObject(0.);
        if (isStartOfArithmeticExpression(actualSymbol())) {

            terme = terme();
            if (actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                    || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                    || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
                ret = expression2(terme);
            } else {
                throw new UnexpectedSymbolExceptionUnvalidStartOfExpression(actualSymbol());
            }
        } else {

        }
        return ret;
    }

    CombinedReturnObject expression2(CombinedReturnObject terme) throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        CombinedReturnObject terme2 = new CombinedReturnObject(0.);
        Symbol op;
        if (actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS) {
            op = actualSymbol();
            nextSymbol();
            if (isStartOfArithmeticExpression(actualSymbol())) {
                terme2 = terme();
                if (op.type() == SymboleTypes.OPPLUS) {
                    
                    ret = new CombinedReturnObject(terme);
                    ret.add(terme2);
                    
                } else {
                   
                    //ret.setIsValue(terme.isValue() && terme2.isValue());
                    ret = new CombinedReturnObject(terme);
                    ret.sub(terme2);

                }
                evaluator.setActualEvaluationCellSpecialValue(false);
                if (actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                        || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                        || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
                    ret = expression2(ret);
                } else {
                    //System.out.println("ERROR5!-" + actualSymbol());
                    throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
                }
            } else {
                //System.out.println("ERROR6! - " + actualSymbol());
                throw new UnexpectedSymbolException(actualSymbol());
            }
        } else if (actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
            ret = terme;

        } else {
            //System.out.println("ERROR7! - " + actualSymbol());
            throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject terme() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        CombinedReturnObject factor = new CombinedReturnObject(0.);
        if (isStartOfArithmeticExpression(actualSymbol())) {

            factor = factor();

            if (actualSymbol().type() == SymboleTypes.OPMULT || actualSymbol().type() == SymboleTypes.OPDIVIDE
                    || actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                    || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                    || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
                ret = terme2(factor);
            } else {
                //System.out.println("ERROR8!-" + actualSymbol());
                throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
            }
        } else {
            //System.out.println("ERROR9!-" + actualSymbol());
            throw new UnexpectedSymbolExceptionUnvalidStartOfExpression(actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject terme2(CombinedReturnObject factor) throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        Symbol op;
        CombinedReturnObject factor2 = new CombinedReturnObject(0.);

        if (actualSymbol().type() == SymboleTypes.OPMULT || actualSymbol().type() == SymboleTypes.OPDIVIDE) {
            op = actualSymbol();
            nextSymbol();

            if (isStartOfArithmeticExpression(actualSymbol())) {
                factor2 = factor();
                if (op.type() == SymboleTypes.OPMULT) {
                    //ret.setDoubleValue(facteur.doubleValue() * facteur2.doubleValue());
                    /*added*///ret.setIsValue(facteur.isValue()&&facteur2.isValue());/*added*/
                    ret = new CombinedReturnObject(factor);
                    ret.mul(factor2);
                } else {
                    //ret.setDoubleValue(facteur.doubleValue() / facteur2.doubleValue());
                    ret = new CombinedReturnObject(factor);
                    ret.div(factor2);
                }
                evaluator.setActualEvaluationCellSpecialValue(false);
                if (actualSymbol().type() == SymboleTypes.OPMULT || actualSymbol().type() == SymboleTypes.OPDIVIDE
                        || actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                        || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                        || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
                    ret = terme2(ret);
                } else {
                    //System.out.println("ERROR10!-" + actualSymbol());
                    throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
                }
            } else {
                //System.out.println("ERROR11!-" + actualSymbol());
                throw new UnexpectedSymbolExceptionUnvalidStartOfExpression(actualSymbol());
            }
        } else if (actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
            ret = factor;
        } else {
            //System.out.println("ERROR12!-" + actualSymbol());
            throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject factor() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        CombinedReturnObject facteur = new CombinedReturnObject(0.);
        if (isStartOfArithmeticExpression(actualSymbol())) {
            facteur = exponent();
            if (actualSymbol().type() == SymboleTypes.OPPOW || actualSymbol().type() == SymboleTypes.OPMULT || actualSymbol().type() == SymboleTypes.OPDIVIDE
                    || actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                    || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                    || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {

                ret = factor2(facteur);

            } else {
                //System.out.println("ERROR8!-" + actualSymbol());
                throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
            }
        } else {
            throw new UnexpectedSymbolExceptionUnvalidStartOfExpression(actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject factor2(CombinedReturnObject facteur) throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        CombinedReturnObject facteur2 = new CombinedReturnObject(0.);
        if (actualSymbol().type() == SymboleTypes.OPPOW) {
            nextSymbol();
            if (isStartOfArithmeticExpression(actualSymbol())) {
                facteur2 = exponent();
                ret = new CombinedReturnObject(facteur);
                ret.pow(facteur2);
                evaluator.setActualEvaluationCellSpecialValue(false);
                if (actualSymbol().type() == SymboleTypes.OPPOW || actualSymbol().type() == SymboleTypes.OPMULT || actualSymbol().type() == SymboleTypes.OPDIVIDE
                        || actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                        || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                        || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
                    ret = factor2(ret);
                } else {
                    //System.out.println("ERROR10!-" + actualSymbol());
                    throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
                }

            }

        } else if (actualSymbol().type() == SymboleTypes.OPMULT || actualSymbol().type() == SymboleTypes.OPDIVIDE
                || actualSymbol().type() == SymboleTypes.OPPLUS || actualSymbol().type() == SymboleTypes.OPMINUS
                || actualSymbol().type() == SymboleTypes.SHARP || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS
                || actualSymbol().type() == SymboleTypes.COMMA || LogicExpressionEvaluator.isLogicOperation(actualSymbol())) {
            ret = facteur;
        } else {
            throw new UnexpectedSymbolExceptionExpectedAnOperationOrEndOfExpression(actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject exponent() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);

        if ((actualSymbol().type()) == SymboleTypes.OPMINUS) {
            nextSymbol();
            if (isStartOfArithmeticExpression(actualSymbol())) {
                ret = exponent();
                ret.negate();
            } else {
                throw new UnexpectedSymbolExceptionUnvalidStartOfExpression(actualSymbol());
            }

        } else if (actualSymbol().type() == SymboleTypes.DOUBLENUMBER) {
            ret = actualSymbol().value();
            nextSymbol(); //aprés un nombre il est impossible d'avoir un nombre directe et donc on favorise le -
        } else if (actualSymbol().type() == SymboleTypes.OPENEDPARENTHESIS) {
            nextSymbol();
            if (GeneralExpressionEvaluator.isStartofGeneralExpression(actualSymbol())) {
                ret = myAttachedGeneralExpressionEvaluator().generalExpression();
                if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                    nextSymbol();
                } else {
                    throw new BadParenthesizedExpectedClosedException(actualSymbol());
                    //System.out.println("ERROR13!-" + actualSymbol());
                }
            } else {
                //System.out.println("ERROR14!-" + actualSymbol());
                throw new UnexpectedSymbolException(actualSymbol());
            }

        } else if (ArithmeticFunctionEvaluator.isFunction(actualSymbol())) {
            CombinedReturnObject recognized = myAttachedArithmeticFunctionEvaluator().formula();
            ret = recognized;
        } else if (LogicExpressionEvaluator.isStartOfLogicalExpression2(actualSymbol())) {
            CombinedReturnObject recognized = myAttachedLogicExpressionEvaluator().logicalExpression2();
            ret = recognized;

        } else if (actualSymbol().type() == SymboleTypes.VARIABLE) {
            ret = variable();
        } else {
            //System.out.println("ERROR15!-" + actualSymbol());
            throw new UnexpectedSymbolException(actualSymbol());
        }

        return ret;
    }

    CombinedReturnObject variable() throws EvaluatorException {
        CombinedReturnObject var1;
        if (actualSymbol().type() == SymboleTypes.VARIABLE) {
            Symbol nameVar1 = actualSymbol();
            var1 = new CombinedReturnObject(myEvaluator().getVariableValue(nameVar1.stringValue()));
            if (nameVar1.getPosition() == 1 && myEvaluator().getMyLexicalAnalyser().lookAfterSymbol().type() == SymboleTypes.SHARP) {
                myEvaluator().setActualEvaluationCellSpecialValue(true);
            } else {
                myEvaluator().setActualEvaluationCellSpecialValue(false);
            }
            nextSymbol();
            /*if (actualSymbol().type() == SymboleTypes.DOUBLEPOINT) {
             nextSymbol();
             if (actualSymbol().type() == SymboleTypes.VARIABLE) {
             CombinedReturnObject nameVar2 = actualSymbol().value();
             ArrayList<CombinedReturnObject> ListOfVar = myEvaluator().getVariableFamilyValues(nameVar1.stringValue(), nameVar2.stringValue());
             ArrayList<Integer> dims = myEvaluator().variableFamilyDimensions(nameVar1.stringValue(), nameVar2.stringValue());

             CombinedReturnObject ret = new CombinedReturnObject(ListOfVar.get(0));
             ret.createMatrixFromArrayList(dims.get(0), dims.get(1), ListOfVar);

             nextOneSymbol();
             return ret;
             } else {
             throw new UnexpectedSymbolExceptionVariableExpected(actualSymbol());
             }
             } else if (actualSymbol().type() == SymboleTypes.DOUBLEPOINTSUM || actualSymbol().type() == SymboleTypes.DOUBLEPOINTMUL || actualSymbol().type() == SymboleTypes.DOUBLEPOINTSUMSQUARE || actualSymbol().type() == SymboleTypes.DOUBLEPOINTLENGTH) {
             Symbol op = actualSymbol();
             nextSymbol();
             if (actualSymbol().type() == SymboleTypes.VARIABLE) {
             CombinedReturnObject nameVar2 = actualSymbol().value();
             ArrayList<CombinedReturnObject> listOfVar = myEvaluator().getVariableFamilyValues(nameVar1.stringValue(), nameVar2.stringValue());
             double sum = 0.;
             boolean isValue = true;
             if (op.type() == SymboleTypes.DOUBLEPOINTSUM) {
             for (int i = 0; i < listOfVar.size(); i++) {
             sum = sum + listOfVar.get(i).doubleValue();
             isValue = isValue && listOfVar.get(i).isValue();
             }
             } else if (op.type() == SymboleTypes.DOUBLEPOINTMUL) {
             sum = 1.;
             for (int i = 0; i < listOfVar.size(); i++) {
             sum = sum * listOfVar.get(i).doubleValue();
             isValue = isValue && listOfVar.get(i).isValue();
             }
             } else if (op.type() == SymboleTypes.DOUBLEPOINTSUMSQUARE) {
             sum = 0;
             for (int i = 0; i < listOfVar.size(); i++) {
             sum = sum + Math.pow(listOfVar.get(i).doubleValue(), 2);
             isValue = isValue && listOfVar.get(i).isValue();
             }
             } else if (op.type() == SymboleTypes.DOUBLEPOINTLENGTH) {
             sum = listOfVar.size();

             }
             CombinedReturnObject ret = new CombinedReturnObject(sum);
             ret.setIsValue(isValue);

             nextOneSymbol();
             return ret;
             }
             }
             */
            var1 = variable2(nameVar1, var1);
            //if (var1.isEmpty()) throw new EmptyReferencedCellException(nameVar1);
            if (var1.isEmpty() || var1.isErrorInExpression()) {
                var1.setErrorInExpression(true);
            }
        } else {
            throw new UnexpectedSymbolExceptionVariableExpected(actualSymbol());
        }

        return var1;
    }

    CombinedReturnObject variable2(Symbol nameVar1, CombinedReturnObject var1) throws EvaluatorException {
        CombinedReturnObject ret = var1;
        if (actualSymbol().type() == SymboleTypes.DOUBLEPOINT) {

            nextSymbol();
            if (actualSymbol().type() == SymboleTypes.VARIABLE) {
                CombinedReturnObject nameVar2 = actualSymbol().value();
                
                if (actualSymbol().getPosition() == 3 && myEvaluator().getMyLexicalAnalyser().lookAfterSymbol().type() == SymboleTypes.SHARP) {
                    myEvaluator().setActualEvaluationCellSpecialValue(true);
                } else {
                    myEvaluator().setActualEvaluationCellSpecialValue(false);
                }
                
                ArrayList<CombinedReturnObject> ListOfVar = myEvaluator().getVariableFamilyValues(nameVar1.stringValue(), nameVar2.stringValue());
                ArrayList<Integer> dims = myEvaluator().variableFamilyDimensions(nameVar1.stringValue(), nameVar2.stringValue());
                //if (var1.isEmpty()) throw new EmptyReferencedCellException(nameVar1);

                ret = new CombinedReturnObject(ListOfVar.get(0));
                //if (var1.isEmpty() || var1.isErrorInExpression()) ret.setErrorInExpression(true);
                /*for (int i =0;i<ListOfVar.size();i++){
                 if (ListOfVar.get(i).isEmpty()) throw new EmptyReferencedCellException();
                 }*/
                for (int i = 0; i < ListOfVar.size(); i++) {
                    if (ListOfVar.get(i).isEmpty() || ListOfVar.get(i).isErrorInExpression()) {
                        ret.setErrorInExpression(true);
                    }
                }
                ret.createMatrixFromArrayList(dims.get(0), dims.get(1), ListOfVar);

                nextSymbol();
                return ret;
            } else {
                throw new UnexpectedSymbolExceptionVariableExpected(actualSymbol());
            }
        } else if (actualSymbol().type() == SymboleTypes.DOUBLEPOINTSUM || actualSymbol().type() == SymboleTypes.DOUBLEPOINTMUL || actualSymbol().type() == SymboleTypes.DOUBLEPOINTSUMSQUARE || actualSymbol().type() == SymboleTypes.DOUBLEPOINTLENGTH) {
            Symbol op = actualSymbol();
            nextSymbol();
            if (actualSymbol().type() == SymboleTypes.VARIABLE) {
                CombinedReturnObject nameVar2 = actualSymbol().value();
                ArrayList<CombinedReturnObject> listOfVar = myEvaluator().getVariableFamilyValues(nameVar1.stringValue(), nameVar2.stringValue());
                //if (var1.isEmpty()) throw new EmptyReferencedCellException(nameVar1);

                double sum = 0.;
                boolean isValue = true;
                if (op.type() == SymboleTypes.DOUBLEPOINTSUM) {
                    for (int i = 0; i < listOfVar.size(); i++) {
                        sum = sum + listOfVar.get(i).doubleValue();
                        isValue = isValue && listOfVar.get(i).isValue();
                    }
                } else if (op.type() == SymboleTypes.DOUBLEPOINTMUL) {
                    sum = 1.;
                    for (int i = 0; i < listOfVar.size(); i++) {
                        sum = sum * listOfVar.get(i).doubleValue();
                        isValue = isValue && listOfVar.get(i).isValue();
                    }
                } else if (op.type() == SymboleTypes.DOUBLEPOINTSUMSQUARE) {
                    sum = 0;
                    for (int i = 0; i < listOfVar.size(); i++) {
                        sum = sum + Math.pow(listOfVar.get(i).doubleValue(), 2);
                        isValue = isValue && listOfVar.get(i).isValue();
                    }
                } else if (op.type() == SymboleTypes.DOUBLEPOINTLENGTH) {
                    sum = listOfVar.size();

                }
                ret = new CombinedReturnObject(sum);
                //if (var1.isEmpty() || var1.isErrorInExpression()) ret.setErrorInExpression(true);
                /*for (int i =0;i<ListOfVar.size();i++){
                 if (ListOfVar.get(i).isEmpty()) throw new EmptyReferencedCellException();
                 }*/
                for (int i = 0; i < listOfVar.size(); i++) {
                    if (listOfVar.get(i).isEmpty() || listOfVar.get(i).isErrorInExpression()) {
                        ret.setErrorInExpression(true);
                    }
                }
                ret.setIsValue(isValue);

                nextSymbol();
                return ret;
            }
        } else {
            ret = var1;
        }
        return ret;
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
    GeneralExpressionEvaluator myAttachedGeneralExpressionEvaluator() {
        return evaluator.myAttachedGeneralExpressionEvaluator();
    }

    LogicExpressionEvaluator myAttachedLogicExpressionEvaluator() {
        return evaluator.myAttachedLogicExpressionEvaluator();
    }

    ArithmeticFunctionEvaluator myAttachedArithmeticFunctionEvaluator() {
        return this.eAFE;
    }

    Evaluator myEvaluator() {
        return evaluator;
    }

    /*
     CombinedReturnObject facteur(){
     CombinedReturnObject ret=new CombinedReturnObject(0.);
     char x = expression.charAt(tc);
     String reste = expression.substring(tc);

     if (isStartOfNumber(x)) {
     double recognized = recognizeNumber(reste);
     ret.setDoubleValue(recognized);
     }
     else if (x=='(') {
     tc = tc+1;
     x=expression.charAt(tc);
     if (ExpressionEvaluator.isStartofGeneralExpression(x)) {

     ret = ExpressionEvaluator.ee.generalExpression();
     x=expression.charAt(tc);
     if (x==')') tc=tc+1;
     else System.out.println("ERROR13!-"+tc);
     }
     else System.out.println("ERROR14!-"+tc);

     }
     else if (FunctionEvaluator.isStartOfFunction(x)){
     CombinedReturnObject recognized = ExpressionEvaluator.fe.formula();
     ret = recognized;
     }
     else if (LogicExpressionEvaluator.isExclusifStartOfLogicExpression(x)){
     CombinedReturnObject recognized = ExpressionEvaluator.ee.logicalExpression3();
     ret = recognized;
     }
     else System.out.println("ERROR15!-"+tc);
     return ret;
     }
     */

    /*double recognizeNumber(String numberText){
     double ret = 0.;
     Pattern pattern = Pattern.compile(REGULAREXP_DOUBLE);
     Matcher matcher = pattern.matcher(numberText);
     if (matcher.find()) {
     ret = Double.valueOf(matcher.group());
     tc += matcher.end();
     //System.out.println(matcher.start()+"-"+matcher.end());
     }
     else ret = 0.;
     return ret;
     }
     */
    /*double recognizeNumber(String numberText){
     double ret = 0.;
     Pattern pattern = Pattern.compile(REGULAREXP_DOUBLE);
     Matcher matcher = pattern.matcher(numberText);
     while (matcher.find()) {
     //ret = Double.valueOf(matcher.group());
     System.out.println(matcher.group());
     }
     ret=1.;
     return ret;
     }*/
    TreeMap<String, Function> getTableOfFunction() {
        return eAFE.FUNCTIONS;
    }
}
