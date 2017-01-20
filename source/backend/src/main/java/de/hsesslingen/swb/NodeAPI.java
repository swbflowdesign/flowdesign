package de.hsesslingen.swb;

import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.nodeAttribut;
import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.wire;

import java.util.ArrayList;
import java.util.List;

public class NodeAPI {

    /*
     * All methods are working with the active diagram stored in the dataStorage!
     * Fallback is searching the correct diagram in the whole project.
     */


    /**
     * Add a node
     *
     * @param type Diagram type
     * @return ID of the node
     */
    public static String addNode(nodeType type) {

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(DataStorage.activeDiagram.getName());

        return DataStorage.activeDiagram.getActiveVersion().addNode(type);

    }


    /**
     * Remove a node
     *
     * @param nodeID ID of the node
     * @return List of affected wires
     */
    public static List<String> removeNode(String nodeID) {

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

            /* Check the source node */
            if (tempWire.getSourceNode().getId().equals(nodeID)) {
                /* Add wire to the list */
                affectedWires.add(tempWire.getId());
                /* Remove Wire */
                WireAPI.removeWire(tempWire.getId());

                continue;
            }

            /* Check the target node */
            if (tempWire.getTargetNode().getId().equals(nodeID)) {
                /* Add wire to the list */
                affectedWires.add(tempWire.getId());
                /* Remove Wire */
                WireAPI.removeWire(tempWire.getId());
            }
        }

        /* Remove node */
        fallbackDiagram.getActiveVersion().removeNode(nodeID);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());


        return affectedWires;

    }


    /**
     * Set the label of a node
     *
     * @param nodeID Id of the node
     * @param label  Label
     */
    public static void setLabel(String nodeID, String label) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set label */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setLabel(label);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the text of a node
     *
     * @param nodeID Id of the node
     * @param text   Text
     */
    public static void setText(String nodeID, String text) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set text */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setText(text);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the attribute of a node
     *
     * @param nodeID    Id of the node
     * @param attribute Attribute
     */
    public static void setAttribute(String nodeID, nodeAttribut attribute) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set attribute */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setAttribut(attribute);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the link of a node
     *
     * @param nodeID Id of the node
     * @param link   Link
     */
    public static void setLink(String nodeID, String link) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set link */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setLink(link);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the color of a node
     *
     * @param nodeID Id of the node
     * @param color  Color
     */
    public static void setColor(String nodeID, nodeColor color) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set color */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setColor(color);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the image of a node
     *
     * @param nodeID Id of the node
     * @param image  Link to the image
     */
    public static void setImage(String nodeID, String image) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set image */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setImage(image);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the additional of a node
     *
     * @param nodeID     Id of the node
     * @param additional Link to the image
     */
    public static void setAdditional(String nodeID, String additional) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set image */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setAdditional(additional);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the size of a node
     *
     * @param nodeID Id of the node
     * @param height Height of the node
     * @param width  Width of the node
     */
    public static void setSize(String nodeID, double height, double width) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set size */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setHeight(height);
        fallbackDiagram.getActiveVersion().getNode(nodeID).setWidth(width);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }


    /**
     * Set the position of a node
     *
     * @param nodeID Id of the node
     * @param x      Height of the node
     * @param y      Width of the node
     */
    public static void setPosition(String nodeID, double x, double y) {

        /* If node is not inside the active diagram the fallback is searching in the whole project */
        diagram fallbackDiagram;
        if (DataStorage.activeDiagram.getActiveVersion().getNode(nodeID) == null) {
            fallbackDiagram = DiagramAPI.getDiagramByNode(nodeID);
            if (fallbackDiagram == null)
                return;
        } else {
            fallbackDiagram = DataStorage.activeDiagram;
        }


        /* Set position */
        fallbackDiagram.getActiveVersion().getNode(nodeID).setX(x);
        fallbackDiagram.getActiveVersion().getNode(nodeID).setY(y);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(fallbackDiagram.getName());

    }

}
