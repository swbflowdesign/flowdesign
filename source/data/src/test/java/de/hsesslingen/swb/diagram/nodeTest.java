package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.Utils;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import de.hsesslingen.swb.diagram.enums.nodeAttribut;
import de.hsesslingen.swb.diagram.enums.nodeColor;
import de.hsesslingen.swb.diagram.enums.nodeType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class nodeTest {

    @Test
    public void getId() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertEquals("ID was generated correctly", 36, temp.getId().length(), 0);
    }

    @Test
    public void setId() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        String uuid = Utils.generateUUID();
        temp.setId(uuid);
        assertSame("ID was updated correctly", uuid, temp.getId());
    }

    @Test
    public void getType() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertSame("Type was set correctly", nodeType.fd_Unit, temp.getType());
    }

    @Test
    public void getText() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertSame("Text was set correctly", "", temp.getText());
    }

    @Test
    public void setText() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setText("Test");
        assertSame("Text was set correctly", "Test", temp.getText());
    }

    @Test
    public void getAttribut() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertSame("Attribut was set correctly", nodeAttribut.NONE, temp.getAttribut());
    }

    @Test
    public void setAttribut() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setAttribut(nodeAttribut.Configurable);
        assertSame("Attribut was updated correctly", nodeAttribut.Configurable, temp.getAttribut());
    }

    @Test
    public void getConnectors() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.addConnector(connectorOrientation.bottom);
        temp.addConnector(connectorOrientation.bottom);
        List<connector> connectors = temp.getConnectors();
        assertEquals("Returns a list of two connectors", 2, connectors.size(), 0);
    }

    @Test
    public void getHeight() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertEquals("Height was set correctly", 0, temp.getHeight(), 0);
    }

    @Test
    public void setHeight() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setHeight(12.3);
        assertEquals("Height was updated correctly", 12.3, temp.getHeight(), 0);
    }

    @Test
    public void getWidth() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertEquals("Width was set correctly", 0, temp.getHeight(), 0);
    }

    @Test
    public void setWidth() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setWidth(9.6);
        assertEquals("Width was updated correctly", 9.6, temp.getWidth(), 0);
    }

    @Test
    public void getX() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertEquals("X coordinate was set correctly", 0, temp.getX(), 0);
    }

    @Test
    public void setX() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setX(2.36);
        assertEquals("X coordinate was updated correctly", 2.36, temp.getX(), 0);
    }

    @Test
    public void getY() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertEquals("Y coordinate was set correctly", 0, temp.getY(), 0);
    }

    @Test
    public void setY() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setY(6.4);
        assertEquals("Y coordinate updated set correctly", 6.4, temp.getY(), 0);
    }

    @Test
    public void getLink() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertSame("Link was set correctly", "", temp.getLink());
    }

    @Test
    public void setLink() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setLink("URL");
        assertSame("Link was updated correctly", "URL", temp.getLink());
    }

    @Test
    public void getColor() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertSame("Color was set correctly", nodeColor.Default, temp.getColor());
    }

    @Test
    public void setColor() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setColor(nodeColor.Blue);
        assertSame("Color was updated correctly", nodeColor.Blue, temp.getColor());
    }

    @Test
    public void getImage() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertSame("Image was set correctly", "", temp.getImage());
    }

    @Test
    public void setImage() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setImage("URL");
        assertSame("Image was updated set correctly", "URL", temp.getImage());
    }

    @Test
    public void getAdditional() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        assertSame("Additional was set correctly", "", temp.getAdditional());
    }

    @Test
    public void setAdditional() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        temp.setAdditional("Text");
        assertSame("Additional was updated set correctly", "Text", temp.getAdditional());
    }

    @Test
    public void getConnector() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        String conID1 = temp.addConnector(connectorOrientation.bottom);
        String conID2 = temp.addConnector(connectorOrientation.bottom);
        connector con1 = temp.getConnector(conID1);
        connector con2 = temp.getConnector(conID2);
        assertSame("Type of the first connector is correct", connectorOrientation.bottom, con1.getOrientation());
        assertSame("Type of the second connector is correct", connectorOrientation.bottom, con2.getOrientation());
    }

    @Test
    public void addConnector() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        String conID = temp.addConnector(connectorOrientation.bottom);
        connector con = temp.getConnector(conID);
        assertSame("Type of the connector is correct", connectorOrientation.bottom, con.getOrientation());
    }

    @Test
    public void removeConnectorByObject() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        String conID1 = temp.addConnector(connectorOrientation.bottom);
        String conID2 = temp.addConnector(connectorOrientation.bottom);
        List<connector> connectors = temp.getConnectors();
        assertEquals("Returns a list of two connectors", 2, connectors.size(), 0);
        connector con1 = temp.getConnector(conID1);
        temp.removeConnector(con1);
        connectors = temp.getConnectors();
        assertEquals("Returns a list of one connector", 1, connectors.size(), 0);
        connector con2 = temp.getConnector(conID2);
        assertSame("Removed the correct connector", connectorOrientation.bottom, con2.getOrientation());
    }

    @Test
    public void removeConnectorByID() throws Exception {
        node temp = new node(nodeType.fd_Unit);
        String conID1 = temp.addConnector(connectorOrientation.bottom);
        String conID2 = temp.addConnector(connectorOrientation.bottom);
        List<connector> connectors = temp.getConnectors();
        assertEquals("Returns a list of two connectors", 2, connectors.size(), 0);
        temp.removeConnector(conID1);
        connectors = temp.getConnectors();
        assertEquals("Returns a list of one connectors", 1, connectors.size(), 0);
        connector con2 = temp.getConnector(conID2);
        assertSame("Removed the correct connector", connectorOrientation.bottom, con2.getOrientation());
    }

}