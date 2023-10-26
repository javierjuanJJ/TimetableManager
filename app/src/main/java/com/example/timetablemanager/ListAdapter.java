package com.example.timetablemanager;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.GradientDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class ListAdapter extends BaseAdapter implements View.OnLongClickListener {
   private ArrayList<Task> tasks;
   private Context context;
   private LocalDate showedDate;
   private Task task;
   private TextView tvFrom;
   private TextView tvTo;
   private TextView tvTask;

   public ListAdapter(ArrayList<Task> tasks, Context context, LocalDate showedDate) {
      this.tasks = tasks;
      this.context = context;
      this.showedDate = showedDate;
   }

   @Override
   public int getCount() {
      return tasks.size();
   }

   @Override
   public Object getItem(int i) {
      return tasks.get(i);
   }

   @Override
   public long getItemId(int i) {
      return i;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   @Override
   public View getView(int i, View view, ViewGroup viewGroup) {

      LayoutInflater inflater = LayoutInflater.from(context);
      View view1 = inflater.inflate(R.layout.task, null);

      tvFrom = view1.findViewById(R.id.tvFrom);
      tvTo = view1.findViewById(R.id.tvTo);
      tvTask = view1.findViewById(R.id.tvTask);

      task = tasks.get(i);

      tvFrom.setText(task.getFromToString());
      tvTo.setText(task.getToString());
      tvTask.setText(task.getTask());

      GradientDrawable gradientDrawable = (GradientDrawable) tvTask.getBackground();
      gradientDrawable.setColor(task.getColorId(context));

      tvTask.setOnLongClickListener(this);

      return view1;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   private void screenTaskEditor(Task task) {
      Intent intent = new Intent(context, TaskEditor.class);
      intent.putExtra("Task", true);

      intent.putExtra("Date", showedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd")));

      intent.putExtra("taskObject", task);

      context.startActivity(intent);
   }

   @SuppressLint("NonConstantResourceId")
   @RequiresApi(api = Build.VERSION_CODES.O)
   @Override
   public boolean onLongClick(View view) {
      switch (view.getId()) {
         case R.id.tvTask:
            if (task != null) {
               screenTaskEditor(task);
            }
            return true;
         default:
            return false;

      }
   }
}
