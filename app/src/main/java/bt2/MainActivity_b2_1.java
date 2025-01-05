package bt2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bt1_1.R;

import java.util.List;

public class MainActivity_b2_1 extends AppCompatActivity {

    private EditText edtMaNV, edtTenNV, edtTuoi;
    private Button btnThem, btnXoa, btnOpenAct;
    private Spinner spPhongBan;
    private ListView lvDanhSach;
    private MyDatabase db;
    private ArrayAdapter<String> adapter;
    private List<String> danhSachNhanVien;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_b21);

        db = new MyDatabase(this);

        // Ánh xạ các View
        edtMaNV = findViewById(R.id.edtMaNV);
        edtTenNV = findViewById(R.id.edtTenNV);
        edtTuoi = findViewById(R.id.edtTuoi);
        btnThem = findViewById(R.id.btnThem);
        btnXoa = findViewById(R.id.btnXoa);
        btnOpenAct = findViewById(R.id.btnOpenAct);
        spPhongBan = findViewById(R.id.spPhongBan);
        lvDanhSach = findViewById(R.id.lvDanhSach);

        // Kiểm tra xem database đã có dữ liệu chưa
        if (db.getSoLuongPhongBan() == 0) {
            db.addPhongBan(new PhongBan(1, "IT", 101));
            db.addPhongBan(new PhongBan(2, "HR", 102));
            db.addPhongBan(new PhongBan(3, "Finance", 103));
        }

        loadPhongBan();
        spPhongBan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loadNhanVien();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });

        btnThem.setOnClickListener(v -> themNhanVien());
        btnXoa.setOnClickListener(v -> xoaNhanVien());
        btnOpenAct.setOnClickListener(v -> moActivityKhac());

        lvDanhSach.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = danhSachNhanVien.get(position);
                String[] parts = selectedItem.split(":");
                if (parts.length == 4) {
                    String maNV = parts[1].trim();
                    String tenNV = parts[2].trim();
                    String tuoi = parts[3].trim();
                    String tenPhongBan = parts[0].trim();

                    edtMaNV.setText(maNV);
                    edtTenNV.setText(tenNV);
                    edtTuoi.setText(tuoi);

                    // Chọn phòng ban tương ứng trong Spinner
                    for (int i = 0; i < spPhongBan.getCount(); i++) {
                        if (spPhongBan.getItemAtPosition(i).toString().equals(tenPhongBan)) {
                            spPhongBan.setSelection(i);
                            break;
                        }
                    }
                }
            }
        });
    }

    private void loadPhongBan() {
        List<String> dsPhongBan = db.getAllPhongBan();
        if (dsPhongBan == null || dsPhongBan.isEmpty()) {
            Toast.makeText(this, "Không có phòng ban!", Toast.LENGTH_SHORT).show();
            return;
        }
        ArrayAdapter<String> adapterPhongBan = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, dsPhongBan);
        adapterPhongBan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spPhongBan.setAdapter(adapterPhongBan);
    }

    private void loadNhanVien() {
        String selectedPhongBan = spPhongBan.getSelectedItem().toString();
        if (selectedPhongBan == null || selectedPhongBan.isEmpty()) {
            Toast.makeText(this, "Vui lòng chọn phòng ban!", Toast.LENGTH_SHORT).show();
            return;
        }
        danhSachNhanVien = db.getNhanViensByPhongBan(selectedPhongBan);
        if (danhSachNhanVien == null || danhSachNhanVien.isEmpty()) {
            Toast.makeText(this, "Không có nhân viên trong phòng ban này!", Toast.LENGTH_SHORT).show();
            return;
        }
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, danhSachNhanVien);
        lvDanhSach.setAdapter(adapter);
    }

    private void themNhanVien() {
        String maNV = edtMaNV.getText().toString();
        String tenNV = edtTenNV.getText().toString();
        String tuoi = edtTuoi.getText().toString();
        String phongBan = spPhongBan.getSelectedItem() != null ? spPhongBan.getSelectedItem().toString() : "";

        if (maNV.isEmpty() || tenNV.isEmpty() || tuoi.isEmpty() || phongBan.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (db.checkNhanVienExists(maNV)) {
            Toast.makeText(this, "Mã nhân viên đã tồn tại!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Get PhongBan ID from database
        int maPB = db.getPhongBanIdFromDatabase(phongBan);

        if (maPB == -1) {
            Toast.makeText(this, "Không tìm thấy phòng ban!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a new NhanVien object
        NhanVien nv = new NhanVien(maNV, maPB, tenNV, Integer.parseInt(tuoi));

        // Add NhanVien to the database
        db.addNhanVien(nv);
        Toast.makeText(this, "Thêm nhân viên thành công!", Toast.LENGTH_SHORT).show();
        loadNhanVien();
    }


    private void xoaNhanVien() {
        String maNV = edtMaNV.getText().toString();
        if (maNV.isEmpty()) {
            Toast.makeText(this, "Vui lòng nhập mã nhân viên!", Toast.LENGTH_SHORT).show();
            return;
        }

        boolean result = db.deleteNhanVien(maNV);
        if (result) {
            Toast.makeText(this, "Xóa nhân viên thành công!", Toast.LENGTH_SHORT).show();
            loadNhanVien();
        } else {
            Toast.makeText(this, "Mã nhân viên không tồn tại!", Toast.LENGTH_SHORT).show();
        }
    }

    private void moActivityKhac() {
        // Kiểm tra nếu spPhongBan không phải null và có ít nhất 1 phần tử
        if (spPhongBan != null && spPhongBan.getSelectedItem() != null) {
            String selectedPhongBan = spPhongBan.getSelectedItem().toString();
            int soLuong = db.getSoLuongNhanVien(selectedPhongBan);

            Intent intent = new Intent(MainActivity_b2_1.this, MainActivity_b2_2.class);
            intent.putExtra("tenPhongBan", selectedPhongBan);
            intent.putExtra("soLuong", soLuong);
            startActivity(intent);
        } else {
            Toast.makeText(this, "Vui lòng chọn phòng ban!", Toast.LENGTH_SHORT).show();
        }
    }
}