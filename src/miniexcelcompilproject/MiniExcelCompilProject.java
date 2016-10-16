/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package miniexcelcompilproject;

import java.io.File;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import static miniexcelcompilproject.FXMLDocumentController.APP_TITLE;
import static miniexcelcompilproject.FXMLDocumentController.workingSheet;
import org.controlsfx.control.action.Action;
import org.controlsfx.dialog.Dialog;
import org.controlsfx.dialog.Dialogs;

/**
 *
 * @author Hatem
 */
public class MiniExcelCompilProject extends Application {

    static Stage masterStage;

    @Override
    public void start(Stage stage) throws Exception {
        masterStage = stage;
        Parent root = FXMLLoader.load(getClass().getResource("FXMLView.fxml"));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("/mycss.css").toExternalForm());
        
        stage.setScene(scene);
        MiniExcelCompilProject.masterStage.setTitle(APP_TITLE + " - \"" + FXMLDocumentController.workingSheet + "\"");
        stage.setResizable(false);
        stage.setMaxHeight(600+30);
        stage.setMaxWidth(900+6);
        stage.getIcons().add(new Image("icon.png"));
        stage.show();
        stage.setOnCloseRequest(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                event.consume();
                Action response = Dialogs.create()
                        .owner(MiniExcelCompilProject.masterStage)
                        .title("Confirmation")
                        .masthead("Exit the Application")
                        .message("Would You save your file before you exit the application ?")
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
                //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);

    }

}
