package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lac.cvapp.db.entity.CvEntity;

import java.util.List;

@Dao
public interface CvDao {

    @Query("SELECT * FROM cv")
    List<CvEntity> getAll();

    @Query("SELECT * FROM cv WHERE id IN (:cvIds)")
    List<CvEntity> loadAllByIds(int[] cvIds);

    @Query("SELECT * FROM cv WHERE first_name LIKE :first AND "
            + "last_name LIKE :last LIMIT 1")
    CvEntity findByName(String first, String last);

    @Insert
    void insert(CvEntity cvEntity);

    @Delete
    void delete(CvEntity cvEntity);

}
