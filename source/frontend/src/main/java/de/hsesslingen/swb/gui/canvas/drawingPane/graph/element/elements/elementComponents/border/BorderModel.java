package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.border;

import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;

public class BorderModel {

    private Element element;
    private nodeType type;
    private Border border;
    private double w;
    private double h;


    public BorderModel(Element element) {

        this.element = element;
        w = element.getW();
        h = element.getH();

        border = new Border(w, h);
        type = element.getElementType();


        /* Add the necessary connectorPoints */
        switch (type) {

            /* for oval Elements */
            case fd_Unit:
            case fd_ResourceOval:
            case fd_UnitState:
            case sud_System:
            case fd_SinkOfFlow:
            case fd_SourceOfFlow:
            case fd_Iteration:
                border.relocate(0 - w / 2, 0 - h / 2);
                completeBorder();
                break;

            /* for rectangle elements */
            default:
                border.relocate(0, 0);
                completeBorder();
                break;
        }

    }

    public void completeBorder() {
        element.getChildren().add(border);
        border.toFront();
        border.setVisible(false);
    }

    public Border getBorder() {
        return border;
    }

    public void removeBorder() {
        border = null;
    }
}
