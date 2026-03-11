package edu.jsu.mcis.cs408.memopad.model.dao;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DAOFactory extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "memos.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_MEMOS = "memos";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_MEMO = "memo";

    public DAOFactory(@Nullable Context context, @Nullable String name,
                      @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
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

    public MemoDAO getMemoDao() {
        return new MemoDAO(this);
    }
}