package de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire;


import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.connector;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.enums.wireType;
import de.hsesslingen.swb.diagram.node;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires.ArrowDotWire;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires.ArrowWire;

import java.util.ArrayList;
import java.util.List;

public class WireModel {

    List<Wire> tempAllWires = new ArrayList<>();
    private List<Wire> allWires;
    private Graph graph;

    public WireModel(Graph graph) {
        this.graph = graph;
        allWires = new ArrayList<>();

    }

    public List<Wire> getAllWires() {
        return allWires;
    }

    /* Add an wire to the list of all wire */
    private void addWire(Wire w) {
        allWires.add(w);
    }

    private void removeWireFromList(Wire w) {
        allWires.remove(w);
    }

    public void removeWire(Wire wire) {

         /* remove the wire from backend*/
        BackendAPI.wire().removeWire(wire.getWireId());

        /* remove the wire form  allWires List */
        removeWireFromList(wire);

        /* set the connected status of the two connectors Connector */
        wire.getSourceConnector().setConnected(checkSourceConnectorConnection(wire));
        wire.getTargetConnector().setConnected(checkTargetConnectorConnection(wire));

        /* adds the left and right connector of a portal when the last wire get's removed */
        if (wire.getSourceConnector().getElement().getElementType() == nodeType.fd_Portal) {
            wire.getSourceConnector().getElement().addPortalConnector();
        } else if (wire.getTargetConnector().getElement().getElementType() == nodeType.fd_Portal) {
            wire.getTargetConnector().getElement().addPortalConnector();
        }

        /* remove the wire children*/
        wire.getChildren().clear();

        /* remove the empty group */
        graph.getDrawingPane().getChildren().remove(wire);

        /* set wire equal to null */
        wire = null;
    }

    public void addWire(String id, Connector sourceConnector, Connector targetConnector) {

        /* check if there is a existing connection between the elements */
        if (!checkConnection(sourceConnector, targetConnector)) {
            return;
        }

        /* mark the two connectors as connected */
        sourceConnector.setConnected(true);
        targetConnector.setConnected(true);

        /* delete the connector of a Portal on Wire connection */
        if (sourceConnector.getElement().getElementType() == nodeType.fd_Portal) {
            sourceConnector.getElement().removePortalConnector();
        } else if (targetConnector.getElement().getElementType() == nodeType.fd_Portal) {
            targetConnector.getElement().removePortalConnector();
        }

        if (id == null) {
            /* if there is no id, get one */
            id = getID(sourceConnector, targetConnector);
        }

        /* create the wire */
        createWire(id, sourceConnector, targetConnector);


    }

    private String getID(Connector sourceConnector, Connector targetConnector) {
        /* we need a UUID, so we fetch it from the Backend */
        node sourceNode = DataStorage.activeDiagram.getActiveVersion().getNode(sourceConnector.getElement().getElementId());
        node targetNode = DataStorage.activeDiagram.getActiveVersion().getNode(targetConnector.getElement().getElementId());
        connector sConnector = DataStorage.activeDiagram.getActiveVersion().getNode(sourceConnector.getElement().getElementId()).getConnector(sourceConnector.getConnectorId());
        connector tConnector = DataStorage.activeDiagram.getActiveVersion().getNode(targetConnector.getElement().getElementId()).getConnector(targetConnector.getConnectorId());

        //create a arrowWire
        if (sourceConnector.getConnectorOrientation() == connectorOrientation.right) {

            /* get the UUID from Backend */
            return BackendAPI.wire().addWire(wireType.Arrow, sourceNode, sConnector, targetNode, tConnector);
        }

        //create a arrowDotWire
        if (sourceConnector.getConnectorOrientation() == connectorOrientation.bottom ||
                sourceConnector.getConnectorOrientation() == connectorOrientation.center ||
                targetConnector.getConnectorOrientation() == connectorOrientation.topPoint ||
                targetConnector.getConnectorOrientation() == connectorOrientation.bottomPoint ||
                targetConnector.getConnectorOrientation() == connectorOrientation.leftPoint ||
                targetConnector.getConnectorOrientation() == connectorOrientation.rightPoint) {

            /* get the UUID from Backend */
            return BackendAPI.wire().addWire(wireType.ArrowDot, sourceNode, sConnector, targetNode, tConnector);
        }
        return null;
    }


