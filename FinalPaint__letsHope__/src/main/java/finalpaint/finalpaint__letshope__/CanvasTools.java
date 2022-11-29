package finalpaint.finalpaint__letshope__;

import javafx.geometry.Rectangle2D;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;
import java.io.File;

// ---------------------------------- //
//  File: CanvasTools.java
//  Date: 11/27/22
//  Programmer: Nick Bean
//
//
//  Purpose: This is the class housing the functions responsible for drawing specific shapes and tool utilization.
//      Miscellaneous functions at the bottom responsible for getting/setting specific things such as fill & line color.
//
//  Notes: "Fill" was never fully implemented in this version of the project, but had worked in previous versions.
//      This one never came together as I fell behind starting at sprint 5.
//
// ---------------------------------- //

public class CanvasTools extends Canvas {
    private final GraphicsContext gc;
    public CanvasTools(){
        super();
        this.gc = this.getGraphicsContext2D();
        this.gc.setLineCap(StrokeLineCap.ROUND);
    }
    public void drawingLine(double firstX, double firstY, double secondX, double secondY){
        gc.strokeLine(firstX, firstY, secondX, secondY);
    }
    public void drawingRect(double firstX, double firstY, double secondX, double secondY){
        double finalX = (Math.min(firstX, secondX));
        double finalY = (Math.min(firstY, secondY));
        double finalWidth = Math.abs(firstX - secondX);
        double finalHeight = Math.abs(firstY - secondY);
        if(MenuForCanvTools.getFillStatus())
            this.gc.fillRect(finalX, finalY, finalWidth, finalHeight);
        this.gc.strokeRect(finalX, finalY, finalWidth, finalHeight);
    }
    public void drawingEllipse(double firstX, double firstY, double secondX, double secondY){
        double finalX = (Math.min(firstX, secondX));
        double finalY = (Math.min(firstY, secondY));
        double finalWidth = Math.abs(firstX - secondX);
        double finalHeight = Math.abs(firstY - secondY);
        if(MenuForCanvTools.getFillStatus())
            this.gc.fillOval(finalX, finalY, finalWidth, finalHeight);
        this.gc.strokeOval(finalX, finalY, finalWidth, finalHeight);
    }
    public void drawingSquare(double firstX, double firstY, double secondX, double secondY){
        final double angle45 = Math.PI/4.0;
        final int Side = 4;
        double[] sideX = new double[Side];
        double[] sideY = new double[Side];

        double radius = Math.sqrt(Math.pow((firstX - secondX), 2)
                + Math.pow((firstY - secondY), 2));
        for(int i = 0; i < Side; i++){
            sideX[i] = firstX + (radius * Math.cos(((2 * Math.PI * i)/4) + angle45));
            sideY[i] = firstY + (radius * Math.sin(((2 * Math.PI * i)/4) + angle45));
        }
    }
    public void drawingCrazyGon(double firstX, double firstY, double secondX, double secondY, int z){

        double[] sideX = new double[z];
        double[] sideY = new double[z];
        double startAngle = Math.atan2(secondY - firstY, secondX-firstX);
        double radius = Math.sqrt(Math.pow((firstX - secondX), 2) + Math.pow((firstY - secondY), 2));
        for(int i = 0; i < z; i++){
            sideX[i] = firstX + (radius * Math.cos((2*Math.PI*i)/z) + startAngle);
            sideY[i] = secondX + (radius * Math.sin((2*Math.PI*i)/z) + startAngle);
        }
        if(MenuForCanvTools.getFillStatus())
            this.gc.fillPolygon(sideX, sideY, z);
        this.gc.strokePolygon(sideX, sideY, z);
    }

    // Below are all the Getters and Setters for things I plan to use later in the program, across the different files.

    // NOTE - they were later used and called from the "MenuForCanvTools" file, that one makes much more sense. However,
    // I'm keeping a few of the setters here as they're still used elsewhere.
    public void setLineColor(Color color){gc.setStroke(color);}
    public void setFillColor(Color color){gc.setFill(color);}
    public void setFillShape(boolean fillShape){
    }
    public void setLineWidth(double width){this.gc.setLineWidth(width);}
    public void clearCanvas(){
        this.gc.clearRect(0,0, this.getWidth(), this.getHeight());
    }

    // This is how I grab the image within a user-defined space.
    // Used for calculating the 'getPixCol' function, for the 'stackUpdate' function, and a few other places
    // that are responsible for image capture.
    public Image getArea(double firstX, double firstY, double secondX, double secondY){
        SnapshotParameters snap = new SnapshotParameters();
        WritableImage writIm = new WritableImage((int)Math.abs(firstX - secondX), (int)Math.abs(firstY - secondY) );
        snap.setViewport(new Rectangle2D(
                (Math.min(firstX, secondX)),
                (Math.min(firstY, secondY)),
                Math.abs(firstX - secondX),
                Math.abs(firstY - secondY)
        ));
        this.snapshot(snap, writIm);
        return writIm;
    }


    // This is how I use the eye-dropper tool. This is what is done to get the pixel color. Very simple, single-line function :)
    public Color getPixCol(double xCord, double yCord){
        return this.getArea(xCord, yCord, xCord + 1, yCord + 1).getPixelReader().getColor(0, 0);
    }

    // Used thrice in "FunctionalCanvas" to aid the redo/undo image stacking.
    public void drawImage(Image im){
        clearCanvas();
        this.setWidth((im.getWidth()));
        this.setHeight(im.getHeight());
        this.gc.drawImage(im,0,0);
    }
    // Similar to the function above, this is primarily used for the "paste" feature.
    public void drawImageAt(Image im, double x, double y){this.gc.drawImage(im, x, y);}

    // Used to open an image file, not just from a stack. Very important difference.
   public void drawImage(File file){
        if(file != null){
            Image im = new Image(file.toURI().toString());
            this.drawImage(im);
        }
    }
}