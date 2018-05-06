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
import com.example.lac.cvapp.db.entity.ExperienceEntity;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddExperienceActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private ExperienceEntity experienceEntity;
    private CvEntity cvEntity;

    private TextInputEditText etPosition, etCompany, etCountry, etCity;
    private EditText etStartDate, etEndDate;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);

        appDatabase = AppDatabase.getInstance(AddExperienceActivity.this);

        etPosition = findViewById(R.id.et_position);
        etCompany = findViewById(R.id.et_company);
        etCountry = findViewById(R.id.et_country);
        etCity = findViewById(R.id.et_city);
        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);

        Button button = findViewById(R.id.button);
        if ( (experienceEntity = (ExperienceEntity) getIntent().getSerializableExtra("experience")) != null ){
            getSupportActionBar().setTitle("Update experience");
            update = true;
            button.setText("Update");
            etPosition.setText(experienceEntity.getPosition());
            etCompany.setText(experienceEntity.getCompany());
            etCountry.setText(experienceEntity.getCountry());
            etCity.setText(experienceEntity.getCity());

            DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
            String startDate = "";
            String endDate = "";
            try {
                startDate =  format.format(experienceEntity.getStartDate());
                endDate =  format.format(experienceEntity.getEndDate());
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }

            etStartDate.setText(startDate);
            etEndDate.setText(endDate);
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    experienceEntity.setPosition(etPosition.getText().toString());
                    experienceEntity.setCompany(etCompany.getText().toString());
                    experienceEntity.setCountry(etCountry.getText().toString());
                    experienceEntity.setCity(etCity.getText().toString());

                    DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
                    Date startDate = new Date();
                    Date endDate = new Date();
                    try {
                        startDate =  format.parse(etStartDate.getText().toString());
                        endDate =  format.parse(etEndDate.getText().toString());
                    } catch (Exception e) {
                        Log.getStackTraceString(e);
                    }

                    experienceEntity.setStartDate(startDate);
                    experienceEntity.setEndDate(endDate);

                    if (experienceEntity.getId() > 0) {
                        appDatabase.experienceDao().update(experienceEntity);
                    }

                    setResult(experienceEntity, 2);
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

                    experienceEntity = new ExperienceEntity(etPosition.getText().toString(), etCompany.getText().toString(),
                            etCountry.getText().toString(), etCity.getText().toString(),
                            startDate, endDate);

                    if ( (cvEntity = (CvEntity) getIntent().getSerializableExtra("cv")) != null
                            && (cvEntity.getId() > 0) ){
                        new InsertTask(AddExperienceActivity.this, experienceEntity, cvEntity.getId()).execute();
                    }
                    setResult(experienceEntity, 1);
                }
            }
        });
    }

    private void setResult(ExperienceEntity experience, int flag){
        setResult(flag, new Intent().putExtra("experience", experience));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddExperienceActivity> activityReference;
        private ExperienceEntity experienceEntity;
        private long cvId;

        InsertTask(AddExperienceActivity context, ExperienceEntity experienceEntity, long cvId) {
            activityReference = new WeakReference<>(context);
            this.experienceEntity = experienceEntity;
            this.cvId = cvId;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            experienceEntity.setCvId(cvId);

            long j = activityReference.get().appDatabase.experienceDao().insert(experienceEntity);
            experienceEntity.setId(j);
            Log.e("ID ", "doInBackground: " + j );
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(experienceEntity, 1);
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
