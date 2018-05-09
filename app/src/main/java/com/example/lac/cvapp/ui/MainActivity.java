package com.example.lac.cvapp.ui;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.lac.cvapp.R;
import com.example.lac.cvapp.db.AppDatabase;
import com.example.lac.cvapp.db.adapter.CvListAdapter;
import com.example.lac.cvapp.db.entity.CvEntity;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CvListAdapter.OnCvListItemClick{

    /*private TextView textViewMsg;*/
    private AppDatabase appDatabase;

    private SearchView searchView;
    private RecyclerView recyclerView;
    private CvListAdapter cvListAdapter;
    private List<CvEntity> cvs;
    private List<CvEntity> queriedCvs;

    private int pos;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeViews();
        displayList();
    }

    private void initializeViews(){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        /*textViewMsg =  (TextView) findViewById(R.id.tv__empty);*/
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(listener);

        queriedCvs = new ArrayList<>();

        recyclerView = findViewById(R.id.rv_cv);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        cvs = new ArrayList<>();
        cvListAdapter = new CvListAdapter(cvs,MainActivity.this);
        recyclerView.setAdapter(cvListAdapter);
    }

    private void displayList(){
        appDatabase = AppDatabase.getInstance(MainActivity.this);
        new RetrieveTask(this).execute();
    }

    private static class RetrieveTask extends AsyncTask<Void,Void,List<CvEntity>>{

        private WeakReference<MainActivity> activityReference;

        RetrieveTask(MainActivity context) {
            activityReference = new WeakReference<>(context);
        }

        @Override
        protected List<CvEntity> doInBackground(Void... voids) {
            if (activityReference.get() != null)
                return activityReference.get().appDatabase.cvDao().getAll();
            else
                return null;
        }

        @Override
        protected void onPostExecute(List<CvEntity> cvs) {
            if (cvs != null && cvs.size() > 0 ){
                activityReference.get().cvs.clear();
                activityReference.get().cvs.addAll(cvs);

                //activityReference.get().textViewMsg.setVisibility(View.GONE);
                activityReference.get().cvListAdapter.notifyDataSetChanged();
            }
        }
    }

    private View.OnClickListener listener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            startActivityForResult(new Intent(MainActivity.this, AddCvActivity.class),100);
        }
    };

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 100 && resultCode > 0 ){
            if( resultCode == 1){
                cvs.add((CvEntity) data.getSerializableExtra("cv"));
            }else if( resultCode == 2){
                cvs.set(pos,(CvEntity) data.getSerializableExtra("cv"));
            }
            listVisibility();
        }
    }

    @Override
    public void onCvClick(final int pos) {
        new AlertDialog.Builder(MainActivity.this)
                .setItems(new String[]{getResources().getString(R.string.text_update),
                        getResources().getString(R.string.text_delete)}, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        switch (i){
                            case 0:
                                MainActivity.this.pos = pos;
                                startActivityForResult(
                                        new Intent(MainActivity.this,
                                                AddCvActivity.class).putExtra("cv", cvs.get(pos)),
                                        100);
                                break;
                            case 1:
                                appDatabase.cvDao().delete(cvs.get(pos));
                                cvs.remove(pos);
                                listVisibility();
                                break;
                        }
                    }
                }).show();

    }

    private void listVisibility(){
        /*int emptyMsgVisibility = View.GONE;
        if (cvs.size() == 0){ // no item to display
            if (textViewMsg.getVisibility() == View.GONE)
                emptyMsgVisibility = View.VISIBLE;
        }
        textViewMsg.setVisibility(emptyMsgVisibility);*/
        cvListAdapter.notifyDataSetChanged();
    }

    /*@Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        MenuItem myActionMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) myActionMenuItem.getActionView();
        searchView.setOnQueryTextListener(onQueryTextListener);

        return true;
    }

    private SearchView.OnQueryTextListener onQueryTextListener =
            new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    getCvsFromDb(query);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    if ("".equals(newText)) {
                        cvs.clear();
                        cvs.addAll(appDatabase.cvDao().getAll());
                        listVisibility();
                    }
                    return false;
                }

                private void getCvsFromDb(String searchText) {
                    if ("".equals(searchText)) {
                        cvs.clear();
                        cvs.addAll(appDatabase.cvDao().getAll());
                    } else {
                        searchText = "%" + searchText + "%";

                        queriedCvs = appDatabase.cvDao().searchByQuery(searchText);

                        cvs.clear();
                        cvs.addAll(queriedCvs);
                    }
                    listVisibility();
                }
            };*/

    @Override
    protected void onDestroy() {
        appDatabase.cleanUp();
        super.onDestroy();
    }

}
