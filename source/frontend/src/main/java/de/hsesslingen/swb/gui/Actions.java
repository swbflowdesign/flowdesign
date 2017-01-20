package de.hsesslingen.swb.gui;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.fileSystem.directoryChooser;
import de.hsesslingen.swb.fileSystem.fileChooser;
import de.hsesslingen.swb.gui.canvas.CanvasSpace;
import de.hsesslingen.swb.gui.canvas.MapCanvasSpaces;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.scene.control.TextInputDialog;

import java.io.File;
import java.util.Optional;


public final class Actions {

    /**
     * displays a file chooser to search for a project an open it
     */
    public static void openProject() {
        String[] extensions = new String[]{"flowproject"};
        File file = fileChooser.showOpenDialog(extensions);
        System.out.println(file);

        if (file != null) {
            if (BackendAPI.project().openProject(file.getPath())) {
                /* Update GUI */
                FlowFileTree.updateFileTree();
                FlowMainContent.closeAllTabs();

                /* Update statusbar */
                FlowStatusBar.displaySuccess("Projekt '" + DataStorage.project.getName() + "' geladen");
            }

        }
    }

    /**
     * closes the current project
     */
    public static void closeProject() {
        if (BackendAPI.project().closeProject()) {
            /* Update GUI */
            FlowFileTree.destroyFileTree();
            FlowMainContent.closeAllTabs();
        }

    }

    /**
     * saves the current project
     */
    public static void saveProject() {
        BackendAPI.project().saveProject();

        /* Update statusbar */
        FlowStatusBar.displaySuccess("Projekt '" + DataStorage.project.getName() + "' wurde gespeichert");
    }

    /**
     * deletes the current project
     */
    public static void deleteProject() {
        // TODO: ADD function in backend to delete a project. Necessary?
        //backendAPI.project().deleteProject();
    }

    /**
     * creates a new project
     */
    public static void createNewProject() {

        /* Get Project Name */
        String projectName = "";

        TextInputDialog dialog = new TextInputDialog("Projekt");
        dialog.setTitle("Flow Designer");
        dialog.setHeaderText("Neues Projekt erstellen");
        dialog.setContentText("Projekt Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            projectName = result.get();
        }

        if (projectName.equals(""))
            return;

        /* Get Project directory */
        String projectDirectory = "";
        File directory = directoryChooser.showDirectoryDialog();
        if (directory != null)
            projectDirectory = directory.toString();

        if (projectDirectory.equals(""))
            return;

        /* Create new project */
        if (BackendAPI.project().createNewProject(projectDirectory, projectName)) {
            /* Update GUI */
            FlowFileTree.updateFileTree();
            FlowMainContent.closeAllTabs();

            /* Update statusbar */
            FlowStatusBar.displaySuccess("Projekt '" + DataStorage.project.getName() + "' wurde erstellt");
        }


    }

    /**
     * opens a diagram identified by its name
     *
     * @param name String
     */
    public static void openDiagram(String name) {

        // Open diagram if not already opened, otherwise switch to the diagram tab
        if (FlowMainContent.getTab(name) == null) {
            /* Add tab */
            FlowMainContent.openDiagram(name);

            /* Set CanvasSpace */
            CanvasSpace canvas = new CanvasSpace();
            canvas.renderDiagram(name);
            MapCanvasSpaces.setCanvasSpaceOfDiagram(DataStorage.project.getDiagram(name), canvas);
            FlowMainContent.getTab(name).setContent(MapCanvasSpaces.getCanvasSpaceOfDiagram(name).getCanvasSpace());

            /* Set RightContainer */
            RightSideBar.setRightContainer(name);
        } else {
            FlowMainContent.setSelectedTab(name);
        }
    }

    /**
     * closes a diagram identified by its name
     *
     * @param name String
     */
    public static void closeDiagram(String name) {
        /* Remove CanvasSpace */
        MapCanvasSpaces.removeCanvasSpaceOfDiagram(name);

        /* Close Tab */
        FlowMainContent.closeTab(name);
    }

