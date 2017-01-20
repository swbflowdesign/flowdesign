package de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires;

import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.Wire;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.WireDataType;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires.wireComponents.Arrow;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.shape.Line;

public class ArrowWire extends Wire {

    private Line line;
    private Arrow arrow;

    public ArrowWire(Graph graph, String id, Connector sourceConnector, Element sourceElement, Connector targetConnector, Element targetElement) {
        super(graph, id, sourceConnector, sourceElement, targetConnector, targetElement);

        /* define the wire Type */
        wireType = wireType.Arrow;

        /* create a Group */
        //group = new Group();

        /* create a new line */
        line = new Line();
        line.setStrokeWidth(3);

        /* Bind the Line of Wire to the connectorPoints */
        line.startXProperty().bind(targetConnector.layoutXProperty().add(targetConnector.getParent().layoutXProperty()).add(sourceConnector.getRadius() / 2));
        line.startYProperty().bind(targetConnector.layoutYProperty().add(targetConnector.getParent().layoutYProperty()).add(sourceConnector.getRadius() / 2));

        line.endXProperty().bind(sourceConnector.layoutXProperty().add(sourceConnector.getParent().layoutXProperty()).add(sourceConnector.getRadius() / 2));
        line.endYProperty().bind(sourceConnector.layoutYProperty().add(sourceConnector.getParent().layoutYProperty()).add(sourceConnector.getRadius() / 2));

        arrow = new Arrow(line);

        //label = "( )";

        dataType = new WireDataType(graph, line, sourceConnector, targetConnector);

        /* the view shall be the line */
        getChildren().addAll(line, arrow, dataType.getDataType());

        /* add the group to the drawing pane */
        graph.getDrawingPane().getChildren().addAll(this);

        /* add changeListener to line */
        addChangeListener();

    }

    /*
        @Override
        public String getLabel() {
            return label;
        }

        @Override
        public void setLabel(String text) {
            label = text;
            dataType.setDataType(text);
        }
    */
    @Override
    public Line getLine() {
        return line;
    }

    @Override
    public Arrow getArrow() {
        return arrow;
    }

    @Override
    public WireDataType getDataType() {
        return dataType;
    }


    private void addChangeListener() {

        /* Add changeListener to the line, to update the arrow rotation*/
        line.startXProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                arrow.update();
                dataType.updatePos();
            }
        });

        line.startYProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                arrow.update();
                dataType.updatePos();
            }
        });

        line.endXProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                arrow.update();
                dataType.updatePos();
            }
        });

        line.endYProperty().addListener(new ChangeListener() {

            @Override
            public void changed(ObservableValue observable, Object oldValue, Object newValue) {
                arrow.update();
                dataType.updatePos();
            }
        });

        targetConnector.getDataTypeProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                dataType.updateData();
                dataType.updatePos();
            }
        });

        sourceConnector.getDataTypeProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                dataType.updateData();
                dataType.updatePos();
            }
        });

    }

}
