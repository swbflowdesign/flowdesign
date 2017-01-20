package de.hsesslingen.swb.diagram;

import com.rits.cloning.Cloner;
import de.hsesslingen.swb.diagram.enums.diagramType;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class diagram {

    private String name;
    private String parent;
    private diagramType type;
    private double scrollPositionX;
    private double scrollPositionY;
    private List<version> versions;
    private int activeVersionNum;

    /**
     * Constructor
     *
     * @param name Name of the diagram
     * @param type Type of the diagram
     */
    public diagram(String name, diagramType type) {
        /* Passed values */
        this.name = name;
        this.type = type;

        /* Default values */
        this.parent = "root";
        this.scrollPositionX = 0.0;
        this.scrollPositionY = 0.0;
        this.versions = new ArrayList<version>();
        this.activeVersionNum = -1;
    }

    /**
     * Getters and Setters
     */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public diagramType getType() {
        return type;
    }

    public double getScrollPositionX() {
        return scrollPositionX;
    }

    public void setScrollPositionX(double scrollPositionX) {
        this.scrollPositionX = scrollPositionX;
    }

    public double getScrollPositionY() {
        return scrollPositionY;
    }

    public void setScrollPositionY(double scrollPositionY) {
        this.scrollPositionY = scrollPositionY;
    }

    public List<version> getVersions() {
        return versions;
    }

    public int getActiveVersionNum() {
        return activeVersionNum;
    }

    public void setActiveVersionNum(int activeVersionNum) {
        if (this.getVersion(activeVersionNum) != null) {
            this.activeVersionNum = activeVersionNum;
        }
    }

    /**
     * Compute the next version number
     *
     * @return Version number
     */
    public int findNextVersionNumber() {
        int tempNum = 1;

        for (version temp : this.versions) {
            if (temp.getNum() >= tempNum) {
                tempNum = temp.getNum();
            }
        }

        return tempNum + 1;
    }

    /**
     * Get a version with its version number
     *
     * @param num Version number of the version
     * @return Version
     */
    public version getVersion(int num) {
        for (version temp : this.versions) {
            if (temp.getNum() == num) {
                return temp;
            }
        }
        return null;
    }

    /**
     * Get the active version
     *
     * @return Version
     */
    public version getActiveVersion() {
        if (this.activeVersionNum > -1) {
            return getVersion(this.activeVersionNum);
        }
        return null;
    }

    /**
     * Add new version
     *
     * @return Version object
     */
    public int addVersion(version vers) {

        Cloner cloner = new Cloner();
        version tempVersion = cloner.deepClone(vers);

        // Check for unique num
        for (version temp : this.versions) {
            if (temp.getNum() == tempVersion.getNum()) {
                tempVersion.setNum(findNextVersionNumber());
                System.out.println("Version number already existed. Version number was updated!");
            }
        }

        this.versions.add(tempVersion);
        this.activeVersionNum = tempVersion.getNum();

        return tempVersion.getNum();
    }

    /**
     * Add new version
     *
     * @return Version number
     */
    public int addVersion() {
        int num = findNextVersionNumber();
        version temp;

        if (this.versions.size() == 0) {
            num = 1;
            temp = new version(num);
        } else {
            Cloner cloner = new Cloner();
            temp = cloner.deepClone(this.getActiveVersion());
            temp.setNum(findNextVersionNumber());
            temp.setCreated(new Date());
            temp.setEdited(new Date());
        }

        this.versions.add(temp);
        this.activeVersionNum = num;

        return num;
    }

    /**
     * Remove a version
     *
     * @param vers Version object
     */
    public void removeVersion(version vers) {
        this.versions.remove(vers);

        if (vers.getNum() == this.activeVersionNum) {
            this.activeVersionNum = findNextVersionNumber() - 1;
        }
    }

    /**
     * Remove a version
     *
     * @param num Version number of the version
     */
    public void removeVersion(int num) {
        for (version temp : this.versions) {
            if (temp.getNum() == num) {
                this.versions.remove(temp);
                break;
            }
        }

        if (num == this.activeVersionNum) {
            this.activeVersionNum = findNextVersionNumber() - 1;
        }
    }


}
