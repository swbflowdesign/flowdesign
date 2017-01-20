package de.hsesslingen.swb.gui.canvas;

import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.*;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.canvas.drawingPane.DrawingPane;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import javafx.scene.layout.BorderPane;

public class CanvasSpace {

    BorderPane root;
    DrawingPane drawingPane;

    public CanvasSpace() {

        root = new BorderPane();
        drawingPane = new DrawingPane();
        root.setCenter(drawingPane.getScrollPane());

    }

    public BorderPane getCanvasSpace() {
        return root;

    }

    public DrawingPane getDrawingPane() {
        return drawingPane;
    }


    /**
     * Render a diagram
     *
     * @param name Name of the diagram
     */
    public void renderDiagram(String name) {

        /* Get the diagram */
        diagram diag = DataStorage.project.getDiagram(name);
        version vers = diag.getActiveVersion();

        /* Render nodes */
        for (node tempNode : vers.getNodes()) {

            /* Add element */
            Element tempElem;
            if (tempNode.getType() == nodeType.dd_Image) {
                tempElem = this.drawingPane.getGraph().getElementModel().addElement(tempNode.getId(), tempNode.getType(), tempNode.getX(), tempNode.getY(), tempNode.getImage(), false);
            } else {
                tempElem = this.drawingPane.getGraph().getElementModel().addElement(tempNode.getId(), tempNode.getType(), tempNode.getX(), tempNode.getY(), null, false);
            }
            tempElem.relocate(tempElem.getX() + tempElem.getCenterX(), tempElem.getY() + tempElem.getCenterY());

            /* Set height and width */
            Double height = tempNode.getHeight();
            Double width = tempNode.getWidth();
            tempElem.setH(height);
            tempElem.setW(width);

            /* Set color */
            tempElem.setNodeColor(tempNode.getColor());

            /* Set label */
            tempElem.setLabel(tempNode.getLabel());

            /* Set text */
            tempElem.setText(tempNode.getText());

            /* Set link */
            tempElem.setLink(tempNode.getLink());

            /* Set image */
            tempElem.setImage(tempNode.getImage());

            /* Set additional */
            tempElem.setAdditional(tempNode.getAdditional());

            /* Set attribute */
            tempElem.setNodeAttribut(tempNode.getAttribut());


            /* Set connectors */
            for (connector tempConn : tempNode.getConnectors()) {
                if (tempConn.getOrientation() == connectorOrientation.top) {
                    // Top
                    tempElem.addTopConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.right) {
                    // Right
                    tempElem.addRightConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.bottom) {
                    // Bottom
                    tempElem.addBottomConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.left) {
                    // Left
                    tempElem.addLeftConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.center) {
                    // Center
                    tempElem.addCenterConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.topPoint) {
                    // TopPoint
                    tempElem.addTopPointConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.bottomPoint) {
                    // BottomPoint
                    tempElem.addBottomPointConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.leftPoint) {
                    // LeftPoint
                    tempElem.addLeftPointConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else if (tempConn.getOrientation() == connectorOrientation.rightPoint) {
                    // RightPoint
                    tempElem.addRightPointConnector(tempConn.getId());
                    tempElem.getConnector(tempConn.getId()).setDataType(tempConn.getType());
                } else {
                    throw new UnsupportedOperationException("Unsupported connector orientation: " + tempConn.getOrientation().toString());
                }
            }

            /* Hide all connectors */
            for (Connector tempConn : tempElem.getConnectors()) {
                tempConn.setVisible(false);
            }

        }

        /* Render wires */
        for (wire tempWire : vers.getWires()) {

            /* Add wire */
            Connector tempSourceConn = this.drawingPane.getGraph().getElementModel().getElement(tempWire.getSourceNode().getId()).getConnector(tempWire.getSourceConnector().getId());
            Connector tempTargetConn = this.drawingPane.getGraph().getElementModel().getElement(tempWire.getTargetNode().getId()).getConnector(tempWire.getTargetConnector().getId());
            this.drawingPane.getGraph().getWireModel().addWire(tempWire.getId(), tempSourceConn, tempTargetConn);

        }

    }


    /**
     * returns the active (selected) element in the canvas
     *
     * @return active Element
     */
    public Element getActiveElement() {
        return drawingPane.getController().getDragContext().getSelectedElement();
    }

    /**
     * set a Element as active (selected)
     *
     * @param element
     */
    public void setActiveElement(Element element) {
        if (getActiveElement() != null) {
            drawingPane.getController().getElementGestures().removeSelectedElement(getActiveElement());
        }
        drawingPane.getController().getElementGestures().setSelectedElement(element);
    }
}
