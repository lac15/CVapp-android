package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lac.cvapp.db.entity.LanguageEntity;

import java.util.List;

@Dao
public interface LanguageDao {

    @Insert
    void insert(LanguageEntity languageEntity);

    @Delete
    void delete(LanguageEntity languageEntity);

    @Query("SELECT * FROM language WHERE cv_id = :cvId")
    List<LanguageEntity> findLanguagesForCv(final long cvId);
}
