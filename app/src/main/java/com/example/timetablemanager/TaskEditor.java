package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class TaskEditor extends AppCompatActivity {


    private EditText etTask;
    private TextView btnDelete, tvFrom, tvClickHere1, tvColor, tvClickHere2, tvColorSpinner;
    private Spinner sColor;

    private Button tvSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);
        setUI();
    }

    private void setUI() {
        etTask = findViewById(R.id.etTask);

        tvFrom = findViewById(R.id.tvFrom);

        tvClickHere1 = findViewById(R.id.tvClickHere1);
        tvClickHere2 = findViewById(R.id.tvClickHere2);

        tvColor = findViewById(R.id.tvColor);
        tvColorSpinner = findViewById(R.id.tvColorSpinner);

        sColor = findViewById(R.id.sColor);

        btnDelete = findViewById(R.id.tvDelete);
        tvSubmit = findViewById(R.id.tvSubmit);
    }
}