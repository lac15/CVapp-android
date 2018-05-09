package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lac.cvapp.db.entity.CvEntity;

import java.util.List;

@Dao
public interface CvDao {

    @Query("SELECT * FROM cv")
    List<CvEntity> getAll();

    @Query("SELECT * FROM cv WHERE id IN (:cvIds)")
    List<CvEntity> loadAllByIds(int[] cvIds);

    @Query("SELECT cv.id, cv.first_name, cv.last_name, cv.address_id, cv.phone_number, cv.email_address, cv.gender, " +
            "cv.birth_date, cv.nationality, cv.native_language " +
            "FROM cv " +
            "JOIN address a ON cv.address_id = a.id " +
            "JOIN driving_license dl ON cv.id = dl.cv_id " +
            "JOIN experience e ON cv.id = e.cv_id " +
            "JOIN hobby h ON cv.id = h.cv_id " +
            "JOIN language l ON cv.id = l.cv_id " +
            "JOIN study s ON cv.id = s.cv_id " +
            "WHERE cv.first_name LIKE :query OR cv.last_name LIKE :query OR cv.email_address LIKE :query " +
            "   OR cv.gender LIKE :query OR cv.nationality LIKE :query OR cv.native_language LIKE :query " +
            "   OR dl.type LIKE :query OR e.position LIKE :query OR e.company LIKE :query OR e.country LIKE :query " +
            "   OR e.city LIKE :query OR h.name LIKE :query OR l.name LIKE :query OR s.name LIKE :query " +
            "   OR s.school LIKE :query OR s.country LIKE :query OR s.city LIKE :query OR s.description LIKE :query")
    List<CvEntity> searchByQuery(String query);

    @Insert
    long insert(CvEntity cvEntity);

    @Update
    void update(CvEntity cvEntity);

    @Delete
    void delete(CvEntity cvEntity);

}
