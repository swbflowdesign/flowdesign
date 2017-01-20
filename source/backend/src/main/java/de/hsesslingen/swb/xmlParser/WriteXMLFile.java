package de.hsesslingen.swb.xmlParser;

import de.hsesslingen.swb.DataStorage;
import de.hsesslingen.swb.Storage.LastProject;
import de.hsesslingen.swb.diagram.*;
import org.jdom2.Attribute;
import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.output.Format;
import org.jdom2.output.XMLOutputter;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;


public class WriteXMLFile {

    public static void project() {

        try {

            /* Create document */
            Document doc = new Document();


            /* Create root element project */
            Element project = new Element("project");
            project.setAttribute(new Attribute("name", DataStorage.project.getName()));
            DateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss", Locale.GERMAN);
            project.setAttribute(new Attribute("created", format.format(DataStorage.project.getCreated())));
            project.setAttribute(new Attribute("edited", format.format(new Date())));
            doc.setRootElement(project);


            /* Add diagrams */
            Element diagrams = new Element("diagrams");
            for (diagram tempDiagram : DataStorage.project.getDiagrams()) {
                diagrams.addContent(new Element("diagram").setAttribute("name", tempDiagram.getName()));
            }
            doc.getRootElement().addContent(diagrams);


            /* File output */
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(DataStorage.projectDirectory + File.separator + DataStorage.project.getName() + ".flowproject"));

            System.out.println("Project '" + DataStorage.project.getName() + "' saved!");

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

    }

    public static void diagrams() {


        /* Create a file for each diagram */
        for (diagram tempDiagram : DataStorage.project.getDiagrams()) {

            if (!DataStorage.dirtyDiagrams.contains(tempDiagram.getName())) {
                continue;
            }

            try {

                /* Create document */
                Document doc = new Document();


                /* Create root element diagram */
                Element diagram = new Element("diagram");
                diagram.setAttribute(new Attribute("name", tempDiagram.getName()));
                diagram.setAttribute(new Attribute("type", tempDiagram.getType().toString()));
                diagram.setAttribute(new Attribute("parent", tempDiagram.getParent()));
                diagram.setAttribute(new Attribute("scrollPositionX", Double.toString(tempDiagram.getScrollPositionX())));
                diagram.setAttribute(new Attribute("scrollPositionY", Double.toString(tempDiagram.getScrollPositionY())));
                diagram.setAttribute(new Attribute("activeVersionNum", Integer.toString(tempDiagram.getActiveVersionNum())));
                doc.setRootElement(diagram);


                /* Add diagrams */
                Element versions = new Element("versions");
                for (version tempVersion : tempDiagram.getVersions()) {

                    /* Create element version */
                    Element version = new Element("version");
                    version.setAttribute(new Attribute("num", Integer.toString(tempVersion.getNum())));
                    DateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss", Locale.GERMAN);
                    version.setAttribute(new Attribute("created", format.format(tempVersion.getCreated())));
                    version.setAttribute(new Attribute("edited", format.format(tempVersion.getEdited())));

                    /* Add nodes */
                    Element nodes = new Element("nodes");
                    for (node tempNode : tempVersion.getNodes()) {

                        Element node = new Element("node");
                        node.setAttribute(new Attribute("id", tempNode.getId()));
                        node.setAttribute(new Attribute("type", tempNode.getType().toString()));
                        node.setAttribute(new Attribute("x", Double.toString(tempNode.getX())));
                        node.setAttribute(new Attribute("y", Double.toString(tempNode.getY())));
                        node.setAttribute(new Attribute("width", Double.toString(tempNode.getWidth())));
                        node.setAttribute(new Attribute("height", Double.toString(tempNode.getHeight())));

                        node.addContent(new Element("text").setText(tempNode.getText()));
                        node.addContent(new Element("label").setText(tempNode.getLabel()));
                        node.addContent(new Element("link").setText(tempNode.getLink()));
                        node.addContent(new Element("attribut").setText(tempNode.getAttribut().toString()));
                        node.addContent(new Element("image").setText(tempNode.getImage()));
                        node.addContent(new Element("color").setText(tempNode.getColor().toString()));
                        node.addContent(new Element("additional").setText(tempNode.getAdditional()));

                        /* Add connectors */
                        Element connectors = new Element("connectors");
                        for (connector tempConnector : tempNode.getConnectors()) {

                            Element connector = new Element("connector");
                            connector.setAttribute(new Attribute("id", tempConnector.getId()));
                            connector.setAttribute(new Attribute("orientation", tempConnector.getOrientation().toString()));
                            connector.setAttribute(new Attribute("type", tempConnector.getType()));

                            /* Add to connectors */
                            connectors.addContent(connector);

                        }
                        node.addContent(connectors);


                        /* Add to connectors */
                        nodes.addContent(node);

                    }
                    version.addContent(nodes);

                    /* Add wires */
                    Element wires = new Element("wires");
                    for (wire tempWire : tempVersion.getWires()) {

                        Element wire = new Element("wire");
                        wire.setAttribute(new Attribute("id", tempWire.getId()));
                        wire.setAttribute(new Attribute("type", tempWire.getType().toString()));
                        wire.setAttribute(new Attribute("srcNode", tempWire.getSourceNode().getId()));
                        wire.setAttribute(new Attribute("srcConn", tempWire.getSourceConnector().getId()));
                        wire.setAttribute(new Attribute("targetNode", tempWire.getTargetNode().getId()));
                        wire.setAttribute(new Attribute("targetConn", tempWire.getTargetConnector().getId()));

                        wire.addContent(new Element("label").setText(tempWire.getLabel()));

                        /* Add joints */
                        Element joints = new Element("joints");
                        for (joint tempJoint : tempWire.getJoints()) {

                            Element joint = new Element("joint");
                            joint.setAttribute(new Attribute("x", Double.toString(tempJoint.getX())));
                            joint.setAttribute(new Attribute("y", Double.toString(tempJoint.getY())));

                            /* Add to joints */
                            joints.addContent(joint);

                        }
                        wire.addContent(joints);


                        /* Add to wires */
                        wires.addContent(wire);

                    }
                    version.addContent(wires);


                    /* Add to versions */
                    versions.addContent(version);

                }
                doc.getRootElement().addContent(versions);


                /* File output */
                XMLOutputter xmlOutput = new XMLOutputter();
                xmlOutput.setFormat(Format.getPrettyFormat());
                xmlOutput.output(doc, new FileWriter(DataStorage.projectDirectory + File.separator + "diagrams" + File.separator + tempDiagram.getType().toString() + File.separator + tempDiagram.getName() + ".xml"));

                System.out.println("Project '" + DataStorage.project.getName() + "': Diagram '" + tempDiagram.getName() + "' saved!");

            } catch (IOException io) {
                System.out.println(io.getMessage());
            }

        }

    }

