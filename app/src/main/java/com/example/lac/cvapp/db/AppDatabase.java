package com.example.lac.cvapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.example.lac.cvapp.db.dao.CvDao;
import com.example.lac.cvapp.db.entity.Cv;

/**
 * To get an instance of the created database, use the following code:
 * appDB = AppDatabase.getInstance("CurrentActivity".this);
 */
@Database(entities = {Cv.class}, version = 1)
public abstract class AppDatabase extends RoomDatabase {

    public abstract CvDao cvDao();

    private static AppDatabase appDB;

    public static AppDatabase getInstance(Context context) {
        if (null == appDB) {
            appDB = buildDatabaseInstance(context);
        }
        return appDB;
    }

    private static AppDatabase buildDatabaseInstance(Context context) {
        return Room.databaseBuilder(context,
                AppDatabase.class,
                "APP_DATABASE.DB")
                .allowMainThreadQueries().build();
    }

    public void cleanUp(){
        appDB = null;
    }

}
