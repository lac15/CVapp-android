package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;

import com.example.lac.cvapp.db.entity.AddressEntity;

@Dao
public interface AddressDao {

    @Insert
    void insert(AddressEntity addressEntity);

    @Delete
    void delete(AddressEntity addressEntity);
}
