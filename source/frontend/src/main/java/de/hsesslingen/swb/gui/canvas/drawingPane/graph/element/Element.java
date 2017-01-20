package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeAttribut;
import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.border.Border;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.ConnectorModel;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

import static de.hsesslingen.swb.gui.FlowProperties.refreshProperties;

public abstract class Element extends Pane {

    protected String id;
    protected nodeType nodeType;

    protected double height;
    protected double width;

    protected double x;
    protected double y;
    protected nodeColor elementColor;
    protected String label;
    protected String text;
    protected String link;
    protected String image;
    protected String additional;
    protected nodeAttribut elementAttribut;
    protected List<Connector> Connectors;

    protected int numberLeftConnectors;
    protected int numberRightConnectors;
    protected int numberTopConnectors;
    protected int numberBottomConnectors;
    protected int numberCenterConnectors;
    protected int numberTopPointConnectors;
    protected int numberBottomPointConnectors;
    protected int numberLeftPointConnectors;
    protected int numberRightPointConnectors;

    protected ConnectorModel connectorModel;

    protected Node view;
    protected Border elementBorder;

    /**
     * Constructor
     *
     * @param id
     */
    public Element(String id) {
        this.id = id;

        /* Default values */
        height = 50;
        width = 50;
        x = 0;
        y = 0;
        elementColor = nodeColor.Default;
        label = "";
        text = "";
        link = "";
        image = "";
        additional = "";
        elementAttribut = nodeAttribut.NONE;

        /* Create a Connector List for this element */
        Connectors = new ArrayList<>();

        numberLeftConnectors = 0;
        numberRightConnectors = 0;
        numberTopConnectors = 0;
        numberBottomConnectors = 0;
        numberCenterConnectors = 0;
        numberLeftPointConnectors = 0;
        numberRightPointConnectors = 0;
        numberTopPointConnectors = 0;
        numberBottomPointConnectors = 0;

    }


    /**
     * returns the ID of the element
     *
     * @return ID
     */
    public String getElementId() {
        return id;
    }

    /**
     * set the ID of the element
     *
     * @param id
     */
    public void setElementId(String id) {
        this.id = id;

    }

    /**
     * returns the height of the element
     *
     * @return height
     */
    public double getH() {
        return height;
    }

    /**
     * set the height of the element
     *
     * @param height
     */
    public void setH(double height) {
        this.height = height;
        updateSizeBackend();
    }

    /**
     * returns the width of the element
     *
     * @return width
     */
    public double getW() {
        return width;
    }

    /**
     * set the height of the element
     *
     * @param width
     */
    public void setW(double width) {
        this.width = width;
        updateSizeBackend();
    }

    /**
     * returns the horizontal (x) position of the element
     *
     * @return x
     */
    public double getX() {
        return x;
    }

    /**
     * set the horizontal (x) position of the element
     *
     * @param x
     */
    public void setX(double x) {
        this.x = x;
        updatePositionBackend();
    }

    /**
     * get the vertical (y) position of the element
     *
     * @return
     */
    public double getY() {
        return y;
    }

    /**
     * set the vertical (y) position of the element
     *
     * @param y
     */
    public void setY(double y) {
        this.y = y;
        updatePositionBackend();
    }

    /**
     * returns the node color of the element
     *
     * @return nodeColor
     */
    public nodeColor getNodeColor() {
        return elementColor;
    }

    /**
     * set the node color of the element
     *
     * @param elementColor
     */
    public void setNodeColor(nodeColor elementColor) {
        this.elementColor = elementColor;
        updateColorBackend();
    }

    /**
     * returns the type of the element
     *
     * @return nodeType
     */
    public nodeType getElementType() {
        return nodeType;
    }

    /**
     * set the type of the element
     *
     * @param nodeType
     */
    public void setElementType(nodeType nodeType) {
        this.nodeType = nodeType;
    }

    /**
     * returns the label text of the element
     *
     * @return String
     */
    public String getLabel() {
        return label;
    }

    /**
     * set the label text of the element
     *
     * @param label
     */
    public void setLabel(String label) {
        this.label = label;
        updateLabelBackend();
    }

    /**
     * returns a additional Text of the element
     *
     * @return String
     */
    public String getText() {
        return text;
    }

    /**
     * set the additional text of the element
     *
     * @param text
     */
    public void setText(String text) {
        this.text = text;
        updateTextBackend();
    }

    /**
     * returns the link of the element
     *
     * @return String
     */
    public String getLink() {
        return link;
    }

    /**
     * set the link of the element
     *
     * @param link
     */
    public void setLink(String link) {
        this.link = link;
        updateLinkBackend();
    }

    /**
     * returns the image path of the element
     *
     * @return String
     */
    public String getImage() {
        return image;
    }

    /**
     * set the image path of the element
     *
     * @param image
     */
    public void setImage(String image) {
        this.image = image;
        updateImageBackend();
    }

