package com.example.lac.cvapp.db.entity;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;
import java.util.Date;

@Entity
public class Cv implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private long uid;

    @ColumnInfo(name = "first_name")
    private String firstName;

    @ColumnInfo(name = "last_name")
    private String lastName;

//    @ColumnInfo(name = "address")
//    private Address address;

    @ColumnInfo(name = "phone_number")
    private String phoneNumber;

    @ColumnInfo(name = "email_address")
    private String emailAddress;

    @ColumnInfo(name = "gender")
    private String gender;

    /**
     * This has to be a Date later.
     */
    @ColumnInfo(name = "birth_date")
    private String birthDate;

    @ColumnInfo(name = "nationality")
    private String nationality;

//    @ColumnInfo(name = "professional_experience")
//    private List<ProfessionalExperience> professionalExperiences;

//    @ColumnInfo(name = "studies")
//    private List<Studies> studies;

    @ColumnInfo(name = "native_language")
    private String native_language;

//    @ColumnInfo(name = "language")
//    private List<Language> languages;

//    @ColumnInfo(name = "driving_license")
//    private List<DrivingLicense> driving_licenses;

//    @ColumnInfo(name = "hobby")
//    private List<Hobby> hobbies;

    public Cv() {
    }

    public Cv(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Cv(String firstName, String lastName, String phoneNumber, String emailAddress, String gender, String birthDate, String nationality, String native_language) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.phoneNumber = phoneNumber;
        this.emailAddress = emailAddress;
        this.gender = gender;
        this.birthDate = birthDate;
        this.nationality = nationality;
        this.native_language = native_language;
    }

    public long getUid() {
        return uid;
    }

    public void setUid(long uid) {
        this.uid = uid;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getNative_language() {
        return native_language;
    }

    public void setNative_language(String native_language) {
        this.native_language = native_language;
    }
}
