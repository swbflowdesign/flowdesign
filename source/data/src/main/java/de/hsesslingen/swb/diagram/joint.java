package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.Utils;

public class joint {

    private String id;
    private double x;
    private double y;

    /**
     * Constructor
     *
     * @param x Position X
     * @param y Position Y
     */
    public joint(double x, double y) {
        /* Passed values */
        this.x = x;
        this.y = y;

        /* Default values */
        this.id = Utils.generateUUID();
    }

    /**
     * Getters and Setters
     */
    public String getId() {
        return id;
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

}
