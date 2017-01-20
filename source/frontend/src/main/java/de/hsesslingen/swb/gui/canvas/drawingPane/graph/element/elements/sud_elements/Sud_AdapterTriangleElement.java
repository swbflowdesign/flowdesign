package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.sud_elements;

import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class Sud_AdapterTriangleElement extends Element {

    private Group view;
    private Polygon adapterTriangle;

    public Sud_AdapterTriangleElement(String id) {
        super(id);

        /* Override Default values */
        nodeType = nodeType.sud_AdapterTriangle;

        /* create a group */
        view = new Group();

        /* create the resource element */
        adapterTriangle = new Polygon(width / 2, 0, width, height, 0, height);
        adapterTriangle.setStroke(Color.BLACK);
        adapterTriangle.setFill(Color.WHITE);

        /* add the components to the Group */
        view.getChildren().addAll(adapterTriangle);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }


    @Override
    public void setNodeColor(nodeColor nodeColor) {
        this.elementColor = nodeColor;
        adapterTriangle.setFill(nodeColorToColor(nodeColor));
        updateColorBackend();
    }


    @Override
    public void setH(double height) {
        this.height = height;
        adapterTriangle.getPoints().setAll(width / 2, 0.0, width, height, 0.0, height);
        getElementBorder().setH(height);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        adapterTriangle.getPoints().setAll(width / 2, 0.0, width, height, 0.0, height);
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
