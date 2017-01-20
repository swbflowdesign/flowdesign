package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.Utils;
import de.hsesslingen.swb.diagram.enums.wireType;

import java.util.ArrayList;
import java.util.List;

public class wire {

    private String id;
    private wireType type;
    private node sourceNode;
    private node targetNode;
    private connector sourceConnector;
    private connector targetConnector;
    private String label;
    private List<joint> joints;

    /**
     * Constructor
     *
     * @param type            Type der Wire
     * @param sourceNode      Source node
     * @param sourceConnector Source connector
     * @param targetNode      Target node
     * @param targetConnector Target connector
     */
    public wire(wireType type, node sourceNode, connector sourceConnector, node targetNode, connector targetConnector) {
        /* Passed values */
        this.type = type;
        this.sourceNode = sourceNode;
        this.targetNode = targetNode;
        this.sourceConnector = sourceConnector;
        this.targetConnector = targetConnector;

        /* Default values */
        this.id = Utils.generateUUID();
        this.label = "";
        this.joints = new ArrayList<joint>();
    }

    /**
     * Getters and Setters
     */
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public wireType getType() {
        return type;
    }

    public void setType(wireType type) {
        this.type = type;
    }

    public node getSourceNode() {
        return sourceNode;
    }

    public void setSourceNode(node sourceNode) {
        this.sourceNode = sourceNode;
    }

    public node getTargetNode() {
        return targetNode;
    }

    public void setTargetNode(node targetNode) {
        this.targetNode = targetNode;
    }

    public connector getSourceConnector() {
        return sourceConnector;
    }

    public void setSourceConnector(connector sourceConnector) {
        this.sourceConnector = sourceConnector;
    }

    public connector getTargetConnector() {
        return targetConnector;
    }

    public void setTargetConnector(connector targetConnector) {
        this.targetConnector = targetConnector;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public List<joint> getJoints() {
        return joints;
    }

    /**
     * Get a joint with its ID
     *
     * @param id ID of the joint
     * @return Joint
     */
    public joint getJoint(String id) {
        for (joint temp : this.joints) {
            if (temp.getId().equals(id)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Add new joint
     *
     * @param x Position X
     * @param y Position Y
     * @return ID of the adde joint
     */
    public String addJoint(double x, double y) {
        joint temp = new joint(x, y);
        this.joints.add(temp);

        return temp.getId();
    }

    /**
     * Delete a joint
     *
     * @param joi Joint object
     */
    public void removeJoint(joint joi) {
        this.joints.remove(joi);
    }

    /**
     * Delete a Joint
     *
     * @param id ID of the joint
     */
    public void removeJoint(String id) {
        for (joint temp : this.joints) {
            if (temp.getId().equals(id)) {
                this.joints.remove(temp);
                break;
            }
        }
    }

}
