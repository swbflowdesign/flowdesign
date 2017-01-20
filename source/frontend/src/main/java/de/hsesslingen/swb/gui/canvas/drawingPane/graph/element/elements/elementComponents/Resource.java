package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Resource {

    Polygon view;
    double width;
    double height;

    public Resource() {

        //Set height and width
        height = 13;
        width = 13;

        //Create new element
        view = new Polygon(width / 2, 0, width, height, 0, height);
        view.setStroke(Color.BLACK);
        view.setFill(Color.WHITE);

    }

    /*Getter*/
    public Polygon getView() {
        return view;
    }

    /*Setter*/
    public void setView(Polygon view) {
        this.view = view;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }


}
