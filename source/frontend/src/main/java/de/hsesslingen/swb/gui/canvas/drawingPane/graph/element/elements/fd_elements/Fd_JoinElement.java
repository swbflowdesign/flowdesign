package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.Group;
import javafx.scene.shape.Rectangle;

public class Fd_JoinElement extends Element {
    private Group view;
    private Rectangle join;

    public Fd_JoinElement(String id) {
        super(id);

        /* override Default-Values */
        height = 50;
        width = 4;
        nodeType = nodeType.fd_Join;
        elementColor = nodeColor.Black;

        /* create a Group */
        view = new Group();

        /* create JOIN-Element */
        join = new Rectangle();
        join.setWidth(width);
        join.setHeight(height);

        /* add the components to the Group */
        view.getChildren().addAll(join);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }


    @Override
    public void setNodeColor(nodeColor nodeColor) {
        join.setFill(nodeColorToColor(nodeColor));
        this.elementColor = nodeColor;
        updateColorBackend();
    }

    @Override
    public void setH(double height) {
        this.height = height;
        join.setHeight(height);
        getElementBorder().setH(height);
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

