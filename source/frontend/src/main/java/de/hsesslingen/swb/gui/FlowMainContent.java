package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.gui.canvas.CanvasSpace;
import de.hsesslingen.swb.gui.canvas.MapCanvasSpaces;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;


public class FlowMainContent {

    public static TabPane mainContent;

    public static TabPane getMainContent() {
        mainContent = new TabPane();

        //tell backend on tab change which is the active diagram
        mainContent.getSelectionModel().selectedItemProperty().addListener(
                new ChangeListener<Tab>() {
                    @Override
                    public void changed(ObservableValue<? extends Tab> ov, Tab oldTab, Tab newTab) {
                        if (mainContent.getSelectionModel().getSelectedItem() != null) {
                            BackendAPI.diagram().setActiveDiagram(mainContent.getSelectionModel().getSelectedItem().getText());
                            RightSideBar.setRightContainer(newTab.getText());
                            FlowProperties.showDiagramSettings();
                            FlowFileTree.updateSelectedItem(mainContent.getSelectionModel().getSelectedItem().getText());

                            CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(mainContent.getSelectionModel().getSelectedItem().getText());
                            if (canvas != null) {
                                if (canvas.getActiveElement() != null) {
                                    FlowProperties.updateProperties(canvas.getActiveElement());
                                    RightSideBar.getRightSideBar().getSelectionModel().select(1);
                                }
                            }

                        } else {
                            BackendAPI.diagram().setActiveDiagram(null);
                            RightSideBar.clearOldContainer();
                        }
                    }
                }
        );
        return mainContent;
    }

    /**
     * Adds a Tab to the Tabpane and displays it
     *
     * @param name Name of the Tab
     */
    public static void addTab(String name) {
        Tab tab = new Tab();
        tab.setText(name);
        tab.setOnClosed(new EventHandler<Event>() {
            @Override
            public void handle(Event event) {
                Actions.closeDiagram(name);
            }
        });
        mainContent.getTabs().add(tab);
        mainContent.getSelectionModel().selectLast();
    }

    /**
     * Adds a Tab to the Tabpane, opens a diagram and displays it
     *
     * @param name Name of the Diagram
     */
    public static void openDiagram(String name) {
        addTab(name);
    }


    /**
     * Get a tab by ID
     *
     * @param idx ID of the tab
     * @return Tab
     */
    public static Tab getTab(Integer idx) {
        return mainContent.getTabs().get(idx);
    }


    /**
     * Get tab by name
     *
     * @param name Name of the tab
     * @return Tab
     */
    public static Tab getTab(String name) {
        for (Tab tempTab : mainContent.getTabs()) {
            if (tempTab.getText().equals(name))
                return tempTab;
        }

        return null;
    }


    /**
     * Close a tab by name
     */
    public static void closeTab(String name) {
        for (Tab tempTab : mainContent.getTabs()) {
            if (tempTab.getText().equals(name)) {
                mainContent.getTabs().remove(tempTab);
                return;
            }
        }
    }


    /**
     * Close all tabs
     */
    public static void closeAllTabs() {
        mainContent.getTabs().clear();
        RightSideBar.clearOldContainer();
        BackendAPI.diagram().setActiveDiagram(null);
        MapCanvasSpaces.removeAllCanvasSpaces();
    }


    /**
     * Set selected tab by name
     *
     * @param name Name of the tab
     */
    public static void setSelectedTab(String name) {
        for (int i = 0; i < mainContent.getTabs().size(); i++) {
            if (mainContent.getTabs().get(i).getText().equals(name))
                mainContent.getSelectionModel().select(i);
        }
    }

}
