package finalpaint.finalpaint__letshope__;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

// ---------------------------------- //
//  File: popup.java
//  Date: 11/27/22
//  Programmer: Nick Bean
//
//  Purpose: This file contains the popup to warn the user about unsaved changes and makes sure they are aware
//      they are about to close the current tab. However, the primary stage is not closed (noted as 'TheStage' in "WindowBuild.java").
//
//  Notes: None.
// ---------------------------------- //

public class popup {

    public static void exitWarning(Stage stage){
        Alert exitAlert = new Alert(Alert.AlertType.CONFIRMATION, "Are you ready to quit? Changes may be *unsaved*.",
                ButtonType.YES, ButtonType.NO);
        exitAlert.setTitle("WARNING");
        exitAlert.initOwner(WindowBuild.TheStage);
        if(exitAlert.showAndWait().get()==ButtonType.YES) {
            WindowBuild.closeTab();
            stage.close();
            Platform.exit();
            System.exit(0);
        }
        else{
            System.out.println("Not done yet!");
        }
    }

}
