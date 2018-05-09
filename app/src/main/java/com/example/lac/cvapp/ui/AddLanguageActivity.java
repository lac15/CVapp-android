package com.example.lac.cvapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.AppDatabase;
import com.example.lac.cvapp.db.entity.CvEntity;
import com.example.lac.cvapp.db.entity.LanguageEntity;

import java.lang.ref.WeakReference;

public class AddLanguageActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private LanguageEntity languageEntity;
    private CvEntity cvEntity;

    private TextInputEditText etName;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_language);

        appDatabase = AppDatabase.getInstance(AddLanguageActivity.this);

        etName = findViewById(R.id.et_name);

        Button button = findViewById(R.id.button);
        if ( (languageEntity = (LanguageEntity) getIntent().getSerializableExtra("language")) != null ){
            update = true;
            button.setText(getResources().getString(R.string.button_update));
            etName.setText(languageEntity.getName());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    languageEntity.setName(etName.getText().toString());

                    if (languageEntity.getId() > 0) {
                        appDatabase.languageDao().update(languageEntity);
                    }

                    setResult(languageEntity, 2);
                } else {
                    languageEntity = new LanguageEntity(etName.getText().toString());

                    if ( (cvEntity = (CvEntity) getIntent().getSerializableExtra("cv")) != null
                            && (cvEntity.getId() > 0) ){
                        new InsertTask(AddLanguageActivity.this, languageEntity, cvEntity.getId()).execute();
                    }
                    setResult(languageEntity, 1);
                }
            }
        });
    }

    private void setResult(LanguageEntity languageEntity, int flag){
        setResult(flag, new Intent().putExtra("language", languageEntity));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddLanguageActivity> activityReference;
        private LanguageEntity languageEntity;
        private long cvId;

        InsertTask(AddLanguageActivity context, LanguageEntity languageEntity, long cvId) {
            activityReference = new WeakReference<>(context);
            this.languageEntity = languageEntity;
            this.cvId = cvId;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            languageEntity.setCvId(cvId);

            long j = activityReference.get().appDatabase.languageDao().insert(languageEntity);
            languageEntity.setId(j);
            Log.e("ID ", "doInBackground: " + j );
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(languageEntity, 1);
                activityReference.get().finish();
            }
        }
    }

    @Override
    protected void onDestroy() {
        appDatabase.cleanUp();
        super.onDestroy();
    }

}
