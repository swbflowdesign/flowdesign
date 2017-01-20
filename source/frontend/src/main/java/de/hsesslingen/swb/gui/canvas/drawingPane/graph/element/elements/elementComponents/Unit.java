package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents;

import javafx.scene.paint.Color;
import javafx.scene.shape.Ellipse;

public class Unit {

    Ellipse view;
    double height;
    double width;

    public Unit() {

        /*Set height and width*/
        height = 50;
        width = 100;

        /*Create new element*/
        view = new Ellipse();
        view.setRadiusX(width / 2);
        view.setRadiusY(height / 2);
        view.setFill(Color.WHITE);
        view.setStroke(Color.BLACK);
    }

    /*Getter*/

    /**
     * get the view
     *
     * @return view
     */
    public Ellipse getView() {
        return view;
    }

    /**
     * set the view
     *
     * @param view
     */
    public void setView(Ellipse view) {
        this.view = view;
    }

    /**
     * get the height of the unit
     *
     * @return height of unit
     */
    public double getH() {
        return height;
    }


    /*Setter*/

    /**
     * set the height and display it on screen
     *
     * @param height
     */
    public void setH(double height) {
        this.height = height;
        view.setRadiusY(height / 2);
    }

    /**
     * get the width of the unit
     *
     * @return width of unit
     */
    public double getW() {
        return width;
    }

    /**
     * set the width and display it on screen
     *
     * @param width
     */
    public void setW(double width) {
        this.width = width;
        view.setRadiusX(width / 2);
    }
}
