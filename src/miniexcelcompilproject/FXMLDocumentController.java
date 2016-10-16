/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniexcelcompilproject;

import com.sun.javafx.beans.event.AbstractNotifyListener;
import evaluator.*;
import evaluator.Function.FunctionType;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.TreeMap;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCharacterCombination;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.util.Duration;
import org.controlsfx.control.Notifications;
import org.controlsfx.control.action.Action;
import org.controlsfx.control.spreadsheet.GridBase;
import org.controlsfx.control.spreadsheet.SpreadsheetCell;
import org.controlsfx.control.spreadsheet.SpreadsheetCellBase;
import org.controlsfx.control.spreadsheet.SpreadsheetView;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Hatem
 */
public class FXMLDocumentController implements Initializable {

    /*@FXML
     private Label label;
     @FXML
     private Label LabelExpressionCell;
     @FXML
     private Label LabelExpressionValue;*/
    @FXML
    MenuButton menuButtonFunctions;
    @FXML
    Label labelFunctions;
    @FXML
    Button ChoiceFonctionButton;
    @FXML
    public TextField EditTextFormula;
    @FXML
    public ChoiceBox ArithmeticChoiceBoxFunction;
    @FXML
    public ChoiceBox StatisticChoiceBoxFunction;
    @FXML
    public ChoiceBox LogicalChoiceBoxFunction;

    @FXML
    public MenuItem menuItemNewFile;
    @FXML
    public MenuItem menuItemSave;
    @FXML
    public MenuItem menuItemSaveAs;
    @FXML
    public MenuItem menuItemLoad;
    @FXML
    public MenuItem menuItemExit;
    @FXML
    public MenuItem menuItemCopy;
    @FXML
    public MenuItem menuItemPaste;
    @FXML
    public MenuItem menuItemCut;
    @FXML
    public MenuItem menuItemDelete;
    @FXML
    public MenuItem menuItemShowError;
    @FXML
    public MenuItem menuItemExpandMatrix;
    @FXML
    public MenuItem menuItemUndo;
    @FXML
    public MenuItem menuItemRedo;
    @FXML
    public MenuItem menuItemInsertFonction;
    @FXML
    public SpreadsheetView previewSheet;
    @FXML
    private Pane xlPreviewPane;
    @FXML
    public MenuItem menuItemRefresh;

    @FXML
    public Button newFileButton;
    @FXML
    public Button loadFileButton;
    @FXML
    public Button saveButton;
    @FXML
    public Button saveAsButton;

    @FXML
    public Button undoButton;
    @FXML
    public Button redoButton;

    @FXML
    public Button copyButton;
    @FXML
    public Button cutButton;
    @FXML
    public Button pasteButton;
    @FXML
    public Button deleteButton;

    @FXML
    public Button showErrorButton;
    @FXML
    public Button refreshButton;
    @FXML
    public Button expandMatrixButton;

    @FXML
    public Label labelSelectedCell;
    @FXML
    public Label labelValue;
    @FXML
    public Label labelIsMatrix;
    @FXML
    public Label labelError;

    @FXML
    public Label labelFunctionName;
    @FXML
    public Label labelFunctionType;
    @FXML
    public Label labelMinArgs;
    @FXML
    public Label labelMaxArgs;

    @FXML
    public Rectangle colorwhite;
    @FXML
    public Rectangle colorlightgreen;
    @FXML
    public Rectangle colorgreen;
    @FXML
    public Rectangle colorblue;
    @FXML
    public Rectangle colorheavyblue;
    @FXML
    public Rectangle coloryellow;
    @FXML
    public Rectangle colorred;
    @FXML
    public Rectangle colorheavyred;
    @FXML
    public Rectangle colorwisteria;
    @FXML
    public Rectangle coloramethyst;
    @FXML
    public Rectangle colorrose;
    @FXML
    public Rectangle colorblack;

    static public FXMLDocumentController controller;
    public static final String APP_TITLE = "MiniExcel V1.0";
    public MenuItem showFullErrorMessageMenuItem;
    public MenuItem expandMatrixContextMenu;
    public MenuItem cutContextMenu;
    public MenuItem deleteContextMenu;
    public static boolean change;
    public static String workingSheet = "";
    public static final int ROWCOUNT = /*50*/ 50;
    public static final int COLUMNCOUNT = /*26*/ 50;
    public static final int PREFCELLWIDTH = 90;

    static Evaluator evaluator;
    public static UndoRedoController undoredoController;
    public static boolean goForward;
    public static ExpandedDataHistory expandedDataHistory;

    public static final String CSS_ERROR = "error";
    public static final String CSS_NOVALUE = "novalue";

    public static final String CSS_GREEN = "green";
    public static final String CSS_LIGHTGREEN = "lightgreen";
    public static final String CSS_BLUE = "blue";
    public static final String CSS_HEAVYBLUE = "heavyblue";
    public static final String CSS_AMETHYST = "amethyst";
    public static final String CSS_WISTERIA = "wisteria";
    public static final String CSS_YELLOW = "yellow";
    public static final String CSS_BLACK = "black";
    public static final String CSS_ROSE = "rose";
    public static final String CSS_HEAVYRED = "heavyred";
    public static final String CSS_RED = "red";

    public boolean saved;

    //public static final String CSS_SPECIALVALUE = "specialValue";
    /*
     @FXML
     private void handleColorPicker(ActionEvent event) throws Exception {
     String color = colorPicker.getValue().toString().substring(2);
     System.out.println(color);
     ArrayList<Integer> indexes = evaluator.getVariableIndexFromName(evaluator.getSelectedCell().getName());
     int i = indexes.get(0);
     int j = indexes.get(1);

        
     }*/
    @Override

