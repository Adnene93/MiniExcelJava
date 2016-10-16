/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import evaluator.Function.FunctionType;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
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
public class Evaluator implements Serializable {
    
    NumberFormat formatter = new DecimalFormat("#.######");
    
    private GeneralExpressionEvaluator eGE;
    private ArithmeticExpressionEvaluator eAE;
    private LogicExpressionEvaluator eLE;
    private LexicalAnalyser myLexicalAnalyser;
    private TreeMap<String, Cell> TableOfCells;
    private Cell actualEvaluatingCell;
    private Cell selectedCell;
    private String currentExpression;
    private String currentExpressionWithoutTreatment;
    private EvaluatorException lastError;

    public static ArrayList<String> STRINGONVARIABLENAME;

    public Evaluator() {
        this.eGE = new GeneralExpressionEvaluator(this);
        this.eAE = new ArithmeticExpressionEvaluator(this);
        this.eLE = new LogicExpressionEvaluator(this);
        this.myLexicalAnalyser = new LexicalAnalyser(this);
        TableOfCells = new TreeMap<String, Cell>();
        initStringOnVariableName();
        

    }

    CombinedReturnObject evaluate(String expression) {
        this.currentExpressionWithoutTreatment = expression;
        this.currentExpression = /*expression.toLowerCase().replaceAll("[ \t\n]", "")*/ expression.toLowerCase() + "#";

        this.myLexicalAnalyser.startEvaluation(currentExpression);
        CombinedReturnObject ret = new CombinedReturnObject("");
        try {
            ret = eGE.generalaxiome();
            if (actualEvaluatingCell != null) {
                this.actualEvaluatingCell.setIsExpression(true);
                this.actualEvaluatingCell.setError(null);
            }
            //lassstt ADDED//

        } catch (EvaluatorException e) {
            lastError = e;

            //System.out.println(lastError.getMessage());
            if (actualEvaluatingCell != null) {
                this.actualEvaluatingCell.setError(lastError);
                if (e instanceof IsNotExpressionException) {
                    this.actualEvaluatingCell.setIsExpression(false);

                    ret = new CombinedReturnObject(currentExpressionWithoutTreatment);
                } else {
                    this.actualEvaluatingCell.setIsExpression(true);
                    ret.setErrorInExpression(true);
                    //System.out.println(selectedCell.getName()+" :1 " + selectedCell.isErrorInExpression());

                }
            }

        }
        return ret;
    }

    public void putVariable(String nameVariable, String expression) throws UnrecognizedFunctionException {
        Cell cell = getVariable(nameVariable);
        cell.evaluate(expression);
        //TableOfCells.put(nameVariable, cell);
        //System.out.println(nameVariable+":"+TableOfCells.get(nameVariable).getExpression());
        /*TableOfCells.forEach((String t, Cell u) -> {
         if (t.compareTo(nameVariable)!=0) {
         if(!u.isEmpty()) {
         System.out.println("Remembering "+t+"....");
         TableOfCells.get(t).evaluate(u.getExpression());
         }
                
                
         //System.out.println(t+": "+u.getExpression());
         //System.out.println(t+":"+u.getExpression());
         }
         });*/

    }

    public Cell getVariable(String nameVariable) {
        Cell ret;
        if (TableOfCells.containsKey(nameVariable)) {
            ret = TableOfCells.get(nameVariable);
        } else {
            TableOfCells.put(nameVariable, new Cell(nameVariable, this));
            ret = TableOfCells.get(nameVariable);
        }
        if (actualEvaluatingCell != null) {
            if (ret.getName().compareTo(actualEvaluatingCell.getName()) != 0) {

                if (actualEvaluatingCell.fullTreeOfListners().containsKey(ret.getName())) {
                    System.out.println("Expression in " + actualEvaluatingCell.getName() + " generated indirect recursive reference");

                    actualEvaluatingCell.setContainRecursiveReference(true);

                } else {
                    //ret.addListner(selectedCell);

                }
                ret.addListner(actualEvaluatingCell);
                actualEvaluatingCell.addInformer(ret);
            } else {
                System.out.println("Expression in " + actualEvaluatingCell.getName() + " contain direct recursive reference");
                actualEvaluatingCell.setContainRecursiveReference(true);
                ret.addListner(actualEvaluatingCell);
                actualEvaluatingCell.addInformer(ret);
            }

        }
        return ret;
    }

