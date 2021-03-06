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
import com.example.lac.cvapp.db.entity.StudyEntity;
import com.example.lac.cvapp.util.DateStringConverter;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddStudyActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private DateStringConverter dateStringConverter;

    private StudyEntity studyEntity;
    private CvEntity cvEntity;

    private TextInputEditText etName, etSchool, etCountry, etCity, etStartDate, etEndDate, etDescription;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_study);

        appDatabase = AppDatabase.getInstance(AddStudyActivity.this);

        dateStringConverter = new DateStringConverter();

        etName = findViewById(R.id.et_name);
        etSchool = findViewById(R.id.et_school);
        etCountry = findViewById(R.id.et_country);
        etCity = findViewById(R.id.et_city);
        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);
        etDescription = findViewById(R.id.et_description);

        Button button = findViewById(R.id.button);
        if ( (studyEntity = (StudyEntity) getIntent().getSerializableExtra("study")) != null ){
            update = true;
            button.setText(getResources().getString(R.string.button_update));
            etName.setText(studyEntity.getName());
            etSchool.setText(studyEntity.getSchool());
            etCountry.setText(studyEntity.getCountry());
            etCity.setText(studyEntity.getCity());
            etStartDate.setText(dateStringConverter.dateToString(studyEntity.getStartDate(), "yyyy.MM.dd."));
            etEndDate.setText(dateStringConverter.dateToString(studyEntity.getEndDate(), "yyyy.MM.dd."));
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
                    studyEntity.setStartDate(dateStringConverter.stringToDate(etStartDate.getText().toString(), "yyyy.MM.dd."));
                    studyEntity.setEndDate(dateStringConverter.stringToDate(etEndDate.getText().toString(), "yyyy.MM.dd."));
                    studyEntity.setDescription(etDescription.getText().toString());

                    if (studyEntity.getId() > 0) {
                        appDatabase.studyDao().update(studyEntity);
                    }

                    setResult(studyEntity, 2);
                } else {
                    studyEntity = new StudyEntity(etName.getText().toString(), etSchool.getText().toString(),
                            etCountry.getText().toString(), etCity.getText().toString(),
                            dateStringConverter.stringToDate(etStartDate.getText().toString(), "yyyy.MM.dd."),
                            dateStringConverter.stringToDate(etEndDate.getText().toString(), "yyyy.MM.dd."),
                            etDescription.getText().toString());

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

    @Override
    protected void onDestroy() {
        appDatabase.cleanUp();
        super.onDestroy();
    }

}
