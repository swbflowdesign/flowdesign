package de.hsesslingen.swb;

import de.hsesslingen.swb.xmlParser.ReadXMLFile;
import de.hsesslingen.swb.xmlParser.WriteXMLFile;
import javafx.collections.FXCollections;

import java.util.ArrayList;
import java.util.HashMap;

public class BackendAPI {

    /**
     * Return access to the projectAPI
     *
     * @return projectAPI
     */
    public static ProjectAPI project() {
        return null;
    }


    /**
     * Return access to the diagramAPI
     *
     * @return diagramAPI
     */
    public static DiagramAPI diagram() {
        return null;
    }


    /**
     * Return access to the nodeAPI
     *
     * @return nodeAPI
     */
    public static NodeAPI node() {
        return null;
    }


    /**
     * Return access to the connectorAPI
     *
     * @return connectorAPI
     */
    public static ConnectorAPI connector() {
        return null;
    }


    /**
     * Return access to the wireAPI
     *
     * @return wireAPI
     */
    public static WireAPI wire() {
        return null;
    }


    /**
     * Return access to the jointAPI
     *
     * @return jointAPI
     */
    public static JointAPI joint() {
        return null;
    }


    /**
     * Return access to the fileAPI
     *
     * @return fileAPI
     */
    public static FileAPI file() {
        return null;
    }


    /**
     * Initialize the dataStorage
     */
    public static void initDataStorage() {

        /* Add default data types */
        DataStorage.dataTypes.addAll(DataStorage.defaultDataTypes);
        DataStorage.dataTypeDescriptions.putAll(DataStorage.defaultDataTypeDescriptions);

        /* Load storage from file */
        ReadXMLFile.storage(DataStorage.storageFilePath);

    }


    /**
     * Clear the dataStorage
     */
    public static void clearDataStorage() {

        DataStorage.projectDirectory = "";
        DataStorage.project = null;

        DataStorage.dirtyDiagrams = new ArrayList<String>();

        DataStorage.activeDiagram = null;
        DataStorage.canvasSpaces = new HashMap();
        DataStorage.dataTypes = FXCollections.observableArrayList();
        DataStorage.dataTypeDescriptions = new HashMap<>();

    }


    /**
     * Add a custom data type to the dataStorage
     *
     * @param type Data type as string
     * @return Boolean
     */
    public static boolean addDataType(String type) {

        if (type != null && !type.equals("") && !DataStorage.dataTypes.contains(type)) {
            /* Add type */
            DataStorage.dataTypes.add(type);

            /* Write to file */
            WriteXMLFile.storage();

            return true;
        }

        return false;

    }


    /**
     * Remove a custom data type from the dataStorage
     *
     * @param type Data type as string
     * @return Boolean
     */
    public static boolean removeDataType(String type) {

        if (DataStorage.dataTypes.contains(type) && !DataStorage.defaultDataTypes.contains(type)) {
            /* Remove type */
            DataStorage.dataTypes.remove(type);

            /* Write to file */
            WriteXMLFile.storage();

            return true;
        }

        return false;

    }


    /**
     * Get a data type description
     *
     * @param type Data type as string
     * @return Description
     */
    public static String getDataTypeDescription(String type) {

        if (DataStorage.dataTypeDescriptions.containsKey(type)) {
            return DataStorage.dataTypeDescriptions.get(type);
        }

        return "";

    }


    /**
     * Add a custom data type description to the dataStorage
     *
     * @param type        Data type as string
     * @param description Data type description
     * @return Boolean
     */
    public static boolean addDataTypeDescription(String type, String description) {

        if (type != null && !type.equals("") && DataStorage.dataTypes.contains(type) && !DataStorage.defaultDataTypes.contains(type)) {
            /* Add description */
            DataStorage.dataTypeDescriptions.put(type, description);

            /* Write to file */
            WriteXMLFile.storage();

            return true;
        }

        return false;

    }


    /**
     * Remove a custom data type description from the dataStorage
     *
     * @param type Data type as string
     * @return Boolean
     */
    public static boolean removeDataTypeDescription(String type) {

        if (DataStorage.dataTypes.contains(type) && !DataStorage.defaultDataTypes.contains(type)) {
            /* Remove type */
            DataStorage.dataTypeDescriptions.remove(type);

            /* Write to file */
            WriteXMLFile.storage();

            return true;
        }

        return false;

    }


    /**
     * Mark a diagram dirty
     *
     * @param diagramName Name of the diagram
     */
    public static void markDiagramDirty(String diagramName) {

        if (!DataStorage.dirtyDiagrams.contains(diagramName)) {
            DataStorage.dirtyDiagrams.add(diagramName);
        }

    }


    /**
     * Remove all diagrams from the dirty list
     */
    public static void markAllDiagramsUndirty() {
        DataStorage.dirtyDiagrams = new ArrayList<String>();
    }

}