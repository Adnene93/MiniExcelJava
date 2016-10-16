/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package evaluator;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author Hatem
 */
public class SampleCompil extends Application {

    @Override
    public void start(Stage primaryStage) {
        Button btn = new Button();
        btn.setText("Say 'Hello World'");
        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent event) {
                System.out.println("Hello World!");
            }
        });

        StackPane root = new StackPane();
        root.getChildren().add(btn);

        Scene scene = new Scene(root, 300, 250);

        primaryStage.setTitle("Hello World!");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        /*String test="11";
         Pattern pattern = Pattern.compile("\\d");
         // in case you would like to ignore case sensitivity,
         // you could use this statement:
         // Pattern pattern = Pattern.compile("\\s+", Pattern.CASE_INSENSITIVE);
         Matcher matcher = pattern.matcher("This is my 00 small example string 6 which I'm going to use for pattern matching.");
         // check all occurance
         while (matcher.find()) {
         System.out.print("Start index: " + matcher.start());
         System.out.print(" End index: " + matcher.end() + " ");
         System.out.println(matcher.group());
         }*/
        /*
         String texte = "hello (i m adnane,asdsaz)";
         String reste = texte.substring(5);
         System.out.println(reste.indexOf(';'));
         reste = reste.substring(0, reste.indexOf(','));
         System.out.println(reste);
         */
        /*
         ArithmeticExpressionEvaluator aee = new ArithmeticExpressionEvaluator();
         System.out.println(String.valueOf(aee.evaluate("-3,")));
         */

        //FunctionEvaluator fe = new FunctionEvaluator();
        //GeneralExpressionEvaluator evaluator = new GeneralExpressionEvaluator();
        //GeneralExpressionEvaluator.ee=evaluator;
        //System.out.println(evaluator.evaluateBoolean("et(non(false),true,max(3,4.1)<=4)"));
        //System.out.println(evaluator.evaluateBoolean("(si((max(2,2.9999)>=3),3,1))").doubleValue());
        /*System.out.println(evaluator.evaluate("si(12+(2*max(2,3*(4-2))>=12)>2,3+5,2+5)>7").stringValue());*/
        //System.out.println(evaluator.evaluate("true+(si(3>4,3,max(3,4))+max(3,cos(4)))+1").stringValue());
        //System.out.println(evaluator.evaluate("3+4").stringValue());
        //System.out.println(evaluator.evaluate("a3+a4").stringValue());    
        //evaluator.putVariable("a3", "3+4a");
        //evaluator.putVariable("a4", "max(2,3)");
        //evaluator.putVariable("a5", "max(a3,a4)+1");
        /*evaluator.putVariable("a3", "=3");
         //System.out.println(evaluator.VARIABLES.get("a3").getValue().stringValue());
         evaluator.putVariable("a2", "=5+a3");
         System.out.println(evaluator.getVariable("a2").getValue().stringValue());
         evaluator.putVariable("a3", "=max(5,4.2)");
         System.out.println(evaluator.getVariable("a2").getValue().stringValue());
         */
        //evaluator.putVariable("a1", "=randof(a2,a3)");
        //evaluator.putVariable("a2", "=true");
        //evaluator.putVariable("a3", "=true");
        //System.out.println(evaluator.getVariable("a2").getMyListners().size());
        /*evaluator.putVariable("a4", "=true");
         evaluator.putVariable("a5", "=true");
         evaluator.putVariable("a6", "=et(a3:a5)");
         *///System.out.println(evaluator.getVariable("a1").getValue());
        /*evaluator.putVariable("a5", "=false");
         System.out.println(evaluator.getVariable("a6").getValue());
         */
        Evaluator evaluator = new Evaluator();

        /*evaluator.putVariable("a1", "=si(3+a2-a3>=8,a4,a5)");
         System.out.println("------------");
         evaluator.putVariable("a2", "=max(1,4)");
         evaluator.putVariable("a3", "=min(-1,2)");
         evaluator.putVariable("a4", "Happy");
         evaluator.putVariable("a5", "Sad");
         System.out.println("a1 = "+evaluator.getVariable("a1"));*/
        //System.out.println(evaluator.evaluate("3"));
        //System.out.println(evaluator.evaluate("=3+5"));
        //evaluator.getVariable("b2").evaluate("=a5");
        //Good sample of recursive refernce
        /*evaluator.selectCell("a5").evaluate("=a1");
         evaluator.selectCell("a1").evaluate("=somme(A2:A4)");
         evaluator.selectCell("b1").evaluate("=a2");
         evaluator.selectCell("a2").evaluate("=3");
        
         evaluator.selectCell("a3").evaluate("=4");
         evaluator.selectCell("a4").evaluate("=a5");
        
         evaluator.selectCell("a5").evaluate("=12");*/
        //evaluator.getVariable("a5").evaluate("=12");
        //evaluator.getVariable("a5").evaluate("=a1");
        //evaluator.getVariable("a1").evaluate("=a2+a4");
        //evaluator.getVariable("a4").evaluate("=a5");
        //evaluator.selectCell("a5").evaluate("=3");
        //evaluator.selectCell("a1").evaluate("=a2");
        //evaluator.selectCell("a2").evaluate("=a3");
        //evaluator.selectCell("a3").evaluate("=a2");
        //System.out.println("------------");
        //evaluator.selectCell("a3").evaluate("=max(3,4)+13");
        //evaluator.selectCell("a1").evaluate("=max(3,4)");
        //evaluator.selectCell("a2").evaluate("=3+4");
        //evaluator.getVariable("a5").evaluate("=3");
        //evaluator.selectCell("a1").evaluate("adnane");
        //System.out.println(evaluator.selectCell("a2").myListners());
        //evaluator.selectCell("b1").evaluate("=si(b2,max(10,11),min(1,2))");
        /*evaluator.selectCell("b1").evaluate("=max(3,1,4,si(b2,max(10,11),min(1,2)))");
         evaluator.selectCell("b2").evaluate("adane");
         evaluator.selectCell("a1").evaluate("=a2  +   (3*max(3,2)>4)+     si(3>4,1,2)-max(-5,-6)");
         evaluator.selectCell("a2").evaluate("adnane");
         evaluator.selectCell("a2").evaluate("3");
         System.out.println(evaluator.selectCell("b1")+":"+evaluator.selectCell("b1").isValue());  */
        //evaluator.selectCell("a2").evaluate("-8");
        //evaluator.selectCell("a2").evaluate("3");
        //evaluator.selectCell("b1").evaluate("=si(5>1,max(10,11),min(1,2))");
        //evaluator.selectCell("b2").evaluate("=(3+6--1+min(3,-11))*3/2");
        //evaluator.selectCell("a1").evaluate("=3+max(4,5)");
        /*evaluator.selectCell("z1").evaluate("=a1+3");

         evaluator.selectCell("a2").evaluate("=7");
         evaluator.selectCell("b2").evaluate("=3");
         evaluator.selectCell("a3").evaluate("=adnane()");
         evaluator.selectCell("b3").evaluate("=4");

         evaluator.selectCell("a4").evaluate("=1");
         evaluator.selectCell("b4").evaluate("=3");
         evaluator.selectCell("a5").evaluate("=2");
         evaluator.selectCell("b5").evaluate("=4");

         //evaluator.selectCell("a1").evaluate("=max(a2:+a5,a2:_a5,a2:^a5)");
         evaluator.selectCell("a1").evaluate("=.a");
         evaluator.selectCell("b8").evaluate("=a1");
         evaluator.selectCell("b8");
         //evaluator.selectCell("c1").evaluate("=a1");
         evaluator.selectCell("c2").evaluate("=variance(2,3,4,5,6,7)");
         evaluator.selectCell("c3").evaluate("=1");
         evaluator.selectCell("c4").evaluate("adnane");
         evaluator.selectCell("c2");
         */
        System.out.println(evaluator.selectCell("a1").evaluate("=-(a2:a3+2)").getMatrix());
        
        /*double[][] mat  = {{7,3},{1,4}};
         System.out.println(Matrix.invertDouble(mat)[1][0]);*/
    //System.out.println(evaluator.evaluate("=3+max(3,4)+-2"));
        //System.out.println(evaluator.selectCell("b1"));
        //System.out.println(evaluator.evaluate("=3+4+max(3,4)--3-3"));
