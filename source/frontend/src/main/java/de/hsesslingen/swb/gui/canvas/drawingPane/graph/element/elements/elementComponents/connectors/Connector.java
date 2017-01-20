package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors;


import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.DiagramAPI;
import de.hsesslingen.swb.diagram.connector;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.node;
import de.hsesslingen.swb.gui.FlowMainContent;
import de.hsesslingen.swb.gui.canvas.MapCanvasSpaces;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Connector extends Pane {

    protected String id;
    protected Element element;
    protected double radius;
    protected de.hsesslingen.swb.diagram.enums.connectorOrientation connectorOrientation;
    protected double x;
    protected double y;
    protected Color color;
    protected boolean connected;
    protected String dataType;
    protected StringProperty dataTypeProperty;
    protected Node view;

    public Connector(String id, Element element) {
        this.id = id;
        this.element = element;
        dataType = "";
        dataTypeProperty = new SimpleStringProperty(null);
        radius = 12;
        this.connected = false;
    }


    public String getConnectorId() {
        return id;
    }

    public void setConnectorId(String id) {
        this.id = id;
    }

    public Element getElement() {
        return element;
    }

    public void setElement(Element element) {
        this.element = element;
    }

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    public de.hsesslingen.swb.diagram.enums.connectorOrientation getConnectorOrientation() {
        return connectorOrientation;
    }

    public void setConnectorOrientation(de.hsesslingen.swb.diagram.enums.connectorOrientation connectorOrientation) {
        this.connectorOrientation = connectorOrientation;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public boolean isConnected() {
        return connected;
    }

    public void setConnected(boolean connected) {
        this.connected = connected;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String newDataType) {
        this.dataType = newDataType;
        this.dataTypeProperty.setValue(newDataType);

        /* if there is a link on another diagram set the source- or sinkOfFlow  */
        if (this.getElement().getLink().length() != 0) {
            updateDataTypeOfAChildDiagram(newDataType);
        }
        /* if there is a Unit somewhere that has a link on this diagram */
        else if (DiagramAPI.getNodesReferringToDiagram(DiagramAPI.getActiveDiagram().getName()) != null)
            updateDataTypeOfAParentsNode(newDataType);

        /* set output of Split when input changes */
        if (getElement().getElementType() == nodeType.fd_Split && connectorOrientation == de.hsesslingen.swb.diagram.enums.connectorOrientation.left) {
            autoSetOutputForSplit(newDataType);
        }

        /* set output of Join when input changes */
        if (getElement().getElementType() == nodeType.fd_Join && connectorOrientation == de.hsesslingen.swb.diagram.enums.connectorOrientation.left) {
            autoSetOutputForJoin(newDataType);
        }

        /* Update backend */
        BackendAPI.connector().setType(this.element.getElementId(), this.id, newDataType);
    }

    public StringProperty getDataTypeProperty() {
        return dataTypeProperty;
    }

    public void setView(Node view) {
        this.view = view;
        getChildren().add(view);
    }

    /**
     * updates the SourceOfFlow or SinkOfFlow of a diagram
     * when the In- or Output of a Unit that has a link to this diagram changes.
     *
     * @param newDataType the new value for the Sink- or SourceOfFlow (String)
     */
    private void updateDataTypeOfAChildDiagram(String newDataType) {

        //if diagram is already opened in a Tab update frontend
        if (FlowMainContent.getTab(getElement().getLink()) != null) {
            List<Element> elements = MapCanvasSpaces.getCanvasSpaceOfDiagram(getElement().getLink()).getDrawingPane().getGraph().getElementModel().getAllElements();
            if (elements != null) {
                for (Element element : elements) {
                    if (element.getElementType() == nodeType.fd_SourceOfFlow && getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.left) {
                        element.getConnectors().get(0).setDataType(newDataType);
                    } else if (element.getElementType() == nodeType.fd_SinkOfFlow && getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.right) {
                        element.getConnectors().get(0).dataType = newDataType;
                        element.getConnectors().get(0).dataTypeProperty.setValue(newDataType);
                    }
                }
            }
        }

        //always update backend
        //for sourceOfFlow
        if (getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.left) {
            List<node> nodes = DataStorage.project.getDiagram(this.getElement().getLink()).getActiveVersion().getNodes();
            if (nodes != null) {
                for (node node : nodes) {
                    if (node.getType() == nodeType.fd_SourceOfFlow) {
                        BackendAPI.connector().setType(node.getId(), node.getConnectors().get(0).getId(), newDataType);

                    }
                }
            }
        }
        //for sinkOfFlow
        else if (getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.right) {
            List<node> nodes = DataStorage.project.getDiagram(this.getElement().getLink()).getActiveVersion().getNodes();
            if (nodes != null) {
                for (node node : nodes) {
                    if (node.getType() == nodeType.fd_SinkOfFlow) {
                        BackendAPI.connector().setType(node.getId(), node.getConnectors().get(0).getId(), newDataType);
                    }
                }
            }
        }
    }

    /**
     * updates the DataType of every Unit that has a link to the diagram,
     * when the Source- or SinkOfFlow In-/Output changes
     *
     * @param newDataType the new DataType for the In-/Output (Sting)
     */
    private void updateDataTypeOfAParentsNode(String newDataType) {

        //if the Output dataType of the SourceOfFlow changes
        if (getElement().getElementType() == nodeType.fd_SourceOfFlow) {
            List<String[]> references = DiagramAPI.getNodesReferringToDiagram(DiagramAPI.getActiveDiagram().getName());
            for (String[] reference : references) {

                //update backend
                List<node> nodes = DataStorage.project.getDiagram(reference[0]).getActiveVersion().getNodes();
                if (nodes != null) {
                    for (node node : nodes) {
                        if (node.getId().equals(reference[1])) {
                            List<connector> connectors = node.getConnectors();
                            if (connectors != null) {
                                for (connector connector : connectors) {
                                    if (connector.getOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.left) {
                                        BackendAPI.connector().setType(node.getId(), connector.getId(), newDataType);
                                    }
                                }
                            }
                        }
                    }
                }

                //update frontend
                //if diagram is already opened in a Tab
                if (FlowMainContent.getTab(reference[0]) != null) {
                    List<Element> elements = MapCanvasSpaces.getCanvasSpaceOfDiagram(reference[0]).getDrawingPane().getGraph().getElementModel().getAllElements();
                    if (elements != null) {
                        for (Element element : elements) {
                            if (element.getElementId().equals(reference[1])) {
                                List<Connector> connectors = element.getConnectors();
                                if (connectors != null) {
                                    for (Connector connector : connectors) {
                                        if (connector.getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.left) {
                                            connector.dataType = newDataType;
                                            connector.dataTypeProperty.setValue(newDataType);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        //if the Input dataType of the SinkOfFlow changes
        else if (getElement().getElementType() == nodeType.fd_SinkOfFlow) {
            List<String[]> references = DiagramAPI.getNodesReferringToDiagram(DiagramAPI.getActiveDiagram().getName());
            for (String[] reference : references) {

                //update backend
                List<node> nodes = DataStorage.project.getDiagram(reference[0]).getActiveVersion().getNodes();
                if (nodes != null) {
                    for (node node : nodes) {
                        if (node.getId().equals(reference[1])) {
                            List<connector> connectors = node.getConnectors();
                            if (connectors != null) {
                                for (connector connector : connectors) {
                                    if (connector.getOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.right) {
                                        BackendAPI.connector().setType(node.getId(), connector.getId(), newDataType);
                                    }
                                }
                            }
                        }
                    }
                }

                //update frontend
                //if diagram is already opened in a Tab
                if (FlowMainContent.getTab(reference[0]) != null) {
                    List<Element> elements = MapCanvasSpaces.getCanvasSpaceOfDiagram(reference[0]).getDrawingPane().getGraph().getElementModel().getAllElements();
                    if (elements != null) {
                        for (Element element : elements) {
                            if (element.getElementId().equals(reference[1])) {
                                List<Connector> connectors = element.getConnectors();
                                for (Connector connector : connectors) {
                                    if (connector.getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.right) {
                                        connector.dataType = newDataType;
                                        connector.dataTypeProperty.setValue(newDataType);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * updates the output DataType of a Split,
     * when the input DataType changes
     *
     * @param newDataType new value for the output Connectors (String)
     */
    private void autoSetOutputForSplit(String newDataType) {

        List<Connector> con = element.getConnectors();
        if (con != null) {
            for (Connector connection : con) {
                if (connection.getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.right) {
                    connection.setDataType(newDataType);

                }
            }
        }


    }

    private void autoSetOutputForJoin(String newDataType) {

        /* create newDataType */
        List<String> dataType = new ArrayList<>();
        dataType.add("(");

        List<Connector> con = element.getConnectors();
        if (con != null) {
            for (Connector connection : con) {
                if (connection.getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.left) {
                    dataType.add(connection.getDataType().replace("(", "").replace(")", ", "));
                }
            }
        }
        dataType.add(dataType.size() - 1, dataType.get(dataType.size() - 1).replace(", ", ""));
        dataType.remove(dataType.size() - 1);
        dataType.add(")");

        String finalDataType = "";
        for (String str : dataType) {
            finalDataType += str;
        }

        //change the DataType of the output Connector
        if (con != null) {
            for (Connector connection : con) {
                if (connection.getConnectorOrientation() == de.hsesslingen.swb.diagram.enums.connectorOrientation.right) {
                    connection.setDataType(finalDataType);
                }
            }
        }

    }


}
