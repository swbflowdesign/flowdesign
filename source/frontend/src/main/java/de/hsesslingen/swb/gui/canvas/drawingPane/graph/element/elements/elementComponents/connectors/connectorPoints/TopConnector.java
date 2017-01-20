package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.connectorPoints;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.transform.Rotate;

public class TopConnector extends Connector {

    Group view;
    Polygon point;

    public TopConnector(String id, Element element) {

        /*Set id to this connectors*/
        super(id, element);

        connectorOrientation = connectorOrientation.top;

         /* create a Group */
        view = new Group();

        /* Create a Polygon */
        point = new Polygon(radius / 2, 0, radius, radius, 0, radius);
        point.setFill(Color.RED);
        point.getTransforms().add(new Rotate(180, radius / 2, radius / 2));

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
