package de.hsesslingen.swb.gui.canvas;

import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.diagram;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class MapCanvasSpaces {

    /**
     * Set CanvasSpace of a diagram in the dataStorage
     *
     * @param diag   Diagram object
     * @param canvas Canvas object
     */
    public static void setCanvasSpaceOfDiagram(diagram diag, CanvasSpace canvas) {
        if (DataStorage.canvasSpaces.containsKey(diag.getName()))
            return;

        DataStorage.canvasSpaces.put(diag.getName(), canvas);
    }


    /**
     * Remove CanvasSpace from the map
     *
     * @param diagramName Name of the Diagram
     */
    public static void removeCanvasSpaceOfDiagram(String diagramName) {
        if (!DataStorage.canvasSpaces.containsKey(diagramName))
            return;

        DataStorage.canvasSpaces.remove(diagramName);
    }


    /**
     * Remove all CanvasSpaces from the map
     */
    public static void removeAllCanvasSpaces() {
        DataStorage.canvasSpaces = new HashMap();
    }


    /**
     * Get the CanvasSpace of a diagram
     *
     * @param diagramName Name of the Diagram
     * @return CanvasSpace
     */
    public static CanvasSpace getCanvasSpaceOfDiagram(String diagramName) {
        if (!DataStorage.canvasSpaces.containsKey(diagramName))
            return null;

        return (CanvasSpace) DataStorage.canvasSpaces.get(diagramName);
    }


    /**
     * Get diagram name of a CanvasSpace
     *
     * @param canvas CanvasSpace
     * @return Name of the diagram
     */
    public static diagram getDiagramOfCanvasSpace(CanvasSpace canvas) {
        if (!DataStorage.canvasSpaces.containsValue(canvas))
            return null;

        Iterator it = DataStorage.canvasSpaces.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            if (pair.getValue() == canvas)
                return DataStorage.project.getDiagram(pair.getKey().toString());
            it.remove(); // avoids a ConcurrentModificationException
        }

        return null;
    }


    /**
     * Update the diagram name as key for the CanvasSpace
     *
     * @param oldName Old name of the diagram
     * @param newName New name of the diagram
     * @return Boolean
     */
    public static boolean updateDiagramName(String oldName, String newName) {
        if (!DataStorage.canvasSpaces.containsKey(oldName))
            return false;

        Object obj = DataStorage.canvasSpaces.remove(oldName);
        DataStorage.canvasSpaces.put(newName, obj);

        return true;
    }

}
