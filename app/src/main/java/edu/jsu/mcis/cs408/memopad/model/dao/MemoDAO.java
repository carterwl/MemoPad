package edu.jsu.mcis.cs408.memopad.model.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import edu.jsu.mcis.cs408.memopad.Memo;

public class MemoDAO {

    private final DAOFactory daoFactory;

    MemoDAO(DAOFactory daoFactory) {
        this.daoFactory = daoFactory;
    }

    public void create(Memo m) {
        SQLiteDatabase db = daoFactory.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(DAOFactory.COLUMN_MEMO, m.getMemo());

        db.insert(DAOFactory.TABLE_MEMOS, null, values);
        db.close();
    }

    public void delete(Integer id) {
        SQLiteDatabase db = daoFactory.getWritableDatabase();

        db.delete(
                DAOFactory.TABLE_MEMOS,
                DAOFactory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        db.close();
    }

    public Memo find(int id) {
        SQLiteDatabase db = daoFactory.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DAOFactory.TABLE_MEMOS +
                        " WHERE " + DAOFactory.COLUMN_ID + " = ?",
                new String[]{String.valueOf(id)}
        );

        Memo memo = null;

        if (cursor.moveToFirst()) {
            int memoId = cursor.getInt(cursor.getColumnIndexOrThrow(DAOFactory.COLUMN_ID));
            String memoText = cursor.getString(cursor.getColumnIndexOrThrow(DAOFactory.COLUMN_MEMO));
            memo = new Memo(memoId, memoText);
        }

        cursor.close();
        db.close();

        return memo;
    }

    public List<Memo> list() {
        List<Memo> list = new ArrayList<>();

        SQLiteDatabase db = daoFactory.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM " + DAOFactory.TABLE_MEMOS +
                        " ORDER BY " + DAOFactory.COLUMN_ID + " ASC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(DAOFactory.COLUMN_ID));
                String memoText = cursor.getString(cursor.getColumnIndexOrThrow(DAOFactory.COLUMN_MEMO));
                list.add(new Memo(id, memoText));
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return list;
    }
}