    public Cell selectCell(String nameCell) {
        Cell ret = getVariable(nameCell);
        selectedCell = ret;
        //getVariable(nameCell).evaluate(ret.getExpression());
        //selectedCell=ret;
        return ret;
    }

    public CombinedReturnObject getVariableValue(String nameVariable) {
        return getVariable(nameVariable).getValue();
    }

    public ArrayList<CombinedReturnObject> getVariableFamilyValues(String nameVariable1, String nameVariable2) {
        ArrayList<CombinedReturnObject> ret = new ArrayList<CombinedReturnObject>();
        ArrayList<String> recupFamily = variableNameFamily(nameVariable1, nameVariable2);
        for (String nameV : recupFamily) {
            ret.add(getVariableValue(nameV));
        }
        return ret;
    }

    Symbol actualSymbol() {
        return myLexicalAnalyser.actualSymbol();
    }

    /*
     Symbol lookafterSymbol() {
     return myLexicalAnalyser.lookafterSymbol();
     }*/
    Symbol nextSymbol() throws EvaluatorException {
        return myLexicalAnalyser.nextSymbol();
    }

    /*Symbol nextOneSymbol() throws EvaluatorException {
     return myLexicalAnalyser.nextSymbolOneCar();
     }*/
    public void setActualEvaluatingCell(Cell actualEvaluatingCell) {
        this.actualEvaluatingCell = actualEvaluatingCell;
    }

    private void initStringOnVariableName() {
        STRINGONVARIABLENAME = new ArrayList<String>();
        String alphabet = "abcdefghijklmnopqrstuvwxyz";
        for (int i = 0; i < 26; i++) {
            STRINGONVARIABLENAME.add(alphabet.charAt(i) + "");
        }
        for (int i = 0; i < 26; i++) {
            for (int j = 0; j < 26; j++) {
                STRINGONVARIABLENAME.add(String.valueOf(alphabet.charAt(i)) + String.valueOf(alphabet.charAt(j)) + "");
            }
        }
    }

    public ArrayList<String> variableNameFamily(String nameVariable1, String nameVariable2) {
        ArrayList<String> ret = new ArrayList<>();
        String permute;
        if (nameVariable1.compareTo(nameVariable2) > 0) {
            permute = nameVariable1;
            nameVariable1 = nameVariable2;
            nameVariable2 = permute;
        }
        String partchar1 = "", partnum1 = "";
        String partchar2 = "", partnum2 = "";
        Pattern pattern = Pattern.compile("[a-z][a-z]?");
        Matcher matcher = pattern.matcher(nameVariable1);
        if (matcher.find()) {
            partchar1 = matcher.group();
            partnum1 = nameVariable1.substring(matcher.end());
        }
        matcher = pattern.matcher(nameVariable2);
        if (matcher.find()) {
            partchar2 = matcher.group();
            partnum2 = nameVariable2.substring(matcher.end());
        }

        String startchar = partchar1;
        String endchar = partchar2;
        int startnum = Integer.valueOf(partnum1);
        int endnum = Integer.valueOf(partnum2);
        int startcharpos = STRINGONVARIABLENAME.indexOf(startchar);
        int endcharpos = STRINGONVARIABLENAME.indexOf(endchar);

        int permutenum;
        if (startnum > endnum) {
            permutenum = startnum;
            startnum = endnum;
            endnum = permutenum;
        }

        for (int i = startcharpos; i <= endcharpos; i++) {
            for (int j = startnum; j <= endnum; j++) {
                ret.add(STRINGONVARIABLENAME.get(i) + String.valueOf(j));
            }
        }

        return ret;
    }

    public ArrayList<Integer> variableFamilyDimensions(String nameVariable1, String nameVariable2) {
        ArrayList<Integer> ret = new ArrayList<>();
        String permute;
        if (nameVariable1.compareTo(nameVariable2) > 0) {
            permute = nameVariable1;
            nameVariable1 = nameVariable2;
            nameVariable2 = permute;
        }
        String partchar1 = "", partnum1 = "";
        String partchar2 = "", partnum2 = "";
        Pattern pattern = Pattern.compile("[a-z][a-z]?");
        Matcher matcher = pattern.matcher(nameVariable1);
        if (matcher.find()) {
            partchar1 = matcher.group();
            partnum1 = nameVariable1.substring(matcher.end());
        }
        matcher = pattern.matcher(nameVariable2);
        if (matcher.find()) {
            partchar2 = matcher.group();
            partnum2 = nameVariable2.substring(matcher.end());
        }

        String startchar = partchar1;
        String endchar = partchar2;
        int startnum = Integer.valueOf(partnum1);
        int endnum = Integer.valueOf(partnum2);
        int startcharpos = STRINGONVARIABLENAME.indexOf(startchar);
        int endcharpos = STRINGONVARIABLENAME.indexOf(endchar);

        int permutenum;
        if (startnum > endnum) {
            permutenum = startnum;
            startnum = endnum;
            endnum = permutenum;
        }

        int n = endnum - startnum + 1;

        int m = endcharpos - startcharpos + 1;

        ret.add(m);
        ret.add(n);

        return ret;
    }

