package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.Utils;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeType;
import de.hsesslingen.swb.diagram.enums.wireType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class wireTest {

    @Test
    public void getId() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        assertEquals("ID was generated correctly", 36, temp.getId().length(), 0);
    }

    @Test
    public void setId() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        String uuid = Utils.generateUUID();
        temp.setId(uuid);
        assertSame("ID was updated correctly", uuid, temp.getId());
    }

    @Test
    public void getType() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        assertSame("Type was set correctly", wireType.Blank, temp.getType());
    }

    @Test
    public void setType() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        temp.setType(wireType.Blank);
        assertSame("Type was updated correctly", wireType.Blank, temp.getType());
    }

    @Test
    public void getSourceNode() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        assertEquals("Source was set correctly", source, temp.getSourceNode());
    }

    @Test
    public void setSourceNode() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        temp.setSourceNode(target);
        assertEquals("Source was updated correctly", target, temp.getSourceNode());
    }

    @Test
    public void getTargetNode() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        assertEquals("Target was set correctly", target, temp.getTargetNode());
    }

    @Test
    public void setTargetNode() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        temp.setTargetNode(source);
        assertEquals("Target was updated correctly", source, temp.getTargetNode());
    }

    @Test
    public void getSourceConnector() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        assertEquals("Source connector was set correctly", sourceConn, temp.getSourceConnector());
    }

    @Test
    public void setSourceConnector() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        temp.setSourceNode(target);
        assertEquals("Source connector was updated correctly", sourceConn, temp.getSourceConnector());
    }

    @Test
    public void getTargetConnector() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        assertEquals("Target connector was set correctly", targetConn, temp.getTargetConnector());
    }

    @Test
    public void setTargetConnector() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        temp.setTargetNode(source);
        assertEquals("Target connector was updated correctly", targetConn, temp.getTargetConnector());
    }

    @Test
    public void getLabel() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        assertSame("InputLabel was initialized correctly", "", temp.getLabel());
    }

    @Test
    public void setLabel() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        temp.setLabel("Input");
        assertSame("InputLabel was updated correctly", "Input", temp.getLabel());
    }

    @Test
    public void getJoints() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        temp.addJoint(10, 15);
        temp.addJoint(6, 8);
        List<joint> joints = temp.getJoints();
        assertEquals("Returns a list of two joints", 2, joints.size(), 0);
    }

    @Test
    public void getJoint() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        String joiID1 = temp.addJoint(10, 15);
        String joiID2 = temp.addJoint(6, 8);
        joint joi1 = temp.getJoint(joiID1);
        joint joi2 = temp.getJoint(joiID2);
        assertEquals("X coordinate of the first joint is correct", 10, joi1.getX(), 0);
        assertEquals("Y coordinate of the second joint is correct", 8, joi2.getY(), 0);
    }

    @Test
    public void addJoint() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        String joiID = temp.addJoint(10, 15);
        joint joi = temp.getJoint(joiID);
        assertEquals("X coordinate of the joint is correct", 10, joi.getX(), 0);
        assertEquals("Y coordinate of the joint is correct", 15, joi.getY(), 0);
    }

    @Test
    public void removeJointByObject() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        String joiID1 = temp.addJoint(10, 15);
        String joiID2 = temp.addJoint(6, 8);
        List<joint> joints = temp.getJoints();
        assertEquals("Returns a list of two joints", 2, joints.size(), 0);
        joint joi1 = temp.getJoint(joiID1);
        temp.removeJoint(joi1);
        joints = temp.getJoints();
        assertEquals("Returns a list of one joints", 1, joints.size(), 0);
        joint joi2 = temp.getJoint(joiID2);
        assertEquals("Removed the correct joint", 6, joi2.getX(), 0);
    }

    @Test
    public void removeJointByID() throws Exception {
        node source = new node(nodeType.fd_Unit);
        connector sourceConn = source.getConnector(source.addConnector(connectorOrientation.bottom));
        node target = new node(nodeType.fd_Unit);
        connector targetConn = target.getConnector(target.addConnector(connectorOrientation.center));
        wire temp = new wire(wireType.Blank, source, sourceConn, target, targetConn);
        String joiID1 = temp.addJoint(10, 15);
        String joiID2 = temp.addJoint(6, 8);
        List<joint> joints = temp.getJoints();
        assertEquals("Returns a list of two joints", 2, joints.size(), 0);
        temp.removeJoint(joiID1);
        joints = temp.getJoints();
        assertEquals("Returns a list of one joints", 1, joints.size(), 0);
        joint joi2 = temp.getJoint(joiID2);
        assertEquals("Removed the correct joint", 6, joi2.getX(), 0);
    }

}