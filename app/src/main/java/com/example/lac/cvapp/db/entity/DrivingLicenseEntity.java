package com.example.lac.cvapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "driving_license",
        foreignKeys = {
                @ForeignKey(entity = CvEntity.class,
                        parentColumns = "id",
                        childColumns = "cv_id",
                        onDelete = CASCADE)})
public class DrivingLicenseEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "cv_id")
    private long cvId;

    @ColumnInfo(name = "type")
    private String type;

    public DrivingLicenseEntity() {
    }

    @Ignore
    public DrivingLicenseEntity(String type) {
        this.type = type;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getCvId() {
        return cvId;
    }

    public void setCvId(long cvId) {
        this.cvId = cvId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
