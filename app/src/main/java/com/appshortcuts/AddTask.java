package com.appshortcuts;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatTextView;
import android.util.Log;
import android.view.View;

import com.appshortcuts.models.AddTasks;
import com.appshortcuts.utilities.PrefUtil;

import java.util.Arrays;

import io.realm.Realm;

/**
 * Created by daamjad on 3/28/2017.
 */

public class AddTask extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AppShortcut";

    private AppCompatButton buttonLogout;
    private AppCompatTextView textViewAddedTasks;
    private Intent intent;
    private Realm realm;
    private String getTasks;
    private String tasks;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_task);

        setUpReferences();
        setOnClickListener();
        getDataFromBundle();
    }

    //region General Helper method
    private void setUpReferences() {
        buttonLogout = (AppCompatButton) findViewById(R.id.buttonLogout);
        textViewAddedTasks = (AppCompatTextView) findViewById(R.id.textViewAddedTask);
        realm = Realm.getDefaultInstance();
    }

    private void setOnClickListener() {
        buttonLogout.setOnClickListener(this);
    }
    //endregion

    //region Helper method to get the data from bundle
    private void getDataFromBundle() {
        intent = getIntent();
        if (intent != null) {
            tasks = (intent.getStringExtra("Task"));
            Log.d(TAG, "getDataFromBundle: " + tasks);
            addTaskInToDb();
            getTask();
            showAddedTaskInView();

        }
    }
    //endregion

    private void showAddedTaskInView() {
        textViewAddedTasks.setText(tasks);
    }

    private void addTaskInToDb() {
        AddTasks addTasks = new AddTasks();
        addTasks.setTask(tasks);

        realm.beginTransaction();
        realm.copyToRealm(addTasks);
        realm.commitTransaction();
    }

    private String getTask() {
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                AddTasks addTasks = realm.where(AddTasks.class).findFirst();
                if (addTasks != null)
                    getTasks = addTasks.getTask();
            }
        });
        return getTasks;
    }


    //region Helper method for removeShortcuts
    @TargetApi(25)
    private void removeShortcuts() {
        ShortcutManager shortcutManager = getSystemService(ShortcutManager.class);
        shortcutManager.disableShortcuts(Arrays.asList("shortcut"));
        shortcutManager.removeAllDynamicShortcuts();
    }
    //endregion

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogout:
                removeShortcuts();
                PrefUtil.clearAllSharedPreference(this);
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
