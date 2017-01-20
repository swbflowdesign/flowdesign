package de.hsesslingen.swb.gui.canvas.drawingPane.mouseGestures;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;

public class DragContext {

    double x;
    double y;

    /* Source Nodes */
    Connector sourceConnector;
    Element sourceElement;

    /* Target Nodes */
    Connector targetConnector;
    Element targetElement;

    /* MouseOver Element */
    Element tempElement;

    /* Selected Element */
    Element selectedElement = null;
    boolean isDragged = false;
    boolean clickInElement = false;


    public Element getSelectedElement() {
        return selectedElement;
    }

    public void setSelectedElement(Element selectedElement) {
        this.selectedElement = selectedElement;
    }

}
