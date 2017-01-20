package de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.Wire;
import javafx.scene.shape.Line;

public class ArrowConversionWire extends Wire {

    private Line line;

    public ArrowConversionWire(Graph graph, String id, Connector sourceConnector, Element sourceElement, Connector targetConnector, Element targetElement) {
        super(graph, id, sourceConnector, sourceElement, targetConnector, targetElement);

        /* define the wire Type */
        wireType = wireType.ArrowConversion;

        /* create a Group */
        //group = new Group();

        /* create a new line */
        line = new Line();
        line.setStrokeWidth(3);

        //TODO: add object for ArrowConversion and add label

        /* the view shall be the line */
        getChildren().addAll(line);

        /* add the group to the drawing pane */
        graph.getDrawingPane().getChildren().add(this);


    }

    @Override
    public Line getLine() {
        return line;
    }
}
