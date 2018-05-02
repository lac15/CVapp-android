package com.example.lac.cvapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Ignore;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

import static android.arch.persistence.room.ForeignKey.CASCADE;

@Entity(foreignKeys = {
        @ForeignKey(entity = CvEntity.class,
                parentColumns = "id",
                childColumns = "cv_id",
                onDelete = CASCADE)})
public class ExperienceEntity implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long id;

    @ColumnInfo(name = "cv_id")
    private long cvId;

    @ColumnInfo(name = "position")
    private String position;

    @ColumnInfo(name = "company")
    private String company;

    @ColumnInfo(name = "country")
    private String country;

    @ColumnInfo(name = "city")
    private String city;

    @ColumnInfo(name = "start_date")
    private Date startDate;

    @ColumnInfo(name = "end_date")
    private Date endDate;

    public ExperienceEntity() {
    }

    @Ignore
    public ExperienceEntity(long cvId, String position, String company, String country, String city, Date startDate, Date endDate) {
        this.cvId = cvId;
        this.position = position;
        this.company = company;
        this.country = country;
        this.city = city;
        this.startDate = startDate;
        this.endDate = endDate;
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

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }
}
