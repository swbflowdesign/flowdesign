package de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire;


import de.hsesslingen.swb.diagram.enums.wireType;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.Graph;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.Element;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.element.elements.elementComponents.connectors.Connector;
import de.hsesslingen.swb.gui.canvas.drawingPane.graph.wire.wires.wireComponents.Arrow;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;

public abstract class Wire extends Group {

    protected String id;
    protected Element sourceElement;
    protected Element targetElement;
    protected Connector sourceConnector;
    protected Connector targetConnector;
    protected wireType wireType;
    protected Node view;
    protected Graph graph;
    //protected String label;
    //protected Group group;
    protected WireDataType dataType;


    public Wire(Graph graph, String id, Connector sourceConnector, Element sourceElement, Connector targetConnector, Element targetElement) {
        this.graph = graph;
        this.id = id;
        this.sourceConnector = sourceConnector;
        this.targetConnector = targetConnector;
        this.sourceElement = sourceElement;
        this.targetElement = targetElement;
    }


    public Element getSourceElement() {
        return sourceElement;
    }

    public void setSourceElement(Element sourceElement) {
        this.sourceElement = sourceElement;
    }

    public Element getTargetElement() {
        return targetElement;
    }

    public void setTargetElement(Element targetElement) {
        this.targetElement = targetElement;
    }

    public Connector getSourceConnector() {
        return sourceConnector;
    }

    public void setSourceConnector(Connector sourceConnector) {
        this.sourceConnector = sourceConnector;
    }

    public Connector getTargetConnector() {
        return targetConnector;
    }

    public void setTargetConnector(Connector targetConnector) {
        this.targetConnector = targetConnector;
    }

    /*
        public void setLabel(String text) {

            label = null;
        }

        public String getLabel() {
            return label;
        }
    */
    public wireType getWireType() {
        return wireType;
    }

    public void setWireType(wireType type) {
        this.wireType = type;
    }

    public Node getView() {
        return view;
    }

    public String getWireId() {
        return id;
    }

    public void setWireId(String id) {
        this.id = id;
    }

    public Line getLine() {
        return null;
    }

    public Polygon getPolygon() {
        return null;
    }

    public Circle getArrowTip() {
        return null;
    }

    public Circle getArrowEnd() {
        return null;
    }

    public Arrow getArrow() {
        return null;
    }

    public Circle getDot() {
        return null;
    }

    /*
        public Group getGroup() {
            return group;
        }

        public void setGroup(Group group) {
            this.group = group;
        }
    */
    public WireDataType getDataType() {
        return null;
    }


}
