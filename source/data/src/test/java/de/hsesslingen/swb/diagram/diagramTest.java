package de.hsesslingen.swb.diagram;

import de.hsesslingen.swb.diagram.enums.diagramType;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class diagramTest {

    @Test
    public void getName() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        assertSame("Name was set correctly", "Test", temp.getName());
    }

    @Test
    public void setName() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        temp.setName("Name");
        assertSame("Name was updated correctly", "Name", temp.getName());
    }

    @Test
    public void getParent() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        assertSame("Parent was set correctly", "root", temp.getParent());
    }

    @Test
    public void setParent() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        temp.setParent("Test");
        assertSame("Parent was updated correctly", "Test", temp.getParent());
    }

    @Test
    public void getType() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        assertSame("Type was set correctly", diagramType.FlowDesign, temp.getType());
    }

    @Test
    public void getScrollPositionX() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        assertEquals("ScrollPositionX was set correctly", 0, temp.getScrollPositionX(), 0);
    }

    @Test
    public void setScrollPositionX() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        temp.setScrollPositionX(5.3);
        assertEquals("ScrollPositionX was updated correctly", 5.3, temp.getScrollPositionX(), 0);
    }

    @Test
    public void getScrollPositionY() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        assertEquals("getScrollPositionY was set correctly", 0, temp.getScrollPositionY(), 0);
    }

    @Test
    public void setScrollPositionY() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        temp.setScrollPositionY(9.1);
        assertEquals("ScrollPositionX was updated correctly", 9.1, temp.getScrollPositionY(), 0);

    }

    @Test
    public void getVersions() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        temp.addVersion();
        temp.addVersion();
        List<version> versions = temp.getVersions();
        assertEquals("Returns a list of two versions", 2, versions.size(), 0);

    }

    @Test
    public void getVersion() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        int versNum1 = temp.addVersion();
        int versNum2 = temp.addVersion();
        version vers1 = temp.getVersion(versNum1);
        version vers2 = temp.getVersion(versNum2);
        assertEquals("Num of the first version is correct", 1, vers1.getNum());
        assertEquals("Num of the second version is correct", 2, vers2.getNum());
    }

    @Test
    public void addVersion() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        int versNum = temp.addVersion();
        version vers = temp.getVersion(versNum);
        assertEquals("Num of the first version is correct", 1, vers.getNum());
    }

    @Test
    public void removeVersionByObject() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        int versNum1 = temp.addVersion();
        int versNum2 = temp.addVersion();
        List<version> versions = temp.getVersions();
        assertEquals("Returns a list of two versions", 2, versions.size(), 0);
        version vers1 = temp.getVersion(versNum1);
        temp.removeVersion(vers1);
        versions = temp.getVersions();
        assertEquals("Returns a list of one version", 1, versions.size(), 0);
        version vers2 = temp.getVersion(versNum2);
        assertSame("Removed the correct version", 2, vers2.getNum());
    }

    @Test
    public void removeVersionByID() throws Exception {
        diagram temp = new diagram("Test", diagramType.FlowDesign);
        int versNum1 = temp.addVersion();
        int versNum2 = temp.addVersion();
        List<version> versions = temp.getVersions();
        assertEquals("Returns a list of two versions", 2, versions.size(), 0);
        temp.removeVersion(versNum1);
        versions = temp.getVersions();
        assertEquals("Returns a list of one version", 1, versions.size(), 0);
        version vers2 = temp.getVersion(versNum2);
        assertSame("Removed the correct version", 2, vers2.getNum());
    }

}