    /**
     * returns the node Attribute of the element
     *
     * @return nodeAttribute
     */
    public nodeAttribut getAttribut() {
        return elementAttribut;
    }

    /**
     * set the node Attribute of the element
     *
     * @param nodeAttribut
     */
    public void setNodeAttribut(nodeAttribut nodeAttribut) {
        this.elementAttribut = nodeAttribut;
        updateAttributeBackend();
    }

    /**
     * returns additional information of the element
     *
     * @return String
     */
    public String getAdditional() {
        return additional;
    }

    /**
     * set additional information of the element
     *
     * @param additional
     */
    public void setAdditional(String additional) {
        this.additional = additional;
        updateAdditionalBackend();
    }

    /**
     * returns the number of left connectors
     *
     * @return
     */
    public int getNumberLeftConnectors() {
        return numberLeftConnectors;
    }

    /**
     * set the number of left connectors
     *
     * @param numberLeftConnectors
     */
    public void setNumberLeftConnectors(int numberLeftConnectors) {
        this.numberLeftConnectors = numberLeftConnectors;
    }

    /**
     * returns the number of right connectors
     *
     * @return
     */
    public int getNumberRightConnectors() {
        return numberRightConnectors;
    }

    /**
     * set the number of right connectors
     *
     * @param numberRightConnectors
     */
    public void setNumberRightConnectors(int numberRightConnectors) {
        this.numberRightConnectors = numberRightConnectors;
    }

    /**
     * returns the number of top connectors
     *
     * @return
     */
    public int getNumberTopConnectors() {
        return numberTopConnectors;
    }

    /**
     * set the number of top connectors
     *
     * @param numberTopConnectors
     */
    public void setNumberTopConnectors(int numberTopConnectors) {
        this.numberTopConnectors = numberTopConnectors;
    }

    /**
     * returns the number of bottom connectors
     *
     * @return
     */
    public int getNumberBottomConnectors() {
        return numberBottomConnectors;
    }

    /**
     * set the number of bottom connectors
     *
     * @param numberBottomConnectors
     */
    public void setNumberBottomConnectors(int numberBottomConnectors) {
        this.numberBottomConnectors = numberBottomConnectors;
    }

    /**
     * returns the number of center connectors
     *
     * @return
     */
    public int getNumberCenterConnectors() {
        return numberCenterConnectors;
    }

    /**
     * set the number of center connectors
     *
     * @param numberCenterConnectors
     */
    public void setNumberCenterConnectors(int numberCenterConnectors) {
        this.numberCenterConnectors = numberCenterConnectors;
    }

    public int getNumberTopPointConnectors() {
        return numberTopPointConnectors;
    }

    public void setNumberTopPointConnectors(int numberTopPointConnectors) {
        this.numberTopPointConnectors = numberTopPointConnectors;
    }

    public int getNumberBottomPointConnectors() {
        return numberBottomPointConnectors;
    }

    public void setNumberBottomPointConnectors(int numberBottomPointConnectors) {
        this.numberBottomPointConnectors = numberBottomPointConnectors;
    }

    public int getNumberLeftPointConnectors() {
        return numberLeftPointConnectors;
    }

    public void setNumberLeftPointConnectors(int numberLeftPointConnectors) {
        this.numberLeftPointConnectors = numberLeftPointConnectors;
    }

    public int getNumberRightPointConnectors() {
        return numberRightPointConnectors;
    }

    public void setNumberRightPointConnectors(int numberRightPointConnectors) {
        this.numberRightPointConnectors = numberRightPointConnectors;
    }

    /**
     * returns a list of connectors of the element
     *
     * @return List of connectors
     */
    public List<Connector> getConnectors() {
        return Connectors;
    }

    /**
     * set a list of connectors of the element
     *
     * @param connectors
     */
    public void setConnectors(List<Connector> connectors) {
        Connectors = connectors;
    }

    /**
     * returns the correction of x offset for moving the element
     *
     * @return double
     */
    public double correctionOfX() {
        return 0;
    }

    /**
     * returns the correction of y offset by moving the element
     *
     * @return
     */
    public double correctionOfY() {
        return 0;
    }

    /**
     * returns the border of the element
     *
     * @return Border
     */
    public Border getElementBorder() {
        return elementBorder;
    }

    /**
     * set the border of the element
     *
     * @param elementBorder
     */
    public void setElementBorder(Border elementBorder) {
        this.elementBorder = elementBorder;
    }


    /**
     * relocate the element on the screen
     * and sends the new coordinates to the backend
     *
     * @param x
     * @param y
     */
    public void relocate(double x, double y) {

        this.x = x;
        this.y = y;

        /* send the coordinate to the Backend */
        BackendAPI.node().setPosition(this.getElementId(), x, y);

        setLayoutX(x - getLayoutBounds().getMinX());
        setLayoutY(y - getLayoutBounds().getMinY());

    }

