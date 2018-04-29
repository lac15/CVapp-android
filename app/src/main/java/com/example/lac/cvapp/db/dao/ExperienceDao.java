package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lac.cvapp.db.entity.Experience;

import java.util.List;

@Dao
public interface ExperienceDao {

    @Insert
    void insert(Experience experience);

    @Delete
    void delete(Experience experience);

    @Query("SELECT * FROM experience WHERE cv_id = :cvId")
    List<Experience> findExperiencesForCv(final long cvId);
}
