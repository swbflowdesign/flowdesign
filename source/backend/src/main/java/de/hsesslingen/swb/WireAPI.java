package de.hsesslingen.swb;

import de.hsesslingen.swb.diagram.connector;
import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.wireType;
import de.hsesslingen.swb.diagram.node;

public class WireAPI {

    /*
     * All methods are working with the active diagram stored in the dataStorage!
     * Fallback is searching the correct diagram in the whole project.
     */


    /**
     * Add a wire
     *
     * @param type            Wire type
     * @param sourceNode      Sourece node
     * @param sourceConnector Source target
     * @param targetNode      Target node
     * @param targetConnector Target connector
     * @return ID of the wire
     */
    public static String addWire(wireType type, node sourceNode, connector sourceConnector, node targetNode, connector targetConnector) {

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(DataStorage.activeDiagram.getName());

        return DataStorage.activeDiagram.getActiveVersion().addWire(type, sourceNode, sourceConnector, targetNode, targetConnector);

    }


    /**
     * Remove a wire
     *
     * @param wireID ID of the wire
     */
    public static void removeWire(String wireID) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Remove wire */
        fallbackDiagram.getActiveVersion().removeWire(wireID);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the source
     *
     * @param wireID          ID of the wire
     * @param sourceNode      Source node
     * @param sourceConnector Source connector
     */
    public static void setSource(String wireID, node sourceNode, connector sourceConnector) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set source */
        fallbackDiagram.getActiveVersion().getWire(wireID).setSourceNode(sourceNode);
        fallbackDiagram.getActiveVersion().getWire(wireID).setSourceConnector(sourceConnector);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the target
     *
     * @param wireID          ID of the wire
     * @param targetNode      Target node
     * @param targetConnector Target connector
     */
    public static void setTarget(String wireID, node targetNode, connector targetConnector) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set target */
        fallbackDiagram.getActiveVersion().getWire(wireID).setTargetNode(targetNode);
        fallbackDiagram.getActiveVersion().getWire(wireID).setTargetConnector(targetConnector);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the label
     *
     * @param wireID ID of the wire
     * @param label  Label text
     */
    public static void setLabel(String wireID, String label) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set label */
        fallbackDiagram.getActiveVersion().getWire(wireID).setLabel(label);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the type
     *
     * @param wireID ID of the wire
     * @param type   NodeType
     */
    public static void setType(String wireID, wireType type) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set type */
        fallbackDiagram.getActiveVersion().getWire(wireID).setType(type);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }

}
