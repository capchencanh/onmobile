package bt2;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class MyDatabase extends SQLiteOpenHelper {

    public MyDatabase(Context context) {
        super(context, "NhanVienDB", null, 2);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPhongBanTable = "CREATE TABLE PhongBan (id INTEGER PRIMARY KEY AUTOINCREMENT, tenPhongBan TEXT, soPhong INTEGER)";
        String createNhanVienTable = "CREATE TABLE NhanVien (maNV TEXT PRIMARY KEY, maPB INTEGER, tenNV TEXT, tuoi INTEGER, FOREIGN KEY(maPB) REFERENCES PhongBan(id))";
        db.execSQL(createPhongBanTable);
        db.execSQL(createNhanVienTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS PhongBan");
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        onCreate(db);
    }

    public void addPhongBan(PhongBan pb) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO PhongBan(tenPhongBan, soPhong) VALUES(?, ?)";
        db.execSQL(query, new Object[]{pb.getTenPB(), pb.getSoPhong()});
    }

    public int getSoLuongPhongBan() {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM PhongBan";
        Cursor cursor = db.rawQuery(query, null);

        int soLuong = 0;
        if (cursor != null && cursor.moveToFirst()) {
            soLuong = cursor.getInt(0);
            cursor.close();
        }
        return soLuong;
    }

    public List<String> getAllPhongBan() {
        List<String> phongBanList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT tenPhongBan FROM PhongBan";
        Cursor cursor = db.rawQuery(query, null);

        if (cursor != null && cursor.moveToFirst()) {
            do {
                phongBanList.add(cursor.getString(0));
            } while (cursor.moveToNext());
            cursor.close();
        }
        return phongBanList;
    }

    public int getPhongBanIdFromDatabase(String tenPhongBan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT id FROM PhongBan WHERE tenPhongBan = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tenPhongBan});

        int phongBanId = -1;
        if (cursor != null && cursor.moveToFirst()) {
            phongBanId = cursor.getInt(0);
            cursor.close();
        }
        return phongBanId;
    }

    public List<String> getNhanViensByPhongBan(String tenPhongBan) {
        List<String> nhanVienList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT pb.tenPhongBan, nv.maNV, nv.tenNV, nv.tuoi " +
                "FROM NhanVien nv " +
                "INNER JOIN PhongBan pb ON nv.maPB = pb.id " +
                "WHERE pb.tenPhongBan = ?";
        Cursor cursor = db.rawQuery(query, new String[]{tenPhongBan});

        if (cursor != null && cursor.moveToFirst()) {
            do {
                String tenPB = cursor.getString(0);
                String maNV = cursor.getString(1);
                String tenNV = cursor.getString(2);
                int tuoi = cursor.getInt(3);
                nhanVienList.add(tenPB + ": " + maNV + ": " + tenNV + ": " + tuoi);
            } while (cursor.moveToNext());
            cursor.close();
        }
        return nhanVienList;
    }

    public void addNhanVien(NhanVien nv) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO NhanVien(maNV, maPB, tenNV, tuoi) VALUES(?, ?, ?, ?)";
        db.execSQL(query, new Object[]{nv.getMaNV(), nv.getMaPB(), nv.getTenNV(), nv.getTuoi()});
    }

    public boolean checkNhanVienExists(String maNV) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM NhanVien WHERE maNV = ?";
        Cursor cursor = db.rawQuery(query, new String[]{maNV});

        boolean exists = false;
        if (cursor != null && cursor.moveToFirst()) {
            exists = cursor.getInt(0) > 0;
            cursor.close();
        }
        return exists;
    }

    public boolean deleteNhanVien(String maNV) {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM NhanVien WHERE maNV = ?";
        db.execSQL(query, new Object[]{maNV});
        return true;
    }

    public int getSoLuongNhanVien(String tenPhongBan) {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT COUNT(*) FROM NhanVien WHERE maPB = (SELECT id FROM PhongBan WHERE tenPhongBan = ?)";
        Cursor cursor = db.rawQuery(query, new String[]{tenPhongBan});

        int soLuong = 0;
        if (cursor != null && cursor.moveToFirst()) {
            soLuong = cursor.getInt(0);
            cursor.close();
        }
        return soLuong;
    }
}