    public void completeWire(Wire wire, Connector sourceConnector, Connector targetConnector) {

        /* add the new Wire to the wire list */
        addWire(wire);

        /* add mouse gestures to the Wire */
        graph.getDrawingPane().getController().getWireGestures().addGestures(wire);

        /* set the Connectors to the front */
        sourceConnector.getElement().toFront();
        targetConnector.getElement().toFront();

    }


    public void removeWiresOfElement(Element element) {

        for (Wire wire : allWires) {
            if (wire.getSourceElement() == element || wire.getTargetElement() == element) {

                /* remove the wire from backend */
                BackendAPI.wire().removeWire(wire.getWireId());



                /* add the wires to be removed to the tempAllWires List */
                tempAllWires.add(wire);

            }
        }

        allWires.removeAll(tempAllWires);

        for (Wire wire : tempAllWires) {

            /* check if there still is a connection on the Connector */
            wire.getSourceConnector().setConnected(checkSourceConnectorConnection(wire));
            wire.getTargetConnector().setConnected(checkTargetConnectorConnection(wire));

            /* remove the from pane */
            graph.getDrawingPane().getChildren().removeAll(wire);

            /* clear the wire */
            wire.getChildren().clear();

            /* remove the wire */
            wire = null;
        }

        tempAllWires.clear();
    }

    public void createWire(String id, Connector sourceConnector, Connector targetConnector) {
        //create a arrowWire
        if (sourceConnector.getConnectorOrientation() == connectorOrientation.right) {

            /* create a wire */
            ArrowWire arrowWire = new ArrowWire(graph, id, sourceConnector, sourceConnector.getElement(), targetConnector, targetConnector.getElement());
            completeWire(arrowWire, sourceConnector, targetConnector);
        }

        //create a arrowDotWire
        if (sourceConnector.getConnectorOrientation() == connectorOrientation.bottom ||
                targetConnector.getConnectorOrientation() == connectorOrientation.center ||
                targetConnector.getConnectorOrientation() == connectorOrientation.topPoint ||
                targetConnector.getConnectorOrientation() == connectorOrientation.bottomPoint ||
                targetConnector.getConnectorOrientation() == connectorOrientation.leftPoint ||
                targetConnector.getConnectorOrientation() == connectorOrientation.rightPoint) {

            /* create a wire */
            ArrowDotWire arrowDotWire = new ArrowDotWire(graph, id, sourceConnector, sourceConnector.getElement(), targetConnector, targetConnector.getElement());
            completeWire(arrowDotWire, sourceConnector, targetConnector);
        }
    }


    private boolean checkConnection(Connector sourceConnector, Connector targetConnector) {
        /* check if the two elements already connected */
        for (Wire wire : allWires) {
            if (wire.getSourceElement() == sourceConnector.getElement() && wire.getTargetElement() == targetConnector.getElement()) {
                return false;
            }

            if (wire.getSourceElement() == targetConnector.getElement() && wire.getTargetElement() == sourceConnector.getElement()) {
                return false;
            }
        }
        return true;
    }


    public boolean checkTargetConnectorConnection(Wire wire) {
        for (Wire w : allWires) {
            if (w.getTargetConnector() == wire.getTargetConnector()) {
                return true;
            }
        }
        return false;
    }

    public boolean checkSourceConnectorConnection(Wire wire) {
        for (Wire w : allWires) {
            if (w.getSourceConnector() == wire.getSourceConnector()) {
                return true;
            }
        }
        return false;
    }
}
