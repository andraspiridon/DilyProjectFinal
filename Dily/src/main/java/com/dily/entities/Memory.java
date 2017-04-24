package com.dily.entities;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * Created by Andra on 4/10/2017.
 */
@Entity
@Table(name = "Memory")
public class Memory {
    @Id
    private int memoryId;
    private String title;
    private String description;
    private String location;
    private Date memoryDate;
    private String privacy;

    public Memory(int memoryId, String title, String description, String location, Date memoryDate, String privacy) {
        this.memoryId = memoryId;
        this.title = title;
        this.description = description;
        this.location = location;
        this.memoryDate = memoryDate;
        this.privacy = privacy;
    }

    public int getMemoryId() {
        return memoryId;
    }

    public void setMemoryId(int memoryId) {
        this.memoryId = memoryId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Date getMemoryDate() {
        return memoryDate;
    }

    public void setMemoryDate(Date memoryDate) {
        this.memoryDate = memoryDate;
    }

    public String getPrivacy() {
        return privacy;
    }

    public void setPrivacy(String privacy) {
        this.privacy = privacy;
    }
}
