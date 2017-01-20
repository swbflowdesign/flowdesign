package de.hsesslingen.swb.gui.canvas.drawingPane.graph.element;

import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.dd_elements.*;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.border.BorderModel;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.ConnectorModel;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.fd_elements.*;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.sud_elements.*;

import java.util.ArrayList;
import java.util.List;

public class ElementModel {

    private List<Element> allElements;
    private Graph graph;
    private ConnectorModel connectorModel;

    public ElementModel(Graph graph) {
        this.graph = graph;
        allElements = new ArrayList<>();
    }

    /**
     * Add an element to the list of all elements
     *
     * @param e
     */
    private void addElement(Element e) {
        allElements.add(e);
    }


    /**
     * Create a Element depending to the nodeType
     *
     * @param id
     * @param type
     * @param x
     * @param y
     * @param additional
     * @param defaultConnectors
     */
    public Element addElement(String id, nodeType type, double x, double y, String additional, boolean defaultConnectors) {

        switch (type) {

            /* ######################################################### */
            /* FlowDesign section */

            case fd_SinkOfFlow:
                /* Create a AdapterSquareElement */
                Fd_SinkOfFlowElement sinkOfFlow = new Fd_SinkOfFlowElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(sinkOfFlow);
                }

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(sinkOfFlow, x, y);

                return sinkOfFlow;

            case fd_SourceOfFlow:
                /* Create a AdapterSquareElement */
                Fd_SourceOfFlowElement sourceOfFlow = new Fd_SourceOfFlowElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(sourceOfFlow);
                }

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(sourceOfFlow, x, y);

                return sourceOfFlow;

