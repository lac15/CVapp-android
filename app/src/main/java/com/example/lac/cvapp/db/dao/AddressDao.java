package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import com.example.lac.cvapp.db.entity.AddressEntity;

@Dao
public interface AddressDao {

    @Query("SELECT * FROM address WHERE id = :id")
    AddressEntity findById(long id);

    @Insert
    long insert(AddressEntity addressEntity);

    @Update
    void update(AddressEntity addressEntity);

    @Delete
    void delete(AddressEntity addressEntity);
}
