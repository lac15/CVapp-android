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
import com.example.lac.cvapp.db.adapter.DrivingLicenseListAdapter;
import com.example.lac.cvapp.db.adapter.LanguageListAdapter;
import com.example.lac.cvapp.db.adapter.StudyListAdapter;
import com.example.lac.cvapp.db.entity.AddressEntity;
import com.example.lac.cvapp.db.entity.CvEntity;
import com.example.lac.cvapp.db.entity.DrivingLicenseEntity;
import com.example.lac.cvapp.db.entity.LanguageEntity;
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

public class AddCvActivity extends AppCompatActivity implements StudyListAdapter.OnStudyListItemClick,
        DrivingLicenseListAdapter.OnDrivingLicenseListItemClick, LanguageListAdapter.OnLanguageListItemClick {

    private AppDatabase appDatabase;

    private RecyclerView recyclerView;
    private RecyclerView recyclerDrivingLicenseView;
    private RecyclerView recyclerLanguageView;
    private StudyListAdapter studyListAdapter;
    private DrivingLicenseListAdapter drivingLicenseListAdapter;
    private LanguageListAdapter languageListAdapter;
    private List<StudyEntity> studies;
    private List<DrivingLicenseEntity> drivingLicenses;
    private List<LanguageEntity> languages;

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
                    new InsertTask(AddCvActivity.this, cvEntity, addressEntity, studies, drivingLicenses, languages).execute();
                }
            }
        });

        initializeViews();
        displayList();
    }

    private void initializeViews(){
        ImageButton ibAddStudy = (ImageButton) findViewById(R.id.imageButtonStudy);
        ibAddStudy.setOnClickListener(listener);

        ImageButton ibAddDrivingLicense = (ImageButton) findViewById(R.id.imageButtonDrivingLicense);
        ibAddDrivingLicense.setOnClickListener(drivingLicenseListener);

        ImageButton ibAddLanguage = (ImageButton) findViewById(R.id.imageButtonLanguage);
        ibAddLanguage.setOnClickListener(languageListener);

        recyclerView = findViewById(R.id.rv_study);
        recyclerDrivingLicenseView = findViewById(R.id.rv_driving_license);
        recyclerLanguageView = findViewById(R.id.rv_language);
        recyclerView.setLayoutManager(new LinearLayoutManager(AddCvActivity.this));
        recyclerDrivingLicenseView.setLayoutManager(new LinearLayoutManager(AddCvActivity.this));
        recyclerLanguageView.setLayoutManager(new LinearLayoutManager(AddCvActivity.this));
        studies = new ArrayList<>();
        drivingLicenses = new ArrayList<>();
        languages = new ArrayList<>();
        studyListAdapter = new StudyListAdapter(studies,AddCvActivity.this);
        drivingLicenseListAdapter = new DrivingLicenseListAdapter(drivingLicenses,AddCvActivity.this);
        languageListAdapter = new LanguageListAdapter(languages,AddCvActivity.this);
        recyclerView.setAdapter(studyListAdapter);
        recyclerDrivingLicenseView.setAdapter(drivingLicenseListAdapter);
        recyclerLanguageView.setAdapter(languageListAdapter);
    }

    private void displayList(){
        if (cvEntity != null) {
            new RetrieveTask(this, cvEntity.getId()).execute();
            new RetrieveDrivingLicenseTask(this, cvEntity.getId()).execute();
            new RetrieveLanguageTask(this, cvEntity.getId()).execute();
        } else {
            new RetrieveTask(this).execute();
            new RetrieveDrivingLicenseTask(this).execute();
            new RetrieveLanguageTask(this).execute();
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

    private static class RetrieveDrivingLicenseTask extends AsyncTask<Void,Void,List<DrivingLicenseEntity>>{

        private WeakReference<AddCvActivity> activityReference;
        private long cvId;

        RetrieveDrivingLicenseTask(AddCvActivity context, long cvId) {
            activityReference = new WeakReference<>(context);
            this.cvId = cvId;
        }

        RetrieveDrivingLicenseTask(AddCvActivity context) {
            activityReference = new WeakReference<>(context);
            cvId = -1;
        }

        @Override
        protected List<DrivingLicenseEntity> doInBackground(Void... voids) {
            if (activityReference.get() != null && cvId != -1)
                return activityReference.get().appDatabase.drivingLicenseDao().findDrivingLicensesForCv(cvId);
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<DrivingLicenseEntity> drivingLicenses) {
            if (drivingLicenses != null && drivingLicenses.size() > 0 ){
                activityReference.get().drivingLicenses.clear();
                activityReference.get().drivingLicenses.addAll(drivingLicenses);
                activityReference.get().drivingLicenseListAdapter.notifyDataSetChanged();
            }
        }
    }

    private static class RetrieveLanguageTask extends AsyncTask<Void,Void,List<LanguageEntity>>{

        private WeakReference<AddCvActivity> activityReference;
        private long cvId;

        RetrieveLanguageTask(AddCvActivity context, long cvId) {
            activityReference = new WeakReference<>(context);
            this.cvId = cvId;
        }

        RetrieveLanguageTask(AddCvActivity context) {
            activityReference = new WeakReference<>(context);
            cvId = -1;
        }

        @Override
        protected List<LanguageEntity> doInBackground(Void... voids) {
            if (activityReference.get() != null && cvId != -1)
                return activityReference.get().appDatabase.languageDao().findLanguagesForCv(cvId);
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<LanguageEntity> languages) {
            if (languages != null && languages.size() > 0 ){
                activityReference.get().languages.clear();
                activityReference.get().languages.addAll(languages);
                activityReference.get().languageListAdapter.notifyDataSetChanged();
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

    @Override
    public void onDrivingLicenseClick(final int pos) {
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
                                                AddDrivingLicenseActivity.class).putExtra("driving_license", drivingLicenses.get(pos)),
                                        200);

                                break;
                            case 1:
                                if (drivingLicenses.get(pos).getId() > 0) {
                                    appDatabase.drivingLicenseDao().delete(drivingLicenses.get(pos));
                                }
                                drivingLicenses.remove(pos);
                                listDrivingLicenseVisibility();
                                break;
                        }
                    }
                }).show();

    }

    @Override
    public void onLanguageClick(final int pos) {
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
                                                AddLanguageActivity.class).putExtra("language", languages.get(pos)),
                                        300);

                                break;
                            case 1:
                                if (languages.get(pos).getId() > 0) {
                                    appDatabase.languageDao().delete(languages.get(pos));
                                }
                                languages.remove(pos);
                                listLanguageVisibility();
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
        private List<DrivingLicenseEntity> drivingLicenses;
        private List<LanguageEntity> languages;

        InsertTask(AddCvActivity context, CvEntity cvEntity, AddressEntity addressEntity,
                   List<StudyEntity> studies, List<DrivingLicenseEntity> drivingLicenses,
                   List<LanguageEntity> languages) {
            activityReference = new WeakReference<>(context);
            this.cvEntity = cvEntity;
            this.addressEntity = addressEntity;
            this.studies = studies;
            this.drivingLicenses = drivingLicenses;
            this.languages = languages;
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

            for (DrivingLicenseEntity drivingLicenseEntity : drivingLicenses) {
                drivingLicenseEntity.setCvId(j);
                activityReference.get().appDatabase.drivingLicenseDao().insert(drivingLicenseEntity);
            }

            for (LanguageEntity languageEntity : languages) {
                languageEntity.setCvId(j);
                activityReference.get().appDatabase.languageDao().insert(languageEntity);
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

    private View.OnClickListener drivingLicenseListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(
                    new Intent(AddCvActivity.this,
                            AddDrivingLicenseActivity.class).putExtra("cv", cvEntity),
                    200);
        }
    };

    private View.OnClickListener languageListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(
                    new Intent(AddCvActivity.this,
                            AddLanguageActivity.class).putExtra("cv", cvEntity),
                    300);
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
        if (requestCode == 200 && resultCode > 0 ){
            if( resultCode == 1){
                drivingLicenses.add((DrivingLicenseEntity) data.getSerializableExtra("driving_license"));
            }else if( resultCode == 2){
                drivingLicenses.set(pos,(DrivingLicenseEntity) data.getSerializableExtra("driving_license"));
            }
            listDrivingLicenseVisibility();
        }
        if (requestCode == 300 && resultCode > 0 ){
            if( resultCode == 1){
                languages.add((LanguageEntity) data.getSerializableExtra("language"));
            }else if( resultCode == 2){
                languages.set(pos,(LanguageEntity) data.getSerializableExtra("language"));
            }
            listLanguageVisibility();
        }
    }

    private void listVisibility(){
        studyListAdapter.notifyDataSetChanged();
    }

    private void listDrivingLicenseVisibility(){
        drivingLicenseListAdapter.notifyDataSetChanged();
    }

    private void listLanguageVisibility(){
        languageListAdapter.notifyDataSetChanged();
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
