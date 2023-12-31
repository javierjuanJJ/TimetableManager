package com.example.timetablemanager;

import android.content.Context;
import android.graphics.Color;
import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.timetablemanager.Constants.TanleConstants;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;

public class Task implements Comparable<Task>, Serializable {
   private int id;
   private String task, color;
   private LocalTime from, to;
   private transient DateTimeFormatter formatter;
   private Calendar calendar;


   private void writeObject(ObjectOutputStream oos)
           throws IOException {
      oos.defaultWriteObject();
      oos.writeObject("HH:mm");
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   private void readObject(ObjectInputStream ois)
           throws ClassNotFoundException, IOException {
      ois.defaultReadObject();
      String houseNumber = (String) ois.readObject();
      DateTimeFormatter a = DateTimeFormatter.ofPattern(houseNumber);
      this.setFormatter(a);
   }

   public void setFormatter(DateTimeFormatter formatter) {
      this.formatter = formatter;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public Task(){
      formatter = DateTimeFormatter.ofPattern("HH:mm");
      calendar = Calendar.getInstance();
      from = getCalendarFrom(calendar);
      to = getCalendarFrom(calendar);
      task="";

   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   private static LocalTime getCalendarFrom(Calendar calendar) {
      return LocalTime.of(calendar.get(Calendar.HOUR), calendar.get(Calendar.MINUTE));
   }

   public Task(int id, String task, String color, LocalTime from, LocalTime to, DateTimeFormatter formatter) {
      this.id = id;
      this.task = task;
      this.color = color;
      this.from = from;
      this.to = to;
      this.formatter = formatter;
   }

   public int getId() {
      return id;
   }

   public void setId(int id) {
      this.id = id;
   }

   public String getTask() {
      return task;
   }

   public void setTask(String task) {
      this.task = task;
   }

   public String getColor() {
      return color;
   }

   public void setColor(String color) {
      this.color = color;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public String getFromToString() {
      return from.format(formatter);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public String getToString() {
      return to.format(formatter);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public void setFrom(String from) {
      this.from = getParseDate(from);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   private LocalTime getParseDate(String from) {
      return LocalTime.parse(from, formatter);
   }



   @RequiresApi(api = Build.VERSION_CODES.O)
   public void setTo(String to) {
      this.from = getParseDate(to);
   }

   public int getColorId(Context context){
      String[] stringArray = context.getResources().getStringArray(R.array.colors_content);
      int position = TanleConstants.findColor(context.getResources().getStringArray(R.array.colors_name), getColor());
      return Color.parseColor(stringArray[position]);
   }

   public LocalTime getFrom() {
      return from;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   @Override
   public int compareTo(Task task) {
      return from.compareTo(task.getFrom());
   }
}
