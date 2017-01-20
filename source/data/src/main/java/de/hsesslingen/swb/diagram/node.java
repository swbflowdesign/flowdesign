package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.Utils;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeAttribut;
import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.diagram.enums.nodeType;

import java.util.ArrayList;
import java.util.List;

public class node {

    private String id;
    private nodeType type;
    private String text;
    private String label;
    private nodeAttribut attribut;
    private List<connector> connectors;
    private double height;
    private double width;
    private double x;
    private double y;
    private String link;
    private nodeColor color;
    private String image;
    private String additional;

    /**
     * Constructor
     *
     * @param type Type of the node
     */
    public node(nodeType type) {
        /* Passed values */
        this.type = type;

        /* Default values */
        this.id = Utils.generateUUID();
        this.text = "";
        this.label = "";
        this.attribut = nodeAttribut.NONE;
        this.connectors = new ArrayList<connector>();
        this.height = 0.0;
        this.width = 0.0;
        this.x = 0.0;
        this.y = 0.0;
        this.link = "";
        this.color = nodeColor.Default;
        this.image = "";
        this.additional = "";
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

    public nodeType getType() {
        return type;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public nodeAttribut getAttribut() {
        return attribut;
    }

    public void setAttribut(nodeAttribut attribut) {
        this.attribut = attribut;
    }

    public List<connector> getConnectors() {
        return connectors;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public nodeColor getColor() {
        return color;
    }

    public void setColor(nodeColor color) {
        this.color = color;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getAdditional() {
        return additional;
    }

    public void setAdditional(String additional) {
        this.additional = additional;
    }

    /**
     * Get a connector with its ID
     *
     * @param id ID of the connector
     * @return Connector
     */
    public connector getConnector(String id) {
        for (connector temp : this.connectors) {
            if (temp.getId().equals(id)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Add new connector
     *
     * @param conn Connector object
     * @return ID of the added connector
     */
    public String addConnector(connector conn) {
        this.connectors.add(conn);

        return conn.getId();
    }

    /**
     * Add new connector
     *
     * @param type Type of the connector
     * @return ID of the added connector
     */
    public String addConnector(connectorOrientation type) {
        connector temp = new connector(type);
        this.connectors.add(temp);

        return temp.getId();
    }

    /**
     * Remove a connector
     *
     * @param conn Connector object
     */
    public void removeConnector(connector conn) {
        this.connectors.remove(conn);
    }

    /**
     * Remove a connector
     *
     * @param id ID of the connector
     */
    public void removeConnector(String id) {
        for (connector temp : this.connectors) {
            if (temp.getId().equals(id)) {
                this.connectors.remove(temp);
                break;
            }
        }
    }

}
