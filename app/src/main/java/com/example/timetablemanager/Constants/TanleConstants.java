package com.example.timetablemanager.Constants;

public class TanleConstants {
   public static final String TASK_TABLE_NAME = "task_table";
   public static final String FROM_TABLE_NAME = "from_table";
   public static final String TO_TABLE_NAME = "to_table";
   public static final String COLOR_TABLE_NAME = "color_table";
   public static final String ID_NAME_TABLE = "_id";
   public static final String UPDATE_SENTENCE = String.format("UPDATE %%s SET %s = '%%s', %s = '%%s', %s = '%%s', %s = '%%s' WHERE %s = %%d;", TASK_TABLE_NAME, FROM_TABLE_NAME, TO_TABLE_NAME, COLOR_TABLE_NAME, ID_NAME_TABLE);
   public static final String SELECT_ALL_SENTENCE = "SELECT * FROM %s;";
   public static final String CREATE_SENTENCE = String.format("CREATE TABLE IF NOT EXISTS %%s (%s INTEGER PRIMARY KEY AUTOINCREMENT, %s TEXT, %s TEXT, %s TEXT, %s TEXT);", ID_NAME_TABLE, TASK_TABLE_NAME, FROM_TABLE_NAME, TO_TABLE_NAME, COLOR_TABLE_NAME);
   public static final String DELETE_SENTENCE = String.format("DELETE FROM %%s WHERE %s = %%d;", ID_NAME_TABLE);
   public static final String INSERT_SENTENCE = String.format("INSERT INTO %%s (%s , %s, %s , %s) VALUES(\"%%s\" , \"%%s\" , \"%%s\" , \"%%s\");", TASK_TABLE_NAME, FROM_TABLE_NAME, TO_TABLE_NAME, COLOR_TABLE_NAME);
   public static final String DATABASE_NAME = "Database4";
   public static final int VERSION_DATABASE = 1;
   public static final String TASK_NAME = "Task";
   public static final String TASK_OBJECT_NAME = "taskObject";
   public static final String DATE_NAME = "Date";

   public static int findColor(String[] stringArray, String wordToFound) {
      int position = 0;
      boolean isFound = false;
      for (int counter = 0; counter < stringArray.length && !isFound; counter++) {
         if (stringArray[counter].equalsIgnoreCase(wordToFound)){
            position = counter;
            isFound = true;
         }
      }

      return position;
   }
}
