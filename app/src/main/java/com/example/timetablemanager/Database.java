package com.example.timetablemanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

class Database extends SQLiteOpenHelper {
   public Database(@Nullable Context context) {
      super(context, "Database4", null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {

   }

   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

   }

   public void checkTable(String date){
      String create = String.format("CREATE TABLE IF NOT EXISTS %s (_id INTEGER PRIMARY KEY AUTOINCREMENT, task_table TEXT, from_table TEXT, to_table TEXT, color_table TEXT);", getStringTable(date));
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @NonNull
   private static String getStringTable(String date) {
      return "tasks_" + date.replaceAll("-", "");
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public void addTask(Task task, String date){
      checkTable(date);
      String create = String.format("INSERT INTO %s (task_table , from_table, to_table , color_table) VALUES(\"%s\" , \"%s\" , \"%s\" , \"%s\");", getStringTable(date), task.getTask(), task.getFromToString(), task.getToString(), task.getColor());
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public ArrayList<Task> getAllTasks(String date){
      checkTable(date);
      String create = String.format("SELECT * FROM %s;", getStringTable(date));
      ArrayList<Task> tasks = new ArrayList<>();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery(create, null);
      cursor.moveToFirst();

      if (cursor.moveToFirst()) {
         while (cursor.moveToNext()){
            Task task = new Task();

            task.setId(cursor.getInt(0));
            Log.i("idTaskUpdated", String.valueOf(task.getId()));
            task.setTask(cursor.getString(1));
            task.setFrom(cursor.getString(2));
            task.setTo(cursor.getString(3));
            task.setColor(cursor.getString(cursor.getColumnIndexOrThrow("color_table")));

            tasks.add(task);
         }
      }

      cursor.close();

      return tasks;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public int getNextId(String date){
      ArrayList<Task> tasks = getAllTasks(date);
      int id = 0;
      int size = tasks.size();
      int lastIndex;

      if (!tasks.isEmpty()){
         /*lastIndex = size - 1;
         id = tasks.get(lastIndex).getId() + 1;*/
         id = tasks.size() + 1;
      }
      else {
         id = 1;
      }

      return id;
   }


   @RequiresApi(api = Build.VERSION_CODES.O)
   public void updateTask(Task task, String date){
      checkTable(date);
      Log.i("idTaskUpdated", String.valueOf(task.getId()));
      String create = String.format("UPDATE %s SET task_table = '%s', from_table = '%s', to_table = '%s', color_table = '%s' WHERE _id = %d;", getStringTable(date), task.getTask(), task.getFromToString(), task.getToString(), task.getColor(), task.getId());
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public void deleteTask(int idTask, String date){
      checkTable(date);
      String create = String.format("DELETE FROM %s WHERE _id = %d;", getStringTable(date), idTask);
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

}
