package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lac.cvapp.db.entity.Study;

import java.util.List;

@Dao
public interface StudyDao {

    @Insert
    void insert(Study study);

    @Delete
    void delete(Study study);

    @Query("SELECT * FROM study WHERE cv_id = :cvId")
    List<Study> findStudiesForCv(final long cvId);
}
