package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.enums.wireType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class version {

    private int num;
    private Date created;
    private Date edited;
    private List<node> nodes;
    private List<wire> wires;

    /**
     * Constructor
     *
     * @param num Version number
     */
    public version(int num) {
        /* Passed values */
        this.num = num;

        /* Default values */
        this.created = new Date();
        this.edited = new Date();
        this.nodes = new ArrayList<node>();
        this.wires = new ArrayList<wire>();
    }

    /**
     * Getters and Setters
     */
    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }

    public List<node> getNodes() {
        return nodes;
    }

    public List<wire> getWires() {
        return wires;
    }

    /**
     * Update edited
     */
    private void updateEdited() {
        this.edited = new Date();
    }

    /**
     * Get a node with its ID
     *
     * @param id ID of the node
     * @return Node
     */
    public node getNode(String id) {
        for (node temp : this.nodes) {
            if (temp.getId().equals(id)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Adda new node
     *
     * @param nod Node object
     * @return ID of the added node
     */
    public String addNode(node nod) {
        this.nodes.add(nod);

        updateEdited();     // Update edited

        return nod.getId();
    }

    /**
     * Adda new node
     *
     * @param type Type of the node
     * @return ID of the added node
     */
    public String addNode(nodeType type) {
        node temp = new node(type);
        this.nodes.add(temp);

        updateEdited();     // Update edited

        return temp.getId();
    }

    /**
     * Remove a node
     *
     * @param nod Node object
     */
    public void removeNode(node nod) {
        this.nodes.remove(nod);

        updateEdited();     // Update edited
    }

    /**
     * Remove a node
     *
     * @param id ID of the node
     */
    public void removeNode(String id) {
        for (node temp : this.nodes) {
            if (temp.getId().equals(id)) {
                this.nodes.remove(temp);
                break;
            }
        }

        updateEdited();     // Update edited
    }

    /**
     * Get a wire with its ID
     *
     * @param id ID of the wire
     * @return Wire
     */
    public wire getWire(String id) {
        for (wire temp : this.wires) {
            if (temp.getId().equals(id)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Add new wire
     *
     * @param wir Wire type
     * @return ID of the added wire
     */
    public String addWire(wire wir) {
        this.wires.add(wir);

        updateEdited();     // Update edited

        return wir.getId();
    }

    /**
     * Add new wire
     *
     * @param type Type of the wire
     * @return ID of the added wire
     */
    public String addWire(wireType type, node sourceNode, connector sourceConnector, node targetNode, connector targetConnector) {
        wire temp = new wire(type, sourceNode, sourceConnector, targetNode, targetConnector);
        this.wires.add(temp);

        updateEdited();     // Update edited

        return temp.getId();
    }

    /**
     * Remove a wire
     *
     * @param conn Wire object
     */
    public void removeWire(wire conn) {
        this.wires.remove(conn);

        updateEdited();     // Update edited
    }

    /**
     * Remove a wire
     *
     * @param id ID of the wire
     */
    public void removeWire(String id) {
        for (wire temp : this.wires) {
            if (temp.getId().equals(id)) {
                this.wires.remove(temp);
                break;
            }
        }

        updateEdited();     // Update edited
    }

}
