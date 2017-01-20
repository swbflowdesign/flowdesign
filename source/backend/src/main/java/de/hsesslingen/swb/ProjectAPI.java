package de.hsesslingen.swb;

import de.hsesslingen.swb.Storage.LastProject;
import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.project.ProjectTreeItem;
import de.hsesslingen.swb.project.project;
import de.hsesslingen.swb.xmlParser.ReadXMLFile;
import de.hsesslingen.swb.xmlParser.WriteXMLFile;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class ProjectAPI {

    /**
     * Open an existing project
     *
     * @param projectFilePath Path to the project file
     */
    public static boolean openProject(String projectFilePath) {

        /* Check project file */
        File projectFile = new File(projectFilePath);
        if (!projectFile.isFile()) {
            throw new IllegalArgumentException("Project file not found!");
        }
        if (projectFilePath.contains(".")) {

            if (!projectFilePath.split("\\.")[1].equals("flowproject"))
                throw new IllegalArgumentException("Project file is wrong type!");

        } else {
            throw new IllegalArgumentException(projectFilePath + " is not a file path!");
        }

        /* Check if another project is still open */
        if (!closeProject()) {
            return false;
        }

        /* Set project directory in dataStorage */
        DataStorage.projectDirectory = projectFile.getPath()
                .replace(File.separator + projectFile.getName(), "");

        /* Load whole project */
        ReadXMLFile.project(projectFilePath);

        /* Add project to lastProjects */
        updateLastProjects();

        return true;

    }


    /**
     * Save a project
     */
    public static void saveProject() {

        if (DataStorage.dirtyDiagrams.size() > 0) {
            /* Save project */
            WriteXMLFile.project();

            /* Save diagrams */
            WriteXMLFile.diagrams();

            /* Clear dirtyDiagrams list */
            BackendAPI.markAllDiagramsUndirty();
        }

    }


    /**
     * Save a project and clear dataStorage
     *
     * @return boolean False on cancel action
     */
    public static boolean closeProject() {

        if (DataStorage.project != null) {

            if (DataStorage.dirtyDiagrams.size() > 0) {
                /* Save project */
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Flow Designer");
                alert.setHeaderText("Möchten Sie die Änderungen am Projekt '" + DataStorage.project.getName() + "' speichern?");

                ButtonType buttonTypeYes = new ButtonType("Ja", ButtonBar.ButtonData.YES);
                ButtonType buttonTypeNo = new ButtonType("Nein", ButtonBar.ButtonData.NO);
                ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

                alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo, buttonTypeCancel);

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == buttonTypeYes) {
                    saveProject();
                } else if (result.get() == buttonTypeCancel) {
                    return false;
                }
            }

            /* Clear dataStorage */
            System.out.println("Project '" + DataStorage.project.getName() + "' closed!");
            BackendAPI.clearDataStorage();

        }

        return true;

    }


    /**
     * Create a new Project
     * - Create new project
     * - Create Folder Structure
     * - Save project file
     *
     * @param projectDirectory Path to the project directory
     * @param projectName      Name of the new project
     */
    public static boolean createNewProject(String projectDirectory, String projectName) {

        /* Create Folder Structure */
        if (!createFolderStructure(projectDirectory))
            return false;

        /* Check if another project is still open */
        if (!closeProject())
            return false;

        /* Create new project */
        DataStorage.projectDirectory = projectDirectory;
        DataStorage.project = new project(projectName);

        /* Save project file */
        WriteXMLFile.project();

        /* Add project to lastProjects */
        updateLastProjects();

        System.out.println("Project '" + DataStorage.project.getName() + "' created!");

        return true;

    }


    /**
     * Create the folder structure for the project
     *
     * @param projectDirectory Path to the project directory
     */
    public static boolean createFolderStructure(String projectDirectory) {

        File dir = new File(projectDirectory);

        /* Check if directory exists */
        if (!dir.exists() && !dir.isDirectory()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flow Designer");
            alert.setHeaderText("Das angegebene Verzeichnis existiert nicht!");
            alert.showAndWait();

            return false;
        }


        /* Check if directory is empty */
        if (dir.list().length > 0) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flow Designer");
            alert.setHeaderText("Es befinden sich Dateien in dem angegebenen Verzeichnis!");
            alert.setContentText("Bitte stellen sie sicher, dass das Verzeichnis, in dem das neue Projekt gespeichert werden soll, leer ist!");
            alert.showAndWait();

            return false;
        }


        /* Create folder 'diagrams' with subfolder 'SystemUmweltDiagramm', 'DialogDiagramm' and 'FlowDesign' */
        File dirDiag = new File(projectDirectory + File.separator + "diagrams");
        File dirDiagSUD = new File(projectDirectory + File.separator + "diagrams" + File.separator + "SystemUmweltDiagram");
        File dirDiagDD = new File(projectDirectory + File.separator + "diagrams" + File.separator + "DialogDiagram");
        File dirDiagFD = new File(projectDirectory + File.separator + "diagrams" + File.separator + "FlowDesign");


        /* Create folder 'resources' with subfolder 'images' */
        File dirRes = new File(projectDirectory + File.separator + "resources");
        File dirResImg = new File(projectDirectory + File.separator + "resources" + File.separator + "images");


        try {
            dirDiag.mkdir();
            dirDiagSUD.mkdir();
            dirDiagDD.mkdir();
            dirDiagFD.mkdir();
            dirRes.mkdir();
            dirResImg.mkdir();
        } catch (SecurityException se) {
            System.out.println(se.getMessage());
            return false;
        }

        return true;

    }


    /**
     * Get the latest opened projects
     *
     * @return List List of the latest projects
     */
    public static List<LastProject> getLastProjects() {

        return DataStorage.lastProjects;

    }


    /**
     * Update the lastProject in the dataStorage
     */
    public static void updateLastProjects() {

        /* Delete project in lastProjects */
        for (int i = DataStorage.lastProjects.size() - 1; i >= 0; i--) {
            String tempProjectPath = DataStorage.projectDirectory + File.separator + DataStorage.project.getName() + ".flowproject";
            if (DataStorage.lastProjects.get(i).getFilePath().equals(tempProjectPath)) {
                DataStorage.lastProjects.remove(i);
            }
        }

        /* Add project as first project in lastProjects */
        String tempProjectPath = DataStorage.projectDirectory + File.separator + DataStorage.project.getName() + ".flowproject";
        LastProject tempProject = new LastProject(DataStorage.project.getName(), tempProjectPath, DataStorage.project.getEdited());
        DataStorage.lastProjects.add(0, tempProject);

        /* Remove the last project, if more than 10 projects are listed */
        if (DataStorage.lastProjects.size() > 10)
            DataStorage.lastProjects.remove(DataStorage.lastProjects.size() - 1);

        /* Write to file */
        WriteXMLFile.storage();

    }


    /**
     * Get all diagrams in a hierarchical structure
     *
     * @return List
     */
    public static List<ProjectTreeItem> getProjectTree() {

        List<ProjectTreeItem> projectTree = new ArrayList<ProjectTreeItem>();
        List<ProjectTreeItem> projectTreeItemsNotResolved = new ArrayList<ProjectTreeItem>();


        /* Loop through all diagrams in the project */
        for (int i = 0; i < DataStorage.project.getDiagrams().size(); i++) {

            diagram tempDiag = DataStorage.project.getDiagrams().get(i);
            ProjectTreeItem tempItem = new ProjectTreeItem(tempDiag.getName(), tempDiag.getType(), tempDiag.getParent());

            if (tempItem.getParent().equals("root")) {
                /* If root, add the item as root item */
                projectTree.add(tempItem);
            } else {

                /* If not root, than search the the parent in the projectTree */
                Boolean found = addProjectTreeItemRec(projectTree, tempItem);

                /* If parent not found add item to projectTreeItemsNotResolved */
                if (!found)
                    projectTreeItemsNotResolved.add(tempItem);

            }

        }


        /* If not all item resolved */
        int resolved;
        while (projectTreeItemsNotResolved.size() > 0) {

            resolved = 0;

            /* loop through projectTreeItemsNotResolved */
            for (int r = projectTreeItemsNotResolved.size() - 1; r >= 0; r--) {

                /* Search the the parent in the projectTree */
                Boolean found = addProjectTreeItemRec(projectTree, projectTreeItemsNotResolved.get(r));

                /* If parent found */
                if (found) {
                    projectTreeItemsNotResolved.remove(r);
                    resolved++;
                }

            }

            /* If no item could be resolved exit while loop */
            if (resolved == 0) {
                System.out.println("Not all items could be resolved!");
                break;
            }

        }


        return projectTree;

    }


    /**
     * Search the ProjectTree recursive for the parent diagram
     *
     * @param block List of ProjectTreeItem to be searched
     * @param item  Item which needs a parent
     * @return Boolean True if parent found
     */
    public static Boolean addProjectTreeItemRec(List<ProjectTreeItem> block, ProjectTreeItem item) {

        Boolean found = false;

        for (int i = 0; i < block.size(); i++) {

            if (block.get(i).getName().equals(item.getParent())) {
                /* If parent found add the item as child */
                block.get(i).addChild(item);
                found = true;
                break;
            } else if (block.get(i).getChildren().size() > 0) {
                if (addProjectTreeItemRec(block.get(i).getChildren(), item)) {
                    found = true;
                    break;
                }
            }

        }

        return found;

    }


    /**
     * Import a diagram from a file
     *
     * @param diagramFilePath Path to the diagram file
     * @return Diagram object
     */
    public static diagram importDiagram(String diagramFilePath) {

        /* Read file */
        diagram tempDiagram = ReadXMLFile.diagram(diagramFilePath);

        /* If diagram is null */
        if (tempDiagram == null)
            return null;

        /* Check if diagram name is unique */
        if (DataStorage.project.getDiagram(tempDiagram.getName()) != null) {

            /* Add a number after the name */
            String name = tempDiagram.getName();
            int number = 1;
            while (DataStorage.project.getDiagram(tempDiagram.getName()) != null) {
                String tempName = name + "." + Integer.toString(number);
                tempDiagram.setName(tempName);

                number++;
            }

            System.out.println("Imported diagram '" + name + "' renamed to '" + tempDiagram.getName() + "'");

        }

        /* Add diagram to the project */
        DataStorage.project.addDiagram(tempDiagram);

        System.out.println("Diagram '" + tempDiagram.getName() + "' imported!");


        return DataStorage.project.getDiagram(tempDiagram.getName());
    }


}
