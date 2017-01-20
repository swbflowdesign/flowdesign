package de.hsesslingen.swb.gui.canvas.drawingPane.mouseGestures;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.Actions;
import de.hsesslingen.swb.gui.FlowProperties;
import de.hsesslingen.swb.gui.RightSideBar;
import de.hsesslingen.swb.gui.canvas.drawingPane.DrawingPane;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.input.ContextMenuEvent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import static de.hsesslingen.swb.gui.FlowProperties.refreshProperties;


public class ElementGestures {

    EventHandler<MouseEvent> onMouseClickedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {

            /* Open link */
            if (event.getClickCount() == 2 && event.getButton() == MouseButton.PRIMARY) {
                Element element = (Element) event.getSource();
                if (!element.getLink().equals(""))
                    Actions.openDiagram(element.getLink());
            }
        }
    };
    private DrawingPane drawingPane;
    private DragContext dragContext;
    EventHandler<MouseEvent> onMouseEnteredEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            Element element = (Element) event.getSource();

            /* this is our temp element */
            dragContext.tempElement = element;

            /* add a border to the element */
            addBorder(element);

            /* set the output connectors visible */
            for (Connector connector : element.getConnectors()) {
                if (connector.getConnectorOrientation() == connectorOrientation.right ||
                        connector.getConnectorOrientation() == connectorOrientation.bottom ||
                        connector.getConnectorOrientation() == connectorOrientation.center ||
                        connector.getConnectorOrientation() == connectorOrientation.topPoint ||
                        connector.getConnectorOrientation() == connectorOrientation.leftPoint ||
                        connector.getConnectorOrientation() == connectorOrientation.rightPoint ||
                        connector.getConnectorOrientation() == connectorOrientation.bottomPoint
                        )
                    connector.setVisible(true);
                connector.toFront();
            }
        }
    };
    EventHandler<MouseEvent> onMouseExitedEventHandler = new EventHandler<MouseEvent>() {
        @Override
        public void handle(MouseEvent event) {

            if (dragContext.tempElement.getConnectors() == null)
                return;

            /* set all connector visible */
            for (Connector connector : dragContext.tempElement.getConnectors()) {
                connector.setVisible(false);
            }

            /* remove the Border */
            removeBorder(dragContext.tempElement);
        }
    };
    EventHandler<MouseEvent> onMouseDraggedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                Element node = (Element) event.getSource();

                /* calculate the offset */
                double offsetX = event.getScreenX() + dragContext.x;
                double offsetY = event.getScreenY() + dragContext.y;

                /* adjust the offset in case we are zoomed */
                double scale = drawingPane.getScrollPane().getScaleValue();

                offsetX /= scale;
                offsetY /= scale;

                /* for the two connectorPoints */
                node.relocate(offsetX, offsetY);

                /* refresh properties */
                if (dragContext.selectedElement != null) {
                    refreshProperties(node);
                }

                /* set that there was a drag */
                dragContext.isDragged = true;
            }
        }
    };
    EventHandler<MouseEvent> onMouseReleasedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {
                drawingPane.getScrollPane().setPannable(true);

                /* get the actual element */
                Element element = (Element) event.getSource();

                /* check if there is a other element selected */
                if (dragContext.selectedElement == null) {

                    /* check if the other element was dragged */
                    if (dragContext.isDragged == false) {

                        /* set the selected Element */
                        setSelectedElement(element);
                    }
                }

                /* Activate the textfield if available
                if(element.getTextField() != null) {
                    element.activateTextField();
                } */

            }
        }
    };
    private ContextMenu contextMenu = new ContextMenu();
    EventHandler<MouseEvent> onMousePressedEventHandler = new EventHandler<MouseEvent>() {

        @Override
        public void handle(MouseEvent event) {
            if (event.getButton().equals(MouseButton.PRIMARY)) {

                /* if the click is not inside the contextMenu, hide it */
                contextMenu.hide();

                drawingPane.getScrollPane().setPannable(false);

                /* get the element and safe it in dragContext*/
                Element element = (Element) event.getSource();

                /* we work with this element */
                dragContext.sourceElement = element;

                /* the press is in a Element */
                dragContext.clickInElement = true;

                /* the press is in a other Element as before */
                if (dragContext.selectedElement != element && dragContext.selectedElement != null) {
                    removeSelectedElement(dragContext.selectedElement);
                }

                /* it shall be in front of other nodes */
                if (element.getElementType() != nodeType.dd_Image)
                    element.toFront();

                /* get the actual scale factor */
                double scale = drawingPane.getScrollPane().getScaleValue();

                /* set the coordinates of node in dragContext */
                dragContext.x = element.getBoundsInParent().getMinX() * scale - event.getScreenX() + element.correctionOfX() * scale;
                dragContext.y = element.getBoundsInParent().getMinY() * scale - event.getScreenY() + element.correctionOfY() * scale;

                /* set that there was no drag yet */
                dragContext.isDragged = false;

            }
        }
    };
    private MenuItem menuItem1;
    private MenuItem menuItem2;
    private MenuItem menuItem3;
    EventHandler<ContextMenuEvent> onContextMenuRequestHandler = new EventHandler<ContextMenuEvent>() {

        @Override
        public void handle(ContextMenuEvent event) {

            /* get the actual element */
            Element element = (Element) event.getSource();

            /* Build the context menu */
            contextMenu.getItems().clear();
            if (!element.getLink().equals("")) {
                contextMenu.getItems().addAll(menuItem1, menuItem3, menuItem2);
            } else {
                contextMenu.getItems().addAll(menuItem1, menuItem3);
            }

            /* display the context menu */
            contextMenu.show(element, event.getScreenX(), event.getScreenY());

            menuItem1.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    /* remove the element from backend*/
                    BackendAPI.node().removeNode(element.getElementId());

                    /* remove the element from drawingpane */
                    drawingPane.getChildren().remove(element);

                    /* remove all wires whch are connected with this element */
                    drawingPane.getGraph().getWireModel().removeWiresOfElement(element);

                    /* hide the properties*/
                    hideProperties();

                    /* remove the element children*/
                    element.getChildren().clear();
                }
            });

            menuItem2.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent event) {

                    /* Open link */
                    if (!element.getLink().equals(""))
                        Actions.openDiagram(element.getLink());
                }
            });

            menuItem3.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent event) {
                    Actions.createDiagram(diagramType.FlowDesign, element.getLabel(), element);
                }
            });

        }
    };


    public ElementGestures(DrawingPane drawingPane, DragContext dragContext) {
        this.drawingPane = drawingPane;
        this.dragContext = dragContext;
        addMenuContextEntry();
    }

    public void addGestures(Element element) {
        element.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_ENTERED, onMouseEnteredEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);
        element.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, onContextMenuRequestHandler);
    }

    public void removeGestures(Element element) {
        element.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_ENTERED, onMouseEnteredEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_CLICKED, onMouseClickedEventHandler);
        element.removeEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, onContextMenuRequestHandler);

    }

    public void addClickGestures(Element element) {
        element.addEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        element.addEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, onContextMenuRequestHandler);
    }

    public void removeClickGestures(Element element) {
        element.removeEventHandler(MouseEvent.MOUSE_PRESSED, onMousePressedEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_DRAGGED, onMouseDraggedEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_RELEASED, onMouseReleasedEventHandler);
        element.removeEventHandler(ContextMenuEvent.CONTEXT_MENU_REQUESTED, onContextMenuRequestHandler);
    }

    public void addMouseOverGestures(Element element) {
        element.addEventHandler(MouseEvent.MOUSE_ENTERED, onMouseEnteredEventHandler);
        element.addEventHandler(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);
    }

    public void removeMouseOverGestures(Element element) {
        element.removeEventHandler(MouseEvent.MOUSE_ENTERED, onMouseEnteredEventHandler);
        element.removeEventHandler(MouseEvent.MOUSE_EXITED, onMouseExitedEventHandler);
    }

    public void addBorder(Element element) {

        /* display the border */
        element.getElementBorder().setVisible(true);

    }

    public void removeBorder(Element element) {

        /* hide the border */
        element.getElementBorder().setVisible(false);
    }

    public void removeSelectedElement(Element element) {

        /* add MouseOver Gestures */
        addMouseOverGestures(element);

        /* hide the Properties Panel */
        hideProperties();

        /* hide Border */
        element.getElementBorder().setVisible(false);
        element.getElementBorder().getRecBorder().setStroke(Color.BLUE);

        /* hide Connectors */
        element.getConnectors().forEach(Connector -> {
            Connector.setVisible(false);
        });

        /* reset selected Element */
        dragContext.selectedElement = null;

    }

    public void showProperties(Element element) {

        //###################################
        //wird aufgerufen wenn ein element ohne Drag angeklickt wird
        //###################################
        FlowProperties.updateProperties(element);
        RightSideBar.getRightSideBar().getSelectionModel().select(1);

    }

    public void hideProperties() {

        //###################################
        //wird aufgerufen wenn kein Element mehr ausgewählt ist
        //###################################
        RightSideBar.getRightSideBar().getSelectionModel().select(0);
        FlowProperties.showDiagramSettings();

    }

    public void addMenuContextEntry() {
        /* Add Remove MenuItem to ContextMenu */
        menuItem1 = new MenuItem("Löschen");

        /* Add OpenLink MenuItem to ContextMenu */
        menuItem2 = new MenuItem("Link öffnen");

        /* Add CreateDiagram MenuItem to ContextMenu */
        menuItem3 = new MenuItem("Diagramm generieren");
    }

    public void setSelectedElement(Element element) {

        /* set the selected Element */
        dragContext.selectedElement = element;

        /* show the Border of the Element */
        dragContext.selectedElement.getElementBorder().setVisible(true);
        dragContext.selectedElement.getElementBorder().getRecBorder().setStroke(Color.RED);

        /* show the properties bar */
        showProperties(dragContext.selectedElement);

        /* remove the mouseOver Effects of selectedElement */
        removeMouseOverGestures(dragContext.selectedElement);
    }

}