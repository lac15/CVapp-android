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
import com.example.lac.cvapp.db.entity.HobbyEntity;

import java.lang.ref.WeakReference;

public class AddHobbyActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private HobbyEntity hobbyEntity;
    private CvEntity cvEntity;

    private TextInputEditText etName;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_hobby);

        appDatabase = AppDatabase.getInstance(AddHobbyActivity.this);

        etName = findViewById(R.id.et_name);

        Button button = findViewById(R.id.button);
        if ( (hobbyEntity = (HobbyEntity) getIntent().getSerializableExtra("hobby")) != null ){
            getSupportActionBar().setTitle(getResources().getString(R.string.label_update_hobby));
            update = true;
            button.setText(getResources().getString(R.string.button_update));
            etName.setText(hobbyEntity.getName());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    hobbyEntity.setName(etName.getText().toString());

                    if (hobbyEntity.getId() > 0) {
                        appDatabase.hobbyDao().update(hobbyEntity);
                    }

                    setResult(hobbyEntity, 2);
                } else {
                    hobbyEntity = new HobbyEntity(etName.getText().toString());

                    if ( (cvEntity = (CvEntity) getIntent().getSerializableExtra("cv")) != null
                            && (cvEntity.getId() > 0) ){
                        new InsertTask(AddHobbyActivity.this, hobbyEntity, cvEntity.getId()).execute();
                    }
                    setResult(hobbyEntity, 1);
                }
            }
        });
    }

    private void setResult(HobbyEntity hobbyEntity, int flag){
        setResult(flag, new Intent().putExtra("hobby", hobbyEntity));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddHobbyActivity> activityReference;
        private HobbyEntity hobbyEntity;
        private long cvId;

        InsertTask(AddHobbyActivity context, HobbyEntity hobbyEntity, long cvId) {
            activityReference = new WeakReference<>(context);
            this.hobbyEntity = hobbyEntity;
            this.cvId = cvId;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            hobbyEntity.setCvId(cvId);

            long j = activityReference.get().appDatabase.hobbyDao().insert(hobbyEntity);
            hobbyEntity.setId(j);
            Log.e("ID ", "doInBackground: " + j );
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(hobbyEntity, 1);
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
