package de.hsesslingen.swb.gui.canvas.drawingPane;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.mouseGestures.Controller;
import javafx.scene.layout.Pane;

public class DrawingPane extends Pane {

    private Graph graph;
    private ZoomableScrollPane scrollPane;
    private Controller controller;

    public DrawingPane() {

        /* Add a graph to this pane */
        graph = new Graph(this);

        /* Set DrawingPane into a zoomable scrollpane */
        scrollPane = new ZoomableScrollPane(this);
        scrollPane.setPannable(true);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        /* Add mouseGestures */
        controller = new Controller(this);

    }

    /* Getter and Setter */

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    public ZoomableScrollPane getScrollPane() {
        return scrollPane;
    }

    public void setScrollPane(ZoomableScrollPane scrollPane) {
        this.scrollPane = scrollPane;
    }

    public Controller getController() {
        return controller;
    }

    public void setController(Controller controller) {
        this.controller = controller;
    }

}
