package de.hsesslingen.swb.gui;


import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.enums.wireType;
import de.hsesslingen.swb.gui.canvas.CanvasSpace;
import de.hsesslingen.swb.gui.canvas.MapCanvasSpaces;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.Wire;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;

import java.util.ArrayList;
import java.util.List;

public class shortcuts {

    public static void init() {

        /* ============================================ */
        /* Project */

        /* Save project */
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        if (DataStorage.project != null)
                            Actions.saveProject();
                    }
                }
        );




        /* ============================================ */
        /* Diagram */

        /* Create new diagram */
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.N, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        if (DataStorage.activeDiagram != null) {
                            Actions.createDiagram(DataStorage.activeDiagram.getType(), "", null);
                        }
                    }
                }
        );



        /* Create new node */
        // ToDo: Wenn connector bereits besetzt ist, verschiebe newElement um ein paar Pixel
        // TODO: Scrollpane verschieben
        // Unit (right)
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.U, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberRightConnectors() == 0)
                                return;

                            /* Create new element */
                            String nodeID = BackendAPI.node().addNode(nodeType.fd_Unit);
                            Double calcWidth = (refElement.getElementType() == nodeType.fd_Unit || refElement.getElementType() == nodeType.fd_UnitState || refElement.getElementType() == nodeType.fd_SourceOfFlow || refElement.getElementType() == nodeType.fd_Iteration) ? refElement.getW() / 2 + 50 : refElement.getW() + 50;
                            Double calcX = refElement.getX() + calcWidth + 120;
                            Double calcY = refElement.getY() + refElement.getCenterY();
                            Element newElement = canvas.getDrawingPane().getGraph().getElementModel().addElement(nodeID, nodeType.fd_Unit, calcX, calcY, null, true);
                            newElement.updateAllBackend();

                            /* Get connectors */
                            Connector sourceConn = null;
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.right)
                                    sourceConn = tempConn;
                            }
                            Connector targetConn = null;
                            for (Connector tempConn : newElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.left)
                                    targetConn = tempConn;
                            }

                            /* Add input dataType of the new element */
                            if (!sourceConn.getDataType().equals("")) {
                                targetConn.setDataType(sourceConn.getDataType());
                            }

                            /* Add wire */
                            String wireID = BackendAPI.wire().addWire(
                                    wireType.Arrow,
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()).getConnector(sourceConn.getConnectorId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()).getConnector(targetConn.getConnectorId())
                            );
                            canvas.getDrawingPane().getGraph().getWireModel().addWire(wireID, sourceConn, targetConn);

                            /* Select created Node */
                            canvas.setActiveElement(newElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                            System.out.println("Create new unit element on the right side");

                        }
                    }
                }
        );
        // Join (right)
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.J, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberRightConnectors() == 0)
                                return;

                            /* Create new element */
                            String nodeID = BackendAPI.node().addNode(nodeType.fd_Join);
                            Double calcWidth = (refElement.getElementType() == nodeType.fd_Unit || refElement.getElementType() == nodeType.fd_UnitState || refElement.getElementType() == nodeType.fd_SourceOfFlow || refElement.getElementType() == nodeType.fd_Iteration) ? refElement.getW() / 2 : refElement.getW();
                            Double calcX = refElement.getX() + calcWidth + 120;
                            Double calcY = refElement.getY() + refElement.getCenterY();
                            Element newElement = canvas.getDrawingPane().getGraph().getElementModel().addElement(nodeID, nodeType.fd_Join, calcX, calcY, null, true);
                            newElement.updateAllBackend();

                            /* Get connectors */
                            Connector sourceConn = null;
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.right)
                                    sourceConn = tempConn;
                            }
                            Connector targetConn = null;
                            for (Connector tempConn : newElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.left)
                                    targetConn = tempConn;
                            }

                            /* Add input dataType of the new element */
                            if (!sourceConn.getDataType().equals("")) {
                                targetConn.setDataType(sourceConn.getDataType());
                            }

                            /* Add wire */
                            String wireID = BackendAPI.wire().addWire(
                                    wireType.Arrow,
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()).getConnector(sourceConn.getConnectorId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()).getConnector(targetConn.getConnectorId())
                            );
                            canvas.getDrawingPane().getGraph().getWireModel().addWire(wireID, sourceConn, targetConn);

                            /* Select created Node */
                            canvas.setActiveElement(newElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                            System.out.println("Create new join element on the right side");

                        }
                    }
                }
        );
        // Iteration (right)
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.I, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberRightConnectors() == 0)
                                return;

                            /* Create new element */
                            String nodeID = BackendAPI.node().addNode(nodeType.fd_Iteration);
                            Double calcWidth = (refElement.getElementType() == nodeType.fd_Unit || refElement.getElementType() == nodeType.fd_UnitState || refElement.getElementType() == nodeType.fd_SourceOfFlow || refElement.getElementType() == nodeType.fd_Iteration) ? refElement.getW() / 2 + 50 : refElement.getW() + 50;
                            Double calcX = refElement.getX() + calcWidth + 120;
                            Double calcY = refElement.getY() + refElement.getCenterY();
                            Element newElement = canvas.getDrawingPane().getGraph().getElementModel().addElement(nodeID, nodeType.fd_Iteration, calcX, calcY, null, true);
                            newElement.updateAllBackend();

                            /* Get connectors */
                            Connector sourceConn = null;
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.right)
                                    sourceConn = tempConn;
                            }
                            Connector targetConn = null;
                            for (Connector tempConn : newElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.left)
                                    targetConn = tempConn;
                            }

                            /* Add input dataType of the new element */
                            if (!sourceConn.getDataType().equals("")) {
                                targetConn.setDataType(sourceConn.getDataType());
                            }

                            /* Add wire */
                            String wireID = BackendAPI.wire().addWire(
                                    wireType.Arrow,
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()).getConnector(sourceConn.getConnectorId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()).getConnector(targetConn.getConnectorId())
                            );
                            canvas.getDrawingPane().getGraph().getWireModel().addWire(wireID, sourceConn, targetConn);

                            /* Select created Node */
                            canvas.setActiveElement(newElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                            System.out.println("Create new iteration element on the right side");

                        }
                    }
                }
        );
        // Portal (right)
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberRightConnectors() == 0)
                                return;

                            /* Create new element */
                            String nodeID = BackendAPI.node().addNode(nodeType.fd_Portal);
                            Double calcWidth = (refElement.getElementType() == nodeType.fd_Unit || refElement.getElementType() == nodeType.fd_UnitState || refElement.getElementType() == nodeType.fd_SourceOfFlow || refElement.getElementType() == nodeType.fd_Iteration) ? refElement.getW() / 2 : refElement.getW();
                            Double calcX = refElement.getX() + calcWidth + 120 + 50;
                            Double calcY = refElement.getY() + refElement.getCenterY();
                            Element newElement = canvas.getDrawingPane().getGraph().getElementModel().addElement(nodeID, nodeType.fd_Portal, calcX, calcY, null, true);
                            newElement.updateAllBackend();

                            /* Get connectors */
                            Connector sourceConn = null;
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.right)
                                    sourceConn = tempConn;
                            }
                            Connector targetConn = null;
                            for (Connector tempConn : newElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.left)
                                    targetConn = tempConn;
                            }

                            /* Add input dataType of the new element */
                            if (!sourceConn.getDataType().equals("")) {
                                targetConn.setDataType(sourceConn.getDataType());
                            }

                            /* Add wire */
                            String wireID = BackendAPI.wire().addWire(
                                    wireType.Arrow,
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()).getConnector(sourceConn.getConnectorId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()).getConnector(targetConn.getConnectorId())
                            );
                            canvas.getDrawingPane().getGraph().getWireModel().addWire(wireID, sourceConn, targetConn);

                            /* Select created Node */
                            canvas.setActiveElement(newElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                            System.out.println("Create new portal element on the right side");

                        }
                    }
                }
        );
        // Resource (down)
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.R, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberBottomConnectors() == 0)
                                return;

                            /* Create new element */
                            String nodeID = BackendAPI.node().addNode(nodeType.fd_ResourceOval);
                            Double calcHeight = (refElement.getElementType() == nodeType.fd_Unit || refElement.getElementType() == nodeType.fd_UnitState || refElement.getElementType() == nodeType.fd_SourceOfFlow || refElement.getElementType() == nodeType.fd_Iteration) ? refElement.getH() / 2 : refElement.getH();
                            Double calcX = refElement.getX() + refElement.getCenterX();
                            Double calcY = refElement.getY() + calcHeight + 120;
                            Element newElement = canvas.getDrawingPane().getGraph().getElementModel().addElement(nodeID, nodeType.fd_ResourceOval, calcX, calcY, null, true);
                            newElement.updateAllBackend();

                            /* Get connectors */
                            Connector sourceConn = null;
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.bottom)
                                    sourceConn = tempConn;
                            }
                            Connector targetConn = null;
                            for (Connector tempConn : newElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.top)
                                    targetConn = tempConn;
                            }

                            /* Add wire */
                            String wireID = BackendAPI.wire().addWire(
                                    wireType.ArrowDot,
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(refElement.getElementId()).getConnector(sourceConn.getConnectorId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()),
                                    DataStorage.activeDiagram.getActiveVersion().getNode(newElement.getElementId()).getConnector(targetConn.getConnectorId())
                            );
                            canvas.getDrawingPane().getGraph().getWireModel().addWire(wireID, sourceConn, targetConn);

                            /* Select created Node */
                            // ToDo: canvas.setActiveElement(newElement);

                            System.out.println("Create new resource element underneath");

                        }
                    }
                }
        );



        /* Jump between elements */
        // Move up
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.UP, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberTopConnectors() == 0)
                                return;

                            /* Get connector */
                            List<Connector> conns = new ArrayList<Connector>();
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.top) {
                                    conns.add(tempConn);
                                }
                            }
                            if (conns.size() == 0)
                                return;

                            /* Find connected wire */
                            Wire wire = null;
                            for (Wire tempWire : canvas.getDrawingPane().getGraph().getWireModel().getAllWires()) {
                                if (conns.contains(tempWire.getTargetConnector())) {
                                    wire = tempWire;
                                    break;
                                }
                            }
                            if (wire == null)
                                return;

                            /* Get connected element */
                            Element connElement = wire.getSourceElement();

                            /* Select created Node */
                            canvas.setActiveElement(connElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                        }
                    }
                }
        );
        // Move down
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.DOWN, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberBottomConnectors() == 0)
                                return;

                            List<Connector> conns = new ArrayList<Connector>();
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.bottom) {
                                    conns.add(tempConn);
                                }
                            }
                            if (conns.size() == 0)
                                return;

                            /* Find connected wire */
                            Wire wire = null;
                            for (Wire tempWire : canvas.getDrawingPane().getGraph().getWireModel().getAllWires()) {
                                if (conns.contains(tempWire.getSourceConnector())) {
                                    wire = tempWire;
                                    break;
                                }
                            }
                            if (wire == null)
                                return;

                            /* Get connected element */
                            Element connElement = wire.getTargetElement();

                            /* Select created Node */
                            canvas.setActiveElement(connElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                        }
                    }
                }
        );
        // Move left
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.LEFT, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberLeftConnectors() == 0)
                                return;

                            /* Get connector */
                            List<Connector> conns = new ArrayList<Connector>();
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.left) {
                                    conns.add(tempConn);
                                }
                            }
                            if (conns.size() == 0)
                                return;

                            /* Find connected wire */
                            Wire wire = null;
                            for (Wire tempWire : canvas.getDrawingPane().getGraph().getWireModel().getAllWires()) {
                                if (conns.contains(tempWire.getTargetConnector())) {
                                    wire = tempWire;
                                    break;
                                }
                            }
                            if (wire == null)
                                return;

                            /* Get connected element */
                            Element connElement = wire.getSourceElement();

                            /* Select created Node */
                            canvas.setActiveElement(connElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                        }
                    }
                }
        );
        // Move right
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.RIGHT, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && DataStorage.activeDiagram.getType() == diagramType.FlowDesign && canvas.getActiveElement() != null
                                && DataStorage.activeDiagram.getActiveVersion().getNode(canvas.getActiveElement().getElementId()) != null) {

                            /* Get selected element */
                            Element refElement = canvas.getActiveElement();

                            /* Check if actions is allowed for refElement */
                            if (refElement.getNumberRightConnectors() == 0)
                                return;

                            /* Get connector */
                            List<Connector> conns = new ArrayList<Connector>();
                            for (Connector tempConn : refElement.getConnectors()) {
                                if (tempConn.getConnectorOrientation() == connectorOrientation.right) {
                                    conns.add(tempConn);
                                }
                            }
                            if (conns.size() == 0)
                                return;

                            /* Find connected wire */
                            Wire wire = null;
                            for (Wire tempWire : canvas.getDrawingPane().getGraph().getWireModel().getAllWires()) {
                                if (conns.contains(tempWire.getSourceConnector())) {
                                    wire = tempWire;
                                    break;
                                }
                            }
                            if (wire == null)
                                return;

                            /* Get connected element */
                            Element connElement = wire.getTargetElement();

                            /* Select created Node */
                            canvas.setActiveElement(connElement);

                            /* Focus is on PropertiesTab */
                            FlowProperties.getFirstText().requestFocus();

                        }
                    }
                }
        );



        /* Quick edit of elements label */
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.ENTER, KeyCombination.CONTROL_DOWN),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && canvas.getActiveElement() != null) {
                            System.out.println("Element quick edit");

                            /* Get selected element */
                            Element element = canvas.getActiveElement();

                            /* Enable/ disable textfield */
                            if (element.getTextField() != null) {
                                if (element.getTextField().isDisabled()) {
                                    element.activateTextField();
                                } else {
                                    element.deactivateTextField();
                                }
                            }

                        }
                    }
                }
        );



        /* Remove element */
        Main.getMainScene().getAccelerators().put(
                new KeyCodeCombination(KeyCode.DELETE),
                new Runnable() {
                    @Override
                    public void run() {
                        /* Get canvas */
                        CanvasSpace canvas = MapCanvasSpaces.getCanvasSpaceOfDiagram(DataStorage.activeDiagram.getName());

                        if (DataStorage.activeDiagram != null && canvas.getActiveElement() != null) {
                            System.out.println("Element removed");

                            /* Get selected element */
                            Element element = canvas.getActiveElement();

                            /* remove the element from backend*/
                            BackendAPI.node().removeNode(element.getElementId());

                            /* remove the element from drawingpane */
                            canvas.getDrawingPane().getChildren().remove(element);

                            /* remove all wires whch are connected with this element */
                            canvas.getDrawingPane().getGraph().getWireModel().removeWiresOfElement(element);

                            /* hide the properties*/
                            RightSideBar.getRightSideBar().getSelectionModel().select(0);
                            FlowProperties.showDiagramSettings();

                            /* remove the element children*/
                            element.getChildren().clear();

                        }
                    }
                }
        );

    }

}
