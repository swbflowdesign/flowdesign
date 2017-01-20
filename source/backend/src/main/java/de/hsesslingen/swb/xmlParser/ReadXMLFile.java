package de.hsesslingen.swb.xmlParser;

import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.Storage.LastProject;
import de.hsesslingen.swb.diagram.*;
import de.hsesslingen.swb.diagram.enums.*;
import de.hsesslingen.swb.project.project;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.JDOMException;
import org.jdom2.input.SAXBuilder;

import java.io.File;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;


public class ReadXMLFile {

    public static void project(String projectPath) {

        /* Read file */
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(projectPath);

        try {

            /* Get root element */
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();

            /* Create project */
            DataStorage.project = new project(rootNode.getAttributeValue("name"));

            /* Set dates */
            try {
                DateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss", Locale.GERMAN);

                Date created = format.parse(rootNode.getAttributeValue("created"));
                DataStorage.project.setCreated(created);

                Date edited = format.parse(rootNode.getAttributeValue("edited"));
                DataStorage.project.setEdited(edited);
            } catch (ParseException ex) {
                System.out.println(ex.getMessage());
            }


            /* Read in the diagrams */
            List diagrams = rootNode.getChild("diagrams").getChildren("diagram");
            for (int d = 0; d < diagrams.size(); d++) {

                Element diagramNode = (Element) diagrams.get(d);

                String tempFilePath = findDiagramFile(diagramNode.getAttributeValue("name"));
                if (tempFilePath != null) {
                    diagram tempDiag = diagram(tempFilePath);
                    DataStorage.project.addDiagram(tempDiag);
                } else {     // If file not found
                    System.out.println("Project '" + DataStorage.project.getName() + "': Diagram '" + diagramNode.getAttributeValue("name") + "' not found!");
                }

            }


            /* Console output */
            System.out.println("Project '" + DataStorage.project.getName() + "' imported!");

        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }
    }

    public static diagram diagram(String diagramFilePath) {

        /* Read file */
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(diagramFilePath);

        /* Check file */
        if (!xmlFile.exists() || !xmlFile.isFile())     // If file not found
            return null;

        /* Read file */
        try {

            /* Get root element */
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();

            /* Create diagram */
            diagram diag = new diagram(rootNode.getAttributeValue("name"), diagramType.valueOf(rootNode.getAttributeValue("type")));
            diag.setParent(rootNode.getAttributeValue("parent"));
            diag.setScrollPositionX(Double.parseDouble(rootNode.getAttributeValue("scrollPositionX")));
            diag.setScrollPositionY(Double.parseDouble(rootNode.getAttributeValue("scrollPositionY")));
            diag.setActiveVersionNum(Integer.parseInt(rootNode.getAttributeValue("activeVersionNum")));

            /* Set versions */
            List versions = rootNode.getChild("versions").getChildren("version");
            for (int i = 0; i < versions.size(); i++) {

                Element versionNode = (Element) versions.get(i);

                /* Create version */
                version vers = new version(Integer.parseInt(versionNode.getAttributeValue("num")));

                /* Set dates */
                try {
                    DateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss", Locale.GERMAN);

                    Date created = format.parse(versionNode.getAttributeValue("created"));
                    vers.setCreated(created);

                    Date edited = format.parse(versionNode.getAttributeValue("edited"));
                    vers.setEdited(edited);
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }


                /* Set nodes */
                List nodes = versionNode.getChild("nodes").getChildren("node");
                for (int j = 0; j < nodes.size(); j++) {

                    Element nodeNode = (Element) nodes.get(j);

                    /* Create Node */
                    node nod = new node(nodeType.valueOf(nodeNode.getAttributeValue("type")));

                    nod.setId(nodeNode.getAttributeValue("id"));
                    nod.setX(Double.parseDouble(nodeNode.getAttributeValue("x")));
                    nod.setY(Double.parseDouble(nodeNode.getAttributeValue("y")));
                    nod.setWidth(Double.parseDouble(nodeNode.getAttributeValue("width")));
                    nod.setHeight(Double.parseDouble(nodeNode.getAttributeValue("height")));

                    nod.setText(nodeNode.getChildText("text"));
                    nod.setLabel(nodeNode.getChildText("label"));
                    nod.setLink(nodeNode.getChildText("link"));
                    nod.setAttribut(nodeAttribut.valueOf(nodeNode.getChildText("attribut")));
                    nod.setImage(nodeNode.getChildText("image"));
                    nod.setColor(nodeColor.valueOf(nodeNode.getChildText("color")));
                    nod.setAdditional(nodeNode.getChildText("additional"));

                    /* Set connectors */
                    List connectors = nodeNode.getChild("connectors").getChildren("connector");
                    for (int c = 0; c < connectors.size(); c++) {

                        Element connectorNode = (Element) connectors.get(c);

                        /* Create Connector */
                        connector conn = new connector(connectorOrientation.valueOf(connectorNode.getAttributeValue("orientation")));

                        conn.setId(connectorNode.getAttributeValue("id"));
                        conn.setType(connectorNode.getAttributeValue("type"));


                        /* Add connector to the node */
                        nod.addConnector(conn);

                    }


                    /* Add node to the version */
                    vers.addNode(nod);

                }


                /* Set wires */
                List wires = versionNode.getChild("wires").getChildren("wire");
                for (int w = 0; w < wires.size(); w++) {

                    Element wireNode = (Element) wires.get(w);

                    /* Create wire */
                    wireType wirType = wireType.valueOf(wireNode.getAttributeValue("type"));
                    node wirSource = vers.getNode(wireNode.getAttributeValue("srcNode"));
                    connector conSource = vers.getNode(wirSource.getId()).getConnector(wireNode.getAttributeValue("srcConn"));
                    node wirTarget = vers.getNode(wireNode.getAttributeValue("targetNode"));
                    connector conTarget = vers.getNode(wirTarget.getId()).getConnector(wireNode.getAttributeValue("targetConn"));
                    wire wir = new wire(wirType, wirSource, conSource, wirTarget, conTarget);

                    wir.setId(wireNode.getAttributeValue("id"));
                    wir.setLabel(wireNode.getChildText("label"));

                    /* Set joints */
                    List joints = wireNode.getChild("joints").getChildren("joint");
                    for (int b = 0; b < joints.size(); b++) {

                        Element jointNode = (Element) joints.get(b);

                        /* Create and add joint */
                        wir.addJoint(Double.parseDouble(jointNode.getAttributeValue("x")), Double.parseDouble(jointNode.getAttributeValue("y")));

                    }


                    /* Add wire to the version */
                    vers.addWire(wir);

                }


                /* Add version to the diagram */
                diag.addVersion(vers);

            }


            /* Console output */
            System.out.println("Project '" + DataStorage.project.getName() + "': Diagram '" + diag.getName() + "' imported!");


            return diag;

        } catch (IOException io) {
            //System.out.println(io.getMessage());
            return null;
        } catch (JDOMException jdomex) {
            //System.out.println(jdomex.getMessage());
            return null;
        }

    }