    public ArrayList<Integer> getVariableIndexFromName(String nameVariable) {
        ArrayList<Integer> ret = new ArrayList<>();
        String permute;

        String partchar1 = "", partnum1 = "";
        Pattern pattern = Pattern.compile("[a-z][a-z]?");
        Matcher matcher = pattern.matcher(nameVariable);
        if (matcher.find()) {
            partchar1 = matcher.group();
            partnum1 = nameVariable.substring(matcher.end());
        }

        String startchar = partchar1;
        int startnum = Integer.valueOf(partnum1);
        int startcharpos = STRINGONVARIABLENAME.indexOf(startchar);

        int n = startnum - 1;

        int m = startcharpos;

        //ret.add(n);
        //ret.add(m);
        ret.add(m);
        ret.add(n);

        return ret;
    }

    GeneralExpressionEvaluator myAttachedGeneralExpressionEvaluator() {
        return eGE;
    }

    LogicExpressionEvaluator myAttachedLogicExpressionEvaluator() {
        return eLE;
    }

    ArithmeticExpressionEvaluator myAttachedArithmeticExpressionEvaluator() {
        return eAE;
    }

    public TreeMap<String, Cell> getTableOfCells() {
        return TableOfCells;
    }

    String getCurrentEvaluatingExpression() {
        return currentExpressionWithoutTreatment;
    }

    public Cell getSelectedCell() {
        return selectedCell;
    }

