package de.hsesslingen.swb.diagram;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class jointTest {

    @Test
    public void getId() throws Exception {
        joint temp = new joint(10, 4);
        assertEquals("ID was generated correctly", 36, temp.getId().length(), 0);
    }

    @Test
    public void getX() throws Exception {
        joint temp = new joint(10, 4);
        assertEquals("X coordinate was set correctly", 10.0, temp.getX(), 0);
    }

    @Test
    public void setX() throws Exception {
        joint temp = new joint(10, 4);
        temp.setX(12);
        assertEquals("X coordinate was updated correctly", 12.0, temp.getX(), 0);
    }

    @Test
    public void getY() throws Exception {
        joint temp = new joint(10, 4);
        assertEquals("Y coordinate was set correctly", 4.0, temp.getY(), 0);
    }

    @Test
    public void setY() throws Exception {
        joint temp = new joint(10, 4);
        temp.setY(9);
        assertEquals("Y coordinate was updated correctly", 9.0, temp.getY(), 0);
    }

}