    private static String findDiagramFile(String diagramName) {

        /* Check SystemUmweltDiagramm */
        File xmlFile = new File(DataStorage.projectDirectory + File.separator + "diagrams" + File.separator + "SystemUmweltDiagram" + File.separator + diagramName + ".xml");
        if (xmlFile.exists() && xmlFile.isFile()) {
            return xmlFile.getPath();
        }

        /* Check SystemUmweltDiagramm */
        xmlFile = new File(DataStorage.projectDirectory + File.separator + "diagrams" + File.separator + "DialogDiagram" + File.separator + diagramName + ".xml");
        if (xmlFile.exists() && xmlFile.isFile()) {
            return xmlFile.getPath();
        }

        /* Check SystemUmweltDiagramm */
        xmlFile = new File(DataStorage.projectDirectory + File.separator + "diagrams" + File.separator + "FlowDesign" + File.separator + diagramName + ".xml");
        if (xmlFile.exists() && xmlFile.isFile()) {
            return xmlFile.getPath();
        }

        return null;
    }

    public static void storage(String storageFilePath) {

        /* Read file */
        SAXBuilder builder = new SAXBuilder();
        File xmlFile = new File(storageFilePath);

        try {

            /* Get root element */
            Document document = (Document) builder.build(xmlFile);
            Element rootNode = document.getRootElement();


            /* Read in the last projects */
            List projects = rootNode.getChild("lastProjects").getChildren("project");
            for (int d = 0; d < projects.size(); d++) {

                Element projectNode = (Element) projects.get(d);

                String tempName = projectNode.getAttributeValue("name");
                String tempPath = projectNode.getAttributeValue("path");
                Date tempEdited = null;
                try {
                    DateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss", Locale.GERMAN);
                    tempEdited = format.parse(projectNode.getAttributeValue("edited"));
                } catch (ParseException ex) {
                    System.out.println(ex.getMessage());
                }

                File tempProjectFile = new File(tempPath);
                if (tempProjectFile.isFile()) {
                    DataStorage.lastProjects.add(new LastProject(tempName, tempPath, tempEdited));
                } else {
                    System.out.println("Project file '" + tempPath + "' not found!");
                }

            }


            /* Read in the custom data types */
            List dataTypes = rootNode.getChild("dataTypes").getChildren("type");
            for (int d = 0; d < dataTypes.size(); d++) {

                Element typeNode = (Element) dataTypes.get(d);

                String tempName = typeNode.getAttributeValue("name");
                if (tempName != null && !tempName.equals("")) {
                    DataStorage.dataTypes.add(tempName);

                    String tempDescription = typeNode.getAttributeValue("description");
                    if (tempDescription != null && !tempDescription.equals("")) {
                        DataStorage.dataTypeDescriptions.put(tempName, tempDescription);
                    }
                }

            }


            /* Console output */
            System.out.println("FlowDesigner storage imported");

        } catch (IOException io) {
            System.out.println(io.getMessage());
        } catch (JDOMException jdomex) {
            System.out.println(jdomex.getMessage());
        }

    }

}
