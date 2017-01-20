package de.hsesslingen.swb;

import de.hsesslingen.swb.Storage.LastProject;
import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.project.project;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DataStorage {

    /* Path of the project directory */
    public static String projectDirectory;


    /* Containing the project with all the diagrams */
    public static project project;


    /* Storage file path */
    public static String storageFilePath = "backend/src/resources/storage.xml";


    /* Containing the last 10 edited projects */
    public static List<LastProject> lastProjects = new ArrayList<LastProject>();


    /* Dirty diagrams */
    public static List<String> dirtyDiagrams = new ArrayList<String>();


    /* Runtime storage */
    public static diagram activeDiagram = null;

    public static Map canvasSpaces = new HashMap();

    public static ObservableList<String> defaultDataTypes = FXCollections.observableArrayList("byte", "short", "int", "long", "float", "double", "char", "String", "Boolean");
    public static Map<String, String> defaultDataTypeDescriptions = new HashMap<>();
    public static ObservableList<String> dataTypes = FXCollections.observableArrayList();
    public static Map<String, String> dataTypeDescriptions = new HashMap<>();

    static {
        defaultDataTypeDescriptions.put("byte", "Ganzzahl (1-byte)");
        defaultDataTypeDescriptions.put("short", "Ganzzahl (2-byte)");
        defaultDataTypeDescriptions.put("int", "Ganzzahl (4-byte)");
        defaultDataTypeDescriptions.put("long", "Ganzzahl (8-byte)");
        defaultDataTypeDescriptions.put("float", "Gleitkommazahl (4-byte)");
        defaultDataTypeDescriptions.put("double", "Gleitkommazahl mit höherer Genauigkeit (8-byte)");
        defaultDataTypeDescriptions.put("char", "Einzelnes Zeichen in Unicode-Kodierung (2-byte)");
        defaultDataTypeDescriptions.put("String", "Zeichenkette bestehend aus einzelnen Zeichen in Unicode-Kodierung");
        defaultDataTypeDescriptions.put("Boolean", "Wahrheitswert (true, false)");
    }

}
