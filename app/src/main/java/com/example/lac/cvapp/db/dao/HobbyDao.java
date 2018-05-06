package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lac.cvapp.db.entity.HobbyEntity;

import java.util.List;

@Dao
public interface HobbyDao {

    @Insert
    long insert(HobbyEntity hobbyEntity);

    @Update
    void update(HobbyEntity hobbyEntity);

    @Delete
    void delete(HobbyEntity hobbyEntity);

    @Query("SELECT * FROM hobby WHERE cv_id = :cvId")
    List<HobbyEntity> findHobbiesForCv(final long cvId);
}
