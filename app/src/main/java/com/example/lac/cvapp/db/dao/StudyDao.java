package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lac.cvapp.db.entity.StudyEntity;

import java.util.List;

@Dao
public interface StudyDao {

    @Insert
    void insert(StudyEntity studyEntity);

    @Delete
    void delete(StudyEntity studyEntity);

    @Query("SELECT * FROM StudyEntity WHERE cv_id = :cvId")
    List<StudyEntity> findStudiesForCv(final long cvId);
}
