package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.sud_elements;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sud_AdapterSquareElement extends Element {

    private Group view;
    private Rectangle adapterSquare;

    public Sud_AdapterSquareElement(String id) {
        super(id);

        /* override Default-Values */
        height = 30;
        width = 30;
        nodeType = nodeType.sud_AdapterSquare;

        /* creating a group */
        view = new Group();

        /* create SPLIT-Element */
        adapterSquare = new Rectangle();
        adapterSquare.setFill(Color.BLACK);
        adapterSquare.setStroke(Color.BLACK);
        adapterSquare.setWidth(width);
        adapterSquare.setHeight(height);

        /* add the components to the Group */
        view.getChildren().addAll(adapterSquare);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setH(double height) {
        this.height = height;
        adapterSquare.setHeight(height);
        getElementBorder().setH(height);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        adapterSquare.setWidth(width);
        getElementBorder().setW(width);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public double getCenterX() {
        return width / 2;
    }

    @Override
    public double getCenterY() {
        return height / 2;
    }

}
