/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import static evaluator.Function.FunctionType.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author Hatem
 */
public class ArithmeticFunctionEvaluator implements Serializable {

    public TreeMap<String, Function> FUNCTIONS;
    //static String REGEXP_FUNCTIONS = "^cos|^sin|^max"; //1
    //static String REGEXP_FUNCTIONS = "^[[^\\(].]+[\\(]"; // 2
    static String REGEXP_FUNCTIONS = "^[a-z]+[0-9]*[\\(]"; //3
    static String REGEXP_FUNCTIONSMATCH = "[a-z]+[0-9]*[\\(]";
    //static String REGEXP_OTHERSYMBOLS = "^\\(|^\\)|^#|^,";
    //static String REGEXP_FUNCTIONS = "^[.&&[^\\(]]*\\(";
    //static String REGULAREXP_DOUBLE="^-?[0-9]+(\\.[0-9]+)?";
    //static String ENUM_STARTOF_FUNCTIONS = "cm";
    ArithmeticExpressionEvaluator eAE;

    /*String expression;
     int tc=0;*/
    public ArithmeticFunctionEvaluator(ArithmeticExpressionEvaluator myAttachedAE) {
        this.eAE = myAttachedAE;
        FUNCTIONS = new TreeMap<String, Function>();
        FUNCTIONS.put("floor", new Function(1, 1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.floor(arg.get(0).doubleValue()));
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });
        FUNCTIONS.put("cos", new Function(1, 1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.cos(Math.toRadians(arg.get(0).doubleValue())));
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });
        FUNCTIONS.put("sin", new Function(1, 1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.sin(Math.toRadians(arg.get(0).doubleValue())));
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("tan", new Function(1, 1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.tan(Math.toRadians(arg.get(0).doubleValue())));
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("exp", new Function(1, 1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.exp(arg.get(0).doubleValue()));
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });
        FUNCTIONS.put("ln", new Function(1, 1, ARITHMETIC,false,true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.log(arg.get(0).doubleValue()));
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("log", new Function(1, 1, ARITHMETIC,false,true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.log10(arg.get(0).doubleValue()));

                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("abs", new Function(1, 1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.abs(arg.get(0).doubleValue()));

                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("round", new Function(1, 1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.round(arg.get(0).doubleValue()));

                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("div", new Function(2, 2, ARITHMETIC, true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {

                long a = Math.round(arg.get(0).doubleValue());
                long b = Math.round(arg.get(1).doubleValue());
                CombinedReturnObject ret = new CombinedReturnObject(Math.floorDiv(a, b));
                ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression() && arg.get(1).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("mod", new Function(2, 2, ARITHMETIC, true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {

                long a = Math.round(arg.get(0).doubleValue());
                long b = Math.round(arg.get(1).doubleValue());
                CombinedReturnObject ret = new CombinedReturnObject(Math.floorMod(a, b));
                ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression() && arg.get(1).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("fact", new Function(1, 1, ARITHMETIC, true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(1);
                long i = Math.round(arg.get(0).doubleValue());
                long res = 1;
                while (i > 0) {
                    res = res * i;
                    i--;
                }
                ret.setIntValue(res);
                ret.setIsValue(arg.get(0).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("pi", new Function(0, 0, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.PI);

                return ret;
            }
        });

        FUNCTIONS.put("pgcd", new Function(2, 2, ARITHMETIC, true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(1);
                long a = Math.round(arg.get(0).doubleValue());
                long b = Math.round(arg.get(1).doubleValue());
                long c = 0;
                if (a < b) {
                    c = b;
                    b = a;
                    a = c;
                }

                long r = a;
                if (b != 0) {
                    while (r != 0) {
                        r = a % b;
                        a = b;
                        b = r;
                    }
                } else {
                    a = 1;
                }

                ret.setIntValue(a);

                ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression() && arg.get(1).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("ppcm", new Function(2, 2, ARITHMETIC, true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(1);
                long a = Math.round(arg.get(0).doubleValue());
                long b = Math.round(arg.get(1).doubleValue());
                long c = 0;
                if (a < b) {
                    c = b;
                    b = a;
                    a = c;
                }

                long r = a;
                if (b != 0) {
                    while (r != 0) {
                        r = a % b;
                        a = b;
                        b = r;
                    }
                } else {
                    a = 1;
                }
                long a1 = Math.round(arg.get(0).doubleValue());
                long b1 = Math.round(arg.get(1).doubleValue());
                ret.setIntValue(a1 * b1 / a);

                ret.setIsValue(arg.get(0).isValue() && arg.get(1).isValue());
                ret.setErrorInExpression(arg.get(0).isErrorInExpression() && arg.get(1).isErrorInExpression());
                return ret;
            }
        });

        FUNCTIONS.put("rand", new Function(0, -1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.random());
                boolean isValueTemp = true;
                boolean isErrorTemp = false;

                if (arg.size() > 0) {
                    int i = 0;
                    int randint = (int) Math.round(Math.random() * (arg.size() - 1));

                    ret = new CombinedReturnObject(arg.get(randint));
                    System.out.println(" ----  : " + ret.stringValue());
                    for (i = 0; i < arg.size(); i++) {
                        //System.out.println(i+":"+arg.get(i).doubleValue());
                        //ret.setDoubleValue(ret.doubleValue() + arg.get(i).doubleValue());
                        if (!arg.get(i).isValue()) {
                            isValueTemp = true;
                        }
                        if (arg.get(i).isErrorInExpression()) {
                            isErrorTemp = true;
                        }
                    }
                }

                Cell cell = eAE.evaluator.getActualEvaluatingCell();
                if (cell != null) {
                    cell.setContainRandomFunction(true);
                }
                ret.setIsValue(isValueTemp);
                ret.setErrorInExpression(isErrorTemp);

                return ret;
            }
        });
        FUNCTIONS.put("alea", new Function(2, 2, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.random());

                int i = 0;
                double a, b;
                if (arg.get(0).doubleValue() <= arg.get(1).doubleValue()) {
                    a = arg.get(0).doubleValue();
                    b = arg.get(1).doubleValue();
                } else {
                    b = arg.get(0).doubleValue();
                    a = arg.get(1).doubleValue();
                }
                double rand = Math.random() * (b - a) + a;
                ret = new CombinedReturnObject(rand);
                if (!arg.get(0).isValue()) {
                    ret.setIsValue(false);
                } else {
                    if (!arg.get(1).isValue()) {
                        ret.setIsValue(false);
                    }
                }
                if (arg.get(0).isErrorInExpression()) {
                    ret.setErrorInExpression(true);
                } else {
                    if (arg.get(1).isErrorInExpression()) {
                        ret.setErrorInExpression(true);
                    }
                }
                Cell cell = eAE.evaluator.getActualEvaluatingCell();
                if (cell != null) {
                    cell.setContainRandomFunction(true);
                }
                return ret;
            }
        });

        FUNCTIONS.put("aleaint", new Function(2, 2, ARITHMETIC, true) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(Math.random());

                int i = 0;
                double a, b;
                if (arg.get(0).doubleValue() <= arg.get(1).doubleValue()) {
                    a = arg.get(0).doubleValue();
                    b = arg.get(1).doubleValue();
                } else {
                    b = arg.get(0).doubleValue();
                    a = arg.get(1).doubleValue();
                }
                long rand = Math.round((Math.random() * (b - a) + a));
                ret = new CombinedReturnObject(rand);
                if (!arg.get(0).isValue()) {
                    ret.setIsValue(false);
                } else {
                    if (!arg.get(1).isValue()) {
                        ret.setIsValue(false);
                    }
                }
                if (arg.get(0).isErrorInExpression()) {
                    ret.setErrorInExpression(true);
                } else {
                    if (arg.get(1).isErrorInExpression()) {
                        ret.setErrorInExpression(true);
                    }
                }
                Cell cell = eAE.evaluator.getActualEvaluatingCell();
                if (cell != null) {
                    cell.setContainRandomFunction(true);
                }
                return ret;
            }
        });
        FUNCTIONS.put("max", new Function(1, -1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret;
                boolean isValueTemp = true;
                boolean isErrorTemp = false;
                ret = new CombinedReturnObject(arg.get(0).doubleValue());
                //added
                /*ArrayList<CombinedReturnObject> listofvars = arg.get(0).asArrayList();
               
                 for (int k = 0; k < listofvars.size(); k++) {
                 if (ret.doubleValue() < listofvars.get(k).doubleValue()) {
                 ret = new CombinedReturnObject(listofvars.get(k).doubleValue());
                 }
                 }*/
                //added
                if (!arg.get(0).isValue()) {
                    isValueTemp = false;
                }
                if (arg.get(0).isErrorInExpression()) {
                    isErrorTemp = true;
                }
                int i = 0;
                for (i = 1; i < arg.size(); i++) {
                    if (ret.doubleValue() < arg.get(i).doubleValue()) {
                        /*listofvars = arg.get(i).asArrayList();
                         for (int k = 0; k < listofvars.size(); k++) {
                         if (ret.doubleValue() < listofvars.get(k).doubleValue()) {
                         ret = new CombinedReturnObject(listofvars.get(k).doubleValue());
                         }
                         }*/
                        //added New
                        ret = new CombinedReturnObject(arg.get(i).doubleValue());
                    }
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
        FUNCTIONS.put("min", new Function(1, -1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret = new CombinedReturnObject(arg.get(0).doubleValue());
                boolean isValueTemp = true;
                boolean isErrorTemp = false;

                int i = 0;
                if (!arg.get(0).isValue()) {
                    isValueTemp = false;
                }
                if (arg.get(0).isErrorInExpression()) {
                    isErrorTemp = true;
                }
                for (i = 1; i < arg.size(); i++) {
                    if (ret.doubleValue() > arg.get(i).doubleValue()) {
                        ret = new CombinedReturnObject(arg.get(i).doubleValue());

                    }
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
        FUNCTIONS.put("somme", new Function(1, -1, ARITHMETIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret;
                boolean isValueTemp = true;
                boolean isErrorTemp = false;
                ret = new CombinedReturnObject(arg.get(0).doubleValue());
                if (!arg.get(0).isValue()) {
                    isValueTemp = false;
                }
                if (arg.get(0).isErrorInExpression()) {
                    isErrorTemp = true;
                }
                int i = 0;
                //System.out.println(i+":"+arg.get(i).doubleValue());
                for (i = 1; i < arg.size(); i++) {
                    //System.out.println(i+":"+arg.get(i).doubleValue());
                    ret.setDoubleValue(ret.doubleValue() + arg.get(i).doubleValue());
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

        FUNCTIONS.put("moyenne", new Function(1, -1, STATISTIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret;
                boolean isValueTemp = true;
                boolean isErrorTemp = false;
                ret = new CombinedReturnObject(arg.get(0).doubleValue());
                if (!arg.get(0).isValue()) {
                    isValueTemp = false;
                }
                if (arg.get(0).isErrorInExpression()) {
                    isErrorTemp = true;
                }
                int i = 0;
                //System.out.println(i+":"+arg.get(i).doubleValue());
                for (i = 1; i < arg.size(); i++) {
                    //System.out.println(i+":"+arg.get(i).doubleValue());
                    ret.setDoubleValue(ret.doubleValue() + arg.get(i).doubleValue());
                    if (!arg.get(i).isValue()) {
                        isValueTemp = true;
                    }
                    if (arg.get(i).isErrorInExpression()) {
                        isErrorTemp = true;
                    }
                }

                ret.setDoubleValue(ret.doubleValue() / arg.size());
                ret.setIsValue(isValueTemp);
                ret.setErrorInExpression(isErrorTemp);
                return ret;
            }
        });

        FUNCTIONS.put("variance", new Function(1, -1, STATISTIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret;
                boolean isValueTemp = true;
                boolean isErrorTemp = false;
                double squareSum;
                double sum;
                ret = new CombinedReturnObject(arg.get(0).doubleValue());
                squareSum = Math.pow(arg.get(0).doubleValue(), 2);
                sum = arg.get(0).doubleValue();
                if (!arg.get(0).isValue()) {
                    isValueTemp = false;
                }
                if (arg.get(0).isErrorInExpression()) {
                    isErrorTemp = true;
                }
                int i = 0;
                //System.out.println(i+":"+arg.get(i).doubleValue());
                for (i = 1; i < arg.size(); i++) {
                    //System.out.println(i+":"+arg.get(i).doubleValue());
                    //ret.setDoubleValue(ret.doubleValue() + arg.get(i).doubleValue());
                    squareSum = squareSum + Math.pow(arg.get(i).doubleValue(), 2);
                    sum = sum + arg.get(i).doubleValue();
                    if (!arg.get(i).isValue()) {
                        isValueTemp = true;
                    }
                    if (arg.get(i).isErrorInExpression()) {
                        isErrorTemp = true;
                    }
                }
                double meanofsquares = squareSum / arg.size();
                double squaredmean = Math.pow(sum / arg.size(), 2);

                ret.setDoubleValue(meanofsquares - squaredmean);
                ret.setIsValue(isValueTemp);
                ret.setErrorInExpression(isErrorTemp);
                return ret;
            }
        });

        FUNCTIONS.put("mediane", new Function(1, -1, STATISTIC) {
            @Override
            CombinedReturnObject execute(ArrayList<CombinedReturnObject> arg) {
                CombinedReturnObject ret;
                boolean isValueTemp = true;
                boolean isErrorTemp = false;
                ret = new CombinedReturnObject(arg.get(0).doubleValue());
                if (!arg.get(0).isValue()) {
                    isValueTemp = false;
                }
                if (arg.get(0).isErrorInExpression()) {
                    isErrorTemp = true;
                }
                int i = 0;
                //System.out.println(i+":"+arg.get(i).doubleValue());
                for (i = 1; i < arg.size(); i++) {
                    //System.out.println(i+":"+arg.get(i).doubleValue());
                    //ret.setDoubleValue(ret.doubleValue() + arg.get(i).doubleValue());
                    if (!arg.get(i).isValue()) {
                        isValueTemp = true;
                    }
                    if (arg.get(i).isErrorInExpression()) {
                        isErrorTemp = true;
                    }
                }

                int mid = Math.floorDiv(arg.size(), 2);
                if (arg.size() % 2 == 0) {
                    ret.setDoubleValue((arg.get(mid).doubleValue() + arg.get(mid - 1).doubleValue()) / 2);
                } else {
                    ret.setDoubleValue(arg.get(mid).doubleValue());
                }

                ret.setIsValue(isValueTemp);
                ret.setErrorInExpression(isErrorTemp);
                return ret;
            }
        });

    }

    CombinedReturnObject evaluate(String exp) throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        return axiome();
    }

    CombinedReturnObject axiome() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        if (isFunction(actualSymbol())) {
            ret = formula();
            if (actualSymbol().type() != SymboleTypes.SHARP) {
                System.out.println("FNCERROR!-" + actualSymbol());
            }
        } else {
            System.out.println("FNCError ! - " + actualSymbol());
        }
        return ret;
    }

    CombinedReturnObject formula() throws EvaluatorException {
        CombinedReturnObject ret = new CombinedReturnObject(0.);
        if (isFunction(actualSymbol())) {
            Symbol fnc = actualSymbol();
            if (FUNCTIONS.containsKey(fnc.value().stringValue())) {
                nextSymbol();
                if (actualSymbol().type() == SymboleTypes.OPENEDPARENTHESIS) {
                    nextSymbol();
                    FunctionAsAttribute ff = new FunctionAsAttribute(fnc, FUNCTIONS.get(fnc.value().stringValue()));

                    if (ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(actualSymbol()) || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                        ff = listeExpressions(ff);
                    }

                    if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                        if (ff.numberOfArgsRespected()) {
                            if (ff.functionIdentifier.isIntegerFunction()) {

                                if (ff.allArgumentsAreInteger()) {
                                    ret = ff.functionIdentifier.execute(ff.listOfCombined);
                                } else {
                                    throw new NotValidTypeOfArgumentInteger(ff.functionSymbol);
                                }
                            } else {
                                if (ff.functionIdentifier.isRealPositiveFunction()) {
                                    if (ff.allArgumentsAreRealPostif()) {
                                        ret = ff.functionIdentifier.execute(ff.listOfCombined);
                                    }
                                    else {
                                        throw new NotValidTypeOfArgumentPostifReal(ff.functionSymbol);
                                    }
                                } else {
                                    ret = ff.functionIdentifier.execute(ff.listOfCombined);
                                }
                            }

                            if ((ff.getFunctionSymbol().stringValue().compareTo("rand") == 0) && ff.getFunctionSymbol().getPosition() == 1) {
                                eAE.evaluator.setActualEvaluationCellSpecialValue(true);
                            } else {
                                eAE.evaluator.setActualEvaluationCellSpecialValue(false);
                            }

                            nextSymbol(); //favoriser le - et pas un double
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

    //new with cellule : cellule//
    FunctionAsAttribute listeExpressions(FunctionAsAttribute ff) throws EvaluatorException {
        FunctionAsAttribute ret = ff;
        if (ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(actualSymbol()) /*&& lookafterSymbol().type() != SymboleTypes.DOUBLEPOINT*/) {
            CombinedReturnObject recNum = eAE.expression();
            //----addNew

            ret.listOfCombined.addAll(recNum.asArrayList());
            //----addNew
            //----Ancient
            //ret.listOfCombined.add(recNum);
            //----Ancient

            if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                ret = listeExpression2(ret);
            } else {
                throw new UnexpectedSymbolExceptionExpectedAnotherArgumentOrAClosedParenthesis(actualSymbol());
            }
        } /*else if (((actualSymbol().type() == SymboleTypes.VARIABLE) && (lookafterSymbol().type() == SymboleTypes.DOUBLEPOINT))) {
         Symbol varsymb = actualSymbol();
         nextSymbol();
         nextSymbol();
         if (actualSymbol().type() == SymboleTypes.VARIABLE) {
         Symbol varsymb2 = actualSymbol();
         nextSymbol();
         ff.listOfCombined = eAE.myEvaluator().getVariableFamilyValues(varsymb.stringValue(), varsymb2.stringValue());
         } else {
         System.out.println("Expected a variable and found - " + actualSymbol().toString());
         throw new UnexpectedSymbolException(actualSymbol());
         }
         }*/ else if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
            ret = ff;
        } else {

            //System.out.println("FNCError ! - " + actualSymbol());
            throw new BadParenthesizedExpectedClosedException(actualSymbol());
        }
        return ret;
    }
    //new with without cellule : cellule//

    /*
     //ancient without cellule : cellule//
     FunctionFormula listeExpressions(FunctionFormula ff) {
     FunctionFormula ret = ff;
     if (ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(actualSymbol())) {
     CombinedReturnObject recNum = ExpressionEvaluator.aee.expression();
     ret.listOfCombined.add(recNum);
     if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
     ret = listeExpression2(ret);
     } else {
     System.out.println("FNCError ! - " + tc);
     }
     } else if (actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
     ret = ff;
     } else {
     System.out.println("FNCError ! - " + tc);
     }
     return ret;
     }
     //ancient without cellule : cellule//
     */
    FunctionAsAttribute listeExpression2(FunctionAsAttribute ff) throws EvaluatorException {
        FunctionAsAttribute ret = ff;
        if (actualSymbol().type() == SymboleTypes.COMMA) {
            nextSymbol();
            if (ArithmeticExpressionEvaluator.isStartOfArithmeticExpression(actualSymbol())) {
                CombinedReturnObject recNum = eAE.expression();

                //----addNew
                ret.listOfCombined.addAll(recNum.asArrayList());
                //----addNew
                //----ancient
                //ret.listOfCombined.add(recNum);
                //----ancient
                if (ff.numberOfArgsStillRespected()) {
                    if (actualSymbol().type() == SymboleTypes.COMMA || actualSymbol().type() == SymboleTypes.CLOSEDPARENTHESIS) {
                        ret = listeExpression2(ret);
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

    /*String recognizeFunction(String function){
     String ret;
     Pattern pattern = Pattern.compile(REGEXP_FUNCTIONS);
     Matcher matcher = pattern.matcher(function);
     if (matcher.find()) {
     ret = matcher.group().toLowerCase();
     ret = ret.substring(0,ret.length()-1);
     if (FUNCTIONS.containsKey(ret)) {
     tc += matcher.end()-1; //pour revenir avant la parenthése
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
     }*/
    /*static boolean isStartOfFunction(char x) {
     return ENUM_STARTOF_FUNCTIONS.contains(String.valueOf(x));
     }*/

    /*static boolean isFunction(Symbol symbol) {
     return (symbol.type() == SymboleTypes.COS) || (symbol.type() == SymboleTypes.MAX)
     || (symbol.type() == SymboleTypes.SIN) || (symbol.type() == SymboleTypes.MIN);
     }*/
    static boolean isFunction(Symbol symbol) {
        return (symbol.type() == SymboleTypes.ARITHMETICFUNCTION) || (symbol.type() == SymboleTypes.FUNCTION);
    }

    Symbol actualSymbol() {
        return eAE.actualSymbol();
    }

    /*Symbol lookafterSymbol() {
     return eAE.lookafterSymbol();
     }*/
    Symbol nextSymbol() throws EvaluatorException {
        return eAE.nextSymbol();
    }

    /*Symbol nextOneSymbol() throws EvaluatorException {
     return eAE.nextOneSymbol();
     }*/
}
