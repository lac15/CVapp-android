package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lac.cvapp.db.entity.DrivingLicenseEntity;

import java.util.List;

@Dao
public interface DrivingLicenseDao {

    @Insert
    long insert(DrivingLicenseEntity drivingLicenseEntity);

    @Update
    void update(DrivingLicenseEntity drivingLicenseEntity);

    @Delete
    void delete(DrivingLicenseEntity drivingLicenseEntity);

    @Query("SELECT * FROM driving_license WHERE cv_id = :cvId")
    List<DrivingLicenseEntity> findDrivingLicensesForCv(final long cvId);
}
