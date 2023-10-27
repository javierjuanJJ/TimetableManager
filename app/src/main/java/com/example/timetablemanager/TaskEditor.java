package com.example.timetablemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.timetablemanager.Constants.TanleConstants;
import com.example.timetablemanager.Pickers.OwnTimePickerDialog;
import com.example.timetablemanager.Pickers.onDateResultPickerDialog;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TaskEditor extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener, onDateResultPickerDialog {


    private EditText etTask;
    private TextView btnDelete, tvFrom, tvClickHere1, tvColor, tvClickHere2, tvColorSpinner;
    private Spinner sColor;

    private Button tvSubmit;
    private Database database;
    private Task task;
    private String date;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_editor);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUI();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
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

        database = new Database(this);
        task = new Task();

        date = getIntent().getStringExtra(TanleConstants.DATE_NAME);
        String[] colors = getResources().getStringArray(R.array.colors_name);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        sColor.setAdapter(adapter);

        if (getIntent().hasExtra(TanleConstants.TASK_NAME)){
            task = (Task) getIntent().getSerializableExtra(TanleConstants.TASK_OBJECT_NAME);
            sColor.setSelection(adapter.getPosition(task.getColor()));

            GradientDrawable background = (GradientDrawable) tvColor.getBackground();
            background.setColor(task.getColorId(TaskEditor.this));

            etTask.setText(task.getTask());
            tvClickHere1.setText(task.getFromToString());
            tvClickHere2.setText(task.getToString());

            btnDelete.setVisibility(View.VISIBLE);
        }
        else {
            btnDelete.setVisibility(View.GONE);
        }
        etTask.setOnClickListener(this);
        tvClickHere1.setOnClickListener(this);
        tvClickHere2.setOnClickListener(this);
        tvSubmit.setOnClickListener(this);
        btnDelete.setOnClickListener(this);
        sColor.setOnItemSelectedListener(this);




    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint({"NonConstantResourceId", "StringFormatMatches"})
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvClickHere1:

                OwnTimePickerDialog timePickerDialog1 = new OwnTimePickerDialog(this, task.getFrom());
                timePickerDialog1.show(getSupportFragmentManager(), getString(R.string.timepickerText));
                break;
            case R.id.tvClickHere2:
                OwnTimePickerDialog timePickerDialog2 = new OwnTimePickerDialog(this, task.getFrom());
                timePickerDialog2.show(getSupportFragmentManager(), getString(R.string.timepickerText));
                break;

            case R.id.tvSubmit:
                if (etTask.getText().toString().isEmpty()){
                    etTask.setError(getString(R.string.task_cannot_be_empty));
                }
                if (tvClickHere1.getText().toString().equals("Click here") || tvClickHere2.getText().toString().equals("Click here")){
                    Toast.makeText(this, String.format(getString(R.string.select_time_s, tvClickHere1.getText().toString().equals("Click here") ? getString(R.string.from) : getString(R.string.to))), Toast.LENGTH_SHORT).show();
                }
                task.setTask(etTask.getText().toString());
                if (getIntent().hasExtra(TanleConstants.TASK_NAME)) {
                    database.updateTask(task, date);
                    Toast.makeText(this, R.string.task_updated_succesfully, Toast.LENGTH_SHORT).show();
                }
                else{
                    database.addTask(task, date);
                    Toast.makeText(this, R.string.task_inserted_succesfully, Toast.LENGTH_SHORT).show();

                }
                finish();
                break;

            case R.id.tvDelete:
                database.deleteTask(task.getId(), String.valueOf(date));
                Toast.makeText(this, R.string.task_deleted_succesfully, Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        task.setColor(sColor.getSelectedItem().toString());

        GradientDrawable background = (GradientDrawable) tvColor.getBackground();
        background.setColor(task.getColorId(getApplicationContext()));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void resultDialog(LocalTime datePickerDialog) {
        String ho = new DecimalFormat("00").format(datePickerDialog.getHour());
        String min = new DecimalFormat("00").format(datePickerDialog.getMinute());
        tvClickHere1.setText(ho + ":" + min);
        tvClickHere2.setText(ho + ":" + min);
    }
}