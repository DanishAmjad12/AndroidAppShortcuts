package com.appshortcuts;

import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.appshortcuts.utilities.PrefUtil;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "AppShortcut";
    private AppCompatButton buttonLogin;
    private AppCompatEditText editTextUsername, editTextPassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setUpReferences();
        setOnClickListener();
        if (PrefUtil.getPrefIsUserLogin(this)) {
            getDatFromBundle();
        }
    }

    //region General Helper method
    private void setUpReferences() {
        buttonLogin = (AppCompatButton) findViewById(R.id.buttonLogin);
        editTextUsername = (AppCompatEditText) findViewById(R.id.editTextUsername);
        editTextPassword = (AppCompatEditText) findViewById(R.id.editTextPassword);

    }

    private void setOnClickListener() {
        buttonLogin.setOnClickListener(this);
    }
    //endregion

    //region Helper method for Creating a App Shortcut
    @TargetApi(25)
    private void createShortcuts() {
        ShortcutManager sM = getSystemService(ShortcutManager.class);

        Intent intent1 = new Intent(getApplicationContext(), AddTask.class);
        intent1.setAction(Intent.ACTION_VIEW);

        ShortcutInfo shortcut1 = new ShortcutInfo.Builder(this, "shortcut")
                .setIntent(intent1)
                .setShortLabel(getString(R.string.add_task_shortcut_long_label1))
                .setLongLabel("Add new Task")
                .setShortLabel("Add Task")
                .setDisabledMessage("Login to open this")
                .setIcon(Icon.createWithResource(this, R.drawable.ic_add))
                .build();

        sM.setDynamicShortcuts(Arrays.asList(shortcut1));
    }
    //endregion

    //region Helper method to get the data from bundle
    private void getDatFromBundle() {
        Intent intent = getIntent();
        String action = intent.getAction();
        String type = intent.getType();

        if (Intent.ACTION_SEND.equals(action) && type != null) {
            if ("text/plain".equals(type)) {
                Log.d(TAG, "getDatFromBundle: " + "from Text/plain");
                handleSendText(intent);

            } else if (type.startsWith("image/")) {
                Log.d(TAG, "getDatFromBundle: " + "from image/");
                // Handle single image being sent
            }
        } else {
            Intent sendIntent = new Intent(this, AddTask.class);
            startActivity(sendIntent);
            finish();
        }
    }
    //endregion

    private void handleSendText(Intent intent) {
        String sharedText = intent.getStringExtra(Intent.EXTRA_TEXT);
        if (sharedText != null) {
            Log.d(TAG, "getDatFromBundle: " + "from Text/plain" + sharedText);
            Intent sendIntent = new Intent(this, AddTask.class);
            sendIntent.putExtra("Task", sharedText);
            startActivity(sendIntent);
            finish();
        }
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.buttonLogin:
                if (!TextUtils.isEmpty(editTextUsername.getText()) && !TextUtils.isEmpty(editTextPassword.getText())) {
                    if (editTextUsername.getText().toString().equals("test") && editTextPassword.getText().toString().equals("123")) {
                        PrefUtil.setPrefLoginSession(this, true);
                        createShortcuts();
                        Intent intent = new Intent(this, AddTask.class);
                        startActivity(intent);
                        finish();
                    } else {
                        Toast.makeText(this, "Wrong username or password", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(this, "Please first fill the required fields", Toast.LENGTH_SHORT).show();
                }

                break;
        }
    }

}
