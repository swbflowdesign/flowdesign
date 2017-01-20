package de.hsesslingen.swb;

import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.node;
import de.hsesslingen.swb.diagram.version;
import de.hsesslingen.swb.diagram.wire;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DiagramAPI {

    /**
     * Get the active diagram
     *
     * @return Active diagram
     */
    public static diagram getActiveDiagram() {
        return DataStorage.activeDiagram;
    }

    /**
     * Set the active diagram (on open or tab change)
     *
     * @param name Name of the active diagram
     */
    public static void setActiveDiagram(String name) {
        if (name == null) {
            DataStorage.activeDiagram = null;
            return;
        }

        if (DataStorage.project != null && DataStorage.project.getDiagram(name) != null)
            DataStorage.activeDiagram = DataStorage.project.getDiagram(name);
    }

    /**
     * Get a Diagram
     *
     * @param name Name of the diagram
     * @return Diagram object
     */
    public static diagram getDiagram(String name) {
        return DataStorage.project.getDiagram(name);
    }


    /**
     * Get a Diagram by a contained node
     *
     * @param nodeID Id of a node
     * @return Diagram object
     */
    public static diagram getDiagramByNode(String nodeID) {

        for (diagram tempDiag : DataStorage.project.getDiagrams()) {
            for (node tempNode : tempDiag.getActiveVersion().getNodes()) {
                if (tempNode.getId().equals(nodeID)) {
                    return tempDiag;
                }
            }
        }

        return null;
    }


    /**
     * Get a Diagram by a contained wire
     *
     * @param wireID Id of a wire
     * @return Diagram object
     */
    public static diagram getDiagramByWire(String wireID) {

        for (diagram tempDiag : DataStorage.project.getDiagrams()) {
            for (wire tempWire : tempDiag.getActiveVersion().getWires()) {
                if (tempWire.getId().equals(wireID)) {
                    return tempDiag;
                }
            }
        }

        return null;
    }


    /**
     * Create a diagram
     *
     * @param name Name of the new diagram
     * @param type Type of the new diagram
     * @return Diagram object
     */
    public static diagram createDiagram(String name, diagramType type) {
        /* Check if the diagram name already exists */
        if (DataStorage.project.getDiagram(name) == null) {
            /* Create diagram */
            DataStorage.project.addDiagram(name, type);
            /* Create first version */
            DataStorage.project.getDiagram(name).addVersion();
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Flow Designer");
            alert.setHeaderText("Der angegebene Diagramname existiert bereits!");
            alert.showAndWait();
            return null;
        }

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(name);


        return DataStorage.project.getDiagram(name);
    }


    /**
     * Delete a diagram
     *
     * @param name Name of the diagram
     * @return Boolean
     */
    public static boolean deleteDiagram(String name) {

        /* If diagram doesn't exist */
        if (DataStorage.project.getDiagram(name) == null) {
            return false;
        }


        /* Ask for permission */
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Flow Designer");
        alert.setHeaderText("Möchten Sie das Diagramm '" + name + "' wirklich löschen?");

        ButtonType buttonTypeYes = new ButtonType("Ja", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeNo = new ButtonType("Nein", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(buttonTypeYes, buttonTypeNo);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeYes) {

            /* Delete the file */
            try {
                File file = new File(DataStorage.projectDirectory + "/diagrams/" + DataStorage.project.getDiagram(name).getType().toString() + "/" + name + ".xml");
                System.out.println(file);
                if (file.delete()) {
                    System.out.println(name + " is deleted!");
                } else {
                    System.out.println("Delete operation is failed.");
                }
            } catch (Exception e) {
                e.printStackTrace();
            }

            /* Remove all references */
            // Parent
            for (diagram tempDiagram : DataStorage.project.getDiagrams()) {
                if (tempDiagram.getParent().equals(name)) {
                    tempDiagram.setParent(DataStorage.project.getDiagram(name).getParent());

                    /* Mark diagram as dirty */
                    BackendAPI.markDiagramDirty(tempDiagram.getName());
                }
            }
            // Link
            for (String[] ref : getNodesReferringToDiagram(name)) {
                DataStorage.project.getDiagram(ref[0]).getActiveVersion().getNode(ref[1]).setLink("");
            }

            /* Remove from project */
            DataStorage.project.removeDiagram(name);


        } else {
            return false;
        }

        return true;

    }


    /**
     * Rename a diagram
     *
     * @param name    Name of the diagram
     * @param newName New name of the diagram
     * @return Boolean
     */
    public static boolean renameDiagram(String name, String newName) {

        /* If diagram doesn't exist */
        if (DataStorage.project.getDiagram(name) == null) {
            return false;
        }


        /* Rename */
        try {
            File file = new File(DataStorage.projectDirectory + "/diagrams/" + DataStorage.project.getDiagram(name).getType().toString() + "/" + name + ".xml");
            File newFile = new File(DataStorage.projectDirectory + "/diagrams/" + DataStorage.project.getDiagram(name).getType().toString() + "/" + newName + ".xml");
            if (file.renameTo(newFile)) {
                System.out.println(name + " renamed to " + newName);
            } else {
                System.out.println("Rename operation is failed.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        /* Rename all references */
        // Parent
        for (diagram tempDiagram : DataStorage.project.getDiagrams()) {
            if (tempDiagram.getParent().equals(name)) {
                tempDiagram.setParent(newName);

                /* Mark diagram as dirty */
                BackendAPI.markDiagramDirty(tempDiagram.getName());
            }
        }
        // Link
        for (String[] ref : getNodesReferringToDiagram(name)) {
            DataStorage.project.getDiagram(ref[0]).getActiveVersion().getNode(ref[1]).setLink(newName);
        }

        /* Rename in project */
        DataStorage.project.getDiagram(name).setName(newName);


        return true;

    }


    /**
     * Get versions of a diagram
     *
     * @param name Name of the diagram
     * @return List with all diagram version numbers
     */
    public static List<Integer> getVersions(String name) {

        List<Integer> nums = new ArrayList<Integer>();

        for (version tempVersion : DataStorage.project.getDiagram(name).getVersions()) {

            // Add the version number to the list
            nums.add(tempVersion.getNum());

        }

        return nums;

    }


    /**
     * Add a new version to the diagram
     *
     * @param name Name of the diagram
     */
    public static void addVersion(String name) {

        diagram diag = DataStorage.project.getDiagram(name);

        // Check if active version is highest version
        if (diag.getActiveVersionNum() != diag.findNextVersionNumber() - 1) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Flow Designer");
            alert.setHeaderText("Die aktuell geöffnete Version " + diag.getActiveVersionNum() + " ist nicht die Version mit der höchsten Versionsnummer!");
            alert.setContentText("Wollen Sie die neue Version auf Basis der aktiven Version erstellen, oder auf Basis der Version mit der höchsten Versionsnummer?");

            ButtonType buttonTypeRight = new ButtonType("Neuste Version", ButtonBar.ButtonData.OTHER);
            ButtonType buttonTypeLeft = new ButtonType("Aktive Version", ButtonBar.ButtonData.OTHER);
            ButtonType buttonTypeCancel = new ButtonType("Abbrechen", ButtonBar.ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeCancel, buttonTypeLeft, buttonTypeRight);

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == buttonTypeRight) {
                diag.setActiveVersionNum(diag.findNextVersionNumber() - 1);
            } else if (result.get() == buttonTypeCancel) {
                return;
            }

        }

        diag.addVersion();

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(diag.getName());

    }


    /**
     * Set the active version of a diagram
     *
     * @param name Name of the diagram
     * @param num  Version number
     */
    public static void setActiveVersion(String name, int num) {

        DataStorage.project.getDiagram(name).setActiveVersionNum(num);

    }


    /**
     * Remove a version of a diagram
     *
     * @param name Name of the diagram
     * @param num  Version number
     */
    public static void removeVersion(String name, int num) {

        DataStorage.project.getDiagram(name).removeVersion(num);

        /* Mark diagram as dirty */
        BackendAPI.markDiagramDirty(name);

    }


    /**
     * Get all nodes of a diagramType
     *
     * @param type Diagram type
     * @return List of the node names
     */
    public static List<String> getEnumTypes(diagramType type) {

        String prefix;
        if (type == diagramType.FlowDesign) {
            prefix = "fd_";
        } else if (type == diagramType.SystemUmweltDiagram) {
            prefix = "sud_";
        } else {
            prefix = "dd_";
        }

        List<String> enums = new ArrayList<String>();
        for (nodeType tempType : nodeType.values()) {

            // If prefix matched, add type to list
            if (tempType.toString().startsWith(prefix)) {
                enums.add(tempType.toString());
            }

        }

        return enums;

    }


    public static List<String[]> getNodesReferringToDiagram(String diagramName) {

        List<String[]> refs = new ArrayList<String[]>();

        /* Searching all references */
        for (diagram tempDiagram : DataStorage.project.getDiagrams()) {
            for (node tempNode : tempDiagram.getActiveVersion().getNodes()) {
                if (tempNode.getLink().equals(diagramName)) {
                    /* Add infos to the list */
                    String[] ref = new String[2];
                    ref[0] = tempDiagram.getName();
                    ref[1] = tempNode.getId();
                    refs.add(ref);

                    /* Mark diagram as dirty */
                    BackendAPI.markDiagramDirty(tempDiagram.getName());
                }
            }
        }

        return refs;

    }

}
