package de.hsesslingen.swb.gui.canvas.drawingPane.mouseGestures;

import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.FlowProperties;
import de.hsesslingen.swb.gui.canvas.drawingPane.DrawingPane;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.Wire;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;

public class WireGestures {

    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;
    private ContextMenu contextMenu;
    private DrawingPane drawingPane;
    EventHandler<ContextMenuEvent> onContextMenuRequestHandler = new EventHandler<ContextMenuEvent>() {

        @Override
        public void handle(ContextMenuEvent event) {

            /* get the actual wire */
            Wire wire = (Wire) event.getSource();

            /* Build the context menu */
            contextMenu.getItems().clear();

            contextMenu.getItems().addAll(menuItem1);

            //can't change the output of a Join or Split
            if (wire.getSourceConnector().getElement().getElementType() == nodeType.fd_Split || wire.getSourceConnector().getElement().getElementType() == nodeType.fd_Join) {

            } else {
                contextMenu.getItems().addAll(menuItem2);
            }

            contextMenu.getItems().addAll(menuItem3);


            /* display the context menu */
            contextMenu.show(wire, event.getScreenX(), event.getScreenY());

            menuItem1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    drawingPane.getGraph().getWireModel().removeWire(wire);
                }
            });

            menuItem2.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FlowProperties.showDataDialog(wire.getSourceConnector());
                }
            });

            menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    FlowProperties.showDataDialog(wire.getTargetConnector());
                }
            });
        }
    };
    private DragContext dragContext;

    public WireGestures(DrawingPane drawingPane, DragContext dragContext) {
        this.drawingPane = drawingPane;
        this.dragContext = dragContext;

        contextMenu = new ContextMenu();

        menuItem1 = new MenuItem("Löschen");
        menuItem2 = new MenuItem("Output Datentyp ändern");
        menuItem3 = new MenuItem("Input Datentyp ändern");
    }

    public void addGestures(Wire wire) {
        wire.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, onContextMenuRequestHandler);
    }

    public void removeGestures(Wire wire) {
        wire.removeEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, onContextMenuRequestHandler);
    }

}
