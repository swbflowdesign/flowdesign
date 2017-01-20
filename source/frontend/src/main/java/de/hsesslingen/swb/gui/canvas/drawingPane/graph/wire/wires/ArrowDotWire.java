package de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires;

import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.Wire;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

public class ArrowDotWire extends Wire {

    private Line line;
    private Circle dot;

    public ArrowDotWire(Graph graph, String id, Connector sourceConnector, Element sourceElement, Connector targetConnector, Element targetElement) {
        super(graph, id, sourceConnector, sourceElement, targetConnector, targetElement);

        /* define the wire Type */
        wireType = wireType.ArrowDot;

        /* create a Group */
        //group = new Group();

        /* create a new line */
        line = new Line();
        line.setStrokeWidth(3);

        /* create a dot */
        dot = new Circle();
        dot.setRadius(4);

        /* Bind the Line of Wire to the connectorPoints */
        if (sourceConnector.getConnectorOrientation() == connectorOrientation.bottom) {
            line.startXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()).add(sourceConnector.getRadius() / 2));
            line.startYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()).add(sourceConnector.getRadius() / 2).add(-(dot.getRadius())));

            line.endXProperty().bind(sourceConnector.layoutXProperty().add(sourceConnector.getParent().layoutXProperty()).add(sourceConnector.getRadius() / 2));
            line.endYProperty().bind(sourceConnector.layoutYProperty().add(sourceConnector.getParent().layoutYProperty()).add(sourceConnector.getRadius() / 2));

            dot.centerXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()).add(sourceConnector.getRadius() / 2));
            dot.centerYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()).add(sourceConnector.getRadius() / 2).add(-(dot.getRadius())));
        } else if (targetConnector.getConnectorOrientation() == connectorOrientation.bottomPoint) {
            line.startXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()));
            line.startYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty().add(sourceConnector.getRadius() / 2)));

            line.endXProperty().bind(sourceConnector.layoutXProperty().add(sourceConnector.getParent().layoutXProperty()));
            line.endYProperty().bind(sourceConnector.layoutYProperty().add(sourceConnector.getParent().layoutYProperty()));

            dot.centerXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()));
            dot.centerYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty().add(sourceConnector.getRadius() / 2)));

        } else if (targetConnector.getConnectorOrientation() == connectorOrientation.topPoint) {
            line.startXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()));
            line.startYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty().add(-(sourceConnector.getRadius() / 2))));

            line.endXProperty().bind(sourceConnector.layoutXProperty().add(sourceConnector.getParent().layoutXProperty()));
            line.endYProperty().bind(sourceConnector.layoutYProperty().add(sourceConnector.getParent().layoutYProperty()));

            dot.centerXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()));
            dot.centerYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty().add(-(sourceConnector.getRadius() / 2))));


        } else if (targetConnector.getConnectorOrientation() == connectorOrientation.rightPoint) {
            line.startXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty().add(sourceConnector.getRadius() / 2)));
            line.startYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()));

            line.endXProperty().bind(sourceConnector.layoutXProperty().add(sourceConnector.getParent().layoutXProperty()));
            line.endYProperty().bind(sourceConnector.layoutYProperty().add(sourceConnector.getParent().layoutYProperty()));

            dot.centerXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty().add(sourceConnector.getRadius() / 2)));
            dot.centerYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()));

        } else if (targetConnector.getConnectorOrientation() == connectorOrientation.leftPoint) {
            line.startXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty().add(-(sourceConnector.getRadius() / 2))));
            line.startYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()));

            line.endXProperty().bind(sourceConnector.layoutXProperty().add(sourceConnector.getParent().layoutXProperty()));
            line.endYProperty().bind(sourceConnector.layoutYProperty().add(sourceConnector.getParent().layoutYProperty()));

            dot.centerXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty().add(-(sourceConnector.getRadius() / 2))));
            dot.centerYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()));

        } else {
            line.startXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()));
            line.startYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()));

            line.endXProperty().bind(sourceConnector.layoutXProperty().add(sourceConnector.getParent().layoutXProperty()));
            line.endYProperty().bind(sourceConnector.layoutYProperty().add(sourceConnector.getParent().layoutYProperty()));

            dot.centerXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()));
            dot.centerYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()));
        }

        /* the view shall be the line */
        getChildren().addAll(line, dot);

        /* add the group to the drawing pane */
        graph.getDrawingPane().getChildren().add(this);

    }

    @Override
    public Line getLine() {
        return line;
    }

    @Override
    public Circle getDot() {
        return dot;
    }
}

