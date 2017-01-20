package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.DataStorage;
import javafx.scene.Scene;
import javafx.scene.control.MenuBar;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

public class Main {

    public static Scene mainScene;

    public static BorderPane root;


    public static void launch() {
        // Create a BorderPane with a Text node in each of the five regions
        root = new BorderPane();

        MenuBar menubar = FlowMenuBar.getMenuBar();
        VBox filetree = FlowFileTree.getFileTree();
        filetree.setMinWidth(300);
        TabPane mainContent = FlowMainContent.getMainContent();
        TabPane rightContainer = RightSideBar.getRightSideBar();
        HBox statusbar = FlowStatusBar.getStatusBar();

        //init properties tab
        FlowProperties.initProperties();
        //init FileTree
        FlowFileTree.init();


        root.setTop(menubar);
        root.setLeft(filetree);
        root.setRight(rightContainer);
        root.setCenter(mainContent);
        root.setBottom(statusbar);

        // Set the Size of Borderpane
        root.setPrefSize(1200, 600);

        // Create the Scene
        mainScene = new Scene(root);



        /* Load project */
        if (DataStorage.project != null) {
            /* Update GUI */
            FlowMainContent.closeAllTabs();
            FlowFileTree.updateFileTree();

            /* Update statusbar */
            FlowStatusBar.displaySuccess("Projekt '" + DataStorage.project.getName() + "' geladen");
        }

    }

    public static Scene getMainScene() {
        return mainScene;
    }

}
