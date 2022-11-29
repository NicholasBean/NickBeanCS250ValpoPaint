package finalpaint.finalpaint__letshope__;

import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCombination;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import javafx.scene.image.Image;

// ---------------------------------- //
//  File: MenuControls.java
//  Date: 11/27/22
//  Programmer: Nick Bean
//
//
//  Purpose: This entire file establishes the menu controls for File,
//      Edit, and About. From there, key binds are assigned to each menu item.
//
//  Notes: The rotation could be a bit better. It doesn't rotate "x" degrees from
//      the CURRENT position, but the ORIGINAL position of the canvas/image.
// ---------------------------------- //

public class MenuControls extends MenuBar {

    public MenuControls() throws FileNotFoundException {
        super();

        int wah = 25;   // Controls the width and height for icons.

        Menu file = new Menu("File");
        Menu edit = new Menu("Edit");   // Creating new menus & titles
        Menu help = new Menu("Help");

        getMenus().add(file);
        getMenus().add(edit);   // Adding them to the menu bar.
        getMenus().add(help);

        SeparatorMenuItem s = new SeparatorMenuItem();
        SeparatorMenuItem s2 = new SeparatorMenuItem();     // Stupid menu separators. Hate these.

// ------------------- //
        // Below are the image instances for the menu options.

        // I know, I know, these are hardcoded into MY device, which isn't good. I never properly investigated a proper
        // solution to be completely honest. However, the icons are sourced from a folder within the project package,
        // not just a random folder on my laptop.
        FileInputStream newIcon = new FileInputStream("C:\\Users\\Nick\\IdeaProjects\\FinalPaint__letsHope__\\src\\main\\resources\\icons\\IconImg\\openIcon.png");
        Image newIm = new Image(newIcon);
        ImageView newView = new ImageView(newIm);
        newView.setFitWidth(wah);
        newView.setFitHeight(wah);

        FileInputStream saveIcon = new FileInputStream("C:\\Users\\Nick\\IdeaProjects\\FinalPaint__letsHope__\\src\\main\\resources\\icons\\IconImg\\saveIcon.png");
        Image saveIm = new Image(saveIcon);
        ImageView saveView = new ImageView(saveIm);
        saveView.setFitHeight(wah);
        saveView.setFitWidth(wah);

        FileInputStream saveAsIcon = new FileInputStream("C:\\Users\\Nick\\IdeaProjects\\FinalPaint__letsHope__\\src\\main\\resources\\icons\\IconImg\\saveasIcon.png");
        Image saveAsIm = new Image(saveAsIcon);
        ImageView saveAsView = new ImageView(saveAsIm);
        saveAsView.setFitWidth(wah);
        saveAsView.setFitHeight(wah);

        FileInputStream openIcon = new FileInputStream("C:\\Users\\Nick\\IdeaProjects\\FinalPaint__letsHope__\\src\\main\\resources\\icons\\IconImg\\newIcon.png");
        Image openIm = new Image(openIcon);
        ImageView openView = new ImageView(openIm);
        openView.setFitHeight(wah);
        openView.setFitWidth(wah);

        FileInputStream exitIcon = new FileInputStream("C:\\Users\\Nick\\IdeaProjects\\FinalPaint__letsHope__\\src\\main\\resources\\icons\\IconImg\\exitIcon.png");
        Image exitIm = new Image(exitIcon);
        ImageView exitView = new ImageView(exitIm);
        exitView.setFitWidth(wah);
        exitView.setFitHeight(wah);

// ------------------- //
        // These are keybindings for all the menu options as well as setting the menu icons.
        MenuItem newWind = new MenuItem("New");
        newWind.setGraphic(newView);
        newWind.setMnemonicParsing(true);
        newWind.setAccelerator(KeyCombination.keyCombination("Ctrl+N"));

        MenuItem saveAs = new MenuItem("Save As");
        saveAs.setGraphic(saveAsView);
        saveAs.setMnemonicParsing(true);
        saveAs.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+S"));

        MenuItem save = new MenuItem("Save");
        save.setGraphic(saveView);
        save.setMnemonicParsing(true);
        save.setAccelerator(KeyCombination.keyCombination("Ctrl+S"));

        MenuItem open = new MenuItem("Open");
        open.setGraphic(openView);
        open.setMnemonicParsing((true));
        open.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));

        MenuItem exit = new MenuItem("Exit");
        exit.setGraphic(exitView);
        exit.setMnemonicParsing(true);
        exit.setAccelerator(KeyCombination.keyCombination("Ctrl+Alt+Q"));

        MenuItem clock90 = new MenuItem("Rotate 90 degrees");
        MenuItem flip = new MenuItem("Flip 180 degrees");
        MenuItem counter90 = new MenuItem("Counterclockwise 90 degrees");
        MenuItem original = new MenuItem("Original Orientation");

        MenuItem redo = new MenuItem("Redo");
        redo.setMnemonicParsing(true);
        redo.setAccelerator(KeyCombination.keyCombination("Ctrl+X"));

        MenuItem undo = new MenuItem("Undo");
        undo.setMnemonicParsing(true);
        undo.setAccelerator(KeyCombination.keyCombination("Ctrl+Z"));

        MenuItem about = new MenuItem("About Pain(t)");
        MenuItem helpme = new MenuItem("Help Me");

// ------------------- //
        // Adding those menu options to the primary menus (Primary: file, edit, help. Secondary: everything else).
        file.getItems().addAll(newWind, open, s, save, saveAs, s2, exit);
        edit.getItems().addAll(redo, undo, original, clock90, flip, counter90);
        help.getItems().addAll(about, helpme);

                // From here, I'll need to bring over the methods from tab construction.
                // I'm realizing that save, save as, and open are contingent on canvas availability.
                // Thus, Tab creation comes first.

                // That's exactly what happened!

// ------------------- //

        // Action events to execute menu functions.
        open.setOnAction((ActionEvent e) -> {
            TabConstruction.openImage();
        });
        newWind.setOnAction((ActionEvent e) -> {
            TabConstruction.openBlankImage();
        });
        save.setOnAction((ActionEvent e) -> {
            WindowBuild.getCurrentTab().saveImage();
        });
        saveAs.setOnAction((ActionEvent e) -> {
            WindowBuild.getCurrentTab().imageSaveAs();
        });
        original.setOnAction((ActionEvent e) -> {
            WindowBuild.getCurrentTab().getCanvas().setRotate(360);
            e.consume();
        });
        clock90.setOnAction((ActionEvent e) -> {
            WindowBuild.getCurrentTab().getCanvas().setRotate(90);
            e.consume();
        });
        flip.setOnAction((ActionEvent e) -> {
            WindowBuild.getCurrentTab().getCanvas().setRotate(180);
            e.consume();
        });
        counter90.setOnAction((ActionEvent e) ->{
            WindowBuild.getCurrentTab().getCanvas().setRotate(270);
            e.consume();
        });
        redo.setOnAction((ActionEvent e) -> {
            WindowBuild.getCurrentTab().redo();
            e.consume();
        });
        undo.setOnAction((ActionEvent e) -> {
            WindowBuild.getCurrentTab().undo();
        });
        exit.setOnAction((ActionEvent e) -> {
            TabConstruction.exit();
        });

    }
}