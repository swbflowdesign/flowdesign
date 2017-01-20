package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.border;


import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Border extends Pane {

    private Rectangle recBorder;
    private Node view;

    public Border(double w, double h) {

        /* create a border */
        recBorder = new Rectangle();
        recBorder.setFill(Color.TRANSPARENT);
        recBorder.setStroke(Color.BLUE);
        recBorder.setWidth(w);
        recBorder.setHeight(h);

        /* the view shall be the rectangle */
        view = recBorder;

        /* add the view to the Pane */
        getChildren().add(view);
    }


    public Rectangle getRecBorder() {
        return recBorder;
    }

    public void setRecBorder(Rectangle recBorder) {
        this.recBorder = recBorder;
    }

    public Node getView() {
        return view;
    }

    public void setView(Node view) {
        this.view = view;
    }

    public void setH(double height) {
        recBorder.setHeight(height);
    }

    public void setW(double width) {
        recBorder.setWidth(width);
    }

}
