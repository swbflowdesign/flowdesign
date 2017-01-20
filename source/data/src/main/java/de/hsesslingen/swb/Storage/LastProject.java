package de.hsesslingen.swb.Storage;

import java.util.Date;

public class LastProject {

    private String name;
    private String filePath;
    private Date edited;

    /**
     * Constructor
     *
     * @param name     Name of the project
     * @param filePath Path of the project file
     * @param edited   Date of last edit
     */
    public LastProject(String name, String filePath, Date edited) {
        this.name = name;
        this.filePath = filePath;
        this.edited = edited;
    }

    public String getName() {
        return name;
    }

    public String getFilePath() {
        return filePath;
    }

    public Date getEdited() {
        return edited;
    }

    public void setEdited(Date edited) {
        this.edited = edited;
    }
}
