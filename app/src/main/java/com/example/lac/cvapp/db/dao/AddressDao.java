package com.example.lac.cvapp.db.dao;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.example.lac.cvapp.db.entity.Address;

@Dao
public interface AddressDao {

    @Insert
    void insert(Address address);

    @Delete
    void delete(Address address);
}