    /**
     * renames a diagram identified by its oldName
     *
     * @param oldName String
     */
    public static void renameDiagram(String oldName) {

        /* Get diagram name */
        String diagramName = "";

        TextInputDialog dialog = new TextInputDialog(oldName);
        dialog.setTitle("Flow Designer");
        dialog.setHeaderText("Diagram umbenennen");
        dialog.setContentText("Neuer Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            diagramName = result.get();
        }

        if (diagramName.equals(""))
            return;


        /* Rename diagram */
        if (BackendAPI.diagram().renameDiagram(oldName, diagramName)) {
            /* Update GUI */
            FlowFileTree.updateFileTree();

            /* Update CanvasSpace map */
            MapCanvasSpaces.updateDiagramName(oldName, diagramName);

            /* Update tab */
            if (FlowMainContent.getTab(oldName) != null) {
                FlowMainContent.getTab(oldName).setText(diagramName);
                FlowMainContent.getTab(diagramName).setContent(MapCanvasSpaces.getCanvasSpaceOfDiagram(diagramName).getCanvasSpace());
            }

            /* Update statusbar */
            FlowStatusBar.displaySuccess("Diagramm '" + oldName + "' wurde in '" + diagramName + "' umbenannt");
        }

    }

    /**
     * creates a diagram identified by its type
     *
     * @param type diagramType(enum)
     */
    public static void createDiagram(diagramType type, String name, Element element) {

        /* Get diagram name */
        String diagramName = "";

        TextInputDialog dialog = new TextInputDialog("");
        if (name.length() == 0) {
            dialog.getEditor().setText("Diagramm");
        } else {
            dialog.getEditor().setText(name);
        }
        dialog.setTitle("Flow Designer");
        dialog.setHeaderText("Neues Diagamm erstellen");
        dialog.setContentText("Diagram Name:");

        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()) {
            diagramName = result.get();
        }

        if (diagramName.equals(""))
            return;


        /* Create diagram */
        if (BackendAPI.diagram().createDiagram(diagramName, type) != null) {

            /* Set link if the new diagram was create via the contextMenu of the element*/
            if (element != null) {
                element.setLink(diagramName);

                //TODO: set Parent
                /*
                if (element.getElementType().toString().startsWith("fd_")) {
                    dataStorage.project.getDiagram(diagramName).setParent();
                }
                */

            }

            /* Update GUI */
            FlowFileTree.updateFileTree();
            FlowMainContent.addTab(diagramName);
            
            /* Set CanvasSpace */
            CanvasSpace canvas = new CanvasSpace();
            MapCanvasSpaces.setCanvasSpaceOfDiagram(DataStorage.project.getDiagram(diagramName), canvas);
            FlowMainContent.getTab(diagramName).setContent(MapCanvasSpaces.getCanvasSpaceOfDiagram(diagramName).getCanvasSpace());

            /* Create element fd_SourceOfFlow if diagramType is FlowDesign*/
            if (DataStorage.activeDiagram.getType() == diagramType.FlowDesign) {
                String elementID = BackendAPI.node().addNode(nodeType.fd_SourceOfFlow);
                Element srcElement = canvas.getDrawingPane().getGraph().getElementModel().addElement(elementID, nodeType.fd_SourceOfFlow, 100.0, 150.0, null, true);
                srcElement.updateAllBackend();
                canvas.setActiveElement(srcElement);
            }

            /* Set RightContent */
            RightSideBar.setRightContainer(type);

            /* Update statusbar */
            FlowStatusBar.displaySuccess("Diagramm '" + diagramName + "' wurde erstellt");
        }
    }


    /**
     * deletes a diagram identified by its name
     *
     * @param name String
     */
    public static void deleteDiagram(String name) {
        if (BackendAPI.diagram().deleteDiagram(name)) {
            /* Update GUI */
            FlowFileTree.updateFileTree();
            closeDiagram(name);

            /* Update statusbar */
            FlowStatusBar.displaySuccess("Diagramm '" + name + "' wurde gelöscht");
        }
    }

    /**
     * imports a image
     */
    public static void importImageToResources() {
        String[] extensions = new String[]{"Bilder"};
        File file = fileChooser.showOpenDialog(extensions);
        System.out.println(file);

        if (file != null) {
            String imageName = BackendAPI.file().importImage(file.getPath());

            if (imageName != null) {
                /* Update statusbar */
                FlowStatusBar.displaySuccess("Bild '" + file.getName() + "' importiert!");
            }

        }
    }

}
