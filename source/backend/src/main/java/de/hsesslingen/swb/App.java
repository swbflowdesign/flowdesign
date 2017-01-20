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
        DataStorage.project = new project("Projekt 1");

        /* Diagram erstellen */
        diagram diag = new diagram("Diagramm 1", diagramType.FlowDesign);

        /* Version hinzufuegen */
        int versNum = diag.addVersion();

        /* Nodes hinzufuegen */
        String nodeID1 = diag.getVersion(versNum).addNode(nodeType.fd_Unit);
        diag.getVersion(versNum).getNode(nodeID1).setText("Node 1");
        String nodeID2 = diag.getVersion(versNum).addNode(nodeType.dd_Image);
        diag.getVersion(versNum).getNode(nodeID2).setText("Node 2");

        /* Connectors hinzufuegen */
        String connID1 = diag.getVersion(versNum).getNode(nodeID1).addConnector(connectorOrientation.bottom);
        String connID2 = diag.getVersion(versNum).getNode(nodeID1).addConnector(connectorOrientation.left);
        String connID3 = diag.getVersion(versNum).getNode(nodeID2).addConnector(connectorOrientation.right);
        String connID4 = diag.getVersion(versNum).getNode(nodeID2).addConnector(connectorOrientation.center);
        String connID5 = diag.getVersion(versNum).getNode(nodeID2).addConnector(connectorOrientation.left);

        /* Wires hinzufuegen */
        node node1 = diag.getVersion(versNum).getNode(nodeID1);
        connector conn1 = diag.getVersion(versNum).getNode(nodeID1).getConnector(connID1);
        node node2 = diag.getVersion(versNum).getNode(nodeID2);
        connector conn2 = diag.getVersion(versNum).getNode(nodeID2).getConnector(connID4);
        String wirID = diag.getVersion(versNum).addWire(wireType.Blank, node1, conn1, node2, conn2);
        diag.getVersion(versNum).getWire(wirID).setLabel("Label");
        String wir2ID = diag.getVersion(versNum).addWire(wireType.Arrow, node2, conn2, node1, conn1);
        diag.getVersion(versNum).getWire(wir2ID).setLabel("Label2");

        /* Joint hinzufuegen */
        diag.getVersion(versNum).getWire(wirID).addJoint(5, 9);
        diag.getVersion(versNum).getWire(wirID).addJoint(3, 6);

        /* Diagram zum Projekt hinzufügen */
        DataStorage.project.addDiagram(diag);



        /* Diagram2 erstellen */
        DataStorage.project.addDiagram("Diagramm 2", diagramType.SystemUmweltDiagram);

        /* Diagram3 erstellen */
        DataStorage.project.addDiagram("Diagramm 3", diagramType.FlowDesign);
        DataStorage.project.getDiagram("Diagramm 3").setParent("Diagramm 1");

        /* Diagram4 erstellen */
        DataStorage.project.addDiagram("Diagramm 4", diagramType.FlowDesign);
        DataStorage.project.getDiagram("Diagramm 4").setParent("Diagramm 1");

        /* Diagram5 erstellen */
        DataStorage.project.addDiagram("Diagramm 5", diagramType.FlowDesign);
        DataStorage.project.getDiagram("Diagramm 5").setParent("Diagramm 3");

        /* Diagram7 erstellen */
        DataStorage.project.addDiagram("Diagramm 7", diagramType.FlowDesign);
        DataStorage.project.getDiagram("Diagramm 7").setParent("Diagramm 5");

        /* Diagram6 erstellen */
        DataStorage.project.addDiagram("Diagramm 6", diagramType.FlowDesign);
        DataStorage.project.getDiagram("Diagramm 6").setParent("Diagramm 3");





        /* XML Export / Import */
        //dataStorage.projectDirectory = "C:\\Users\\dev\\Desktop\\xmlFiles";

        //writeXMLFile.project();
        //writeXMLFile.diagrams();

        //dataStorage.project = null;
        //readXMLFile.project("C:\\Users\\dev\\Desktop\\xmlFiles\\Projekt 1.flowproject");



        /* Delete diagram */
        //backendAPI.clearDataStorage();
        //backendAPI.project().openProject("C:\\Users\\dev\\Desktop\\xmlFiles\\Projekt 1.flowproject");

        //System.out.println(dataStorage.project.getDiagram("Diagramm 5").getName());
        //backendAPI.diagram().deleteDiagram("Diagramm 5");
        //System.out.println(dataStorage.project.getDiagram("Diagramm 5"));



        /* Rename diagram */
        //backendAPI.clearDataStorage();
        //backendAPI.project().openProject("C:\\Users\\dev\\Desktop\\xmlFiles\\Projekt 1.flowproject");

        //System.out.println(dataStorage.project.getDiagram("Diagramm 8").getName());
        //backendAPI.diagram().renameDiagram("Diagramm 8", "Diagramm 3");
        //backendAPI.project().saveProject();
        //System.out.println(dataStorage.project.getDiagram("Diagramm 8"));
        //System.out.println(dataStorage.project.getDiagram("Diagramm 3").getName());



        /* Import diagram */
        //backendAPI.clearDataStorage();
        //backendAPI.project().openProject("C:\\Users\\dev\\Desktop\\xmlFiles\\Projekt 1.flowproject");

        //diagram tempDiagram = backendAPI.project().importDiagram("C:\\Users\\dev\\Desktop\\xmlFiles\\diagrams\\FlowDesign\\Diagramm 3.xml");

        //backendAPI.project().saveProject();

        /* Get ProjectTree */
        //List<ProjectTreeItem> projectTree = backendAPI.project().getProjectTree();

    }
}
