package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lac.cvapp.db.entity.ExperienceEntity;

import java.util.List;

@Dao
public interface ExperienceDao {

    @Insert
    long insert(ExperienceEntity experienceEntity);

    @Update
    void update(ExperienceEntity experienceEntity);

    @Delete
    void delete(ExperienceEntity experienceEntity);

    @Query("SELECT * FROM experience WHERE cv_id = :cvId")
    List<ExperienceEntity> findExperiencesForCv(final long cvId);
}
