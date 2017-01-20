package de.hsesslingen.swb.project;

import de.hsesslingen.swb.diagram.diagram;
import de.hsesslingen.swb.diagram.enums.diagramType;
import org.junit.Test;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

public class projectTest {

    @Test
    public void getName() throws Exception {
        project temp = new project("Test");
        assertSame("Name was set correctly", "Test", temp.getName());
    }

    @Test
    public void setName() throws Exception {
        project temp = new project("Test");
        temp.setName("Name");
        assertSame("Name was updated correctly", "Name", temp.getName());
    }

    @Test
    public void getCreated() throws Exception {
        project temp = new project("Test");
        assertEquals("Created Date was set correctly", temp.getCreated(), temp.getEdited());
    }

    @Test
    public void setCreated() throws Exception {
        project temp = new project("Test");
        Date dat = new Date();
        temp.setCreated(dat);
        assertEquals("Created Date was updated correctly", dat, temp.getCreated());
    }

    @Test
    public void getEdited() throws Exception {
        project temp = new project("Test");
        assertEquals("Edited Date was set correctly", temp.getCreated(), temp.getEdited());
    }

    @Test
    public void setEdited() throws Exception {
        project temp = new project("Test");
        Date dat = new Date();
        temp.setEdited(dat);
        assertEquals("Edited Date was updated correctly", dat, temp.getEdited());
    }

    @Test
    public void getDiagrams() throws Exception {
        project temp = new project("Test");
        temp.addDiagram("Diag1", diagramType.SystemUmweltDiagram);
        temp.addDiagram("Diag2", diagramType.FlowDesign);
        List<diagram> diagrams = temp.getDiagrams();
        assertEquals("Returns a list of two diagrams", 2, diagrams.size(), 0);
    }

    @Test
    public void getDiagram() throws Exception {
        project temp = new project("Test");
        temp.addDiagram("Diag1", diagramType.SystemUmweltDiagram);
        temp.addDiagram("Diag2", diagramType.FlowDesign);
        diagram diag1 = temp.getDiagram("Diag1");
        diagram diag2 = temp.getDiagram("Diag2");
        assertSame("Type of the first diagram is correct", diagramType.SystemUmweltDiagram, diag1.getType());
        assertSame("Type of the second diagram is correct", diagramType.FlowDesign, diag2.getType());
    }

    @Test
    public void addDiagramByObject() throws Exception {
        project temp = new project("Test");
        diagram diag = new diagram("Name", diagramType.DialogDiagram);
        temp.addDiagram(diag);
        diagram diag2 = temp.getDiagram("Name");
        assertEquals("Type of the diagram is correct", diag, diag2);
    }

    @Test
    public void addDiagram() throws Exception {
        project temp = new project("Test");
        temp.addDiagram("Diag", diagramType.SystemUmweltDiagram);
        diagram diag = temp.getDiagram("Diag");
        assertSame("Type of the diagram is correct", diagramType.SystemUmweltDiagram, diag.getType());
    }

    @Test
    public void removeDiagramByObject() throws Exception {
        project temp = new project("Test");
        temp.addDiagram("Diag1", diagramType.SystemUmweltDiagram);
        temp.addDiagram("Diag2", diagramType.FlowDesign);
        List<diagram> diagrams = temp.getDiagrams();
        assertEquals("Returns a list of two diagrams", 2, diagrams.size(), 0);
        diagram diag1 = temp.getDiagram("Diag1");
        temp.removeDiagram(diag1);
        diagrams = temp.getDiagrams();
        assertEquals("Returns a list of one diagram", 1, diagrams.size(), 0);
        diagram diag2 = temp.getDiagram("Diag2");
        assertSame("Removed the correct diagram", diagramType.FlowDesign, diag2.getType());
    }

    @Test
    public void removeDiagramByName() throws Exception {
        project temp = new project("Test");
        temp.addDiagram("Diag1", diagramType.SystemUmweltDiagram);
        temp.addDiagram("Diag2", diagramType.FlowDesign);
        List<diagram> diagrams = temp.getDiagrams();
        assertEquals("Returns a list of two diagrams", 2, diagrams.size(), 0);
        temp.removeDiagram("Diag1");
        diagrams = temp.getDiagrams();
        assertEquals("Returns a list of one diagram", 1, diagrams.size(), 0);
        diagram diag2 = temp.getDiagram("Diag2");
        assertSame("Removed the correct diagram", diagramType.FlowDesign, diag2.getType());
    }

}