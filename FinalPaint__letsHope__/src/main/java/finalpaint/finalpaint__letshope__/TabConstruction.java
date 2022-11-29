package finalpaint.finalpaint__letshope__;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.scene.control.Tab;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javax.imageio.ImageIO;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Timer;
import java.util.TimerTask;
import javafx.embed.swing.SwingFXUtils;

// ---------------------------------- //
//  File: TabConstruction.java
//  Date: 11/27/22
//  Programmer: Nick Bean
//
//  Purpose: This tab establishes what will be on a single tab. It holds the
//      code housing the logging and auto save timer, as well as creating the base
//      conditions for the tabs canvas.
//      Furthermore, I have the functions for save, save as, open, and essential menu
//      functionality. I've also pulled redo and undo over from the canvas build, so we
//      can utilize those features across the entire tab, not limited to the canvas.
//
//  Notes: This is the foundation of the entire project. Everything is built off of the concept that
//      the assorted panes and canvases are all stacked in a way that allows for efficiency.
//
// ---------------------------------- //
public class TabConstruction extends Tab {
        public Pane canvasPane;
        private ScrollPane scrolling;
        private StackPane stackyCanvas;
        private FunctionalCanvas canvas;
        private String tabTitle;
        private static FileChooser chosenFile;
        private File route;
        private int autoSaveTime;
        private Timer asTimer;  // AutoSaveTimer
        private  TimerTask as;  // AutoSave
        public final static String autosaveLocation = "C:\\Users\\Nick\\Pictures\\";
                // Instead of actually hardcoding the path into the function, I call this string as a reference to a
                // directory. This can be changed to something way more universal by someone much smarter than I.
        private Image asBackup; // AutoSaveBack
        private Timer logTimer;
        private TimerTask log;
        public final static String LogRoute = "log.txt";
        private final static int secondConversion = 1000;

        public TabConstruction(){
                super();
                this.route = null;
                this.canvas = new FunctionalCanvas();
                this.tabTitle = "New Tab";
                develop();
        }
        /*
        public TabConstruction(File file){
                super();
                this.route = null;
                this.canvas = new FunctionalCanvas();           // Forget about this, this didn't do anything I wanted it to.
                this.tabTitle = route.getName();
                this.canvChange = false;
                develop();
        }
         */
        private void develop(){

                this.canvasPane = new Pane(canvas);
                this.stackyCanvas = new StackPane();
                this.stackyCanvas.getChildren().addAll(canvasPane);
                this.scrolling = new ScrollPane(this.stackyCanvas);
                this.setContent(scrolling);

                this.scrolling.setPrefViewportHeight(this.canvas.getHeight()/2);
                this.scrolling.setPrefViewportWidth(this.canvas.getWidth()/2);
                this.setText(tabTitle);

                chosenFile = new FileChooser();
                chosenFile.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Png files (*.png)", "*.png"),
                new FileChooser.ExtensionFilter("Jpg files (*.jpg)", "*.jpg"),
                new FileChooser.ExtensionFilter("Bmp Files (*.bmp)", "*.bmp"));

                this.setOnCloseRequest((Event e) ->{
                        e.consume();
                        popup.exitWarning(WindowBuild.TheStage);
                });
                this.asBackup = null;
                // Timer for automatically saving the canvas. This is quality of life for the user.
                // Timer saves every 60 seconds, or one minute.
                this.autoSaveTime = 60;
                this.asTimer = new Timer();
                this.as = new TimerTask() {
                        @Override
                        public void run() {
                                Platform.runLater(() -> {
                                        asImage();
                                        asTimer.schedule(as, 0, (long) autoSaveTime * secondConversion);
                                });
                        }};
                this.asTimer.schedule(this.as, 12000, (long) this.autoSaveTime * secondConversion);

