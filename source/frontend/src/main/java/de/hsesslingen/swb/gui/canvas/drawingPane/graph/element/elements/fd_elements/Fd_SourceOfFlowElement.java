package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.CircleTemplate;
import javafx.scene.Group;

public class Fd_SourceOfFlowElement extends Element {

    private Group view;
    private CircleTemplate sourceOfFlow;


    public Fd_SourceOfFlowElement(String id) {
        super(id);

        /* Override default values */
        nodeType = nodeType.fd_SourceOfFlow;
        width = 40;
        height = 40;

        /* create a Group */
        view = new Group();

        /* create necessary components */
        sourceOfFlow = new CircleTemplate(width / 2);

        /* place the element */
        //circle is auto centered

        /* add the components to the Group */
        view.getChildren().addAll(sourceOfFlow.getCircle());

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setNodeColor(nodeColor nodeColor) {
        sourceOfFlow.getCircle().setFill(nodeColorToColor(nodeColor));
        this.elementColor = nodeColor;
        updateColorBackend();
    }

    @Override
    public void setH(double height) {
        this.height = height;
        this.width = height;
        sourceOfFlow.setRadius(width / 2);
        getElementBorder().setH(height);
        getElementBorder().setW(height);
        getElementBorder().relocate(-width / 2, -height / 2);
        relocateAllConnectors();
        updateSizeBackend();
    }

    @Override
    public void setW(double width) {
        this.width = width;
        this.height = width;
        sourceOfFlow.setRadius(width / 2);
        getElementBorder().setW(width);
        getElementBorder().setH(width);
        getElementBorder().relocate(-width / 2, -height / 2);
        relocateAllConnectors();
        updateSizeBackend();
    }


    @Override
    public double correctionOfX() {
        return width / 2;
    }

    @Override
    public double correctionOfY() {
        return height / 2;
    }


}
