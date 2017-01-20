package de.hsesslingen.swb.gui.canvas.drawingPane.mouseGestures;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.canvas.drawingPane.DrawingPane;
import de.hsesslingen.swb.gui.canvas.drawingPane.ZoomableScrollPane;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.event.EventHandler;
import javafx.scene.input.*;

public class ScrollPaneGestures {

    private DrawingPane drawingPane;
    /**
     *
     */
    EventHandler<DragEvent> onDragOverEventHandler = new EventHandler<DragEvent>() {
        public void handle(DragEvent event) {
            /* data is dragged over the target */
            /* accept it only if it is not dragged from the same node and if it has a string data */
            if (event.getGestureSource() != drawingPane.getScrollPane() && event.getDragboard().hasString()) {
                    /* allow for both copying and moving, whatever user chooses */
                event.acceptTransferModes(TransferMode.COPY_OR_MOVE);
            }
            event.consume();
        }
    };
    private ZoomableScrollPane scrollPane;
    private DragContext dragContext;
    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {

                /* set the selected Element as unselected */
                if (dragContext.clickInElement == false) {

                    if (dragContext.selectedElement != null) {
                        /* remove selected Element */
                        drawingPane.getController().getElementGestures().removeSelectedElement(dragContext.selectedElement);
                    }
                } else {
                    /* reset the clickInElement value */
                    dragContext.clickInElement = false;
                }

            }
        }
    };
    private double scale;
    EventHandler<DragEvent> onDragDroppedEventHandler = new EventHandler<DragEvent>() {
        public void handle(DragEvent event) {
            /* Get the actual zoom-factor */
            scale = drawingPane.getScrollPane().getScaleValue();

                /* Get actual Mouse coordinates on drawingPane */
            double x = xOnDrawingPane(event.getX());
            double y = yOnDrawingPane(event.getY());


            /* data dropped */
            /* read string data of dragboard and look it up in nodeType enum, if exists execute addElement to draw it to the graph */
            Dragboard db = event.getDragboard();
            boolean success = false;

            if (elementTypeContains(db.getString())) {

                String id = BackendAPI.node().addNode(nodeType.valueOf(db.getString()));

                /* Elements are handled individually */
                Element elem;
                if (nodeType.valueOf(db.getString()) == nodeType.dd_Image) {
                    String imageName = db.getRtf();
                    elem = drawingPane.getGraph().getElementModel().addElement(id, nodeType.valueOf(db.getString()), x, y, imageName, true);
                } else {
                    elem = drawingPane.getGraph().getElementModel().addElement(id, nodeType.valueOf(db.getString()), x, y, null, true);
                }

                /* Save defaults in the backend */
                elem.updateAllBackend();

                success = true;

            }

            /* let the source know whether the string was successfully */
            /* transferred and used */
            event.setDropCompleted(success);
            event.consume();
        }
    };

    public ScrollPaneGestures(DrawingPane drawingPane, DragContext dragContext) {
        this.drawingPane = drawingPane;
        this.scrollPane = drawingPane.getScrollPane();
        this.dragContext = dragContext;

        addGestures();

    }

    private static boolean elementTypeContains(String name) {
        for (nodeType c : nodeType.values()) {
            if (c.name().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void addGestures() {
        scrollPane.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        scrollPane.addEventHandler(DragEvent.DRAG_OVER, onDragOverEventHandler);
        scrollPane.addEventHandler(DragEvent.DRAG_DROPPED, onDragDroppedEventHandler);
    }

    private double xOnDrawingPane(double x) {

        /* Add offset of the DrawingArea */
        x += drawingPane.getBoundsInLocal().getMinX() * scale;

        /* Add ScrollOffset */
        double tempWidth = 0.0;

        if (drawingPane.getWidth() * scale < drawingPane.getScrollPane().getWidth()) {
            tempWidth = drawingPane.getScrollPane().getWidth();
        } else {
            tempWidth = drawingPane.getWidth() * scale;
        }
        double xScroll = -1 * drawingPane.getBoundsInLocal().getMinX() * scale + tempWidth;     // Width of drawing Area + Offset

        xScroll -= drawingPane.getScrollPane().getWidth();                                      // Minus ScrollPane Width
        xScroll *= drawingPane.getScrollPane().getHvalue();                                     // Multiply with scroll factor
        x += xScroll;

        /* Calculate new x position with zoomfactor */
        x /= scale;

        return x;
    }

    private double yOnDrawingPane(double y) {

        /* Add offset of the DrawingArea */
        y += drawingPane.getBoundsInLocal().getMinY() * scale;

        /* Add ScrollOffset */
        double tempHeight = 0.0;
        if (drawingPane.getHeight() * scale < drawingPane.getScrollPane().getHeight()) {
            tempHeight = drawingPane.getScrollPane().getHeight();
        } else {
            tempHeight = drawingPane.getHeight() * scale;
        }
        double yScroll = -1 * drawingPane.getBoundsInLocal().getMinY() * scale + tempHeight;    // Height of drawing Area + Offset
        yScroll -= drawingPane.getScrollPane().getHeight();                                     // Minus ScrollPane Height
        yScroll *= drawingPane.getScrollPane().getVvalue();                                     // Multiply with scroll factor
        y += yScroll;

        /* Calculate y position with zoomfactor */
        y /= scale;

        return y;
    }


}
