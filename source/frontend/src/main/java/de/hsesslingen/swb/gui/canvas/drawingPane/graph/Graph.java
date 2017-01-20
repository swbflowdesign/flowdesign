package de.hsesslingen.swb.gui.canvas.drawingPane.graph;

import de.hsesslingen.swb.gui.canvas.drawingPane.DrawingPane;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.ElementModel;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.WireModel;

public class Graph {

    private DrawingPane drawingPane;
    private ElementModel elementModel;
    private WireModel wireModel;


    public Graph(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;

        /* Create a Element-Model for this Graph */
        elementModel = new ElementModel(this);

        /* Create a Wire-Model for this Graph */
        wireModel = new WireModel(this);

    }


    /* Getter and Setter */

    public DrawingPane getDrawingPane() {
        return drawingPane;
    }

    public void setDrawingPane(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    public ElementModel getElementModel() {
        return elementModel;
    }

    public void setElementModel(ElementModel elementModel) {
        this.elementModel = elementModel;
    }

    public WireModel getWireModel() {
        return wireModel;
    }

    public void setWireModel(WireModel wireModel) {
        this.wireModel = wireModel;
    }

}
