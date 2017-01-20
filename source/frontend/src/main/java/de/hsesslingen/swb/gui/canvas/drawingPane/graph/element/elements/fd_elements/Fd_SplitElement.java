package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Fd_SplitElement extends Element {

    private Group view;
    private Rectangle split;

    public Fd_SplitElement(String id) {
        super(id);

        /* override Default-Values */
        height = 30;
        width = 30;
        nodeType = nodeType.fd_Split;

        /* creating a group */
        view = new Group();

        /* create SPLIT-Element */
        split = new Rectangle();
        split.setFill(Color.WHITE);
        split.setStroke(Color.BLACK);
        split.setWidth(width);
        split.setHeight(height);

        /* add the components to the Group */
        view.getChildren().addAll(split);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setNodeColor(nodeColor nodeColor) {
        split.setFill(nodeColorToColor(nodeColor));
        this.elementColor = nodeColor;
        updateColorBackend();
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