    public void save(String path) {
        ObjectOutputStream out;

        try {
            out = new ObjectOutputStream(new BufferedOutputStream(new FileOutputStream(new File(path))));
            out.writeObject(this);
            //System.out.println("Saved !");
            out.close();
        } catch (IOException ex) {
            Logger.getLogger(Evaluator.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static Evaluator load(String path) {
        ObjectInputStream in;

        Evaluator evaluator;
        try {
            in = new ObjectInputStream(new BufferedInputStream(new FileInputStream(new File(path))));
            try {
                evaluator = ((Evaluator) in.readObject());
                evaluator.initStringOnVariableName();
                in.close();
                //System.out.println("Loaded !");
                return evaluator;
            } catch (ClassNotFoundException ex) {
                return null;
                //Logger.getLogger(SauvMatch.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (IOException ex) {
            return null;
            //Logger.getLogger(SauvMatch.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String getCellName(int i, int j) {
        String ret;
        //ret = Evaluator.STRINGONVARIABLENAME.get(j) + String.valueOf(i + 1);
        ret = Evaluator.STRINGONVARIABLENAME.get(i) + String.valueOf(j + 1);
        return ret;
    }

    static public ArrayList<String> getnbStringFromVariableNames(int n) {
        ArrayList<String> ret = new ArrayList<>();
        for (int i = 0; i < n; i++) {
            ret.add(STRINGONVARIABLENAME.get(i));
        }
        return ret;
    }

    public String getCurrentExpression() {
        return currentExpression;
    }

    public ArrayList<String> getArrayOfFunctions() {
        ArrayList<String> returned = new ArrayList<>();
        ArrayList<String> arithmeticFunction = new ArrayList<>(eAE.getTableOfFunction().keySet());
        ArrayList<String> logicalFunction = new ArrayList<>(eLE.getTableOfFunction().keySet());

        for (int i = 0; i < arithmeticFunction.size(); i++) {
            returned.add(arithmeticFunction.get(i));
        }
        for (int i = 0; i < logicalFunction.size(); i++) {
            returned.add(logicalFunction.get(i));
        }
        return returned;
    }

    public ArrayList<String> getArithmeticFunction() {
        ArrayList<String> returned = new ArrayList<>();
        ArrayList<String> arithmeticFunction = new ArrayList<>(eAE.getTableOfFunction().keySet());
        TreeMap<String, Function> arithmeticHashMap = eAE.getTableOfFunction();
        for (int i = 0; i < arithmeticFunction.size(); i++) {
            if (arithmeticHashMap.get(arithmeticFunction.get(i)).getTypeOfFunction() == FunctionType.ARITHMETIC) {
                returned.add(arithmeticFunction.get(i));
            }
        }
        return returned;

    }

    public TreeMap<String, Function> getArithmeticFunctionTree() {
        TreeMap<String, Function> returned = new TreeMap<>();
        ArrayList<String> arithmeticFunction = new ArrayList<>(eAE.getTableOfFunction().keySet());
        TreeMap<String, Function> arithmeticHashMap = eAE.getTableOfFunction();
        for (int i = 0; i < arithmeticFunction.size(); i++) {
            if (arithmeticHashMap.get(arithmeticFunction.get(i)).getTypeOfFunction() == FunctionType.ARITHMETIC) {
                returned.put(arithmeticFunction.get(i), arithmeticHashMap.get(arithmeticFunction.get(i)));
            }
        }
        return returned;
    }

    public ArrayList<String> getStatisticFunction() {
        ArrayList<String> returned = new ArrayList<>();
        ArrayList<String> arithmeticFunction = new ArrayList<>(eAE.getTableOfFunction().keySet());
        TreeMap<String, Function> arithmeticHashMap = eAE.getTableOfFunction();
        for (int i = 0; i < arithmeticFunction.size(); i++) {
            if (arithmeticHashMap.get(arithmeticFunction.get(i)).getTypeOfFunction() == FunctionType.STATISTIC) {
                returned.add(arithmeticFunction.get(i));
            }
        }
        return returned;
    }

    public TreeMap<String, Function> getStatisticFunctionTree() {
        TreeMap<String, Function> returned = new TreeMap<>();
        ArrayList<String> arithmeticFunction = new ArrayList<>(eAE.getTableOfFunction().keySet());
        TreeMap<String, Function> arithmeticHashMap = eAE.getTableOfFunction();
        for (int i = 0; i < arithmeticFunction.size(); i++) {
            if (arithmeticHashMap.get(arithmeticFunction.get(i)).getTypeOfFunction() == FunctionType.STATISTIC) {
                returned.put(arithmeticFunction.get(i), arithmeticHashMap.get(arithmeticFunction.get(i)));
            }
        }
        return returned;
    }

    public ArrayList<String> getLogicalFunction() {
        ArrayList<String> returned = new ArrayList<>();
        ArrayList<String> logicalFunction = new ArrayList<>(eLE.getTableOfFunction().keySet());
        TreeMap<String, Function> logicalHashMap = eLE.getTableOfFunction();
        for (int i = 0; i < logicalFunction.size(); i++) {
            if (logicalHashMap.get(logicalFunction.get(i)).getTypeOfFunction() == FunctionType.LOGIC) {
                returned.add(logicalFunction.get(i));
            }
        }
        return returned;
    }

    ArrayList<ArrayList<String>> TableDeTable;

    public TreeMap<String, Function> getLogicalFunctionTree() {

        TreeMap<String, Function> returned = new TreeMap<>();
        ArrayList<String> logicalFunction = new ArrayList<>(eLE.getTableOfFunction().keySet());
        TreeMap<String, Function> logicalHashMap = eLE.getTableOfFunction();
        for (int i = 0; i < logicalFunction.size(); i++) {
            if (logicalHashMap.get(logicalFunction.get(i)).getTypeOfFunction() == FunctionType.LOGIC) {
                returned.put(logicalFunction.get(i), logicalHashMap.get(logicalFunction.get(i)));
            }
        }

        ArrayList<String> logicalFunction2 = new ArrayList<>(eLE.getTableOfSpecFunction().keySet());
        TreeMap<String, Function> logicalHashMap2 = eLE.getTableOfSpecFunction();
        for (int i = 0; i < logicalFunction2.size(); i++) {
            if (logicalHashMap2.get(logicalFunction2.get(i)).getTypeOfFunction() == FunctionType.LOGIC) {
                returned.put(logicalFunction2.get(i), logicalHashMap2.get(logicalFunction2.get(i)));
            }
        }
        return returned;
    }

    public Cell getActualEvaluatingCell() {
        return actualEvaluatingCell;
    }
    
    public void setActualEvaluationCellSpecialValue(boolean isSpecValue){
        if (actualEvaluatingCell!=null) actualEvaluatingCell.setIsSpecialValueCell(isSpecValue);
    }

    public LexicalAnalyser getMyLexicalAnalyser() {
        return myLexicalAnalyser;
    }
    
    
    

}