    /**
     * returns the view of the element
     *
     * @return Node
     */
    public Node getView() {
        return view;
    }

    /**
     * set the view of the element
     * add it as children of the element
     *
     * @param view
     */
    public void setView(Node view) {
        this.view = view;
        getChildren().add(view);
    }

    /**
     * converts the nodeColor type into a color type
     *
     * @param nodeColor
     * @return
     */
    public Color nodeColorToColor(nodeColor nodeColor) {

        switch (nodeColor) {
            case Green:
                return Color.rgb(165, 214, 167, 1.0);
            case Yellow:
                return Color.rgb(244, 255, 129, 1.0);
            case Orange:
                return Color.rgb(255, 183, 77, 1.0);
            case Blue:
                return Color.rgb(129, 212, 250, 1.0);
            case Red:
                return Color.rgb(239, 154, 154, 1.0);
            case Gray:
                return Color.rgb(207, 216, 220, 1.0);
            case Black:
                return Color.BLACK;
            default:
                return Color.WHITE;

        }
    }

    /**
     * converts the color type into a nodeColor type
     *
     * @param color
     * @return
     */
    public nodeColor colorToNodeColor(String color) {

        switch (color) {
            //GREEN
            case "0xa5d6a7ff":
                return nodeColor.Green;
            //YELLOW
            case "0xf4ff81ff":
                return nodeColor.Yellow;
            //ORANGE
            case "0xffb74dff":
                return nodeColor.Orange;
            //BLUE
            case "0x81d4faff":
                return nodeColor.Blue;
            //RED
            case "0xef9a9aff":
                return nodeColor.Red;
            //GRAY
            case "0xcfd8dcff":
                return nodeColor.Gray;
            //BLACK
            case "0x000000ff":
                return nodeColor.Black;
            default:
                return nodeColor.Default;

        }
    }

    /**
     * converts the node Attribute into a string to display it on Node
     *
     * @param attribut
     * @return
     */
    public String getStringOfAttribute(nodeAttribut attribut) {
        switch (attribut) {
            case EntryPoint:
                return "(E)";
            case Singleton:
                return "(S)";
            case Multiton:
                return "(M)";
            case Configurable:
                return "(C)";
            case NONE:
                return "";
        }
        return null;
    }

    /**
     * returns the horizontal center position of the element
     *
     * @return
     */
    public double getCenterX() {
        return 0;
    }

    /**
     * returns the vertical center position of the element
     *
     * @return
     */
    public double getCenterY() {
        return 0;
    }


    public ConnectorModel getConnectorModel() {
        return connectorModel;
    }

    public void setConnectorModel(ConnectorModel connectorModel) {
        this.connectorModel = connectorModel;
    }


    /**
     * Get a connector by its ID
     *
     * @param connectorID ID of the connector
     * @return Connector
     */
    public Connector getConnector(String connectorID) {

        /* Loop through all connectors */
        for (Connector tempConn : this.Connectors) {

            /* Return connector */
            if (tempConn.getConnectorId().equals(connectorID))
                return tempConn;

        }

        /* If connector not found return null */
        return null;

    }


    /**
     * update all properties in Backend
     */
    public void updateAllBackend() {
        updateColorBackend();
        updateLabelBackend();
        updatePositionBackend();
        updateSizeBackend();
        updateTextBackend();
        updateAttributeBackend();
        updateImageBackend();
        updateLinkBackend();
        updateAdditionalBackend();
    }

    /**
     * send the position of element to backend
     */
    public void updatePositionBackend() {
        BackendAPI.node().setPosition(id, x, y);
    }

    /**
     * send the size of element to backend
     */
    public void updateSizeBackend() {
        BackendAPI.node().setSize(id, height, width);
    }

    /**
     * send the color of element to backend
     */
    public void updateColorBackend() {
        BackendAPI.node().setColor(id, elementColor);
    }

    /**
     * send the label of element to backend
     */
    public void updateLabelBackend() {
        BackendAPI.node().setLabel(id, label);
    }

    /**
     * send the text of element to backend
     */
    public void updateTextBackend() {
        BackendAPI.node().setText(id, text);
    }

    /**
     * send the link of element to backend
     */
    public void updateLinkBackend() {
        BackendAPI.node().setLink(id, link);
    }

    /**
     * send the image of element to backend
     */
    public void updateImageBackend() {
        BackendAPI.node().setImage(id, image);
    }

    /**
     * send the attribute of element to backend
     */
    public void updateAttributeBackend() {
        BackendAPI.node().setAttribute(id, elementAttribut);
    }

    /**
     * send additional information of element to backend
     */
    public void updateAdditionalBackend() {
        BackendAPI.node().setAdditional(id, additional);
    }

