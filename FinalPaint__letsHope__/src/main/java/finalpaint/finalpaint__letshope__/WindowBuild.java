package finalpaint.finalpaint__letshope__;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

// ---------------------------------- //
//  File: WindowBuild.java
//  Date: 11/27/22
//  Programmer: Nick Bean
//
//
//  Purpose: This is the master file. This calls from all other .java files,
//      pulls everything together, puts a bow on top of it all, and launches
//      the program. Nothing too intense, just the framing of it all.
//
//  Notes: The window title could be anything, not reflective of the file name. This is how we launch.
//
// ---------------------------------- //
public class WindowBuild extends Application {

    public static TabPane newTab;
    public static MenuForCanvTools tools;
    public static MenuControls menus;
    public static Stage TheStage;

    private final static int Length = 1600;
    private final static int Height = 900;
    private final static String Title = "Pain(t) Mk. III";

    @Override
    public void start(Stage TheStage) throws FileNotFoundException {
        newTab = new TabPane();
        tools =  new MenuForCanvTools();
        menus = new MenuControls();

            BorderPane pane = new BorderPane();
            VBox tbox = new VBox(tools);
            VBox fbox = new VBox(menus);
            Scene scene = new Scene(pane, Length, Height);
            pane.setBottom(tbox);
            pane.setCenter(newTab);
            pane.setTop(fbox);

        newTab.getTabs().add(new TabConstruction());
        newTab.getSelectionModel().selectFirst();

            TheStage.setTitle(Title);
            TheStage.setScene(scene);
            TheStage.show();
    }
    public static TabConstruction getCurrentTab(){return (TabConstruction)newTab.getSelectionModel().getSelectedItem();}
    public static void closeTab(){
        WindowBuild.newTab.getTabs().remove(WindowBuild.getCurrentTab());
    }
    public static void main(String[] args) {
        launch();
    }
}

// See you, space cowboy...