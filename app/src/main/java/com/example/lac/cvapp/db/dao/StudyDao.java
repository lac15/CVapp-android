package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lac.cvapp.db.entity.StudyEntity;

import java.util.List;

@Dao
public interface StudyDao {

    @Insert
    long insert(StudyEntity studyEntity);

    @Update
    void update(StudyEntity studyEntity);

    @Delete
    void delete(StudyEntity studyEntity);

    @Query("SELECT * FROM study WHERE cv_id = :cvId")
    List<StudyEntity> findStudiesForCv(final long cvId);
}
