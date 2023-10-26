package com.example.timetablemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.timetablemanager.Pickers.OwnDatePickerDialog;
import com.example.timetablemanager.Pickers.OwnTimePickerDialog;
import com.example.timetablemanager.Pickers.onTimeResultPickerDialog;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, onTimeResultPickerDialog {

    private TextView date;
    private RelativeLayout rlTop;
    private ImageView ivLeft, ivRight, ivPlus;
    private ListView lvTop;
    private LinearLayout llPlus;
    private LocalDate showedDate;
    private DateTimeFormatter mainDate;
    private Database database;
    private ArrayList<Task> listTasks;
    private ListAdapter listAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            setUI();
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void setUI() {
        date = findViewById(R.id.date);
        rlTop = findViewById(R.id.rlTop);
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
        ivPlus = findViewById(R.id.ivPlus);
        lvTop = findViewById(R.id.lvTop);
        llPlus = findViewById(R.id.llPlus);

        showedDate = LocalDate.now();
        mainDate = DateTimeFormatter.ofPattern("EEEE dd/MM");
        listTasks = new ArrayList<>();
        database = new Database(this);
        refreshAdapter();

        rlTop.setOnClickListener(this);
        ivLeft.setOnClickListener(this);
        llPlus.setOnClickListener(this);
        date.setOnClickListener(this);

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refreshAdapter() {
        listTasks = database.getAllTasks(showedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        listAdapter = new ListAdapter(listTasks, this, showedDate);
        lvTop.setAdapter(listAdapter);
        listAdapter.notifyDataSetChanged();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date:
                /*DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
                    showedDate = LocalDate.of(i, i1, i2);
                }, showedDate.getYear(), showedDate.getMonthValue() - 1, showedDate.getDayOfMonth());
                datePickerDialog.show();*/
                OwnDatePickerDialog dialogPicker = new OwnDatePickerDialog(this, showedDate);
                dialogPicker.show(getSupportFragmentManager(), "datePicker");
                refreshDate();
                refreshAdapter();
                break;

            case R.id.ivLeft:
                showedDate.minusDays(1);
                refreshDate();
                refreshAdapter();
                break;

            case R.id.ivRight:
                showedDate.plusDays(1);
                refreshDate();
                refreshAdapter();
                break;

            case R.id.llPlus:
                Intent intent = new Intent(MainActivity.this, TaskEditor.class);
                intent.putExtra("Date", showedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                startActivity(intent);

                break;

        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onResume() {
        super.onResume();
        refreshDate();
        refreshAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onRestart() {
        super.onRestart();
        refreshDate();
        refreshAdapter();
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void refreshDate(){
        date.setText(showedDate.format(mainDate));

    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void resultDialog(LocalDate datePickerDialog) {
        showedDate =  LocalDate.of(datePickerDialog.getYear(), datePickerDialog.getMonthValue() + 1, datePickerDialog.getDayOfMonth());
    }
}