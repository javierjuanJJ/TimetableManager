package com.example.timetablemanager;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import java.util.ArrayList;

class Database extends SQLiteOpenHelper {
   public Database(@Nullable Context context) {
      super(context, "Database", null, 1);
   }

   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {

   }

   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

   }

   public void checkTable(String date){
      String create = String.format("CREATE TABLE IF NOT EXISTS %s ('ID' integer, 'TASK' text, 'FROM' text, 'TO' text, 'Color', text);", date);
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public void addTask(Task task, String date){
      checkTable(date);
      String create = String.format("INSERT INTO '%s' ('ID' , 'TASK' , 'FROM', 'TO' , 'COLOR') VALUES(%d , %s , %s , %s , %s);", date, task.getId(), task.getTask(), task.getFromToString(), task.getToString(), task.getColor());
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public ArrayList<Task> getAllTasks(String date){
      checkTable(date);
      String create = String.format("SELECT * FROM %s;", date);
      ArrayList<Task> tasks = new ArrayList<>();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery(create, null);
      cursor.moveToFirst();

      if (cursor != null && cursor.moveToFirst() ) {
         while (cursor.moveToNext()){
            Task task = new Task();

            task.setId(cursor.getInt(0));
            task.setTask(cursor.getString(1));
            task.setFrom(cursor.getString(2));
            task.setTo(cursor.getString(3));
            task.setColor(cursor.getString(4));

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

      if (size != 0){
         lastIndex = size - 1;
         id = tasks.get(lastIndex).getId() + 1;
      }

      return id;
   }


   @RequiresApi(api = Build.VERSION_CODES.O)
   public void updateTask(Task task, String date){
      checkTable(date);
      String create = String.format("UPDATE '%s' SET 'TASK' = '%s', 'FROM' = '%s', 'TO' = '%s', 'COLOR' = '%s' WHERE 'ID'' = %d;", date, task.getTask(), task.getFromToString(), task.getToString(), task.getColor(), task.getId());
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public void deleteTask(int idTask, String date){
      checkTable(date);
      String create = String.format("DELETE FROM '%s' WHERE 'ID' = '%d';", date, idTask);
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

}
