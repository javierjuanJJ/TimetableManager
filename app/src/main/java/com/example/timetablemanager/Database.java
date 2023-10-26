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

import com.example.timetablemanager.Constants.TanleConstants;

import java.util.ArrayList;

class Database extends SQLiteOpenHelper {

   public Database(@Nullable Context context) {
      super(context, TanleConstants.DATABASE_NAME, null, TanleConstants.VERSION_DATABASE);
   }

   @Override
   public void onCreate(SQLiteDatabase sqLiteDatabase) {

   }

   @Override
   public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

   }

   public void checkTable(String date){
      String create = String.format(TanleConstants.CREATE_SENTENCE, getStringTable(date));
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
      String create = String.format(TanleConstants.INSERT_SENTENCE, getStringTable(date), task.getTask(), task.getFromToString(), task.getToString(), task.getColor());
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public ArrayList<Task> getAllTasks(String date){
      checkTable(date);
      String create = String.format(TanleConstants.SELECT_ALL_SENTENCE, getStringTable(date));
      ArrayList<Task> tasks = new ArrayList<>();
      SQLiteDatabase db = this.getReadableDatabase();
      Cursor cursor = db.rawQuery(create, null);
      cursor.moveToFirst();

      if (cursor.moveToFirst()) {
         while (cursor.moveToNext()){
            Task task = new Task();

            task.setId(cursor.getInt(0));
            task.setTask(cursor.getString(1));
            task.setFrom(cursor.getString(2));
            task.setTo(cursor.getString(3));
            task.setColor(cursor.getString(cursor.getColumnIndexOrThrow(TanleConstants.COLOR_TABLE_NAME)));

            tasks.add(task);
         }
      }

      cursor.close();

      return tasks;
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public void updateTask(Task task, String date){
      checkTable(date);

      String create = String.format(TanleConstants.UPDATE_SENTENCE, getStringTable(date), task.getTask(), task.getFromToString(), task.getToString(), task.getColor(), task.getId());
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

   @RequiresApi(api = Build.VERSION_CODES.O)
   public void deleteTask(int idTask, String date){
      checkTable(date);
      String create = String.format(TanleConstants.DELETE_SENTENCE, getStringTable(date), idTask);
      SQLiteDatabase db = this.getWritableDatabase();
      db.execSQL(create);
   }

}
