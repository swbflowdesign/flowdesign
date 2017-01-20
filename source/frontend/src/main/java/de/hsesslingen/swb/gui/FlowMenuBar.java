package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.gui.canvas.Fullscreen;
import de.hsesslingen.swb.gui.canvas.MapCanvasSpaces;
import de.hsesslingen.swb.helpModal.HelpModal;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;


public class FlowMenuBar {

    private static MenuBar menubar = new MenuBar();
    private static Menu file = new Menu("Projekt");
    private static Menu view = new Menu("Zeichenfläche");
    private static Menu help = new Menu("Hilfe");

    public static MenuBar getMenuBar() {
        //MenuBar menubar = new MenuBar(new Menu("File"), new Menu("Edit"), new Menu("View"), new Menu("Help"));
        initMenuBar();
        menubar.setStyle("-fx-border-color: gray; -fx-border-width:1 ; -fx-border-style: solid;");
        return menubar;
    }

    private static void initMenuBar() {
        menubar.getMenus().addAll(file, view, help);

        MenuItem newProject = new MenuItem("Projekt erstellen...");
        newProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.createNewProject();
            }
        });

        MenuItem openProject = new MenuItem("Projekt öffnen...");
        openProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.openProject();
            }
        });

        MenuItem closeProject = new MenuItem("Projekt schließen");
        closeProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.closeProject();
            }
        });

        MenuItem saveProject = new MenuItem("Projekt speichern");
        saveProject.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                Actions.saveProject();
            }
        });
        saveProject.setAccelerator(new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN));

        file.getItems().addAll(newProject, openProject, saveProject, closeProject);


        MenuItem canvasFullscreen = new MenuItem("Zeichenfläche - Fullscreen");
        canvasFullscreen.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Fullscreen.canvasFullscreenShow();
            }
        });

        SeparatorMenuItem seperator = new SeparatorMenuItem();

        MenuItem zoomToFit = new MenuItem("Zeichenfläche - Zoom to Fit");
        zoomToFit.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DataStorage.activeDiagram != null)
                    MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName()).getDrawingPane().getScrollPane().zoomToFit(true);
            }
        });

        MenuItem zoom100 = new MenuItem("Zeichenfläche - Zoom 100%");
        zoom100.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (DataStorage.activeDiagram != null)
                    MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName()).getDrawingPane().getScrollPane().zoomActual();
            }
        });

        view.getItems().addAll(canvasFullscreen, seperator, zoomToFit, zoom100);


        MenuItem shortcuts = new MenuItem("Tastenkombinationen");
        shortcuts.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                new HelpModal();
            }
        });

        help.getItems().addAll(shortcuts);

    }
}
