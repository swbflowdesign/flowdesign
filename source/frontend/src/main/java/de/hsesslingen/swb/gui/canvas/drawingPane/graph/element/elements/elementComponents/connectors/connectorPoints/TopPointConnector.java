package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.connectorPoints;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TopPointConnector extends Connector {

    private Group view;
    private Circle point;

    public TopPointConnector(String id, Element element) {

        /*Set id to this connectors*/
        super(id, element);

        connectorOrientation = connectorOrientation.topPoint;

        radius = 8;
        /* create a Group */
        view = new Group();

        /*Create dependency input point (center)*/
        point = new Circle(radius / 2);
        point.setFill(Color.RED);
        point.setStrokeWidth(0);

        /* the view shall be the point */
        view.getChildren().addAll(point);

        /* add the view to the Pane */
        setView(view);
    }

    @Override
    public void setColor(Color color) {
        point.setFill(color);
    }

}
