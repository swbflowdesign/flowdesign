package de.hsesslingen.swb;

import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.wire;

import java.util.ArrayList;
import java.util.List;

public class ConnectorAPI {

    /*
     * All methods are working with the active diagram stored in the dataStorage by default!
     * Fallback is searching the correct diagram in the whole project.
     */


    /**
     * Add a connector to a node
     *
     * @param nodeID      ID of the Node
     * @param orientation Orientation of the connector
     * @return ID of the connector
     */
    public static String addConnector(String nodeID, connectorOrientation orientation) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return null;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

        return fallbackDiagram.getActiveVersion().getNode(nodeID).addConnector(orientation);

    }


    /**
     * Remove a connector from a node
     *
     * @param nodeID      ID of the Node
     * @param connectorID ID of the connector
     * @return List of affected wires
     */
    public static List<String> removeConnector(String nodeID, String connectorID) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return null;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }

        /* Check for references */
        List<String> affectedWires = new ArrayList<String>();
        for (int i = 0; i < fallbackDiagram.getActiveVersion().getWires().size(); i++) {
            wire tempWire = fallbackDiagram.getActiveVersion().getWires().get(i);

            /* Check the source connector */
            if (tempWire.getSourceConnector().getId().equals(connectorID)) {
                /* Add wire to the list */
                affectedWires.add(tempWire.getId());
                /* Remove Wire */
                WireAPI.removeWire(tempWire.getId());

                continue;
            }

            /* Check the target connector */
            if (tempWire.getTargetConnector().getId().equals(nodeID)) {
                /* Add wire to the list */
                affectedWires.add(tempWire.getId());
                /* Remove Wire */
                WireAPI.removeWire(tempWire.getId());
            }
        }

        /* Remove wire */
        fallbackDiagram.getActiveVersion().getNode(nodeID).removeConnector(connectorID);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());


        return affectedWires;

    }


    /**
     * Set the type of a connector
     *
     * @param nodeID      ID of the Node
     * @param connectorID ID of the connector
     * @param type        new dataType of the connector
     */
    public static void setType(String nodeID, String connectorID, String type) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Update the data type */
        fallbackDiagram.getActiveVersion().getNode(nodeID).getConnector(connectorID).setType(type);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }

}
