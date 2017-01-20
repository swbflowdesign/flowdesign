package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements;

import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.CircleTemplate;
import javafx.scene.Group;
import javafx.scene.shape.Line;

public class Fd_SinkOfFlowElement extends Element {

    private Group view;
    private CircleTemplate sinkOfFlow;
    private Line line1;
    private Line line2;

    public Fd_SinkOfFlowElement(String id) {
        super(id);

        /* Override default values */
        nodeType = nodeType.fd_SinkOfFlow;
        width = 40;
        height = 40;

        /* create a Group */
        view = new Group();

        /* create necessary components */
        sinkOfFlow = new CircleTemplate(width / 2);
        line1 = new Line();
        line2 = new Line();

        line1.setStartX(-width / 2);
        line1.setStartY(0);

        line1.setEndX(width / 2);
        line1.setEndY(0);

        line2.setStartX(-width / 2);
        line2.setStartY(0);

        line2.setEndX(width / 2);
        line2.setEndY(0);

        line1.setRotate(-45);
        line2.setRotate(45);



        /* place the element */
        //circle is auto centered

        /* add the components to the Group */
        view.getChildren().addAll(sinkOfFlow.getCircle(), line1, line2);

        /* add the Group to a Node and add it as children of the Element */
        setView(view);
    }

    @Override
    public void setNodeColor(nodeColor nodeColor) {
        sinkOfFlow.getCircle().setFill(nodeColorToColor(nodeColor));
        this.elementColor = nodeColor;
        updateColorBackend();
    }

    @Override
    public void setH(double height) {
        this.height = height;
        this.width = height;
        sinkOfFlow.setRadius(width / 2);

        line1.setStartX(-width / 2);
        line1.setStartY(0);

        line1.setEndX(width / 2);
        line1.setEndY(0);

        line2.setStartX(-width / 2);
        line2.setStartY(0);

        line2.setEndX(width / 2);
        line2.setEndY(0);

        line1.setRotate(-45);
        line2.setRotate(45);


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
        sinkOfFlow.setRadius(width / 2);

        line1.setStartX(-width / 2);
        line1.setStartY(0);

        line1.setEndX(width / 2);
        line1.setEndY(0);

        line2.setStartX(-width / 2);
        line2.setStartY(0);

        line2.setEndX(width / 2);
        line2.setEndY(0);

        line1.setRotate(-45);
        line2.setRotate(45);

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
