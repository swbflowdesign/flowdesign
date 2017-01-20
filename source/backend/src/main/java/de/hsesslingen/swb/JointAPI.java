package de.hsesslingen.swb;

import de.hsesslingen.swb.diagram.diagram;

public class JointAPI {

    /*
     * All methods are working with the active diagram stored in the dataStorage!
     * Fallback is searching the correct diagram in the whole project.
     */


    /**
     * Add a joint to a wire
     *
     * @param wireID ID of the wire
     * @param x      X position
     * @param y      Y position
     * @return ID of the joint
     */
    public static String addJoint(String wireID, double x, double y) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return null;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

        return fallbackDiagram.getActiveVersion().getWire(wireID).addJoint(x, y);

    }


    /**
     * Remove a joint from a wire
     *
     * @param wireID  ID of the wire
     * @param jointID ID of the joint
     */
    public static void removeJoint(String wireID, String jointID) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Remove joint */
        fallbackDiagram.getActiveVersion().getWire(wireID).removeJoint(jointID);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the position of a joint
     *
     * @param wireID  ID of the wire
     * @param jointID ID of the joint
     * @param x       X position
     * @param y       Y position
     */
    public static void setPosition(String wireID, String jointID, double x, double y) {

        /* If wire is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getWire(wireID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByWire(wireID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set position */
        fallbackDiagram.getActiveVersion().getWire(wireID).getJoint(jointID).setX(x);
        fallbackDiagram.getActiveVersion().getWire(wireID).getJoint(jointID).setY(y);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }

}
