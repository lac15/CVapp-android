package com.example.lac.cvapp.ui;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.AppDatabase;
import com.example.lac.cvapp.db.entity.Cv;

import java.lang.ref.WeakReference;

public class MainActivity extends AppCompatActivity {
    private AppDatabase appDB;
    private Cv cv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        appDB = AppDatabase.getInstance(MainActivity.this);
    }

    public void onSave(View view) {
        EditText editText = (EditText) findViewById(R.id.editText);
        EditText editText2 = (EditText) findViewById(R.id.editText2);
        cv = new Cv(editText.getText().toString(), editText2.getText().toString());

        new DatabaseAsync(MainActivity.this, cv).execute();
    }

    private static class DatabaseAsync extends AsyncTask<Void, Void, Boolean> {
        private WeakReference<MainActivity> activityReference;
        private Cv cv;

        DatabaseAsync(MainActivity context, Cv cv) {
            activityReference = new WeakReference<>(context);
            this.cv = cv;
        }

        @Override
        protected Boolean doInBackground(Void... voids) {
            activityReference.get().appDB.cvDao().insert(cv);

            return true;
        }
    }
}
