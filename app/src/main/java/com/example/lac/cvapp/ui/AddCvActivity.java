package com.example.lac.cvapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.AppDatabase;
import com.example.lac.cvapp.db.adapter.StudyListAdapter;
import com.example.lac.cvapp.db.entity.AddressEntity;
import com.example.lac.cvapp.db.entity.CvEntity;
import com.example.lac.cvapp.db.entity.StudyEntity;
import com.example.lac.cvapp.util.DateRoomConverter;

import java.lang.ref.WeakReference;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class AddCvActivity extends AppCompatActivity implements StudyListAdapter.OnStudyListItemClick{

    private AppDatabase appDatabase;

    private RecyclerView recyclerView;
    private StudyListAdapter studyListAdapter;
    private List<StudyEntity> studies;

    private DateRoomConverter dateRoomConverter;
    private CvEntity cvEntity;
    private AddressEntity addressEntity;

    private TextInputEditText etFirstName, etLastName, etCountry, etZipCode, etState, etCity, etStreet, etHouseNumber,
            etPhoneNumber, etEmailAddress, etGender, etBirthDate, etNationality, etNativeLanguage;

    private int pos;
    private boolean update;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_cv);
        etFirstName = findViewById(R.id.et_first_name);
        etLastName = findViewById(R.id.et_last_name);
        etCountry = findViewById(R.id.et_country);
        etZipCode = findViewById(R.id.et_zip_code);
        etState = findViewById(R.id.et_state);
        etCity = findViewById(R.id.et_city);
        etStreet = findViewById(R.id.et_street);
        etHouseNumber = findViewById(R.id.et_house_number);
        etPhoneNumber = findViewById(R.id.et_phone_number);
        etEmailAddress = findViewById(R.id.et_email_address);
        etGender = findViewById(R.id.et_gender);
        etBirthDate = findViewById(R.id.et_birth_date);
        etNationality = findViewById(R.id.et_nationality);
        etNativeLanguage = findViewById(R.id.et_native_language);

        appDatabase = AppDatabase.getInstance(AddCvActivity.this);

        Button button = findViewById(R.id.button);
        if ( (cvEntity = (CvEntity) getIntent().getSerializableExtra("cv")) != null ){
            getSupportActionBar().setTitle("Update CV");
            update = true;
            button.setText("Update");

            AddressEntity updateAddressEntity = appDatabase.addressDao().findById(cvEntity.getId());

            etFirstName.setText(cvEntity.getFirstName());
            etLastName.setText(cvEntity.getLastName());
            etCountry.setText(updateAddressEntity.getCountry());
            etZipCode.setText(updateAddressEntity.getZipCode());
            etState.setText(updateAddressEntity.getState());
            etCity.setText(updateAddressEntity.getCity());
            etStreet.setText(updateAddressEntity.getStreet());
            etHouseNumber.setText(updateAddressEntity.getHouseNumber());
            etPhoneNumber.setText(cvEntity.getPhoneNumber());
            etEmailAddress.setText(cvEntity.getEmailAddress());
            etGender.setText(cvEntity.getGender());

            DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
            String birthDate = "";
            try {
                birthDate =  format.format(cvEntity.getBirthDate());
            } catch (Exception e) {
                Log.getStackTraceString(e);
            }

            etBirthDate.setText(birthDate);
            etNationality.setText(cvEntity.getNationality());
            etNativeLanguage.setText(cvEntity.getNativeLanguage());
        }
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (update){
                    cvEntity.setFirstName(etFirstName.getText().toString());
                    cvEntity.setLastName(etLastName.getText().toString());
                    cvEntity.setPhoneNumber(etPhoneNumber.getText().toString());
                    cvEntity.setEmailAddress(etEmailAddress.getText().toString());
                    cvEntity.setGender(etGender.getText().toString());

                    DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
                    Date birthDate = new Date();
                    try {
                        birthDate =  format.parse(etBirthDate.getText().toString());
                    } catch (Exception e) {
                        Log.getStackTraceString(e);
                    }

                    cvEntity.setBirthDate(birthDate);
                    cvEntity.setNationality(etNationality.getText().toString());
                    cvEntity.setNativeLanguage(etNativeLanguage.getText().toString());

                    addressEntity.setCountry(etCountry.getText().toString());
                    addressEntity.setZipCode(etZipCode.getText().toString());
                    addressEntity.setState(etState.getText().toString());
                    addressEntity.setCity(etCity.getText().toString());
                    addressEntity.setStreet(etStreet.getText().toString());
                    addressEntity.setHouseNumber(etHouseNumber.getText().toString());

                    appDatabase.addressDao().update(addressEntity);
                    appDatabase.cvDao().update(cvEntity);
                    setResult(cvEntity, 2);
                } else {
                    addressEntity = new AddressEntity(etCountry.getText().toString(), etZipCode.getText().toString(),
                            etState.getText().toString(), etCity.getText().toString(),
                            etStreet.getText().toString(), etHouseNumber.getText().toString());

                    DateFormat format = new SimpleDateFormat("yyyy.MM.dd.", Locale.ENGLISH);
                    Date birthDate = new Date();
                    try {
                        birthDate =  format.parse(etBirthDate.getText().toString());
                    } catch (Exception e) {
                        Log.getStackTraceString(e);
                    }

                    cvEntity = new CvEntity(etFirstName.getText().toString(), etLastName.getText().toString(),
                            etPhoneNumber.getText().toString(), etEmailAddress.getText().toString(),
                            etGender.getText().toString(), birthDate,
                            etNationality.getText().toString(), etNativeLanguage.getText().toString());
                    new InsertTask(AddCvActivity.this, cvEntity, addressEntity, studies).execute();
                }
            }
        });

        initializeViews();
        displayList();
    }

    private void initializeViews(){
        ImageButton ibAddStudy = (ImageButton) findViewById(R.id.imageButtonStudy);
        ibAddStudy.setOnClickListener(listener);

        recyclerView = findViewById(R.id.rv_study);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddCvActivity.this));
        studies = new ArrayList<>();
        studyListAdapter = new StudyListAdapter(studies,AddCvActivity.this);
        recyclerView.setAdapter(studyListAdapter);
    }

    private void displayList(){
        if (cvEntity != null) {
            new RetrieveTask(this, cvEntity.getId()).execute();
        } else {
            new RetrieveTask(this).execute();
        }
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<StudyEntity>>{

        private WeakReference<AddCvActivity> activityReference;
        private long cvId;

        RetrieveTask(AddCvActivity context, long cvId) {
            activityReference = new WeakReference<>(context);
            this.cvId = cvId;
        }

        RetrieveTask(AddCvActivity context) {
            activityReference = new WeakReference<>(context);
            cvId = -1;
        }

        @Override
        protected List<StudyEntity> doInBackground(Void... voids) {
            if (activityReference.get() != null && cvId != -1)
                return activityReference.get().appDatabase.studyDao().findStudiesForCv(cvId);
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<StudyEntity> studies) {
            if (studies != null && studies.size() > 0 ){
                activityReference.get().studies.clear();
                activityReference.get().studies.addAll(studies);
                activityReference.get().studyListAdapter.notifyDataSetChanged();
            }
        }
    }

    private void setResult(CvEntity cv, int flag){
        setResult(flag,new Intent().putExtra("cv", cv));
        finish();
    }

    @Override
    public void onStudyClick(final int pos) {
        new AlertDialog.Builder(AddCvActivity.this)
                .setTitle("Select Options")
                .setItems(new String[]{"Update", "Delete"}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                AddCvActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(AddCvActivity.this,
                                                AddStudyActivity.class).putExtra("study", studies.get(pos)),
                                        100);

                                break;
                            case 1:
                                if (studies.get(pos).getId() > 0) {
                                    appDatabase.studyDao().delete(studies.get(pos));
                                }
                                studies.remove(pos);
                                listVisibility();
                                break;
                        }
                    }
                }).show();

    }

    private static class InsertTask extends AsyncTask<Void, Void, Boolean> {

        private WeakReference<AddCvActivity> activityReference;
        private CvEntity cvEntity;
        private AddressEntity addressEntity;
        private List<StudyEntity> studies;

        InsertTask(AddCvActivity context, CvEntity cvEntity, AddressEntity addressEntity,
                   List<StudyEntity> studies) {
            activityReference = new WeakReference<>(context);
            this.cvEntity = cvEntity;
            this.addressEntity = addressEntity;
            this.studies = studies;
        }

        @Override
        protected Boolean doInBackground(Void... objs) {
            cvEntity.setAddressId(activityReference.get().appDatabase.addressDao().insert(addressEntity));

            long j = activityReference.get().appDatabase.cvDao().insert(cvEntity);
            cvEntity.setId(j);

            for (StudyEntity studyEntity : studies) {
                studyEntity.setCvId(j);
                activityReference.get().appDatabase.studyDao().insert(studyEntity);
            }

            Log.e("ID ", "doInBackground: " + j );
            return true;
        }

        @Override
        protected void onPostExecute(Boolean bool) {
            if (bool){
                activityReference.get().setResult(cvEntity, 1);
                activityReference.get().finish();
            }
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(
                    new Intent(AddCvActivity.this,
                            AddStudyActivity.class).putExtra("cv", cvEntity),
                    100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                studies.add((StudyEntity) data.getSerializableExtra("study"));
            }else if( resultCode == 2){
                studies.set(pos,(StudyEntity) data.getSerializableExtra("study"));
            }
            listVisibility();
        }
    }

    private void listVisibility(){
        studyListAdapter.notifyDataSetChanged();
    }

    public void onAddressClick(View view) {
        LinearLayout llAddressFields = findViewById(R.id.ll_address_fields);

        if (llAddressFields.getVisibility() == VISIBLE) {
            llAddressFields.setVisibility(GONE);
        } else {
            llAddressFields.setVisibility(VISIBLE);
        }
    }

    @Override
    protected void onDestroy() {
        appDatabase.cleanUp();
        super.onDestroy();
    }
}
