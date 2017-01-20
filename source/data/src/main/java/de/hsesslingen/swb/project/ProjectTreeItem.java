package de.hsesslingen.swb.project;

import de.hsesslingen.swb.diagram.enums.diagramType;

import java.util.ArrayList;
import java.util.List;

public class ProjectTreeItem {

    private String name;
    private diagramType type;
    private String parent;
    private List<ProjectTreeItem> children;

    /**
     * Contructor
     *
     * @param name Name of the diagram
     */
    public ProjectTreeItem(String name, diagramType type, String parent) {
        /* Passed values */
        this.name = name;
        this.type = type;
        this.parent = parent;

        /* Default values */
        children = new ArrayList<ProjectTreeItem>();
    }

    public String getName() {
        return name;
    }

    public diagramType getType() {
        return type;
    }

    public String getParent() {
        return parent;
    }

    public List<ProjectTreeItem> getChildren() {
        return children;
    }


    /**
     * Add new child
     *
     * @param child ProjectTreeItem object
     */
    public void addChild(ProjectTreeItem child) {
        this.children.add(child);
    }

}
