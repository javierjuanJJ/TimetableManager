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

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class TaskEditor extends AppCompatActivity implements View.OnClickListener, AdapterView.OnItemSelectedListener {


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

        date = getIntent().getStringExtra("Date");
        String[] colors = {"rose","blue","green","orange","grey","purple","yellow"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, colors);
        sColor.setAdapter(adapter);

        if (getIntent().hasExtra("Task")){
            task = (Task) getIntent().getSerializableExtra("taskObject");
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
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {

            case R.id.tvClickHere1:
                TimePickerDialog timePickerDialog = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String ho = new DecimalFormat("00").format(i);
                        String min = new DecimalFormat("00").format(i1);
                        tvClickHere1.setText(ho + ":" + min);

                    }
                }, task.getFrom().getHour(), task.getFrom().getMinute(), true);
                timePickerDialog.show();
                break;
            case R.id.tvClickHere2:
                TimePickerDialog timePickerDialog1 = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker timePicker, int i, int i1) {
                        String ho = new DecimalFormat("00").format(i);
                        String min = new DecimalFormat("00").format(i1);
                        tvClickHere2.setText(ho + ":" + min);

                    }
                }, task.getFrom().getHour(), task.getFrom().getMinute(), true);
                timePickerDialog1.show();
                break;

            case R.id.tvSubmit:
                if (etTask.getText().toString().isEmpty()){
                    etTask.setError("Task cannot be empty");
                }
                if (tvClickHere1.getText().toString().equals("Click here") || tvClickHere2.getText().toString().equals("Click here")){
                    Toast.makeText(this, "Select time: " + (tvClickHere1.getText().toString().equals("Click here") ? "from" : "to"), Toast.LENGTH_SHORT).show();
                }
                task.setTask(etTask.getText().toString());
                if (getIntent().hasExtra("Task")) {
                    database.updateTask(task, date);
                    Toast.makeText(this, "Task updated succesfully", Toast.LENGTH_SHORT).show();
                }
                else{
                    database.addTask(task, date);
                    Toast.makeText(this, "Task inserted succesfully", Toast.LENGTH_SHORT).show();

                }
                finish();
                break;

            case R.id.tvDelete:
                database.deleteTask(task.getId(), String.valueOf(date));
                Toast.makeText(this, "Task deleted succesfully", Toast.LENGTH_SHORT).show();
                finish();
                break;

        }
    }


    @Override
    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
        task.setColor(sColor.getSelectedItem().toString());

        GradientDrawable background = (GradientDrawable) tvColor.getBackground();
        background.setColor(task.getColorId(this));
    }

    @Override
    public void onNothingSelected(AdapterView<?> adapterView) {

    }
}