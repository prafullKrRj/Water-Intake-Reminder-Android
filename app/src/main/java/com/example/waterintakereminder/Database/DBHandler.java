package com.example.waterintakereminder.Database;

import static com.example.waterintakereminder.Database.params_db.params.AMOUNT_AMOUNT_TABLE;
import static com.example.waterintakereminder.Database.params_db.params.ANALYTICS_TABLE;
import static com.example.waterintakereminder.Database.params_db.params.CURRENT_AMOUNT;
import static com.example.waterintakereminder.Database.params_db.params.CURRENT_TIME;
import static com.example.waterintakereminder.Database.params_db.params.DATABASE_NAME;
import static com.example.waterintakereminder.Database.params_db.params.DATE;
import static com.example.waterintakereminder.Database.params_db.params.DB_VERSION;
import static com.example.waterintakereminder.Database.params_db.params.FINAL_AMOUNT;
import static com.example.waterintakereminder.Database.params_db.params.HISTORY_TABLE;
import static com.example.waterintakereminder.Database.params_db.params.TABLE_AMOUNT;

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
        sqLiteDatabase.execSQL(historyTable);
        sqLiteDatabase.execSQL(analyticsTable);
        sqLiteDatabase.execSQL(amount);
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
}
