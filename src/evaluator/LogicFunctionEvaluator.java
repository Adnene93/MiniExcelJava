/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import static evaluator.Function.FunctionType.LOGIC;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Hatem
 */
public class LogicFunctionEvaluator implements Serializable {

    TreeMap<String, Function> FUNCTIONS;
    TreeMap<String, Function> SPECFUNCTIONS;
    //static String REGEXP_LOGFUNCTIONS = "^et|^ou|^non";
    //static String REGEXP_LOGFUNCTIONS = "^[[^\\(].]+[\\(]";
    //static String REGEXP_OTHERSYMBOLS = "^\\(|^\\)|^#|^,";
    //static String REGULAREXP_DOUBLE="^-?[0-9]+(\\.[0-9]+)?";
    //static String ENUM_STARTOF_LOGFUNCTIONS = "eon";
    //static String ENUM_STARTOF_SPECFUNCTIONS = "s";
    //boolean functionNotRecognized = false;
    private LogicExpressionEvaluator eLE;

    /*String expression;
     int tc=0;*/
    public LogicFunctionEvaluator(LogicExpressionEvaluator eLE) {
        this.eLE = eLE;
        //functionNotRecognized = false;
        FUNCTIONS = new TreeMap<>();
        FUNCTIONS.put("et", new Function(1, -1, LOGIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(true);
                boolean isValueTemp = true;
                boolean isErrorTemp = false;
                int i = 0;
                for (i = 0; i < arg.size(); i++) {
                    ret.setBooleanValue(ret.booleanValue() && arg.get(i).booleanValue());
                    if (!arg.get(i).isValue()) {
                        isValueTemp = false;
                    }
                    if (arg.get(i).isErrorInExpression()) {
                        isErrorTemp = true;
                    }
                }
                ret.setIsValue(isValueTemp);
                ret.setErrorInExpression(isErrorTemp);
                return ret;
            }
        });
        FUNCTIONS.put("ou", new Function(1, -1, LOGIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(false);
                boolean isValueTemp = true;
                boolean isErrorTemp = false;
                int i = 0;
                for (i = 0; i < arg.size(); i++) {
                    ret.setBooleanValue(ret.booleanValue() || arg.get(i).booleanValue());
                    if (!arg.get(i).isValue()) {
                        isValueTemp = false;
                    }
                    if (arg.get(i).isErrorInExpression()) {
                        isErrorTemp = true;
                    }
                }
                ret.setIsValue(isValueTemp);
                ret.setErrorInExpression(isErrorTemp);
                return ret;
            }
        });
        FUNCTIONS.put("non", new Function(1, 1, LOGIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(true);
                ret = new CombinedReturnObject(!arg.get(0).booleanValue());
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }

        });
        FUNCTIONS.put("randbool", new Function(0, 0, LOGIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(false);
                int randint = (int) Math.round(Math.random());
                if (randint == 0) {
                    ret = new CombinedReturnObject(false);
                } else {
                    ret = new CombinedReturnObject(true);
                }
                Cell cell = eLE.myEvaluator().getActualEvaluatingCell();
                if (cell != null) {
                    cell.setContainRandomFunction(true);
                }
                return ret;
            }
        });

        SPECFUNCTIONS = new TreeMap<>();
        SPECFUNCTIONS.put("si", new Function(1, 3, LOGIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(false);
                boolean logicalValue = arg.get(0).booleanValue();
                if (arg.size() == 1) {
                    ret = new CombinedReturnObject(arg.get(0).booleanValue());
                    ret.setIsValue(arg.get(0).isValue());
                    ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                } else if (arg.size() == 2) {
                    if (logicalValue) {
                        ret = new CombinedReturnObject(arg.get(1));
                        ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue());
                        ret.setErrorInExpression(arg.get(0).isErrorInExpression() || arg.get(1).isErrorInExpression());
                    } else {
                        ret = new CombinedReturnObject(false);
                        ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue());
                        ret.setErrorInExpression(arg.get(0).isErrorInExpression() || arg.get(1).isErrorInExpression());
                    }
                } else {
                    if (logicalValue) {
                        ret = new CombinedReturnObject(arg.get(1));
                        ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue());
                        ret.setErrorInExpression(arg.get(0).isErrorInExpression() || arg.get(1).isErrorInExpression() || arg.get(2).isErrorInExpression());
                    } else {
                        ret = new CombinedReturnObject(arg.get(2));
                        ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue() && arg.get(2).isValue());
                        ret.setErrorInExpression(arg.get(0).isErrorInExpression() || arg.get(1).isErrorInExpression() || arg.get(2).isErrorInExpression());
                    }
                }

                return ret;
            }
        });

    }

    CombinedReturnObject evaluate(String exp) throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(false);
        return axiome();
    }

    CombinedReturnObject axiome() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(false);
        if (isLogicFunction(actualSymbol())) {
            ret = formula();
            if (actualSymbol().type() != SymboleTypes.SHARP) {
                System.out.println("FNCERROR1!-" + actualSymbol());
            }
        } else {
            System.out.println("FNCError2 ! - " + actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject formula() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(false);

        if (isLogicFunction(actualSymbol())) {
            Symbol fnc = actualSymbol();
            if (FUNCTIONS.containsKey(fnc.value().stringValue()) || SPECFUNCTIONS.containsKey(fnc.value().stringValue())) {
                nextSymbol();
                if (actualSymbol().type() == SymboleTypes.OPENEDPARENTHESIS) {
                    nextSymbol();
                    FunctionAsAttribute ff;
                    if (SPECFUNCTIONS.containsKey(fnc.value().stringValue())) {
                        ff = new FunctionAsAttribute(fnc, SPECFUNCTIONS.get(fnc.value().stringValue()));

                        /*if (SPECFUNCTIONS.get(fnc.value().stringValue()).maximalNumberofArguments == -1 || SPECFUNCTIONS.get(fnc.value().stringValue()).maximalNumberofArguments > 0) {
                            
                         if (actualSymbol().type() != SymboleTypes.CLOSEDPARENTHESIS) {
                         System.out.println("notstart of expression - " + actualSymbol().value().stringValue());
                         }
                         }*/
                    } else {
                        ff = new FunctionAsAttribute(fnc, FUNCTIONS.get(fnc.value().stringValue()));


                        /*if (FUNCTIONS.get(fnc.value().stringValue()).maximalNumberofArguments == -1 || FUNCTIONS.get(fnc.value().stringValue()).maximalNumberofArguments > 0) {
                         if (LogicExpressionEvaluator.isStartOfLogicExpression(actualSymbol()) ) {
                         ff = listeLogicExpressions(ff);
                         }
                         if (actualSymbol().type() != SymboleTypes.CLOSEDPARENTHESIS) {
                         System.out.println("notstart of expression - " + actualSymbol().value().stringValue());
                         }
                         }*/
                    }

                    if (LogicExpressionEvaluator.isStartOfLogicExpression(actualSymbol()) || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                        ff = listeLogicExpressions(ff);
                    }
                    if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                        if (ff.numberOfArgsRespected()) {
                            ret = ff.functionIdentifier.execute(ff.listOfCombined);

                            if (SPECFUNCTIONS.containsKey(ff.getFunctionSymbol().stringValue()) && ff.getFunctionSymbol().getPosition() == 1) {
                                myEvaluator().setActualEvaluationCellSpecialValue(true);
                            } else {
                                myEvaluator().setActualEvaluationCellSpecialValue(false);
                            }
                            nextSymbol();//favoriser le - et le non le double
                        } else {
                            throw new UnvalidNumberOfArgumentException(fnc, ff.functionIdentifier.minimalNumberofArguments, ff.functionIdentifier.maximalNumberofArguments, ff.listOfCombined.size());
                        }

                    } else {
                        throw new BadParenthesizedExpectedClosedException(actualSymbol());
                    }
                } else {
                    throw new UnexpectedSymbolException(actualSymbol());
                }
            } else {
                throw new UnrecognizedFunctionException(actualSymbol());
            }
        } else {
            throw new UnexpectedSymbolException(actualSymbol());
        }
        return ret;
    }

    //new after cellule : cellule
    FunctionAsAttribute listeLogicExpressions(FunctionAsAttribute ff) throws EvaluatorException {
        FunctionAsAttribute ret = ff;
        if (LogicExpressionEvaluator.isStartOfLogicExpression(actualSymbol()) /*&& (lookafterSymbol().type() != SymboleTypes.DOUBLEPOINT)*/) {
            CombinedReturnObject recLog = myEvaluator().myAttachedGeneralExpressionEvaluator().generalExpression();
            //ancient
            //ret.listOfCombined.add(recLog);
            //ancient

            //---added-----
            ret.listOfCombined.addAll(recLog.asArrayList());
            //---added-------
            if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                ret = listeLogicExpressions2(ret);
            } else {
                throw new UnexpectedSymbolExceptionExpectedAnotherArgumentOrAClosedParenthesis(actualSymbol());
            }
        } /*else if ((actualSymbol().type() == SymboleTypes.VARIABLE) && (lookafterSymbol().type() == SymboleTypes.DOUBLEPOINT)) {
         Symbol varsymb = actualSymbol();
         nextSymbol();
         nextSymbol();
         if (actualSymbol().type() == SymboleTypes.VARIABLE) {
         Symbol varsymb2 = actualSymbol();
         nextSymbol();
         ff.listOfCombined = myEvaluator().getVariableFamilyValues(varsymb.stringValue(), varsymb2.stringValue());
         } else {
         System.out.println("Expected a variable and found - " + actualSymbol().toString());
         }
         } */ else if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
            ret = ff;
        } else {
            throw new BadParenthesizedExpectedClosedException(actualSymbol());
        }
        return ret;
    }

    //before cellulle : cellule
    /*LogicFunctionFormula listeLogicExpressions(LogicFunctionFormula ff) {
     LogicFunctionFormula ret = ff;
     if (LogicExpressionEvaluator.isStartOfLogicExpression(actualSymbol())) {
     CombinedReturnObject recLog = ExpressionEvaluator.ee.generalExpression();
     ret.listOfCombined.add(recLog);
     if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
     ret = listeLogicExpressions2(ret);
     } else {
     System.out.println("FNCError6 ! - " + tc);
     }
     } else if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
     ret = ff;
     } else {
     System.out.println("FNCError7 ! - " + tc);
     }
     return ret;
     }*/
    //before cellulle : cellule
    FunctionAsAttribute listeLogicExpressions2(FunctionAsAttribute ff) throws EvaluatorException {
        FunctionAsAttribute ret = ff;
        if (actualSymbol().type() == SymboleTypes.COMMA) {
            nextSymbol();
            //String reste = expression.substring(tc);
            if (LogicExpressionEvaluator.isStartOfLogicExpression(actualSymbol())) {
                CombinedReturnObject recLog = myEvaluator().myAttachedGeneralExpressionEvaluator().generalExpression();
                //--ancient---
                //ret.listOfCombined.add(recLog);
                //--ancient---
                //---added-----
                ret.listOfCombined.addAll(recLog.asArrayList());
                //---added-------
                if (ff.numberOfArgsStillRespected()) {
                    if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                        ret = listeLogicExpressions2(ret);
                    } else {
                        throw new UnexpectedSymbolExceptionExpectedAnotherArgumentOrAClosedParenthesis(actualSymbol());
                    }
                } else {
                    throw new UnvalidNumberOfArgumentException(ff.functionSymbol, ff.functionIdentifier.minimalNumberofArguments, ff.functionIdentifier.maximalNumberofArguments, ff.listOfCombined.size());
                }
            } else {
                throw new UnexpectedSymbolExceptionExpectedAnotherExpression(actualSymbol());
            }
        } else if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
            ret = ff;
        } else {
            throw new BadParenthesizedExpectedClosedException(actualSymbol());
        }
        return ret;
    }

    /*LogicFunctionFormula listeCombinedExpressions(LogicFunctionFormula sff) {
     LogicFunctionFormula ret = sff;

     if (LogicExpressionEvaluator.isStartOfLogicExpression(actualSymbol())) {
     CombinedReturnObject recLog = ExpressionEvaluator.ee.generalExpression();
     ret.listOfCombined.add(recLog);
     if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
     ret = listeCombinedExpressions2(ret);
     } else {
     System.out.println("FNCError ! - " + tc);
     }
     } else {
     System.out.println("FNCError ! - " + tc);
     }
     return ret;
     }

     LogicFunctionFormula listeCombinedExpressions2(LogicFunctionFormula sff) {
     LogicFunctionFormula ret = sff;
     if (actualSymbol().type() == SymboleTypes.COMMA) {
     nextSymbol();
     if (LogicExpressionEvaluator.isStartOfLogicExpression(actualSymbol())) {
     CombinedReturnObject recdouble = ExpressionEvaluator.ee.generalExpression();
     ret.listOfCombined.add(recdouble);
     if (sff.numberOfArgsRespected()) {
     if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
     ret = listeCombinedExpressions2(ret);
     } else {
     System.out.println("FNCError ! - " + tc);
     }
     } else {
     System.out.println("ARGS ERROR");
     }
     }
     } else if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
     ret = sff;
     } else {
     System.out.println("FNCError ! - " + tc);
     }
     return ret;
     }*/

    /*String recognizeLogFunction(String function){
     String ret;
     Pattern pattern = Pattern.compile(REGEXP_LOGFUNCTIONS);
     Matcher matcher = pattern.matcher(function);
     if (matcher.find()) {
     ret = matcher.group().toLowerCase();
     ret=ret.substring(0, ret.length()-1);
     if (FUNCTIONS.containsKey(ret) || SPECFUNCTIONS.containsKey(ret)) {
     tc += matcher.end()-1; //pour annuler la parenthése
     }
     else {
     ret="";
     functionNotRecognized=true;
     }
     //System.out.println(matcher.start()+"-"+matcher.end());
     }
       
     else {
     ret = "";
     functionNotRecognized=true;
     }
     return ret;
     }
     */
    /*class LogicFunctionFormula {

     String functionName;
     Function functionIdentifier;
     ArrayList<CombinedReturnObject> listOfCombined;

     public LogicFunctionFormula(String functionName, Function functionIdentifier) {
     this.functionName = functionName;
     this.functionIdentifier = functionIdentifier;
     listOfCombined = new ArrayList<CombinedReturnObject>();
     }

     public boolean numberOfArgsStillRespected() {
     return (((this.functionIdentifier.maximalNumberofArguments == -1)
     || (listOfCombined.size() <= this.functionIdentifier.maximalNumberofArguments)));
     }

     public boolean numberOfArgsRespected() {
     return (((this.functionIdentifier.maximalNumberofArguments == -1)
     || (listOfCombined.size() <= this.functionIdentifier.maximalNumberofArguments))) && (listOfCombined.size() >= this.functionIdentifier.minimalNumberofArguments);
     }
     }*/
    static boolean isLogicFunction(Symbol symbol) {
        return (symbol.type() == SymboleTypes.LOGICFUNCTION) || (symbol.type() == SymboleTypes.FUNCTION);
    }

    boolean isSpecialFunction(Symbol symbol) {
        return (symbol.type() == SymboleTypes.LOGICFUNCTION) && (this.SPECFUNCTIONS.containsKey(symbol.value().toString()));
    }

    Symbol actualSymbol() {
        return eLE.actualSymbol();
    }

    /*Symbol lookafterSymbol() {
     return eLE.lookafterSymbol();
     }*/
    Symbol nextSymbol() throws EvaluatorException {
        return eLE.nextSymbol();
    }

    /*Symbol nextOneSymbol() throws EvaluatorException {
     return eLE.nextOneSymbol();
     }*/
    LogicExpressionEvaluator myAttachedLogicExpressionEvaluator() {
        return this.eLE;
    }

    Evaluator myEvaluator() {
        return this.eLE.myEvaluator();
    }

    /*static boolean isStartOfLogFunction(char x) {
     return ENUM_STARTOF_LOGFUNCTIONS.contains(String.valueOf(x)) || ENUM_STARTOF_SPECFUNCTIONS.contains(String.valueOf(x));
     }*/
}