    /**
     * add a left connector to element
     *
     * @param id
     */
    public void addLeftConnector(String id) {
        numberLeftConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.left);
        }
        connectorModel.createLeftConnector(id);
    }

    /**
     * add a right connector to element
     *
     * @param id
     */
    public void addRightConnector(String id) {
        numberRightConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.right);
        }
        connectorModel.createRightConnector(id);
    }

    /**
     * add a top connector to element
     *
     * @param id
     */
    public void addTopConnector(String id) {
        numberTopConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.top);
        }
        connectorModel.createTopConnector(id);
    }

    /**
     * add bottom connector to element
     *
     * @param id
     */
    public void addBottomConnector(String id) {
        numberBottomConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.bottom);
        }
        connectorModel.createBottomConnector(id);
    }

    /**
     * add center connector to element
     *
     * @param id
     */
    public void addCenterConnector(String id) {
        numberCenterConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.center);
        }
        connectorModel.createCenterConnector(id);
    }

    /**
     * add left point connector to element
     *
     * @param id
     */
    public void addLeftPointConnector(String id) {
        numberLeftPointConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.leftPoint);
        }
        connectorModel.createLeftPointConnector(id);
    }

    /**
     * add right point connector to element
     *
     * @param id
     */
    public void addRightPointConnector(String id) {
        numberRightPointConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.rightPoint);
        }
        connectorModel.createRightPointConnector(id);
    }

    /**
     * add top point connector to element
     *
     * @param id
     */
    public void addTopPointConnector(String id) {
        numberTopPointConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.topPoint);
        }
        connectorModel.createTopPointConnector(id);
    }

    /**
     * add bottom point connector to element
     *
     * @param id
     */
    public void addBottomPointConnector(String id) {
        numberBottomPointConnectors++;
        if (id == null) {
            id = BackendAPI.connector().addConnector(this.id, connectorOrientation.bottomPoint);
        }
        connectorModel.createBottomPointConnector(id);
    }

    /**
     * relocates all connectors with a uniformly distance
     */
    public void relocateAllConnectors() {
        connectorModel.relocateLeftConnector();
        connectorModel.relocateRightConnector();
        connectorModel.relocateTopConnector();
        connectorModel.relocateBottomConnector();
        connectorModel.relocateCenterConnector();
        connectorModel.relocateTopPointConnector();
        ;
        connectorModel.relocateBottomPointConnector();
        connectorModel.relocateLeftPointConnector();
        connectorModel.relocateRightPointConnector();
    }


    /**
     * removes a left connector
     *
     * @return
     */
    public boolean removeLeftConnector() {
        return connectorModel.removeLeftConnector();
    }

    /**
     * removes a right connector
     *
     * @return
     */
    public boolean removeRightConnector() {
        return connectorModel.removeRightConnector();
    }

    /**
     * removes a top connector
     *
     * @return
     */
    public boolean removeTopConnector() {
        return connectorModel.removeTopConnector();
    }

    /**
     * removes a bottom connector
     *
     * @return
     */
    public boolean removeBottomConnector() {
        return connectorModel.removeBottomConnector();
    }

    /**
     * adds the left or right connector of a Portal
     *
     * @return true on success and false of failure
     */
    public boolean addPortalConnector() {
        return connectorModel.addPortalConnector();
    }

    /**
     * removes the left or right connector of a Portal
     *
     * @return true on success and false of failure
     */
    public boolean removePortalConnector() {
        return connectorModel.removePortalConnector();
    }

    /**
     * calculate the radius of ellipse at the angle 45°
     *
     * @return
     */
    public double getAngleRadiusOfEllipse() {
    /* calculate the radius - our angle is always 45° */
        //return (width/2)/(Math.sqrt(1-(1-Math.pow(width/2,2)/Math.pow(height/2,2))*Math.pow(Math.cos(30),2)));
        double a = width / 2;
        double b = height / 2;

        double s = Math.sin(45), c = Math.cos(45);
        return (a * b) / Math.sqrt((a * a) * (s * s) + (b * b) * (c * c));
    }

    /**
     * returns the x value of ellipse at the angle 45°
     *
     * @return
     */
    public double getAngleXOfEllipse() {
        return getAngleRadiusOfEllipse() * Math.cos(45);
    }

    /**
     * returns the y value of ellipse at the angle 45°
     *
     * @return
     */
    public double getAngleYOfEllipse() {
        return getAngleRadiusOfEllipse() * Math.sin(45);
    }


    public void addLabelListener(TextField textField) {
        textField.textProperty().addListener((observable, oldValue, newValue) -> {
            label = newValue;
            updateLabelBackend();
            refreshProperties(this);
        });
    }


    public boolean activateTextField() {
        return false;
    }

    public boolean deactivateTextField() {
        return false;
    }

    public TextField getTextField() {
        return null;
    }
}

