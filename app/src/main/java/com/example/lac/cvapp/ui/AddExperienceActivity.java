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
import com.example.lac.cvapp.db.entity.ExperienceEntity;
import com.example.lac.cvapp.util.DateStringConverter;

import java.lang.ref.WeakReference;

public class AddExperienceActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private DateStringConverter dateStringConverter;

    private ExperienceEntity experienceEntity;
    private CvEntity cvEntity;

    private TextInputEditText etPosition, etCompany, etCountry, etCity, etStartDate, etEndDate;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_experience);

        appDatabase = AppDatabase.getInstance(AddExperienceActivity.this);

        dateStringConverter = new DateStringConverter();

        etPosition = findViewById(R.id.et_position);
        etCompany = findViewById(R.id.et_company);
        etCountry = findViewById(R.id.et_country);
        etCity = findViewById(R.id.et_city);
        etStartDate = findViewById(R.id.et_start_date);
        etEndDate = findViewById(R.id.et_end_date);

        Button button = findViewById(R.id.button);
        if ( (experienceEntity = (ExperienceEntity) getIntent().getSerializableExtra("experience")) != null ){
            getSupportActionBar().setTitle(getResources().getString(R.string.label_update_experience));
            update = true;
            button.setText(getResources().getString(R.string.button_update));
            etPosition.setText(experienceEntity.getPosition());
            etCompany.setText(experienceEntity.getCompany());
            etCountry.setText(experienceEntity.getCountry());
            etCity.setText(experienceEntity.getCity());
            etStartDate.setText(dateStringConverter.dateToString(experienceEntity.getStartDate(), "yyyy.MM.dd."));
            etEndDate.setText(dateStringConverter.dateToString(experienceEntity.getEndDate(), "yyyy.MM.dd."));
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    experienceEntity.setPosition(etPosition.getText().toString());
                    experienceEntity.setCompany(etCompany.getText().toString());
                    experienceEntity.setCountry(etCountry.getText().toString());
                    experienceEntity.setCity(etCity.getText().toString());
                    experienceEntity.setStartDate(dateStringConverter.stringToDate(etStartDate.getText().toString(), "yyyy.MM.dd."));
                    experienceEntity.setEndDate(dateStringConverter.stringToDate(etEndDate.getText().toString(), "yyyy.MM.dd."));

                    if (experienceEntity.getId() > 0) {
                        appDatabase.experienceDao().update(experienceEntity);
                    }

                    setResult(experienceEntity, 2);
                } else {
                    experienceEntity = new ExperienceEntity(etPosition.getText().toString(), etCompany.getText().toString(),
                            etCountry.getText().toString(), etCity.getText().toString(),
                            dateStringConverter.stringToDate(etStartDate.getText().toString(), "yyyy.MM.dd."),
                            dateStringConverter.stringToDate(etEndDate.getText().toString(), "yyyy.MM.dd."));

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