                // Logging! In the 'log.txt' file, we can see what a user does at any particular moment.
                // Included in the log is the current tool and local time/date on the user's device.
                this.logTimer = new Timer();
                this.log = new TimerTask() {
                        @Override
                        public void run() {
                                Platform.runLater(() -> {
                                        File logFile = new File(LogRoute);
                                        try{
                                                logFile.createNewFile();
                                        }
                                        catch (Exception ex){
                                                System.out.println("'Logging Exception' Catch");
                                        }
                                        try{
                                                FileWriter fileWriter = new FileWriter(LogRoute, true);
                                                BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
                                                bufferedWriter.write(MenuForCanvTools.currTool() + ", " + LocalTime.now() + ", " + LocalDate.now());
                                                bufferedWriter.newLine();
                                                bufferedWriter.close();
                                        }
                                        catch(Exception ex){
                                                System.out.println("'Logging New File Exception' Catch");
                                        }
                                });
                        }};
                this.logTimer.scheduleAtFixedRate(this.log, 6000, 12000);
        }
        public void setFileRoute(File route){this.route = route;}
        public File getFileRoute(){return this.route;}
        public FunctionalCanvas getCanvas(){return this.canvas;}

        // Never got this working, forgot where I left off and couldn't recall where this was headed.
        public void newTabName(){
                if(this.route != null)
                        this.tabTitle = this.route.getName();
                else
                        this.setText(this.tabTitle);
        }
        public void setTitle(String name) {this.tabTitle = tabTitle;}

        // Image "Save As" function, allows us to create a new image under a new image type/name.
        public void imageSaveAs(){
                File route = chosenFile.showSaveDialog(WindowBuild.TheStage);
                this.setFileRoute(route);
                this.saveImage();
        }

        // Saves all changes from the previous save and overwrites.
        public void saveImage(){
                Image saveImage = this.canvas.getArea(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
                try {
                        if (this.route != null) {
                                ImageIO.write(SwingFXUtils.fromFXImage(saveImage, null), "png", this.route);
                                this.setTitle(this.getFileRoute().getName());
                        }}
                catch (IOException ex) {
                        System.out.println(ex.toString());
                }
        }

        // Opens a file already saved on the computer.
        public static void openImage(){
                File route = chosenFile.showOpenDialog(WindowBuild.TheStage);
                TabConstruction createTab;
                createTab = new TabConstruction();
                createTab.canvas.drawImage(route);
                WindowBuild.newTab.getTabs().add(createTab);
                WindowBuild.newTab.getSelectionModel().select(createTab);
        }
        /*
        No clue what I was going to do here. Already have an open image that works with opening files, this is just redundant.
        public static void openImage(File route){
                TabConstruction createTab;
                createTab = new TabConstruction();
                createTab.canvas.drawImage(route);
                WindowBuild.newTab.getTabs().add(createTab);
                WindowBuild.newTab.getSelectionModel().select(createTab);
        }*/

        // Allows me to cast a blank canvas and retain that as the base layer for a new tab.
        public static void openBlankImage(){
                TabConstruction createTab = new TabConstruction();
                WindowBuild.newTab.getTabs().add(createTab);
                WindowBuild.newTab.getSelectionModel().select(createTab);
        }

        // Function responsible for auto saving the canvas as an image to a specific path.
        public void asImage() {
                if (this.route != null) {
                        this.asBackup = this.canvas.getArea(0, 0, this.canvas.getWidth(), this.canvas.getHeight());
                        File autoSaveFile = new File(autosaveLocation + LocalDate.now());
                        try {
                                autoSaveFile.createNewFile();
                                ImageIO.write(SwingFXUtils.fromFXImage(this.asBackup, null), "png", new FileOutputStream(autoSaveFile));
                                System.out.println("asImage Test WORKS");
                        } catch (IOException ex) {
                                System.out.println("asImage Test DOES NOT work");
                        }
                }
        }


        public static void exit(){
                popup.exitWarning(WindowBuild.TheStage);        // Calls on the popup instead of exit; prompts user
        }                                                       //      to exit with a 'yes' or 'no'.
        public void redo(){                     // Called from the original instance of redo (( from "FunctionalCanvas.java" ))
                this.canvas.redo();
        }
        public void undo(){                     // Called from the original instance of undo (( from "FunctionalCanvas.java" ))
                this.canvas.undo();
        }
}