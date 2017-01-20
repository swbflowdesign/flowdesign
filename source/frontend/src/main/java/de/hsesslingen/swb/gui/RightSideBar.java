package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.enums.diagramType;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class RightSideBar {

    private static TabPane rightContainer = new TabPane();

    /**
     * returns the TabPane to be placed on the right side of the program
     *
     * @return TabPane
     */
    public static TabPane getRightSideBar() {

        return rightContainer;
    }

    /**
     * fills the TabPane with content
     *
     * @param typeOfThisTab The Diagramtype of the diagram that is opened in this Tab
     */
    public static void setRightContainer(diagramType typeOfThisTab) {

        Components.clearComponents();
        Tab properties = FlowProperties.getProperties();
        Components.setComponents(typeOfThisTab);
        Tab comp = Components.getComponentsTab();

        rightContainer.getTabs().add(comp);
        rightContainer.getTabs().add(properties);

    }

    /**
     * fills the TabPane with content
     *
     * @param diagramName The name of the diagram that is opened in this Tab
     */
    public static void setRightContainer(String diagramName) {

        Components.clearComponents();
        Tab properties = FlowProperties.getProperties();
        diagramType typeOfThisTab = DataStorage.project.getDiagram(diagramName).getType();
        Components.setComponents(typeOfThisTab);
        Tab comp = Components.getComponentsTab();

        rightContainer.getTabs().add(comp);
        rightContainer.getTabs().add(properties);
    }

    /**
     * clears the Tab on the right side
     */
    public static void clearOldContainer() {

        rightContainer.getTabs().clear();
        //Components.clearComponents();
    }

}

