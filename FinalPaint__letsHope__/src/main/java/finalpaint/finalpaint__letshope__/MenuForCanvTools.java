package finalpaint.finalpaint__letshope__;

import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.scene.control.*;
import javafx.scene.paint.Color;

// ---------------------------------- //
//  File: MenuForCanvTools
//  Date: 11/27/22
//  Programmer: Nick Bean
//
//  Purpose: This file is responsible for establishing the bottom toolbar and
//      initial conditions. On top of that, we have smaller functions to stream smaller pieces of data i.e., line
//      width, color, fill status, and a custom polygon's # of sides.
//
//  Notes: I think I would go back and mess with the array systems. They're efficient, but I think I could improve
//      how I work with arrays in the future and how to use them better. Just a small criticism for myself.
//
// ---------------------------------- //

public class MenuForCanvTools extends ToolBar{
    private static ColorPicker fillCP;
    private static ColorPicker lineCP;
    private static CheckBox setFill;
    private static int currWidth;
    private static int currTool;
    private static ComboBox<Integer> WidthBox;
    private static ComboBox<String> DrawTool;
    private static int nSidePoly;
    private static TextField sideNum;
    private final static String[] dTools = {"None", "Eraser", "Line", "Free Draw", "Rectangle",
            "Square", "Ellipse", "Circle", "Multi-gon", "Eyedropper", "Cut", "Copy", "Paste"
    };
    private final static Integer[] dWidth = {2, 4, 6, 8, 10, 12, 14, 16, 18, 20, 25, 50, 75, 100, 200};

    public MenuForCanvTools(){
        super();
        // Initializing everything, listing the starting tools, width, and base polygon sides.
        currWidth = 2;
        currTool = 0;
        nSidePoly = 6;
        WidthBox = new ComboBox<>(FXCollections.observableArrayList(dWidth));
        DrawTool = new ComboBox<>(FXCollections.observableArrayList(dTools));
        sideNum = new TextField("6");
        fillCP = new ColorPicker();
        lineCP = new ColorPicker();
        setFill = new CheckBox();

        // Names all the available options for tools and their associated boxes/widgets.
        Label canv_tools = new Label("Canvas Tools");
        Label line_color = new Label("Line Color");
        Label fill_color = new Label("Fill Color");
        Label line_width = new Label("Line Width");
        Label fill = new Label("Fill");
        getItems().addAll(
                canv_tools, DrawTool, sideNum, new Separator(),
                line_color, lineCP,
                fill_color, fillCP, new Separator(),
                line_width, WidthBox, new Separator(),
                fill, setFill
        );

        //Formatting for the toolbar. Sets default values.
        lineCP.setValue(Color.BLACK);
        fillCP.setValue(Color.WHITE);
        DrawTool.setValue("None");
        WidthBox.setEditable(true);
        WidthBox.setPrefWidth(75);
        WidthBox.setValue(2);
        sideNum.setPrefWidth(45);

        // Determines which tool is being used when selected from the available list.
        // The polygon-side box is hidden when the multi-sided polygon isn't in use.
        DrawTool.getSelectionModel().selectedIndexProperty().addListener((observable, val, newVal) ->{
            currTool = newVal.intValue();
            sideNum.setVisible(dTools[currTool].equals("Multi-gon"));
        });
        WidthBox.getEditor().focusedProperty().addListener((obs, wasFoc, isFoc) -> {
            if(isFoc){
                if(Integer.parseInt(WidthBox.getEditor().getText()) >= 1 ) {
                    WidthBox.setValue(Integer.parseInt(WidthBox.getEditor().getText()));
                    }
                else{
                    WidthBox.setValue(0);
                }
            }
        });
        // Listeners for the width box and the polygon-side box.
        WidthBox.setOnAction((ActionEvent e) ->{
            currWidth = WidthBox.getValue();
        });
        sideNum.textProperty().addListener((observable, val, newVal) -> {
            if(Integer.parseInt(newVal) >= 3){
                nSidePoly = Integer.parseInt(newVal);
            }
            else{
                sideNum.setText("3");
            }
        });

        // Tooltips for all presented tools!
        switch (MenuForCanvTools.currTool()) {
            case ("Eraser") -> {
                Tooltip eraserTip = new Tooltip("Erases and clears up any mistakes.");
                DrawTool.setTooltip(eraserTip);
            }
            case ("Line") -> {
                Tooltip lineTip = new Tooltip("Click and drag for a straight line.");
                DrawTool.setTooltip(lineTip);
            }
            case ("Rectangle") -> {
                Tooltip rectTip = new Tooltip("Click and drag for a rectangle.");
                DrawTool.setTooltip(rectTip);
            }
            case ("Square") -> {
                Tooltip squareTip = new Tooltip("Click and drag for a square.");
                DrawTool.setTooltip(squareTip);
            }
            case ("Ellipse") -> {
                Tooltip EllTip = new Tooltip("Click and drag for an ellipse");
                DrawTool.setTooltip(EllTip);
            }
            case ("Circle") -> {
                Tooltip circTip = new Tooltip("Click and drag for a circle.");
                DrawTool.setTooltip(circTip);
            }
            case ("Multi-gon") -> {
                Tooltip nGonTip = new Tooltip("Click and drag for a multi-sided polygon");
                DrawTool.setTooltip(nGonTip);
            }
            case ("Eeydropper") -> {
                Tooltip eyeTip = new Tooltip("Click for a particular pixel color.");
                DrawTool.setTooltip(eyeTip);
            }
            case ("Copy") -> {
                Tooltip copyTip = new Tooltip("Click and drag to copy a part of your canvas.");
                DrawTool.setTooltip(copyTip);
            }
            case ("Paste") -> {
                Tooltip pasteTip = new Tooltip("Click to paste the copied image.");
                DrawTool.setTooltip(pasteTip);
            }
            case ("Cut") -> {
                Tooltip cutTip = new Tooltip("Click and drag to select part of your canvas. This will be deleted once released.");
                DrawTool.setTooltip(cutTip);
            }
        }

    }

    // These are essential in the "FunctionalCanvas.java" class, used to establish the following:
    //      Line width, line color, fill status, current tool, and the number of polygon sides. **ESSENTIAL**.
    public static int pGonSides(){return nSidePoly;}
    public static void setLineColor(Color color){ lineCP.setValue(color);}
    public static Color getLineColor(){return lineCP.getValue();}
    public static void setFillColor(Color color){fillCP.setValue(color);}
    public static Color getFillColor(){return fillCP.getValue();}
    public static int getLineWidth(){ return currWidth;}
    public static boolean getFillStatus(){return setFill.isSelected();}
    public static String currTool(){return dTools[currTool];}

}