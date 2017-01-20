package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.Utils;
import de.hsesslingen.swb.diagram.enums.connectorOrientation;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class connectorTest {

    @Test
    public void getId() throws Exception {
        connector temp = new connector(connectorOrientation.bottom);
        assertEquals("ID was generated correctly", 36, temp.getId().length(), 0);
    }

    @Test
    public void setId() throws Exception {
        connector temp = new connector(connectorOrientation.bottom);
        String uuid = Utils.generateUUID();
        temp.setId(uuid);
        assertSame("ID was updated correctly", uuid, temp.getId());
    }

    @Test
    public void getOrientation() throws Exception {
        connector temp = new connector(connectorOrientation.bottom);
        assertSame("Orientation was set correctly", connectorOrientation.bottom, temp.getOrientation());
    }

    @Test
    public void setOrientation() throws Exception {
        connector temp = new connector(connectorOrientation.bottom);
        temp.setOrientation(connectorOrientation.bottom);
        assertSame("Orientation was updated correctly", connectorOrientation.bottom, temp.getOrientation());
    }

    @Test
    public void getType() throws Exception {
        connector temp = new connector(connectorOrientation.bottom);
        assertSame("Type was set correctly", "", temp.getType());
    }

    @Test
    public void setType() throws Exception {
        connector temp = new connector(connectorOrientation.bottom);
        temp.setType("String");
        assertSame("Type was updated correctly", "String", temp.getType());
    }

}