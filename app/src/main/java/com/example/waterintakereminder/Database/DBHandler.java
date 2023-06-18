package com.example.waterintakereminder.Database;

import static com.example.waterintakereminder.Database.params.params.ANALYTICS_TABLE;
import static com.example.waterintakereminder.Database.params.params.CURRENT_AMOUNT;
import static com.example.waterintakereminder.Database.params.params.CURRENT_TIME;
import static com.example.waterintakereminder.Database.params.params.DATABASE_NAME;
import static com.example.waterintakereminder.Database.params.params.DATE;
import static com.example.waterintakereminder.Database.params.params.DB_VERSION;
import static com.example.waterintakereminder.Database.params.params.FINAL_AMOUNT;
import static com.example.waterintakereminder.Database.params.params.HISTORY_TABLE;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import com.example.waterintakereminder.Fragments.HistoryManager.RecyclerView.HistoryModel;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.TooManyListenersException;

public class DBHandler extends SQLiteOpenHelper {

    public DBHandler(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String historyTable = "create Table history(id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, time TEXT)";
        String analyticsTable = "create Table analytics(id INTEGER PRIMARY KEY AUTOINCREMENT, amount TEXT, date TEXT)";
        sqLiteDatabase.execSQL(historyTable);
        sqLiteDatabase.execSQL(analyticsTable);
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
        Cursor cursor = database.rawQuery(select, null);
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

}
