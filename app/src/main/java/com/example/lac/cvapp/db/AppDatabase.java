package com.example.lac.cvapp.db;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.example.lac.cvapp.db.dao.AddressDao;
import com.example.lac.cvapp.db.dao.CvDao;
import com.example.lac.cvapp.db.dao.ExperienceDao;
import com.example.lac.cvapp.db.dao.LanguageDao;
import com.example.lac.cvapp.db.dao.StudyDao;
import com.example.lac.cvapp.db.entity.AddressEntity;
import com.example.lac.cvapp.db.entity.CvEntity;
import com.example.lac.cvapp.db.entity.ExperienceEntity;
import com.example.lac.cvapp.db.entity.LanguageEntity;
import com.example.lac.cvapp.db.entity.StudyEntity;
import com.example.lac.cvapp.util.DateRoomConverter;

/**
 * To get an instance of the created database, use the following code:
 * appDB = AppDatabase.getInstance("CurrentActivity".this);
 */
@Database(version = 1, entities = {CvEntity.class, AddressEntity.class, ExperienceEntity.class,
        StudyEntity.class, LanguageEntity.class})
@TypeConverters({DateRoomConverter.class})
public abstract class AppDatabase extends RoomDatabase {

    public abstract CvDao cvDao();
    public abstract AddressDao addressDao();
    public abstract ExperienceDao experienceDao();
    public abstract StudyDao studyDao();
    public abstract LanguageDao languageDao();

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
