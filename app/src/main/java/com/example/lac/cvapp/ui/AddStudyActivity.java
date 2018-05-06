package com.example.lac.cvapp.ui;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.AppDatabase;
import com.example.lac.cvapp.db.entity.CvEntity;
import com.example.lac.cvapp.db.entity.StudyEntity;
import com.example.lac.cvapp.util.DateRoomConverter;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddStudyActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private DateRoomConverter dateRoomConverter;
    private StudyEntity studyEntity;
    private CvEntity cvEntity;

    private TextInputEditText etName, etSchool, etCountry, etCity, etDescription;
    private EditText etStartDate, etEndDate;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study);

        appDatabase = AppDatabase.getInstance(AddStudyActivity.this);

        etName = findViewById(R.id.et_name);
        etSchool = findViewById(R.id.et_school);
        etCountry = findViewById(R.id.et_country);
        etCity = findViewById(R.id.et_city);
        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);
        etDescription = findViewById(R.id.et_description);

        Button button = findViewById(R.id.button);
        if ( (studyEntity = (StudyEntity) getIntent().getSerializableExtra("study")) != null ){
            getSupportActionBar().setTitle("Update study");
            update = true;
            button.setText("Update");
            etName.setText(studyEntity.getName());
            etSchool.setText(studyEntity.getSchool());
            etCountry.setText(studyEntity.getCountry());
            etCity.setText(studyEntity.getCity());

            DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
            String startDate = "";
            String endDate = "";
            try {
                startDate =  format.format(studyEntity.getStartDate());
                endDate =  format.format(studyEntity.getEndDate());
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }

            etStartDate.setText(startDate);
            etEndDate.setText(endDate);
            etDescription.setText(studyEntity.getDescription());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    studyEntity.setName(etName.getText().toString());
                    studyEntity.setSchool(etSchool.getText().toString());
                    studyEntity.setCountry(etCountry.getText().toString());
                    studyEntity.setCity(etCity.getText().toString());

                    DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
                    Date startDate = new Date();
                    Date endDate = new Date();
                    try {
                        startDate =  format.parse(etStartDate.getText().toString());
                        endDate =  format.parse(etEndDate.getText().toString());
                    } catch (Exception e) {
                        Log.getStackTraceString(e);
                    }

                    studyEntity.setStartDate(startDate);
                    studyEntity.setEndDate(endDate);
                    studyEntity.setDescription(etDescription.getText().toString());

                    if (studyEntity.getId() > 0) {
                        appDatabase.studyDao().update(studyEntity);
                    }

                    setResult(studyEntity, 2);
                } else {
                    DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
                    Date startDate = new Date();
                    Date endDate = new Date();
                    try {
                        startDate =  format.parse(etStartDate.getText().toString());
                        endDate =  format.parse(etEndDate.getText().toString());
                    } catch (Exception e) {
                        Log.getStackTraceString(e);
                    }

                    studyEntity = new StudyEntity(etName.getText().toString(), etSchool.getText().toString(),
                            etCountry.getText().toString(), etCity.getText().toString(),
                            startDate, endDate, etDescription.getText().toString());

                    if ( (cvEntity = (CvEntity) getIntent().getSerializableExtra("cv")) != null
                            && (cvEntity.getId() > 0) ){
                        new InsertTask(AddStudyActivity.this, studyEntity, cvEntity.getId()).execute();
                    }
                    setResult(studyEntity, 1);
                }
            }
        });
    }

    private void setResult(StudyEntity study, int flag){
        setResult(flag, new Intent().putExtra("study", study));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddStudyActivity> activityReference;
        private StudyEntity studyEntity;
        private long cvId;

        InsertTask(AddStudyActivity context, StudyEntity studyEntity, long cvId) {
            activityReference = new WeakReference<>(context);
            this.studyEntity = studyEntity;
            this.cvId = cvId;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            studyEntity.setCvId(cvId);

            long j = activityReference.get().appDatabase.studyDao().insert(studyEntity);
            studyEntity.setId(j);
            Log.e("ID ", "doInBackground: " + j );
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(studyEntity, 1);
                activityReference.get().finish();
            }
        }
    }

}
