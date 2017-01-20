package de.hsesslingen.swb;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;


public class utilsTest {

    @Test
    public void generateUUID() throws Exception {

        String tempUUID;

        // The generated UUID should be 36 characters long
        tempUUID = Utils.generateUUID();
        assertSame("The generated UUID should be 36 characters long", 36, tempUUID.length());

        // All 100 generated UUIDs are unique
        List<String> uuids = new ArrayList<String>();
        boolean unique = true;
        for (int i = 0; i < 100; i++) {
            tempUUID = Utils.generateUUID();
            if (uuids.contains(tempUUID)) {
                unique = false;
            }
            uuids.add(tempUUID);
        }
        assertTrue("All 100 generated UUIDs are unique", unique);

    }

}