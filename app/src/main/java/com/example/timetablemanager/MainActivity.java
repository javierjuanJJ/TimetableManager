package com.example.timetablemanager;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView date;
    private RelativeLayout rlTop;
    private ImageView ivLeft, ivRight, ivPlus;
    private ListView lvTop;
    private LinearLayout llPlus;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUI();
    }

    private void setUI() {
        date = findViewById(R.id.date);
        rlTop = findViewById(R.id.rlTop);
        ivLeft = findViewById(R.id.ivLeft);
        ivRight = findViewById(R.id.ivRight);
        ivPlus = findViewById(R.id.ivPlus);
        lvTop = findViewById(R.id.lvTop);
        llPlus = findViewById(R.id.llPlus);
    }
}