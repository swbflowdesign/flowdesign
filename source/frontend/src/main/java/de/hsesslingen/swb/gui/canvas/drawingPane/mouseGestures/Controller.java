package de.hsesslingen.swb.gui.canvas.drawingPane.mouseGestures;


import de.hsesslingen.swb.gui.canvas.drawingPane.DrawingPane;

public class Controller {

    private DrawingPane drawingPane;
    private DrawingPaneGestures drawingPaneGestures;
    private ScrollPaneGestures scrollPaneGestures;
    private ElementGestures elementGestures;
    private WireGestures wireGestures;
    private ConnectorGestures connectorGestures;
    private DragContext dragContext;

    public Controller(DrawingPane drawingPane) {

        this.drawingPane = drawingPane;

        /* Set a drag Context */
        dragContext = new DragContext();

        /* Enable Gestures on drawingPane */
        drawingPaneGestures = new DrawingPaneGestures(drawingPane, dragContext);

        /* Enable Gestures on scrollPane */
        scrollPaneGestures = new ScrollPaneGestures(drawingPane, dragContext);

        /* Enable Gestures on Elements */
        elementGestures = new ElementGestures(drawingPane, dragContext);

        /* Enable Gestures on Wires */
        wireGestures = new WireGestures(drawingPane, dragContext);

        /* Enable Gestures on Connectors */
        connectorGestures = new ConnectorGestures(drawingPane, dragContext);
    }


    /* Getter and Setter */
    public DrawingPane getDrawingPane() {
        return drawingPane;
    }

    public void setDrawingPane(DrawingPane drawingPane) {
        this.drawingPane = drawingPane;
    }

    public DrawingPaneGestures getDrawingPaneGestures() {
        return drawingPaneGestures;
    }

    public void setDrawingPaneGestures(DrawingPaneGestures drawingPaneGestures) {
        this.drawingPaneGestures = drawingPaneGestures;
    }

    public ElementGestures getElementGestures() {
        return elementGestures;
    }

    public void setElementGestures(ElementGestures elementGestures) {
        this.elementGestures = elementGestures;
    }

    public WireGestures getWireGestures() {
        return wireGestures;
    }

    public void setWireGestures(WireGestures wireGestures) {
        this.wireGestures = wireGestures;
    }

    public ConnectorGestures getConnectorGestures() {
        return connectorGestures;
    }

    public void setConnectorGestures(ConnectorGestures connectorGestures) {
        this.connectorGestures = connectorGestures;
    }

    public DragContext getDragContext() {
        return dragContext;
    }

    public void setDragContext(DragContext dragContext) {
        this.dragContext = dragContext;
    }

    public ScrollPaneGestures getScrollPaneGestures() {
        return scrollPaneGestures;
    }

    public void setScrollPaneGestures(ScrollPaneGestures scrollPaneGestures) {
        this.scrollPaneGestures = scrollPaneGestures;
    }
}
