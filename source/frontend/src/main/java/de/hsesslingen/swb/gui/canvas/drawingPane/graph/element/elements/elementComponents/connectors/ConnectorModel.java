package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors;

import de.hsesslingen.swb.BackendAPI;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.gui.FlowProperties;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.connectorPoints.*;

import java.util.List;

public class ConnectorModel {

    private final double radius = 12;
    private Element element;
    private Graph graph;

    public ConnectorModel(Element element, Graph graph) {
        this.element = element;
        this.graph = graph;
        createAllConnectors();
    }

    /**
     * add to each elements the default connectors or nothing for elements of storage
     */
    public void createAllConnectors() {
    /* default or nothing*/

        /* create left connectors */
        for (int i = 0; i < element.getNumberLeftConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.left);

            /* create a new Connector */
            createLeftConnector(id);

        }

        /* create right connectors */
        for (int i = 0; i < element.getNumberRightConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.right);

            /* create a new Connector */
            createRightConnector(id);
        }

        /* create top connectors */
        for (int i = 0; i < element.getNumberTopConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.top);

            /* create a new Connector */
            createTopConnector(id);
        }

        /* create bottom connectors */
        for (int i = 0; i < element.getNumberBottomConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.bottom);

            /* create a new Connector */
            createBottomConnector(id);
        }

        /* create center connectors */
        for (int i = 0; i < element.getNumberCenterConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.center);
            /* create a new Connector */
            createCenterConnector(id);

        }

        /* create topPoint connectors */
        for (int i = 0; i < element.getNumberTopPointConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.topPoint);
            /* create a new Connector */
            createTopPointConnector(id);

        }

        /* create bottomPoint connectors */
        for (int i = 0; i < element.getNumberBottomPointConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.bottomPoint);
            /* create a new Connector */
            createBottomPointConnector(id);

        }

        /* create leftPoint connectors */
        for (int i = 0; i < element.getNumberLeftPointConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.leftPoint);
            /* create a new Connector */
            createLeftPointConnector(id);

        }

        /* create rightPoint connectors */
        for (int i = 0; i < element.getNumberRightPointConnectors(); i++) {
            /* get ID from backend */
            String id = BackendAPI.connector().addConnector(element.getElementId(), connectorOrientation.rightPoint);
            /* create a new Connector */
            createRightPointConnector(id);

        }

    }

    /**
     * Add the connector to the list of connectors of the element
     *
     * @param connector
     */
    public void addConnector(Connector connector) {
        element.getConnectors().add(connector);
    }


    /**
     * relocate the connectors on the element
     *
     * @param connector
     * @param x
     * @param y
     * @param width
     * @param height
     * @param distance
     */
    public void relocateConnector(Connector connector, double x, double y, double width, double height, double distance) {
        switch (connector.getConnectorOrientation()) {

            case left:
                connector.relocate(x - width / 2 - radius / 2, y - height / 2 + distance);
                connector.setVisible(false);
                break;
            case leftPoint:
                connector.relocate(x - width / 2, y - height / 2 + distance);
                connector.setVisible(true);
                break;
            case right:
                connector.relocate(x + width / 2 - radius / 2, y - height / 2 + distance);
                connector.setVisible(true);
                break;
            case rightPoint:
                connector.relocate(x + width / 2, y - height / 2 + distance);
                connector.setVisible(true);
                break;
            case top:
                connector.relocate(x - width / 2 + distance, y - height / 2 - radius / 2);
                connector.setVisible(false);
                break;
            case topPoint:
                connector.relocate(x - width / 2 + distance, y - height / 2);
                connector.setVisible(true);
                break;
            case bottom:
                connector.relocate(x - width / 2 + distance, y + height / 2 - radius / 2);
                connector.setVisible(true);
                break;
            case bottomPoint:
                connector.relocate(x - width / 2 + distance, y + height / 2);
                connector.setVisible(true);
                break;
            case center:
                connector.relocate(x, y);
                connector.prefWidth(connector.getRadius() / 2);
                connector.prefHeight(connector.getRadius() / 2);
                connector.setVisible(true);
                break;
        }
    }

    /**
     * create a left connector
     *
     * @param id
     */
    public void createLeftConnector(String id) {

        /* create a connector */
        LeftConnector leftConnector = new LeftConnector(id, element);

        /* add Connector to Connector List */
        addConnector(leftConnector);

        /* relocate all left connectors */
        relocateLeftConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(leftConnector);

        /* Add the connector to the Element */
        element.getChildren().add(leftConnector);
    }

    /**
     * create a right connector
     *
     * @param id
     */
    public void createRightConnector(String id) {

        /* create a connector */
        RightConnector rightConnector = new RightConnector(id, element);

        /* add Connector to Connector List */
        addConnector(rightConnector);

        /* relocate all left connectors */
        relocateRightConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(rightConnector);

        /* Add the connector to the Element */
        element.getChildren().add(rightConnector);

    }

    /**
     * create a top connector
     *
     * @param id
     */
    public void createTopConnector(String id) {

        /* create a connector */
        TopConnector topConnector = new TopConnector(id, element);

        /* add Connector to Connector List */
        addConnector(topConnector);

        /* relocate all Top connectors */
        relocateTopConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(topConnector);

        /* Add the connector to the Element */
        element.getChildren().add(topConnector);

    }

    /**
     * create a bottom connector
     *
     * @param id
     */
    public void createBottomConnector(String id) {

        /* create a connector */
        BottomConnector bottomConnector = new BottomConnector(id, element);

        /* add Connector to Connector List */
        addConnector(bottomConnector);

        /* relocate all Bottom connectors */
        relocateBottomConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(bottomConnector);

        /* Add the connector to the Element */
        element.getChildren().add(bottomConnector);

    }

    /**
     * create a center connector
     *
     * @param id
     */
    public void createCenterConnector(String id) {

        /* create a connector */
        CenterConnector centerConnector = new CenterConnector(id, element);

        /* add Connector to Connector List */
        addConnector(centerConnector);

        /* relocate all Center connectors */
        relocateCenterConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(centerConnector);

        /* Add the connector to the Element */
        element.getChildren().add(centerConnector);

    }

    /**
     * create a top point connector
     *
     * @param id
     */
    public void createTopPointConnector(String id) {

        /* create a connector */
        TopPointConnector topPointConnector = new TopPointConnector(id, element);

        /* add Connector to Connector List */
        addConnector(topPointConnector);

        /* relocate all Center connectors */
        relocateTopPointConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(topPointConnector);

        /* Add the connector to the Element */
        element.getChildren().add(topPointConnector);

    }

    /**
     * create a bottom point connector
     *
     * @param id
     */
    public void createBottomPointConnector(String id) {

        /* create a connector */
        BottomPointConnector bottomPointConnector = new BottomPointConnector(id, element);

        /* add Connector to Connector List */
        addConnector(bottomPointConnector);

        /* relocate all Center connectors */
        relocateBottomPointConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(bottomPointConnector);

        /* Add the connector to the Element */
        element.getChildren().add(bottomPointConnector);

    }

    /**
     * create a left point connector
     *
     * @param id
     */
    public void createLeftPointConnector(String id) {

        /* create a connector */
        LeftPointConnector leftPointConnector = new LeftPointConnector(id, element);

        /* add Connector to Connector List */
        addConnector(leftPointConnector);

        /* relocate all Center connectors */
        relocateLeftPointConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(leftPointConnector);

        /* Add the connector to the Element */
        element.getChildren().add(leftPointConnector);

    }

    /**
     * create a right point connector
     *
     * @param id
     */
    public void createRightPointConnector(String id) {

        /* create a connector */
        RightPointConnector rightPointConnector = new RightPointConnector(id, element);

        /* add Connector to Connector List */
        addConnector(rightPointConnector);

        /* relocate all Center connectors */
        relocateRightPointConnector();

        /* Add mouse Gestures to this connector */
        graph.getDrawingPane().getController().getConnectorGestures().addGestures(rightPointConnector);

        /* Add the connector to the Element */
        element.getChildren().add(rightPointConnector);

    }

    /**
     * calculate the distance between the connectors depending on the number of connectors
     *
     * @param number
     * @param doubleTemp
     * @return
     */
    public double getDistance(double number, double doubleTemp) {

        double distance;

        /* Calculate the distance between the connector points */
        if (number == 1) {
            distance = doubleTemp / 2 - 12 / 2;
        } else {
            distance = (doubleTemp - 12) / (number - 1);
        }

        return distance;

    }

    public double getPointsDistance(double number, double doubleTemp) {

        double distance;

        /* Calculate the distance between the connector points */
        if (number == 1) {
            distance = doubleTemp / 2;
        } else {
            distance = (doubleTemp - 8) / (number - 1);
        }

        return distance;

    }

    /**
     * relocate the left connectors with a uniformly distance
     */
    public void relocateLeftConnector() {
        int number = element.getNumberLeftConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getDistance(number, element.getH());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.left) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }

    }

    /**
     * relocate the right connectors with a uniformly distance
     */
    public void relocateRightConnector() {
        int number = element.getNumberRightConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getDistance(number, element.getH());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.right) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }

    }

    /**
     * relocate the top connectors with a uniformly distance
     */
    public void relocateTopConnector() {
        int number = element.getNumberTopConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getDistance(number, element.getW());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.top) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }
    }

    /**
     * relocate the bottom connectors with a uniformly distance
     */
    public void relocateBottomConnector() {
        int number = element.getNumberBottomConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getDistance(number, element.getW());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.bottom) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }

    }

    /**
     * relocate the center connectors to the center of the element
     */
    public void relocateCenterConnector() {
        int number = element.getNumberCenterConnectors();
        if (number < 1)
            return;

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.center) {
                relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), 0);
                i++;
            }
        }

    }

    /**
     * relocate the center connectors to the center of the element
     */
    public void relocateTopPointConnector() {
        int number = element.getNumberTopPointConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getPointsDistance(number, element.getW());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.topPoint) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }

    }

    /**
     * relocate the center connectors to the center of the element
     */
    public void relocateBottomPointConnector() {
        int number = element.getNumberBottomPointConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getPointsDistance(number, element.getW());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.bottomPoint) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }

    }

    /**
     * relocate the center connectors to the center of the element
     */
    public void relocateLeftPointConnector() {
        int number = element.getNumberLeftPointConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getPointsDistance(number, element.getH());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.leftPoint) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }

    }

    /**
     * relocate the center connectors to the center of the element
     */
    public void relocateRightPointConnector() {
        int number = element.getNumberRightPointConnectors();
        if (number < 1)
            return;

        /* get distance between connectors */
        double distance = getPointsDistance(number, element.getH());

        int i = 0;
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation.rightPoint) {
                if (number == 1) {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance);
                } else {
                    relocateConnector(connector, element.getCenterX(), element.getCenterY(), element.getW(), element.getH(), distance * i);
                    i++;
                }
            }
        }

    }

    /**
     * reduce the number of left connectors by one
     *
     * @return
     */
    public boolean removeLeftConnector() {

        if (element.getNumberLeftConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.left)) {
            element.setNumberLeftConnectors(element.getNumberLeftConnectors() - 1);
            relocateLeftConnector();
            return true;
        }

        return false;
    }

    /**
     * reduce the number of right connectors by one
     *
     * @return
     */
    public boolean removeRightConnector() {

        if (element.getNumberRightConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.right)) {
            element.setNumberRightConnectors(element.getNumberRightConnectors() - 1);
            relocateRightConnector();
            return true;
        }

        return false;
    }

    /**
     * reduce the number of bottom connectors by one
     *
     * @return
     */
    public boolean removeBottomConnector() {

        if (element.getNumberBottomConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.bottom)) {
            element.setNumberBottomConnectors(element.getNumberBottomConnectors() - 1);
            relocateBottomConnector();
            return true;
        }

        return false;
    }

    /**
     * reduce the number of top connectors by one
     *
     * @return
     */
    public boolean removeTopConnector() {

        if (element.getNumberTopConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.top)) {
            element.setNumberTopConnectors(element.getNumberTopConnectors() - 1);
            relocateTopConnector();
            return true;
        }

        return false;
    }

    /**
     * reduce the number of top points connectors by one
     *
     * @return
     */
    public boolean removeTopPointConnector() {

        if (element.getNumberTopPointConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.topPoint)) {
            element.setNumberTopPointConnectors(element.getNumberTopPointConnectors() - 1);
            relocateTopPointConnector();
            return true;
        }

        return false;
    }

    /**
     * reduce the number of bottom points connectors by one
     *
     * @return
     */
    public boolean removeBottomPointConnector() {

        if (element.getNumberBottomPointConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.bottomPoint)) {
            element.setNumberBottomPointConnectors(element.getNumberBottomPointConnectors() - 1);
            relocateBottomPointConnector();
            return true;
        }

        return false;
    }

    /**
     * reduce the number of left point connectors by one
     *
     * @return
     */
    public boolean removeLeftPointConnector() {

        if (element.getNumberLeftPointConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.leftPoint)) {
            element.setNumberLeftPointConnectors(element.getNumberLeftPointConnectors() - 1);
            relocateLeftPointConnector();
            return true;
        }

        return false;
    }

    /**
     * reduce the number of right points connectors by one
     *
     * @return
     */
    public boolean removeRightPointConnector() {

        if (element.getNumberRightPointConnectors() == 1) {
            return false;
        }

        if (checkRemove(connectorOrientation.rightPoint)) {
            element.setNumberRightPointConnectors(element.getNumberRightPointConnectors() - 1);
            relocateRightPointConnector();
            return true;
        }

        return false;
    }

    /**
     * check if it allowed to remove a connector
     *
     * @param connectorOrientation
     * @return
     */
    private boolean checkRemove(connectorOrientation connectorOrientation) {
        for (Connector connector : element.getConnectors()) {
            if (connector.getConnectorOrientation() == connectorOrientation) {
                if (!connector.isConnected()) {
                    BackendAPI.connector().removeConnector(connector.getElement().getElementId(), connector.getConnectorId());
                    element.getConnectors().remove(connector);
                    connector.getChildren().clear();
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * remove the left or right connector of the Portal
     *
     * @return true on success and false on failure
     */
    public boolean removePortalConnector() {

        List<Connector> con = element.getConnectors();
        if (con != null) {
            for (Connector connection : con) {
                //remove right connector
                if (connection.getConnectorOrientation() == connectorOrientation.left && connection.isConnected()) {
                    if (checkRemove(connectorOrientation.right)) {
                        element.setNumberRightConnectors(element.getNumberRightConnectors() - 1);
                        relocateRightConnector();
                        return true;
                    }
                }
                //remove left connector
                else if (connection.getConnectorOrientation() == connectorOrientation.right && connection.isConnected()) {
                    if (checkRemove(connectorOrientation.left)) {
                        element.setNumberLeftConnectors(element.getNumberLeftConnectors() - 1);
                        relocateLeftConnector();
                        return true;
                    }
                }
            }
        }

        return false;
    }

    /**
     * add the left or right connector of the Portal
     *
     * @return true on success and false on failure
     */
    public boolean addPortalConnector() {
        List<Connector> con = element.getConnectors();
        if (con != null) {
            for (Connector connection : con) {
                //add right connector
                if (connection.getConnectorOrientation() == connectorOrientation.left) {
                    element.setNumberRightConnectors(element.getNumberRightConnectors() + 1);
                    element.addRightConnector(null);
                    FlowProperties.refreshProperties(element);
                    return true;
                }
                //add left connector
                else if (connection.getConnectorOrientation() == connectorOrientation.right) {
                    element.setNumberLeftConnectors(element.getNumberLeftConnectors() + 1);
                    element.addLeftConnector(null);
                    FlowProperties.refreshProperties(element);
                    return true;
                }
            }
        }

        return false;
    }


}