            case fd_Iteration:
                /* Create a unit */
                Fd_IterationElement iteration = new Fd_IterationElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(iteration);
                }

                /* There is no need to center the element, because it is centered

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(iteration, x, y);

                return iteration;


            case fd_Portal:
                /* Create a portal */
                Fd_PortalElement portal = new Fd_PortalElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(portal);
                }

                /* Set the points to center the element */
                x = x - (portal.getCenterX());
                y = y - (portal.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(portal, x, y);

                return portal;

            case fd_Unit:
                /* Create a unit */
                Fd_UnitElement unit = new Fd_UnitElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(unit);
                }

                /* There is no need to center the element, because it is centered

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(unit, x, y);

                return unit;

            case fd_Split:
                /* Create a split */
                Fd_SplitElement split = new Fd_SplitElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(split);
                }

                /* Set the points to center the element */
                x = x - (split.getCenterX());
                y = y - (split.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(split, x, y);

                return split;

            case fd_Join:
                /* Create a join */
                Fd_JoinElement join = new Fd_JoinElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(join);
                }

                /* Set the points to center the element */
                x = x - (join.getCenterX());
                y = y - (join.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(join, x, y);

                return join;

            case fd_StateGet:
                /* Create a stateGet */
                Fd_StateGetElement stateGet = new Fd_StateGetElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(stateGet);
                }

                /* Set the points to center the element */
                x = x - (stateGet.getCenterX());
                y = y - (stateGet.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(stateGet, x, y);

                return stateGet;

            case fd_StateSet:
                /* Create a stateSet */
                Fd_StateSetElement stateSet = new Fd_StateSetElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(stateSet);
                }

                /* Set the points to center the element */
                x = x - (stateSet.getCenterX());
                y = y - (stateSet.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(stateSet, x, y);

                return stateSet;

            case fd_ResourceOval:
                /* Create a resourceOval */
                Fd_ResourceOvalElement resourceOval = new Fd_ResourceOvalElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(resourceOval);
                }

                /* There is no need to center the element, because it is centered

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(resourceOval, x, y);

                return resourceOval;

            case fd_UnitState:
                /* Create a unitState */
                Fd_UnitStateElement unitState = new Fd_UnitStateElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(unitState);
                }

                /* There is no need to center the element, because it is centered

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(unitState, x, y);

                return unitState;

            case fd_Resource:
                /* Create a unitState */
                Fd_ResourceElement resource = new Fd_ResourceElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(resource);
                }

                /* Set the points to center the element */
                x = x - (resource.getCenterX());
                y = y - (resource.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(resource, x, y);

                return resource;


            /* ######################################################### */
            /* Dialog diagram section */
            case dd_CurvedRedArrow:
                /* Create a CurvedRedArrow */
                Dd_CurvedRedArrowElement curvedRedArrow = new Dd_CurvedRedArrowElement(id);

                /* Set the points to center the element */
                x = x - (curvedRedArrow.getCenterX());
                y = y - (curvedRedArrow.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(curvedRedArrow, x, y);

                return curvedRedArrow;

            case dd_StraightRedArrow:
                /* Create a StraightRedArrow */
                Dd_StraightRedArrowElement straightRedArrow = new Dd_StraightRedArrowElement(id);

                /* Set the points to center the element */
                x = x - (straightRedArrow.getCenterX());
                y = y - (straightRedArrow.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(straightRedArrow, x, y);

                return straightRedArrow;

            case dd_Image:
                /* Create an Image */
                Dd_ImageElement image = new Dd_ImageElement(id, additional);

                /* Set the points to center the element */
                x = x - (image.getCenterX());
                y = y - (image.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(image, x, y);

                return image;

            case dd_Text:
                /* Create a Text */
                Dd_TextElement text = new Dd_TextElement(id);

                /* Set the points to center the element */
                x = x - (text.getCenterX());
                y = y - (text.getCenterY());

                completeElement(text, x, y);

                return text;

            case dd_Square:
                /* Create a square */
                Dd_SquareElement square = new Dd_SquareElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(square);
                }

                /* Set the points to center the element */
                x = x - (square.getCenterX());
                y = y - (square.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(square, x, y);

                return square;


            /* ######################################################### */
            /* system enviroment diagram section */

            case sud_AdapterSquare:
                /* Create a AdapterSquare */
                Sud_AdapterSquareElement adapterSquare = new Sud_AdapterSquareElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(adapterSquare);
                }

                /* Set the points to center the element */
                x = x - (adapterSquare.getCenterX());
                y = y - (adapterSquare.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(adapterSquare, x, y);

                return adapterSquare;

            case sud_AdapterTriangle:
                /* Create a AdapterTriangle */
                Sud_AdapterTriangleElement adapterTriangle = new Sud_AdapterTriangleElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(adapterTriangle);
                }

                /* Set the points to center the element */
                x = x - (adapterTriangle.getCenterX());
                y = y - (adapterTriangle.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(adapterTriangle, x, y);

                return adapterTriangle;

            case sud_Database:
                /* Create a Database */
                Sud_DatabaseElement database = new Sud_DatabaseElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(database);
                }

                /* Set the points to center the element */
                x = x - (database.getCenterX());
                y = y - (database.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(database, x, y);

                return database;

            case sud_HumanResource:
                /* Create a HumanResource */
                Sud_HumanResourceElement humanResource = new Sud_HumanResourceElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(humanResource);
                }

                /* Set the points to center the element */
                x = x - (humanResource.getCenterX());
                y = y - (humanResource.getCenterY());

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(humanResource, x, y);

                return humanResource;

            case sud_System:
                /* Create a AdapterSquareElement */
                Sud_SystemElement system = new Sud_SystemElement(id);

                if (defaultConnectors) {
                    setDefaultConnectors(system);
                }

                /* Add connector, relocate and mouseGestures and add ot to the drawingPane */
                completeElement(system, x, y);

                return system;

            case sud_Text:
                /* Create a Text */
                Dd_TextElement sText = new Dd_TextElement(id);

                /* Set the points to center the element */
                x = x - (sText.getCenterX());
                y = y - (sText.getCenterY());

                completeElement(sText, x, y);

                return sText;

            default:
                throw new UnsupportedOperationException("Unsupported type: " + type);
        }

    }

    /**
     * complete the Element with its components
     *
     * @param element
     * @param x
     * @param y
     */
    private void completeElement(Element element, double x, double y) {

        /* add the Element to the list of Elements */
        addElement(element);

        /* set the position of the Element */
        element.relocate(x, y);

        /* create Connector using a connector model */
        connectorModel = new ConnectorModel(element, graph);

        /* add the connector model to the element */
        element.setConnectorModel(connectorModel);

        /* create a Border for the Element */
        BorderModel borderModel = new BorderModel(element);

        /* add the Border to the element */
        element.setElementBorder(borderModel.getBorder());

        /* send some default information to the Backend */
        // ToDo: Kann vermutlich entfernt werden
        // element.updateAllBackend();

        /* add the Element to the drawingPane */
        graph.getDrawingPane().getChildren().addAll(element);

        /* add mouse gestures to the Element */
        graph.getDrawingPane().getController().getElementGestures().addGestures(element);


        /* Solve the issue that the node is bigger than the displayed node */
        if (element.getElementType() == nodeType.fd_Unit ||
                element.getElementType() == nodeType.fd_ResourceOval ||
                element.getElementType() == nodeType.fd_UnitState ||
                element.getElementType() == nodeType.fd_SinkOfFlow ||
                element.getElementType() == nodeType.fd_SourceOfFlow ||
                element.getElementType() == nodeType.fd_Iteration ||
                element.getElementType() == nodeType.sud_System
                ) {
            element.setMaxWidth(element.getW() / 2);        //Set the maximal width of Element
            element.setMaxHeight(element.getH() / 2);       //Set the maximal height of Element
        }

        /* Solve the issue that the node is bigger than the displayed node */
        if (element.getElementType() == nodeType.fd_StateGet ||
                element.getElementType() == nodeType.fd_StateSet
                ) {
            element.setMaxWidth(element.getW() - element.getW() / 20);        //Set the maximal width of Element
            element.setMaxHeight(element.getH() - element.getH() / 20);       //Set the maximal height of Element
        }

    }

    /**
     * returns a list of all Elements
     *
     * @return
     */
    public List<Element> getAllElements() {
        return allElements;
    }

    /**
     * defines the number of default connectors
     *
     * @param element
     */
    public void setDefaultConnectors(Element element) {

        /* add default connectors to element */
        switch (element.getElementType()) {

            case fd_SinkOfFlow:
                element.setNumberLeftConnectors(1);
                break;
            case fd_SourceOfFlow:
                element.setNumberRightConnectors(1);
                break;
            case fd_Join:
                element.setNumberLeftConnectors(2);
                element.setNumberRightConnectors(1);
                break;
            case fd_Split:
                element.setNumberLeftConnectors(1);
                element.setNumberRightConnectors(1);
                break;
            case fd_ResourceOval:
            case fd_Resource:
                element.setNumberTopConnectors(1);
                element.setNumberTopConnectors(1);
                break;
            case fd_Portal:
                element.setNumberLeftConnectors(1);
                element.setNumberRightConnectors(1);
                element.setNumberTopConnectors(1);
                break;
            case fd_StateGet:
            case fd_StateSet:
            case fd_Unit:
            case fd_UnitState:
            case fd_Iteration:
                element.setNumberLeftConnectors(1);
                element.setNumberRightConnectors(1);
                element.setNumberTopConnectors(1);
                element.setNumberBottomConnectors(1);
                break;
            case sud_AdapterSquare:
            case sud_AdapterTriangle:
            case sud_Database:
            case sud_HumanResource:
            case sud_System:
                element.setNumberLeftPointConnectors(1);
                element.setNumberRightPointConnectors(1);
                element.setNumberTopPointConnectors(1);
                element.setNumberBottomPointConnectors(1);
                //element.setNumberCenterConnectors(1);
                break;

        }
    }


    /**
     * Get an element by its ID
     *
     * @param elementID ID of the element
     * @return Connector
     */
    public Element getElement(String elementID) {

        /* Loop through all elements */
        for (Element tempElem : this.allElements) {

            /* Return connector */
            if (tempElem.getElementId().equals(elementID))
                return tempElem;

        }

        /* If element not found return null */
        return null;

    }


}

