package finalpaint.finalpaint__letshope__;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import java.util.Stack;

// ---------------------------------- //
//  File: FunctionalCanvas.java
//  Date: 11/27/22
//  Programmer: Nick Bean
//
//
//  Purpose: This class houses the mouse action-event handlers for drawing and tool use. This is also
//      the first instance where redo, undo, and stack updating is implemented. Future uses are called from this file.
//
//  Notes: Extends CanvasTools, carries over the functions responsible for drawing/tool use.
//
// ---------------------------------- //
public class FunctionalCanvas extends CanvasTools {
private double xCord;
private double yCord;
private Image cAp;      //Copy and Paste, hence cAp.
    private final Stack<Image> undoStack;
    private final Stack<Image> redoStack;
    public FunctionalCanvas(){
        super();
        // Initial placement cords are set at zero, so nothing is placed weirdly.
        xCord = 0;
        yCord = 0;
        // Creating the canvas, establishing size, height, width, base case stuff.
        this.setHeight(900);
        this.setWidth(1600);
        this.setFillColor(Color.WHITE);
        this.setLineColor(Color.WHITE);
        this.drawingRect(0,0,this.getWidth(), this.getHeight());
        this.setFillShape(false);

        // Creates the undo and redo stacks, responsible for housing the undo and redo images.
        // By using images, this could allow me to pull from a backup? Will investigate this, but image stack is much
        // better than the alternative.
        this.undoStack = new Stack<>();
        this.redoStack = new Stack<>();
        this.undoStack.push(this.getArea(0,0,this.getWidth(), this.getHeight()));

        // Mouse action events, this shit is vital.
        this.setOnMousePressed(e->{
            xCord = e.getX();
            yCord = e.getY();
            this.setFillColor(MenuForCanvTools.getFillColor());
            this.setLineColor(MenuForCanvTools.getLineColor());
            this.setLineWidth(MenuForCanvTools.getLineWidth());
            this.setFillShape(MenuForCanvTools.getFillStatus());
            switch (MenuForCanvTools.currTool()) {
                case ("Eraser") -> {
                    this.setLineColor(Color.WHITE);
                    this.stackUpdate();
                }
                case ("Line") -> {
                    this.drawingLine(xCord, yCord, xCord, yCord);
                    this.stackUpdate();
                }
                case ("Rectangle") -> {
                    this.drawingRect(xCord, yCord, xCord, yCord);
                    this.stackUpdate();
                }
                case ("Square") -> {
                    this.drawingSquare(xCord, yCord, xCord, yCord);
                    this.stackUpdate();
                }
                case ("Ellipse") -> {
                    this.drawingEllipse(xCord, yCord, xCord, yCord);
                    this.stackUpdate();
                }
                case ("Circle") -> {
                    this.drawingCrazyGon(xCord, yCord, xCord, yCord, 314);
                    this.stackUpdate();
                }
                case ("Multi-gon") -> {
                    this.drawingCrazyGon(xCord, yCord, xCord, yCord, 8);
                    this.stackUpdate();
                }
                case ("Eeydropper") -> {
                    MenuForCanvTools.setLineColor(this.getPixCol(xCord, yCord));
                    MenuForCanvTools.setFillColor((this.getPixCol(xCord, yCord)));
                }
                case ("Copy"), ("Cut") -> {
                    this.setLineWidth(2);
                    this.setLineColor(Color.BLACK);
                    this.drawingRect(xCord, yCord, xCord, yCord);
                    this.setFillShape(false);
                    this.stackUpdate();
                }
                case ("Paste") -> {
                    this.drawImageAt(cAp, e.getX(), e.getY());
                    this.stackUpdate();
                }
            }
        });
        this.setOnMouseDragged(e->{
            switch (MenuForCanvTools.currTool()) {
                case ("Free Draw"), ("Eraser") -> {
                    this.drawingLine(xCord, yCord, e.getX(), e.getY());
                    xCord = e.getX();
                    yCord = e.getY();
                }
                case ("Line") -> {
                    this.undo();
                    this.drawingLine(xCord, yCord, e.getX(), e.getY());
                    this.stackUpdate();
                }
                case ("Rectangle"), ("Cut"), ("Copy") -> {
                    this.undo();
                    this.drawingRect(xCord, yCord, e.getX(), e.getY());
                    this.stackUpdate();
                }
                case ("Square") -> {
                    this.undo();
                    this.drawingSquare(xCord, yCord, e.getX(), e.getY());
                    this.stackUpdate();
                }
                case ("Ellipse") -> {
                    this.undo();
                    this.drawingEllipse(xCord, yCord, e.getX(), e.getY());
                    this.stackUpdate();
                }
                case ("Circle") -> {
                    this.undo();
                    this.drawingCrazyGon(xCord, yCord, e.getX(), e.getY(), 314);
                    this.stackUpdate();
                }
                case ("Multi-gon") -> {
                    this.undo();
                    this.drawingCrazyGon(xCord, yCord, e.getX(), e.getY(), MenuForCanvTools.pGonSides());
                    this.stackUpdate();
                }
                case ("Eeydropper") -> {
                    MenuForCanvTools.setLineColor(this.getPixCol(e.getX(), e.getY()));
                    MenuForCanvTools.setFillColor((this.getPixCol(e.getX(), e.getY())));
                }
                case ("Paste") -> {
                    this.undo();
                    try{this.drawImageAt(cAp, e.getX(), e.getY());}
                    catch(Exception f){System.out.println(e);}
                    this.stackUpdate();
                }
            }
        });
        this.setOnMouseReleased(e->{
            switch (MenuForCanvTools.currTool()) {
                case ("Free Draw"), ("Eraser") -> this.drawingLine(xCord, yCord, e.getX(), e.getY());
                case ("Line") -> {
                    this.undo();
                    this.drawingLine(xCord, yCord, e.getX(), e.getY());
                }
                case ("Rectangle") -> {
                    this.undo();
                    this.drawingRect(xCord, yCord, e.getX(), e.getY());
                }
                case ("Square") -> {
                    this.undo();
                    this.drawingSquare(xCord, yCord, e.getX(), e.getY());
                }
                case ("Ellipse") -> {
                    this.undo();
                    this.drawingEllipse(xCord, yCord, e.getX(), e.getY());
                }
                case ("Circle") -> {
                    this.undo();
                    this.drawingCrazyGon(xCord, yCord, e.getX(), e.getY(), 314);
                }
                case ("Multi-gon") -> {
                    this.undo();
                    this.drawingCrazyGon(xCord, yCord, e.getX(), e.getY(), MenuForCanvTools.pGonSides());
                }
                case ("Eeydropper") -> {
                    MenuForCanvTools.setLineColor(this.getPixCol(xCord, yCord));
                    MenuForCanvTools.setFillColor((this.getPixCol(xCord, yCord)));
                }
                case ("Copy") -> {
                    this.undo();
                    this.cAp = this.getArea(xCord, yCord, e.getX(), e.getY());
                }
                case ("Paste") -> {
                    this.undo();
                    if (this.cAp != null) {
                        this.drawImageAt(cAp, e.getX(), e.getY());
                    }
                }
                case ("Cut") -> {
                    this.undo();
                    this.cAp = this.getArea(xCord, yCord, e.getX(), e.getY());
                    this.setFillShape(true);
                    this.setLineColor(MenuForCanvTools.getFillColor());
                    this.setLineWidth(2);
                    this.drawingRect(xCord, yCord, e.getX(), e.getY());
                }
            }
            this.stackUpdate();
            xCord=0;
            yCord=0;
        });
    }
    // Performs a redo of the users action; shows the saved image in the redo stack.
    public void redo() {
        if (!redoStack.empty()) {
            Image im = redoStack.pop();
            undoStack.push(im);
            this.drawImage(im);
        }}

    // Performs an undo of the users previous action; shows the previous image in the undo stack.
    public void undo(){
        Image im = undoStack.pop();
        if(!undoStack.empty()){
            redoStack.push(im);
            this.drawImage(undoStack.peek());
        }
        else{
            this.drawImage(im);
            undoStack.push(im);
        }}

    // Updates the "stack" type.
    public void stackUpdate(){
        undoStack.push(this.getArea(0, 0, this.getWidth(), this.getHeight()));
        redoStack.clear();
    }
}