    public void initialize(URL url, ResourceBundle rb) {
        Evaluator loaded = null;
        saved = true;
        controller = this;
        goForward = true;
        undoredoController = new UndoRedoController();
        /*if ((loaded = Evaluator.load(workingSheet)) != null) {
         evaluator = loaded;
         } else {*/
        evaluator = new Evaluator();
        /*}*/
        change = true;
        initPreviewSheet();

        EditTextFormula.setDisable(true);
        EditTextFormula.setOnKeyReleased(keyEventHandlerFormulaET);
        /*EditTextFormula.focusedProperty().addListener(new ChangeListener<Boolean>() {

         @Override
         public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
         if (!newValue) {
         actionOfEditText();
         }
         }
         });*/
        expandedDataHistory = new ExpandedDataHistory();
        xlPreviewPane.getChildren().add(previewSheet);//.getChildren().add(previewSheet);
        menuItemNewFile.setOnAction(ActionMenuItemNewFile);
        menuItemSave.setOnAction(ActionMenuItemSave);
        menuItemSaveAs.setOnAction(ActionMenuItemSaveAs);
        menuItemLoad.setOnAction(ActionMenuItemLoad);
        menuItemExit.setOnAction(ActionMenuItemExit);
        menuItemCopy.setOnAction(ActionMenuCopy);
        menuItemPaste.setOnAction(ActionMenuPaste);
        menuItemCut.setOnAction(ActionMenuCut);
        menuItemDelete.setOnAction(ActionMenuDelete);
        menuItemExpandMatrix.setOnAction(ActionMenuExpandMatrix);
        menuItemShowError.setOnAction(ActionMenuShowError);
        menuItemCopy.setDisable(true);
        menuItemPaste.setDisable(true);
        menuItemCut.setDisable(true);
        menuItemDelete.setDisable(true);
        menuItemExpandMatrix.setDisable(true);
        menuItemShowError.setDisable(true);
        menuItemUndo.setOnAction(ActionMenuUndo);
        menuItemRedo.setOnAction(ActionMenuRedo);
        menuItemRefresh.setOnAction(ActionMenuRefresh);
        menuItemUndo.setDisable(true);
        menuItemRedo.setDisable(true);

        undoButton.setDisable(true);
        redoButton.setDisable(true);

        copyButton.setDisable(true);
        pasteButton.setDisable(true);
        cutButton.setDisable(true);
        deleteButton.setDisable(true);
        setColorButtonDisabled(true);

        showErrorButton.setOnAction(ActionMenuShowError);
        menuItemInsertFonction.setOnAction(ActionButtonInsert);

        menuItemExpandMatrix.setDisable(true);
        expandMatrixContextMenu.setDisable(true);
        expandMatrixButton.setDisable(true);
        showErrorButton.setDisable(true);

        undoredoController.index.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (undoredoController.indexRespectdBoundsForUndo()) {
                    menuItemUndo.setDisable(false);
                    undoButton.setDisable(false);
                } else {
                    menuItemUndo.setDisable(true);
                    undoButton.setDisable(true);
                }

                if (undoredoController.indexRespectdBoundsForRedo()) {
                    menuItemRedo.setDisable(false);
                    redoButton.setDisable(false);
                } else {
                    menuItemRedo.setDisable(true);
                    redoButton.setDisable(true);
                }

            }
        });

        newFileButton.setOnAction(ActionMenuItemNewFile);
        loadFileButton.setOnAction(ActionMenuItemLoad);
        saveButton.setOnAction(ActionMenuItemSave);
        saveAsButton.setOnAction(ActionMenuItemSaveAs);

        undoButton.setOnAction(ActionMenuUndo);
        redoButton.setOnAction(ActionMenuRedo);

        copyButton.setOnAction(ActionMenuCopy);
        pasteButton.setOnAction(ActionMenuPaste);
        cutButton.setOnAction(ActionMenuCut);
        deleteButton.setOnAction(ActionMenuDelete);

        showErrorButton.setOnAction(ActionMenuShowError);
        refreshButton.setOnAction(ActionMenuRefresh);
        expandMatrixButton.setOnAction(ActionMenuExpandMatrix);

        initChoiceBoxArithmetic();
        initChoiceBoxStatistic();
        initChoiceBoxLogical();
        initColorButton();

        //initChoiceBoxColor();
        ChoiceFonctionButton.setOnAction(ActionButtonInsert);

        menuItemInsertFonction.setDisable(true);
        ChoiceFonctionButton.setDisable(true);

        previewSheet.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);

        goForward = false;

        //previewSheet.getSelectionModel().selectFirst();
        updateSpreadSheetView();
        goForward = true;
        updateFunctionBar();
        // TODO
    }

    void initPreviewSheet() {

        GridBase grid = new GridBase(ROWCOUNT, COLUMNCOUNT);

        ObservableList<ObservableList<SpreadsheetCell>> rows = FXCollections.observableArrayList();

        for (int row = 0; row < grid.getRowCount(); ++row) {
            final ObservableList<SpreadsheetCell> list = FXCollections.observableArrayList();
            for (int column = 0; column < grid.getColumnCount(); ++column) {
                SpreadsheetCell cell = new SpreadsheetCellBase(row, column, 1, 1, new MySpreadSheetType(this));
                //cell.itemProperty().addListener(onChangedCellItem);

                list.add(cell);
            }
            rows.add(list);
        }

        grid.setRows(rows);

        grid.getRowHeaders().setAll(Evaluator.getnbStringFromVariableNames(ROWCOUNT));
        grid.getColumnHeaders().setAll("1");
        for (int k = 2; k <= COLUMNCOUNT; k++) {
            grid.getColumnHeaders().add(String.valueOf(k));
        }

        //System.out.println(rows.get(1).get(2).getItem());
        previewSheet = new SpreadsheetView(grid);
        //System.out.println("HELLO");
        ContextMenu contextmenu = previewSheet.getContextMenu();

        showFullErrorMessageMenuItem = new MenuItem("Show Error");
        //showFullErrorMessageMenuItem.setAccelerator((new KeyCharacterCombination(KeyCode.E.toString(), KeyCombination.CONTROL_DOWN)));
        showFullErrorMessageMenuItem.setOnAction(ActionMenuShowError);
        expandMatrixContextMenu = new MenuItem("Expand Matrix");
        //expandMatrixContextMenu.setAccelerator((new KeyCharacterCombination(KeyCode.D.toString(), KeyCombination.CONTROL_DOWN)));
        expandMatrixContextMenu.setOnAction(ActionMenuExpandMatrix);
        cutContextMenu = new MenuItem("Cut");
        cutContextMenu.setOnAction(ActionMenuCut);
        deleteContextMenu = new MenuItem("Delete");
        deleteContextMenu.setOnAction(ActionMenuDelete);
        contextmenu.getItems().get(0).setOnAction(ActionMenuCopy);
        contextmenu.getItems().get(0).setText("Copy");
        contextmenu.getItems().get(0).setAccelerator(null);
        contextmenu.getItems().get(1).setOnAction(ActionMenuPaste);
        contextmenu.getItems().get(1).setText("Paste");
        contextmenu.getItems().get(1).setAccelerator(null);
        contextmenu.getItems().remove(2);
        contextmenu.getItems().add(cutContextMenu);
        contextmenu.getItems().add(showFullErrorMessageMenuItem);
        contextmenu.getItems().add(expandMatrixContextMenu);
        contextmenu.getItems().add(deleteContextMenu);
        previewSheet.setContextMenu(contextmenu);

        //previewSheet.getEditor(SpreadsheetCellType.STRING).get().startEdit(null);
        previewSheet.setPrefHeight(445);
        previewSheet.setPrefWidth(900);
        for (int i = 0; i < previewSheet.getColumns().size(); i++) {
            previewSheet.getColumns().get(i).setPrefWidth(PREFCELLWIDTH);

        }

        previewSheet.getSelectionModel().getSelectedCells().addListener(onSelectionChange);

    }

    private final ListChangeListener<TablePosition> onSelectionChange = new ListChangeListener<TablePosition>() {

        @Override
        public void onChanged(ListChangeListener.Change<? extends TablePosition> c) {
            //c.next();
            if (EditTextFormula.isDisabled()) {
                EditTextFormula.setDisable(false);
            }
            if (menuItemCopy.isDisable()) {
                menuItemCopy.setDisable(false);
                menuItemPaste.setDisable(false);
                menuItemCut.setDisable(false);
                menuItemDelete.setDisable(false);
                menuItemShowError.setDisable(false);
                menuItemExpandMatrix.setDisable(false);
                menuItemInsertFonction.setDisable(false);
                ChoiceFonctionButton.setDisable(false);
                setColorButtonDisabled(false);
                copyButton.setDisable(false);
                pasteButton.setDisable(false);
                cutButton.setDisable(false);
                deleteButton.setDisable(false);
                expandMatrixButton.setDisable(false);
                showErrorButton.setDisable(false);

            }
            //System.out.println(c.getList().toArray().length);
            if (c.getList().toArray().length > 0) {
                int i = c.getList().get(0).getRow();
                int j = c.getList().get(0).getColumn();
                String name = getCellName(i, j);
                Cell cell = evaluator.selectCell(name);
                SpreadsheetCell spvcell = previewSheet.getGrid().getRows().get(i).get(j);
                MySpreadSheetType spvcelltype = (MySpreadSheetType) spvcell.getCellType();
                if (spvcelltype.getCell() == null || !evaluator.getTableOfCells().containsValue(spvcelltype.getCell())) {
                    spvcelltype.setCell(cell);

                }

                if (!cell.isEmpty() && (cell.isErrorInExpression() || !cell.isExpression())) {
                    showFullErrorMessageMenuItem.setDisable(false);
                    menuItemShowError.setDisable(false);
                    showErrorButton.setDisable(false);
                } else {
                    showFullErrorMessageMenuItem.setDisable(true);
                    menuItemShowError.setDisable(true);
                    showErrorButton.setDisable(true);
                }
                if (cell.isMatrix() && !cell.isErrorInExpression() && cell.isPrintableValue()) {
                    menuItemExpandMatrix.setDisable(false);
                    expandMatrixContextMenu.setDisable(false);
                    expandMatrixButton.setDisable(false);
                } else {
                    menuItemExpandMatrix.setDisable(true);
                    expandMatrixContextMenu.setDisable(true);
                    expandMatrixButton.setDisable(true);
                }

                if (cell.isExpanded()) {
                    expandMatrixContextMenu.setText("Compress Matrix");
                    menuItemExpandMatrix.setText("Compress Matrix");
                    expandMatrixButton.setText("Compress Matrix");
                } else {
                    expandMatrixContextMenu.setText("Expand Matrix");
                    menuItemExpandMatrix.setText("Expand Matrix");
                    expandMatrixButton.setText("Expand Matrix");
                }
                /*LabelExpressionCell.setText(cell.getExpression());
                 LabelExpressionValue.setText(cell.getValue().toString());*/
                //LabelExpressionError.setText(cell.getErrorMessage());
                EditTextFormula.setText(cell.getExpression());
                updateBottomBar();
                //System.out.println(evaluator.getSelectedCell().isSpecialValueCell());
                //System.out.println(cell.isErrorInExpression() + "|" + cell.getErrorMessage());
            }

        }
    };

    private final ChangeListener<Object> onChangedCellItem = new ChangeListener<Object>() {

        @Override
        public void changed(ObservableValue<? extends Object> observable, Object oldValue, Object newValue) {

            changeCellContentAction(newValue);

        }
    };

    EventHandler<KeyEvent> keyEventHandlerFormulaET
            = new EventHandler<KeyEvent>() {
                public void handle(final KeyEvent keyEvent) {
                    if (keyEvent.getCode() == KeyCode.ENTER) {
                        actionOfEditText();
                        //System.out.println(exp);
                        keyEvent.consume();
                    }
                }
            };

    void actionOfEditText() {
        String exp;
        if (!EditTextFormula.getText().isEmpty()) {
            exp = EditTextFormula.getText();
        } else {
            exp = "";
        }
        //majInterface(p);
        Cell cell = evaluator.getSelectedCell();

        ArrayList<Integer> index = getCellIndexFromName(cell.getName());
        int i = index.get(0), j = index.get(1);
        //previewSheet.getGrid().getRows().get(i).get(j).setItem(exp);
        changeCellContentAction(exp);
    }

    void notifyChange() {
        int i, j;
        for (Map.Entry<String, Cell> en : evaluator.getSelectedCell().fullTreeOfListners().entrySet()) {

            String nameVar = en.getKey();
            Cell cell = en.getValue();
            i = getCellIndexFromName(nameVar).get(0);
            j = getCellIndexFromName(nameVar).get(1);
            previewSheet.getGrid().setCellValue(i, j, cell.toString());

        }
    }

    private String getCellName(int i, int j) {
        String ret;
        //ret = Evaluator.STRINGONVARIABLENAME.get(j) + String.valueOf(i + 1);
        ret = Evaluator.STRINGONVARIABLENAME.get(i) + String.valueOf(j + 1);
        //System.out.println(ret);
        return ret;
    }

    public static ArrayList<Integer> getCellIndexFromName(String name) {
        return evaluator.getVariableIndexFromName(name);
    }

    public void updateSpreadSheetView() {
        //change = false;

        int i, j;
        
        for (Map.Entry<String, Cell> en : evaluator.getTableOfCells().entrySet()) {

            String nameVar = en.getKey();
            Cell cell = en.getValue();
            i = getCellIndexFromName(nameVar).get(0);
            j = getCellIndexFromName(nameVar).get(1);
            //System.out.println(cell.toString());
            String oldValue = previewSheet.getGrid().getRows().get(i).get(j).getText();
            previewSheet.getGrid().setCellValue(i, j, cell.toString());

            if (!cell.isPrintableValue()) {
                //removeColor(cell.getName());
                previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_ERROR);
                ///previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_SPECIALVALUE);
                previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().add(CSS_NOVALUE);
                //cell.setColor(CSS_NOVALUE);

            } else {

                //previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_SPECIALVALUE);
                previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_NOVALUE);
                previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_ERROR);
                if (cell.isSpecialValueCell()) {
                    // previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().add(CSS_SPECIALVALUE);
                } else {
                    // previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_SPECIALVALUE);
                }
            }

            if (cell.isErrorInExpression()) {
                removeColor(cell.getName());
                //previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_SPECIALVALUE);
                previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_NOVALUE);
                previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().add(CSS_ERROR);
                //cell.setColor(CSS_ERROR);

            } else {

                previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_ERROR);
            }

        }
        //change = true;
        Cell cell = evaluator.getSelectedCell();
        if (cell != null) {
            if (cell.isMatrix() && !cell.isErrorInExpression() && cell.isPrintableValue()) {
                menuItemExpandMatrix.setDisable(false);
                expandMatrixContextMenu.setDisable(false);
                expandMatrixButton.setDisable(false);
            } else {
                menuItemExpandMatrix.setDisable(true);
                expandMatrixContextMenu.setDisable(true);
                expandMatrixButton.setDisable(true);
            }
            if (cell != null) {
                EditTextFormula.setText(cell.getExpression());
            } else {
                EditTextFormula.setText("");
            }
            if (goForward) {
                undoredoController.nextStep();
                saved = false;

            }
            /*System.out.println("-------update------");
             undoredoController.show();
             System.out.println("-------------------");*/
            if (evaluator.getSelectedCell().isExpanded()) {
                if (cell.isExpanded()) {
                    expandMatrixContextMenu.setText("Compress Matrix");
                    menuItemExpandMatrix.setText("Compress Matrix");
                    expandMatrixButton.setText("Compress Marix");
                } else {
                    expandMatrixContextMenu.setText("Expand Matrix");
                    menuItemExpandMatrix.setText("Expand Matrix");
                    expandMatrixButton.setText("Expand Marix");
                }
            }
        }

        updateBottomBar();

    }

    void clearSheet() {
        goForward = true;

        undoredoController = new UndoRedoController();
        expandedDataHistory = new ExpandedDataHistory();
        menuItemUndo.setDisable(true);
        menuItemRedo.setDisable(true);
        menuItemInsertFonction.setDisable(true);
        ChoiceFonctionButton.setDisable(true);
        menuItemExpandMatrix.setDisable(true);
        expandMatrixContextMenu.setDisable(true);

        undoButton.setDisable(true);
        redoButton.setDisable(true);
        setColorButtonDisabled(true);
        expandMatrixButton.setDisable(true);
        showErrorButton.setDisable(true);
        copyButton.setDisable(true);
        pasteButton.setDisable(true);
        deleteButton.setDisable(true);
        cutButton.setDisable(true);
        initColorButton();
        undoredoController.index.addListener(new ChangeListener<Number>() {

            @Override
            public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                if (undoredoController.indexRespectdBoundsForUndo()) {
                    menuItemUndo.setDisable(false);
                } else {
                    menuItemUndo.setDisable(true);
                }

                if (undoredoController.indexRespectdBoundsForRedo()) {
                    menuItemRedo.setDisable(false);
                } else {
                    menuItemRedo.setDisable(true);
                }

            }
        });
        for (Map.Entry<String, Cell> entry : evaluator.getTableOfCells().entrySet()) {
            String namecell = entry.getKey();
            Cell cell = entry.getValue();
            removeColor(namecell);
            //if (cell.isExpanded()) cell.disExpandMatrix();
            cell.evaluate("");

        }

        goForward = false;
        updateSpreadSheetView();
        goForward = true;
        previewSheet.getSelectionModel().clearSelection();

        this.EditTextFormula.setText("");
        evaluator = new Evaluator();
        updateFunctionBar();
        expandMatrixContextMenu.setText("Expand Matrix");
        menuItemExpandMatrix.setText("Expand Matrix");
        expandMatrixButton.setText("Expand Marix");

        //
        updateBottomBar();
        //MiniExcelCompilProject.masterStage.hide();
        /*choiceBoxColor.getSelectionModel().selectedIndexProperty().removeListener(changecolorlistner);
         choiceBoxColor.getSelectionModel().selectFirst();
         choiceBoxColor.getSelectionModel().selectedIndexProperty().addListener(changecolorlistner);*/
        //MiniExcelCompilProject.masterStage.show();*/
    }

    EventHandler<ActionEvent> ActionMenuItemNewFile = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            //System.out.println("EXIT");

            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("New File");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("MCX", "*.mcx")
            );
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.setInitialFileName("new sheet" + ".mcx");
            File file = fileChooser.showSaveDialog(MiniExcelCompilProject.masterStage.getOwner());
            if (file != null) {
                Action response = Dialogs.create()
                        .owner(MiniExcelCompilProject.masterStage)
                        .title("Confirmation")
                        .masthead("Creating new File")
                        .message("Would You save your file before creating a new file ? ")
                        .showConfirm();

                if (response == Dialog.ACTION_YES) {

                    if (workingSheet.length() == 0) {
                        FileChooser fileChooser2 = new FileChooser();
                        fileChooser2.setTitle("Save as");
                        fileChooser2.getExtensionFilters().addAll(
                                new FileChooser.ExtensionFilter("MCX", "*.mcx")
                        );
                        fileChooser2.setInitialDirectory(new File(System.getProperty("user.home")));
                        fileChooser2.setInitialFileName("new sheet" + ".mcx");
                        File file2 = fileChooser2.showSaveDialog(MiniExcelCompilProject.masterStage.getOwner());
                        if (file2 != null) {
                            workingSheet = file2.getAbsolutePath();
                            evaluator.save(workingSheet);
                            clearSheet();
                            String path = workingSheet;
                            String newPath = file.getAbsolutePath();
                            evaluator.save(newPath);
                            workingSheet = newPath;
                            MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                            MiniExcelCompilProject.masterStage.hide();
                            MiniExcelCompilProject.masterStage.show();
                        }

                    } else {
                        evaluator.save(workingSheet);
                        clearSheet();
                        String path = workingSheet;
                        String newPath = file.getAbsolutePath();
                        evaluator.save(newPath);
                        workingSheet = newPath;
                        MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                        MiniExcelCompilProject.masterStage.hide();
                        MiniExcelCompilProject.masterStage.show();
                    }
                } else if (response == Dialog.ACTION_NO) {
                    clearSheet();
                    String path = workingSheet;
                    String newPath = file.getAbsolutePath();
                    evaluator.save(newPath);
                    workingSheet = newPath;
                    MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                    MiniExcelCompilProject.masterStage.hide();
                    MiniExcelCompilProject.masterStage.show();
                } else {

                }

            }

            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuItemSave = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            //System.out.println("EXIT");
            String path = workingSheet;
            if (path.length() == 0) {
                FileChooser fileChooser = new FileChooser();
                fileChooser.setTitle("Save as");
                fileChooser.getExtensionFilters().addAll(
                        new FileChooser.ExtensionFilter("MCX", "*.mcx")
                );
                fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
                fileChooser.setInitialFileName("new sheet" + ".mcx");
                File file = fileChooser.showSaveDialog(MiniExcelCompilProject.masterStage.getOwner());
                if (file != null) {
                    String newPath = file.getAbsolutePath();
                    evaluator.save(newPath);
                    workingSheet = newPath;
                    MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                    saved = true;
                }
            } else {
                evaluator.save(path);
                Notifications.create().title("Saving file").text("File have been Successfully Saved").position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
            }
            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuItemSaveAs = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

            //System.out.println("EXIT");
            String path = workingSheet;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Save As");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("MCX", "*.mcx")
            );
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.setInitialFileName("new sheet" + ".mcx");
            File file = fileChooser.showSaveDialog(MiniExcelCompilProject.masterStage.getOwner());
            if (file != null) {
                String newPath = file.getAbsolutePath();
                evaluator.save(newPath);
                workingSheet = newPath;
                MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                MiniExcelCompilProject.masterStage.hide();
                MiniExcelCompilProject.masterStage.show();
                saved = true;
                Notifications.create().title("Saved file").text("File have been Successfully Saved").position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
            }

            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuItemLoad = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            //System.out.println("EXIT");

            String path = workingSheet;
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Load");
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("MCX", "*.mcx")
            );
            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
            fileChooser.setInitialFileName("new sheet" + ".mcx");
            File file = fileChooser.showOpenDialog(MiniExcelCompilProject.masterStage.getOwner());

            if (file != null) {
                if (saved) {
                    String newPath = file.getAbsolutePath();
                    workingSheet = newPath;
                    MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                    clearSheet();
                    Evaluator oldEvaluator = evaluator;
                    evaluator = Evaluator.load(newPath);

                    Notifications.create().title("Loading file").text("File have been Successfully Loaded").position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
                    updateSpreadSheetView();
                    for (Map.Entry<String, Cell> entry : evaluator.getTableOfCells().entrySet()) {
                        String key = entry.getKey();
                        Cell cell = entry.getValue();
                        if (!cell.getColor().isEmpty()) {
                            setColor(key, cell.getColor());
                        }
                    }
                    saved = true;
                } else {
                    Action response = Dialogs.create()
                            .owner(MiniExcelCompilProject.masterStage)
                            .title("Confirmation")
                            .masthead("Loading a new File")
                            .message("Would You save your current file before loading this file ? ")
                            .showConfirm();

                    if (response == Dialog.ACTION_YES) {

                        if (workingSheet.length() == 0) {
                            FileChooser fileChooser2 = new FileChooser();
                            fileChooser2.setTitle("Save as");
                            fileChooser2.getExtensionFilters().addAll(
                                    new FileChooser.ExtensionFilter("MCX", "*.mcx")
                            );
                            fileChooser2.setInitialDirectory(new File(System.getProperty("user.home")));
                            fileChooser2.setInitialFileName("new sheet" + ".mcx");
                            File file2 = fileChooser2.showSaveDialog(MiniExcelCompilProject.masterStage.getOwner());
                            if (file2 != null) {
                                workingSheet = file2.getAbsolutePath();
                                evaluator.save(workingSheet);

                                String newPath = file.getAbsolutePath();
                                workingSheet = newPath;
                                MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                                clearSheet();
                                Evaluator oldEvaluator = evaluator;
                                evaluator = Evaluator.load(newPath);
                                /*for (Map.Entry<String, Cell> entry : oldEvaluator.getTableOfCells().entrySet()) {
                                 String key = entry.getKey();
                                 Cell cell = entry.getValue();
                                 if (!evaluator.getTableOfCells().containsKey(key)) evaluator.getTableOfCells().put(key, cell);
                    
                                 }*/
                                Notifications.create().title("Loading file").text("File have been Successfully Loaded").position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
                                updateSpreadSheetView();
                                for (Map.Entry<String, Cell> entry : evaluator.getTableOfCells().entrySet()) {
                                    String key = entry.getKey();
                                    Cell cell = entry.getValue();
                                    if (!cell.getColor().isEmpty()) {
                                        setColor(key, cell.getColor());
                                    }
                                }
                                saved = true;
                            }

                        } else {
                            evaluator.save(workingSheet);

                            String newPath = file.getAbsolutePath();
                            workingSheet = newPath;
                            MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                            clearSheet();
                            Evaluator oldEvaluator = evaluator;
                            evaluator = Evaluator.load(newPath);
                            /*for (Map.Entry<String, Cell> entry : oldEvaluator.getTableOfCells().entrySet()) {
                             String key = entry.getKey();
                             Cell cell = entry.getValue();
                             if (!evaluator.getTableOfCells().containsKey(key)) evaluator.getTableOfCells().put(key, cell);
                    
                             }*/
                            Notifications.create().title("Loading file").text("File have been Successfully Loaded").position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
                            updateSpreadSheetView();
                            for (Map.Entry<String, Cell> entry : evaluator.getTableOfCells().entrySet()) {
                                String key = entry.getKey();
                                Cell cell = entry.getValue();
                                if (!cell.getColor().isEmpty()) {
                                    setColor(key, cell.getColor());
                                }
                            }
                            saved = true;
                        }

                    } else if (response == Dialog.ACTION_NO) {
                        String newPath = file.getAbsolutePath();
                        workingSheet = newPath;
                        MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + workingSheet + "\"");
                        clearSheet();
                        Evaluator oldEvaluator = evaluator;
                        evaluator = Evaluator.load(newPath);

                        Notifications.create().title("Loading file").text("File have been Successfully Loaded").position(Pos.CENTER).hideAfter(Duration.seconds(2)).showInformation();
                        updateSpreadSheetView();
                        for (Map.Entry<String, Cell> entry : evaluator.getTableOfCells().entrySet()) {
                            String key = entry.getKey();
                            Cell cell = entry.getValue();
                            if (!cell.getColor().isEmpty()) {
                                setColor(key, cell.getColor());
                            }
                        }
                        saved = true;

                    } else {

                    }
                }

            }

            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuItemExit = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {

            Action response = Dialogs.create()
                    .owner(MiniExcelCompilProject.masterStage)
                    .title("Confirmation")
                    .masthead("Exit the Application")
                    .message("Would You save your file before you exit the application ?")
                    .showConfirm();

            if (response == Dialog.ACTION_YES && saved) {
                if (workingSheet.length() == 0) {
                    FileChooser fileChooser2 = new FileChooser();
                    fileChooser2.setTitle("Save as");
                    fileChooser2.getExtensionFilters().addAll(
                            new FileChooser.ExtensionFilter("MCX", "*.mcx")
                    );
                    fileChooser2.setInitialDirectory(new File(System.getProperty("user.home")));
                    fileChooser2.setInitialFileName("new sheet" + ".mcx");
                    File file2 = fileChooser2.showSaveDialog(MiniExcelCompilProject.masterStage.getOwner());
                    if (file2 != null) {
                        workingSheet = file2.getAbsolutePath();
                        FXMLDocumentController.evaluator.save(workingSheet);
                        Platform.exit();
                    }
                } else {
                    FXMLDocumentController.evaluator.save(workingSheet);
                    Platform.exit();
                }

            }
            if (response == Dialog.ACTION_NO) {
                Platform.exit();
            } else {

            }
            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuCopy = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(evaluator.getSelectedCell().getExpression());
            clipboard.setContent(content);

            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    EventHandler<ActionEvent> ActionMenuPaste = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            String expression = clipboard.getString();
            //System.out.println(text);
            String nameSelected = evaluator.getSelectedCell().getName();
            ArrayList<Integer> index = getCellIndexFromName(nameSelected);
            int i = index.get(0);
            int j = index.get(1);
            Cell selected = evaluator.getSelectedCell();
            //HashMap<String,Cell> cellArea = selected.cellAreaFromName(nameSelected,copied.getExpandedRowsNumber(),copied.getExpandedColNumber());
            undoredoController.clearActualIndex();
            undoredoController.add(new Cell(selected));
            evaluator.getSelectedCell().evaluate(expression);
            //previewSheet.getGrid().setCellValue(i, j, text);
            updateSpreadSheetView();
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }
    };

    EventHandler<ActionEvent> ActionMenuCut = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            final Clipboard clipboard = Clipboard.getSystemClipboard();
            final ClipboardContent content = new ClipboardContent();
            content.putString(evaluator.getSelectedCell().getExpression());
            clipboard.setContent(content);
            String nameSelected = evaluator.getSelectedCell().getName();
            ArrayList<Integer> index = getCellIndexFromName(nameSelected);
            int i = index.get(0);
            int j = index.get(1);
            //previewSheet.getGrid().setCellValue(i, j, "");
            Cell cell = evaluator.getSelectedCell();
            HashMap<String, Cell> cellArea = (HashMap<String, Cell>) cell.cellArea();
            undoredoController.set(cellArea);
            cell.evaluate("");
            updateSpreadSheetView();

        }
    };

    EventHandler<ActionEvent> ActionMenuDelete = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            String nameSelected = evaluator.getSelectedCell().getName();
            ArrayList<Integer> index = getCellIndexFromName(nameSelected);
            int i = index.get(0);
            int j = index.get(1);
            //previewSheet.getGrid().setCellValue(i, j, "");
            Cell cell = evaluator.getSelectedCell();
            HashMap<String, Cell> cellArea = (HashMap<String, Cell>) cell.cellArea();
            undoredoController.set(cellArea);
            cell.evaluate("");
            updateSpreadSheetView();

        }
    };

    EventHandler<ActionEvent> ActionMenuShowError = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuExpandMatrix = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();

            Cell cell = evaluator.getSelectedCell();
            if (cell.isExpanded()) {
                Cell oldCell = new Cell(cell);
                HashMap<String, Cell> ret = cell.compressMatrix();
                /*undoredoController.add(oldCell);
                 for (Map.Entry<String, Cell> entry : ret.entrySet()) {
                 String string = entry.getKey();
                 Cell value = entry.getValue();
                 undoredoController.add(value);
                 }*/
                undoredoController.set(ret);

                //for expansion treatment
                /*HashMap<String, Cell> history = expandedDataHistory.getExpandedDataHistory(cell.getName());
                 for (Map.Entry<String, Cell> entry : history.entrySet()) {
                 String historyCellName = entry.getKey();
                 Cell historyCell = entry.getValue();
                 evaluator.getTableOfCells().get(historyCellName).set(historyCell);
                 }*/
                //for expansion treatment
                expandMatrixContextMenu.setText("Expand Matrix");
                menuItemExpandMatrix.setText("Expand Matrix");
                expandMatrixButton.setText("Expand Matrix");
            } else {
                Cell oldCell = new Cell(cell);
                HashMap<String, Cell> ret = cell.expandMatrix();
                //for expansion treatment

                for (Map.Entry<String, Cell> entry : ret.entrySet()) {
                    String string = entry.getKey();
                    Cell value = entry.getValue();

                    expandedDataHistory.put(oldCell.getName(), new Cell(value));
                }

                //for expansion treatment
                undoredoController.set(ret);
                undoredoController.add(oldCell);
                /*for (Map.Entry<String, Cell> entry : ret.entrySet()) {
                 String string = entry.getKey();
                 Cell value = entry.getValue();
                 undoredoController.add(value);
                 }*/
                expandMatrixContextMenu.setText("Compress Matrix");
                menuItemExpandMatrix.setText("Compress Matrix");
                expandMatrixButton.setText("Compress Matrix");
            }
            updateSpreadSheetView();

            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    public static boolean isChange() {
        return change;
    }

    public static void setChange(boolean change) {
        FXMLDocumentController.change = change;
    }

    public Cell changeCellContentAction(Object newValue) {
        Cell cell = null;
        if (change) {
            change = false;
            //System.out.println(newValue);
            if (newValue == null) {
                newValue = "";
            }
            cell = evaluator.getSelectedCell();

            FXMLDocumentController.undoredoController.add(new Cell(cell));
            if (cell.isExpanded()) {
                HashMap<String, Cell> area = cell.cellArea();
                for (Cell areaCell : area.values()) {
                    FXMLDocumentController.undoredoController.add(new Cell(areaCell));
                }
            }
            TreeMap<String, Cell> ftl = cell.fullTreeOfListners();
            for (Map.Entry<String, Cell> entry : ftl.entrySet()) {
                String string = entry.getKey();
                Cell listener = entry.getValue();
                FXMLDocumentController.undoredoController.add(new Cell(listener));
                if (listener.isExpanded()) {
                    HashMap<String, Cell> area = listener.cellArea();
                    for (Cell areaCell : area.values()) {
                        FXMLDocumentController.undoredoController.add(new Cell(areaCell));
                    }
                }

            }

            cell.evaluate(newValue.toString());
            EditTextFormula.setText(newValue.toString());
            ArrayList<Integer> index = getCellIndexFromName(cell.getName());
            int i = index.get(0);
            int j = index.get(1);
            previewSheet.getGrid().setCellValue(i, j, cell.toString());
            previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_ERROR);
            if (!cell.isEmpty() && (cell.isErrorInExpression() || !cell.isExpression())) {
                showFullErrorMessageMenuItem.setDisable(false);
                menuItemShowError.setDisable(false);

                if (cell.isErrorInExpression()) {
                    previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().add(CSS_ERROR);
                }

            } else {
                showFullErrorMessageMenuItem.setDisable(true);
                menuItemShowError.setDisable(true);
            }

            if (cell.isExpanded()) {
                expandMatrixContextMenu.setText("Compress Matrix");
                menuItemExpandMatrix.setText("Compress Matrix");
                expandMatrixButton.setText("Compress Marix");
            } else {
                expandMatrixContextMenu.setText("Expand Matrix");
                menuItemExpandMatrix.setText("Expand Matrix");
                expandMatrixButton.setText("Expand Marix");
            }

            //early changed
            //notifyChange();
            //early changed
            updateSpreadSheetView();
            //System.out.println(evaluator.getSelectedCell().getName());
            change = true;

        }
        return cell;
    }

    EventHandler<ActionEvent> ActionMenuUndo = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            HashMap<String, Cell> undo = undoredoController.undo();
            HashMap<String, Cell> old = new HashMap<String, Cell>();
            for (Map.Entry<String, Cell> entry : undo.entrySet()) {
                String string = entry.getKey();
                Cell cell = entry.getValue();
                old.put(string, new Cell(evaluator.getVariable(string)));
                evaluator.getTableOfCells().get(string).set(cell);
            }
            //new
            for (Map.Entry<String, Cell> entry : undo.entrySet()) {
                String string = entry.getKey();
                Cell cell = entry.getValue();

                evaluator.getTableOfCells().get(string).reevaluate();
            }

            //new
            goForward = false;
            updateSpreadSheetView();
            goForward = true;
            if (evaluator.getSelectedCell().isExpanded()) {
                expandMatrixContextMenu.setText("Compress Matrix");
                menuItemExpandMatrix.setText("Compress Matrix");
                expandMatrixButton.setText("Compress Matrix");
            } else {
                expandMatrixContextMenu.setText("Expand Matrix");
                menuItemExpandMatrix.setText("Expand Matrix");
                expandMatrixButton.setText("Expand Matrix");
            }

            /*for (Map.Entry<String, Cell> entry : old.entrySet()) {
             String string = entry.getKey();
             Cell cell = entry.getValue();
             undoredoController.add(cell);
             }*/
            undoredoController.oldStep();

            undoredoController.set(old);

            /*System.out.println("------undo------");
             undoredoController.show();

             System.out.println("----------------");*/
            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuRedo = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            HashMap<String, Cell> redo = undoredoController.redo();
            HashMap<String, Cell> old = new HashMap<String, Cell>();
            for (Map.Entry<String, Cell> entry : redo.entrySet()) {
                String string = entry.getKey();
                Cell cell = entry.getValue();
                old.put(string, new Cell(evaluator.getVariable(string)));
                evaluator.getTableOfCells().get(string).set(cell);
            }
            //new
            for (Map.Entry<String, Cell> entry : redo.entrySet()) {
                String string = entry.getKey();
                Cell cell = entry.getValue();

                evaluator.getTableOfCells().get(string).reevaluate();
            }
            //new
            goForward = false;
            updateSpreadSheetView();
            goForward = true;
            if (evaluator.getSelectedCell().isExpanded()) {
                expandMatrixContextMenu.setText("Compress Matrix");
                menuItemExpandMatrix.setText("Compress Matrix");
                expandMatrixButton.setText("Compress Matrix");
            } else {
                expandMatrixContextMenu.setText("Expand Matrix");
                menuItemExpandMatrix.setText("Expand Matrix");
                expandMatrixButton.setText("Expand Matrix");
            }
            undoredoController.set(old);
            undoredoController.next();
            /*System.out.println("-------redo------");
             undoredoController.show();
             System.out.println("----------------");*/
            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionMenuRefresh = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();

            //goForward = false;
            for (Cell cell : evaluator.getTableOfCells().values()) {
                if (cell.isContainRandomFunction()) {
                    undoredoController.add(new Cell(cell));
                    TreeMap<String, Cell> tree = cell.fullTreeOfListners();
                    for (Cell cell2 : tree.values()) {
                        undoredoController.add(new Cell(cell2));
                        if (cell2.isMatrix() && cell2.isExpanded()) {
                            HashMap<String, Cell> hashMap = cell2.cellArea();
                            for (Cell cell3 : hashMap.values()) {
                                undoredoController.add(new Cell(cell3));
                            }
                        }
                    }
                    if (cell.isExpanded() && cell.isMatrix()) {
                        HashMap<String, Cell> hashMap = cell.cellArea();
                        for (Cell cell4 : hashMap.values()) {
                            undoredoController.add(new Cell(cell4));
                        }
                    }
                    cell.evaluate(cell.getExpression());

                }
            }

            updateSpreadSheetView();
            //goForward = true;

            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    EventHandler<ActionEvent> ActionButtonInsert = new EventHandler<ActionEvent>() {

        @Override
        public void handle(ActionEvent event) {
            event.consume();
            EditTextFormula.appendText(labelFunctions.getText() + "(" + ")");
            //Notifications.create().title("Error detail").text(evaluator.getSelectedCell().getErrorMessage()).position(Pos.CENTER).showError();
        }
    };

    /*void initChoiceBoxColor() {
     choiceBoxColor.getItems().add("no Color");
     choiceBoxColor.getItems().add("blue");
     choiceBoxColor.getItems().add("green");
     choiceBoxColor.getItems().add("brown");
     choiceBoxColor.getItems().add("black");
     choiceBoxColor.getSelectionModel().selectFirst();
     choiceBoxColor.getSelectionModel().selectedIndexProperty().addListener(changecolorlistner);

     }*/
    void initChoiceBoxArithmetic() {

        ArrayList<String> arithmeticFunction = evaluator.getArithmeticFunction();
        for (int i = 0; i < arithmeticFunction.size(); i++) {
            ArithmeticChoiceBoxFunction.getItems().add(arithmeticFunction.get(i).toUpperCase());
        }
        ArithmeticChoiceBoxFunction.getSelectionModel().selectFirst();
        ArithmeticChoiceBoxFunction.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                labelFunctions.setText((String) newValue);
                updateFunctionBar();
                //System.out.println("1");
            }
        });
        labelFunctions.setText((String) ArithmeticChoiceBoxFunction.getItems().get(0));

    }

    void initChoiceBoxStatistic() {

        ArrayList<String> staticFunction = evaluator.getStatisticFunction();
        for (int i = 0; i < staticFunction.size(); i++) {
            StatisticChoiceBoxFunction.getItems().add(staticFunction.get(i).toUpperCase());
        }
        StatisticChoiceBoxFunction.getSelectionModel().selectFirst();
        StatisticChoiceBoxFunction.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                labelFunctions.setText((String) newValue);
                updateFunctionBar();
                //System.out.println("2");
            }

        });

    }

    void initChoiceBoxLogical() {

        ArrayList<String> logicFunction = evaluator.getLogicalFunction();
        for (int i = 0; i < logicFunction.size(); i++) {
            LogicalChoiceBoxFunction.getItems().add(logicFunction.get(i).toUpperCase());
        }
        LogicalChoiceBoxFunction.getSelectionModel().selectFirst();
        LogicalChoiceBoxFunction.getSelectionModel().selectedItemProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                labelFunctions.setText((String) newValue);
                updateFunctionBar();
                System.out.println("3");
            }
        });

    }

    void updateBottomBar() {
        Cell cell = evaluator.getSelectedCell();
        if (cell != null) {
            labelSelectedCell.setText(cell.getName());
            if (cell.isPrintableValue()) {
                labelValue.setText(cell.getValue().toString());
            } else {
                labelValue.setText(" No Value ! ");
            }

            if (cell.isMatrix()) {
                labelIsMatrix.setText(" Yes");
            } else {
                labelIsMatrix.setText(" No");
            }

            if (cell.isErrorInExpression()) {
                labelError.setText(cell.getErrorType());
            } else if (!cell.isExpression()) {
                labelError.setText("No Expression");
            } else {
                labelError.setText("No Error");
            }

        } else {
            labelSelectedCell.setText(" - ");
            labelValue.setText(" - ");
            labelIsMatrix.setText(" No");
            labelError.setText("No Error");

        }

    }

    void updateFunctionBar() {
        String ret = labelFunctions.getText().toLowerCase();

        TreeMap<String, Function> tree = evaluator.getArithmeticFunctionTree();
        if (tree.containsKey(ret)) {
            labelFunctionName.setText(ret.toUpperCase());
            labelFunctionType.setText(tree.get(ret).getTypeOfFunction().toString().toLowerCase());
            labelMinArgs.setText(tree.get(ret).getMinimalNumberofArguments());
            labelMaxArgs.setText(tree.get(ret).getMaximalNumberofArguments());
        } else {
            TreeMap<String, Function> tree2 = evaluator.getStatisticFunctionTree();
            if (tree2.containsKey(ret)) {
                labelFunctionName.setText(ret.toUpperCase());
                labelFunctionType.setText(tree2.get(ret).getTypeOfFunction().toString().toLowerCase());
                labelMinArgs.setText(tree2.get(ret).getMinimalNumberofArguments());
                labelMaxArgs.setText(tree2.get(ret).getMaximalNumberofArguments());
            } else {
                TreeMap<String, Function> tree3 = evaluator.getLogicalFunctionTree();
                if (tree3.containsKey(ret)) {
                    labelFunctionName.setText(ret.toUpperCase());
                    labelFunctionType.setText(tree3.get(ret).getTypeOfFunction().toString().toLowerCase());
                    labelMinArgs.setText(tree3.get(ret).getMinimalNumberofArguments());
                    labelMaxArgs.setText(tree3.get(ret).getMaximalNumberofArguments());
                }
            }
        }

    }

    void setColor(String cssStyle) {
        ArrayList<Integer> index = getCellIndexFromName(evaluator.getSelectedCell().getName());
        int i = index.get(0);
        int j = index.get(1);
        removeColor(evaluator.getSelectedCell().getName());
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().add(cssStyle);
        evaluator.getSelectedCell().setColor(cssStyle);
    }

    void setColor(String name, String cssStyle) {
        ArrayList<Integer> index = getCellIndexFromName(name);
        int i = index.get(0);
        int j = index.get(1);
        removeColor(name);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().add(cssStyle);
        evaluator.getVariable(name).setColor(cssStyle);
    }

    void removeColor(String name) {
        ArrayList<Integer> index = getCellIndexFromName(name);
        int i = index.get(0);
        int j = index.get(1);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_NOVALUE);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_ERROR);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_BLACK);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_AMETHYST);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_BLUE);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_GREEN);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_LIGHTGREEN);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_HEAVYBLUE);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_WISTERIA);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_RED);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_HEAVYRED);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_ROSE);
        previewSheet.getGrid().getRows().get(i).get(j).getStyleClass().remove(CSS_YELLOW);
        evaluator.getVariable(name).setColor("");

    }

    void removeColor() {
        removeColor(evaluator.getSelectedCell().getName());
    }

    /*ChangeListener changecolorlistner = new ChangeListener<Number>() {

     @Override
     public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
     int i = newValue.intValue();
     if (i == 0) {
     removeColor(evaluator.getSelectedCell().getName());
     } else if (i == 1) {
     setColor(CSS_BLUE);
     } else if (i == 2) {
     setColor(CSS_GREEN);
     } else if (i == 3) {
     setColor(CSS_BROWN);
     } else if (i == 4) {
     setColor(CSS_BLACK);
     }
     }
     };*/
    void setColorButtonDisabled(boolean disabled) {
        coloramethyst.setDisable(disabled);
        colorblack.setDisable(disabled);
        colorblue.setDisable(disabled);
        colorgreen.setDisable(disabled);
        colorheavyblue.setDisable(disabled);
        colorheavyred.setDisable(disabled);
        colorlightgreen.setDisable(disabled);
        colorred.setDisable(disabled);
        colorrose.setDisable(disabled);
        colorwhite.setDisable(disabled);
        colorwisteria.setDisable(disabled);
        coloryellow.setDisable(disabled);
    }

    private void initColorButton() {
        //new
        colorwhite.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                removeColor();
            }
        });
        colorgreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_GREEN);
            }
        });
        colorlightgreen.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_LIGHTGREEN);
            }
        });
        colorblue.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_BLUE);
            }
        });
        colorheavyblue.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_HEAVYBLUE);
            }
        });
        coloramethyst.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_AMETHYST);
            }
        });
        colorwisteria.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_WISTERIA);
            }
        });
        coloryellow.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_YELLOW);
            }
        });
        colorblack.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_BLACK);
            }
        });
        colorrose.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_ROSE);
            }
        });
        colorheavyred.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_HEAVYRED);
            }
        });
        colorred.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                setColor(CSS_RED);
            }
        });
    }
}
