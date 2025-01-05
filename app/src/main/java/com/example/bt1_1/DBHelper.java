package com.example.bt1_1;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DBHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "QLSP.db";

    public static final String TABLE_SP = "SanPham";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_TEN = "ten";
    public static final String COLUMN_GIA = "gia";
    public static final String COLUMN_MOTA = "mota";

    public DBHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DATABASE_SP = "CREATE TABLE " + TABLE_SP + "("+
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COLUMN_TEN + " TEXT," +
                COLUMN_GIA + " REAL," +
                COLUMN_MOTA + " TEXT" +
                ")";
        db.execSQL(CREATE_DATABASE_SP);
        // Thêm dữ liệu mẫu vào bảng
        insertSampleData(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_SP);
        onCreate(db);
    }

    public void insertSampleData(SQLiteDatabase db) {
        db.execSQL("INSERT INTO " + TABLE_SP + " (" + COLUMN_TEN + ", " + COLUMN_GIA + ") VALUES " +
                "('Sản phẩm 1', 10.0), ('Sản phẩm 2', 20.0), ('Sản phẩm 3', 30.0), " +
                "('Sản phẩm 4', 40.0), ('Sản phẩm 5', 50.0), ('Sản phẩm 6', 60.0)");
    }
}