    public static void storage() {

        try {

            /* Create document */
            Document doc = new Document();


            /* Create root element FlowDesigner */
            Element root = new Element("FlowDesigner");
            doc.setRootElement(root);


            /* Add last projects */
            Element lastProjects = new Element("lastProjects");
            for (LastProject tempProject : DataStorage.lastProjects) {
                Element tempElement = new Element("project");
                tempElement.setAttribute("name", tempProject.getName());
                tempElement.setAttribute("path", tempProject.getFilePath());
                DateFormat format = new SimpleDateFormat("MM.dd.yyyy HH:mm:ss", Locale.GERMAN);
                tempElement.setAttribute("edited", format.format(tempProject.getEdited()));

                lastProjects.addContent(tempElement);
            }
            doc.getRootElement().addContent(lastProjects);


            /* Add custom data types */
            Element dataTypes = new Element("dataTypes");
            for (String tempType : DataStorage.dataTypes) {
                if (!DataStorage.defaultDataTypes.contains(tempType)) {
                    Element tempElement = new Element("type");
                    tempElement.setAttribute("name", tempType);

                    if (DataStorage.dataTypeDescriptions.containsKey(tempType)) {
                        tempElement.setAttribute("description", DataStorage.dataTypeDescriptions.get(tempType));
                    } else {
                        tempElement.setAttribute("description", "");
                    }

                    dataTypes.addContent(tempElement);
                }
            }
            doc.getRootElement().addContent(dataTypes);



            /* File output */
            XMLOutputter xmlOutput = new XMLOutputter();
            xmlOutput.setFormat(Format.getPrettyFormat());
            xmlOutput.output(doc, new FileWriter(DataStorage.storageFilePath));

            System.out.println("FlowDesigner storage updated!");

        } catch (IOException io) {
            System.out.println(io.getMessage());
        }

    }

}
