package com.example.waterintakereminder.Database;

import static com.example.waterintakereminder.Database.params_db.params.ACTIVITY_LEVEL;
import static com.example.waterintakereminder.Database.params_db.params.AMOUNT_AMOUNT_TABLE;
import static com.example.waterintakereminder.Database.params_db.params.ANALYTICS_TABLE;
import static com.example.waterintakereminder.Database.params_db.params.CURRENT_AMOUNT;
import static com.example.waterintakereminder.Database.params_db.params.CURRENT_TIME;
import static com.example.waterintakereminder.Database.params_db.params.DAILY_INTAKE_TABLE;
import static com.example.waterintakereminder.Database.params_db.params.DATABASE_NAME;
import static com.example.waterintakereminder.Database.params_db.params.DATE;
import static com.example.waterintakereminder.Database.params_db.params.DB_VERSION;
import static com.example.waterintakereminder.Database.params_db.params.FINAL_AMOUNT;
import static com.example.waterintakereminder.Database.params_db.params.GENDER;
import static com.example.waterintakereminder.Database.params_db.params.HISTORY_TABLE;
import static com.example.waterintakereminder.Database.params_db.params.TABLE_AMOUNT;
import static com.example.waterintakereminder.Database.params_db.params.USERNAME;
import static com.example.waterintakereminder.Database.params_db.params.WEATHER;
import static com.example.waterintakereminder.Database.params_db.params.WEIGHT;
import static com.example.waterintakereminder.Database.params_db.params.WEIGHT_UNIT;

import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.waterintakereminder.Fragments.HistoryManager.RecyclerView.HistoryModel;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DBHandler extends SQLiteOpenHelper {
    private int lastAmount;

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String historyTable = "create Table history(id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, time TEXT)";
        String analyticsTable = "create Table analytics(id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, date TEXT)";
        String amount = "create table currAmount(amount TEXT)";
        String dailyIntake = "create Table dailyIntake(username TEXT, gender TEXT, weight TEXT, unit TEXT, activity TEXT, weather)";
        sqLiteDatabase.execSQL(historyTable);
        sqLiteDatabase.execSQL(analyticsTable);
        sqLiteDatabase.execSQL(amount);
        sqLiteDatabase.execSQL(dailyIntake);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
    public void dailyFinalAmount(int finalAmount, String date){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(FINAL_AMOUNT, String.valueOf(finalAmount));
        values.put(DATE, date);
        database.insert(ANALYTICS_TABLE, null, values);
    }

    public void regularAmountInsertion(int amount){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(CURRENT_AMOUNT, String.valueOf(amount));
        values.put(CURRENT_TIME, LocalTime.now().toString());
        database.insert(HISTORY_TABLE, null, values);
    }
    public int current(){
        SQLiteDatabase database = this.getReadableDatabase();
        String select = "SELECT * FROM "+HISTORY_TABLE;
        @SuppressLint("Recycle") Cursor cursor = database.rawQuery(select, null);
        int sum=0;
        if (cursor.moveToNext()){
            do{
                sum += cursor.getInt(1);
            }while (cursor.moveToNext());
        }
        return sum;
    }
    public List<HistoryModel> getHistory(){
        List<HistoryModel> list = new ArrayList<>();
        SQLiteDatabase database = this.getReadableDatabase();
        String select = "SELECT * FROM " + HISTORY_TABLE;
        Cursor cursor = database.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do{
                HistoryModel contact = new HistoryModel();
                contact.setAmount(cursor.getString(1));
                LocalTime time = LocalTime.parse(cursor.getString(2));
                String timeString = time.getHour()+":"+time.getMinute();
                contact.setTime(timeString);
                list.add(contact);
            }while(cursor.moveToNext());
        }
        return list;
    }

    public void updateAmount(int amount){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(AMOUNT_AMOUNT_TABLE, String.valueOf(amount));
        database.insert(TABLE_AMOUNT, null, values);
    }
    public int getCurrAmount(){
        SQLiteDatabase database = this.getReadableDatabase();
        String select = "SELECT * FROM " + TABLE_AMOUNT;
        Cursor cursor = database.rawQuery(select, null);
        if(cursor.moveToFirst()){
            do{
                lastAmount = Integer.parseInt(cursor.getString(0));
            }while(cursor.moveToNext());
        }
        return lastAmount;
    }

    public int size(){
        int n=0;
        SQLiteDatabase database = this.getReadableDatabase();
        String select = "SELECT * FROM " + HISTORY_TABLE;
        Cursor cursor = database.rawQuery(select, null);

        if(cursor.moveToFirst()){
            do{
                n++;
            }while(cursor.moveToNext());
        }
        return n;
    }

    public void insertUserDetails(){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(USERNAME, new userDetails().getName());
        values.put(GENDER, new userDetails().getGender());
        values.put(WEIGHT, new userDetails().getWeight());
        values.put(WEIGHT_UNIT, new userDetails().getWeightUnit());
        values.put(ACTIVITY_LEVEL, new userDetails().getDailyActivity());
        values.put(WEATHER, new userDetails().getWeather());
        database.insert(DAILY_INTAKE_TABLE, null, values);
    }
    public List<String> getUserValues(){
        SQLiteDatabase database = this.getReadableDatabase();
        String select = "SELECT * FROM " + DAILY_INTAKE_TABLE;
        Cursor cursor = database.rawQuery(select, null);
        List<String> list = new ArrayList<>();
        if (cursor.moveToNext()){
            do{
                list.add(cursor.getString(0));
                list.add(cursor.getString(1));
                list.add(cursor.getString(2));
                list.add(cursor.getString(3));
                list.add(cursor.getString(4));
                list.add(cursor.getString(5));
            }while (cursor.moveToNext());
        }
        return list;
    }
    public boolean change(String column, String value){
        SQLiteDatabase database = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        int rowId = 1;
        values.put(column, value);
        int rowsAffected = database.update(DAILY_INTAKE_TABLE, values, "id = ?", new String[]{String.valueOf(rowId)});
        return rowsAffected > 0;
    }
}
