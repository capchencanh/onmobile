package bt2;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "EmployeeDB";
    private static final int DATABASE_VERSION = 2; // Đã tăng lên 2 để đảm bảo onUpgrade được gọi

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createPhongBanTable = "CREATE TABLE PhongBan (id INTEGER PRIMARY KEY AUTOINCREMENT, tenPhongBan TEXT, soPhong INTEGER)";
        String createNhanVienTable = "CREATE TABLE NhanVien (maNV TEXT PRIMARY KEY, maPB INTEGER, tenNV TEXT, tuoi INTEGER, " +
                "FOREIGN KEY (maPB) REFERENCES PhongBan(id))";
        db.execSQL(createPhongBanTable);
        db.execSQL(createNhanVienTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa bảng cũ và tạo lại với cấu trúc mới
        db.execSQL("DROP TABLE IF EXISTS NhanVien");
        db.execSQL("DROP TABLE IF EXISTS PhongBan");
        onCreate(db);
    }
}