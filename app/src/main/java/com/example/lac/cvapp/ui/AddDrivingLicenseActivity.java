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
import com.example.lac.cvapp.db.entity.DrivingLicenseEntity;

import java.lang.ref.WeakReference;

public class AddDrivingLicenseActivity extends AppCompatActivity {

    private AppDatabase appDatabase;

    private DrivingLicenseEntity drivingLicenseEntity;
    private CvEntity cvEntity;

    private TextInputEditText etType;

    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_driving_license);

        appDatabase = AppDatabase.getInstance(AddDrivingLicenseActivity.this);

        etType = findViewById(R.id.et_type);

        Button button = findViewById(R.id.button);
        if ( (drivingLicenseEntity = (DrivingLicenseEntity) getIntent().getSerializableExtra("driving_license")) != null ){
            getSupportActionBar().setTitle("Update driving license");
            update = true;
            button.setText("Update");
            etType.setText(drivingLicenseEntity.getType());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    drivingLicenseEntity.setType(etType.getText().toString());

                    if (drivingLicenseEntity.getId() > 0) {
                        appDatabase.drivingLicenseDao().update(drivingLicenseEntity);
                    }

                    setResult(drivingLicenseEntity, 2);
                } else {
                    drivingLicenseEntity = new DrivingLicenseEntity(etType.getText().toString());

                    if ( (cvEntity = (CvEntity) getIntent().getSerializableExtra("cv")) != null
                            && (cvEntity.getId() > 0) ){
                        new InsertTask(AddDrivingLicenseActivity.this, drivingLicenseEntity, cvEntity.getId()).execute();
                    }
                    setResult(drivingLicenseEntity, 1);
                }
            }
        });
    }

    private void setResult(DrivingLicenseEntity drivingLicense, int flag){
        setResult(flag, new Intent().putExtra("driving_license", drivingLicense));
        finish();
    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddDrivingLicenseActivity> activityReference;
        private DrivingLicenseEntity drivingLicenseEntity;
        private long cvId;

        InsertTask(AddDrivingLicenseActivity context, DrivingLicenseEntity drivingLicenseEntity, long cvId) {
            activityReference = new WeakReference<>(context);
            this.drivingLicenseEntity = drivingLicenseEntity;
            this.cvId = cvId;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            drivingLicenseEntity.setCvId(cvId);

            long j = activityReference.get().appDatabase.drivingLicenseDao().insert(drivingLicenseEntity);
            drivingLicenseEntity.setId(j);
            Log.e("ID ", "doInBackground: " + j );
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(drivingLicenseEntity, 1);
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
