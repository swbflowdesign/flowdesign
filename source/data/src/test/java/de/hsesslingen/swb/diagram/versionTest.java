package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.enums.wireType;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class versionTest {
    @Test
    public void getNum() throws Exception {
        version temp = new version(1);
        assertEquals("Num was set correctly", 1, temp.getNum(), 0);
    }

    @Test
    public void getCreated() throws Exception {
        version temp = new version(1);
        assertEquals("Created Date was set correctly", temp.getCreated(), temp.getEdited());
    }

    @Test
    public void setCreated() throws Exception {
        version temp = new version(1);
        Date dat = new Date();
        temp.setCreated(dat);
        assertEquals("Created Date was updated correctly", dat, temp.getCreated());
    }

    @Test
    public void getEdited() throws Exception {
        version temp = new version(1);
        assertEquals("Edited Date was set correctly", temp.getCreated(), temp.getEdited());
    }

    @Test
    public void setEdited() throws Exception {
        version temp = new version(1);
        Date dat = new Date();
        temp.setEdited(dat);
        assertEquals("Edited Date was updated correctly", dat, temp.getEdited());
    }

    @Test
    public void getNodes() throws Exception {
        version temp = new version(1);
        temp.addNode(nodeType.fd_Unit);
        temp.addNode(nodeType.fd_Unit);
        List<node> nodes = temp.getNodes();
        assertEquals("Returns a list of two nodes", 2, nodes.size(), 0);
    }

    @Test
    public void getWires() throws Exception {
        version temp = new version(1);
        node node1 = new node(nodeType.fd_Unit);
        connector conn1 = node1.getConnector(node1.addConnector(connectorOrientation.center));
        node node2 = new node(nodeType.dd_Image);
        connector conn2 = node2.getConnector(node2.addConnector(connectorOrientation.center));
        temp.addWire(wireType.Blank, node1, conn1, node2, conn2);
        temp.addWire(wireType.Arrow, node2, conn2, node1, conn1);
        List<wire> wires = temp.getWires();
        assertEquals("Returns a list of two wires", 2, wires.size(), 0);
    }

    @Test
    public void getNode() throws Exception {
        version temp = new version(1);
        String nodeID1 = temp.addNode(nodeType.fd_Unit);
        String nodeID2 = temp.addNode(nodeType.dd_Image);
        node node1 = temp.getNode(nodeID1);
        node node2 = temp.getNode(nodeID2);
        assertSame("Type of the first node is correct", nodeType.fd_Unit, node1.getType());
        assertSame("Type of the second node is correct", nodeType.dd_Image, node2.getType());
    }

    @Test
    public void addNode() throws Exception {
        version temp = new version(1);
        String nodeID = temp.addNode(nodeType.fd_Unit);
        node node = temp.getNode(nodeID);
        assertSame("Type of the node is correct", nodeType.fd_Unit, node.getType());
    }

    @Test
    public void removeNodeByObject() throws Exception {
        version temp = new version(1);
        String nodeID1 = temp.addNode(nodeType.fd_Unit);
        String nodeID2 = temp.addNode(nodeType.dd_Image);
        List<node> nodes = temp.getNodes();
        assertEquals("Returns a list of two nodes", 2, nodes.size(), 0);
        node node1 = temp.getNode(nodeID1);
        temp.removeNode(node1);
        nodes = temp.getNodes();
        assertEquals("Returns a list of one node", 1, nodes.size(), 0);
        node node2 = temp.getNode(nodeID2);
        assertSame("Removed the correct node", nodeType.dd_Image, node2.getType());
    }

    @Test
    public void removeNodeByID() throws Exception {
        version temp = new version(1);
        String nodeID1 = temp.addNode(nodeType.fd_Unit);
        String nodeID2 = temp.addNode(nodeType.dd_Image);
        List<node> nodes = temp.getNodes();
        assertEquals("Returns a list of two nodes", 2, nodes.size(), 0);
        temp.removeNode(nodeID1);
        nodes = temp.getNodes();
        assertEquals("Returns a list of one node", 1, nodes.size(), 0);
        node node2 = temp.getNode(nodeID2);
        assertSame("Removed the correct node", nodeType.dd_Image, node2.getType());
    }

    @Test
    public void getWire() throws Exception {
        version temp = new version(1);
        node node1 = new node(nodeType.fd_Unit);
        connector conn1 = node1.getConnector(node1.addConnector(connectorOrientation.center));
        node node2 = new node(nodeType.dd_Image);
        connector conn2 = node2.getConnector(node2.addConnector(connectorOrientation.center));
        String conID1 = temp.addWire(wireType.Blank, node1, conn1, node2, conn2);
        String conID2 = temp.addWire(wireType.Arrow, node2, conn2, node1, conn1);
        wire con1 = temp.getWire(conID1);
        wire con2 = temp.getWire(conID2);
        assertSame("Type of the first wire is correct", wireType.Blank, con1.getType());
        assertSame("Type of the second wire is correct", wireType.Arrow, con2.getType());
    }

    @Test
    public void addWire() throws Exception {
        version temp = new version(1);
        node node1 = new node(nodeType.fd_Unit);
        connector conn1 = node1.getConnector(node1.addConnector(connectorOrientation.center));
        node node2 = new node(nodeType.dd_Image);
        connector conn2 = node2.getConnector(node2.addConnector(connectorOrientation.center));
        String conID = temp.addWire(wireType.Blank, node1, conn1, node2, conn2);
        wire con = temp.getWire(conID);
        assertSame("Type of the wire is correct", wireType.Blank, con.getType());
    }

    @Test
    public void removeWireByObject() throws Exception {
        version temp = new version(1);
        node node1 = new node(nodeType.fd_Unit);
        connector conn1 = node1.getConnector(node1.addConnector(connectorOrientation.center));
        node node2 = new node(nodeType.dd_Image);
        connector conn2 = node2.getConnector(node2.addConnector(connectorOrientation.center));
        String conID1 = temp.addWire(wireType.Blank, node1, conn1, node2, conn2);
        String conID2 = temp.addWire(wireType.Arrow, node2, conn2, node1, conn1);
        List<wire> wires = temp.getWires();
        assertEquals("Returns a list of two wires", 2, wires.size(), 0);
        wire con1 = temp.getWire(conID1);
        temp.removeWire(con1);
        wires = temp.getWires();
        assertEquals("Returns a list of one wire", 1, wires.size(), 0);
        wire con2 = temp.getWire(conID2);
        assertSame("Removed the correct wire", wireType.Arrow, con2.getType());
    }

    @Test
    public void removeWireByID() throws Exception {
        version temp = new version(1);
        node node1 = new node(nodeType.fd_Unit);
        connector conn1 = node1.getConnector(node1.addConnector(connectorOrientation.center));
        node node2 = new node(nodeType.dd_Image);
        connector conn2 = node2.getConnector(node2.addConnector(connectorOrientation.center));
        String conID1 = temp.addWire(wireType.Blank, node1, conn1, node2, conn2);
        String conID2 = temp.addWire(wireType.Arrow, node2, conn2, node1, conn1);
        List<wire> wires = temp.getWires();
        assertEquals("Returns a list of two wires", 2, wires.size(), 0);
        temp.removeWire(conID1);
        wires = temp.getWires();
        assertEquals("Returns a list of one wire", 1, wires.size(), 0);
        wire con2 = temp.getWire(conID2);
        assertSame("Removed the correct wire", wireType.Arrow, con2.getType());
    }

}