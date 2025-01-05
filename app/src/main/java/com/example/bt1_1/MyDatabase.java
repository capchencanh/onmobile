package com.example.bt1_1;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


import java.util.ArrayList;

public class MyDatabase {
    private DBHelper dbHelper;
    private SQLiteDatabase database;

    public MyDatabase(Context context) {
        dbHelper = new DBHelper(context);
        database = dbHelper.getWritableDatabase();
    }

    // Thêm sản phẩm vào cơ sở dữ liệu
    public void insertProduct(SanPham product) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TEN, product.getTen());
        values.put(DBHelper.COLUMN_GIA, product.getGia());
        database.insert(DBHelper.TABLE_SP, null, values);
    }

    // Xóa sản phẩm theo id
    public void deleteProduct(int id) {
        database.delete(DBHelper.TABLE_SP, DBHelper.COLUMN_ID + "=?", new String[]{String.valueOf(id)});
    }

    // Cập nhật sản phẩm
    public void updateProduct(SanPham product) {
        ContentValues values = new ContentValues();
        values.put(DBHelper.COLUMN_TEN, product.getTen());  // Đảm bảo sử dụng getName()
        values.put(DBHelper.COLUMN_GIA, product.getGia());  // Đảm bảo sử dụng getPrice()
        database.update(DBHelper.TABLE_SP, values, DBHelper.COLUMN_ID + "=?", new String[]{String.valueOf(product.getId())});
    }

    public ArrayList<SanPham> getAllProducts() {
        ArrayList<SanPham> productList = new ArrayList<>();
        Cursor cursor = database.query(DBHelper.TABLE_SP, null, null, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                // Kiểm tra và lấy chỉ số cột để tránh lỗi
                int idColumnIndex = cursor.getColumnIndex(DBHelper.COLUMN_ID);
                int nameColumnIndex = cursor.getColumnIndex(DBHelper.COLUMN_TEN);
                int priceColumnIndex = cursor.getColumnIndex(DBHelper.COLUMN_GIA);

                if (idColumnIndex != -1 && nameColumnIndex != -1 && priceColumnIndex != -1) {
                    int id = cursor.getInt(idColumnIndex);
                    String name = cursor.getString(nameColumnIndex);
                    float price = cursor.getFloat(priceColumnIndex);
                    productList.add(new SanPham(id, name, price,null));
                } else {
                    // Xử lý trường hợp không tìm thấy cột
                    Log.e("MyDatabase", "Cột không tồn tại trong cơ sở dữ liệu");
                }
            } while (cursor.moveToNext());
            cursor.close();
        }
        return productList;
    }

}
