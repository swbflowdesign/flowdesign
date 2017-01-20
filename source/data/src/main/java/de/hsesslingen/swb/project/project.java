package de.hsesslingen.swb.project;

import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.diagramType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class project {

    private String name;
    private Date created;
    private Date edited;
    private List<diagram> diagrams;

    /**
     * Constructor
     *
     * @param name Name of the project
     */
    public project(String name) {
        /* Passed values */
        this.name = name;

        /* Default values */
        this.created = new Date();
        this.edited = created;
        this.diagrams = new ArrayList<diagram>();
    }

    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public List<diagram> getDiagrams() {
        return diagrams;
    }

    /**
     * Update edited
     */
    private void updateEdited() {
        this.edited = new Date();
    }

    /**
     * Get a diagram with its name
     *
     * @param name Name of the diagram
     * @return diagram
     */
    public diagram getDiagram(String name) {
        for (diagram temp : this.diagrams) {
            if (temp.getName().equals(name)) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Add a diagram
     *
     * @param diagObj Diagram object
     */
    public void addDiagram(diagram diagObj) {
        this.diagrams.add(diagObj);

        updateEdited();     // Update edited
    }

    /**
     * Add new diagram
     *
     * @param name Name of the diagram
     * @param type Type of the diagram
     */
    public void addDiagram(String name, diagramType type) {
        diagram temp = new diagram(name, type);
        this.diagrams.add(temp);

        updateEdited();     // Update edited
    }

    /**
     * Remove a diagram
     *
     * @param diag Diagram object
     */
    public void removeDiagram(diagram diag) {
        this.diagrams.remove(diag);

        updateEdited();     // Update edited
    }

    /**
     * Remove a diagram
     *
     * @param name ID of the diagrams
     */
    public void removeDiagram(String name) {
        for (diagram temp : this.diagrams) {
            if (temp.getName().equals(name)) {
                this.diagrams.remove(temp);
                break;
            }
        }

        updateEdited();     // Update edited
    }


}
