package com.example.lac.cvapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(tableName = "hobby",
        foreignKeys = {
                @ForeignKey(entity = CvEntity.class,
                        parentColumns = "id",
                        childColumns = "cv_id",
                        onDelete = CASCADE)})
public class HobbyEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "cv_id")
    private long cvId;

    @ColumnInfo(name = "name")
    private String name;

    public HobbyEntity() {
    }

    @Ignore
    public HobbyEntity(long cvId, String name) {
        this.cvId = cvId;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
