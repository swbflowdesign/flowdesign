package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.Utils;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;

public class connector {

    private String id;
    private connectorOrientation orientation;
    private String type;

    /**
     * Constructor
     *
     * @param orientation Orientation of the connector
     */
    public connector(connectorOrientation orientation) {
        /* Passed values */
        this.orientation = orientation;

        /* Default values */
        this.id = Utils.generateUUID();
        this.type = "";
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

    public connectorOrientation getOrientation() {
        return orientation;
    }

    public void setOrientation(connectorOrientation orientation) {
        this.orientation = orientation;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
