package edu.jsu.mcis.cs408.memopad;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class DatabaseHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MEMOS = "memos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MEMO = "memo";

    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable =
                "CREATE TABLE " + TABLE_MEMOS + " (" +
                        COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                        COLUMN_MEMO + " TEXT NOT NULL" +
                        ");";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_MEMOS);
        onCreate(db);
    }


    public void addMemo(String memoText) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_MEMO, memoText);
        db.insert(TABLE_MEMOS, null, values);
        db.close();
    }


    public ArrayList<Memo> getAllMemos() {
        ArrayList<Memo> list = new ArrayList<>();

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + TABLE_MEMOS + " ORDER BY " + COLUMN_ID + " ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID));
                String memoText = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_MEMO));
                list.add(new Memo(id, memoText));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }


    public void deleteMemo(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_MEMOS, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
        db.close();
    }
}