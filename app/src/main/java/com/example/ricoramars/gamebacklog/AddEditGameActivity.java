package com.example.ricoramars.gamebacklog;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class AddEditGameActivity extends AppCompatActivity {
    public static final String EXTRA_ID =
            "com.example.ricoramars.gamebacklog.EXTRA_ID";
    public static final String EXTRA_NAME =
            "com.example.ricoramars.gamebacklog.EXTRA_NAME";
    public static final String EXTRA_PLATFORM =
            "com.example.ricoramars.gamebacklog.EXTRA_PLATFORM";
    public static final String EXTRA_NOTES =
            "com.example.ricoramars.gamebacklog.EXTRA_NOTES";
    public static final String EXTRA_STATUS =
            "com.example.ricoramars.gamebacklog.EXTRA_STATUS";

    private EditText editTextName;
    private EditText editTextPlatform;
    private EditText editTextNotes;
    private EditText editTextStatus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        editTextName = findViewById(R.id.titleTxt);
        editTextNotes = findViewById(R.id.txtNotes);
        editTextPlatform = findViewById(R.id.platformTxt);
        editTextStatus = findViewById(R.id.statusSpinner);

        //Set the title of the screen
        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_ID)) {
            setTitle("Edit Game");
            editTextName.setText(intent.getStringExtra(EXTRA_NAME));
            editTextPlatform.setText(intent.getStringExtra(EXTRA_PLATFORM));
            editTextNotes.setText(intent.getStringExtra(EXTRA_NOTES));
            editTextStatus.setText(intent.getStringExtra(EXTRA_STATUS));
        } else {
            setTitle("Add Game");
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.final_add_game);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = editTextName.getText().toString();
                String note = editTextNotes.getText().toString();
                String platform = editTextPlatform.getText().toString();
                String status = editTextStatus.getText().toString();

                if (name.trim().isEmpty() || note.trim().isEmpty() || platform.trim().isEmpty() || status.trim().isEmpty()) {
                    Toast.makeText(AddEditGameActivity.this, "Please fill in missing spaces", Toast.LENGTH_SHORT).show();
                    return;
                }
                    Intent data = new Intent();
                    data.putExtra(EXTRA_NAME, name);
                    data.putExtra(EXTRA_PLATFORM, platform);
                    data.putExtra(EXTRA_NOTES, note);
                    data.putExtra(EXTRA_STATUS, status);

                    int id = getIntent().getIntExtra(EXTRA_ID, -1);
                    if (id != -1) {
                        data.putExtra(EXTRA_ID, id);
                    }

                    setResult(RESULT_OK, data);
                    finish();
            }
        });
    }
}
