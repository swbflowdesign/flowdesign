package de.hsesslingen.swb.gui.canvas.drawingPane.mouseGestures;


import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.Main;
import de.hsesslingen.swb.gui.canvas.drawingPane.DrawingPane;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import javafx.event.EventHandler;
import javafx.scene.Cursor;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;


public class ConnectorGestures {

    private DrawingPane drawingPane;
    private Line line;
    private DragContext dragContext;
    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                /* detect the clicked connector */
                Connector connector = (Connector) event.getSource();

                /* get the element of the connector */
                Element element = connector.getElement();

                /* if the connector is on another element as the selected, remove the selected */
                if (dragContext.selectedElement != element && dragContext.selectedElement != null) {
                    drawingPane.getController().getElementGestures().removeSelectedElement(dragContext.selectedElement);
                }

                /* Safe the Connector and the Element in DragContext */
                dragContext.sourceConnector = connector;
                dragContext.sourceElement = element;

                /* disable the mouseGestures of the Element */
                drawingPane.getController().getElementGestures().removeGestures(element);

                /* change the cursor to an open hand */
                if (connector.getConnectorOrientation() == connectorOrientation.bottom ||
                        connector.getConnectorOrientation() == connectorOrientation.leftPoint ||
                        connector.getConnectorOrientation() == connectorOrientation.rightPoint ||
                        connector.getConnectorOrientation() == connectorOrientation.topPoint ||
                        connector.getConnectorOrientation() == connectorOrientation.bottomPoint
                        ) {
                    Main.getMainScene().setCursor(Cursor.OPEN_HAND);
                }


                /* get the position of connector in the drawingpane */
                double startX = startX(connector.localToParent(element.getBoundsInParent()).getMinX(), element.getElementType(), connector.getRadius());
                double startY = startY(connector.localToParent(element.getBoundsInParent()).getMinY(), element.getElementType(), connector.getRadius());

                /* create a line with the start and end position of connector */
                line = new Line(
                        startX, startY,
                        startX, startY
                );

                line.setStrokeWidth(3);

                /* add the line to the drawingpane */
                drawingPane.getChildren().add(line);

                /* get the actual scale factor */
                double scale = drawingPane.getScrollPane().getScaleValue();

                /* calculate the dragcontext */
                dragContext.x = startX * scale - event.getScreenX();
                dragContext.y = startY * scale - event.getScreenY();

                /* get the connectortype of selected connector */
                connectorOrientation connectorOrientation = connector.getConnectorOrientation();

                /* display the connector points of each element */
                drawingPane.getGraph().getElementModel().getAllElements().forEach(Element -> {
                            if (Element != element) {
                                if (Element.getElementType().toString().contains("fd_") && Element.getX() < (element.getX() - element.getWidth() - Element.getWidth())) {

                                } else {
                                    Element.getConnectors().forEach(Connector -> {
                                        if (connectorOrientation == connectorOrientation.right) {
                                            if (Connector.getConnectorOrientation() == connectorOrientation.left) {
                                                Connector.toFront();
                                                Connector.setVisible(true);
                                                dragGestures(Connector);
                                            }
                                        }

                                    });
                                }

                                if (Element.getElementType().toString().contains("fd_") && Element.getY() < element.getY()) {

                                } else {
                                    Element.getConnectors().forEach(Connector -> {
                                        if (connectorOrientation == connectorOrientation.bottom) {
                                            if (Connector.getConnectorOrientation() == connectorOrientation.top) {
                                                Connector.toFront();
                                                Connector.setVisible(true);
                                                dragGestures(Connector);
                                            }
                                        }
                                    });
                                }


                                Element.getConnectors().forEach(Connector -> {

                                    if (connectorOrientation == connectorOrientation.center) {
                                        if (Connector.getConnectorOrientation() == connectorOrientation.center) {
                                            Connector.toFront();
                                            Connector.setVisible(true);
                                            dragGestures(Connector);
                                        }
                                    }

                                });


                                Element.getConnectors().forEach(Connector -> {

                                    if (connectorOrientation == connectorOrientation.topPoint ||
                                            connectorOrientation == connectorOrientation.bottomPoint ||
                                            connectorOrientation == connectorOrientation.leftPoint ||
                                            connectorOrientation == connectorOrientation.rightPoint
                                            ) {
                                        Main.getMainScene().setCursor(Cursor.OPEN_HAND);
                                        if (Connector.getConnectorOrientation() == connectorOrientation.topPoint ||
                                                Connector.getConnectorOrientation() == connectorOrientation.bottomPoint ||
                                                Connector.getConnectorOrientation() == connectorOrientation.leftPoint ||
                                                Connector.getConnectorOrientation() == connectorOrientation.rightPoint) {
                                            Connector.toFront();
                                            Connector.setVisible(true);
                                            dragGestures(Connector);
                                        }
                                    }

                                });

                            }
                        }
                );