//evaluator.getVariable("a5").evaluate("6");
        //evaluator.getVariable("a6").evaluate("7");
        /*System.out.println("----------------TEST------------");
         String s = "  a\t";
         String x=s.replaceAll("[ \t\n]", "");
         System.out.println(s+":"+s.length());
         System.out.println(x+":"+x.length());
         */
        //System.out.println(evaluator.evaluate("=six(3>4,3,a3)").stringValue());    
        //---------------
        /*
         ExpressionEvaluator.expression="=a3+4#";
         ExpressionEvaluator.tc=0;
         Symbol ret=new Symbol("");
         int i=1;
         while ((ret=evaluator.nextOneSymbol()).type()!=SymboleTypes.UKNOWN){
         System.out.println(i+":"+evaluator.actualSymbol().value().stringValue()+"-"+evaluator.actualSymbol().type().toString());
         System.out.println("next:"+evaluator.lookafterSymbol());
         i++;
         }
         System.out.println(i+":"+ret.value().stringValue()+"-"+ret.type().toString());
         System.out.println("-------------------");
         //System.out.println("aa1".compareTo("aa2"));
         System.out.println(evaluator.variableNameFamily("c11", "a21").toString());
         //ret=evaluator.nextSymbol();
         //System.out.println(ret.value().stringValue()+"-"+ret.type().toString());
         */
        //--------------
        //System.out.println("-1".matches(ArithmeticExpressionEvaluator.REGULAREXP_DOUBLE));
        /*
         double d=3.21;
         long i;
         i=(long) d;
         System.out.println(""+i);
         */
        //SpecialFunctionEvaluator sfe = new SpecialFunctionEvaluator();
        //System.out.println(sfe.evaluate("si(3>4,3,4)").seeAs());
        /*CombinedReturnObject c = new CombinedReturnObject(true);
         System.out.println(c.booleanValue());
         */
        /*
         String text="az00xx";
         Pattern pattern = Pattern.compile(LexicalAnalyser.REGEXP_VARIABLES);
         Matcher matcher = pattern.matcher(text);
         String ret="";
         if (matcher.find()){
         ret = matcher.group();
         System.out.println(ret+" ---"+"("+matcher.start()+","+matcher.end()+")");
         }
         //ret=ret.substring(0, ret.length()-1);
         */
        //System.out.println(evaluator.expression);
        //System.out.println("true".matches("true"));
        //System.out.println(evaluator.evaluate("(cos(3))"));
        //launch(args);
    }

}
