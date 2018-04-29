package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.example.lac.cvapp.db.entity.Experience;

@Dao
public interface ExperienceDao {

    @Insert
    void insert(Experience experience);

    @Delete
    void delete(Experience experience);
}