                event.consume();
            }
        }
    };
    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            if (event.getButton().equals(MouseButton.PRIMARY)) {

                /* calculate the offset */
                double offsetX = event.getScreenX() + dragContext.x;
                double offsetY = event.getScreenY() + dragContext.y;

                /* get the actual zoom-factor */
                double scale = drawingPane.getScrollPane().getScaleValue();

                /* include the zoomfactor to the offset */
                offsetX /= scale;
                offsetY /= scale;

                /* change the end of line to the mouse position */
                line.setEndX(offsetX);
                line.setEndY(offsetY);

                line.toBack();
                event.consume();
            }
        }
    };
    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            if (event.getButton().equals(MouseButton.PRIMARY)) {
                /* hide the connectors, except of the selected Element */
                for (Element e : drawingPane.getGraph().getElementModel().getAllElements()) {
                    e.toFront();
                    /* remove the border of all elements, except the selected one*/
                    if (e != dragContext.selectedElement) {
                        drawingPane.getController().getElementGestures().removeBorder(e);

                        for (Connector c : e.getConnectors()) {
                            c.setVisible(false);
                        }
                    }
                }

                /* remove the line */
                drawingPane.getChildren().remove(line);
                line = null;

                /* enable the mouseGestures of the element again */
                if (dragContext.selectedElement == dragContext.sourceElement) {
                    drawingPane.getController().getElementGestures().addClickGestures(dragContext.selectedElement);
                } else {
                    drawingPane.getController().getElementGestures().addGestures(dragContext.sourceElement);
                }


                Main.getMainScene().setCursor(Cursor.DEFAULT);
                event.consume();
            }
        }
    };
    EventHandler<MouseEvent> onDragDetectedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {
            /* enable full drag, so other nodes can be recognized */
            dragContext.sourceConnector.startFullDrag();
            event.consume();
        }
    };

    public ConnectorGestures(DrawingPane drawingPane, DragContext dragContext) {
        this.drawingPane = drawingPane;
        this.dragContext = dragContext;
    }

    public void addGestures(final Connector connector) {
        connector.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        connector.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        connector.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        connector.addEventHandler(MouseEvent.DRAG_DETECTED, onDragDetectedEventHandler);

    }

    public double startX(double x, nodeType type, double radius) {

        switch (type) {

            case fd_Unit:
            case fd_ResourceOval:
            case fd_UnitState:
            case fd_SinkOfFlow:
            case fd_SourceOfFlow:
            case sud_System:
                return x + dragContext.sourceElement.getW() / 2 + radius / 2;

            case fd_Iteration:
                return x + dragContext.sourceElement.getW() / 2 + radius / 2 + 5;


            //return dragContext.sourceElement.getX();

            case sud_AdapterSquare:
            case sud_AdapterTriangle:
            case sud_Database:
            case sud_HumanResource:
                return x + radius / 2;

            default:
                return x + radius / 2;
        }


    }

    public double startY(double y, nodeType type, double radius) {
        switch (type) {

            case fd_Unit:
            case fd_UnitState:
            case fd_ResourceOval:
            case fd_SinkOfFlow:
            case fd_SourceOfFlow:
            case fd_Iteration:
            case sud_System:
                return y + dragContext.sourceElement.getH() / 2 + radius / 2;


            //return dragContext.sourceElement.getY();

            case sud_AdapterSquare:
            case sud_AdapterTriangle:
            case sud_Database:
            case sud_HumanResource:
                return y + radius / 2;


            default:
                return y + radius / 2;
        }
    }

    public void dragGestures(Connector targetConnector) {

        targetConnector.setOnMouseDragEntered(mouseEvent -> {
            if (targetConnector.getConnectorOrientation() == connectorOrientation.top ||
                    targetConnector.getConnectorOrientation() == connectorOrientation.leftPoint ||
                    targetConnector.getConnectorOrientation() == connectorOrientation.rightPoint ||
                    targetConnector.getConnectorOrientation() == connectorOrientation.topPoint ||
                    targetConnector.getConnectorOrientation() == connectorOrientation.bottomPoint
                    ) {
                Main.getMainScene().setCursor(Cursor.CLOSED_HAND);
            }
            targetConnector.setColor(Color.BLUE);
        });

        targetConnector.setOnMouseDragExited(mouseEvent -> {
            //Main.getMainScene().setCursor(Cursor.OPEN_HAND);
            targetConnector.setColor(Color.RED);
        });

        targetConnector.setOnMouseDragReleased(mouseEvent -> {
            dragContext.targetConnector = targetConnector;
            /* enable the mouseGestures of the element again */
            drawingPane.getGraph().getWireModel().addWire(null, dragContext.sourceConnector, dragContext.targetConnector);
        });


    }

}
