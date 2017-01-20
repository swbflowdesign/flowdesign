package de.hsesslingen.swb;

import de.hsesslingen.swb.diagram.connector;
import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.diagramType;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.enums.wireType;
import de.hsesslingen.swb.diagram.node;
import de.hsesslingen.swb.project.project;

public class App {
    public static void main(String[] args) {

        /* Projekt erstellen */
        project proj = new project("Projekt 1");

        /* Diagram erstellen */
        diagram diag = new diagram("Test", diagramType.FlowDesign);

        /* Version hinzufuegen */
        int versNum = diag.addVersion();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ignored) {
        }     // Just to show the lastEdited gets updated automatically

        /* Nodes hinzufuegen */
        String nodeID1 = diag.getVersion(versNum).addNode(nodeType.fd_Unit);
        diag.getVersion(versNum).getNode(nodeID1).setText("Node 1");
        String nodeID2 = diag.getVersion(versNum).addNode(nodeType.dd_Image);
        diag.getVersion(versNum).getNode(nodeID2).setText("Node 2");

        /* Wires hinzufuegen */
        diag.getVersion(versNum).getNode(nodeID1).addConnector(connectorOrientation.bottom);
        diag.getVersion(versNum).getNode(nodeID1).addConnector(connectorOrientation.left);
        diag.getVersion(versNum).getNode(nodeID2).addConnector(connectorOrientation.right);
        diag.getVersion(versNum).getNode(nodeID2).addConnector(connectorOrientation.center);
        diag.getVersion(versNum).getNode(nodeID2).addConnector(connectorOrientation.left);

        /* Wire hinzufuegen */
        node node1 = diag.getVersion(versNum).getNode(nodeID1);
        connector conn1 = node1.getConnector(node1.addConnector(connectorOrientation.center));
        node node2 = diag.getVersion(versNum).getNode(nodeID2);
        connector conn2 = node2.getConnector(node2.addConnector(connectorOrientation.center));
        String connID = diag.getVersion(versNum).addWire(wireType.Blank, node1, conn1, node2, conn2);
        diag.getVersion(versNum).getWire(connID).setLabel("Label");

        /* Joint hinzufuegen */
        diag.getVersion(versNum).getWire(connID).addJoint(5, 9);

        /* Diagram zum Projekt hinzufügen */
        proj.addDiagram(diag);

    }
}
