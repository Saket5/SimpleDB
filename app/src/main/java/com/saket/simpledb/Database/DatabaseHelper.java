package com.saket.simpledb.Database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.saket.simpledb.Model.Students;

import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DBNAME = "sampleDB.sqlite";
    public static final String DBLOCATION = "/data/data/com.saket.simpledb/databases/";
    private Context mContext;
    private SQLiteDatabase mDatabase;

    public DatabaseHelper(Context context) {
        super(context, DBNAME, null, 1);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void openDatabase() {
        String dbPath = mContext.getDatabasePath(DBNAME).getPath();
        if (mDatabase != null && mDatabase.isOpen()) {
            return;
        }
        mDatabase = SQLiteDatabase.openDatabase(dbPath, null, SQLiteDatabase.OPEN_READWRITE);
    }

    public void closeDatabase() {
        if (mDatabase != null) {
            mDatabase.close();
        }
    }

    public ArrayList<Students> getListStudent() {
        Students student = null;
        ArrayList<Students> studentList = new ArrayList<>();
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM STUDENTS", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            student = new Students(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
            studentList.add(student);
            cursor.moveToNext();
        }
        cursor.close();
        closeDatabase();
        return studentList;
    }

    public Students getStudentById(int id) {
        Students student = null;
        openDatabase();
        Cursor cursor = mDatabase.rawQuery("SELECT * FROM STUDENTS WHERE ID = ?", new String[]{String.valueOf(id)});
        cursor.moveToFirst();
        student = new Students(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
        cursor.close();
        closeDatabase();
        return student;
    }

    public long updateStudent(Students student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", student.getId());
        contentValues.put("NAME", student.getName());
        contentValues.put("AGE", student.getAge());
        contentValues.put("DEPARTMENT", student.getDepartment());
        openDatabase();
        long returnValue = mDatabase.insert("STUDENTS", null, contentValues);
        closeDatabase();
        return returnValue;
    }

    public boolean checkID(int id) {
        openDatabase();
        String Query = "SELECT * FROM STUDENTS WHERE ID = ?";
        Cursor cursor = mDatabase.rawQuery(Query, new String[]{String.valueOf(id)});
        if(cursor.getCount() <= 0){
            cursor.close();
            return false;
        }
        cursor.close();
        closeDatabase();
        return true;
    }

    public long addStudent(Students student) {
        ContentValues contentValues = new ContentValues();
        contentValues.put("ID", student.getId());
        contentValues.put("NAME", student.getName());
        contentValues.put("AGE", student.getAge());
        contentValues.put("DEPARTMENT", student.getDepartment());
        openDatabase();
        long returnValue = mDatabase.insert("STUDENTS", null, contentValues);
        closeDatabase();
        return returnValue;
    }

    public boolean deleteStudentById(int id) {
        openDatabase();
        int result = mDatabase.delete("STUDENTS", "ID =?", new String[]{String.valueOf(id)});
        closeDatabase();
        return result != 0;
    }
}