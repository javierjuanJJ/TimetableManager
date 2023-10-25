package com.example.timetablemanager;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.app.ListActivity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private TextView date;
    private RelativeLayout rlTop;
    private ImageView ivLeft, ivRight, ivPlus;
    private ListView lvTop;
    private LinearLayout llPlus;
    private LocalDate showedDate;

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
        date.setOnClickListener(this);
        showedDate = LocalDate.now();

    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("NonConstantResourceId")
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date:
                DatePickerDialog datePickerDialog = new DatePickerDialog(this, (datePicker, i, i1, i2) -> {
                    showedDate = LocalDate.of(i, i1, i2);
                }, showedDate.getYear(), showedDate.getMonthValue() - 1, showedDate.getDayOfMonth());
                datePickerDialog.show();
                break;

            case R.id.ivLeft:
                showedDate.minusDays(1);
                refreshDate();
                break;

            case R.id.ivRight:
                showedDate.plusDays(1);
                refreshDate();
                break;

            case R.id.ivPlus:
                Intent intent = new Intent(MainActivity.this, TaskEditor.class);
                intent.putExtra("Date", showedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
                startActivity(intent);

                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        refreshDate();
    }

    private void refreshDate(){

    }
}